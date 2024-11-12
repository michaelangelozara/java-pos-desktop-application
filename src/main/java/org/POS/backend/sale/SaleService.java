package org.POS.backend.sale;

import jakarta.transaction.Transactional;
import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.cash_transaction.CashTransactionPaymentMethod;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.exception.ResourceNotFoundException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.sale_item.AddSaleItemRequestDto;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.sale_item.SaleItemMapper;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.User;
import org.POS.backend.user.UserDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaleService {

    private CodeGeneratorService codeGeneratorService;

    private SaleDAO saleDAO;

    private SaleMapper saleMapper;

    private PersonDAO personDAO;

    private UserDAO userDAO;

    private SaleItemMapper saleItemMapper;

    private ProductDAO productDAO;

    public SaleService() {
        this.saleDAO = new SaleDAO();
        this.saleMapper = new SaleMapper();
        this.personDAO = new PersonDAO();
        this.userDAO = new UserDAO();
        this.saleItemMapper = new SaleItemMapper();
        this.productDAO = new ProductDAO();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public int add(AddSaleRequestDto dto, Set<AddSaleItemRequestDto> saleItemDtos) {
        var customer = this.personDAO.getValidPersonById(dto.customerId());
        var currentUser = this.userDAO.getUserById(CurrentUser.id);

        // get all product ids
        Set<Integer> productIds = new HashSet<>();
        for (var saleItemDto : saleItemDtos) {
            productIds.add(saleItemDto.productId());
        }

        if (customer != null && currentUser != null) {
            var sale = this.saleMapper.toSale(dto);
            sale.setReceiptNumber(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));
            sale.setVatSales(sale.getNetTotal().multiply(BigDecimal.valueOf(0.12)).setScale(2, RoundingMode.HALF_UP));
            customer.addSale(sale);
            currentUser.addSale(sale);

            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<Product> updatedProducts = new ArrayList<>();
            List<SaleItem> saleItems = new ArrayList<>();
            List<Stock> stocks = new ArrayList<>();
            for (var product : products) {
                for (var saleItemDto : saleItemDtos) {
                    if (saleItemDto.productId() == product.getId()) {
                        var saleItem = this.saleItemMapper.toSaleItem(saleItemDto);
                        product.addSaleItem(saleItem);
                        sale.addSaleItem(saleItem);


                        product.setStock(product.getStock() - saleItem.getQuantity());

                        Stock stock = new Stock();
                        stock.setDate(LocalDate.now());
                        stock.setStockInOrOut(saleItem.getQuantity());
                        stock.setPrice(saleItem.getPrice());
                        stock.setType(StockType.OUT);
                        stock.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.STOCK_IN_PREFIX));

                        currentUser.addStock(stock);
                        product.addStock(stock);

                        saleItems.add(saleItem);
                        updatedProducts.add(product);
                        stocks.add(stock);
                    }
                }
            }

            if (!dto.paymentMethod().equals(CashTransactionPaymentMethod.PO_PAYMENT) && !dto.paymentMethod().equals(CashTransactionPaymentMethod.ONLINE_PAYMENT)) {
                // cash transaction
                CashTransaction cashTransaction = new CashTransaction();
                cashTransaction.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.CASH_TRANSACTION_PREFIX));
                cashTransaction.setReference(sale.getNote());
                cashTransaction.setCashIn(sale.getNetTotal());
                cashTransaction.setCashOut(sale.getAmount().subtract(sale.getNetTotal()));
                cashTransaction.setCashTransactionPaymentMethod(dto.paymentMethod());
                cashTransaction.setDateTime(LocalDateTime.now());

                currentUser.addCashTransaction(cashTransaction);
                var savedSale = this.saleDAO.add(sale, saleItems, updatedProducts, stocks, cashTransaction);
                return savedSale.getId();
            }

            var savedSale = this.saleDAO.add(sale, saleItems, updatedProducts, stocks);
            return savedSale.getId();
        }

        return -1;
    }

    public List<SaleResponseDto> getAllValidSales(int numberOfSales) {
        return this.saleMapper.toSaleResponseDtoList(this.saleDAO.getAllValidSales(numberOfSales));
    }
}

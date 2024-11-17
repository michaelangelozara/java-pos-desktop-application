package org.POS.backend.sale;

import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.cash_transaction.TransactionPaymentMethod;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.invoice.InvoiceStatus;
import org.POS.backend.order.Order;
import org.POS.backend.order.OrderStatus;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.sale_item.AddSaleItemRequestDto;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.sale_item.SaleItemMapper;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
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

            String orderInvoiceCode = this.codeGeneratorService.generateProductCode();

            Order order = new Order();
            order.setSale(sale);
            order.setOrderDate(LocalDate.now());
            order.setCode(GlobalVariable.ORDER_PREFIX + orderInvoiceCode);
            customer.addOrder(order);

            Invoice invoice = new Invoice();
            invoice.setSale(sale);
            invoice.setCode(GlobalVariable.INVOICE_PREFIX + orderInvoiceCode);
            invoice.setDate(LocalDate.now());
            customer.addInvoice(invoice);

            if (dto.paymentMethod().equals(TransactionPaymentMethod.CASH_PAYMENT)) {
                // cash transaction
                CashTransaction cashTransaction = new CashTransaction();
                cashTransaction.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.CASH_TRANSACTION_PREFIX));
                cashTransaction.setReference(sale.getNote());
                cashTransaction.setCashIn(sale.getNetTotal());
                cashTransaction.setCashOut(sale.getAmount().subtract(sale.getNetTotal()));
                cashTransaction.setTransactionPaymentMethod(dto.paymentMethod());
                cashTransaction.setDateTime(LocalDateTime.now());

                order.setStatus(OrderStatus.COMPLETED);
                invoice.setStatus(InvoiceStatus.COMPLETED);

                currentUser.addCashTransaction(cashTransaction);

                sale.setTransactionMethod(SaleTransactionMethod.CASH_PAYMENT);
                sale.setAmountDue(BigDecimal.ZERO);
                sale.setReference(this.codeGeneratorService.generateProductCode(GlobalVariable.REFERENCE_PREFIX));


                var savedSale = this.saleDAO.add(sale, saleItems, updatedProducts, stocks, cashTransaction, order, invoice);
                return savedSale.getId();
            }else if(dto.paymentMethod().equals(TransactionPaymentMethod.PO_PAYMENT)){
                // this is PO payment
                order.setStatus(OrderStatus.PENDING);
                invoice.setStatus(InvoiceStatus.PENDING);
                sale.setTransactionMethod(SaleTransactionMethod.PO_PAYMENT);
                sale.setAmountDue(dto.netTotal().subtract(dto.amount()));
                sale.setReference(this.codeGeneratorService.generateProductCode(GlobalVariable.REFERENCE_PREFIX));

                var savedSale = this.saleDAO.add(sale, saleItems, updatedProducts, stocks, order, invoice);
                return savedSale.getId();
            }else{
                // online payment
                order.setStatus(OrderStatus.COMPLETED);
                invoice.setStatus(InvoiceStatus.COMPLETED);
                sale.setCode(dto.poReference());
                sale.setAmountDue(dto.netTotal().subtract(dto.amount()));
                sale.setTransactionMethod(SaleTransactionMethod.ONLINE_PAYMENT);
                sale.setReference(dto.poReference());
                var savedSale = this.saleDAO.add(sale, saleItems, updatedProducts, stocks, order, invoice);
                return savedSale.getId();
            }
        }
        return -1;
    }

    public List<SaleResponseDto> getAllValidSales(int numberOfSales) {
        return this.saleMapper.toSaleResponseDtoList(this.saleDAO.getAllValidSales(numberOfSales));
    }

    public List<SaleResponseDto> getAllValidSalesByRange(LocalDate start, LocalDate end){
        return this.saleMapper.toSaleResponseDtoList(this.saleDAO.getAllValidSalesByRange(start, end));
    }

    public List<Sale> getAllValidSalesWithNoDto(int numberOfSales){
        return this.saleDAO.getAllValidSales(numberOfSales);
    }

    public List<Sale> getAllValidSalesByCashTransactionType(TransactionPaymentMethod type){
        return this.saleDAO.getAllSalesByCashTransactionType(type);
    }
}

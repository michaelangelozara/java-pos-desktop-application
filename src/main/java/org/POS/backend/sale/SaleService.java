package org.POS.backend.sale;

import org.POS.backend.additional_fee.AdditionalFee;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.exception.CustomException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.order.Order;
import org.POS.backend.order.OrderStatus;
import org.POS.backend.payment.AddPaymentRequestDto;
import org.POS.backend.payment.PaymentMapper;
import org.POS.backend.payment.TransactionType;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.product.ProductType;
import org.POS.backend.sale_product.AddSaleProductRequestDto;
import org.POS.backend.sale_product.SaleProduct;
import org.POS.backend.sale_product.SaleProductMapper;
import org.POS.backend.shipping.AddShippingRequestDto;
import org.POS.backend.shipping.ShippingAddressMapper;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;
import org.POS.backend.user_log.UserLogMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaleService {

    private CodeGeneratorService codeGeneratorService;
    private SaleDAO saleDAO;
    private SaleMapper saleMapper;
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private SaleProductMapper saleProductMapper;
    private ProductDAO productDAO;
    private ShippingAddressMapper shippingAddressMapper;
    private PaymentMapper paymentMapper;
    private UserLogMapper userLogMapper;

    public SaleService() {
        this.saleDAO = new SaleDAO();
        this.saleMapper = new SaleMapper();
        this.personDAO = new PersonDAO();
        this.userDAO = new UserDAO();
        this.saleProductMapper = new SaleProductMapper();
        this.productDAO = new ProductDAO();
        this.codeGeneratorService = new CodeGeneratorService();
        this.shippingAddressMapper = new ShippingAddressMapper();
        this.paymentMapper = new PaymentMapper();
        this.userLogMapper = new UserLogMapper();
    }

    public int add(
            AddSaleRequestDto dto,
            List<AddSaleProductRequestDto> saleProductRequestDtoList,
            AddShippingRequestDto shippingRequestDto,
            AddPaymentRequestDto paymentRequestDto,
            List<AdditionalFee> additionalFees
    ) {
        try {
            var customer = this.personDAO.getValidPersonById(dto.customerId());
            var user = this.userDAO.getUserById(CurrentUser.id);

            if (user == null)
                throw new RuntimeException("Invalid Cashier");

            if (customer == null)
                throw new RuntimeException("Invalid Customer");

            Sale sale = new Sale();

            // get all product id
            Set<Integer> productIds = new HashSet<>();
            for (var saleProductDto : saleProductRequestDtoList) {
                productIds.add(saleProductDto.productId());
            }

            // fetch all the added product
            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            BigDecimal netTotal = BigDecimal.ZERO;
            for (var product : products) {
                for (var saleProductDto : saleProductRequestDtoList) {
                    if (product.getId().equals(saleProductDto.productId())) {
                        if (product.getProductType().equals(ProductType.VARIABLE)) {
                            for (var attribute : product.getProductAttributes()) {
                                for (var variation : attribute.getProductVariations()) {
                                    if (variation.getId().equals(saleProductDto.variationId())) {
                                        SaleProduct saleProduct = this.saleProductMapper.toSaleItem(saleProductDto, product);
                                        saleProduct.setPrice(variation.getSrp());
                                        saleProduct.setSubtotal(variation.getSrp().multiply(BigDecimal.valueOf(saleProduct.getQuantity())));

                                        // add the sale product to the variation
                                        variation.addSaleProduct(saleProduct);

                                        variation.setQuantity(variation.getQuantity() - saleProductDto.quantity());

                                        saleProduct.setProduct(product);
                                        sale.addSaleProduct(saleProduct);

                                        Stock stock = new Stock();
                                        stock.setUser(user);
                                        stock.setPerson(customer);
                                        stock.setStockInOrOut(saleProduct.getQuantity());
                                        stock.setPrice(saleProduct.getSubtotal());
                                        stock.setDate(LocalDate.now());
                                        stock.setType(StockType.OUT);
                                        stock.setCode(product.getProductCode());
                                        product.addStock(stock);

                                        netTotal = netTotal.add(variation.getSrp().multiply(BigDecimal.valueOf(saleProductDto.quantity())));
                                        break;
                                    }
                                }

                            }
                        } else {
                            SaleProduct saleProduct = this.saleProductMapper.toSaleItem(saleProductDto, product);
                            product.setStock(product.getStock() - saleProductDto.quantity());
                            saleProduct.setProduct(product);
                            sale.addSaleProduct(saleProduct);

                            Stock stock = new Stock();
                            stock.setUser(user);
                            stock.setPerson(customer);
                            stock.setStockInOrOut(saleProduct.getQuantity());
                            stock.setPrice(saleProduct.getSubtotal());
                            stock.setDate(LocalDate.now());
                            stock.setType(StockType.OUT);
                            stock.setCode(product.getProductCode());
                            product.addStock(stock);

                            netTotal = netTotal.add(product.getSellingPrice().multiply(BigDecimal.valueOf(saleProductDto.quantity())));
                        }
                    }

                }
            }

            BigDecimal totalAdditionalFees = BigDecimal.ZERO;
            for (var additionalFee : additionalFees) {
                AdditionalFee tempAdditional = new AdditionalFee();
                tempAdditional.setTitle(additionalFee.getTitle());
                tempAdditional.setAmount(additionalFee.getAmount());
                totalAdditionalFees = totalAdditionalFees.add(additionalFee.getAmount());
                sale.addAdditionalFee(tempAdditional);
            }

            if (!(shippingRequestDto.name() == null)
                    && !(shippingRequestDto.shippingAddress() == null)
                    && !(shippingRequestDto.phoneNumber() == null)) {
                // create shipping instance
                var shippingAddress = this.shippingAddressMapper.toShippingAddress(shippingRequestDto);
                shippingAddress.setSale(sale);
                sale.setShippingAddress(shippingAddress);
            }

            UserLog userLog = this.userLogMapper.toUserLog(UserActionPrefixes.POS_TRANSACTION_ACTION_LOG_PREFIX, sale.getSaleNumber());
            user.addUserLog(userLog);

            String codeForOrderAndInvoice = this.codeGeneratorService.generateProductCode();

            Order order = new Order();
            order.setSale(sale);
            order.setOrderNumber(GlobalVariable.ORDER_PREFIX + codeForOrderAndInvoice);

            Invoice invoice = new Invoice();
            invoice.setSale(sale);
            invoice.setInvoiceNumber(GlobalVariable.INVOICE_PREFIX + codeForOrderAndInvoice);

            // sale
            sale.setSaleNumber(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));
            sale.setNetTotal(netTotal.add(totalAdditionalFees));
            sale.setTotalTax(sale.getNetTotal().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));
            sale.setDate(LocalDate.now());
            sale.setVatSale(sale.getNetTotal().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));

            var payment = this.paymentMapper.toPayment(paymentRequestDto);
            // set up the payment
            if (paymentRequestDto.transactionType().equals(TransactionType.PO)) {
                order.setStatus(OrderStatus.PENDING);
                payment.setChangeAmount(BigDecimal.ZERO);
                payment.setReferenceNumber(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));
                payment.setAmountDue(sale.getNetTotal());
            } else if (paymentRequestDto.transactionType().equals(TransactionType.CASH)) {
                payment.setAmountDue(BigDecimal.ZERO);
                payment.setChangeAmount(paymentRequestDto.paidAmount().subtract(sale.getNetTotal()));
                order.setStatus(OrderStatus.COMPLETED);
                payment.setReferenceNumber(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));
            } else {
                order.setStatus(OrderStatus.COMPLETED);
                payment.setAmountDue(BigDecimal.ZERO);
            }

            sale.setPerson(customer);
            sale.setUser(user);
            sale.setOrder(order);
            sale.setInvoice(invoice);
            sale.setPayment(payment);

            userLog.setCode(sale.getSaleNumber());
            return this.saleDAO.add(sale, userLog, products);
        } catch (Exception e) {
            throw new CustomException("Error saving", e);
        }
    }

    public List<SaleResponseDto> getAllValidSales(int numberOfSales) {
        return this.saleMapper.toSaleResponseDtoList(this.saleDAO.getAllValidSales(numberOfSales));
    }

    public List<SaleResponseDto> getAllValidSalesByRange(LocalDate start, LocalDate end) {
        return this.saleMapper.toSaleResponseDtoList(this.saleDAO.getAllValidSalesByRange(start, end));
    }

    public List<Sale> getAllValidSalesWithoutDto(int numberOfSales) {
        return this.saleDAO.getAllValidSales(numberOfSales);
    }

    public List<Sale> getAllValidCashSales() {
        return this.saleDAO.getAllCashSales();
    }

    public List<Sale> getAllValidPOSalesWithLimit(int number) {
        return this.saleDAO.getAllValidPOSales(number, TransactionType.PO);
    }

    public List<Sale> getAllValidPOSalesWithoutLimit(LocalDate start, LocalDate end) {
        return this.saleDAO.getAllValidPOSales(start, end, TransactionType.PO);
    }

    public List<Sale> getAllValidSalesByRangeAndWithoutDto(LocalDate start, LocalDate end) {
        return this.saleDAO.getAllValidSalesByRangeWithoutDto(start, end);
    }

    public BigDecimal getTotalSales() {
        return this.saleDAO.getTotalSales();
    }
}

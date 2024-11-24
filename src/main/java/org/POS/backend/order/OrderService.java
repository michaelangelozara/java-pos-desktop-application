package org.POS.backend.order;

import jakarta.persistence.NoResultException;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.invoice.InvoiceDAO;
import org.POS.backend.payment.POLog;
import org.POS.backend.payment.Payment;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.return_product.ReturnItem;
import org.POS.backend.return_product.ReturnOrder;
import org.POS.backend.sale_product.SaleProductDAO;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    private UserDAO userDAO;

    private SaleProductDAO saleProductDAO;

    private CodeGeneratorService codeGeneratorService;

    private InvoiceDAO invoiceDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.saleProductDAO = new SaleProductDAO();
        this.codeGeneratorService = new CodeGeneratorService();
        this.userDAO = new UserDAO();
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Order> getAllValidOrder(int number) {
        return this.orderDAO.getAllValidOrders(number);
    }

    public Order getValidOrderById(int orderId) {
        return this.orderDAO.getValidOrderById(orderId);
    }

    private int getRangeOfDay(LocalDate date) {
        return (int) ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    public void updateSaleAmountDue(Order order, BigDecimal pay) {
        var invoice = this.invoiceDAO.getValidInvoiceById(order.getSale().getInvoice().getId());
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (invoice != null && user != null) {
            try{
                var sale = order.getSale();
                var payment = sale.getPayment();
                payment.setAmountDue(payment.getAmountDue().subtract(pay));

                POLog poLog = new POLog();
                payment.addPOLog(poLog);
                poLog.setPaidAmount(pay);
                poLog.setDate(LocalDate.now());

                if (payment.getAmountDue().compareTo(BigDecimal.ZERO) <= 0) {
                    order.setStatus(OrderStatus.COMPLETED);
                }
                // update order and sale
                this.orderDAO.updateOrderAmountDue(order, sale);
            }catch (Exception e){
                throw e;
            }
        } else {
            throw new RuntimeException("Invalid order.");
        }
    }

    public void update(UpdateOrderRequestDto dto) {
        try {
            var order = this.orderDAO.getValidOrderById(dto.orderId());
            var user = this.userDAO.getUserById(CurrentUser.id);
            if (order != null && user != null) {

                // check if the order placed more than 7 days ago
                if (getRangeOfDay(order.getSale().getDate()) > 1) {
                    throw new RuntimeException("You can't perform this action anymore.");
                }

                ReturnOrder returnOrder = new ReturnOrder();
                returnOrder.setReturnReason(dto.reason());
                returnOrder.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.RETURN_PRODUCT_PREFIX));
                returnOrder.setUser(user);
                returnOrder.setReturnedAt(LocalDate.now());
                returnOrder.setOrder(order);

                UserLog userLog = new UserLog();
                userLog.setCode(returnOrder.getCode());
                userLog.setDate(LocalDate.now());
                userLog.setAction(UserActionPrefixes.RETURNED_ORDER_ADD_ACTION_LOG_PREFIX);
                user.addUserLog(userLog);

                List<Integer> productItemIds = new ArrayList<>();
                for (var returnItem : dto.returnItems()) {
                    productItemIds.add(returnItem.productId());
                }
                var saleItems = this.saleProductDAO.getAllValidSaleItemsByIds(productItemIds);

                List<Product> updatedProducts = new ArrayList<>();

                BigDecimal costOfReturnedProducts = BigDecimal.ZERO;
                for (var saleProduct : saleItems) {
                    for (var returnItemDto : dto.returnItems()) {

                        if (saleProduct.getId().equals(returnItemDto.productId())) {
                            ReturnItem returnItem = new ReturnItem();
                            returnOrder.addReturnItem(returnItem);
                            returnItem.setSaleProduct(saleProduct);
                            returnItem.setReturnedDate(LocalDate.now());
                            returnItem.setReturnedQuantity(returnItemDto.returnedQuantity());

                            saleProduct.setQuantity(saleProduct.getQuantity() - returnItemDto.returnedQuantity());
                            saleProduct.setSubtotal(saleProduct.getSubtotal().subtract(saleProduct.getPrice().multiply(BigDecimal.valueOf(returnItem.getReturnedQuantity()))));
                            var product = saleProduct.getProduct();

                            if (product.getProductType().equals(ProductType.VARIABLE)) {
                                var tempVariation = saleProduct.getProductVariation();
                                tempVariation.setQuantity(tempVariation.getQuantity() + returnItemDto.returnedQuantity());
                            } else {
                                product.setStock(product.getStock() + returnItemDto.returnedQuantity());
                            }

                            Stock stock = new Stock();
                            stock.setDate(LocalDate.now());
                            stock.setStockInOrOut(returnItem.getReturnedQuantity());
                            stock.setPrice(saleProduct.getPrice().multiply(BigDecimal.valueOf(returnItemDto.returnedQuantity())));
                            stock.setType(StockType.IN);
                            stock.setCode(product.getProductCode());
                            user.addStock(stock);
                            product.addStock(stock);

                            costOfReturnedProducts = costOfReturnedProducts.add(stock.getPrice());

                            updatedProducts.add(product);
                            break;
                        }
                    }

                }

                returnOrder.setCostOfReturnProducts(costOfReturnedProducts);
                var sale = order.getSale();
                sale.setNetTotal(order.getSale().getNetTotal().subtract(costOfReturnedProducts));
                this.orderDAO.update(updatedProducts, returnOrder, userLog, sale);
            } else {
                throw new NoResultException("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllValidOrderByClientNameOrOrderIdOrTransactionMethodOrStatus(String code) {
        return this.orderDAO.getAllValidOrdersByCode(code);
    }

    public List<Order> getAllValidOrderByRange(LocalDate start, LocalDate end) {
        return this.orderDAO.getAllValidOrdersByRange(start, end);
    }
}

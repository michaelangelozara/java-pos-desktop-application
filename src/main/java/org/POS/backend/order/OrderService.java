package org.POS.backend.order;

import jakarta.persistence.NoResultException;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.payment.Payment;
import org.POS.backend.product.Product;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.sale_item.SaleItemDAO;
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

    private SaleItemDAO saleItemDAO;

    private CodeGeneratorService codeGeneratorService;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.saleItemDAO = new SaleItemDAO();
        this.codeGeneratorService = new CodeGeneratorService();
        this.userDAO = new UserDAO();
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
        var sale = order.getSale();
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (sale != null && user != null) {
            sale.setAmountDue(sale.getAmountDue().subtract(pay));

            var invoice = order.getSale().getInvoice();

            Payment payment = new Payment();
            payment.setDate(LocalDate.now());
            payment.setNetTotal(sale.getNetTotal());
            payment.setPaidAmount(pay);
            payment.setAmountDue(sale.getAmountDue());
            order.getPerson().addPayment(payment);
            user.addPayment(payment);
            invoice.addPayment(payment);

            if (sale.getAmountDue().compareTo(BigDecimal.ZERO) <= 0) {
                order.setStatus(OrderStatus.COMPLETED);
            }
            // update order and sale
            this.orderDAO.updateOrderAmountDue(order, sale, payment);
        } else {
            throw new RuntimeException("Invalid order.");
        }
    }

    public void update(UpdateOrderRequestDto dto) {
        var order = this.orderDAO.getValidOrderById(dto.orderId());
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (order != null && user != null) {

            // check if the order placed more than 7 days ago
            if (getRangeOfDay(order.getSale().getDate()) > 1) {
                throw new RuntimeException("You can't perform this action anymore.");
            }

            var sale = order.getSale();

            ReturnProduct returnProduct = new ReturnProduct();
            returnProduct.setReturnReason(dto.reason());
            returnProduct.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.RETURN_PRODUCT_PREFIX));
            returnProduct.setOrder(order);
            returnProduct.setUser(user);
            returnProduct.setReturnedAt(LocalDate.now());

            UserLog userLog = new UserLog();
            userLog.setCode(returnProduct.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.RETURNED_ORDER_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            var saleItems = this.saleItemDAO.getAllValidSaleItemsByIds(dto.returnedProductIds());
            BigDecimal costOfReturnedProducts = BigDecimal.ZERO;
            // set these sale items as return

            List<Product> updatedProducts = new ArrayList<>();

            for (var saleItem : saleItems) {
                saleItem.setReturned(true);
                saleItem.setReturnedAt(LocalDate.now());
                costOfReturnedProducts = costOfReturnedProducts.add(saleItem.getSubtotal());
                returnProduct.addReturnSaleItem(saleItem);

                Stock stock = new Stock();
                stock.setDate(LocalDate.now());
                stock.setStockInOrOut(saleItem.getQuantity());
                stock.setPrice(saleItem.getPrice().multiply(BigDecimal.valueOf(saleItem.getQuantity())));
                stock.setType(StockType.IN);
                stock.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.STOCK_IN_PREFIX));
                stock.setPerson(order.getPerson());

                user.addStock(stock);
                order.getPerson().getStocks().add(stock);

                var updatedProduct = saleItem.getProduct();
                updatedProduct.setStock(updatedProduct.getStock() + saleItem.getQuantity());

                updatedProduct.addStock(stock);

                updatedProducts.add(updatedProduct);
            }

            returnProduct.setCostOfReturnProducts(costOfReturnedProducts);

            // deduct the cost of returned item from sale
            sale.setNetTotal(sale.getNetTotal().subtract(costOfReturnedProducts));
            sale.setAmountDue(
                    sale.getAmountDue().subtract(costOfReturnedProducts).compareTo(BigDecimal.ZERO) < 0
                            ? BigDecimal.ZERO
                            : sale.getAmountDue().subtract(costOfReturnedProducts)
            );

            boolean areAllReturned = true;
            for (var saleItem : order.getSale().getSaleItems()) {
                if (!saleItem.isReturned()) {
                    areAllReturned = false;
                    break;
                }
            }

            if (areAllReturned) {
                order.setStatus(OrderStatus.RETURNED);
            }

            if (order.getSale().getAmountDue().compareTo(BigDecimal.ZERO) <= 0) {
                order.setStatus(OrderStatus.COMPLETED);
            }

            // re-set the delivery address and note
            sale.setDeliveryPlace(dto.deliveryAddress());
            sale.setNote(dto.note());

            this.orderDAO.update(order, returnProduct, sale, updatedProducts, saleItems, userLog);
        } else {
            throw new NoResultException("Order not found");
        }
    }

    public List<Order> getAllValidOrderByClientNameOrOrderIdOrTransactionMethodOrStatus(String code) {
        return this.orderDAO.getAllValidOrdersByCode(code);
    }

    public List<Order> getAllValidOrderByRange(LocalDate start, LocalDate end) {
        return this.orderDAO.getAllValidOrdersByRange(start, end);
    }
}

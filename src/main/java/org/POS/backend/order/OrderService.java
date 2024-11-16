package org.POS.backend.order;

import jakarta.persistence.NoResultException;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product.Product;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.sale_item.SaleItemDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    private SaleItemDAO saleItemDAO;

    private CodeGeneratorService codeGeneratorService;

    public OrderService(){
        this.orderDAO = new OrderDAO();
        this.saleItemDAO = new SaleItemDAO();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public List<Order> getAllValidOrder(int number){
        return this.orderDAO.getAllValidOrders(number);
    }

    public Order getValidOrderById(int orderId){
        return this.orderDAO.getValidOrderById(orderId);
    }

    private int getRangeOfDay(LocalDate date){
        return (int) ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    public void update(UpdateOrderRequestDto dto){
        var order = this.orderDAO.getValidOrderById(dto.orderId());
        if(order != null){

            // check if the order placed more than 7 days ago
            if(getRangeOfDay(order.getSale().getDate()) >= 7){
                throw new RuntimeException("You can't perform this action anymore.");
            }

            var sale = order.getSale();

            ReturnProduct returnProduct = new ReturnProduct();
            returnProduct.setReturnedAt(LocalDate.now());
            returnProduct.setReturnReason(dto.reason());
            returnProduct.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.RETURN_PRODUCT_PREFIX));

            var saleItems = this.saleItemDAO.getAllValidSaleItemsByIds(dto.returnedProductId());
            BigDecimal costOfReturnedProducts = BigDecimal.ZERO;
            // set these sale items as return

            List<Product> updatedProducts = new ArrayList<>();

            for(var saleItem : saleItems){
                saleItem.setReturned(true);
                costOfReturnedProducts = costOfReturnedProducts.add(saleItem.getSubtotal());
                returnProduct.addReturnSaleItem(saleItem);

                var updatedProduct = saleItem.getProduct();
                updatedProduct.setStock(updatedProduct.getStock() + saleItem.getQuantity());
                updatedProducts.add(updatedProduct);
            }

            returnProduct.setCostOfReturnProducts(costOfReturnedProducts);

            // deduct the cost of returned item from sale
            sale.setNetTotal(sale.getNetTotal().subtract(costOfReturnedProducts));

            // re-set the delivery address and note
            sale.setDeliveryPlace(dto.deliveryAddress());
            sale.setNote(dto.note());

            this.orderDAO.update(returnProduct, sale, updatedProducts, saleItems);
        }else{
            throw new NoResultException("Order not found");
        }
    }
}

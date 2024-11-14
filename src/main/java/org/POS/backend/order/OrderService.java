package org.POS.backend.order;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService(){
        this.orderDAO = new OrderDAO();
    }

    public List<Order> getAllValidOrder(int number){
        return this.orderDAO.getAllValidOrders(number);
    }
}

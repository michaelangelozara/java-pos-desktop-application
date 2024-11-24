package org.POS.backend.return_product;

import java.util.List;

public class ReturnOrderService {

    private ReturnOrderDAO returnOrderDAO;

    public ReturnOrderService(){
        this.returnOrderDAO = new ReturnOrderDAO();
    }

    public List<ReturnOrder> getAllValidReturnOrder(int number){
        return this.returnOrderDAO.getAllValidReturnedProducts(number);
    }
}

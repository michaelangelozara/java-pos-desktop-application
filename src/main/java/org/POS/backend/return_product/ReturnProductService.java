package org.POS.backend.return_product;

import java.util.List;

public class ReturnProductService {

    private ReturnProductDAO returnProductDAO;

    public ReturnProductService(){
        this.returnProductDAO = new ReturnProductDAO();
    }

    public List<ReturnProduct> getAllValidReturnProductsByQuery(String query){
        return this.returnProductDAO.getAllValidReturnProductsByCode(query);
    }
}

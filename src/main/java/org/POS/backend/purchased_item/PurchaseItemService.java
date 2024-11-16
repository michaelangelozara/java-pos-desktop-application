package org.POS.backend.purchased_item;

public class PurchaseItemService {

    private PurchaseItemDAO purchaseItemDAO;

    public PurchaseItemService() {
        this.purchaseItemDAO = new PurchaseItemDAO();
    }


    public void deletePurchaseProductByPurchaseProductId(int id){
        this.purchaseItemDAO.delete(id);
    }
}

package org.POS.backend.purchase;

import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.purchased_product.PurchaseProductService;

import java.util.List;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;

    private PurchaseMapper purchaseMapper;

    private PersonDAO personDAO;

    private PurchaseProductService purchaseProductService;

    public PurchaseService() {
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseMapper = new PurchaseMapper();
        this.personDAO = new PersonDAO();
        this.purchaseProductService = new PurchaseProductService();
    }

    public String add(AddPurchaseRequestDto dto) {
        var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);

        if (supplier == null)
            return GlobalVariable.PERSON_NOT_FOUND;


        var purchase = this.purchaseMapper.toPurchase(dto, supplier);
        this.purchaseDAO.add(purchase);

//        this.purchaseProductService.add(purchase, );

        return GlobalVariable.PURCHASE_ADDED;
    }

    private List<PurchaseProduct> computeProductsSubtotal(List<PurchaseProduct> purchaseProducts) {
        return null;
    }

    public List<PurchaseResponseDto> getAllValidPurchases() {
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchases());
    }

    public List<PurchaseResponseDto> getAllValidPurchaseBySupplierId(int supplierId) {
        return
                this.purchaseMapper
                        .toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchaseBySupplierId(supplierId));
    }

}
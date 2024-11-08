package org.POS.backend.purchase;

import jakarta.transaction.Transactional;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
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

    @Transactional
    public String add(AddPurchaseRequestDto dto) {
        var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);

        if (supplier == null)
            return GlobalVariable.PERSON_NOT_FOUND;


        var purchase = this.purchaseMapper.toPurchase(dto, supplier);
        var savedPurchase = this.purchaseDAO.add(purchase);
        purchaseProductService.add(savedPurchase, dto.purchaseProducts());
        return GlobalVariable.PURCHASE_ADDED;
    }

    public void update(UpdatePurchaseRequestDto dto){
        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());

        if(purchase != null){
            var updatedPurchase = this.purchaseMapper.toUpdatedPurchase(purchase, dto);
            this.purchaseDAO.update(updatedPurchase);
            purchaseProductService.update(dto.purchaseProducts(), updatedPurchase);
        }
    }

    public PurchaseResponseDto getValidPurchaseByPurchaseId(int purchaseId){
        return this.purchaseMapper.toPurchaseResponseDto(this.purchaseDAO.getValidPurchaseById(purchaseId));
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

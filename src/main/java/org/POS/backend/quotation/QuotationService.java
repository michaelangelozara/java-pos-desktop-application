package org.POS.backend.quotation;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.purchased_item.UpdatePurchaseItemRequestDto;
import org.POS.backend.quoted_item.*;
import org.POS.backend.user.UserDAO;

import java.util.*;

public class QuotationService {

    private QuotationDAO quotationDAO;

    private UserDAO userDAO;

    private ProductDAO productDAO;

    private PersonDAO personDAO;

    private QuotationMapper quotationMapper;

    private QuotedItemMapper quotedItemMapper;

    private QuotedItemDAO quotedItemDAO;

    public QuotationService() {
        this.quotationDAO = new QuotationDAO();
        this.productDAO = new ProductDAO();
        this.userDAO = new UserDAO();
        this.personDAO = new PersonDAO();
        this.quotationMapper = new QuotationMapper();
        this.quotedItemMapper = new QuotedItemMapper();
        this.quotedItemDAO = new QuotedItemDAO();
    }

    public String add(AddQuotationRequestDto dto, Set<AddQuotedItemRequestDto> quotedItemDtoList) {
        var client = this.personDAO.getValidPersonByTypeAndId(dto.clientId(), PersonType.CLIENT);
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (client != null && user != null) {
            Set<Integer> productIds = new HashSet<>();
            for (var quotedItem : quotedItemDtoList) {
                productIds.add(quotedItem.productId());
            }

            Quotation quotation = this.quotationMapper.toQuotation(dto);
            client.addQuotation(quotation);
            user.addQuotation(quotation);

            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<QuotedItem> quotedItems = new ArrayList<>();
            for (var product : products) {
                for (var quotedItemDto : quotedItemDtoList) {
                    if (product.getId() == quotedItemDto.productId()) {
                        var quotedItem = this.quotedItemMapper.toQuotedItem(quotedItemDto);
                        product.addQuotedItem(quotedItem);
                        quotation.addQuotedItem(quotedItem);
                        quotedItems.add(quotedItem);
                        break;
                    }
                }
            }
            this.quotationDAO.add(quotation, quotedItems);
        }
        return GlobalVariable.PURCHASE_ADDED;
    }

    public void update(UpdateQuotationRequestDto dto) {
        var quotation = this.quotationDAO.getValidQuotationById(dto.quotationId());
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (quotation != null && user != null) {
            var updatedQuotation = this.quotationMapper.toUpdatedQuotation(quotation, dto);

            Set<String> productCodes = new HashSet<>();
            List<UpdateQuotedItemRequestDto> newUpdateQuotedItemList = new ArrayList<>();

            for (var purchaseItemDto : dto.quotedItems()) {
                if (purchaseItemDto.quotedItemId() == null) {
                    productCodes.add(purchaseItemDto.productCode());
                    newUpdateQuotedItemList.add(purchaseItemDto);
                }
            }

            // get all the products that equaled to products' codes
            var products = this.productDAO.getAllValidProductByProductCode(productCodes);
            // iterate all the product and save the purchase item
            for (var product : products) {
                for (var quotedItemDto : newUpdateQuotedItemList) {
                    if (product.getCode().equals(quotedItemDto.productCode())) {
                        QuotedItem quotedItem = new QuotedItem();
                        quotedItem.setQuantity(quotedItemDto.quantity());
                        quotedItem.setPurchasePrice(quotedItemDto.purchasePrice());
                        quotedItem.setSellingPrice(quotedItemDto.sellingPrice());
                        quotedItem.setTax(quotedItemDto.taxValue());
                        quotedItem.setSubtotal(quotedItemDto.subtotal());

                        product.addQuotedItem(quotedItem);
                        updatedQuotation.addQuotedItem(quotedItem);
                        break;
                    }
                }
            }

            Set<Integer> quotedItemIds = new HashSet<>();
            // filter all existing quoted item
            for (var purchaseItemDto : dto.quotedItems()) {
                if (purchaseItemDto.quotedItemId() != null) {
                    quotedItemIds.add(purchaseItemDto.quotedItemId());
                }
            }

            List<QuotedItem> updatedQuotedItems = new ArrayList<>();

            // fetch all valid purchase items
            var quotedItems = this.quotedItemDAO.getALlValidQuotedItemByIds(quotedItemIds);
            for (var quotedItem : quotedItems) {
                for (var quotedItemDto : dto.quotedItems()) {
                    if (quotedItem.getId().equals(quotedItemDto.quotedItemId())) {
                        quotedItem.setQuantity(quotedItemDto.quantity());
                        quotedItem.setPurchasePrice(quotedItemDto.purchasePrice());
                        quotedItem.setSellingPrice(quotedItemDto.sellingPrice());
                        quotedItem.setTax(quotedItemDto.taxValue());
                        quotedItem.setSubtotal(quotedItemDto.subtotal());
                        updatedQuotedItems.add(quotedItem);
                        break;
                    }
                }
            }

            // save here
            this.quotationDAO.update(updatedQuotation, updatedQuotedItems);
        }
    }

    public List<Quotation> getAllValidQuotation(int number) {
        return this.quotationDAO.getAllValidQuotation(number);
    }

    public Quotation getValidQuotationById(int id) {
        return this.quotationDAO.getValidQuotationById(id);
    }

    public List<Quotation> getAllValidQuotationsByCustomerName(String name) {
        return this.quotationDAO.getAllValidQuotationByCustomerName(name);
    }
}

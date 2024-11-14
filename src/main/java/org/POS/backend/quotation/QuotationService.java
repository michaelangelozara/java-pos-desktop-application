package org.POS.backend.quotation;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.quoted_item.AddQuotedItemRequestDto;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.quoted_item.QuotedItemMapper;
import org.POS.backend.user.UserDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuotationService {

    private QuotationDAO quotationDAO;

    private UserDAO userDAO;

    private ProductDAO productDAO;

    private PersonDAO personDAO;

    private QuotationMapper quotationMapper;

    private QuotedItemMapper quotedItemMapper;

    public QuotationService(){
        this.quotationDAO = new QuotationDAO();
        this.productDAO = new ProductDAO();
        this.userDAO = new UserDAO();
        this.personDAO = new PersonDAO();
        this.quotationMapper = new QuotationMapper();
        this.quotedItemMapper = new QuotedItemMapper();
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

    public List<Quotation> getAllValidQuotation(int number){
        return this.quotationDAO.getAllValidQuotation(number);
    }

    public Quotation getValidQuotationById(int id){
        return this.quotationDAO.getValidQuotationById(id);
    }
}

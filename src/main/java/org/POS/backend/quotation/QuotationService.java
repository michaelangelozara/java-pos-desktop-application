package org.POS.backend.quotation;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.product_attribute.ProductVariationDAO;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.quoted_item.QuotedItemDAO;
import org.POS.backend.quoted_item.QuotedItemType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuotationService {

    private QuotationDAO quotationDAO;

    private UserDAO userDAO;

    private ProductDAO productDAO;

    private PersonDAO personDAO;

    private QuotationMapper quotationMapper;

    private QuotedItemDAO quotedItemDAO;

    private ProductVariationDAO productVariationDAO;

    public QuotationService() {
        this.quotationDAO = new QuotationDAO();
        this.productDAO = new ProductDAO();
        this.userDAO = new UserDAO();
        this.personDAO = new PersonDAO();
        this.quotationMapper = new QuotationMapper();
        this.quotedItemDAO = new QuotedItemDAO();
        this.productVariationDAO = new ProductVariationDAO();
    }

    public String add(AddQuotationRequestDto dto) {
        var client = this.personDAO.getValidPersonByTypeAndId(dto.clientId(), PersonType.CLIENT);
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (client != null && user != null) {
            Set<Integer> simpleProductIds = new HashSet<>();
            for (var quotedItem : dto.quotedItemDtoList()) {
                if (quotedItem.type().equals(QuotedItemType.SIMPLE)) {
                    simpleProductIds.add(quotedItem.productId());
                }
            }

            Set<Integer> variationProductIds = new HashSet<>();
            for (var quotedItem : dto.quotedItemDtoList()) {
                if (quotedItem.type().equals(QuotedItemType.VARIABLE)) {
                    variationProductIds.add(quotedItem.variationId());
                }

            }

            Quotation quotation = this.quotationMapper.toQuotation(dto);
            client.addQuotation(quotation);
            user.addQuotation(quotation);

            var variations = this.productVariationDAO.getAllValidProductVariationByIds(variationProductIds);
            var products = this.productDAO.getAllValidProductsByProductIds(simpleProductIds);

            BigDecimal subtotal = BigDecimal.ZERO;

            for (var quotedItemDto : dto.quotedItemDtoList()) {
                if (quotedItemDto.type().equals(QuotedItemType.SIMPLE)) {
                    boolean isEqualed = false;
                    for (var product : products) {
                        if (product.getId().equals(quotedItemDto.productId())) {
                            QuotedItem quotedItem = new QuotedItem();
                            quotedItem.setQuantity(quotedItemDto.quantity());
                            quotedItem.setPurchasePrice(product.getPurchasePrice());
                            quotedItem.setSellingPrice(product.getSellingPrice());
                            quotedItem.setQuantity(quotedItemDto.quantity());
                            quotedItem.setQuotedItemType(QuotedItemType.SIMPLE);
                            quotedItem.setSubtotal(product.getSellingPrice().multiply(BigDecimal.valueOf(quotedItemDto.quantity())));

                            quotedItem.setProduct(product);
                            quotedItem.setVariation(null);

                            quotation.addQuotedItem(quotedItem);
                            isEqualed = true;

                            subtotal = subtotal.add(product.getSellingPrice().multiply(BigDecimal.valueOf(quotedItemDto.quantity())));
                            break;
                        }
                    }
                    if (!isEqualed) {
                        throw new RuntimeException("Invalid Simple Product");
                    }
                } else {
                    boolean isEqualed = false;
                    for (var variation : variations) {
                        if (variation.getId().equals(quotedItemDto.variationId())) {
                            QuotedItem quotedItem = new QuotedItem();
                            quotedItem.setQuantity(quotedItemDto.quantity());
                            quotedItem.setPurchasePrice(variation.getPurchasePrice());
                            quotedItem.setSellingPrice(variation.getSrp());
                            quotedItem.setQuantity(quotedItemDto.quantity());
                            quotedItem.setQuotedItemType(QuotedItemType.VARIABLE);
                            quotedItem.setSubtotal(variation.getSrp().multiply(BigDecimal.valueOf(quotedItemDto.quantity())));

                            quotedItem.setProduct(null);
                            quotedItem.setVariation(variation);

                            quotation.addQuotedItem(quotedItem);
                            isEqualed = true;
                            subtotal = subtotal.add(variation.getSrp().multiply(BigDecimal.valueOf(quotedItemDto.quantity())));
                            break;
                        }
                    }
                    if (!isEqualed) {
                        throw new RuntimeException("Invalid Variation");
                    }
                }
            }

            quotation.setSubtotal(subtotal);

            UserLog userLog = new UserLog();
            userLog.setCode(quotation.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.QUOTATION_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.quotationDAO.add(quotation, userLog);
        }
        return GlobalVariable.PURCHASE_ADDED;
    }

    public void delete(int id) {
        try {
            this.quotationDAO.delete(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(UpdateQuotationRequestDto dto) {
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

    public List<Quotation> getAllValidQuotationsByRange(LocalDate start, LocalDate end){
        return this.quotationDAO.getAllValidQuotationsByRange(start, end);
    }
}

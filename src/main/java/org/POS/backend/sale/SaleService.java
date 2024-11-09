package org.POS.backend.sale;

import jakarta.transaction.Transactional;
import org.POS.backend.exception.ResourceNotFoundException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.sale_item.AddSaleItemRequestDto;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.sale_item.SaleItemMapper;
import org.POS.backend.user.UserDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaleService {

    private SaleDAO saleDAO;

    private SaleMapper saleMapper;

    private PersonDAO personDAO;

    private UserDAO userDAO;

    private SaleItemMapper saleItemMapper;

    private ProductDAO productDAO;

    public SaleService(){
        this.saleDAO = new SaleDAO();
        this.saleMapper = new SaleMapper();
        this.personDAO = new PersonDAO();
        this.userDAO = new UserDAO();
        this.saleItemMapper = new SaleItemMapper();
        this.productDAO = new ProductDAO();
    }

    @Transactional
    public String add(AddSaleRequestDto dto, Set<AddSaleItemRequestDto> saleItemDtos){
        var customer = this.personDAO.getValidPersonById(dto.customerId());
        var currentUser = this.userDAO.getUserById(CurrentUser.id);

        // get all product ids
        Set<Integer> productIds = new HashSet<>();
        for(var saleItemDto : saleItemDtos){
            productIds.add(saleItemDto.productId());
        }

        if(customer != null && currentUser != null){
            var sale = this.saleMapper.toSale(dto);
            customer.addSale(sale);
            currentUser.addSale(sale);

            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<SaleItem> saleItems = new ArrayList<>();
            for(var product : products){
                for(var saleItemDto : saleItemDtos){
                    if(saleItemDto.productId() == product.getId()){
                        var saleItem = this.saleItemMapper.toSaleItem(saleItemDto);
                        product.addSaleItem(saleItem);
                        sale.addSaleItem(saleItem);

                        saleItems.add(saleItem);
                    }
                }
            }
            this.saleDAO.add(sale, saleItems);

            return GlobalVariable.SALE_ADDED;
        }

        return GlobalVariable.USER_NOT_FOUND;
    }
}

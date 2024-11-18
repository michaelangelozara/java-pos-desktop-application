package org.POS.backend.stock;

import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;

import java.util.List;

public class StockService {

    private StockDAO stockDAO;

    private PersonDAO personDAO;

    private StockMapper stockMapper;

    public StockService(){
        this.stockDAO = new StockDAO();
        this.personDAO = new PersonDAO();
        this.stockMapper = new StockMapper();
    }

    public String add(AddStockRequestDto dto){
        var person = this.personDAO.getValidPersonById(dto.personId());
        if(person != null){
            var stock = this.stockMapper.toStock(dto, person);
            this.stockDAO.add(stock);
            if(dto.type().equals(StockType.IN)){
                return GlobalVariable.STOCK_IN_ADDED;
            }else{
                return GlobalVariable.STOCK_OUT_ADDED;
            }
        }
        return GlobalVariable.PERSON_NOT_FOUND;
    }

    public List<Stock> getAllValidStockByTypeAndProductId(StockType type, int productId){
        return this.stockDAO.getAllValidStockProductId(type, productId);
    }
}
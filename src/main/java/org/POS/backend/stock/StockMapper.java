package org.POS.backend.stock;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.Person;
import org.POS.backend.person.PersonMapper;

import java.time.LocalDate;
import java.util.List;

public class StockMapper {

    private CodeGeneratorService codeGeneratorService;

    private PersonMapper personMapper;

    public StockMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
        this.personMapper = new PersonMapper();
    }

    public Stock toStock(AddStockRequestDto dto, Person person){
        Stock stock = new Stock();
        stock.setPerson(person);
        stock.setPrice(dto.price());
        stock.setStockInOrOut(dto.stockInOrOut());
        stock.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.STOCK_IN_PREFIX));
        stock.setType(dto.type());
        stock.setDate(LocalDate.now());
        return stock;
    }

    public StockResponseDto toStockResponseDto(Stock stock){
        return new StockResponseDto(
                stock.getDate(),
                stock.getStockInOrOut(),
                stock.getPrice(),
                stock.getCode(),
                this.personMapper.personResponseDto(stock.getPerson())
        );
    }

    public List<StockResponseDto> toStockResponseDtoList(List<Stock> stocks){
        return stocks
                .stream()
                .map(this::toStockResponseDto)
                .toList();
    }
}

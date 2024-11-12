package org.POS.backend.sale;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;
import java.util.List;

public class SaleMapper {

    private CodeGeneratorService codeGeneratorService;

    public SaleMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Sale toSale(AddSaleRequestDto dto){
        Sale sale = new Sale();
        sale.setDiscountType(dto.discountType());
        sale.setDiscount(dto.discount());
        sale.setTransportCost(dto.transportCost());
        sale.setTotalTax(dto.totalTax());
        sale.setNetTotal(dto.netTotal());
        sale.setReceiptNumber(dto.receiptNumber());
        sale.setAmount(dto.amount());
        sale.setDate(LocalDate.now());
        sale.setPoReference(dto.poReference());
        sale.setDeliveryPlace(dto.deliveryPlace());
        sale.setNote(dto.note());
        sale.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));

        return sale;
    }

    public SaleResponseDto toSaleResponseDto(Sale sale){
        return new SaleResponseDto(
                sale.getId(),
                sale.getUser().getName(),
                sale.getCode(),
                sale.getPerson().getName(),
                sale.getNetTotal(),
                sale.getDate()
        );
    }

    public List<SaleResponseDto> toSaleResponseDtoList(List<Sale> sales){
        return sales
                .stream()
                .map(this::toSaleResponseDto)
                .toList();
    }
}

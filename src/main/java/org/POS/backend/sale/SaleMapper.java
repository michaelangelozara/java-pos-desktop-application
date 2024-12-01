package org.POS.backend.sale;

import java.util.List;

public class SaleMapper {

    public SaleResponseDto toSaleResponseDto(Sale sale){
        return new SaleResponseDto(
                sale.getId(),
                sale.getUser().getName(),
                sale.getSaleNumber(),
                sale.getPerson().getName(),
                sale.getNetTotal(),
                sale.getDate(),
                sale.getInvoice()
        );
    }

    public List<SaleResponseDto> toSaleResponseDtoList(List<Sale> sales){
        return sales
                .stream()
                .map(this::toSaleResponseDto)
                .toList();
    }
}

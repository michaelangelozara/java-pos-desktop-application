package org.POS.backend.quotation;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.quoted_item.UpdateQuotedItemRequestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class QuotationMapper {

    private CodeGeneratorService codeGeneratorService;

    public QuotationMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Quotation toQuotation(AddQuotationRequestDto dto){
        Quotation quotation = new Quotation();
        quotation.setPurchaseTax(12);
        quotation.setTotalTax(dto.totalTax());
        quotation.setSubtotal(dto.netSubtotal());
        quotation.setSubtotalTax(dto.subtotalTax());
        quotation.setNetTotal(dto.netSubtotal());
        quotation.setDiscount(dto.discount());
        quotation.setTransportCost(dto.transportCost());
        quotation.setNote(dto.note());
        quotation.setStatus(dto.status());
        quotation.setCreatedDate(LocalDate.now());
        quotation.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.QUOTATION_PREFIX));

        return quotation;
    }

    public Quotation toUpdatedQuotation(Quotation quotation, UpdateQuotationRequestDto dto){
        quotation.setDiscount(dto.discount());
        quotation.setTransportCost(dto.transportCost());
        quotation.setNote(dto.note());
        quotation.setStatus(dto.status());
        BigDecimal netTotal = (dto.subtotalTax().add(dto.netSubtotal()).add(dto.transportCost()).add(dto.totalTax())).subtract(dto.discount()).setScale(2, RoundingMode.HALF_UP);
        quotation.setSubtotalTax(dto.subtotalTax());
        quotation.setTotalTax(dto.netSubtotal().multiply(BigDecimal.valueOf(0.12)));
        quotation.setSubtotal(dto.netSubtotal());
        quotation.setNetTotal(netTotal);
        return quotation;
    }
}

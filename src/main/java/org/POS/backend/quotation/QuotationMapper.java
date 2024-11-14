package org.POS.backend.quotation;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;

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
}

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
        quotation.setNote(dto.note());
        quotation.setCreatedDate(LocalDate.now());
        quotation.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.QUOTATION_PREFIX));

        return quotation;
    }
}

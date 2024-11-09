package org.POS.backend.sale;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;

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
        sale.setChequeNumber(dto.chequeNumber());
        sale.setPoReference(dto.poReference());
        sale.setPayment(dto.paymentTerm());
        sale.setDeliveryPlace(dto.deliveryPlace());
        sale.setNote(dto.note());
        sale.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.SALE_PREFIX));

        return sale;
    }
}

package org.POS.backend.purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.Person;
import org.POS.backend.person.PersonMapper;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.purchased_item.PurchaseItemMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseMapper {

    private CodeGeneratorService codeGeneratorService;

    private PersonMapper personMapper;

    private PurchaseItemMapper purchaseItemMapper;

    public PurchaseMapper() {
        this.codeGeneratorService = new CodeGeneratorService();
        this.personMapper = new PersonMapper();
        this.purchaseItemMapper = new PurchaseItemMapper();
    }

    public Purchase toPurchase(AddPurchaseRequestDto dto) {
        Purchase purchase = new Purchase();
        purchase.setPoReference(dto.purchaseOrderReference());
        purchase.setCreatedDate(LocalDate.now());

        purchase.setPaymentTerm(dto.paymentTerm());
        purchase.setPurchaseTax(12);
        purchase.setDiscount(dto.discount());
        purchase.setTransportCost(dto.transportCost());
        purchase.setAccount(dto.account());
        purchase.setChequeNumber(dto.chequeNumber());
        purchase.setReceiptNumber(dto.receiptNumber());
        purchase.setNote(dto.note());
        purchase.setPurchaseDate(dto.purchaseDate());
        purchase.setPurchaseOrderDate(dto.purchaseOrderDate());
        purchase.setStatus(dto.status());
        purchase.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PURCHASE_PREFIX));

        BigDecimal netTotal = dto.netSubtotal().add(dto.transportCost()).subtract(dto.discount()).setScale(2, RoundingMode.HALF_UP);
        purchase.setBalance(netTotal.subtract(dto.totalPaid()));
        purchase.setTotalPaid(dto.totalPaid());
        purchase.setSubtotalTax(dto.subtotalTax());
        purchase.setTotalTax((dto.netSubtotal().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12))).setScale(2, RoundingMode.HALF_UP));
        purchase.setSubtotal(dto.netSubtotal());
        purchase.setNetTotal(netTotal);

        return purchase;
    }

    public Purchase toUpdatedPurchase(Purchase purchase, UpdatePurchaseRequestDto dto) {
        purchase.setPoReference(dto.purchaseOrderReference());
        purchase.setPaymentTerm(dto.paymentTerm());
        purchase.setPurchaseTax(12);
        purchase.setDiscount(dto.discount());
        purchase.setTransportCost(dto.transportCost());
        purchase.setAccount(dto.account());
        purchase.setChequeNumber(dto.chequeNumber());
        purchase.setReceiptNumber(dto.receiptNumber());
        purchase.setNote(dto.note());
        purchase.setStatus(dto.status());

        BigDecimal netTotal = (dto.subtotalTax().add(dto.netSubtotal()).add(dto.transportCost()).add(dto.totalTax())).subtract(dto.discount()).setScale(2, RoundingMode.HALF_UP);
        purchase.setBalance(netTotal.subtract(dto.totalPaid()));
        purchase.setTotalPaid(dto.totalPaid());
        purchase.setSubtotalTax(dto.subtotalTax());
        purchase.setTotalTax(dto.netSubtotal().multiply(BigDecimal.valueOf(0.12)));
        purchase.setSubtotal(dto.netSubtotal());
        purchase.setNetTotal(netTotal);
        return purchase;
    }

    private BigDecimal computeTotalTax(
            int productQuantity,
            BigDecimal productPurchasePrice,
            int purchaseTax
    ) {
        double vatTax = (double) purchaseTax / 100;
        BigDecimal totalPurchasePrice = BigDecimal
                .valueOf(Long.parseLong(String.valueOf(productQuantity)))
                .multiply(productPurchasePrice);
        return totalPurchasePrice
                .multiply(BigDecimal.valueOf(vatTax));
    }

    private BigDecimal computeNetTotal(
            int productQuantity,
            BigDecimal productPurchasePrice,
            int purchaseTax,
            BigDecimal discount,
            BigDecimal transportCost

    ) {
        BigDecimal totalPurchasePrice = BigDecimal
                .valueOf(Long.parseLong(String.valueOf(productQuantity)))
                .multiply(productPurchasePrice);
        BigDecimal totalTax = computeTotalTax(productQuantity, productPurchasePrice, purchaseTax);
        return (totalPurchasePrice.add(totalTax).add(transportCost)).subtract(discount);
    }

    private BigDecimal computeProductSubtotal(
            int productQuantity,
            BigDecimal productPurchasePrice
    ) {
        return productPurchasePrice.multiply(BigDecimal.valueOf(Long.parseLong(String.valueOf(productQuantity))));
    }

    public PurchaseResponseDto toPurchaseResponseDto(Purchase purchase) {
        List<PurchaseItem> productList = new ArrayList<>(purchase.getPurchaseItems());
        return new PurchaseResponseDto(
                purchase.getId(),
                purchase.getCode(),
                purchase.getCreatedDate(),
                this.personMapper.personResponseDto(purchase.getPerson()),
                purchase.getSubtotal(),
                purchase.getTransportCost(),
                purchase.getDiscount(),
                purchase.getNetTotal(),
                purchase.getTotalPaid(),
                purchase.getBalance(),
                purchase.getStatus(),
                this.purchaseItemMapper.toPurchaseItemResponseDtoList(productList),
                purchase.getSubtotalTax(),
                purchase.getNetTotal(),
                purchase.getPoReference(),
                purchase.getPaymentTerm(),
                purchase.getPurchaseDate(),
                purchase.getPurchaseOrderDate(),
                purchase.getTotalTax(),
                purchase.getAccount(),
                purchase.getChequeNumber(),
                purchase.getReceiptNumber(),
                purchase.getNote(),
                purchase.getBalance()
        );
    }

    public List<PurchaseResponseDto> toPurchaseResponseDtoList(List<Purchase> purchases) {
        return purchases
                .stream()
                .map(this::toPurchaseResponseDto)
                .toList();
    }
}


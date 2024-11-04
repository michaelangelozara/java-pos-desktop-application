package org.POS.backend.purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.Person;
import org.POS.backend.person.PersonMapper;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductMapper;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.purchased_product.PurchaseProductMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseMapper {

    private CodeGeneratorService codeGeneratorService;

    private PersonMapper personMapper;

    private PurchaseProductMapper purchaseProductMapper;

    public PurchaseMapper() {
        this.codeGeneratorService = new CodeGeneratorService();
        this.personMapper = new PersonMapper();
        this.purchaseProductMapper = new PurchaseProductMapper();
    }

    public Purchase toPurchase(AddPurchaseRequestDto dto, Person supplier) {
        BigDecimal netTotal = computeNetTotal(dto.productQuantity(), dto.productPurchasePrice(), dto.purchaseTax(), dto.discount(), dto.transportCost());

        Purchase purchase = new Purchase();
        purchase.setPerson(supplier);
        purchase.setPoReference(dto.purchaseOrderReference());
        purchase.setPaymentTerm(dto.paymentTerm());
        purchase.setPurchaseTax(dto.purchaseTax());
        purchase.setNote(dto.note());
        purchase.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PURCHASE_PREFIX));
        purchase.setCreatedDate(LocalDate.now());
        purchase.setPurchaseDate(dto.purchaseDate());
        purchase.setPurchaseOrderDate(dto.purchaseOrderDate());
        purchase.setStatus(dto.status());
        purchase.setDiscount(dto.discount());
        purchase.setTransportCost(dto.transportCost());
        purchase.setTotalTax(computeTotalTax(dto.productQuantity(), dto.productPurchasePrice(), dto.purchaseTax()));
        purchase.setNetTotal(netTotal);
        purchase.setTotalPaid(BigDecimal.ZERO);
        purchase.setTotalDue(netTotal);
        purchase.setSubtotal(computeProductSubtotal(dto.productQuantity(), dto.productPurchasePrice()));
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

    public Purchase toUpdatedPurchase(Purchase purchase, UpdatePurchaseRequestDto dto) {


        return purchase;
    }

    public PurchaseResponseDto toPurchaseResponseDto(Purchase purchase) {
        List<PurchaseProduct> productList = new ArrayList<>(purchase.getPurchaseProducts());
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
                purchase.getTotalDue(),
                purchase.getStatus(),
                this.purchaseProductMapper.toPurchaseResponseDtoList(productList)
        );
    }

    public List<PurchaseResponseDto> toPurchaseResponseDtoList(List<Purchase> purchases) {
        return purchases
                .stream()
                .map(this::toPurchaseResponseDto)
                .toList();
    }
}


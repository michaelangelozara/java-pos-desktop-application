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

    public PurchaseResponseDto toPurchaseResponseDto(Purchase purchase) {
        return new PurchaseResponseDto(
                purchase.getId(),
                purchase.getCode(),
                purchase.getCreatedDate(),
                purchase.getUser(),
                purchase.getPerson(),
                purchase.getPurchaseItems(),
                purchase.getNote()
        );
    }

    public List<PurchaseResponseDto> toPurchaseResponseDtoList(List<Purchase> purchases) {
        return purchases
                .stream()
                .map(this::toPurchaseResponseDto)
                .toList();
    }
}


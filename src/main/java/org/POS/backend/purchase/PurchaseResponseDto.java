package org.POS.backend.purchase;

import org.POS.backend.person.Person;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.user.User;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponseDto(
        int id,
        String code,
        LocalDate date,
        User user,
        Person person,
        List<PurchaseItem> purchaseItems,
        String note
) {
}

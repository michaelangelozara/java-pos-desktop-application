package org.POS.backend.stock;

import org.POS.backend.person.PersonResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockResponseDto(
        LocalDate date,
        int stockInOrOut,
        BigDecimal price,
        String code,
        PersonResponseDto person
) {
}

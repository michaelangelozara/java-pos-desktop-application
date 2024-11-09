package sale_service;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.sale.AddSaleRequestDto;
import org.POS.backend.sale.SaleService;
import org.POS.backend.sale_item.AddSaleItemRequestDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SaleTest {

    @Test
    void add(){
        SaleService saleService = new SaleService();
        CurrentUser.id = 1;
        AddSaleRequestDto dto = new AddSaleRequestDto(
                1,
                "Fixed",
                BigDecimal.ZERO,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(112),
                BigDecimal.valueOf(10000),
                "Receipt Number",
                BigDecimal.valueOf(10000),
                LocalDate.now(),
                "ChequeNumber",
                "Po reference",
                "Installment",
                "Pimbalayan",
                "This is the first note"
        );

        Set<AddSaleItemRequestDto> saleItemRequestDtos = new HashSet<>();
        AddSaleItemRequestDto dto1 = new AddSaleItemRequestDto(
                1,
                BigDecimal.valueOf(212),
                3,
                BigDecimal.valueOf(2000)
        );
        AddSaleItemRequestDto dto2 = new AddSaleItemRequestDto(
                2,
                BigDecimal.valueOf(212),
                3,
                BigDecimal.valueOf(2000)
        );
        AddSaleItemRequestDto dto3 = new AddSaleItemRequestDto(
                3,
                BigDecimal.valueOf(212),
                3,
                BigDecimal.valueOf(2000)
        );
        saleItemRequestDtos.add(dto1);
        saleItemRequestDtos.add(dto2);
        saleItemRequestDtos.add(dto3);

        System.out.println(saleService.add(dto, saleItemRequestDtos));
    }
}

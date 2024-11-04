package purchase;

import org.POS.backend.purchase.AddPurchaseRequestDto;
import org.POS.backend.purchase.PurchaseService;
import org.POS.backend.purchase.PurchaseStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PurchaseTest {

    @Test
    void add() {
        PurchaseService purchaseService = new PurchaseService();

        Set<Integer> productIds = new HashSet<>();
        productIds.add(1);
        productIds.add(2);

        AddPurchaseRequestDto dto = new AddPurchaseRequestDto(
                1,
                productIds,
                "567113388ds762",
                "Cash final",
                20,
                "This purchase contains a list of products",
                LocalDate.now(),
                LocalDate.of(2024, 10, 22),
                PurchaseStatus.ACTIVE,
                BigDecimal.valueOf(9000),
                BigDecimal.valueOf(70000),
                25,
                BigDecimal.valueOf(95890)
        );

        purchaseService.add(dto);
    }

    @Test
    void getAllValidPurchases() {
        PurchaseService purchaseService = new PurchaseService();
        var purchaseList = purchaseService.getAllValidPurchases();
        for (int i = 0; i < purchaseList.size(); i++) {
            for (int j = 0; j < purchaseList.get(i).products().size(); j++) {
                System.out.println(j+1);
                System.out.println(purchaseList.get(i).supplier().name() + " ||| " + purchaseList.get(i).products().get(j).name());
            }
        }
    }
}

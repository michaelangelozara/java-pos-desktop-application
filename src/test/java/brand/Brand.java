package brand;

import org.POS.backend.brand.AddBrandRequestDto;
import org.POS.backend.brand.BrandService;
import org.POS.backend.brand.BrandStatus;
import org.POS.backend.brand.UpdateBrandRequestDto;
import org.junit.jupiter.api.Test;

public class Brand {

    @Test
    void add(){
        BrandService brandService = new BrandService();
        AddBrandRequestDto dto = new AddBrandRequestDto(
                "Toyota VB89-S",
                BrandStatus.ACTIVE,
                2
        );
        brandService.add(dto);
    }

    @Test
    void update(){
        BrandService brandService = new BrandService();
        UpdateBrandRequestDto dto = new UpdateBrandRequestDto(
                1,
                "Iphone 15 Pro Max",
                BrandStatus.ACTIVE,
                1
        );
        brandService.update(dto);
    }
}

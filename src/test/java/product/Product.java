package product;

import org.POS.backend.product.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class Product {

    @Test
    void add(){
        ProductService productService = new ProductService();

//        AddProductRequestDto dto = new AddProductRequestDto(
//                "Iphone 19",
//                "JSA-10",
//                1,
//                ProductUnit.PIECE,
//                20,
//                ProductTaxType.INCLUSIVE,
//                BigDecimal.valueOf(95990),
//                BigDecimal.valueOf(100),
//                20,
//                "No note yet",
//                10,
//                ProductStatus.ACTIVE,
//                "GVHBNKJKsLML<",
//                0
//        );
//        productService.add(dto);
    }

    @Test
    void update(){
        ProductService productService = new ProductService();

//        UpdateProductRequestDto dto = new UpdateProductRequestDto(
//                1,
//                "Iphone 15",
//                "JSA-15",
//                1,
//                ProductUnit.PIECE,
//                0,
//                ProductTaxType.INCLUSIVE,
//                BigDecimal.valueOf(57990),
//                0,
//                "No note yet",
//                10,
//                ProductStatus.ACTIVE,
//                "GVHBNKJKLML<",
//                BigDecimal.valueOf(0),
//                0
//        );
//        productService.update(dto);
    }

    @Test
    void delete(){
        ProductService productService = new ProductService();
        productService.delete(2);
    }

    @Test
    void getAllValidProduct(){
        ProductService productService = new ProductService();
        productService.getAllValidProductsWithLimit().forEach(p -> {
            System.out.println(p.name());
        });
    }

    @Test
    void getValidProduct(){
        ProductService productService = new ProductService();
        System.out.println(productService.getValidProductById(2));
    }
}

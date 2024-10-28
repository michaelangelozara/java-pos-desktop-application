package product;

import org.POS.backend.product.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class Product {

    @Test
    void add(){
        ProductService productService = new ProductService();

        AddProductRequestDto dto = new AddProductRequestDto(
                "Iphone 10",
                "JSA-10",
                "CODE",
                1,
                ProductUnit.PIECE,
                0,
                ProductTaxType.INCLUSIVE,
                BigDecimal.valueOf(25990),
                0,
                "No note yet",
                10,
                ProductStatus.ACTIVE,
                "GVHBNKJKsLML<"
        );
        productService.add(dto);
    }

    @Test
    void update(){
        ProductService productService = new ProductService();

        UpdateProductRequestDto dto = new UpdateProductRequestDto(
                1,
                "Iphone 15",
                "JSA-15",
                "CODE",
                1,
                ProductUnit.PIECE,
                0,
                ProductTaxType.INCLUSIVE,
                BigDecimal.valueOf(57990),
                0,
                "No note yet",
                10,
                ProductStatus.ACTIVE,
                "GVHBNKJKLML<"
        );
        productService.update(dto);
    }

    @Test
    void delete(){
        ProductService productService = new ProductService();
        productService.delete(2);
    }

    @Test
    void getAllValidProduct(){
        ProductService productService = new ProductService();
        productService.getAllValidProducts().forEach(p -> {
            System.out.println(p.name());
        });
    }

    @Test
    void getValidProduct(){
        ProductService productService = new ProductService();
        System.out.println(productService.getValidProductById(2));
    }
}

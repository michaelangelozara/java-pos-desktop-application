package computer;

import org.POS.backend.product.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;

public class Computer {

    @Test
    void compute(){

        String plainText = "password";
        String encryptedText = BCrypt.hashpw(plainText, BCrypt.gensalt());

        System.out.println("Hashed password: " + encryptedText);

        // Verify the password
        boolean matches = BCrypt.checkpw(plainText, "$2a$10$ylm0OBvO4xS6GqXa3ITByubcE0uGSV9o4PCT/gg51Y/VqGOKIMdSG");
        System.out.println("Password matches: " + matches);
    }

    @Test
    void computeVatWithExclusive(){
        ProductMapper productMapper = new ProductMapper();
//        System.out.println(productMapper.getSellingPrice(BigDecimal.valueOf(1000), ProductTaxType.EXCLUSIVE));
    }

    @Test
    void computeVatWithInclusive(){
        double product = 1000;
        double vat = (product/1.12)*((double) 12 /100);
        System.out.println(vat);
    }

    @Test
    void computeExclusiveTax(){
        double product = 1000;
        double exclusiveTax = (product/1.12);
        System.out.println(exclusiveTax);
    }
}

package computer;

import org.POS.backend.product.ProductMapper;
import org.POS.backend.product.ProductTaxType;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;

public class Computer {

    @Test
    void compute(){
        double vat = 0.20;
        double discount = 0.20;
        double regularPrice = 100D;
        double discountedValue = (((regularPrice * vat) + regularPrice) * discount);
        double sellingPrice = ((regularPrice * vat) + regularPrice) - discountedValue;
        BigDecimal one = BigDecimal.ONE;

        String plainText = "password";
        String encryptedText = BCrypt.hashpw(plainText, BCrypt.gensalt());

        System.out.println("Hashed password: " + encryptedText);

        // Verify the password
        boolean matches = BCrypt.checkpw(plainText, encryptedText);
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

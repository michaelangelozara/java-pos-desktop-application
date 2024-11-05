package computer;

import org.junit.jupiter.api.Test;

public class Computer {

    @Test
    void compute(){
        double vat = 0.20;
        double discount = 0.20;
        double regularPrice = 100D;
        double discountedValue = (((regularPrice * vat) + regularPrice) * discount);
        double sellingPrice = ((regularPrice * vat) + regularPrice) - discountedValue;

    }
}

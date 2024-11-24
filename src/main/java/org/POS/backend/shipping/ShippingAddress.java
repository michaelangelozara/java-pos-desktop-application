package org.POS.backend.shipping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale.Sale;

@Entity
@Table(name = "shipping_addresses")
@Getter
@Setter
@NoArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "shipping_address")
    private String shippingAddress;

    private String city;

    private String barangay;

    private String landmark;

    private String note;

    @OneToOne(mappedBy = "shippingAddress")
    private Sale sale;
}

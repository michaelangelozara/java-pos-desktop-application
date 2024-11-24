package org.POS.backend.sale;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.additional_fee.AdditionalFee;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.order.Order;
import org.POS.backend.payment.Payment;
import org.POS.backend.person.Person;
import org.POS.backend.sale_product.SaleProduct;
import org.POS.backend.shipping.ShippingAddress;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_tax", precision = 10, scale = 2)
    private BigDecimal totalTax;

    @Column(name = "net_total", precision = 10, scale = 2)
    private BigDecimal netTotal;

    private LocalDate date;

    @Column(name = "sale_number", columnDefinition = "VARCHAR(50) NOT NULL")
    private String saleNumber;

    @Column(name = "vat_sale", precision = 10, scale = 2)
    private BigDecimal vatSale;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sale", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SaleProduct> saleProducts = new HashSet<>();

    @OneToOne(mappedBy = "sale", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Order order;

    @OneToOne(mappedBy = "sale", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Invoice invoice;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "sale", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<AdditionalFee> additionalFees = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    public void addSaleProduct(SaleProduct saleProduct) {
        saleProducts.add(saleProduct);
        saleProduct.setSale(this);
    }

    public void addAdditionalFee(AdditionalFee additionalFee) {
        additionalFees.add(additionalFee);
        additionalFee.setSale(this);
    }
}
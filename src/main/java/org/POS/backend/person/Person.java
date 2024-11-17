package org.POS.backend.person;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.order.Order;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.quotation.Quotation;
import org.POS.backend.sale.Sale;
import org.POS.backend.stock.Stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tax_registration_number")
    private String taxRegistrationNumber;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    private String address;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Enumerated(EnumType.STRING)
    private PersonStatus status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quotation> quotations = new ArrayList<>();

    public void addInvoice(Invoice invoice){
        invoices.add(invoice);
        invoice.setPerson(this);
    }

    public void addQuotation(Quotation quotation){
        quotations.add(quotation);
        quotation.setPerson(this);
    }

    public void addOrder(Order order){
        orders.add(order);
        order.setPerson(this);
    }

    public void addSale(Sale sale){
        sales.add(sale);
        sale.setPerson(this);
    }

    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
        purchase.setPerson(this);
    }
}

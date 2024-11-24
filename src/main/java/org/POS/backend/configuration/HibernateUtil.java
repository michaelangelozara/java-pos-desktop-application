package org.POS.backend.configuration;

import org.POS.backend.additional_fee.AdditionalFee;
import org.POS.backend.code_generator.CodeGenerator;
import org.POS.backend.expense.Expense;
import org.POS.backend.expense_category.ExpenseCategory;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;
import org.POS.backend.inventory_adjustment.InventoryAdjustment;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.open_cash.OpenCash;
import org.POS.backend.order.Order;
import org.POS.backend.payment.POLog;
import org.POS.backend.payment.Payment;
import org.POS.backend.person.Person;
import org.POS.backend.product.Product;
import org.POS.backend.product_attribute.ProductAttribute;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.quotation.Quotation;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.return_product.ReturnItem;
import org.POS.backend.return_product.ReturnOrder;
import org.POS.backend.sale.Sale;
import org.POS.backend.sale_product.SaleProduct;
import org.POS.backend.shipping.ShippingAddress;
import org.POS.backend.stock.Stock;
import org.POS.backend.user.User;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user.UserRole;
import org.POS.backend.user.UserStatus;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create a Configuration instance
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml"); // Load config from hibernate.cfg.xml

                // Add annotated entity classes
                configuration.addAnnotatedClass(Payment.class);
                configuration.addAnnotatedClass(ProductCategory.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Expense.class);
                configuration.addAnnotatedClass(CodeGenerator.class);
                configuration.addAnnotatedClass(ExpenseCategory.class);
                configuration.addAnnotatedClass(ExpenseSubcategory.class);
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(PurchaseItem.class);
                configuration.addAnnotatedClass(ReturnOrder.class);
                configuration.addAnnotatedClass(Stock.class);
                configuration.addAnnotatedClass(Sale.class);
                configuration.addAnnotatedClass(SaleProduct.class);
                configuration.addAnnotatedClass(OpenCash.class);
                configuration.addAnnotatedClass(InventoryAdjustment.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Invoice.class);
                configuration.addAnnotatedClass(Quotation.class);
                configuration.addAnnotatedClass(QuotedItem.class);
                configuration.addAnnotatedClass(UserLog.class);
                configuration.addAnnotatedClass(ShippingAddress.class);
                configuration.addAnnotatedClass(AdditionalFee.class);
                configuration.addAnnotatedClass(ProductAttribute.class);
                configuration.addAnnotatedClass(ProductVariation.class);
                configuration.addAnnotatedClass(ReturnItem.class);
                configuration.addAnnotatedClass(POLog.class);

                // Build the SessionFactory
                sessionFactory = configuration.buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build()
                );

                Session session = sessionFactory.openSession();
                Long userCount = session.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                        .getSingleResult();

                if (userCount == 0) {
                    String salt = BCrypt.gensalt(10);
                    String plainText = "password";
                    String encryptedText = BCrypt.hashpw(plainText, salt);
                    UserDAO userDAO = new UserDAO();

                    User superAdmin = new User();
                    superAdmin.setName("Administrator");
                    superAdmin.setRole(UserRole.SUPER_ADMIN);
                    superAdmin.setUsername("username");
                    superAdmin.setPassword(encryptedText);
                    superAdmin.setEmail("michaelangelobuccatzara@gmail.com");
                    superAdmin.setContactNumber("09090909090");
                    superAdmin.setStatus(UserStatus.ACTIVE);
                    superAdmin.setCreatedAt(LocalDate.now());
                    userDAO.add(superAdmin);
                }
                System.out.println("Database connected");
            } catch (Throwable ex) {
                // Use a logging framework instead of printStackTrace
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError("Error creating Hibernate SessionFactory: " + ex.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

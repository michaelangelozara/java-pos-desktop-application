package org.POS.backend.configuration;

import org.POS.backend.brand.Brand;
import org.POS.backend.code_generator.CodeGenerator;
import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.department.Department;
import org.POS.backend.expense.Expense;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.expense_category.ExpenseCategory;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;
import org.POS.backend.person.Person;
import org.POS.backend.product.Product;
import org.POS.backend.product_subcategory.ProductSubcategory;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.sale.Sale;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.stock.Stock;
import org.POS.backend.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create a Configuration instance
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml"); // Load config from hibernate.cfg.xml

                // Add annotated entity classes
                configuration.addAnnotatedClass(Brand.class);
                configuration.addAnnotatedClass(ProductCategory.class);
                configuration.addAnnotatedClass(Department.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(ProductSubcategory.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Expense.class);
                configuration.addAnnotatedClass(CodeGenerator.class);
                configuration.addAnnotatedClass(ExpenseCategory.class);
                configuration.addAnnotatedClass(ExpenseSubcategory.class);
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(PurchaseProduct.class);
                configuration.addAnnotatedClass(ReturnProduct.class);
                configuration.addAnnotatedClass(Stock.class);
                configuration.addAnnotatedClass(Sale.class);
                configuration.addAnnotatedClass(SaleItem.class);

                // Build the SessionFactory
                sessionFactory = configuration.buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build()
                );
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

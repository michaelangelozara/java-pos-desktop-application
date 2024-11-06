
package org.POS.frontend.src.raven.application.form.other;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.POS.backend.brand.BrandService;
import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.product.*;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.backend.string_checker.StringChecker;
import org.POS.frontend.src.raven.cell.TableActionCellRender;

import javax.swing.table.DefaultTableModel;

import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;

public class ProductList extends javax.swing.JPanel {

    public ProductList() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int productId = (Integer) model.getValueAt(row, 1);

                ProductService productService = new ProductService();
                var product = productService.getValidProductById(productId);

                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout()); // Use GridBagLayout for a more flexible layout
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10); // Add padding
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;

                // Create a bold and larger font for the labels
                Font boldFont = new Font("Arial", Font.BOLD, 14);
                Font regularFont = new Font("Arial", Font.PLAIN, 14);

                // Item Name *
                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel itemNameLabel = new JLabel("Item Name *");
                itemNameLabel.setFont(boldFont);
                panel.add(itemNameLabel, gbc);

                gbc.gridx = 1;
                JTextField itemNameField = new JTextField(15);
                itemNameField.setFont(regularFont);
                itemNameField.setText(product.name()); // Prepopulate with existing value
                panel.add(itemNameField, gbc);

                // Item Model
                gbc.gridx = 2;
                JLabel itemModelLabel = new JLabel("Item Model");
                itemModelLabel.setFont(boldFont);
                panel.add(itemModelLabel, gbc);

                gbc.gridx = 3;
                JTextField itemModelField = new JTextField(15);
                itemModelField.setFont(regularFont);
                itemModelField.setText(product.model()); // Prepopulate with existing value
                panel.add(itemModelField, gbc);

                // Item Code *
                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel itemStockLabel = new JLabel("Stock *");
                itemStockLabel.setFont(boldFont);
                panel.add(itemStockLabel, gbc);

                gbc.gridx = 1;
                JTextField itemStockField = new JTextField(15);
                itemStockField.setFont(regularFont);
                itemStockField.setText(String.valueOf(product.stock())); // Prepopulate with existing value
                panel.add(itemStockField, gbc);

                // Category
                gbc.gridx = 2;
                JLabel categoryLabel = new JLabel("Subcategory");
                categoryLabel.setFont(boldFont);
                panel.add(categoryLabel, gbc);

                gbc.gridx = 3;

                Vector<String> productSubcategoryNames = new Vector<>();
                productSubcategoryNames.add("Select Subcategory");
                Map<Integer, Integer> productSubcategoryMap = new HashMap<>();
                ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
                var productSubcategories = productSubcategoryService.getAllValidSubcategories();
                for (int i = 0; i < productSubcategories.size(); i++) {
                    productSubcategoryNames.add(productSubcategories.get(i).name());
                    productSubcategoryMap.put(i + 1, productSubcategories.get(i).id());
                }

                JComboBox<String> productSubcategoryCombo = new JComboBox<>(productSubcategoryNames);
                productSubcategoryCombo.setFont(regularFont);
                panel.add(productSubcategoryCombo, gbc);

                for (int i = 0; i < productSubcategories.size(); i++) {
                    if (productSubcategories.get(i).id() == product.brand().productSubcategory().id()) {
                        productSubcategoryCombo.removeAllItems();

                        productSubcategoryNames.add("Select Subcategory");
                        productSubcategoryCombo.addItem(productSubcategories.get(i).name());
                        productSubcategoryCombo.setSelectedItem(productSubcategories.get(i).name());
                        productSubcategoryMap.put(i + 1, productSubcategories.get(i).id());
                        break;
                    }
                }

                // Brand
                BrandService brandService = new BrandService();
                Map<Integer, Integer> brandMap = new HashMap<>();
                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel brandLabel = new JLabel("Brand");
                brandLabel.setFont(boldFont);
                panel.add(brandLabel, gbc);

                gbc.gridx = 1;
                var tempBrands = brandService.getAllBrandByProductSubcategoryId(product.brand().productSubcategory().id());
                JComboBox<String> brandCombo = new JComboBox<>();
                brandCombo.setFont(regularFont);
                panel.add(brandCombo, gbc);

                for (int i = 0; i < tempBrands.size(); i++) {
                    if (tempBrands.get(i).id() == product.brand().id()) {
                        brandCombo.addItem(tempBrands.get(i).name());
                        brandCombo.setSelectedItem(tempBrands.get(i).name());
                        brandMap.put(i, tempBrands.get(i).id());
                        break;
                    }
                }

                productSubcategoryCombo.addActionListener(e -> {
                    brandCombo.removeAllItems();

                    brandCombo.setSelectedItem("Select Brand");
                    int productSubcategoryIndex = productSubcategoryCombo.getSelectedIndex();
                    int productSubcategoryId = productSubcategoryMap.get(productSubcategoryIndex);
                    var brands = brandService.getAllBrandByProductSubcategoryId(productSubcategoryId);
                    for (int i = 0; i < brands.size(); i++) {
                        brandCombo.addItem(brands.get(i).name());
                        brandMap.put(i + 1, brands.get(i).id());
                    }

                });

                // Unit
                gbc.gridx = 2;
                JLabel unitLabel = new JLabel("Unit");
                unitLabel.setFont(boldFont);
                panel.add(unitLabel, gbc);

                gbc.gridx = 3;
                JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
                unitCombo.setFont(regularFont);
                unitCombo.setSelectedItem(product.unit().equals(ProductUnit.PIECE) ? "Per Piece" : "Per Dozen"); // Preselect existing unit
                panel.add(unitCombo, gbc);

                // Product Tax *
                gbc.gridx = 0;
                gbc.gridy = 3;
                JLabel productTaxLabel = new JLabel("Product Tax *");
                productTaxLabel.setFont(boldFont);
                panel.add(productTaxLabel, gbc);

                gbc.gridx = 1;
                Map<String, Integer> productTaxTypeMap = new HashMap<>();
                productTaxTypeMap.put("VAT@12%", 12);

                Map<Integer, String> productTempMap = new HashMap<>();
                productTempMap.put(12, "VAT@12%");
                JComboBox<String> productTaxCombo = new JComboBox<>(new String[]{"Select Product Type", "VAT@12%"});
                productTaxCombo.setFont(regularFont);
                productTaxCombo.setSelectedItem(productTempMap.get(product.tax())); // Preselect existing product productTax
                panel.add(productTaxCombo, gbc);

                // Tax Type
                gbc.gridx = 2;
                JLabel taxTypeLabel = new JLabel("Tax Type");
                taxTypeLabel.setFont(boldFont);
                panel.add(taxTypeLabel, gbc);

                gbc.gridx = 3;
                JComboBox<String> taxTypeCombo = new JComboBox<>(new String[]{"Select Tax Type", "Exclusive", "Inclusive"});
                taxTypeCombo.setFont(regularFont);
                taxTypeCombo.setSelectedItem(product.taxType().equals(ProductTaxType.EXCLUSIVE) ? "Exclusive" : "Inclusive"); // Preselect existing productTax type
                panel.add(taxTypeCombo, gbc);

                // Purchase Price
                gbc.gridx = 0;
                gbc.gridy = 4;
                JLabel purchasePriceLabel = new JLabel("Purchase Price");
                purchasePriceLabel.setFont(boldFont);
                panel.add(purchasePriceLabel, gbc);

                gbc.gridx = 1;
                JTextField purchasePriceField = new JTextField(15);
                purchasePriceField.setFont(regularFont);
                purchasePriceField.setText(String.valueOf(product.purchasePrice())); // Prepopulate with existing value
                panel.add(purchasePriceField, gbc);

                // Regular Price *
                gbc.gridx = 2;
                JLabel regularPriceLabel = new JLabel("Regular Price *");
                regularPriceLabel.setFont(boldFont);
                panel.add(regularPriceLabel, gbc);

                gbc.gridx = 3;
                JTextField regularPriceField = new JTextField(15);
                regularPriceField.setFont(regularFont);
                regularPriceField.setText(String.valueOf(product.regularPrice())); // Prepopulate with existing value
                panel.add(regularPriceField, gbc);

                // Discount
                gbc.gridx = 0;
                gbc.gridy = 5;
                JLabel discountLabel = new JLabel("Discount");
                discountLabel.setFont(boldFont);
                panel.add(discountLabel, gbc);

                gbc.gridx = 1;
                JTextField discountField = new JTextField(15);
                discountField.setFont(regularFont);
                discountField.setText(String.valueOf(product.discount())); // Prepopulate with existing value
                panel.add(discountField, gbc);

                // Selling Price
                gbc.gridx = 2;
                JLabel sellingPriceLabel = new JLabel("Selling Price");
                sellingPriceLabel.setFont(boldFont);
                panel.add(sellingPriceLabel, gbc);

                gbc.gridx = 3;
                JTextField sellingPriceField = new JTextField(15);
                sellingPriceField.setFont(regularFont);
                sellingPriceField.setText(String.valueOf(product.sellingPrice())); // Prepopulate with existing value
                panel.add(sellingPriceField, gbc);

                // Note
                gbc.gridx = 0;
                gbc.gridy = 6;
                JLabel noteLabel = new JLabel("Note");
                noteLabel.setFont(boldFont);
                panel.add(noteLabel, gbc);

                gbc.gridx = 1;
                gbc.gridwidth = 3;
                JTextArea noteArea = new JTextArea(3, 15);
                noteArea.setFont(regularFont);
                noteArea.setText(product.note()); // Prepopulate with existing value
                JScrollPane noteScrollPane = new JScrollPane(noteArea);
                panel.add(noteScrollPane, gbc);
                gbc.gridwidth = 1;

                // Alert Quantity
                gbc.gridx = 0;
                gbc.gridy = 7;
                JLabel alertQuantityLabel = new JLabel("Alert Quantity");
                alertQuantityLabel.setFont(boldFont);
                panel.add(alertQuantityLabel, gbc);

                gbc.gridx = 1;
                JTextField alertQuantityField = new JTextField(15);
                alertQuantityField.setFont(regularFont);
                alertQuantityField.setText(String.valueOf(product.alertQuantity())); // Prepopulate with existing value
                panel.add(alertQuantityField, gbc);

                // Status
                gbc.gridx = 2;
                JLabel statusLabel = new JLabel("Status");
                statusLabel.setFont(boldFont);
                panel.add(statusLabel, gbc);

                gbc.gridx = 3;
                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select status", "Active", "Inactive"});
                statusCombo.setFont(regularFont);
                statusCombo.setSelectedItem(product.status().equals(ProductStatus.ACTIVE) ? "Active" : "Inactive"); // Preselect existing status
                panel.add(statusCombo, gbc);

                // Image - Modify to use JFileChooser for file upload
                gbc.gridx = 0;
                gbc.gridy = 8;
                JLabel imageLabel = new JLabel("Image");
                imageLabel.setFont(boldFont);
                panel.add(imageLabel, gbc);

                gbc.gridx = 1;
                JButton imageButton = new JButton("Choose File");
                imageButton.setFont(regularFont);

                // Add action listener for the imageButton to open file explorer
                Base64Converter base64Converter = new Base64Converter();
                imageButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        try {
                            base64Converter.setConvertFileToBase64(selectedFile);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                panel.add(imageButton, gbc);

                // Finalize and show the dialog
                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    // Handle saving the edited values here
                    String updatedItemName = itemNameField.getText();
                    String updatedItemModel = itemModelField.getText();
                    String updatedItemStock = itemStockField.getText();
                    String updatedUnit = (String) unitCombo.getSelectedItem();
                    String updatedProductTax = (String) productTaxCombo.getSelectedItem();
                    String updatedTaxType = (String) taxTypeCombo.getSelectedItem();
                    String updatedPurchasePrice = purchasePriceField.getText();
                    String updatedRegularPrice = regularPriceField.getText();
                    String updatedDiscount = discountField.getText();
                    String updatedNote = noteArea.getText();
                    String updatedAlertQuantity = alertQuantityField.getText();
                    String updatedStatus = (String) statusCombo.getSelectedItem();
                    String updatedImage = base64Converter.getBase64();
                    int brandIndex = brandCombo.getSelectedIndex();
                    int brandId = brandMap.get(brandIndex);

                    if (!StringChecker.isNumericOnly(updatedItemStock)) {
                        JOptionPane.showMessageDialog(null, "Any character in the stock is not allowed",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (!StringChecker.isNumericOnly(updatedDiscount)) {
                        JOptionPane.showMessageDialog(null, "Any character in the discount is not allowed",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (!StringChecker.isNumericOnly(updatedAlertQuantity)) {
                        JOptionPane.showMessageDialog(null, "Any character in the alert quantity is not allowed",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (!StringChecker.isNumericOnly(updatedPurchasePrice)) {
                        JOptionPane.showMessageDialog(null, "Any character in the purchase price is not allowed",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } else if (!StringChecker.isNumericOnly(updatedRegularPrice)) {
                        JOptionPane.showMessageDialog(null, "Any character in the regular price is not allowed",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        assert updatedUnit != null;
                        assert updatedTaxType != null;
                        assert updatedStatus != null;
                        UpdateProductRequestDto dto = new UpdateProductRequestDto(
                                product.id(),
                                updatedItemName,
                                updatedItemModel,
                                brandId,
                                updatedUnit.equals("Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN,
                                productTaxTypeMap.get(updatedProductTax),
                                updatedTaxType.equals("Exclusive") ? ProductTaxType.EXCLUSIVE : ProductTaxType.INCLUSIVE,
                                BigDecimal.valueOf(Double.parseDouble(updatedRegularPrice)),
                                Integer.parseInt(updatedDiscount),
                                updatedNote,
                                Integer.parseInt(updatedAlertQuantity),
                                updatedStatus.equals("Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE,
                                updatedImage != null ? updatedImage : product.image(),
                                BigDecimal.valueOf(Double.parseDouble(updatedPurchasePrice)),
                                Integer.parseInt(updatedItemStock)
                        );

                        productService.update(dto);

                        JOptionPane.showMessageDialog(null, "Product Updated Successfully",
                                "Updated", JOptionPane.INFORMATION_MESSAGE);
                        loadProducts();
                    }
                }
            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                // Confirm before deleting
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this Product?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int productId = (Integer) model.getValueAt(row, 1);
                    ProductService productService = new ProductService();
                    productService.delete(productId);
                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            }

            @Override

            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int productId = (Integer) model.getValueAt(row, 1);

                ProductService productService = new ProductService();
                var product = productService.getValidProductById(productId);

                // Placeholder for the image on the left side
                JLabel imageLabel = new JLabel();
                ImageIcon productIcon = new ImageIcon("src/raven/icon/png/logo_1.png");
                imageLabel.setIcon(productIcon);
                imageLabel.setHorizontalAlignment(JLabel.CENTER);

                JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 0, 0));  // 2 columns with no spacing

                // Create a border for each label and detail to give a table-like appearance
                Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
                Border padding = new EmptyBorder(10, 10, 10, 10); // Add padding around the labels

                JLabel label;
                JLabel detail;

                // Method to center text in the JLabel
                int horizontalAlignment = JLabel.CENTER;

                label = new JLabel("Item Name:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));  // Label font size 18
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.name(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));  // Details font size 16
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Code:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.code(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Item Model:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.model(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Category:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.brand().productSubcategory().name(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Brand:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.brand().name(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Unit:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.unit().equals(ProductUnit.PIECE) ? "Per Piece" : "Per Dozen", horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Product Tax:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.tax() + "%"), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Tax Amount:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel("I don't know", horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                // Swapped Selling Price and Purchase Price
                label = new JLabel("Purchase Price:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.purchasePrice()), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                // Add Regular Price under Purchase Price
                label = new JLabel("Regular Price:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.regularPrice()), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Selling Price:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.sellingPrice()), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Stock:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.stock() + "pc"), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Inventory Value:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.sellingPrice().multiply(BigDecimal.valueOf(product.stock()))), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Alert Quantity:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(String.valueOf(product.alertQuantity()), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                label = new JLabel("Note:", horizontalAlignment);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                label.setOpaque(true);
                detailsPanel.add(label);
                detail = new JLabel(product.note(), horizontalAlignment);
                detail.setFont(new Font("Arial", Font.PLAIN, 16));
                detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
                detail.setOpaque(true);
                detailsPanel.add(detail);

                // Main panel to combine image and details
                JPanel mainPanel = new JPanel(new BorderLayout(15, 0));  // 15px horizontal gap for more space
                mainPanel.add(imageLabel, BorderLayout.WEST);  // Image on the left
                mainPanel.add(detailsPanel, BorderLayout.CENTER);  // Details on the right

                // Show the panel inside a JOptionPane
                JOptionPane.showMessageDialog(null, mainPanel, "Product Details", JOptionPane.INFORMATION_MESSAGE);
            }


        };
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadProducts();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Product List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Products");

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("Search");

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "#", "ID", "Code", "Category", "Name", "Item Model", "Unit", "Selling Price", "Status", "Action"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                    false, true, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addContainerGap(1230, Short.MAX_VALUE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for a more flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Create a bold and larger font for the labels
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name *
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        JTextField itemNameField = new JTextField(15);
        itemNameField.setFont(regularFont);
        panel.add(itemNameField, gbc);

        // Item Model
        gbc.gridx = 2;
        JLabel itemModelLabel = new JLabel("Item Model");
        itemModelLabel.setFont(boldFont);
        panel.add(itemModelLabel, gbc);

        gbc.gridx = 3;
        JTextField itemModelField = new JTextField(15);
        itemModelField.setFont(regularFont);
        panel.add(itemModelField, gbc);

        // Item Code *
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel itemStock = new JLabel("Stock *");
        itemStock.setFont(boldFont);
        panel.add(itemStock, gbc);

        gbc.gridx = 1;
        JTextField itemStockField = new JTextField(15);
        itemStockField.setFont(regularFont);
        panel.add(itemStockField, gbc);

        // Category
        gbc.gridx = 2;
        JLabel categoryLabel = new JLabel("Subcategory");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 3;
        Vector<String> productSubcategoryNames = new Vector<>();
        productSubcategoryNames.add("Select Subcategory");
        Map<Integer, Integer> productSubcategoryMap = new HashMap<>();
        ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
        var productSubcategories = productSubcategoryService.getAllValidSubcategories();
        for (int i = 0; i < productSubcategories.size(); i++) {
            productSubcategoryNames.add(productSubcategories.get(i).name());
            productSubcategoryMap.put(i + 1, productSubcategories.get(i).id());
        }
        JComboBox<String> categoryCombo = new JComboBox<>(productSubcategoryNames);
        categoryCombo.setFont(regularFont);
        panel.add(categoryCombo, gbc);

        // Brand
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel brandLabel = new JLabel("Brand");
        brandLabel.setFont(boldFont);
        panel.add(brandLabel, gbc);

        gbc.gridx = 1;
        Vector<String> brandNames = new Vector<>();
        brandNames.add("Select Brand");
        BrandService brandService = new BrandService();
        Map<Integer, Integer> brandMap = new HashMap<>();

        JComboBox<String> brandCombo = new JComboBox<>(brandNames);
        brandCombo.setFont(regularFont);
        brandCombo.setSelectedItem("Select Brand");
        panel.add(brandCombo, gbc);


        categoryCombo.addActionListener(e -> {
            int productSubcategorySelectedIndex = categoryCombo.getSelectedIndex();
            int productSubcategoryId = productSubcategoryMap.get(productSubcategorySelectedIndex);

            brandCombo.removeAllItems();

            var brands = brandService.getAllBrandByProductSubcategoryId(productSubcategoryId);
            brandNames.add("Select Brand");
            brandCombo.setSelectedItem("Select Brand");
            for (int i = 0; i < brands.size(); i++) {
                brandNames.add(brands.get(i).name());
                brandMap.put(i + 1, brands.get(i).id());
            }
        });

        // Unit - Change to JComboBox with options
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        panel.add(unitCombo, gbc);

        // Product Tax *
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel productTaxLabel = new JLabel("Product Tax *");
        productTaxLabel.setFont(boldFont);
        panel.add(productTaxLabel, gbc);

        gbc.gridx = 1;
        Map<String, Integer> productTaxTypeMap = new HashMap<>();
        productTaxTypeMap.put("VAT@12%", 12);

        JTextField productTaxField = new JTextField();
        productTaxField.setFont(regularFont);
        productTaxField.setText("VAT@12%");
        productTaxField.setEnabled(false);
        panel.add(productTaxField, gbc);

        // Tax Type
        gbc.gridx = 2;
        JLabel taxTypeLabel = new JLabel("Tax Type");
        taxTypeLabel.setFont(boldFont);
        panel.add(taxTypeLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> taxTypeCombo = new JComboBox<>(new String[]{"Select Tax Type", "Exclusive", "Inclusive"});
        taxTypeCombo.setFont(regularFont);
        panel.add(taxTypeCombo, gbc);

        // Purchase Price
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel purchasePriceLabel = new JLabel("Purchase Price");
        purchasePriceLabel.setFont(boldFont);
        panel.add(purchasePriceLabel, gbc);

        gbc.gridx = 1;
        JTextField purchasePriceField = new JTextField(15);
        purchasePriceField.setFont(regularFont);
        panel.add(purchasePriceField, gbc);

        // Regular Price *
        gbc.gridx = 2;
        JLabel regularPriceLabel = new JLabel("Regular Price *");
        regularPriceLabel.setFont(boldFont);
        panel.add(regularPriceLabel, gbc);

        gbc.gridx = 3;
        JTextField regularPriceField = new JTextField(15);
        regularPriceField.setFont(regularFont);
        panel.add(regularPriceField, gbc);

        // Discount
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel discountLabel = new JLabel("Discount");
        discountLabel.setFont(boldFont);
        panel.add(discountLabel, gbc);

        gbc.gridx = 1;
        JTextField discountField = new JTextField(15);
        discountField.setFont(regularFont);
        panel.add(discountField, gbc);

        // Selling Price
        gbc.gridx = 2;
        JLabel sellingPriceLabel = new JLabel("Selling Price");
        sellingPriceLabel.setFont(boldFont);
        panel.add(sellingPriceLabel, gbc);

        gbc.gridx = 3;
        JTextField sellingPriceField = new JTextField(15);
        sellingPriceField.setFont(regularFont);
        panel.add(sellingPriceField, gbc);
        sellingPriceField.setEnabled(false);
        sellingPriceField.setText("This will be automatically computed");

        // Note
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        noteArea.setFont(regularFont);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel alertQuantityLabel = new JLabel("Alert Quantity");
        alertQuantityLabel.setFont(boldFont);
        panel.add(alertQuantityLabel, gbc);

        gbc.gridx = 1;
        JTextField alertQuantityField = new JTextField(15);
        alertQuantityField.setFont(regularFont);
        panel.add(alertQuantityField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        panel.add(statusCombo, gbc);

        // Image - Modify to use JFileChooser for file upload
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel imageLabel = new JLabel("Image");
        imageLabel.setFont(boldFont);
        panel.add(imageLabel, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose File");
        imageButton.setFont(regularFont);

        // Add action listener for the imageButton to open file explorer
        Base64Converter base64Converter = new Base64Converter();
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    base64Converter.setConvertFileToBase64(selectedFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(imageButton, gbc);

        // Display the form in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the user clicks OK, process the input
        if (result == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText();
            String stock = itemStockField.getText();
            String regularPrice = regularPriceField.getText();
            String productTaxType = "12";

            // Validate the required fields
            if (name.isEmpty() || regularPrice.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int brandSelectedIndex = brandCombo.getSelectedIndex();
                int brandId = brandMap.get(brandSelectedIndex);

                String model = itemModelField.getText();
                String unit = (String) unitCombo.getSelectedItem();
                String taxType = (String) taxTypeCombo.getSelectedItem();
                String purchasePrice = purchasePriceField.getText();
                String discount = discountField.getText();
                String note = noteArea.getText();
                String alertQuantity = alertQuantityField.getText();
                String status = (String) statusCombo.getSelectedItem();
                String image = base64Converter.getBase64();

//                "Per Piece", "12 pcs"
                ProductService productService = new ProductService();
                assert unit != null;
                assert taxType != null;
                assert status != null;
                AddProductRequestDto dto = new AddProductRequestDto(
                        name,
                        model,
                        brandId,
                        unit.equals("Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN,
                        productTaxTypeMap.get("VAT@12%"),
                        taxType.equals("Exclusive") ? ProductTaxType.EXCLUSIVE : ProductTaxType.INCLUSIVE,
                        BigDecimal.valueOf(Double.parseDouble(purchasePrice)),
                        BigDecimal.valueOf(Double.parseDouble(regularPrice)),
                        Integer.parseInt(discount),
                        note,
                        Integer.parseInt(alertQuantity),
                        status.equals("Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE,
                        image,
                        Integer.parseInt(stock)
                );
                productService.add(dto);
                JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProducts();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ProductService productService = new ProductService();
        var products = productService.getAllValidProducts();
        for (int i = 0; i < products.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    products.get(i).id(),
                    products.get(i).code(),
                    products.get(i).brand().productSubcategory().name(),
                    products.get(i).name(),
                    products.get(i).model(),
                    products.get(i).unit().name(),
                    products.get(i).sellingPrice(),
                    products.get(i).status().name()
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

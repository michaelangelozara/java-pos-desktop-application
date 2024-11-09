
package org.POS.frontend.src.raven.application.form.other;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.POS.backend.brand.BrandService;
import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.product.*;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.backend.string_checker.StringChecker;
import org.POS.frontend.src.raven.cell.TableActionCellRender;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;

public class BrandList extends javax.swing.JPanel {

    public BrandList() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {
  @Override
public void onEdit(int row) {
    // Stop cell editing if any
    if (table.isEditing()) {
        table.getCellEditor().stopCellEditing();
    }

    // Get selected product details from the table
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int productId = (Integer) model.getValueAt(row, 1);
    String productName = (String) model.getValueAt(row, 2);
    double productPrice = (Double) model.getValueAt(row, 3);
    int productStock = (Integer) model.getValueAt(row, 4);

    // Create input fields for editing
    JTextField nameField = new JTextField(productName);
    JTextField priceField = new JTextField(String.valueOf(productPrice));
    JTextField stockField = new JTextField(String.valueOf(productStock));

    // Create a panel with GridLayout to match the 'Create' layout
    JPanel editPanel = new JPanel(new GridLayout(4, 2, 10, 10));
    editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
    editPanel.add(new JLabel("Product Name:"));
    editPanel.add(nameField);
    editPanel.add(new JLabel("Price:"));
    editPanel.add(priceField);
    editPanel.add(new JLabel("Stock:"));
    editPanel.add(stockField);
    editPanel.add(new JLabel("Product ID:"));
    editPanel.add(new JLabel(String.valueOf(productId))); // Display product ID as a label (not editable)

    int option = JOptionPane.showConfirmDialog(null, editPanel, "Edit Product", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        // Collect new values after editing
        String updatedName = nameField.getText();
        double updatedPrice = Double.parseDouble(priceField.getText());
        int updatedStock = Integer.parseInt(stockField.getText());


        JOptionPane.showMessageDialog(null, "Product Updated Successfully", "Updated", JOptionPane.INFORMATION_MESSAGE);
        loadProducts();
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
             // Stop cell editing if any
             if (table.isEditing()) {
                 table.getCellEditor().stopCellEditing();
             }

             // Get selected product details from the table
             DefaultTableModel model = (DefaultTableModel) table.getModel();
             int productId = (Integer) model.getValueAt(row, 1);
             String productName = (String) model.getValueAt(row, 2);
             double productPrice = (Double) model.getValueAt(row, 3);
             int productStock = (Integer) model.getValueAt(row, 4);

             // Create a panel with GridLayout to match the 'Create' layout (view-only mode)
             JPanel viewPanel = new JPanel(new GridLayout(4, 2, 10, 10));
             viewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
             viewPanel.add(new JLabel("Product ID:"));
             viewPanel.add(new JLabel(String.valueOf(productId))); // Display Product ID as a label (read-only)
             viewPanel.add(new JLabel("Product Name:"));
             viewPanel.add(new JLabel(productName)); // Display product name (read-only)
             viewPanel.add(new JLabel("Price:"));
             viewPanel.add(new JLabel(String.valueOf(productPrice))); // Display price (read-only)
             viewPanel.add(new JLabel("Stock:"));
             viewPanel.add(new JLabel(String.valueOf(productStock))); // Display stock (read-only)

             JOptionPane.showMessageDialog(null, viewPanel, "View Product", JOptionPane.INFORMATION_MESSAGE);
         }



        };
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
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
        jLabel1.setText("Brand List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Brands");

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
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "ID", "Code", "Category", "SubCategory", "Name", "Status", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1332, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    // ID
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel idLabel = new JLabel("ID");
    idLabel.setFont(boldFont);
    panel.add(idLabel, gbc);

    gbc.gridx = 1;
    JTextField idField = new JTextField(15);
    idField.setFont(regularFont);
    panel.add(idField, gbc);

    // Code
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel codeLabel = new JLabel("Code");
    codeLabel.setFont(boldFont);
    panel.add(codeLabel, gbc);

    gbc.gridx = 1;
    JTextField codeField = new JTextField(15);
    codeField.setFont(regularFont);
    panel.add(codeField, gbc);

    // Category (Combobox)
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel categoryLabel = new JLabel("Category");
    categoryLabel.setFont(boldFont);
    panel.add(categoryLabel, gbc);

    gbc.gridx = 1;
    Vector<String> categoryNames = new Vector<>();
    categoryNames.add("Select Category");
    // Simulate fetching categories (You should replace this with your actual data)
    categoryNames.add("Category 1");
    categoryNames.add("Category 2");

    JComboBox<String> categoryCombo = new JComboBox<>(categoryNames);
    categoryCombo.setFont(regularFont);
    panel.add(categoryCombo, gbc);

    // SubCategory (Combobox)
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel subCategoryLabel = new JLabel("SubCategory");
    subCategoryLabel.setFont(boldFont);
    panel.add(subCategoryLabel, gbc);

    gbc.gridx = 1;
    Vector<String> subCategoryNames = new Vector<>();
    subCategoryNames.add("Select SubCategory");
    // Simulate fetching subcategories (Replace with your data source)
    subCategoryNames.add("SubCategory 1");
    subCategoryNames.add("SubCategory 2");

    JComboBox<String> subCategoryCombo = new JComboBox<>(subCategoryNames);
    subCategoryCombo.setFont(regularFont);
    panel.add(subCategoryCombo, gbc);

    // Name
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel nameLabel = new JLabel("Name");
    nameLabel.setFont(boldFont);
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    JTextField nameField = new JTextField(15);
    nameField.setFont(regularFont);
    panel.add(nameField, gbc);

    // Status (Active/Inactive)
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select Status", "Active", "Inactive"});
    statusCombo.setFont(regularFont);
    panel.add(statusCombo, gbc);

    // Display the form in a dialog
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // If the user clicks OK, process the input
    if (result == JOptionPane.OK_OPTION) {
        String id = idField.getText();
        String code = codeField.getText();
        String category = (String) categoryCombo.getSelectedItem();
        String subCategory = (String) subCategoryCombo.getSelectedItem();
        String name = nameField.getText();
        String status = (String) statusCombo.getSelectedItem();

        // Validate the required fields
        if (id.isEmpty() || code.isEmpty() || name.isEmpty() || category.equals("Select Category") || subCategory.equals("Select SubCategory") || status.equals("Select Status")) {
            JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Process the input values (you can send them to the database or wherever you need)
            System.out.println("ID: " + id);
            System.out.println("Code: " + code);
            System.out.println("Category: " + category);
            System.out.println("SubCategory: " + subCategory);
            System.out.println("Name: " + name);
            System.out.println("Status: " + status);
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadProducts() {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//
//        ProductService productService = new ProductService();
//        var products = productService.getAllValidProducts();
//        for (int i = 0; i < products.size(); i++) {
//            model.addRow(new Object[]{
//                    i + 1,
//                    products.get(i).id(),
//                    products.get(i).code(),
//                    products.get(i).brand().productSubcategory().name(),
//                    products.get(i).name(),
//                    products.get(i).model(),
//                    products.get(i).unit().name(),
//                    products.get(i).sellingPrice(),
//                    products.get(i).status().name()
//            });
//        }
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


package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.POS.frontend.src.raven.application.form.other.InventoryHistory;

public class Inventory extends javax.swing.JPanel {


public Inventory() {
    initComponents();
    
    TableActionEvent event = new TableActionEvent() {
@Override
        public void onEdit(int row) {

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
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "Product Deleted Successfully", 
                    "Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @Override
        public void onView(int row) {
         Application.showForm(new InventoryHistory());

        }
    };

    if (table.getColumnModel().getColumnCount() > 9) {
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
    } else {
        System.err.println("Error: Table does not have enough columns!");
    }
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

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Inventory");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Product");

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
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Image", "Code", "Name", "Item Model", "Stocks", "Purchase Price", "Selling Price", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true, true
            };

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
            table.getColumnModel().getColumn(6).setHeaderValue("Purchase Price");
            table.getColumnModel().getColumn(7).setHeaderValue("Selling Price");
            table.getColumnModel().getColumn(8).setResizable(false);
            table.getColumnModel().getColumn(8).setHeaderValue("Status");
            table.getColumnModel().getColumn(9).setHeaderValue("Action");
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(1227, Short.MAX_VALUE))
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
 JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    Font boldFont = new Font("Arial", Font.BOLD, 14);

    // Input fields - Column 1
    JLabel itemNameLabel = new JLabel("Item Name *:");
    itemNameLabel.setFont(boldFont);
    JTextField itemNameField = new JTextField(15);

    JLabel itemModelLabel = new JLabel("Item Model:");
    itemModelLabel.setFont(boldFont);
    JTextField itemModelField = new JTextField(15);

    JLabel itemCodeLabel = new JLabel("Item Code *:");
    itemCodeLabel.setFont(boldFont);
    JTextField itemCodeField = new JTextField(15);

    JLabel subCategoryLabel = new JLabel("Sub Category:");
    subCategoryLabel.setFont(boldFont);
    JComboBox<String> subCategoryComboBox = new JComboBox<>(new String[]{"Select a category", "Category 1", "Category 2"});

    JLabel brandLabel = new JLabel("Brand:");
    brandLabel.setFont(boldFont);
    JTextField brandField = new JTextField(15);

    JLabel unitLabel = new JLabel("Unit:");
    unitLabel.setFont(boldFont);
    JComboBox<String> unitComboBox = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});

    // Input fields - Column 2
    JLabel productTaxLabel = new JLabel("Product Tax *:");
    productTaxLabel.setFont(boldFont);
    JComboBox<String> productTaxComboBox = new JComboBox<>(new String[]{"Select Product Tax", "Tax 1", "Tax 2"});

    JLabel taxTypeLabel = new JLabel("Tax Type:");
    taxTypeLabel.setFont(boldFont);
    JComboBox<String> taxTypeComboBox = new JComboBox<>(new String[]{"Select Tax Type", "Type 1", "Type 2"});

    JLabel purchasePriceLabel = new JLabel("Purchase Price:");
    purchasePriceLabel.setFont(boldFont);
    JTextField purchasePriceField = new JTextField(15);

    JLabel regularPriceLabel = new JLabel("Regular Price *:");
    regularPriceLabel.setFont(boldFont);
    JTextField regularPriceField = new JTextField(15);

    JLabel discountLabel = new JLabel("Discount:");
    discountLabel.setFont(boldFont);
    JTextField discountField = new JTextField("0", 5);

    JLabel sellingPriceLabel = new JLabel("Selling Price:");
    sellingPriceLabel.setFont(boldFont);
    JTextField sellingPriceField = new JTextField("0", 15);

    // Additional fields after Note
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(boldFont);
    JTextArea noteArea = new JTextArea(3, 20);
    JScrollPane noteScrollPane = new JScrollPane(noteArea);

    JLabel alertQuantityLabel = new JLabel("Alert Quantity:");
    alertQuantityLabel.setFont(boldFont);
    JTextField alertQuantityField = new JTextField(15);

    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(boldFont);
    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(boldFont);
    JButton imageButton = new JButton("Choose Image");

    // File Chooser for Image Button
    imageButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // You can handle file path here
                JOptionPane.showMessageDialog(null, "Selected image: " + fileChooser.getSelectedFile().getName());
            }
        }
    });

    // Column 1 layout
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(itemNameLabel, gbc);
    gbc.gridx = 1;
    panel.add(itemNameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(itemModelLabel, gbc);
    gbc.gridx = 1;
    panel.add(itemModelField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(itemCodeLabel, gbc);
    gbc.gridx = 1;
    panel.add(itemCodeField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(subCategoryLabel, gbc);
    gbc.gridx = 1;
    panel.add(subCategoryComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(brandLabel, gbc);
    gbc.gridx = 1;
    panel.add(brandField, gbc);

    gbc.gridx = 0; gbc.gridy = 5;
    panel.add(unitLabel, gbc);
    gbc.gridx = 1;
    panel.add(unitComboBox, gbc);

    // Column 2 layout
    gbc.gridx = 2; gbc.gridy = 0;
    panel.add(productTaxLabel, gbc);
    gbc.gridx = 3;
    panel.add(productTaxComboBox, gbc);

    gbc.gridx = 2; gbc.gridy = 1;
    panel.add(taxTypeLabel, gbc);
    gbc.gridx = 3;
    panel.add(taxTypeComboBox, gbc);

    gbc.gridx = 2; gbc.gridy = 2;
    panel.add(purchasePriceLabel, gbc);
    gbc.gridx = 3;
    panel.add(purchasePriceField, gbc);

    gbc.gridx = 2; gbc.gridy = 3;
    panel.add(regularPriceLabel, gbc);
    gbc.gridx = 3;
    panel.add(regularPriceField, gbc);

    gbc.gridx = 2; gbc.gridy = 4;
    panel.add(discountLabel, gbc);
    gbc.gridx = 3;
    panel.add(discountField, gbc);

    gbc.gridx = 2; gbc.gridy = 5;
    panel.add(sellingPriceLabel, gbc);
    gbc.gridx = 3;
    panel.add(sellingPriceField, gbc);

    // Full row for note and extra fields
    gbc.gridx = 0; gbc.gridy = 6;
    gbc.gridwidth = 2;
    panel.add(noteLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 3;
    panel.add(noteScrollPane, gbc);

    // Alert Quantity, Status, Image fields below note
    gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
    panel.add(alertQuantityLabel, gbc);
    gbc.gridx = 1;
    panel.add(alertQuantityField, gbc);

    gbc.gridx = 2;
    panel.add(statusLabel, gbc);
    gbc.gridx = 3;
    panel.add(statusComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 8;
    panel.add(imageLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 3;
    panel.add(imageButton, gbc);

    // Set panel size to fill the JOptionPane appropriately
    panel.setPreferredSize(new Dimension(800, 450));

    int result = JOptionPane.showConfirmDialog(null, panel, 
            "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String itemName = itemNameField.getText().trim();
        String itemModel = itemModelField.getText().trim();
        String itemCode = itemCodeField.getText().trim();
        String subCategory = (String) subCategoryComboBox.getSelectedItem();
        String brand = brandField.getText().trim();
        String unit = (String) unitComboBox.getSelectedItem();
        String productTax = (String) productTaxComboBox.getSelectedItem();
        String taxType = (String) taxTypeComboBox.getSelectedItem();
        String purchasePrice = purchasePriceField.getText().trim();
        String regularPrice = regularPriceField.getText().trim();
        String discount = discountField.getText().trim();
        String sellingPrice = sellingPriceField.getText().trim();
        String note = noteArea.getText().trim();
        String alertQuantity = alertQuantityField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();

        // Validation logic
        if (itemName.isEmpty() || itemCode.isEmpty() || subCategory.equals("Select a category")) {
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
            JOptionPane.showMessageDialog(null, 
                "Please enter the mandatory fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Handle success message and logic here
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
            
            JOptionPane.showMessageDialog(null, 
                "Product Created Successfully!\nItem Name: " + itemName + 
                "\nItem Model: " + itemModel + "\nItem Code: " + itemCode +
                "\nSub Category: " + subCategory + "\nBrand: " + brand +
                "\nUnit: " + unit + "\nProduct Tax: " + productTax +
                "\nTax Type: " + taxType + "\nPurchase Price: " + purchasePrice +
                "\nRegular Price: " + regularPrice + "\nDiscount: " + discount +
                "\nSelling Price: " + sellingPrice + "\nNote: " + note + 
                "\nAlert Quantity: " + alertQuantity + "\nStatus: " + status, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed


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

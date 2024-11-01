
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
    // Sample product data for demonstration purposes (you would fetch this from your data source)
    String existingItemName = "Sample Item Name"; // Assuming column 0 contains item name
    String existingItemModel = "Sample Model";     // Assuming column 1 contains item model
    String existingItemCode = "SMPL123";           // Assuming column 2 contains item code
    String existingCategory = "Category 1";        // Assuming column 3 contains category
    String existingBrand = "Sample Brand";          // Assuming column 4 contains brand
    String existingUnit = "Per Piece";              // Assuming column 5 contains unit
    String existingProductTax = "VAT@10";          // Assuming column 6 contains product tax
    String existingTaxType = "Exclusive";           // Assuming column 7 contains tax type
    String existingPurchasePrice = "100.00";        // Assuming column 8 contains purchase price
    String existingRegularPrice = "150.00";         // Assuming column 9 contains regular price
    String existingDiscount = "10";                  // Assuming column 10 contains discount
    String existingSellingPrice = "140.00";         // Assuming column 11 contains selling price
    String existingNote = "This is a sample note."; // Assuming column 12 contains note
    String existingAlertQuantity = "5";              // Assuming column 13 contains alert quantity
    String existingStatus = "Active";                // Assuming column 14 contains status

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
    itemNameField.setText(existingItemName); // Prepopulate with existing value
    panel.add(itemNameField, gbc);

    // Item Model
    gbc.gridx = 2;
    JLabel itemModelLabel = new JLabel("Item Model");
    itemModelLabel.setFont(boldFont);
    panel.add(itemModelLabel, gbc);

    gbc.gridx = 3;
    JTextField itemModelField = new JTextField(15);
    itemModelField.setFont(regularFont);
    itemModelField.setText(existingItemModel); // Prepopulate with existing value
    panel.add(itemModelField, gbc);

    // Item Code *
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel itemCodeLabel = new JLabel("Item Code *");
    itemCodeLabel.setFont(boldFont);
    panel.add(itemCodeLabel, gbc);

    gbc.gridx = 1;
    JTextField itemCodeField = new JTextField(15);
    itemCodeField.setFont(regularFont);
    itemCodeField.setText(existingItemCode); // Prepopulate with existing value
    panel.add(itemCodeField, gbc);

    // Category
    gbc.gridx = 2;
    JLabel categoryLabel = new JLabel("Category");
    categoryLabel.setFont(boldFont);
    panel.add(categoryLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Select Category", "Category 1", "Category 2"});
    categoryCombo.setFont(regularFont);
    categoryCombo.setSelectedItem(existingCategory); // Preselect existing category
    panel.add(categoryCombo, gbc);

    // Brand
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel brandLabel = new JLabel("Brand");
    brandLabel.setFont(boldFont);
    panel.add(brandLabel, gbc);

    gbc.gridx = 1;
    JTextField brandField = new JTextField(15);
    brandField.setFont(regularFont);
    brandField.setText(existingBrand); // Prepopulate with existing value
    panel.add(brandField, gbc);

    // Unit
    gbc.gridx = 2;
    JLabel unitLabel = new JLabel("Unit");
    unitLabel.setFont(boldFont);
    panel.add(unitLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
    unitCombo.setFont(regularFont);
    unitCombo.setSelectedItem(existingUnit); // Preselect existing unit
    panel.add(unitCombo, gbc);

    // Product Tax *
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel productTaxLabel = new JLabel("Product Tax *");
    productTaxLabel.setFont(boldFont);
    panel.add(productTaxLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> productTaxCombo = new JComboBox<>(new String[]{"Select Product Type", "VAT@0", "VAT@5", "VAT@10"});
    productTaxCombo.setFont(regularFont);
    productTaxCombo.setSelectedItem(existingProductTax); // Preselect existing product tax
    panel.add(productTaxCombo, gbc);

    // Tax Type
    gbc.gridx = 2;
    JLabel taxTypeLabel = new JLabel("Tax Type");
    taxTypeLabel.setFont(boldFont);
    panel.add(taxTypeLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> taxTypeCombo = new JComboBox<>(new String[]{"Select Tax Type", "Exclusive", "Inclusive"});
    taxTypeCombo.setFont(regularFont);
    taxTypeCombo.setSelectedItem(existingTaxType); // Preselect existing tax type
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
    purchasePriceField.setText(existingPurchasePrice); // Prepopulate with existing value
    panel.add(purchasePriceField, gbc);

    // Regular Price *
    gbc.gridx = 2;
    JLabel regularPriceLabel = new JLabel("Regular Price *");
    regularPriceLabel.setFont(boldFont);
    panel.add(regularPriceLabel, gbc);

    gbc.gridx = 3;
    JTextField regularPriceField = new JTextField(15);
    regularPriceField.setFont(regularFont);
    regularPriceField.setText(existingRegularPrice); // Prepopulate with existing value
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
    discountField.setText(existingDiscount); // Prepopulate with existing value
    panel.add(discountField, gbc);

    // Selling Price
    gbc.gridx = 2;
    JLabel sellingPriceLabel = new JLabel("Selling Price");
    sellingPriceLabel.setFont(boldFont);
    panel.add(sellingPriceLabel, gbc);

    gbc.gridx = 3;
    JTextField sellingPriceField = new JTextField(15);
    sellingPriceField.setFont(regularFont);
    sellingPriceField.setText(existingSellingPrice); // Prepopulate with existing value
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
    noteArea.setText(existingNote); // Prepopulate with existing value
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
    alertQuantityField.setText(existingAlertQuantity); // Prepopulate with existing value
    panel.add(alertQuantityField, gbc);

    // Status
    gbc.gridx = 2;
    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select status", "Active", "Inactive"});
    statusCombo.setFont(regularFont);
    statusCombo.setSelectedItem(existingStatus); // Preselect existing status
    panel.add(statusCombo, gbc);

    // Finalize and show the dialog
    int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        // Handle saving the edited values here
//        // Example:
//        String updatedItemName = itemNameField.getText();
//        String updatedItemModel = itemModelField.getText();
//        String updatedItemCode = itemCodeField.getText();
//        String updatedCategory = (String) categoryCombo.getSelectedItem();
//        String updatedBrand = brandField.getText();
//        String updatedUnit = (String) unitCombo.getSelectedItem();
//        String updatedProductTax = (String) productTaxCombo.getSelectedItem();
//        String updatedTaxType = (String) taxTypeCombo.getSelectedItem();
//        String updatedPurchasePrice = purchasePriceField.getText();
//        String updatedRegularPrice = regularPriceField.getText();
//        String updatedDiscount = discountField.getText();
//        String updatedSellingPrice = sellingPriceField.getText();
//        String updatedNote = noteArea.getText();
//        String updatedAlertQuantity = alertQuantityField.getText();
//        String updatedStatus = (String) statusCombo.getSelectedItem();

        // TODO: Save the updated values back to your data source
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
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "Product Deleted Successfully", 
                    "Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        }

            @Override

public void onView(int row) {
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
    detail = new JLabel("Steel Bar", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  // Details font size 16
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Code:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("AA-001", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Item Model:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("Steel Bar", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Category:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("Steel", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Brand:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("Sample Brand", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Unit:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("Per Piece", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Product Tax:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("10%", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Tax Amount:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("15%", horizontalAlignment);
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
    detail = new JLabel("150.00", horizontalAlignment);
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
    detail = new JLabel("155.00", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Selling Price:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("170.00", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Stock:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("150 pc", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Inventory Value:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("25,500.00", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Alert Quantity:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("10", horizontalAlignment);
    detail.setFont(new Font("Arial", Font.PLAIN, 16));  
    detail.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    detail.setOpaque(true);
    detailsPanel.add(detail);

    label = new JLabel("Note:", horizontalAlignment);
    label.setFont(new Font("Arial", Font.BOLD, 18));  
    label.setBorder(BorderFactory.createCompoundBorder(border, padding));  // Add border and padding
    label.setOpaque(true);
    detailsPanel.add(label);
    detail = new JLabel("None", horizontalAlignment);
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
     table.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
     table.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(event));
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
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Category", "Code", "Name", "Item Model", "Unit", "Selling Price", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
    JLabel itemCodeLabel = new JLabel("Item Code *");
    itemCodeLabel.setFont(boldFont);
    panel.add(itemCodeLabel, gbc);

    gbc.gridx = 1;
    JTextField itemCodeField = new JTextField(15);
    itemCodeField.setFont(regularFont);
    itemCodeField.setText("Item Code");
    panel.add(itemCodeField, gbc);

    // Category
    gbc.gridx = 2;
    JLabel categoryLabel = new JLabel("Category");
    categoryLabel.setFont(boldFont);
    panel.add(categoryLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Select Category", "Category 1", "Category 2"});
    categoryCombo.setFont(regularFont);
    panel.add(categoryCombo, gbc);

    // Brand
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel brandLabel = new JLabel("Brand");
    brandLabel.setFont(boldFont);
    panel.add(brandLabel, gbc);

    gbc.gridx = 1;
    JTextField brandField = new JTextField(15);
    brandField.setFont(regularFont);
    panel.add(brandField, gbc);

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
    JComboBox<String> productTaxCombo = new JComboBox<>(new String[]{"Select Product Type", "VAT@0", "VAT@5", "VAT@10"});
    productTaxCombo.setFont(regularFont);
    panel.add(productTaxCombo, gbc);

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
    imageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Selected Image: " + selectedFile.getName());
        }
    });
    panel.add(imageButton, gbc);

    // Display the form in a dialog
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    // If the user clicks OK, process the input
    if (result == JOptionPane.OK_OPTION) {
        String itemName = itemNameField.getText();
        String itemCode = itemCodeField.getText();
        String regularPrice = regularPriceField.getText();
        
        // Validate the required fields
        if (itemName.isEmpty() || itemCode.isEmpty() || regularPrice.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

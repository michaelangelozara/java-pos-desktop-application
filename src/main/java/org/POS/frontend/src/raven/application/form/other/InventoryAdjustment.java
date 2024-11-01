
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.border.TitledBorder;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.POS.frontend.src.raven.application.form.other.InventoryHistory;



public class InventoryAdjustment extends javax.swing.JPanel {


public InventoryAdjustment() {
    initComponents();
    
    TableActionEvent event = new TableActionEvent() {
        
   @Override
public void onEdit(int row) {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    // New Font for labels: Bigger and Bold
    Font boldFont = new Font("Arial", Font.BOLD, 16);

    // Get current row's data
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String product = (String) model.getValueAt(row, 1);
    String reason = (String) model.getValueAt(row, 3);
    String note = (String) model.getValueAt(row, 5);
    String status = (String) model.getValueAt(row, 6);
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    // New Fields: Stock, Adjustment Type, Quantity
    int availableStock = 100; // Example stock, replace with actual stock logic
    String[] adjustmentTypes = {"Increment", "Decrement"};
    int initialQuantity = 1; // Default initial quantity

    // Product options for hardware materials
    String[] products = {"Plywood", "PVC", "Nails", "Cement", "Bricks"};

    // Creating input fields
    JTextField adjustmentNoField = new JTextField("AIA-1"); // Example adjustment number
    adjustmentNoField.setEditable(false);

    JTextField adjustmentReasonField = new JTextField(reason);
    JComboBox<String> selectProductsComboBox = new JComboBox<>(products);
    selectProductsComboBox.setSelectedItem(product);

    JTextField stocksField = new JTextField(String.valueOf(availableStock)); // Stock field
    stocksField.setEditable(false); // Read-only

    JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(adjustmentTypes); // Adjustment Type ComboBox

    JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(initialQuantity, 1, availableStock, 1)); // Quantity Spinner

    // Note section after Quantity
    JTextArea noteField = new JTextArea(3, 20);
    JScrollPane noteScrollPane = new JScrollPane(noteField);
    noteField.setText(note);

    JTextField adjustmentDateField = new JTextField(date); // Current date pre-filled
    adjustmentDateField.setEditable(false); // Read-only

    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusComboBox.setSelectedItem(status);

    // Create panel for Stock, Adjustment Type, Quantity
    JPanel adjustmentDetailsPanel = new JPanel(new GridBagLayout());
    adjustmentDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adjustment Details", TitledBorder.LEFT, TitledBorder.TOP, boldFont));

    GridBagConstraints adjGbc = new GridBagConstraints();
    adjGbc.insets = new Insets(5, 5, 5, 5);
    adjGbc.fill = GridBagConstraints.HORIZONTAL;
    adjGbc.weightx = 1.0;

    adjGbc.gridx = 0; adjGbc.gridy = 0;
    JLabel stocksLabel = new JLabel("Stocks Available:");
    stocksLabel.setFont(boldFont);
    adjustmentDetailsPanel.add(stocksLabel, adjGbc);
    adjGbc.gridx = 1; adjustmentDetailsPanel.add(stocksField, adjGbc);

    adjGbc.gridx = 0; adjGbc.gridy = 1;
    JLabel adjustmentTypeLabel = new JLabel("Adjustment Type *:");
    adjustmentTypeLabel.setFont(boldFont);
    adjustmentDetailsPanel.add(adjustmentTypeLabel, adjGbc);
    adjGbc.gridx = 1; adjustmentDetailsPanel.add(adjustmentTypeComboBox, adjGbc);

    adjGbc.gridx = 0; adjGbc.gridy = 2;
    JLabel quantityLabel = new JLabel("Quantity *:");
    quantityLabel.setFont(boldFont);
    adjustmentDetailsPanel.add(quantityLabel, adjGbc);
    adjGbc.gridx = 1; adjustmentDetailsPanel.add(quantitySpinner, adjGbc);

    // Initially, make the adjustmentDetailsPanel invisible
    adjustmentDetailsPanel.setVisible(false);

    // Add action listener to the product ComboBox to show/hide the panel
    selectProductsComboBox.addItemListener(e -> {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String selectedProduct = (String) e.getItem();
            if (selectedProduct.equals("Plywood") || selectedProduct.equals("PVC")) {
                adjustmentDetailsPanel.setVisible(true); // Show the panel for specific products
            } else {
                adjustmentDetailsPanel.setVisible(false); // Hide the panel for other products
            }
            panel.revalidate();
            panel.repaint();
        }
    });

    // Adding components to the panel with updated bigger and bold labels
    gbc.gridx = 0; gbc.gridy = 0;
    JLabel adjustmentNoLabel = new JLabel("Adjustment No:");
    adjustmentNoLabel.setFont(boldFont);
    panel.add(adjustmentNoLabel, gbc);
    gbc.gridx = 1; panel.add(adjustmentNoField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    JLabel adjustmentReasonLabel = new JLabel("Adjustment Reason:");
    adjustmentReasonLabel.setFont(boldFont);
    panel.add(adjustmentReasonLabel, gbc);
    gbc.gridx = 1; panel.add(adjustmentReasonField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    JLabel selectProductsLabel = new JLabel("Select Products *:");
    selectProductsLabel.setFont(boldFont);
    panel.add(selectProductsLabel, gbc);
    gbc.gridx = 1; panel.add(selectProductsComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    panel.add(adjustmentDetailsPanel, gbc); // Add the group panel with stocks, adjustment type, and quantity

    gbc.gridwidth = 1;
    gbc.gridx = 0; gbc.gridy = 4;
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(boldFont);
    panel.add(noteLabel, gbc);
    gbc.gridx = 1; panel.add(noteScrollPane, gbc);

    gbc.gridx = 0; gbc.gridy = 5;
    JLabel adjustmentDateLabel = new JLabel("Adjustment Date:");
    adjustmentDateLabel.setFont(boldFont);
    panel.add(adjustmentDateLabel, gbc);
    gbc.gridx = 1; panel.add(adjustmentDateField, gbc);

    gbc.gridx = 0; gbc.gridy = 6;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);
    gbc.gridx = 1; panel.add(statusComboBox, gbc);

    // Set panel size
    panel.setPreferredSize(new Dimension(600, 600));

    int result = JOptionPane.showConfirmDialog(null, panel, "Edit Adjustment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String newReason = adjustmentReasonField.getText().trim();
        String newProduct = (String) selectProductsComboBox.getSelectedItem();
        int newQuantity = (Integer) quantitySpinner.getValue();
        String newNote = noteField.getText();
        String newStatus = (String) statusComboBox.getSelectedItem();

        // Validation
        if (newReason.isEmpty() || newProduct == null || newQuantity <= 0) {
            JOptionPane.showMessageDialog(null, "Please fill in all mandatory fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Early exit if validation fails
        }

        // Update table with new values
        model.setValueAt(newProduct, row, 1);
        model.setValueAt(newReason, row, 3);
        model.setValueAt(newQuantity, row, 7); // Assuming you have a column for quantity in the table
        model.setValueAt(newNote, row, 5);
        model.setValueAt(newStatus, row, 6);

        JOptionPane.showMessageDialog(null, "Adjustment updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        model.removeRow(row); // This should remove the row
        JOptionPane.showMessageDialog(null, "Product Deleted Successfully", 
            "Deleted", JOptionPane.INFORMATION_MESSAGE);
    }
        }

        @Override
        public void onView(int row) {
        Application.showForm(new InventoryHistory());

        }
    };

    if (table.getColumnModel().getColumnCount() > 7) {
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
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
        jLabel1.setText("Inventory Adjustments");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Inventory Adjustments");

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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Product", "Adjustment #", "Reason", "Date", "Notes", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(996, Short.MAX_VALUE))
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
gbc.insets = new Insets(10, 10, 10, 10);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;

Font boldFont = new Font("Arial", Font.BOLD, 14);

// Adjustment Reason
JLabel adjustmentReasonLabel = new JLabel("Adjustment Reason *:");
adjustmentReasonLabel.setFont(boldFont);
JTextField adjustmentReasonField = new JTextField(15);

// Select Products (ComboBox)
JLabel selectProductsLabel = new JLabel("Select Products *:");
selectProductsLabel.setFont(boldFont);
String[] productList = {"Product A", "Product B", "Product C", "Product D"}; // Product options
JComboBox<String> selectProductsComboBox = new JComboBox<>(productList);

// Note
JLabel noteLabel = new JLabel("Note:");
noteLabel.setFont(boldFont);
JTextArea noteArea = new JTextArea(3, 15);
JScrollPane noteScrollPane = new JScrollPane(noteArea);

// Adjustment Date (real-time date)
JLabel adjustmentDateLabel = new JLabel("Adjustment Date:");
adjustmentDateLabel.setFont(boldFont);
LocalDate currentDate = LocalDate.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
JTextField adjustmentDateField = new JTextField(currentDate.format(formatter), 15);
adjustmentDateField.setEditable(false); // Date field is read-only

// Status
JLabel statusLabel = new JLabel("Status:");
statusLabel.setFont(boldFont);
JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

// Layout adjustments
gbc.gridx = 0; gbc.gridy = 0;
panel.add(adjustmentReasonLabel, gbc);
gbc.gridx = 1;
panel.add(adjustmentReasonField, gbc);

gbc.gridx = 0; gbc.gridy = 1;
panel.add(selectProductsLabel, gbc);
gbc.gridx = 1;
panel.add(selectProductsComboBox, gbc); // Add ComboBox for product selection

gbc.gridx = 0; gbc.gridy = 2;
panel.add(noteLabel, gbc);
gbc.gridx = 1;
panel.add(noteScrollPane, gbc);

gbc.gridx = 0; gbc.gridy = 3;
panel.add(adjustmentDateLabel, gbc);
gbc.gridx = 1;
panel.add(adjustmentDateField, gbc); // Real-time date field here

gbc.gridx = 0; gbc.gridy = 4;
panel.add(statusLabel, gbc);
gbc.gridx = 1;
panel.add(statusComboBox, gbc);

// Set panel size to fill the JOptionPane appropriately
panel.setPreferredSize(new Dimension(400, 300));

int result = JOptionPane.showConfirmDialog(null, panel,
        "Create Adjustment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (result == JOptionPane.OK_OPTION) {
    String adjustmentReason = adjustmentReasonField.getText().trim();
    String selectedProduct = (String) selectProductsComboBox.getSelectedItem(); // Get selected product
    String note = noteArea.getText().trim();
    String adjustmentDate = adjustmentDateField.getText().trim(); // Real-time date from text field
    String status = (String) statusComboBox.getSelectedItem();

    // Validation logic
    if (adjustmentReason.isEmpty() || selectedProduct == null) {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(null,
                "Please enter the mandatory fields.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        // Add the new data to the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Generate an automatic number for the first column (row count + 1)
        int newRowNumber = model.getRowCount() + 1;

        // Add the row to the table with the correct columns
        model.addRow(new Object[]{
                newRowNumber,              // Auto-generated row number
                selectedProduct,           // Product
                newRowNumber,              // Adjustment # (You may want to change how this is generated)
                adjustmentReason,          // Reason
                adjustmentDate,            // Date
                note,                      // Notes
                status,                    // Status
                "Actions"                  // Placeholder for actions (buttons already in place)
        });

        // Handle success message and logic here
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(null,
                "Adjustment Created Successfully!\nAdjustment Reason: " + adjustmentReason +
                        "\nSelected Product: " + selectedProduct + "\nNote: " + note +
                        "\nAdjustment Date: " + adjustmentDate + "\nStatus: " + status,
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

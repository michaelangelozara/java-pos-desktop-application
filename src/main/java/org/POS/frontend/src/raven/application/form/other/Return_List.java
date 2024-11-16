
package org.POS.frontend.src.raven.application.form.other;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonType;
import org.POS.backend.purchase.PurchaseService;
import org.POS.frontend.src.raven.cell.TableActionCellRender;

import javax.swing.table.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.POS.frontend.src.raven.application.form.other.Return_Details;


public class Return_List extends javax.swing.JPanel {

    public Return_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = 0;
                gbc.gridy = 0;

                Font labelFont = new Font("Arial", Font.BOLD, 16);

                // First row: Purchase No, Purchase Return No, Supplier
                JLabel purchaseNoLabel = new JLabel("Purchase No:");
                purchaseNoLabel.setFont(labelFont);
                panel.add(purchaseNoLabel, gbc);
                gbc.gridx++;
                JTextField purchaseNoField = new JTextField(10);
                panel.add(purchaseNoField, gbc);

                gbc.gridx++;
                JLabel purchaseReturnNoLabel = new JLabel("Purchase Return No:");
                purchaseReturnNoLabel.setFont(labelFont);
                panel.add(purchaseReturnNoLabel, gbc);
                gbc.gridx++;
                JTextField purchaseReturnNoField = new JTextField(10);
                panel.add(purchaseReturnNoField, gbc);

                gbc.gridx++;
                JLabel supplierLabel = new JLabel("Supplier:");
                supplierLabel.setFont(labelFont);
                panel.add(supplierLabel, gbc);
                gbc.gridx++;
                JTextField supplierField = new JTextField(10);
                panel.add(supplierField, gbc);

                // Second row: Return Reason
                gbc.gridx = 0;
                gbc.gridy++;
                JLabel returnReasonLabel = new JLabel("Return Reason:");
                returnReasonLabel.setFont(labelFont);
                panel.add(returnReasonLabel, gbc);
                gbc.gridx++;
                gbc.gridwidth = 4;
                JTextField returnReasonField = new JTextField(30);
                panel.add(returnReasonField, gbc);

                // Table for products
                gbc.gridx = 0;
                gbc.gridy++;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                String[] columnNames = {"#", "Code", "Name", "Purchased Qty", "Current Qty", "Returned Qty", "Unit Cost", "Total Price", "Return Price"};
                Object[][] data = {
                        {1, "HW-000001", "Plywood", 50, 45, 5, 150.00, 7500.00, 750.00},
                        {2, "HW-000002", "Metal", 30, 28, 2, 300.00, 9000.00, 600.00},
                        {3, "HW-000003", "PVC", 100, 95, 5, 100.00, 10000.00, 500.00}
                };

                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(model) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 5;  // Only make "Returned Qty" column editable for JSpinner
                    }
                };

                // Add JSpinner for "Returned Qty" column
                TableColumn returnedQtyColumn = table.getColumnModel().getColumn(5);
                returnedQtyColumn.setCellEditor(new SpinnerEditor());

                JScrollPane tableScrollPane = new JScrollPane(table);
                panel.add(tableScrollPane, gbc);

                gbc.gridwidth = 1;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.gridy++;

                // Subtotal section (Peso values)
                JLabel subtotalLabel = new JLabel("Subtotal:");
                subtotalLabel.setFont(labelFont);
                panel.add(subtotalLabel, gbc);
                gbc.gridx++;
                JLabel subtotalValueLabel = new JLabel("₱29,800.00");  // Display returnedSubtotal in pesos
                panel.add(subtotalValueLabel, gbc);
                gbc.gridx++;
                JLabel returnTotalValueLabel = new JLabel("₱5,960.00");  // Display return total in pesos
                panel.add(returnTotalValueLabel, gbc);

                gbc.gridx = 0;
                gbc.gridy++;

                // Discount, Transport Cost, Purchase Tax
                JLabel discountLabel = new JLabel("Discount:");
                discountLabel.setFont(labelFont);
                panel.add(discountLabel, gbc);
                gbc.gridx++;
                JTextField discountField = new JTextField(10);
                panel.add(discountField, gbc);

                gbc.gridx++;
                JLabel transportCostLabel = new JLabel("Transport Cost:");
                transportCostLabel.setFont(labelFont);
                panel.add(transportCostLabel, gbc);
                gbc.gridx++;
                JTextField transportCostField = new JTextField(10);
                panel.add(transportCostField, gbc);

                gbc.gridx++;
                JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
                purchaseTaxLabel.setFont(labelFont);
                panel.add(purchaseTaxLabel, gbc);
                gbc.gridx++;
                JTextField purchaseTaxField = new JTextField(10);
                panel.add(purchaseTaxField, gbc);

                // Purchase Total, Total Paid, Purchase Due
                gbc.gridx = 0;
                gbc.gridy++;
                JLabel purchaseTotalLabel = new JLabel("Purchase Total:");
                purchaseTotalLabel.setFont(labelFont);
                panel.add(purchaseTotalLabel, gbc);
                gbc.gridx++;
                JTextField purchaseTotalField = new JTextField(10);
                panel.add(purchaseTotalField, gbc);

                gbc.gridx++;
                JLabel totalPaidLabel = new JLabel("Total Paid:");
                totalPaidLabel.setFont(labelFont);
                panel.add(totalPaidLabel, gbc);
                gbc.gridx++;
                JTextField totalPaidField = new JTextField(10);
                panel.add(totalPaidField, gbc);

                gbc.gridx++;
                JLabel purchaseDueLabel = new JLabel("Purchase Due:");
                purchaseDueLabel.setFont(labelFont);
                panel.add(purchaseDueLabel, gbc);
                gbc.gridx++;
                JTextField purchaseDueField = new JTextField(10);
                panel.add(purchaseDueField, gbc);

                // Note
                gbc.gridy++;
                gbc.gridx = 0;
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(labelFont);
                panel.add(noteLabel, gbc);
                gbc.gridx++;
                gbc.gridwidth = 5;
                JTextArea noteArea = new JTextArea(3, 30);
                panel.add(new JScrollPane(noteArea), gbc);

                // Return Date and Status
                gbc.gridwidth = 1;
                gbc.gridy++;
                gbc.gridx = 0;
                JLabel returnDateLabel = new JLabel("Return Date:");
                returnDateLabel.setFont(labelFont);
                panel.add(returnDateLabel, gbc);

                gbc.gridx++;
                JDatePickerImpl returnDatePicker = createDatePicker(); // Create createdAt picker
                panel.add(returnDatePicker, gbc);

                gbc.gridx++;
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(labelFont);
                panel.add(statusLabel, gbc);
                gbc.gridx++;
                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
                panel.add(statusCombo, gbc);

                // Show dialog
                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Purchase Return", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Handle form submission logic
                    String returnReason = returnReasonField.getText();
                    String supplier = supplierField.getText();
                    String note = noteArea.getText();
                    String returnDate = returnDatePicker.getJFormattedTextField().getText();
                    String status = (String) statusCombo.getSelectedItem();

                    // Process the data accordingly
                }
            }

            // JSpinner Editor for JTable column
            class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
                JSpinner spinner = new JSpinner();

                @Override
                public Object getCellEditorValue() {
                    return spinner.getValue();
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    spinner.setValue(value);
                    return spinner;
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
                Application.showForm(new Return_Details());

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
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Return List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Returns");

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
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "Return No	", "Purchase No	", "Supplier", "Return Reason	", "Cost of Return Products	", "Date	", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
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
            table.getColumnModel().getColumn(7).setResizable(false);
        }

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setText("From - To");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
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
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                .addGap(0, 1235, Short.MAX_VALUE)))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Define font for larger bold labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Return Reason and Supplier - first row (full width, two fields)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel returnReasonLabel = new JLabel("Return Reason:");
        returnReasonLabel.setFont(labelFont);
        panel.add(returnReasonLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextField returnReasonField = new JTextField(15);
        panel.add(returnReasonField, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(labelFont);
        panel.add(supplierLabel, gbc);

        PersonService personService = new PersonService();
        var suppliers = personService.getAllValidPeopleByType(PersonType.SUPPLIER);
        Vector<String> supplierNames = new Vector<>();
        supplierNames.add("Select Supplier");
        Map<Integer, Integer> supplierMap = new HashMap<>();
        for(int i = 0; i < suppliers.size(); i++){
            supplierNames.add(suppliers.get(i).name());
            supplierMap.put(i+1, suppliers.get(i).id());
        }

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        JComboBox<String> supplierCombo = new JComboBox<>(supplierNames);
        panel.add(supplierCombo, gbc);

        // Select Purchases - next row (full width)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel selectPurchasesLabel = new JLabel("Select Purchases:");
        selectPurchasesLabel.setFont(labelFont);
        panel.add(selectPurchasesLabel, gbc);

        PurchaseService purchaseService = new PurchaseService();
        Vector<String> purchaseNames = new Vector<>();
        purchaseNames.add("Select Purchase code");
        Map<Integer, Integer> purchaseMap = new HashMap<>();

        gbc.gridx = 1;
        gbc.gridwidth = 5;
        JComboBox<String> purchasesCombo = new JComboBox<>(purchaseNames);
        panel.add(purchasesCombo, gbc);

        supplierCombo.addActionListener(e -> {
            purchasesCombo.removeAllItems();
            purchaseMap.clear();

            int supplierSelectedIndex = supplierCombo.getSelectedIndex();
            int supplierId = supplierMap.get(supplierSelectedIndex);
            var purchases = purchaseService.getAllValidPurchaseBySupplierId(supplierId);
            purchaseNames.add("Select Purchase code");
            for(int i = 0; i < purchases.size(); i++){
                purchasesCombo.addItem(purchases.get(i).code());
                purchaseMap.put(i+1, purchases.get(i).id());
            }

        });

        // Return Table - below Select Purchases
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"#", "Code", "Name", "Purchased Quantity", "Current Quantity", "Returned Quantity", "Unit Cost", "Total Price", "Return Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 8; // Only "Returned Quantity" and "Return Price" are editable
            }
        };

        JTable returnTable = new JTable(tableModel);
        returnTable.setFillsViewportHeight(true);

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < returnTable.getColumnCount(); i++) {
            returnTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(returnTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(tableScrollPane, gbc);

        // Subtotal Panel - below the table
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel subtotalPanel = new JPanel(new GridBagLayout());
        subtotalPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        subtotalPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbcSubtotal = new GridBagConstraints();
        gbcSubtotal.insets = new Insets(5, 10, 5, 10);
        Font subtotalFont = new Font("Arial", Font.BOLD, 14);

        gbcSubtotal.gridx = 0;
        gbcSubtotal.gridy = 0;
        JLabel subtotalLabel = new JLabel("Subtotal: ₱0.00");
        subtotalLabel.setFont(subtotalFont);
        subtotalPanel.add(subtotalLabel, gbcSubtotal);

        gbcSubtotal.gridx = 1;
        JLabel taxLabel = new JLabel("Tax: ₱0.00");
        taxLabel.setFont(subtotalFont);
        subtotalPanel.add(taxLabel, gbcSubtotal);

        gbcSubtotal.gridx = 2;
        JLabel totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(subtotalFont);
        subtotalPanel.add(totalLabel, gbcSubtotal);

        panel.add(subtotalPanel, gbc);

        // Rest of the components (Discount, Transport Cost, Purchase Tax, etc.)
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(labelFont);
        panel.add(discountLabel, gbc);

        gbc.gridx = 1;
        JTextField discountField = new JTextField(10);
        discountField.setEditable(false);
        panel.add(discountField, gbc);

        gbc.gridx = 2;
        JLabel transportCostLabel = new JLabel("Transport Cost:");
        transportCostLabel.setFont(labelFont);
        panel.add(transportCostLabel, gbc);

        gbc.gridx = 3;
        JTextField transportCostField = new JTextField(10);
        transportCostField.setEditable(false);
        panel.add(transportCostField, gbc);

        gbc.gridx = 4;
        JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
        purchaseTaxLabel.setFont(labelFont);
        panel.add(purchaseTaxLabel, gbc);

        gbc.gridx = 5;
        JTextField purchaseTaxField = new JTextField(10);
        purchaseTaxField.setEditable(false);
        panel.add(purchaseTaxField, gbc);

        // Purchase Total, Total Paid, Purchase Due - new row (3 columns)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel purchaseTotalLabel = new JLabel("Purchase Total:");
        purchaseTotalLabel.setFont(labelFont);
        panel.add(purchaseTotalLabel, gbc);

        gbc.gridx = 1;
        JTextField purchaseTotalField = new JTextField(10);
        purchaseTotalField.setEditable(false);
        panel.add(purchaseTotalField, gbc);

        gbc.gridx = 2;
        JLabel totalPaidLabel = new JLabel("Total Paid:");
        totalPaidLabel.setFont(labelFont);
        panel.add(totalPaidLabel, gbc);

        gbc.gridx = 3;
        JTextField totalPaidField = new JTextField(10);
        totalPaidField.setEditable(false);
        panel.add(totalPaidField, gbc);

        gbc.gridx = 4;
        JLabel purchaseDueLabel = new JLabel("Purchase Due:");
        purchaseDueLabel.setFont(labelFont);
        panel.add(purchaseDueLabel, gbc);

        gbc.gridx = 5;
        JTextField purchaseDueField = new JTextField(10);
        purchaseDueField.setEditable(false);
        panel.add(purchaseDueField, gbc);

        // Note - single row with span of 3 columns
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(labelFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 5;
        JTextArea noteArea = new JTextArea(3, 30);
        panel.add(new JScrollPane(noteArea), gbc);

        // Return Date and Status - final row (full width, two fields)
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel returnDateLabel = new JLabel("Return Date:");
        returnDateLabel.setFont(labelFont);
        panel.add(returnDateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JDatePickerImpl returnDatePicker = createDatePicker();
        panel.add(returnDatePicker, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        panel.add(statusCombo, gbc);

        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Return", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Handle form submission logic here
        }
    }

    // Method to create createdAt pickers with the current createdAt
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }    //GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Create a panel to hold the createdAt pickers for "From" and "To" dates
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));  // GridLayout with 2 rows, 2 columns

        // Create bold and larger font for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // 16 size and bold

        JLabel fromLabel = new JLabel("From Date:");
        fromLabel.setFont(labelFont);  // Set to bold and larger size

        JLabel toLabel = new JLabel("To Date:");
        toLabel.setFont(labelFont);  // Set to bold and larger size

        // Create the createdAt pickers
        JDatePickerImpl fromDatePicker = createDatePicker();  // Date picker for "From" createdAt
        JDatePickerImpl toDatePicker = createDatePicker();    // Date picker for "To" createdAt

        // Add components to the panel
        datePanel.add(fromLabel);
        datePanel.add(fromDatePicker);
        datePanel.add(toLabel);
        datePanel.add(toDatePicker);

        // Show a dialog with the createdAt pickers
        int result = JOptionPane.showConfirmDialog(null, datePanel, "Select Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected dates
            String fromDateStr = fromDatePicker.getJFormattedTextField().getText();
            String toDateStr = toDatePicker.getJFormattedTextField().getText();

            // Parse the dates into a Date object or LocalDate for comparison
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Adjust format if necessary
            LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
            LocalDate toDate = LocalDate.parse(toDateStr, formatter);

            // Now, filter the table rows based on the selected createdAt range
            filterTableByDateRange(fromDate, toDate);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private void filterTableByDateRange(LocalDate fromDate, LocalDate toDate) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                // Assuming the createdAt is in the 3rd column (index 2), change as per your table
                String dateStr = (String) entry.getValue(2);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate rowDate = LocalDate.parse(dateStr, formatter);

                    // Return true if the createdAt is within the selected range
                    return !rowDate.isBefore(fromDate) && !rowDate.isAfter(toDate);
                } catch (Exception e) {
                    // Skip rows with invalid dates
                    return false;
                }
            }
        });
    }
}

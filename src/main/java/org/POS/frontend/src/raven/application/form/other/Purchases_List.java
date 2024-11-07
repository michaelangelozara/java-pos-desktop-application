package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductTaxType;
import org.POS.backend.purchase.PurchaseService;
import org.POS.backend.purchased_product.AddPurchaseProductRequestDto;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Purchases_List extends javax.swing.JPanel {

    public Purchases_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = 0;
                gbc.gridy = 0;

                Font labelFont = new Font("Arial", Font.BOLD, 16);

                JComboBox<String> supplierComboBox = new JComboBox<>(new String[]{"Supplier 1", "Supplier 2", "Supplier 3"});
                JTextField purchaseNumberField = new JTextField(10);
                JComboBox<String> productComboBox = new JComboBox<>(new String[]{"Product 1", "Product 2", "Product 3"});

                JLabel supplierLabel = new JLabel("Supplier:");
                supplierLabel.setFont(labelFont);
                panel.add(supplierLabel, gbc);
                gbc.gridx++;
                panel.add(supplierComboBox, gbc);

                gbc.gridx++;
                JLabel purchaseNoLabel = new JLabel("Purchase #:");
                purchaseNoLabel.setFont(labelFont);
                panel.add(purchaseNoLabel, gbc);
                gbc.gridx++;
                panel.add(purchaseNumberField, gbc);

                gbc.gridx++;
                JLabel productLabel = new JLabel("Select Product:");
                productLabel.setFont(labelFont);
                panel.add(productLabel, gbc);
                gbc.gridx++;
                panel.add(productComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                String[] columnNames = {"#", "Code", "Name", "Purchased QTY", "Returned QTY", "Purchase Price", "Unit Cost", "Tax", "Total Price", "Total Return", "Action"};
                Object[][] data = {
                        {1, "C001", "Item A", 10, 0, 50.00, 45.00, 5.00, 500.00, 0.00, "Remove"},
                        {2, "C002", "Item B", 5, 1, 100.00, 90.00, 10.00, 500.00, 100.00, "Remove"},};

                // DefaultTableModel allows for easier row removal
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(model) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 10;  // Only make the "Action" column editable for the button
                    }
                };

                // Add Remove button in the last column
                TableColumn actionColumn = table.getColumnModel().getColumn(10);
                actionColumn.setCellRenderer(new ButtonRenderer());
                actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), table, model)); // Pass model to editor for row removal

                JScrollPane tableScrollPane = new JScrollPane(table);
                panel.add(tableScrollPane, gbc);

                gbc.gridwidth = 1;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.gridy++;

                JLabel poRefLabel = new JLabel("PO Reference:");
                poRefLabel.setFont(labelFont);
                panel.add(poRefLabel, gbc);
                gbc.gridx++;
                JTextField poReferenceField = new JTextField(10);
                panel.add(poReferenceField, gbc);

                gbc.gridx++;
                JLabel paymentTermsLabel = new JLabel("Payment Terms:");
                paymentTermsLabel.setFont(labelFont);
                panel.add(paymentTermsLabel, gbc);
                gbc.gridx++;
                JTextField paymentTermsField = new JTextField(10);
                panel.add(paymentTermsField, gbc);

                gbc.gridx++;
                JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
                purchaseTaxLabel.setFont(labelFont);
                panel.add(purchaseTaxLabel, gbc);
                gbc.gridx++;
                JTextField purchaseTaxField = new JTextField(10);
                panel.add(purchaseTaxField, gbc);

                gbc.gridx++;
                JLabel totalTaxLabel = new JLabel("Total Tax:");
                totalTaxLabel.setFont(labelFont);
                panel.add(totalTaxLabel, gbc);
                gbc.gridx++;
                JTextField totalTaxField = new JTextField(10);
                panel.add(totalTaxField, gbc);

                gbc.gridy++;
                gbc.gridx = 0;
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
                JLabel costReturnProductsLabel = new JLabel("Cost of Return Products:");
                costReturnProductsLabel.setFont(labelFont);
                panel.add(costReturnProductsLabel, gbc);
                gbc.gridx++;
                JTextField costReturnProductsField = new JTextField(10);
                panel.add(costReturnProductsField, gbc);

                gbc.gridx++;
                JLabel netTotalLabel = new JLabel("Net Total:");
                netTotalLabel.setFont(labelFont);
                panel.add(netTotalLabel, gbc);
                gbc.gridx++;
                JTextField netTotalField = new JTextField(10);
                panel.add(netTotalField, gbc);

                // Add Note Section
                gbc.gridy++;
                gbc.gridx = 0;
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(labelFont);
                panel.add(noteLabel, gbc);
                gbc.gridx = 1;
                gbc.gridwidth = 8; // Span across multiple columns
                JTextArea noteArea = new JTextArea(3, 50);
                panel.add(new JScrollPane(noteArea), gbc);

                // Add Date Pickers for Purchase Date and PO Date
                gbc.gridwidth = 1;
                gbc.gridy++;
                gbc.gridx = 0;
                JLabel purchaseDateLabel = new JLabel("Purchase Date:");
                purchaseDateLabel.setFont(labelFont);
                panel.add(purchaseDateLabel, gbc);

                gbc.gridx = 1;
                JDatePickerImpl purchaseDatePicker = createDatePicker(); // Create date picker for Purchase Date
                panel.add(purchaseDatePicker, gbc);

                gbc.gridx = 2;
                JLabel poDateLabel = new JLabel("PO Date:");
                poDateLabel.setFont(labelFont);
                panel.add(poDateLabel, gbc);

                gbc.gridx = 3;
                JDatePickerImpl poDatePicker = createDatePicker(); // Create date picker for PO Date
                panel.add(poDatePicker, gbc);

                gbc.gridx = 4;
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(labelFont);
                panel.add(statusLabel, gbc);

                gbc.gridx = 5;
                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
                panel.add(statusCombo, gbc);

                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Purchase", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Save changes to the table or other backend logic can go here.
                    // Example:
                    String purchaseDate = purchaseDatePicker.getJFormattedTextField().getText();
                    String poDate = poDatePicker.getJFormattedTextField().getText();
                    // Process the data as needed
                }
            }

            // Method to create date pickers with the current date
            private JDatePickerImpl createDatePicker() {
                UtilDateModel model = new UtilDateModel();
                // Set the current date
                LocalDate currentDate = LocalDate.now();
                model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
                model.setSelected(true);  // Automatically selects the current date

                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                return new JDatePickerImpl(datePanel, new DateLabelFormatter());
            }

            // Date label formatter (you may need to implement this class based on your needs)
            class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

                private String datePattern = "dd/MM/yyyy";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parseObject(text);
                }

                @Override
                public String valueToString(Object value) {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }
                    return "";
                }
            }

            // Custom ButtonEditor class for the "Remove" button functionality
            class ButtonEditor extends DefaultCellEditor {

                protected JButton button;
                private String label;
                private boolean isPushed;
                private JTable table;
                private DefaultTableModel tableModel;

                public ButtonEditor(JCheckBox checkBox, JTable table, DefaultTableModel model) {
                    super(checkBox);
                    this.table = table;
                    this.tableModel = model;
                    button = new JButton();
                    button.setOpaque(true);
                    button.addActionListener(e -> fireEditingStopped());
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    label = (value == null) ? "Remove" : value.toString();
                    button.setText(label);
                    isPushed = true;
                    return button;
                }

                @Override
                public Object getCellEditorValue() {
                    if (isPushed) {
                        // Remove the row when the button is clicked
                        int row = table.getSelectedRow();
                        if (row >= 0) {
                            tableModel.removeRow(row);
                        }
                    }
                    isPushed = false;
                    return label;
                }

                @Override
                public boolean stopCellEditing() {
                    isPushed = false;
                    return super.stopCellEditing();
                }

                @Override
                protected void fireEditingStopped() {
                    super.fireEditingStopped();
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
                Application.showForm(new Product_Details());

            }

        };
        table.getColumnModel().getColumn(11).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(11).setCellEditor(new TableActionCellEditor(event));
        loadPurchases();
    }

    private void loadPurchases() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        PurchaseService purchaseService = new PurchaseService();
        var purchases = purchaseService.getAllValidPurchases();
        for (int i = 0; i < purchases.size(); i++) {
            model.addRow(new Object[]{
                    String.valueOf(i + 1),
                    purchases.get(i).code(),
                    purchases.get(i).date(),
                    purchases.get(i).supplier().name(),
                    purchases.get(i).subtotal(),
                    purchases.get(i).transport(),
                    purchases.get(i).discount(),
                    purchases.get(i).netTotal(),
                    purchases.get(i).totalPaid(),
                    purchases.get(i).totalDue(),
                    purchases.get(i).status()
            });
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
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Purchases List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Purchases");

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
                        {null, null, null, null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "Purchase #", "Date", "Supplier", "Subtotal", "Transport", "Discount", "Net Total", "Total Paid", "Total Due", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(8).setResizable(false);
            table.getColumnModel().getColumn(9).setResizable(false);
            table.getColumnModel().getColumn(10).setResizable(false);
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
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1316, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(14, 14, 14))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                                                .addGap(0, 1201, Short.MAX_VALUE)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Define font for larger bold labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

        // Supplier and Select Products - same row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(supplierLabel, gbc);

//        new String[]{"Select Supplier", "Christian James Torres", "Michael Angelo Zara"}
        gbc.gridx = 1;
        JComboBox<String> supplierCombo = new JComboBox<>();
        panel.add(supplierCombo, gbc);
        supplierCombo.addItem("Select Supplier");

        Map<Integer, Integer> supplierMap = new HashMap<>();
        PersonService personService = new PersonService();
        var suppliers = personService.getAllValidPeopleByType(PersonType.SUPPLIER);
        for (int i = 0; i < suppliers.size(); i++) {
            supplierCombo.addItem(suppliers.get(i).name() + suppliers.get(i).id());
            supplierMap.put(i + 1, suppliers.get(i).id());
        }

        gbc.gridx = 2;
        JLabel productsLabel = new JLabel("Select Products:");
        productsLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(productsLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> productsCombo = new JComboBox<>();
        panel.add(productsCombo, gbc);

        ProductService productService = new ProductService();
        var products = productService.getAllValidProducts();
        Map<Integer, Integer> productMap = new HashMap<>();
        // Populate ComboBox with product names
        productsCombo.addItem("Select Product");

        for (int i = 0; i < products.size(); i++) {
            productsCombo.addItem(products.get(i).name());
            productMap.put(i + 1, products.get(i).id());
        }

        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Define a custom table model to make specific columns non-editable
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"#", "ID", "Code", "Name", "Quantity", "Purchase Price", "Selling Price", "Tax Value", "Tax Type", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only "Quantity" and "Purchase Price" columns are editable (indexes 3 and 4)
                return column == 4 || column == 5;
            }
        };

        JTable productsTable = new JTable(tableModel);
        productsTable.setFillsViewportHeight(true);

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Highlight editable cells
        productsTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField())); // Quantity as JTextField
        productsTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField())); // Purchase Price as JTextField
        productsTable.getColumnModel().getColumn(4).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Quantity
        productsTable.getColumnModel().getColumn(5).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Purchase Price

        productsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());

        // Add JScrollPane around the table
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(scrollPane, gbc);

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4 || column == 5) { // Check if it's the "Quantity" or "Purchase Price" column
                List<List<Object>> insertedRows = getAllRows(tableModel);
                Object newValue = tableModel.getValueAt(row, column);
                System.out.println("Cell updated in row " + row + ", column " + column + " with value: " + newValue);
                for (List<Object> r : insertedRows) {
                    System.out.println(r);
                }
                tableModel.setRowCount(0);

                List<List<Object>> updatedRows = new ArrayList<>();
                for (int i = 0; i < insertedRows.size(); i++) {
                    if ((row + 1) == (Integer) insertedRows.get(i).get(0)) {
                        List<Object> editRow = insertedRows.get(i);

                        // get the value
                        System.out.println(editRow.get(7));

                        // updated or compute the value

                        //save to the updatedRows (List)

                        continue;
                    }
                    updatedRows.add(insertedRows.get(i));
                }

                // update the tableModel using the data of updatedRows
//                for loop
            }
        });

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4 || column == 5) { // Check if it's the "Quantity" or "Purchase Price" column
                Object newValue = tableModel.getValueAt(row, column);
                System.out.println("Cell updated in row " + row + ", column " + column + " with value: " + newValue);
            }
        });

        // Add ActionListener to ComboBox
        productsCombo.addActionListener(e -> {
            String selectedProduct = (String) productsCombo.getSelectedItem();
            int productIndex = productsCombo.getSelectedIndex();
            int productId = productMap.get(productIndex);
            assert selectedProduct != null;
            if (!selectedProduct.equals("Select Product")) {
                for (ProductResponseDto product : products) {
                    if (product.id() == productId) {
                        // Add a new row to the table with the product's data
                        tableModel.addRow(new Object[]{
                                tableModel.getRowCount() + 1, // Row number
                                product.id(),
                                product.code(),
                                product.name(),
                                product.stock(),
                                product.purchasePrice(),
                                product.sellingPrice(),
                                product.taxType().equals(ProductTaxType.EXCLUSIVE) ? product.purchasePrice().multiply(BigDecimal.valueOf(0.12)) : product.sellingPrice().divide(BigDecimal.valueOf(1.12)).multiply(BigDecimal.valueOf(0.12)),
                                product.taxType().name(),
                                "Remove"
                        });
                        break;
                    }
                }
            }
        });

        // Add MouseListener to handle "Remove" button clicks in the table
        productsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = productsTable.columnAtPoint(e.getPoint());
                int row = productsTable.rowAtPoint(e.getPoint());

                if (column == 9) { // "Action" column index for "Remove" button
                    tableModel.removeRow(row);
                }
            }
        });


        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Table data and columns
        String[] columnNames = {"#", "Code", "Name", "Quantity", "Purchase Price", "Unit Cost", "Tax", "Subtotal", "Action"};
        Object[][] data = {}; // Initialize with empty data

        // Increment row for the rest of the fields
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        summaryPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbcSummary = new GridBagConstraints();
        gbcSummary.insets = new Insets(5, 10, 5, 10);

        Font summaryFont = new Font("Arial", Font.BOLD, 22); // Larger font size for better visibility

        gbcSummary.gridx = 0;
        gbcSummary.gridy = 0;
        gbcSummary.anchor = GridBagConstraints.EAST;

        JLabel subtotalLabel = new JLabel("Subtotal");
        subtotalLabel.setFont(summaryFont);
        subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(subtotalLabel, gbcSummary);

        gbcSummary.gridx = 1;
        JLabel taxLabel = new JLabel("Tax: ₱456.00");
        taxLabel.setFont(summaryFont);
        taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(taxLabel, gbcSummary);

        gbcSummary.gridx = 2;
        JLabel totalLabel = new JLabel("Total: ₱789.00");
        totalLabel.setFont(summaryFont);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(totalLabel, gbcSummary);

        gbc.gridx = 0;
        gbc.gridy = 2; // Position summary panel right below the table
        gbc.gridwidth = 8;
        panel.add(summaryPanel, gbc);


        // PO Reference, Payment Terms, Purchase Tax, Total Tax - same row
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel poReferenceLabel = new JLabel("PO Reference:");
        poReferenceLabel.setFont(labelFont);
        panel.add(poReferenceLabel, gbc);

        gbc.gridx = 1;
        JTextField poReferenceField = new JTextField(10);
        panel.add(poReferenceField, gbc);

        gbc.gridx = 2;
        JLabel paymentTermsLabel = new JLabel("Payment Terms:");
        paymentTermsLabel.setFont(labelFont);
        panel.add(paymentTermsLabel, gbc);

        gbc.gridx = 3;
        JTextField paymentTermsField = new JTextField(10);
        panel.add(paymentTermsField, gbc);

        gbc.gridx = 4;
        JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
        purchaseTaxLabel.setFont(labelFont);
        panel.add(purchaseTaxLabel, gbc);

        gbc.gridx = 5;
        JComboBox<String> purchaseTaxCombo = new JComboBox<>(new String[]{"VAT@12%"});
        panel.add(purchaseTaxCombo, gbc);

        gbc.gridx = 6;
        JLabel totalTaxLabel = new JLabel("Total Tax:");
        totalTaxLabel.setFont(labelFont);
        panel.add(totalTaxLabel, gbc);

        gbc.gridx = 7;
        JTextField totalTaxField = new JTextField(10);
        panel.add(totalTaxField, gbc);
        totalTaxField.setEnabled(false);

        // New row for Discount and Transport Cost
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(labelFont);
        panel.add(discountLabel, gbc);

        gbc.gridx = 1;
        JTextField discountField = new JTextField(10);
        panel.add(discountField, gbc);

        gbc.gridx = 2;
        JLabel transportCostLabel = new JLabel("Transport Cost:");
        transportCostLabel.setFont(labelFont);
        panel.add(transportCostLabel, gbc);

        gbc.gridx = 3;
        JTextField transportCostField = new JTextField(10);
        panel.add(transportCostField, gbc);

        gbc.gridy = 5; // Position it right below the Discount and Transport Cost row
        gbc.gridx = 0;

        JLabel accountLabel = new JLabel("Account:");
        accountLabel.setFont(labelFont);
        panel.add(accountLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> accountCombo = new JComboBox<>(new String[]{"Select Account", "Onhand Cash", "BDO", "Landbank"});
        panel.add(accountCombo, gbc);

        gbc.gridx = 2;
        JLabel chequeNoLabel = new JLabel("Cheque No:");
        chequeNoLabel.setFont(labelFont);
        panel.add(chequeNoLabel, gbc);

        gbc.gridx = 3;
        JTextField chequeNoField = new JTextField(10);
        panel.add(chequeNoField, gbc);

        gbc.gridx = 4;
        JLabel receiptNoLabel = new JLabel("Receipt No:");
        receiptNoLabel.setFont(labelFont);
        panel.add(receiptNoLabel, gbc);

        gbc.gridx = 5;
        JTextField receiptNoField = new JTextField(10);
        panel.add(receiptNoField, gbc);

        // Shift remaining rows down by one row
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(labelFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 7;
        JTextArea noteArea = new JTextArea(3, 50);
        panel.add(new JScrollPane(noteArea), gbc);

        // Adjusting other elements to fit below the new Discount and Transport Cost row
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel purchaseDateLabel = new JLabel("Purchase Date:");
        purchaseDateLabel.setFont(labelFont);
        panel.add(purchaseDateLabel, gbc);

        gbc.gridx = 1;
        JDatePickerImpl purchaseDatePicker = createDatePicker();
        panel.add(purchaseDatePicker, gbc);

        gbc.gridx = 2;
        JLabel poDateLabel = new JLabel("PO Date:");
        poDateLabel.setFont(labelFont);
        panel.add(poDateLabel, gbc);

        gbc.gridx = 3;
        JDatePickerImpl poDatePicker = createDatePicker();
        panel.add(poDatePicker, gbc);

        gbc.gridx = 4;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 5;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        panel.add(statusCombo, gbc);

        // Shift remaining elements down one row to make space for the new panel
        gbc.gridy++;

        // New Panel for Total Paid and Net Total
        JPanel totalPanel = new JPanel(new GridBagLayout());
        totalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "TOTAL AMOUNT", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints totalGbc = new GridBagConstraints();
        totalGbc.insets = new Insets(5, 5, 5, 5);

        // Total Paid Label and Text Field
        totalGbc.gridx = 0;
        totalGbc.gridy = 0;
        totalGbc.gridwidth = 1;
        JLabel totalPaidLabel = new JLabel("Total Paid:");
        totalPaidLabel.setFont(labelFont);
        totalPanel.add(totalPaidLabel, totalGbc);

        totalGbc.gridx = 1;
        totalGbc.gridwidth = 2;
        JTextField totalPaidField = new JTextField(10); // Regular text field for Total Paid
        totalPanel.add(totalPaidField, totalGbc);

        // Add gap between Total Paid and Net Total
        totalGbc.gridx = 3;
        totalGbc.gridwidth = 1;
        totalPanel.add(Box.createHorizontalStrut(20), totalGbc); // Adds horizontal gap

        // Net Total Label and Display Label
        totalGbc.gridx = 4;
        JLabel netTotalLabel = new JLabel("Net Total:");
        netTotalLabel.setFont(labelFont);
        totalPanel.add(netTotalLabel, totalGbc);

        totalGbc.gridx = 5;
        JLabel netTotalDisplay = new JLabel("0.00"); // Placeholder for Net Total display
        netTotalDisplay.setFont(new Font("Arial", Font.BOLD, 24)); // Set to larger, bold font
        totalPanel.add(netTotalDisplay, totalGbc);

        // Add the new Total Panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 8; // Span the entire row for alignment
        panel.add(totalPanel, gbc);

        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<List<Object>> insertedRows = getAllRows(tableModel);

            for (List<Object> row : insertedRows) {
                System.out.println(row);
            }

            // Handle form submission logic here
            String supplier = (String) supplierCombo.getSelectedItem();
            String product = (String) productsCombo.getSelectedItem();
            String poReference = poReferenceField.getText();
            String paymentTerms = paymentTermsField.getText();
            String purchaseTax = (String) purchaseTaxCombo.getSelectedItem();
            String totalTax = totalTaxField.getText();
            String note = noteArea.getText();
            String purchaseDate = purchaseDatePicker.getJFormattedTextField().getText();
            String poDate = poDatePicker.getJFormattedTextField().getText();
            String status = (String) statusCombo.getSelectedItem();

            // Process the collected data...

        }
    }

    private List<List<Object>> getAllRows(DefaultTableModel model) {
        List<List<Object>> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            rows.add(row);
        }
        return rows;
    }

    class EditableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setForeground(Color.BLUE); // Make text blue for editable cells
            setBorder(BorderFactory.createDashedBorder(Color.GRAY)); // Dashed border for editable cells
            setHorizontalAlignment(SwingConstants.CENTER);
            return component;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            return this;
        }
    }

    // Method to create date pickers with the current date
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);  // Automatically selects the current date

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Create a panel to hold the date pickers for "From" and "To" dates
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));  // GridLayout with 2 rows, 2 columns

        // Create bold and larger font for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // 16 size and bold

        JLabel fromLabel = new JLabel("From Date:");
        fromLabel.setFont(labelFont);  // Set to bold and larger size

        JLabel toLabel = new JLabel("To Date:");
        toLabel.setFont(labelFont);  // Set to bold and larger size

        // Create the date pickers
        JDatePickerImpl fromDatePicker = createDatePicker();  // Date picker for "From" date
        JDatePickerImpl toDatePicker = createDatePicker();    // Date picker for "To" date

        // Add components to the panel
        datePanel.add(fromLabel);
        datePanel.add(fromDatePicker);
        datePanel.add(toLabel);
        datePanel.add(toDatePicker);

        // Show a dialog with the date pickers
        int result = JOptionPane.showConfirmDialog(null, datePanel, "Select Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected dates
            String fromDateStr = fromDatePicker.getJFormattedTextField().getText();
            String toDateStr = toDatePicker.getJFormattedTextField().getText();

            // Parse the dates into a Date object or LocalDate for comparison
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Adjust format if necessary
            LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
            LocalDate toDate = LocalDate.parse(toDateStr, formatter);

            // Now, filter the table rows based on the selected date range
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
                // Assuming the date is in the 3rd column (index 2), change as per your table
                String dateStr = (String) entry.getValue(2);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate rowDate = LocalDate.parse(dateStr, formatter);

                    // Return true if the date is within the selected range
                    return !rowDate.isBefore(fromDate) && !rowDate.isAfter(toDate);
                } catch (Exception e) {
                    // Skip rows with invalid dates
                    return false;
                }
            }
        });
    }
}
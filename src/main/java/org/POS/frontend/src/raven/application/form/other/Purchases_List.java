
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.POS.frontend.src.raven.application.form.other.Product_Details;


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
        {2, "C002", "Item B", 5, 1, 100.00, 90.00, 10.00, 500.00, 100.00, "Remove"},
    };

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
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Purchase #", "Date", "Supplier", "Subtotal", "Transport", "Discount", "Net Total", "Total Paid", "Total Due", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true
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

    gbc.gridx = 1;
    JComboBox<String> supplierCombo = new JComboBox<>(new String[]{"Select Supplier", "Yvonne Melendez", "Supplier 2"});
    panel.add(supplierCombo, gbc);

    gbc.gridx = 2;
    JLabel productsLabel = new JLabel("Select Products:");
    productsLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(productsLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> productsCombo = new JComboBox<>(new String[]{"Search Products", "Product 1", "Product 2"});
    panel.add(productsCombo, gbc);

    // PO Reference, Payment Terms, Purchase Tax, Total Tax - same row
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel poReferenceLabel = new JLabel("PO Reference:");
    poReferenceLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(poReferenceLabel, gbc);

    gbc.gridx = 1;
    JTextField poReferenceField = new JTextField(10);
    panel.add(poReferenceField, gbc);

    gbc.gridx = 2;
    JLabel paymentTermsLabel = new JLabel("Payment Terms:");
    paymentTermsLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(paymentTermsLabel, gbc);

    gbc.gridx = 3;
    JTextField paymentTermsField = new JTextField(10);
    panel.add(paymentTermsField, gbc);

    gbc.gridx = 4;
    JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
    purchaseTaxLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(purchaseTaxLabel, gbc);

    gbc.gridx = 5;
    JComboBox<String> purchaseTaxCombo = new JComboBox<>(new String[]{"VAT@0", "VAT@10", "VAT@20"});
    panel.add(purchaseTaxCombo, gbc);

    gbc.gridx = 6;
    JLabel totalTaxLabel = new JLabel("Total Tax:");
    totalTaxLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(totalTaxLabel, gbc);

    gbc.gridx = 7;
    JTextField totalTaxField = new JTextField(10);
    panel.add(totalTaxField, gbc);

    // Note - full row
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(noteLabel, gbc);

    gbc.gridx = 1;
    gbc.gridwidth = 7;
    JTextArea noteArea = new JTextArea(3, 50);
    panel.add(new JScrollPane(noteArea), gbc);

    // Purchase Date, PO Date, Status - same row
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    JLabel purchaseDateLabel = new JLabel("Purchase Date:");
    purchaseDateLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(purchaseDateLabel, gbc);

    gbc.gridx = 1;
    JDatePickerImpl purchaseDatePicker = createDatePicker();  // Create date picker with current date as placeholder
    panel.add(purchaseDatePicker, gbc);

    gbc.gridx = 2;
    JLabel poDateLabel = new JLabel("PO Date:");
    poDateLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(poDateLabel, gbc);

    gbc.gridx = 3;
    JDatePickerImpl poDatePicker = createDatePicker();  // Create date picker with current date as placeholder
    panel.add(poDatePicker, gbc);

    gbc.gridx = 4;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(labelFont);  // Set label to bold and large
    panel.add(statusLabel, gbc);

    gbc.gridx = 5;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    panel.add(statusCombo, gbc);

    // Show the panel in a dialog
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
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

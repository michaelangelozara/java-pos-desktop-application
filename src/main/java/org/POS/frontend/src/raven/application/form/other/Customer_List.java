
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.POS.frontend.src.raven.application.form.other.Customer_Details;


public class Customer_List extends javax.swing.JPanel {

    public Customer_List() {
        initComponents();
           DefaultTableModel model = (DefaultTableModel) table.getModel();
    Object[][] sampleData = {
        {1, "client1.jpg", "C001", "Christian James Torres", "123-456-7890", "Christian@example.com", "Acme Corp", "Active", "Action"},
        {2, "client2.jpg", "C002", "Michael Angelo Zara", "987-654-3210", "Michael@example.com", "Global Tech", "Inactive", "Action"},
        {3, "client3.jpg", "C003", "Andy Suarez", "555-123-4567", "Andy@example.com", "Johnson Enterprises", "Active", "Action"}
    };
    
    // Clear the table if needed and populate with the sample data
    model.setRowCount(0); // clear the table
    for (Object[] row : sampleData) {
        model.addRow(row);
    }
          TableActionEvent event = new TableActionEvent() {
          
            @Override
          public void onEdit(int row) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    Font labelFont = new Font("Arial", Font.BOLD, 16);  // Larger font for labels
    Dimension fieldSize = new Dimension(250, 30);  // Uniform field size

    // Get selected row data (assuming your model's getValueAt method)
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String name = (String) model.getValueAt(row, 3);
    String email = (String) model.getValueAt(row, 5);
    String contactNumber = (String) model.getValueAt(row, 4);
    String companyName = (String) model.getValueAt(row, 6);
    String taxRegistrationNumber = (String) model.getValueAt(row, 7); // Update as needed based on your model
    String address = ""; // Add logic to get address if it's in the model
    String status = (String) model.getValueAt(row, 8); // Update as needed based on your model

    // 1st Row: Name, Email, Contact Number
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel nameLabel = new JLabel("Name *:");
    nameLabel.setFont(labelFont);
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    JTextField nameField = new JTextField(name);
    nameField.setPreferredSize(fieldSize);
    panel.add(nameField, gbc);

    gbc.gridx = 2;
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(labelFont);
    panel.add(emailLabel, gbc);

    gbc.gridx = 3;
    JTextField emailField = new JTextField(email);
    emailField.setPreferredSize(fieldSize);
    panel.add(emailField, gbc);

    gbc.gridx = 4;
    JLabel contactNumberLabel = new JLabel("Contact Number:");
    contactNumberLabel.setFont(labelFont);
    panel.add(contactNumberLabel, gbc);

    gbc.gridx = 5;
    JTextField contactNumberField = new JTextField(contactNumber);
    contactNumberField.setPreferredSize(fieldSize);
    panel.add(contactNumberField, gbc);

    // 2nd Row: Company Name, Tax Registration Number, Address
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel companyNameLabel = new JLabel("Company Name:");
    companyNameLabel.setFont(labelFont);
    panel.add(companyNameLabel, gbc);

    gbc.gridx = 1;
    JTextField companyNameField = new JTextField(companyName);
    companyNameField.setPreferredSize(fieldSize);
    panel.add(companyNameField, gbc);

    gbc.gridx = 2;
    JLabel taxLabel = new JLabel("Tax Registration Number:");
    taxLabel.setFont(labelFont);
    panel.add(taxLabel, gbc);

    gbc.gridx = 3;
    JTextField taxField = new JTextField(taxRegistrationNumber);
    taxField.setPreferredSize(fieldSize);
    panel.add(taxField, gbc);

    gbc.gridx = 4;
    JLabel addressLabel = new JLabel("Address:");
    addressLabel.setFont(labelFont);
    panel.add(addressLabel, gbc);

    gbc.gridx = 5;
    JTextField addressField = new JTextField(address);  // Retrieve address as needed
    addressField.setPreferredSize(fieldSize);
    panel.add(addressField, gbc);

    // 3rd Row: Image and Status
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(labelFont);
    panel.add(imageLabel, gbc);

    gbc.gridx = 1;
    JButton imageButton = new JButton("Upload Image");
    imageButton.setPreferredSize(fieldSize);

    // File Chooser for Image Upload
    imageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Handle file upload logic here
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    });
    panel.add(imageButton, gbc);

    gbc.gridx = 2;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(labelFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusCombo.setSelectedItem(status); // Set the current status
    statusCombo.setPreferredSize(fieldSize);
    panel.add(statusCombo, gbc);

    // Display the panel
    int result = JOptionPane.showConfirmDialog(null, panel, "Edit Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        // Submission logic
        // Update model with new values
        model.setValueAt(nameField.getText(), row, 3);
        model.setValueAt(emailField.getText(), row, 5);
        model.setValueAt(contactNumberField.getText(), row, 4);
        model.setValueAt(companyNameField.getText(), row, 6);
        model.setValueAt(taxField.getText(), row, 7);
        model.setValueAt(addressField.getText(), row, 8);
        model.setValueAt(statusCombo.getSelectedItem(), row, 9); // Update as needed
        // Handle the image upload if needed
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
          Application.showForm(new Customer_Details());

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
        jLabel1.setText("Customers");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Customers");

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
                "#", "Image", "Client ID", "Name", "Contact Number	", "Email", "Company Name", "Status", "Action"
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    Font labelFont = new Font("Arial", Font.BOLD, 16);  // Larger font for labels
    Dimension fieldSize = new Dimension(250, 30);  // Uniform field size

    // 1st Row: Name, Email, Contact Number
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel nameLabel = new JLabel("Name *:");
    nameLabel.setFont(labelFont);
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    JTextField nameField = new JTextField(20);
    nameField.setPreferredSize(fieldSize);
    panel.add(nameField, gbc);

    gbc.gridx = 2;
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(labelFont);
    panel.add(emailLabel, gbc);

    gbc.gridx = 3;
    JTextField emailField = new JTextField(20);
    emailField.setPreferredSize(fieldSize);
    panel.add(emailField, gbc);

    gbc.gridx = 4;
    JLabel contactNumberLabel = new JLabel("Contact Number:");
    contactNumberLabel.setFont(labelFont);
    panel.add(contactNumberLabel, gbc);

    gbc.gridx = 5;
    JTextField contactNumberField = new JTextField(20);
    contactNumberField.setPreferredSize(fieldSize);  // Uniform size with other fields
    panel.add(contactNumberField, gbc);

    // 2nd Row: Company Name, Tax Registration Number, Address
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel companyNameLabel = new JLabel("Company Name:");
    companyNameLabel.setFont(labelFont);
    panel.add(companyNameLabel, gbc);

    gbc.gridx = 1;
    JTextField companyNameField = new JTextField(20);
    companyNameField.setPreferredSize(fieldSize);
    panel.add(companyNameField, gbc);

    gbc.gridx = 2;
    JLabel taxLabel = new JLabel("Tax Registration Number:");
    taxLabel.setFont(labelFont);
    panel.add(taxLabel, gbc);

    gbc.gridx = 3;
    JTextField taxField = new JTextField(20);
    taxField.setPreferredSize(fieldSize);
    panel.add(taxField, gbc);

    gbc.gridx = 4;
    JLabel addressLabel = new JLabel("Address:");
    addressLabel.setFont(labelFont);
    panel.add(addressLabel, gbc);

    gbc.gridx = 5;
    JTextField addressField = new JTextField(20);  // Uniform size with other fields
    addressField.setPreferredSize(fieldSize);
    panel.add(addressField, gbc);

    // 3rd Row: Image and Status
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(labelFont);
    panel.add(imageLabel, gbc);

    gbc.gridx = 1;
    JButton imageButton = new JButton("Upload Image");
    imageButton.setPreferredSize(fieldSize);

    // File Chooser for Image Upload
    imageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Handle file upload logic here
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    });
    panel.add(imageButton, gbc);

    gbc.gridx = 2;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(labelFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusCombo.setPreferredSize(fieldSize);
    panel.add(statusCombo, gbc);

    // Display the panel
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        // Submission logic
        String name = nameField.getText();
        String email = emailField.getText();
        String contactNumber = contactNumberField.getText();
        String companyName = companyNameField.getText();
        String taxRegistrationNumber = taxField.getText();
        String address = addressField.getText();
        String status = (String) statusCombo.getSelectedItem();
        
        // Handle the image upload if needed
        // selectedFile would be processed here after image upload
    }
}

// Method to create date pickers with the current date
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

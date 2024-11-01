
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;

public class Expenses_List extends javax.swing.JPanel {

    public Expenses_List() {
        initComponents();
          TableActionEvent event = new TableActionEvent() {
          
@Override
public void onEdit(int row) {
    // Get the data from the selected row (adjust the indexes as necessary)
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String expenseReason = (String) model.getValueAt(row, 1);
    String categoryName = (String) model.getValueAt(row, 2);
    String subCategory = (String) model.getValueAt(row, 3);
    String amount = (String) model.getValueAt(row, 4);
    String account = (String) model.getValueAt(row, 5);
    String status = (String) model.getValueAt(row, 7);

    // Create the panel and set the layout to GridBagLayout
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Add padding
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    // Fonts
    Font boldFont = new Font("Arial", Font.BOLD, 14);
    Font regularFont = new Font("Arial", Font.PLAIN, 14);

    // Expense Reason *
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel expenseReasonLabel = new JLabel("Expense Reason *");
    expenseReasonLabel.setFont(boldFont);
    panel.add(expenseReasonLabel, gbc);

    gbc.gridx = 1;
    JTextField expenseReasonField = new JTextField(15);
    expenseReasonField.setFont(regularFont);
    expenseReasonField.setText(expenseReason); // Prefill with existing value
    panel.add(expenseReasonField, gbc);

    // Category Name *
    gbc.gridx = 2;
    JLabel categoryNameLabel = new JLabel("Category Name *");
    categoryNameLabel.setFont(boldFont);
    panel.add(categoryNameLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Select a category", "Category 1", "Category 2"});
    categoryCombo.setFont(regularFont);
    categoryCombo.setSelectedItem(categoryName); // Prefill with existing value
    panel.add(categoryCombo, gbc);

    // Sub Category
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel subCategoryLabel = new JLabel("Sub Category");
    subCategoryLabel.setFont(boldFont);
    panel.add(subCategoryLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> subCategoryCombo = new JComboBox<>(new String[]{"Select a subcategory", "SubCategory 1", "SubCategory 2"});
    subCategoryCombo.setFont(regularFont);
    subCategoryCombo.setSelectedItem(subCategory); // Prefill with existing value
    panel.add(subCategoryCombo, gbc);

    // Amount *
    gbc.gridx = 2;
    JLabel amountLabel = new JLabel("Amount *");
    amountLabel.setFont(boldFont);
    panel.add(amountLabel, gbc);

    gbc.gridx = 3;
    JTextField amountField = new JTextField(15);
    amountField.setFont(regularFont);
    amountField.setText(amount); // Prefill with existing value
    panel.add(amountField, gbc);

    // Account *
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel accountLabel = new JLabel("Account *");
    accountLabel.setFont(boldFont);
    panel.add(accountLabel, gbc);

    gbc.gridx = 1;
    JTextField accountField = new JTextField(15);
    accountField.setFont(regularFont);
    accountField.setText(account); // Prefill with existing value
    panel.add(accountField, gbc);

    // Date (Current Date, Non-Editable)
    gbc.gridx = 2;
    gbc.gridy = 2;
    JLabel dateLabel = new JLabel("Date");
    dateLabel.setFont(boldFont);
    panel.add(dateLabel, gbc);

    gbc.gridx = 3;

    // Get the current date
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    JTextField dateField = new JTextField(currentDate.format(formatter), 15);
    dateField.setFont(regularFont);
    dateField.setEditable(false); // Make it non-editable
    panel.add(dateField, gbc);

    // Status
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusCombo.setFont(regularFont);
    statusCombo.setSelectedItem(status); // Prefill with existing value
    panel.add(statusCombo, gbc);

    // Display the form in a dialog
    int result = JOptionPane.showConfirmDialog(null, panel, "Edit Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        model.setValueAt(expenseReasonField.getText(), row, 1);
        model.setValueAt(categoryCombo.getSelectedItem(), row, 2);
        model.setValueAt(subCategoryCombo.getSelectedItem(), row, 3);
        model.setValueAt(amountField.getText(), row, 4);
        model.setValueAt(accountField.getText(), row, 5);
        model.setValueAt(dateField.getText(), row, 6); // This will always use the current date
        model.setValueAt(statusCombo.getSelectedItem(), row, 7);

        JOptionPane.showMessageDialog(null, "Expense updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
    // Get the data from the selected row (adjust the indexes as necessary)
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String expenseReason = (String) model.getValueAt(row, 1);
    String categoryName = (String) model.getValueAt(row, 2);
    String subCategory = (String) model.getValueAt(row, 3);
    String amount = (String) model.getValueAt(row, 4);
    String account = (String) model.getValueAt(row, 5);
    String date = (String) model.getValueAt(row, 6);
    String status = (String) model.getValueAt(row, 7);

    // Create the panel and set the layout to GridBagLayout
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Add padding
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    // Fonts
    Font boldFont = new Font("Arial", Font.BOLD, 14);
    Font regularFont = new Font("Arial", Font.PLAIN, 14);

    // Expense Reason
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel expenseReasonLabel = new JLabel("Expense Reason");
    expenseReasonLabel.setFont(boldFont);
    panel.add(expenseReasonLabel, gbc);

    gbc.gridx = 1;
    JTextField expenseReasonField = new JTextField(15);
    expenseReasonField.setFont(regularFont);
    expenseReasonField.setText(expenseReason); // Prefill with existing value
    expenseReasonField.setEditable(false);     // Make it non-editable
    panel.add(expenseReasonField, gbc);

    // Category Name
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel categoryNameLabel = new JLabel("Category Name");
    categoryNameLabel.setFont(boldFont);
    panel.add(categoryNameLabel, gbc);

    gbc.gridx = 1;
    JTextField categoryField = new JTextField(15);
    categoryField.setFont(regularFont);
    categoryField.setText(categoryName); // Prefill with existing value
    categoryField.setEditable(false);    // Make it non-editable
    panel.add(categoryField, gbc);

    // Sub Category
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel subCategoryLabel = new JLabel("Sub Category");
    subCategoryLabel.setFont(boldFont);
    panel.add(subCategoryLabel, gbc);

    gbc.gridx = 1;
    JTextField subCategoryField = new JTextField(15);
    subCategoryField.setFont(regularFont);
    subCategoryField.setText(subCategory); // Prefill with existing value
    subCategoryField.setEditable(false);   // Make it non-editable
    panel.add(subCategoryField, gbc);

    // Amount
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel amountLabel = new JLabel("Amount");
    amountLabel.setFont(boldFont);
    panel.add(amountLabel, gbc);

    gbc.gridx = 1;
    JTextField amountField = new JTextField(15);
    amountField.setFont(regularFont);
    amountField.setText(amount); // Prefill with existing value
    amountField.setEditable(false);  // Make it non-editable
    panel.add(amountField, gbc);

    // Account
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel accountLabel = new JLabel("Account");
    accountLabel.setFont(boldFont);
    panel.add(accountLabel, gbc);

    gbc.gridx = 1;
    JTextField accountField = new JTextField(15);
    accountField.setFont(regularFont);
    accountField.setText(account); // Prefill with existing value
    accountField.setEditable(false); // Make it non-editable
    panel.add(accountField, gbc);

    // Date
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel dateLabel = new JLabel("Date");
    dateLabel.setFont(boldFont);
    panel.add(dateLabel, gbc);

    gbc.gridx = 1;
    JTextField dateField = new JTextField(15);
    dateField.setFont(regularFont);
    dateField.setText(date);  // Prefill with existing value
    dateField.setEditable(false); // Make it non-editable
    panel.add(dateField, gbc);

    // Status
    gbc.gridx = 0;
    gbc.gridy = 6;
    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JTextField statusField = new JTextField(15);
    statusField.setFont(regularFont);
    statusField.setText(status);  // Prefill with existing value
    statusField.setEditable(false); // Make it non-editable
    panel.add(statusField, gbc);

    // Display the form in a dialog
    JOptionPane.showMessageDialog(null, panel, "View Expense", JOptionPane.PLAIN_MESSAGE);
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
        jLabel1.setText("Expenses List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Expenses");

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

            },
            new String [] {
                "#", "Expense Reason", "Category", "Sub Category", "Amount	", "Account	", "Date	", "Status", "Action"
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
        }

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
                .addContainerGap(1215, Short.MAX_VALUE))
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

    // Expense Reason *
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel expenseReasonLabel = new JLabel("Expense Reason *");
    expenseReasonLabel.setFont(boldFont);
    panel.add(expenseReasonLabel, gbc);

    gbc.gridx = 1;
    JTextField expenseReasonField = new JTextField(15);
    expenseReasonField.setFont(regularFont);
    panel.add(expenseReasonField, gbc);

    // Category Name *
    gbc.gridx = 2;
    JLabel categoryNameLabel = new JLabel("Category Name *");
    categoryNameLabel.setFont(boldFont);
    panel.add(categoryNameLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Select a category", "Category 1", "Category 2"});
    categoryCombo.setFont(regularFont);
    panel.add(categoryCombo, gbc);

    // Sub Category *
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel subCategoryLabel = new JLabel("Sub Category *");
    subCategoryLabel.setFont(boldFont);
    panel.add(subCategoryLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> subCategoryCombo = new JComboBox<>(new String[]{"Select a sub-category", "Sub Category 1", "Sub Category 2"});
    subCategoryCombo.setFont(regularFont);
    panel.add(subCategoryCombo, gbc);

    // Account *
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel accountLabel = new JLabel("Account *");
    accountLabel.setFont(boldFont);
    panel.add(accountLabel, gbc);

    gbc.gridx = 1;
    JTextField accountField = new JTextField(15);
    accountField.setFont(regularFont);
    panel.add(accountField, gbc);

    // Amount *
    gbc.gridx = 2;
    JLabel amountLabel = new JLabel("Amount *");
    amountLabel.setFont(boldFont);
    panel.add(amountLabel, gbc);

    gbc.gridx = 3;
    JTextField amountField = new JTextField(15);
    amountField.setFont(regularFont);
    panel.add(amountField, gbc);

    // Cheque No
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel chequeNoLabel = new JLabel("Cheque No");
    chequeNoLabel.setFont(boldFont);
    panel.add(chequeNoLabel, gbc);

    gbc.gridx = 1;
    JTextField chequeNoField = new JTextField(15);
    chequeNoField.setFont(regularFont);
    panel.add(chequeNoField, gbc);

    // Voucher No
    gbc.gridx = 2;
    JLabel voucherNoLabel = new JLabel("Voucher No");
    voucherNoLabel.setFont(boldFont);
    panel.add(voucherNoLabel, gbc);

    gbc.gridx = 3;
    JTextField voucherNoField = new JTextField(15);
    voucherNoField.setFont(regularFont);
    panel.add(voucherNoField, gbc);

    // Note
    gbc.gridx = 0;
    gbc.gridy = 4;
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

    // Date (Current Date, Non-Editable)
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel dateLabel = new JLabel("Date");
    dateLabel.setFont(boldFont);
    panel.add(dateLabel, gbc);

    gbc.gridx = 1;
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    JTextField dateField = new JTextField(currentDate.format(formatter), 15);
    dateField.setFont(regularFont);
    dateField.setEditable(false);  // Make the date field non-editable
    panel.add(dateField, gbc);

    // Status
    gbc.gridx = 2;
    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(boldFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 3;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusCombo.setFont(regularFont);
    panel.add(statusCombo, gbc);

    // Image - Modify to use JFileChooser for file upload
    gbc.gridx = 0;
    gbc.gridy = 6;
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
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // If the user clicks OK, process the input
    if (result == JOptionPane.OK_OPTION) {
        String expenseReason = expenseReasonField.getText();
        String amount = amountField.getText();
        String account = accountField.getText();

        // Validate the required fields
        if (expenseReason.isEmpty() || amount.isEmpty() || account.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Expense Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

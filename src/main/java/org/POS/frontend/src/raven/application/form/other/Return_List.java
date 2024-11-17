
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.global_variable.ProhibitedFunction;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.return_product.ReturnProductDAO;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class Return_List extends javax.swing.JPanel {

    public Return_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
//                JPanel panel = new JPanel(new GridBagLayout());
//                GridBagConstraints gbc = new GridBagConstraints();
//                gbc.fill = GridBagConstraints.HORIZONTAL;
//                gbc.insets = new Insets(5, 5, 5, 5);
//                gbc.gridx = 0;
//                gbc.gridy = 0;
//
//                Font labelFont = new Font("Arial", Font.BOLD, 16);
//
//                // First row: Purchase No, Purchase Return No, Supplier
//                JLabel purchaseNoLabel = new JLabel("Purchase No:");
//                purchaseNoLabel.setFont(labelFont);
//                panel.add(purchaseNoLabel, gbc);
//                gbc.gridx++;
//                JTextField purchaseNoField = new JTextField(10);
//                panel.add(purchaseNoField, gbc);
//
//                gbc.gridx++;
//                JLabel purchaseReturnNoLabel = new JLabel("Purchase Return No:");
//                purchaseReturnNoLabel.setFont(labelFont);
//                panel.add(purchaseReturnNoLabel, gbc);
//                gbc.gridx++;
//                JTextField purchaseReturnNoField = new JTextField(10);
//                panel.add(purchaseReturnNoField, gbc);
//
//                gbc.gridx++;
//                JLabel supplierLabel = new JLabel("Supplier:");
//                supplierLabel.setFont(labelFont);
//                panel.add(supplierLabel, gbc);
//                gbc.gridx++;
//                JTextField supplierField = new JTextField(10);
//                panel.add(supplierField, gbc);
//
//                // Second row: Return Reason
//                gbc.gridx = 0;
//                gbc.gridy++;
//                JLabel returnReasonLabel = new JLabel("Return Reason:");
//                returnReasonLabel.setFont(labelFont);
//                panel.add(returnReasonLabel, gbc);
//                gbc.gridx++;
//                gbc.gridwidth = 4;
//                JTextField returnReasonField = new JTextField(30);
//                panel.add(returnReasonField, gbc);
//
//                // Table for products
//                gbc.gridx = 0;
//                gbc.gridy++;
//                gbc.gridwidth = GridBagConstraints.REMAINDER;
//                gbc.fill = GridBagConstraints.BOTH;
//                gbc.weightx = 1.0;
//                gbc.weighty = 1.0;
//
//                String[] columnNames = {"#", "ID", "Code", "Name", "Purchased Qty", "Current Qty", "Returned Qty", "Unit Cost", "Total Price", "Return Price"};
//                Object[][] data = {
//                        {1, "HW-000001", "Plywood", 50, 45, 5, 150.00, 7500.00, 750.00},
//                        {2, "HW-000002", "Metal", 30, 28, 2, 300.00, 9000.00, 600.00},
//                        {3, "HW-000003", "PVC", 100, 95, 5, 100.00, 10000.00, 500.00}
//                };
//
//                DefaultTableModel model = new DefaultTableModel(data, columnNames);
//                JTable table = new JTable(model) {
//                    @Override
//                    public boolean isCellEditable(int row, int column) {
//                        return column == 5;  // Only make "Returned Qty" column editable for JSpinner
//                    }
//                };
//
//                // Add JSpinner for "Returned Qty" column
//                TableColumn returnedQtyColumn = table.getColumnModel().getColumn(5);
//                returnedQtyColumn.setCellEditor(new SpinnerEditor());
//
//                JScrollPane tableScrollPane = new JScrollPane(table);
//                panel.add(tableScrollPane, gbc);
//
//                gbc.gridwidth = 1;
//                gbc.weightx = 0;
//                gbc.weighty = 0;
//                gbc.gridy++;
//
//                // Subtotal section (Peso values)
//                JLabel subtotalLabel = new JLabel("Subtotal:");
//                subtotalLabel.setFont(labelFont);
//                panel.add(subtotalLabel, gbc);
//                gbc.gridx++;
//                JLabel subtotalValueLabel = new JLabel("₱29,800.00");  // Display returnedSubtotal in pesos
//                panel.add(subtotalValueLabel, gbc);
//                gbc.gridx++;
//                JLabel returnTotalValueLabel = new JLabel("₱5,960.00");  // Display return total in pesos
//                panel.add(returnTotalValueLabel, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy++;
//
//                // Discount, Transport Cost, Purchase Tax
//                JLabel discountLabel = new JLabel("Discount:");
//                discountLabel.setFont(labelFont);
//                panel.add(discountLabel, gbc);
//                gbc.gridx++;
//                JTextField discountField = new JTextField(10);
//                panel.add(discountField, gbc);
//
//                gbc.gridx++;
//                JLabel transportCostLabel = new JLabel("Transport Cost:");
//                transportCostLabel.setFont(labelFont);
//                panel.add(transportCostLabel, gbc);
//                gbc.gridx++;
//                JTextField transportCostField = new JTextField(10);
//                panel.add(transportCostField, gbc);
//
//                gbc.gridx++;
//                JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
//                purchaseTaxLabel.setFont(labelFont);
//                panel.add(purchaseTaxLabel, gbc);
//                gbc.gridx++;
//                JTextField purchaseTaxField = new JTextField(10);
//                panel.add(purchaseTaxField, gbc);
//
//                // Purchase Total, Total Paid, Purchase Due
//                gbc.gridx = 0;
//                gbc.gridy++;
//                JLabel purchaseTotalLabel = new JLabel("Purchase Total:");
//                purchaseTotalLabel.setFont(labelFont);
//                panel.add(purchaseTotalLabel, gbc);
//                gbc.gridx++;
//                JTextField purchaseTotalField = new JTextField(10);
//                panel.add(purchaseTotalField, gbc);
//
//                gbc.gridx++;
//                JLabel totalPaidLabel = new JLabel("Total Paid:");
//                totalPaidLabel.setFont(labelFont);
//                panel.add(totalPaidLabel, gbc);
//                gbc.gridx++;
//                JTextField totalPaidField = new JTextField(10);
//                panel.add(totalPaidField, gbc);
//
//                gbc.gridx++;
//                JLabel purchaseDueLabel = new JLabel("Purchase Due:");
//                purchaseDueLabel.setFont(labelFont);
//                panel.add(purchaseDueLabel, gbc);
//                gbc.gridx++;
//                JTextField purchaseDueField = new JTextField(10);
//                panel.add(purchaseDueField, gbc);
//
//                // Note
//                gbc.gridy++;
//                gbc.gridx = 0;
//                JLabel noteLabel = new JLabel("Note:");
//                noteLabel.setFont(labelFont);
//                panel.add(noteLabel, gbc);
//                gbc.gridx++;
//                gbc.gridwidth = 5;
//                JTextArea noteArea = new JTextArea(3, 30);
//                panel.add(new JScrollPane(noteArea), gbc);
//
//                // Return Date and Status
//                gbc.gridwidth = 1;
//                gbc.gridy++;
//                gbc.gridx = 0;
//                JLabel returnDateLabel = new JLabel("Return Date:");
//                returnDateLabel.setFont(labelFont);
//                panel.add(returnDateLabel, gbc);
//
//                gbc.gridx++;
//                JDatePickerImpl returnDatePicker = createDatePicker(); // Create date picker
//                panel.add(returnDatePicker, gbc);
//
//                gbc.gridx++;
//                JLabel statusLabel = new JLabel("Status:");
//                statusLabel.setFont(labelFont);
//                panel.add(statusLabel, gbc);
//                gbc.gridx++;
//                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
//                panel.add(statusCombo, gbc);
//
//                // Show dialog
//                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Purchase Return", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//                if (result == JOptionPane.OK_OPTION) {
//                    // Handle form submission logic
//                    String returnReason = returnReasonField.getText();
//                    String supplier = supplierField.getText();
//                    String note = noteArea.getText();
//                    String returnDate = returnDatePicker.getJFormattedTextField().getText();
//                    String status = (String) statusCombo.getSelectedItem();
//
//                    // Process the data accordingly
//                }
                JOptionPane.showMessageDialog(null, ProhibitedFunction.ACTION_CANNOT_PERFORM);
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
//                if (table.isEditing()) {
//                    table.getCellEditor().stopCellEditing();
//                }
//
//                // Confirm before deleting
//                int confirmation = JOptionPane.showConfirmDialog(null,
//                        "Are you sure you want to delete this Product?",
//                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
//
//                if (confirmation == JOptionPane.YES_OPTION) {
//                    DefaultTableModel model = (DefaultTableModel) table.getModel();
//                    model.removeRow(row);
//                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
//                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
//                }
                JOptionPane.showMessageDialog(null, ProhibitedFunction.ACTION_CANNOT_PERFORM);
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int returnId = (Integer) model.getValueAt(row, 1);

                Application.showForm(new ReturnOrder_Details(returnId));
            }
        };
        table.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(event));
        loadReturnList();
    }

    private void loadReturnList() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ReturnProductDAO returnProductDAO = new ReturnProductDAO();
        SwingWorker<java.util.List<ReturnProduct>, Void> worker = new SwingWorker<>() {
            @Override
            protected java.util.List<ReturnProduct> doInBackground() {
                var returnProducts = returnProductDAO.getAllValidReturnedProducts(50);
                return returnProducts;
            }

            @Override
            protected void done() {
                try {
                    var returnedProducts = get();

                    for (int i = 0; i < returnedProducts.size(); i++) {
                        for (var saleItem : returnedProducts.get(i).getReturnedSaleItems()) {
                            model.addRow(new Object[]{
                                    i + 1,
                                    returnedProducts.get(i).getId(),
                                    returnedProducts.get(i).getCode(),
                                    saleItem.getSale().getPerson().getName(),
                                    returnedProducts.get(i).getReturnReason(),
                                    returnedProducts.get(i).getCostOfReturnProducts(),
                                    saleItem.getReturnedAt(),
                                    saleItem.getProduct().getName()
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
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
                },
                new String[]{
                        "#", "ID", "Return No", "Customer", "Return Reason", "Cost of Return Products", "Date", "Product Name", "Action"
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
        JOptionPane.showMessageDialog(null, "Print here");
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
    }

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


package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.order.Order;
import org.POS.backend.order.OrderService;
import org.POS.backend.order.OrderStatus;
import org.POS.backend.order.UpdateOrderRequestDto;
import org.POS.backend.sale.SaleTransactionMethod;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class Order_List extends javax.swing.JPanel {

    public Order_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            List<Integer> returnedProductIds;

            JTextField netTotalField;

            @Override
            public void onEdit(int row) {
                returnedProductIds = new ArrayList<>();

                DefaultTableModel tempModel = (DefaultTableModel) table.getModel();
                int orderId = (Integer) tempModel.getValueAt(row, 1);

                OrderService orderService = new OrderService();

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);

                Font labelFont = new Font("Arial", Font.BOLD, 16);

                // Row 1: Client and Reference Fields
                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel clientLabel = new JLabel("Client:");
                clientLabel.setFont(labelFont);
                panel.add(clientLabel, gbc);

                gbc.gridx = 1;
                JTextField clientField = new JTextField("Loading...", 15);
                clientField.setEnabled(false);
                panel.add(clientField, gbc);

                gbc.gridx = 2;
                JLabel referenceLabel = new JLabel("Reference:");
                referenceLabel.setFont(labelFont);
                panel.add(referenceLabel, gbc);

                gbc.gridx = 3;
                JTextField referenceField = new JTextField("Loading...", 15);
                referenceField.setEnabled(false);
                panel.add(referenceField, gbc);

                JLabel label3 = new JLabel("PO Reference:");
                label3.setFont(new Font("Arial", Font.BOLD, 14));
                gbc.gridx = 4;
                gbc.gridy = 0;
                panel.add(label3, gbc);

                JTextField poReferenceField = new JTextField(15);
                poReferenceField.setEnabled(false);
                poReferenceField.setText("Loading...");
                gbc.gridx = 5;
                gbc.gridy = 0;
                panel.add(poReferenceField, gbc);

                // Row 2: Select Products (with combo box extending all the way to the right)
                gbc.gridy = 1;
                gbc.gridx = 0;
                gbc.gridwidth = 6;
                JLabel selectProductsLabel = new JLabel("Select Products:");
                selectProductsLabel.setFont(labelFont);
                panel.add(selectProductsLabel, gbc);

                gbc.gridy = 2;
                JComboBox<String> productCombo = new JComboBox<>();
                productCombo.setEnabled(false);
                gbc.gridwidth = 6; // Extend the combo box all the way to the right
                panel.add(productCombo, gbc);


                // Row 4: Product Table with hardware-related items
                gbc.gridy = 4;
                gbc.gridx = 0;
                gbc.gridwidth = 6;

                String[] columnNames = {"#", "ID", "Code", "Product Name", "Invoice Qty", "Price", "Unit Price", "Tax", "Subtotal", "Action"};
                Object[][] data = {
                        {1, "AP-000001", "Plywood 12mm", 10, 300, "$3000", 150, "$3150", "Remove"},
                        {2, "AP-000002", "PVC Pipes 3 inch", 20, 50, "$1000", 50, "$1050", "Remove"},
                        {3, "AP-000003", "Acrylic Paint 5L", 5, 200, "$1000", 50, "$1050", "Remove"}
                };

                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(model) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 4 || column == 9;  // Make only quantity and action columns editable
                    }
                };

                // Set column widths for a smaller table
                int[] columnWidths = {30, 80, 150, 80, 80, 100, 60, 100, 80};
                for (int i = 0; i < columnWidths.length; i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(columnWidths[i]);
                }

                model.setRowCount(0);

                // Custom quantity editor with plus/minus buttons
                TableColumn quantityColumn = table.getColumnModel().getColumn(3);
                quantityColumn.setCellEditor(new QuantityEditor(table, model));

                // Custom Remove button in the action column
                TableColumn actionColumn = table.getColumnModel().getColumn(9);
                actionColumn.setCellRenderer(new ButtonRenderer());
                actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), table, model));


                // Add table to scroll pane with a smaller preferred size
                JScrollPane tableScrollPane = new JScrollPane(table);
                tableScrollPane.setPreferredSize(new Dimension(600, 150)); // Adjust width and height to make it smaller

                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                panel.add(tableScrollPane, gbc);

                // Reset grid constraints for following components
                gbc.gridwidth = 1;
                gbc.weightx = 0;
                gbc.weighty = 0;

                // Row 5: Discount Type, Discount, and Transport Cost Fields
                gbc.gridy = 5;
                gbc.gridx = 0;
                JLabel discountTypeLabel = new JLabel("Discount:");
                discountTypeLabel.setFont(labelFont);
                panel.add(discountTypeLabel, gbc);

                gbc.gridx = 1;
                JTextField discountField = new JTextField(10);
                discountField.setEnabled(false);
                discountField.setText("Loading...");
                panel.add(discountField, gbc);

                gbc.gridx = 2;
                JLabel discountLabel = new JLabel("Transport Cost:");
                discountLabel.setFont(labelFont);
                panel.add(discountLabel, gbc);

                gbc.gridx = 3;

                JTextField transportCostField = new JTextField(10);
                transportCostField.setEnabled(false);
                transportCostField.setText("Loading...");
                panel.add(transportCostField, gbc);

                gbc.gridx = 4;
                JLabel transportCostLabel = new JLabel("Total Tax:");
                transportCostLabel.setFont(labelFont);
                panel.add(transportCostLabel, gbc);

                gbc.gridx = 5;
                JTextField totalTaxField = new JTextField("Loading...", 10);
                totalTaxField.setEnabled(false);
                panel.add(totalTaxField, gbc);

                // Row 8: Delivery Place, Date, Status (just before the Note section)
                gbc.gridy = 8;
                gbc.gridx = 0;
                JLabel deliveryPlaceLabel = new JLabel("Delivery Place:");
                deliveryPlaceLabel.setFont(labelFont);
                panel.add(deliveryPlaceLabel, gbc);

                gbc.gridx = 1;
                JTextField deliveryPlaceField = new JTextField("Loading...", 10);
                panel.add(deliveryPlaceField, gbc);

                gbc.gridx = 2;
                JLabel dateLabel = new JLabel("Date:");
                dateLabel.setFont(labelFont);
                panel.add(dateLabel, gbc);

                gbc.gridx = 3;
//                JDatePickerImpl datePicker = createDatePicker(LocalDate.now());
                JTextField date = new JTextField("Loading...");
                date.setEnabled(false);
                panel.add(date, gbc);

                gbc.gridx = 4;
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(labelFont);
                panel.add(statusLabel, gbc);

                gbc.gridx = 5;
                JComboBox<String> statusCombo = new JComboBox<>();
                statusCombo.setEnabled(false);
                statusCombo.addItem("Loading...");
                statusCombo.setSelectedItem("Loading...");
                panel.add(statusCombo, gbc);
                gbc.gridy = 9;
                gbc.gridx = 0;
                gbc.gridwidth = 6; // Full-width span

                // Net Total Label
                JLabel netTotalLabel = new JLabel("Net Total:");
                netTotalLabel.setFont(labelFont);
                panel.add(netTotalLabel, gbc);

                // Net Total TextField
                gbc.gridy = 10;
                gbc.gridx = 0;
                gbc.gridwidth = 6; // Span full row
                netTotalField = new JTextField("Loading...", 30);
                netTotalField.setEditable(false); // Calculated automatically

                // Customize the appearance of the TextField
                Font netTotalFont = new Font("Arial", Font.BOLD, 24); // Larger, bold font
                netTotalField.setFont(netTotalFont);
                netTotalField.setHorizontalAlignment(JTextField.CENTER); // Center the text
                netTotalField.setPreferredSize(new Dimension(400, 50)); // Increase size of the TextField

                panel.add(netTotalField, gbc);

                // Row 9: Note Section
                gbc.gridy = 11;
                gbc.gridx = 0;
                gbc.gridwidth = 6;
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(labelFont);
                panel.add(noteLabel, gbc);

                // Note TextArea
                gbc.gridy = 12;
                gbc.gridx = 0;
                gbc.gridwidth = 6; // Keep spanning full row
                JTextArea noteArea = new JTextArea(2, 30); // Reduce rows and columns for a smaller area
                noteArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size
                noteArea.setLineWrap(true); // Enable line wrapping
                noteArea.setWrapStyleWord(true); // Wrap lines at word boundaries
                noteArea.setText("Loading...");
                panel.add(new JScrollPane(noteArea), gbc);

                // Row 10: Pay Button
                gbc.gridy = 13; // Below the Note section
                gbc.gridx = 2; // Center the button
                gbc.gridwidth = 2; // Span two columns

                JButton payButton = new JButton("Pay");
                payButton.setFont(new Font("Arial", Font.BOLD, 16));
                payButton.setPreferredSize(new Dimension(120, 40)); // Adjust button size
                payButton.setEnabled(false);

                // Set green background and white text
                payButton.setBackground(new Color(34, 139, 34)); // Forest Green
                payButton.setForeground(Color.WHITE);

                SwingWorker<Order, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Order doInBackground() throws Exception {
                        var order = orderService.getValidOrderById(orderId);
                        return order;
                    }

                    @Override
                    protected void done() {
                        try {
                            var order = get();

                            SwingUtilities.invokeLater(() -> {
                                clientField.setText(order.getSale().getPerson().getName());


                                if (order.getSale().getTransactionMethod().equals(SaleTransactionMethod.ONLINE_PAYMENT)) {
                                    referenceField.setText(order.getSale().getReference());
                                    poReferenceField.setText("");
                                } else if (order.getSale().getTransactionMethod().equals(SaleTransactionMethod.PO_PAYMENT)) {
                                    poReferenceField.setText(order.getSale().getReference());
                                    payButton.setEnabled(true);
                                    referenceField.setText("");
                                }

                                discountField.setText(String.valueOf(order.getSale().getDiscount()));

                                transportCostField.setText(String.valueOf(order.getSale().getTransportCost()));

                                totalTaxField.setText(String.valueOf(order.getSale().getTotalTax()));

                                deliveryPlaceField.setText(order.getSale().getDeliveryPlace());

                                statusCombo.removeAllItems();
                                statusCombo.addItem("Completed");
                                statusCombo.addItem("Pending");
                                statusCombo.addItem("Payment Refunded");
                                statusCombo.setSelectedItem(order.getStatus().equals(OrderStatus.COMPLETED) ? "Completed" : order.getStatus().equals(OrderStatus.PENDING) ? "Pending" : "Payment Refunded");

                                netTotalField.setText(String.valueOf(order.getSale().getNetTotal()));

                                noteArea.setText(order.getSale().getNote());

                                date.setText(String.valueOf(order.getSale().getDate()));

                                if(order.getStatus().equals(OrderStatus.COMPLETED)){
                                    payButton.setEnabled(false);
                                }
                            });



                            int i = 1;
                            for (var saleItem : order.getSale().getSaleItems()) {
                                if (saleItem.isReturned()) continue;
                                model.addRow(new Object[]{
                                        i,
                                        saleItem.getId(),
                                        saleItem.getProduct().getCode(),
                                        saleItem.getProduct().getName(),
                                        saleItem.getQuantity(),
                                        saleItem.getPrice(),
                                        saleItem.getProduct().getSellingPrice(),
                                        saleItem.getPrice().multiply(BigDecimal.valueOf(0.12)).setScale(2, RoundingMode.HALF_UP),
                                        saleItem.getSubtotal()
                                });
                                i++;
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                worker.execute();

                // Add an ActionListener to handle Pay button click
                payButton.addActionListener(e -> {
                    // Logic to handle payment
                    JPanel paymentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc2 = new GridBagConstraints();
                    gbc2.insets = new Insets(10, 10, 10, 10); // Padding between elements
                    gbc2.fill = GridBagConstraints.HORIZONTAL;
                    gbc2.anchor = GridBagConstraints.WEST;

                    // Create input fields
                    JTextField amountField = new JTextField(20); // Increased field width
                    amountField.setText("0");

                    JTextField dateField = new JTextField(15);
                    JTextField balanceField = new JTextField(15);

                    // Make balanceField non-editable
                    try {
                        balanceField.setText(String.valueOf(worker.get().getSale().getAmountDue())); // Example initial value
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                    balanceField.setEditable(false);

                    // Fetch the current date and set it in dateField
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateField.setText(currentDate.format(formatter));
                    dateField.setEditable(false);

                    // Adding labels and fields to the panel
                    gbc2.gridx = 0;
                    gbc2.gridy = 0;
                    paymentPanel.add(new JLabel("Date:"), gbc2);
                    gbc2.gridx = 1;
                    paymentPanel.add(dateField, gbc2);

                    // Add Balance label and field
                    gbc2.gridx = 0;
                    gbc2.gridy = 1;
                    paymentPanel.add(new JLabel("Balance:"), gbc2);
                    gbc2.gridx = 1;
                    paymentPanel.add(balanceField, gbc2);

                    // Modify Amount label and field
                    JLabel amountLabel = new JLabel("Amount:");
                    amountLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set larger font for Amount label
                    amountField.setFont(new Font("Arial", Font.PLAIN, 20));
                    amountField.setPreferredSize(new Dimension(300, 50)); // Increased height
                    amountField.setMinimumSize(new Dimension(300, 50));
                    amountField.setMaximumSize(new Dimension(300, 50)); // Increase field size

                    gbc2.gridx = 0;
                    gbc2.gridy = 2;
                    paymentPanel.add(amountLabel, gbc2);
                    gbc2.gridx = 1;
                    gbc2.gridwidth = 3; // Span across multiple columns
                    paymentPanel.add(amountField, gbc2);

                    // Show the dialog
                    int result = JOptionPane.showConfirmDialog(null, paymentPanel, "Pay Debt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        // Logic to handle payment confirmation
                        try {
                            var order = worker.get();
                            BigDecimal pay = new BigDecimal(amountField.getText());
                            SwingWorker<Boolean, Void> tempWorker = new SwingWorker<>() {
                                @Override
                                protected Boolean doInBackground() throws Exception {
                                    payButton.setText("Loading...");
                                    payButton.setEnabled(false);
                                    Thread.sleep(5000);
                                    orderService.updateSaleAmountDue(order, pay);
                                    return true;
                                }

                                @Override
                                protected void done() {
                                    try {
                                        payButton.setEnabled(true);
                                        payButton.setText("Pay");
                                        statusCombo.setSelectedItem(order.getStatus().equals(OrderStatus.COMPLETED) ? "Completed" : order.getStatus().equals(OrderStatus.PENDING) ? "Pending" : "Payment Refunded");
                                        if(order.getStatus().equals(OrderStatus.COMPLETED)){
                                            payButton.setEnabled(false);
                                        }
                                        boolean result = get();
                                        if(result)
                                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (ExecutionException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            };
                            tempWorker.execute();

                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        } catch (ExecutionException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                panel.add(payButton, gbc);

                // Display the dialog
                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Orders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String reason = JOptionPane.showInputDialog(null, "Reason :");

                    // Handle user inputs
                    String deliveryPlace = deliveryPlaceField.getText();
                    String note = noteArea.getText();
                    UpdateOrderRequestDto dto = new UpdateOrderRequestDto(
                            orderId,
                            returnedProductIds,
                            note,
                            deliveryPlace,
                            reason
                    );

                    try {
                        orderService.update(dto);
                        JOptionPane.showMessageDialog(null, "Order updated");
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                }
                loadOrders();
            }

            private void computeNetTotal(BigDecimal subtotal) {
                BigDecimal currentNetTotal = new BigDecimal(netTotalField.getText().isEmpty() ? "0" : netTotalField.getText());
                SwingUtilities.invokeLater(() -> {
                    netTotalField.setText(String.valueOf(currentNetTotal.subtract(subtotal)));
                });
            }

            // Custom editor for Quantity column with plus/minus buttons
            class QuantityEditor extends AbstractCellEditor implements TableCellEditor {
                private JPanel panel;
                private JButton plusButton, minusButton;
                private JTextField quantityField;
                private JTable table;
                private DefaultTableModel model;

                public QuantityEditor(JTable table, DefaultTableModel model) {
                    this.table = table;
                    this.model = model;
                    panel = new JPanel(new FlowLayout());
                    quantityField = new JTextField(2);
                    plusButton = new JButton("+");
                    minusButton = new JButton("-");

                    plusButton.addActionListener(e -> incrementQuantity());
                    minusButton.addActionListener(e -> decrementQuantity());

                    panel.add(minusButton);
                    panel.add(quantityField);
                    panel.add(plusButton);
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    quantityField.setText(value.toString());
                    return panel;
                }

                @Override
                public Object getCellEditorValue() {
                    return quantityField.getText();
                }

                private void incrementQuantity() {
                    int currentQty = Integer.parseInt(quantityField.getText());
                    quantityField.setText(String.valueOf(currentQty + 1));
                }

                private void decrementQuantity() {
                    int currentQty = Integer.parseInt(quantityField.getText());
                    if (currentQty > 0) {
                        quantityField.setText(String.valueOf(currentQty - 1));
                    }
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
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        int removedProductId = (Integer) model.getValueAt(row, 1);
                        BigDecimal removeSubtotal = (BigDecimal) model.getValueAt(row, 8);
                        computeNetTotal(removeSubtotal);
                        returnedProductIds.add(removedProductId);
                        if (row >= 0) {
                            SwingUtilities.invokeLater(() -> tableModel.removeRow(row));
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

//                Application.showForm(new Invoice_Details());
            }


        };
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadOrders();
    }

    private void loadOrders() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        OrderService orderService = new OrderService();
        SwingWorker<List<Order>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Order> doInBackground() throws Exception {
                var orders = orderService.getAllValidOrder(50);
                return orders;
            }

            @Override
            protected void done() {
                try {
                    var orders = get();
                    for (int i = 0; i < orders.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                orders.get(i).getId(),
                                orders.get(i).getCode(),
                                orders.get(i).getOrderDate(),
                                orders.get(i).getSale().getPerson().getName(),
                                orders.get(i).getSale().getTransactionMethod().name(),
                                orders.get(i).getSale().getNetTotal(),
                                orders.get(i).getSale().getAmountDue(),
                                orders.get(i).getStatus().name()
                        });
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
        jLabel1.setText("Orders");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Orders");

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
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
                        "#", "ID", "Order No	", "Order Date	", "Client	", "Type of Payment", "Net Total	", "Amount Due", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, true, false, false, false, true
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
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                                .addGap(0, 1277, Short.MAX_VALUE)))
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

    }//GEN-LAST:event_jButton1ActionPerformed

    // Method to create createdAt pickers with the current createdAt
    private JDatePickerImpl createDatePicker(LocalDate date) {
        UtilDateModel model = new UtilDateModel();
        model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        LocalDate date = LocalDate.now();
        model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

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

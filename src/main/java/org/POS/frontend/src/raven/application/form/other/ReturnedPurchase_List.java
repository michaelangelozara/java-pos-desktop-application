
package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.brand.BrandService;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchase.PurchaseService;
import org.POS.backend.return_purchase.AddReturnPurchaseRequestDto;
import org.POS.backend.return_purchase.ReturnPurchase;
import org.POS.backend.return_purchase.ReturnPurchaseService;
import org.POS.backend.return_purchase.ReturnPurchaseStatus;
import org.POS.backend.return_purchase.return_item.AddReturnItemRequestDto;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ExecutionException;


public class ReturnedPurchase_List extends javax.swing.JPanel {

    private Timer timer;

    public ReturnedPurchase_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JOptionPane.showMessageDialog(null, "You can't perform this action");
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
                JOptionPane.showMessageDialog(null, "You can't perform this action");
            }

            @Override

            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int returnPurchaseId = (Integer) model.getValueAt(row, 1);
                ReturnPurchaseService returnPurchaseService = new ReturnPurchaseService();
                SwingWorker<ReturnPurchase, Void> worker = new SwingWorker<>() {
                    @Override
                    protected ReturnPurchase doInBackground() throws Exception {
                        return returnPurchaseService.getValidReturnPurchaseById(returnPurchaseId);
                    }

                    @Override
                    protected void done() {
                        try {
                            var returnPurchase = get();
                            Application.showForm(new Return_Purchase_Details(returnPurchase));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                worker.execute();
            }
        };
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadReturnPurchases();
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

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                scheduleQuery();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                scheduleQuery();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                scheduleQuery();
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "#", "ID", "Return No	", "Purchase No	", "Supplier", "Return Reason	", "Cost of Return Products	", "Date	", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, true
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

    private void scheduleQuery() {
        if (timer != null) {
            timer.cancel(); // Cancel any existing scheduled query
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                filterList();
            }
        }, 400); // Delay of 300 ms
    }

    private void filterList() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String name = jTextField1.getText();

        if (name.isEmpty()) {
            loadReturnPurchases();
            return;
        }

        ReturnPurchaseService returnPurchaseService = new ReturnPurchaseService();
        SwingWorker<List<ReturnPurchase>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ReturnPurchase> doInBackground() throws Exception {
                var returnPurchases = returnPurchaseService.getAllValidReturnPurchaseByQuery(name);
                return returnPurchases;
            }

            @Override
            protected void done() {
                try {
                    var returnPurchases = get();

                    int n = 1;
                    for (int i = 0; i < returnPurchases.size(); i++) {
                        for(var returnedPurchaseItem : returnPurchases.get(i).getPurchaseItems()){
                            model.addRow(new Object[]{
                                    n,
                                    returnPurchases.get(i).getId(),
                                    returnPurchases.get(i).getCode(),
                                    returnPurchases.get(i).getPurchase().getCode(),
                                    returnPurchases.get(i).getPurchase().getPerson().getName(),
                                    returnPurchases.get(i).getReason(),
                                    returnedPurchaseItem.getReturnPrice(),
                                    returnedPurchaseItem.getReturnedAt(),
                                    returnPurchases.get(i).getStatus().name()
                            });
                            n++;
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

    private void loadReturnPurchases(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ReturnPurchaseService returnPurchaseService = new ReturnPurchaseService();
        SwingWorker<List<ReturnPurchase>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ReturnPurchase> doInBackground() throws Exception {
                var returnPurchases = returnPurchaseService.getAllValidReturnProduct(50);
                return returnPurchases;
            }

            @Override
            protected void done() {
                try {
                    var returnPurchases = get();

                    int n = 1;
                    for (int i = 0; i < returnPurchases.size(); i++) {
                        for(var returnedPurchaseItem : returnPurchases.get(i).getPurchaseItems()){
                            model.addRow(new Object[]{
                                    n,
                                    returnPurchases.get(i).getId(),
                                    returnPurchases.get(i).getCode(),
                                    returnPurchases.get(i).getPurchase().getCode(),
                                    returnPurchases.get(i).getPurchase().getPerson().getName(),
                                    returnPurchases.get(i).getReason(),
                                    returnedPurchaseItem.getReturnPrice(),
                                    returnedPurchaseItem.getReturnedAt(),
                                    returnPurchases.get(i).getStatus().name()
                            });
                            n++;
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField netTotalField = new JTextField(10);
        JTextField purchaseTaxField = new JTextField(10);

        Map<Integer, Integer> tableMap = new HashMap<>();

        // Define font for larger bold labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Return Reason and Supplier - first row (full width, two fields)
        JLabel label1 = new JLabel("Reason:");
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label1, gbc);

        JTextField reasonField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(reasonField, gbc);

        JLabel label2 = new JLabel("Supplier:");
        label2.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(label2, gbc);

        JTextField supplierField = new JTextField(20);
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(supplierField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel selectPurchasesLabel = new JLabel("Select Purchase:");
        selectPurchasesLabel.setFont(labelFont);
        panel.add(selectPurchasesLabel, gbc);

        PurchaseService purchaseService = new PurchaseService();
        Map<Integer, Integer> purchaseMap = new HashMap<>();

        gbc.gridx = 1;
        gbc.gridwidth = 5;
        JComboBox<String> purchasesCombo = new JComboBox<>();
        panel.add(purchasesCombo, gbc);

        // Return Table - below Select Purchases
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"#", "ID", "Code", "Name", "Purchased Quantity", "Returned Quantity", "Selling Price", "Subtotal", "Return Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only "Returned Quantity" and "Return Price" are editable
            }
        };

        JTable returnTable = new JTable(tableModel);
        returnTable.setFillsViewportHeight(true);

        tableModel.addTableModelListener(e -> {
            int column = e.getColumn();
            int row = e.getFirstRow();

            if (column == 5) { // Listening for changes in the return quantity column
                Object purchaseQtyObj = tableModel.getValueAt(row, 4);
                Object returnQtyObj = tableModel.getValueAt(row, 5);
                Object sellingPriceObj = tableModel.getValueAt(row, 6);

                // Parsing values
                BigDecimal sellingPrice = new BigDecimal(String.valueOf(sellingPriceObj));
                int purchaseQty = Integer.parseInt(String.valueOf(purchaseQtyObj));
                int newReturnQty = Integer.parseInt(String.valueOf(returnQtyObj));

                // Validate return quantity
                if (newReturnQty > purchaseQty) {
                    JOptionPane.showMessageDialog(null, "The return quantity is greater than purchase quantity");
                    tableModel.setValueAt(0, row, 5); // Reset invalid value
                    return;
                }

                // Retrieve the previous return quantity for this row
                int previousReturnQty = tableMap.getOrDefault(row, 0);

                // Calculate the change in return price
                BigDecimal previousReturnPrice = sellingPrice.multiply(BigDecimal.valueOf(previousReturnQty));
                BigDecimal newReturnPrice = sellingPrice.multiply(BigDecimal.valueOf(newReturnQty));
                BigDecimal delta = newReturnPrice.subtract(previousReturnPrice);

                // Update net total
                BigDecimal currentNetTotal = new BigDecimal(netTotalField.getText());
                BigDecimal updatedNetTotal = currentNetTotal.subtract(delta);
                SwingUtilities.invokeLater(() -> {
                    netTotalField.setText(updatedNetTotal.toString());

                    BigDecimal netTotal = new BigDecimal(netTotalField.getText());
                    purchaseTaxField.setText(String.valueOf((netTotal.multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12))).setScale(2, RoundingMode.HALF_UP)));
                });

                // Update the return price for this row in the table model
                tableModel.setValueAt(newReturnPrice, row, 8);

                // Store the new return quantity for the next change
                tableMap.put(row, newReturnQty);
            }
        });

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < returnTable.getColumnCount(); i++) {
            returnTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(returnTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(tableScrollPane, gbc);

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
        JLabel purchaseTaxLabel = new JLabel("Total Tax:");
        purchaseTaxLabel.setFont(labelFont);
        panel.add(purchaseTaxLabel, gbc);

        gbc.gridx = 5;

        purchaseTaxField.setEditable(false);
        panel.add(purchaseTaxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel totalPaidLabel = new JLabel("Status:");
        totalPaidLabel.setFont(labelFont);
        panel.add(totalPaidLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        panel.add(statusCombo, gbc);


        gbc.gridx = 2;
        gbc.gridy = 5;
        JLabel netTotalLabel = new JLabel("Net Total: ");
        netTotalLabel.setFont(labelFont);
        panel.add(netTotalLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;

        netTotalField.setEditable(false);
        panel.add(netTotalField, gbc);

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

        SwingWorker<List<Purchase>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Purchase> doInBackground() {
                SwingUtilities.invokeLater(() -> {
                    purchasesCombo.addItem("Loading...");
                    purchasesCombo.setSelectedItem("Loading...");
                    purchasesCombo.setEnabled(false);
                });
                var purchases = purchaseService.getAllValidPurchasesWithoutLimit();
                return purchases;
            }

            @Override
            protected void done() {
                try {
                    var purchases = get();
                    SwingUtilities.invokeLater(() -> {
                        purchasesCombo.removeAllItems();
                        purchasesCombo.addItem("Select Purchase");
                        purchasesCombo.setSelectedItem("Select Purchase");
                        purchasesCombo.setEnabled(true);
                        for (int i = 0; i < purchases.size(); i++) {
                            purchasesCombo.addItem(purchases.get(i).getCode());
                            purchaseMap.put(i + 1, purchases.get(i).getId());
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();

        purchasesCombo.addActionListener(e -> {
            int selectedIndex = purchasesCombo.getSelectedIndex();
            Integer purchaseId = purchaseMap.get(selectedIndex);

            if (purchaseId != null) {
                SwingWorker<Purchase, Void> purchaseWorker = new SwingWorker<>() {
                    @Override
                    protected Purchase doInBackground() {

                        SwingUtilities.invokeLater(() -> {
                            tableModel.setRowCount(0);
                            supplierField.setText("Loading...");
                            supplierField.setEnabled(false);

                            discountField.setText("Loading...");
                            discountField.setEnabled(false);

                            transportCostField.setText("Loading...");
                            transportCostField.setEnabled(false);

                            purchaseTaxField.setText("Loading...");
                            purchaseTaxField.setEnabled(false);

                            netTotalField.setText("Loading...");
                            netTotalField.setEnabled(false);
                        });
                        var purchase = purchaseService.getValidPurchaseWithoutDto(purchaseId);
                        return purchase;
                    }

                    @Override
                    protected void done() {
                        try {
                            var purchase = get();
                            SwingUtilities.invokeLater(() -> {
                                supplierField.setText(purchase.getPerson().getName());
                                supplierField.setEnabled(true);

                                discountField.setText(String.valueOf(purchase.getDiscount()));
                                discountField.setEnabled(true);

                                transportCostField.setText(String.valueOf(purchase.getTransportCost()));
                                transportCostField.setEnabled(true);

                                purchaseTaxField.setText(String.valueOf(purchase.getTotalTax()));
                                purchaseTaxField.setEnabled(true);

                                netTotalField.setText(String.valueOf(purchase.getNetTotal()));
                                netTotalField.setEnabled(true);

                                for (int i = 0; i < purchase.getPurchaseItems().size(); i++) {
                                    tableModel.addRow(new Object[]{
                                            i + 1,
                                            purchase.getPurchaseItems().get(i).getId(),
                                            purchase.getPurchaseItems().get(i).getProduct().getCode(),
                                            purchase.getPurchaseItems().get(i).getProduct().getName(),
                                            purchase.getPurchaseItems().get(i).getQuantity(),
                                            0,
                                            purchase.getPurchaseItems().get(i).getSellingPrice(),
                                            purchase.getPurchaseItems().get(i).getSubtotal(),
                                            0

                                    });
                                }
                            });
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        } catch (ExecutionException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                };
                purchaseWorker.execute();
            }
        });

        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Return", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String reason = reasonField.getText();
            String note = noteArea.getText();
            BigDecimal netTotal = new BigDecimal(netTotalField.getText());
            BigDecimal totalTax = new BigDecimal(purchaseTaxField.getText());

            // Handle form submission logic here
            var allRows = getAllRows(tableModel);
            Set<AddReturnItemRequestDto> returnItemSet = new HashSet<>();
            for (var selectedProduct : allRows) {
                AddReturnItemRequestDto dto = new AddReturnItemRequestDto(
                        selectedProduct.getId(),
                        selectedProduct.getReturnQty(),
                        selectedProduct.getReturnPrice()
                );
                returnItemSet.add(dto);
            }
            ReturnPurchaseService returnPurchaseService = new ReturnPurchaseService();
            int purchaseIndex = purchasesCombo.getSelectedIndex();
            int purchaseId = purchaseMap.get(purchaseIndex);
            AddReturnPurchaseRequestDto dto = new AddReturnPurchaseRequestDto(
                    purchaseId,
                    returnItemSet,
                    reason,
                    totalTax,
                    netTotal,
                    note,
                    statusCombo.equals("Active") ? ReturnPurchaseStatus.ACTIVE : ReturnPurchaseStatus.INACTIVE
            );

            SwingWorker<Boolean, Void> addWorker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    try {
                        returnPurchaseService.add(dto);
                        return true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    return false;
                }

                @Override
                protected void done() {
                    try {
                        boolean result = get();
                        if(result)
                            JOptionPane.showMessageDialog(null, "Transaction Successful");
                        else
                            JOptionPane.showMessageDialog(null, "Transaction Failed");
                        loadReturnPurchases();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            addWorker.execute();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class SelectedProduct {
        private int id;
        private String code;
        private String name;
        private int purchaseQty;
        private int returnQty;
        private BigDecimal sellingPrice;
        private BigDecimal subTotal;
        private BigDecimal returnPrice;
    }

    private List<SelectedProduct> getAllRows(DefaultTableModel model) {
        List<SelectedProduct> selectedProducts = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object productIdObj = (Object) model.getValueAt(i, 1);
            Object codeObj = (Object) model.getValueAt(i, 2);
            Object nameObj = (Object) model.getValueAt(i, 3);
            Object purchaseQtyObj = (Object) model.getValueAt(i, 4);
            Object returnQtyObj = (Object) model.getValueAt(i, 5);
            Object sellingPriceObj = (Object) model.getValueAt(i, 6);
            Object subtotalObj = (Object) model.getValueAt(i, 7);

            // Convert Objects to desired types
            int productId = (int) productIdObj;
            String code = String.valueOf(codeObj);
            String name = String.valueOf(nameObj);
            int purchaseQty = Integer.parseInt(String.valueOf(purchaseQtyObj));
            int returnQty = Integer.parseInt(String.valueOf(returnQtyObj));
            double sellingPrice = Double.parseDouble(String.valueOf(sellingPriceObj));
            double subtotal = Double.parseDouble(String.valueOf(subtotalObj));

            BigDecimal returnPrice = BigDecimal.valueOf(returnQty).multiply(BigDecimal.valueOf(sellingPrice));
            SelectedProduct selectedProduct = new SelectedProduct(
                    productId,
                    code,
                    name,
                    purchaseQty,
                    returnQty,
                    BigDecimal.valueOf(sellingPrice),
                    BigDecimal.valueOf(subtotal),
                    returnPrice
            );
            selectedProducts.add(selectedProduct);
        }
        model.setRowCount(0);
        return selectedProducts;
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
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ReturnPurchaseService returnPurchaseService = new ReturnPurchaseService();
        SwingWorker<List<ReturnPurchase>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ReturnPurchase> doInBackground() throws Exception {
                var returnPurchases = returnPurchaseService.getAllValidReturnPurchaseByRange(fromDate, toDate);
                return returnPurchases;
            }

            @Override
            protected void done() {
                try {
                    var returnPurchases = get();

                    int n = 1;
                    for (int i = 0; i < returnPurchases.size(); i++) {
                        for(var returnedPurchaseItem : returnPurchases.get(i).getPurchaseItems()){
                            model.addRow(new Object[]{
                                    n,
                                    returnPurchases.get(i).getId(),
                                    returnPurchases.get(i).getCode(),
                                    returnPurchases.get(i).getPurchase().getCode(),
                                    returnPurchases.get(i).getPurchase().getPerson().getName(),
                                    returnPurchases.get(i).getReason(),
                                    returnedPurchaseItem.getReturnPrice(),
                                    returnedPurchaseItem.getReturnedAt(),
                                    returnPurchases.get(i).getStatus().name()
                            });
                            n++;
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
}

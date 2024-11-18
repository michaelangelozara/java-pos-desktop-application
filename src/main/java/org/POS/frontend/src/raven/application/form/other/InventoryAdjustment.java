
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.inventory_adjustment.*;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class InventoryAdjustment extends javax.swing.JPanel {


    public InventoryAdjustment() {
        initComponents();

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int inventoryAdjustmentId = (Integer) model.getValueAt(row, 1);
                InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();


                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;

                ProductService productService = new ProductService();
                JComboBox<String> selectProductsComboBox = new JComboBox<>();
                // Create panel for Stock, Adjustment Type, Quantity
                JPanel adjustmentDetailsPanel = new JPanel(new GridBagLayout());

                JLabel stocksLabel = new JLabel("Stocks Available:");
                JTextField stocksField = new JTextField(); // Stock field

                // New Font for labels: Bigger and Bold
                Font boldFont = new Font("Arial", Font.BOLD, 16);

                // Creating input fields
                JTextField adjustmentNoField = new JTextField(); // Example adjustment number
                adjustmentNoField.setEditable(false);

                JTextField adjustmentReasonField = new JTextField();

                stocksField.setEditable(false); // Read-only

                JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(new String[]{"Increment", "Decrement"}); // Adjustment Type ComboBox

                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Quantity Spinner

                // Note section after Quantity
                JTextArea noteField = new JTextArea(3, 20);
                JScrollPane noteScrollPane = new JScrollPane(noteField);

                JTextField adjustmentDateField = new JTextField(); // Current createdAt pre-filled
                adjustmentDateField.setEditable(false); // Read-only

                JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

                adjustmentDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adjustment Details", TitledBorder.LEFT, TitledBorder.TOP, boldFont));

                GridBagConstraints adjGbc = new GridBagConstraints();
                adjGbc.insets = new Insets(5, 5, 5, 5);
                adjGbc.fill = GridBagConstraints.HORIZONTAL;
                adjGbc.weightx = 1.0;

                adjGbc.gridx = 0;
                adjGbc.gridy = 0;

                stocksLabel.setFont(boldFont);
                adjustmentDetailsPanel.add(stocksLabel, adjGbc);
                adjGbc.gridx = 1;
                adjustmentDetailsPanel.add(stocksField, adjGbc);

                adjGbc.gridx = 0;
                adjGbc.gridy = 1;
                JLabel adjustmentTypeLabel = new JLabel("Adjustment Type *:");
                adjustmentTypeLabel.setFont(boldFont);
                adjustmentDetailsPanel.add(adjustmentTypeLabel, adjGbc);
                adjGbc.gridx = 1;
                adjustmentDetailsPanel.add(adjustmentTypeComboBox, adjGbc);

                adjGbc.gridx = 0;
                adjGbc.gridy = 2;
                JLabel quantityLabel = new JLabel("Quantity *:");
                quantityLabel.setFont(boldFont);
                adjustmentDetailsPanel.add(quantityLabel, adjGbc);
                adjGbc.gridx = 1;
                adjustmentDetailsPanel.add(quantitySpinner, adjGbc);

                // Initially, make the adjustmentDetailsPanel invisible
                adjustmentDetailsPanel.setVisible(false);


                // Adding components to the panel with updated bigger and bold labels
                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel adjustmentNoLabel = new JLabel("Adjustment No:");
                adjustmentNoLabel.setFont(boldFont);
                panel.add(adjustmentNoLabel, gbc);
                gbc.gridx = 1;

                panel.add(adjustmentNoField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel adjustmentReasonLabel = new JLabel("Adjustment Reason:");
                adjustmentReasonLabel.setFont(boldFont);
                panel.add(adjustmentReasonLabel, gbc);
                gbc.gridx = 1;
                panel.add(adjustmentReasonField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel selectProductsLabel = new JLabel("Product :");
                selectProductsLabel.setFont(boldFont);
                panel.add(selectProductsLabel, gbc);
                gbc.gridx = 1;
                panel.add(selectProductsComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                panel.add(adjustmentDetailsPanel, gbc); // Add the group panel with stocks, adjustment type, and quantity

                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 4;
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(boldFont);
                panel.add(noteLabel, gbc);
                gbc.gridx = 1;
                panel.add(noteScrollPane, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                JLabel adjustmentDateLabel = new JLabel("Adjustment Date:");
                adjustmentDateLabel.setFont(boldFont);
                panel.add(adjustmentDateLabel, gbc);
                gbc.gridx = 1;
                panel.add(adjustmentDateField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 6;
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(boldFont);
                panel.add(statusLabel, gbc);
                gbc.gridx = 1;
                panel.add(statusComboBox, gbc);

                SwingWorker<InventoryAdjustmentResponseDto, Void> inventoryAdjustmentWorker = new SwingWorker<InventoryAdjustmentResponseDto, Void>() {
                    @Override
                    protected InventoryAdjustmentResponseDto doInBackground() throws Exception {
                        SwingUtilities.invokeLater(() -> {
                            selectProductsComboBox.addItem("Loading...");
                            selectProductsComboBox.setSelectedItem("Loading...");

                            adjustmentNoField.setText("Loading...");

                            adjustmentReasonField.setText("Loading...");

                            stocksField.setText("Loading...");

                            adjustmentTypeComboBox.removeAllItems();
                            adjustmentTypeComboBox.addItem("Loading...");
                            adjustmentTypeComboBox.setSelectedItem("Loading...");

                            noteField.setText("Loading...");

                            statusComboBox.removeAllItems();
                            statusComboBox.addItem("Loading...");
                            statusComboBox.setSelectedItem("Loading...");

                            adjustmentDateField.setText("Loading...");


                            adjustmentDetailsPanel.setVisible(true);
                        });
                        var inventoryAdjustment = inventoryAdjustmentService.getValidInventoryAdjustmentById(inventoryAdjustmentId);
                        return inventoryAdjustment;
                    }

                    @Override
                    protected void done() {
                        try {
                            var inventoryAdjustment = get();

                            SwingUtilities.invokeLater(() -> {
                                selectProductsComboBox.removeAllItems();
                                selectProductsComboBox.addItem(inventoryAdjustment.product().getName());
                                selectProductsComboBox.setSelectedItem(inventoryAdjustment.product().getName());
                                selectProductsComboBox.setEnabled(false);

                                adjustmentNoField.setText(inventoryAdjustment.code());

                                adjustmentReasonField.setText(inventoryAdjustment.reason());

                                stocksField.setText(String.valueOf(inventoryAdjustment.product().getStock()));

                                adjustmentTypeComboBox.removeAllItems();
                                adjustmentTypeComboBox.addItem("Increment");
                                adjustmentTypeComboBox.addItem("Decrement");
                                adjustmentTypeComboBox.setSelectedItem(inventoryAdjustment.type().equals(InventoryAdjustmentType.INCREMENT) ? "Increment" : "Decrement");

                                noteField.setText(inventoryAdjustment.note());

                                statusComboBox.removeAllItems();
                                statusComboBox.addItem("Active");
                                statusComboBox.addItem("Inactive");
                                statusComboBox.setSelectedItem(inventoryAdjustment.status().equals(InventoryAdjustmentStatus.ACTIVE) ? "Active" : "Inactive");

                                adjustmentDateField.setText(String.valueOf(inventoryAdjustment.date()));

                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                inventoryAdjustmentWorker.execute();


                // Set panel size
                panel.setPreferredSize(new Dimension(600, 600));

                int result = JOptionPane.showConfirmDialog(null, panel, "Create Adjustment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION && adjustmentDetailsPanel.isVisible()) {
                    String reason = adjustmentReasonField.getText().trim();
                    int quantity = (Integer) quantitySpinner.getValue();
                    String note = noteField.getText();
                    String status = (String) statusComboBox.getSelectedItem();

                    String typeOfAdjustment = (String) adjustmentTypeComboBox.getSelectedItem();
                    UpdateInventoryAdjustmentRequestDto dto = new UpdateInventoryAdjustmentRequestDto(
                            inventoryAdjustmentId,
                            reason,
                            typeOfAdjustment.equals("Increment") ? InventoryAdjustmentType.INCREMENT : InventoryAdjustmentType.DECREMENT,
                            quantity,
                            note,
                            status.equals("Active") ? InventoryAdjustmentStatus.ACTIVE : InventoryAdjustmentStatus.INACTIVE
                    );

                    if (typeOfAdjustment.equals("Increment") || typeOfAdjustment.equals("Decrement")) {
                        inventoryAdjustmentService.update(dto);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select adjustment type first", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Inventory Adjustment is successfully Updated", "Updated", JOptionPane.PLAIN_MESSAGE);
                    loadInventoryAdjustment();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product first", "Warning", JOptionPane.INFORMATION_MESSAGE);
                }
            }


            @Override
            public void onDelete(int row) {
                JOptionPane.showMessageDialog(null, "Deletion here is prohibited");
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int id = (Integer) model.getValueAt(row, 1);

                InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();
                SwingWorker<InventoryAdjustmentResponseDto, Void> worker = new SwingWorker<>() {
                    @Override
                    protected InventoryAdjustmentResponseDto doInBackground() throws Exception {
                        return inventoryAdjustmentService.getValidInventoryAdjustmentById(id);
                    }

                    @Override
                    protected void done() {
                        try {
                            var inventory = get();
                            Application.showForm(new InventoryAdjustment_Details(inventory));
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

        table.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(event));
        loadInventoryAdjustment();
    }

    private void loadInventoryAdjustment() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();
        var inventoryAdjustments = inventoryAdjustmentService.getAllValidInventoryAdjustment(50);
        for (int i = 0; i < inventoryAdjustments.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    inventoryAdjustments.get(i).id(),
                    inventoryAdjustments.get(i).product().getName(),
                    inventoryAdjustments.get(i).code(),
                    inventoryAdjustments.get(i).user().getName(),
                    inventoryAdjustments.get(i).reason(),
                    inventoryAdjustments.get(i).date(),
                    inventoryAdjustments.get(i).status().name()
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
                new Object[][]{
                        {null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "ID", "Product", "Adjustment #", "User", "Reason", "Date", "Status", "Actions"
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

        ProductService productService = new ProductService();
        JComboBox<String> selectProductsComboBox = new JComboBox<>();
        // Create panel for Stock, Adjustment Type, Quantity
        JPanel adjustmentDetailsPanel = new JPanel(new GridBagLayout());

        JLabel stocksLabel = new JLabel("Stocks Available:");
        JTextField stocksField = new JTextField(); // Stock field

        // New Font for labels: Bigger and Bold
        Font boldFont = new Font("Arial", Font.BOLD, 16);

        // Creating input fields
        JTextField adjustmentNoField = new JTextField("This is system generated"); // Example adjustment number
        adjustmentNoField.setEditable(false);

        JTextField adjustmentReasonField = new JTextField();

        Map<Integer, Integer> productMap = new HashMap<>();

        ActionListener productComboLister = e -> {
            String selectedProduct = (String) selectProductsComboBox.getSelectedItem();
            if (selectedProduct.equals("Select Product")) {
                adjustmentDetailsPanel.setVisible(false);
                panel.revalidate();
                panel.repaint();
                return;
            }

            int productIndex = selectProductsComboBox.getSelectedIndex();
            Integer productId = productMap.get(productIndex);

            if (productId != null) {
                SwingWorker<ProductResponseDto, Void> tempWorker = new SwingWorker<ProductResponseDto, Void>() {
                    @Override
                    protected ProductResponseDto doInBackground() throws Exception {
                        adjustmentDetailsPanel.setVisible(false);
                        var product = productService.getValidProductById(productId);
                        return product;
                    }

                    @Override
                    protected void done() {
                        try {
                            var product = get();
                            stocksField.setText(String.valueOf(product.stock()));
                            adjustmentDetailsPanel.setVisible(true);
                            panel.revalidate();
                            panel.repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                tempWorker.execute();
            }
        };

        SwingWorker<List<Product>, Void> productWorker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    selectProductsComboBox.removeActionListener(productComboLister);
                    selectProductsComboBox.addItem("Loading Products");
                    selectProductsComboBox.setSelectedItem("Loading Products");
                    selectProductsComboBox.setEnabled(false);
                });

                var products = productService.getAllValidProductsWithoutLimit();
                return products;
            }

            @Override
            protected void done() {
                try {
                    var products = get();
                    SwingUtilities.invokeLater(() -> {
                        selectProductsComboBox.setEnabled(true);
                        selectProductsComboBox.removeAllItems();
                        selectProductsComboBox.addItem("Select Product");

                        for (int i = 0; i < products.size(); i++) {
                            selectProductsComboBox.addItem(products.get(i).getName());
                            productMap.put(i + 1, products.get(i).getId());
                        }
                        selectProductsComboBox.addActionListener(productComboLister);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        productWorker.execute();

        stocksField.setEditable(false); // Read-only

        JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(new String[]{"Increment", "Decrement"}); // Adjustment Type ComboBox

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Quantity Spinner

        // Note section after Quantity
        JTextArea noteField = new JTextArea(3, 20);
        JScrollPane noteScrollPane = new JScrollPane(noteField);

        JTextField adjustmentDateField = new JTextField(); // Current createdAt pre-filled
        adjustmentDateField.setText("This is system generated");
        adjustmentDateField.setEditable(false); // Read-only

        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        adjustmentDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adjustment Details", TitledBorder.LEFT, TitledBorder.TOP, boldFont));

        GridBagConstraints adjGbc = new GridBagConstraints();
        adjGbc.insets = new Insets(5, 5, 5, 5);
        adjGbc.fill = GridBagConstraints.HORIZONTAL;
        adjGbc.weightx = 1.0;

        adjGbc.gridx = 0;
        adjGbc.gridy = 0;

        stocksLabel.setFont(boldFont);
        adjustmentDetailsPanel.add(stocksLabel, adjGbc);
        adjGbc.gridx = 1;
        adjustmentDetailsPanel.add(stocksField, adjGbc);

        adjGbc.gridx = 0;
        adjGbc.gridy = 1;
        JLabel adjustmentTypeLabel = new JLabel("Adjustment Type *:");
        adjustmentTypeLabel.setFont(boldFont);
        adjustmentDetailsPanel.add(adjustmentTypeLabel, adjGbc);
        adjGbc.gridx = 1;
        adjustmentDetailsPanel.add(adjustmentTypeComboBox, adjGbc);

        adjGbc.gridx = 0;
        adjGbc.gridy = 2;
        JLabel quantityLabel = new JLabel("Quantity *:");
        quantityLabel.setFont(boldFont);
        adjustmentDetailsPanel.add(quantityLabel, adjGbc);
        adjGbc.gridx = 1;
        adjustmentDetailsPanel.add(quantitySpinner, adjGbc);

        // Initially, make the adjustmentDetailsPanel invisible
        adjustmentDetailsPanel.setVisible(false);


        // Adding components to the panel with updated bigger and bold labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel adjustmentNoLabel = new JLabel("Adjustment No:");
        adjustmentNoLabel.setFont(boldFont);
        panel.add(adjustmentNoLabel, gbc);
        gbc.gridx = 1;
        panel.add(adjustmentNoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel adjustmentReasonLabel = new JLabel("Adjustment Reason:");
        adjustmentReasonLabel.setFont(boldFont);
        panel.add(adjustmentReasonLabel, gbc);
        gbc.gridx = 1;
        panel.add(adjustmentReasonField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel selectProductsLabel = new JLabel("Select Products *:");
        selectProductsLabel.setFont(boldFont);
        panel.add(selectProductsLabel, gbc);
        gbc.gridx = 1;
        panel.add(selectProductsComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(adjustmentDetailsPanel, gbc); // Add the group panel with stocks, adjustment type, and quantity

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);
        gbc.gridx = 1;
        panel.add(noteScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel adjustmentDateLabel = new JLabel("Adjustment Date:");
        adjustmentDateLabel.setFont(boldFont);
        panel.add(adjustmentDateLabel, gbc);
        gbc.gridx = 1;
        panel.add(adjustmentDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);
        gbc.gridx = 1;
        panel.add(statusComboBox, gbc);

        // Set panel size
        panel.setPreferredSize(new Dimension(600, 600));

        int result = JOptionPane.showConfirmDialog(null, panel, "Create Adjustment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && adjustmentDetailsPanel.isVisible()) {
            String reason = adjustmentReasonField.getText().trim();
            int quantity = (Integer) quantitySpinner.getValue();
            String note = noteField.getText();
            String status = (String) statusComboBox.getSelectedItem();

            int productSelectedIndex = selectProductsComboBox.getSelectedIndex();
            Integer productId = productMap.get(productSelectedIndex);
            if (productId == null) {
                JOptionPane.showMessageDialog(null, "You need to select a product");
                return;
            }
            String typeOfAdjustment = (String) adjustmentTypeComboBox.getSelectedItem();

            InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();

            AddInventoryAdjustmentDto dto = new AddInventoryAdjustmentDto(
                    productId,
                    reason,
                    note,
                    status.equals("Active") ? InventoryAdjustmentStatus.ACTIVE : InventoryAdjustmentStatus.INACTIVE,
                    typeOfAdjustment.equals("Increment") ? InventoryAdjustmentType.INCREMENT : InventoryAdjustmentType.DECREMENT,
                    quantity
            );

            if (typeOfAdjustment.equals("Increment")) {
                inventoryAdjustmentService.add(dto);
            } else if (typeOfAdjustment.equals("Decrement")) {
                inventoryAdjustmentService.add(dto);
            } else {
                JOptionPane.showMessageDialog(null, "Please select adjustment type first", "Warning", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Inventory Adjustment is successfully Created", "Created", JOptionPane.PLAIN_MESSAGE);
            loadInventoryAdjustment();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product first", "Warning", JOptionPane.INFORMATION_MESSAGE);
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

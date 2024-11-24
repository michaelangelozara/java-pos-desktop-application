
package org.POS.frontend.src.raven.application.form.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.inventory_adjustment.*;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_attribute.ProductAttribute;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.product_category.ProductCategoryResponseDto;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class InventoryAdjustment extends javax.swing.JPanel {

    private Timer timer;

    public InventoryAdjustment() {
        initComponents();

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                int inventoryAdjustmentId = (Integer) model.getValueAt(row, 1);
//                InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();


//                JPanel panel = new JPanel(new GridBagLayout());
//                GridBagConstraints gbc = new GridBagConstraints();
//                gbc.insets = new Insets(10, 10, 10, 10);
//                gbc.fill = GridBagConstraints.HORIZONTAL;
//                gbc.weightx = 1.0;
//
//                ProductService productService = new ProductService();
//                JComboBox<String> selectProductsComboBox = new JComboBox<>();
//                // Create panel for Stock, Adjustment Type, Quantity
//                JPanel adjustmentDetailsPanel = new JPanel(new GridBagLayout());
//
//                JLabel stocksLabel = new JLabel("Stocks Available:");
//                JTextField stocksField = new JTextField(); // Stock field
//
//                // New Font for labels: Bigger and Bold
//                Font boldFont = new Font("Arial", Font.BOLD, 16);
//
//                // Creating input fields
//                JTextField adjustmentNoField = new JTextField(); // Example adjustment number
//                adjustmentNoField.setEditable(false);
//
//                JTextField adjustmentReasonField = new JTextField();
//
//                stocksField.setEditable(false); // Read-only
//
//                JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(new String[]{"Increment", "Decrement"}); // Adjustment Type ComboBox
//
//                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Quantity Spinner
//
//                // Note section after Quantity
//                JTextArea noteField = new JTextArea(3, 20);
//                JScrollPane noteScrollPane = new JScrollPane(noteField);
//
//                JTextField adjustmentDateField = new JTextField(); // Current createdAt pre-filled
//                adjustmentDateField.setEditable(false); // Read-only
//
//                JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
//
//                adjustmentDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adjustment Details", TitledBorder.LEFT, TitledBorder.TOP, boldFont));
//
//                GridBagConstraints adjGbc = new GridBagConstraints();
//                adjGbc.insets = new Insets(5, 5, 5, 5);
//                adjGbc.fill = GridBagConstraints.HORIZONTAL;
//                adjGbc.weightx = 1.0;
//
//                adjGbc.gridx = 0;
//                adjGbc.gridy = 0;
//
//                stocksLabel.setFont(boldFont);
//                adjustmentDetailsPanel.add(stocksLabel, adjGbc);
//                adjGbc.gridx = 1;
//                adjustmentDetailsPanel.add(stocksField, adjGbc);
//
//                adjGbc.gridx = 0;
//                adjGbc.gridy = 1;
//                JLabel adjustmentTypeLabel = new JLabel("Adjustment Type *:");
//                adjustmentTypeLabel.setFont(boldFont);
//                adjustmentDetailsPanel.add(adjustmentTypeLabel, adjGbc);
//                adjGbc.gridx = 1;
//                adjustmentDetailsPanel.add(adjustmentTypeComboBox, adjGbc);
//
//                adjGbc.gridx = 0;
//                adjGbc.gridy = 2;
//                JLabel quantityLabel = new JLabel("Quantity *:");
//                quantityLabel.setFont(boldFont);
//                adjustmentDetailsPanel.add(quantityLabel, adjGbc);
//                adjGbc.gridx = 1;
//                adjustmentDetailsPanel.add(quantitySpinner, adjGbc);
//
//                // Initially, make the adjustmentDetailsPanel invisible
//                adjustmentDetailsPanel.setVisible(false);
//
//
//                // Adding components to the panel with updated bigger and bold labels
//                gbc.gridx = 0;
//                gbc.gridy = 0;
//                JLabel adjustmentNoLabel = new JLabel("Adjustment No:");
//                adjustmentNoLabel.setFont(boldFont);
//                panel.add(adjustmentNoLabel, gbc);
//                gbc.gridx = 1;
//
//                panel.add(adjustmentNoField, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy = 1;
//                JLabel adjustmentReasonLabel = new JLabel("Adjustment Reason:");
//                adjustmentReasonLabel.setFont(boldFont);
//                panel.add(adjustmentReasonLabel, gbc);
//                gbc.gridx = 1;
//                panel.add(adjustmentReasonField, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy = 2;
//                JLabel selectProductsLabel = new JLabel("Product :");
//                selectProductsLabel.setFont(boldFont);
//                panel.add(selectProductsLabel, gbc);
//                gbc.gridx = 1;
//                panel.add(selectProductsComboBox, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy = 3;
//                gbc.gridwidth = 2;
//                panel.add(adjustmentDetailsPanel, gbc); // Add the group panel with stocks, adjustment type, and quantity
//
//                gbc.gridwidth = 1;
//                gbc.gridx = 0;
//                gbc.gridy = 4;
//                JLabel noteLabel = new JLabel("Note:");
//                noteLabel.setFont(boldFont);
//                panel.add(noteLabel, gbc);
//                gbc.gridx = 1;
//                panel.add(noteScrollPane, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy = 5;
//                JLabel adjustmentDateLabel = new JLabel("Adjustment Date:");
//                adjustmentDateLabel.setFont(boldFont);
//                panel.add(adjustmentDateLabel, gbc);
//                gbc.gridx = 1;
//                panel.add(adjustmentDateField, gbc);
//
//                gbc.gridx = 0;
//                gbc.gridy = 6;
//                JLabel statusLabel = new JLabel("Status:");
//                statusLabel.setFont(boldFont);
//                panel.add(statusLabel, gbc);
//                gbc.gridx = 1;
//                panel.add(statusComboBox, gbc);
//
//                SwingWorker<InventoryAdjustmentResponseDto, Void> inventoryAdjustmentWorker = new SwingWorker<InventoryAdjustmentResponseDto, Void>() {
//                    @Override
//                    protected InventoryAdjustmentResponseDto doInBackground() throws Exception {
//                        SwingUtilities.invokeLater(() -> {
//                            selectProductsComboBox.addItem("Loading...");
//                            selectProductsComboBox.setSelectedItem("Loading...");
//
//                            adjustmentNoField.setText("Loading...");
//
//                            adjustmentReasonField.setText("Loading...");
//
//                            stocksField.setText("Loading...");
//
//                            adjustmentTypeComboBox.removeAllItems();
//                            adjustmentTypeComboBox.addItem("Loading...");
//                            adjustmentTypeComboBox.setSelectedItem("Loading...");
//
//                            noteField.setText("Loading...");
//
//                            statusComboBox.removeAllItems();
//                            statusComboBox.addItem("Loading...");
//                            statusComboBox.setSelectedItem("Loading...");
//
//                            adjustmentDateField.setText("Loading...");
//
//
//                            adjustmentDetailsPanel.setVisible(true);
//                        });
//                        var inventoryAdjustment = inventoryAdjustmentService.getValidInventoryAdjustmentById(inventoryAdjustmentId);
//                        return inventoryAdjustment;
//                    }
//
//                    @Override
//                    protected void done() {
//                        try {
//                            var inventoryAdjustment = get();
//
//                            SwingUtilities.invokeLater(() -> {
//                                selectProductsComboBox.removeAllItems();
//                                selectProductsComboBox.addItem(inventoryAdjustment.product().getName());
//                                selectProductsComboBox.setSelectedItem(inventoryAdjustment.product().getName());
//                                selectProductsComboBox.setEnabled(false);
//
//                                adjustmentNoField.setText(inventoryAdjustment.code());
//
//                                adjustmentReasonField.setText(inventoryAdjustment.reason());
//
//                                stocksField.setText(String.valueOf(inventoryAdjustment.product().getStock()));
//
//                                adjustmentTypeComboBox.removeAllItems();
//                                adjustmentTypeComboBox.addItem("Increment");
//                                adjustmentTypeComboBox.addItem("Decrement");
//                                adjustmentTypeComboBox.setSelectedItem(inventoryAdjustment.type().equals(InventoryAdjustmentType.INCREMENT) ? "Increment" : "Decrement");
//
//                                noteField.setText(inventoryAdjustment.note());
//
//                                statusComboBox.removeAllItems();
//                                statusComboBox.addItem("Active");
//                                statusComboBox.addItem("Inactive");
//                                statusComboBox.setSelectedItem(inventoryAdjustment.status().equals(InventoryAdjustmentStatus.ACTIVE) ? "Active" : "Inactive");
//
//                                adjustmentDateField.setText(String.valueOf(inventoryAdjustment.date()));
//
//                            });
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                inventoryAdjustmentWorker.execute();
//
//
//                // Set panel size
//                panel.setPreferredSize(new Dimension(600, 600));
//
//                int result = JOptionPane.showConfirmDialog(null, panel, "Create Adjustment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//                if (result == JOptionPane.OK_OPTION && adjustmentDetailsPanel.isVisible()) {
//                    String reason = adjustmentReasonField.getText().trim();
//                    int quantity = (Integer) quantitySpinner.getValue();
//                    String note = noteField.getText();
//                    String status = (String) statusComboBox.getSelectedItem();
//
//                    String typeOfAdjustment = (String) adjustmentTypeComboBox.getSelectedItem();
//                    UpdateInventoryAdjustmentRequestDto dto = new UpdateInventoryAdjustmentRequestDto(
//                            inventoryAdjustmentId,
//                            reason,
//                            typeOfAdjustment.equals("Increment") ? InventoryAdjustmentType.INCREMENT : InventoryAdjustmentType.DECREMENT,
//                            quantity,
//                            note,
//                            status.equals("Active") ? InventoryAdjustmentStatus.ACTIVE : InventoryAdjustmentStatus.INACTIVE
//                    );
//
//                    if (typeOfAdjustment.equals("Increment") || typeOfAdjustment.equals("Decrement")) {
//                        inventoryAdjustmentService.update(dto);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Please select adjustment type first", "Warning", JOptionPane.INFORMATION_MESSAGE);
//                        return;
//                    }
//                    JOptionPane.showMessageDialog(null, "Inventory Adjustment is successfully Updated", "Updated", JOptionPane.PLAIN_MESSAGE);
//                    loadInventoryAdjustment();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please select a product first", "Warning", JOptionPane.INFORMATION_MESSAGE);
//                }
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
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
        loadInventoryAdjustment();
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
    }

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

        String reason = jTextField1.getText();

        if (reason.isEmpty()) {
            loadInventoryAdjustment();
            return;
        }

        InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();
        SwingWorker<List<InventoryAdjustmentResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<InventoryAdjustmentResponseDto> doInBackground() throws Exception {
                return inventoryAdjustmentService.getAllValidInventoryAdjustmentByQuery(reason);
            }

            @Override
            protected void done() {
                try {
                    var inventoryAdjustments = get();
                    for (int i = 0; i < inventoryAdjustments.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                inventoryAdjustments.get(i).id(),
                                inventoryAdjustments.get(i).product().getName(),
                                inventoryAdjustments.get(i).code(),
                                inventoryAdjustments.get(i).user().getName(),
                                inventoryAdjustments.get(i).reason(),
                                inventoryAdjustments.get(i).date()
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void loadInventoryAdjustment() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();
        SwingWorker<List<InventoryAdjustmentResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<InventoryAdjustmentResponseDto> doInBackground() throws Exception {
                return inventoryAdjustmentService.getAllValidInventoryAdjustment(50);
            }

            @Override
            protected void done() {
                try {
                    var inventoryAdjustments = get();
                    for (int i = 0; i < inventoryAdjustments.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                inventoryAdjustments.get(i).id(),
                                inventoryAdjustments.get(i).product().getName(),
                                inventoryAdjustments.get(i).code(),
                                inventoryAdjustments.get(i).user().getName(),
                                inventoryAdjustments.get(i).reason(),
                                inventoryAdjustments.get(i).date()
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

        jTextField1.setText("");

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "#", "ID", "Product", "Adjustment #", "User", "Reason", "Date", "Actions"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, true
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
// Create a modal dialog
        JDialog paymentDialog = new JDialog((JFrame) null, "Select Product Type", true);
        paymentDialog.setSize(400, 300);
        paymentDialog.setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column, with spacing

        // Create buttons with styling
        JButton simpleProductButton = new JButton("Simple Product");
        JButton variableProductButton = new JButton("Variable Product");

        // Apply custom styles to the buttons
        styleButton(simpleProductButton);
        styleButton(variableProductButton);

        // Adjust button size
        simpleProductButton.setPreferredSize(new Dimension(200, 60)); // Adjust width and height
        variableProductButton.setPreferredSize(new Dimension(200, 60)); // Adjust width and height

        // Add action listeners for each button
        simpleProductButton.addActionListener(e -> {
            paymentDialog.dispose();
            showSimpleProductForm();
        });

        variableProductButton.addActionListener(e -> {
            paymentDialog.dispose();
            showVariableProductForm();
        });

        // Add buttons to the panel
        buttonPanel.add(simpleProductButton);
        buttonPanel.add(variableProductButton);

        // Add padding around the buttons
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        paddedPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the padded panel to the dialog
        paymentDialog.add(paddedPanel, BorderLayout.CENTER);

        // Center the dialog relative to the parent frame and make it visible
        paymentDialog.setLocationRelativeTo(null);
        paymentDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void showSimpleProductForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        ProductService productService = new ProductService();

        // Fonts
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Category Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        // Category ComboBox
        gbc.gridx = 1;
        Map<Integer, Integer> categoryMap = new HashMap<>();
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        panel.add(categoryComboBox, gbc);

        // Item Name Label
        gbc.gridx = 2;
        JLabel itemNameLabel = new JLabel("Item Name");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        Map<Integer, Integer> productMap = new HashMap<>();

        // Item Name ComboBox
        gbc.gridx = 3;
        JComboBox<String> itemComboBox = new JComboBox<>();
        itemComboBox.setEnabled(false);
        itemComboBox.setFont(regularFont);
        panel.add(itemComboBox, gbc);

        // load product category
        loadCategoryForComboBox(categoryComboBox, categoryMap);

        categoryComboBox.addActionListener(e -> {
            int categoryIndex = categoryComboBox.getSelectedIndex();
            Integer categoryId = categoryMap.get(categoryIndex);
            SwingWorker<List<Product>, Void> productWorker = new SwingWorker<>() {
                @Override
                protected List<Product> doInBackground() {
                    itemComboBox.addItem("Loading...");
                    itemComboBox.setEnabled(false);
                    return productService.getAllValidProductByCategoryIdWithoutDto(categoryId, ProductType.SIMPLE);
                }

                @Override
                protected void done() {
                    try {
                        var products = get();
                        SwingUtilities.invokeLater(() -> {
                            itemComboBox.removeAllItems();
                            itemComboBox.setEnabled(true);
                            itemComboBox.addItem("Select Product");

                            int i = 1;
                            for (var product : products) {
                                itemComboBox.addItem(product.getName());
                                productMap.put(i, product.getId());
                                i++;
                            }
                        });
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        itemComboBox.setEnabled(true);
                    }
                }
            };

            if (categoryId != null) {
                productWorker.execute();
            }
        });

        // Current Stock Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel currentStockLabel = new JLabel("Current Stock");
        currentStockLabel.setFont(boldFont);
        panel.add(currentStockLabel, gbc);

        // Current Stock TextField
        gbc.gridx = 1;
        JTextField currentStockField = new JTextField(10);
        currentStockField.setEnabled(false);
        currentStockField.setFont(regularFont);
        panel.add(currentStockField, gbc);

        // Adjusted Stock Label
        gbc.gridx = 2;
        JLabel adjustedStockLabel = new JLabel("Adjusted Stock *");
        adjustedStockLabel.setFont(boldFont);
        panel.add(adjustedStockLabel, gbc);

        // Adjusted Stock TextField
        gbc.gridx = 3;
        JTextField adjustedStockField = new JTextField(10);
        adjustedStockField.setFont(regularFont);
        panel.add(adjustedStockField, gbc);

        // Adjustment Type Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel adjustmentTypeLabel = new JLabel("Adjustment Type");
        adjustmentTypeLabel.setFont(boldFont);
        panel.add(adjustmentTypeLabel, gbc);

        // Adjustment Type ComboBox spanning the right
        gbc.gridx = 1;
        gbc.gridwidth = 3; // Spanning the rest of the row
        JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(new String[]{"Select Adjustment Type", "Increment", "Decrement"});
        adjustmentTypeComboBox.setFont(regularFont);
        panel.add(adjustmentTypeComboBox, gbc);
        gbc.gridwidth = 1;

        // Note Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel noteLabel = new JLabel("Note/Reason");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        // Note TextArea
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        itemComboBox.addActionListener(e -> {
            int productIndex = itemComboBox.getSelectedIndex();
            Integer productId = productMap.get(productIndex);
            SwingWorker<Product, Void> worker = new SwingWorker<Product, Void>() {
                @Override
                protected Product doInBackground() throws Exception {
                    SwingUtilities.invokeLater(() -> {
                        currentStockField.setEnabled(false);
                        currentStockField.setText("Loading...");
                    });
                    return productService.getValidProductByIdWithoutDto(productId);
                }

                @Override
                protected void done() {
                    try {
                        var product = get();

                        SwingUtilities.invokeLater(() -> {
                            currentStockField.setText(product.getName());
                            currentStockField.setEnabled(true);

                            currentStockField.setText(String.valueOf(product.getStock()));
                        });
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };

            if (productId != null) {
                worker.execute();
            }
        });


        int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int productIndex = itemComboBox.getSelectedIndex();
            Integer productId = productMap.get(productIndex);
            String type = (String) adjustmentTypeComboBox.getSelectedItem();

            if (productId != null) {
                assert type != null;
                if (!type.equals("Select Adjustment Type")) {
                    InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();

                    AddInventoryAdjustmentDtoForSimpleProduct dto = new AddInventoryAdjustmentDtoForSimpleProduct(
                            productId,
                            noteArea.getText(),
                            type.equals("Increment") ? InventoryAdjustmentType.INCREMENT : InventoryAdjustmentType.DECREMENT,
                            Integer.parseInt(adjustedStockField.getText())
                    );
                    try {
                        inventoryAdjustmentService.addSimpleProduct(dto);
                        JOptionPane.showMessageDialog(null, "Product Adjusted");
                    } catch (InputMismatchException e) {
                        JOptionPane.showMessageDialog(null, "Any character is not allowed");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter number only");
                    }
                }
            }
        }

    }

    private void loadCategoryForComboBox(
            JComboBox<String> categoryCombo,
            Map<Integer, Integer> categoryMap
    ) {
        ProductCategoryService productCategoryService = new ProductCategoryService();
        SwingWorker<List<ProductCategoryResponseDto>, Void> worker = new SwingWorker<List<ProductCategoryResponseDto>, Void>() {
            @Override
            protected List<ProductCategoryResponseDto> doInBackground() throws Exception {
                return productCategoryService.getAllValidProductCategories();
            }

            @Override
            protected void done() {
                try {
                    var categories = get();
                    categoryCombo.addItem("Select Category");
                    int i = 1;
                    for (var category : categories) {
                        categoryCombo.addItem(category.name());
                        categoryMap.put(i, category.id());
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
    }

    private void showVariableProductForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define consistent dimensions
        Dimension fieldSize = new Dimension(200, 25);
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        Map<Integer, Integer> categoryMap = new HashMap<>();

        // Category Label and ComboBox
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        categoryComboBox.setPreferredSize(fieldSize);
        panel.add(categoryComboBox, gbc);

        loadCategoryForComboBox(categoryComboBox, categoryMap);

        // Item Name Label and ComboBox
        gbc.gridx = 2;
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        Map<Integer, Integer> productMap = new HashMap<>();

        gbc.gridx = 3;
        JComboBox<String> itemComboBox = new JComboBox<>();
        itemComboBox.setFont(regularFont);
        itemComboBox.setPreferredSize(fieldSize);
        panel.add(itemComboBox, gbc);

        categoryComboBox.addActionListener(e -> {
            int categoryIndex = categoryComboBox.getSelectedIndex();
            Integer categoryId = categoryMap.get(categoryIndex);
            ProductService productService = new ProductService();
            SwingWorker<List<Product>, Void> productWorker = new SwingWorker<>() {
                @Override
                protected List<Product> doInBackground() {
                    itemComboBox.addItem("Loading...");
                    itemComboBox.setEnabled(false);
                    return productService.getAllValidProductByCategoryIdWithoutDto(categoryId, ProductType.VARIABLE);
                }

                @Override
                protected void done() {
                    try {
                        var products = get();
                        SwingUtilities.invokeLater(() -> {
                            itemComboBox.removeAllItems();
                            itemComboBox.setEnabled(true);
                            itemComboBox.addItem("Select Product");

                            int i = 1;
                            for (var product : products) {
                                itemComboBox.addItem(product.getName());
                                productMap.put(i, product.getId());
                                i++;
                            }
                        });
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        itemComboBox.setEnabled(true);
                    }
                }
            };

            if (categoryId != null) {
                productWorker.execute();
            }
        });

        // Adjustment Type Label and ComboBox
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel adjustmentTypeLabel = new JLabel("Adjustment Type");
        adjustmentTypeLabel.setFont(boldFont);
        panel.add(adjustmentTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JComboBox<String> adjustmentTypeComboBox = new JComboBox<>(new String[]{"Increment", "Decrement"});
        adjustmentTypeComboBox.setFont(regularFont);
        adjustmentTypeComboBox.setPreferredSize(fieldSize);
        panel.add(adjustmentTypeComboBox, gbc);

        gbc.gridwidth = 1;

        // Note Section
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 20);
        noteArea.setFont(regularFont);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteScrollPane.setPreferredSize(new Dimension(450, 75));
        panel.add(noteScrollPane, gbc);

        // Attributes and Variations Section
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        JLabel attributeLabel = new JLabel("Attributes and Variations");
        attributeLabel.setFont(boldFont);
        panel.add(attributeLabel, gbc);

        gbc.gridy = 5;
        JPanel mainAttributePanel = new JPanel();
        mainAttributePanel.setLayout(new BoxLayout(mainAttributePanel, BoxLayout.Y_AXIS));

        JScrollPane mainScrollPane = new JScrollPane(mainAttributePanel);
        mainScrollPane.setPreferredSize(new Dimension(800, 200));
        panel.add(mainScrollPane, gbc);

        List<JScrollPane> attributeScrollPanes = new ArrayList<>();
        List<JScrollPane> variableScrollPanes = new ArrayList<>();

        itemComboBox.addActionListener(e -> {
            int index = itemComboBox.getSelectedIndex();
            Integer id = productMap.get(index);
            ProductService productService = new ProductService();
            SwingWorker<Product, Void> worker = new SwingWorker<>() {
                @Override
                protected Product doInBackground() {
                    return productService.getValidProductByIdWithoutDto(id);
                }

                @Override
                protected void done() {
                    try {
                        var product = get();
                        SwingUtilities.invokeLater(() -> {

                        });

                        for (var attribute : product.getProductAttributes()) {
                            // Create a panel to hold the new attribute section
                            JPanel attributeSection = new JPanel();
                            attributeSection.setLayout(new BoxLayout(attributeSection, BoxLayout.Y_AXIS));

                            // Wrap the attribute section in its own scroll pane
                            JScrollPane attributeScrollPane = new JScrollPane(attributeSection);
                            attributeScrollPane.setPreferredSize(new Dimension(750, 125));
                            attributeScrollPane.setBorder(BorderFactory.createTitledBorder("Attribute Section"));

                            // Add this scroll pane to the main attribute panel
                            mainAttributePanel.add(attributeScrollPane);
                            attributeScrollPanes.add(attributeScrollPane);
                            variableScrollPanes.add(attributeScrollPane);

                            // Attribute Name and Add Variation Button
                            JPanel attributeRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                            JTextField attributeField = new JTextField(15);
                            attributeField.setEnabled(false);
                            attributeField.setText(attribute.getName());

                            attributeRow.add(new JLabel("Attribute Name: "));
                            attributeRow.add(attributeField);
                            attributeSection.add(attributeRow);

                            // Variations Panel
                            JPanel variationsPanel = new JPanel();
                            variationsPanel.setLayout(new BoxLayout(variationsPanel, BoxLayout.Y_AXIS));
                            attributeSection.add(variationsPanel);

                            mainAttributePanel.revalidate();
                            mainAttributePanel.repaint();
                            for (var variation : attribute.getProductVariations()) {
                                JPanel variationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                JTextField variationField = new JTextField(10);
                                JTextField quantityField = new JTextField(5);
                                JTextField updatedQuantity = new JTextField(7);

                                variationField.setText(variation.getVariation());
                                variationField.setEnabled(false);

                                quantityField.setEnabled(false);
                                quantityField.setText(String.valueOf(variation.getQuantity()));

                                updatedQuantity.setText("0");

                                variationRow.add(new JLabel("Variation: "));
                                variationRow.add(variationField);
                                variationRow.add(new JLabel("Current Qty: "));
                                variationRow.add(quantityField);
                                variationRow.add(new JLabel("Update Qty: "));
                                variationRow.add(updatedQuantity);
                                variationsPanel.add(variationRow);

                                variationsPanel.revalidate();
                                variationsPanel.repaint();
                            }
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
            if (id != null) {
                worker.execute();
            }

        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Create Variable Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<ProductAttribute> productVariables = getAllProductAttributesFromJScrollPane(attributeScrollPanes);

            try {
                String note = noteArea.getText();
                String type = (String) adjustmentTypeComboBox.getSelectedItem();
                int index = itemComboBox.getSelectedIndex();
                Integer productId = productMap.get(index);

                ProductService productService = new ProductService();


                if (productId == null) {
                    JOptionPane.showMessageDialog(null, "Please make sure to Select Product First", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<ProductAttribute> productAttributes = new ArrayList<>();
                    for (var productAttribute : productVariables) {
                        productAttributes.add(productAttribute);
                    }
                    assert type != null;
                    AddInventoryAdjustmentDtoForVariableProduct dto = new AddInventoryAdjustmentDtoForVariableProduct(
                            productId,
                            note,
                            type.equals("Increment") ? InventoryAdjustmentType.INCREMENT : InventoryAdjustmentType.DECREMENT,
                            productAttributes
                    );
                    InventoryAdjustmentService inventoryAdjustmentService = new InventoryAdjustmentService();
                    SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                        @Override
                        protected Boolean doInBackground() {
                            try {
                                inventoryAdjustmentService.addVariableProduct(dto);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }

                        @Override
                        protected void done() {
                            try {
                                boolean result = get();

                                if (result)
                                    JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                else
                                    JOptionPane.showMessageDialog(null, "Product Creation Error!", "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    };
                    worker.execute();
                    loadInventoryAdjustment();
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Any character is not allowed");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter number only");
            }
        }
    }

    private List<ProductAttribute> getAllProductAttributesFromJScrollPane(List<JScrollPane> jScrollPanes) {
        List<ProductAttribute> allAttributes = new ArrayList<>();
        try {
            // Iterate over all attribute scroll panes
            for (JScrollPane attributeScrollPane : jScrollPanes) {
                // Get the main panel of the current scroll pane
                JPanel attributeSection = (JPanel) attributeScrollPane.getViewport().getView();

                // Attribute row is the first child of the attribute section
                JPanel attributeRow = (JPanel) attributeSection.getComponent(0);
                JTextField attributeField = (JTextField) attributeRow.getComponent(1); // Attribute name field

                // Get the attribute name
                String attributeName = attributeField.getText();
                if (attributeName.isEmpty()) {
                    throw new NullPointerException("Attribute name cannot be empty.");
                }

                // Create a new ProductAttribute and set its name
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setName(attributeName);

                // Variations panel is the second child of the attribute section
                JPanel variationsPanel = (JPanel) attributeSection.getComponent(1);

                // Iterate over all variation rows in the variations panel
                for (Component component : variationsPanel.getComponents()) {
                    if (component instanceof JPanel variationRow) {
                        // Extract values from JTextFields in the variationRow
                        JTextField quantityField = (JTextField) variationRow.getComponent(5);

                        // Validate fields
                        int quantity = Integer.parseInt(quantityField.getText());

                        // Create and add the variation to the list
                        ProductVariation variation = new ProductVariation();
                        variation.setQuantity(quantity);

                        productAttribute.addProductVariation(variation);
                    }
                }

                // Add the product attribute to the list
                allAttributes.add(productAttribute);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "You must enter valid numeric values for quantity, cost, and SRP.");
        }
        return allAttributes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class UpdateVariationQuantity {
        private int newQuantity;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(150, 183, 162)); // Extracted color
        button.setForeground(Color.WHITE);             // Text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Adjusted padding for height
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor
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

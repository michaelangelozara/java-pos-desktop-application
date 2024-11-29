
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.product.*;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProductList extends javax.swing.JPanel {

    private Timer timer;

    public ProductList() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int id = (Integer) model.getValueAt(row, 1);

                ProductService productService = new ProductService();
                SwingWorker<ProductResponseDto, Void> worker = new SwingWorker<>() {
                    @Override
                    protected ProductResponseDto doInBackground() {
                        return productService.getValidProductById(id);
                    }

                    @Override
                    protected void done() {
                        try {
                            var product = get();
                            if (product.type().equals(ProductType.SIMPLE)) {
                                showSimpleProductFormEdit(product);
                            } else {
                                showVariableProductFormEdit(product);
                            }
                            loadProducts();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                worker.execute();
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
                    int productId = (Integer) model.getValueAt(row, 1);
                    ProductService productService = new ProductService();
                    productService.delete(productId);
                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            }

            @Override
            public void onView(int row) {
            }
        };
        makeCellCenter(table);
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadProducts();
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

    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Product List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Products");

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
                        "#", "ID", "Code", "Category", "Name", "Unit", "Selling Price", "Product Type", "Status", "Action"
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
            table.getColumnModel().getColumn(8).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 1224, Short.MAX_VALUE)))
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
            loadProducts();
            return;
        }

        ProductService productService = new ProductService();
        SwingWorker<List<ProductResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ProductResponseDto> doInBackground() throws Exception {
                return productService.getAllValidProductByName(name);
            }

            @Override
            protected void done() {
                try {
                    var products = get();
                    for (int i = 0; i < products.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                products.get(i).id(),
                                products.get(i).code(),
                                products.get(i).category().getName(),
                                products.get(i).name(),
                                products.get(i).unit().name(),
                                products.get(i).sellingPrice(),
                                products.get(i).type().name(),
                                products.get(i).status().name()
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
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(150, 183, 162)); // Extracted color
        button.setForeground(Color.WHITE);             // Text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Adjusted padding for height
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor
    }//GEN-LAST:event_jButton1ActionPerformed


    private void showSimpleProductFormEdit(ProductResponseDto product) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for a more flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Fonts
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Label spans only one column
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        // Item Name TextField (smaller size)
        gbc.gridx = 1;
        gbc.gridwidth = 1; // Smaller text field spans only 1 column
        JTextField itemNameField = new JTextField(10); // Set to 10 columns for smaller width
        itemNameField.setFont(regularFont);
        itemNameField.setText(product.name());
        panel.add(itemNameField, gbc);

        // Category Label
        gbc.gridx = 2;
        gbc.gridwidth = 1; // Label spans only one column
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        // Category ComboBox
        gbc.gridx = 3;
        gbc.gridwidth = 1; // ComboBox spans only 1 column
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        panel.add(categoryComboBox, gbc);

        Map<Integer, Integer> categoryMap = new HashMap<>();
        ProductCategoryService productCategoryService = new ProductCategoryService();
        SwingWorker<List<ProductCategoryResponseDto>, Void> productWorker = new SwingWorker<>() {
            @Override
            protected List<ProductCategoryResponseDto> doInBackground() {
                categoryComboBox.removeAllItems();
                categoryComboBox.setEnabled(false);
                categoryComboBox.addItem("Loading...");
                categoryComboBox.setSelectedItem("Loading...");
                return productCategoryService.getAllValidProductCategories();
            }

            @Override
            protected void done() {
                try {
                    var categories = get();
                    categoryComboBox.removeAllItems();
                    categoryComboBox.setEnabled(true);
                    for (int i = 0; i < categories.size(); i++) {
                        categoryComboBox.addItem(categories.get(i).name());
                        categoryMap.put(i, categories.get(i).id());
                    }

                    categoryComboBox.setSelectedItem(product.category().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        productWorker.execute();

        // set the selected category base on the product
        categoryComboBox.setSelectedItem(product.category().getName());

        // Reset gridwidth for subsequent components
        gbc.gridwidth = 1;

        // Stock *
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel itemStockLabel = new JLabel("Stock *");
        itemStockLabel.setFont(boldFont);
        panel.add(itemStockLabel, gbc);

        gbc.gridx = 1;
        JTextField itemStockField = new JTextField(15);
        itemStockField.setFont(regularFont);
        itemStockField.setText(String.valueOf(product.stock()));
        panel.add(itemStockField, gbc);

        // Subcategory
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        panel.add(unitCombo, gbc);

        unitCombo.setSelectedItem(product.unit().equals(ProductUnit.PIECE) ? "Per Piece" : "12 pcs");

        // Cost of Goods
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel costLabel = new JLabel("Cost of Goods");
        costLabel.setFont(boldFont);
        panel.add(costLabel, gbc);

        gbc.gridx = 1;
        JTextField costField = new JTextField(15);
        costField.setFont(regularFont);
        costField.setText(String.valueOf(product.purchasePrice()));
        panel.add(costField, gbc);

        // Suggested Retail Price *
        gbc.gridx = 2;
        JLabel priceLabel = new JLabel("Suggested Retail Price *");
        priceLabel.setFont(boldFont);
        panel.add(priceLabel, gbc);

        gbc.gridx = 3;
        JTextField priceField = new JTextField(15);
        priceField.setFont(regularFont);
        priceField.setText(String.valueOf(product.sellingPrice()));
        panel.add(priceField, gbc);

        // Note
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteArea.setText(product.note());
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel alertLabel = new JLabel("Alert Quantity");
        alertLabel.setFont(boldFont);
        panel.add(alertLabel, gbc);

        gbc.gridx = 1;
        JTextField alertField = new JTextField(15);
        alertField.setText(String.valueOf(product.alertQuantity()));
        alertField.setFont(regularFont);
        panel.add(alertField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select Status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        panel.add(statusCombo, gbc);

        statusCombo.setSelectedItem(product.status().equals(ProductStatus.ACTIVE) ? "Active" : "Inactive");

        // Image
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel imageLabel = new JLabel("Image");
        imageLabel.setFont(boldFont);
        panel.add(imageLabel, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose File");
        imageButton.setFont(regularFont);

        JLabel imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(100, 100));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        String[] image = {""};

        imageButton.addActionListener(e -> {
            image[0] = chooseImage(imagePreview);
        });
        panel.add(imageButton, gbc);

        gbc.gridx = 2;
        panel.add(imagePreview, gbc);

        // Display the form
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = itemNameField.getText();
                int stock = Integer.parseInt(itemStockField.getText());
                ProductUnit unit = Objects.equals(unitCombo.getSelectedItem(), "Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN;
                String note = noteArea.getText();
                int alertQuantity = Integer.parseInt(alertField.getText());
                ProductStatus status = Objects.equals(statusCombo.getSelectedItem(), "Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE;
                int selectedIndex = categoryComboBox.getSelectedIndex();
                int categoryId = categoryMap.get(selectedIndex);
                BigDecimal purchasePrice = new BigDecimal(costField.getText());
                BigDecimal sellingPrice = new BigDecimal(priceField.getText());

                ProductService productService = new ProductService();

                UpdateProductRequestDto dto = new UpdateProductRequestDto(
                        product.id(),
                        categoryId,
                        name,
                        unit,
                        note,
                        alertQuantity,
                        status,
                        image[0],
                        purchasePrice,
                        sellingPrice,
                        stock
                );

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    productService.update(dto, new HashSet<>());
                    // Process the input here
                    JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Any character is not allowed");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter number only");
            }
        }
    }

    private void showSimpleProductForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for a more flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Fonts
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Label spans only one column
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        // Item Name TextField (smaller size)
        gbc.gridx = 1;
        gbc.gridwidth = 1; // Smaller text field spans only 1 column
        JTextField itemNameField = new JTextField(10); // Set to 10 columns for smaller width
        itemNameField.setFont(regularFont);
        panel.add(itemNameField, gbc);

        // Category Label
        gbc.gridx = 2;
        gbc.gridwidth = 1; // Label spans only one column
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        // Category ComboBox
        gbc.gridx = 3;
        gbc.gridwidth = 1; // ComboBox spans only 1 column
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        panel.add(categoryComboBox, gbc);

        Map<Integer, Integer> categoryMap = new HashMap<>();
        // load product category
        loadCategoryForComboBox(categoryComboBox, categoryMap);

        // Reset gridwidth for subsequent components
        gbc.gridwidth = 1;

        // Stock *
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel itemStockLabel = new JLabel("Stock *");
        itemStockLabel.setFont(boldFont);
        panel.add(itemStockLabel, gbc);

        gbc.gridx = 1;
        JTextField itemStockField = new JTextField(15);
        itemStockField.setFont(regularFont);
        panel.add(itemStockField, gbc);

        // Subcategory
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        panel.add(unitCombo, gbc);

        // Cost of Goods
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel costLabel = new JLabel("Cost of Goods");
        costLabel.setFont(boldFont);
        panel.add(costLabel, gbc);

        gbc.gridx = 1;
        JTextField costField = new JTextField(15);
        costField.setFont(regularFont);
//    costField.setEnabled(false);
        panel.add(costField, gbc);

        // Suggested Retail Price *
        gbc.gridx = 2;
        JLabel priceLabel = new JLabel("Suggested Retail Price *");
        priceLabel.setFont(boldFont);
        panel.add(priceLabel, gbc);

        gbc.gridx = 3;
        JTextField priceField = new JTextField(15);
        priceField.setFont(regularFont);
//    priceField.setEnabled(false);
        panel.add(priceField, gbc);

        // Note
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel alertLabel = new JLabel("Alert Quantity");
        alertLabel.setFont(boldFont);
        panel.add(alertLabel, gbc);

        gbc.gridx = 1;
        JTextField alertField = new JTextField(15);
        alertField.setFont(regularFont);
        panel.add(alertField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select Status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        panel.add(statusCombo, gbc);

        // Image
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel imageLabel = new JLabel("Image");
        imageLabel.setFont(boldFont);
        panel.add(imageLabel, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose File");
        imageButton.setFont(regularFont);

        JLabel imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(100, 100));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        String[] image = {""};

        imageButton.addActionListener(e -> {
            image[0] = chooseImage(imagePreview);
        });
        panel.add(imageButton, gbc);

        gbc.gridx = 2;
        panel.add(imagePreview, gbc);

        // Display the form
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = itemNameField.getText();
                int stock = Integer.parseInt(itemStockField.getText());
                ProductUnit unit = Objects.equals(unitCombo.getSelectedItem(), "Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN;
                String note = noteArea.getText();
                int alertQuantity = Integer.parseInt(alertField.getText());
                ProductStatus status = Objects.equals(statusCombo.getSelectedItem(), "Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE;
                int selectedIndex = categoryComboBox.getSelectedIndex();
                int categoryId = categoryMap.get(selectedIndex);
                BigDecimal purchasePrice = new BigDecimal(costField.getText());
                BigDecimal sellingPrice = new BigDecimal(priceField.getText());

                ProductService productService = new ProductService();

                AddProductRequestDto dto = new AddProductRequestDto(
                        categoryId,
                        name,
                        stock,
                        unit,
                        note,
                        alertQuantity,
                        status,
                        image[0],
                        ProductType.SIMPLE,
                        purchasePrice,
                        sellingPrice
                );

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    productService.add(dto, new HashSet<>());
                    // Process the input here
                    JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Any character is not allowed");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter number only");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
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

    private String chooseImage(JLabel imagePreview) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath()).getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imagePreview.setIcon(imageIcon);

                // Convert the image file to a Base64 string
                byte[] imageBytes = readFileToByteArray(selectedFile);
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to load image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return "";
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    private void showVariableProductFormEdit(ProductResponseDto product) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int fieldWidth = 15;
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        JTextField itemNameField = new JTextField(10);
        itemNameField.setText(product.name());
        itemNameField.setFont(regularFont);
        panel.add(itemNameField, gbc);

        // Category Label
        gbc.gridx = 2;
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        panel.add(categoryComboBox, gbc);

        Map<Integer, Integer> categoryMap = new HashMap<>();
        // load categories
        ProductCategoryService productCategoryService = new ProductCategoryService();
        SwingWorker<List<ProductCategoryResponseDto>, Void> categoryWorker = new SwingWorker<>() {
            @Override
            protected List<ProductCategoryResponseDto> doInBackground() throws Exception {
                return productCategoryService.getAllValidProductCategories();
            }

            @Override
            protected void done() {
                try {
                    var categories = get();
                    categoryComboBox.removeAllItems();
                    categoryComboBox.setEnabled(true);
                    for (int i = 0; i < categories.size(); i++) {
                        categoryComboBox.addItem(categories.get(i).name());
                        categoryMap.put(i, categories.get(i).id());
                    }

                    categoryComboBox.setSelectedItem(product.category().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        categoryWorker.execute();

        // Stock
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel stockLabel = new JLabel("Stock *");
        stockLabel.setFont(boldFont);
        panel.add(stockLabel, gbc);

        gbc.gridx = 1;
        JTextField stockField = new JTextField(fieldWidth);
        stockField.setFont(regularFont);
        stockField.setText(String.valueOf(product.stock()));
        panel.add(stockField, gbc);

        // Unit
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        unitCombo.setPreferredSize(new Dimension(150, unitCombo.getPreferredSize().height));
        panel.add(unitCombo, gbc);

        unitCombo.setSelectedItem(product.unit().equals(ProductUnit.PIECE) ? "Per Piece" : "12 pcs");

        // Note
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteArea.setText(product.note());
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel alertLabel = new JLabel("Alert Quantity");
        alertLabel.setFont(boldFont);
        panel.add(alertLabel, gbc);

        gbc.gridx = 1;
        JTextField alertField = new JTextField(fieldWidth);
        alertField.setText(String.valueOf(product.alertQuantity()));
        alertField.setFont(regularFont);
        panel.add(alertField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select Status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        statusCombo.setPreferredSize(new Dimension(150, statusCombo.getPreferredSize().height));
        panel.add(statusCombo, gbc);
        statusCombo.setSelectedItem(product.status().equals(ProductStatus.ACTIVE) ? "Active" : "Inactive");

        // Attributes and Variations Section
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JLabel attributeLabel = new JLabel("Attributes and Variations");
        attributeLabel.setFont(boldFont);
        panel.add(attributeLabel, gbc);

        gbc.gridy = 6;
        JPanel mainAttributePanel = new JPanel();
        mainAttributePanel.setLayout(new BoxLayout(mainAttributePanel, BoxLayout.Y_AXIS));

        JScrollPane mainScrollPane = new JScrollPane(mainAttributePanel);
        mainScrollPane.setPreferredSize(new Dimension(800, 255));
        panel.add(mainScrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 7;
        JButton addAttributeButton = new JButton("Add Attribute");
        addAttributeButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addAttributeButton, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose Image");
        imageButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(imageButton, gbc);

        // Image Preview
        gbc.gridx = 2;
        JLabel imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(90, 100));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(imagePreview, gbc);

        String[] image = {""};

        // Action Listener for Image Button
        imageButton.addActionListener(e -> {
            image[0] = chooseImage(imagePreview);
        });

        List<JScrollPane> attributeScrollPanes = new ArrayList<>();
        List<JScrollPane> variableScrollPanes = new ArrayList<>();
        Map<String, Integer> attributeMap = new HashMap<>();
        Map<String, Integer> variationMap = new HashMap<>();
        var fetchedProductAttributes = product.productAttributes();

        if (fetchedProductAttributes != null) {
            for (var attribute : fetchedProductAttributes) {
                attributeMap.put(attribute.getName(), attribute.getId());

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
                JTextField attributeField = new JTextField(attribute.getName(), 15); // Pre-fill with attribute name
                JButton addVariationButton = new JButton("Add Variation");
                JButton removeAttributeButton = new JButton("Remove Attribute");

                attributeRow.add(new JLabel("Attribute Name: "));
                attributeRow.add(attributeField);
                attributeRow.add(addVariationButton);
                attributeRow.add(removeAttributeButton);
                attributeSection.add(attributeRow);

                // Variations Panel
                JPanel variationsPanel = new JPanel();
                variationsPanel.setLayout(new BoxLayout(variationsPanel, BoxLayout.Y_AXIS));
                attributeSection.add(variationsPanel);

                List<JPanel> variationRows = new ArrayList<>();

                for (var variation : attribute.getProductVariations()) {
                    variationMap.put(variation.getVariation(), variation.getId());

                    JPanel variationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JTextField variationField = new JTextField(variation.getVariation(), 10); // Pre-fill variation name
                    JTextField quantityField = new JTextField(String.valueOf(variation.getQuantity()), 5); // Pre-fill quantity
                    JTextField costField1 = new JTextField(String.valueOf(variation.getPurchasePrice()), 7); // Pre-fill cost
                    JTextField srpField = new JTextField(String.valueOf(variation.getSrp()), 7); // Pre-fill SRP
                    JButton removeVariationButton = new JButton("Remove");

                    variationRow.add(new JLabel("Variation: "));
                    variationRow.add(variationField);
                    variationRow.add(new JLabel("Qty: "));
                    variationRow.add(quantityField);
                    variationRow.add(new JLabel("Cost: "));
                    variationRow.add(costField1);
                    variationRow.add(new JLabel("SRP: "));
                    variationRow.add(srpField);
                    variationRow.add(removeVariationButton);
                    variationsPanel.add(variationRow);
                    variationRows.add(variationRow);

                    removeVariationButton.addActionListener(removeVariationEvent -> {
                        variationsPanel.remove(variationRow);
                        variationRows.remove(variationRow);
                        variationsPanel.revalidate();
                        variationsPanel.repaint();
                    });
                }

                addVariationButton.addActionListener(variationEvent -> {
                    JPanel variationRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JTextField variationField1 = new JTextField(10);
                    JTextField quantityField1 = new JTextField(5);
                    JTextField costField2 = new JTextField(7);
                    JTextField srpField1 = new JTextField(7); // SRP Field
                    JButton removeVariationButton1 = new JButton("Remove");

                    variationRow1.add(new JLabel("Variation: "));
                    variationRow1.add(variationField1);
                    variationRow1.add(new JLabel("Qty: "));
                    variationRow1.add(quantityField1);
                    variationRow1.add(new JLabel("Cost: "));
                    variationRow1.add(costField2);
                    variationRow1.add(new JLabel("SRP: "));
                    variationRow1.add(srpField1);
                    variationRow1.add(removeVariationButton1);
                    variationsPanel.add(variationRow1);
                    variationRows.add(variationRow1);

                    variationsPanel.revalidate();
                    variationsPanel.repaint();

                    removeVariationButton1.addActionListener(removeVariationEvent -> {
                        variationsPanel.remove(variationRow1);
                        variationRows.remove(variationRow1);
                        variationsPanel.revalidate();
                        variationsPanel.repaint();
                    });
                });

                removeAttributeButton.addActionListener(removeAttributeEvent -> {
                    mainAttributePanel.remove(attributeScrollPane);
                    attributeScrollPanes.remove(attributeScrollPane);
                    mainAttributePanel.revalidate();
                    mainAttributePanel.repaint();
                });

                mainAttributePanel.revalidate();
                mainAttributePanel.repaint();
            }
        }

        addAttributeButton.addActionListener(e -> {
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
            JButton addVariationButton = new JButton("Add Variation");
            JButton removeAttributeButton = new JButton("Remove Attribute");

            attributeRow.add(new JLabel("Attribute Name: "));
            attributeRow.add(attributeField);
            attributeRow.add(addVariationButton);
            attributeRow.add(removeAttributeButton);
            attributeSection.add(attributeRow);

            // Variations Panel
            JPanel variationsPanel = new JPanel();
            variationsPanel.setLayout(new BoxLayout(variationsPanel, BoxLayout.Y_AXIS));
            attributeSection.add(variationsPanel);

            List<JPanel> variationRows = new ArrayList<>();
            addVariationButton.addActionListener(variationEvent -> {
                JPanel variationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JTextField variationField = new JTextField(10);
                JTextField quantityField = new JTextField(5);
                JTextField costField1 = new JTextField(7);
                JTextField srpField = new JTextField(7); // SRP Field
                JButton removeVariationButton = new JButton("Remove");

                variationRow.add(new JLabel("Variation: "));
                variationRow.add(variationField);
                variationRow.add(new JLabel("Qty: "));
                variationRow.add(quantityField);
                variationRow.add(new JLabel("Cost: "));
                variationRow.add(costField1);
                variationRow.add(new JLabel("SRP: "));
                variationRow.add(srpField);
                variationRow.add(removeVariationButton);
                variationsPanel.add(variationRow);
                variationRows.add(variationRow);

                variationsPanel.revalidate();
                variationsPanel.repaint();

                removeVariationButton.addActionListener(removeVariationEvent -> {
                    variationsPanel.remove(variationRow);
                    variationRows.remove(variationRow);
                    variationsPanel.revalidate();
                    variationsPanel.repaint();
                });
            });

            removeAttributeButton.addActionListener(removeAttributeEvent -> {
                mainAttributePanel.remove(attributeScrollPane);
                attributeScrollPanes.remove(attributeScrollPane);
                mainAttributePanel.revalidate();
                mainAttributePanel.repaint();
            });

            mainAttributePanel.revalidate();
            mainAttributePanel.repaint();
        });

        // Display the form
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Variable Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<ProductAttribute> productVariables = getAllProductAttributesFromJScrollPane(attributeScrollPanes);

            // set existing ids
            // set attribute id
            for (var productVariable : productVariables) {
                if (attributeMap.containsKey(productVariable.getName())) {
                    productVariable.setId(attributeMap.get(productVariable.getName()));
                }
            }

            // set variation id
            for (var productVariable : productVariables) {
                for (var variation : productVariable.getProductVariations()) {
                    if (variationMap.containsKey(variation.getVariation())) {
                        variation.setId(variationMap.get(variation.getVariation()));
                    }
                }
            }

            try {
                String name = itemNameField.getText();
                int stock = Integer.parseInt(stockField.getText());
                ProductUnit unit = Objects.equals(unitCombo.getSelectedItem(), "Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN;
                String note = noteArea.getText();
                int alertQuantity = Integer.parseInt(alertField.getText());
                ProductStatus status = Objects.equals(statusCombo.getSelectedItem(), "Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE;
                int selectedIndex = categoryComboBox.getSelectedIndex();
                int categoryId = categoryMap.get(selectedIndex);


                ProductService productService = new ProductService();

                UpdateProductRequestDto dto = new UpdateProductRequestDto(
                        product.id(),
                        categoryId,
                        name,
                        unit,
                        note,
                        alertQuantity,
                        status,
                        image[0],
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        stock
                );

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Set<ProductAttribute> productAttributeSet = new HashSet<>();
                    productAttributeSet.addAll(productVariables);

                    productService.update(dto, productAttributeSet);
                    loadProducts();
                    // Process the input here
                    JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Any character is not allowed");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter number only");
            }
        }
    }

    private void showVariableProductForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int fieldWidth = 15;
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        JTextField itemNameField = new JTextField(10);
        itemNameField.setFont(regularFont);
        panel.add(itemNameField, gbc);

        // Category Label
        gbc.gridx = 2;
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(regularFont);
        panel.add(categoryComboBox, gbc);

        Map<Integer, Integer> categoryMap = new HashMap<>();
        // load categories
        loadCategoryForComboBox(categoryComboBox, categoryMap);

        // Stock
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel stockLabel = new JLabel("Stock *");
        stockLabel.setFont(boldFont);
        panel.add(stockLabel, gbc);

        gbc.gridx = 1;
        JTextField stockField = new JTextField(fieldWidth);
        stockField.setFont(regularFont);
        panel.add(stockField, gbc);

        // Unit
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        unitCombo.setPreferredSize(new Dimension(150, unitCombo.getPreferredSize().height));
        panel.add(unitCombo, gbc);

        // Note
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel alertLabel = new JLabel("Alert Quantity");
        alertLabel.setFont(boldFont);
        panel.add(alertLabel, gbc);

        gbc.gridx = 1;
        JTextField alertField = new JTextField(fieldWidth);
        alertField.setFont(regularFont);
        panel.add(alertField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select Status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        statusCombo.setPreferredSize(new Dimension(150, statusCombo.getPreferredSize().height));
        panel.add(statusCombo, gbc);

        // Attributes and Variations Section
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JLabel attributeLabel = new JLabel("Attributes and Variations");
        attributeLabel.setFont(boldFont);
        panel.add(attributeLabel, gbc);

        gbc.gridy = 6;
        JPanel mainAttributePanel = new JPanel();
        mainAttributePanel.setLayout(new BoxLayout(mainAttributePanel, BoxLayout.Y_AXIS));

        JScrollPane mainScrollPane = new JScrollPane(mainAttributePanel);
        mainScrollPane.setPreferredSize(new Dimension(800, 255));
        panel.add(mainScrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 7;
        JButton addAttributeButton = new JButton("Add Attribute");
        addAttributeButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addAttributeButton, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose Image");
        imageButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(imageButton, gbc);

        // Image Preview
        gbc.gridx = 2;
        JLabel imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(90, 100));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(imagePreview, gbc);

        String[] image = {""};

        // Action Listener for Image Button
        imageButton.addActionListener(e -> {
            image[0] = chooseImage(imagePreview);
        });

        List<JScrollPane> attributeScrollPanes = new ArrayList<>();
        List<JScrollPane> variableScrollPanes = new ArrayList<>();

        addAttributeButton.addActionListener(e -> {
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
            JButton addVariationButton = new JButton("Add Variation");
            JButton removeAttributeButton = new JButton("Remove Attribute");

            attributeRow.add(new JLabel("Attribute Name: "));
            attributeRow.add(attributeField);
            attributeRow.add(addVariationButton);
            attributeRow.add(removeAttributeButton);
            attributeSection.add(attributeRow);

            // Variations Panel
            JPanel variationsPanel = new JPanel();
            variationsPanel.setLayout(new BoxLayout(variationsPanel, BoxLayout.Y_AXIS));
            attributeSection.add(variationsPanel);

            List<JPanel> variationRows = new ArrayList<>();
            addVariationButton.addActionListener(variationEvent -> {
                JPanel variationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JTextField variationField = new JTextField(10);
                JTextField quantityField = new JTextField(5);
                JTextField costField1 = new JTextField(7);
                JTextField srpField = new JTextField(7); // SRP Field
                JButton removeVariationButton = new JButton("Remove");

                variationRow.add(new JLabel("Variation: "));
                variationRow.add(variationField);
                variationRow.add(new JLabel("Qty: "));
                variationRow.add(quantityField);
                variationRow.add(new JLabel("Cost: "));
                variationRow.add(costField1);
                variationRow.add(new JLabel("SRP: "));
                variationRow.add(srpField);
                variationRow.add(removeVariationButton);
                variationsPanel.add(variationRow);
                variationRows.add(variationRow);

                variationsPanel.revalidate();
                variationsPanel.repaint();

                removeVariationButton.addActionListener(removeVariationEvent -> {
                    variationsPanel.remove(variationRow);
                    variationRows.remove(variationRow);
                    variationsPanel.revalidate();
                    variationsPanel.repaint();
                });
            });

            removeAttributeButton.addActionListener(removeAttributeEvent -> {
                mainAttributePanel.remove(attributeScrollPane);
                attributeScrollPanes.remove(attributeScrollPane);
                mainAttributePanel.revalidate();
                mainAttributePanel.repaint();
            });

            mainAttributePanel.revalidate();
            mainAttributePanel.repaint();
        });

        // Display the form
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Variable Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<ProductAttribute> productVariables = getAllProductAttributesFromJScrollPane(attributeScrollPanes);

            try {
                String name = itemNameField.getText();
                int stock = Integer.parseInt(stockField.getText());
                ProductUnit unit = Objects.equals(unitCombo.getSelectedItem(), "Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN;
                String note = noteArea.getText();
                int alertQuantity = Integer.parseInt(alertField.getText());
                ProductStatus status = Objects.equals(statusCombo.getSelectedItem(), "Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE;
                int selectedIndex = categoryComboBox.getSelectedIndex();
                int categoryId = categoryMap.get(selectedIndex);

                ProductService productService = new ProductService();

                AddProductRequestDto dto = new AddProductRequestDto(
                        categoryId,
                        name,
                        stock,
                        unit,
                        note,
                        alertQuantity,
                        status,
                        image[0],
                        ProductType.VARIABLE,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                );

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Set<ProductAttribute> productAttributeSet = new HashSet<>();
                    for (var productAttribute : productVariables) {
                        productAttributeSet.add(productAttribute);
                    }

                    productService.add(dto, productAttributeSet);
                    // Process the input here
                    JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Any character is not allowed");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter number only");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    private List<String> getAllProductVariableFromJScrollPane(List<JScrollPane> jScrollPanes) {
        List<String> attributeNames = new ArrayList<>();

        // Iterate through all attribute scroll panes
        for (JScrollPane attributeScrollPane : jScrollPanes) {
            // Get the main panel of the current scroll pane
            JPanel attributeSection = (JPanel) attributeScrollPane.getViewport().getView();

            // The attributeRow is the first component in the attributeSection
            JPanel attributeRow = (JPanel) attributeSection.getComponent(0);

            // The attributeField is the second component in the attributeRow (index 1)
            JTextField attributeField = (JTextField) attributeRow.getComponent(1);

            // Get the text from the attributeField and add it to the list
            attributeNames.add(attributeField.getText());
        }

        return attributeNames;
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
                        JTextField variationField = (JTextField) variationRow.getComponent(1);
                        JTextField quantityField = (JTextField) variationRow.getComponent(3);
                        JTextField costField1 = (JTextField) variationRow.getComponent(5);
                        JTextField srpField = (JTextField) variationRow.getComponent(7);

                        // Validate fields
                        String variationName = variationField.getText();
                        int quantity = Integer.parseInt(quantityField.getText());
                        BigDecimal purchasePrice = BigDecimal.valueOf(Double.parseDouble(costField1.getText())).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal srp = BigDecimal.valueOf(Double.parseDouble(srpField.getText())).setScale(2, RoundingMode.HALF_UP);

                        // Create and add the variation to the list
                        ProductVariation variation = new ProductVariation();
                        variation.setVariation(variationName);
                        variation.setQuantity(quantity);
                        variation.setPurchasePrice(purchasePrice);
                        variation.setSrp(srp);

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


    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ProductService productService = new ProductService();
        SwingWorker<List<ProductResponseDto>, Void> worker = new SwingWorker<List<ProductResponseDto>, Void>() {
            @Override
            protected List<ProductResponseDto> doInBackground() throws Exception {
                return productService.getAllValidProductsWithLimit();
            }

            @Override
            protected void done() {
                try {
                    var products = get();
                    for (int i = 0; i < products.size(); i++) {

                        model.addRow(new Object[]{
                                i + 1,
                                products.get(i).id(),
                                products.get(i).code(),
                                products.get(i).category().getName(),
                                products.get(i).name(),
                                products.get(i).unit().name(),
                                products.get(i).sellingPrice(),
                                products.get(i).type().name(),
                                products.get(i).status().name()
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

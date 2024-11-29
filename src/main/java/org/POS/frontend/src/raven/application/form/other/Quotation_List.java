package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.PersonResponseDto;
import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.quotation.AddQuotationRequestDto;
import org.POS.backend.quotation.Quotation;
import org.POS.backend.quotation.QuotationService;
import org.POS.backend.quoted_item.AddQuotedItemRequestDto;
import org.POS.backend.quoted_item.QuotedItemType;
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
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class Quotation_List extends javax.swing.JPanel {
    private Timer timer;
    // JLabel variables
    private JLabel subtotalLabel;

    public Quotation_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JOptionPane.showMessageDialog(null, "You Can't Perform this Action");
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
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int id = (Integer) model.getValueAt(row, 1);

                int output = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete this Quotation?", "Confirmation", JOptionPane.WARNING_MESSAGE);

                QuotationService quotationService = new QuotationService();
                SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        try {
                            quotationService.delete(id);
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
                                JOptionPane.showMessageDialog(null, "Deleted Successfully");
                            else
                                JOptionPane.showMessageDialog(null, "Failed to Delete");
                            loadQuotations();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                if (output == JOptionPane.OK_OPTION) {
                    worker.execute();
                }
            }

            @Override

            public void onView(int row) throws ExecutionException, InterruptedException {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int quotationId = (Integer) model.getValueAt(row, 1);

                Application.showForm(new Quotation_Details(quotationId));
            }


        };
        makeCellCenter(table);
        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        loadQuotations();
    }

    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
    }

    private void loadQuotations() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        QuotationService quotationService = new QuotationService();
        SwingWorker<List<Quotation>, Void> worker = new SwingWorker<List<Quotation>, Void>() {
            @Override
            protected List<Quotation> doInBackground() throws Exception {
                var quotations = quotationService.getAllValidQuotation(50);
                return quotations;
            }

            @Override
            protected void done() {
                try {
                    var quotations = get();

                    int n = 1;
                    for (var quotation : quotations) {
                        model.addRow(new Object[]{
                                n,
                                quotation.getId(),
                                quotation.getCode(),
                                quotation.getCreatedDate(),
                                quotation.getPerson().getName(),
                                quotation.getSubtotal()
                        });
                        n++;
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
        jLabel1.setText("Quotations");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Quotations");

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
                        "#", "ID", "Quotation No", "Quotation Date", "Client", "Subtotal", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, true
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
                                                .addGap(0, 1230, Short.MAX_VALUE)))
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
        }, 400); // Delay o
    }

    private void filterList() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String name = jTextField1.getText();

        if (name.isEmpty()) {
            loadQuotations();
            return;
        }

        QuotationService quotationService = new QuotationService();
        SwingWorker<List<Quotation>, Void> worker = new SwingWorker<List<Quotation>, Void>() {
            @Override
            protected List<Quotation> doInBackground() throws Exception {
                var quotations = quotationService.getAllValidQuotationsByCustomerName(name);
                return quotations;
            }

            @Override
            protected void done() {
                try {
                    var quotations = get();
                    int n = 1;
                    for (var quotation : quotations) {
                        model.addRow(new Object[]{
                                n,
                                quotation.getId(),
                                quotation.getCode(),
                                quotation.getCreatedDate(),
                                quotation.getPerson().getName(),
                                quotation.getSubtotal()
                        });
                        n++;
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Define font for larger bold labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

        subtotalLabel = new JLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel supplierLabel = new JLabel("Customer:");
        supplierLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(supplierLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> clientCombo = new JComboBox<>();
        panel.add(clientCombo, gbc);

        Map<Integer, Integer> clientMap = new HashMap<>();
        PersonService personService = new PersonService();

        SwingWorker<java.util.List<PersonResponseDto>, Void> supplierWorker = new SwingWorker<>() {
            @Override
            protected java.util.List<PersonResponseDto> doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    clientCombo.addItem("Loading Customers");
                    clientCombo.setSelectedItem("Loading Customers");
                    clientCombo.setEnabled(false);
                });
                var clients = personService.getAllValidPeopleByType(PersonType.CLIENT);
                return clients;
            }

            @Override
            protected void done() {
                try {
                    var clients = get();

                    SwingUtilities.invokeLater(() -> {
                        clientCombo.setEnabled(true);
                        clientCombo.removeAllItems();
                        clientCombo.addItem("Select Customer");

                        for (int i = 0; i < clients.size(); i++) {
                            clientCombo.addItem(clients.get(i).name());
                            clientMap.put(i + 1, clients.get(i).id());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        supplierWorker.execute();

        gbc.gridx = 2;
        JLabel productsLabel = new JLabel("Select Products:");
        productsLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(productsLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> productsCombo = new JComboBox<>();
        panel.add(productsCombo, gbc);

        ProductService productService = new ProductService();
        Map<Integer, Integer> productMap = new HashMap<>();
        // Populate ComboBox with product names

        SwingWorker<java.util.List<ProductResponseDto>, Void> productWorker = new SwingWorker<>() {
            @Override
            protected java.util.List<ProductResponseDto> doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    productsCombo.addItem("Loading Products");
                    productsCombo.setSelectedItem("Loading Products");
                    productsCombo.setEnabled(false);
                });
                return productService.getALlValidProductsWithoutLimitDtoResponse();
            }

            @Override
            protected void done() {
                try {
                    var products = get();
                    SwingUtilities.invokeLater(() -> {
                        productsCombo.removeAllItems();
                        productsCombo.addItem("Select Product");
                        productsCombo.setSelectedItem("Select Product");

                        for (int i = 0; i < products.size(); i++) {
                            productsCombo.addItem(products.get(i).name());
                            productMap.put(i + 1, products.get(i).id());
                        }

                        productsCombo.setEnabled(true);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        productWorker.execute();

        gbc.gridx = 4; // Adjust column index to position beside "Select Products"
        gbc.gridy = 0; // Same row as products
        JLabel variationLabel = new JLabel("Select Variation:");
        variationLabel.setFont(labelFont); // Set font to bold and large
        panel.add(variationLabel, gbc);

        gbc.gridx = 5; // Next column for the combobox
        JComboBox<String> variationCombo = new JComboBox<>();
        panel.add(variationCombo, gbc);

        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Define a custom table model to make specific columns non-editable
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"#", "ID", "Code", "Name", "Quantity", "Purchase Price", "Selling Price", "Variation", "Subtotal", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        JTable productsTable = new JTable(tableModel);
        productsTable.setFillsViewportHeight(true);

        final String noVariation = "No Variation";
        List<ProductResponseDto> listOfAllSelectedProducts = new ArrayList<>();
        List<ProductVariation> listOfVariationsUnderAttribute = new ArrayList<>();
        Map<Integer, ProductVariation> variationMap = new HashMap<>();
        productsCombo.addActionListener(e -> {
            int index = productsCombo.getSelectedIndex();
            Integer id = productMap.get(index);
            SwingWorker<ProductResponseDto, Void> worker = new SwingWorker<>() {
                @Override
                protected ProductResponseDto doInBackground() throws Exception {
                    return productService.getValidProductById(id);
                }

                @Override
                protected void done() {
                    try {
                        var product = get();
                        if (product.type().equals(ProductType.SIMPLE)) {
                            // validate the duplicate selection
                            for (var selectedRow : listOfAllSelectedProducts) {
                                if (selectedRow.type().equals(ProductType.SIMPLE) && product.id() == selectedRow.id()) {
                                    JOptionPane.showMessageDialog(null, "The " + product.name() + " is already selected");
                                    return;
                                }
                            }

                            tableModel.addRow(new Object[]{
                                    tableModel.getRowCount() + 1,
                                    product.id(),
                                    product.code(),
                                    product.name(),
                                    1,
                                    product.purchasePrice(),
                                    product.sellingPrice(),
                                    noVariation,
                                    product.sellingPrice(),
                                    "Remove"
                            });
                            listOfAllSelectedProducts.add(product);
                            subtotalLabel.setText(String.valueOf(getTableSubtotal(tableModel)));
                        } else {
                            listOfVariationsUnderAttribute.clear();
                            variationMap.clear();
                            variationCombo.removeAllItems();
                            int n = 0;
                            for (var attribute : product.productAttributes()) {
                                for (var variation : attribute.getProductVariations()) {
                                    variationCombo.addItem(variation.getVariation());
                                    variationMap.put(n, variation);
                                    n++;
                                }
                            }

                            listOfAllSelectedProducts.add(product);
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

        // add the variation to the table
        variationCombo.addActionListener(e -> {
            int variationIndex = variationCombo.getSelectedIndex();
            ProductVariation variation = variationMap.get(variationIndex);

            if (variation != null) {
                for (var selectedProduct : getAllRows(tableModel)) {
                    if (variation.getId().equals(selectedProduct.getId()) && !selectedProduct.getVariation().equals(noVariation)) {
                        JOptionPane.showMessageDialog(null, "The " + variation.getProductAttribute().getProduct().getName() + " with a variation of : " + variation.getVariation() + " is already selected");
                        return;
                    }
                }

                tableModel.addRow(new Object[]{
                        tableModel.getRowCount() + 1,
                        variation.getId(),
                        variation.getProductAttribute().getProduct().getProductCode(),
                        variation.getProductAttribute().getProduct().getName(),
                        1,
                        variation.getPurchasePrice(),
                        variation.getSrp(),
                        variation.getVariation(),
                        variation.getSrp(),
                        "Remove"
                });
                subtotalLabel.setText(String.valueOf(getTableSubtotal(tableModel)));
            }
        });

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Highlight editable cells
        productsTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField())); // Quantity as JTextField
        productsTable.getColumnModel().getColumn(4).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Quantity
        productsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());

        // Add JScrollPane around the table
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(scrollPane, gbc);

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4) { // Check if it's the "Quantity", "Selling" or "Purchase Price" column
//                List<QuotationListedProduct> insertedRows = getAllRows(tableModel);
//                tableModel.setRowCount(0);
                Integer quantity = Integer.parseInt(tableModel.getValueAt(row, 4).toString());
                BigDecimal sellingPrice = (BigDecimal) tableModel.getValueAt(row, 6);
                // update the table
                tableModel.setValueAt(sellingPrice.multiply(BigDecimal.valueOf(quantity)), row, 8);
                subtotalLabel.setText(String.valueOf(getTableSubtotal(tableModel)));
            }
        });

        // Add MouseListener to handle "Remove" button clicks in the table
        productsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = productsTable.columnAtPoint(e.getPoint());
                int row = productsTable.rowAtPoint(e.getPoint());

                if (column == 9) { // "Action" column index for "Remove" button
                    tableModel.removeRow(row);
                    subtotalLabel.setText(String.valueOf(getTableSubtotal(tableModel)));
                }
            }
        });

        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

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


        gbcSummary.gridx = 3;
        JLabel totalLabel = new JLabel("Subtotal: ₱");
        totalLabel.setFont(summaryFont);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(totalLabel, gbcSummary);

        gbcSummary.gridx = 4;
        subtotalLabel.setFont(summaryFont);
        subtotalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        subtotalLabel.setText("0");
        summaryPanel.add(subtotalLabel, gbcSummary);

        gbc.gridx = 0;
        gbc.gridy = 2; // Position summary panel right below the table
        gbc.gridwidth = 8;
        panel.add(summaryPanel, gbc);


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


        // Shift remaining elements down one row to make space for the new panel
        gbc.gridy++;


        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Quotation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<QuotationListedProduct> insertedRows = getAllRows(tableModel);

            int clientSelectIndex = clientCombo.getSelectedIndex();

            if (clientSelectIndex == 0) {
                JOptionPane.showMessageDialog(null, "You didn't enter any supplier from the list");
                return;
            }

            if (insertedRows.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You didn't enter any product from the list");
                return;
            }

            List<AddQuotedItemRequestDto> quotedList = new ArrayList<>();
            for (var row : insertedRows) {
                if (row.getVariation().equals(noVariation)) {
                    AddQuotedItemRequestDto dto = new AddQuotedItemRequestDto(
                            row.getId(),
                            null,
                            row.getQuantity(),
                            QuotedItemType.SIMPLE
                    );
                    quotedList.add(dto);
                } else {
                    AddQuotedItemRequestDto dto = new AddQuotedItemRequestDto(
                            null,
                            row.getId(),
                            row.getQuantity(),
                            QuotedItemType.VARIABLE
                    );
                    quotedList.add(dto);
                }
            }

            int clientId = clientMap.get(clientSelectIndex);
            String note = noteArea.getText();

            QuotationService quotationService = new QuotationService();
            AddQuotationRequestDto dto = new AddQuotationRequestDto(
                    clientId,
                    note,
                    quotedList
            );

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    try {
                        quotationService.add(dto);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        var result = get();
                        if (result)
                            JOptionPane.showMessageDialog(null, "Quotation added");
                        else
                            JOptionPane.showMessageDialog(null, "Failed to add Quotation");
                        loadQuotations();
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

    private BigDecimal getTableSubtotal(DefaultTableModel tableModel) {
        var selectedProducts = getAllRows(tableModel);
        BigDecimal subtotal = BigDecimal.ZERO;
        for (var selectedProduct : selectedProducts) {
            subtotal = subtotal.add(selectedProduct.getSellingPrice().multiply(BigDecimal.valueOf(selectedProduct.getQuantity())));
        }
        return subtotal;
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class QuotationListedProduct {
        private Integer id;
        private String code;
        private String name;
        private int quantity;
        private BigDecimal purchasePrice;
        private BigDecimal sellingPrice;
        private String variation;
    }

    private List<QuotationListedProduct> getAllRows(DefaultTableModel model) {
        List<QuotationListedProduct> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Integer id = model.getValueAt(i, 1) == null ? null : (Integer) model.getValueAt(i, 1);
            String code = (String) model.getValueAt(i, 2);
            String name = (String) model.getValueAt(i, 3);
            int quantity = Integer.parseInt(model.getValueAt(i, 4).toString());
            BigDecimal purchasePrice = (BigDecimal) model.getValueAt(i, 5);
            BigDecimal sellingPrice = (BigDecimal) model.getValueAt(i, 6);
            String variation = (String) model.getValueAt(i, 7);

            QuotationListedProduct quotationListedProduct = new QuotationListedProduct(
                    id,
                    code,
                    name,
                    quantity,
                    purchasePrice,
                    sellingPrice,
                    variation
            );
            rows.add(quotationListedProduct);
        }
        return rows;
    }

    // Method to create createdAt pickers with the current createdAt
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
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        QuotationService quotationService = new QuotationService();
        SwingWorker<List<Quotation>, Void> worker = new SwingWorker<List<Quotation>, Void>() {
            @Override
            protected List<Quotation> doInBackground() throws Exception {
                return quotationService.getAllValidQuotationsByRange(fromDate, toDate);
            }

            @Override
            protected void done() {
                try {
                    var quotations = get();
                    int n = 1;
                    for (var quotation : quotations) {
                        model.addRow(new Object[]{
                                n,
                                quotation.getId(),
                                quotation.getCode(),
                                quotation.getCreatedDate(),
                                quotation.getPerson().getName(),
                                quotation.getSubtotal()
                        });
                        n++;
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

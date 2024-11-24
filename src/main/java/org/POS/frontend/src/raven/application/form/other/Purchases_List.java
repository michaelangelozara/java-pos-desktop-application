package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.PersonResponseDto;
import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonType;
import org.POS.backend.purchase.*;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Purchases_List extends javax.swing.JPanel {

    private Timer timer;

    public Purchases_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int purchaseId = (Integer) model.getValueAt(row, 1);

                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;

                Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                JLabel supplierLabel = new JLabel("Supplier:");
                supplierLabel.setFont(labelFont);  // Set label to bold and large
                panel.add(supplierLabel, gbc);

                gbc.gridx = 1;
                JTextField supplierField = new JTextField();
                supplierField.setText("Loading...");
                supplierField.setEnabled(false);
                panel.add(supplierField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel productNameLabel = new JLabel("Product Name:");
                productNameLabel.setFont(labelFont);  // Set label to bold and large
                panel.add(productNameLabel, gbc);

                gbc.gridx = 1;
                JTextField productNameField = new JTextField();
                panel.add(productNameField, gbc);

                gbc.gridx = 2;
                JLabel quantityLabel = new JLabel("Quantity:");
                quantityLabel.setFont(labelFont);  // Set label to bold and large
                panel.add(quantityLabel, gbc);

                gbc.gridx = 3;
                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));  // Default to 1, max 100
                quantitySpinner.setPreferredSize(new Dimension(120, quantitySpinner.getPreferredSize().height));  // Make the spinner wider
                panel.add(quantitySpinner, gbc);

                gbc.gridx = 4;
                JButton addButton = new JButton("Add");
                addButton.setEnabled(false);
                addButton.setBackground(new Color(204, 255, 204));
                panel.add(addButton, gbc);

                // Table setup below Supplier and Select Products (unchanged)
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 8;
                gbc.fill = GridBagConstraints.BOTH;

                // Define a custom table model to make specific columns non-editable
                DefaultTableModel tableModel = new DefaultTableModel(
                        new Object[]{"#", "ID", "Product Name", "Quantity", "Action"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                JTable productsTable = new JTable(tableModel);
                productsTable.setFillsViewportHeight(true);

                // Add functionality for the "Add" button (e.g., adding the product to a list or table)
                addButton.addActionListener(e -> {
                    String productName = productNameField.getText();
                    int quantity = (int) quantitySpinner.getValue();

                    if (!productName.isEmpty() && quantity > 0) {
                        tableModel.addRow(new Object[]{
                                tableModel.getRowCount() + 1,
                                null,
                                productName,
                                quantity,
                                "Remove"
                        });

                        quantitySpinner.setValue(1);
                        productNameField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Please provide valid product name and quantity.");
                    }
                });

                // Center align all table cell content
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < productsTable.getColumnCount(); i++) {
                    productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                productsTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

                // Add JScrollPane around the table
                JScrollPane scrollPane = new JScrollPane(productsTable);
                scrollPane.setPreferredSize(new Dimension(800, 150));
                panel.add(scrollPane, gbc);

                // Add MouseListener to handle "Remove" button clicks in the table
                productsTable.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int column = productsTable.columnAtPoint(e.getPoint());
                        int row = productsTable.rowAtPoint(e.getPoint());

                        if (column == 4) { // "Action" column index for "Remove" button
                            tableModel.removeRow(row);

                            var rows = getAllRows(tableModel);

                            tableModel.setRowCount(0);

                            for (var eachRow : rows) {
                                tableModel.addRow(new Object[]{
                                        tableModel.getRowCount() + 1,
                                        eachRow.getId(),
                                        eachRow.getName(),
                                        eachRow.getQuantity(),
                                        "Remove"
                                });
                            }
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

                gbcSummary.gridx = 0;
                gbcSummary.gridy = 0;
                gbcSummary.anchor = GridBagConstraints.EAST;

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
                noteArea.setEnabled(false);
                noteArea.setText("Loading...");
                panel.add(new JScrollPane(noteArea), gbc);

                PurchaseService purchaseService = new PurchaseService();
                SwingWorker<PurchaseResponseDto, Void> purchaseWorker = new SwingWorker<>() {
                    @Override
                    protected PurchaseResponseDto doInBackground() {
                        return purchaseService.getValidPurchaseByPurchaseId(purchaseId);
                    }

                    @Override
                    protected void done() {
                        try {
                            var purchase = get();

                            SwingUtilities.invokeLater(() -> {
                                addButton.setEnabled(true);
                                noteArea.setEnabled(true);

                                noteArea.setText(purchase.note());
                                supplierField.setText(purchase.person().getName());

                                var purchasedItems = purchase.purchaseItems();
                                int n = 1;
                                for (var purchaseItem : purchasedItems) {
                                    if(purchaseItem.isDeleted()) continue;

                                    tableModel.addRow(new Object[]{
                                            n,
                                            purchaseItem.getId(),
                                            purchaseItem.getName(),
                                            purchaseItem.getQuantity(),
                                            "Remove"
                                    });
                                    n++;
                                }
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                purchaseWorker.execute();

                // Show the panel in a dialog
                int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try{
                        Set<PurchaseItem> purchaseItemList = new HashSet<>();

                        var purchaseItems = getAllRows(tableModel);

                        for (var selectedItem : purchaseItems) {
                            PurchaseItem purchaseItem = new PurchaseItem();
                            purchaseItem.setId(selectedItem.getId());
                            purchaseItem.setName(selectedItem.getName());
                            purchaseItem.setQuantity(selectedItem.getQuantity());
                            purchaseItemList.add(purchaseItem);
                        }

                        UpdatePurchaseRequestDto dto = new UpdatePurchaseRequestDto(
                                purchaseId,
                                noteArea.getText()
                        );
                        purchaseService.update(dto, purchaseItemList);
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
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
                    int purchaseId = (Integer) model.getValueAt(row, 1);
                    PurchaseService purchaseService = new PurchaseService();

                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            purchaseService.delete(purchaseId);
                            return null;
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
                                    "Deleted", JOptionPane.INFORMATION_MESSAGE);
                            loadPurchases();
                        }
                    };
                    worker.execute();
                }
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int purchaseId = (Integer) model.getValueAt(row, 1);

                PurchaseService purchaseService = new PurchaseService();
                SwingWorker<Purchase, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Purchase doInBackground() throws Exception {
                        var purchase = purchaseService.getValidPurchaseWithoutDto(purchaseId);
                        return purchase;
                    }

                    @Override
                    protected void done() {
                        try {
                            var purchase = get();
                            Application.showForm(new Purchase_Details(purchase));
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

        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        loadPurchases();
    }

    private void loadPurchases() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        PurchaseService purchaseService = new PurchaseService();
        SwingWorker<List<PurchaseResponseDto>, Void> worker = new SwingWorker<List<PurchaseResponseDto>, Void>() {
            @Override
            protected List<PurchaseResponseDto> doInBackground() throws Exception {
                var purchases = purchaseService.getAllValidPurchases();
                return purchases;
            }

            @Override
            protected void done() {
                try {
                    var purchases = get();

                    for (int i = 0; i < purchases.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                purchases.get(i).id(),
                                purchases.get(i).code(),
                                purchases.get(i).date(),
                                purchases.get(i).person().getName(),
                                "Active"
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                new Object[][]{
                },
                new String[]{
                        "#", "ID", "Purchase #", "Date", "Supplier", "Status", "Action"
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
            table.getColumnModel().getColumn(4).setResizable(false);
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

        String query = jTextField1.getText();

        if (query.isEmpty()) {
            loadPurchases();
            return;
        }

        PurchaseService purchaseService = new PurchaseService();

        SwingWorker<List<PurchaseResponseDto>, Void> worker = new SwingWorker<List<PurchaseResponseDto>, Void>() {
            @Override
            protected List<PurchaseResponseDto> doInBackground() throws Exception {
                var purchases = purchaseService.getAllValidPurchaseByCodeAndSupplierName(query);
                return purchases;
            }

            @Override
            protected void done() {
                try {
                    var purchases = get();

                    for (int i = 0; i < purchases.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                purchases.get(i).code(),
                                purchases.get(i).date(),
                                purchases.get(i).person().getName(),
                                "Active"
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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

        Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(supplierLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> supplierCombo = new JComboBox<>();
        panel.add(supplierCombo, gbc);

        Map<Integer, Integer> supplierMap = new HashMap<>();
        PersonService personService = new PersonService();
        SwingWorker<List<PersonResponseDto>, Void> supplierWorker = new SwingWorker<>() {
            @Override
            protected List<PersonResponseDto> doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    supplierCombo.addItem("Loading Suppliers");
                    supplierCombo.setSelectedItem("Loading Suppliers");
                    supplierCombo.setEnabled(false);
                });
                var suppliers = personService.getAllValidPeopleByType(PersonType.SUPPLIER);
                return suppliers;
            }

            @Override
            protected void done() {
                try {
                    var suppliers = get();
                    SwingUtilities.invokeLater(() -> {
                        supplierCombo.setEnabled(true);
                        supplierCombo.removeAllItems();
                        supplierCombo.addItem("Select Supplier");

                        for (int i = 0; i < suppliers.size(); i++) {
                            supplierCombo.addItem(suppliers.get(i).name());
                            supplierMap.put(i + 1, suppliers.get(i).id());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        supplierWorker.execute();

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(productNameLabel, gbc);

        gbc.gridx = 1;
        JTextField productNameField = new JTextField();
        panel.add(productNameField, gbc);

        gbc.gridx = 2;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(quantityLabel, gbc);

        gbc.gridx = 3;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));  // Default to 1, max 100
        quantitySpinner.setPreferredSize(new Dimension(120, quantitySpinner.getPreferredSize().height));  // Make the spinner wider
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 4;
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(204, 255, 204));
        panel.add(addButton, gbc);

// Table setup below Supplier and Select Products (unchanged)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Define a custom table model to make specific columns non-editable
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"#", "Product Name", "Quantity", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable productsTable = new JTable(tableModel);
        productsTable.setFillsViewportHeight(true);

        // Add functionality for the "Add" button (e.g., adding the product to a list or table)
        addButton.addActionListener(e -> {
            String productName = productNameField.getText();
            int quantity = (int) quantitySpinner.getValue();

            if (!productName.isEmpty() && quantity > 0) {
                tableModel.addRow(new Object[]{
                        tableModel.getRowCount() + 1,
                        productName,
                        quantity,
                        "Remove"
                });

                quantitySpinner.setValue(1);
                productNameField.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "Please provide valid product name and quantity.");
            }
        });

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        productsTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        // Add JScrollPane around the table
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(scrollPane, gbc);

        // Add MouseListener to handle "Remove" button clicks in the table
        productsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = productsTable.columnAtPoint(e.getPoint());
                int row = productsTable.rowAtPoint(e.getPoint());

                if (column == 3) { // "Action" column index for "Remove" button
                    tableModel.removeRow(row);

                    var rows = getAllRows(tableModel);
                    tableModel.setRowCount(0);

                    for (var eachRow : rows) {
                        tableModel.addRow(new Object[]{
                                tableModel.getRowCount() + 1,
                                eachRow.getName(),
                                eachRow.getQuantity(),
                                "Remove"
                        });
                    }
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

        gbcSummary.gridx = 0;
        gbcSummary.gridy = 0;
        gbcSummary.anchor = GridBagConstraints.EAST;

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

        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int index = supplierCombo.getSelectedIndex();
            int supplierId = supplierMap.get(index);
            Set<PurchaseItem> purchaseItemList = new HashSet<>();

            var purchaseItems = getAllRows(tableModel);

            for (var selectedItem : purchaseItems) {
                PurchaseItem purchaseItem = new PurchaseItem();
                purchaseItem.setName(selectedItem.getName());
                purchaseItem.setQuantity(selectedItem.getQuantity());
                purchaseItemList.add(purchaseItem);
            }
            PurchaseService purchaseService = new PurchaseService();
            AddPurchaseRequestDto dto = new AddPurchaseRequestDto(
                    supplierId,
                    noteArea.getText()
            );
            SwingWorker<Void, Void> purchaseWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    purchaseService.add(dto, purchaseItemList);
                    return null;
                }

                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(null, "Purchase added");
                    loadPurchases();
                }
            };
            purchaseWorker.execute();
        }
    }

    private List<PurchaseListedProduct> getAllRows(DefaultTableModel model) {
        List<PurchaseListedProduct> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {

            Integer id = (Integer) model.getValueAt(i, 1);
            String name = (String) model.getValueAt(i, 2);
            int quantity = Integer.parseInt(model.getValueAt(i, 3).toString());
            // Create a new `PurchaseListedProduct` instance with the parsed values and add it to the list
            PurchaseListedProduct purchaseListedProduct = new PurchaseListedProduct(
                    id,
                    name,
                    quantity
            );
            rows.add(purchaseListedProduct);
        }
        return rows;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class PurchaseListedProduct {
        private Integer id;
        private String name;
        private int quantity;
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

    // Method to create createdAt pickers with the current createdAt
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        // Set the current createdAt
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);  // Automatically selects the current createdAt

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

        PurchaseService purchaseService = new PurchaseService();


        SwingWorker<List<PurchaseResponseDto>, Void> worker = new SwingWorker<List<PurchaseResponseDto>, Void>() {
            @Override
            protected List<PurchaseResponseDto> doInBackground() throws Exception {
                var purchases = purchaseService.getAllValidPurchaseByRange(fromDate, toDate);
                return purchases;
            }

            @Override
            protected void done() {
                try {
                    var purchases = get();

                    for (int i = 0; i < purchases.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                purchases.get(i).id(),
                                purchases.get(i).code(),
                                purchases.get(i).date(),
                                purchases.get(i).person().getName(),
                                "Active"
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockService;
import org.POS.backend.stock.StockType;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author CJ
 */
public class Inventory_Report extends javax.swing.JPanel {

    private Integer recentCategoryId = null;

    /**
     * Creates new form BalanceSheet_Report
     */
    public Inventory_Report() {
        initComponents();
        makeCellCenter(jTable1);
        loadInventoryReports();
        computeTotalAvailableStock();
    }

    private void loadInventoryReports() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        StockService stockService = new StockService();
        SwingWorker<List<Stock>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Stock> doInBackground() throws Exception {
                return stockService.getAllValidStocks();
            }

            @Override
            protected void done() {
                try {
                    int stockInSummation = 0;
                    int stockOutSummation = 0;
                    var stocks = get();

                    int n = 1;
                    for (var stock : stocks) {
                        var product = stock.getProduct();

                        if (product.getProductType().equals(ProductType.SIMPLE)) {
                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                        } else {

                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                        }
                        n++;
                    }

                    jLabel20.setText(String.valueOf(stockInSummation));
                    jLabel21.setText(String.valueOf(stockOutSummation));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("ZEUSLED");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Phone:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("09123456789");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("zeusled@gmail.com");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Isulan, Sultan Kudarat");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Address:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Email:");

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(150, 183, 162));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Download");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Inventory Report");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setText("October 23, 2024");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Date:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/logogroup.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel3)
                                                                .addComponent(jLabel8))
                                                        .addGap(22, 22, 22)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addGap(18, 18, 18)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel6)
                                                                .addGap(39, 39, 39))
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 683, Short.MAX_VALUE)
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel12))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel11)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel18)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel17)
                                                        .addComponent(jLabel12))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel10)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(31, 31, 31))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "#	", "Code", "Date", "Name", "Previous Stock", "Stock In", "Stock Out", "Stock Available"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton3.setText("From - To");

        jButton3.addActionListener(e -> {
            createDatePickerPanel();
        });

        ProductCategoryService productCategoryService = new ProductCategoryService();
        Vector<String> categoryNames = new Vector<>();
        categoryNames.add("Select Category");
        Map<Integer, Integer> categoryMap = new HashMap<>();
        var categories = productCategoryService.getAllValidProductCategories();
        for (int i = 0; i < categories.size(); i++) {
            categoryNames.add(categories.get(i).name());
            categoryMap.put(i + 1, categories.get(i).id());
        }

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(categoryNames));

        jComboBox1.addActionListener(e -> {
            String subcategoryName = jComboBox1.getSelectedItem() == null ? "" : (String) jComboBox1.getSelectedItem();
            assert subcategoryName != null;
            if (subcategoryName.equals("Select Subcategory")) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                return;
            }

            int categoryIndex = jComboBox1.getSelectedIndex();
            Integer categoryId = categoryMap.get(categoryIndex);

            if (categoryId != null) {
                recentCategoryId = categoryId;
                makeCellCenter(jTable1);
                loadInventories(categoryId);
            }
        });


        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Category Name *");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Total Quantity\t");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(380, 380, 380)
                                .addComponent(jLabel19)
                                .addGap(123, 123, 123)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel22)
                                .addGap(55, 55, 55))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel21))
                                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboBox1)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);  // Automatically selects the current date

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void createDatePickerPanel() {
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
    }

    private void filterTableByDateRange(LocalDate fromDate, LocalDate toDate) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Integer categoryId = recentCategoryId;
        StockService stockService = new StockService();
        SwingWorker<List<Stock>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Stock> doInBackground() throws Exception {
                return categoryId != null
                        ? stockService.getAllValidStocksByRangeAndCategoryId(categoryId, fromDate, toDate)
                        : stockService.getAllValidStocksByRange(fromDate, toDate);
            }

            @Override
            protected void done() {
                try {
                    int stockInSummation = 0;
                    int stockOutSummation = 0;
                    int stockAvailableSummation = 0;

                    var stocks = get();

                    int n = 1;
                    for (var stock : stocks) {
                        var product = stock.getProduct();

                        if (product.getProductType().equals(ProductType.SIMPLE)) {
                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                            stockAvailableSummation += product.getStock();
                        } else {

                            for (var attribute : product.getProductAttributes()) {
                                for (var variation : attribute.getProductVariations()) {
                                    stockAvailableSummation += variation.getQuantity();
                                }
                            }

                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                        }
                        n++;
                    }

                    jLabel20.setText(String.valueOf(stockInSummation));
                    jLabel21.setText(String.valueOf(stockOutSummation));

                    jLabel22.setText(String.valueOf(stockAvailableSummation));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private void loadInventories(int categoryId) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        StockService stockService = new StockService();
        SwingWorker<List<Stock>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Stock> doInBackground() throws Exception {
                return stockService.getAllValidStockByProductCategoryId(categoryId);
            }

            @Override
            protected void done() {
                try {
                    int stockInSummation = 0;
                    int stockOutSummation = 0;
                    int stockAvailableSummation = 0;

                    var stocks = get();

                    int n = 1;
                    for (var stock : stocks) {
                        var product = stock.getProduct();

                        if (product.getProductType().equals(ProductType.SIMPLE)) {
                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                            stockAvailableSummation += product.getStock();
                        } else {

                            for (var attribute : product.getProductAttributes()) {
                                for (var variation : attribute.getProductVariations()) {
                                    stockAvailableSummation += variation.getQuantity();
                                }
                            }

                            if (stock.getType().equals(StockType.IN)) {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        stock.getStockInOrOut(),
                                        0,
                                        stock.getRecentQuantity() + stock.getStockInOrOut()
                                });
                                stockInSummation += stock.getStockInOrOut();
                            } else {
                                model.addRow(new Object[]{
                                        n,
                                        product.getProductCode(),
                                        stock.getDate(),
                                        product.getName(),
                                        stock.getRecentQuantity(),
                                        0,
                                        stock.getStockInOrOut(),
                                        stock.getRecentQuantity() - stock.getStockInOrOut()
                                });
                                stockOutSummation += stock.getStockInOrOut();
                            }
                        }
                        n++;
                    }

                    jLabel20.setText(String.valueOf(stockInSummation));
                    jLabel21.setText(String.valueOf(stockOutSummation));

                    jLabel22.setText(String.valueOf(stockAvailableSummation));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private void computeTotalAvailableStock() {
        ProductService productService = new ProductService();
        SwingWorker<Integer, Void> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                try {
                    jLabel22.setText("Loading...");
                    return productService.getProductTotalQuantity();
                } catch (Exception e) {
                    throw e;
                }
            }

            @Override
            protected void done() {
                try {
                    int totalQuantity = get();
                    jLabel22.setText(String.valueOf(totalQuantity));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unexpected Error");
                }
            }
        };
        worker.execute();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

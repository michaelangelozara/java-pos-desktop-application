package org.POS.frontend.src.zeusled.gui;

import org.POS.backend.expense.ExpenseService;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.frontend.src.javaswingdev.card.ModelCard;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.application.form.other.BeforePOS;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Dashboard extends JPanel {

    public Dashboard() {
        initComponents();
        init();
    }

    private void init() {
        ExpenseService expenseService = new ExpenseService();

        ProductService productService = new ProductService();

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Center the column headers by setting a custom renderer for each column header
        TableCellRenderer headerRenderer = table1.getTableHeader().getDefaultRenderer();
        if (headerRenderer instanceof DefaultTableCellRenderer) {
            DefaultTableCellRenderer defaultHeaderRenderer = (DefaultTableCellRenderer) headerRenderer;
            defaultHeaderRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        }

        table1.fixTable(jScrollPane1);

        SwingWorker<List<ProductResponseDto>, Void> productOutOfStockWorker = new SwingWorker<>() {
            @Override
            protected List<ProductResponseDto> doInBackground() throws Exception {
                var productsBelowAlertQuantity = productService.getAllValidProductsBelowAlertQuantity();
                return productsBelowAlertQuantity;
            }

            @Override
            protected void done() {
                try {

                    var output = get();

                    card2.setData(new ModelCard(null, null, null, String.valueOf(output.size()), "Items Out of Stock"));


                    for(int i = 0; i < output.size(); i++){
                        table1.addRow(new Object[]{
                                i+1,
                                output.get(i).code(),
                                output.get(i).name(),
                                output.get(i).stock(),
                                output.get(i).alertQuantity()
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Unable to fetch the out of stock products");
                }
            }
        };
        productOutOfStockWorker.execute();

        SwingWorker<BigDecimal, Void> totalProductValueWorker = new SwingWorker<>() {
            @Override
            protected BigDecimal doInBackground() throws InterruptedException {
                BigDecimal totalProductValue = BigDecimal.ZERO;
                var products = productService.getAllValidProductsWithLimit();
                for(var product : products){
                    totalProductValue = totalProductValue.add((BigDecimal.valueOf(product.stock()).multiply(product.sellingPrice())));
                }
                return totalProductValue;
            }

            @Override
            protected void done() {
                try {
                    BigDecimal totalValue = get();

                    card3.setData(new ModelCard(null, null, null, "₱ " + totalValue, "Inventory Value"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        totalProductValueWorker.execute();

        SwingWorker<BigDecimal, Void> totalExpenseWorker = new SwingWorker<BigDecimal, Void>() {
            @Override
            protected BigDecimal doInBackground() throws Exception {
                BigDecimal totalExpense = expenseService.getTheSumOfExpenses() != null ? expenseService.getTheSumOfExpenses() : BigDecimal.ZERO;
                return totalExpense;
            }

            @Override
            protected void done() {

                try {
                    BigDecimal totalExpense = get();
                    card4.setData(new ModelCard(null, null, null, "₱ " + totalExpense, "Expenses"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        totalExpenseWorker.execute();

        //  init card data
        card1.setData(new ModelCard(null, null, null, "₱ 500.00", "Total Sales"));
        card2.setData(new ModelCard(null, null, null, "Loading...", "Items Out of Stock"));
        card3.setData(new ModelCard(null, null, null, "₱ Loading...", "Inventory Value"));
        card4.setData(new ModelCard(null, null, null, "₱ Loading...", "Expenses"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modelCard1 = new com.raven.model.ModelCard();
        jLabel1 = new JLabel();
        jButton1 = new JButton();
        jSeparator1 = new JSeparator();
        roundPanel1 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        jLabel2 = new JLabel();
        jScrollPane1 = new JScrollPane();
        table1 = new org.POS.frontend.src.javaswingdev.swing.table.Table();
        roundPanel2 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        card1 = new org.POS.frontend.src.javaswingdev.card.Card();
        card3 = new org.POS.frontend.src.javaswingdev.card.Card();
        card4 = new org.POS.frontend.src.javaswingdev.card.Card();
        card2 = new org.POS.frontend.src.javaswingdev.card.Card();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Today's Summary");

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("POS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Stock Alert");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Code", "Name", "Quantity", "Alert Quantity"
            }
        ) {
            Class[] types = new Class [] {
                Object.class, Object.class, Object.class, Integer.class, Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);

        GroupLayout roundPanel1Layout = new GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );

        GroupLayout roundPanel2Layout = new GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(card3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(card4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(card2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(roundPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, GroupLayout.Alignment.LEADING))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Application.showForm(new BeforePOS());
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.POS.frontend.src.javaswingdev.card.Card card1;
    private org.POS.frontend.src.javaswingdev.card.Card card2;
    private org.POS.frontend.src.javaswingdev.card.Card card3;
    private org.POS.frontend.src.javaswingdev.card.Card card4;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private com.raven.model.ModelCard modelCard1;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel1;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel2;
    private org.POS.frontend.src.javaswingdev.swing.table.Table table1;
    // End of variables declaration//GEN-END:variables
}

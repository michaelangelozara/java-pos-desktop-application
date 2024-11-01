package org.POS.frontend.src.javaswingdev.form;

import org.POS.frontend.src.javaswingdev.card.ModelCard;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.application.form.other.POS;

public class Form_Dashboard extends javax.swing.JPanel {

    public Form_Dashboard() {
        initComponents();
        init();
    }

    private void init() {
//        table.fixTable(jScrollPane1);
//        table.addRow(new Object[]{"1", "ITEM-001", "Steel Bar Wide", "243", "15"});
//        table.addRow(new Object[]{"2", "ITEM-001", "Steel Bar Narrow", "21", "15"});
//        table.addRow(new Object[]{"3", "ITEM-001", "Steel Bar", "342", "15"});
//        table.addRow(new Object[]{"4", "ITEM-002", "Aluminum Sheet Large", "150", "20"});
//        table.addRow(new Object[]{"5", "ITEM-002", "Aluminum Sheet Small", "45", "20"});
//        table.addRow(new Object[]{"6", "ITEM-003", "Copper Wire Thick", "76", "30"});
//        table.addRow(new Object[]{"7", "ITEM-003", "Copper Wire Thin", "135", "30"});
//        table.addRow(new Object[]{"8", "ITEM-004", "Brass Pipe", "89", "25"});
//        table.addRow(new Object[]{"9", "ITEM-005", "Iron Rod", "200", "10"});
//        table.addRow(new Object[]{"10", "ITEM-006", "Plastic Tube", "300", "5"});
//        table.addRow(new Object[]{"11", "ITEM-007", "Stainless Steel Bolt", "500", "8"});
//        table.addRow(new Object[]{"12", "ITEM-008", "PVC Pipe", "230", "12"});
//        table.addRow(new Object[]{"13", "ITEM-009", "Galvanized Nail", "750", "5"});
//        table.addRow(new Object[]{"14", "ITEM-010", "Rubber Gasket", "125", "3"});
//        table.addRow(new Object[]{"15", "ITEM-011", "Wooden Plank", "180", "10"});
//        table.addRow(new Object[]{"16", "ITEM-012", "Ceramic Tile", "320", "7"});
//        table.addRow(new Object[]{"17", "ITEM-013", "Glass Panel", "90", "25"});
//        table.addRow(new Object[]{"18", "ITEM-014", "Concrete Block", "60", "50"});
//        table.addRow(new Object[]{"19", "ITEM-015", "Iron Nail", "900", "4"});
//        table.addRow(new Object[]{"20", "ITEM-016", "Brass Fitting", "130", "18"});



        //  init card data
//        card1.setData(new ModelCard(null, null, null, "₱ 500.00", "Total Sales"));
//        card2.setData(new ModelCard(null, null, null, "40", "Items Out of Stock"));
//        card3.setData(new ModelCard(null, null, null, "100", "Inventory"));
//        card4.setData(new ModelCard(null, null, null, "₱ 300.00", "Expenses"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modelCard1 = new com.raven.model.ModelCard();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        card1 = new org.POS.frontend.src.javaswingdev.card.Card();
        card2 = new org.POS.frontend.src.javaswingdev.card.Card();
        card3 = new org.POS.frontend.src.javaswingdev.card.Card();
        card4 = new org.POS.frontend.src.javaswingdev.card.Card();
        roundPanel1 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new org.POS.frontend.src.javaswingdev.swing.table.Table();

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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table1);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(roundPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 462, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Application.showForm(new POS());
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.POS.frontend.src.javaswingdev.card.Card card1;
    private org.POS.frontend.src.javaswingdev.card.Card card2;
    private org.POS.frontend.src.javaswingdev.card.Card card3;
    private org.POS.frontend.src.javaswingdev.card.Card card4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.raven.model.ModelCard modelCard1;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel1;
    private org.POS.frontend.src.javaswingdev.swing.table.Table table1;
    // End of variables declaration//GEN-END:variables
}

package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class POS extends javax.swing.JPanel {

    public POS() {
        initComponents();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        item1 = new org.POS.frontend.src.com.raven.component.Item();
        item2 = new org.POS.frontend.src.com.raven.component.Item();
        item3 = new org.POS.frontend.src.com.raven.component.Item();
        item4 = new org.POS.frontend.src.com.raven.component.Item();
        item5 = new org.POS.frontend.src.com.raven.component.Item();
        item6 = new org.POS.frontend.src.com.raven.component.Item();
        item7 = new org.POS.frontend.src.com.raven.component.Item();
        item8 = new org.POS.frontend.src.com.raven.component.Item();
        item9 = new org.POS.frontend.src.com.raven.component.Item();
        item10 = new org.POS.frontend.src.com.raven.component.Item();
        item11 = new org.POS.frontend.src.com.raven.component.Item();
        item12 = new org.POS.frontend.src.com.raven.component.Item();
        item13 = new org.POS.frontend.src.com.raven.component.Item();
        item14 = new org.POS.frontend.src.com.raven.component.Item();
        item15 = new org.POS.frontend.src.com.raven.component.Item();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jPanel1.setPreferredSize(new java.awt.Dimension(1620, 1000));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Create Sales");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Customer", "Andy Suarez", "Christian James Torres", "Michael Angelo Zara" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category", "Steel", "Wood", "PVC" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Sub Category", "Sub for Steel", "Sub for Wood", "Sub for PVC" }));

        jTextField1.setText("Search");

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setText("Add Product");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 255, 204));
        jButton3.setText("Add Customer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(item1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(item2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(item3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(item4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(item5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(item6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(item7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(item8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(item9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(item10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(item11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(item12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(item13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(item14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(item15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(item3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(item2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(item1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel9);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product", "Price", "Quantity", "Subtotal", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox3, 0, 527, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Discount Type");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Discount Type", "Fixed", "Percentage %" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Transport Cost");

        jTextField2.setText("Enter Transport Cost");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Discount");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Tax", "10% Tax", "20% Tax", "30% Tax" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Invoice Tax");

        jTextField3.setText("Enter Discount");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 1056, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5)))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setText("Net Total:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setText("10,000");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(610, 610, 610)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save & Payment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1653, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1190, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // Create a JPanel with GridBagLayout to hold the custom form (2 columns)
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);  // Add padding between components for better spacing
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Font for bold and larger labels
    Font boldFont = new Font("Arial", Font.BOLD, 14);

    // Row 1 - Item Name and Item Model
    JLabel itemNameLabel = new JLabel("Item Name *:");
    itemNameLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(itemNameLabel, gbc);

    JTextField itemNameField = new JTextField(15);
    gbc.gridx = 1;
    gbc.gridy = 0;
    panel.add(itemNameField, gbc);

    JLabel itemModelLabel = new JLabel("Item Model:");
    itemModelLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 0;
    panel.add(itemModelLabel, gbc);

    JTextField itemModelField = new JTextField(15);
    gbc.gridx = 3;
    gbc.gridy = 0;
    panel.add(itemModelField, gbc);

    // Row 2 - Item Code
    JLabel itemCodeLabel = new JLabel("Item Code *:");
    itemCodeLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(itemCodeLabel, gbc);

    JTextField itemCodeField = new JTextField(15);
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel.add(itemCodeField, gbc);

    // Row 3 - Sub Category and Brand
    JLabel subCategoryLabel = new JLabel("Sub Category *:");
    subCategoryLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(subCategoryLabel, gbc);

    JComboBox<String> subCategoryCombo = new JComboBox<>(new String[] { "Category 1", "Category 2", "Category 3" });
    gbc.gridx = 1;
    gbc.gridy = 2;
    panel.add(subCategoryCombo, gbc);

    JLabel brandLabel = new JLabel("Brand:");
    brandLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 2;
    panel.add(brandLabel, gbc);

    JComboBox<String> brandCombo = new JComboBox<>(new String[] { "Brand 1", "Brand 2", "Brand 3" });
    gbc.gridx = 3;
    gbc.gridy = 2;
    panel.add(brandCombo, gbc);

    // Row 4 - Unit and Product Tax
    JLabel unitLabel = new JLabel("Unit *:");
    unitLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(unitLabel, gbc);

    JComboBox<String> unitCombo = new JComboBox<>(new String[] { "Unit 1", "Unit 2", "Unit 3" });
    gbc.gridx = 1;
    gbc.gridy = 3;
    panel.add(unitCombo, gbc);

    JLabel taxLabel = new JLabel("Product Tax *:");
    taxLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 3;
    panel.add(taxLabel, gbc);

    JComboBox<String> taxCombo = new JComboBox<>(new String[] { "VAT@0", "VAT@10", "VAT@20" });
    gbc.gridx = 3;
    gbc.gridy = 3;
    panel.add(taxCombo, gbc);

    // Row 5 - Tax Type and Regular Price
    JLabel taxTypeLabel = new JLabel("Tax Type *:");
    taxTypeLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(taxTypeLabel, gbc);

    JComboBox<String> taxTypeCombo = new JComboBox<>(new String[] { "Exclusive", "Inclusive" });
    gbc.gridx = 1;
    gbc.gridy = 4;
    panel.add(taxTypeCombo, gbc);

    JLabel priceLabel = new JLabel("Regular Price *:");
    priceLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 4;
    panel.add(priceLabel, gbc);

    JTextField priceField = new JTextField(15);
    gbc.gridx = 3;
    gbc.gridy = 4;
    panel.add(priceField, gbc);

    // Row 6 - Discount and Selling Price
    JLabel discountLabel = new JLabel("Discount:");
    discountLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(discountLabel, gbc);

    JTextField discountField = new JTextField(5);
    gbc.gridx = 1;
    gbc.gridy = 5;
    panel.add(discountField, gbc);

    JLabel sellingPriceLabel = new JLabel("Selling Price:");
    sellingPriceLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 5;
    panel.add(sellingPriceLabel, gbc);

    JTextField sellingPriceField = new JTextField(15);
    gbc.gridx = 3;
    gbc.gridy = 5;
    panel.add(sellingPriceField, gbc);

    // Row 7 - Note and Alert Quantity (as JSpinner)
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(noteLabel, gbc);

    JTextArea noteArea = new JTextArea(3, 20);
    gbc.gridx = 1;
    gbc.gridy = 6;
    panel.add(new JScrollPane(noteArea), gbc);

    JLabel alertQtyLabel = new JLabel("Alert Quantity:");
    alertQtyLabel.setFont(boldFont);
    gbc.gridx = 2;
    gbc.gridy = 6;
    panel.add(alertQtyLabel, gbc);

    // JSpinner for alert quantity
    JSpinner alertQtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    gbc.gridx = 3;
    gbc.gridy = 6;
    panel.add(alertQtySpinner, gbc);

    // Row 8 - Status
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(statusLabel, gbc);

    JComboBox<String> statusCombo = new JComboBox<>(new String[] { "Active", "Inactive" });
    gbc.gridx = 1;
    gbc.gridy = 7;
    panel.add(statusCombo, gbc);

    // Row 9 - Image Upload
    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(boldFont);
    gbc.gridx = 0;
    gbc.gridy = 8;
    panel.add(imageLabel, gbc);

    JButton uploadButton = new JButton("Browse");
    gbc.gridx = 1;
    gbc.gridy = 8;
    panel.add(uploadButton, gbc);

    uploadButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    });

    // Show the panel inside a larger JOptionPane
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // Handle form submission
    if (result == JOptionPane.OK_OPTION) {
        JOptionPane.showMessageDialog(null, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "Product creation cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   // Creating a JPanel with GridBagLayout for better control over positioning
    JPanel paymentPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Padding between elements
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    // Creating input fields
    JTextField accountField = new JTextField(15);
    JTextField amountField = new JTextField(15);
    JTextField chequeNoField = new JTextField(15);
    JTextField receiptNoField = new JTextField(15);
    JTextField poReferenceField = new JTextField(15);
    JTextField paymentTermsField = new JTextField(15);
    JTextField referenceField = new JTextField(15);
    JTextField deliveryPlaceField = new JTextField(15);
    JTextField dateField = new JTextField(15);
    JComboBox<String> statusCombo = new JComboBox<>(new String[] {"Active", "Inactive"});
    JTextField noteField = new JTextField(15);

    // Adding labels and fields to the panel in two columns
    gbc.gridx = 0; gbc.gridy = 0;
    paymentPanel.add(new JLabel("Account:"), gbc);
    gbc.gridx = 1;
    paymentPanel.add(accountField, gbc);

    gbc.gridx = 2; gbc.gridy = 0;
    paymentPanel.add(new JLabel("Amount:"), gbc);
    gbc.gridx = 3;
    paymentPanel.add(amountField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    paymentPanel.add(new JLabel("Cheque No:"), gbc);
    gbc.gridx = 1;
    paymentPanel.add(chequeNoField, gbc);

    gbc.gridx = 2; gbc.gridy = 1;
    paymentPanel.add(new JLabel("Receipt No:"), gbc);
    gbc.gridx = 3;
    paymentPanel.add(receiptNoField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    paymentPanel.add(new JLabel("PO Reference:"), gbc);
    gbc.gridx = 1;
    paymentPanel.add(poReferenceField, gbc);

    gbc.gridx = 2; gbc.gridy = 2;
    paymentPanel.add(new JLabel("Payment Terms:"), gbc);
    gbc.gridx = 3;
    paymentPanel.add(paymentTermsField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    paymentPanel.add(new JLabel("Reference:"), gbc);
    gbc.gridx = 1;
    paymentPanel.add(referenceField, gbc);

    gbc.gridx = 2; gbc.gridy = 3;
    paymentPanel.add(new JLabel("Delivery Place:"), gbc);
    gbc.gridx = 3;
    paymentPanel.add(deliveryPlaceField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    paymentPanel.add(new JLabel("Date:"), gbc);
    gbc.gridx = 1;
    paymentPanel.add(dateField, gbc);

    gbc.gridx = 2; gbc.gridy = 4;
    paymentPanel.add(new JLabel("Status:"), gbc);
    gbc.gridx = 3;
    paymentPanel.add(statusCombo, gbc);

    gbc.gridx = 0; gbc.gridy = 5;
    paymentPanel.add(new JLabel("Note:"), gbc);
    gbc.gridx = 1; gbc.gridwidth = 3;
    paymentPanel.add(noteField, gbc);

    // Show the JOptionPane with the custom panel
    int result = JOptionPane.showConfirmDialog(null, paymentPanel, "Add Payment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // Handling OK/Cancel button click
    if (result == JOptionPane.OK_OPTION) {
        String account = accountField.getText();
        String amount = amountField.getText();
        String chequeNo = chequeNoField.getText();
        String receiptNo = receiptNoField.getText();
        String poReference = poReferenceField.getText();
        String paymentTerms = paymentTermsField.getText();
        String reference = referenceField.getText();
        String deliveryPlace = deliveryPlaceField.getText();
        String date = dateField.getText();
        String status = (String) statusCombo.getSelectedItem();
        String note = noteField.getText();

        // Show a JOptionPane to confirm the payment addition
        JOptionPane.showMessageDialog(null, "Payment Added:\n"
                + "Account: " + account + "\n"
                + "Amount: " + amount + "\n"
                + "Cheque No: " + chequeNo + "\n"
                + "Receipt No: " + receiptNo + "\n"
                + "PO Reference: " + poReference + "\n"
                + "Payment Terms: " + paymentTerms + "\n"
                + "Reference: " + reference + "\n"
                + "Delivery Place: " + deliveryPlace + "\n"
                + "Date: " + date + "\n"
                + "Status: " + status + "\n"
                + "Note: " + note);
    } else {
        // Show a message if payment is canceled
        JOptionPane.showMessageDialog(null, "Payment canceled.");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    // Create a JPanel with GridBagLayout to hold the custom form (2 columns)
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);  // Add padding between components for better spacing
    gbc.fill = GridBagConstraints.BOTH;  // Allow components to fill the grid cell
    gbc.weightx = 1.0;  // Allow horizontal expansion
    gbc.weighty = 1.0;  // Allow vertical expansion when needed

    // Font for bold labels (larger size)
    Font boldFont = new Font("Arial", Font.BOLD, 14);

    // Row 1 - Name (Bold Label)
    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(nameLabel, gbc);

    JTextField nameField = new JTextField(20);  // Made the text field a bit wider
    gbc.gridx = 1;
    gbc.gridy = 0;
    panel.add(nameField, gbc);

    // Row 2 - Email (Bold Label)
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(emailLabel, gbc);

    JTextField emailField = new JTextField(20);  // Made the text field a bit wider
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel.add(emailField, gbc);

    // Row 3 - Contact Number (Bold Label, Fixed Country Code +63 for Philippines)
    JLabel contactLabel = new JLabel("Contact Number:");
    contactLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(contactLabel, gbc);

    JLabel countryCodeLabel = new JLabel("+63");
    JTextField contactField = new JTextField(15);  // Allow only the phone number input, country code is fixed
    JPanel contactPanel = new JPanel(new BorderLayout());
    contactPanel.add(countryCodeLabel, BorderLayout.WEST);
    contactPanel.add(contactField, BorderLayout.CENTER);
    gbc.gridx = 1;
    gbc.gridy = 2;
    panel.add(contactPanel, gbc);

    // Row 4 - Company Name (Bold Label)
    JLabel companyLabel = new JLabel("Company Name:");
    companyLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(companyLabel, gbc);

    JTextField companyField = new JTextField(20);  // Made the text field a bit wider
    gbc.gridx = 1;
    gbc.gridy = 3;
    panel.add(companyField, gbc);

    // Row 5 - Address (Bold Label)
    JLabel addressLabel = new JLabel("Address:");
    addressLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(addressLabel, gbc);

    JTextArea addressArea = new JTextArea(4, 20);  // Made the text area a bit larger
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.weighty = 2.0;  // Allow the text area to expand vertically more
    panel.add(new JScrollPane(addressArea), gbc);

    // Row 6 - Image Upload (Button to Open File Manager, Bold Label)
    JLabel imageLabel = new JLabel("Image:");
    imageLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(imageLabel, gbc);

    JButton uploadButton = new JButton("Browse");
    gbc.gridx = 1;
    gbc.gridy = 5;
    panel.add(uploadButton, gbc);

    // File chooser for selecting an image file
    uploadButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());  // Here you can store or process the file
        }
    });

    // Row 7 - Status Dropdown (Bold Label)
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(boldFont);  // Make label bold
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(statusLabel, gbc);

    JComboBox<String> statusCombo = new JComboBox<>(new String[] { "Active", "Inactive" });
    gbc.gridx = 1;
    gbc.gridy = 6;
    panel.add(statusCombo, gbc);

    // Set preferred panel size
    panel.setPreferredSize(new Dimension(600, 400));

    // Show the panel inside a larger JOptionPane
    int result = JOptionPane.showConfirmDialog(null, panel, "Create Client", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    // Handle form submission
    if (result == JOptionPane.OK_OPTION) {
        // If the user clicked "Add"
        String name = nameField.getText();
        String email = emailField.getText();
        String contactNumber = "+63 " + contactField.getText();  // Prepend the fixed country code
        String companyName = companyField.getText();
        String address = addressArea.getText();
        String status = (String) statusCombo.getSelectedItem();

        // Check if the name field is filled (basic validation)
        if (!name.isEmpty()) {
            // Success message
            JOptionPane.showMessageDialog(null, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If name is empty, show an error message
            JOptionPane.showMessageDialog(null, "Please fill out the Name field.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // If the user clicked "Cancel"
        JOptionPane.showMessageDialog(null, "Client creation cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.POS.frontend.src.com.raven.component.Item item1;
    private org.POS.frontend.src.com.raven.component.Item item10;
    private org.POS.frontend.src.com.raven.component.Item item11;
    private org.POS.frontend.src.com.raven.component.Item item12;
    private org.POS.frontend.src.com.raven.component.Item item13;
    private org.POS.frontend.src.com.raven.component.Item item14;
    private org.POS.frontend.src.com.raven.component.Item item15;
    private org.POS.frontend.src.com.raven.component.Item item2;
    private org.POS.frontend.src.com.raven.component.Item item3;
    private org.POS.frontend.src.com.raven.component.Item item4;
    private org.POS.frontend.src.com.raven.component.Item item5;
    private org.POS.frontend.src.com.raven.component.Item item6;
    private org.POS.frontend.src.com.raven.component.Item item7;
    private org.POS.frontend.src.com.raven.component.Item item8;
    private org.POS.frontend.src.com.raven.component.Item item9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

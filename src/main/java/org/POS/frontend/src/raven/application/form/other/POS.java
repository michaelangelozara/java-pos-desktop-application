
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.person.AddPersonRequestDto;
import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonStatus;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductService;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.frontend.src.com.raven.component.Item;
import org.POS.frontend.src.com.raven.model.ModelItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class POS extends JPanel {

    private Vector<String> clientNames;

    private Map<Integer, Integer> clientMap;

    private DefaultComboBoxModel<String> clientComboModel;

    private ActionListener clientActionListener;

    private List<ModelItem> selectedItems;

    public POS() {
        selectedItems = new ArrayList<>();
        initComponents();
    }

    private void reloadProductTable(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for(int i = 0; i < selectedItems.size(); i++){
            model.addRow(new Object[]{
                    selectedItems.get(i).getItemID(),
                    selectedItems.get(i).getItemName(),
                    selectedItems.get(i).getPrice(),
                    "0",
                    "0"
            });
        }

    }

    public void addItem(ModelItem data) {
        Item item = new Item();
        item.setData(data);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    Item clickedItem = (Item) me.getSource();

                    ModelItem itemData = clickedItem.getData();
                    if(selectedItems.contains(itemData)){
                        JOptionPane.showMessageDialog(null, "Product is already added");
                        return;
                    }
                    selectedItems.add(itemData);
                    reloadProductTable();
                }
            }
        });
        panelItem.add(item);
        panelItem.repaint();
        panelItem.revalidate();
    }

//    private void testData() {
//        int ID = 1;
//        for (int i = 0; i <= 5; i++) {
//            addItem(new ModelItem(ID++, "HollowBlocks", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img1.png"))));
//            addItem(new ModelItem(ID++, "Cement", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img2.png"))));
//            addItem(new ModelItem(ID++, "Plywood", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img3.png"))));
//            addItem(new ModelItem(ID++, "Steel", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img4.png"))));
//            addItem(new ModelItem(ID++, "Tire", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img5.png"))));
//            addItem(new ModelItem(ID++, "PVC Pipes", "description", 160, "HOLCIM", new ImageIcon(getClass().getResource("/image/img6.png"))));
//        }
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel5 = new JLabel();
        jSeparator1 = new JSeparator();
        jPanel2 = new JPanel();
        jComboBox1 = new JComboBox<>();
        jButton3 = new JButton();
        jComboBox2 = new JComboBox<>();
        jComboBox3 = new JComboBox<>();
        jButton2 = new JButton();
        jTextField1 = new JTextField();
        roundPanel2 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        scroll = new JScrollPane();
        panelItem = new org.POS.frontend.src.com.raven.swing.PanelItem();
        jPanel7 = new JPanel();
        jLabel1 = new JLabel();
        jComboBox4 = new JComboBox<>();
        jLabel2 = new JLabel();
        jTextField2 = new JTextField();
        jComboBox5 = new JComboBox<>();
        jLabel4 = new JLabel();
        jTextField3 = new JTextField();
        jLabel3 = new JLabel();
        jPanel3 = new JPanel();
        jLabel7 = new JLabel();
        jLabel6 = new JLabel();
        jButton1 = new JButton();

        jPanel1.setBorder(BorderFactory.createEtchedBorder());

        jLabel5.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Create Sales");

        jPanel2.setBorder(BorderFactory.createEtchedBorder());

        clientNames = new Vector<>();
        clientNames.add("Select Customer");
        clientMap = new HashMap<>();
        PersonService personService = new PersonService();

        clientComboModel = new DefaultComboBoxModel<>(clientNames);
        jComboBox1.setModel(clientComboModel);

        clientActionListener = e -> {
            JComboBox<String> clientCombo = new JComboBox<>(clientComboModel);
            int clientSelectedIndex = clientCombo.getSelectedIndex();
            int clientId = clientMap.get(clientSelectedIndex);
            JOptionPane.showMessageDialog(null, "Client id : " + clientId);
        };

        var clients = personService.getAllValidPeopleByType(PersonType.CLIENT);
        for (int i = 0; i < clients.size(); i++) {
            clientNames.add(clients.get(i).name());
            clientMap.put(i + 1, clients.get(i).id());
        }

        jComboBox1.addActionListener(clientActionListener);

        jButton3.setBackground(new java.awt.Color(204, 255, 204));
        jButton3.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Add Customer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Vector<String> categoryNames = new Vector<>();
        Map<Integer, Integer> productCategoryMap = new HashMap<>();
        categoryNames.add("Select Category");
        ProductCategoryService productCategoryService = new ProductCategoryService();
        var productCategories = productCategoryService.getAllValidProductCategories();

        for (int i = 0; i < productCategories.size(); i++) {
            categoryNames.add(productCategories.get(i).name());
            productCategoryMap.put(i + 1, productCategories.get(i).id());
        }
        DefaultComboBoxModel<String> categoryCombo = new DefaultComboBoxModel<>(categoryNames);

        jComboBox2.setModel(categoryCombo);
        jComboBox2.setPreferredSize(new Dimension(318, 22));

        Vector<String> subcategoryNames = new Vector<>();
        Map<Integer, Integer> productSubcategoryMap = new HashMap<>();
        subcategoryNames.add("Select Subcategory");
        ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();

        DefaultComboBoxModel<String> subcategoryCombo = new DefaultComboBoxModel<>(subcategoryNames);
        jComboBox3.setModel(subcategoryCombo);

        ProductService productService = new ProductService();
        ActionListener subcategoryActionListener = e -> {
            this.panelItem.removeAll();
            panelItem.repaint();
            panelItem.revalidate();

            JComboBox<String> productSubcategoryCombo = new JComboBox<>(subcategoryCombo);
            int productSubcategorySelectedIndex = productSubcategoryCombo.getSelectedIndex();
            int productSubcategoryId = productSubcategoryMap.get(productSubcategorySelectedIndex);
            var productsUnderSubcategory = productService.getAllValidProductByProductSubcategoryId(productSubcategoryId);
            for (int i = 0; i < productsUnderSubcategory.size(); i++) {
                addItem(new ModelItem(
                        productsUnderSubcategory.get(i).id(),
                        productsUnderSubcategory.get(i).name(),
                        "",
                        productsUnderSubcategory.get(i).sellingPrice().doubleValue(),
                        productsUnderSubcategory.get(i).brand().name(),
                        productsUnderSubcategory.get(i).image() != null ? new ImageIcon(getImage(productsUnderSubcategory.get(i).image())) : new ImageIcon()
                ));
            }
        };

        jComboBox2.addActionListener(evt -> {
            JComboBox<String> tempProductCategoryCombo = new JComboBox<>(categoryCombo);
            int productSelectedIndex = tempProductCategoryCombo.getSelectedIndex();
            int productCategoryId = productCategoryMap.get(productSelectedIndex);

            jComboBox3.removeActionListener(subcategoryActionListener);
            subcategoryCombo.removeAllElements();
            subcategoryCombo.addElement("Select Subcategory");
            var productSubcategories = productSubcategoryService.getAllValidSubcategoriesByCategoryId(productCategoryId);
            for (int i = 0; i < productSubcategories.size(); i++) {
                subcategoryCombo.addElement(productSubcategories.get(i).name());
                productSubcategoryMap.put(i + 1, productSubcategories.get(i).id());
            }
            jComboBox3.addActionListener(subcategoryActionListener);

//            jComboBox2ActionPerformed(evt);
        });

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Add Product");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setText("Search");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox3, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox2, 0, 0, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1)
                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jComboBox2, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                                        .addComponent(jTextField1))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jComboBox3)
                                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{
                        "Product", "Price", "Quantity", "Subtotal", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem);

        GroupLayout roundPanel2Layout = new GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
                roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1)
                                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
                roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(roundPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                        .addComponent(scroll))
                                .addGap(10, 10, 10))
        );

        jPanel7.setBorder(BorderFactory.createEtchedBorder());

        jLabel1.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Discount Type");

        jComboBox4.setModel(new DefaultComboBoxModel<>(new String[]{"Select Discount Type", "Fixed", "Percentage %"}));

        jLabel2.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Transport Cost");

        jTextField2.setText("Enter Transport Cost");

        jComboBox5.setModel(new DefaultComboBoxModel<>(new String[]{"Select Tax", "10% Tax", "20% Tax", "30% Tax"}));

        jLabel4.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Invoice Tax");

        jTextField3.setText("Enter Discount");

        jLabel3.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Discount");

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox4, GroupLayout.Alignment.TRAILING, 0, 520, Short.MAX_VALUE)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jTextField2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                                        .addComponent(jComboBox5, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox5, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                        .addComponent(jTextField2))
                                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(BorderFactory.createEtchedBorder());

        jLabel7.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setText("10,000");

        jLabel6.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setText("Net Total:");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                .addGap(329, 329, 329))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(14, 14, 14))
        );

        jButton1.setBackground(new java.awt.Color(235, 161, 132));
        jButton1.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save & Payment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(roundPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private Image getImage(String imageBase64) {

        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

        // Convert byte array to Image
        Image image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Create a JPanel with GridBagLayout to hold the custom form (2 columns)
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 16);  // Larger font for labels
        Dimension fieldSize = new Dimension(250, 30);  // Uniform field size

        // 1st Row: Name, Email, Contact Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name *:");
        nameLabel.setFont(labelFont);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        nameField.setPreferredSize(fieldSize);
        panel.add(nameField, gbc);

        gbc.gridx = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        panel.add(emailLabel, gbc);

        gbc.gridx = 3;
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(fieldSize);
        panel.add(emailField, gbc);

        gbc.gridx = 4;
        JLabel contactNumberLabel = new JLabel("Contact Number:");
        contactNumberLabel.setFont(labelFont);
        panel.add(contactNumberLabel, gbc);

        gbc.gridx = 5;
        JTextField contactNumberField = new JTextField(20);
        contactNumberField.setPreferredSize(fieldSize);  // Uniform size with other fields
        panel.add(contactNumberField, gbc);

        // 2nd Row: Company Name, Tax Registration Number, Address
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameLabel.setFont(labelFont);
        panel.add(companyNameLabel, gbc);

        gbc.gridx = 1;
        JTextField companyNameField = new JTextField(20);
        companyNameField.setPreferredSize(fieldSize);
        panel.add(companyNameField, gbc);

        gbc.gridx = 2;
        JLabel taxLabel = new JLabel("Tax Registration Number:");
        taxLabel.setFont(labelFont);
        panel.add(taxLabel, gbc);

        gbc.gridx = 3;
        JTextField taxField = new JTextField(20);
        taxField.setPreferredSize(fieldSize);
        panel.add(taxField, gbc);

        gbc.gridx = 4;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(labelFont);
        panel.add(addressLabel, gbc);

        gbc.gridx = 5;
        JTextField addressField = new JTextField(20);  // Uniform size with other fields
        addressField.setPreferredSize(fieldSize);
        panel.add(addressField, gbc);

        // 3rd Row: Image and Status
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setFont(labelFont);
        panel.add(imageLabel, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Upload Image");
        imageButton.setPreferredSize(fieldSize);

        // File Chooser for Image Upload
        Base64Converter base64Converter = new Base64Converter();
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Image");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Handle file upload logic here
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                try {
                    base64Converter.setConvertFileToBase64(selectedFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(imageButton, gbc);

        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusCombo.setPreferredSize(fieldSize);
        panel.add(statusCombo, gbc);

        // Display the panel
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            PersonService personService = new PersonService();

            // Submission logic
            String name = nameField.getText();
            String email = emailField.getText();
            String contactNumber = contactNumberField.getText();
            String companyName = companyNameField.getText();
            String taxRegistrationNumber = taxField.getText();
            String address = addressField.getText();
            String status = (String) statusCombo.getSelectedItem();
            String image = base64Converter.getBase64();

            AddPersonRequestDto dto = new AddPersonRequestDto(
                    name,
                    email,
                    contactNumber,
                    companyName,
                    taxRegistrationNumber,
                    PersonType.CLIENT,
                    address,
                    image,
                    status.equals("Active") ? PersonStatus.ACTIVE : PersonStatus.INACTIVE
            );

            personService.add(dto);
            JOptionPane.showMessageDialog(null, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadClients(personService);
            // Handle the image upload if needed
            // selectedFile would be processed here after image upload
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void loadClients(PersonService personService) {
        jComboBox1.removeActionListener(clientActionListener);
        this.clientComboModel.removeAllElements();
        var clients = personService.getAllValidPeopleByType(PersonType.CLIENT);
        clientNames.add("Select Customer");
        for (int i = 0; i < clients.size(); i++) {
            clientNames.add(clients.get(i).name());
            clientMap.put(i + 1, clients.get(i).id());
        }
        clientComboModel.setSelectedItem("Select Customer");
        jComboBox1.addActionListener(clientActionListener);
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

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

        JComboBox<String> subCategoryCombo = new JComboBox<>(new String[]{"Category 1", "Category 2", "Category 3"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(subCategoryCombo, gbc);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(boldFont);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(brandLabel, gbc);

        JComboBox<String> brandCombo = new JComboBox<>(new String[]{"Brand 1", "Brand 2", "Brand 3"});
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(brandCombo, gbc);

        // Row 4 - Unit and Product Tax
        JLabel unitLabel = new JLabel("Unit *:");
        unitLabel.setFont(boldFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(unitLabel, gbc);

        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Unit 1", "Unit 2", "Unit 3"});
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(unitCombo, gbc);

        JLabel taxLabel = new JLabel("Product Tax *:");
        taxLabel.setFont(boldFont);
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(taxLabel, gbc);

        JComboBox<String> taxCombo = new JComboBox<>(new String[]{"VAT@0", "VAT@10", "VAT@20"});
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(taxCombo, gbc);

        // Row 5 - Tax Type and Regular Price
        JLabel taxTypeLabel = new JLabel("Tax Type *:");
        taxTypeLabel.setFont(boldFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(taxTypeLabel, gbc);

        JComboBox<String> taxTypeCombo = new JComboBox<>(new String[]{"Exclusive", "Inclusive"});
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

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
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
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        JTextField noteField = new JTextField(15);

        // Adding labels and fields to the panel in two columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(new JLabel("Account:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(accountField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        paymentPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentPanel.add(new JLabel("Cheque No:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(chequeNoField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        paymentPanel.add(new JLabel("Receipt No:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(receiptNoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentPanel.add(new JLabel("PO Reference:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(poReferenceField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        paymentPanel.add(new JLabel("Payment Terms:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(paymentTermsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        paymentPanel.add(new JLabel("Reference:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(referenceField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        paymentPanel.add(new JLabel("Delivery Place:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(deliveryPlaceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        paymentPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(dateField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        paymentPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(statusCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        paymentPanel.add(new JLabel("Note:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JComboBox<String> jComboBox3;
    private JComboBox<String> jComboBox4;
    private JComboBox<String> jComboBox5;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel7;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private org.POS.frontend.src.com.raven.swing.PanelItem panelItem;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel2;
    private JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}

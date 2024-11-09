
package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.brand.BrandService;
import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.person.AddPersonRequestDto;
import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonStatus;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.*;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.backend.sale.AddSaleRequestDto;
import org.POS.backend.sale.SaleService;
import org.POS.backend.sale_item.AddSaleItemRequestDto;
import org.POS.frontend.src.com.raven.component.Item;
import org.POS.frontend.src.com.raven.model.ModelItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class POS extends JPanel {

    private Vector<String> clientNames;

    private Map<Integer, Integer> clientMap;

    private DefaultComboBoxModel<String> clientComboModel;

    private ActionListener clientActionListener;

    private List<PurchaseListedProduct> selectedItems;

    public POS() {
        selectedItems = new ArrayList<>();

        initComponents();
    }

    private void reloadProductTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        List<PurchaseListedProduct> rows = getAllRows();

        model.setRowCount(0);

        int i = 1;
        for (PurchaseListedProduct product : selectedItems) {
            boolean isEqual = false;

            for (var dataInTheTable : rows) {
                if (Objects.equals(product.getId(), dataInTheTable.getId())) {
                    model.addRow(new Object[]{
                            i,
                            dataInTheTable.getId(),
                            dataInTheTable.getName(),
                            dataInTheTable.getPrice(),
                            dataInTheTable.getQuantity(),
                            dataInTheTable.getSubtotal(),
                            "Remove"
                    });
                    isEqual = true;
                    break;
                }
            }

            if (!isEqual) {
                model.addRow(new Object[]{
                        i,
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getSubtotal(),
                        "Remove"
                });
            }
            i++;
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

                    for (int i = 0; i < selectedItems.size(); i++) {
                        if (selectedItems.get(i).getId() == itemData.getItemID()) {
                            JOptionPane.showMessageDialog(null, "Product is already added");
                            return;
                        }
                    }

                    selectedItems.add(new PurchaseListedProduct(
                            data.getItemID(),
                            data.getItemName(),
                            1,
                            BigDecimal.valueOf(data.getPrice()),
                            BigDecimal.valueOf(data.getPrice())
                    ));
                    reloadProductTable();
                    loadNetTotal();
                }
            }
        });
        panelItem.add(item);
        panelItem.repaint();
        panelItem.revalidate();
    }

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
            JOptionPane.showMessageDialog(null, "Client : " + clientCombo.getSelectedItem());
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
            panelItem.repaint();
            panelItem.revalidate();
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
                },
                new String[]{
                        "#", "ID", "Product", "Price", "Quantity", "Subtotal", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, true, false, false
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
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        TableColumn actionColumn = jTable1.getColumnModel().getColumn(5);
//        actionColumn.setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), jTable1, model)); // Pass model to editor for row removal

        // Add MouseListener to handle "Remove" button clicks in the table
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());

                if (column == 6) { // "Action" column index for "Remove" button
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    int id = (Integer) model.getValueAt(row, 1);
                    for (var selectedItem : selectedItems) {
                        if (selectedItem.getId() == id) {
                            selectedItems.remove(selectedItem);
                            reloadProductTable();
                            loadNetTotal();
                            break;
                        }
                    }
                }
            }
        });

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4) {
                int productId = (Integer) model.getValueAt(row, 1);
                List<PurchaseListedProduct> products = getAllRows();

                model.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId() == productId) {
                        products.get(i).setSubtotal((BigDecimal.valueOf(products.get(i).getQuantity()).multiply(products.get(i).getPrice())).setScale(2, RoundingMode.HALF_UP));
                    }
                }

                for (int i = 0; i < products.size(); i++) {
                    model.addRow(new Object[]{
                            i+1,
                            products.get(i).getId(),
                            products.get(i).getName(),
                            products.get(i).getPrice(),
                            products.get(i).getQuantity(),
                            products.get(i).getSubtotal(),
                            "Remove"
                    });
                }

                loadNetTotal();
            }
        });


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
        String[] discountComboArr = new String[]{"Select Discount Type", "Fixed", "Percentage %"};
        DefaultComboBoxModel<String> discountCombo = new DefaultComboBoxModel<>(discountComboArr);
        jComboBox4.setModel(discountCombo);

        jLabel2.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Transport Cost");

        jTextField2.setText("0");

        jTextField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = jTextField2.getText();
                for (char c : currentText.toCharArray()) {
                    if (Character.isLetter(c)) {
                        JOptionPane.showMessageDialog(null, "Letter is not allow for this field");
                        jTextField2.setText("0");
                        return;
                    }
                }

                loadNetTotal();
            }
        });

        jComboBox5.setModel(new DefaultComboBoxModel<>(new String[]{"0"}));
        jComboBox5.setSelectedItem("0");
        jComboBox5.setEnabled(false);

        jLabel4.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Total Tax");

        jTextField3.setText("0");
        jTextField3.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = jTextField3.getText();
                for (char c : currentText.toCharArray()) {
                    if (Character.isLetter(c)) {
                        JOptionPane.showMessageDialog(null, "Letter is not allow for this field");
                        jTextField3.setText("0");
                        return;
                    }
                }

                if (discountCombo.getSelectedItem().equals("Select Discount Type")) {
                    JOptionPane.showMessageDialog(null, "Select discount type first");
                    jTextField3.setText("0");
                    return;
                }
                loadNetTotal();
            }
        });

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
        jLabel7.setText("0.00");

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

    private void computeTotalTax() {
        BigDecimal netTotal = new BigDecimal(jLabel7.getText());
        String totalValue = String.valueOf((netTotal.multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
        jComboBox5.addItem(totalValue);
        jComboBox5.setSelectedItem(totalValue);
    }

    private List<PurchaseListedProduct> getAllRows() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        List<PurchaseListedProduct> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            int id = (Integer) model.getValueAt(i, 1);
            String name = (String) model.getValueAt(i, 2);
            // Check if `quantity` and `purchasePrice` are stored as `String`; otherwise, safely convert them.
            int quantity = Integer.parseInt(model.getValueAt(i, 4).toString());
            BigDecimal purchasePrice = (BigDecimal) model.getValueAt(i, 3);
            BigDecimal subtotal = new BigDecimal(model.getValueAt(i, 5).toString());

            // Create a new `PurchaseListedProduct` instance with the parsed values and add it to the list
            PurchaseListedProduct purchaseListedProduct = new PurchaseListedProduct(
                    id,
                    name,
                    quantity,
                    purchasePrice,
                    subtotal
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
        private BigDecimal price;
        private BigDecimal subtotal;
    }

    private void loadNetTotal() {
        List<PurchaseListedProduct> products = getAllRows();

        BigDecimal summation = BigDecimal.ZERO;

        for (int i = 0; i < products.size(); i++) {
            summation = summation.add(products.get(i).getSubtotal());
        }

        BigDecimal transportCost = new BigDecimal(jTextField2.getText());
        BigDecimal fixDiscount = new BigDecimal(jTextField3.getText());
        double percentageDiscount = Double.parseDouble(jTextField3.getText());

        String discountType = (String) jComboBox4.getSelectedItem();

        assert discountType != null;
        if (discountType.equals("Fixed")) {
            BigDecimal tempVar = transportCost.add(summation).subtract(fixDiscount);
            jLabel7.setText(String.valueOf(tempVar.setScale(2, RoundingMode.HALF_UP)));
        } else if (discountType.equals("Percentage %")) {
            double percentage = percentageDiscount / 100;
            BigDecimal tempVar = ((transportCost.add(summation)).multiply(BigDecimal.valueOf(percentage))).setScale(2, RoundingMode.HALF_UP);
            jLabel7.setText(String.valueOf(tempVar));
        } else {
            BigDecimal tempVar = (transportCost.add(summation));
            jLabel7.setText(String.valueOf(tempVar.setScale(2, RoundingMode.HALF_UP)));
        }
        computeTotalTax();
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for a more flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;


        // Create a bold and larger font for the labels
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Item Name *
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel itemNameLabel = new JLabel("Item Name *");
        itemNameLabel.setFont(boldFont);
        panel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        JTextField itemNameField = new JTextField(15);
        itemNameField.setFont(regularFont);
        panel.add(itemNameField, gbc);

        // Item Model
        gbc.gridx = 2;
        JLabel itemModelLabel = new JLabel("Item Model");
        itemModelLabel.setFont(boldFont);
        panel.add(itemModelLabel, gbc);

        gbc.gridx = 3;
        JTextField itemModelField = new JTextField(15);
        itemModelField.setFont(regularFont);
        panel.add(itemModelField, gbc);

        // Item Code *
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel itemStock = new JLabel("Stock *");
        itemStock.setFont(boldFont);
        panel.add(itemStock, gbc);

        gbc.gridx = 1;
        JTextField itemStockField = new JTextField(15);
        itemStockField.setFont(regularFont);
        panel.add(itemStockField, gbc);

        // Category
        gbc.gridx = 2;
        JLabel categoryLabel = new JLabel("Subcategory");
        categoryLabel.setFont(boldFont);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 3;
        Vector<String> productSubcategoryNames = new Vector<>();
        productSubcategoryNames.add("Select Subcategory");
        Map<Integer, Integer> productSubcategoryMap = new HashMap<>();
        ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
        var productSubcategories = productSubcategoryService.getAllValidSubcategories();
        for (int i = 0; i < productSubcategories.size(); i++) {
            productSubcategoryNames.add(productSubcategories.get(i).name());
            productSubcategoryMap.put(i + 1, productSubcategories.get(i).id());
        }
        JComboBox<String> categoryCombo = new JComboBox<>(productSubcategoryNames);
        categoryCombo.setFont(regularFont);
        panel.add(categoryCombo, gbc);

        // Brand
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel brandLabel = new JLabel("Brand");
        brandLabel.setFont(boldFont);
        panel.add(brandLabel, gbc);

        gbc.gridx = 1;
        Vector<String> brandNames = new Vector<>();
        brandNames.add("Select Brand");
        BrandService brandService = new BrandService();
        Map<Integer, Integer> brandMap = new HashMap<>();

        JComboBox<String> brandCombo = new JComboBox<>(brandNames);
        brandCombo.setFont(regularFont);
        brandCombo.setSelectedItem("Select Brand");
        panel.add(brandCombo, gbc);


        categoryCombo.addActionListener(e -> {
            int productSubcategorySelectedIndex = categoryCombo.getSelectedIndex();
            int productSubcategoryId = productSubcategoryMap.get(productSubcategorySelectedIndex);

            brandCombo.removeAllItems();

            var brands = brandService.getAllBrandByProductSubcategoryId(productSubcategoryId);
            brandNames.add("Select Brand");
            brandCombo.setSelectedItem("Select Brand");
            for (int i = 0; i < brands.size(); i++) {
                brandNames.add(brands.get(i).name());
                brandMap.put(i + 1, brands.get(i).id());
            }
        });

        // Unit - Change to JComboBox with options
        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Unit");
        unitLabel.setFont(boldFont);
        panel.add(unitLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> unitCombo = new JComboBox<>(new String[]{"Per Piece", "12 pcs"});
        unitCombo.setFont(regularFont);
        panel.add(unitCombo, gbc);

        // Product Tax *
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel productTaxLabel = new JLabel("Product Tax *");
        productTaxLabel.setFont(boldFont);
        panel.add(productTaxLabel, gbc);

        gbc.gridx = 1;
        Map<String, Integer> productTaxTypeMap = new HashMap<>();
        productTaxTypeMap.put("VAT@12%", 12);

        JTextField productTaxField = new JTextField();
        productTaxField.setFont(regularFont);
        productTaxField.setText("VAT@12%");
        productTaxField.setEnabled(false);
        panel.add(productTaxField, gbc);

        // Tax Type
        gbc.gridx = 2;
        JLabel taxTypeLabel = new JLabel("Tax Type");
        taxTypeLabel.setFont(boldFont);
        panel.add(taxTypeLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> taxTypeCombo = new JComboBox<>(new String[]{"Select Tax Type", "Exclusive", "Inclusive"});
        taxTypeCombo.setFont(regularFont);
        panel.add(taxTypeCombo, gbc);

        // Purchase Price
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel purchasePriceLabel = new JLabel("Purchase Price");
        purchasePriceLabel.setFont(boldFont);
        panel.add(purchasePriceLabel, gbc);

        gbc.gridx = 1;
        final JTextField purchasePriceField = new JTextField(15);
        purchasePriceField.setFont(regularFont);
        purchasePriceField.setEnabled(false);
        panel.add(purchasePriceField, gbc);

        // Regular Price *
        gbc.gridx = 2;
        JLabel sellingPriceLabel = new JLabel("Selling Price *");
        sellingPriceLabel.setFont(boldFont);
        panel.add(sellingPriceLabel, gbc);

        gbc.gridx = 3;
        JTextField sellingPriceField = new JTextField(15);
        sellingPriceField.setFont(regularFont);
        sellingPriceField.setEnabled(false);
        panel.add(sellingPriceField, gbc);

        purchasePriceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void onTextChanged() {

                if (purchasePriceField.getText().isEmpty())
                    return;

                for (int i = 0; i < purchasePriceField.getText().length(); i++) {
                    if (Character.isLetter(purchasePriceField.getText().charAt(i))) {
                        return;
                    }
                }

                if (purchasePriceField.getText().isEmpty())
                    sellingPriceField.setText("");


                double purchase = Double.parseDouble(purchasePriceField.getText());
                sellingPriceField.setText(String.format("%.2f", purchase * 1.12));
            }
        });

        sellingPriceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void onChange() {

                if (sellingPriceField.getText().isEmpty())
                    return;

                for (int i = 0; i < sellingPriceField.getText().length(); i++) {
                    if (Character.isLetter(sellingPriceField.getText().charAt(i))) {
                        return;
                    }
                }

                if (sellingPriceField.getText().isEmpty())
                    purchasePriceField.setText("");

                double purchase = Double.parseDouble(sellingPriceField.getText());
                purchasePriceField.setText(String.format("%.2f", purchase * 0.12 / 1.12));
            }
        });

        taxTypeCombo.addActionListener(e -> {
            String selectedItem = (String) taxTypeCombo.getSelectedItem();
            assert selectedItem != null;
            if (selectedItem.equals("Exclusive")) {
                sellingPriceField.setText("");
                sellingPriceField.setEnabled(false);
                purchasePriceField.setEnabled(true);
            } else if (selectedItem.equals("Inclusive")) {
                sellingPriceField.setEnabled(true);
                purchasePriceField.setEnabled(false);
                purchasePriceField.setText("");
            } else {
                sellingPriceField.setEnabled(false);
                purchasePriceField.setEnabled(false);
                purchasePriceField.setText("");
                sellingPriceField.setText("");
            }
        });

        // Discount
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel discountLabel = new JLabel("Discount");
        discountLabel.setFont(boldFont);
        panel.add(discountLabel, gbc);

        gbc.gridx = 1;
        JTextField discountField = new JTextField(15);
        discountField.setFont(regularFont);
        panel.add(discountField, gbc);

        // Note
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setFont(boldFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea noteArea = new JTextArea(3, 15);
        noteArea.setFont(regularFont);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        panel.add(noteScrollPane, gbc);
        gbc.gridwidth = 1;

        // Alert Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel alertQuantityLabel = new JLabel("Alert Quantity");
        alertQuantityLabel.setFont(boldFont);
        panel.add(alertQuantityLabel, gbc);

        gbc.gridx = 1;
        JTextField alertQuantityField = new JTextField(15);
        alertQuantityField.setFont(regularFont);
        panel.add(alertQuantityField, gbc);

        // Status
        gbc.gridx = 2;
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(boldFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Select status", "Active", "Inactive"});
        statusCombo.setFont(regularFont);
        panel.add(statusCombo, gbc);

        // Image - Modify to use JFileChooser for file upload
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel imageLabel = new JLabel("Image");
        imageLabel.setFont(boldFont);
        panel.add(imageLabel, gbc);

        gbc.gridx = 1;
        JButton imageButton = new JButton("Choose File");
        imageButton.setFont(regularFont);

        // Add action listener for the imageButton to open file explorer
        Base64Converter base64Converter = new Base64Converter();
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    base64Converter.setConvertFileToBase64(selectedFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(imageButton, gbc);

        // Display the form in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the user clicks OK, process the input
        if (result == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText();
            String stock = itemStockField.getText();

            // Validate the required fields
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int brandSelectedIndex = brandCombo.getSelectedIndex();
                int brandId = brandMap.get(brandSelectedIndex);

                String model = itemModelField.getText();
                String unit = (String) unitCombo.getSelectedItem();
                String taxType = (String) taxTypeCombo.getSelectedItem();
                String purchasePrice = purchasePriceField.getText();
                String discount = discountField.getText();
                String note = noteArea.getText();
                String alertQuantity = alertQuantityField.getText();
                String status = (String) statusCombo.getSelectedItem();
                String image = base64Converter.getBase64();
                String sellingPrice = sellingPriceField.getText();

                ProductService productService = new ProductService();
                assert unit != null;
                assert taxType != null;
                assert status != null;
                AddProductRequestDto dto = new AddProductRequestDto(
                        name,
                        model,
                        brandId,
                        unit.equals("Per Piece") ? ProductUnit.PIECE : ProductUnit.DOZEN,
                        productTaxTypeMap.get("VAT@12%"),
                        taxType.equals("Exclusive") ? ProductTaxType.EXCLUSIVE : ProductTaxType.INCLUSIVE,
                        BigDecimal.valueOf(Double.parseDouble(purchasePrice)),
                        BigDecimal.valueOf(Double.parseDouble(sellingPrice)),
                        Integer.parseInt(discount),
                        note,
                        Integer.parseInt(alertQuantity),
                        status.equals("Active") ? ProductStatus.ACTIVE : ProductStatus.INACTIVE,
                        image,
                        Integer.parseInt(stock)
                );
                productService.add(dto);
                JOptionPane.showMessageDialog(null, "Product Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
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
        JTextField amountField = new JTextField(15);
        JTextField chequeNoField = new JTextField(15);
        JTextField receiptNoField = new JTextField(15);
        JTextField poReferenceField = new JTextField(15);
        JTextField paymentTermsField = new JTextField(15);
        JTextField deliveryPlaceField = new JTextField(30); // Increased length for Delivery Place
        JTextField dateField = new JTextField(15);
        JTextField noteField = new JTextField(30);

        // Make receiptNoField non-editable
        receiptNoField.setEditable(false);

        // Fetch the current date and set it in dateField
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateField.setText(currentDate.format(formatter));
        dateField.setEditable(false); // Make the date field non-editable if needed

        // Adding labels and fields to the panel in two columns
        gbc.gridx = 0; gbc.gridy = 0;
        paymentPanel.add(new JLabel("Receipt No:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(receiptNoField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        paymentPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        paymentPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(dateField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        paymentPanel.add(new JLabel("Cheque No:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(chequeNoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        paymentPanel.add(new JLabel("PO Reference:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(poReferenceField, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        paymentPanel.add(new JLabel("Payment Terms:"), gbc);
        gbc.gridx = 3;
        paymentPanel.add(paymentTermsField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        paymentPanel.add(new JLabel("Delivery Place:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; // Span across multiple columns
        paymentPanel.add(deliveryPlaceField, gbc);
        gbc.gridwidth = 1; // Reset grid width for following fields

        gbc.gridx = 0; gbc.gridy = 4;
        paymentPanel.add(new JLabel("Note:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        paymentPanel.add(noteField, gbc);

        // Show the JOptionPane with the custom panel
        int result = JOptionPane.showConfirmDialog(null, paymentPanel, "Add Payment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Handling OK/Cancel button click
        if (result == JOptionPane.OK_OPTION) {
            String amount = amountField.getText();
            String chequeNo = chequeNoField.getText();
            String receiptNo = receiptNoField.getText();
            String poReference = poReferenceField.getText();
            String paymentTerms = paymentTermsField.getText();
            String deliveryPlace = deliveryPlaceField.getText();
            String date = dateField.getText();
            String note = noteField.getText();

            int customerSelectedIndex = jComboBox1.getSelectedIndex();
            int customerId = clientMap.get(customerSelectedIndex);

            String discountType = (String) jComboBox4.getSelectedItem();
            BigDecimal totalTax = BigDecimal.valueOf(Double.parseDouble(jComboBox5.getSelectedItem().toString().isEmpty() ? "0" : jComboBox5.getSelectedItem().toString()));
            BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(jTextField3.getText()));
            BigDecimal transportCost = BigDecimal.valueOf(Double.parseDouble(jTextField2.getText()));
            BigDecimal netTotal = BigDecimal.valueOf(Double.parseDouble(jLabel7.getText()));

            AddSaleRequestDto saleDto = new AddSaleRequestDto(
                    customerId,
                    discountType,
                    discount,
                    transportCost,
                    totalTax,
                    netTotal,
                    receiptNo,
                    BigDecimal.valueOf(Double.parseDouble(amount)),
                    LocalDate.now(),
                    chequeNo,
                    poReference,
                    paymentTerms,
                    deliveryPlace,
                    note
            );

            SaleService saleService = new SaleService();
            Set<AddSaleItemRequestDto> addSaleItemRequestDtoSet = new HashSet<>();
            CurrentUser.id = 2;
            var choseItems = getAllRows();

            for(var item : choseItems){
                AddSaleItemRequestDto dto = new AddSaleItemRequestDto(
                        item.getId(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                );
                addSaleItemRequestDtoSet.add(dto);
            }

            saleService.add(saleDto, addSaleItemRequestDtoSet);

            // Show a JOptionPane to confirm the payment addition
            JOptionPane.showMessageDialog(null, "Payment Added:\n"
                    + "Amount: " + amount + "\n"
                    + "Cheque No: " + chequeNo + "\n"
                    + "Receipt No: " + receiptNo + "\n"
                    + "PO Reference: " + poReference + "\n"
                    + "Payment Terms: " + paymentTerms + "\n"
                    + "Delivery Place: " + deliveryPlace + "\n"
                    + "Date: " + date + "\n"
                    + "Note: " + note);
        } else {
            // Show a message if payment is canceled
            JOptionPane.showMessageDialog(null, "Payment cancelled.");
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

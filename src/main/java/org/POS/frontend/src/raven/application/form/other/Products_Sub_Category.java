
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.product_subcategory.AddProductSubcategoryRequestDto;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.backend.product_subcategory.ProductSubcategoryStatus;
import org.POS.backend.product_subcategory.UpdateProductSubcategoryRequestDto;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Timer;
import java.util.*;

public class Products_Sub_Category extends javax.swing.JPanel {

    private Timer timer;

    public Products_Sub_Category() {
        initComponents();

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int subcategoryId = (Integer) model.getValueAt(row, 1);
                String subcategoryName = (String) model.getValueAt(row, 3);
                String code = (String) model.getValueAt(row, 4);
                String status = (String) model.getValueAt(row, 5);

                // Create a panel for displaying the data
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                Font largerFont = new Font("Arial", Font.BOLD, 18);
                Font detailsFont = new Font("Arial", Font.PLAIN, 16);

                ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
                var productSubcategory = productSubcategoryService.getValidSubcategory(subcategoryId);

                ProductCategoryService productCategoryService = new ProductCategoryService();
                var productCategories = productCategoryService.getAllValidProductCategories();

                // Category
                JLabel categoryLabel = new JLabel("Category:");
                categoryLabel.setFont(largerFont);

                Map<Integer, Integer> productCategoryMap = new HashMap<>();

                Vector<String> productCategoryNames = new Vector<>();
                productCategoryNames.add("Select Category");
                for (int i = 0; i < productCategories.size(); i++) {
                    productCategoryNames.add(productCategories.get(i).name());
                    productCategoryMap.put(i + 1, productCategories.get(i).id());
                }

                JComboBox<String> categoryComboBox = new JComboBox<>(productCategoryNames);
                categoryComboBox.setSelectedItem(productSubcategory.productCategory().name());
                categoryComboBox.setFont(detailsFont);


                // Subcategory Code
                JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
                subCategoryCodeLabel.setFont(largerFont);
                JTextField subCategoryCodeField = new JTextField(15);
                subCategoryCodeField.setText(code);
                subCategoryCodeField.setFont(detailsFont);
                subCategoryCodeField.setEnabled(false);

                // Subcategory Name
                JLabel nameLabel = new JLabel("SubCategory Name:");
                nameLabel.setFont(largerFont);
                JTextField nameField = new JTextField(15);
                nameField.setText(subcategoryName);
                nameField.setFont(detailsFont);

                // Status
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(largerFont);
                String[] statuses = {"Select Status", "Active", "Inactive"};
                JComboBox<String> statusComboBox = new JComboBox<>(statuses);
                statusComboBox.setFont(detailsFont);
                statusComboBox.setSelectedItem(status.equals("ACTIVE") ? "Active" : "Inactive");

                // Note
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);
                JTextArea noteArea = new JTextArea(5, 20);
                noteArea.setText(productSubcategory.note());
                noteArea.setFont(detailsFont);
                JScrollPane noteScrollPane = new JScrollPane(noteArea);

                // Add components to the panel
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(categoryLabel, gbc);
                gbc.gridx = 1;
                panel.add(categoryComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(subCategoryCodeLabel, gbc);
                gbc.gridx = 1;
                panel.add(subCategoryCodeField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(nameLabel, gbc);
                gbc.gridx = 1;
                panel.add(nameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(statusLabel, gbc);
                gbc.gridx = 1;
                panel.add(statusComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                panel.add(noteLabel, gbc);
                gbc.gridx = 1;
                panel.add(noteScrollPane, gbc);

                panel.setPreferredSize(new Dimension(500, 300));

                // Show the edit panel
                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Edit SubCategory", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String subCategoryCode = subCategoryCodeField.getText().trim();
                    String name = nameField.getText().trim();
                    String updatedStatus = (String) statusComboBox.getSelectedItem();
                    String note = noteArea.getText().trim();
                    int productCategoryComboIndex = categoryComboBox.getSelectedIndex();
                    int categoryId = productCategoryMap.get(productCategoryComboIndex);
                    if (categoryId == 0 || subCategoryCode.isEmpty() || name.isEmpty() || status.equals("Select Status")) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid Category, Subcategory Code, Name, and Status.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        assert updatedStatus != null;
                        UpdateProductSubcategoryRequestDto dto = new UpdateProductSubcategoryRequestDto(
                                subcategoryId,
                                name,
                                updatedStatus.equals("Active") ? ProductSubcategoryStatus.ACTIVE : ProductSubcategoryStatus.INACTIVE,
                                note,
                                categoryId
                        );
                        productSubcategoryService.update(dto);

                        JOptionPane.showMessageDialog(null,
                                "SubCategory Updated Successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadProductCategories();
                    }
                }
            }


            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                // Confirm before deleting
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this SubCategory?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int subcategoryId = (Integer) model.getValueAt(row, 1);
                    ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
                    productSubcategoryService.delete(subcategoryId);

                    JOptionPane.showMessageDialog(null, "SubCategory Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadProductCategories();
                }
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int subcategoryId = (Integer) model.getValueAt(row, 1);
                String category = (String) model.getValueAt(row, 2);
                String subcategory = (String) model.getValueAt(row, 3);
                String subcategoryCode = (String) model.getValueAt(row, 4);
                String status = (String) model.getValueAt(row, 5);         // Status

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                Font largerFont = new Font("Arial", Font.BOLD, 18);
                Font detailsFont = new Font("Arial", Font.PLAIN, 16);

                // Category Label and Value
                JLabel categoryLabel = new JLabel("Category:");
                categoryLabel.setFont(largerFont);
                JLabel categoryValue = new JLabel(category);
                categoryValue.setFont(detailsFont);

                // SubCategory Code Label and Value
                JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
                subCategoryCodeLabel.setFont(largerFont);
                JLabel subCategoryCodeValue = new JLabel(subcategoryCode);
                subCategoryCodeValue.setFont(detailsFont);

                // SubCategory Name Label and Value
                JLabel nameLabel = new JLabel("SubCategory Name:");
                nameLabel.setFont(largerFont);
                JLabel nameValue = new JLabel(subcategory);
                nameValue.setFont(detailsFont);

                // Status Label and Value
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(largerFont);
                JLabel statusValue = new JLabel(status);
                statusValue.setFont(detailsFont);

                ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
                var productSubcategory = productSubcategoryService.getValidSubcategory(subcategoryId);
                // Note Label and Value (TextArea for multiline note)
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);
                JTextArea noteValue = new JTextArea(5, 20);
                noteValue.setText(productSubcategory.note());
                noteValue.setEditable(false);
                noteValue.setFocusable(false);
                noteValue.setOpaque(false);
                noteValue.setWrapStyleWord(true);
                noteValue.setLineWrap(true);
                noteValue.setFont(detailsFont);
                JScrollPane noteScrollPane = new JScrollPane(noteValue);
                noteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                // Adding components to the panel in the correct grid positions

                // Add Category row
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(categoryLabel, gbc);
                gbc.gridx = 1;
                panel.add(categoryValue, gbc);

                // Add SubCategory Code row (incremented gridy)
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(subCategoryCodeLabel, gbc);
                gbc.gridx = 1;
                panel.add(subCategoryCodeValue, gbc);

                // Add SubCategory Name row (incremented gridy)
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(nameLabel, gbc);
                gbc.gridx = 1;
                panel.add(nameValue, gbc);

                // Add Status row (incremented gridy)
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(statusLabel, gbc);
                gbc.gridx = 1;
                panel.add(statusValue, gbc);

                // Add Note row (incremented gridy)
                gbc.gridx = 0;
                gbc.gridy = 4;
                panel.add(noteLabel, gbc);
                gbc.gridx = 1;
                panel.add(noteScrollPane, gbc);

                panel.setPreferredSize(new Dimension(500, 300));

                // Display the panel in JOptionPane
                JOptionPane.showMessageDialog(null, panel, "View SubCategory", JOptionPane.INFORMATION_MESSAGE);
            }

        };
        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        loadProductCategories();
    }

    private void loadProductCategories() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
        var productSubcategories = productSubcategoryService.getAllValidSubcategories();
        for (int i = 0; i < productSubcategories.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    productSubcategories.get(i).id(),
                    productSubcategories.get(i).productCategory().name(),
                    productSubcategories.get(i).name(),
                    productSubcategories.get(i).code(),
                    productSubcategories.get(i).status().name()

            });
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

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Product Categories");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Sub Categories");

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
                        "#", "ID", "Category", "Sub Category Name	", "Sub Category Code	", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    true, true, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
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
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addContainerGap(1126, Short.MAX_VALUE))
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
            loadProductCategories();
            return;
        }

        ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
        var productSubcategories = productSubcategoryService.getAllValidProductSubcategoryByName(name);

        for (int i = 0; i < productSubcategories.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    productSubcategories.get(i).id(),
                    productSubcategories.get(i).categoryName(),
                    productSubcategories.get(i).name(),
                    productSubcategories.get(i).code(),
                    productSubcategories.get(i).status().name()
            });
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        Font boldFont = new Font("Arial", Font.BOLD, 14);

        ProductCategoryService productCategoryService = new ProductCategoryService();
        var productCategories = productCategoryService.getAllValidProductCategories();
        Map<Integer, Integer> productCategoryMap = new HashMap<>();
        // Input fields
        JLabel categoryLabel = new JLabel("Category Name:");
        categoryLabel.setFont(boldFont);
        Vector<String> categoryNames = new Vector<>();
        categoryNames.add("Select a category");
        for (int i = 0; i < productCategories.size(); i++) {
            categoryNames.add(productCategories.get(i).name());
            productCategoryMap.put(i + 1, productCategories.get(i).id());
        }

        JComboBox<String> categoryComboBox = new JComboBox<>(categoryNames);
        JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
        subCategoryCodeLabel.setFont(boldFont);
        JTextField subCategoryCodeField = new JTextField(15);
        subCategoryCodeField.setEnabled(false);
        subCategoryCodeField.setText("This is system generated");

        JLabel nameLabel = new JLabel("SubCategory Name:");
        nameLabel.setFont(boldFont);
        JTextField nameField = new JTextField(15);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(boldFont);
        String[] statuses = {"Active", "Inactive"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(boldFont);
        JTextArea noteArea = new JTextArea(5, 20);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(subCategoryCodeLabel, gbc);
        gbc.gridx = 1;
        panel.add(subCategoryCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(statusLabel, gbc);
        gbc.gridx = 1;
        panel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(noteLabel, gbc);
        gbc.gridx = 1;
        panel.add(noteScrollPane, gbc);

        panel.setPreferredSize(new Dimension(600, 350));

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Create Product Sub Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String category = (String) categoryComboBox.getSelectedItem();
            String subCategoryCode = subCategoryCodeField.getText().trim();
            String name = nameField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            String note = noteArea.getText().trim();

            if (name.isEmpty() || subCategoryCode.isEmpty() || category.equals("Select a category")) {
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
                UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
                JOptionPane.showMessageDialog(null,
                        "Please enter a Name, Subcategory Code and select a valid Category.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
                UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
                int productCategoryIndex = categoryComboBox.getSelectedIndex();
                int productCategoryId = productCategoryMap.get(productCategoryIndex);
                ProductSubcategoryService productSubcategoryService = new ProductSubcategoryService();
                AddProductSubcategoryRequestDto dto = new AddProductSubcategoryRequestDto(
                        productCategoryId,
                        name,
                        status.equals("Active") ? ProductSubcategoryStatus.ACTIVE : ProductSubcategoryStatus.INACTIVE,
                        note
                );
                productSubcategoryService.add(dto);
                JOptionPane.showMessageDialog(null,
                        "Sub Category Created Successfully!\nCategory: " + category + "\nSubCategory Code: " + subCategoryCode +
                                "\nName: " + name + "\nStatus: " + status + "\nNote: " + note,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadProductCategories();
            }
        }
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

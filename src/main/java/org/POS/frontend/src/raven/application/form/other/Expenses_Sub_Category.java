
package org.POS.frontend.src.raven.application.form.other;
import javax.swing.*;
import java.awt.*;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import javax.swing.table.DefaultTableModel;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionEvent;

public class Expenses_Sub_Category extends javax.swing.JPanel {


public Expenses_Sub_Category() {
    initComponents();
    
    TableActionEvent event = new TableActionEvent() {
@Override
public void onEdit(int row) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String currentCategory = (String) model.getValueAt(row, 0);         // Category
    String currentSubCategoryCode = (String) model.getValueAt(row, 1);  // Subcategory Code
    String currentName = (String) model.getValueAt(row, 2);             // Subcategory Name
    String currentStatus = (String) model.getValueAt(row, 3);           // Status
    String currentNote = (String) model.getValueAt(row, 4);             // Note

    // Create a panel for displaying the data
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    Font largerFont = new Font("Arial", Font.BOLD, 18); 
    Font detailsFont = new Font("Arial", Font.PLAIN, 16);

    // Category
    JLabel categoryLabel = new JLabel("Category:");
    categoryLabel.setFont(largerFont);
    JTextField categoryField = new JTextField(15);
    categoryField.setText(currentCategory);
    categoryField.setFont(detailsFont);

    // Subcategory Code
    JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
    subCategoryCodeLabel.setFont(largerFont);
    JTextField subCategoryCodeField = new JTextField(15);
    subCategoryCodeField.setText(currentSubCategoryCode);
    subCategoryCodeField.setFont(detailsFont);

    // Subcategory Name
    JLabel nameLabel = new JLabel("SubCategory Name:");
    nameLabel.setFont(largerFont);
    JTextField nameField = new JTextField(15);
    nameField.setText(currentName);
    nameField.setFont(detailsFont);

    // Status
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(largerFont);
    String[] statuses = {"Select Status", "Active", "Inactive"};
    JComboBox<String> statusComboBox = new JComboBox<>(statuses);
    statusComboBox.setFont(detailsFont);
    statusComboBox.setSelectedItem(currentStatus);

    // Note
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(largerFont);
    JTextArea noteArea = new JTextArea(5, 20);
    noteArea.setText(currentNote);
    noteArea.setFont(detailsFont);
    JScrollPane noteScrollPane = new JScrollPane(noteArea);

    // Add components to the panel
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(categoryLabel, gbc);
    gbc.gridx = 1;
    panel.add(categoryField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(subCategoryCodeLabel, gbc);
    gbc.gridx = 1;
    panel.add(subCategoryCodeField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(nameLabel, gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(statusLabel, gbc);
    gbc.gridx = 1;
    panel.add(statusComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(noteLabel, gbc);
    gbc.gridx = 1;
    panel.add(noteScrollPane, gbc);

    panel.setPreferredSize(new Dimension(500, 300));

    // Show the edit panel
    int result = JOptionPane.showConfirmDialog(null, panel, 
            "Edit SubCategory", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String category = categoryField.getText().trim();
        String subCategoryCode = subCategoryCodeField.getText().trim();
        String name = nameField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();
        String note = noteArea.getText().trim();

        if (category.isEmpty() || subCategoryCode.isEmpty() || name.isEmpty() || status.equals("Select Status")) {
            JOptionPane.showMessageDialog(null, 
                "Please enter valid Category, Subcategory Code, Name, and Status.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Update the table with the edited values
            model.setValueAt(category, row, 0);
            model.setValueAt(subCategoryCode, row, 1);
            model.setValueAt(name, row, 2);
            model.setValueAt(status, row, 3);
            model.setValueAt(note, row, 4);

            JOptionPane.showMessageDialog(null, 
                "SubCategory Updated Successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
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
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "SubCategory Deleted Successfully", 
                    "Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @Override
        public void onView(int row) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    String currentSubCategoryCode = (String) model.getValueAt(row, 0);  // Assuming column 0 is the SubCategory Code
    String currentCategory = (String) model.getValueAt(row, 1);         // Category
    String currentName = (String) model.getValueAt(row, 2);             // SubCategory Name
    String currentNote = (String) model.getValueAt(row, 3);             // Note
    String currentStatus = (String) model.getValueAt(row, 4);           // Status

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
    JLabel categoryValue = new JLabel(currentCategory);
    categoryValue.setFont(detailsFont);

    // SubCategory Code Label and Value
    JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
    subCategoryCodeLabel.setFont(largerFont);
    JLabel subCategoryCodeValue = new JLabel(currentSubCategoryCode);
    subCategoryCodeValue.setFont(detailsFont);

    // SubCategory Name Label and Value
    JLabel nameLabel = new JLabel("SubCategory Name:");
    nameLabel.setFont(largerFont);
    JLabel nameValue = new JLabel(currentName);
    nameValue.setFont(detailsFont);

    // Status Label and Value
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(largerFont);
    JLabel statusValue = new JLabel(currentStatus);
    statusValue.setFont(detailsFont);

    // Note Label and Value (TextArea for multiline note)
    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(largerFont);
    JTextArea noteValue = new JTextArea(5, 20);
    noteValue.setText(currentNote);
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
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(categoryLabel, gbc);
    gbc.gridx = 1;
    panel.add(categoryValue, gbc);

    // Add SubCategory Code row (incremented gridy)
    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(subCategoryCodeLabel, gbc);
    gbc.gridx = 1;
    panel.add(subCategoryCodeValue, gbc);

    // Add SubCategory Name row (incremented gridy)
    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(nameLabel, gbc);
    gbc.gridx = 1;
    panel.add(nameValue, gbc);

    // Add Status row (incremented gridy)
    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(statusLabel, gbc);
    gbc.gridx = 1;
    panel.add(statusValue, gbc);

    // Add Note row (incremented gridy)
    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(noteLabel, gbc);
    gbc.gridx = 1;
    panel.add(noteScrollPane, gbc);

    panel.setPreferredSize(new Dimension(500, 300));

    // Display the panel in JOptionPane
    JOptionPane.showMessageDialog(null, panel, "View SubCategory", JOptionPane.INFORMATION_MESSAGE);
}

    };

    if (table.getColumnModel().getColumnCount() > 4) {
        table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
    } else {
        System.err.println("Error: Table does not have enough columns!");
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
        jLabel1.setText("Expenses Categories");

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

        jTextField1.setText("Search");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Category", "Sub Category Code	", "Sub Category Name	", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
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
                .addContainerGap(1111, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;

    Font boldFont = new Font("Arial", Font.BOLD, 14);

    // Input fields
    JLabel categoryLabel = new JLabel("Category Name:");
    categoryLabel.setFont(boldFont);
    String[] categories = {"Select a category", "Category 1", "Category 2", "Category 3"};
    JComboBox<String> categoryComboBox = new JComboBox<>(categories);

    JLabel subCategoryCodeLabel = new JLabel("SubCategory Code:");
    subCategoryCodeLabel.setFont(boldFont);
    JTextField subCategoryCodeField = new JTextField(15);

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
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(categoryLabel, gbc);
    gbc.gridx = 1;
    panel.add(categoryComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(subCategoryCodeLabel, gbc);
    gbc.gridx = 1;
    panel.add(subCategoryCodeField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(nameLabel, gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(statusLabel, gbc);
    gbc.gridx = 1;
    panel.add(statusComboBox, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(noteLabel, gbc);
    gbc.gridx = 1;
    panel.add(noteScrollPane, gbc);

    panel.setPreferredSize(new Dimension(600, 350));

    int result = JOptionPane.showConfirmDialog(null, panel, 
             "Create Exprenses Sub Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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
            
            JOptionPane.showMessageDialog(null, 
                "Sub Category Created Successfully!\nCategory: " + category + "\nSubCategory Code: " + subCategoryCode +
                "\nName: " + name + "\nStatus: " + status + "\nNote: " + note, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addRow(new Object[]{category, subCategoryCode, name, status, note});
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

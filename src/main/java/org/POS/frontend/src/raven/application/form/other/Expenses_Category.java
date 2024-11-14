package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.expense_category.*;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class Expenses_Category extends javax.swing.JPanel {

    private Timer timer;

    public Expenses_Category() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int id = (Integer) model.getValueAt(row, 1);
                String currentName = (String) model.getValueAt(row, 2);
//                String currentCode = (String) model.getValueAt(row, 3);
                String currentNote = (String) model.getValueAt(row, 4);
                String currentStatus = (String) model.getValueAt(row, 5);

                // Create a panel for displaying the data
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
                gbc.fill = GridBagConstraints.BOTH;  // Allow components to fill their space
                gbc.weightx = 1.0; // Allow horizontal expansion
                gbc.weighty = 1.0; // Allow vertical expansion

                // Create a larger font for labels and text
                Font largerFont = new Font("Arial", Font.BOLD, 18); // Larger bold font for labels
                Font detailsFont = new Font("Arial", Font.PLAIN, 16); // Larger plain font for details

                // Create input fields for editing
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont(largerFont);
                JTextField nameField = new JTextField(15);
                nameField.setText(currentName);
                nameField.setFont(detailsFont);

                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(largerFont);
                String[] statuses = {"Select Status", "Active", "Inactive"};
                JComboBox<String> statusComboBox = new JComboBox<>(statuses);
                statusComboBox.setFont(detailsFont);
                statusComboBox.setSelectedItem(currentStatus.equals("ACTIVE") ? "Active" : "Inactive");

                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);
                JTextArea noteArea = new JTextArea(5, 20);
                noteArea.setText(currentNote);
                noteArea.setFont(detailsFont);
                JScrollPane noteScrollPane = new JScrollPane(noteArea);

                // Add components to the panel with GridBagLayout
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(nameLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(nameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(statusLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(statusComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(noteLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                panel.add(noteScrollPane, gbc);

                // Set a preferred size for the panel to ensure everything fits nicely
                panel.setPreferredSize(new Dimension(500, 300));

                // Show the edit panel in a JOptionPane
                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Edit Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Handle the OK button press
                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String status = (String) statusComboBox.getSelectedItem();
                    String note = noteArea.getText().trim();

                    if (name.isEmpty() || status.equals("Select Status")) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a valid Name and Status.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
                        UpdateExpenseCategoryRequestDto dto = new UpdateExpenseCategoryRequestDto(
                                id,
                                name,
                                status.equals("Active") ? ExpenseCategoryStatus.ACTIVE : ExpenseCategoryStatus.INACTIVE,
                                note
                        );

                        expenseCategoryService.update(dto);

                        // Display a success message
                        JOptionPane.showMessageDialog(null,
                                GlobalVariable.CATEGORY_UPDATED,
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        loadCategories();
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
                        "Are you sure you want to delete this Category?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int id = (Integer) model.getValueAt(row, 1);
                    ExpenseCategoryService service = new ExpenseCategoryService();
                    service.delete(id);

                    JOptionPane.showMessageDialog(null, "Category Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadCategories();
                }
            }

            @Override
            public void onView(int row) {
                // Fetch current data from the selected row
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int categoryId = (Integer) model.getValueAt(row, 1);
                String currentName = (String) model.getValueAt(row, 2);
                String currentNote = (String) model.getValueAt(row, 4);
                String currentStatus = (String) model.getValueAt(row, 5);

                // Create a panel for displaying the data
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
                gbc.fill = GridBagConstraints.BOTH;  // Allow components to fill their space
                gbc.weightx = 1.0; // Allow horizontal expansion
                gbc.weighty = 1.0; // Allow vertical expansion

                // Create a larger font for labels and text
                Font largerFont = new Font("Arial", Font.BOLD, 18); // Larger bold font for labels
                Font detailsFont = new Font("Arial", Font.PLAIN, 16); // Larger plain font for details

                // Create labels to display the data in larger font
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont(largerFont);
                JLabel nameValue = new JLabel(currentName);
                nameValue.setFont(detailsFont); // Set larger font for the details

                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(largerFont);
                JLabel statusValue = new JLabel(currentStatus);
                statusValue.setFont(detailsFont); // Set larger font for the details

                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);

                JTextArea noteValue = new JTextArea(5, 20);
                noteValue.setText(currentNote);
                noteValue.setEditable(false);       // Make it non-editable
                noteValue.setFocusable(false);      // Disable focus so it can't be clicked
                noteValue.setOpaque(false);         // Make it look like a label by removing the background
                noteValue.setWrapStyleWord(true);   // Wrap words properly
                noteValue.setLineWrap(true);        // Enable line wrapping
                noteValue.setFont(detailsFont);     // Set larger font for the details
                JScrollPane noteScrollPane = new JScrollPane(noteValue);
                noteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar

                // Add components to the panel
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(nameLabel, gbc);
                gbc.gridx = 1;
                panel.add(nameValue, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(statusLabel, gbc);
                gbc.gridx = 1;
                panel.add(statusValue, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(noteLabel, gbc);
                gbc.gridx = 1;
                panel.add(noteScrollPane, gbc);

                // Set a preferred size for the panel to ensure everything fits nicely
                panel.setPreferredSize(new Dimension(500, 300));

                // Show the read-only panel in a JOptionPane with larger fonts
                JOptionPane.showMessageDialog(null, panel, "View Category", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        loadCategories();
    }

    private void loadCategories() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // clear existing table
        model.setRowCount(0);

        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

        SwingWorker<List<ExpenseCategoryResponseDto>, Void> categoriesWorker = new SwingWorker<List<ExpenseCategoryResponseDto>, Void>() {
            @Override
            protected List<ExpenseCategoryResponseDto> doInBackground() throws Exception {
                var categories = expenseCategoryService.getAllValidExpenseCategories();
                return categories;
            }

            @Override
            protected void done() {
                try {
                    List<ExpenseCategoryResponseDto> categories = get();

                    for (int i = 0; i < categories.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                categories.get(i).id(),
                                categories.get(i).name(),
                                categories.get(i).code(),
                                categories.get(i).note(),
                                categories.get(i).status().name()
                        });
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        categoriesWorker.execute();
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
        jLabel2.setText("Expenses Categories");

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
                        {null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "ID", "Name", "Code", "Note", "Status", "Action"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                    true, false, false, true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
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
                                                .addGap(0, 1105, Short.MAX_VALUE)))
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

        String name = jTextField1.getText();

        if (name.isEmpty()) {
            loadCategories();
            return;
        }

        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

        SwingWorker<List<ExpenseCategoryResponseDto>, Void> categoryWorker = new SwingWorker<List<ExpenseCategoryResponseDto>, Void>() {
            @Override
            protected List<ExpenseCategoryResponseDto> doInBackground() throws Exception {

                var categories = expenseCategoryService.getAllValidExpenseCategoryByName(name);
                return categories;
            }

            @Override
            protected void done() {
                try {
                    var categories = get();

                    for (int i = 0; i < categories.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                categories.get(i).id(),
                                categories.get(i).name(),
                                categories.get(i).code(),
                                categories.get(i).note(),
                                categories.get(i).status().name()
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        categoryWorker.execute();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
        gbc.fill = GridBagConstraints.BOTH;  // Allow components to fill their space
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 1.0; // Allow vertical expansion

        // Create a larger font for labels and text
        Font largerFont = new Font("Arial", Font.BOLD, 18); // Larger bold font for labels
        Font detailsFont = new Font("Arial", Font.PLAIN, 16); // Larger plain font for details

        // Create input fields for Name and Status
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(largerFont);
        JTextField nameField = new JTextField(15);
        nameField.setFont(detailsFont);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(largerFont);
        String[] statuses = {"Select Status", "Active", "Inactive"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setFont(detailsFont);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(largerFont);
        JTextArea noteArea = new JTextArea(5, 20);
        noteArea.setFont(detailsFont);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);

        // Add components to the panel with GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(noteScrollPane, gbc);

        // Set a preferred size for the panel to ensure everything fits nicely
        panel.setPreferredSize(new Dimension(500, 300));

        // Show the custom panel in a JOptionPane with the larger font
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Create Expenses Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Handle the OK button press
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            String note = noteArea.getText().trim();

            if (name.isEmpty() || status.equals("Select Status")) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a Name and select a valid Status.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Add the entered information to the JTable
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                model.addRow(new Object[]{model.getRowCount() + 1, name, note, status, "Actions"});

                ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

                AddExpenseCategoryRequestDto dto = new AddExpenseCategoryRequestDto(
                        name,
                        status.equals("Active") ? ExpenseCategoryStatus.ACTIVE : ExpenseCategoryStatus.INACTIVE,
                        note
                );

                expenseCategoryService.add(dto);

                // Display a success message
                JOptionPane.showMessageDialog(null,
                        "Category Created Successfully!\nName: " + name + "\nStatus: " + status + "\nNote: " + note,
                        GlobalVariable.CATEGORY_ADDED, JOptionPane.INFORMATION_MESSAGE);
                loadCategories();
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

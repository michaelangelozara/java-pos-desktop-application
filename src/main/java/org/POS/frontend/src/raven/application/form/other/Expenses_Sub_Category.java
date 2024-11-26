package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.expense_category.ExpenseCategoryResponseDto;
import org.POS.backend.expense_category.ExpenseCategoryService;
import org.POS.backend.expense_subcategory.*;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Expenses_Sub_Category extends javax.swing.JPanel {

    private Timer timer;

    public Expenses_Sub_Category() {
        initComponents();

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int subcategoryId = (Integer) model.getValueAt(row, 1);
                String currentCategory = (String) model.getValueAt(row, 3);         // Category
                String currentStatus = (String) model.getValueAt(row, 5);           // Status

                ExpenseSubcategoryService expenseSubcategoryService = new ExpenseSubcategoryService();

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

                ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();


                Map<Integer, Integer> categoryMap = new HashMap<>();

                JComboBox<String> categoryComboBox = new JComboBox<>();
                categoryComboBox.setFont(detailsFont);

                SwingWorker<ExpenseSubcategoryResponseDto, Void> worker = new SwingWorker<>() {
                    @Override
                    protected ExpenseSubcategoryResponseDto doInBackground() throws Exception {
                        return expenseSubcategoryService.getValidExpenseSubcategoryById(subcategoryId);
                    }

                    @Override
                    protected void done() {
                        try {
                            var expenseSubcategory = get();
                            categoryComboBox.setSelectedItem(expenseSubcategory.expenseCategoryResponseDto().name());
                            categoryComboBox.setEnabled(true);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };

                SwingWorker<List<ExpenseCategoryResponseDto>, Void> categoryWorker = new SwingWorker<List<ExpenseCategoryResponseDto>, Void>() {
                    @Override
                    protected List<ExpenseCategoryResponseDto> doInBackground() throws Exception {
                        categoryComboBox.addItem("Loading...");
                        categoryComboBox.setSelectedItem("Loading...");
                        categoryComboBox.setEnabled(false);
                        var validCategories = expenseCategoryService.getAllValidExpenseCategories();
                        return validCategories;
                    }

                    @Override
                    protected void done() {
                        try {
                            var validCategories = get();
                            SwingUtilities.invokeLater(() -> {
                                categoryComboBox.removeAllItems();
                                categoryComboBox.addItem("Select Category");
                                for (int i = 0; i < validCategories.size(); i++) {
                                    categoryComboBox.addItem(validCategories.get(i).name());
                                    categoryMap.put(i + 1, validCategories.get(i).id());
                                }
                            });
                            worker.execute();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                categoryWorker.execute();

                // Subcategory Name
                JLabel nameLabel = new JLabel("SubCategory Name:");
                nameLabel.setFont(largerFont);
                JTextField nameField = new JTextField(15);
                nameField.setText(currentCategory);
                nameField.setFont(detailsFont);

                // Status
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(largerFont);
                String[] statuses = {"Select Status", "Active", "Inactive"};
                JComboBox<String> statusComboBox = new JComboBox<>(statuses);
                statusComboBox.setFont(detailsFont);
                statusComboBox.setSelectedItem(currentStatus.equals("ACTIVE") ? "Active" : "Inactive");

                // Note
                ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
                var expense = subcategoryService.getValidExpenseSubcategoryById(subcategoryId);
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);
                JTextArea noteArea = new JTextArea(5, 20);
                noteArea.setText(expense.note());
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
                    int selectedCategoryIndex = categoryComboBox.getSelectedIndex();
//                    String subCategoryCode = subCategoryCodeField.getText().trim();
                    String name = nameField.getText().trim();
                    String status = (String) statusComboBox.getSelectedItem();
                    String note = noteArea.getText().trim();

                    if (selectedCategoryIndex == 0 || name.isEmpty() || status.equals("Select Status")) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid Category, Subcategory Code, Name, and Status.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int categoryId = categoryMap.get(selectedCategoryIndex);
                        ExpenseSubcategoryService productSubcategoryService = new ExpenseSubcategoryService();
                        UpdateExpenseSubcategoryRequestDto dto = new UpdateExpenseSubcategoryRequestDto(
                                subcategoryId,
                                name,
                                status.equals("Active") ? ExpenseSubcategoryStatus.ACTIVE : ExpenseSubcategoryStatus.INACTIVE,
                                note,
                                categoryId
                        );
                        productSubcategoryService.update(dto);

                        JOptionPane.showMessageDialog(null,
                                "SubCategory Updated Successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        loadSubcategories();
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

                    ExpenseSubcategoryService expenseSubcategoryService = new ExpenseSubcategoryService();
                    expenseSubcategoryService.delete(subcategoryId);

                    JOptionPane.showMessageDialog(null, "SubCategory Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);

                    loadSubcategories();
                }
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                String currentSubCategoryCode = (String) model.getValueAt(row, 0);  // Assuming column 0 is the SubCategory Code
//                String currentCategory = (String) model.getValueAt(row, 1);         // Category
//                String currentName = (String) model.getValueAt(row, 2);             // SubCategory Name
//                String currentNote = (String) model.getValueAt(row, 3);             // Note
//                String currentStatus = (String) model.getValueAt(row, 4);           // Status

                int subcategoryId = (Integer) model.getValueAt(row, 1);
                String category = (String) model.getValueAt(row, 2);  // Assuming column 0 is the SubCategory Code
                String subcategory = (String) model.getValueAt(row, 3);         // Category
                String subcategoryCode = (String) model.getValueAt(row, 4);             // SubCategory Name
                String status = (String) model.getValueAt(row, 5);             // Note


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

                ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
                var expenseSubcategory = subcategoryService.getValidExpenseSubcategoryById(subcategoryId);
                // Note Label and Value (TextArea for multiline note)
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(largerFont);
                JTextArea noteValue = new JTextArea(5, 20);
                noteValue.setText(expenseSubcategory.note());
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
        makeCellCenter(table);
        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        loadSubcategories();
    }
    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
    }

    private void loadSubcategories() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // clear existing table
        model.setRowCount(0);

        // Clear all existing rows
        ExpenseSubcategoryService expenseSubcategoryService = new ExpenseSubcategoryService();

        SwingWorker<List<ExpenseSubcategoryResponseDto>, Void> worker = new SwingWorker<List<ExpenseSubcategoryResponseDto>, Void>() {
            @Override
            protected List<ExpenseSubcategoryResponseDto> doInBackground() throws Exception {
                var subcategories = expenseSubcategoryService.getAllValidExpenseSubcategories();
                return subcategories;
            }

            @Override
            protected void done() {
                try {
                    var subcategories = get();

                    for (int i = 0; i < subcategories.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                subcategories.get(i).id(),
                                subcategories.get(i).expenseCategoryResponseDto().name(),
                                subcategories.get(i).name(),
                                subcategories.get(i).code(),
                                subcategories.get(i).status().name()
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

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
            loadSubcategories();
            return;
        }
        ExpenseSubcategoryService expenseSubcategoryService = new ExpenseSubcategoryService();

        SwingWorker<List<ExpenseSubcategoryResponseDto>, Void> worker = new SwingWorker<List<ExpenseSubcategoryResponseDto>, Void>() {
            @Override
            protected List<ExpenseSubcategoryResponseDto> doInBackground() throws Exception {
                var subcategories = expenseSubcategoryService.getAllValidExpenseSubcategoryByName(name);
                return subcategories;
            }

            @Override
            protected void done() {
                try {
                    var subcategories = get();

                    for (int i = 0; i < subcategories.size(); i++) {
                        model.addRow(new Object[]{
                                i + 1,
                                subcategories.get(i).id(),
                                subcategories.get(i).expenseCategoryResponseDto().name(),
                                subcategories.get(i).name(),
                                subcategories.get(i).code(),
                                subcategories.get(i).status().name()
                        });
                    }
                }catch (Exception e){
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
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "ID", "Category", "Sub Category Name	", "Sub Category Code	", "Status", "Action"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                    true, true, false, false, false, false, true
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

        ExpenseCategoryService expenseSubcategoryService1 = new ExpenseCategoryService();
        var validCategories = expenseSubcategoryService1.getAllValidExpenseCategories();

        Map<Integer, Integer> categoryMap = new HashMap<>();

        String[] categories = new String[validCategories.size() + 1];
        categories[0] = "Select a category";
        for (int i = 1; i < categories.length; i++) {
            categories[i] = validCategories.get(i - 1).name();
            categoryMap.put(i, validCategories.get(i - 1).id());
        }

        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        JLabel nameLabel = new JLabel("Subcategory Name:");
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

//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        panel.add(subCategoryCodeLabel, gbc);
//        gbc.gridx = 1;
//        panel.add(subCategoryCodeField, gbc);

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
                "Create Exprenses Sub Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String category = (String) categoryComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            String note = noteArea.getText().trim();
            int selectedIndex = categoryComboBox.getSelectedIndex();

            if (name.isEmpty() || category.equals("Select a category")) {
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
                UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
                JOptionPane.showMessageDialog(null,
                        "Please enter a Name, Subcategory Code and select a valid Category.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
                UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));

                ExpenseSubcategoryService expenseSubcategoryService = new ExpenseSubcategoryService();

                int categoryId = categoryMap.get(selectedIndex);

                AddExpenseSubcategoryRequestDto dto = new AddExpenseSubcategoryRequestDto(
                        name,
                        status.equals("Active") ? ExpenseSubcategoryStatus.ACTIVE : ExpenseSubcategoryStatus.INACTIVE,
                        note,
                        categoryId
                );

                // save subcategory to the database
                expenseSubcategoryService.add(dto);

                JOptionPane.showMessageDialog(null,
                        "Sub Category Created Successfully!\nCategory: " + category
                                + "\nName: " + name + "\nStatus: " + status + "\nNote: " + note,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                loadSubcategories();
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

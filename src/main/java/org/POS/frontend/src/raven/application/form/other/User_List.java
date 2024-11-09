
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.person.*;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class User_List extends javax.swing.JPanel {

    public User_List() {
        initComponents();
        Object[][] supplierSampleData = {
//                {1, "supplier1.jpg", "S001", "Syke Raphael Suarez", "123-456-7890", "syke.raphael@example.com", "Best Supplies Co.", "Active", "Action"},
//                {2, "supplier2.jpg", "S002", "Cindy Castanares", "987-654-3210", "cindy.castanares@example.com", "Global Tech", "Active", "Action"},
//                {3, "supplier3.jpg", "S003", "Debbie Castanares", "555-123-4567", "debbie.castanares@example.com", "Johnson Enterprises", "Active", "Action"}
        };

        // Create a DefaultTableModel and set it to the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setModel(model);

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {


            }


            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                // Confirm before deleting
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this Product?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int supplierId = (Integer) model.getValueAt(row, 2);

                    PersonService personService = new PersonService();
                    personService.delete(supplierId);

                    JOptionPane.showMessageDialog(null, "Supplier Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadSuppliers();
                }
            }

            @Override

            public void onView(int row) {

            }


        };
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadSuppliers();
    }

    private void loadSuppliers() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        PersonService personService = new PersonService();
        var people = personService.getAllValidPeopleByType(PersonType.SUPPLIER);
        for (int i = 0; i < people.size(); i++) {
            model.addRow(new Object[]{i + 1, getMediaTypeFromBase64(people.get(i).image()), people.get(i).id(), people.get(i).name(), people.get(i).taxRegistration(), people.get(i).contactNumber(), people.get(i).email(), people.get(i).companyName(), people.get(i).status().toString()});
        }
    }

    private String getMediaTypeFromBase64(String base64Data) {
        String dataUri = "data:image/jpeg;base64," + base64Data;

        if (base64Data == null) {
            return "No image";
        }

        if (!base64Data.startsWith("data:")) {
            return "image/jpeg";
        }

        int commaIndex = dataUri.indexOf(',');
        if (commaIndex == -1) {
            return "No image";
        }

        // Extract the media type
        String mediaTypeSection = dataUri.substring(5, commaIndex); // Skip "data:"
        int semicolonIndex = mediaTypeSection.indexOf(';');

        if (semicolonIndex != -1) {
            return mediaTypeSection.substring(0, semicolonIndex); // Return the media type only
        } else {
            return mediaTypeSection; // In case there's no `;`, return the full mediaTypeSection
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("User Management");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Users");

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

            },
            new String [] {
                "#", "User ID", "Name", "Role", "Username", "Password", "Email", "Contact Number", "Status", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(7).setResizable(false);
            table.getColumnModel().getColumn(8).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1316, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
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
                        .addGap(0, 1151, Short.MAX_VALUE)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    Font labelFont = new Font("Arial", Font.BOLD, 16);  // Larger font for labels
    Dimension fieldSize = new Dimension(250, 30);  // Uniform field size

    // Row 1: User ID (Uneditable)
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel userIdLabel = new JLabel("User ID:");
    userIdLabel.setFont(labelFont);
    panel.add(userIdLabel, gbc);

    gbc.gridx = 1;
    JTextField userIdField = new JTextField(20);
    userIdField.setPreferredSize(fieldSize);
    userIdField.setEditable(false);  // Make User ID uneditable
    panel.add(userIdField, gbc);

    // Row 2: Name
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(labelFont);
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    JTextField nameField = new JTextField(20);
    nameField.setPreferredSize(fieldSize);
    panel.add(nameField, gbc);

    // Row 3: Role (ComboBox)
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel roleLabel = new JLabel("Role:");
    roleLabel.setFont(labelFont);
    panel.add(roleLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Cashier", "Admin", "Manager"});
    roleCombo.setPreferredSize(fieldSize);
    panel.add(roleCombo, gbc);

    // Row 4: Username
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setFont(labelFont);
    panel.add(usernameLabel, gbc);

    gbc.gridx = 1;
    JTextField usernameField = new JTextField(20);
    usernameField.setPreferredSize(fieldSize);
    panel.add(usernameField, gbc);

    // Row 5: Password (Password Field)
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(labelFont);
    panel.add(passwordLabel, gbc);

    gbc.gridx = 1;
    JPasswordField passwordField = new JPasswordField(20);
    passwordField.setPreferredSize(fieldSize);
    panel.add(passwordField, gbc);

    // Row 6: Email
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(labelFont);
    panel.add(emailLabel, gbc);

    gbc.gridx = 1;
    JTextField emailField = new JTextField(20);
    emailField.setPreferredSize(fieldSize);
    panel.add(emailField, gbc);

    // Row 7: Contact Number
    gbc.gridx = 0;
    gbc.gridy = 6;
    JLabel contactLabel = new JLabel("Contact Number:");
    contactLabel.setFont(labelFont);
    panel.add(contactLabel, gbc);

    gbc.gridx = 1;
    JTextField contactNumberField = new JTextField(20);
    contactNumberField.setPreferredSize(fieldSize);
    panel.add(contactNumberField, gbc);

    // Row 8: Status (ComboBox)
    gbc.gridx = 0;
    gbc.gridy = 7;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(labelFont);
    panel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusCombo.setPreferredSize(fieldSize);
    panel.add(statusCombo, gbc);

    // Display the panel
    int result = JOptionPane.showConfirmDialog(null, panel, "User Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        // Retrieve values and perform action
        String userId = userIdField.getText();
        String name = nameField.getText();
        String role = (String) roleCombo.getSelectedItem();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String contactNumber = contactNumberField.getText();
        String status = (String) statusCombo.getSelectedItem();

        // Perform your submission logic here
        // Example DTO and Service call
        // UserDto dto = new UserDto(userId, name, role, username, password, email, contactNumber, status);
        // userService.addUser(dto);

        JOptionPane.showMessageDialog(null, "User information saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        // Refresh or perform additional actions if needed
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

    private void filterTableByDateRange(LocalDate fromDate, LocalDate toDate) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                // Assuming the date is in the 3rd column (index 2), change as per your table
                String dateStr = (String) entry.getValue(2);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate rowDate = LocalDate.parse(dateStr, formatter);

                    // Return true if the date is within the selected range
                    return !rowDate.isBefore(fromDate) && !rowDate.isAfter(toDate);
                } catch (Exception e) {
                    // Skip rows with invalid dates
                    return false;
                }
            }
        });
    }
}

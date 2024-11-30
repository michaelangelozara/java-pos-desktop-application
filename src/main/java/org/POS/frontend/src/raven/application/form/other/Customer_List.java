
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.person.*;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class Customer_List extends JPanel {

    private Timer timer;

    public Customer_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;

                Font labelFont = new Font("Arial", Font.BOLD, 16);  // Larger font for labels
                Dimension fieldSize = new Dimension(250, 30);  // Uniform field size

                // Get selected row data (assuming your model's getValueAt method)
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int clientId = (Integer) model.getValueAt(row, 2);
                String name = (String) model.getValueAt(row, 3);
                String taxRegistrationNumber = (String) model.getValueAt(row, 4);
                String contactNumber = (String) model.getValueAt(row, 5);
                String email = (String) model.getValueAt(row, 6);
                String companyName = (String) model.getValueAt(row, 7);
                // Update as needed based on your model
                String address = ""; // Add logic to get address if it's in the model
                PersonStatus status = (PersonStatus) model.getValueAt(row, 8); // Update as needed based on your model

                // 1st Row: Name, Email, Contact Number
                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel nameLabel = new JLabel("Name *:");
                nameLabel.setFont(labelFont);
                panel.add(nameLabel, gbc);

                gbc.gridx = 1;
                JTextField nameField = new JTextField(name);
                nameField.setPreferredSize(fieldSize);
                panel.add(nameField, gbc);

                gbc.gridx = 2;
                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setFont(labelFont);
                panel.add(emailLabel, gbc);

                gbc.gridx = 3;
                JTextField emailField = new JTextField(email);
                emailField.setPreferredSize(fieldSize);
                panel.add(emailField, gbc);

                gbc.gridx = 4;
                JLabel contactNumberLabel = new JLabel("Contact Number:");
                contactNumberLabel.setFont(labelFont);
                panel.add(contactNumberLabel, gbc);

                gbc.gridx = 5;
                JTextField contactNumberField = new JTextField(contactNumber);
                contactNumberField.setPreferredSize(fieldSize);
                panel.add(contactNumberField, gbc);

                // 2nd Row: Company Name, Tax Registration Number, Address
                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel companyNameLabel = new JLabel("Company Name:");
                companyNameLabel.setFont(labelFont);
                panel.add(companyNameLabel, gbc);

                gbc.gridx = 1;
                JTextField companyNameField = new JTextField(companyName);
                companyNameField.setPreferredSize(fieldSize);
                panel.add(companyNameField, gbc);

                gbc.gridx = 2;
                JLabel taxLabel = new JLabel("Tax Registration Number:");
                taxLabel.setFont(labelFont);
                panel.add(taxLabel, gbc);

                gbc.gridx = 3;
                JTextField taxField = new JTextField(taxRegistrationNumber);
                taxField.setPreferredSize(fieldSize);
                panel.add(taxField, gbc);

                PersonService personService = new PersonService();
                var client = personService.getValidPersonById(clientId);

                gbc.gridx = 4;
                JLabel addressLabel = new JLabel("Address:");
                addressLabel.setFont(labelFont);
                panel.add(addressLabel, gbc);

                gbc.gridx = 5;
                JTextField addressField = new JTextField(client.address());  // Retrieve address as needed
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
                Base64Converter base64Converter = new Base64Converter();
                // File Chooser for Image Upload
                imageButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select Image");
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        // Handle file upload logic here
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
                statusCombo.setSelectedItem(status.equals(PersonStatus.ACTIVE) ? "Active" : "Inactive"); // Set the current status
                statusCombo.setPreferredSize(fieldSize);
                panel.add(statusCombo, gbc);

                // Display the panel
                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Update as needed
                    // Handle the image upload if needed

                    String updatedName = nameField.getText();
                    String updatedEmail = emailField.getText();
                    String updatedContactNumber = contactNumberField.getText();
                    String updatedCompanyName = companyNameField.getText();
                    String updatedTaxRegistration = taxField.getText();
                    String updatedAddress = addressField.getText();
                    String updatedImage = base64Converter.getBase64();
                    String updatedPersonStatus = (String) statusCombo.getSelectedItem();

                    assert updatedPersonStatus != null;
                    UpdatePersonRequestDto dto = new UpdatePersonRequestDto(
                            clientId,
                            updatedName,
                            updatedEmail,
                            updatedContactNumber,
                            updatedCompanyName,
                            updatedTaxRegistration,
                            PersonType.CLIENT,
                            updatedAddress,
                            updatedImage != null ? updatedImage : client.image(),
                            updatedPersonStatus.equals("Active") ? PersonStatus.ACTIVE : PersonStatus.INACTIVE
                    );
                    personService.update(dto);

                    JOptionPane.showMessageDialog(null, "Product Updated Successfully",
                            "Updated", JOptionPane.INFORMATION_MESSAGE);

                    loadClients();
                }

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
                    int clientId = (Integer) model.getValueAt(row, 2);

                    PersonService personService = new PersonService();
                    personService.delete(clientId);

                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadClients();
                }
            }

            @Override

            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int clientId = (Integer) model.getValueAt(row, 2);

                PersonService personService = new PersonService();
                SwingWorker<PersonResponseDto, Void> worker = new SwingWorker<>() {
                    @Override
                    protected PersonResponseDto doInBackground() throws Exception {
                        var client = personService.getValidPersonById(clientId);
                        return client;
                    }

                    @Override
                    protected void done() {
                        try {
                            var client = get();
                            Application.showForm(new Supplier_Details(client));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                worker.execute();
            }
        };
        makeCellCenter(table);
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        loadClients();
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
    }

    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
    }

    private void loadClients() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // clear existing table
        model.setRowCount(0);

        PersonService personService = new PersonService();
        var people = personService.getAllValidPeopleByType(PersonType.CLIENT);
        for (int i = 0; i < people.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    getMediaTypeFromBase64(people.get(i).image()),
                    people.get(i).id(),
                    people.get(i).name(),
                    people.get(i).taxRegistration(),
                    people.get(i).contactNumber(),
                    people.get(i).email(),
                    people.get(i).companyName(),
                    people.get(i).status()
            });
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

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jButton1 = new JButton();
        jTextField1 = new JTextField();
        jScrollPane1 = new JScrollPane();
        table = new JTable();

        jLabel1.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Customers");

        jPanel2.setBorder(BorderFactory.createEtchedBorder());

        jLabel2.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Customers");

        jButton1.setBackground(new Color(235, 161, 132));
        jButton1.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("");

        table.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "#", "Image", "Client ID", "Name", "Tax Registration #", "Contact Number	", "Email", "Company Name", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(7).setResizable(false);
            table.getColumnModel().getColumn(8).setResizable(false);
        }

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1316, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 1235, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            loadClients();
            return;
        }
        PersonService personService = new PersonService();
        var people = personService.getAllValidPersonByName(name, PersonType.CLIENT);

        for (int i = 0; i < people.size(); i++) {
            model.addRow(new Object[]{
                    i + 1,
                    getMediaTypeFromBase64(people.get(i).image()),
                    people.get(i).id(),
                    people.get(i).name(),
                    people.get(i).taxRegistration(),
                    people.get(i).contactNumber(),
                    people.get(i).email(),
                    people.get(i).companyName(),
                    people.get(i).status()
            });
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground(){
                    try {
                        personService.add(dto);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        boolean result = get();
                        if(result){
                            JOptionPane.showMessageDialog(null, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        }else{
                            JOptionPane.showMessageDialog(null, "Unable to Create a New Client!", "Error", JOptionPane.INFORMATION_MESSAGE);

                        }
                        loadClients();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            worker.execute();
        }
    }

    // Method to create createdAt pickers with the current createdAt
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTextField jTextField1;
    private JTable table;
    // End of variables declaration//GEN-END:variables

    private void filterTableByDateRange(LocalDate fromDate, LocalDate toDate) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                // Assuming the createdAt is in the 3rd column (index 2), change as per your table
                String dateStr = (String) entry.getValue(2);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate rowDate = LocalDate.parse(dateStr, formatter);

                    // Return true if the createdAt is within the selected range
                    return !rowDate.isBefore(fromDate) && !rowDate.isAfter(toDate);
                } catch (Exception e) {
                    // Skip rows with invalid dates
                    return false;
                }
            }
        });
    }
}

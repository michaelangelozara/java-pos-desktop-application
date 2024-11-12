package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.PersonService;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductTaxType;
import org.POS.backend.purchase.AddPurchaseRequestDto;
import org.POS.backend.purchase.PurchaseService;
import org.POS.backend.purchase.PurchaseStatus;
import org.POS.backend.purchase.UpdatePurchaseRequestDto;
import org.POS.backend.purchased_item.*;
import org.POS.frontend.src.raven.application.Application;
import org.POS.frontend.src.raven.cell.TableActionCellEditor;
import org.POS.frontend.src.raven.cell.TableActionCellRender;
import org.POS.frontend.src.raven.cell.TableActionEvent;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Purchases_List extends javax.swing.JPanel {

    private JLabel totalTaxProductLabel;

    private JLabel subtotalLabel;

    private JTextField netTotalField;

    private JTextField transportCostField;

    private JTextField discountField;

    private JTextField totalPaidField;

    private JTextField totalTaxField;

    public Purchases_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int purchaseId = (Integer) model.getValueAt(row, 1);

                PurchaseService purchaseService = new PurchaseService();
                var purchase = purchaseService.getValidPurchaseByPurchaseId(purchaseId);

                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;

                // Define font for larger bold labels
                Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

                // Supplier and Select Products - same row
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                JLabel supplierLabel = new JLabel("Supplier:");
                supplierLabel.setFont(labelFont);  // Set label to bold and large
                panel.add(supplierLabel, gbc);

//        new String[]{"Select Supplier", "Christian James Torres", "Michael Angelo Zara"}
                gbc.gridx = 1;
                JComboBox<String> supplierCombo = new JComboBox<>();
                supplierCombo.addItem(purchase.supplier().name());
                supplierCombo.setSelectedItem(purchase.supplier().name());
                supplierCombo.setEnabled(false);
                panel.add(supplierCombo, gbc);

                gbc.gridx = 2;
                JLabel productsLabel = new JLabel("Select Products:");
                productsLabel.setFont(labelFont);  // Set label to bold and large
                panel.add(productsLabel, gbc);

                gbc.gridx = 3;
                JComboBox<String> productsCombo = new JComboBox<>();
                panel.add(productsCombo, gbc);

                ProductService productService = new ProductService();
                var products = productService.getAllValidProducts();
                Map<Integer, Integer> productMap = new HashMap<>();
                // Populate ComboBox with product names
                productsCombo.addItem("Select Product");

                for (int i = 0; i < products.size(); i++) {
                    productsCombo.addItem(products.get(i).name());
                    productMap.put(i + 1, products.get(i).id());
                }

                // Table setup below Supplier and Select Products
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 8;
                gbc.fill = GridBagConstraints.BOTH;

                // Define a custom table model to make specific columns non-editable
                DefaultTableModel tableModel = new DefaultTableModel(
                        new Object[]{"#", "ID", "Code", "Name", "Quantity", "Purchase Price", "Selling Price", "Tax Value", "Tax Type", "Subtotal", "Action"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // Get the value from the "Tax Type" column (column 8)
                        Object taxTypeValue = getValueAt(row, 8); // Assuming Tax Type is at index 8

                        // Check if taxTypeValue is a String and has the expected value
                        if (taxTypeValue instanceof String) {
                            String taxType = (String) taxTypeValue;
                            if (taxType.equals("EXCLUSIVE")) {
                                // Make "Quantity" (index 4) and "Purchase Price" (index 5) editable
                                return column == 4 || column == 5;
                            } else if (taxType.equals("INCLUSIVE")) {
                                // Make "Quantity" (index 4) and "Selling Price" (index 6) editable
                                return column == 4 || column == 6;
                            }
                        }

                        // Default case: no cells are editable if tax type is neither "exclusive" nor "inclusive"
                        return false;
                    }
                };

                for(int i = 0; i < purchase.purchaseProducts().size(); i++){
                    tableModel.addRow(new Object[]{
                            i+1,
                            purchase.purchaseProducts().get(i).id(),
                            purchase.purchaseProducts().get(i).product().code(),
                            purchase.purchaseProducts().get(i).product().name(),
                            purchase.purchaseProducts().get(i).quantity(),
                            purchase.purchaseProducts().get(i).purchasePrice(),
                            purchase.purchaseProducts().get(i).sellingPrice(),
                            purchase.purchaseProducts().get(i).taxValue(),
                            purchase.purchaseProducts().get(i).product().taxType().name(),
                            purchase.purchaseProducts().get(i).subtotal(),
                            "Remove"
                    });
                }

                JTable productsTable = new JTable(tableModel);
                productsTable.setFillsViewportHeight(true);

                // Center align all table cell content
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < productsTable.getColumnCount(); i++) {
                    productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                // Highlight editable cells
                productsTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField())); // Quantity as JTextField
                productsTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField())); // Purchase Price as JTextField
                productsTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField())); // Selling Price as JTextField
                productsTable.getColumnModel().getColumn(4).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Quantity
                productsTable.getColumnModel().getColumn(5).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Purchase Price
                productsTable.getColumnModel().getColumn(6).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Selling Price

                productsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());

                // Add JScrollPane around the table
                JScrollPane scrollPane = new JScrollPane(productsTable);
                scrollPane.setPreferredSize(new Dimension(800, 150));
                panel.add(scrollPane, gbc);

                tableModel.addTableModelListener(e -> {
                    int column = e.getColumn();

                    if (column == 4 || column == 5 || column == 6) { // Check if it's the "Quantity" or "Purchase Price" column
                        List<PurchaseListedProduct> insertedRows = getAllRows(tableModel);

                        // this is for specific selected column or cell
//                Object newValue = tableModel.getValueAt(row, column);
                        tableModel.setRowCount(0);

                        List<PurchaseListedProduct> updatedRows = new ArrayList<>();

                        for (int i = 0; i < insertedRows.size(); i++) {
                            if ((row + 1) == insertedRows.get(i).getNumber()) {
                                PurchaseListedProduct editRow = insertedRows.get(i);
                                if (editRow.getTaxType().equals("EXCLUSIVE")) {
                                    BigDecimal updatedSubtotal = BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                                    editRow.setSubtotal(updatedSubtotal);
                                    editRow.setTaxValue(updatedSubtotal.subtract((BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getPurchasePrice()))));
                                    editRow.setSellingPrice(editRow.getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                                } else {
                                    editRow.setTaxValue(
                                            editRow.getSellingPrice()
                                                    .multiply(BigDecimal.valueOf(0.12))
                                                    .divide(BigDecimal.valueOf(1.12), 2, RoundingMode.HALF_UP) // Specify scale and rounding mode here
                                    );

                                    editRow.setPurchasePrice(editRow.getSellingPrice().subtract(editRow.getTaxValue()));
                                    editRow.setSubtotal(BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getSellingPrice()));
                                }
                                updatedRows.add(editRow);
                                continue;
                            }
                            updatedRows.add(insertedRows.get(i));
                        }

                        for (int i = 0; i < updatedRows.size(); i++) {
                            BigDecimal updatedSubtotal = BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                            BigDecimal taxValueForInclusive = (updatedRows.get(i).getSellingPrice().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));
                            tableModel.addRow(new Object[]{
                                    tableModel.getRowCount() + 1, // Row number
                                    updatedRows.get(i).getId(),
                                    updatedRows.get(i).getCode(),
                                    updatedRows.get(i).getName(),
                                    String.valueOf(updatedRows.get(i).getQuantity()), // Ensuring stock is a String
                                    updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedRows.get(i).getPurchasePrice().setScale(2, RoundingMode.HALF_UP) : updatedRows.get(i).getSellingPrice().subtract(taxValueForInclusive).setScale(2, RoundingMode.HALF_UP),
                                    updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedRows.get(i).getPurchasePrice().multiply(BigDecimal.valueOf(1.12)).setScale(2, RoundingMode.HALF_UP) : updatedRows.get(i).getSellingPrice().setScale(2, RoundingMode.HALF_UP),
                                    updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedSubtotal.subtract((BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getPurchasePrice()))).setScale(2, RoundingMode.HALF_UP) : taxValueForInclusive.setScale(2, RoundingMode.HALF_UP),
                                    updatedRows.get(i).getTaxType(), // Assuming taxType() returns an enum, use name() to get String
                                    updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedSubtotal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getSellingPrice()).setScale(2, RoundingMode.HALF_UP), // Subtotal calculation
                                    "Remove"
                            });
                        }

                        // update the Subtotal and total tax value
                        BigDecimal totalTaxSummation = BigDecimal.ZERO;
                        BigDecimal SubtotalSummation = BigDecimal.ZERO;
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                            BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                            totalTaxSummation = totalTaxSummation.add(totalTax);
                            SubtotalSummation = SubtotalSummation.add(subTotal);
                        }

                        // update the computed total tax and computed subtotal
                        computeSubtotal(SubtotalSummation);
                        computeSubtotalTax(totalTaxSummation);
                        computeNetTotal();
                    }
                });

                // Add ActionListener to ComboBox
                productsCombo.addActionListener(e -> {

                    String selectedProduct = (String) productsCombo.getSelectedItem();
                    int productIndex = productsCombo.getSelectedIndex();
                    int productId = productMap.get(productIndex);

                    String productName = (String) productsCombo.getSelectedItem();

                    List<PurchaseListedProduct> rows = getAllRows(tableModel);
                    for (int i = 0; i < rows.size(); i++) {
                        // check if that product is already added
                        if (productName.equals(rows.get(i).getName())) {
                            JOptionPane.showMessageDialog(null, "This product is already added.");
                            return;
                        }
                    }

                    if (productMap.get(productIndex) == null) return;

                    assert selectedProduct != null;
                    if (!selectedProduct.equals("Select Product")) {
                        BigDecimal totalTaxSummation = BigDecimal.ZERO;
                        BigDecimal SubtotalSummation = BigDecimal.ZERO;


                        for (ProductResponseDto product : products) {

                            if (product.id() == productId) {
                                BigDecimal updatedSubtotal = BigDecimal.valueOf(product.stock()).multiply(product.purchasePrice().multiply(BigDecimal.valueOf(1.12)));

                                BigDecimal taxValueForInclusive = (product.sellingPrice().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));

                                tableModel.addRow(new Object[]{
                                        tableModel.getRowCount() + 1, // Row number
                                        null,
                                        product.code(),
                                        product.name(),
                                        String.valueOf(product.stock()), // Ensuring stock is a String
                                        product.taxType().equals(ProductTaxType.EXCLUSIVE) ? product.purchasePrice().setScale(2, RoundingMode.HALF_UP) : product.sellingPrice().subtract(taxValueForInclusive).setScale(2, RoundingMode.HALF_UP),
                                        product.taxType().equals(ProductTaxType.EXCLUSIVE) ? product.purchasePrice().multiply(BigDecimal.valueOf(1.12)).setScale(2, RoundingMode.HALF_UP) : product.sellingPrice().setScale(2, RoundingMode.HALF_UP),
                                        product.taxType().equals(ProductTaxType.EXCLUSIVE) ? updatedSubtotal.subtract((BigDecimal.valueOf(product.stock()).multiply(product.purchasePrice()))).setScale(2, RoundingMode.HALF_UP) : taxValueForInclusive.setScale(2, RoundingMode.HALF_UP),
                                        product.taxType().name(), // Assuming taxType() returns an enum, use name() to get String
                                        product.taxType().equals(ProductTaxType.EXCLUSIVE) ? updatedSubtotal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.valueOf(product.stock()).multiply(product.sellingPrice()).setScale(2, RoundingMode.HALF_UP), // Subtotal calculation
                                        "Remove"
                                });
                                break;
                            }
                        }

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                            BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                            totalTaxSummation = totalTaxSummation.add(totalTax);
                            SubtotalSummation = SubtotalSummation.add(subTotal);
                        }

                        // update the computed total tax and computed subtotal
                        computeSubtotal(SubtotalSummation);
                        computeSubtotalTax(totalTaxSummation);
                        computeNetTotal();

                    }
                });

                // Add MouseListener to handle "Remove" button clicks in the table
                productsTable.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int column = productsTable.columnAtPoint(e.getPoint());
                        int row = productsTable.rowAtPoint(e.getPoint());

                        if (column == 10) { // "Action" column index for "Remove" button
                            PurchaseItemService purchaseItemService = new PurchaseItemService();
                            Integer purchaseProductToBeRemoved = (Integer) productsTable.getValueAt(row, 1);
                            if(purchaseProductToBeRemoved != null){
                                purchaseItemService.deletePurchaseProductByPurchaseProductId(purchaseProductToBeRemoved);

                            }
                            tableModel.removeRow(row);

                            BigDecimal totalTaxSummation = BigDecimal.ZERO;
                            BigDecimal SubtotalSummation = BigDecimal.ZERO;

                            for (int i = 0; i < tableModel.getRowCount(); i++) {
                                BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                                BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                                totalTaxSummation = totalTaxSummation.add(totalTax);
                                SubtotalSummation = SubtotalSummation.add(subTotal);
                            }

                            // update the computed total tax and computed subtotal
                            computeSubtotal(SubtotalSummation);
                            computeSubtotalTax(totalTaxSummation);
                            computeNetTotal();
                        }
                    }
                });

                // Table setup below Supplier and Select Products
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 8;
                gbc.fill = GridBagConstraints.BOTH;

                // Increment row for the rest of the fields
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JPanel summaryPanel = new JPanel(new GridBagLayout());
                summaryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                summaryPanel.setBackground(new Color(245, 245, 245));

                GridBagConstraints gbcSummary = new GridBagConstraints();
                gbcSummary.insets = new Insets(5, 10, 5, 10);

                Font summaryFont = new Font("Arial", Font.BOLD, 22); // Larger font size for better visibility

                gbcSummary.gridx = 0;
                gbcSummary.gridy = 0;
                gbcSummary.anchor = GridBagConstraints.EAST;

                gbcSummary.gridx = 1;
                JLabel taxLabel = new JLabel("Tax: ₱");
                taxLabel.setFont(summaryFont);
                taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                summaryPanel.add(taxLabel, gbcSummary);

                gbcSummary.gridx = 2;
                totalTaxProductLabel = new JLabel();
                totalTaxProductLabel.setFont(summaryFont);
                totalTaxProductLabel.setHorizontalAlignment(SwingConstants.LEFT);
                totalTaxProductLabel.setText(String.valueOf(purchase.subtotalTax()));
                summaryPanel.add(totalTaxProductLabel, gbcSummary);

                gbcSummary.gridx = 3;
                JLabel totalLabel = new JLabel("Subtotal: ₱");
                totalLabel.setFont(summaryFont);
                totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                summaryPanel.add(totalLabel, gbcSummary);

                gbcSummary.gridx = 4;
                subtotalLabel = new JLabel();
                subtotalLabel.setFont(summaryFont);
                subtotalLabel.setHorizontalAlignment(SwingConstants.LEFT);
                subtotalLabel.setText(String.valueOf(purchase.netSubtotal()));
                summaryPanel.add(subtotalLabel, gbcSummary);

                gbc.gridx = 0;
                gbc.gridy = 2; // Position summary panel right below the table
                gbc.gridwidth = 8;
                panel.add(summaryPanel, gbc);

                // PO Reference, Payment Terms, Purchase Tax, Total Tax - same row
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel poReferenceLabel = new JLabel("PO Reference:");
                poReferenceLabel.setFont(labelFont);
                panel.add(poReferenceLabel, gbc);

                gbc.gridx = 1;
                JTextField poReferenceField = new JTextField(10);
                poReferenceField.setText(purchase.purchaseOrderReference());
                panel.add(poReferenceField, gbc);

                gbc.gridx = 2;
                JLabel paymentTermsLabel = new JLabel("Payment Terms:");
                paymentTermsLabel.setFont(labelFont);
                panel.add(paymentTermsLabel, gbc);

                gbc.gridx = 3;
                JTextField paymentTermsField = new JTextField(10);
                paymentTermsField.setText(purchase.paymentTerm());
                panel.add(paymentTermsField, gbc);

                gbc.gridx = 4;
                JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
                purchaseTaxLabel.setFont(labelFont);
                panel.add(purchaseTaxLabel, gbc);

                gbc.gridx = 5;
                JComboBox<String> purchaseTaxCombo = new JComboBox<>(new String[]{"VAT@12%"});
                panel.add(purchaseTaxCombo, gbc);
                purchaseTaxCombo.setSelectedItem("VAT@12%");
                purchaseTaxCombo.setEnabled(false);

                gbc.gridx = 6;
                JLabel totalTaxLabel = new JLabel("Total Tax:");
                totalTaxLabel.setFont(labelFont);
                panel.add(totalTaxLabel, gbc);

                gbc.gridx = 7;
                totalTaxField = new JTextField(10);
                totalTaxField.setText(String.valueOf(purchase.totalTax()));
                panel.add(totalTaxField, gbc);
                totalTaxField.setEnabled(false);

                // New row for Discount and Transport Cost
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel discountLabel = new JLabel("Discount:");
                discountLabel.setFont(labelFont);
                panel.add(discountLabel, gbc);

                gbc.gridx = 1;
                discountField = new JTextField(10);
                discountField.setText(String.valueOf(purchase.discount()));
                panel.add(discountField, gbc);

                discountField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        for (char c : discountField.getText().toCharArray()) {
                            if (Character.isLetter(c)) {
                                JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                                discountField.setText("0");
                                break;
                            }
                        }
                        computeNetTotal();
                    }
                });


                gbc.gridx = 2;
                JLabel transportCostLabel = new JLabel("Transport Cost:");
                transportCostLabel.setFont(labelFont);
                panel.add(transportCostLabel, gbc);

                gbc.gridx = 3;
                transportCostField = new JTextField(10);
                transportCostField.setText(String.valueOf(purchase.transport()));
                panel.add(transportCostField, gbc);


                transportCostField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                        for (char c : transportCostField.getText().toCharArray()) {
                            if (Character.isLetter(c)) {
                                JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                                transportCostField.setText("0");
                                break;
                            }
                        }

                        computeNetTotal();
                    }
                });

                gbc.gridy = 5; // Position it right below the Discount and Transport Cost row
                gbc.gridx = 0;

                JLabel accountLabel = new JLabel("Account:");
                accountLabel.setFont(labelFont);
                panel.add(accountLabel, gbc);

                gbc.gridx = 1;
                JTextField accountField = new JTextField();
                accountField.setText(purchase.account());
                panel.add(accountField, gbc);

                gbc.gridx = 2;
                JLabel chequeNoLabel = new JLabel("Cheque No:");
                chequeNoLabel.setFont(labelFont);
                panel.add(chequeNoLabel, gbc);

                gbc.gridx = 3;
                JTextField chequeNoField = new JTextField(10);
                chequeNoField.setText("This should be deleted");
                panel.add(chequeNoField, gbc);

                gbc.gridx = 4;
                JLabel receiptNoLabel = new JLabel("Receipt No:");
                receiptNoLabel.setFont(labelFont);
                panel.add(receiptNoLabel, gbc);

                gbc.gridx = 5;
                JTextField receiptNoField = new JTextField(10);
                receiptNoField.setText(purchase.code());
                receiptNoField.setEnabled(false);
                panel.add(receiptNoField, gbc);

                // Shift remaining rows down by one row
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.gridwidth = 2;
                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setFont(labelFont);
                panel.add(noteLabel, gbc);

                gbc.gridx = 1;
                gbc.gridwidth = 7;
                JTextArea noteArea = new JTextArea(3, 50);
                noteArea.setText(purchase.note());
                panel.add(new JScrollPane(noteArea), gbc);

                // Adjusting other elements to fit below the new Discount and Transport Cost row
                gbc.gridx = 0;
                gbc.gridy = 7;
                gbc.gridwidth = 1;
                JLabel purchaseDateLabel = new JLabel("Purchase Date:");
                purchaseDateLabel.setFont(labelFont);
                panel.add(purchaseDateLabel, gbc);

                gbc.gridx = 1;
                JDatePickerImpl purchaseDatePicker = createDatePicker(purchase.purchaseDate());
                panel.add(purchaseDatePicker, gbc);

                gbc.gridx = 2;
                JLabel poDateLabel = new JLabel("PO Date:");
                poDateLabel.setFont(labelFont);
                panel.add(poDateLabel, gbc);

                gbc.gridx = 3;
                JDatePickerImpl poDatePicker = createDatePicker(purchase.poDate());
                panel.add(poDatePicker, gbc);

                gbc.gridx = 4;
                JLabel statusLabel = new JLabel("Status:");
                statusLabel.setFont(labelFont);
                panel.add(statusLabel, gbc);

                gbc.gridx = 5;
                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
                statusCombo.setSelectedItem(purchase.status().equals(PurchaseStatus.ACTIVE) ? "Active" : "Inactive");
                panel.add(statusCombo, gbc);

                // Shift remaining elements down one row to make space for the new panel
                gbc.gridy++;

                // New Panel for Total Paid and Net Total
                JPanel totalPanel = new JPanel(new GridBagLayout());
                totalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "TOTAL AMOUNT", TitledBorder.LEFT, TitledBorder.TOP));
                GridBagConstraints totalGbc = new GridBagConstraints();
                totalGbc.insets = new Insets(5, 5, 5, 5);

                // Total Paid Label and Text Field
                totalGbc.gridx = 0;
                totalGbc.gridy = 0;
                totalGbc.gridwidth = 1;
                JLabel totalPaidLabel = new JLabel("Total Paid:");
                totalPaidLabel.setFont(labelFont);
                totalPanel.add(totalPaidLabel, totalGbc);

                totalGbc.gridx = 1;
                totalGbc.gridwidth = 2;
                totalPaidField = new JTextField(10); // Regular text field for Total Paid
                totalPaidField.setText(String.valueOf(purchase.totalPaid()));
                totalPanel.add(totalPaidField, totalGbc);

                totalPaidField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        for (char c : totalPaidField.getText().toCharArray()) {
                            if (Character.isLetter(c)) {
                                JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                                totalPaidField.setText("0");
                                break;
                            }
                        }

                        computeNetTotal();
                    }
                });

                // Add gap between Total Paid and Net Total
                totalGbc.gridx = 3;
                totalGbc.gridwidth = 1;
                totalPanel.add(Box.createHorizontalStrut(20), totalGbc); // Adds horizontal gap

                // Net Total Label and Display Label
                totalGbc.gridx = 4;
                JLabel netTotalLabel = new JLabel("Net Total:");
                netTotalLabel.setFont(labelFont);
                totalPanel.add(netTotalLabel, totalGbc);

                totalGbc.gridx = 5;
                netTotalField = new JTextField(); // Placeholder for Net Total display
                netTotalField.setFont(new Font("Arial", Font.BOLD, 24)); // Set to larger, bold font
                netTotalField.setEnabled(false);
                netTotalField.setText(String.valueOf(purchase.netTotal()));
                totalPanel.add(netTotalField, totalGbc);

                // Add the new Total Panel to the main panel
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.gridwidth = 8; // Span the entire row for alignment
                panel.add(totalPanel, gbc);


                // Show the panel in a dialog
                int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    List<PurchaseListedProduct> insertedRows = getAllRows(tableModel);

                    List<UpdatePurchaseItemRequestDto> purchaseProductRequestDtoSet = new ArrayList<>();

                    for (int i = 0; i < insertedRows.size(); i++) {
                        UpdatePurchaseItemRequestDto dto = new UpdatePurchaseItemRequestDto(
                                insertedRows.get(i).getId(),
                                insertedRows.get(i).getQuantity(),
                                insertedRows.get(i).getPurchasePrice(),
                                insertedRows.get(i).getSellingPrice(),
                                insertedRows.get(i).getTaxValue(),
                                insertedRows.get(i).getSubtotal(),
                                insertedRows.get(i).getCode()
                        );
                        purchaseProductRequestDtoSet.add(dto);
                    }

                    if (insertedRows.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You didn't enter any product from the list");
                        return;
                    }

                    if (receiptNoField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You didn't enter receipt number");
                        return;
                    }

                    if (chequeNoField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You didn't enter cheque number");
                        return;
                    }

                    String poReference = poReferenceField.getText();
                    String paymentTerms = paymentTermsField.getText();
                    int purchaseTax = 12;
                    BigDecimal subtotalTax = BigDecimal.valueOf(Double.parseDouble(totalTaxProductLabel.getText()));
                    BigDecimal totalTax = BigDecimal.valueOf(Double.parseDouble(totalTaxField.getText()));
                    BigDecimal netSubtotal = BigDecimal.valueOf(Double.parseDouble(subtotalLabel.getText()));
                    BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(discountField.getText())).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal transportCost = BigDecimal.valueOf(Double.parseDouble(transportCostField.getText())).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal totalPaid = BigDecimal.valueOf(Double.parseDouble(totalPaidField.getText()));
                    String account = accountField.getText();
                    String chequeNumber = chequeNoField.getText();
                    String receiptNumber = receiptNoField.getText();
                    String note = noteArea.getText();
                    String purchaseDate = purchaseDatePicker.getJFormattedTextField().getText();
                    String poDate = poDatePicker.getJFormattedTextField().getText();
                    String status = (String) statusCombo.getSelectedItem();

                    // dd/mm/yyyy
                    String[] splitPurchaseDate = purchaseDate.split("/");
                    String[] splitPoDate = poDate.split("/");

                    assert status != null;
                    UpdatePurchaseRequestDto dto = new UpdatePurchaseRequestDto(
                            purchaseId,
                            purchaseProductRequestDtoSet,
                            poReference,
                            paymentTerms,
                            purchaseTax,
                            totalTax,
                            subtotalTax,
                            netSubtotal,
                            discount,
                            transportCost,
                            account,
                            chequeNumber,
                            receiptNumber,
                            note,
                            LocalDate.of(Integer.parseInt(splitPurchaseDate[2]), Integer.parseInt(splitPurchaseDate[1]), Integer.parseInt(splitPurchaseDate[0])),
                            LocalDate.of(Integer.parseInt(splitPoDate[2]), Integer.parseInt(splitPoDate[1]), Integer.parseInt(splitPoDate[0])),
                            status.equals("Active") ? PurchaseStatus.ACTIVE : PurchaseStatus.INACTIVE,
                            totalPaid
                    );

                    purchaseService.update(dto);
                }
                loadPurchases();
            }

            // Method to create date pickers with the current date
            private JDatePickerImpl createDatePicker() {
                UtilDateModel model = new UtilDateModel();
                // Set the current date
                LocalDate currentDate = LocalDate.now();
                model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
                model.setSelected(true);  // Automatically selects the current date

                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                return new JDatePickerImpl(datePanel, new DateLabelFormatter());
            }

            private JDatePickerImpl createDatePicker(LocalDate localDate) {
                UtilDateModel model = new UtilDateModel();
                // Set the current date
                model.setDate(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
                model.setSelected(true);  // Automatically selects the current date

                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                return new JDatePickerImpl(datePanel, new DateLabelFormatter());
            }

            // Date label formatter (you may need to implement this class based on your needs)
            class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

                private String datePattern = "dd/MM/yyyy";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parseObject(text);
                }

                @Override
                public String valueToString(Object value) {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }
                    return "";
                }
            }

            // Custom ButtonEditor class for the "Remove" button functionality
            class ButtonEditor extends DefaultCellEditor {

                protected JButton button;
                private String label;
                private boolean isPushed;
                private JTable table;
                private DefaultTableModel tableModel;

                public ButtonEditor(JCheckBox checkBox, JTable table, DefaultTableModel model) {
                    super(checkBox);
                    this.table = table;
                    this.tableModel = model;
                    button = new JButton();
                    button.setOpaque(true);
                    button.addActionListener(e -> fireEditingStopped());
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    label = (value == null) ? "Remove" : value.toString();
                    button.setText(label);
                    isPushed = true;
                    return button;
                }

                @Override
                public Object getCellEditorValue() {
                    if (isPushed) {
                        // Remove the row when the button is clicked
                        int row = table.getSelectedRow();
                        if (row >= 0) {
                            tableModel.removeRow(row);
                        }
                    }
                    isPushed = false;
                    return label;
                }

                @Override
                public boolean stopCellEditing() {
                    isPushed = false;
                    return super.stopCellEditing();
                }

                @Override
                protected void fireEditingStopped() {
                    super.fireEditingStopped();
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
                    int purchaseId = (Integer) model.getValueAt(row, 1);
                    PurchaseService purchaseService = new PurchaseService();
                    purchaseService.delete(purchaseId);
                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully",
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadPurchases();
                }
            }

            @Override

            public void onView(int row) {
                Application.showForm(new Product_Details());
            }

        };

        table.getColumnModel().getColumn(12).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(12).setCellEditor(new TableActionCellEditor(event));
        loadPurchases();
    }

    private void loadPurchases() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        PurchaseService purchaseService = new PurchaseService();
        var purchases = purchaseService.getAllValidPurchases();
        for (int i = 0; i < purchases.size(); i++) {
            model.addRow(new Object[]{
                    String.valueOf(i + 1),
                    purchases.get(i).id(),
                    purchases.get(i).code(),
                    purchases.get(i).date(),
                    purchases.get(i).supplier().name(),
                    purchases.get(i).subtotal(),
                    purchases.get(i).transport(),
                    purchases.get(i).discount(),
                    purchases.get(i).netTotal(),
                    purchases.get(i).totalPaid(),
                    purchases.get(i).totalDue(),
                    purchases.get(i).status()
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
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Purchases List");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Purchases");

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
                new Object[][]{
                        {null, null, null, null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null, null, null, null}
                },
                new String[]{
                        "#", "ID", "Purchase #", "Date", "Supplier", "Subtotal", "Transport", "Discount", "Net Total", "Total Paid", "Total Due", "Status", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(9).setResizable(false);
            table.getColumnModel().getColumn(10).setResizable(false);
            table.getColumnModel().getColumn(11).setResizable(false);
        }

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setText("From - To");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1316, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(14, 14, 14))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                .addGap(0, 1201, Short.MAX_VALUE)))
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Define font for larger bold labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // Increase font size to 16

        // Supplier and Select Products - same row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(supplierLabel, gbc);

//        new String[]{"Select Supplier", "Christian James Torres", "Michael Angelo Zara"}
        gbc.gridx = 1;
        JComboBox<String> supplierCombo = new JComboBox<>();
        panel.add(supplierCombo, gbc);
        supplierCombo.addItem("Select Supplier");

        Map<Integer, Integer> supplierMap = new HashMap<>();
        PersonService personService = new PersonService();
        var suppliers = personService.getAllValidPeopleByType(PersonType.SUPPLIER);
        for (int i = 0; i < suppliers.size(); i++) {
            supplierCombo.addItem(suppliers.get(i).name() + suppliers.get(i).id());
            supplierMap.put(i + 1, suppliers.get(i).id());
        }

        gbc.gridx = 2;
        JLabel productsLabel = new JLabel("Select Products:");
        productsLabel.setFont(labelFont);  // Set label to bold and large
        panel.add(productsLabel, gbc);

        gbc.gridx = 3;
        JComboBox<String> productsCombo = new JComboBox<>();
        panel.add(productsCombo, gbc);

        ProductService productService = new ProductService();
        var products = productService.getAllValidProducts();
        Map<Integer, Integer> productMap = new HashMap<>();
        // Populate ComboBox with product names
        productsCombo.addItem("Select Product");

        for (int i = 0; i < products.size(); i++) {
            productsCombo.addItem(products.get(i).name());
            productMap.put(i + 1, products.get(i).id());
        }

        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Define a custom table model to make specific columns non-editable
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"#", "ID", "Code", "Name", "Quantity", "Purchase Price", "Selling Price", "Tax Value", "Tax Type", "Subtotal", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Get the value from the "Tax Type" column (column 8)
                Object taxTypeValue = getValueAt(row, 8); // Assuming Tax Type is at index 8

                // Check if taxTypeValue is a String and has the expected value
                if (taxTypeValue instanceof String) {
                    String taxType = (String) taxTypeValue;
                    if (taxType.equals("EXCLUSIVE")) {
                        // Make "Quantity" (index 4) and "Purchase Price" (index 5) editable
                        return column == 4 || column == 5;
                    } else if (taxType.equals("INCLUSIVE")) {
                        // Make "Quantity" (index 4) and "Selling Price" (index 6) editable
                        return column == 4 || column == 6;
                    }
                }

                // Default case: no cells are editable if tax type is neither "exclusive" nor "inclusive"
                return false;
            }
        };

        JTable productsTable = new JTable(tableModel);
        productsTable.setFillsViewportHeight(true);

        // Center align all table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Highlight editable cells
        productsTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField())); // Quantity as JTextField
        productsTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField())); // Purchase Price as JTextField
        productsTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField())); // Selling Price as JTextField
        productsTable.getColumnModel().getColumn(4).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Quantity
        productsTable.getColumnModel().getColumn(5).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Purchase Price
        productsTable.getColumnModel().getColumn(6).setCellRenderer(new EditableCellRenderer()); // Custom renderer for Selling Price

        productsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());

        // Add JScrollPane around the table
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        panel.add(scrollPane, gbc);

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 4 || column == 5 || column == 6) { // Check if it's the "Quantity", "Selling" or "Purchase Price" column
                List<PurchaseListedProduct> insertedRows = getAllRows(tableModel);

                // this is for specific selected column or cell
//                Object newValue = tableModel.getValueAt(row, column);
                tableModel.setRowCount(0);

                List<PurchaseListedProduct> updatedRows = new ArrayList<>();

                for (int i = 0; i < insertedRows.size(); i++) {
                    if ((row + 1) == insertedRows.get(i).getNumber()) {
                        PurchaseListedProduct editRow = insertedRows.get(i);
                        if (editRow.getTaxType().equals("EXCLUSIVE")) {
                            BigDecimal updatedSubtotal = BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                            editRow.setSubtotal(updatedSubtotal);
                            editRow.setTaxValue(updatedSubtotal.subtract((BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getPurchasePrice()))));
                            editRow.setSellingPrice(editRow.getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                        } else {
                            editRow.setTaxValue(
                                    editRow.getSellingPrice()
                                            .multiply(BigDecimal.valueOf(0.12))
                                            .divide(BigDecimal.valueOf(1.12), 2, RoundingMode.HALF_UP) // Specify scale and rounding mode here
                            );

                            editRow.setPurchasePrice(editRow.getSellingPrice().subtract(editRow.getTaxValue()));
                            editRow.setSubtotal(BigDecimal.valueOf(editRow.getQuantity()).multiply(editRow.getSellingPrice()));
                        }
                        updatedRows.add(editRow);
                        continue;
                    }
                    updatedRows.add(insertedRows.get(i));
                }

                for (int i = 0; i < updatedRows.size(); i++) {
                    BigDecimal updatedSubtotal = BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getPurchasePrice().multiply(BigDecimal.valueOf(1.12)));
                    BigDecimal taxValueForInclusive = (updatedRows.get(i).getSellingPrice().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));
                    tableModel.addRow(new Object[]{
                            tableModel.getRowCount() + 1, // Row number
                            updatedRows.get(i).getId(),
                            updatedRows.get(i).getCode(),
                            updatedRows.get(i).getName(),
                            String.valueOf(updatedRows.get(i).getQuantity()), // Ensuring stock is a String
                            updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedRows.get(i).getPurchasePrice().setScale(2, RoundingMode.HALF_UP) : updatedRows.get(i).getSellingPrice().subtract(taxValueForInclusive).setScale(2, RoundingMode.HALF_UP),
                            updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedRows.get(i).getPurchasePrice().multiply(BigDecimal.valueOf(1.12)).setScale(2, RoundingMode.HALF_UP) : updatedRows.get(i).getSellingPrice().setScale(2, RoundingMode.HALF_UP),
                            updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedSubtotal.subtract((BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getPurchasePrice()))).setScale(2, RoundingMode.HALF_UP) : taxValueForInclusive.setScale(2, RoundingMode.HALF_UP),
                            updatedRows.get(i).getTaxType(), // Assuming taxType() returns an enum, use name() to get String
                            updatedRows.get(i).getTaxType().equals("EXCLUSIVE") ? updatedSubtotal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.valueOf(updatedRows.get(i).getQuantity()).multiply(updatedRows.get(i).getSellingPrice()).setScale(2, RoundingMode.HALF_UP), // Subtotal calculation
                            "Remove"
                    });
                }

                // update the Subtotal and total tax value
                BigDecimal totalTaxSummation = BigDecimal.ZERO;
                BigDecimal SubtotalSummation = BigDecimal.ZERO;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                    BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                    totalTaxSummation = totalTaxSummation.add(totalTax);
                    SubtotalSummation = SubtotalSummation.add(subTotal);
                }

                // update the computed total tax and computed subtotal
                computeSubtotal(SubtotalSummation);
                computeSubtotalTax(totalTaxSummation);
                computeNetTotal();
            }
        });

        // Add ActionListener to ComboBox
        productsCombo.addActionListener(e -> {

            String selectedProduct = (String) productsCombo.getSelectedItem();
            int productIndex = productsCombo.getSelectedIndex();
            int productId = productMap.get(productIndex);


            List<PurchaseListedProduct> rows = getAllRows(tableModel);
            for (int i = 0; i < rows.size(); i++) {
                // check if that product is already added
                if (productId == rows.get(i).getId()) {
                    JOptionPane.showMessageDialog(null, "This product is already added.");
                    return;
                }
            }

            if (productMap.get(productIndex) == null) return;

            assert selectedProduct != null;
            if (!selectedProduct.equals("Select Product")) {
                BigDecimal totalTaxSummation = BigDecimal.ZERO;
                BigDecimal SubtotalSummation = BigDecimal.ZERO;


                for (ProductResponseDto product : products) {

                    if (product.id() == productId) {
                        BigDecimal updatedSubtotal = BigDecimal.valueOf(product.stock()).multiply(product.purchasePrice().multiply(BigDecimal.valueOf(1.12)));

                        BigDecimal taxValueForInclusive = (product.sellingPrice().multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP));

                        tableModel.addRow(new Object[]{
                                tableModel.getRowCount() + 1, // Row number
                                product.id(),
                                product.code(),
                                product.name(),
                                String.valueOf(product.stock()), // Ensuring stock is a String
                                product.taxType().equals(ProductTaxType.EXCLUSIVE) ? product.purchasePrice().setScale(2, RoundingMode.HALF_UP) : product.sellingPrice().subtract(taxValueForInclusive).setScale(2, RoundingMode.HALF_UP),
                                product.taxType().equals(ProductTaxType.EXCLUSIVE) ? product.purchasePrice().multiply(BigDecimal.valueOf(1.12)).setScale(2, RoundingMode.HALF_UP) : product.sellingPrice().setScale(2, RoundingMode.HALF_UP),
                                product.taxType().equals(ProductTaxType.EXCLUSIVE) ? updatedSubtotal.subtract((BigDecimal.valueOf(product.stock()).multiply(product.purchasePrice()))).setScale(2, RoundingMode.HALF_UP) : taxValueForInclusive.setScale(2, RoundingMode.HALF_UP),
                                product.taxType().name(), // Assuming taxType() returns an enum, use name() to get String
                                product.taxType().equals(ProductTaxType.EXCLUSIVE) ? updatedSubtotal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.valueOf(product.stock()).multiply(product.sellingPrice()).setScale(2, RoundingMode.HALF_UP), // Subtotal calculation
                                "Remove"
                        });
                        break;
                    }
                }

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                    BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                    totalTaxSummation = totalTaxSummation.add(totalTax);
                    SubtotalSummation = SubtotalSummation.add(subTotal);
                }

                // update the computed total tax and computed subtotal
                computeSubtotal(SubtotalSummation);
                computeSubtotalTax(totalTaxSummation);
                computeNetTotal();

            }
        });

        // Add MouseListener to handle "Remove" button clicks in the table
        productsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = productsTable.columnAtPoint(e.getPoint());
                int row = productsTable.rowAtPoint(e.getPoint());

                if (column == 10) { // "Action" column index for "Remove" button
                    tableModel.removeRow(row);

                    BigDecimal totalTaxSummation = BigDecimal.ZERO;
                    BigDecimal SubtotalSummation = BigDecimal.ZERO;

                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        BigDecimal totalTax = (BigDecimal) tableModel.getValueAt(i, 7);
                        BigDecimal subTotal = (BigDecimal) tableModel.getValueAt(i, 9);
                        totalTaxSummation = totalTaxSummation.add(totalTax);
                        SubtotalSummation = SubtotalSummation.add(subTotal);
                    }

                    // update the computed total tax and computed subtotal
                    computeSubtotal(SubtotalSummation);
                    computeSubtotalTax(totalTaxSummation);
                    computeNetTotal();
                }
            }
        });

        // Table setup below Supplier and Select Products
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;

        // Increment row for the rest of the fields
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        summaryPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbcSummary = new GridBagConstraints();
        gbcSummary.insets = new Insets(5, 10, 5, 10);

        Font summaryFont = new Font("Arial", Font.BOLD, 22); // Larger font size for better visibility

        gbcSummary.gridx = 0;
        gbcSummary.gridy = 0;
        gbcSummary.anchor = GridBagConstraints.EAST;

        gbcSummary.gridx = 1;
        JLabel taxLabel = new JLabel("Tax: ₱");
        taxLabel.setFont(summaryFont);
        taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(taxLabel, gbcSummary);

        gbcSummary.gridx = 2;
        totalTaxProductLabel = new JLabel();
        totalTaxProductLabel.setFont(summaryFont);
        totalTaxProductLabel.setHorizontalAlignment(SwingConstants.LEFT);
        totalTaxProductLabel.setText("0");
        summaryPanel.add(totalTaxProductLabel, gbcSummary);

        gbcSummary.gridx = 3;
        JLabel totalLabel = new JLabel("Subtotal: ₱");
        totalLabel.setFont(summaryFont);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(totalLabel, gbcSummary);

        gbcSummary.gridx = 4;
        subtotalLabel = new JLabel();
        subtotalLabel.setFont(summaryFont);
        subtotalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        subtotalLabel.setText("0");
        summaryPanel.add(subtotalLabel, gbcSummary);

        gbc.gridx = 0;
        gbc.gridy = 2; // Position summary panel right below the table
        gbc.gridwidth = 8;
        panel.add(summaryPanel, gbc);

        // PO Reference, Payment Terms, Purchase Tax, Total Tax - same row
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel poReferenceLabel = new JLabel("PO Reference:");
        poReferenceLabel.setFont(labelFont);
        panel.add(poReferenceLabel, gbc);

        gbc.gridx = 1;
        JTextField poReferenceField = new JTextField(10);
        panel.add(poReferenceField, gbc);

        gbc.gridx = 2;
        JLabel paymentTermsLabel = new JLabel("Payment Terms:");
        paymentTermsLabel.setFont(labelFont);
        panel.add(paymentTermsLabel, gbc);

        gbc.gridx = 3;
        JTextField paymentTermsField = new JTextField(10);
        panel.add(paymentTermsField, gbc);

        gbc.gridx = 4;
        JLabel purchaseTaxLabel = new JLabel("Purchase Tax:");
        purchaseTaxLabel.setFont(labelFont);
        panel.add(purchaseTaxLabel, gbc);

        gbc.gridx = 5;
        JComboBox<String> purchaseTaxCombo = new JComboBox<>(new String[]{"VAT@12%"});
        panel.add(purchaseTaxCombo, gbc);
        purchaseTaxCombo.setSelectedItem("VAT@12%");
        purchaseTaxCombo.setEnabled(false);

        gbc.gridx = 6;
        JLabel totalTaxLabel = new JLabel("Total Tax:");
        totalTaxLabel.setFont(labelFont);
        panel.add(totalTaxLabel, gbc);

        gbc.gridx = 7;
        totalTaxField = new JTextField(10);
        totalTaxField.setText("0");
        panel.add(totalTaxField, gbc);
        totalTaxField.setEnabled(false);

        // New row for Discount and Transport Cost
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(labelFont);
        panel.add(discountLabel, gbc);

        gbc.gridx = 1;
        discountField = new JTextField(10);
        discountField.setText("0");
        panel.add(discountField, gbc);

        discountField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                for (char c : discountField.getText().toCharArray()) {
                    if (Character.isLetter(c)) {
                        JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                        discountField.setText("0");
                        break;
                    }
                }
                computeNetTotal();
            }
        });


        gbc.gridx = 2;
        JLabel transportCostLabel = new JLabel("Transport Cost:");
        transportCostLabel.setFont(labelFont);
        panel.add(transportCostLabel, gbc);

        gbc.gridx = 3;
        transportCostField = new JTextField(10);
        transportCostField.setText("0");
        panel.add(transportCostField, gbc);


        transportCostField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

                for (char c : transportCostField.getText().toCharArray()) {
                    if (Character.isLetter(c)) {
                        JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                        transportCostField.setText("0");
                        break;
                    }
                }

                computeNetTotal();
            }
        });

        gbc.gridy = 5; // Position it right below the Discount and Transport Cost row
        gbc.gridx = 0;

        JLabel accountLabel = new JLabel("Account:");
        accountLabel.setFont(labelFont);
        panel.add(accountLabel, gbc);

        gbc.gridx = 1;
        JTextField accountField = new JTextField();
        panel.add(accountField, gbc);

        gbc.gridx = 2;
        JLabel chequeNoLabel = new JLabel("Cheque No:");
        chequeNoLabel.setFont(labelFont);
        panel.add(chequeNoLabel, gbc);

        gbc.gridx = 3;
        JTextField chequeNoField = new JTextField(10);
        panel.add(chequeNoField, gbc);

        gbc.gridx = 4;
        JLabel receiptNoLabel = new JLabel("Receipt No:");
        receiptNoLabel.setFont(labelFont);
        panel.add(receiptNoLabel, gbc);

        gbc.gridx = 5;
        JTextField receiptNoField = new JTextField(10);
        panel.add(receiptNoField, gbc);

        // Shift remaining rows down by one row
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(labelFont);
        panel.add(noteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 7;
        JTextArea noteArea = new JTextArea(3, 50);
        panel.add(new JScrollPane(noteArea), gbc);

        // Adjusting other elements to fit below the new Discount and Transport Cost row
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel purchaseDateLabel = new JLabel("Purchase Date:");
        purchaseDateLabel.setFont(labelFont);
        panel.add(purchaseDateLabel, gbc);

        gbc.gridx = 1;
        JDatePickerImpl purchaseDatePicker = createDatePicker();
        panel.add(purchaseDatePicker, gbc);

        gbc.gridx = 2;
        JLabel poDateLabel = new JLabel("PO Date:");
        poDateLabel.setFont(labelFont);
        panel.add(poDateLabel, gbc);

        gbc.gridx = 3;
        JDatePickerImpl poDatePicker = createDatePicker();
        panel.add(poDatePicker, gbc);

        gbc.gridx = 4;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        panel.add(statusLabel, gbc);

        gbc.gridx = 5;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        panel.add(statusCombo, gbc);

        // Shift remaining elements down one row to make space for the new panel
        gbc.gridy++;

        // New Panel for Total Paid and Net Total
        JPanel totalPanel = new JPanel(new GridBagLayout());
        totalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "TOTAL AMOUNT", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints totalGbc = new GridBagConstraints();
        totalGbc.insets = new Insets(5, 5, 5, 5);

        // Total Paid Label and Text Field
        totalGbc.gridx = 0;
        totalGbc.gridy = 0;
        totalGbc.gridwidth = 1;
        JLabel totalPaidLabel = new JLabel("Total Paid:");
        totalPaidLabel.setFont(labelFont);
        totalPanel.add(totalPaidLabel, totalGbc);

        totalGbc.gridx = 1;
        totalGbc.gridwidth = 2;
        totalPaidField = new JTextField(10); // Regular text field for Total Paid
        totalPaidField.setText("0");
        totalPanel.add(totalPaidField, totalGbc);

        totalPaidField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                for (char c : totalPaidField.getText().toCharArray()) {
                    if (Character.isLetter(c)) {
                        JOptionPane.showMessageDialog(null, "You can't enter any letter but number");
                        totalPaidField.setText("0");
                        break;
                    }
                }

                computeNetTotal();
            }
        });

        // Add gap between Total Paid and Net Total
        totalGbc.gridx = 3;
        totalGbc.gridwidth = 1;
        totalPanel.add(Box.createHorizontalStrut(20), totalGbc); // Adds horizontal gap

        // Net Total Label and Display Label
        totalGbc.gridx = 4;
        JLabel netTotalLabel = new JLabel("Net Total:");
        netTotalLabel.setFont(labelFont);
        totalPanel.add(netTotalLabel, totalGbc);

        totalGbc.gridx = 5;
        netTotalField = new JTextField(); // Placeholder for Net Total display
        netTotalField.setFont(new Font("Arial", Font.BOLD, 24)); // Set to larger, bold font
        netTotalField.setEnabled(false);
        netTotalField.setText("0");
        totalPanel.add(netTotalField, totalGbc);

        // Add the new Total Panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 8; // Span the entire row for alignment
        panel.add(totalPanel, gbc);

        // Show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Create Purchase Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<PurchaseListedProduct> insertedRows = getAllRows(tableModel);
            Set<AddPurchaseItemRequestDto> purchaseItemRequestDtoSet = new HashSet<>();

            for (int i = 0; i < insertedRows.size(); i++) {
                AddPurchaseItemRequestDto addPurchaseItemRequestDto = new AddPurchaseItemRequestDto(
                        insertedRows.get(i).getId(),
                        insertedRows.get(i).getQuantity(),
                        insertedRows.get(i).getPurchasePrice(),
                        insertedRows.get(i).getSellingPrice(),
                        insertedRows.get(i).getTaxValue(),
                        insertedRows.get(i).getSubtotal()
                );
                purchaseItemRequestDtoSet.add(addPurchaseItemRequestDto);
            }

            // Handle form submission logic here
            int supplierSelectIndex = supplierCombo.getSelectedIndex();
            if (supplierSelectIndex == 0) {
                JOptionPane.showMessageDialog(null, "You didn't enter any supplier from the list");
                return;
            }

            if (insertedRows.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You didn't enter any product from the list");
                return;
            }

            if (poReferenceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You didn't enter any product from the list");
                return;
            }

            if (receiptNoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You didn't enter receipt number");
                return;
            }

            if (chequeNoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You didn't enter cheque number");
                return;
            }

            int supplierId = supplierMap.get(supplierSelectIndex);

            String poReference = poReferenceField.getText();
            String paymentTerms = paymentTermsField.getText();
            int purchaseTax = 12;
            BigDecimal subtotalTax = BigDecimal.valueOf(Double.parseDouble(totalTaxProductLabel.getText()));
            BigDecimal totalTax = BigDecimal.valueOf(Double.parseDouble(totalTaxField.getText()));
            BigDecimal netSubtotal = BigDecimal.valueOf(Double.parseDouble(subtotalLabel.getText()));
            BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(discountField.getText())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal transportCost = BigDecimal.valueOf(Double.parseDouble(transportCostField.getText())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalPaid = BigDecimal.valueOf(Double.parseDouble(totalPaidField.getText()));
            String account = accountField.getText();
            String chequeNumber = chequeNoField.getText();
            String receiptNumber = receiptNoField.getText();
            String note = noteArea.getText();
            String purchaseDate = purchaseDatePicker.getJFormattedTextField().getText();
            String poDate = poDatePicker.getJFormattedTextField().getText();
            String status = (String) statusCombo.getSelectedItem();

            // dd/mm/yyyy
            String[] splitPurchaseDate = purchaseDate.split("/");
            String[] splitPoDate = poDate.split("/");

            PurchaseService purchaseService = new PurchaseService();

            assert status != null;
            AddPurchaseRequestDto dto = new AddPurchaseRequestDto(
                    supplierId,
                    poReference,
                    paymentTerms,
                    purchaseTax,
                    totalTax,
                    subtotalTax,
                    netSubtotal,
                    discount,
                    transportCost,
                    account,
                    chequeNumber,
                    receiptNumber,
                    note,
                    LocalDate.of(Integer.parseInt(splitPurchaseDate[2]), Integer.parseInt(splitPurchaseDate[1]), Integer.parseInt(splitPurchaseDate[0])),
                    LocalDate.of(Integer.parseInt(splitPoDate[2]), Integer.parseInt(splitPoDate[1]), Integer.parseInt(splitPoDate[0])),
                    status.equals("Active") ? PurchaseStatus.ACTIVE : PurchaseStatus.INACTIVE,
                    totalPaid
            );

            purchaseService.add(dto, purchaseItemRequestDtoSet);
            JOptionPane.showMessageDialog(null, "Purchase added");
            loadPurchases();
        }
    }

    private void computeNetTotal() {
        if (transportCostField.getText().isEmpty()) {
            transportCostField.setText("0");
        } else if (discountField.getText().isEmpty()) {
            discountField.setText("0");
        } else if (totalPaidField.getText().isEmpty()) {
            totalPaidField.setText("0");
        }

        BigDecimal transportCost = new BigDecimal(transportCostField.getText());
        BigDecimal discount = new BigDecimal(discountField.getText());
        BigDecimal totalTax = new BigDecimal(totalTaxField.getText());
        BigDecimal totalPaid = new BigDecimal(totalPaidField.getText());
        BigDecimal subtotalTax = new BigDecimal(totalTaxProductLabel.getText());
        BigDecimal subtotalNet = new BigDecimal(subtotalLabel.getText());

        BigDecimal totalNet = ((subtotalTax.add(subtotalNet).add(transportCost).add(totalTax)).subtract(discount)).subtract(totalPaid);
        netTotalField.setText(String.valueOf(totalNet));
    }

    private void computeTotalTax() {
        BigDecimal totalTax = BigDecimal.valueOf(Double.parseDouble(subtotalLabel.getText())).multiply(BigDecimal.valueOf(0.12)).setScale(2, RoundingMode.HALF_UP);
        totalTaxField.setText(String.valueOf(totalTax));
    }

    private void computeSubtotal(BigDecimal subtotals) {
        subtotalLabel.setText(String.valueOf(subtotals.setScale(2, RoundingMode.HALF_UP)));
        computeTotalTax();
    }

    private void computeSubtotalTax(BigDecimal totalTaxes) {
        totalTaxProductLabel.setText(String.valueOf(totalTaxes.setScale(2, RoundingMode.HALF_UP)));
    }

    private List<PurchaseListedProduct> getAllRows(DefaultTableModel model) {
        List<PurchaseListedProduct> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            int number = (Integer) model.getValueAt(i, 0);
            Integer id =  model.getValueAt(i, 1) == null ? null : (Integer) model.getValueAt(i, 1);
            String code = (String) model.getValueAt(i, 2);
            String name = (String) model.getValueAt(i, 3);

            // Check if `quantity` and `purchasePrice` are stored as `String`; otherwise, safely convert them.
            int quantity = Integer.parseInt(model.getValueAt(i, 4).toString());
            BigDecimal purchasePrice = new BigDecimal(model.getValueAt(i, 5).toString());

            // Ensure `sellingPrice`, `taxValue`, and `subtotal` are `BigDecimal` values; otherwise, convert them.
            BigDecimal sellingPrice = new BigDecimal(model.getValueAt(i, 6).toString());
            BigDecimal taxValue = new BigDecimal(model.getValueAt(i, 7).toString());

            String taxType = (String) model.getValueAt(i, 8);
            BigDecimal subtotal = new BigDecimal(model.getValueAt(i, 9).toString());

            // Create a new `PurchaseListedProduct` instance with the parsed values and add it to the list
            PurchaseListedProduct purchaseListedProduct = new PurchaseListedProduct(
                    number,
                    id,
                    code,
                    name,
                    quantity,
                    purchasePrice,
                    sellingPrice,
                    taxValue,
                    taxType,
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
        private int number;
        private Integer id;
        private String code;
        private String name;
        private int quantity;
        private BigDecimal purchasePrice;
        private BigDecimal sellingPrice;
        private BigDecimal taxValue;
        private String taxType;
        private BigDecimal subtotal;
    }

    class EditableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setForeground(Color.BLUE); // Make text blue for editable cells
            setBorder(BorderFactory.createDashedBorder(Color.GRAY)); // Dashed border for editable cells
            setHorizontalAlignment(SwingConstants.CENTER);
            return component;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
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

    // Method to create date pickers with the current date
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);  // Automatically selects the current date

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Create a panel to hold the date pickers for "From" and "To" dates
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));  // GridLayout with 2 rows, 2 columns

        // Create bold and larger font for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // 16 size and bold

        JLabel fromLabel = new JLabel("From Date:");
        fromLabel.setFont(labelFont);  // Set to bold and larger size

        JLabel toLabel = new JLabel("To Date:");
        toLabel.setFont(labelFont);  // Set to bold and larger size

        // Create the date pickers
        JDatePickerImpl fromDatePicker = createDatePicker();  // Date picker for "From" date
        JDatePickerImpl toDatePicker = createDatePicker();    // Date picker for "To" date

        // Add components to the panel
        datePanel.add(fromLabel);
        datePanel.add(fromDatePicker);
        datePanel.add(toLabel);
        datePanel.add(toDatePicker);

        // Show a dialog with the date pickers
        int result = JOptionPane.showConfirmDialog(null, datePanel, "Select Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected dates
            String fromDateStr = fromDatePicker.getJFormattedTextField().getText();
            String toDateStr = toDatePicker.getJFormattedTextField().getText();

            // Parse the dates into a Date object or LocalDate for comparison
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Adjust format if necessary
            LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
            LocalDate toDate = LocalDate.parse(toDateStr, formatter);

            // Now, filter the table rows based on the selected date range
            filterTableByDateRange(fromDate, toDate);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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

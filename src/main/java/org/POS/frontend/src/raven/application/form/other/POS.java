
package org.POS.frontend.src.raven.application.form.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.additional_fee.AdditionalFee;
import org.POS.backend.cryptography.Base64Converter;
import org.POS.backend.payment.AddPaymentRequestDto;
import org.POS.backend.payment.PaymentDiscountType;
import org.POS.backend.payment.TransactionType;
import org.POS.backend.person.*;
import org.POS.backend.product.ProductResponseDto;
import org.POS.backend.product.ProductService;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_category.ProductCategoryResponseDto;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.sale.AddSaleRequestDto;
import org.POS.backend.sale.SaleService;
import org.POS.backend.sale_product.AddSaleProductRequestDto;
import org.POS.backend.shipping.AddShippingRequestDto;
import org.POS.frontend.src.com.raven.component.Item;
import org.POS.frontend.src.com.raven.model.ModelItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public class POS extends JPanel {

    private Timer timer;

    private Map<Integer, Integer> clientMap;

    private CopyOnWriteArrayList<SelectedProduct> selectedProductSet;

    private List<SelectedProduct> selectedProductList;

    private Map<Integer, Integer> categoryMap;

    private Integer selectClientId;

    private List<AddFee> addFees;

    private Shipping shipping;

    private Discount discount;

    private Map<Integer, TypeOfProduct> idType;

    // this only uses for cart table
    enum TypeOfProduct {
        SIMPLE,
        VARIABLE
    }

    public POS() {
        this.categoryMap = new HashMap<>();
        this.clientMap = new HashMap<>();
        this.selectedProductSet = new CopyOnWriteArrayList<>();
        this.selectedProductList = new ArrayList<>();
        this.addFees = new ArrayList<>();
        this.idType = new HashMap<>();

        this.shipping = new Shipping();
        this.discount = new Discount();

        initComponents();
        loadProductTable();
        loadCategoryForComboBox(jComboBox2, categoryMap);
        loadClientForComboBox(jComboBox1, clientMap);
        jComboBox2.addActionListener(e -> {
            int index = jComboBox2.getSelectedIndex();
            Integer categoryId = categoryMap.get(index);
            if (categoryId != null) {
                loadProductsUnderCategory(categoryId);
            }
        });

        jComboBox1.addActionListener(e -> {
            int index = jComboBox1.getSelectedIndex();
            Integer clientId = clientMap.get(index);
            if (clientId != null) {
                selectClientId = clientId;
            }
        });
        activateTableListener();

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

    private void activateTableListener() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 2) {
                Integer id = (Integer) model.getValueAt(row, 0);
                Integer updatedQuantity = Integer.parseInt(model.getValueAt(row, 2).toString());
                for (var selectedItem : selectedProductList) {
                    if (id.equals(selectedItem.getId())) {
                        if (selectedItem.getType().equals(ProductType.VARIABLE) && selectedItem.getVariableTotalQuantity() < updatedQuantity) {
                            JOptionPane.showMessageDialog(null, "Stock Remaining : " + selectedItem.getVariableTotalQuantity(), "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                            model.setValueAt(selectedItem.getVariableTotalQuantity(), row, 2);
                            return;
                        }

                        if (selectedItem.getType().equals(ProductType.SIMPLE) && selectedItem.getQuantity() < updatedQuantity) {
                            JOptionPane.showMessageDialog(null, "Stock Remaining : " + selectedItem.getQuantity(), "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                            model.setValueAt(selectedItem.getQuantity(), row, 2);
                            return;
                        }
                    }
                }

                updateSubtotal();
            }
        });

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());

                if (column == 5) {
                    // Fetch the ID and Type values from the selected row
                    Integer id = (Integer) model.getValueAt(row, 0);
                    String typeStr = (String) model.getValueAt(row, 4);
                    if (id == null || typeStr == null) {
                        JOptionPane.showMessageDialog(null, "Invalid product data!");
                        return;
                    }

                    // Convert the type string to enum (or keep as string if enums aren't used)
                    ProductType type;
                    try {
                        type = ProductType.valueOf(typeStr.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Unknown product type!");
                        return;
                    }

                    // Find and remove the matching product
                    SelectedProduct productToRemove = null;
                    for (SelectedProduct product : selectedProductSet) {
                        if (product.getType().equals(ProductType.SIMPLE) && product.getId().equals(id) && product.getType() == type) {
                            productToRemove = product;
                            break;
                        }

                        if (product.getType().equals(ProductType.VARIABLE) && product.getVariationId().equals(id) && product.getType() == type) {
                            productToRemove = product;
                            break;
                        }
                    }

                    if (productToRemove != null) {
                        // Remove product from the set and update the table
                        selectedProductSet.remove(productToRemove);
                        model.removeRow(row); // Remove row from JTable
                        JOptionPane.showMessageDialog(null, "Removed " + type + " product with ID: " + id);
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found in selection!");
                    }


//                    Integer id = (Integer) model.getValueAt(row, 0);
//                    String type = (String) model.getValueAt(row, 4);
//                    // remove the item from the list
//                    for (var selectedItem : selectedProductSet) {
////                        if (id != null && id.equals(selectedItem.getId())) {
////                            selectedProductSet.remove(selectedItem);
////                            reloadProductTable();
////                        }
//                        if(type.equals("VARIABLE") && idType.get(id).equals(TypeOfProduct.VARIABLE)
//                                && selectedItem.getType().equals(ProductType.VARIABLE)){
//                            JOptionPane.showMessageDialog(null, "Variable");
//                            break;
//                        }else if(type.equals("SIMPLE") && idType.get(id).equals(TypeOfProduct.SIMPLE)
//                                && selectedItem.getType().equals(ProductType.SIMPLE)){
//                            JOptionPane.showMessageDialog(null, "Simple");
//                            break;
//                        }
//                    }
//                    reloadProductTable();
//                    model.removeRow(row);
                }
            }
        });
    }

    private ModelItem openModalForVariableProducts(ModelItem itemData) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Fonts
        Font boldFont = new Font("Arial", Font.BOLD, 16);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Product Name Label (Centered)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel productNameLabel = new JLabel(itemData.getItemName(), SwingConstants.CENTER);
        productNameLabel.setFont(boldFont);
        panel.add(productNameLabel, gbc);

        // Price Label (Centered)
        gbc.gridy = 1;
        JLabel priceLabel = new JLabel("0.00", SwingConstants.CENTER);
        priceLabel.setFont(boldFont);
        panel.add(priceLabel, gbc);

        // Variation Categories Panel (ScrollPane)
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel variationsContainer = new JPanel();
        variationsContainer.setLayout(new BoxLayout(variationsContainer, BoxLayout.Y_AXIS));

        // ScrollPane for Variations
        JScrollPane scrollPane = new JScrollPane(variationsContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Adjust size for scrollable variations
        panel.add(scrollPane, gbc);

        // Add Variation Categories (e.g., Color, Size)
        // Example 1: Color
        Map<Integer, Integer> variationMap = new HashMap<>();
        Map<Integer, Integer> variationQuantityMap = new HashMap<>();
        for (var attribute : itemData.getProductAttributes()) {
            double[] prices = new double[attribute.getProductVariations().size()];
            String[] names = new String[attribute.getProductVariations().size()];
            Integer[] variationIds = new Integer[attribute.getProductVariations().size()];
            int[] variationQuantities = new int[attribute.getProductVariations().size()];


            for (int i = 0; i < attribute.getProductVariations().size(); i++) {

                prices[i] = Double.parseDouble(String.valueOf(attribute.getProductVariations().get(i).getSrp()));
                names[i] = attribute.getProductVariations().get(i).getVariation();
                variationIds[i] = attribute.getProductVariations().get(i).getId();
                variationQuantities[i] = attribute.getProductVariations().get(i).getQuantity();
            }


            variationsContainer.add(createVariationCategoryPanel(attribute.getName(), names,
                    prices, priceLabel, regularFont, boldFont, variationIds, variationQuantities, variationQuantityMap, variationMap));

        }

        // Quantity Label
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(regularFont);
        panel.add(quantityLabel, gbc);

        // Quantity Spinner
        gbc.gridx = 1;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        quantitySpinner.setFont(regularFont);
        panel.add(quantitySpinner, gbc);

        // Notes Label
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(regularFont);
        panel.add(notesLabel, gbc);

        // Notes Text Area
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JTextArea notesTextArea = new JTextArea(3, 20);
        notesTextArea.setFont(regularFont);
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        panel.add(notesScrollPane, gbc);

        // Buttons Panel
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Display the form in a dialog
        JDialog dialog = new JDialog((Frame) null, "Product Selection", true);

        boolean[] isAddToCartClicked = new boolean[1];

        // Add to Cart Button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(boldFont);
        addToCartButton.setBackground(new Color(0, 102, 204));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.addActionListener(e -> {
            int selectedQuantity = (int) quantitySpinner.getValue();
            double price = Double.parseDouble(priceLabel.getText());
            if (selectedQuantity <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                itemData.setPrice(price);
                boolean hasSelectedVatiation = false;
                for (var attribute : itemData.getProductAttributes()) {
                    boolean isBreak = false;
                    for (var variation : attribute.getProductVariations()) {
                        if (variationMap.get(variation.getId()) != null) {
                            itemData.setVariationId(variationMap.get(variation.getId()));
                            isBreak = true;
                            break;
                        }
                    }

                    if (isBreak) {
                        hasSelectedVatiation = true;
                        break;
                    }
                }

                // validate the stock if enough
                int remainingStock = 0;
                for (var attribute : itemData.getProductAttributes()) {
                    boolean isBreak = false;
                    for (var variation : attribute.getProductVariations()) {
                        if (variationQuantityMap.get(variation.getId()) != null) {
                            // check if the quantity is enough
                            if (variationQuantityMap.get(variation.getId()) < selectedQuantity) {
                                isBreak = true;
                                remainingStock = variationQuantityMap.get(variation.getId());
                            } else {
                                itemData.setQuantity(selectedQuantity);
                                itemData.setVariableTotalQuantity(variation.getQuantity());
                            }
                            break;
                        }
                    }

                    if (isBreak) {
                        itemData.setQuantity(remainingStock);
                        itemData.setVariableTotalQuantity(remainingStock);
                        JOptionPane.showMessageDialog(null, "Stock Remaining : " + remainingStock, "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                if (hasSelectedVatiation) {
                    isAddToCartClicked[0] = true;
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select a Product Variation");
                }
            }
        });
        buttonPanel.add(addToCartButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(boldFont);
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            // Close the dialog
            SwingUtilities.getWindowAncestor(panel).dispose();
        });
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, gbc);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return isAddToCartClicked[0] ? itemData : null;
    }

    private JPanel createVariationCategoryPanel(
            String categoryName,
            String[] options,
            double[] prices,
            JLabel priceLabel,
            Font regularFont,
            Font boldFont,
            Integer[] variationId,
            int[] variationQuantities,
            Map<Integer, Integer> variationQuantityMap,
            Map<Integer, Integer> variationMap
    ) {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), categoryName, 0, 0, boldFont));

        for (int i = 0; i < options.length; i++) {
            JButton optionButton = new JButton(options[i]);
            int index = i; // Capture index for event handling

            if (variationQuantities[index] <= 0) {
                optionButton.setEnabled(false);
            }
            optionButton.setFont(regularFont);
            optionButton.addActionListener(e -> {
                // clear the map
                variationMap.clear();
                variationQuantityMap.clear();

                priceLabel.setText(String.valueOf(prices[index]));
                variationMap.put(variationId[index], variationId[index]);
                variationQuantityMap.put(variationId[index], variationQuantities[index]);
            });
            categoryPanel.add(optionButton);
        }
        return categoryPanel;
    }

    private void loadClientForComboBox(JComboBox<String> clientCombo, Map<Integer, Integer> clientMap) {
        PersonService personService = new PersonService();
        SwingWorker<List<PersonResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<PersonResponseDto> doInBackground() {
                return personService.getAllValidPeopleByType(PersonType.CLIENT);
            }

            @Override
            protected void done() {
                try {
                    var clients = get();
                    clientCombo.removeAllItems();
                    clientCombo.addItem("Select Customer");
                    for (int i = 0; i < clients.size(); i++) {
                        clientCombo.addItem(clients.get(i).name());
                        clientMap.put(i + 1, clients.get(i).id());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private void loadCategoryForComboBox(JComboBox<String> categoryCombo, Map<Integer, Integer> categoryMap) {
        ProductCategoryService productCategoryService = new ProductCategoryService();
        categoryCombo.removeAllItems();
        SwingWorker<List<ProductCategoryResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ProductCategoryResponseDto> doInBackground() {
                return productCategoryService.getAllValidProductCategories();
            }

            @Override
            protected void done() {
                try {
                    var categories = get();
                    categoryCombo.addItem("Select Category");
                    int i = 1;
                    for (var category : categories) {
                        categoryCombo.addItem(category.name());
                        categoryMap.put(i, category.id());
                        i++;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private void loadProductsUnderCategory(int id) {
        selectedProductList.clear();
        selectedProductSet.clear();
        ProductService productService = new ProductService();
        SwingWorker<List<ProductResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ProductResponseDto> doInBackground() throws Exception {
                return productService.getAllValidProductByCategoryId(id);
            }

            @Override
            protected void done() {
                try {
                    panelItem.removeAll();
                    var products = get();
                    for (var product : products) {
                        if (product.type().equals(ProductType.SIMPLE) && product.stock() <= 0) continue;
                        ModelItem modelItem = new ModelItem();
                        modelItem.setQuantity(product.type().equals(ProductType.SIMPLE) ? product.stock() : 0);
                        modelItem.setProductAttributes(product.productAttributes());
                        modelItem.setType(product.type());
                        modelItem.setItemID(product.id());
                        modelItem.setItemName(product.name());
                        modelItem.setDescription("");
                        modelItem.setPrice(Double.parseDouble(String.valueOf(product.sellingPrice())));
                        modelItem.setBrandName(product.category().getName());
                        modelItem.setImage(product.image().isEmpty() ? null : decodeBase64(product.image()));
                        addItem(modelItem);
                    }
                    panelItem.repaint();
                    panelItem.revalidate();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
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

                    for (var purchaseProduct : selectedProductSet) {
                        if (purchaseProduct.getType().equals(ProductType.SIMPLE) && purchaseProduct.getId() == itemData.getItemID()) {
                            JOptionPane.showMessageDialog(null, purchaseProduct.getName() + " is already added");
                            return;
                        }
                    }

                    if (itemData.getType().equals(ProductType.VARIABLE)) {
                        ModelItem updatedItem = openModalForVariableProducts(itemData);
                        if (updatedItem != null) {

                            // validate if this variation is not exist in the table yet
                            for (var rowInTheTable : selectedProductSet) {
                                if (rowInTheTable.getType().equals(ProductType.VARIABLE) && rowInTheTable.getVariationId().equals(updatedItem.getVariationId())) {
                                    JOptionPane.showMessageDialog(null, rowInTheTable.getName() + " is already added");
                                    return;
                                }
                            }

                            idType.put(updatedItem.getVariationId(), TypeOfProduct.VARIABLE);
                            selectedProductSet.add(new SelectedProduct(
                                    updatedItem.getItemID(),
                                    updatedItem.getItemName(),
                                    updatedItem.getQuantity(),
                                    BigDecimal.valueOf(updatedItem.getPrice()),
                                    updatedItem.getType(),
                                    updatedItem.getVariationId(),
                                    updatedItem.getVariableTotalQuantity()
                            ));

                            selectedProductList.add(new SelectedProduct(
                                    updatedItem.getItemID(),
                                    updatedItem.getItemName(),
                                    updatedItem.getVariableTotalQuantity(),
                                    BigDecimal.valueOf(updatedItem.getPrice()),
                                    updatedItem.getType(),
                                    updatedItem.getVariationId(),
                                    updatedItem.getVariableTotalQuantity()
                            ));

                        }
                    } else {
                        idType.put(data.getItemID(), TypeOfProduct.SIMPLE);
                        selectedProductSet.add(new SelectedProduct(
                                data.getItemID(),
                                data.getItemName(),
                                1,
                                BigDecimal.valueOf(data.getPrice()),
                                itemData.getType(),
                                null,
                                0
                        ));

                        selectedProductList.add(new SelectedProduct(
                                data.getItemID(),
                                data.getItemName(),
                                data.getQuantity(),
                                BigDecimal.valueOf(data.getPrice()),
                                itemData.getType(),
                                null,
                                0
                        ));
                    }
                    reloadProductTable();
                }
            }
        });
        panelItem.add(item);
        panelItem.repaint();
        panelItem.revalidate();
    }

    private void reloadProductTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (var selectedProduct : selectedProductSet) {
            if (selectedProduct.getType().equals(ProductType.SIMPLE)) {
                model.addRow(new Object[]{
                        selectedProduct.getId(),
                        selectedProduct.getName(),
                        selectedProduct.getQuantity(),
                        selectedProduct.getPrice(),
                        selectedProduct.getType().name(),
                        "Remove"
                });
            } else {
                model.addRow(new Object[]{
                        selectedProduct.getVariationId(),
                        selectedProduct.getName(),
                        selectedProduct.getQuantity(),
                        selectedProduct.getPrice(),
                        selectedProduct.getType().name(),
                        "Remove"
                });
            }

        }
        updateSubtotal();
    }

    private void updateSubtotal() {
        var rows = getAllRows();
        double summation = 0D;
        for (var row : rows) {
            summation += Double.parseDouble(String.valueOf(row.getPrice().multiply(BigDecimal.valueOf(row.getQuantity()))));
        }

        double addFeeAmount = 0D;
        if (!addFees.isEmpty()) {
            for (var addFee : addFees) {
                addFeeAmount += addFee.getAmount();
            }
        }

        jLabel8.setText(String.valueOf(BigDecimal.valueOf((summation + addFeeAmount) - discount.getAmount()).setScale(2, RoundingMode.HALF_UP)));
        updateTotalTax();
    }

    private void updateTotalTax() {
        Double subtotal = Double.parseDouble(jLabel8.getText()) * 0.12 / 1.12;
        jLabel10.setText(String.valueOf(BigDecimal.valueOf(subtotal).setScale(2, RoundingMode.HALF_UP)));
    }

    private ImageIcon decodeBase64(String base64String) {
        ImageIcon icon = null;
        try {
            // Decode Base64 string to byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            // Convert byte array to BufferedImage
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);

            // Create ImageIcon from BufferedImage
            icon = new ImageIcon(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load image from Base64 string: " + e.getMessage());

        }
        return icon;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        roundPanel2 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        scroll = new javax.swing.JScrollPane();
        panelItem = new org.POS.frontend.src.com.raven.swing.PanelItem();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        roundPanel3 = new org.POS.frontend.src.javaswingdev.swing.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Create Sales");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select Customer", "Andy Suarez", "Christian James Torres", "Michael Angelo Zara"}));

        jButton3.setBackground(new java.awt.Color(204, 255, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Add Customer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setText("");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Select Category"}));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox1, 0, 422, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                        .addComponent(jComboBox2))
                                .addGap(10, 10, 10))
        );

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
                roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
        );
        roundPanel2Layout.setVerticalGroup(
                roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroll)
                                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Add");

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Fee");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 255, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Shipping");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(204, 255, 204));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setText("Discount");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(24, 24, 24)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButton5)
                                                        .addComponent(jButton4)))
                                        .addComponent(jLabel6))
                                .addGap(10, 10, 10))
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "ID", "Product", "Quantity", "Price", "Type", "Action"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jTable1.getColumn("Action").setCellRenderer(new ButtonRenderer());

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
                roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
                roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(roundPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel8.setText("0.00");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("Subtotal");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                .addGap(120, 120, 120)
                                .addComponent(jLabel8)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(14, 14, 14))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("0");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setText("Tax");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(roundPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        // Create a modal dialog
        JDialog paymentDialog = new JDialog((JFrame) null, "Select Payment Method", true);
        paymentDialog.setSize(400, 300);
        paymentDialog.setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, with spacing

        // Create buttons with styling
        JButton cashPaymentButton = new JButton("Cash Payment");
        JButton poPaymentButton = new JButton("PO Payment");
        JButton onlinePaymentButton = new JButton("Online Payment");

        // Apply custom styles to the buttons
        styleButton(cashPaymentButton);
        styleButton(poPaymentButton);
        styleButton(onlinePaymentButton);

        // Add action listeners for each button
        cashPaymentButton.addActionListener(e -> {
            paymentDialog.dispose();
            showCashPaymentModal();
        });

        poPaymentButton.addActionListener(e -> {
            paymentDialog.dispose();
            showPaymentModal("PO", paymentDialog, 200.00);
        });

        onlinePaymentButton.addActionListener(e -> {

            paymentDialog.dispose();
            showPaymentModal("Online", paymentDialog, 100.00);// Close the dialog
        });

        // Add buttons to the panel
        buttonPanel.add(cashPaymentButton);
        buttonPanel.add(poPaymentButton);
        buttonPanel.add(onlinePaymentButton);

        // Add padding around the buttons
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        paddedPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the padded panel to the dialog
        paymentDialog.add(paddedPanel, BorderLayout.CENTER);

        // Center the dialog relative to the parent frame and make it visible
        paymentDialog.setLocationRelativeTo(null);
        paymentDialog.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(150, 183, 162)); // Extracted color
        button.setForeground(Color.WHITE);             // Text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor

    }//GEN-LAST:event_jButton1ActionPerformed

    private void showPaymentModal(String paymentType, JDialog paymentDialog, double totalAmount) {
        // Create the modal dialog
        JDialog paymentModal = new JDialog(paymentDialog, paymentType + " Payment", true);
        paymentModal.setSize(400, 300);
        paymentModal.setLayout(new GridBagLayout());
        paymentModal.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Components
        JLabel totalLabel = new JLabel("Total Amount: ₱" + Double.parseDouble(jLabel8.getText()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bold label
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel referenceLabel = new JLabel("Reference Number:");
        referenceLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold label

        JTextField referenceField = new JTextField(15);

        // Styled Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> {
            paymentModal.dispose(); // Close the modal
            paymentDialog.setVisible(true); // Show the original payment dialog
        });

        // Styled Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(e -> {
            if (paymentType.equals("Online") && referenceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter the Reference Number");
                return;
            }

            SaleService saleService = new SaleService();
            if (selectClientId == null) {
                JOptionPane.showMessageDialog(null, "Please Select Customer First");
                return;
            }
            AddSaleRequestDto dto = new AddSaleRequestDto(
                    selectClientId
            );

            List<AddSaleProductRequestDto> saleProductDtoList = new ArrayList<>();
            var rows = getAllRows();
            for (var row : rows) {
                if (row.getType().equals(ProductType.SIMPLE)) {
                    AddSaleProductRequestDto saleDto = new AddSaleProductRequestDto(
                            row.getId(),
                            row.getPrice(),
                            row.getQuantity(),
                            null
                    );
                    saleProductDtoList.add(saleDto);
                } else {
                    for (var selectedProduct : selectedProductSet) {
                        if(selectedProduct.getVariationId() == null) continue;

                        if (selectedProduct.getVariationId().equals(row.getId()) && selectedProduct.getType().equals(ProductType.VARIABLE)) {
                            AddSaleProductRequestDto saleDto = new AddSaleProductRequestDto(
                                    selectedProduct.getId(),
                                    row.getPrice(),
                                    row.getQuantity(),
                                    selectedProduct.getVariationId()
                            );
                            saleProductDtoList.add(saleDto);
                            break;
                        }
                    }
                }
            }

            AddShippingRequestDto shippingDto = new AddShippingRequestDto(
                    shipping.getName(),
                    shipping.getPhoneNumber(),
                    shipping.getShippingAddress(),
                    shipping.getCity(),
                    shipping.getBarangay(),
                    shipping.getLandmark(),
                    shipping.getNote()
            );
            AddPaymentRequestDto paymentDto = new AddPaymentRequestDto(
                    discount.getType(),
                    discount.getAmount(),
                    paymentType.equals("Online") ? TransactionType.ONLINE : TransactionType.PO,
                    BigDecimal.ZERO,
                    paymentType.equals("Online") ? referenceField.getText() : ""
            );
            List<AdditionalFee> additionalFees = new ArrayList<>();
            for (var addFee : addFees) {
                AdditionalFee additionalFee = new AdditionalFee();
                additionalFee.setTitle(addFee.getName());
                additionalFee.setAmount(BigDecimal.valueOf(addFee.getAmount()));
                additionalFees.add(additionalFee);
            }

            SwingWorker<Integer, Void> worker = new SwingWorker<>() {
                @Override
                protected Integer doInBackground() {
                    try {
                        return saleService.add(
                                dto,
                                saleProductDtoList,
                                shippingDto,
                                paymentDto,
                                additionalFees
                        );
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        Integer result = get();
                        if (result != null) {
                            JOptionPane.showMessageDialog(paymentDialog, "Payment successful!\n\nCash In : " + totalAmount + "\nDate: " + LocalDate.now(), "Success", JOptionPane.INFORMATION_MESSAGE);
                            reset();
                        }
                        paymentDialog.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(paymentDialog, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();

        });

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        paymentModal.add(totalLabel, gbc);

        if (paymentType.equals("Online")) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // Reset to single column
            paymentModal.add(referenceLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            paymentModal.add(referenceField, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        paymentModal.add(cancelButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        paymentModal.add(saveButton, gbc);

        // Show the modal
        paymentModal.setLocationRelativeTo(paymentDialog);
        paymentModal.setVisible(true);
    }

    private void reset() {
        jComboBox1.setSelectedItem("Select Customer");
        selectedProductSet.clear();
        selectedProductList.clear();
        selectClientId = null;
        addFees.clear();

        shipping.setBarangay("");
        shipping.setCity("");
        shipping.setNote("");
        shipping.setShippingAddress("");
        shipping.setPhoneNumber("");
        shipping.setLandmark("");
        shipping.setName("");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        discount.setAmount(0D);
        jLabel8.setText("0");
        updateSubtotal();
        panelItem.removeAll();
        loadProductTable();
        panelItem.repaint();
        panelItem.revalidate();
    }

    private void showCashPaymentModal() {
        JDialog paymentDialog = new JDialog((Frame) null, "Payment Details", true);
        paymentDialog.setSize(500, 600);
        paymentDialog.setLayout(new BorderLayout());
        paymentDialog.setResizable(false);

        // Title panel
        JLabel amountLabel = new JLabel(String.valueOf(jLabel8.getText()), SwingConstants.CENTER);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 36));
        paymentDialog.add(amountLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Row 1: Tendered/Change section
        JLabel tenderedLabel = new JLabel("Tendered / Change:");
        tenderedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(tenderedLabel, gbc);

        // Tendered Field
        JTextField tenderedField = new JTextField("");
        tenderedField.setFont(new Font("Arial", Font.PLAIN, 18));
        tenderedField.setHorizontalAlignment(SwingConstants.CENTER);
        tenderedField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        tenderedField.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(tenderedField, gbc);

        // Change Label
        JLabel changeLabel = new JLabel("0.00", SwingConstants.CENTER);
        changeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        changeLabel.setForeground(Color.RED);
        changeLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        changeLabel.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(changeLabel, gbc);

        double finalSummationOfTotal = Double.parseDouble(jLabel8.getText());
        tenderedField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChange(tenderedField, changeLabel, finalSummationOfTotal);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChange(tenderedField, changeLabel, finalSummationOfTotal);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChange(tenderedField, changeLabel, finalSummationOfTotal);
            }
        });

        // Row 2: Numeric Keypad
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Add buttons to the keypad
        Dimension buttonSize = new Dimension(50, 50);
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(buttonFont);
            numberButton.setPreferredSize(buttonSize);
            numberButton.setFocusable(false);
            numberButton.addActionListener(e -> {
                tenderedField.setText(tenderedField.getText() + numberButton.getText());
                updateChange(tenderedField, changeLabel, finalSummationOfTotal);
            });
            keypadPanel.add(numberButton);
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setFont(buttonFont);
        zeroButton.setPreferredSize(buttonSize);
        zeroButton.setFocusable(false);
        zeroButton.addActionListener(e -> {
            if (!tenderedField.getText().equals("0")) {
                tenderedField.setText(tenderedField.getText() + "0");
                updateChange(tenderedField, changeLabel, finalSummationOfTotal);
            }
        });
        keypadPanel.add(zeroButton);

        JButton decimalButton = new JButton(".");
        decimalButton.setFont(buttonFont);
        decimalButton.setPreferredSize(buttonSize);
        decimalButton.setFocusable(false);
        decimalButton.addActionListener(e -> {
            if (!tenderedField.getText().contains(".")) {
                tenderedField.setText(tenderedField.getText() + ".");
            }
        });
        keypadPanel.add(decimalButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(buttonFont);
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setFocusable(false);
        clearButton.addActionListener(e -> {
            tenderedField.setText("");
            changeLabel.setText("0.00");
        });
        keypadPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        centerPanel.add(keypadPanel, gbc);

        paymentDialog.add(centerPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> paymentDialog.dispose());

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(120, 40));
        payButton.setBackground(Color.BLUE);
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Arial", Font.BOLD, 18));
        payButton.addActionListener(e -> {
            String tendered = tenderedField.getText();
            double change = Double.parseDouble(changeLabel.getText());
            if (change < 0) {
                JOptionPane.showMessageDialog(paymentDialog, "Insufficient amount!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SaleService saleService = new SaleService();
                if (selectClientId == null) {
                    JOptionPane.showMessageDialog(null, "Please Select Customer First");
                    return;
                }
                AddSaleRequestDto dto = new AddSaleRequestDto(
                        selectClientId
                );

                List<AddSaleProductRequestDto> saleProductDtoList = new ArrayList<>();
                var rows = getAllRows();
                for (var row : rows) {
                    if (row.getType().equals(ProductType.SIMPLE)) {
                        AddSaleProductRequestDto saleDto = new AddSaleProductRequestDto(
                                row.getId(),
                                row.getPrice(),
                                row.getQuantity(),
                                null
                        );
                        saleProductDtoList.add(saleDto);
                    } else {
                        for (var selectedProduct : selectedProductSet) {
                            if(selectedProduct.getVariationId() == null) continue;

                            if (selectedProduct.getVariationId().equals(row.getId()) && selectedProduct.getType().equals(ProductType.VARIABLE)) {
                                AddSaleProductRequestDto saleDto = new AddSaleProductRequestDto(
                                        selectedProduct.getId(),
                                        row.getPrice(),
                                        row.getQuantity(),
                                        selectedProduct.getVariationId()
                                );
                                saleProductDtoList.add(saleDto);
                                break;
                            }
                        }
                    }
                }

                AddShippingRequestDto shippingDto = new AddShippingRequestDto(
                        shipping.getName(),
                        shipping.getPhoneNumber(),
                        shipping.getShippingAddress(),
                        shipping.getCity(),
                        shipping.getBarangay(),
                        shipping.getLandmark(),
                        shipping.getNote()
                );
                AddPaymentRequestDto paymentDto = new AddPaymentRequestDto(
                        discount.getType(),
                        discount.getAmount(),
                        TransactionType.CASH,
                        BigDecimal.valueOf(Double.parseDouble(tendered)),
                        ""
                );
                List<AdditionalFee> additionalFees = new ArrayList<>();
                for (var addFee : addFees) {
                    AdditionalFee additionalFee = new AdditionalFee();
                    additionalFee.setTitle(addFee.getName());
                    additionalFee.setAmount(BigDecimal.valueOf(addFee.getAmount()));
                    additionalFees.add(additionalFee);
                }

                SwingWorker<Integer, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Integer doInBackground() {
                        try {
                            return saleService.add(
                                    dto,
                                    saleProductDtoList,
                                    shippingDto,
                                    paymentDto,
                                    additionalFees
                            );
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            Integer result = get();
                            if (result != null) {
                                JOptionPane.showMessageDialog(paymentDialog, "Payment successful!\n\nCash In : " + finalSummationOfTotal + "\nChange : " + change + "\nDate: " + LocalDate.now(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                reset();
                            }
                            paymentDialog.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(paymentDialog, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                worker.execute();
            }
        });

        footerPanel.add(cancelButton);
        footerPanel.add(payButton);

        paymentDialog.add(footerPanel, BorderLayout.SOUTH);

        // Display the dialog
        paymentDialog.setLocationRelativeTo(null);
        paymentDialog.setVisible(true);
    }

    private void updateChange(JTextField tenderedField, JLabel changeLabel, double finalSummationOfTotal) {
        try {
            String text = tenderedField.getText();
            if (text.isEmpty() || !text.matches("-?\\d+(\\.\\d+)?")) {
                changeLabel.setText("0");
                return;
            }
            Double tender = Double.parseDouble(text);
            Double change = tender - finalSummationOfTotal;
            changeLabel.setText(String.valueOf(change));
        } catch (NumberFormatException e) {
            changeLabel.setText("0.00");
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
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

            SwingWorker<Void, Void> categoryWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    jComboBox1.addItem("Loading...");
                    jComboBox1.setSelectedItem("Loading...");
                    jComboBox1.setEnabled(false);
                    loadClientForComboBox(jComboBox1, clientMap);
                    return null;
                }

                @Override
                protected void done() {
                    jComboBox1.setEnabled(true);
                }
            };

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
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
                        if (result) {
                            JOptionPane.showMessageDialog(null, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            categoryWorker.execute();
                        } else {
                            JOptionPane.showMessageDialog(null, "Unable to Create a Client", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            worker.execute();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JDialog discountDialog = new JDialog((Frame) null, "Cart Discount", true);
        discountDialog.setSize(500, 500);
        discountDialog.setLayout(new BorderLayout());
        discountDialog.setResizable(false);

        discount = new Discount();

        // Title panel
        JLabel titleLabel = new JLabel("Cart Discount", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        discountDialog.add(titleLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Type section
        JPanel typePanel = new JPanel(new BorderLayout());
        typePanel.setBorder(BorderFactory.createTitledBorder("Type"));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Select Type", "Percentage (%)", "Amount (₱)"});
        typeComboBox.setFont(new Font("Arial", Font.BOLD, 18));
        typePanel.add(typeComboBox, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        centerPanel.add(typePanel, gbc);

        // Amount section
        JPanel amountPanel = new JPanel(new BorderLayout());
        amountPanel.setBorder(BorderFactory.createTitledBorder("Amount"));
        JLabel amountDisplay = new JLabel(String.valueOf(discount == null ? 0D : discount.getAmount()), SwingConstants.CENTER);
        amountDisplay.setFont(new Font("Arial", Font.BOLD, 36));
        amountPanel.add(amountDisplay, BorderLayout.CENTER);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        centerPanel.add(amountPanel, gbc);

        // Numeric keypad
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        keypadPanel.setBackground(Color.LIGHT_GRAY);

        Font keypadFont = new Font("Arial", Font.BOLD, 24);
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(keypadFont);
            keypadPanel.add(numberButton);
            numberButton.addActionListener(e -> {
                String currentText = amountDisplay.getText();
                if (currentText.equals("0.00")) currentText = "";
                amountDisplay.setText(currentText + numberButton.getText());
            });
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setFont(keypadFont);
        keypadPanel.add(zeroButton);
        zeroButton.addActionListener(e -> {
            String currentText = amountDisplay.getText();
            if (!currentText.equals("0.00")) amountDisplay.setText(currentText + "0");
        });

        JButton decimalButton = new JButton(".");
        decimalButton.setFont(keypadFont);
        keypadPanel.add(decimalButton);
        decimalButton.addActionListener(e -> {
            String currentText = amountDisplay.getText();
            if (!currentText.contains(".")) amountDisplay.setText(currentText + ".");
        });

        JButton clearButton = new JButton("X");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(keypadFont);
        keypadPanel.add(clearButton);
        clearButton.addActionListener(e -> amountDisplay.setText("0.00"));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        centerPanel.add(keypadPanel, gbc);

        discountDialog.add(centerPanel, BorderLayout.CENTER);

// Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40)); // Set fixed button size
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> discountDialog.dispose());

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40)); // Set fixed button size
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(e -> {
            try {
                String type = (String) typeComboBox.getSelectedItem();
                double amount = Double.parseDouble(amountDisplay.getText());
                double subtotal = Double.parseDouble(jLabel8.getText());
                if (type.equals("Percentage (%)")) {
                    discount.setAmount(subtotal * (amount / (double) 100));
                    discount.setType(PaymentDiscountType.PERCENTAGE);
                    updateSubtotal();
                    discountDialog.dispose();
                } else if (type.equals("Amount (₱)")) {
                    discount.setAmount(amount);
                    discount.setType(PaymentDiscountType.AMOUNT);
                    updateSubtotal();
                    discountDialog.dispose();
                } else {
                    discount.setAmount(0D);
                    discount.setType(PaymentDiscountType.NO_DISCOUNT);
                    JOptionPane.showMessageDialog(null, "Please Select Discount Type");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Make sure that there's no any Character Entered");
            }
        });

        footerPanel.add(cancelButton);
        footerPanel.add(saveButton);

        discountDialog.add(footerPanel, BorderLayout.SOUTH);


        // Display dialog
        discountDialog.setLocationRelativeTo(null);
        discountDialog.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JDialog shippingDialog = new JDialog((Frame) null, "Shipping Information", true);
        shippingDialog.setSize(600, 400); // Adjusted size for fewer fields
        shippingDialog.setLayout(new BorderLayout());
        shippingDialog.setResizable(false);

        // Title panel
        JLabel titleLabel = new JLabel("Shipping Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        shippingDialog.add(titleLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reduced spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields to be added
        String[] stringLabels = {
                "Full Name",
                "Phone Number",
                "Shipping Address",
                "City",
                "Barangay",
                "Landmark",
                "Shipping Note"
        };

        JTextField[] fields = new JTextField[stringLabels.length];
        JLabel[] labels = new JLabel[stringLabels.length];
        for (int i = 0; i < labels.length; i++) {
            // Add label
            JLabel label = new JLabel(stringLabels[i] + ":");
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3; // Label takes 30% of the width
            centerPanel.add(label, gbc);
            labels[i] = label;

            // Add input field
            JTextField textField = new JTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weightx = 0.7; // Input field takes 70% of the width
            centerPanel.add(textField, gbc);
            fields[i] = textField;
        }

        if (shipping != null) {
            fields[0].setText(shipping.getName());
            fields[1].setText(shipping.getPhoneNumber());
            fields[2].setText(shipping.getShippingAddress());
            fields[3].setText(shipping.getCity());
            fields[4].setText(shipping.getBarangay());
            fields[5].setText(shipping.getLandmark());
            fields[6].setText(shipping.getNote());
        }

        shippingDialog.add(new JScrollPane(centerPanel), BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Adjusted spacing

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> shippingDialog.dispose());

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(e -> {
            boolean isValid = true;
            for (int i = 0; i < labels.length; i++) {
                if (fields[i].getText().isEmpty()) {
                    isValid = false;
                    JOptionPane.showMessageDialog(null, labels[i].getText() + " is Empty");
                    return;
                }
            }

            if (isValid) {
                // Save logic
                shipping = new Shipping(
                        fields[0].getText(),
                        fields[1].getText(),
                        fields[2].getText(),
                        fields[3].getText(),
                        fields[4].getText(),
                        fields[5].getText(),
                        fields[6].getText()
                );

                shippingDialog.dispose();
            }
        });

        footerPanel.add(cancelButton);
        footerPanel.add(saveButton);

        shippingDialog.add(footerPanel, BorderLayout.SOUTH);

        // Display dialog
        shippingDialog.setLocationRelativeTo(null);
        shippingDialog.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JDialog discountDialog = new JDialog((Frame) null, "Fee", true);
        discountDialog.setSize(500, 600);
        discountDialog.setLayout(new BorderLayout());
        discountDialog.setResizable(false);

        // Title panel
        JLabel titleLabel = new JLabel("Additional Fee", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        discountDialog.add(titleLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

// Row 1: Title and TextBox
        JLabel titleInputLabel = new JLabel("Title:");
        titleInputLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 0; // No resizing for the label
        gbc.gridwidth = 1; // Single column for the label
        gbc.anchor = GridBagConstraints.EAST; // Align to the right of the grid cell
        centerPanel.add(titleInputLabel, gbc);

        JTextField titleTextField = new JTextField();
        titleTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1; // Second column
        gbc.gridy = 0; // Same row as the label
        gbc.weightx = 1; // Allow resizing horizontally
        gbc.gridwidth = 1; // Single column for the text field
        gbc.anchor = GridBagConstraints.WEST; // Align to the left of the grid cell
        centerPanel.add(titleTextField, gbc);

        // Row 2: Amount section
        JPanel amountPanel = new JPanel(new BorderLayout());
        amountPanel.setBorder(BorderFactory.createTitledBorder("Amount"));
        JLabel amountDisplay = new JLabel("0.00", SwingConstants.CENTER);
        amountDisplay.setFont(new Font("Arial", Font.BOLD, 36));
        amountPanel.add(amountDisplay, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        centerPanel.add(amountPanel, gbc);

        // Row 3: Numeric keypad
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        keypadPanel.setBackground(Color.LIGHT_GRAY);

        Font keypadFont = new Font("Arial", Font.BOLD, 24);
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(keypadFont);
            keypadPanel.add(numberButton);
            numberButton.addActionListener(e -> {
                String currentText = amountDisplay.getText();
                if (currentText.equals("0.00")) currentText = "";
                amountDisplay.setText(currentText + numberButton.getText());
            });
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setFont(keypadFont);
        keypadPanel.add(zeroButton);
        zeroButton.addActionListener(e -> {
            String currentText = amountDisplay.getText();
            if (!currentText.equals("0.00")) amountDisplay.setText(currentText + "0");
        });

        JButton decimalButton = new JButton(".");
        decimalButton.setFont(keypadFont);
        keypadPanel.add(decimalButton);
        decimalButton.addActionListener(e -> {
            String currentText = amountDisplay.getText();
            if (!currentText.contains(".")) amountDisplay.setText(currentText + ".");
        });

        JButton clearButton = new JButton("X");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(keypadFont);
        keypadPanel.add(clearButton);
        clearButton.addActionListener(e -> amountDisplay.setText("0.00"));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        centerPanel.add(keypadPanel, gbc);

        discountDialog.add(centerPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40)); // Set fixed button size
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> discountDialog.dispose());

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40)); // Set fixed button size
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(e -> {
            String titleText = titleTextField.getText();
            if (titleText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(discountDialog, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AddFee addFee = new AddFee(titleText, Double.parseDouble(amountDisplay.getText()));
            addFees.add(addFee);
            updateSubtotal();
            discountDialog.dispose();
        });

        footerPanel.add(cancelButton);
        footerPanel.add(saveButton);

        discountDialog.add(footerPanel, BorderLayout.SOUTH);

        // Display dialog
        discountDialog.setLocationRelativeTo(null);
        discountDialog.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void loadProductTable() {
        selectedProductList.clear();
        selectedProductSet.clear();

        ProductService productService = new ProductService();
        SwingWorker<List<ProductResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ProductResponseDto> doInBackground() {
                return productService.getAllValidProducts();
            }

            @Override
            protected void done() {
                try {
                    panelItem.removeAll();
                    var products = get();

                    for (var product : products) {

                        if (product.type().equals(ProductType.SIMPLE) && product.stock() <= 0) continue;

                        ModelItem modelItem = new ModelItem();
                        modelItem.setProductAttributes(product.productAttributes());
                        modelItem.setQuantity(product.type().equals(ProductType.SIMPLE) ? product.stock() : 0);
                        modelItem.setType(product.type());
                        modelItem.setItemID(product.id());
                        modelItem.setItemName(product.name());
                        modelItem.setDescription("");
                        modelItem.setPrice(Double.parseDouble(String.valueOf(product.sellingPrice())));
                        modelItem.setBrandName(product.category().getName());
                        modelItem.setImage(product.image().isEmpty() ? null : decodeBase64(product.image()));
                        addItem(modelItem);
                    }
                    panelItem.repaint();
                    panelItem.revalidate();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
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
        String name = jTextField1.getText();

        if (name.isEmpty()) {
            loadProductTable();
            return;
        }

        ProductService productService = new ProductService();
        SwingWorker<List<ProductResponseDto>, Void> worker = new SwingWorker<List<ProductResponseDto>, Void>() {
            @Override
            protected List<ProductResponseDto> doInBackground() throws Exception {
                return productService.getAllValidProductByName(name);
            }

            @Override
            protected void done() {

                try {
                    panelItem.removeAll();
                    var products = get();

                    for (var product : products) {
                        if (product.type().equals(ProductType.SIMPLE) && product.stock() <= 0) continue;

                        ModelItem modelItem = new ModelItem();
                        modelItem.setProductAttributes(product.productAttributes());
                        modelItem.setQuantity(product.type().equals(ProductType.SIMPLE) ? product.stock() : 0);
                        modelItem.setType(product.type());
                        modelItem.setItemID(product.id());
                        modelItem.setItemName(product.name());
                        modelItem.setDescription("");
                        modelItem.setPrice(Double.parseDouble(String.valueOf(product.sellingPrice())));
                        modelItem.setBrandName(product.category().getName());
                        modelItem.setImage(product.image().isEmpty() ? null : decodeBase64(product.image()));
                        addItem(modelItem);
                    }
                    panelItem.repaint();
                    panelItem.revalidate();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
    }

    private List<SelectedProduct> getAllRows() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        List<SelectedProduct> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            int id = (Integer) model.getValueAt(i, 0);
            String name = (String) model.getValueAt(i, 1);
            String type = (String) model.getValueAt(i, 4);
            // Check if `quantity` and `purchasePrice` are stored as `String`; otherwise, safely convert them.
            int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
            BigDecimal purchasePrice = (BigDecimal) model.getValueAt(i, 3);

            // Create a new `PurchaseListedProduct` instance with the parsed values and add it to the list
            SelectedProduct purchaseListedProduct = new SelectedProduct();
            purchaseListedProduct.setId(id);
            purchaseListedProduct.setQuantity(quantity);
            purchaseListedProduct.setName(name);
            purchaseListedProduct.setType(type.equals("SIMPLE") ? ProductType.SIMPLE : ProductType.VARIABLE);
            purchaseListedProduct.setPrice(purchasePrice);
            rows.add(purchaseListedProduct);
        }
        return rows;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class SelectedProduct {
        private Integer id;
        private String name;
        private int quantity;
        private BigDecimal price;
        private ProductType type;
        private Integer variationId;
        private int variableTotalQuantity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class AddFee {
        private String name;
        private double amount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Shipping {
        private String name;
        private String phoneNumber;
        private String shippingAddress;
        private String city;
        private String barangay;
        private String landmark;
        private String note;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Discount {
        private PaymentDiscountType type;
        private double amount;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.POS.frontend.src.com.raven.swing.PanelItem panelItem;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel2;
    private org.POS.frontend.src.javaswingdev.swing.RoundPanel roundPanel3;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}

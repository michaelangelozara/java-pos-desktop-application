
package org.POS.frontend.src.raven.application.form.other;

import org.POS.backend.invoice.Invoice;
import org.POS.backend.invoice.InvoiceService;
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
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class Invoice_List extends JPanel {

    private Timer timer;

    public Invoice_List() {
        initComponents();
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onEdit(int row) {
                JOptionPane.showMessageDialog(null, "You can't perform this action");
            }


            @Override
            public void onDelete(int row) {
                JOptionPane.showMessageDialog(null, "You can't perform this action");
            }

            @Override
            public void onView(int row) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int invoiceId = (Integer) model.getValueAt(row, 1);
                Application.showForm(new Invoice_Details(invoiceId));
            }

        };
        makeCellCenter(table);
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        activateSearch();
        loadInvoices();
    }

    private void makeCellCenter(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
    }

    private void activateSearch() {
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
            loadInvoices();
            return;
        }

        InvoiceService invoiceService = new InvoiceService();
        SwingWorker<List<Invoice>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Invoice> doInBackground() throws Exception {
                var invoices = invoiceService.getAllValidInvoicesByCode(name);
                return invoices;
            }

            @Override
            protected void done() {
                try {
                    var invoices = get();
                    int n = 1;
                    for (var invoice : invoices) {
                        BigDecimal subTotal = BigDecimal.ZERO;
                        for (var itemSale : invoice.getSale().getSaleProducts()) {
                            subTotal = subTotal.add(itemSale.getSubtotal());
                        }
                        model.addRow(new Object[]{
                                n,
                                invoice.getId(),
                                invoice.getInvoiceNumber(),
                                invoice.getSale().getDate(),
                                invoice.getSale().getPerson().getName(),
                                subTotal,
                                invoice.getSale().getPayment().getDiscount(),
                                invoice.getSale().getNetTotal().multiply(BigDecimal.valueOf(0.12).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP)),
                                invoice.getSale().getNetTotal()
                        });
                        n++;

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

    private void loadInvoices() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        InvoiceService invoiceService = new InvoiceService();
        SwingWorker<List<Invoice>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Invoice> doInBackground() throws Exception {
                var invoices = invoiceService.getAllValidInvoices(50);
                return invoices;
            }

            @Override
            protected void done() {
                try {
                    var invoices = get();

                    int n = 1;
                    for (var invoice : invoices) {
                        BigDecimal subTotal = BigDecimal.ZERO;
                        for (var itemSale : invoice.getSale().getSaleProducts()) {
                            subTotal = subTotal.add(itemSale.getSubtotal());
                        }
                        model.addRow(new Object[]{
                                n,
                                invoice.getId(),
                                invoice.getInvoiceNumber(),
                                invoice.getSale().getDate(),
                                invoice.getSale().getPerson().getName(),
                                subTotal,
                                invoice.getSale().getPayment().getDiscount(),
                                invoice.getSale().getNetTotal().multiply(BigDecimal.valueOf(0.12).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP)),
                                invoice.getSale().getNetTotal()
                        });
                        n++;

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jButton2 = new JButton();
        jButton1 = new JButton();

        jLabel1.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Invoice");

        jPanel2.setBorder(BorderFactory.createEtchedBorder());

        jLabel2.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("List of Invoice");

        jTextField1.setText("");

        table.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "ID", "Invoice No	", "Invoice Date	", "Client	", "Subtotal	", "Discount", "Tax	", "Net Total	", "Action"
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
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(7).setResizable(false);
            table.getColumnModel().getColumn(8).setResizable(false);
        }

        jButton2.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setText("From - To");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new Color(235, 161, 132));
        jButton1.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
                            .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 966, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(14, 14, 14))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
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
                        .addGap(0, 1272, Short.MAX_VALUE)))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Create a panel to hold the createdAt pickers for "From" and "To" dates
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));  // GridLayout with 2 rows, 2 columns

        // Create bold and larger font for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);  // 16 size and bold

        JLabel fromLabel = new JLabel("From Date:");
        fromLabel.setFont(labelFont);  // Set to bold and larger size

        JLabel toLabel = new JLabel("To Date:");
        toLabel.setFont(labelFont);  // Set to bold and larger size

        // Create the createdAt pickers
        JDatePickerImpl fromDatePicker = createDatePicker(LocalDate.now());  // Date picker for "From" createdAt
        JDatePickerImpl toDatePicker = createDatePicker(LocalDate.now());    // Date picker for "To" createdAt

        // Add components to the panel
        datePanel.add(fromLabel);
        datePanel.add(fromDatePicker);
        datePanel.add(toLabel);
        datePanel.add(toDatePicker);

        // Show a dialog with the createdAt pickers
        int result = JOptionPane.showConfirmDialog(null, datePanel, "Select Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected dates
            String fromDateStr = fromDatePicker.getJFormattedTextField().getText();
            String toDateStr = toDatePicker.getJFormattedTextField().getText();

            // Parse the dates into a Date object or LocalDate for comparison
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Adjust format if necessary
            LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
            LocalDate toDate = LocalDate.parse(toDateStr, formatter);

            // Now, filter the table rows based on the selected createdAt range
            filterTableByDateRange(fromDate, toDate);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private JDatePickerImpl createDatePicker(LocalDate localDate) {
        UtilDateModel model = new UtilDateModel();
        // Set the current createdAt
        model.setDate(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        model.setSelected(true);  // Automatically selects the current createdAt

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTextField jTextField1;
    private JTable table;
    // End of variables declaration//GEN-END:variables

    private void filterTableByDateRange(LocalDate fromDate, LocalDate toDate) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        InvoiceService invoiceService = new InvoiceService();
        SwingWorker<List<Invoice>, Void> worker = new SwingWorker<List<Invoice>, Void>() {
            @Override
            protected List<Invoice> doInBackground() throws Exception {
                return invoiceService.getALlValidInvoicesByRange(fromDate, toDate);
            }

            @Override
            protected void done() {
                try {
                    var invoices = get();
                    int n = 1;
                    for (var invoice : invoices) {
                        BigDecimal subTotal = BigDecimal.ZERO;
                        for (var itemSale : invoice.getSale().getSaleProducts()) {
                            subTotal = subTotal.add(itemSale.getSubtotal());
                        }
                        model.addRow(new Object[]{
                                n,
                                invoice.getId(),
                                invoice.getInvoiceNumber(),
                                invoice.getSale().getDate(),
                                invoice.getSale().getPerson().getName(),
                                subTotal,
                                invoice.getSale().getPayment().getDiscount(),
                                invoice.getSale().getNetTotal().multiply(BigDecimal.valueOf(0.12).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP)),
                                invoice.getSale().getNetTotal()
                        });
                        n++;

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
}

package org.POS.backend.invoice;

import java.util.List;

public class InvoiceService {

    private InvoiceDAO invoiceDAO;

    public InvoiceService(){
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Invoice> getAllValidInvoices(int number){
        return this.invoiceDAO.getAllValidInvoices(number);
    }
}

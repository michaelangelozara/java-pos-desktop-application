package org.POS.backend.invoice;

import java.time.LocalDate;
import java.util.List;

public class InvoiceService {

    private InvoiceDAO invoiceDAO;

    public InvoiceService(){
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Invoice> getAllValidInvoices(int number){
        return this.invoiceDAO.getAllValidInvoices(number);
    }

    public Invoice getValidInvoiceById(int id){
        return this.invoiceDAO.getValidInvoiceById(id);
    }

    public List<Invoice> getAllValidInvoicesByPersonId(int id){
        return this.invoiceDAO.getAllInvoicesByPersonId(id);
    }

    public List<Invoice> getALlValidInvoicesByRange(LocalDate start, LocalDate end, int id){
        return this.invoiceDAO.getAllValidInvoiceByRange(start, end, id);
    }

    public List<Invoice> getAllValidInvoiceByCodeAndPersonId(String code, int personId){
        return this.invoiceDAO.getAllValidInvoiceByCodeAndPersonId(code, personId);
    }

    public List<Invoice> getAllValidInvoicesByCode(String query){
        return this.invoiceDAO.getAllValidInvoicesByCode(query);
    }
}

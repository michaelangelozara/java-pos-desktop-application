package org.POS.backend.payment;

import java.time.LocalDate;
import java.util.List;

public class PaymentService {

    private PaymentDAO paymentDAO;

    public PaymentService(){
        this.paymentDAO = new PaymentDAO();
    }

    public List<Payment> getAllCashPayments(){
        return this.paymentDAO.getAllCashPayment();
    }

    public List<Payment> getAllCashPaymentsByUsername(String query){
        return this.paymentDAO.getAllCashPaymentByRange(query);
    }

    public List<Payment> getAllCashPaymentsByRange(LocalDate start, LocalDate end){
        return this.paymentDAO.getAllCashPaymentByRange(start, end);
    }
}

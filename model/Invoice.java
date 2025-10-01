package model;

import java.util.Date;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private Date date;
    private double totalAmount;
    private String paymentStatus;
    private List<Service> services;

    public Invoice(String invoiceId, Date date, double totalAmount, String paymentStatus, List<Service> services) {
        this.invoiceId = invoiceId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.services = services;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

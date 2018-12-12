package com.TicketIT.Model;

import java.util.List;

public class Booking {

    private String id;

    private String customerId;

    private List<String> tickets;

    private String invoiceId;

    private Boolean sendTickets;

    private String encryptSalt;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public List<String> getTickets() { return tickets; }

    public void setTickets(List<String> tickets) { this.tickets = tickets; }

    public String getInvoiceId() { return invoiceId; }

    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public Boolean getSendTickets() { return sendTickets; }

    public void setSendTickets(Boolean sendTickets) { this.sendTickets = sendTickets; }

    public String getEncryptSalt() { return encryptSalt; }

    public void setEncryptSalt(String encryptSalt) { this.encryptSalt = encryptSalt; }

}

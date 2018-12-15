package com.TicketIT.Model;

public class Member extends Customer {

    private String password;

    private String cardId;

    private Boolean isAdmin;

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getCardId() { return this.cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }

    public Boolean getIsAdmin() { return this.isAdmin; }

    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
}

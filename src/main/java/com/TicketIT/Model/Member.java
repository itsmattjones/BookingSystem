package com.TicketIT.Model;

import java.util.List;

public class Member {

    private String id;

    private String email;

    private String password;

    private String name;

    private List<String> address;

    private String telephone;

    private String cardId;

    private Boolean isAdmin;

    private String encryptSalt;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getAddress() { return address; }

    public void setAddress(List<String> address) { this.address = address; }

    public String getTelephone() { return this.telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getCardId() { return this.cardId; }

    public void setCardId(String cardid) { this.cardId = cardid; }

    public Boolean getIsAdmin() { return this.isAdmin; }

    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public String getEncryptSalt() { return encryptSalt; }

    public void setEncryptSalt(String encryptSalt) { this.encryptSalt = encryptSalt; }

}

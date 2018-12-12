package com.TicketIT.Model;

import java.util.List;

public class Customer {

    private String id;

    private String email;

    private String name;

    private List<String> address;

    private String telephone;

    private String encryptSalt;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getAddress() { return address; }

    public void setAddress(List<String> address) { this.address = address; }

    public String getTelephone() { return this.telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEncryptSalt() { return encryptSalt; }

    public void setEncryptSalt(String encryptSalt) { this.encryptSalt = encryptSalt; }

}

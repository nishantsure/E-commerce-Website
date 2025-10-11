package com.example.AmazonClone.models;

public class Customer {
    private Long cid;
    private String username;
    private String email;
    private String password;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer() {}

    public Customer(String password, String email, String username, Long cid) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.cid = cid;
    }

    public Customer(Long cid, String username, String email, String password) {
        this.cid = cid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(Long cid) {
        this.cid = cid;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "cid=" + cid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

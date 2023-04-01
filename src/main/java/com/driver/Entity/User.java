package com.driver.Entity;

import com.driver.model.CountryName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;

    private String password;
    private String originalIp;
    private String maskedIp;
    private boolean connected;

    @ManyToMany
    @JoinColumn
    List<ServiceProvider> serviceProviderList = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Connection> connectionList = new ArrayList<>();

    @OneToOne
    @JoinColumn
    Country country;
    public User(){

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getOriginalIp() {
        return originalIp;
    }

    public String getMaskedIp() {
        return maskedIp;
    }

    public boolean isConnected() {
        return connected;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public Country getCountry() {
        return country;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOriginalIp(String originalIp) {
        this.originalIp = originalIp;
    }

    public void setMaskedIp(String maskedIp) {
        this.maskedIp = maskedIp;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

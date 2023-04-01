package com.driver.Entity;

import com.driver.model.CountryName;

import javax.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private CountryName countryName;
    private String code;
   @OneToOne(mappedBy = "country",cascade = CascadeType.ALL)
   User user;

   @ManyToOne
   @JoinColumn
   ServiceProvider serviceProvider;

   public Country(){

   }

    public Country(CountryName countryName,String code) {
        this.countryName = countryName;
        this.code = code;

    }

    public int getId() {
        return id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public String getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}

package com.driver.Service.Impl;

import com.driver.Entity.Admin;
import com.driver.Entity.Country;
import com.driver.Entity.ServiceProvider;
import com.driver.Service.AdminService;
import com.driver.model.CountryName;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
       Admin admin = new Admin(username,password);
       adminRepository1.save(admin);
       return  admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) throws Exception {
        ServiceProvider serviceProvider = new ServiceProvider(providerName);
        Admin admin;
        try{
            admin = adminRepository1.findById(adminId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage() + "invalid admin id");
        }
        serviceProvider.setAdmin(admin);
        serviceProviderRepository1.save(serviceProvider);
        return  admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception {
        ServiceProvider serviceProvider;
        try{
            serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage() + "invalid serviceProvider Id");
        }
        CountryName countryName1 = null;
        if(countryName == "IND"){
            countryName1 = CountryName.IND;
        }else if(countryName == "USA"){
            countryName1 = CountryName.USA;
        }else if(countryName == "AUS"){
            countryName1 = CountryName.AUS;
        }else if(countryName == "CHI"){
            countryName1 = CountryName.CHI;
        }else if(countryName == "JPN"){
            countryName1 = CountryName.JPN;
        }

        Country country = new Country(countryName1,countryName1.toCode());
        serviceProvider.getCountryList().add(country);
        serviceProviderRepository1.save(serviceProvider);
        countryRepository1.save(country);
        return serviceProvider;
    }
}

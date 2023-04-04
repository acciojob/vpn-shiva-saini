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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
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
       admin.setServiceProviderList(new ArrayList<>());
       adminRepository1.save(admin);
       return  admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) throws Exception {
        Admin admin;
        try{
            admin = adminRepository1.findById(adminId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage() + "invalid admin id");
        }
        ServiceProvider serviceProvider = new ServiceProvider(providerName);
        serviceProvider.setAdmin(admin);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(new ArrayList<>());
        serviceProvider.setUserList(new ArrayList<>());
        List<ServiceProvider> serviceProviderList =  admin.getServiceProviderList();
        serviceProviderList.add(serviceProvider);
        admin.setServiceProviderList(serviceProviderList);
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
        if(countryName.equalsIgnoreCase("IND")){
            countryName1 = CountryName.IND;
        }else if(countryName.equalsIgnoreCase("USA")){
            countryName1 = CountryName.USA;
        }else if(countryName.equalsIgnoreCase("AUS")){
            countryName1 = CountryName.AUS;
        }else if(countryName.equalsIgnoreCase("CHI")){
            countryName1 = CountryName.CHI;
        }else if(countryName.equalsIgnoreCase("JPN")){
            countryName1 = CountryName.JPN;
        }else{
            throw new Exception("Country not found");
        }

        Country country = new Country(countryName1,countryName1.toCode());
       List<Country> countryList = serviceProvider.getCountryList();
       country.setServiceProvider(serviceProvider);
        countryList.add(country);
        serviceProvider.setCountryList(countryList);
        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;
    }
}

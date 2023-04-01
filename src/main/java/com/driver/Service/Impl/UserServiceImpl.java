package com.driver.Service.Impl;

import com.driver.Entity.Country;
import com.driver.Entity.ServiceProvider;
import com.driver.Entity.User;
import com.driver.Service.UserService;
import com.driver.model.CountryName;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName){
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
        User user = new User(username,password);
        user.setCountry(country);
        country.setServiceProvider(null);
        countryRepository3.save(country);
        userRepository3.save(user);
        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) throws Exception {
        ServiceProvider serviceProvider;
        try{
            serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage() + "invalid id");
        }

        User user;
        try{
            user = userRepository3.findById(userId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        serviceProvider.getUserList().add(user);
        serviceProviderRepository3.save(serviceProvider);

       return user;
    }
}

package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.services.UserService;
import com.driver.model.CountryName;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception {
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
        User user = new User(username,password);
        user.setCountry(country);
        user.setConnected(false);
        user.setMaskedIp("");
        country.setUser(user);
        user.setCountry(country);
        country.setServiceProvider(null);
        user = userRepository3.save(user);
        user.setOriginalIp(new String(user.getCountry().getCode()+"."+user.getId()));
        countryRepository3.save(country);
        user = userRepository3.save(user);

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

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        List<User> userList = serviceProvider.getUserList();
        serviceProvider.getUserList().add(user);
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);
        userList.add(user);
        serviceProvider.setUserList(userList);
        serviceProviderRepository3.save(serviceProvider);

       return user;
    }
}

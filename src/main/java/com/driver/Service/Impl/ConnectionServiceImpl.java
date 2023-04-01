package com.driver.Service.Impl;

import com.driver.Entity.Country;
import com.driver.Entity.ServiceProvider;
import com.driver.Entity.User;
import com.driver.Service.ConnectionService;
import com.driver.model.CountryName;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception {
            User user;
            try{
                user = userRepository2.findById(userId).get();
            }catch (Exception e){
                throw new Exception(e.getMessage());
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

        Country country = user.getCountry();
        ServiceProvider serviceProvider = country.getServiceProvider();
        serviceProvider.getUserList().add(user);
        serviceProviderRepository2.save(serviceProvider);
        return user;

    }
    @Override
    public User disconnect(int userId)  {
            return null;
    }
    @Override
    public User communicate(int senderId, int receiverId)  {
           return null;
    }
}

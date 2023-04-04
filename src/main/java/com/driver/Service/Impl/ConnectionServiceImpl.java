package com.driver.Service.Impl;

import com.driver.Entity.Connection;
import com.driver.Entity.Country;
import com.driver.Entity.ServiceProvider;
import com.driver.Entity.User;
import com.driver.Service.ConnectionService;
import com.driver.model.CountryName;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
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

        if(user.isConnected() == true){
            throw new Exception("Already connected");
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
        }
        if(user.getCountry().getCountryName().equals(countryName1)){
            return user;
        }

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        ServiceProvider serviceProviderWithLowestId = null;
        Integer lowestId = null;
        for(ServiceProvider serviceProvider:serviceProviderList){
            List<Country> countryList = serviceProvider.getCountryList();
            for(Country country:countryList){
                if(country.getCode().equals(countryName1.toCode())){
                    if(serviceProviderWithLowestId == null || (lowestId > serviceProvider.getId())){
                        serviceProviderWithLowestId = serviceProvider;
                        lowestId = serviceProviderWithLowestId.getId();
                    }
                }
            }
        }

        if(serviceProviderWithLowestId == null){
            throw new Exception("Unable to connect");
        }

        Connection connection = new Connection();
        connection.setServiceProvider(serviceProviderWithLowestId);
        connection.setUser(user);
        user.setConnected(true);
        List<Connection> connectionList = user.getConnectionList();
        connectionList.add(connection);
        user.setConnectionList(connectionList);
        user.setMaskedIp(new String(countryName1.toCode() + "." + serviceProviderWithLowestId.getId() + user.getId()));
        connection.setServiceProvider(serviceProviderWithLowestId);
        List<Connection> connections =  serviceProviderWithLowestId.getConnectionList();
       connections.add(connection);
       serviceProviderWithLowestId.setConnectionList(connections);
        userRepository2.save(user);
        serviceProviderRepository2.save(serviceProviderWithLowestId);

        return user;

    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user;
        try{
            user = userRepository2.findById(userId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        if(!user.isConnected()){
            throw new Exception("Already disconnected");
        }
        user.setConnected(false);
        user.setMaskedIp(null);
        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User receiver;
        try{
            receiver = userRepository2.findById(receiverId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        CountryName revceiverCountryName = null;

        if(receiver.isConnected()){
            String maskedCode = receiver.getMaskedIp().substring(0,3);
            if(maskedCode.equals("001")){
                revceiverCountryName = CountryName.IND;
            }else if(maskedCode.equals("002")){
                revceiverCountryName = CountryName.USA;
            }else if(maskedCode.equals("003")){
                revceiverCountryName = CountryName.AUS;
            }else if(maskedCode.equals("004")){
                revceiverCountryName = CountryName.CHI;
            }else if(maskedCode.equals("005")){
                revceiverCountryName = CountryName.JPN;
            }
        }else{
            revceiverCountryName = receiver.getCountry().getCountryName();
        }


        User sender;
        try{
            sender = userRepository2.findById(senderId).get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        User user;
        try{
            user = connect(senderId,revceiverCountryName.toString());
        }catch (Exception e){
            throw new Exception("Cannot establish communication");
        }
        return user;
    }
}

package com.driver.Service;

import com.driver.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User register(String username, String password, String countryName) throws Exception;
    public User subscribe(Integer userId, Integer serviceProviderId) throws Exception;
}

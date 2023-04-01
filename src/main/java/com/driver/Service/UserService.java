package com.driver.Service;

import com.driver.Entity.User;

public interface UserService {
    public User register(String username, String password, String countryName);
    public User subscribe(Integer userId, Integer serviceProviderId) throws Exception;
}

package com.driver.Service;

import com.driver.Entity.User;

public interface ConnectionService {
    public User connect(int userId, String countryName) throws Exception;
    public User disconnect(int userId) throws Exception;
    public User communicate(int senderId, int receiverId) throws Exception;
}

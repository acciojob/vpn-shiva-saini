package com.driver.Service;

import com.driver.Entity.Admin;
import com.driver.Entity.ServiceProvider;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    public Admin register(String username, String password);
    public Admin addServiceProvider(int adminId, String providerName) throws Exception;

    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception;
}

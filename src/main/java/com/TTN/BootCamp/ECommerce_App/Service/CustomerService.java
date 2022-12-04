package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
public interface CustomerService {

    public CustomerResponseDTO showCustomerProfile(String email, Locale locale) throws IOException;

    public String updateProfile(String email, CustomerUpdateDTO customerDTO, Locale locale);

    public String updatePassword(String email, String password, Locale locale);

    public String updateAddress(String email, AddressUpdateDTO addressDTO, long id, Locale locale);

    public List<Address> showCustomerAddresses(String email, Locale locale);

    public String addAddress(String email, AddressDTO addressDTO, Locale locale);

    public String deleteAddress(String email, long id, Locale locale);
}

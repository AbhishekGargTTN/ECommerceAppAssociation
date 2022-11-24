package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public CustomerDTO showCustomerProfile(String email);

    public String updateProfile(String email, CustomerUpdateDTO customerDTO);

    public String updatePassword(String email, String password);

    public String updateAddress(String email, AddressUpdateDTO addressDTO, long id);

    public List<Address> showCustomerAddresses(String email);

    public String addAddress(String email, AddressDTO addressDTO);

    public String deleteAddress(String email, long id);
}

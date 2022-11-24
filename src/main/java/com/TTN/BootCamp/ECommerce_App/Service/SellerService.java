package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface SellerService {

    public SellerDTO showSellerProfile(String email);

    public String updateProfile(String email, SellerUpdateDTO sellerDTO);

    public String updatePassword(String email, String password);

    public String updateAddress(String email, AddressUpdateDTO addressDTO);
}

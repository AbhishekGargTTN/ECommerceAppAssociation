package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SellerService {

    public SellerDTO showSellerProfile(String email) throws IOException;

    public String updateProfile(String email, SellerUpdateDTO sellerDTO);

    public String updatePassword(String email, String password);

    public String updateAddress(String email, AddressUpdateDTO addressDTO);
}

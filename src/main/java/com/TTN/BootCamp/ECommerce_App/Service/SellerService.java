package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Locale;

@Service
public interface SellerService {

    public SellerResponseDTO showSellerProfile(String email, Locale locale) throws IOException;

    public String updateProfile(String email, SellerUpdateDTO sellerDTO, Locale locale);

    public String updatePassword(String email, String password, Locale locale);

    public String updateAddress(String email, AddressUpdateDTO addressDTO, Locale locale);
}

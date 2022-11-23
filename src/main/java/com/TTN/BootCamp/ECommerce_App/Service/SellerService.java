package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SellerService {

    public SellerDTO showSellerProfile(String email);

    public String updateProfile(String email, SellerDTO sellerDTO);
}

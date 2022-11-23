package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Service.SellerService;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    SellerService sellerService;

    @GetMapping(path="/profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<SellerDTO> viewProfile(Authentication authentication){

        logger.info("SellerController: viewProfile started execution");
        SellerDTO seller = sellerService.showSellerProfile(authentication.getName());
        logger.info("SellerController: viewProfile ended execution ");
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PatchMapping(path = "/update_profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> updateProfile(Authentication authentication
            ,@RequestBody @Valid SellerDTO sellerDTO){
        logger.info("SellerController: updateProfile started execution");
        String response = sellerService.updateProfile(authentication.getName(), sellerDTO);
        logger.info("SellerController: updateProfile ended execution ");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

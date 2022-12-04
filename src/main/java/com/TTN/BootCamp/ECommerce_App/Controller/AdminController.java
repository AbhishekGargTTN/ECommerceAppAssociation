package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.Service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;


    @GetMapping(path="/customers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CustomerResponseDTO>>
    getAllCustomers(@RequestParam(defaultValue = "0") Integer pageNo
            ,@RequestParam(defaultValue = "10") Integer pageSize
            ,@RequestParam(defaultValue = "id") String sortBy){
        logger.info("AdminController: getAllCustomers started execution");
        return new ResponseEntity<>(adminService.listAllCustomers(pageNo, pageSize, sortBy), HttpStatus.OK);
    }


    @GetMapping(path="/sellers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SellerResponseDTO>>
    getAllSellers(@RequestParam(defaultValue = "0") Integer pageNo
            ,@RequestParam(defaultValue = "10") Integer pageSize
            ,@RequestParam(defaultValue = "id") String sortBy){

        logger.info("AdminController: getAllSellers started execution");
        List<SellerResponseDTO> sellers = adminService.listAllSellers(pageNo, pageSize, sortBy);
        logger.info("AdminController: getAllCustomers ended execution");
        return new ResponseEntity<>(sellers,HttpStatus.OK);
    }


    @PatchMapping(path="/activate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>  activateUser(@RequestParam("id") Long id){
        logger.info("AdminController: activateUser started execution");
        logger.info("AdminController: activateUser trying to activate user: "+ id);
        Locale locale = LocaleContextHolder.getLocale();
        String response = adminService.activateUser(id,locale);
        logger.info("AdminController: activateUser ended execution with response : "+ response);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

    @PatchMapping(path="/deactivate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>  deactivateUser(@RequestParam("id") Long id){
        logger.info("AdminController: deactivateUser started execution");
        logger.info("AdminController: deactivateUser trying to deactivate user: "+ id);
        Locale locale = LocaleContextHolder.getLocale();
        String response = adminService.deactivateUser(id, locale);
        logger.info("AdminController: deactivateUser ended execution with response : "+ response);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

    @PatchMapping(path="/unlock_user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>  unlockUser(@RequestParam("id") Long id){
        logger.info("AdminController: deactivateUser started execution");
        Locale locale = LocaleContextHolder.getLocale();
        String response = adminService.unlockUser(id, locale);
        logger.info("AdminController: deactivateUser ended execution with response : "+ response);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }
}

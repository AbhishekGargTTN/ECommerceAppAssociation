package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.SellerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.ResourceNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Exception.UserNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.AddressRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.SellerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.SellerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Locale;

import static com.TTN.BootCamp.ECommerce_App.Config.FilterProperties.getNullPropertyNames;

@Component
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    MailService mailService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MessageSource messageSource;

    public SellerResponseDTO showSellerProfile(String email, Locale locale) throws IOException {

        User user= userRepo.findUserByEmail(email);

        if(user == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound",null,locale));
        }
        Seller seller = user.getSeller();
        if(seller == null){
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound",null,locale));
        }
        SellerResponseDTO sellerDTO  = new SellerResponseDTO();

        String fullName;
        if (user.getMiddleName() == null) {
            fullName = user.getFirstName() + " " + user.getLastName();
        } else {
            fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        }
        sellerDTO.setFullName(fullName);
        sellerDTO.setId(user.getId());
        sellerDTO.setEmail(user.getEmail());
        sellerDTO.setActive(user.isActive());
        sellerDTO.setCompanyContact(seller.getCompanyContact());
        sellerDTO.setCompanyName(seller.getCompanyName());
        sellerDTO.setGst(seller.getGst());

        String path= "images/"+user.getId()+".jpg";
        var imgFile = new ClassPathResource(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        sellerDTO.setImage(bytes);
        sellerDTO.setAddress(seller.getAddress());

        return sellerDTO;
    }

    public String updateProfile(String email, SellerUpdateDTO sellerUpdateDTO, Locale locale){
//        logger.info("SellerService::updateProfile execution started.");

        User user = userRepo.findUserByEmail(email);
        Seller seller = user.getSeller();
        Address address = seller.getAddress();
//        logger.debug("SellerService::updateProfile fetching details from request");

        AddressUpdateDTO addressDTO = sellerUpdateDTO.getAddress();
//        logger.debug("SellerService::updateProfile updating details");

        BeanUtils.copyProperties(addressDTO, address, FilterProperties.getNullPropertyNames(addressDTO));

        address.setSeller(seller);
        addressRepo.save(address);

        BeanUtils.copyProperties(sellerUpdateDTO, user, FilterProperties.getNullPropertyNames(sellerUpdateDTO));
        BeanUtils.copyProperties(sellerUpdateDTO, seller, FilterProperties.getNullPropertyNames(sellerUpdateDTO));

        userRepo.save(user);
        sellerRepo.save(seller);
//        logger.info("SellerService::updateProfile execution ended.");
        return messageSource.getMessage("api.response.updateSuccess",null, Locale.ENGLISH);
    }

    public String updatePassword(String email, String password, Locale locale){
        User user = userRepo.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        mailService.sendSuccessfulChangeMail(user, locale);
        return messageSource.getMessage("api.response.updateSuccess",null,locale);
    }


    public String updateAddress(String email, AddressUpdateDTO addressDTO, Locale locale) {
        User user= userRepo.findUserByEmail(email);
        Address address = addressRepo.findById(user.getSeller().getAddress().getId()).orElseThrow(
                () ->  new ResourceNotFoundException(
                        messageSource.getMessage("api.error.addressNotFound",null,locale)
                )
        );


        BeanUtils.copyProperties(addressDTO, address, getNullPropertyNames(addressDTO));
        addressRepo.save(address);
        return messageSource.getMessage("api.response.updateSuccess",null,locale);
    }
}
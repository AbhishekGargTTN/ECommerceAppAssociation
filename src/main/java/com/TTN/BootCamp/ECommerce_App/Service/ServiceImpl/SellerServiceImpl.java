package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
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
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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

    public SellerDTO showSellerProfile(String email, Locale locale) throws IOException {

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
        SellerDTO sellerDTO  = new SellerDTO();

        sellerDTO.setFirstName(user.getFirstName());
        sellerDTO.setMiddleName(user.getMiddleName());
        sellerDTO.setLastName(user.getLastName());
        sellerDTO.setEmail(user.getEmail());
        sellerDTO.setActive(user.isActive());
        sellerDTO.setCompanyContact(seller.getCompanyContact());
        sellerDTO.setCompanyName(seller.getCompanyName());
        sellerDTO.setGst(seller.getGst());

        String path= "images/"+user.getId()+".jpg";
        var imgFile = new ClassPathResource(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        sellerDTO.setImage(bytes);

        AddressDTO address= new AddressDTO();
        address.setCity(seller.getAddress().getCity());
        address.setState(seller.getAddress().getState());
        address.setCountry(seller.getAddress().getCountry());
        address.setAddressLine(seller.getAddress().getAddressLine());
        address.setZipCode(seller.getAddress().getZipCode());
        address.setLabel(seller.getAddress().getLabel());
        sellerDTO.setAddress(address);

        return sellerDTO;
    }

    public String updateProfile(String email, SellerUpdateDTO sellerDTO, Locale locale){
        User user = userRepo.findUserByEmail(email);
        if(user == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound",null,locale));
        }
        Seller seller = user.getSeller();
        if(seller == null){
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound",null,locale));
        }
        Address address = seller.getAddress();
        if(address == null){
            throw new ResourceNotFoundException(
                    messageSource.getMessage("api.error.resourceNotFound",null,locale));
        }

        AddressUpdateDTO addressDTO = sellerDTO.getAddress();

        BeanUtils.copyProperties(addressDTO, address, getNullPropertyNames(addressDTO));

        address.setSeller(seller);
        addressRepo.save(address);

        BeanUtils.copyProperties(sellerDTO, user, getNullPropertyNames(sellerDTO));
        BeanUtils.copyProperties(sellerDTO, seller, getNullPropertyNames(sellerDTO));

        userRepo.save(user);
        sellerRepo.save(seller);
        return messageSource.getMessage("api.response.updateSuccess",null,locale);
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

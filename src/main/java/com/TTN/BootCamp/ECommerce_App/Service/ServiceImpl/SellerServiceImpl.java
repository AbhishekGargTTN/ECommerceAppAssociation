package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.UserNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.AddressRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.SellerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.SellerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    AddressRepo addressRepo;

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }

    public SellerDTO showSellerProfile(String email){

        User user= userRepo.findUserByEmail(email);

        if(user == null) {
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
        }
        Seller seller = user.getSeller();
        if(seller == null){
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
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

    public String updateProfile(String email, SellerDTO sellerDTO){
        User user = userRepo.findUserByEmail(email);
        Seller seller = user.getSeller();
        Address address = seller.getAddress();

        // extract addressDTO object from the incoming request
        AddressDTO addressDTO = sellerDTO.getAddress();

        //partial update of address - ignoring null properties received in request
        BeanUtils.copyProperties(addressDTO, address, getNullPropertyNames(addressDTO));

        // saving updates
        address.setSeller(seller);
        addressRepo.save(address);

        // partial updates
        BeanUtils.copyProperties(sellerDTO, user, getNullPropertyNames(sellerDTO));
        BeanUtils.copyProperties(sellerDTO, seller, getNullPropertyNames(sellerDTO));
        // saving updates
        userRepo.save(user);
        sellerRepo.save(seller);
        return "User profile updated successfully";

    }
}

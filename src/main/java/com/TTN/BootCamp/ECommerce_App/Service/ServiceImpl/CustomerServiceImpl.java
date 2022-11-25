package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.UserNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.AddressRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CustomerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.CustomerService;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.*;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    MailService mailService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
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

    public CustomerDTO showCustomerProfile(String email) throws IOException {

        User user= userRepo.findUserByEmail(email);

        if(user == null) {
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
        }
        Customer customer = user.getCustomer();
        if(customer == null){
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
        }
        CustomerDTO customerDTO  = new CustomerDTO();

        customerDTO.setFirstName(user.getFirstName());
        customerDTO.setMiddleName(user.getMiddleName());
        customerDTO.setLastName(user.getLastName());
        customerDTO.setEmail(user.getEmail());
        customerDTO.setActive(user.isActive());
        customerDTO.setContact(user.getCustomer().getContact());

        String path= "images/"+user.getId()+".jpg";
        var imgFile = new ClassPathResource(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        customerDTO.setImage(bytes);

//        AddressDTO address= new AddressDTO();
//        address.setCity(seller.getAddress().getCity());
//        address.setState(seller.getAddress().getState());
//        address.setCountry(seller.getAddress().getCountry());
//        address.setAddressLine(seller.getAddress().getAddressLine());
//        address.setZipCode(seller.getAddress().getZipCode());
//        address.setLabel(seller.getAddress().getLabel());
//        customerDTO.setAddress(address);

        return customerDTO;
    }

    public String updateProfile(String email, CustomerUpdateDTO customerDTO){
        User user = userRepo.findUserByEmail(email);
        Customer customer = user.getCustomer();

        // partial updates
        BeanUtils.copyProperties(customerDTO, user, getNullPropertyNames(customerDTO));
        BeanUtils.copyProperties(customerDTO, customer, getNullPropertyNames(customerDTO));
        // saving updates
        userRepo.save(user);
        customerRepo.save(customer);
        return "User profile updated successfully";
    }

    public String updatePassword(String email, String password){
        User user = userRepo.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        mailService.sendSuccessfulChangeMail(user);
        return "Password updated successfully";
    }


    public String updateAddress(String email, AddressUpdateDTO addressDTO, long id) {
        User user= userRepo.findUserByEmail(email);
        List<Address> addresses = user.getCustomer().getAddresses();
        Address address= addressRepo.findByAddress_Id(id);
//                addressRepo.findById(addressId).orElseThrow(
//                () ->  new ResourceNotFoundException(
//                        messageSource.getMessage("api.error.addressNotFound",null,Locale.ENGLISH)
//                )
//        );

        if(addresses.contains(address)) {
            BeanUtils.copyProperties(addressDTO, address, getNullPropertyNames(addressDTO));
            addressRepo.save(address);
//        return messageSource.getMessage("api.response.addressChanged",null,Locale.ENGLISH);

            return "Address updated Successfully";
        }
        else {
            return "Address not found";
        }
    }

    public List<Address> showCustomerAddresses(String email){

        User user= userRepo.findUserByEmail(email);

        if(user == null) {
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
        }
        Customer customer = user.getCustomer();
        if(customer == null){
            throw new UserNotFoundException("User not found"
//                    messageSource.getMessage("api.error.userNotFound",null,Locale.ENGLISH)
            );
        }

        List<Address> addresses= customer.getAddresses();
        System.out.println(addresses);

//        AddressDTO address= new AddressDTO();
//        address.setCity(seller.getAddress().getCity());
//        address.setState(seller.getAddress().getState());
//        address.setCountry(seller.getAddress().getCountry());
//        address.setAddressLine(seller.getAddress().getAddressLine());
//        address.setZipCode(seller.getAddress().getZipCode());
//        address.setLabel(seller.getAddress().getLabel());
//        customerDTO.setAddress(address);

        return addresses;
    }

    public String addAddress(String email, AddressDTO addressDTO) {
        User user= userRepo.findUserByEmail(email);
        Customer customer= user.getCustomer();
//        Address address = user.getSeller().getAddress();
//                addressRepo.findById(addressId).orElseThrow(
//                () ->  new ResourceNotFoundException(
//                        messageSource.getMessage("api.error.addressNotFound",null,Locale.ENGLISH)
//                )
//        );

        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address.setLabel(addressDTO.getLabel());
        address.setCustomer(customer);

//        BeanUtils.copyProperties(addressDTO, address, getNullPropertyNames(addressDTO));
        addressRepo.save(address);
//        return messageSource.getMessage("api.response.addressChanged",null,Locale.ENGLISH);

        return "Address updated Successfully";
    }

    public String deleteAddress(String email, long id) {
        User user= userRepo.findUserByEmail(email);
        List<Address> addresses = user.getCustomer().getAddresses();
        Address address= addressRepo.findByAddress_Id(id);
//                addressRepo.findById(addressId).orElseThrow(
//                () ->  new ResourceNotFoundException(
//                        messageSource.getMessage("api.error.addressNotFound",null,Locale.ENGLISH)
//                )
//        );

        if(addresses.contains(address)) {
            addressRepo.delete(address);
//        return messageSource.getMessage("api.response.addressChanged",null,Locale.ENGLISH);

            return "Address Deleted Successfully";
        }
        else {
            return "Address not found";
        }
    }
}

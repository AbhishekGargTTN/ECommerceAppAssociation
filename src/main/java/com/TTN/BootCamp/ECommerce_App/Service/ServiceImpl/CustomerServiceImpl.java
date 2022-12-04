package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.Controller.CustomerController;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.AddressUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CustomerUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.ResourceNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Exception.UserNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.AddressRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CustomerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.CustomerService;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
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

    @Autowired
    MessageSource messageSource;

    public CustomerResponseDTO showCustomerProfile(String email, Locale locale) throws IOException {

        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound", null, locale));
        }
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound", null, locale));
        }
        CustomerResponseDTO customerDTO = new CustomerResponseDTO();

        String fullName;
        if (user.getMiddleName() == null) {
            fullName = user.getFirstName() + " " + user.getLastName();
        } else {
            fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        }
        customerDTO.setFullName(fullName);
        customerDTO.setId(user.getCustomer().getId());
        customerDTO.setEmail(user.getEmail());
        customerDTO.setActive(user.isActive());
        customerDTO.setContact(user.getCustomer().getContact());

        String path = "images/" + user.getId() + ".jpg";
        var imgFile = new ClassPathResource(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        customerDTO.setImage(bytes);

        return customerDTO;
    }

    public String updateProfile(String email, CustomerUpdateDTO customerDTO, Locale locale) {
        User user = userRepo.findUserByEmail(email);
        Customer customer = user.getCustomer();

        BeanUtils.copyProperties(customerDTO, user, FilterProperties.getNullPropertyNames(customerDTO));
        BeanUtils.copyProperties(customerDTO, customer, FilterProperties.getNullPropertyNames(customerDTO));

        userRepo.save(user);
        customerRepo.save(customer);
        return messageSource.getMessage("api.response.updateSuccess", null, locale);
    }

    public String updatePassword(String email, String password, Locale locale) {
        User user = userRepo.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        mailService.sendSuccessfulChangeMail(user, locale);
        return messageSource.getMessage("api.response.updateSuccess", null, locale);
    }


    public String updateAddress(String email, AddressUpdateDTO addressDTO, long id, Locale locale) {
        User user = userRepo.findUserByEmail(email);
        List<Address> addresses = user.getCustomer().getAddresses();
        Address address = addressRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        messageSource.getMessage("api.error.addressNotFound", null, locale)));

        if (addresses.contains(address)) {
            BeanUtils.copyProperties(addressDTO, address, FilterProperties.getNullPropertyNames(addressDTO));
            addressRepo.save(address);
            return messageSource.getMessage("api.response.updateSuccess", null, locale);

        } else {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("api.error.addressNotFound", null, locale));
        }
    }

    public List<Address> showCustomerAddresses(String email, Locale locale) {

        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound", null, locale));
        }
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new UserNotFoundException(
                    messageSource.getMessage("api.error.userNotFound", null, locale));
        }

        return customer.getAddresses();
    }

    public String addAddress(String email, AddressDTO addressDTO, Locale locale) {
        User user = userRepo.findUserByEmail(email);
        Customer customer = user.getCustomer();

        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address.setLabel(addressDTO.getLabel());
        address.setCustomer(customer);

        addressRepo.save(address);

        return messageSource.getMessage("api.response.addedSuccess", null, locale);
    }

    public String deleteAddress(String email, long id, Locale locale) {
        User user = userRepo.findUserByEmail(email);
        Customer loggedCustomer = user.getCustomer();
        Optional<Address> address = addressRepo.findById(id);
        Address reqdAddress = address.get();

        if (address.isPresent()) {
            if (reqdAddress.getCustomer() == loggedCustomer) {
                logger.debug("CustomerService::deleteAddress deleting the address");
                addressRepo.delete(reqdAddress);
                logger.info("CustomerService::deleteAddress execution ended.");
                return messageSource.getMessage("api.response.addressDelete", null, locale);
            }
            logger.error("CustomerService::deleteAddress exception occurred while deleting");
            throw new ResourceNotFoundException(messageSource.getMessage("api.error.addressNotFound", null, locale));

        }
        logger.error("CustomerService::deleteAddress exception occurred while deleting");
        throw new ResourceNotFoundException(messageSource.getMessage("api.error.addressNotFound", null, locale));
    }
}
package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
//    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    SecureTokenRepo secureTokenRepo;
    @Autowired
    MailService mailService;
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);
        return user;
    }

    public Customer addCustomerDetails(CustomerDTO customerDTO, String role, String siteURL)  {
        System.out.println(customerDTO.toString());
        User newUser = new User();
        newUser.setEmail(customerDTO.getEmail());
//        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(customerDTO.getPassword());
        newUser.setFirstName(customerDTO.getFirstName());
        newUser.setMiddleName(customerDTO.getMiddleName());
        newUser.setLastName(customerDTO.getLastName());
//        newUser.setActive(false);

        Role newrole= new Role();
        newrole= roleRepo.findByRole(role);
        newUser.setRole(newrole);

        Customer customer = new Customer();
        customer.setContact(customerDTO.getContact());
        customer.setUser(newUser);

        customerDTO.getAddresses().forEach(addressDTO -> {
            Address address= new Address();
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setAddressLine(addressDTO.getAddressLine());
            address.setZipCode(addressDTO.getZipCode());
            address.setCountry(addressDTO.getCountry());
            address.setLabel(addressDTO.getLabel());
            address.setCustomer(customer);

            addressRepo.save(address);

        });

//
//        mailService.register();
//        mailService.sendEmailVerification(customerDTO,siteURL);

//        List<Address> addresses= new ArrayList<>();

//        customerDTO.getAddresses().forEach(a-> );

//        int numberOfAddresses= customerDTO.getAddresses().size();


//        addresses= customerDTO.getAddresses();
//        Address address1= new Address();
//        address1.setCity(userDTO.getCity());
//        address1.setState(userDTO.getState());
//        address1.setZipCode(userDTO.getZipCode());
//        address1.setAddressLine(userDTO.getAddressLine());
//        address1.setLabel(userDTO.getLabel());
//        address1.setCountry(userDTO.getCountry());
//        address1.setCustomer(customer);
//        addresses.add(address1);
//
//        Address address2= new Address();
//        address2.setCity(userDTO.getCity());
//        address2.setState(userDTO.getState());
//        address2.setZipCode(userDTO.getZipCode());
//        address2.setAddressLine(userDTO.getAddressLine());
//        address2.setLabel(userDTO.getLabel());
//        address2.setCountry(userDTO.getCountry());
//        address2.setCustomer(customer);
//        addresses.add(address2);

//        customer.setAddressLine1(userDTO.getAddressLine1());
//        customer.setAddressLine2(userDTO.getAddressLine2());
//        customer.setCity(userDTO.getCity());
//        customer.setCountry(userDTO.getCountry());
//        customer.setUser(newUser);
//        customer.setAddresses(addresses);
        customerRepo.save(customer);
        userRepo.save(newUser);

        SecureToken secureToken=new SecureToken(newUser);
        secureTokenRepo.save(secureToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("abhishekgarg919@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/confirm-account?token="+secureToken.getSecureToken());

        mailService.sendEmail(mailMessage);
        System.out.println("email sent");

        return customer;
    }

    public Seller addSellerDetails(SellerDTO sellerDTO, String role){

        System.out.println(sellerDTO.toString());
        User newUser = new User();
        newUser.setEmail(sellerDTO.getEmail());
//        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(sellerDTO.getPassword());
        newUser.setFirstName(sellerDTO.getFirstName());
        newUser.setMiddleName(sellerDTO.getMiddleName());
        newUser.setLastName(sellerDTO.getLastName());
//        newUser.setActive(false);

        Role newrole= new Role();
        newrole= roleRepo.findByRole(role);
        newUser.setRole(newrole);

        Seller seller = new Seller();
        seller.setCompanyName(sellerDTO.getCompanyName());
        seller.setGst(sellerDTO.getGst());
        seller.setCompanyContact(sellerDTO.getCompanyContact());
        seller.setUser(newUser);

        Address address= new Address();
        address.setCity(sellerDTO.getAddress().getCity());
        address.setState(sellerDTO.getAddress().getState());
        address.setAddressLine(sellerDTO.getAddress().getAddressLine());
        address.setZipCode(sellerDTO.getAddress().getZipCode());
        address.setCountry(sellerDTO.getAddress().getCountry());
        address.setLabel(sellerDTO.getAddress().getLabel());
        address.setSeller(seller);

//        seller.setAddress(address);
        addressRepo.save(address);
        sellerRepo.save(seller);
        userRepo.save(newUser);

        return seller;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

//    public Optional<User> activateRegistration(String key) {
//        return secureTokenRepo.findOneByActivationKey(key)
//                .map(user -> {
//                    user.setActive(true);
//                    secureTokenRepo.deleteByActivationKey(key);
//                    userRepo.save(user);
//                    return user;
//                });
//    }
}

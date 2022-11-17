package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Customer addCustomerDetails(CustomerDTO customerDTO, String role) {
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
}

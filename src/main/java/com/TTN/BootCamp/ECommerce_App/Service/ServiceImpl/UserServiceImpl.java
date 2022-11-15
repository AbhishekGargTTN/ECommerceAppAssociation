package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.Role;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.CustomerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.RoleRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
//    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    CustomerRepo customerRepo;
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

    public User registerUser(UserDTO userDTO, String password) {

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
//        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(userDTO.getPassword());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setMiddleName(userDTO.getMiddleName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setActive(false);

//        Role role= new Role();
//        role= roleRepo.findByRole("CUSTOMER");
//        newUser.addRole(role);
        Customer customer = new Customer();

        customer.setContact(userDTO.getContact());
        customer.setUser(newUser);
        Set<Address> addresses= new HashSet<>();
        Address address1= new Address();
        address1.setCity("Shastri Nagar");
        address1.setState("Delhi");
        address1.setZipCode(110052);
        address1.setAddressLine("749");
        address1.setLabel("Home");
        address1.setCountry("India");
        address1.setCustomer(customer);
        addresses.add(address1);

//        Address address2= new Address();
//        address2.setCity("Rohini");
//        address2.setState("Delhi");
//        address2.setZipCode(110039);
//        address2.setAddressLine("1234");
//        address2.setLabel("Office");
//        address2.setCountry("India");
//        address2.setCustomer(customer);
//        addresses.add(address2);
//        customer.setAddressLine1(userDTO.getAddressLine1());
//        customer.setAddressLine2(userDTO.getAddressLine2());
//        customer.setCity(userDTO.getCity());
//        customer.setCountry(userDTO.getCountry());
//        customer.setUser(newUser);
        customer.setAddresses(addresses);
        userRepo.save(newUser);
        customerRepo.save(customer);
        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}

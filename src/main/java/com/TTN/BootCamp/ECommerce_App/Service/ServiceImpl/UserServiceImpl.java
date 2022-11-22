package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Exception.LinkExpiredException;
import com.TTN.BootCamp.ECommerce_App.Exception.PasswordDoNotMatchException;
import com.TTN.BootCamp.ECommerce_App.Exception.UserAlreadyExistsException;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
//    public User createUser(UserDTO userDTO) {
//        User user = new User();
//        user.setFirstName(userDTO.getFirstName());
//        user.setMiddleName(userDTO.getMiddleName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//
//        userRepo.save(user);
//        return user;
//    }

    public String addCustomerDetails(CustomerDTO customerDTO, String role) {

//        logger.info("RegistrationService::createCustomer execution started.");

        // checking if username(email) already exists
        String providedEmail = customerDTO.getEmail();
        User existingUser = userRepo.findUserByEmail(providedEmail);
        if (existingUser != null) {
//            logger.error("Exception occurred while persisting customer to the database");
            throw new UserAlreadyExistsException("User with the provided email already exists.");
        }
        // checking if password and reEnterPassword match
        else if (!(customerDTO.getPassword().equals(customerDTO.getConfirmPassword()))) {
//            logger.error("Exception occurred while persisting customer to the database");
            throw new PasswordDoNotMatchException("Passwords do not match.");
        } else {

//            logger.debug("RegistrationService::createCustomer persisting customer to the database and sending email");
            User newUser = new User();
            newUser.setEmail(customerDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
            newUser.setFirstName(customerDTO.getFirstName());
            newUser.setMiddleName(customerDTO.getMiddleName());
            newUser.setLastName(customerDTO.getLastName());

            Role newrole = roleRepo.findByRole(role);
            newUser.setRole(newrole);

            Customer customer = new Customer();
            customer.setContact(customerDTO.getContact());
            customer.setUser(newUser);

            customerDTO.getAddresses().forEach(addressDTO -> {
                Address address = new Address();
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setAddressLine(addressDTO.getAddressLine());
                address.setZipCode(addressDTO.getZipCode());
                address.setCountry(addressDTO.getCountry());
                address.setLabel(addressDTO.getLabel());
                address.setCustomer(customer);

                addressRepo.save(address);

            });

            customerRepo.save(customer);
            userRepo.save(newUser);

            mailService.sendActivationMail(newUser);

            return "Customer Registered Successfully";
        }
    }
    public String addSellerDetails(SellerDTO sellerDTO, String role){

//        logger.info("RegistrationService::createSeller execution started.");

        String providedEmail = sellerDTO.getEmail();
        User existingUser = userRepo.findUserByEmail(providedEmail);
        if(existingUser!=null){
//            logger.error("Exception occurred while persisting seller to the database");
            throw new UserAlreadyExistsException("User with the provided email already exists.");
        }
        // checking if password and reEnterPassword match
        else if( !(sellerDTO.getPassword().equals(sellerDTO.getConfirmPassword())) ){
//            logger.error("Exception occurred while persisting seller to the database");
            throw new PasswordDoNotMatchException("Passwords do not match");
        }
        else {
            User newUser = new User();
            newUser.setEmail(sellerDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
            newUser.setFirstName(sellerDTO.getFirstName());
            newUser.setMiddleName(sellerDTO.getMiddleName());
            newUser.setLastName(sellerDTO.getLastName());

            Role newrole = roleRepo.findByRole(role);
            newUser.setRole(newrole);

            String providedGst = sellerDTO.getGst();
            Seller existingGst = sellerRepo.findByGst(providedGst);

            String providedCompanyName = sellerDTO.getCompanyName();
            Seller existingCompanyName = sellerRepo.findByCompanyNameIgnoreCase(providedCompanyName);
            if (existingGst != null) {
//                logger.error("Exception occurred while persisting seller to the database");
                throw new UserAlreadyExistsException("Seller with the provided GST number already exists.");
            } else if (existingCompanyName != null) {
//                logger.error("Exception occurred while persisting seller to the database");
                throw new UserAlreadyExistsException("Seller with the provided Company Name already exists.");
            } else {
//                logger.debug("RegistrationService::createSeller persisting seller to the database");
                Seller seller = new Seller();
                seller.setCompanyName(sellerDTO.getCompanyName());
                seller.setGst(sellerDTO.getGst());
                seller.setCompanyContact(sellerDTO.getCompanyContact());
                seller.setUser(newUser);

                Address address = new Address();
                address.setCity(sellerDTO.getAddress().getCity());
                address.setState(sellerDTO.getAddress().getState());
                address.setAddressLine(sellerDTO.getAddress().getAddressLine());
                address.setZipCode(sellerDTO.getAddress().getZipCode());
                address.setCountry(sellerDTO.getAddress().getCountry());
                address.setLabel(sellerDTO.getAddress().getLabel());
                address.setSeller(seller);


                addressRepo.save(address);
                sellerRepo.save(seller);
                userRepo.save(newUser);

            }
        }
                return "Seller Registered Successfully";

    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


    public String activateUserAccount(String token){

//        logger.info("NotificationTokenService::activateUserAccount execution started.");
//
//        logger.debug("NotificationTokenService::activateUserAccount activating account");

        SecureToken secureToken = secureTokenRepo.findBySecureToken(token);
        // checks if provided token exists
        if(secureToken!=null){
            User user = userRepo.findUserByEmail(secureToken.getUser().getEmail());
            // check to see whether token has expired
            // Expires after 3hrs
            if(secureToken.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(3*60l))){
                // delete the token and trigger a new mail
                secureTokenRepo.delete(secureToken);
                mailService.sendActivationMail(user);
//                logger.error("Exception occurred while activating account");
                throw new LinkExpiredException("The link you followed has expired, " +
                        "a new activation mail has been sent to the registered email.\"");
            }
            else{
                // activate account, delete token & trigger a new mail notifying that account is active
                user.setActive(true);
                secureTokenRepo.delete(secureToken);
                userRepo.save(user);
                mailService.sendIsActivatedMail(user);

//                logger.debug("NotificationTokenService::activateUserAccount activated account");
//                logger.info("NotificationTokenService::activateUserAccount execution ended.");

                return "Account activated.";
            }
        }
        else {
//            logger.error("Exception occurred while activating account");
            throw new InvalidTokenException("Invalid token.") ;
        }
    }


    public String resendActivationMail(String email){

//        logger.info("NotificationTokenService::resendActivationMail execution started.");

//        logger.debug("NotificationTokenService::resendActivationMail resending mail");

        User user = userRepo.findUserByEmail(email);
        if(user==null){
//            logger.error("Exception occurred while resending the mail");
            throw new BadCredentialsException("Invalid Email.");
        } else{
            // check if account is already active
            if(user.isActive()){
//                logger.debug("NotificationTokenService::resendActivationMail account is already active");
//                logger.info("NotificationTokenService::resendActivationMail execution ended.");
                return "Account is already active.";
            }
            // if inactive, check if there is an existing activation token
            else{

                SecureToken token = secureTokenRepo.findByUser(user);
                // delete if activation token already exists & trigger activation mail
                if(token!=null){
                    secureTokenRepo.delete(token);
                    mailService.sendActivationMail(user);
                }
                // trigger activation mail
                else {
                    mailService.sendActivationMail(user);
                }
            }
        }
//        logger.debug("NotificationTokenService::resendActivationMail mail sent.");
//        logger.info("NotificationTokenService::resendActivationMail execution ended.");
        return "Activation mail has been sent to the provided email.";
    }

    public String forgotPassword(String email){
//        logger.info("NotificationTokenService::forgotPassword execution started.");

//        logger.debug("NotificationTokenService::forgotPassword sending mail");
        User user = userRepo.findUserByEmail(email);
        if(user==null){
//            logger.error("Exception occurred while sending the mail");
            throw new BadCredentialsException("Invalid Email.");
        } else{
            // check if account is active
            if(user.isActive()){
                SecureToken token = secureTokenRepo.findByUser(user);
                // delete if reset password token already exists & trigger reset password mail
                if(token!=null){
                    secureTokenRepo.delete(token);
                    mailService.sendForgotPasswordMail(user);
                }
                // trigger reset password mail
                else {
                    mailService.sendForgotPasswordMail(user);
                }
            }
            // in case account is inactive
            else{
//                logger.error("Exception occurred while sending the mail");
//                throw new AccountInActiveException("Account is in-active. Request cannot be processed.");

            }
        }
//        logger.debug("NotificationTokenService::forgotPassword mail sent");
//        logger.info("NotificationTokenService::forgotPassword execution ended.");
        return "A mail has been sent to the provided email.";
    }

    public String resetPassword(String token, String password, String confirmPassword){

//        logger.info("NotificationTokenService::resetPassword execution started.");

//        logger.debug("NotificationTokenService::resetPassword changing the password");
        SecureToken passwordToken = secureTokenRepo.findBySecureToken(token);
        // checks if provided token exists
        if(passwordToken!=null){
            User user = userRepo.findUserByEmail(passwordToken.getUser().getEmail());
            // check to see whether token has expired
            // Expires after 15 min
            if(passwordToken.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(15l))){
                // delete the token
                secureTokenRepo.delete(passwordToken);
//                logger.error("Exception occurred while changing the password");
//                throw new LinkExpiredException("Link has expired. Request cannot be processed further.") ;
            }
            else{
                // check if passwords match
                if(!(password.equals(confirmPassword))){
//                    logger.error("Exception occurred while changing the password");
//                    throw new PasswordDoNotMatchException("Passwords do not match.");
                }
                // change password
                user.setPassword(passwordEncoder.encode(password));
                userRepo.save(user);
                secureTokenRepo.delete(passwordToken);
                mailService.sendSuccessfulChangeMail(user);
//                logger.info("NotificationTokenService::resetPassword execution ended.");
                return "Password successfully changed.";
            }
        }
        else {
//            logger.error("Exception occurred while changing the password");
            throw new InvalidTokenException("Invalid token. Request cannot be processed further.");
        }
        return token;
    }
}

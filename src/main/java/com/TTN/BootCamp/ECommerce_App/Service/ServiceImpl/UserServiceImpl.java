package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CustomerDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.SellerDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Exception.InActiveAccountException;
import com.TTN.BootCamp.ECommerce_App.Exception.LinkExpiredException;
import com.TTN.BootCamp.ECommerce_App.Exception.PasswordDoNotMatchException;
import com.TTN.BootCamp.ECommerce_App.Exception.UserAlreadyExistsException;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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

    @Autowired
    MessageSource messageSource;

    @Value("${image.file.path}")
    private String FILE_PATH;

    public String addCustomerDetails(CustomerDTO customerDTO, String role, Locale locale) {

        logger.info("UserService: addCustomerDetails started execution");

        String providedEmail = customerDTO.getEmail();
        User existingUser = userRepo.findUserByEmail(providedEmail);
        if (existingUser != null) {
            logger.error("Exception occurred while persisting customer " +
                    "to the database-- Email already exist in database");
            throw new UserAlreadyExistsException(messageSource
                    .getMessage("api.error.userEmailExists",null, locale));
        }

        else if (!(customerDTO.getPassword().equals(customerDTO.getConfirmPassword()))) {
            logger.error("Exception occurred while persisting customer " +
                    "to the database-- password and confirm password does not match");
            throw new PasswordDoNotMatchException(messageSource
                    .getMessage("api.error.passwordDoNotMatch",null, locale));
        } else {

            logger.debug("UserService: addCustomerDetails persisting customer " +
                    "to the database and sending activation mail");
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

            mailService.sendActivationMail(newUser, locale);

            return messageSource
                    .getMessage("api.response.customerRegistered",null, locale);
        }
    }
    public String addSellerDetails(SellerDTO sellerDTO, String role, Locale locale){

        logger.info("UserService: addSellerDetails started execution");

        String providedEmail = sellerDTO.getEmail();
        User existingUser = userRepo.findUserByEmail(providedEmail);
        if(existingUser!=null){
            logger.error("Exception occurred while persisting seller " +
                    "to the database-- Email already exist in database");
            throw new UserAlreadyExistsException(messageSource
                    .getMessage("api.error.userEmailExists",null, locale));
        }
        // checking if password and reEnterPassword match
        else if( !(sellerDTO.getPassword().equals(sellerDTO.getConfirmPassword())) ){
            logger.error("Exception occurred while persisting seller " +
                    "to the database-- password and confirm password does not match");
            throw new PasswordDoNotMatchException(messageSource
                    .getMessage("api.error.passwordDoNotMatch",null, locale));
        }
        else {
            logger.debug("UserService: addSellerDetails persisting user details " +
                    "to the database");
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
                logger.error("Exception occurred while persisting seller " +
                        "to the database-- GST already exists in database");
                throw new UserAlreadyExistsException(messageSource
                        .getMessage("api.error.userGstExists",null, locale));
            } else if (existingCompanyName != null) {
                logger.error("Exception occurred while persisting seller " +
                        "to the database-- Company name already exists in database");
                throw new UserAlreadyExistsException(messageSource
                        .getMessage("api.error.userCompanyExists",null, locale));
            } else {
                logger.debug("UserService: addSellerDetails persisting seller to the database");
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
                mailService.sendAwaitingApprovalMail(newUser, locale);
            }
        }
                return messageSource
                        .getMessage("api.response.sellerRegistered",null, locale);

    }


    public String activateUserAccount(String token, Locale locale){

        logger.info("UserService: activateUserAccount started execution");
        logger.debug("UserService: activateUserAccount activating customer account");

        SecureToken secureToken = secureTokenRepo.findBySecureToken(token);

        if(secureToken!=null){
            User user = userRepo.findUserByEmail(secureToken.getUser().getEmail());

            if(secureToken.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(3*60L))){

                secureTokenRepo.delete(secureToken);
                mailService.sendActivationMail(user, locale);
                logger.error("Exception occurred while activating " +
                        "customer account-- link expired");
                throw new LinkExpiredException(messageSource
                        .getMessage("api.error.activationLinkExpired",null, locale));
            }
            else{

                user.setActive(true);
                secureTokenRepo.delete(secureToken);
                userRepo.save(user);
                mailService.sendIsActivatedMail(user, locale);

                logger.debug("UserService: activateUserAccount account activated");
                logger.info("UserService: activateUserAccount ended execution");

                return messageSource.getMessage("api.response.userAccountActivated",null, locale);
            }
        }
        else {
            logger.error("Exception occurred while activating account-- Invalid Token");
            throw new InvalidTokenException(
                    messageSource.getMessage("api.error.invalidToken",null, locale)) ;
        }
    }


    public String resendActivationMail(String email, Locale locale){

        logger.info("UserService: resendActivationMail started execution");
        logger.debug("UserService: resendActivationMail resending activation mail");

        User user = userRepo.findUserByEmail(email);
        if(user==null){
            logger.error("Exception occurred while resending the mail-- Invalid email");
            throw new BadCredentialsException(
                    messageSource.getMessage("api.error.invalidEmail",null, locale));
        } else{

            if(user.isActive()){
                logger.debug("UserService: resendActivationMail account is already active");
                logger.info("UserService: resendActivationMail ended execution");
                return messageSource.getMessage("api.response.userAlreadyActive",null, locale);
            }

            else{

                SecureToken token = secureTokenRepo.findByUser(user);

                if(token!=null){
                    secureTokenRepo.delete(token);
                    mailService.sendActivationMail(user, locale);
                }

                else {
                    mailService.sendActivationMail(user, locale);
                }
            }
        }
        logger.debug("UserService: resendActivationMail mail sent.");
        logger.info("UserService: resendActivationMail ended execution");
        return messageSource.getMessage("api.response.resendActivation",null, locale);
    }

    public String forgotPassword(String email, Locale locale){
        logger.info("UserService: forgotPassword started execution");

        logger.debug("UserService: forgotPassword sending password reset mail");
        User user = userRepo.findUserByEmail(email);
        if(user==null){
            logger.error("Exception occurred while sending the mail-- Invalid email");
            throw new BadCredentialsException(
                    messageSource.getMessage("api.error.invalidEmail",null, locale));
        } else{

            if(user.isActive()){
                SecureToken token = secureTokenRepo.findByUser(user);

                if(token!=null){
                    secureTokenRepo.delete(token);
                    mailService.sendForgotPasswordMail(user, locale);
                }

                else {
                    mailService.sendForgotPasswordMail(user, locale);
                }
            }

            else{
                logger.error("Exception occurred while sending the mail-- Inactive account");
                throw new InActiveAccountException(
                        messageSource.getMessage("api.error.accountInactive",null, locale));

            }
        }
        logger.debug("UserService: forgotPassword mail sent");
        logger.info("UserService: forgotPassword ended execution");
        return messageSource.getMessage("api.response.forgotPassword",null, locale);
    }

    public String resetPassword(String token, String password, String confirmPassword, Locale locale){

        logger.info("UserService: resetPassword started execution");

        logger.debug("UserService: resetPassword updating password");
        SecureToken passwordToken = secureTokenRepo.findBySecureToken(token);
        // checks if provided token exists
        if(passwordToken!=null){
            User user = userRepo.findUserByEmail(passwordToken.getUser().getEmail());

            if(passwordToken.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(15l))){

                secureTokenRepo.delete(passwordToken);
                logger.error("Exception occurred while changing the password-- Link expired");
                throw new LinkExpiredException(
                        messageSource.getMessage("api.error.linkExpired",null, locale)) ;
            }
            else{

                if(!(password.equals(confirmPassword))){
                    logger.error("Exception occurred while changing " +
                            "the password-- password and confirm password does not match");
                    throw new PasswordDoNotMatchException(
                            messageSource.getMessage("api.error.passwordDoNotMatch",null, locale));
                }

                user.setPassword(passwordEncoder.encode(password));
                userRepo.save(user);
                secureTokenRepo.delete(passwordToken);
                mailService.sendSuccessfulChangeMail(user, locale);
                logger.info("UserService: resetPassword ended execution");
                return messageSource.getMessage("api.response.passwordChanged",null, locale);
            }
        }
        else {
            logger.error("Exception occurred while changing the password-- Invalid token");
            throw new InvalidTokenException(
                    messageSource.getMessage("api.error.invalidToken",null, locale));
        }

    }
//    String filepath= "src/main/resources/images";
    public String uploadImage( MultipartFile file, String email, Locale locale){

        User user= userRepo.findUserByEmail(email);
        File directory = new File(FILE_PATH);
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (SecurityException se) {
                return null;
            }
        }

        String fileName = FILE_PATH+File.separator+user.getId()+".jpg";

        final String path = System.getProperty("user.dir") + File.separator + fileName;

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(new File(path))) {
            byte[] buf = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buf)) >= 0) {
                fileOutputStream.write(buf, 0, numRead);
            }
        } catch (Exception e) {
            return null;
        }

        return messageSource.getMessage("api.response.imageUploaded",null, locale);
    }
    }


package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.UserNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.CustomerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.SellerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.AdminService;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@Transactional
public class AdminServiceImpl implements AdminService {

    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    MailService mailService;

    @Autowired
    MessageSource messageSource;


    public List<CustomerResponseDTO> listAllCustomers(Integer pageNo, Integer pageSize, String sortBy){
        logger.info("AdminService: listAllCustomers started execution");

        logger.debug("AdminService: listAllCustomers retrieving list of customers");
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Customer> pagedResultCustomer = customerRepo.findAll(pageable);
        List<CustomerResponseDTO> customers = new ArrayList<>();


        logger.debug("AdminService: listAllCustomers adding customer data to CustomerResponseDTO");
        for (Customer customer : pagedResultCustomer) {
            User user = customer.getUser();
            CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
            customerResponseDTO.setId(user.getId());
            customerResponseDTO.setEmail(user.getEmail());

            String fullName;
            if(user.getMiddleName()==null) {
                fullName = user.getFirstName() + " " + user.getLastName();
            }
            else {
                fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
            }
            customerResponseDTO.setFullName(fullName);
            customerResponseDTO.setActive(user.isActive());
            customers.add(customerResponseDTO);
        }

        logger.debug("AdminService: listAllCustomers returning list of CustomerResponseDTO");

        logger.info("AdminService: listAllCustomers ended execution");
        return customers;
    }


    public List<SellerResponseDTO> listAllSellers(Integer pageNo, Integer pageSize, String sortBy){
        logger.info("AdminService: listAllSellers started execution");

        logger.debug("AdminService: listAllSellers retrieving list of sellers");
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Seller> pagedResultSeller = sellerRepo.findAll(pageable);
        List<SellerResponseDTO> sellers = new ArrayList<>();

        logger.debug("AdminService: listAllSellers adding seller data to SellerResponseDTO");
        for (Seller seller : pagedResultSeller) {
            User user = seller.getUser();
            SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
            sellerResponseDTO.setId(user.getId());
            sellerResponseDTO.setEmail(user.getEmail());
            String fullName;
            if(user.getMiddleName()==null) {
                fullName = user.getFirstName() + " " + user.getLastName();
            }
            else {
                fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
            }
            sellerResponseDTO.setFullName(fullName);
            sellerResponseDTO.setActive(user.isActive());
            sellerResponseDTO.setCompanyName(seller.getCompanyName());
            sellerResponseDTO.setGst(seller.getGst());
            sellerResponseDTO.setCompanyContact(seller.getCompanyContact());
            sellerResponseDTO.setAddress(seller.getAddress());
            sellers.add(sellerResponseDTO);
        }

        logger.debug("AdminService: listAllSellers returning list of SellerResponseDTO");

        logger.info("AdminService: listAllSellers ended execution");
        return sellers;
    }


    public String activateUser(Long userId, Locale locale){
        logger.info("AdminService: activateUser started execution");
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            if(user.get().isActive()){
                logger.debug("AdminService: activateUser user account is already active");
                logger.info("AdminService: activateUser ended execution");
                return messageSource.getMessage("api.response.userAlreadyActive",null, locale);
            }
            else {
                logger.debug("AdminService: activateUser setting account account status as active");
                user.get().setActive(true);
                userRepo.save(user.get());
                mailService.sendIsActivatedMail(user.get(), locale);
                logger.info("AdminService: activateUser ended execution");
                return messageSource.getMessage("api.response.userAccountActivated",null, locale);
            }
        }
        else{
            logger.error("AdminService: activateUser exception occurred while execution-- User not found");
            throw new UserNotFoundException(messageSource
                    .getMessage("api.error.userNotFound",null, locale));
        }

    }

    public String deactivateUser(Long userId, Locale locale){
        logger.info("AdminService: deactivateUser started execution");
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            if(user.get().isActive()){
                logger.debug("AdminService: deactivateUser setting account status as inactive");
                user.get().setActive(false);
                userRepo.save(user.get());
                mailService.sendDeActivatedMail(user.get(), locale);
                logger.info("AdminService::deactivateUser ended execution");
                return messageSource.getMessage("api.response.userAccountDeactivated",null, locale);
            }
            else {
                logger.debug("AdminService: deactivateUser user account is already inactive");
                logger.info("AdminService: deactivateUser ended execution");
                return messageSource.getMessage("api.response.userAlreadyDeactivated",null, locale);
            }
        }
        else{
            logger.error("AdminService: deactivateUser exception occurred while execution-- User not found");
            throw new UserNotFoundException(messageSource
                    .getMessage("api.error.userNotFound",null, locale));
        }

    }
}

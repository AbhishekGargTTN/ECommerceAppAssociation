package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.EmailDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.PasswordDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/users")
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {

            User newUser = userService.createUser(userDTO);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
//                    .headers(HeaderUtil.createAlert(applicationName,  "A user is created with identifier " + newUser.getEmail(), newUser.getEmail()))
                    .body(newUser);
//        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
         List<User> users = userService.getAllUsers();
        return users;
    }



    @PutMapping(path = "/activate_account")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token){
//        logger.info("NotificationTokenController::activateAccount request body: " + token);
        String response = userService.activateUserAccount(token);
//        logger.info("NotificationTokenController::activateAccount response: " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/resend_activation")
    public ResponseEntity<String> resendActivation(@Valid @RequestBody EmailDTO emailDTO){
//        logger.info("NotificationTokenController::resendActivation request body: " + emailDTO.toString());
        String response = userService.resendActivationMail(emailDTO.getEmail());
//        logger.info("NotificationTokenController::resendActivation response: " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/forgot_password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody EmailDTO emailDTO){
//        logger.info("NotificationTokenController::forgotPassword request body: " + emailDTO.toString());
        String response = userService.forgotPassword(emailDTO.getEmail());
//        logger.info("NotificationTokenController::forgotPassword response: " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path="/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordDTO passwordDTO){
//        logger.info("NotificationTokenController::resetPassword request body: " + token+ "," + passwordDTO.toString());
        String response = userService.resetPassword(token, passwordDTO.getPassword(), passwordDTO.getConfirmPassword());
//        logger.info("NotificationTokenController::resetPassword response: " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

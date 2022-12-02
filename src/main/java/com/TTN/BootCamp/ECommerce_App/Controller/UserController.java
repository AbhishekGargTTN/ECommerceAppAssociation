package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.EmailDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.PasswordDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl.UserServiceImpl;
import com.TTN.BootCamp.ECommerce_App.Service.UserService;
import com.sun.mail.iap.ResponseHandler;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(path = "/activate_account")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token){
        logger.info("UserController: activating Customer");
        Locale locale = LocaleContextHolder.getLocale();
        String response = userService.activateUserAccount(token, locale);
        logger.info("UserController: generated response-- " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/resend_activation")
    public ResponseEntity<String> resendActivation(@Valid @RequestBody EmailDTO emailDTO){
        logger.info("UserController: resending activation mail");
        Locale locale = LocaleContextHolder.getLocale();
        String response = userService.resendActivationMail(emailDTO.getEmail(), locale);
        logger.info("UserController: generated response-- " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path="/forgot_password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody EmailDTO emailDTO){
        logger.info("UserController: triggered forgot password");
        Locale locale = LocaleContextHolder.getLocale();
        String response = userService.forgotPassword(emailDTO.getEmail(), locale);
        logger.info("UserController: generated response-- " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path="/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordDTO passwordDTO){
        logger.info("UserController: resetting user Password");
        Locale locale = LocaleContextHolder.getLocale();
        String response = userService
                .resetPassword(token, passwordDTO.getPassword(), passwordDTO.getConfirmPassword(), locale);
        logger.info("UserController: generated response-- " + response );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add_image")
    public ResponseEntity<String> uploadImage(Authentication authentication
            ,@RequestParam("file") MultipartFile file) {
        String email = authentication.getName();

        Locale locale = LocaleContextHolder.getLocale();
        return new ResponseEntity<>(userService.uploadImage(file, email, locale),HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/get_image",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam long id) throws IOException {

        String path= "images/"+id+".jpg";
        var imgFile = new ClassPathResource(path);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}

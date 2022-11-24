package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.LoginDTO;
import com.TTN.BootCamp.ECommerce_App.Repository.RoleRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TokenStore tokenStore;

//    @GetMapping("/")
//    public String index(){
//        return "Products";
//    }
//
//    @GetMapping("/admin/home")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public String adminHome(){
//        return "Admin home";
//    }
//
//    @GetMapping("/user/home")
//    @PreAuthorize("hasAuthority('USER')")
//    public String userHome(){
//        return "User home";
//    }
//
//    @GetMapping("/customer/home")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    public String customerHome(){
//        return "Customer home";
//    }
//
//    @GetMapping("/seller/home")
//    @PreAuthorize("hasAuthority('SELLER')")
//    public String sellerHome(){
//        return "Seller home";
//    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        logger.info("AuthenticationController: started execution");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("AuthenticationController: ended execution");
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,HttpServletResponse response){
        logger.info("AuthenticationController: started execution");
        removeSecurityContext(request,response);

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
        }
        catch (Exception e){
            logger.error("An exception occurred while execution");
            return ResponseEntity.badRequest().body("Invalid access token");
        }
        logger.info("AuthenticationController: ended execution");
        return ResponseEntity.ok().body("User logged out successfully");
    }

    private void removeSecurityContext(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
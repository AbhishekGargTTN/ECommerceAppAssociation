package com.TTN.BootCamp.ECommerce_App.Config;

import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationManagerConfig implements AuthenticationManager {

    Logger logger = LoggerFactory.getLogger(AuthenticationManagerConfig.class);
    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.info("AuthenticationManagerConfig: authenticating user credentials");
        logger.debug("AuthenticationManagerConfig: authenticating user credentials and generating token");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepo.findUserByEmail(username);

        if(user==null){
            logger.error("Exception occurred while authenticating");
            throw new UsernameNotFoundException("Invalid credentials");
        }
        if(user.isLocked()){
            logger.error("Exception occurred while authenticating");
            throw new BadCredentialsException("Account is locked");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.debug("AuthenticationManagerConfig: authenticating invalid login attempts");
            int counter = user.getInvalidAttemptCount();
            if(counter < 2){
                user.setInvalidAttemptCount(++counter);
                userRepo.save(user);
            } else{
                user.setLocked(true);
                user.setInvalidAttemptCount(0);
                mailService.sendAccountLockedMail(user);
                userRepo.save(user);
                logger.debug("AuthenticationManagerConfig: user account "+
                        "locked for crossing invalid attempt limit");
            }
            logger.error("Exception occurred while authenticating");
            throw new BadCredentialsException("Invalid Credentials");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));

        user.setInvalidAttemptCount(0);
        userRepo.save(user);

        logger.info("AuthenticationManagerConfig: ended execution");
        return new UsernamePasswordAuthenticationToken(username, password,authorities);
    }
}
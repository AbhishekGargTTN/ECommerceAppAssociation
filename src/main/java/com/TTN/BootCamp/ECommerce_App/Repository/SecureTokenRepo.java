package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Entity.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecureTokenRepo extends JpaRepository<SecureToken, Long> {

   SecureToken findBySecureToken(String secureToken);

   SecureToken findByUser(User user);


}

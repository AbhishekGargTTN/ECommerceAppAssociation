package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select * from User where First_Name=:firstName", nativeQuery = true)
    public User findByFirstName(@Param("firstName") String firstName);
//    @Query(value = "select * from User", nativeQuery = true)
    public List<User> findAll();

    Optional<User> findOneByEmail(String login);

    Optional<User> findOneByEmailIgnoreCase(String email);
}

package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select * from User where First_Name=:firstName", nativeQuery = true)
    public User findByFirstName(@Param("firstName") String firstName);
//    @Query(value = "select * from User", nativeQuery = true)
    public List<User> findAll();

    User findOneByEmail(String login);

    User findOneByEmailIgnoreCase(String email);

    User findUserByEmail(String mail);
}

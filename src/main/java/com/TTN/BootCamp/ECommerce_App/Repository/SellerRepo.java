package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import com.TTN.BootCamp.ECommerce_App.Entity.Seller;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {

    @Query(value = "select * from Seller where User_Id=:userId",nativeQuery = true)
    public Seller findByUser_Id(@Param("userId") long userId);

    public Seller findByGst(String gst);

    Seller findByCompanyNameIgnoreCase(String companyName);

    Seller findByUser(User user);


}

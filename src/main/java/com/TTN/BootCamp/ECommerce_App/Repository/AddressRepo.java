package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import com.TTN.BootCamp.ECommerce_App.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

    @Query(value = "select * from Address where Customer_ID=:customerId",nativeQuery = true)
    public List<Address> findByCustomer_ID(@Param("customerId") long customerId);

    @Query(value = "select * from Address where Seller_ID=:sellerId",nativeQuery = true)
    public Address findBySeller_ID(@Param("sellerId") long sellerId);

    @Query("select a from Address a where a.id = ?1")
    Address findByAddress_Id(Long aLong);
}

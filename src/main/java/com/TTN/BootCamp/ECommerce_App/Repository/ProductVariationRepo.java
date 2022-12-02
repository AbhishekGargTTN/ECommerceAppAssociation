package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.Product;
import com.TTN.BootCamp.ECommerce_App.Entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariationRepo extends JpaRepository<ProductVariation, Long> {
    List<ProductVariation> findByProduct(Product product);


}

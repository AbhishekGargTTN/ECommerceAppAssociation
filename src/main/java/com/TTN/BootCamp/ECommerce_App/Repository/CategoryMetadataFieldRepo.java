package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldRepo extends JpaRepository<CategoryMetadataField, Long> {

    CategoryMetadataField findByNameIgnoreCase(String name);
}

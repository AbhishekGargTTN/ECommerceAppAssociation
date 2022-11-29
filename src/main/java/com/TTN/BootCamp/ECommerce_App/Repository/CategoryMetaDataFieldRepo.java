package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetaDataFieldRepo extends JpaRepository<CategoryMetaDataField, Long> {

    CategoryMetaDataField findByName(String name);
}

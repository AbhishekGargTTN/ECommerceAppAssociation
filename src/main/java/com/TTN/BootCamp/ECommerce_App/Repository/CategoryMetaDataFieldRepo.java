package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryMetaDataFieldRepo extends JpaRepository<CategoryMetaDataField, Long> {

    CategoryMetaDataField findByName(String name);



    @Query("select c from CategoryMetaDataField c where c.id = ?1")
    CategoryMetaDataField findByFieldId(Long aLong);
}

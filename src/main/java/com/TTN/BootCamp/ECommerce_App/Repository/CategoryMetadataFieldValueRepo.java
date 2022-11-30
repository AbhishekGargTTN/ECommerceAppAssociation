package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataFieldKey;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryMetadataFieldValueRepo extends JpaRepository<CategoryMetadataFieldValue, CategoryMetadataFieldKey> {
    List<CategoryMetadataFieldValue> findByCategory(Category category);
    Optional<CategoryMetadataFieldValue> findById(CategoryMetadataFieldKey key);
}
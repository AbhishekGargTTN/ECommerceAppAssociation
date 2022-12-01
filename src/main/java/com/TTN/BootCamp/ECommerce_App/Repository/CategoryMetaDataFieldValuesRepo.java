package com.TTN.BootCamp.ECommerce_App.Repository;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataCompositeKey;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataFieldValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetaDataFieldValuesRepo
        extends JpaRepository<CategoryMetaDataFieldValues, CategoryMetaDataCompositeKey> {

    CategoryMetaDataFieldValues findByCategoryMetaDataCompositeKey_CategoryId(long categoryId);

    List<CategoryMetaDataFieldValues> findByCategory(Category category);




}

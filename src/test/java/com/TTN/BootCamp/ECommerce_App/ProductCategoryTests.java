package com.TTN.BootCamp.ECommerce_App;

import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductCategoryTests {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;


    @Autowired
    ProductVariationRepo productVariationRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    CategoryMetadataFieldValueRepo categoryMetadataFieldValueRepo;

    @Test
    public void addCategory(){
        Category category= new Category();
        category.setName("Fashion");
        categoryRepo.save(category);
    }

    @Test
    public void addProduct(){

        Product product=new Product();

        product.setName("Shoes");
        product.setBrand("Adidas");
        product.setDescription("Nice shoes");
        Category category=new Category();
//        category= categoryRepo.findByName("Shoes");
        product.setCategory(category);
        Seller seller= sellerRepo.findByUser_Id(2);
        product.setSeller(seller);
        productRepo.save(product);
    }

    @Test
    public void addSubCategory(){
        Category category= new Category();
        category.setName("Shoes");
//        Category parentCategory= categoryRepo.findByName("Fashion");
//        category.setParentCategory(parentCategory);
        categoryRepo.save(category);
    }

    @Test
    public void addMetadataField(){
//        CategoryMetaDataField categoryMetaDataField= new CategoryMetaDataField();
//        categoryMetaDataField.setName("Size");
//        categoryMetadataFieldRepo.save(categoryMetaDataField);
    }

    @Transactional
    @Test
    public void addFieldValues(){
//        CategoryMetaDataFieldValues categoryMetaDataFieldValues= new CategoryMetaDataFieldValues();
//
//        Category category= categoryRepo.findByName("Shoes");
//        categoryMetaDataFieldValues.setCategory(category);
//
//        CategoryMetaDataField categoryMetaDataField= categoryMetadataFieldRepo.findByName("Size");
//        categoryMetaDataFieldValues.setCategoryMetaDataField(categoryMetaDataField);

//        categoryMetaDataFieldValues.setValues("S,M,L,XL");
//        CategoryMetaDataCompositeKey categoryMetaDataCompositeKey = new CategoryMetaDataCompositeKey();
//        categoryMetaDataCompositeKey.setCategoryId(category.getCategoryId());
///        categoryMetaDataCompositeKey.setCategoryMetaDataFieldId(categoryMetaDataField.getId());
//        categoryMetaDataFieldValues.setCategoryMetaDataCompositeKey(categoryMetaDataCompositeKey);
//        categoryMetadataFieldValueRepo.save(categoryMetaDataFieldValues);
//    }
//}

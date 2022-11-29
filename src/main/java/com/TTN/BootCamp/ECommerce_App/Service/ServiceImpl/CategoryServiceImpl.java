package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataCompositeKey;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataField;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataFieldValues;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetaDataFieldRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetaDataFieldValuesRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryRepo;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMetaDataFieldValuesRepo categoryMetaDataFieldValuesRepo;

    public String addMetaDataField(MetaDataFieldDTO metaDataFieldDTO){
       CategoryMetaDataField existingCategoryMetaDataField=
               categoryMetaDataFieldRepo.findByName(metaDataFieldDTO.getName());
        if(existingCategoryMetaDataField!=null) {
            return "Field Already exists";
        }
        else {
            CategoryMetaDataField categoryMetaDataField = new CategoryMetaDataField();
            categoryMetaDataField.setName(metaDataFieldDTO.getName());
            categoryMetaDataFieldRepo.save(categoryMetaDataField);
            return "Meta Data Field Added Successfully";
        }
    }

    public List<MetaDataFieldResponseDTO> getMetaDataFields(Integer pageNo, Integer pageSize, String sortBy){

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CategoryMetaDataField> pagedResultFields = categoryMetaDataFieldRepo.findAll(pageable);
        List<MetaDataFieldResponseDTO> metaDataFields = new ArrayList<>();

//        logger.debug("AdminService: listAllSellers adding seller data to SellerResponseDTO");
        for (CategoryMetaDataField metaDataField : pagedResultFields) {

            MetaDataFieldResponseDTO metaDataFieldResponseDTO= new MetaDataFieldResponseDTO();
            metaDataFieldResponseDTO.setId(metaDataField.getId());
            metaDataFieldResponseDTO.setName(metaDataField.getName());
            metaDataFields.add(metaDataFieldResponseDTO);
        }

//        logger.debug("AdminService: listAllSellers returning list of SellerResponseDTO");

//        logger.info("AdminService: listAllSellers ended execution");
        return metaDataFields;
    }

    public String addCategory(CategoryDTO categoryDTO){
        Category existingCategory= categoryRepo.findByName(categoryDTO.getName());
        if(existingCategory!= null){
            return "Category already exists";
        }
        else{
            Category category = new Category();
            category.setName(categoryDTO.getName());
            if(categoryDTO.getParentCategoryId()!= null) {
                Category parentCategory = categoryRepo.findByCategoryId(categoryDTO.getParentCategoryId());
                category.setParentCategory(parentCategory);
            }
            categoryRepo.save(category);
            return "Category Added Successfully";
        }
    }

    public CategoryResponseDTO getCategory(long id){
        Optional<Category> category = categoryRepo.findById((long) id); //.orElseThrow(() -> new BadRequestException());

            CategoryResponseDTO categoryResponseDTO= new CategoryResponseDTO();
            categoryResponseDTO.setId(category.get().getId());
            categoryResponseDTO.setName(category.get().getName());
            categoryResponseDTO.setParentCategory(category.get().getParentCategory());
            categoryResponseDTO.setSubCategories(category.get().getSubCategories());
            return categoryResponseDTO;

    }

    public List<CategoryResponseDTO> getAllCategories(Integer pageNo, Integer pageSize, String sortBy){

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> pagedResultCategories = categoryRepo.findAll(pageable);
        List<CategoryResponseDTO> categories = new ArrayList<>();

//        logger.debug("AdminService: listAllSellers adding seller data to SellerResponseDTO");
        for (Category category : pagedResultCategories) {

            CategoryResponseDTO categoryResponseDTO= new CategoryResponseDTO();
            categoryResponseDTO.setId(category.getId());
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTO.setParentCategory(category.getParentCategory());
            categoryResponseDTO.setSubCategories(category.getSubCategories());
            categories.add(categoryResponseDTO);
        }

//        logger.debug("AdminService: listAllSellers returning list of SellerResponseDTO");

//        logger.info("AdminService: listAllSellers ended execution");
        return categories;
    }

    public String updateCategory(long id, CategoryUpdateDTO categoryUpdateDTO){
        Category existingCategory= categoryRepo.findByName(categoryUpdateDTO.getName());
        if(existingCategory!= null){
            return "Category already exists";
        }
        else{
            Category category = categoryRepo.findByCategoryId(id);
            category.setName(categoryUpdateDTO.getName());
            categoryRepo.save(category);
            return "Category Updated Successfully";
        }
    }

    public String addCategoryMetaDataField(CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){

        CategoryMetaDataFieldValues categoryMetaDataFieldValues= new CategoryMetaDataFieldValues();

        Category category= categoryRepo.findByCategoryId(categoryMetaDataFieldValueDTO.getCategoryId());
        categoryMetaDataFieldValues.setCategory(category);

        CategoryMetaDataField categoryMetaDataField= categoryMetaDataFieldRepo.findByFieldId(categoryMetaDataFieldValueDTO.getMetaDataFieldId());
        categoryMetaDataFieldValues.setCategoryMetaDataField(categoryMetaDataField);

        String newValue = "";
        for(String value: categoryMetaDataFieldValueDTO.getValues()){
            newValue.concat("," + value);
        }
        categoryMetaDataFieldValues.setValues(newValue);

        CategoryMetaDataCompositeKey categoryMetaDataCompositeKey = new CategoryMetaDataCompositeKey();
        categoryMetaDataCompositeKey.setCategoryId(category.getId());
        categoryMetaDataCompositeKey.setCategoryMetaDataFieldId(categoryMetaDataField.getId());
        categoryMetaDataFieldValues.setCategoryMetaDataCompositeKey(categoryMetaDataCompositeKey);

        categoryMetaDataFieldValuesRepo.save(categoryMetaDataFieldValues);

        return "Meta Data Field added to Category Successfully";
    }

    public String updateCategoryMetaDataField(CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){
        CategoryMetaDataFieldValues categoryMetaDataFieldValues= new CategoryMetaDataFieldValues();

        categoryMetaDataFieldValues= categoryMetaDataFieldValuesRepo
                .findByCategoryMetaDataCompositeKey_CategoryId
                        (categoryMetaDataFieldValueDTO.getCategoryId());

        if(categoryMetaDataFieldValues.getCategoryMetaDataCompositeKey()
                .getCategoryMetaDataFieldId()!= categoryMetaDataFieldValueDTO.getMetaDataFieldId()){
            return "Meta Data Field is not associated with the Category";
        }
        else {
//            categoryMetaDataFieldValues.setValues(categoryMetaDataFieldValueDTO.getValues());
            categoryMetaDataFieldValuesRepo.save(categoryMetaDataFieldValues);
            return "Meta Data Field added to Category Successfully";
        }


    }
}

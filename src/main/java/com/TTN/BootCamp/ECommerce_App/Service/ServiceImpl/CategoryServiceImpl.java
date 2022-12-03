package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldValueResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataCompositeKey;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataField;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetaDataFieldValues;
import com.TTN.BootCamp.ECommerce_App.Exception.BadRequestException;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetaDataFieldRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetaDataFieldValuesRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryRepo;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NameNotFoundException;
import java.util.*;

@Component
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMetaDataFieldValuesRepo categoryMetaDataFieldValuesRepo;

    @Autowired
    MessageSource messageSource;

    public String addMetaDataField(MetaDataFieldDTO metaDataFieldDTO, Locale locale){
       CategoryMetaDataField existingCategoryMetaDataField=
               categoryMetaDataFieldRepo.findByName(metaDataFieldDTO.getName());
        if(existingCategoryMetaDataField!=null) {
            throw new BadRequestException(messageSource
                    .getMessage("api.error.fieldExists",null, locale));
        }
        else {
            CategoryMetaDataField categoryMetaDataField = new CategoryMetaDataField();
            categoryMetaDataField.setName(metaDataFieldDTO.getName());
            categoryMetaDataFieldRepo.save(categoryMetaDataField);
            return messageSource.getMessage("api.response.addedSuccess",null, locale);
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

    public String addCategory(CategoryDTO categoryDTO, Locale locale){
        Category existingCategory= categoryRepo.findByName(categoryDTO.getName());
        if(existingCategory!= null){
            throw new BadRequestException(messageSource
                    .getMessage("api.error.fieldExists",null, locale));
        }
        else{
            Category category = new Category();
            category.setName(categoryDTO.getName());
            if(categoryDTO.getParentCategoryId()!= null) {
                Category parentCategory = categoryRepo.findByCategoryId(categoryDTO.getParentCategoryId());
                category.setParentCategory(parentCategory);
            }
            categoryRepo.save(category);
            return messageSource.getMessage("api.response.addedSuccess",null, locale);
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

    public String updateCategory(long id, CategoryUpdateDTO categoryUpdateDTO, Locale locale){
        Category existingCategory= categoryRepo.findByName(categoryUpdateDTO.getName());
        if(existingCategory!= null){
            throw new BadRequestException(messageSource
                    .getMessage("api.error.fieldExists",null, locale));
        }
        else{
            Category category = categoryRepo.findByCategoryId(id);
            category.setName(categoryUpdateDTO.getName());
            categoryRepo.save(category);
            return messageSource.getMessage("api.response.updateSuccess",null, locale);
        }
    }

    public String addCategoryMetaDataField
            (CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO, Locale locale){

//        CategoryMetaDataFieldValues categoryMetaDataFieldValues= new CategoryMetaDataFieldValues();
//
//        Category category= categoryRepo.findByCategoryId(categoryMetaDataFieldValueDTO.getCategoryId());
//        categoryMetaDataFieldValues.setCategory(category);
//
//        CategoryMetaDataField categoryMetaDataField= categoryMetaDataFieldRepo.findByFieldId(categoryMetaDataFieldValueDTO.getMetaDataFieldId());
//        categoryMetaDataFieldValues.setCategoryMetaDataField(categoryMetaDataField);
//
//        categoryMetaDataFieldValues.setValue(categoryMetaDataFieldValueDTO.getValues().toString());
//
//        CategoryMetaDataCompositeKey categoryMetaDataCompositeKey = new CategoryMetaDataCompositeKey();
//        categoryMetaDataCompositeKey.setCategoryId(category.getId());
//        categoryMetaDataCompositeKey.setCategoryMetaDataFieldId(categoryMetaDataField.getId());
//        categoryMetaDataFieldValues.setCategoryMetaDataCompositeKey(categoryMetaDataCompositeKey);
//
//        categoryMetaDataFieldValuesRepo.save(categoryMetaDataFieldValues);
//
//        return messageSource.getMessage("api.response.metadataCategoryAdded",null, locale);

//        logger.info("CategoryService::addMetaValues execution started");

        Long categoryId = categoryMetaDataFieldValueDTO.getCategoryId();
        Long metadataId = categoryMetaDataFieldValueDTO.getMetaDataFieldId();

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new BadRequestException(
                messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
        ));
        CategoryMetaDataField metaField = categoryMetaDataFieldRepo.findById(metadataId).orElseThrow(() -> new BadRequestException(
                messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
        ));

        CategoryMetaDataFieldValues fieldValue = new CategoryMetaDataFieldValues();
        fieldValue.setCategoryMetaDataCompositeKey(new CategoryMetaDataCompositeKey(category.getId(), metaField.getId()));
        fieldValue.setCategory(category);
        fieldValue.setCategoryMetaDataField(metaField);

        String newValues = "";

        CategoryMetaDataCompositeKey key = new CategoryMetaDataCompositeKey(categoryId,metadataId);

        Optional<CategoryMetaDataFieldValues> object = categoryMetaDataFieldValuesRepo.findById(key);
        String originalValues="";
        if(object.isPresent()){
            originalValues = object.get().getValue();
        }

        if(originalValues!=null){
            newValues = originalValues;
        }

        Optional<List<String>> check = Optional.of(List.of(originalValues.split(",")));

        for (String value : categoryMetaDataFieldValueDTO.getValues()){

            if(check.isPresent() && check.get().contains(value)){
                throw new BadRequestException(messageSource.getMessage("api.error.invalidFieldValue",null,Locale.ENGLISH));
            }

            newValues = newValues.concat(value + ",");
        }
        fieldValue.setValue(newValues);


        categoryMetaDataFieldValuesRepo.save(fieldValue);

        // create appropriate responseDTO
//        MetaDataFieldValueResponseDTO metaFieldValueResponseDTO = new MetaDataFieldValueResponseDTO();
//        metaFieldValueResponseDTO.set(category.getId());
//        metaFieldValueResponseDTO.setMetaFieldId(metaField.getId());
//        metaFieldValueResponseDTO.setValues(fieldValue.getValue());


         return messageSource.getMessage("api.response.metadataCategoryAdded",null, locale);
    }

    public String updateCategoryMetaDataField
            (CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO, Locale locale){
        CategoryMetaDataFieldValues categoryMetaDataFieldValues= new CategoryMetaDataFieldValues();

        categoryMetaDataFieldValues= categoryMetaDataFieldValuesRepo
                .findByCategoryMetaDataCompositeKey_CategoryId
                        (categoryMetaDataFieldValueDTO.getCategoryId());

        if(categoryMetaDataFieldValues.getCategoryMetaDataCompositeKey()
                .getCategoryMetaDataFieldId()!= categoryMetaDataFieldValueDTO.getMetaDataFieldId()){
            throw new BadRequestException(messageSource
                    .getMessage("api.error.metadataCategoryNotAssociated",null, locale));
        }
        else {
            categoryMetaDataFieldValues.setValue(categoryMetaDataFieldValueDTO.getValues().toString());
            categoryMetaDataFieldValuesRepo.save(categoryMetaDataFieldValues);
            return messageSource.getMessage("api.response.updateSuccess",null, locale);
        }


    }

    public List<SellerCategoryResponseDTO> getAllSellerCategories(){

        List<Category> categoryList = categoryRepo.findAll();

        List<SellerCategoryResponseDTO> resultList = new ArrayList<>();

        for(Category category: categoryList){
            if(category.getSubCategories().isEmpty()){

                List<CategoryMetaDataFieldValues> metadataList =
                        categoryMetaDataFieldValuesRepo.findByCategory(category);

                SellerCategoryResponseDTO sellerResponse = new SellerCategoryResponseDTO();
                sellerResponse.setId(category.getId());
                sellerResponse.setName(category.getName());
                sellerResponse.setParent(category.getParentCategory());
                // convert metadata and its values to appropriate responseDTO
                List<MetaDataFieldValueResponseDTO> metaList = new ArrayList<>();
                for (CategoryMetaDataFieldValues metadata: metadataList){
                    MetaDataFieldValueResponseDTO metaDataFieldValueResponseDTO = new MetaDataFieldValueResponseDTO();
                    metaDataFieldValueResponseDTO.setId(metadata.getCategoryMetaDataField().getId());
                    metaDataFieldValueResponseDTO.setName(metadata.getCategoryMetaDataField().getName());
                    metaDataFieldValueResponseDTO.setValues(metadata.getValue());
                    metaList.add(metaDataFieldValueResponseDTO);
                }
                sellerResponse.setMetadata(metaList);
                resultList.add(sellerResponse);
            }
        }
        return resultList;
    }

    public Set<Category> getCustomerCategories(Optional<Long> optionalId){
        if(optionalId.isPresent()){
            // if ID is present, fetch its immediate children
            Category category = categoryRepo.findById(optionalId.get()).orElseThrow(() ->
                    new BadRequestException("Invalid id"));
//                    new BadRequestException(
//                    messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)));
            Set<Category> childList = category.getSubCategories();
            return childList;
        }
        else{
            List<Category> categoryList = categoryRepo.findAll();
            Set<Category> rootNodes = new HashSet<>();

            for(Category category: categoryList){
                if(category.getParentCategory()==null){
                    rootNodes.add(category);
                }
            }
            return rootNodes;
        }
    }


}

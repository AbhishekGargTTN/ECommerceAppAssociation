package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;


import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ChildCategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetadataDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaFieldValueResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetadataResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataField;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataFieldKey;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataFieldValue;
import com.TTN.BootCamp.ECommerce_App.Exception.BadRequestException;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetadataFieldRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryMetadataFieldValueRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryRepo;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    MessageSource messageSource;

    @Autowired
    CategoryMetadataFieldValueRepo categoryMetadataFieldValueRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    CategoryRepo categoryRepo;

    public Long createCategory(CategoryDTO categoryDTO) {

        Category category = new Category();

        if (categoryDTO.getParentId() != null) {
            Optional<Category> parentCategory = categoryRepo.findById(categoryDTO.getParentId());
            category.setParent(parentCategory.get());
        }
        category.setName(categoryDTO.getName());
        return categoryRepo.save(category).getId();


    }

    public Long createMetadataField(MetadataDTO metadataDTO) {

//        String providedName = metadataDTO.getFieldName();
//
//        CategoryMetadataField existingField = categoryMetadataFieldRepo.findByNameIgnoreCase(providedName);
//        if (existingField != null) {
////            throw new BadRequestException(messageSource.getMessage("api.error.fieldExists", null, Locale.ENGLISH));
//            throw new BadRequestException("Field exists");
//        }
//        CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
//        categoryMetadataField.setName(providedName);
//        return categoryMetadataFieldRepo.save(categoryMetadataField).getId();

        CategoryMetadataField existingCategoryMetaDataField=
                categoryMetadataFieldRepo.findByName(metaDataFieldDTO.getName());
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

    public Page<CategoryMetadataField> viewAllMetadataFields(Pageable paging) {
        return categoryMetadataFieldRepo.findAll(paging);

    }

    public CategoryResponseDTO viewCategory(int id) {
        Category category = categoryRepo.findById((long) id).orElseThrow(() -> new BadRequestException("Invalid id")
                //new BadRequestException(
              //  messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)

        );

        // converting to appropriate ResponseDTO
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setParent(category.getParent());

        // fetching immediate child and adding it to ResponseDTO
        Set<ChildCategoryDTO> childList = new HashSet<>();

        for(Category child: category.getChildren()){
            ChildCategoryDTO childCategoryDTO = new ChildCategoryDTO();
            childCategoryDTO.setId(child.getId());
            childCategoryDTO.setName(child.getName());
            childList.add(childCategoryDTO);

        }
        categoryResponseDTO.setChildren(childList);

        // fetching associated metadata info and adding it to ResponseDTO
        List<CategoryMetadataFieldValue> metadataList =
                categoryMetadataFieldValueRepo.findByCategory(category);

        // filtering out metadata
        List<MetadataResponseDTO> metaList = new ArrayList<>();
        for (CategoryMetadataFieldValue metadata: metadataList){
            MetadataResponseDTO metadataResponseDTO = new MetadataResponseDTO();
            metadataResponseDTO.setMetadataId(metadata.getCategoryMetadataField().getId());
            metadataResponseDTO.setFieldName(metadata.getCategoryMetadataField().getName());
            metadataResponseDTO.setPossibleValues(metadata.getValue());
            metaList.add(metadataResponseDTO);
        }
        categoryResponseDTO.setMetadataList(metaList);
        return categoryResponseDTO;

    }

    public List<CategoryResponseDTO> viewAllCategories(Pageable paging) {
        Page<Category> categoryPage = categoryRepo.findAll(paging);
        List<CategoryResponseDTO> requiredCategories = new ArrayList<>();
        for (Category category : categoryPage) {


            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();

            categoryResponseDTO.setId(category.getId());
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTO.setParent(category.getParent());

            Set<ChildCategoryDTO> childList = new HashSet<>();

            for(Category child: category.getChildren()){
                ChildCategoryDTO childCategoryDTO = new ChildCategoryDTO();
                childCategoryDTO.setId(child.getId());
                childCategoryDTO.setName(child.getName());
                childList.add(childCategoryDTO);

            }
            categoryResponseDTO.setChildren(childList);

            // fetching associated metadata info and adding it to ResponseDTO
            List<CategoryMetadataFieldValue> metadataList =
                    categoryMetadataFieldValueRepo.findByCategory(category);

            // filtering out metadata
            List<MetadataResponseDTO> metaList = new ArrayList<>();
            for (CategoryMetadataFieldValue metadata: metadataList){
                MetadataResponseDTO metadataResponseDTO = new MetadataResponseDTO();
                metadataResponseDTO.setMetadataId(metadata.getCategoryMetadataField().getId());
                metadataResponseDTO.setFieldName(metadata.getCategoryMetadataField().getName());
                metadataResponseDTO.setPossibleValues(metadata.getValue());
                metaList.add(metadataResponseDTO);
            }
            categoryResponseDTO.setMetadataList(metaList);

            requiredCategories.add(categoryResponseDTO);
        }
        return requiredCategories;
    }

    public String updateCategoryName(CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepo.findById(categoryUpdateDTO.getId()).orElseThrow(() -> //new BadRequestException(
               // messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
                new BadRequestException("invalid id")
        );
        BeanUtils.copyProperties(categoryUpdateDTO, category, FilterProperties.getNullPropertyNames(category));
        categoryRepo.save(category);
        return messageSource.getMessage("api.response.updateSuccess", null, Locale.ENGLISH);
    }

    public MetaFieldValueResponseDTO addMetaValues(MetaFieldValueDTO metaFieldValueDTO){

        logger.info("CategoryService::addMetaValues execution started");

        Long categoryId = metaFieldValueDTO.getCategoryId();
        Long metadataId = metaFieldValueDTO.getMetadataId();

        // check to see if provided ids are valid
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> //new BadRequestException(
//                messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
                new BadRequestException("Invalid id")
        );
        CategoryMetadataField metaField = categoryMetadataFieldRepo.findById(metadataId).orElseThrow(() -> //new BadRequestException(
//                messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
                new BadRequestException("invalid id")
        );


        // convert requestDTO to entity obj
        CategoryMetadataFieldValue fieldValue = new CategoryMetadataFieldValue();
        fieldValue.setCategoryMetadataFieldKey(new CategoryMetadataFieldKey(category.getId(), metaField.getId()));
        fieldValue.setCategory(category);
        fieldValue.setCategoryMetadataField(metaField);

        String newValues = "";

        // check to see if values are unique for category, metadataField combo
        CategoryMetadataFieldKey key = new CategoryMetadataFieldKey(categoryId,metadataId);

        Optional<CategoryMetadataFieldValue> object = categoryMetadataFieldValueRepo.findById(key);
        String originalValues="";
        if(object.isPresent()){
            originalValues = object.get().getValue();
        }

        if(originalValues!=null){
            newValues = originalValues;
        }

        Optional<List<String>> check = Optional.of(List.of(originalValues.split(",")));

        for (String value : metaFieldValueDTO.getValues()){

            if(check.isPresent() && check.get().contains(value)){
               // throw new BadRequestException(messageSource.getMessage("api.error.invalidFieldValue",null,Locale.ENGLISH));
                throw new BadRequestException("invalid field value");
            }
            // convert list of String values in request
            // to a comma separated String to be stored in database
            newValues = newValues.concat(value + ",");
        }
        fieldValue.setValue(newValues);

        // save field values to repo
        categoryMetadataFieldValueRepo.save(fieldValue);

        // create appropriate responseDTO
        MetaFieldValueResponseDTO metaFieldValueResponseDTO = new MetaFieldValueResponseDTO();
        metaFieldValueResponseDTO.setCategoryId(category.getId());
        metaFieldValueResponseDTO.setMetaFieldId(metaField.getId());
        metaFieldValueResponseDTO.setValues(fieldValue.getValue());


        return metaFieldValueResponseDTO;

    }

    public List<SellerCategoryResponseDTO> viewSellerCategory(){
        // obtain list of categories
        List<Category> categoryList = categoryRepo.findAll();

        List<SellerCategoryResponseDTO> resultList = new ArrayList<>();
        // filter out leaf nodes
        for(Category category: categoryList){
            if(category.getChildren().isEmpty()){
                // get its parental hierarchy till root node
                // use category to fetch all metadata fields and values related
                List<CategoryMetadataFieldValue> metadataList =
                        categoryMetadataFieldValueRepo.findByCategory(category);

                // convert to appropriate responseDTO

                SellerCategoryResponseDTO sellerResponse = new SellerCategoryResponseDTO();
                sellerResponse.setId(category.getId());
                sellerResponse.setName(category.getName());
                sellerResponse.setParent(category.getParent());
                // convert metadata and its values to appropriate responseDTO
                List<MetadataResponseDTO> metaList = new ArrayList<>();
                for (CategoryMetadataFieldValue metadata: metadataList){
                    MetadataResponseDTO metadataResponseDTO = new MetadataResponseDTO();
                    metadataResponseDTO.setMetadataId(metadata.getCategoryMetadataField().getId());
                    metadataResponseDTO.setFieldName(metadata.getCategoryMetadataField().getName());
                    metadataResponseDTO.setPossibleValues(metadata.getValue());
                    metaList.add(metadataResponseDTO);
                }
                sellerResponse.setMetadata(metaList);
                resultList.add(sellerResponse);
            }
        }
        return resultList;
    }

    public Set<Category> viewCustomerCategory(Optional<Integer> optionalId){
        if(optionalId.isPresent()){
            // if ID is present, fetch its immediate children
            Category category = categoryRepo.findById((long)optionalId.get()).orElseThrow(() -> //new BadRequestException(
//                    messageSource.getMessage("api.error.invalidId", null, Locale.ENGLISH)
                    new BadRequestException("invalid id")
            );

            Set<Category> childList = category.getChildren();
            return childList;
        }
        else{
            // if ID isn't provided fetch all root nodes
            List<Category> categoryList = categoryRepo.findAll();
            Set<Category> rootNodes = new HashSet<>();
            // filtering rootNodes
            for(Category category: categoryList){
                if(category.getParent()==null){
                    rootNodes.add(category);
                }
            }
            return rootNodes;
        }
    }

//    public Set<Category> filterCustomerCategory(Integer id) {
//
//    }


}

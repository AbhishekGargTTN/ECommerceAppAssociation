package com.TTN.BootCamp.ECommerce_App.Controller;


import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetadataDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaFieldValueResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataField;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO){
        Long id = categoryService.createCategory(categoryDTO);
//        String response = messageSource.getMessage("api.response.addedSuccess",null, Locale.ENGLISH);
        String response= "Category Added successfully";
        return new ResponseEntity<>(response+"Category Id: " + id,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_meta_data_field")
    public ResponseEntity<String> addMetadata(@RequestBody MetadataDTO metadataDTO){
        Long id = categoryService.createMetadataField(metadataDTO);
//        String response = messageSource.getMessage("api.response.addedSuccess",null, Locale.ENGLISH);
        String response= "Meta data Added successfully";
        return new ResponseEntity<>(response+"Meta Data Id: " + id,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get_all_meta_data_field")
    public ResponseEntity<Page<CategoryMetadataField>> viewMetadata(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @Pattern(regexp="DESC|ASC") @RequestParam(required = false) String sortOrder){

        if(sortOrder=="DESC"){
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
            Page<CategoryMetadataField> fieldList = categoryService.viewAllMetadataFields(paging);
            return new ResponseEntity<>(fieldList,HttpStatus.OK);
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CategoryMetadataField> fieldList = categoryService.viewAllMetadataFields(paging);
        return new ResponseEntity<>(fieldList,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get_all_category")
    public ResponseEntity<List<CategoryResponseDTO>> viewAllCategory(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @Pattern(regexp="DESC|ASC") @RequestParam(required = false) String sortOrder){

        if(sortOrder=="DESC"){
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
            List<CategoryResponseDTO> fieldList = categoryService.viewAllCategories(paging);
            return new ResponseEntity<>(fieldList,HttpStatus.OK);
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<CategoryResponseDTO> fieldList = categoryService.viewAllCategories(paging);
        return new ResponseEntity<>(fieldList,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get_category")
    public ResponseEntity<CategoryResponseDTO> viewCategory(@RequestParam int id){

        CategoryResponseDTO category = categoryService.viewCategory(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update_category")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO){

        String response = categoryService.updateCategoryName(categoryUpdateDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_category_meta_data_field")
    public ResponseEntity<MetaFieldValueResponseDTO> addMetaFieldValues(@RequestBody MetaFieldValueDTO metaFieldValueDTO){

        logger.info("CategoryController::addMetaFieldValues request: "+ metaFieldValueDTO.toString());
        MetaFieldValueResponseDTO response = categoryService.addMetaValues(metaFieldValueDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')") // CHANGE TO SELLER
//    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/seller")
    public ResponseEntity< List<SellerCategoryResponseDTO> > viewSellerCategory(){

        List<SellerCategoryResponseDTO> responseList = categoryService.viewSellerCategory();
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')") // CHANGE TO CUSTOMER
//    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping(value = {"/customer", "/customer/{id}"})
    public ResponseEntity<Set<Category>> viewCustomerCategory(@PathVariable("id") Optional<Integer> optionalId){

        Set<Category> responseList = categoryService.viewCustomerCategory(optionalId);
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }


//    @PreAuthorize("hasAuthority('ADMIN')") // CHANGE TO CUSTOMER
////    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @GetMapping("/filterCustomer/{id}")
//    public ResponseEntity<Set<Category>> filteredCustomerCategory(@PathVariable("id") Integer id){
//        Set<Category> responseList = categoryService.filterCustomerCategory(id);
//        return new ResponseEntity<>(responseList,HttpStatus.OK);
//    }





}

package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addNewMetaDataField(@Valid @RequestBody MetaDataFieldDTO metaDataFieldDTO){

        Locale locale = LocaleContextHolder.getLocale();
        String responseMessage = categoryService.addMetaDataField(metaDataFieldDTO, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/get_all_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MetaDataFieldResponseDTO>> getAllMetaDataField(
            @RequestParam(defaultValue = "0") Integer pageNo
            ,@RequestParam(defaultValue = "10") Integer pageSize
            ,@RequestParam(defaultValue = "id") String sortBy){

        List<MetaDataFieldResponseDTO> metaDataFields =
                categoryService.getMetaDataFields(pageNo,pageSize,sortBy);
        return new ResponseEntity<>(metaDataFields, HttpStatus.OK);
    }

    @PostMapping("/add_category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){

        Locale locale = LocaleContextHolder.getLocale();
        String responseMessage = categoryService.addCategory(categoryDTO, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/get_category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> getCategory(@NonNull @RequestParam long id){

        CategoryResponseDTO categoryResponseDTO =
                categoryService.getCategory(id);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_all_category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategory(
            @RequestParam(defaultValue = "0") Integer pageNo
            ,@RequestParam(defaultValue = "10") Integer pageSize
            ,@RequestParam(defaultValue = "id") String sortBy){

        List<CategoryResponseDTO> categories =
                categoryService.getAllCategories(pageNo,pageSize,sortBy);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/update_category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateCategory(@RequestParam long id
            ,@RequestBody CategoryUpdateDTO categoryUpdateDTO){

        Locale locale = LocaleContextHolder.getLocale();
        String responseMessage = categoryService.updateCategory(id,categoryUpdateDTO, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/add_category_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addCategoryMetaDataField(@Valid @RequestBody CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){

        Locale locale = LocaleContextHolder.getLocale();
        String responseMessage = categoryService.addCategoryMetaDataField(categoryMetaDataFieldValueDTO, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/update_category_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateCategoryMetaDataField(@Valid @RequestBody CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){

        Locale locale = LocaleContextHolder.getLocale();
        String responseMessage = categoryService.updateCategoryMetaDataField(categoryMetaDataFieldValueDTO, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/get_all_category_seller")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<SellerCategoryResponseDTO>> getAllCategorySeller(){

        List<SellerCategoryResponseDTO> categories = categoryService.getAllSellerCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = {"/get_category_customer","/get_category_customer/{id}"})
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Set<Category>> getCategoryCustomer(@PathVariable("id") Optional<Long> optionalId){

        Set<Category> categories = categoryService.getCustomerCategories(optionalId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}

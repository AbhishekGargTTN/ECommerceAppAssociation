package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addNewMetaDataField(@Valid @RequestBody MetaDataFieldDTO metaDataFieldDTO){

        String responseMessage = categoryService.addMetaDataField(metaDataFieldDTO);
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

        String responseMessage = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/get_category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> getCategory(@RequestParam long id){

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

        String responseMessage = categoryService.updateCategory(id,categoryUpdateDTO);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/add_category_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addCategoryMetaDataField(@Valid @RequestBody CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){

        String responseMessage = categoryService.addCategoryMetaDataField(categoryMetaDataFieldValueDTO);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/update_category_meta_data_field")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateCategoryMetaDataField(@Valid @RequestBody CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO){

        String responseMessage = categoryService.updateCategoryMetaDataField(categoryMetaDataFieldValueDTO);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}

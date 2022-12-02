package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Service.ProductService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add_product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO productDTO
            ,Authentication authentication){

        Locale locale = LocaleContextHolder.getLocale();
        String email= authentication.getName();
        String responseMessage = productService.addProduct(productDTO, email, locale);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/view_product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductResponseDTO> viewProduct(@RequestParam long id, Authentication authentication){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        ProductResponseDTO productResponseDTO =
                productService.viewProduct(id, email, locale);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/view_all_product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<ProductResponseDTO>> viewAllProduct(Authentication authentication){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        List<ProductResponseDTO> productResponseDTO =
                productService.viewAllProduct(email, locale);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete_product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> deleteProduct(@RequestParam long id, Authentication authentication){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        String  response = productService.deleteProduct(id, email, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update_product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String > updateProduct(@RequestParam long id, Authentication authentication
            , @Valid @RequestBody ProductUpdateDTO productUpdateDTO){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        String  response =
                productService.updateProduct(id, email, productUpdateDTO, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

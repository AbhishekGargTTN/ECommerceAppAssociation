package com.TTN.BootCamp.ECommerce_App.Controller;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductVariationDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductVariationResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductVariationUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Product;
import com.TTN.BootCamp.ECommerce_App.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PostMapping(path ="/variation")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> addProductVariation( @RequestBody ProductVariationDTO productVariationDTO){
        Locale locale = LocaleContextHolder.getLocale();
        String response = productService.addProductVariation(productVariationDTO, locale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/view_product_variation")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVariationResponseDTO> viewProductVariation(@RequestParam long id, Authentication authentication){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        ProductVariationResponseDTO productVariationResponseDTO =
                productService.viewProductVariation(id, email, locale);
        return new ResponseEntity<>(productVariationResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/view_all_product_variation")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<ProductVariationResponseDTO>>
    viewAllProductVariation(@RequestParam long id, Authentication authentication){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        List<ProductVariationResponseDTO> productResponseDTO =
                productService.viewAllProductVariation(id, email, locale);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/update_product_variation")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<String> updateProductVariation(@RequestParam long id, Authentication authentication
            , @Valid @RequestBody ProductVariationUpdateDTO productVariationUpdateDTO){

        String email= authentication.getName();
        Locale locale = LocaleContextHolder.getLocale();
        String  response =
                productService.updateProductVariation(id, email, productVariationUpdateDTO, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin_view_product")
    public ResponseEntity<ProductResponseDTO> viewAdminProduct(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        ProductResponseDTO response = productService.adminViewProduct(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin_view_all_product")
    public ResponseEntity<List<ProductResponseDTO>> viewAdminProducts(){
        Locale locale = LocaleContextHolder.getLocale();
        List<ProductResponseDTO> response = productService.adminViewAllProducts(locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/activate_product")
    public ResponseEntity<String> activateProduct(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        String response = productService.activateProduct(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deactivate_product")
    public ResponseEntity<String> deactivateProduct(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        String response = productService.deactivateProduct(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer_view_product")
    public ResponseEntity<ProductResponseDTO> viewCustomerProduct(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        ProductResponseDTO response = productService.customerViewProduct(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer_view_all_product")
    public ResponseEntity<List<ProductResponseDTO>> viewAllCustomerProducts(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        List<ProductResponseDTO> response = productService.customerViewAllProducts(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer_view_similar_product")
    public ResponseEntity<List<Product>> viewSimilarProducts(@RequestParam Long id){
        Locale locale = LocaleContextHolder.getLocale();
        List<Product> response = productService.viewSimilarProducts(id, locale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

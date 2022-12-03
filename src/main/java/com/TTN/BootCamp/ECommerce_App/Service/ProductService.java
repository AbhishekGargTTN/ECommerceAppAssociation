package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductVariationDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductVariationResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductVariationUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public interface ProductService {

    public String addProduct(ProductDTO productDTO, String email, Locale locale);

    public ProductResponseDTO viewProduct(long id,String email, Locale locale);

    public List<ProductResponseDTO> viewAllProduct(String email, Locale locale);

    public String  deleteProduct(long id,String email, Locale locale);

    public String  updateProduct(long id, String email, ProductUpdateDTO productUpdateDTO, Locale locale);

    public  String addProductVariation(ProductVariationDTO productVariationDTO,Locale locale);

    public ProductVariationResponseDTO viewProductVariation(long id, String email, Locale locale);

    public List<ProductVariationResponseDTO> viewAllProductVariation(long id, String email, Locale locale);

    public String  updateProductVariation(long id, String email, ProductVariationUpdateDTO productVariationUpdateDTO, Locale locale);

    public  ProductResponseDTO adminViewProduct(long id,Locale locale);

    public List<ProductResponseDTO> adminViewAllProducts(Locale locale);

    public String activateProduct(long id,Locale locale);

    public String deactivateProduct(long id,Locale locale);

    public ProductResponseDTO customerViewProduct(long id,Locale locale);

    public List<ProductResponseDTO> customerViewAllProducts(long id,Locale locale);

    public List<Product> viewSimilarProducts(long id, Locale locale);
}

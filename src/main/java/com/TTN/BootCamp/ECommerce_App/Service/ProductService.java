package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public String addProduct(ProductDTO productDTO,String email);

    public ProductResponseDTO viewProduct(long id,String email);

    public List<ProductResponseDTO> viewAllProduct(String email);

    public String  deleteProduct(long id,String email);

    public String  updateProduct(long id, String email, ProductUpdateDTO productUpdateDTO);
}

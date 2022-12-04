package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public interface CategoryService {

    public String addMetaDataField(MetaDataFieldDTO metaDataFieldDTO, Locale locale);

    public List<MetaDataFieldResponseDTO> getMetaDataFields(Integer pageNo, Integer pageSize, String sortBy);

    public String addCategory(CategoryDTO categoryDTO, Locale locale);

    public CategoryResponseDTO getCategory(long id);

    public List<CategoryResponseDTO> getAllCategories(Integer pageNo, Integer pageSize, String sortBy);

    public String updateCategory(long id, CategoryUpdateDTO categoryUpdateDTO, Locale locale);

    public String addCategoryMetaDataField
            (CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO, Locale locale);

    public String updateCategoryMetaDataField
            (CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO, Locale locale);

    public List<SellerCategoryResponseDTO> getAllSellerCategories();

    public Set<Category> getCustomerCategories(Optional<Long> optionalId, Locale locale);
}

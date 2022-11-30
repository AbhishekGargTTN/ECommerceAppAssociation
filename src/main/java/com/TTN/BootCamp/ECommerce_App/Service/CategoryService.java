package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetadataDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaFieldValueResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerCategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataField;
import com.TTN.BootCamp.ECommerce_App.Entity.CategoryMetadataFieldValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface CategoryService {

    public Long createCategory(CategoryDTO categoryDTO);

    public Long createMetadataField(MetadataDTO metadataDTO);

    public Page<CategoryMetadataField> viewAllMetadataFields(Pageable paging);

    public List<CategoryResponseDTO> viewAllCategories(Pageable paging);

    public String updateCategoryName(CategoryUpdateDTO categoryUpdateDTO);

    public MetaFieldValueResponseDTO addMetaValues(MetaFieldValueDTO metaFieldValueDTO);

    public List<SellerCategoryResponseDTO> viewSellerCategory();

    public Set<Category> viewCustomerCategory(Optional<Integer> optionalId);

    public CategoryResponseDTO viewCategory(int id);
}

package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.CategoryMetaDataFieldValueDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.MetaDataFieldDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CategoryResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.MetaDataFieldResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.CategoryUpdateDTO;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;

@Service
public interface CategoryService {

    public String addMetaDataField(MetaDataFieldDTO metaDataFieldDTO);

    public List<MetaDataFieldResponseDTO> getMetaDataFields(Integer pageNo, Integer pageSize, String sortBy);

    public String addCategory(CategoryDTO categoryDTO);

    public CategoryResponseDTO getCategory(long id);

    public List<CategoryResponseDTO> getAllCategories(Integer pageNo, Integer pageSize, String sortBy);

    public String updateCategory(long id, CategoryUpdateDTO categoryUpdateDTO);

    public String addCategoryMetaDataField(CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO);

    public String updateCategoryMetaDataField(CategoryMetaDataFieldValueDTO categoryMetaDataFieldValueDTO);
}

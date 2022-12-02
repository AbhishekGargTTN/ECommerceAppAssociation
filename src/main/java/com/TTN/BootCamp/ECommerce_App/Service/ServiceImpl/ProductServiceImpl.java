package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductVariationDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductVariationResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductVariationUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Exception.BadRequestException;
import com.TTN.BootCamp.ECommerce_App.Exception.ResourceNotFoundException;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import com.TTN.BootCamp.ECommerce_App.Service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryMetaDataFieldValuesRepo categoryMetaDataFieldValuesRepo;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    @Autowired
    ProductVariationRepo productVariationRepo;

    public String addProduct(ProductDTO productDTO, String email, Locale locale) {

        User user= userRepo.findUserByEmail(email);
        Product product=new Product();

        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());

        Category category= categoryRepo.findByCategoryId(productDTO.getCategoryId());
        Set<Category> subCategories= category.getSubCategories();

        if(category==null){
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        else if(!subCategories.isEmpty()){
            throw new BadRequestException(messageSource
                    .getMessage("api.error.notLeafCategory",null, locale));
        }
        else {
            product.setCategory(category);
            product.setUser(user);
            productRepo.save(product);
            return messageSource
                    .getMessage("api.response.addedSuccess",null, locale);
        }
    }

    public ProductResponseDTO viewProduct(long id, String email, Locale locale){
        Optional<Product> product= productRepo.findById(id);
        User user= userRepo.findUserByEmail(email);
        if(product.get().getUser().getId()==user.getId()) {

            if (product.isPresent()) {
                ProductResponseDTO productResponseDTO = new ProductResponseDTO();
                productResponseDTO.setId(product.get().getId());
                productResponseDTO.setName(product.get().getName());
                productResponseDTO.setBrand(product.get().getBrand());
                productResponseDTO.setDescription(product.get().getDescription());
                productResponseDTO.setActive(product.get().isActive());
                productResponseDTO.setCancellable(product.get().isCancellable());
                productResponseDTO.setReturnable(product.get().isReturnable());
                productResponseDTO.setCategory(product.get().getCategory());
                return productResponseDTO;
            }
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        throw new BadRequestException(messageSource
                .getMessage("api.error.notAuthorized",null, locale));
    }

    public List<ProductResponseDTO> viewAllProduct(String email, Locale locale){

        User user= userRepo.findUserByEmail(email);
        List<Product> products= productRepo.findByUser(user);
        List<ProductResponseDTO> productResponseDTOList= new ArrayList<>();

        if(products!=null){

        for(Product product: products){

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setBrand(product.getBrand());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setActive(product.isActive());
            productResponseDTO.setCancellable(product.isCancellable());
            productResponseDTO.setReturnable(product.isReturnable());
            productResponseDTO.setCategory(product.getCategory());
            productResponseDTOList.add(productResponseDTO);

        }
        return productResponseDTOList;
        }
        throw new ResourceNotFoundException(messageSource
                .getMessage("api.error.resourceNotFound",null, locale));
    }

    public String deleteProduct(long id,String email, Locale locale){

        Optional<Product> product= productRepo.findById(id);
        User user= userRepo.findUserByEmail(email);
        if(product.get().getUser().getId()==user.getId()) {

            if (product.isPresent()) {
                productRepo.delete(product.get());
                return messageSource
                        .getMessage("api.response.deletedSuccess",null, locale);
            }
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        throw new BadRequestException(messageSource
                .getMessage("api.error.notAuthorized",null, locale));
    }

    public String  updateProduct(long id, String email, ProductUpdateDTO productUpdateDTO, Locale locale){
        Product product= productRepo.findByProductId(id);
        User user= userRepo.findUserByEmail(email);
        if(product.getUser().getId()==user.getId()) {

            if (product!=null) {
                BeanUtils.copyProperties(productUpdateDTO, product, FilterProperties.getNullPropertyNames(productUpdateDTO));
                productRepo.save(product);
                return messageSource
                        .getMessage("api.response.updateSuccess",null, locale);
            }
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        throw new BadRequestException(messageSource
                .getMessage("api.error.notAuthorized",null, locale));
    }

    public String addProductVariation(ProductVariationDTO productVariationDTO,Locale locale){

        System.out.println(productVariationDTO.toString());
        Optional<Product> product = productRepo.findById(productVariationDTO.getProductId());
        if(product.isEmpty()){
            throw new BadRequestException(messageSource.getMessage("api.error.invalidId",null,locale));
        }

        // check product status
        if (product.get().isActive() || product.get().isDeleted()){
            throw new BadRequestException(messageSource.getMessage("api.error.productInactiveDeleted",null,locale));
        }

        Category associatedCategory = product.get().getCategory();

        // check if provided field and their values
        // are among the existing metaField-values defined for the category

        Map<String, Set<String>> requestMetadata = productVariationDTO.getMetadata();
        System.out.println(requestMetadata);
        List<String> requestKeySet = requestMetadata.keySet().stream().collect(Collectors.toList());
        System.out.println(requestKeySet);

        List<CategoryMetaDataFieldValues> associatedMetadata = categoryMetaDataFieldValuesRepo.findByCategory(associatedCategory);
        List<String> associatedKeySet = new ArrayList<>();

        for(CategoryMetaDataFieldValues metadataFieldValue : associatedMetadata){
            CategoryMetaDataField field = metadataFieldValue.getCategoryMetaDataField();
            String fieldName = field.getName();
            associatedKeySet.add(fieldName);
        }

        // check if metadataField are associated with the category
        if(Collections.indexOfSubList(associatedKeySet,requestKeySet)==-1){
            requestKeySet.removeAll(associatedKeySet);
            String errorResponse = messageSource.getMessage("api.error.fieldNotAssociated",null,locale);
            errorResponse=errorResponse.replace("[[fields]]", requestKeySet.toString());
            throw new BadRequestException(errorResponse);
        }

        // check if metadataValues are associated for given category, metadataField
        for(String key: requestKeySet){
            CategoryMetaDataField categoryMetadataField = categoryMetaDataFieldRepo.findByName(key);
            CategoryMetaDataFieldValues categoryMetadataFieldValue = categoryMetaDataFieldValuesRepo.findByCategoryAndCategoryMetaDataField(associatedCategory,categoryMetadataField);
            String associatedStringValues = categoryMetadataFieldValue.getValue();
            String associatedField = categoryMetadataFieldValue.getCategoryMetaDataField().getName();
            Set<String> associatedValues =Set.of(associatedStringValues.split(","));
            Set<String> requestValues = requestMetadata.get(key);


            if(!associatedValues.containsAll(requestValues)){
                requestValues.removeAll(associatedValues);
                String errorResponse = messageSource.getMessage("api.error.valueNotAssociated",null,locale);
                errorResponse=errorResponse.replace("[[value]]",associatedField+"-"+requestValues);
                throw new BadRequestException(errorResponse);
            }
        }
        ProductVariation productVariation = new ProductVariation();
        BeanUtils.copyProperties(productVariationDTO,productVariation);
        productVariation.setProduct(product.get());
        productVariationRepo.save(productVariation);

        return messageSource.getMessage("api.response.addedSuccess",null,locale);
    }

    public ProductVariationResponseDTO viewProductVariation(long id, String email, Locale locale){
        Optional<ProductVariation> productVariation= productVariationRepo.findById(id);
        Optional<Product> product= productRepo.findById(productVariation.get().getProduct().getId());
        User user= userRepo.findUserByEmail(email);
        if(product.get().getUser().getId()==user.getId()) {

            if (product.isPresent() && productVariation.isPresent()) {
                ProductVariationResponseDTO productVariationResponseDTO = new ProductVariationResponseDTO();
                productVariationResponseDTO.setId(productVariation.get().getId());
                productVariationResponseDTO.setProductId(productVariation.get().getProduct().getId());
                productVariationResponseDTO.setPrice(productVariation.get().getPrice());
                productVariationResponseDTO.setQuantityAvailable(productVariation.get().getQuantityAvailable());
                productVariationResponseDTO.setMetadata(productVariation.get().getMetaData());
                return productVariationResponseDTO;
            }
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        throw new BadRequestException(messageSource
                .getMessage("api.error.notAuthorized",null, locale));
    }

    public List<ProductVariationResponseDTO> viewAllProductVariation(long id, String email, Locale locale){
        User user= userRepo.findUserByEmail(email);
        Optional<Product> product= productRepo.findById(id);
        List<ProductVariation> productVariations= productVariationRepo.findByProduct(product.get());
        List<ProductVariationResponseDTO> productVariationResponseDTOList= new ArrayList<>();

        if(product!=null&& productVariations!=null){

            for(ProductVariation productVariation: productVariations){

                ProductVariationResponseDTO productVariationResponseDTO = new ProductVariationResponseDTO();
                productVariationResponseDTO.setId(productVariation.getId());
                productVariationResponseDTO.setProductId(productVariation.getProduct().getId());
                productVariationResponseDTO.setPrice(productVariation.getPrice());
                productVariationResponseDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
                productVariationResponseDTO.setMetadata(productVariation.getMetaData());
                productVariationResponseDTOList.add(productVariationResponseDTO);

            }
            return productVariationResponseDTOList;
        }
        throw new ResourceNotFoundException(messageSource
                .getMessage("api.error.resourceNotFound",null, locale));
    }

    public String  updateProductVariation(long id, String email
            ,ProductVariationUpdateDTO productVariationUpdateDTO, Locale locale){

        Optional<ProductVariation> productVariation= productVariationRepo.findById(id);
        Product product= productRepo.findByProductId(productVariation.get().getProduct().getId());
        User user= userRepo.findUserByEmail(email);
        if(product.getUser().getId()==user.getId()) {

            if (product!=null&&productVariation.isPresent()) {
                BeanUtils.copyProperties(productVariationUpdateDTO, productVariation, FilterProperties.getNullPropertyNames(productVariationUpdateDTO));
                productVariationRepo.save(productVariation.get());
                return messageSource
                        .getMessage("api.response.updateSuccess",null, locale);
            }
            throw new ResourceNotFoundException(messageSource
                    .getMessage("api.error.resourceNotFound",null, locale));
        }
        throw new BadRequestException(messageSource
                .getMessage("api.error.notAuthorized",null, locale));
    }
}

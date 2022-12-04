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
import com.TTN.BootCamp.ECommerce_App.Service.MailService;
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

    @Autowired
    MailService mailService;

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
//            mailService.sendNewProductMail(product);
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
                productVariationRepo.deleteAllInBatch(productVariationRepo.findByProduct(product.get()));
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

        Optional<Product> product = productRepo.findById(productVariationDTO.getProductId());
        if(product.isEmpty()){
            throw new BadRequestException(messageSource.getMessage("api.error.invalidId",null,Locale.ENGLISH));
        }

        // check product status
        if (product.get().isActive() || product.get().isDeleted()){
            throw new BadRequestException(messageSource.getMessage("api.error.productInactiveDeleted",null,Locale.ENGLISH));
        }

        Category associatedCategory = product.get().getCategory();

        Map<String, Set<String>> requestMetadata = productVariationDTO.getMetadata();
        List<String> requestKeySet = requestMetadata.keySet().stream().collect(Collectors.toList());

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
            String errorResponse = messageSource.getMessage("api.error.fieldNotAssociated",null,Locale.ENGLISH);
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
                String errorResponse = messageSource.getMessage("api.error.valueNotAssociated",null,Locale.ENGLISH);
                errorResponse=errorResponse.replace("[[value]]",associatedField+"-"+requestValues);
                throw new BadRequestException(errorResponse);
            }
        }
        ProductVariation productVariation = new ProductVariation();
        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setMetaData(productVariationDTO.getMetadata());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable());
        productVariation.setProduct(product.get());
        productVariationRepo.save(productVariation);

        return messageSource.getMessage("api.response.addedSuccess",null,Locale.ENGLISH);
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

        System.out.println(productVariationUpdateDTO.toString());

        Product product= productRepo.findByProductId(productVariation.get().getProduct().getId());
        User user= userRepo.findUserByEmail(email);
        if(product.getUser().getId()==user.getId()) {

            if (product!=null&&productVariation.isPresent()) {
//                BeanUtils.copyProperties(productVariationUpdateDTO, productVariation, FilterProperties.getNullPropertyNames(productVariationUpdateDTO));

                if(productVariationUpdateDTO.getMetadata()!= null){
                    productVariation.get().setMetaData(productVariationUpdateDTO.getMetadata());
                }
                if(productVariationUpdateDTO.getPrice()!= null){
                    productVariation.get().setPrice(productVariationUpdateDTO.getPrice());
                }
                if(productVariationUpdateDTO.getQuantityAvailable()!= null){
                    productVariation.get().setQuantityAvailable(productVariationUpdateDTO.getQuantityAvailable());
                }

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

    public  ProductResponseDTO adminViewProduct(long id,Locale locale){
        Optional<Product> product = productRepo.findById(id);
        if (product == null || product.isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("api.error.invalidProductId",null,locale));
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.get().getId());
        productResponseDTO.setName(product.get().getName());
        productResponseDTO.setBrand(product.get().getBrand());
        productResponseDTO.setDescription(product.get().getDescription());
        productResponseDTO.setActive(product.get().isActive());
        productResponseDTO.setReturnable(product.get().isReturnable());
        productResponseDTO.setCancellable(product.get().isCancellable());
        productResponseDTO.setCategory(product.get().getCategory());

        return productResponseDTO;
    }

    public List<ProductResponseDTO> adminViewAllProducts(Locale locale){
        List<Product> products = productRepo.findAll();

        if(products.isEmpty()){
            throw new BadRequestException(messageSource.getMessage("api.error.productNotFound",null,locale));
        }

        List<ProductResponseDTO> productResponseDTOList= new ArrayList<>();
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

    public String activateProduct(long id,Locale locale){
        Optional<Product> product = productRepo.findById(id);
        if (product == null || product.isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("api.error.invalidProductId",null,locale));
        }
        if (product.get().isActive()){
            throw new BadRequestException(messageSource.getMessage("api.error.productAlreadyActive",null,locale));

        }else{
            product.get().setActive(true);
            productRepo.save(product.get());

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            BeanUtils.copyProperties(product.get(),productResponseDTO);
            User productOwner = product.get().getUser();
            mailService.sendProductActivationMail(productResponseDTO,productOwner);

            return messageSource.getMessage("api.response.activationSuccess",null,locale);
        }
    }

    public String deactivateProduct(long id,Locale locale){
        Optional<Product> product = productRepo.findById(id);
        if (product == null || product.isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("api.error.invalidProductId",null,locale));
        }
        if (product.get().isActive()==false){
            throw new BadRequestException(messageSource.getMessage("api.error.productAlreadyInactive",null,locale));

        } else{
            product.get().setActive(false);
            productRepo.save(product.get());

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            BeanUtils.copyProperties(product.get(), productResponseDTO);
            User productOwner = product.get().getUser();
            mailService.sendProductDeactivationMail(productResponseDTO,productOwner);

            return messageSource.getMessage("api.response.deactivationSuccess",null,Locale.ENGLISH);
        }
    }

    public ProductResponseDTO customerViewProduct(long id,Locale locale){
        Optional<Product> product = productRepo.findById(id);
        if (product == null || product.isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("api.error.invalidProductId",null,locale));
        }
        // check product status
        if (product.get().isDeleted() || product.get().isActive() == false ){
            throw new BadRequestException(messageSource.getMessage("api.error.productInactiveDeleted",null,locale));
        }

        // convert to appropriate DTO
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.get().getId());
        productResponseDTO.setName(product.get().getName());
        productResponseDTO.setBrand(product.get().getBrand());
        productResponseDTO.setDescription(product.get().getDescription());
        productResponseDTO.setActive(product.get().isActive());
        productResponseDTO.setReturnable(product.get().isReturnable());
        productResponseDTO.setCancellable(product.get().isCancellable());
        productResponseDTO.setCategory(product.get().getCategory());

        return productResponseDTO;
    }

    public List<ProductResponseDTO> customerViewAllProducts(long id,Locale locale){
        Category category = categoryRepo.findById(id).orElseThrow(() -> new BadRequestException(
                messageSource.getMessage("api.error.invalidId", null, locale)
        ));
        if (!category.getSubCategories().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("api.error.notLeafNode",null,locale));
        }

        List<Product> products = productRepo.findByCategory(category);

        if(products.isEmpty()){
            throw new BadRequestException(messageSource.getMessage("api.error.productNotFound",null,locale));
        }

        List<ProductResponseDTO> productResponseDTOList= new ArrayList<>();
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

    public List<Product> viewSimilarProducts(long id,Locale locale){
        Optional<Product> product = productRepo.findById(id);
        if (product == null || product.isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("api.error.invalidProductId",null,locale));
        }

        if (product.get().isDeleted() || product.get().isActive() == false ){
            throw new BadRequestException(messageSource.getMessage("api.error.productInactiveDeleted",null,locale));
        }

        Category associatedCategory = product.get().getCategory();
        List<Product> similarProducts = new ArrayList<>();

        List<Product> siblingProducts = productRepo.findByCategory(associatedCategory);

        for(Product individualProduct: siblingProducts){
            similarProducts.add(individualProduct);
        }

        if(similarProducts.size()==1){
            throw new BadRequestException(messageSource.getMessage("api.error.similarProducts",null,locale));
        }

        return similarProducts;
    }
}
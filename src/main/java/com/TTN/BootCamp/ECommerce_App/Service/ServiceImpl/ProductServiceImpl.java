package com.TTN.BootCamp.ECommerce_App.Service.ServiceImpl;

import com.TTN.BootCamp.ECommerce_App.Config.FilterProperties;
import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ProductDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.ProductResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO.ProductUpdateDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.TTN.BootCamp.ECommerce_App.Entity.Product;
import com.TTN.BootCamp.ECommerce_App.Entity.User;
import com.TTN.BootCamp.ECommerce_App.Exception.BadRequestException;
import com.TTN.BootCamp.ECommerce_App.Repository.CategoryRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.ProductRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.SellerRepo;
import com.TTN.BootCamp.ECommerce_App.Repository.UserRepo;
import com.TTN.BootCamp.ECommerce_App.Service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
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

    public String addProduct(ProductDTO productDTO, String email) {

        User user= userRepo.findUserByEmail(email);
        Product product=new Product();

        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());

        Category category= categoryRepo.findByCategoryId(productDTO.getCategoryId());
        Set<Category> subCategories= category.getSubCategories();

        if(category==null){
            throw new BadRequestException("Category does not exist");
        }
        else if(!subCategories.isEmpty()){
            throw new BadRequestException("This is not a leaf category" +
                    ", product can be added only to leaf category");
        }
        else {
            product.setCategory(category);
            product.setUser(user);
            productRepo.save(product);
            return "Product added Successfully";
        }
    }

    public ProductResponseDTO viewProduct(long id, String email){
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
            throw new BadRequestException("Product not found");
        }
        throw new BadRequestException("You are not Authorized to view the products of other sellers");
    }

    public List<ProductResponseDTO> viewAllProduct(String email){

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
        throw new BadRequestException("You are not Authorized to view the products of other sellers");
    }

    public String deleteProduct(long id,String email){

        Optional<Product> product= productRepo.findById(id);
        User user= userRepo.findUserByEmail(email);
        if(product.get().getUser().getId()==user.getId()) {

            if (product.isPresent()) {
                productRepo.delete(product.get());
                return "Product Deleted Successfully";
            }
            throw new BadRequestException("Product not found");
        }
        throw new BadRequestException("You are not Authorized to delete the products of other sellers");
    }

    public String  updateProduct(long id, String email, ProductUpdateDTO productUpdateDTO){
        Product product= productRepo.findByProductId(id);
        User user= userRepo.findUserByEmail(email);
        if(product.getUser().getId()==user.getId()) {

            if (product!=null) {
                BeanUtils.copyProperties(productUpdateDTO, product, FilterProperties.getNullPropertyNames(productUpdateDTO));
                productRepo.save(product);
                return "Product updated Successfully";
            }
            throw new BadRequestException("Product not found");
        }
        throw new BadRequestException("You are not Authorized to modify the products of other sellers");
    }


}

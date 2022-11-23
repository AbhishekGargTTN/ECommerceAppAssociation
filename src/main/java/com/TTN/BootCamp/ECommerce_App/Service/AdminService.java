package com.TTN.BootCamp.ECommerce_App.Service;

import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.CustomerResponseDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO.SellerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    public List<CustomerResponseDTO> listAllCustomers(Integer pageNo, Integer pageSize, String sortBy);

    public List<SellerResponseDTO> listAllSellers(Integer pageNo, Integer pageSize, String sortBy);

    public String activateUser(Long userId);

    public String deactivateUser(Long userId);
}

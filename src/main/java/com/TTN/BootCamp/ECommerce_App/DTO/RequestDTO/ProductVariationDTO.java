package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.MetaData;
import com.TTN.BootCamp.ECommerce_App.Entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
public class ProductVariationDTO {

    private long quantityAvailable;

    private double price;

    private MetaData metaData;

    private Long productId;
}

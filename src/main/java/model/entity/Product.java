package model.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String pUuid;
    private String pName;
    private Double price;
    private Integer qty;
    private Boolean isDeleted;



}

package model.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Product {
    private Integer id;
    private String pUuid;
    private String pName;
    private Double price;
    private Integer qty;
    private Boolean isDeleted;
}

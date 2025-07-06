package model.entity;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Product {
    private Integer id;
    private String pUuid;
    private String pName;
    private BigDecimal price;
    private Integer qty;
    private Boolean isDeleted;
}

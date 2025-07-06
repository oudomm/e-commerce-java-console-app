package model.dto;

import java.math.BigDecimal;

public record ProductResponseDto2(
        Integer id,
        String pUuid,
        String category,
        String pName,
        BigDecimal price,
        Integer qty
) {

}

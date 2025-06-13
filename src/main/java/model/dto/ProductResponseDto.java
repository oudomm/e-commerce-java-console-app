package model.dto;

public record ProductResponseDto(
        String pUuid,
        String category,
        String pName,
        Double price,
        Integer qty
) {

}

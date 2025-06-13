package model.dto;

public record ProductResponseDto(
        String pUuid,
        String pName,
        Double price,
        Integer qty
) {

}

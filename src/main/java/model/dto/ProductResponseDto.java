package model.dto;

public record ProductResponseDto(
        String category,
        String productName,
        Double price,
        Integer qty) {

}

package model.service;

import model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> productSearch(String keyword);

}
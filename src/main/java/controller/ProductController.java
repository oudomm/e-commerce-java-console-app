package controller;

import model.dto.ProductResponseDto;
import model.service.ProductService;
import model.service.ProductServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService = new ProductServiceImpl();
    public List<ProductResponseDto> productSearch(String keyword){
        return productService.productSearch(keyword);
    }

}

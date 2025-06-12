package controller;
import model.entity.Product;
import model.dto.ProductResponseDto;
import model.repository.ProductRepository;
import mapper.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductController {
    private final ProductRepository productRepository;

    public ProductController() {
        this.productRepository = new ProductRepository();
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::MapFromProductToProductResponseDto)
                .toList();
    }

    public Map<String, List<ProductResponseDto>> getProductsGroupedByCategory() {
        return getAllProducts().stream()
                .collect(Collectors.groupingBy(ProductResponseDto::category));
    }

}

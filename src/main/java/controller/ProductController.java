package controller;
import model.dto.ProductResponseDto2;
import model.entity.Product;
import model.dto.ProductResponseDto;
import model.repository.ProductRepository;
import mapper.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.dto.ProductResponseDto;
import model.service.ProductService;
import model.service.ProductServiceImpl;

import java.util.List;

public class ProductController {
    private final ProductRepository productRepository;
    private final ProductServiceImpl productServiceImp = new ProductServiceImpl();

    public ProductController() {
        this.productRepository = new ProductRepository();
    }

    public List<ProductResponseDto> getAllProducts(int roleNumber,int numberOfProducts) {
        List<Product> products = productRepository.findAll(roleNumber,numberOfProducts);
        return products.stream()
                .map(ProductMapper::mapFromProductToProductResponseDto)
                .toList();
    }

    public Map<String, List<ProductResponseDto>> getProductsGroupedByCategory(int roleNumber,int numberOfProducts) {
        return getAllProducts(roleNumber,numberOfProducts).stream()
                .collect(Collectors.groupingBy(ProductResponseDto::category));
    }

    private final ProductService productService = new ProductServiceImpl();
    public List<ProductResponseDto> productSearch(String keyword){
        return productService.productSearch(keyword);
    }

    public void insertProductAsBatch(int batchSize) {
        productServiceImp.insertProductAsBatch(batchSize);
    }
    public List<ProductResponseDto2> readProductAsBatch(int batchSize, int offset) {
        return productServiceImp.readProductAsBatch(batchSize,offset);
    }
    public ProductResponseDto getProductResponseDto(List<Product> responseDto,String uuid) {
        return ProductServiceImpl.findProductResponseDtoList(responseDto,uuid);
    }

}

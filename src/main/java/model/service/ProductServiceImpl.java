package model.service;

import mapper.ProductMapper;
import model.dto.ProductCreateDto;
import model.dto.ProductResponseDto;
import model.dto.ProductResponseDto2;
import model.entity.CartItem;
import model.entity.Product;
import model.repository.ProductRepository;
import controller.ProductController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService{
private final ProductRepository productRepository = new ProductRepository();

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductResponseDto insertNewProduct(ProductCreateDto productModel) {
        return null;
    }

    @Override
    public ProductResponseDto getProductByName(String productName) {
        return null;
    }

    @Override
    public ProductResponseDto getProductByCategory(String productCategory) {
        return null;
    }

    @Override
    public CartItem addToCart(String uuid, Integer quantity) {
        return null;
    }

    @Override
    public List<CartItem> getAllCartProducts() {
        return List.of();
    }

    @Override
    public List<ProductResponseDto> productSearch(String keyword) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        try {
            Product exactProduct = productRepository.searchByExactName(keyword);
            if (exactProduct != null) {
                productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto(exactProduct));
            }
            List<Product> partialProducts = productRepository.searchByPartialName(keyword);
            for (Product p : partialProducts) {
                productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto(p));
            }
            if (keyword.length() == 1) {
                List<Product> startingProducts = productRepository.searchByStartingLetter(keyword.charAt(0));
                for (Product p : startingProducts) {
                    productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto(p));
                }

                List<Product> endingProducts = productRepository.searchByEndingLetter(keyword.charAt(0));
                for (Product p : endingProducts) {
                    productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto(p));
                }
            }

            List<Product> categoryProducts = productRepository.searchByCategory(keyword);
            for (Product p : categoryProducts) {
                productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto(p));
            }

        } catch (Exception e) {
            System.out.println("[!] Error during search: " + e.getMessage());
        }

        return productResponseDtoList;
    }
    public void insertProductAsBatch(int batchSize  ) {
        productRepository.insertProductAsBatch(batchSize);
    }
    public List<ProductResponseDto2> readProductAsBatch(int batchSize, int offset) {
        List<ProductResponseDto2> productResponseDtoList = new ArrayList<>();
        productRepository.readProductAsBatch(batchSize,offset).forEach(product -> productResponseDtoList.add(ProductMapper.mapFromProductToProductResponseDto2(product)));
        return productResponseDtoList;
    }

    public static ProductResponseDto findProductResponseDtoList(List<Product> products,String productUuid) {
        return products.stream().map(ProductMapper::mapFromProductToProductResponseDto).collect(Collectors.toList()).stream().filter(productResponseDto1 -> productResponseDto1.pUuid().equals(productUuid)).findFirst().get();
    }
}

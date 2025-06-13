package model.service;

import mapper.ProductMapper;
import model.dto.ProductCreateDto;
import model.dto.ProductResponseDto;
import model.entity.CartItem;
import model.entity.Product;
import model.repository.ProductRepository;
import controller.ProductController;

import java.util.ArrayList;
import java.util.List;

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
}

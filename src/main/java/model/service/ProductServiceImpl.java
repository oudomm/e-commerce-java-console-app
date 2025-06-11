package model.service;

import mapper.ProductMapper;
import model.dto.ProductResponseDto;
import model.entity.Product;
import model.repository.ProductRepository;
import controller.ProductController;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService{
private final ProductRepository productRepository = new ProductRepository();

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

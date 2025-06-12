package model.service;

import model.dto.ProductCreateDto;
import model.dto.ProductResponseDto;
import model.entity.CartItem;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto insertNewProduct(ProductCreateDto productModel);
    ProductResponseDto getProductByName(String productName);
    ProductResponseDto getProductByCategory(String productCategory);
    CartItem addToCart(String uuid, Integer quantity);
    List<CartItem> getAllCartProducts();
}

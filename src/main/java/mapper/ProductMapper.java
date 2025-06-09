package mapper;

import model.dto.ProductResponseDto;
import model.entity.Product;

public class ProductMapper {
    public static ProductResponseDto MapFromProductToProductResponseDto(Product product) {
        return new ProductResponseDto(product.getPName());
    }
}

package mapper;

import model.dto.ProductResponseDto;
import model.entity.Product;

public class ProductMapper {
    public static ProductResponseDto MapFromProductToProductResponseDto(Product product) {
        String[] parts = product.getPName().split(" - ", 2);
        String category = parts.length == 2 ? parts[0].trim() : "Uncategorized";
        String productName = parts.length == 2 ? parts[1].trim() : product.getPName();

        return new ProductResponseDto(
                category,
                productName,
                product.getPrice(),
                product.getQty()
        );
    }
}

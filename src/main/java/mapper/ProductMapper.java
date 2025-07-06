package mapper;

import model.dto.ProductResponseDto;
import model.dto.ProductResponseDto2;
import model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {

    public static Product mapProduct(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setId(result.getInt("id"));
        product.setPUuid(result.getString("uuid"));
        product.setPName(result.getString("p_name"));
        product.setPrice(result.getBigDecimal("price"));
        product.setQty(result.getInt("qty"));
        return product;
    }

    public static ProductResponseDto mapFromProductToProductResponseDto(Product product) {
        String[] parts = product.getPName().split(" - ", 2);
        String category = parts.length == 2 ? parts[0].trim() : "Uncategorized";
        String productName = parts.length == 2 ? parts[1].trim() : product.getPName();

        return new ProductResponseDto(
                product.getPUuid(),
                category,
                productName,
                product.getPrice(),
                product.getQty()
        );
    }
    public static ProductResponseDto2 mapFromProductToProductResponseDto2(Product product) {
        String[] parts = product.getPName().split(" - ", 2);
        String category = parts.length == 2 ? parts[0].trim() : "Uncategorized";
        String productName = parts.length == 2 ? parts[1].trim() : product.getPName();

        return new ProductResponseDto2(
                product.getId(),
                product.getPUuid(),
                category,
                productName,
                product.getPrice(),
                product.getQty()
        );
    }
}

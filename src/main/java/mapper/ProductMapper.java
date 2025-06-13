package mapper;

import model.dto.ProductCreateDto;
import model.dto.ProductResponseDto;
import model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public static ProductResponseDto mapFromProductToProductResponseDto(Product product){
        return new ProductResponseDto(product.getPUuid()
                , product.getPName(), product.getPrice(), product.getQty());
    }
    public static Product mapProduct(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setId(result.getInt("id"));
        product.setPUuid(result.getString("uuid"));
        product.setPName(result.getString("p_name"));
        product.setPrice(result.getDouble("price"));
        product.setQty(result.getInt("qty"));
        return product;
    }
}

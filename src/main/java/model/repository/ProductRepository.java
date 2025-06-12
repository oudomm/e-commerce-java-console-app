package model.repository;

import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product, Integer> {

    private final String jdbcUrl = "jdbc:postgresql://35.224.242.247:5432/postgres";
    private final String username = "postgres";
    private final String password = "houygood@123";

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        String query = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products WHERE is_deleted = false";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("p_uuid"),
                        rs.getString("p_name"),
                        rs.getDouble("price"),
                        rs.getInt("qty"),
                        rs.getBoolean("is_deleted")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product save(Product product) {
        // You can implement insert logic here if needed
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        // You can implement delete logic here if needed
        return 0;
    }

    public Product findProductById(Integer id) {
        String query = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("p_uuid"),
                            rs.getString("p_name"),
                            rs.getDouble("price"),
                            rs.getInt("qty"),
                            rs.getBoolean("is_deleted")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

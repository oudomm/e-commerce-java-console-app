package model.repository;

import model.entity.Product;
import utils.DatabaseConnectionConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static mapper.ProductMapper.mapProduct;

public class ProductRepository implements Repository<Product, Integer> {

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products WHERE is_deleted = false";

        try (Connection conn = DatabaseConnectionConfig.getConnection()) {
            if (conn == null) {
                System.err.println("[!] Failed to establish database connection");
                return products;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

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
            }

        } catch (SQLException e) {
            System.err.println("[!] Error during findAll: " + e.getMessage());
            System.err.println("[!] Please check your database connection and try again");
        }

        return products;
    }

    @Override
    public Product save(Product product) {
        String sql = """
                INSERT INTO products(id, p_uuid, p_name, price, qty, is_deleted)
                VALUES(?,?,?,?,?,?)
                """;
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, product.getId());
            pre.setString(2, product.getPUuid());
            pre.setString(3, product.getPName());
            pre.setDouble(4, product.getPrice());
            pre.setInt(5, product.getQty());
            pre.setBoolean(6, product.getIsDeleted());
            int rowAffected = pre.executeUpdate();
            return rowAffected > 0 ? product : null;
        } catch (Exception exception) {
            System.out.println("[!] Error during insert data to Product table: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String sql = "UPDATE products SET is_deleted = true WHERE id = ?";
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            int rowsAffected = pre.executeUpdate();
            return rowsAffected > 0 ? id : null;
        } catch (Exception e) {
            System.out.println("[!] Error during delete: " + e.getMessage());
        }
        return null;
    }

    public Product findProductById(Integer id) {
        return findById(id); // Delegate to existing findById method
    }

    public Product findById(Integer id) {
        String sql = """
        SELECT * FROM products
        WHERE id = ? AND is_deleted = false
    """;

        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet result = pre.executeQuery();

            if (result.next()) {
                return mapProduct(result);
            }

        } catch (Exception e) {
            System.out.println("[!] Error during findById: " + e.getMessage());
        }

        return null;
    }

    public Product searchByExactName(String name) {
        String sql = """
            SELECT * FROM products
            WHERE LOWER(p_name) = LOWER(?) AND is_deleted = false
        """;
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, name);
            ResultSet result = pre.executeQuery();
            if (result.next()) {
                return mapProduct(result);
            }
        } catch (Exception e) {
            System.out.println("[!] Error during search by exact name: " + e.getMessage());
        }
        return null;
    }

    public List<Product> searchByPartialName(String keyword) {
        String sql = """
            SELECT * FROM products
            WHERE LOWER(p_name) LIKE LOWER(?) AND is_deleted = false
        """;
        List<Product> products = new ArrayList<>();
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + keyword + "%");
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                products.add(mapProduct(result));
            }
        } catch (Exception e) {
            System.out.println("[!] Error during partial name search: " + e.getMessage());
        }
        return products;
    }

    public List<Product> searchByStartingLetter(char letter) {
        String sql = """
            SELECT * FROM products
            WHERE LOWER(p_name) LIKE LOWER(?) AND is_deleted = false
        """;
        List<Product> products = new ArrayList<>();
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, letter + "%");
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                products.add(mapProduct(result));
            }
        } catch (Exception e) {
            System.out.println("[!] Error during starting letter search: " + e.getMessage());
        }
        return products;
    }

    public List<Product> searchByEndingLetter(char letter) {
        String sql = """
            SELECT * FROM products
            WHERE LOWER(p_name) LIKE LOWER(?) AND is_deleted = false
        """;
        List<Product> products = new ArrayList<>();
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + letter);
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                products.add(mapProduct(result));
            }
        } catch (Exception e) {
            System.out.println("[!] Error during ending letter search: " + e.getMessage());
        }
        return products;
    }

    public List<Product> searchByCategory(String category) {
        String sql = """
            SELECT * FROM products
            WHERE LOWER(category) = LOWER(?) AND is_deleted = false
        """;
        List<Product> products = new ArrayList<>();
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, category);
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                products.add(mapProduct(result));
            }
        } catch (Exception e) {
            System.out.println("[!] Error during category search: " + e.getMessage());
        }
        return products;
    }
}
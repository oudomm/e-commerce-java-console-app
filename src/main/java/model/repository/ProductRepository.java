package model.repository;

import com.github.javafaker.Faker;
import model.entity.Product;
import utils.DatabaseConnectionConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static mapper.ProductMapper.mapProduct;

public class ProductRepository implements Repository<Product, Integer> {

    @Override
    public List<Product> findAll(int roleNumber,int numberOfProducts) {
        List<Product> products = new ArrayList<>();

        String query = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products " +
                "WHERE is_deleted = false " +
                "LIMIT " + numberOfProducts + " OFFSET " + roleNumber;

        try (Connection conn = DatabaseConnectionConfig.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("p_uuid"),
                            rs.getString("p_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("qty"),
                            rs.getBoolean("is_deleted")
                    );
                    products.add(product);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product save(Product product) {
        String sql = """
                INSERT INTO products( id ,p_uuid, p_name, price, qty, isDeleted)
                VALUES(?,?,?,?,?,?)
                """;
        try(Connection con = DatabaseConnectionConfig.getConnection()){
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1,product.getId());
            pre.setString(2, product.getPUuid());
            pre.setString(3, product.getPName());
            pre.setBigDecimal(4,product.getPrice());
            pre.setInt(5,product.getQty());
            pre.setBoolean(6,product.getIsDeleted());
            int rowAffected = pre.executeUpdate();
            return rowAffected>0 ? product: null;
        }catch (Exception exception){
            System.out.println("[!] Error during insert data to Product table: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }
    public Product findProductById(Integer id) {
        String query = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("p_uuid"),
                            rs.getString("p_name"),
                            rs.getBigDecimal("price"),
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

    public Product findById(Integer id) {
        String sql = """
        SELECT * FROM products
        WHERE id = ?
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
            WHERE LOWER(p_name) = LOWER(?)
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
            WHERE LOWER(p_name) LIKE LOWER(?)
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
            WHERE LOWER(p_name) LIKE LOWER(?)
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
            WHERE LOWER(p_name) LIKE LOWER(?)
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
            WHERE LOWER(category) = LOWER(?)
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

    public void insertProductAsBatch(int batchSize) {
        Faker faker = new Faker();
        String sql = "INSERT INTO products(p_uuid, p_name, price, qty, is_deleted) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnectionConfig.getConnection()) {
            assert con != null;
            PreparedStatement pre = con.prepareStatement(sql);

            for (int i = 0; i < batchSize; i++) {
                pre.setString(1, UUID.randomUUID().toString());
                pre.setString(2, faker.commerce().productName());
                pre.setBigDecimal(3, new BigDecimal(faker.commerce().price()).setScale(2, RoundingMode.HALF_UP));
                pre.setInt(4, faker.number().numberBetween(0, 100));
                pre.setBoolean(5, false);
                pre.addBatch();

                // Execute every 1000 rows (adjust as needed)
                if (i % 1000 == 0 && i > 0) {
                    pre.executeBatch();
                    pre.clearBatch();
                }
            }
            pre.executeBatch(); // Execute remaining batch
            pre.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Product>  readProductAsBatch(int batchSize,int offset) {
        List<Product> products = new ArrayList<>(10_000_000);
        String sql = "SELECT id, p_uuid, p_name, price, qty, is_deleted FROM products LIMIT ? OFFSET ?";
        try (Connection connection = DatabaseConnectionConfig.getConnection()){
            boolean hasMore = true;
            while (hasMore) {
                assert connection != null;
                try (PreparedStatement pre = connection.prepareStatement(sql)) {
                    pre.setInt(1, batchSize); // ✅ LIMIT
                    pre.setInt(2, offset);    // ✅ OFFSET

                    try (ResultSet rs = pre.executeQuery()) {
                        int rowsRead = 0;
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String uuid = rs.getString("p_uuid");
                            String name = rs.getString("p_name");
                            BigDecimal price = rs.getBigDecimal("price");
                            int qty = rs.getInt("qty");
                            boolean isDeleted = rs.getBoolean("is_deleted");
                            Product product = new  Product(id,uuid,name,price,qty,isDeleted);
                            products.add(product);
                            rowsRead++;
                        }
                        if (rowsRead < batchSize) {
                            hasMore = false;
                        }else {
                            offset += rowsRead;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("[!] Error during reading products: " + e.getMessage());
        }
        return products;
    }
}

package model.repository;


import model.entity.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class OrderRepository implements Repository<Order,Integer> {
    public void makeOrders(Integer userId,Integer productId)  {
        String sql = """
                INSERT INTO orders_products
                VALUES (?,?)
                """;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            int rowEffected =preparedStatement.executeUpdate();
            if(rowEffected > 0){
                System.out.println("Order added successfully");
            }

        } catch (Exception e) {
            System.out.println("Error make order "+e.getMessage());
        }
    }

    @Override
    public Order save(Order orders) {
        for(Integer pId:orders.getProductIds()){
            makeOrders(orders.getUserId(),pId);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }
}


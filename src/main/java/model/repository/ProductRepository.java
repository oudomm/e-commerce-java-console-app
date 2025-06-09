package model.repository;

import model.entity.Product;

import java.util.List;

public class ProductRepository implements Repository<Product,Integer> {
    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }
    public Product findProductById(Integer id) {
        return null;
    }
}

package model.repository;

import java.util.List;

public interface Repository<T, K> {
    T save(T t);
    List<T> findAll(int roleNumber,int numberOfProducts);
    K delete(K id);
}

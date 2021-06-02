package com.nothinq.database.repository;

import com.nothinq.database.repository.object.Page;
import com.nothinq.database.repository.object.PageImpl;
import com.nothinq.database.repository.object.Pageable;

import java.util.List;

public interface CrudRepository<T, K> {

    T save(T entity);

    T update(T entity);

    void update(List<T> entities);

    void save(List<T> entities);

    T findById(K id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    void deleteById(K id);

    void delete(T entity);
}

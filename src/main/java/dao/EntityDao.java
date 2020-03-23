package dao;

import java.util.List;

public interface EntityDao<T> {
    
    List<T> getAll();
    
    boolean create(T entity);
    
    boolean update(T entity);
    
    boolean remove(T entity);
}

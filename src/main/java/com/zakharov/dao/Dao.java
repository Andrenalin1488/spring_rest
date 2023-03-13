package com.zakharov.dao;

import java.util.List;

public interface Dao<T> {

    void save(T t);
    List<T> getAll();
}

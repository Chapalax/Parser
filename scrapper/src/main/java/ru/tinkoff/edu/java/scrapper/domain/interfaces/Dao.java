package ru.tinkoff.edu.java.scrapper.domain.interfaces;

import java.util.List;

public interface Dao<T> {
    T add(T object);

    int remove(T object);

    List<T> findAll();
}

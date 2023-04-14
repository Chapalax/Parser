package ru.tinkoff.edu.java.scrapper.domain.interfaces;

public interface Dao<T> {
    int add(T object);

    int remove(T object);

    Iterable<T> findAll();
}

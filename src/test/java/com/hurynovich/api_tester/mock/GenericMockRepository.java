package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.persistence.Identified;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GenericMockRepository<E extends Identified<I>, I extends Serializable> implements CrudRepository<E, I> {

    private final Map<I, E> storage = new LinkedHashMap<>();

    @Override
    public <S extends E> S save(final S entity) {
        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public <S extends E> Iterable<S> saveAll(final Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<E> findById(final I i) {
        return Optional.ofNullable(storage.get(i));
    }

    @Override
    public boolean existsById(final I i) {
        return storage.containsKey(i);
    }

    @Override
    public Iterable<E> findAll() {
        return storage.values();
    }

    @Override
    public Iterable<E> findAllById(final Iterable<I> is) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(final I i) {
        storage.remove(i);
    }

    @Override
    public void delete(final E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(final Iterable<? extends E> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

}

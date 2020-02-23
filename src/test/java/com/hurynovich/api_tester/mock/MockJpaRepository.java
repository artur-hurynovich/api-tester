package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MockJpaRepository<E extends AbstractEntity> implements JpaRepository<E, Long> {

    private final Map<Long, E> storage = new LinkedHashMap<>();

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<E> findAll(final Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<E> findAll(final Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> findAllById(final Iterable<Long> is) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(final Long i) {
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

    @Override
    public <S extends E> S save(final S entity) {
        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public <S extends E> List<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<E> findById(final Long i) {
        return Optional.ofNullable(storage.get(i));
    }

    @Override
    public boolean existsById(final Long i) {
        return storage.containsKey(i);
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> S saveAndFlush(final S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteInBatch(final Iterable<E> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getOne(final Long i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> Optional<S> findOne(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> List<S> findAll(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> List<S> findAll(final Example<S> example, final Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> long count(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> boolean exists(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

}

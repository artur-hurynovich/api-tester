package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.document.AbstractDocument;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MockMongoRepository<D extends AbstractDocument> implements MongoRepository<D, String> {

    private final Map<String, D> storage = new LinkedHashMap<>();

    @Override
    public <S extends D> S save(final S entity) {
        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public <S extends D> List<S> saveAll(final Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<D> findById(final String s) {
        return Optional.ofNullable(storage.get(s));
    }

    @Override
    public boolean existsById(final String s) {
        return storage.containsKey(s);
    }

    @Override
    public List<D> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<D> findAllById(final Iterable<String> strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(final String s) {
        storage.remove(s);
    }

    @Override
    public void delete(final D entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(final Iterable<? extends D> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public List<D> findAll(final Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<D> findAll(final Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> S insert(final S s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> List<S> insert(final Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> Optional<S> findOne(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> List<S> findAll(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> List<S> findAll(final Example<S> example, final Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> long count(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends D> boolean exists(final Example<S> example) {
        throw new UnsupportedOperationException();
    }

}

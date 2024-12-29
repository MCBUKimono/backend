package com.kimono.backend.mappers;

public interface Mapper <E, D> {
    D mapTo(E entity);
    E mapFrom(D dto);
}

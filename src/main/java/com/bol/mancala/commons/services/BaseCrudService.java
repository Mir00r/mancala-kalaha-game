package com.bol.mancala.commons.services;

import com.bol.mancala.commons.models.entities.BaseEntity;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public interface BaseCrudService<T extends BaseEntity> {
    Page<T> search(String query, Integer page, Integer size);

    T save(T entity);

    Optional<T> find(Long id);

    void delete(Long id, Boolean softDelete) throws NotFoundException;
}

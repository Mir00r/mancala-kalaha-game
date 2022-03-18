package com.bol.mancala.commons.controllers;

import com.bol.mancala.commons.models.dtos.BaseDto;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public interface BaseCrudController<T extends BaseDto> {
    ResponseEntity<Page<T>> search(String query, Integer page, Integer size);

    ResponseEntity<T> find(Long id) throws NotFoundException;

    ResponseEntity<T> create(T dto);

    ResponseEntity<T> update(Long id, T dto) throws NotFoundException;

    ResponseEntity<Object> delete(Long id) throws NotFoundException;
}

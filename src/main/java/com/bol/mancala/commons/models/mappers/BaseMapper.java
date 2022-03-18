package com.bol.mancala.commons.models.mappers;

import com.bol.mancala.commons.models.dtos.BaseDto;
import com.bol.mancala.commons.models.entities.BaseEntity;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public interface BaseMapper<T extends BaseEntity, S extends BaseDto> {
    S map(T entity);

    T map(S dto, T exEntity);
}

package com.bol.mancala.domains.pits.models.mappers;

import com.bol.mancala.commons.models.mappers.BaseMapper;
import com.bol.mancala.domains.pits.models.dtos.PitDto;
import com.bol.mancala.domains.pits.models.entities.Pit;
import org.springframework.stereotype.Component;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@Component
public class PitMapper implements BaseMapper<Pit, PitDto> {

    /**
     * Method to mapping data from entity to dto class
     *
     * @param entity
     * @return
     */
    @Override
    public PitDto map(Pit entity) {
        PitDto dto = new PitDto();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setAmount(entity.getStones());
        dto.setSequence(entity.getSequence());
        dto.setMain(entity.isMain());
        return dto;
    }

    /**
     * Method to mapping data from dto to entity class for creating new entry
     *
     * @param dto
     * @param exEntity
     * @return
     */
    @Override
    public Pit map(PitDto dto, Pit exEntity) {
        Pit entity = exEntity == null ? new Pit() : exEntity;

        entity.setStones(dto.getAmount());
        entity.setSequence(dto.getSequence());
        entity.setMain(dto.isMain());
        return entity;
    }
}

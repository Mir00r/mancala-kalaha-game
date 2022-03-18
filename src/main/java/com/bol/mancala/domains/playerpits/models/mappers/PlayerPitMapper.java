package com.bol.mancala.domains.playerpits.models.mappers;

import com.bol.mancala.commons.models.mappers.BaseMapper;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.pits.repositories.PitRepository;
import com.bol.mancala.domains.playerpits.models.dtos.PlayerPitDto;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.repositories.PlayerRepository;
import com.bol.mancala.exceptions.ServiceExceptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@Component
public class PlayerPitMapper implements BaseMapper<PlayerPits, PlayerPitDto> {

    private final PlayerRepository playerRepository;
    private final PitRepository pitRepository;

    @Autowired
    public PlayerPitMapper(PlayerRepository playerRepository, PitRepository pitRepository) {
        this.playerRepository = playerRepository;
        this.pitRepository = pitRepository;
    }

    /**
     * Method to mapping data from entity to dto class
     *
     * @param entity
     * @return
     */
    @Override
    public PlayerPitDto map(PlayerPits entity) {
        PlayerPitDto dto = new PlayerPitDto();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setPlayerId(entity.getPlayer().getId());
        dto.setPitId(entity.getPit().getId());
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
    public PlayerPits map(PlayerPitDto dto, PlayerPits exEntity) {
        PlayerPits entity = exEntity == null ? new PlayerPits() : exEntity;

        Player player = this.playerRepository.find(dto.getPlayerId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(PlayerPits.class.getName(), dto.getPlayerId()));
        Pit pit = this.pitRepository.find(dto.getPitId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Pit.class.getName(), dto.getPitId()));

        entity.setPlayer(player);
        entity.setPit(pit);
        return entity;
    }

    public PlayerPits map(PlayerPitDto dto) {
        PlayerPits entity = new PlayerPits();

        Player player = this.playerRepository.find(dto.getPlayerId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(PlayerPits.class.getName(), dto.getPlayerId()));
        Pit pit = this.pitRepository.find(dto.getPitId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Pit.class.getName(), dto.getPitId()));

        entity.setPlayer(player);
        entity.setPit(pit);
        return entity;
    }


    public List<PlayerPits> map(List<PlayerPitDto> dtos) {

        List<PlayerPits> entities = new ArrayList<>();
        dtos.forEach(pp -> entities.add(this.map(pp)));

        return entities;
    }
}

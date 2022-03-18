package com.bol.mancala.domains.games.models.mappers;

import com.bol.mancala.commons.models.mappers.BaseMapper;
import com.bol.mancala.domains.games.models.dtos.GameDto;
import com.bol.mancala.domains.games.models.entities.Game;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.models.mappers.PlayerMapper;
import com.bol.mancala.domains.players.services.IPlayerService;
import com.bol.mancala.exceptions.ServiceExceptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mir00r on 12/2/22
 * @project IntelliJ IDEA
 */
@Component
public class GameMapper implements BaseMapper<Game, GameDto> {

    private final IPlayerService playerService;
    private final PlayerMapper playerMapper;

    @Autowired
    public GameMapper(IPlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    /**
     * Method to mapping data from entity to dto class
     *
     * @param entity
     * @return
     */
    @Override
    public GameDto map(Game entity) {
        GameDto dto = new GameDto();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setPlayerTurn(entity.getPlayerTurn());
        dto.setCompleted(entity.isCompleted());
        dto.setWinner(entity.getWinner());
        dto.setPlayerOneId(entity.getPlayerA().getId());
        dto.setPlayerTwoId(entity.getPlayerB().getId());
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
    public Game map(GameDto dto, Game exEntity) {
        Game entity = exEntity == null ? new Game() : exEntity;
        Player playerA = this.playerService.find(dto.getPlayerOneId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Player.class.getName(), dto.getPlayerOneId()));
        Player playerB = this.playerService.find(dto.getPlayerTwoId()).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Player.class.getName(), dto.getPlayerTwoId()));
        entity.setPlayerA(playerA);
        entity.setPlayerB(playerB);
        return entity;
    }

    public Game mapAutomatically() {
        Game entity = new Game();
        List<Player> playerList = this.playerService.saveAutoAll(this.playerMapper.map());
        if (playerList.size() == 2) {
            entity.setPlayerA(playerList.get(0));
            entity.setPlayerB(playerList.get(1));
        } else
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getSimpleName(), "Need minimum two player to initiate the game");
        return entity;
    }
}

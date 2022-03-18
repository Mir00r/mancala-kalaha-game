package com.bol.mancala.domains.players.models.mappers;

import com.bol.mancala.commons.models.mappers.BaseMapper;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.pits.models.mappers.PitMapper;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import com.bol.mancala.domains.players.models.dtos.PlayerDto;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@Component
public class PlayerMapper implements BaseMapper<Player, PlayerDto> {

    private final PitMapper pitMapper;

    @Autowired
    public PlayerMapper(PitMapper pitMapper) {
        this.pitMapper = pitMapper;
    }

    /**
     * Method to mapping data from entity to dto class
     *
     * @param entity
     * @return
     */
    @Override
    public PlayerDto map(Player entity) {
        PlayerDto dto = new PlayerDto();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setName(entity.getName());
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
    public Player map(PlayerDto dto, Player exEntity) {
        Player entity = exEntity == null ? new Player() : exEntity;
        entity.setName(dto.getName());
        return entity;
    }

    public Player map(String playerName, int pitStart, int pitEnd) {
        Player player = new Player();
        player.setName(playerName);
        ArrayList<PlayerPits> playerPitsList = new ArrayList<>();

        for (int i = pitStart; i < pitEnd; i++) {
            Pit pit = new Pit((long) i, (long) Constants.MAX_STONES, false);
            PlayerPits playerPits = new PlayerPits();
            playerPits.setPlayer(player);
            playerPits.setPit(pit);
            playerPitsList.add(playerPits);
        }
        PlayerPits playerPitsForMain = new PlayerPits();
        playerPitsForMain.setPlayer(player);
        playerPitsForMain.setPit(new Pit((long) pitEnd, 0L, true));
        playerPitsList.add(playerPitsForMain);

        player.setPlayerPits(playerPitsList);
        return player;
    }

    public List<Player> map() {
        ArrayList<Player> playerList = new ArrayList<>();

        playerList.add(this.map("Player A", 0, 6));
        playerList.add(this.map("Player B", 7, 13));
        return playerList;
    }
}

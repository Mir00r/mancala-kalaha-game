package com.bol.mancala.domains.players.models.entities;

import com.bol.mancala.commons.models.entities.BaseEntity;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.LAZY)
    private List<PlayerPits> playerPits;

    public Pit getMainPit() {
        return playerPits.stream().filter(x -> x.getPit().isMain()).findFirst().get().getPit();
    }
}

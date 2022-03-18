package com.bol.mancala.domains.playerpits.models.entities;

import com.bol.mancala.commons.models.entities.BaseEntity;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.players.models.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player_pits")
public class PlayerPits extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "pit_id")
    private Pit pit;
}

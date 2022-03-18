package com.bol.mancala.domains.games.models.entities;

import com.bol.mancala.commons.models.entities.BaseEntity;
import com.bol.mancala.domains.games.models.enums.PlayerTurns;
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
@Table(name = "game_statuses")
public class Game extends BaseEntity {

    private boolean completed;
    private String winner;

    @OneToOne
    @JoinColumn(name = "player_a_id")
    private Player playerA;

    @OneToOne
    @JoinColumn(name = "player_b_id")
    private Player playerB;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_turns")
    private PlayerTurns playerTurn;
}

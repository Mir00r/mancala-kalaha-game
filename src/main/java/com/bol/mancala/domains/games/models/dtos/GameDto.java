package com.bol.mancala.domains.games.models.dtos;

import com.bol.mancala.commons.models.dtos.BaseDto;
import com.bol.mancala.domains.games.models.enums.PlayerTurns;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author mir00r on 12/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto extends BaseDto {

    @NotNull
    @ApiModelProperty(example = "1", notes = "Id of first player", required = true)
    private Long playerOneId;

    @NotNull
    @ApiModelProperty(example = "1", notes = "Id of second player", required = true)
    private Long playerTwoId;

    @ApiModelProperty(example = "false", notes = "Status of game")
    private boolean completed;

    @ApiModelProperty(example = "Player 1", notes = "Winner of this game")
    private String winner;

    @ApiModelProperty(example = "PlayerA", notes = "Turn of player of this game")
    private PlayerTurns playerTurn;
}

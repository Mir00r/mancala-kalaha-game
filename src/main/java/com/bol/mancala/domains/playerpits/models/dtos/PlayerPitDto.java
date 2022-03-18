package com.bol.mancala.domains.playerpits.models.dtos;

import com.bol.mancala.commons.models.dtos.BaseDto;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.players.models.entities.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPitDto extends BaseDto {

    @NotNull
    @ApiModelProperty(example = "1", notes = "Id of player", required = true)
    private Long playerId;

    @NotNull
    @ApiModelProperty(example = "1", notes = "Id of pit", required = true)
    private Long pitId;

    private Pit pit;

    @JsonIgnore
    private Player player;
}

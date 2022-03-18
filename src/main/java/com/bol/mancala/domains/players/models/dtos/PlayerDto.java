package com.bol.mancala.domains.players.models.dtos;

import com.bol.mancala.commons.models.dtos.BaseDto;
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
public class PlayerDto extends BaseDto {

    @NotNull
    @ApiModelProperty(example = "Player 1", notes = "The name of player", required = true)
    private String name;
}

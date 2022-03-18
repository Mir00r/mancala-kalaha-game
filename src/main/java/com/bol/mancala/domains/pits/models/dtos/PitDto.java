package com.bol.mancala.domains.pits.models.dtos;

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
public class PitDto extends BaseDto {

    @NotNull
    @ApiModelProperty(example = "1", notes = "The sequence number of pit", required = true)
    private Long sequence;

    @NotNull
    @ApiModelProperty(example = "1", notes = "The amount of pit", required = true)
    private Long amount;

    @ApiModelProperty(example = "false", notes = "Is the is main")
    private boolean main;
}

package com.bol.mancala.commons.models.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {

    @ApiModelProperty(hidden = true, example = "1", notes = "Auto incremented long value")
    private Long id;

    @ApiModelProperty(hidden = true, example = "2020-09-13T03:48:36Z", notes = "Date when this entity was first created. ( Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' )")
    private Instant createdAt;

    @ApiModelProperty(hidden = true, example = "2020-09-13T03:48:36Z", notes = "Date when this entity was last updated. ( Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' )")
    private Instant updatedAt;

    @ApiModelProperty(hidden = true, example = "57f47dde-ba80-45e4-aada-1dac9d7923d4", notes = "unique string value for every row")
    private String uuid;
}

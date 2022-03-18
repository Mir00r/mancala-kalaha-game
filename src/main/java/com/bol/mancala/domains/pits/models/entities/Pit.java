package com.bol.mancala.domains.pits.models.entities;

import com.bol.mancala.commons.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pits")
public class Pit extends BaseEntity {

    private Long sequence;
    private Long stones;
    private boolean main;

    public void addStones(Long amount) {
        this.stones += amount;
    }
}

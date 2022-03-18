package com.bol.mancala.domains.players.services;

import com.bol.mancala.commons.services.BaseCrudService;
import com.bol.mancala.domains.players.models.entities.Player;

import java.util.List;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
public interface IPlayerService extends BaseCrudService<Player> {

    public List<Player> saveAutoAll(List<Player> entities);
}

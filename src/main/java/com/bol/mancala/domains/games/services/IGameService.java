package com.bol.mancala.domains.games.services;

import com.bol.mancala.commons.services.BaseCrudService;
import com.bol.mancala.domains.games.models.entities.Game;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public interface IGameService extends BaseCrudService<Game> {

    Game movePieces(Long gameId, Long pitId, Long playerId);
}

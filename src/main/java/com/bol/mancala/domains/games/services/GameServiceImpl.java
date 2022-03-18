package com.bol.mancala.domains.games.services;

import com.bol.mancala.domains.games.components.GameRules;
import com.bol.mancala.domains.games.models.entities.Game;
import com.bol.mancala.domains.games.repositories.GameRepository;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.pits.repositories.PitRepository;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.repositories.PlayerRepository;
import com.bol.mancala.exceptions.ServiceExceptionHolder;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import com.bol.mancala.utils.Constants;
import com.bol.mancala.utils.ExceptionUtil;
import com.bol.mancala.utils.PageAttr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@Service
public class GameServiceImpl implements IGameService {

    private static final Logger logger = LogManager.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PitRepository pitRepository;
    private final GameRules gameRules;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, PitRepository pitRepository, GameRules gameRules) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.pitRepository = pitRepository;
        this.gameRules = gameRules;
    }


    @Override
    public Page<Game> search(String query, Integer page, Integer size) {
        return this.gameRepository.search(query, PageAttr.getPageRequestExact(page, size));
    }

    @Override
    public Game save(Game entity) {
        return this.gameRepository.save(entity);
    }

    @Override
    public Optional<Game> find(Long id) {
        return this.gameRepository.find(id);
    }

    @Override
    public void delete(Long id, Boolean softDelete) throws NotFoundException {
        if (!softDelete) {
            this.gameRepository.deleteById(id);
            return;
        }
        Game entity = this.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Game.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity.setDeleted(true);
        this.save(entity);
    }

    @Override
    public Game movePieces(Long gameId, Long pitId, Long playerId) {
        Player player = this.playerRepository.find(playerId).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Player.class.getName(), playerId));
        Pit pit = this.pitRepository.find(pitId).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Pit.class.getName(), pitId));
        Game game = this.gameRepository.find(gameId).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Game.class.getName(), gameId));

        this.gameRules.validateData(player, pit, game);

        List<Pit> board = this.gameRules.getBoardPits(game);
        logger.debug("Start to putting pieces into the pits...");
        Long initialPit = pit.getSequence();
        Long initialAmount = pit.getStones();

        board.get(initialPit.intValue()).setStones(0L);
        Long currentPitSequence = 0L;
        int start = (int) (initialPit + 1);
        int end = (int) (initialPit + initialAmount + 1);
        for (int i = start; i < end; i++) {
            Pit p = board.get(i % board.size());
            if (!p.isMain()) {
                if (!this.gameRules.captureStones(p, initialAmount, board, player)) {
                    p.addStones(1L);
                }
            } else {
                if (this.gameRules.pitBelongsToPlayer(p, player)) {
                    p.addStones(1L);
                }
            }
            logger.info(p);
            currentPitSequence = p.getSequence();
        }
        this.pitRepository.saveAll(board);

        boolean finished = this.gameRules.evaluateEndGame(player);
        game.setCompleted(finished);
        this.gameRules.setPlayerTurn(currentPitSequence, game);

        if (finished)
            game.setWinner(this.gameRules.getWinner(game.getPlayerA().getId(), game.getPlayerB().getId()));
        return this.save(game);
    }
}

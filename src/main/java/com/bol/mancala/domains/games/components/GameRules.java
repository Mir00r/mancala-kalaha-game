package com.bol.mancala.domains.games.components;

import com.bol.mancala.domains.games.models.entities.Game;
import com.bol.mancala.domains.games.models.enums.PlayerTurns;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import com.bol.mancala.domains.playerpits.repositories.PlayerPitRepository;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.repositories.PlayerRepository;
import com.bol.mancala.exceptions.ServiceExceptionHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author mir00r on 14/2/22
 * @project IntelliJ IDEA
 */
@Component
public class GameRules {

    private static final Logger logger = LogManager.getLogger(GameRules.class);

    private final PlayerRepository playerRepository;
    private final PlayerPitRepository playerPitRepository;

    @Autowired
    public GameRules(PlayerRepository playerRepository, PlayerPitRepository playerPitRepository) {
        this.playerRepository = playerRepository;
        this.playerPitRepository = playerPitRepository;
    }

    public String getWinner(Long playerOneId, Long playerTwoId) {
        Player player1 = this.playerRepository.find(playerOneId).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Player.class.getName(), playerOneId));
        Player player2 = this.playerRepository.find(playerTwoId).orElseThrow(() -> new ServiceExceptionHolder.EntityNotFoundException(Player.class.getName(), playerTwoId));
        if (player1.getMainPit().getStones() > player2.getMainPit().getStones()) {
            return player1.getName();
        } else {
            return player2.getName();
        }
    }

    public boolean captureStones(Pit p, Long initialAmount, List<Pit> board, Player currentPlayer) {
        if (p.getStones() == 0 && initialAmount == 1 && this.pitBelongsToPlayer(p, currentPlayer)) {
            int pitSequence = p.getSequence().intValue();
            int oppositeSidePitSequence = (pitSequence + (board.size() - 2)) - (2 * pitSequence);
            if (board.get(oppositeSidePitSequence).getStones() > 0) {
                Pit mainPit = currentPlayer.getMainPit();
                board.get(mainPit.getSequence().intValue()).addStones(board.get(oppositeSidePitSequence).getStones() + 1);
                board.get(oppositeSidePitSequence).setStones(0L);
                board.get(pitSequence).setStones(0L);
                return true;
            }
        }
        return false;
    }

    public boolean pitBelongsToPlayer(Pit pit, Player currentPlayer) {
        return currentPlayer.getPlayerPits().stream().anyMatch(x -> Objects.equals(x.getPit().getSequence(), pit.getSequence()));
    }

    public boolean evaluateEndGame(Player currentPlayer) {
        int currentPlayerTotalAmount = currentPlayer.getPlayerPits().stream()
                .filter(x -> !x.getPit().isMain())
                .mapToInt(x -> Math.toIntExact(x.getPit().getStones()))
                .reduce(Integer::sum)
                .getAsInt();

        logger.info(String.format("Total amount player[%s] -> [%d]", currentPlayer.getName(), currentPlayerTotalAmount));

        return currentPlayerTotalAmount == 0;
    }

    public void setPlayerTurnForFirstMove(Game game, Pit pit) {
        if (game.getPlayerTurn() == null) {
            if (pit.getSequence() < game.getPlayerA().getMainPit().getSequence())
                game.setPlayerTurn(PlayerTurns.PlayerA);
            else
                game.setPlayerTurn(PlayerTurns.PlayerB);
        }
    }

    public void setPlayerTurn(Long currentPitSequence, Game game) {
        if (!Objects.equals(currentPitSequence, game.getPlayerA().getMainPit().getSequence()) && !Objects.equals(currentPitSequence, game.getPlayerB().getMainPit().getSequence()))
            game.setPlayerTurn(nextTurn(game.getPlayerTurn()));
    }

    public void validatePlayerTurn(Game game, Pit pit) {
        if (game.getPlayerTurn() == PlayerTurns.PlayerA && pit.getSequence() > game.getPlayerA().getMainPit().getSequence() ||
                game.getPlayerTurn() == PlayerTurns.PlayerB && pit.getSequence() < game.getPlayerA().getMainPit().getSequence())
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "Wrong player turn");
    }

    public PlayerTurns nextTurn(PlayerTurns currentTurn) {
        return currentTurn == PlayerTurns.PlayerA ? PlayerTurns.PlayerB : PlayerTurns.PlayerA;
    }

    public void noMoveForMainPit(Pit pit, Player player) {
        if (Objects.equals(pit.getSequence(), player.getMainPit().getSequence()))
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "Pit sequence id and player main sequence id same so unable to move this pit ");
    }

    public void noMoveForOppositePit(Pit pit, Player player) {
        Optional<PlayerPits> playerPits = this.playerPitRepository.find(player.getId(), pit.getId());
        if (playerPits.isEmpty())
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "Wrong pit access from wrong player! ");
    }

    public void noMoveForZeroAmountPit(Pit pit) {
        if (pit.getStones() == 0)
            throw new ServiceExceptionHolder.ResourceNotFoundException(Pit.class.getName(), "Already move all stones from this pit! amount: " + pit.getStones());
    }

    public void isGameCompleted(Game game) {
        if (game.isCompleted())
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "This game already completed and the winner is: " + game.getWinner());
    }

    public List<Pit> getBoardPits(Game game) {
        List<Pit> playerAPits = this.playerPitRepository.searchPitByPlayer(game.getPlayerA().getId());
        if (playerAPits.size() == 0)
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "Player: " + game.getPlayerA().getName() + " do not have any pits");
        List<Pit> playerBPits = this.playerPitRepository.searchPitByPlayer(game.getPlayerB().getId());
        if (playerBPits.size() == 0)
            throw new ServiceExceptionHolder.ResourceNotFoundException(Game.class.getName(), "Player: " + game.getPlayerB().getName() + " do not have any pits");
        List<Pit> board = new ArrayList<>();
        board.addAll(playerAPits);
        board.addAll(playerBPits);
        return board;
    }

    public void validateData(Player player, Pit pit, Game game) {
        this.isGameCompleted(game);
        this.noMoveForMainPit(pit, player);
        this.noMoveForZeroAmountPit(pit);
        this.noMoveForOppositePit(pit, player);
        this.validatePlayerTurn(game, pit);
        this.setPlayerTurnForFirstMove(game, pit);
    }
}

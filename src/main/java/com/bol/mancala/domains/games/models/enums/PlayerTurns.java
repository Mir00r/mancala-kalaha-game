package com.bol.mancala.domains.games.models.enums;

/**
 * @author mir00r on 16/2/22
 * @project IntelliJ IDEA
 */
public enum PlayerTurns {
    PlayerA ("A"), PlayerB ("B");

    private final String turn;

    PlayerTurns(String turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return turn;
    }
}

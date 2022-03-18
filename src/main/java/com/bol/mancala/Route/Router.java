package com.bol.mancala.Route;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public class Router {

    public static final String REST_API = "Rest API";
    public static final String API = "api/";
    public static final String VERSION = "v1";

    public static final String CREATE_PITS = API + VERSION + "/pits";
    public static final String FIND_PITS_BY_ID = API + VERSION + "/pits/{id}";
    public static final String UPDATE_PITS_BY_ID = API + VERSION + "/pits/{id}";
    public static final String DELETE_PITS_BY_ID = API + VERSION + "/pits/{id}";
    public static final String SEARCH_ALL_PITS = API + VERSION + "/pits";

    public static final String CREATE_PLAYERS = API + VERSION + "/players";
    public static final String CREATE_PLAYERS_AUTO = API + VERSION + "/players/auto";
    public static final String FIND_PLAYERS_BY_ID = API + VERSION + "/players/{id}";
    public static final String UPDATE_PLAYERS_BY_ID = API + VERSION + "/players/{id}";
    public static final String DELETE_PLAYERS_BY_ID = API + VERSION + "/players/{id}";
    public static final String SEARCH_ALL_PLAYERS = API + VERSION + "/players";

    public static final String CREATE_PLAYER_PITS = API + VERSION + "/players/pits";
    public static final String FIND_PLAYER_PITS_BY_ID = API + VERSION + "/players/pits/{id}";
    public static final String UPDATE_PLAYER_PITS_BY_ID = API + VERSION + "/players/pits/{id}";
    public static final String DELETE_PLAYER_PITS_BY_ID = API + VERSION + "/players/pits/{id}";
    public static final String SEARCH_ALL_PLAYER_PITS = API + VERSION + "/players/pits";

    public static final String CREATE_GAMES_AUTO = API + VERSION + "/games/auto";
    public static final String MOVE_PIECES = API + VERSION + "/games/{gameId}/pits/{pitId}/player/{playerId}";
    public static final String CREATE_GAMES = API + VERSION + "/games";
    public static final String FIND_GAMES_BY_ID = API + VERSION + "/games/{id}";
    public static final String UPDATE_GAMES_BY_ID = API + VERSION + "/games/{id}";
    public static final String DELETE_GAMES_BY_ID = API + VERSION + "/games/{id}";
    public static final String SEARCH_ALL_GAMES = API + VERSION + "/games";

    public static String getMovePiecesTestUrl(Long gameId, Long pitId, Long playerId) {
        return "/api/v1/games/" + gameId + "/pits/" + pitId + "/player/" + playerId;
    }
}

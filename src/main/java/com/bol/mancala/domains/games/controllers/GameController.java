package com.bol.mancala.domains.games.controllers;

import com.bol.mancala.Route.Router;
import com.bol.mancala.commons.controllers.BaseCrudController;
import com.bol.mancala.domains.games.models.dtos.GameDto;
import com.bol.mancala.domains.games.models.entities.Game;
import com.bol.mancala.domains.games.models.mappers.GameMapper;
import com.bol.mancala.domains.games.services.IGameService;
import com.bol.mancala.domains.players.models.dtos.PlayerDto;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import com.bol.mancala.utils.Constants;
import com.bol.mancala.utils.ExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mir00r on 12/2/22
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.GAME)
public class GameController implements BaseCrudController<GameDto> {

    private final GameMapper gameMapper;
    private final IGameService gameService;

    @Autowired
    public GameController(GameMapper gameMapper, IGameService gameService) {
        this.gameMapper = gameMapper;
        this.gameService = gameService;
    }

    @ApiOperation(value = "Create a new Game", response = GameDto[].class)
    @PostMapping(Router.CREATE_GAMES_AUTO)
    public ResponseEntity<GameDto> createAutomatically() {
        Game entity = this.gameService.save(this.gameMapper.mapAutomatically());
        return ResponseEntity.ok(this.gameMapper.map(entity));
    }

    @ApiOperation(value = "Move pits by player", response = GameDto[].class)
    @PostMapping(Router.MOVE_PIECES)
    public ResponseEntity<GameDto> movePieces(@PathVariable("gameId") Long gameId, @PathVariable("pitId") Long pitId, @PathVariable("playerId") Long playerId) {
        Game entity = this.gameService.movePieces(gameId, pitId, playerId);
        return ResponseEntity.ok(this.gameMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Get all list of games", response = GameDto[].class)
    @GetMapping(Router.SEARCH_ALL_GAMES)
    public ResponseEntity<Page<GameDto>> search(@RequestParam(value = "q", defaultValue = "") String query,
                                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Game> entities = this.gameService.search(query, page, size);
        return ResponseEntity.ok(entities.map(this.gameMapper::map));
    }

    @Override
    @ApiOperation(value = "Get Game by id", response = GameDto[].class)
    @GetMapping(Router.FIND_GAMES_BY_ID)
    public ResponseEntity<GameDto> find(@PathVariable("id") Long id) throws NotFoundException {
        Game entity = this.gameService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Game.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        return ResponseEntity.ok(this.gameMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Create a new Game", response = GameDto[].class)
    @PostMapping(Router.CREATE_GAMES)
    public ResponseEntity<GameDto> create(@Valid @RequestBody GameDto dto) {
        Game entity = this.gameService.save(this.gameMapper.map(dto, null));
        return ResponseEntity.ok(this.gameMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Update an existing Game by id", response = GameDto[].class)
    @PatchMapping(Router.UPDATE_GAMES_BY_ID)
    public ResponseEntity<GameDto> update(@PathVariable("id") Long id, @Valid @RequestBody GameDto dto) throws NotFoundException {
        Game entity = this.gameService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Game.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity = this.gameService.save(this.gameMapper.map(dto, entity));
        return ResponseEntity.ok(this.gameMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Delete Game by id", response = PlayerDto[].class)
    @DeleteMapping(Router.DELETE_GAMES_BY_ID)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws NotFoundException {
        this.gameService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}

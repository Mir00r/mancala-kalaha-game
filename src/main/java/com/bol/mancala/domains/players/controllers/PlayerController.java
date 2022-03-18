package com.bol.mancala.domains.players.controllers;

import com.bol.mancala.Route.Router;
import com.bol.mancala.commons.controllers.BaseCrudController;
import com.bol.mancala.domains.players.models.dtos.PlayerDto;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.models.mappers.PlayerMapper;
import com.bol.mancala.domains.players.services.IPlayerService;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.PLAYER)
public class PlayerController implements BaseCrudController<PlayerDto> {

    private final PlayerMapper playerMapper;
    private final IPlayerService playerService;

    @Autowired
    public PlayerController(PlayerMapper playerMapper, IPlayerService playerService) {
        this.playerMapper = playerMapper;
        this.playerService = playerService;
    }

    @Override
    @ApiOperation(value = "Get all list of players", response = PlayerDto[].class)
    @GetMapping(Router.SEARCH_ALL_PLAYERS)
    public ResponseEntity<Page<PlayerDto>> search(@RequestParam(value = "q", defaultValue = "") String query,
                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<Player> entities = this.playerService.search(query, page, size);
        return ResponseEntity.ok(entities.map(this.playerMapper::map));
    }

    @Override
    @ApiOperation(value = "Get Player by id", response = PlayerDto[].class)
    @GetMapping(Router.FIND_PLAYERS_BY_ID)
    public ResponseEntity<PlayerDto> find(@PathVariable("id") Long id) throws NotFoundException {
        Player entity = this.playerService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Player.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        return ResponseEntity.ok(this.playerMapper.map(entity));
    }

    @ApiOperation(value = "Create a new Player automatically with siz pits", response = PlayerDto[].class)
    @PostMapping(Router.CREATE_PLAYERS_AUTO)
    public ResponseEntity<List<PlayerDto>> createAuto() {
        List<Player> entities = this.playerService.saveAutoAll(this.playerMapper.map());
        return ResponseEntity.ok(entities.stream().map(this.playerMapper::map).collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "Create a new Player", response = PlayerDto[].class)
    @PostMapping(Router.CREATE_PLAYERS)
    public ResponseEntity<PlayerDto> create(@Valid @RequestBody PlayerDto dto) {
        Player entity = this.playerService.save(this.playerMapper.map(dto, null));
        return ResponseEntity.ok(this.playerMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Update an existing Player by id", response = PlayerDto[].class)
    @PatchMapping(Router.UPDATE_PLAYERS_BY_ID)
    public ResponseEntity<PlayerDto> update(@PathVariable("id") Long id, @Valid @RequestBody PlayerDto dto) throws NotFoundException {
        Player entity = this.playerService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Player.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity = this.playerService.save(this.playerMapper.map(dto, entity));
        return ResponseEntity.ok(this.playerMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Delete Player by id", response = PlayerDto[].class)
    @DeleteMapping(Router.DELETE_PLAYERS_BY_ID)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws NotFoundException {
        this.playerService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}

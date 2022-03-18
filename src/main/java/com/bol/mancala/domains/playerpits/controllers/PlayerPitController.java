package com.bol.mancala.domains.playerpits.controllers;

import com.bol.mancala.Route.Router;
import com.bol.mancala.commons.controllers.BaseCrudController;
import com.bol.mancala.domains.playerpits.models.dtos.PlayerPitDto;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import com.bol.mancala.domains.playerpits.models.mappers.PlayerPitMapper;
import com.bol.mancala.domains.playerpits.services.IPlayerPitService;
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
@Api(tags = Constants.PLAYER_PIT)
public class PlayerPitController implements BaseCrudController<PlayerPitDto> {

    private final PlayerPitMapper playerPitMapper;
    private final IPlayerPitService playerPitService;

    @Autowired
    public PlayerPitController(PlayerPitMapper playerPitMapper, IPlayerPitService playerPitService) {
        this.playerPitMapper = playerPitMapper;
        this.playerPitService = playerPitService;
    }

    @Override
    @ApiOperation(value = "Get all list of players pits", response = PlayerPitDto[].class)
    @GetMapping(Router.SEARCH_ALL_PLAYER_PITS)
    public ResponseEntity<Page<PlayerPitDto>> search(@RequestParam(value = "q", defaultValue = "") String query,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<PlayerPits> entities = this.playerPitService.search(query, page, size);
        return ResponseEntity.ok(entities.map(this.playerPitMapper::map));
    }

    @Override
    @ApiOperation(value = "Get Player pit by id", response = PlayerPitDto[].class)
    @GetMapping(Router.FIND_PLAYER_PITS_BY_ID)
    public ResponseEntity<PlayerPitDto> find(@PathVariable("id") Long id) throws NotFoundException {
        PlayerPits entity = this.playerPitService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(PlayerPits.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        return ResponseEntity.ok(this.playerPitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Create a new Player Pit", response = PlayerPitDto[].class)
    @PostMapping(Router.CREATE_PLAYER_PITS)
    public ResponseEntity<PlayerPitDto> create(@Valid @RequestBody PlayerPitDto dto) {
        PlayerPits entity = this.playerPitService.save(this.playerPitMapper.map(dto, null));
        return ResponseEntity.ok(this.playerPitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Update an existing Player Pit by id", response = PlayerPitDto[].class)
    @PatchMapping(Router.UPDATE_PLAYER_PITS_BY_ID)
    public ResponseEntity<PlayerPitDto> update(@PathVariable("id") Long id, @Valid @RequestBody PlayerPitDto dto) throws NotFoundException {
        PlayerPits entity = this.playerPitService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(PlayerPits.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity = this.playerPitService.save(this.playerPitMapper.map(dto, entity));
        return ResponseEntity.ok(this.playerPitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Delete Player Pit by id", response = PlayerPits[].class)
    @DeleteMapping(Router.DELETE_PLAYER_PITS_BY_ID)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws NotFoundException {
        this.playerPitService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}

package com.bol.mancala.domains.pits.controllers;

import com.bol.mancala.Route.Router;
import com.bol.mancala.commons.controllers.BaseCrudController;
import com.bol.mancala.domains.pits.models.dtos.PitDto;
import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.pits.models.mappers.PitMapper;
import com.bol.mancala.domains.pits.services.IPitService;
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
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.PIT)
public class PitController implements BaseCrudController<PitDto> {

    private final IPitService pitService;
    private final PitMapper pitMapper;

    @Autowired
    public PitController(IPitService pitService, PitMapper pitMapper) {
        this.pitService = pitService;
        this.pitMapper = pitMapper;
    }

    @Override
    @ApiOperation(value = "Get all list of pits", response = PitDto[].class)
    @GetMapping(Router.SEARCH_ALL_PITS)
    public ResponseEntity<Page<PitDto>> search(@RequestParam(value = "q", required = false) String query,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<Pit> entities = this.pitService.search(query, page, size);
        return ResponseEntity.ok(entities.map(this.pitMapper::map));
    }

    @Override
    @ApiOperation(value = "Get pit by id", response = PitDto[].class)
    @GetMapping(Router.FIND_PITS_BY_ID)
    public ResponseEntity<PitDto> find(@PathVariable("id") Long id) throws NotFoundException {
        Pit entity = this.pitService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Pit.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        return ResponseEntity.ok(this.pitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Create a new Pit", response = PitDto[].class)
    @PostMapping(Router.CREATE_PITS)
    public ResponseEntity<PitDto> create(@Valid @RequestBody PitDto dto) {
        Pit entity = this.pitService.save(this.pitMapper.map(dto, null));
        return ResponseEntity.ok(this.pitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Update an existing pit by id", response = PitDto[].class)
    @PatchMapping(Router.UPDATE_PITS_BY_ID)
    public ResponseEntity<PitDto> update(@PathVariable("id") Long id, @Valid @RequestBody PitDto dto) throws NotFoundException {
        Pit entity = this.pitService.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Pit.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity = this.pitService.save(this.pitMapper.map(dto, entity));
        return ResponseEntity.ok(this.pitMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Delete pit by id", response = PitDto[].class)
    @DeleteMapping(Router.DELETE_PITS_BY_ID)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws NotFoundException {
        this.pitService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}

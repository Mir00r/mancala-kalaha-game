package com.bol.mancala.domains.playerpits.services;

import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import com.bol.mancala.domains.playerpits.repositories.PlayerPitRepository;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import com.bol.mancala.utils.Constants;
import com.bol.mancala.utils.ExceptionUtil;
import com.bol.mancala.utils.PageAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mir00r on 10/2/22
 * @project IntelliJ IDEA
 */
@Service
public class PlayerPitServiceImpl implements IPlayerPitService {

    private final PlayerPitRepository playerPitRepository;

    @Autowired
    public PlayerPitServiceImpl(PlayerPitRepository playerPitRepository) {
        this.playerPitRepository = playerPitRepository;
    }

    @Override
    public Page<PlayerPits> search(String query, Integer page, Integer size) {
        return this.playerPitRepository.search(query, PageAttr.getPageRequestExact(page, size));
    }

    @Override
    public PlayerPits save(PlayerPits entity) {
        return this.playerPitRepository.save(entity);
    }

    @Override
    public Optional<PlayerPits> find(Long id) {
        return this.playerPitRepository.find(id);
    }

    @Override
    public void delete(Long id, Boolean softDelete) throws NotFoundException {
        if (!softDelete) {
            this.playerPitRepository.deleteById(id);
            return;
        }
        PlayerPits entity = this.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(PlayerPits.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity.setDeleted(true);
        this.save(entity);
    }
}

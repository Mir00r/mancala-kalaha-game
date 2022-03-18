package com.bol.mancala.domains.players.services;

import com.bol.mancala.domains.pits.repositories.PitRepository;
import com.bol.mancala.domains.players.models.entities.Player;
import com.bol.mancala.domains.players.repositories.PlayerRepository;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import com.bol.mancala.utils.Constants;
import com.bol.mancala.utils.ExceptionUtil;
import com.bol.mancala.utils.PageAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@Service
public class PlayerServiceImpl implements IPlayerService {

    private final PlayerRepository playerRepository;
    private final PitRepository pitRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PitRepository pitRepository) {
        this.playerRepository = playerRepository;
        this.pitRepository = pitRepository;
    }

    @Override
    public Page<Player> search(String query, Integer page, Integer size) {
        return this.playerRepository.search(query, PageAttr.getPageRequestExact(page, size));
    }

    @Override
    public Player save(Player entity) {
        if (entity.getPlayerPits() != null && entity.getPlayerPits().size() > 0) {
            entity.getPlayerPits().forEach(pp -> pp.setPit(this.pitRepository.save(pp.getPit())));
        }
        return this.playerRepository.save(entity);
    }

    @Override
    public List<Player> saveAutoAll(List<Player> entities) {
        entities.forEach(p -> {
            if (p.getPlayerPits() != null && p.getPlayerPits().size() > 0) {
                p.getPlayerPits().forEach(pp -> pp.setPit(this.pitRepository.save(pp.getPit())));
            }
        });
        return this.playerRepository.saveAll(entities);
    }

    @Override
    public Optional<Player> find(Long id) {
        return this.playerRepository.find(id);
    }

    @Override
    public void delete(Long id, Boolean softDelete) throws NotFoundException {
        if (!softDelete) {
            this.playerRepository.deleteById(id);
            return;
        }
        Player entity = this.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Player.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity.setDeleted(true);
        this.save(entity);
    }
}

package com.bol.mancala.domains.pits.services;

import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.pits.repositories.PitRepository;
import com.bol.mancala.exceptions.notfound.NotFoundException;
import com.bol.mancala.utils.Constants;
import com.bol.mancala.utils.ExceptionUtil;
import com.bol.mancala.utils.PageAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mir00r on 9/2/22
 * @project IntelliJ IDEA
 */
@Service
public class PitServiceImpl implements IPitService {

    private final PitRepository pitRepository;

    @Autowired
    public PitServiceImpl(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    @Override
    public Page<Pit> search(String query, Integer page, Integer size) {
        return this.pitRepository.search(query != null ? Long.parseLong(query) : null, PageAttr.getPageRequestExact(page, size));
    }

    @Override
    public Pit save(Pit entity) {
        return this.pitRepository.save(entity);
    }

    @Override
    public Optional<Pit> find(Long id) {
        return this.pitRepository.find(id);
    }

    @Override
    public void delete(Long id, Boolean softDelete) throws NotFoundException {
        if (!softDelete) {
            this.pitRepository.deleteById(id);
            return;
        }
        Pit entity = this.find(id).orElseThrow(() -> new NotFoundException(ExceptionUtil.getNotFoundMsg(Pit.class.getSimpleName(), Constants.NOT_FOUND_WITH_ID, id)));
        entity.setDeleted(true);
        this.save(entity);
    }
}

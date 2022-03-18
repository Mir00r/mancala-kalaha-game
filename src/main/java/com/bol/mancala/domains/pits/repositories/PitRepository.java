package com.bol.mancala.domains.pits.repositories;

import com.bol.mancala.domains.pits.models.entities.Pit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {

    @Query("SELECT p FROM Pit p WHERE (:q IS NULL OR p.stones = :q OR p.sequence = :q) AND p.deleted=false")
    Page<Pit> search(@Param("q") Long q, Pageable pageable);

    @Query("SELECT p FROM Pit p WHERE p.id=:id AND p.deleted=false")
    Optional<Pit> find(@Param("id") Long id);

    @Query("SELECT p FROM Pit p WHERE p.uuid=:uuid AND p.deleted=false")
    Optional<Pit> findByUuId(@Param("uuid") String uuid);

}

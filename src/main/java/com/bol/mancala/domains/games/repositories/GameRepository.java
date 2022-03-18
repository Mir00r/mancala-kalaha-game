package com.bol.mancala.domains.games.repositories;

import com.bol.mancala.domains.games.models.entities.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mir00r on 12/2/22
 * @project IntelliJ IDEA
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE (:q IS NULL OR g.playerA.name LIKE %:q% OR g.playerB.name LIKE %:q%) AND g.deleted=false")
    Page<Game> search(@Param("q") String q, Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.id=:id AND g.deleted=false")
    Optional<Game> find(@Param("id") Long id);

    @Query("SELECT g FROM Game g WHERE g.uuid=:uuid AND g.deleted=false")
    Optional<Game> findByUuId(@Param("uuid") String uuid);
}

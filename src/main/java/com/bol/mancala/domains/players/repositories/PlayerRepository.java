package com.bol.mancala.domains.players.repositories;

import com.bol.mancala.domains.players.models.entities.Player;
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
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE (:q IS NULL OR p.name LIKE %:q%) AND p.deleted=false")
    Page<Player> search(@Param("q") String q, Pageable pageable);

    @Query("SELECT p FROM Player p WHERE p.id=:id AND p.deleted=false")
    Optional<Player> find(@Param("id") Long id);

    @Query("SELECT p FROM Player p WHERE p.uuid=:uuid AND p.deleted=false")
    Optional<Player> findByUuId(@Param("uuid") String uuid);

}

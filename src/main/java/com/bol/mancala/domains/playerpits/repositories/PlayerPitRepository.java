package com.bol.mancala.domains.playerpits.repositories;

import com.bol.mancala.domains.pits.models.entities.Pit;
import com.bol.mancala.domains.playerpits.models.entities.PlayerPits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@Repository
public interface PlayerPitRepository extends JpaRepository<PlayerPits, Long> {

    @Query("SELECT pp FROM PlayerPits pp WHERE (:q IS NULL OR pp.player.name =:q) AND pp.deleted=false")
    Page<PlayerPits> search(@Param("q") String q, Pageable pageable);

    @Query("SELECT pp FROM PlayerPits pp WHERE (:playerId IS NULL OR pp.player.id =:playerId) AND pp.deleted=false")
    Page<PlayerPits> searchByPlayer(@Param("playerId") Long playerId, Pageable pageable);

    @Query("SELECT pp.pit FROM PlayerPits pp WHERE (:playerId IS NULL OR pp.player.id =:playerId) AND pp.deleted=false")
    List<Pit> searchPitByPlayer(@Param("playerId") Long playerId);

    @Query("SELECT pp FROM PlayerPits pp WHERE (:pitId IS NULL OR pp.pit.id =:pitId) AND pp.deleted=false")
    Page<PlayerPits> searchByPit(@Param("pitId") Long pitId, Pageable pageable);

    @Query("SELECT pp FROM PlayerPits pp WHERE pp.id=:id AND pp.deleted=false")
    Optional<PlayerPits> find(@Param("id") Long id);

    @Query("SELECT pp FROM PlayerPits pp WHERE pp.pit.id=:pitId AND pp.player.id =:playerId AND pp.deleted=false")
    Optional<PlayerPits> find(@Param("playerId") Long playerId, @Param("pitId") Long pitId);

    @Query("SELECT pp FROM PlayerPits pp WHERE pp.uuid=:uuid AND pp.deleted=false")
    Optional<PlayerPits> findByUuId(@Param("uuid") String uuid);

}

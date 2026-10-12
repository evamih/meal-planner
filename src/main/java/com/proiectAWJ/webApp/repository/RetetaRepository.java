/** Clasa repository pentru functiile cu acces la baza de date referitoare la retete
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.repository;

import com.proiectAWJ.webApp.models.Meniu;
import com.proiectAWJ.webApp.models.Reteta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RetetaRepository extends JpaRepository<Reteta, Long> {
    Optional<Reteta> findByNume(String url);

    @Query("SELECT r from Reteta r where lower(r.nume) like lower(concat('%', :query, '%'))") //jpql query
    List<Reteta> searchRetete(String query);

    @Query("select r from Reteta r where r.id = :retetaId")
    Reteta findByRetetaId(Long retetaId);

    @Modifying
    @Transactional
    @Query("update Reteta r set r.meniu = :meniu where r.id = :retetaId")
    void addRetetaToMeniu(Long retetaId, Meniu meniu);
}

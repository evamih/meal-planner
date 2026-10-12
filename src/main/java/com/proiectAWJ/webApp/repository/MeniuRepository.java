/** Clasa repository pentru functiile cu acces la baza de date referitoare la meniuri
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.repository;

import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.models.Meniu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeniuRepository extends JpaRepository<Meniu, Long> {
    @Query("select r from Reteta r where r.meniu.id = :meniuId")
    List<Reteta> findRetete(Long meniuId);
}

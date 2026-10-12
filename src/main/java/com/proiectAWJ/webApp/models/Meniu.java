/** Clasa entitate pentru lucrul cu tabelul meniuri din baza de date
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "meniuri")
public class Meniu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Numele meniului trebuie sa fie completat")
    private String nume;
    @NotEmpty(message = "Descrierea meniului trebuie sa fie completată")
    private String descriere;
    @NotNull(message = "Data realizării meniului trebuie să fie completată")
    @DateTimeFormat(pattern = "dd-mm-yyyy HH:mm")
    private LocalDateTime programatLa;
    private String status;
    @CreationTimestamp
    private LocalDateTime creatLa;

    @OneToMany(mappedBy = "meniu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Reteta> reteta = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDateTime getProgramatLa() {
        return programatLa;
    }

    public void setProgramatLa(LocalDateTime programatLa) {
        this.programatLa = programatLa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(LocalDateTime creatLa) {
        this.creatLa = creatLa;
    }

    public Set<Reteta> getReteta() {
        return reteta;
    }

    public void setReteta(Set<Reteta> reteta) {
        this.reteta = reteta;
    }

    public Meniu(Long id, String nume, String descriere, LocalDateTime programatLa, String status, LocalDateTime creatLa) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.programatLa = programatLa;
        this.status = status;
        this.creatLa = creatLa;
    }

    public Meniu() {
    }
}

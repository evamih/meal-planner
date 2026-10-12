/** Clasa entitate pentru lucrul cu tabelul retete din baza de date
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Retete")
public class Reteta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Numele retetei trebuie sa fie completat")
    public String nume;
    @NotEmpty(message = "Descrierea retetei trebuie sa fie completata")
    private String descriere;
    @NotEmpty(message = "Link-ul fotografiei retetei trebuie sa fie completat")
    public String photoUrl;
    //@Min(value = 1, message = "Timpul de preparare al retetei trebuie sa fie un nr pozitiv mai mare decat 0")
    //@Max(value = 500, message = "Timpul de preparare nu poate depăși 500 de minute.")
    private String timpPreparare;
    //@Min(value = 1, message = "Numarul de calorii al retetei trebuie sa fie un nr pozitiv mai mare decat 0")
    private String calorii;
    @CreationTimestamp
    private LocalDateTime creataLa;

    @OneToMany(mappedBy = "reteta", cascade = CascadeType.REMOVE)
    private Set<RetetaIngrediente> retetaIngrediente = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "meniu_id")
    private Meniu meniu;

    public Reteta()
    {
    }

    public Reteta(Long id, String nume, String descriere, String photoUrl, String timpPreparare, String calorii, LocalDateTime creataLa) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.photoUrl = photoUrl;
        this.timpPreparare = timpPreparare;
        this.calorii = calorii;
        this.creataLa = creataLa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTimpPreparare(String timpPreparare) {
        this.timpPreparare = timpPreparare;
    }

    public void setCalorii(String calorii) {
        this.calorii = calorii;
    }

    public void setCreataLa(LocalDateTime creataLa) {
        this.creataLa = creataLa;
    }

    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getTimpPreparare() {
        return timpPreparare;
    }

    public String getCalorii() {
        return calorii;
    }

    public LocalDateTime getCreataLa() {
        return creataLa;
    }


}

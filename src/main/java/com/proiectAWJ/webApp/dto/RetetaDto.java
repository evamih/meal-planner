/** Clasa dto (data transfer object) pentru lucrul cu obiectele de tip reteta
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RetetaDto {
    private Long id;
    @NotEmpty(message = "Numele retetei trebuie sa fie completat")
    public String nume;
    @NotEmpty(message = "Descrierea retetei trebuie sa fie completată")
    private String descriere;
    @NotEmpty(message = "Link-ul fotografiei retetei trebuie sa fie completat")
    public String photoUrl;
    //@Min(value = 1, message = "Timpul de preparare al retetei trebuie sa fie un nr pozitiv mai mare decat 0")
    //@Max(value = 500, message = "Timpul de preparare nu poate depăși 500 de minute.")
    private String timpPreparare;
    //@Min(value = 1, message = "Numarul de calorii al retetei trebuie sa fie un nr pozitiv mai mare decat 0")
    private String calorii;
    private LocalDateTime creataLa;

    public RetetaDto() {

    }

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTimpPreparare() {
        return timpPreparare;
    }

    public void setTimpPreparare(String timpPreparare) {
        this.timpPreparare = timpPreparare;
    }

    public String getCalorii() {
        return calorii;
    }

    public void setCalorii(String calorii) {
        this.calorii = calorii;
    }

    public LocalDateTime getCreataLa() {
        return creataLa;
    }

    public void setCreataLa(LocalDateTime creataLa) {
        this.creataLa = creataLa;
    }
    public RetetaDto(Long id, String nume, String descriere, String photoUrl, String timpPreparare, String calorii, LocalDateTime creataLa) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.photoUrl = photoUrl;
        this.timpPreparare = timpPreparare;
        this.calorii = calorii;
        this.creataLa = creataLa;
    }
}

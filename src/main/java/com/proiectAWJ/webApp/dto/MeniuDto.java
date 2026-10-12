/** Clasa dto (data transfer object) pentru lucrul cu obiectele de tip meniu
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class MeniuDto {
    private Long id;
    @NotEmpty(message = "Numele meniului trebuie sa fie completat")
    private String nume;
    @NotEmpty(message = "Descrierea meniului trebuie sa fie completată")
    private String descriere;
    @NotNull(message = "Data realizării meniului trebuie să fie completată")
    @DateTimeFormat(pattern = "dd-mm-yyyy HH:mm")
    private LocalDateTime programatLa;
    @NotEmpty(message = "Starea meniului trebuie sa fie completată")
    private String status;
    private LocalDateTime creatLa;

    public MeniuDto() {
    }

    public MeniuDto(Long id, String nume, String descriere, LocalDateTime programatLa, String status, LocalDateTime creatLa) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.programatLa = programatLa;
        this.status = status;
        this.creatLa = creatLa;
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
}

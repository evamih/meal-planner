/** Clasa rest controller pentru testarea metodelor de post, get, put si delete a retetelor cu ajutorul POSTMAN
 * @author Mihai Eva-Elena
 * @version 10 Ianuarie 2025
 */
package com.proiectAWJ.webApp.controllers;

import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.services.RetetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retete") // Prefixul pentru toate rutele api
public class RetetaAPIController {

    private final RetetaService retetaService;

    public RetetaAPIController(RetetaService retetaService) {
        this.retetaService = retetaService;
    }

    @GetMapping
    public List<RetetaDto> getAllRetete() {
        return retetaService.findAllRetete();
    }

    @GetMapping("/{id}")
    public RetetaDto getRetetaById(@PathVariable Long id) {
        return retetaService.findRetetaById(id);
    }

    @PostMapping
    public Reteta createReteta(@RequestBody Reteta reteta) {
        return retetaService.saveReteta(reteta);
    }

    @PutMapping("/{id}")
    public Reteta updateReteta(@PathVariable Long id, @RequestBody Reteta reteta) {
        return retetaService.updateRetetaById(reteta, id);
    }

    @DeleteMapping("/{id}")
    public void deleteReteta(@PathVariable Long id) {
        retetaService.delete(id);
    }
}


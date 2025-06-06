package com.energia.gestao.controller;

import com.energia.gestao.model.AlertaInterrupcao;
import com.energia.gestao.model.WeatherData;
import com.energia.gestao.service.AlertaService;
import com.energia.gestao.service.ExternalApiService; 

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") 
public class AlertaController {

    private final AlertaService alertaService;
    private final ExternalApiService externalApiService;

    @Autowired
    public AlertaController(AlertaService alertaService, ExternalApiService externalApiService) {
        this.alertaService = alertaService;
        this.externalApiService = externalApiService;
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<AlertaInterrupcao>> getAllAlertas() {
        List<AlertaInterrupcao> alertas = alertaService.getAllAlertas();
        return ResponseEntity.ok(alertas);
    }

    @PostMapping("/alertas/reportar-queda")
    public ResponseEntity<?> reportarQueda(@RequestParam String idMedidor, @RequestParam String descricao) {
        try {
            AlertaInterrupcao novoAlerta = alertaService.reportarQuedaEnergia(idMedidor, descricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAlerta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/alertas")
    public ResponseEntity<AlertaInterrupcao> createAlerta(@Valid @RequestBody AlertaInterrupcao alerta) {
        AlertaInterrupcao novoAlerta = alertaService.createAlerta(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAlerta);
    }


    @GetMapping("/clima/{city}")
    public ResponseEntity<WeatherData> getWeatherByCity(@PathVariable String city) {
        WeatherData weatherData = externalApiService.getWeatherForCity(city);
        if (weatherData != null) {
            return ResponseEntity.ok(weatherData);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
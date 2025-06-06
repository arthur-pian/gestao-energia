package com.energia.gestao.service;

import com.energia.gestao.model.AlertaInterrupcao;
import com.energia.gestao.model.LeituraMedidor;
import com.energia.gestao.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    @Autowired
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaInterrupcao> getAllAlertas() {
        return alertaRepository.findAll();
    }

    public AlertaInterrupcao createAlerta(AlertaInterrupcao alerta) {
        alerta.setDataHora(LocalDateTime.now());
        alerta.setResolvido(false);
        return alertaRepository.save(alerta);
    }

    public AlertaInterrupcao reportarQuedaEnergia(String idMedidor, String descricao) {
        boolean medidorEstaOnline = true;
        if (idMedidor.equals("MED002")) {
            medidorEstaOnline = false;
        }

        if (!medidorEstaOnline) {
            AlertaInterrupcao novoAlerta = new AlertaInterrupcao(idMedidor, "queda_energia", descricao, LocalDateTime.now(), false);
            return alertaRepository.save(novoAlerta);
        } else {
            throw new IllegalArgumentException("Medidor " + idMedidor + " está online, não há queda de energia reportada.");
        }
    }

}
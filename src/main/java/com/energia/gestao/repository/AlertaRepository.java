package com.energia.gestao.repository;

import com.energia.gestao.model.AlertaInterrupcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaInterrupcao, Long> {

}
package com.example.gestaoresiduos.repository;

import com.example.gestaoresiduos.entity.PontoDeColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoDeColetaRepository extends JpaRepository<PontoDeColeta, Long> {
  @Query("SELECT p FROM PontoDeColeta p WHERE p.nivelAtualKg >= p.capacidadeMaximaKg")
  List<PontoDeColeta> findPontosDeColetaComLimiteAtingido();

  @Query("SELECT p FROM PontoDeColeta p WHERE p.nivelAtualKg >= (p.capacidadeMaximaKg * (:percentual / 100.0))")
  List<PontoDeColeta> findPontosDeColetaAcimaPercentual(Double percentual);
}

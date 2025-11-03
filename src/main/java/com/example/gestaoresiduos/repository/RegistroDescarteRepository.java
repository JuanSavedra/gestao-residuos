package com.example.gestaoresiduos.repository;

import com.example.gestaoresiduos.entity.RegistroDescarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroDescarteRepository extends JpaRepository<RegistroDescarte, Long> {
  List<com.example.gestaoresiduos.entity.RegistroDescarte> findByUsuarioIdOrderByDataHoraDesc(Long usuarioId);
}

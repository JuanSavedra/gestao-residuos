package com.example.gestaoresiduos.repository;

import com.example.gestaoresiduos.entity.TipoDeResiduo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDeResiduoRepository extends JpaRepository<TipoDeResiduo, Long> {

}

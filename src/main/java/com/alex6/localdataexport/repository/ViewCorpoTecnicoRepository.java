package com.alex6.localdataexport.repository;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewCorpoTecnicoRepository extends JpaRepository<ViewCorpoTecnico, Long> {

    @Query(nativeQuery = true,
           value = "SELECT * FROM VW_CORPO_TECNICO vct " +
                   "WHERE vct.ANO_REFERENCIA = ?1 AND " +
                   "vct.SG_ENTIDADE_NACIONAL = ?2 AND " +
                   "vct.SG_ENTIDADE_REGIONAl = ?3")
    List<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento);
}

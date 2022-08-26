package com.alex6.localdataexport.service;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.repository.ViewCorpoTecnicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewCorpoTecnicoService {

    private ViewCorpoTecnicoRepository viewCorpoTecnicoRepository;

    public List<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento){
        return viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(ano, sgEntidade, sgDepartamento);
    }
}

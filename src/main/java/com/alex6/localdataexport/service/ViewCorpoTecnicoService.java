package com.alex6.localdataexport.service;

import com.alex6.localdataexport.controller.exceptions.ByteTransferErrorException;
import com.alex6.localdataexport.controller.exceptions.ObjectNotFoundException;
import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.enums.TipoExportacaoEnum;
import com.alex6.localdataexport.exporter.ExportadorODSStrategy;
import com.alex6.localdataexport.exporter.ExportadorXLSXStrategy;
import com.alex6.localdataexport.repository.ViewCorpoTecnicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class ViewCorpoTecnicoService {

    private ViewCorpoTecnicoRepository viewCorpoTecnicoRepository;

    public List<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento) throws ObjectNotFoundException {
        var corpoTecnicoList = viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(ano, sgEntidade, sgDepartamento);

        if(corpoTecnicoList.isEmpty())
            throw new ObjectNotFoundException("Não foram encontrados colaboradores para os parâmetros informados.");

        return corpoTecnicoList;
    }

    public Page<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento, Pageable pageable) throws ObjectNotFoundException {
        var corpoTecnicoList = viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(ano, sgEntidade, sgDepartamento, pageable);

        if(corpoTecnicoList.isEmpty())
            throw new ObjectNotFoundException("Não foram encontrados colaboradores para os parâmetros informados.");

        return corpoTecnicoList;
    }

    public void export(List<ViewCorpoTecnico> corpoTecnicoList, TipoExportacaoEnum tipoExportacao, HttpServletResponse response) throws ByteTransferErrorException {

        var opcoesExport = List.of(new ExportadorODSStrategy(), new ExportadorXLSXStrategy());

        String[] headers = new String[]{"Nome dos Empregados"};

        var exportador = opcoesExport.get(tipoExportacao.getCode()-1);

        exportador.export(corpoTecnicoList, headers, "CorpoTecnico.xlsx", response);
    }
}

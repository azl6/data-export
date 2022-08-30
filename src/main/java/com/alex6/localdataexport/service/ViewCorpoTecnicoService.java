package com.alex6.localdataexport.service;

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

    public List<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento){
        return viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(ano, sgEntidade, sgDepartamento);
    }

    public Page<ViewCorpoTecnico> findAllByAnoEntidadeDepartamento(Integer ano, String sgEntidade, String sgDepartamento, Pageable pageable) {
        return viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(ano, sgEntidade, sgDepartamento, pageable);
    }

    public void export(List<ViewCorpoTecnico> corpoTecnicoList, TipoExportacaoEnum tipoExportacao, HttpServletResponse response) {

        var opcoesExport = List.of(new ExportadorODSStrategy(), new ExportadorXLSXStrategy());

        String[] headers = new String[]{"Nome dos Empregados"};

        var exportador = opcoesExport.get(tipoExportacao.getCode()-1);

        exportador.export(corpoTecnicoList, headers, "export.xlsx", response);
    }
}

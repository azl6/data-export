package com.alex6.localdataexport.controller;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.enums.TipoExportacaoEnum;
import com.alex6.localdataexport.exporter.ExportadorODSStrategy;
import com.alex6.localdataexport.exporter.ExportadorStrategy;
import com.alex6.localdataexport.exporter.ExportadorXLSXStrategy;
import com.alex6.localdataexport.service.ViewCorpoTecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-corpo-tecnico")
@AllArgsConstructor
public class ViewCorpoTecnicoController {

    ViewCorpoTecnicoService viewCorpoTecnicoService;

    @GetMapping("/export")
    public ResponseEntity<List<ViewCorpoTecnico>> export(@RequestParam Integer ano,
                                                         @RequestParam String entidade,
                                                         @RequestParam String departamento,
                                                         @RequestParam(required = false) TipoExportacaoEnum tipoExportacao){

        List<ViewCorpoTecnico> corpoTecnicoList = viewCorpoTecnicoService
                                                    .findAllByAnoEntidadeDepartamento(ano, entidade, departamento);

        String[] headers = new String[]{"Nome do funcionário", "Entidade", "Departamento"};

        var opcoesExport = List.of(new ExportadorODSStrategy(), new ExportadorXLSXStrategy());

        opcoesExport.get(tipoExportacao.getCode() - 1).export(corpoTecnicoList, headers, departamento.concat(ano + ".xlsx"));

        return ResponseEntity.ok(corpoTecnicoList);
    }
}

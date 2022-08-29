package com.alex6.localdataexport.controller;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.enums.TipoExportacaoEnum;
import com.alex6.localdataexport.service.ViewCorpoTecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api-corpo-tecnico")
@AllArgsConstructor
public class ViewCorpoTecnicoController {

    ViewCorpoTecnicoService viewCorpoTecnicoService;

    @GetMapping("/export")
    public void export(@RequestParam Integer ano,
                       @RequestParam String entidade,
                       @RequestParam String departamento,
                       @RequestParam(required = false) TipoExportacaoEnum tipoExportacao){

        List<ViewCorpoTecnico> corpoTecnicoList = viewCorpoTecnicoService
                                                    .findAllByAnoEntidadeDepartamento(ano, entidade, departamento);

        viewCorpoTecnicoService.export(corpoTecnicoList, tipoExportacao);

    }

    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Page<ViewCorpoTecnico>> list(@RequestParam Integer ano,
                                                       @RequestParam String entidade,
                                                       @RequestParam String departamento,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "15") Integer size,
                                                       @RequestParam(required = false, defaultValue = "NOME_FUNCIONARIO") String sortBy){

        Page<ViewCorpoTecnico> corpoTecnicoPage = viewCorpoTecnicoService
                .findAllByAnoEntidadeDepartamento(ano, entidade, departamento, PageRequest.of(page, size, Sort.by(sortBy)));

        return ResponseEntity.ok(corpoTecnicoPage);

    }
}

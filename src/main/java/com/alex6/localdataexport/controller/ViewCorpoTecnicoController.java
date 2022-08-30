package com.alex6.localdataexport.controller;

import com.alex6.localdataexport.controller.exceptions.ByteTransferErrorException;
import com.alex6.localdataexport.controller.exceptions.ObjectNotFoundException;
import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.enums.TipoExportacaoEnum;
import com.alex6.localdataexport.service.ViewCorpoTecnicoService;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api-corpo-tecnico")
@AllArgsConstructor
public class ViewCorpoTecnicoController {

    ViewCorpoTecnicoService viewCorpoTecnicoService;

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestParam Integer ano,
                       @RequestParam String entidade,
                       @RequestParam String departamento,
                       @RequestParam(required = false) TipoExportacaoEnum tipoExportacao,
                       HttpServletResponse response) throws ObjectNotFoundException, ByteTransferErrorException {

        List<ViewCorpoTecnico> corpoTecnicoList = viewCorpoTecnicoService
                                                    .findAllByAnoEntidadeDepartamento(ano, entidade, departamento);

        viewCorpoTecnicoService.export(corpoTecnicoList, tipoExportacao, response);

    }

    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Page<ViewCorpoTecnico>> list(@RequestParam Integer ano,
                                                       @RequestParam String entidade,
                                                       @RequestParam String departamento,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "15") Integer size,
                                                       @RequestParam(required = false, defaultValue = "NOME_FUNCIONARIO") String sortBy) throws ObjectNotFoundException {

        Page<ViewCorpoTecnico> corpoTecnicoPage = viewCorpoTecnicoService
                .findAllByAnoEntidadeDepartamento(ano, entidade, departamento, PageRequest.of(page, size, Sort.by(sortBy)));

        return ResponseEntity.ok(corpoTecnicoPage);

    }
}

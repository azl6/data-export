package com.alex6.localdataexport;

import com.alex6.localdataexport.controller.ViewCorpoTecnicoController;
import com.alex6.localdataexport.controller.exceptions.ObjectNotFoundException;
import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.repository.ViewCorpoTecnicoRepository;
import com.alex6.localdataexport.service.ViewCorpoTecnicoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = LocaldataexportApplicationTests.class)
@AutoConfigureMockMvc
public class ViewCorpoTecnicoControllerTest {

    ViewCorpoTecnicoService viewCorpoTecnicoService;

    MockMvc mockMvc;

    @MockBean
    ViewCorpoTecnicoRepository viewCorpoTecnicoRepository;

    ViewCorpoTecnicoController viewCorpoTecnicoController;

    List<ViewCorpoTecnico> corpoTecnicoList = new ArrayList<>();

    @BeforeEach
    void setUp(){

        corpoTecnicoList.add(ViewCorpoTecnico.builder()
                .nomeFuncionario("CÉLIA FERREIRA DE OLIVEIRA")
                .ano("2022")
                .sgEntidadeNacional("SESI")
                .sgEntidadeRegional("SESI-SP")
                .titulo("TITULO")
                .nota("NOTA")
                .fonte("FONTE")
                .extra("EXTRA")
                .build());

        corpoTecnicoList.add(ViewCorpoTecnico.builder()
                .nomeFuncionario("RENATO ROMIRO RODRIGUES")
                .ano("2022")
                .sgEntidadeNacional("SESI")
                .sgEntidadeRegional("SESI-SP")
                .titulo("TITULO")
                .nota("NOTA")
                .fonte("FONTE")
                .extra("EXTRA")
                .build());

        corpoTecnicoList.add(ViewCorpoTecnico.builder()
                .nomeFuncionario("MARCELO PRADO LEITE DA SILVA")
                .ano("2022")
                .sgEntidadeNacional("SESI")
                .sgEntidadeRegional("SESI-SP")
                .titulo("TITULO")
                .nota("NOTA")
                .fonte("FONTE")
                .extra("EXTRA")
                .build());

        viewCorpoTecnicoService = new ViewCorpoTecnicoService(viewCorpoTecnicoRepository);

        viewCorpoTecnicoController = new ViewCorpoTecnicoController(viewCorpoTecnicoService);

        mockMvc = MockMvcBuilders.standaloneSetup(viewCorpoTecnicoController).build();
    }

    @Test
    public void dadaViewPublica_quandoPassamosParametrosNaoExistentesNaView_EntaoLancaExcecao() throws Exception {

        assertThatExceptionOfType(NestedServletException.class)
                .isThrownBy(() -> {
                    mockMvc.perform(MockMvcRequestBuilders.get("/api-corpo-tecnico/export")
                                    .param("ano", "2023")
                                    .param("entidade", "ENTIDADE_NAO_EXISTENTE")
                                    .param("departamento", "DEPARTAMENTO_NAO_EXISTENTE")
                                    .param("tipoExportacao", "XLSX"))
                            .andExpect(status().isNotFound());
                })
                .withMessageContaining("Não foram encontrados colaboradores para os parâmetros informados.");

    }

    @Test
    public void dadaViewPublica_QuandoPassamosParametrosValidos_EntaoRetornaPageDeColaboradores() throws Exception {

        BDDMockito.given(
                viewCorpoTecnicoRepository.findAllByAnoEntidadeDepartamento(
                        2022, "SESI", "SESI-SP", PageRequest.of(0, 15, Sort.by("NOME_FUNCIONARIO").ascending()))).willReturn(new PageImpl<>(corpoTecnicoList));

        mockMvc.perform(MockMvcRequestBuilders.get("/api-corpo-tecnico/list")
                        .param("ano", "2022")
                        .param("entidade", "SESI")
                        .param("departamento", "SESI-SP")
                        .param("page", "0")
                        .param("size", "15")
                )
                .andDo(print())
                .andExpect(jsonPath("$.content[0].nomeFuncionario", is("CÉLIA FERREIRA DE OLIVEIRA")))
                .andExpect(jsonPath("$.content[1].nomeFuncionario", is("RENATO ROMIRO RODRIGUES")))
                .andExpect(jsonPath("$.content[2].nomeFuncionario", is("MARCELO PRADO LEITE DA SILVA")))
                .andExpect(status().isOk());

    }
}

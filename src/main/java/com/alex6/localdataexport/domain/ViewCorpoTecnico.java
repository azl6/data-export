package com.alex6.localdataexport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VW_CORPO_TECNICO")
public class ViewCorpoTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoCorpoTecnico;//TODO: RETIRAR DAQUI PRA CIMA. VIEW N TEM CODIGO

    @Column(name = "NOME_FUNCIONARIO")
    private String nomeFuncionario;

    @Column(name = "SG_ENTIDADE_NACIONAL")
    private String sgEntidadeNacional;

    @Column(name = "SG_ENTIDADE_REGIONAL")
    private String sgEntidadeRegional;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "NOTA")
    private String nota;

    @Column(name = "FONTE")
    private String fonte;

    @Column(name = "EXTRA")
    private String extra;

    @Column(name = "ANO_REFERENCIA")
    private String ano;
}

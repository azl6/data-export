package com.alex6.localdataexport;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import com.alex6.localdataexport.exporter.ExportadorXLSXStrategy;
import com.alex6.localdataexport.repository.ViewCorpoTecnicoRepository;
import com.alex6.localdataexport.service.ViewCorpoTecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class LocaldataexportApplication implements CommandLineRunner {

	private ViewCorpoTecnicoRepository viewCorpoTecnicoRepository;

	public static void main(String[] args) {
		SpringApplication.run(LocaldataexportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<ViewCorpoTecnico> corpoTecnicoList = new ArrayList<>();

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

		viewCorpoTecnicoRepository.saveAll(corpoTecnicoList);

	}
}

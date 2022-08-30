package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class ExportadorODSStrategy implements ExportadorStrategy{
    @Override
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName, HttpServletResponse response) {
        System.out.println("Exportará ODS...");
    }
}

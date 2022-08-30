package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;

import java.io.File;
import java.util.List;

public class ExportadorODSStrategy implements ExportadorStrategy{
    @Override
    public byte[] export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName) {
        System.out.println("Exportará ODS...");
        return null;
    }
}

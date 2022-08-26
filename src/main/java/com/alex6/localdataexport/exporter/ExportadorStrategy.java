package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;

import java.util.List;

public interface ExportadorStrategy {
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName);
}

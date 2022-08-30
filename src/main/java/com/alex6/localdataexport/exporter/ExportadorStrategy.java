package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportadorStrategy {
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName, HttpServletResponse response);
}

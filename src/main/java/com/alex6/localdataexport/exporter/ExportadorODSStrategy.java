package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import org.apache.poi.ss.usermodel.*;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportadorODSStrategy implements ExportadorStrategy{
    @Override
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName, HttpServletResponse response){
        System.out.println("Exportará ODS...");

        String titulo = corpoTecnicoList.get(0).getTitulo();
        String nota = corpoTecnicoList.get(0).getNota();
        String fonte = corpoTecnicoList.get(0).getFonte();
        String extra = corpoTecnicoList.get(0).getExtra();
        String sgEntidade = corpoTecnicoList.get(0).getSgEntidadeNacional();
        String sgDepartamento = corpoTecnicoList.get(0).getSgEntidadeRegional();
        String ano = corpoTecnicoList.get(0).getAno();

        final Object[][] data = new Object[100][3];
        data[0] = createDataDeEmissaoRow();
        data[1] = createDataDePublicacaoRow();
        data[3] = createTituloRow(ano);
        data[4] = createDepartamentoRegionalRow(sgDepartamento);
        data[6] = createHeaderRow(headers);
        createCorposTecnicosRows(data, corpoTecnicoList);
        data[corpoTecnicoList.size() + 8] = createFonteRow(fonte);
        data[corpoTecnicoList.size() + 9] = createNotaRow(nota);


        String[] columns = new String[] { "Transparência " + sgEntidade, "", "" };

        TableModel model = new DefaultTableModel(data, columns);

        final File file = new File("temperature.ods");

        try{
            SpreadSheet.createEmpty(model).saveAs(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Object[] createDataDeEmissaoRow(){

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        String[] dateTime = timeStamp.split(" ");

        return new Object[]{"Data de emissão:", dateTime[0], dateTime[1]};
    }

    private Object[] createDataDePublicacaoRow(){
        return new Object[]{"Data de publicação:", "26/08/2022", "14:44:44"};
    }

    private Object[] createTituloRow(String ano){
        return new Object[]{"Relação dos Membros do Corpo Técnico - " + ano, "", ""};
    }

    private Object[] createDepartamentoRegionalRow(String departamento) {
        String dnOuDr = departamento.endsWith("DN") ? "Nacional" : "Regional";
        return new Object[]{"Departamento " + dnOuDr + " - " + departamento, "", ""};
    }

    private Object[] createHeaderRow(String[] headers) {
        return new Object[]{headers[0], "", ""};
    }


    private void createCorposTecnicosRows(Object[][] data, List<ViewCorpoTecnico> corpoTecnicoList) {
        int corpoTecnicoIndex=0;

        for (int i = 7; i < corpoTecnicoList.size() + 7; i++, corpoTecnicoIndex++) {
            data[i] = new Object[]{corpoTecnicoList.get(corpoTecnicoIndex).getNomeFuncionario(), "", ""};
        }
    }

    private Object[] createFonteRow(String fonte){
        return new Object[]{fonte, "", ""};
    }

    private Object[] createNotaRow(String nota){
        return new Object[]{nota, "", ""};
    }
}

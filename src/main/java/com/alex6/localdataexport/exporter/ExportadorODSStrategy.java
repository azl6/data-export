package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;

import lombok.extern.slf4j.Slf4j;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.FileUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class ExportadorODSStrategy implements ExportadorStrategy{

    private int[] tamanhoColunas = {80, 25 , 25};

    @Override
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName, HttpServletResponse response){
        log.info("Exportará ODS...");

        Integer listSize = corpoTecnicoList.size();
        Integer fonteIndex = listSize + 8;
        Integer notaIndex = listSize + 9;

        final Object[][] data = new Object[listSize][3];

        String titulo = corpoTecnicoList.get(0).getTitulo();
        String nota = corpoTecnicoList.get(0).getNota();
        String fonte = corpoTecnicoList.get(0).getFonte();
        String extra = corpoTecnicoList.get(0).getExtra();
        String sgEntidade = corpoTecnicoList.get(0).getSgEntidadeNacional();
        String sgDepartamento = corpoTecnicoList.get(0).getSgEntidadeRegional();
        String ano = corpoTecnicoList.get(0).getAno();

        data[0] = createDataDeEmissaoRow();
        data[1] = createDataDePublicacaoRow();
        data[3] = createTituloRow(ano);
        data[4] = createDepartamentoRegionalRow(sgDepartamento);
        data[6] = createHeaderRow(headers);
        createCorposTecnicosRows(data, corpoTecnicoList);
        data[fonteIndex] = createFonteRow(fonte);
        data[notaIndex] = createNotaRow(nota);

        String[] columns = new String[] { "Transparência " + sgEntidade, "", "" };

        TableModel model = new DefaultTableModel(data, columns);

        SpreadSheet spreadSheet = SpreadSheet.createEmpty(model);
        formatSheet(spreadSheet);
        download(spreadSheet, response);

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

    private void formatSheet(SpreadSheet spreadSheet){
        Sheet sheet = spreadSheet.getFirstSheet();
        for(int coluna = 0; coluna < 3; coluna++){
            MutableCell<SpreadSheet> cell = sheet.getCellAt(0, 7);
            cell.setBackgroundColor(Color.decode("#c0c0c0"));
            sheet.getColumn(coluna).setWidth(tamanhoColunas[coluna]);
        }
    }

    private void download(SpreadSheet spreadSheet, HttpServletResponse response) {
        byte[] dataFile = getBytesDataFile(spreadSheet);
        setResponseProperties(response);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(dataFile, 0, dataFile.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponseProperties(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=CorpoTecnico.ods");
        response.setContentType("application/octet-stream");
    }

    private byte[] getBytesDataFile(SpreadSheet spreadSheet){
        File file = new File("CorpoTecnico.ods");
        spreadSheet.getFirstSheet().setName("Membros do Corpo Tecnico");
        try {
            spreadSheet.saveAs(file);
            return FileUtils.readBytes(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

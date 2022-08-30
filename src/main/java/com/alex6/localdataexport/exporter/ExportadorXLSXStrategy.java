package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportadorXLSXStrategy implements ExportadorStrategy{

    @Override
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName, HttpServletResponse response) {
        System.out.println("Exportará XLSX...");

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(fileName);

        String titulo = corpoTecnicoList.get(0).getTitulo();
        String nota = corpoTecnicoList.get(0).getNota();
        String fonte = corpoTecnicoList.get(0).getFonte();
        String extra = corpoTecnicoList.get(0).getExtra();
        String sgEntidade = corpoTecnicoList.get(0).getSgEntidadeNacional();
        String sgDepartamento = corpoTecnicoList.get(0).getSgEntidadeRegional();
        String ano = corpoTecnicoList.get(0).getAno();

        createFirstRow(workbook, sheet, sgEntidade);
        createDataDeEmissaoRow(workbook, sheet); //TODO
        createDataDePublicacaoRow(workbook, sheet);
        createTituloRow(workbook, sheet, ano);
        createDepartamentoRegionalRow(workbook, sheet, sgDepartamento);
        createHeaderRow(workbook, sheet, headers);

        int corpoTecnicoIndex=0;

        for(int i = 7; i < corpoTecnicoList.size()+7; i++, corpoTecnicoIndex++) {
            int rowIndex = i + 1;
            createNewRow(workbook, sheet, rowIndex, corpoTecnicoList.get(corpoTecnicoIndex));
        }

        createFonteRow(workbook, sheet, (7 + corpoTecnicoList.size() + 2), fonte);
        createNotaRow(workbook, sheet, (7 + corpoTecnicoList.size() + 3), nota);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        setResponseProperties(response);
        download(response, workbook);
    }

    private void setResponseProperties(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=CorpoTecnico.xlsx");
        response.setContentType("application/octet-stream");
    }

    private void download(HttpServletResponse response, Workbook workbook){
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFirstRow(Workbook workbook, Sheet sheet, String entidade){
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", true, 20);

        Cell cell = row.createCell(0);
        cell.setCellValue("Transparência " + entidade);
        cell.setCellStyle(cellStyle);

    }

    private void createDataDeEmissaoRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(1);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", false, 11);

        Cell cellStr = row.createCell(0);
        cellStr.setCellStyle(cellStyle);
        cellStr.setCellValue("Data de emissão:");

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        String[] dateTime = timeStamp.split(" ");

        Cell cellDate = row.createCell(1);
        cellDate.setCellStyle(cellStyle);
        cellDate.setCellValue(dateTime[0]);

        Cell cellHour = row.createCell(2);
        cellHour.setCellStyle(cellStyle);
        cellHour.setCellValue(dateTime[1]);

    }

    private void createDataDePublicacaoRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(2);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", false, 11);

        Cell cellStr = row.createCell(0);
        cellStr.setCellStyle(cellStyle);
        cellStr.setCellValue("Data de publicação:");

        Cell cellDate = row.createCell(1);
        cellDate.setCellStyle(cellStyle);
        cellDate.setCellValue("20/08/2022"); //TODO ALTERAR B3

        Cell cellHour = row.createCell(2);
        cellHour.setCellStyle(cellStyle);
        cellHour.setCellValue("14:44:11"); //TODO ALTERAR C3

    }

    private void createTituloRow(Workbook workbook, Sheet sheet, String ano){
        Row row = sheet.createRow(4);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", true, 14);

        Cell cellStr = row.createCell(0);
        cellStr.setCellStyle(cellStyle);
        cellStr.setCellValue("Relação dos Membros do Corpo Técnico - " + ano);

    }

    private void createDepartamentoRegionalRow(Workbook workbook, Sheet sheet, String departamento){
        Row row = sheet.createRow(5);

        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", true, 10);

        Cell cellStr = row.createCell(0);
        cellStr.setCellStyle(cellStyle);

        String dnOuDr = departamento.endsWith("DN") ? "Nacional" : "Regional";
        cellStr.setCellValue("Departamento " + dnOuDr  + " - " + departamento);

    }

    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(7);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", true, 10);

        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Cell cell = headerRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(headers[0]);
    }

    private void createNewRow(Workbook workbook, Sheet sheet, int rowIndex, ViewCorpoTecnico corpoTecnico) {
        Row row = sheet.createRow(rowIndex);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", false, 10);

        Cell cell = row.createCell(0);
        cell.setCellValue(corpoTecnico.getNomeFuncionario());
        cell.setCellStyle(cellStyle);
    }

    private void createFonteRow(Workbook workbook, Sheet sheet, int rowIndex, String fonte){
        Row row = sheet.createRow(rowIndex);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Calibri", false, 11);

        Cell cell = row.createCell(0);
        cell.setCellValue(fonte);
        cell.setCellStyle(cellStyle);
    }

    private void createNotaRow(Workbook workbook, Sheet sheet, int rowIndex, String nota){
        Row row = sheet.createRow(rowIndex);
        CellStyle cellStyle = workbook.createCellStyle();

        setUpFont(workbook, cellStyle, "Arial", false, 9);

        Cell cell = row.createCell(0);
        cell.setCellValue(nota);
        cell.setCellStyle(cellStyle);
    }

    private void setUpFont(Workbook workbook, CellStyle cellStyle, String fontName, boolean isBold, int fontHeight){
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setBold(isBold);
        font.setFontHeightInPoints((short) fontHeight);
        cellStyle.setFont(font);
    }
}
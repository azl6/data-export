package com.alex6.localdataexport.exporter;

import com.alex6.localdataexport.domain.ViewCorpoTecnico;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportadorXLSXStrategy implements ExportadorStrategy{

    @Override
    public void export(List<ViewCorpoTecnico> corpoTecnicoList, String[] headers, String fileName) {
        System.out.println("Exportará XLSX...");

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(fileName);

        String titulo = corpoTecnicoList.get(0).getTitulo();
        String nota = corpoTecnicoList.get(0).getNota();
        String fonte = corpoTecnicoList.get(0).getFonte();
        String extra = corpoTecnicoList.get(0).getExtra();

        createFirstRow(workbook, sheet, "2022");
        createDataDeEmissaoRow(workbook, sheet);
        createDataDePublicacaoRow(workbook, sheet);
        createTituloRow(workbook, sheet);
        createDepartamentoRegionalRow(workbook, sheet);
        createHeaderRow(workbook, sheet, headers);

        int corpoTecnicoIndex=0;

        for(int i = 7; i < corpoTecnicoList.size()+7; i++, corpoTecnicoIndex++) {
            int rowIndex = i + 1;
            createNewRow(workbook, sheet, rowIndex, corpoTecnicoList.get(corpoTecnicoIndex));
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFirstRow(Workbook workbook, Sheet sheet, String ano){
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Membros do Corpo Técnico - " + ano);

    }

    private void createDataDeEmissaoRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(1);

        Cell cellStr = row.createCell(0);
        cellStr.setCellValue("Data de emissão:");

        Cell cellDate = row.createCell(1);
        cellDate.setCellValue("26/08/2022");

        Cell cellHour = row.createCell(2);
        cellHour.setCellValue("13:50:32");

    }

    private void createDataDePublicacaoRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(2);

        Cell cellStr = row.createCell(0);
        cellStr.setCellValue("Data de publicação:");

        Cell cellDate = row.createCell(1);
        cellDate.setCellValue("20/08/2022");

        Cell cellHour = row.createCell(2);
        cellHour.setCellValue("14:44:11");

    }

    private void createTituloRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(4);

        Cell cellStr = row.createCell(0);
        cellStr.setCellValue("TITULO - ANO");

    }

    private void createDepartamentoRegionalRow(Workbook workbook, Sheet sheet){
        Row row = sheet.createRow(5);

        Cell cellStr = row.createCell(0);
        cellStr.setCellValue("DEPARTAMENTO REGIONAL");

    }

    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(7);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        for(int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void createNewRow(Workbook workbook, Sheet sheet, int rowIndex, ViewCorpoTecnico corpoTecnico) {
        Row row = sheet.createRow(rowIndex);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        Cell cell = row.createCell(0);
        cell.setCellValue(corpoTecnico.getNomeFuncionario());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue(corpoTecnico.getSgEntidadeNacional());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue(corpoTecnico.getSgEntidadeRegional());
        cell.setCellStyle(cellStyle);
    }

}
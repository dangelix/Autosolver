/**
 *   Copyright 2013 Nekorp
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tikal.tallerWeb.servicio.reporte.cliente;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tikal.tallerWeb.modelo.reporte.cliente.RegistroCostoRC;
import com.tikal.tallerWeb.modelo.reporte.cliente.ReporteCliente;

/**
 *
 */
@Component
public class CostoMecanicaReporteCliente extends AbstractSeccionXLS implements GeneradorSeccionXLS {

    @Override
    public BordeSeccion generar(BordeSeccion borde, ContextoSeccion contexto, ReporteCliente datos) {
        Sheet sheet = contexto.getSheet();
        XSSFWorkbook wb = contexto.getWb();
        int initialRow = borde.getUpperRow();
        int initialColumn = borde.getLeftColumn();
        BordeSeccion r = new BordeSeccion();
        r.setLeftColumn(initialColumn);
        r.setUpperRow(initialRow);
        
        Cell cell;
        Row row = getRow(sheet, initialRow);
        cell = row.createCell(initialColumn);
        cell.setCellValue("Mecanica");
        XSSFCellStyle cellStyle = wb.createCellStyle();
        addHeaderStyle(cellStyle, wb);
        addBorders(wb, cellStyle, CellStyle.BORDER_MEDIUM);
        cell.setCellStyle(cellStyle);
        for (int i = 1; i < 3; i++) {
            cell = row.createCell(initialColumn + i);
            cellStyle = wb.createCellStyle();
            addBorders(wb, cellStyle, CellStyle.BORDER_MEDIUM);
            cell.setCellStyle(cellStyle);
        }
        //merge de celdas
        sheet.addMergedRegion(new CellRangeAddress(
                initialRow, //first row (0-based)
                initialRow, //last row  (0-based)
                initialColumn, //first column (0-based)
                initialColumn + 2  //last column  (0-based)
        ));
        
        //detalle
        cellStyle = wb.createCellStyle();
        XSSFDataFormat df = wb.createDataFormat();
        cellStyle.setDataFormat(df.getFormat("$#,##0.00"));
        Cell inicio = null;
        Cell fin = null;
        for (RegistroCostoRC x: datos.getRegistroMecanica()) {
            initialRow = initialRow + 1;
            row = getRow(sheet, initialRow);
            //tipo
            cell = row.createCell(initialColumn);
            cell.setCellValue(x.getTipo());
            //descripcion
            cell = row.createCell(initialColumn + 1);
            cell.setCellValue(x.getDescripcion());
            //costo
            cell = row.createCell(initialColumn + 2);
            cell.setCellValue(x.getCosto());
            cell.setCellStyle(cellStyle);
            if (inicio == null) {
               inicio = cell;
            }
        }
        if (inicio != null) {
            fin = cell;
        }
        initialRow = initialRow + 1;
        row = getRow(sheet, initialRow);
        cell = row.createCell(initialColumn + 1);
        cell.setCellValue("Total");
        cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        
        cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setDataFormat(df.getFormat("$#,##0.00"));
        cell = row.createCell(initialColumn + 2);
        cell.setCellStyle(cellStyle);
        if (inicio != null) {
            String formula = "SUM("+getSimpleReference(inicio)+":"+getSimpleReference(fin)+")";
            cell.setCellFormula(formula);
            contexto.put("totalMecanica", cell);
        } else {
            cell.setCellValue(0.0);
        }
        r.setLowerRow(initialRow);
        r.setRightColumn(initialColumn + 2);
        BordeSeccion sinTotal = new BordeSeccion(r);
        sinTotal.setLowerRow(r.getLowerRow() - 1);
        paintBorder(wb, sheet, CellStyle.BORDER_MEDIUM, sinTotal);
        return r;
    }

}

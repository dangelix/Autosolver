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

import java.awt.Color;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 */
public class AbstractSeccionXLS {

    public void addHeaderStyle(XSSFCellStyle cellStyle, XSSFWorkbook wb) {
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        cellStyle.setFillForegroundColor(new XSSFColor(new Color(85, 142, 213)));
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        XSSFFont font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);
    }
    
    public void addBorders(XSSFWorkbook wb, XSSFCellStyle cellStyle, short borderType) {
        cellStyle.setBorderBottom(borderType);        
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(borderType);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(borderType);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(borderType);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    }
    
    public Row getRow(Sheet sheet, int i) {
        Row r = sheet.getRow(i);
        if (r == null) {
            r = sheet.createRow(i);
        }
        return r;
    }
    
    public String getSimpleReference(Cell cell) {
        CellReference reference = new CellReference(cell);
        String[] partsInicio = reference.getCellRefParts();
        return partsInicio[2] + partsInicio[1];
    }
    
    public void paintBorder(XSSFWorkbook wb, Sheet sheet, short borderType, BordeSeccion borde) {
        for (int i = borde.getUpperRow(); i <= borde.getLowerRow(); i++) {
            Row row = sheet.getRow(i);
            for (int j = borde.getLeftColumn(); j <= borde.getRightColumn(); j++) {
                if (i == borde.getUpperRow() || i == borde.getLowerRow() || j == borde.getLeftColumn() || j == borde.getRightColumn()) {
                    Cell cell = row.getCell(j);
                    XSSFCellStyle actual = (XSSFCellStyle) cell.getCellStyle();
                    XSSFCellStyle nuevo = wb.createCellStyle();
                    nuevo.cloneStyleFrom(actual);
                    if (i == borde.getUpperRow()) {
                        nuevo.setBorderTop(borderType);        
                        nuevo.setTopBorderColor(IndexedColors.BLACK.getIndex());
                    }
                    if (i == borde.getLowerRow()) {
                        nuevo.setBorderBottom(borderType);        
                        nuevo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                    }
                    if (j == borde.getLeftColumn()) {
                        nuevo.setBorderLeft(borderType);        
                        nuevo.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                    }
                    if (j == borde.getRightColumn()) {
                        nuevo.setBorderRight(borderType);        
                        nuevo.setRightBorderColor(IndexedColors.BLACK.getIndex());
                    }
                    cell.setCellStyle(nuevo);
                }
            }
        }
    }
}

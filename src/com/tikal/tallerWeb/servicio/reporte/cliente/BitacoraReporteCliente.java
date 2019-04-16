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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tikal.tallerWeb.modelo.reporte.cliente.EventoRC;
import com.tikal.tallerWeb.modelo.reporte.cliente.ReporteCliente;

/**
 *
 */
@Component
public class BitacoraReporteCliente extends AbstractSeccionXLS implements GeneradorSeccionXLS {

    private String[] atributos = new String[] {
        "nombreEvento",
        "detalle",
        "fecha",
        "etiqueta"
    };
    
    @Override
    public BordeSeccion generar(BordeSeccion borde, ContextoSeccion contexto, ReporteCliente datos) {
        Sheet sheet = contexto.getSheet();
        XSSFWorkbook wb = contexto.getWb();
        int initialRow = borde.getUpperRow();
        int initialColumn = borde.getLeftColumn();
        BordeSeccion r = new BordeSeccion();
        r.setLeftColumn(initialColumn);
        r.setUpperRow(initialRow);
        
        Row row = getRow(sheet, initialRow);
        Cell cell = row.createCell(initialColumn);
        cell.setCellValue("Bitacora");
        XSSFCellStyle cellStyle = wb.createCellStyle();
        addHeaderStyle(cellStyle, wb);
        addBorders(wb, cellStyle, CellStyle.BORDER_MEDIUM);
        cell.setCellStyle(cellStyle);
        
        for (int i = 1; i < 4; i++) {
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
                initialColumn + 3  //last column  (0-based)
        ));
        
        CreationHelper createHelper = wb.getCreationHelper();
        for (EventoRC x: datos.getBitacora()) {
            initialRow = initialRow + 1;
            row = getRow(sheet, initialRow);
            for (int i = 0; i < atributos.length; i++) {
                cell = row.createCell(initialColumn + i);
                cellStyle = wb.createCellStyle();
                try {
                    if (atributos[i].equals("fecha")) {
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yy/m/d h:mm:ss"));
                        cell.setCellValue((Date)PropertyUtils.getProperty(x, atributos[i]));
                    } else {
                        cell.setCellValue(PropertyUtils.getProperty(x, atributos[i]).toString());
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    cell.setCellValue("");
                }
                addBorders(wb, cellStyle, CellStyle.BORDER_THIN);
                cell.setCellStyle(cellStyle);
            }
        }
        r.setLowerRow(initialRow);
        r.setRightColumn(initialColumn + 3);
        paintBorder(wb, sheet, CellStyle.BORDER_MEDIUM, r);
        return r;
    }

}

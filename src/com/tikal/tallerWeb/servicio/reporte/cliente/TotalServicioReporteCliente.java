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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tikal.tallerWeb.modelo.reporte.cliente.ReporteCliente;

/**
 *
 */
@Component
public class TotalServicioReporteCliente extends AbstractSeccionXLS implements GeneradorSeccionXLS {

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
        row.createCell(initialColumn).setCellValue("Total del servicio:");
        //calculo del total
        Cell totalMecanica = (Cell) contexto.get("totalMecanica");
        Cell totalHojalateria = (Cell) contexto.get("totalHojalateria");
        Cell cell = row.createCell(initialColumn + 1);
        if (totalMecanica != null && totalHojalateria != null) {
            String formula = getSimpleReference(totalMecanica)+"+"+getSimpleReference(totalHojalateria);
            cell.setCellFormula(formula);
        } else {
            if (totalMecanica != null) {
                String formula = getSimpleReference(totalMecanica);
                cell.setCellFormula(formula);
            }
            if (totalHojalateria != null) {
                String formula = getSimpleReference(totalHojalateria);
                cell.setCellFormula(formula);
            }
        }
        if (totalMecanica == null && totalHojalateria == null ) {
            cell.setCellValue(0d);
        }
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFDataFormat df = wb.createDataFormat();
        cellStyle.setDataFormat(df.getFormat("$#,##0.00"));
        cell.setCellStyle(cellStyle);
        
        r.setLowerRow(initialRow);
        r.setRightColumn(initialColumn + 1);
        return r;
    }

}

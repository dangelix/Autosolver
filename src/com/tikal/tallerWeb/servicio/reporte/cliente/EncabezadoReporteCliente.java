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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.tikal.tallerWeb.modelo.reporte.cliente.ReporteCliente;

/**
 *
 */
@Component
public class EncabezadoReporteCliente extends AbstractSeccionXLS implements GeneradorSeccionXLS {

    @Override
    public BordeSeccion generar(BordeSeccion borde, ContextoSeccion contexto, ReporteCliente datos) {
        Sheet sheet = contexto.getSheet();
        XSSFWorkbook wb = contexto.getWb();
        int initialRow = borde.getUpperRow();
        int initialColumn = borde.getLeftColumn();
        BordeSeccion r = new BordeSeccion();
        r.setLeftColumn(initialColumn);
        r.setUpperRow(initialRow);
        
        //escribir el primer renglon
        Row row = getRow(sheet, initialRow);
        row.createCell(initialColumn).setCellValue("No de servicio:");
        row.createCell(initialColumn + 1).setCellValue(datos.getNumeroDeServicio());
        row.createCell(initialColumn + 2).setCellValue("Tiempo de reparación:");
        row.createCell(initialColumn + 3).setCellValue(datos.getTiempoReparacion());
        //segundo renglon encabezado
        initialRow = initialRow + 1;
        row = getRow(sheet, initialRow);
        row.createCell(initialColumn).setCellValue("Nombre del cliente:");
        row.createCell(initialColumn + 1).setCellValue(datos.getNombreDelCliente());        
        //tercer renglon encabezado
        initialRow = initialRow + 1;
        row = getRow(sheet, initialRow);
        row.createCell(initialColumn).setCellValue("Descripción del servicio:");
        row.createCell(initialColumn + 1).setCellValue(datos.getDescripcionServicio());
        
        r.setLowerRow(initialRow);
        r.setRightColumn(initialColumn + 3);
        return r;
    }

}

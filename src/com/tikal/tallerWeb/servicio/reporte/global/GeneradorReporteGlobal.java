/**
 *   Copyright 2013-2015 TIKAL-TECHNOLOGY
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

package com.tikal.tallerWeb.servicio.reporte.global;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.modelo.reporte.global.ParametrosReporteGlobal;
import com.tikal.tallerWeb.modelo.reporte.global.RenglonRG;
import com.tikal.tallerWeb.servicio.reporte.GeneradorReporte;

/**
 * @author Nekorp
 */
@Service("GeneradorReporteGlobal")
public class GeneradorReporteGlobal implements GeneradorReporte<ParametrosReporteGlobal> {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(GeneradorReporteGlobal.class);
    @Autowired
    private RenglonFactoryRG renglonFactory;
    @Autowired
    private ServicioDAO servicioDAO;
    
    private String[] encabezado = new String[] {
        "Folio",
        "Servicio Programado",
        "Nombre del Cliente",
        "Direccion",
        "Colonia",
        "Ciudad",
        "Contacto",
        "Telefono",
        "Marca",
        "Tipo",
        "Version",
        "Serie",
        "Modelo",
        "Color",
        "Placas",
        "Kilometraje",
        "Falla que presenta el auto",
        "Diagnostico",
        "Trabajo realizado",
        "Fecha de Ingreso Auto",
        "Fecha de entrega auto",
        "Costo de Mano de Obra",
        "Costo de Refacciones",
        "Sub total del costo",
        "Iva",
        "Costo total",
        "Mano de Obra facturado",
        "Costo de refacciones facturado",
        "Sub total Facturado",
        "Iva facturado",
        "Total facturado",
        "Diferencia costos",
        "Diferencia Total",
        "Recomendaciones",
        "No de factura",
        "Fecha elaboracion factura",
        "Fecha de presentacion a cobro",
        "Fecha de pago"
    };
    
    private CellValueMap[] valueMap = new CellValueMap[] {
        new CellValueMap(CellValueType.TEXT, "datosServicio.folio"),
        new CellValueMap(CellValueType.TEXT, "datosServicio.programado"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.nombre"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.direccion"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.colonia"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.ciudad"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.contacto"),
        new CellValueMap(CellValueType.TEXT, "datosCliente.telefono"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.marca"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.tipo"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.version"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.serie"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.modelo"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.color"),
        new CellValueMap(CellValueType.TEXT, "datosAuto.placas"),
        new CellValueMap(CellValueType.TEXT, "datosServicio.kilometraje"),
        new CellValueMap(CellValueType.TEXT, "datosServicio.falla"),
        new CellValueMap(CellValueType.TEXT, "datosBitacora.diagnostico"),
        new CellValueMap(CellValueType.TEXT, "datosCosto.manoDeObra"),
        new CellValueMap(CellValueType.TEXT, "datosServicio.fechaInicio"),
        new CellValueMap(CellValueType.DATE, "datosBitacora.fechaEntregaAuto"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.costoManoDeObra"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.costoRefacciones"),
        new CellValueMap(CellValueType.FORMULA, "V<row>+W<row>"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.ivaCosto"),
        new CellValueMap(CellValueType.FORMULA, "X<row>+Y<row>"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.manoDeObraFacturado"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.refaccionesFacturado"),
        new CellValueMap(CellValueType.FORMULA, "AA<row>+AB<row>"),
        new CellValueMap(CellValueType.AMOUNT, "datosCosto.ivaFacturado"),
        new CellValueMap(CellValueType.FORMULA, "AC<row>+AD<row>"),
        new CellValueMap(CellValueType.FORMULA, "AC<row>-X<row>"),
        new CellValueMap(CellValueType.FORMULA, "AE<row>-Z<row>"),
        new CellValueMap(CellValueType.TEXT, "datosBitacora.recomendaciones"),
    };
    @Override
    public void generaReporte(ParametrosReporteGlobal param, ServletOutputStream fileOut) {
//        FileOutputStream fileOut = null;
        try {
        	System.out.println("-----inicio:"+param.getFechaInicial());
        	System.out.println("-----fin:"+param.getFechaFinal());
            //List<Servicio> datos = servicioDAO.getByDate(new DateTime("2013-05-1T00:00:00.000-00:00"), new DateTime("2013-05-1T23:59:59.999-00:00"));
            List<ServicioEntity> datos = servicioDAO.getByDate(param.getFechaInicial(), param.getFechaFinal());
            
            GeneradorReporteGlobal.LOGGER.debug("file:" + param.getDestination());
            GeneradorReporteGlobal.LOGGER.debug("fecha Inicial:" + param.getFechaInicial());
            GeneradorReporteGlobal.LOGGER.debug("fecha Final:" + param.getFechaFinal());
            XSSFWorkbook wb = new XSSFWorkbook();
            //estilo para las fechas
            XSSFCellStyle dateCellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
            //estilos para las cantidades
            XSSFCellStyle moneyCellStyle = wb.createCellStyle();
            XSSFDataFormat mf = wb.createDataFormat();
            moneyCellStyle.setDataFormat(mf.getFormat("$#,##0.00"));
            Sheet sheet = wb.createSheet("Hoja1");
            int rowCount = 1;
            Cell actual;
            Row r;
            for (ServicioEntity x: datos) {
            	x.setId(x.getIdServicio());
            	  System.out.println("folio repglob:"+x.getIdServicio());
            	  System.out.println("cliente repglob:"+x.getIdCliente());
            	  System.out.println(" repglob:"+x.getFechaInicio());
                RenglonRG datRen = renglonFactory.build(x);
                System.out.println("folio datareng:"+datRen.getDatosServicio().getFolio());
                System.out.println("placas repglob:"+datRen.getDatosAuto().getPlacas());
                System.out.println("cliente repglob:"+datRen.getDatosCliente().getNombre());
                System.out.println("fechaaaaaa repglob:"+datRen.getDatosServicio().getFechaInicio());
                r = sheet.createRow(rowCount);
                rowCount = rowCount + 1;
                for (int i = 0; i < valueMap.length; i++) {
                    actual = r.createCell(i);
                    if (valueMap[i].getType() == CellValueType.TEXT) {
                        Object obj = PropertyUtils.getProperty(datRen, valueMap[i].getValue());
                        actual.setCellValue("");
                        if(obj!=null){
                        actual.setCellValue(obj.toString());
                        }
                    }
                    if (valueMap[i].getType() == CellValueType.DATE) {
                        Object obj = PropertyUtils.getProperty(datRen, valueMap[i].getValue());
                        if (obj != null) {
                            Date val = (Date) obj;
                            actual.setCellValue(val);
                            actual.setCellStyle(dateCellStyle);
                        }
                    }
                    if (valueMap[i].getType() == CellValueType.AMOUNT) {
                        Object obj = PropertyUtils.getProperty(datRen, valueMap[i].getValue());
                        double val = (double) obj;
                        actual.setCellValue(val);
                        actual.setCellStyle(moneyCellStyle);
                    }
                    if (valueMap[i].getType() == CellValueType.FORMULA) {
                        String formulaRaw = valueMap[i].getValue();
                        ST formula = new ST(formulaRaw);
                        formula.add("row", rowCount + "");
                        String render = formula.render();
                        actual.setCellFormula(formula.render());
                        actual.setCellStyle(moneyCellStyle);
                    }
                }
            }
            llenarEncabezado(sheet, 0, 0);
//            fileOut = new FileOutputStream(param.getDestination());
            wb.write(fileOut);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | FormulaParseException | IOException ex) {
            GeneradorReporteGlobal.LOGGER.error("error al generar reporte", ex);
        } finally {
            try {
                fileOut.close();
            } catch (IOException | NullPointerException ex) {
                GeneradorReporteGlobal.LOGGER.error("error al cerrar archivo de reporte", ex);
            }
        }
    }

    private void llenarEncabezado(Sheet sheet, int row, int col) {
        int cellCount = col;
        Cell actual;
        Row r = sheet.createRow(row);
        for (String x: encabezado) {
            actual = r.createCell(cellCount);
            actual.setCellValue(x);
//            sheet.autoSizeColumn(cellCount);
            cellCount = cellCount + 1;
        }
    }
}

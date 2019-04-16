package com.tikal.tallerWeb.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.ibm.icu.text.SimpleDateFormat;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
//import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
//import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
//import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

public class GeneradorPresupuestoPDF extends AbstractPdfView {

	public class HeaderTable extends PdfPageEventHelper {
		protected PdfPTable table;
		protected float tableHeight;

		public HeaderTable() throws MalformedURLException, IOException, DocumentException {
			table = new PdfPTable(2);
			table.setTotalWidth(523);
			table.setLockedWidth(true);

			// Image img = Image.getInstance(new
			// URL("http://127.0.0.1:8888/_ah/img/qAMszxQYHcjo5eu9-UC3Ww"));
			Image img = Image.getInstance("WEB-INF/Images/ACELogo.PNG");

			PdfPCell uno = new PdfPCell(img, true);
			uno.setHorizontalAlignment(Element.ALIGN_CENTER);

			Phrase holis = new Phrase();
			holis.add(new Chunk("Orden de Servicio", FontFactory.getFont(FontFactory.HELVETICA, 16)));
			holis.add(new Chunk(
//					" \nAutosolver Especializado México S de RL de CV \nAV. Pino Suarez No 2012. \nCol. La Magdalena \nToluca, Estado de México, CP. 50190 \nTel. 722 232 55 56",
					" \nISTAANBY SA DE CV \nCalle Independencia #641. \nCol. Sta. Ana Tlapaltitlan.\nToluca, Estado de México, \n CP. 50160 Tel. 4029370\nCel. 722 232 55 56",
					FontFactory.getFont(FontFactory.HELVETICA, 10)));

			PdfPCell dos = new PdfPCell(holis);

			uno.setBorder(Rectangle.NO_BORDER);
			dos.setBorder(Rectangle.NO_BORDER);

			PdfPCell celulin = new PdfPCell(new Paragraph("\n\n\n\n"));
			celulin.setColspan(2);
			celulin.setBorder(Rectangle.NO_BORDER);

			table.addCell(celulin);
			table.addCell(uno);
			table.addCell(dos);
			tableHeight = table.getTotalHeight();
		}

		public float getTableHeight() {
			return tableHeight;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			table.writeSelectedRows(0, -1, document.left(), document.top() + ((document.topMargin() + tableHeight) / 2),
					writer.getDirectContent());
		}
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> mapa, Document documento, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	

		/*DatosPresupuestoVO datos = (DatosPresupuestoVO) mapa.get("aquinoseporquevaesto");
		HeaderTable event = new HeaderTable();

		documento.setPageSize(PageSize.A4);
		documento.setMargins(36, 36, 20 + event.getTableHeight(), 50);
		writer.setPageEvent(event);

		Paragraph datosClienteLabel = new Paragraph(
				new Chunk("Datos del cliente", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph datosV = new Paragraph(
				new Chunk("Datos del vehiculo", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph inventarioLabel = new Paragraph(
				new Chunk("Inventario de daños", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph especificacionesLabel = new Paragraph(
				new Chunk("Especificaciones", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph leyendaLabel = new Paragraph(
				new Chunk("*Los costos no incluyen IVA.", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		PdfPTable datosCliente = new PdfPTable(4);
		datosCliente.getDefaultCell().setBorder(1);
		datosCliente.setWidthPercentage(100);

		//PdfPCell noServicio = new PdfPCell(new Paragraph("No. de Servicio: " + datos.getListaServicios().get(0).getPresupuestos().get(0).getId()));
		PdfPCell noServicio = new PdfPCell(new Paragraph("No. de Servicio: "));

		noServicio.setBorder(Rectangle.NO_BORDER);
		noServicio.setColspan(2);
		
		Date date = new Date();

		//PdfPCell fecha = new PdfPCell(new Paragraph("Fecha: " + sdf.format(date)));
		PdfPCell fecha = new PdfPCell(new Paragraph("Fecha: " + date));
		fecha.setBorder(Rectangle.NO_BORDER);
		fecha.setColspan(2);

		PdfPCell nombreLabel = new PdfPCell(new Paragraph("Nombre: "));
		PdfPCell nombreField = new PdfPCell(new Paragraph(datos.getNombre()));
		nombreField.setColspan(3);
		nombreField.setBorderWidthBottom(0);

		PdfPCell dirLabel = new PdfPCell(new Paragraph("Dirección: "));
		PdfPCell dirField = new PdfPCell(new Paragraph(datos.getDireccion()));
		dirField.setColspan(3);
		dirField.setBorderWidthBottom(0);
		dirField.setBorderWidthLeft(0);
		dirField.setBorderWidthTop(0);

		PdfPCell telLabel = new PdfPCell(new Paragraph("Teléfono: "));
		PdfPCell telField = new PdfPCell(new Paragraph(datos.getTelefono()));
		telField.setColspan(3);
		telField.setBorderWidthBottom(0);
		telField.setBorderWidthLeft(0);
		telField.setBorderWidthTop(0);

		PdfPCell emailLabel = new PdfPCell(new Paragraph("E-mail: "));
		PdfPCell emailField = new PdfPCell(new Paragraph(datos.getEmail()));
		emailField.setColspan(3);
		emailField.setBorderWidthBottom(0);
		emailField.setBorderWidthLeft(0);
		emailField.setBorderWidthTop(0);

		PdfPCell asesorLabel = new PdfPCell(new Paragraph("Asesor: "));
		PdfPCell asesorField = new PdfPCell(new Paragraph(datos.getAsesor()));
		asesorField.setColspan(3);
		asesorField.setBorderWidthLeft(0);
		asesorField.setBorderWidthTop(0);

		datosCliente.addCell(noServicio);
		datosCliente.addCell(fecha);
		datosCliente.addCell(nombreLabel);
		datosCliente.addCell(nombreField);
		datosCliente.addCell(dirLabel);
		datosCliente.addCell(dirField);
		datosCliente.addCell(telLabel);
		datosCliente.addCell(telField);
		datosCliente.addCell(emailLabel);
		datosCliente.addCell(emailField);
		datosCliente.addCell(asesorLabel);
		datosCliente.addCell(asesorField);

		PdfPTable datosVe = new PdfPTable(7);
		datosVe.setWidthPercentage(100);
		datosVe.addCell(new PdfPCell(new Paragraph("Marca: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Tipo: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Modelo: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Color:")));
		datosVe.addCell(new PdfPCell(new Paragraph("Placas: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("KM: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Serie: ")));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getMarca())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getTipo())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getModelo())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getColor())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getPlacas())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getKilometros())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getSerie())));
		PdfPCell servicioCell = new PdfPCell(new Paragraph("Servicio: " + datos.getServicio()));
		servicioCell.setColspan(7);
		datosVe.addCell(servicioCell);

		// Hacer la consulta para sacar las imagenes y meterlas a la tabla

		PdfPTable imagenesBox = new PdfPTable(2);
		imagenesBox.setWidthPercentage(100);
		for (String path : datos.getListaImages()) {
			String ruta = "http://127.0.0.1:8888/_ah/img/";
			Image img = Image.getInstance(new URL(ruta.concat(path)));
			img.scalePercent(50);
			PdfPCell cell = new PdfPCell(img, true);
			imagenesBox.addCell(cell);
		}

		PdfPTable especificacionesTable = new PdfPTable(4);
		especificacionesTable.setWidthPercentage(100);

		PdfPCell combustibleLabel = new PdfPCell(new Paragraph("\n Nivel de Combustible:"));
		PdfPCell combustibleField = new PdfPCell(new Paragraph(datos.getNivelCombustible()));
		combustibleField.setColspan(3);

		PdfPCell observacionesLabel = new PdfPCell(new Paragraph("\n Observaciones:"));
		PdfPCell observacionesField = new PdfPCell(new Paragraph(datos.getObservaciones()));
		observacionesField.setColspan(3);

		especificacionesTable.addCell(combustibleLabel);
		especificacionesTable.addCell(combustibleField);
		especificacionesTable.addCell(observacionesLabel);
		especificacionesTable.addCell(observacionesField);

		PdfPTable repTable = new PdfPTable(5);
		repTable.setWidthPercentage(100);
		PdfPCell cantLabel = new PdfPCell(new Paragraph("Cantidad"));
		PdfPCell desLabel = new PdfPCell(new Paragraph("Descripción"));
		desLabel.setColspan(3);
		PdfPCell costLabel = new PdfPCell(new Paragraph("Costo"));
		cantLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		desLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		costLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		repTable.addCell(cantLabel);
		repTable.addCell(desLabel);
		repTable.addCell(costLabel);
		PdfPCell vacio = new PdfPCell();
		vacio.setBorderWidthBottom(0);
		vacio.setBorderWidthRight(0);
		vacio.setBorderWidthTop(0);

		Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		if (datos.isConCosto() == true) {
			float total = 0;
			for (GruposCosto grupo : datos.getListaServicios()) {
				repTable.addCell(vacio);
				PdfPCell nombreGrupo = new PdfPCell(new Paragraph(grupo.getNombre(), boldFont));
				nombreGrupo.setBorderWidthBottom(0);
				nombreGrupo.setBorderWidthTop(0);
				nombreGrupo.setColspan(3);
				repTable.addCell(nombreGrupo);
				float subtotal = 0;
				for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
					subtotal += presupuesto.getCantidad() * Float.parseFloat(presupuesto.getPrecioCliente().getValue());
				}
				total += subtotal;
				PdfPCell subsubTotalCell = new PdfPCell(new Paragraph(formatter.format(subtotal), boldFont));
				subsubTotalCell.setBorderWidthBottom(0);
				subsubTotalCell.setBorderWidthLeft(0);
				subsubTotalCell.setBorderWidthTop(0);
				subsubTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				repTable.addCell(subsubTotalCell);

				for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
					PdfPCell cantCell = new PdfPCell(new Paragraph(presupuesto.getCantidad() + ""));
					cantCell.setBorderWidthTop(0);
					cantCell.setBorderWidthBottom(0);
					cantCell.setBorderWidthRight(0);
					cantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					repTable.addCell(cantCell);
					PdfPCell conceptoCell = new PdfPCell(new Paragraph(presupuesto.getConcepto()));
					conceptoCell.setBorderWidthTop(0);
					conceptoCell.setBorderWidthBottom(0);
					conceptoCell.setColspan(3);
					repTable.addCell(conceptoCell);
					PdfPCell precioClienteCell = new PdfPCell(new Paragraph(
							formatter.format(Float.parseFloat(presupuesto.getPrecioCliente().getValue()))));
					precioClienteCell.setBorderWidthTop(0);
					precioClienteCell.setBorderWidthBottom(0);
					precioClienteCell.setBorderWidthLeft(0);
					precioClienteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					repTable.addCell(precioClienteCell);

				}
				PdfPCell celdaTontaUno = new PdfPCell(new Paragraph("\n"));
				celdaTontaUno.setBorderWidthTop(0);
				celdaTontaUno.setBorderWidthBottom(0);
				celdaTontaUno.setBorderWidthRight(0);
				repTable.addCell(celdaTontaUno);
				PdfPCell celdaTontaDos = new PdfPCell(new Paragraph(" "));
				celdaTontaDos.setBorderWidthTop(0);
				celdaTontaDos.setBorderWidthBottom(0);
				celdaTontaDos.setColspan(3);
				repTable.addCell(celdaTontaDos);
				PdfPCell celdaTontaTres = new PdfPCell(new Paragraph(" "));
				celdaTontaTres.setBorderWidthTop(0);
				celdaTontaTres.setBorderWidthBottom(0);
				celdaTontaTres.setBorderWidthLeft(0);
				repTable.addCell(celdaTontaTres);

			}

			PdfPCell totalCell = new PdfPCell(new Paragraph("Total: " + formatter.format(total), boldFont));
			totalCell.setColspan(5);
			totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			repTable.addCell(totalCell);
			
			
		} else {
			float total = 0;
			for (GruposCosto grupo : datos.getListaServicios()) {
				repTable.addCell(vacio);
				PdfPCell nombreGrupo = new PdfPCell(new Paragraph(grupo.getNombre(), boldFont));
				nombreGrupo.setBorderWidthBottom(0);
				nombreGrupo.setBorderWidthTop(0);
				nombreGrupo.setColspan(3);
				repTable.addCell(nombreGrupo);
				float subtotal = 0;
				for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
					subtotal += presupuesto.getCantidad() * Float.parseFloat(presupuesto.getPrecioCliente().getValue());
				}
				total += subtotal;
				PdfPCell subsubTotalCell = new PdfPCell(new Paragraph(""));
				subsubTotalCell.setBorderWidthBottom(0);
				subsubTotalCell.setBorderWidthLeft(0);
				subsubTotalCell.setBorderWidthTop(0);
				subsubTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				repTable.addCell(subsubTotalCell);

				for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
					PdfPCell cantCell = new PdfPCell(new Paragraph(presupuesto.getCantidad() + ""));
					cantCell.setBorderWidthTop(0);
					cantCell.setBorderWidthBottom(0);
					cantCell.setBorderWidthRight(0);
					cantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					repTable.addCell(cantCell);
					PdfPCell conceptoCell = new PdfPCell(new Paragraph(presupuesto.getConcepto()));
					conceptoCell.setBorderWidthTop(0);
					conceptoCell.setBorderWidthBottom(0);
					conceptoCell.setColspan(3);
					repTable.addCell(conceptoCell);
					PdfPCell precioClienteCell = new PdfPCell(new Paragraph(""));
					precioClienteCell.setBorderWidthTop(0);
					precioClienteCell.setBorderWidthBottom(0);
					precioClienteCell.setBorderWidthLeft(0);
					precioClienteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					repTable.addCell(precioClienteCell);

				}
				PdfPCell celdaTontaUno = new PdfPCell(new Paragraph("\n"));
				celdaTontaUno.setBorderWidthTop(0);
				celdaTontaUno.setBorderWidthBottom(0);
				celdaTontaUno.setBorderWidthRight(0);
				repTable.addCell(celdaTontaUno);
				PdfPCell celdaTontaDos = new PdfPCell(new Paragraph(" "));
				celdaTontaDos.setBorderWidthTop(0);
				celdaTontaDos.setBorderWidthBottom(0);
				celdaTontaDos.setColspan(3);
				repTable.addCell(celdaTontaDos);
				PdfPCell celdaTontaTres = new PdfPCell(new Paragraph(" "));
				celdaTontaTres.setBorderWidthTop(0);
				celdaTontaTres.setBorderWidthBottom(0);
				celdaTontaTres.setBorderWidthLeft(0);
				repTable.addCell(celdaTontaTres);

			}
			PdfPCell celdin = new PdfPCell();
			celdin.setBorderWidthTop(0);
			celdin.setColspan(5);
			repTable.addCell(celdin);
		}
		
		
		documento.add(new Paragraph("\n\n"));
		documento.add(datosClienteLabel);
		documento.add(datosCliente);
		documento.add(datosV);
		documento.add(new Paragraph("\n"));
		documento.add(datosVe);
		documento.add(inventarioLabel);
		documento.add(new Paragraph("\n"));
		documento.add(imagenesBox);
		documento.add(especificacionesLabel);
		documento.add(new Paragraph("\n"));
		documento.add(especificacionesTable);
		documento.add(new Paragraph("\n"));
		documento.add(repTable);
		if(datos.isConCosto()==false){
		}else{
			documento.add(leyendaLabel);
		}*/
	}

}

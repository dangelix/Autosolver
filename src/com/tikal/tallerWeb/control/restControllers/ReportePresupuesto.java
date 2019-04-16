//package com.tikal.tallerWeb.control.restControllers;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
//import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
//import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
//import com.tikal.tallerWeb.data.access.AutoDAO;
//import com.tikal.tallerWeb.data.access.BitacoraDAO;
//import com.tikal.tallerWeb.data.access.ClienteDAO;
//import com.tikal.tallerWeb.data.access.CostoDAO;
//import com.tikal.tallerWeb.data.access.ServicioDAO;
//import com.tikal.tallerWeb.modelo.entity.AutoEntity;
//import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
//import com.tikal.tallerWeb.modelo.entity.EventoEntity;
//import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
//import com.tikal.tallerWeb.rest.util.NewServiceObject;
//import com.tikal.tallerWeb.util.AsignadorDeCharset;
//
//import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;
//
//@Controller
//@RequestMapping(value= { "/reporte" })
//public class ReportePresupuesto {
//	
//	@Autowired
//	ServicioDAO servdao;		
//	
//	@Autowired
//	AutoDAO autodao;
//	
//	@Autowired
//	ClienteDAO clientedao;
//	
//	@Autowired
//	CostoDAO costodao;
//	
//	@Autowired
//	BitacoraDAO bitacorin;
//	
//	@RequestMapping(value={"/presupuestoPDF/{id}"}, method = RequestMethod.GET,produces="application/pdf")
//	public ModelAndView generaReporte(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws UnsupportedEncodingException{
//		AsignadorDeCharset.asignar(request, response);
//
//		NewServiceObject servicio = new NewServiceObject();
//		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
//		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
//		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
//		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
//		DatosServicioVO datosin = new DatosServicioVO();
//		datosin.setServicio(servicio);
//		datosin.setPresupuesto(grupos);
//		
//		NewServiceObject interfacin = datosin.getServicio();
//		List<GruposCosto> presupuestin = datosin.getPresupuesto();
//		AutoEntity auto = interfacin.getAuto();
//		ClienteEntity cliente = interfacin.getCliente();
//		ServicioEntity servicin = interfacin.getServicio();
//		
//		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
//				
//		DatosPresupuestoVO datos = new DatosPresupuestoVO();
//		datos.setConCosto(true);
//		datos.setNombre(cliente.getNombre());
//		datos.setDireccion(domicilio);
//		datos.setEmail(cliente.getEmail());
//		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
//		datos.setAsesor("S/D");
//		datos.setMarca(auto.getMarca());
//		datos.setTipo(auto.getTipo());
//		datos.setModelo(auto.getModelo());
//		datos.setColor(auto.getColor());
//		datos.setPlacas(auto.getPlacas());
//		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
//		datos.setSerie(auto.getNumeroSerie());
//		datos.setServicio(servicin.getDescripcion());
//		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
//		datos.setObservaciones("Sin Obervaciones");
//		datos.setListaServicios(presupuestin);
//	
//		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
//		List<String> pathImagenes= new ArrayList<String>();
//		for(EventoEntity evento:eventin){
//			for(Evidencia ev:evento.getEvidencia()){
//				if(ev.isAppended(true)){
//					pathImagenes.add(ev.getImage());
//				}
//			}
//		}
//		datos.setListaImages(pathImagenes);
//		return new ModelAndView("PDFViewer","aquinoseporquevaesto", datos);
//	}
//	
//	@RequestMapping(value={"/presupuestoPDFSin/{id}"}, method = RequestMethod.GET,produces="application/pdf")
//	public ModelAndView generaReporteSinCosto(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws UnsupportedEncodingException{
//
//		AsignadorDeCharset.asignar(request, response);
//		NewServiceObject servicio = new NewServiceObject();
//		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
//		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
//		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
//		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
//		DatosServicioVO datosin = new DatosServicioVO();
//		datosin.setServicio(servicio);
//		datosin.setPresupuesto(grupos);
//		
//		NewServiceObject interfacin = datosin.getServicio();
//		List<GruposCosto> presupuestin = datosin.getPresupuesto();
//		AutoEntity auto = interfacin.getAuto();
//		ClienteEntity cliente = interfacin.getCliente();
//		ServicioEntity servicin = interfacin.getServicio();
//		
//		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
//				
//		DatosPresupuestoVO datos = new DatosPresupuestoVO();
//		datos.setConCosto(false);
//		datos.setNombre(cliente.getNombre());
//		datos.setDireccion(domicilio);
//		datos.setEmail(cliente.getEmail());
//		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
//		datos.setAsesor("S/D");
//		datos.setMarca(auto.getMarca());
//		datos.setTipo(auto.getTipo());
//		datos.setModelo(auto.getModelo());
//		datos.setColor(auto.getColor());
//		datos.setPlacas(auto.getPlacas());
//		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
//		datos.setSerie(auto.getNumeroSerie());
//		datos.setServicio(servicin.getDescripcion());
//		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
//		datos.setObservaciones("Sin Obervaciones");
//		datos.setListaServicios(presupuestin);
//	
//		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
//		List<String> pathImagenes= new ArrayList<String>();
//		for(EventoEntity evento:eventin){
//			for(Evidencia ev:evento.getEvidencia()){
//				if(ev.isAppended(true)){
//					pathImagenes.add(ev.getImage());
//				}
//			}
//		}
//		datos.setListaImages(pathImagenes);
//		return new ModelAndView("PDFViewer","aquinoseporquevaesto", datos);
//	}
//	
//	
//
//}

package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.CostoDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.PdfMaker;

import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;

@Controller
@RequestMapping(value= { "/reporte" })
public class ReportePresupuesto {
	
	@Autowired
	ServicioDAO servdao;		
	
	@Autowired
	AutoDAO autodao;
	
	@Autowired
	ClienteDAO clientedao;
	
	@Autowired
	CostoDAO costodao;
	
	@Autowired
	BitacoraDAO bitacorin;
	
	@RequestMapping(value={"/presupuestoPDF/{id}"}, method = RequestMethod.GET, produces="application/pdf")
	public void generaReporte(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws DocumentException, IOException{
		AsignadorDeCharset.asignar(request, response);
		
		response.setContentType("Application/Pdf");
//		String id = request.getParameter("id");

		PdfMaker nuevo = new PdfMaker();
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(grupos);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		
		String asesor=servicin.getAsesor();
		if(servicin.getAsesor()==null){
			asesor="S/D";
		}
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setConCosto(true);
		datos.setConFirmas(false);
		datos.setConImagenes(false);
		datos.setConDiagnostico(true);
		datos.setNumeroServicio(servicin.getIdServicio());
		datos.setEsFacturado(servicin.getCobranza().isFacturado());
		datos.setNombre(cliente.getNombre());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
		datos.setAsesor(asesor);
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("");
		datos.setListaServicios(presupuestin);
	
		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
		List<String> pathImagenes= new ArrayList<String>();
		nuevo.setDatos(datos);
		PdfWriter writer = PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPresupuesto();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value={"/presupuestoPDFSin/{id}"}, method = RequestMethod.GET,produces="application/pdf")
	public void generaReporteSinCosto(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws DocumentException, IOException{

		AsignadorDeCharset.asignar(request, response);
		
		response.setContentType("Application/Pdf");
//		String id = request.getParameter("id");
		PdfMaker nuevo = new PdfMaker();
		
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
		List<GruposCosto> gruposf=new ArrayList<GruposCosto>();
		for(GruposCosto gru:grupos){
			List<PresupuestoEntity> news= new ArrayList<PresupuestoEntity>();
			for(PresupuestoEntity pre:gru.getPresupuestos()){
				if(pre.isAutorizado()){
					news.add(pre);
				}
			}
			
			gru.setPresupuestos(news);
			if(news.size()>0){
				gruposf.add(gru);
			}
		}
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(gruposf);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		
		String asesor=servicin.getAsesor();
		if(servicin.getAsesor()==null){
			asesor="S/D";
		}
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setConImagenes(false);
		datos.setConFirmasSencillas(true);
		datos.setConDiagnostico(true);
		datos.setConCosto(true);
		datos.setNumeroServicio(servicin.getIdServicio());
		datos.setEsFacturado(servicio.servicio.getCobranza().isFacturado());
		datos.setNombre(cliente.getNombre());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
		datos.setAsesor(asesor);
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
	
		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
		List<String> pathImagenes= new ArrayList<String>();
		nuevo.setDatos(datos);
		PdfWriter writer = PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPresupuesto();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value={"/presupuestoPDFInterno/{id}"}, method = RequestMethod.GET,produces="application/pdf")
	public void generaReporteInterno(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws DocumentException, IOException{
		
		AsignadorDeCharset.asignar(request, response);
		
		response.setContentType("Application/Pdf");
//		String id = request.getParameter("id");
		PdfMaker nuevo = new PdfMaker();
		
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
//		for(GruposCosto gru:grupos){
//			List<PresupuestoEntity> news= new ArrayList<PresupuestoEntity>();
//			for(PresupuestoEntity pre:gru.getPresupuestos()){
//				if(pre.isAutorizado()){
//					news.add(pre);
//				}
//			}
//			gru.setPresupuestos(news);
//		}
//		for(GruposCosto gru:grupos){
		List<GruposCosto> gruposf=new ArrayList<GruposCosto>();
		for(GruposCosto gru:grupos){
			List<PresupuestoEntity> news= new ArrayList<PresupuestoEntity>();
			for(PresupuestoEntity pre:gru.getPresupuestos()){
				if(pre.isAutorizado()){
					news.add(pre);
				}
			}
			
			gru.setPresupuestos(news);
			if(news.size()>0){
				gruposf.add(gru);
			}
		}
	
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(gruposf);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		String asesor=servicin.getAsesor();
		if(servicin.getAsesor()==null){
			asesor="S/D";
		}
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setConCosto(false);
		datos.setConDiagnostico(true);
		datos.setConImagenes(false);
		datos.setConFirmas(false);
		datos.setNombre(cliente.getNombre());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setNumeroServicio(servicin.getIdServicio());
		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
		datos.setAsesor(asesor);
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
	
		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
		List<String> pathImagenes= new ArrayList<String>();
		nuevo.setDatos(datos);
		PdfWriter writer = PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPresupuesto();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value={"/presupuestoPDFAutorizadoFirmas/{id}"}, method = RequestMethod.GET,produces="application/pdf")
	public void generaReporteAutorizadoFirmas(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws DocumentException, IOException{
		
		AsignadorDeCharset.asignar(request, response);
		
		response.setContentType("Application/Pdf");
//		String id = request.getParameter("id");
		PdfMaker nuevo = new PdfMaker();
		
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
		for(GruposCosto gru:grupos){
			List<PresupuestoEntity> news= new ArrayList<PresupuestoEntity>();
			for(PresupuestoEntity pre:gru.getPresupuestos()){
				if(pre.isAutorizado()){
					news.add(pre);
				}
			}
			gru.setPresupuestos(news);
		}
		
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(grupos);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		
		String asesor=servicin.getAsesor();
		if(servicin.getAsesor()==null){
			asesor="S/D";
		}
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setConCosto(true);
		datos.setConFirmas(true);
		datos.setConImagenes(false);
		datos.setConDiagnostico(true);
		datos.setNombre(cliente.getNombre());
		datos.setNumeroServicio(servicin.getIdServicio());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
		datos.setAsesor(asesor);
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
	
		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
		List<String> pathImagenes= new ArrayList<String>();
		for(EventoEntity evento:eventin){
			for(Evidencia ev:evento.getEvidencia()){
				if(ev.isAppended(true)){
					pathImagenes.add(ev.getImage());
				}
			}
		}
		
		datos.setListaImages(pathImagenes);
		nuevo.setDatos(datos);
		PdfWriter writer = PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPresupuesto();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value={"/presupuestoPDFSoloDatos/{id}"}, method = RequestMethod.GET, produces="application/pdf")
	public void generaReporteSoloDatos(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws DocumentException, IOException{
		AsignadorDeCharset.asignar(request, response);
		
		response.setContentType("Application/Pdf");
//		String id = request.getParameter("id");

		PdfMaker nuevo = new PdfMaker();
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(grupos);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		
		String asesor=servicin.getAsesor();
		if(servicin.getAsesor()==null){
			asesor="S/D";
		}
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setConCosto(false);
		datos.setConFirmas(false);
		datos.setConImagenes(true);
		datos.setConDiagnostico(false);
		datos.setConFirmasSencillas(true);
		datos.setNombre(cliente.getNombre());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setNumeroServicio(servicin.getIdServicio());
		datos.setTelefono(cliente.getTelefonoContacto().get(0).getValor());
		datos.setAsesor(asesor);
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
	
		List<EventoEntity> eventin = bitacorin.cargar(Long.parseLong(id));
		List<String> pathImagenes= new ArrayList<String>();
		for(EventoEntity evento:eventin){
			if(evento.getTipo().compareToIgnoreCase("Entrada de Auto")==0){
				for(Evidencia ev:evento.getEvidencia()){
						pathImagenes.add(ev.getImage());
				}
			}
		}
		datos.setListaImages(pathImagenes);
		nuevo.setDatos(datos);
		PdfWriter writer = PdfWriter.getInstance(nuevo.getDocument(), response.getOutputStream());
		nuevo.getDocument().open();
		nuevo.imprimirPresupuesto();
		nuevo.getDocument().close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	
}




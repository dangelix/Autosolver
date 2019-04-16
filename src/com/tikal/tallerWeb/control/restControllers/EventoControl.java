package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.control.restControllers.VO.BitacoraVO;
import com.tikal.tallerWeb.control.restControllers.VO.EventoVO;
import com.tikal.tallerWeb.control.restControllers.VO.EvidenciaVO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.EstatusMap;
import com.tikal.tallerWeb.util.JsonConvertidor;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;
import technology.tikal.taller.automotriz.model.servicio.Servicio;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;
import technology.tikal.taller.automotriz.model.servicio.metadata.ServicioMetadata;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

@Controller
@RequestMapping(value = { "/eventos" })
public class EventoControl {

	@Autowired
	BitacoraDAO bitacora;
	
	@Autowired
	ServicioDAO servdao;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		System.out.println("si entra aqui........");
		EventoVO vo = (EventoVO) JsonConvertidor.fromJson(json, EventoVO.class);
		///////////////
		EventoEntity e = vo.getEvento();
//		System.out.println("evento id del servicio:"+e.getId());
//		ServicioEntity s = servdao.cargar(e.getId());
//		ServicioMetadata sm = new ServicioMetadata();
//		sm.setStatus("Finalizado");
		
		//s.setMetadata(sm);
		//s.getMetadata().setStatus("Terminado");
		//a = s.getAuto();
	//	ObjectifyService.ofy().save().entities(s).now();
		///////////////////////////////////////////////
		
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date nueva=df.parse(vo.getFecha());
			nueva.setHours(nueva.getHours()+5);
			e.setFecha(nueva);
			System.out.println("fecha mas 5 horas:"+nueva);
			e.setFechaCreacion(new Date());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String tipo = vo.getEvento().getTipo();
		System.out.println("tipo de evento:"+tipo);
		///////////////////////////////////////
		if(tipo.compareTo("Diagnóstico")==0){

			ServicioEntity s = servdao.cargar(e.getId());
			//ServicioMetadata sm = new ServicioMetadata();
		//	sm.setStatus("Finalizado");
			
		//	s.setMetadata(sm);
			s.getMetadata().setStatus("Diagnóstico");
			
			ObjectifyService.ofy().save().entities(s).now();
		}
	//	tipo=EstatusMap.getStatus(tipo);
		
//		if(tipo.compareTo("Cotización")==0){
//
//			ServicioEntity s = servdao.cargar(e.getId());
//			//ServicioMetadata sm = new ServicioMetadata();
//		//	sm.setStatus("Finalizado");
//			
//		//	s.setMetadata(sm);
//			s.getMetadata().setStatus("Cotización");
//			
//			ObjectifyService.ofy().save().entities(s).now();
//		}
		
	//	tipo=EstatusMap.getStatus(tipo);
		
		
//		if(tipo.compareTo("Presupuesto")==0){
//
//			ServicioEntity s = servdao.cargar(e.getId());
//			//ServicioMetadata sm = new ServicioMetadata();
//		//	sm.setStatus("Finalizado");
//			
//		//	s.setMetadata(sm);
//			s.getMetadata().setStatus("Presupuesto");
//			
//			ObjectifyService.ofy().save().entities(s).now();
//		}
//		
//	//	tipo=EstatusMap.getStatus(tipo);
//		
//		
//		if(tipo.compareTo("Reparación")==0){
//
//			ServicioEntity s = servdao.cargar(e.getId());
//			//ServicioMetadata sm = new ServicioMetadata();
//		//	sm.setStatus("Finalizado");
//			
//		//	s.setMetadata(sm);
//			s.getMetadata().setStatus("Reparación");
//			
//			ObjectifyService.ofy().save().entities(s).now();
//		}
//		
//	//	tipo=EstatusMap.getStatus(tipo);
//		
//		if(tipo.compareTo("Facturado")==0){
//
//			ServicioEntity s = servdao.cargar(e.getId());
//			//ServicioMetadata sm = new ServicioMetadata();
//		//	sm.setStatus("Finalizado");
//			
//		//	s.setMetadata(sm);
//			s.getMetadata().setStatus("Facturado");
//			
//			ObjectifyService.ofy().save().entities(s).now();
//		}
//		
//	//	tipo=EstatusMap.getStatus(tipo);
		
		if(tipo.compareTo("Pagado")==0){

			ServicioEntity s = servdao.cargar(e.getId());
			//ServicioMetadata sm = new ServicioMetadata();
		//	sm.setStatus("Finalizado");
			
		//	s.setMetadata(sm);
			List<PagoCobranza> pagos=s.getCobranza().getPagos();
		
//			Moneda totalPagos=;
//		//	Double totalPagos = new Double("0");
			for (PagoCobranza p:pagos){
//				//Double pago = new Double(p.getMonto());
//				totalPagos= p.getMonto();
				System.out.println("pago moneda"+p.getMonto());
			}
//			if (s.getMetadata().getCostoTotal()>s.getCobranza()){
			    s.getMetadata().setStatus("Pagado");
//			}
			
			ObjectifyService.ofy().save().entities(s).now();
		}
		
	//	tipo=EstatusMap.getStatus(tipo);
		
		if(tipo.compareTo("Salida de Auto")==0){

			ServicioEntity s = servdao.cargar(e.getId());
			//ServicioMetadata sm = new ServicioMetadata();
		//	sm.setStatus("Finalizado");
			
		//	s.setMetadata(sm);
			s.getMetadata().setStatus("Finalizado");
			
			ObjectifyService.ofy().save().entities(s).now();
		}
		
	//	tipo=EstatusMap.getStatus(tipo);
		System.out.println("tipo de evento mapeado:"+tipo);
		
		if(tipo.compareTo("Cancelación")==0){

			ServicioEntity s = servdao.cargar(e.getId());
			//ServicioMetadata sm = new ServicioMetadata();
		//	sm.setStatus("Finalizado");
			
		//	s.setMetadata(sm);
			s.getMetadata().setStatus("Cancelación");
			
			ObjectifyService.ofy().save().entities(s).now();
		}
		
	//	tipo=EstatusMap.getStatus(tipo);
		System.out.println("tipo de evento mapeado:"+tipo);
		//////////////////////////////////////
		if(tipo.compareTo("Termino de servicio")==0){

			ServicioEntity s = servdao.cargar(e.getId());
			//ServicioMetadata sm = new ServicioMetadata();
		//	sm.setStatus("Finalizado");
			
		//	s.setMetadata(sm);
			s.getMetadata().setStatus("Terminado");
			
			ObjectifyService.ofy().save().entities(s).now();
		}
		
		tipo=EstatusMap.getStatus(tipo);
		System.out.println("tipo de evento mapeado:"+tipo);
		
		
		if(tipo!=null){
			ServicioEntity ser= servdao.cargar(e.getId());
			
			if(tipo.compareTo("Diagnóstico")==0){
				ser.setAsesor(vo.getEvento().getResponsable());
			}
			System.out.println("tipo de evento:"+tipo);
			
				ser.getMetadata().setStatus(tipo);
				servdao.guardar(ser);
			
		}
		response.getWriter().println(JsonConvertidor.toJson(bitacora.agregar(e.getId(), e)));
	}
	
	@RequestMapping(value = { "/appendImages/{id}" }, method = RequestMethod.POST, produces = "application/json")
	public void addImages(HttpServletRequest request, HttpServletResponse response, @PathVariable String id,@RequestBody String json)
			throws IOException {
		EvidenciaVO evi= (EvidenciaVO) JsonConvertidor.fromJson(json, EvidenciaVO.class);
		AsignadorDeCharset.asignar(request, response);
		
		EventoEntity evento=bitacora.cargarEvento(evi.getIdEvento());
		List<Evidencia> lista= new ArrayList<Evidencia>();
		for(String image:evi.getImages()){
			Evidencia e= new Evidencia();
			e.setImage(image);
			lista.add(e);
		}
		evento.getEvidencia().addAll(lista);
		response.getWriter().println(JsonConvertidor.toJson(bitacora.guardar(evento)));
	}
	@RequestMapping(value = { "/getBitacora/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void get(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		response.getWriter().println(JsonConvertidor.toJson(bitacora.cargar(Long.parseLong(id))));
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		EventoEntity e = bitacora.cargarEvento(Long.parseLong(id));
		if (e != null) {
			bitacora.borrarEvento(e.getIdEvento());
		}
	}
	
	@RequestMapping(value = { "/update/" }, method = RequestMethod.POST, consumes="application/json")
	public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		BitacoraVO bit= (BitacoraVO) JsonConvertidor.fromJson(json, BitacoraVO.class);
		bitacora.guardar(Long.parseLong(bit.getId()), bit.getEventos());
		response.getWriter().println(JsonConvertidor.toJson(bit.getEventos()));
	}

}

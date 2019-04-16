package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.BitacoraVO;
import com.tikal.tallerWeb.control.restControllers.VO.BusquedaVO;
import com.tikal.tallerWeb.control.restControllers.VO.IdClienteVo;
import com.tikal.tallerWeb.control.restControllers.VO.ServicioListVO;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.JsonConvertidor;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;

@Controller
@RequestMapping(value = { "/search" })
public class SearchController {

	@Autowired
	ServicioDAO servdao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	ClienteDAO clientedao;

	@RequestMapping(value = "/general/{busca}", method = RequestMethod.GET)
	public void buscarGeneral(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		List<ClienteEntity> clientes = clientedao.buscarClientes(busca);
		List<AutoEntity> autos = autodao.buscar(busca);
		BusquedaVO vo = new BusquedaVO();
		for (ClienteEntity cli : clientes) {
			vo.getNombres().add(cli.getNombre());
			vo.getTipos().add("cliente");
			vo.getIds().add(cli.getIdCliente());
		}
		List<AutoEntity> aut = autodao.buscar(busca);
		for (AutoEntity a : aut) {
			vo.getNombres().add(a.getNumeroSerie());
			vo.getTipos().add("serie");
			vo.getIds().add(a.getIdAuto());
		}
		autos = autodao.buscarPlacas(busca);
		for (AutoEntity a : autos) {
			vo.getNombres().add(a.getPlacas());
			vo.getTipos().add("placas");
			vo.getIds().add(a.getIdAuto());
		}
		resp.getWriter().println(JsonConvertidor.toJson(vo));

	}
	
	
	@RequestMapping(value = "/generalGroup/{busca}", method = RequestMethod.GET)
	public void buscarGeneralG(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		List<ClienteEntity> clientes = clientedao.buscarClientesGroupBy(busca);
		
		resp.getWriter().println(JsonConvertidor.toJson(clientes));
	}
	
	
	@RequestMapping(value = "/cliente/{busca}", method = RequestMethod.GET)
	public void buscarCliente(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		List<ClienteEntity> clientes = clientedao.buscarClientes(busca);
		
		resp.getWriter().println(JsonConvertidor.toJson(clientes));

	}

	@RequestMapping(value = "/filtra", method = RequestMethod.POST , consumes = "application/json")
	public void buscarTipo(HttpServletResponse resp, HttpServletRequest req, @RequestBody String json ) throws IOException {
	//
		AsignadorDeCharset.asignar(req, resp);
		System.out.println("JSON YISUS:"+json);
		BusquedaVO vo =(BusquedaVO) JsonConvertidor.fromJson(json,BusquedaVO.class);
		List<ServicioIndex> lista = servdao.getIndiceServicios1();
		List<ServicioIndex> result = new ArrayList<ServicioIndex>();
		
		if (vo.getTipos().get(0).compareTo("cliente") == 0) {
			//ClienteEntity cli = clientedao.buscarCliente(vo.getNombres().get(0));
			ClienteEntity cli = clientedao.cargar(vo.getIds().get(0));
			System.out.println("cliente nid:"+cli.getIdCliente());
			for (ServicioIndex sind : lista) {
				if (sind.getIdCliente() != null) {
					ClienteEntity c= clientedao.cargar(sind.getIdCliente());
					System.out.println("CLIENTE:"+cli);
					if (c.getNombre().compareTo(cli.getNombre()) == 0) {
						result.add(sind);
					}
				}
			}
		}

		if (vo.getTipos().get(0).compareTo("serie") == 0) {
			AutoEntity au = autodao.cargar(vo.getNombres().get(0));
			System.out.println("objecttttttttt serie:"+au);
			for (ServicioIndex sind : lista) {
				
				if (sind.getIdAuto()==null || au.getIdAuto().toString()==null){}
				else{
					String ids=String.valueOf(au.getIdAuto());//.toString();
					if (sind.getIdAuto().compareToIgnoreCase(ids) ==0){
						
						result.add(sind);
						System.out.println("result :"+result);
					}
				}
			}
		}

		if (vo.getTipos().get(0).compareTo("placas") == 0) {
			AutoEntity auto = autodao.buscarPlacas(vo.getNombres().get(0)).get(0);
			System.out.println("auto  placas:"+auto);
			for (ServicioIndex sind : lista) {
				System.out.println("sind.getid:"+sind.getIdAuto());
				System.out.println("auto,:"+auto.getIdAuto().toString());
				
				if (sind.getIdAuto()==null || auto.getIdAuto().toString()==null){}
				else{
					String id=String.valueOf(auto.getIdAuto());//.toString();
					if (sind.getIdAuto().compareToIgnoreCase(id)==0) {
						result.add(sind);
						System.out.println(" Si entra  result :"+result);
						//break;
					}
				}
			}
		}

		List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
		for (ServicioIndex si : result) {
			AutoEntity auto = new AutoEntity();
			ClienteEntity cliente = new ClienteEntity();
			try {
				auto = autodao.cargar(Long.parseLong(si.getIdAuto()));
				cliente = clientedao.cargar(si.getIdCliente());
			} catch (Exception e) {

			}
			Date fp= servdao.cargar(si.getId()).getFechaPagado();
			ServicioListVO svo = new ServicioListVO(si, auto, cliente, fp);
			ret.add(svo);
		}
		
		
		
		
		resp.getWriter().println(JsonConvertidor.toJson(ret));
	}
	
	@RequestMapping(value = "/auto/{busca}", method = RequestMethod.GET)
	public void buscarAuto(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		System.out.println("buscando auto con serie"+busca);
		List<AutoEntity> autos = autodao.buscarAutos(busca);
		
		resp.getWriter().println(JsonConvertidor.toJson(autos));

	}

	@RequestMapping(value = "/autoPlaca/{placa}", method = RequestMethod.GET)
	public void buscarAutoPlaca(HttpServletResponse resp, HttpServletRequest req, @PathVariable String placa)
			throws IOException {
		System.out.println("buscando auto con placa"+placa);
		List<AutoEntity> autos = autodao.buscarAutosPlaca(placa);
		
		resp.getWriter().println(JsonConvertidor.toJson(autos));

	}
	
	@RequestMapping(value = "/fechaPago/{idServicio}/{fecha}", method = RequestMethod.GET)
	public void putfecha(HttpServletResponse resp, HttpServletRequest req, @PathVariable Long idServicio, @PathVariable String fecha)
			throws IOException, ParseException {
		
		ServicioEntity s= servdao.cargar(idServicio);
		
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = format.parse(fecha);
		System.out.println(date); 
	   
		s.setFechaPagado(date);
		resp.getWriter().println(JsonConvertidor.toJson("fecha de pago guardada"));
		

	}
	
}

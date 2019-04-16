package com.tikal.tallerWeb.control.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.ControlAuto;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.util.JsonConvertidor;

import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData;
import technology.tikal.taller.automotriz.model.servicio.Servicio;
import technology.tikal.taller.automotriz.model.servicio.auto.DatosAuto;

@Controller
@RequestMapping(value = { "/auto" })
public class ControlAutoImp implements ControlAuto {

	@Autowired
	AutoDAO autoDAO;

	@Override
	@RequestMapping(value = { "/loadAuto" }, method = RequestMethod.GET)
	public void loadAuto(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			ServicioIndexAutoData origen) {
		this.autoDAO.cargar(origen.getNumeroSerie());
	}

	@Override
	@RequestMapping(value = { "/getAutos" }, method = RequestMethod.GET)
	public List<ServicioIndexAutoData> getAutos(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response) {
//		Auto a = new Auto().getEquipamiento().;
		DatosAuto da = new DatosAuto();
		Servicio s = new Servicio();
		
		String json = JsonConvertidor.toJson(this.autoDAO.getIndiceAutos());
		return null;
	}

	@Override
	public void buscarAuto(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String numeroSerie, List<ServicioIndexAutoData> cmd) {
		this.autoDAO.buscar(numeroSerie, cmd);
//		technology.tikal.taller.automotriz.model.auto.Equipamiento e =a.getEquipamiento();
		String json = JsonConvertidor.toJson(this.autoDAO.buscar(numeroSerie, cmd));

	}

}

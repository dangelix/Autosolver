package com.tikal.tallerWeb.data.access.rest;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.CostoDAO;
import com.tikal.tallerWeb.data.access.RenglonDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.modelo.reporte.global.DatosAutoRG;
import com.tikal.tallerWeb.modelo.reporte.global.DatosBitacoraRG;
import com.tikal.tallerWeb.modelo.reporte.global.DatosClienteRG;
import com.tikal.tallerWeb.modelo.reporte.global.DatosCostoRG;
import com.tikal.tallerWeb.modelo.reporte.global.DatosServicioRG;
import com.tikal.tallerWeb.modelo.reporte.global.RenglonRG;

public class RenglonDAOImp implements RenglonDAO {

	@Autowired
	ServicioDAO servdao;
	@Autowired
	ClienteDAO clientedao;
	@Autowired
	CostoDAO costodao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	BitacoraDAO bitacora;

	@Override
	public RenglonRG getRenglon(Long id) {
		ServicioEntity servicio = servdao.cargar(id);
		System.out.println("folio00000:"+servicio.getIdServicio());
		ClienteEntity cliente = new ClienteEntity();
		AutoEntity auto = new AutoEntity();
		List<EventoEntity> bita;
		List<GruposCosto> costos;
		if (servicio != null) {
			if (servicio.getIdCliente() != null) {
				cliente = clientedao.cargar(servicio.getIdCliente());
				System.out.println("clienteeeeeeeeee:"+cliente.getNombre());
			}
			if (servicio.getIdAuto() != null) {
				auto = autodao.cargar(Long.parseLong(servicio.getIdAuto()));
				System.out.println("autooooooo:"+auto.getPlacas());
			}
			bita = bitacora.cargar(servicio.getIdServicio());
			costos = costodao.cargar(servicio.getIdServicio());
			return this.llenar(servicio, cliente, auto, bita, costos);
		}

		return new RenglonRG();
	}

	private RenglonRG llenar(ServicioEntity servicio, ClienteEntity cliente, AutoEntity auto, List<EventoEntity> bita,
			List<GruposCosto> costos) {
		RenglonRG ren = new RenglonRG();
		DatosServicioRG serv = new DatosServicioRG();
		DatosClienteRG cli = new DatosClienteRG();
		DatosAutoRG car = new DatosAutoRG();
		DatosBitacoraRG bit = new DatosBitacoraRG();
		DatosCostoRG cos=new DatosCostoRG();
		
		serv.setFolio(servicio.getIdServicio().toString());
		serv.setFalla(servicio.getDescripcion());
		serv.setKilometraje(servicio.getDatosAuto().getKilometraje());
		serv.setProgramado("");
		
//		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy"); //HH:mm:ss");
//    	
//    	DateTime ini = formatter.parseDateTime(servicio.getFechaInicio().toString());
		serv.setFechaInicio(servicio.getFechaInicio().toGMTString().substring(0,11));
		
		
		if(cliente!=null){
		cli.setCiudad(cliente.getDomicilio().getCiudad());
		cli.setColonia(cliente.getDomicilio().getColonia());
		cli.setContacto(cliente.getContacto());
		cli.setDireccion(cliente.getDomicilio().getCalle()+" "+cliente.getDomicilio().getNumInterior());
		cli.setNombre(cliente.getNombre());
		}
		if(auto!=null){
		car.setColor(auto.getColor());
		car.setMarca(auto.getMarca());
		car.setModelo(auto.getModelo());
		car.setPlacas(auto.getPlacas());
		car.setSerie(auto.getNumeroSerie());
		car.setTipo(auto.getTipo());
		car.setVersion(auto.getVersion());
		}
		bit.setDiagnostico("");
		
		for(EventoEntity e:bita){
			if(e.getTipo().compareToIgnoreCase("termino de servicio")==0){
				bit.setFechaEntregaAuto(e.getFecha());
			}
			if(e.getTipo().compareToIgnoreCase("entrada de auto")==0){
				bit.setFechaIngresoAuto(e.getFecha());
			}
		}
		
		double costoManoDeObra= 0;
		double costoRefacciones=0;
		double manoDeObra=0;
		double refacciones=0;
		for(GruposCosto g:costos){
			List<PresupuestoEntity> pre= g.getPresupuestos();
			for(PresupuestoEntity p:pre){
				if(p.getSubtipo().compareTo("MO")==0){
					costoManoDeObra+= p.getCantidad()*Double.parseDouble(p.getPrecioCotizado().getValue());
					manoDeObra+= p.getCantidad()*Double.parseDouble(p.getPrecioCliente().getValue());
				}else{
					costoRefacciones+= p.getCantidad()*Double.parseDouble(p.getPrecioCotizado().getValue());
					refacciones+= p.getCantidad()*Double.parseDouble(p.getPrecioCliente().getValue());
				}
			}
		}
		cos.setCostoManoDeObra(costoManoDeObra);
		cos.setCostoRefacciones(costoRefacciones);
		cos.setIvaCosto((costoManoDeObra+ costoRefacciones)*0.16);
		cos.setIvaFacturado(cos.getIvaCosto()*1.16);
		cos.setRefaccionesFacturado(refacciones);
		cos.setManoDeObraFacturado(manoDeObra);
		cos.setIvaFacturado((refacciones+manoDeObra)*0.16);
		
		ren.setDatosAuto(car);
		ren.setDatosBitacora(bit);
		ren.setDatosCliente(cli);
		ren.setDatosCosto(cos);
		ren.setDatosServicio(serv);
		
		return ren;
	}

}

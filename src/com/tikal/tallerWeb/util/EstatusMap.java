package com.tikal.tallerWeb.util;

import java.util.HashMap;
import java.util.Map;

public class EstatusMap {
	
	public static String getStatus(String evento){
		Map<String,String> mapa= new HashMap<String,String>();
		mapa.put("Entrada de Auto", "Diagnóstico");
		mapa.put("Presupuesto", "Presupuesto");
		mapa.put("Cotización", "Cotización");
		mapa.put("Reparación", "Reparación");
		mapa.put("Facturado", "Facturado");
		mapa.put("Pagado", "Pagado");
		mapa.put("Salida de Auto", "Finalizado");
		mapa.put("Cancelación", "Cancelado");
		mapa.put("Termino de Servicio", "Terminado");
		
		return mapa.get(evento);
	}
}

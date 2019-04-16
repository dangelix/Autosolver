package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tikal.tallerWeb.modelo.reporte.global.ParametrosReporteGlobal;
import com.tikal.tallerWeb.servicio.reporte.global.GeneradorReporteGlobal;

@Controller
@RequestMapping(value={"/reporteGlobal"})
public class GenererReporteController {
	
	@Autowired
	GeneradorReporteGlobal generador;
	
	@RequestMapping(value={"/getReporte.xlsx/{fini}/{ffin}"}, method= RequestMethod.GET, produces= "application/vnd.ms-excel")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String fini, @PathVariable String ffin) throws Exception {
		HttpSession s= request.getSession();
		String n= (String) s.getAttribute("userName");
		if(n==null){
			response.sendError(400);
			return null;
		}
		else{
			response.getOutputStream();
        try {
        	ParametrosReporteGlobal param= new ParametrosReporteGlobal();
        	
        	//param.setFechaFinal(new DateTime());
        	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy"); //HH:mm:ss");
        	System.out.println("inicio:"+fini);
        	System.out.println("fin:"+ffin);
        	DateTime ini = formatter.parseDateTime(fini);
        	DateTime fin = formatter.parseDateTime(ffin);
        //	param.setFechaInicial(dt);
        	param.setFechaInicial(ini);
        	param.setFechaFinal(fin);
        	generador.generaReporte(param, response.getOutputStream());
//            response.getOutputStream().write(buffer);
//            response.flushBuffer();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException ex) {
            // Sacar log de error.
            throw ex;
        }
        
        return null;
		}
    }
	
//	@RequestMapping(value={"/getReporte.xlsx"}, method= RequestMethod.GET)
//	public ModelAndView handleRequest(HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//		HttpSession s= request.getSession();
//		String n= (String) s.getAttribute("userName");
//		if(n==null){
//			response.sendError(400);
//			return null;
//		}
//		else{
//			response.getOutputStream();
//        try {
//        	ParametrosReporteGlobal param= new ParametrosReporteGlobal();
//        	param.setFechaFinal(new DateTime());
//        	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
//        	DateTime dt = formatter.parseDateTime("01/01/2010 00:00:00");
//        	param.setFechaInicial(dt);
//        	generador.generaReporte(param, response.getOutputStream());
////            response.getOutputStream().write(buffer);
////            response.flushBuffer();
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//        } catch (IOException ex) {
//            // Sacar log de error.
//            throw ex;
//        }
//        
//        return null;
//		}
//    }
	private String getMoneda(String valor){
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(valor);
	}

}

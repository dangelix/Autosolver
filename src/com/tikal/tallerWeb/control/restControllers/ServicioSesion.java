package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.util.JsonConvertidor;


@Controller
public class ServicioSesion {
	
	@Autowired
	UsuarioDAOImp usuariodao;
	
	@RequestMapping(value={"/user"},method=RequestMethod.GET,produces="application/json")
	  public void user(HttpServletResponse res, HttpServletRequest req,Principal u) throws IOException {
		req.getSession().setAttribute("userName", u.getName());
		res.getWriter().println(JsonConvertidor.toJson(u));
	}

	//currentSession
	
	@RequestMapping(value={"/currentSession"},method=RequestMethod.GET,produces="application/json")
	  public void currentUser(HttpServletResponse res, HttpServletRequest req) throws IOException {
		
		HttpSession s= req.getSession();
		System.out.println("current::::"+s);
		String n= (String) s.getAttribute("userName");
		System.out.println("nnnnnnnnn:"+n);
		if(n==null){
			res.sendError(400);
		}
	}
	
	
	@RequestMapping(value={"/cerrarSession"},method=RequestMethod.GET,produces="application/json")
	  public void close(HttpServletResponse res, HttpServletRequest req) throws IOException {
		req.getSession().invalidate();
	}
}

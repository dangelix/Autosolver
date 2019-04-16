package com.tikal.tallerWeb.control;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.PMF;

@Controller
public class StartingServer {
	
	@RequestMapping(value="/prueba")
	public String prueba(){
		PersistenceManager pm= PMF.get().getPersistenceManager();
		return "Prueba";
	}
	
	
}

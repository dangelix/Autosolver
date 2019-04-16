package com.tikal.tallerWeb.control.imp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.modelo.servicio.Person;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping("/prueba")
public class MechanicalWebController {
	
	static {
        ObjectifyService.register(Person.class);
    }
	
	@RequestMapping(value = { "/prueba" }, method = RequestMethod.GET)
	public void prueba(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("llega");
		// return "akdsjf";
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String name = "Israel";
		String email = "correo@falso.com";

		Person datos = new Person(email);
		ObjectifyService.ofy().save().entity(datos).now();

		// Key customerKey = KeyFactory.createKey("DatosClienteOS", email);
		//
		// Entity customer = new Entity("DatosClienteOS", customerKey);
		// customer.setProperty("name", name);
		// customer.setProperty("email", email);
		//
		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		// datastore.put(customer);
		response.getWriter().write("Elemento agregado");

	}

	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)
	public void listCustomer(HttpServletResponse response) throws IOException {

//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		Query query = new Query("DatosClienteOS").addSort("email", Query.SortDirection.DESCENDING);
//		List<Entity> customers = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10));
//		ObjectifyService.ofy().load().type(Person.class).list();

		String b = JsonConvertidor.toJson(ObjectifyService.ofy().load().type(Person.class).list());

		response.getWriter().print(b);

	}
}

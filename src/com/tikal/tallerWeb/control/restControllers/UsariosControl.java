package com.tikal.tallerWeb.control.restControllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.JsonConvertidor;


@Controller
@RequestMapping(value={" /usuario"})
public class UsariosControl {
	
	@Autowired
	UsuarioDAOImp usuarioImp;
	
	@RequestMapping(value={"/registro"}, method = RequestMethod.POST, consumes = "Application/Json")
	public void crearUsuario(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) throws UnsupportedEncodingException{
		AsignadorDeCharset.asignar(request, response);
		Usuario usuario = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
		usuarioImp.crearUsuario(usuario);
		
	}
	
	@RequestMapping(value={"/prueba"}, method = RequestMethod.GET)
	public void prueba(HttpServletRequest request, HttpServletResponse response){
		 Storage storage = StorageOptions.getDefaultInstance().getService();

		    // The name for the new bucket
		    String bucketName = "webproyect-1332.appspot.com/taller_almacen";  // "my-new-bucket";

		    // Creates the new bucket
		    Bucket bucket = storage.create(BucketInfo.of(bucketName));

		    System.out.printf("Bucket %s created.%n", bucket.getName());
	}
	
	@RequestMapping(value = {"/setup"}, method = RequestMethod.GET)
	public void crearUsuarioMaster(HttpServletRequest request, HttpServletResponse response){
		Usuario usuario = new Usuario();
		usuario.setTipo("Administrador");
		usuario.setUsuario("root");
		usuario.setPass("root");
		usuarioImp.crearUsuario(usuario);
	}

}

package com.tikal.tallerWeb.control.restControllers;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.control.restControllers.VO.ServicioListVO;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.CostoDAO;
import com.tikal.tallerWeb.data.access.CotizacionDAO;

import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;

import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;
import com.tikal.tallerWeb.server.BlobServicio;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.JsonConvertidor;
import com.tikal.tallerWeb.util.UploadUrl;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;
import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

@Controller
@RequestMapping(value = { "/servicio" })
public class ServicioControl {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Autowired
	ServicioDAO servdao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	ClienteDAO clientedao;

	@Autowired
	BitacoraDAO bitacora;

	@Autowired
	ServletContext context;
	
	@Autowired
	CostoDAO costodao;
	
	@Autowired
	CotizacionDAO cotizaciondao;

	public ServicioControl() {

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = {
			"/add/{asesor}" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json,@PathVariable String asesor)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		System.out.println("yISUS TRAE:"+json);
		DatosServicioVO datos = (DatosServicioVO) JsonConvertidor.fromJson(json, DatosServicioVO.class);
		datos.getServicio().servicio.getMetadata().setStatus("Diagnóstico");
		
		NewServiceObject s = datos.getServicio();
	
		AutoEntity a = (AutoEntity) autodao.cargar(s.getAuto().getNumeroSerie());
		if (a == null) {
			autodao.guardar(s.getAuto());
		} else {
			s.setAuto(a);
		}
		System.out.println("cliente a buscar:"+s.getCliente().getNombre());
		ClienteEntity c = clientedao.buscarCliente(s.getCliente().getNombre());
	//	System.out.println("cliente.nombre"+c.getNombre());
		if (c == null || c.equals(null)) {
			System.out.println("esta aquiiiiii");
			clientedao.guardar(s.getCliente());
		} else {
			s.setCliente(c);
		}
		System.out.println("s.cliente"+s.getCliente().getNombre());
		
		s.getServicio().getMetadata().getCostoTotal();
		servdao.guardar(s.getServicio());
		ServicioEntity ser = s.getServicio();
		ser.setAsesor(asesor);
		ser.setIdAuto(Long.toString(s.getAuto().getIdAuto()));
		ser.setIdCliente(s.getCliente().getIdCliente());

		List<PresupuestoEntity> presu = new ArrayList();
		Long total = 0l;
		if (datos.getPresupuesto() != null) {

			for (GruposCosto g : datos.getPresupuesto()) {
				for (PresupuestoEntity pe : g.getPresupuestos()) {
					pe.setGrupo(g.getNombre());
					pe.setId(ser.getId());
					presu.add(pe);
					total += pe.getCantidad() * Long.parseLong(pe.getPrecioCliente().getValue());
				}
				costodao.guardar(ser.getId(), presu);
			}
		}
		Moneda costoTotal = new Moneda();
		costoTotal.setValue(total + "");
		ser.getMetadata().setCostoTotal(costoTotal);
		
		servdao.guardar(ser);
		// List<AutoEntity> ae =
		// ObjectifyService.ofy().load().type(AutoEntity.class)
		// .filter("numeroSerie", s.getAuto().getNumeroSerie()).list();
		// if (ae.size() == 0) {
		// a = new AutoEntity(s.getAuto());
		// ObjectifyService.ofy().save().entities(a).now();
		// }

		response.getWriter().write(JsonConvertidor.toJson(datos));
	}

	@RequestMapping(value = {
			"/update" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		AsignadorDeCharset.asignar(request, response);
		NewServiceObject s = (NewServiceObject) JsonConvertidor.fromJson(json, NewServiceObject.class);

		AutoEntity a = new AutoEntity();
		ServicioEntity ser = new ServicioEntity();
		EventoEntity ev = new EventoEntity();
		technology.tikal.taller.automotriz.model.servicio.Servicio serv = new technology.tikal.taller.automotriz.model.servicio.Servicio();
		technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData si = new technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData();
		List<AutoEntity> ae = ObjectifyService.ofy().load().type(AutoEntity.class)
				.filter("numeroSerie", s.getAuto().getNumeroSerie()).list();
		if (ae.size() == 0) {
			a = s.getAuto();
			ObjectifyService.ofy().save().entities(a).now();
		}
		response.getWriter().write(JsonConvertidor.toJson(a));
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
	public void handleFileUpload(HttpServletRequest req,
			HttpServletResponse res /*
									 * ,
									 * 
									 * @RequestParam("file") MultipartFile file
									 */) throws IOException {
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);

		// AUTOSOLVER   res.addHeader("Access-Control-Allow-Origin", "https://autosolversystem.appspot.com");
		
		res.addHeader("Access-Control-Allow-Origin", "https://webproyect-1332.appspot.com");
		int len = Integer.parseInt(req.getParameter("length"));
		List<BlobKey> lista = new ArrayList<BlobKey>();
		long id = Long.parseLong(req.getParameter("idEvento"));
		EventoEntity evento = bitacora.cargarEvento(id);
		if (evento.getEvidencia() == null) {
			evento.setEvidencia(new ArrayList<Evidencia>());
		}
		for (int i = 0; i < len; i++) {
			BlobKey blobKey = blobs.get("files" + i);
			lista.add(blobKey);
			Evidencia e = new Evidencia();
			e.setImage(blobKey.getKeyString());
			evento.getEvidencia().add(e);
		}
		bitacora.agregar(evento.getId(), evento);

		// System.out.println("Key: "+ blobKey.toString());
		res.getWriter().println(JsonConvertidor.toJson(evento));

		// InputStream istr=file.getInputStream();
		// ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		// Streams.copy(istr, bytes, true /* close stream after copy */);
		// Blob blob = new Blob(bytes.toByteArray());
		//
		// if (!file.isEmpty()) {
		// try {
		// // String uploadsDir = "/uploads/";
		// // String realPathtoUploads =
		// // request.getContextPath()+"/uploads/";
		// // if(! new File(realPathtoUploads).exists())
		// // {
		// // new File(realPathtoUploads).mkdir();
		// // }
		// //
		// //
		// //
		// // String orgName = file.getOriginalFilename();
		// // String filePath = realPathtoUploads + orgName;
		// // File dest = new File(filePath);
		// // file.transferTo(dest);
		// } catch (Exception e) {
		// }
		// }
	}

	@RequestMapping(value = "/findCar/{numSerie}", method = RequestMethod.GET, produces = "application/json")
	public void findCar(HttpServletResponse resp, HttpServletRequest req, @PathVariable String numSerie)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		AutoEntity car = autodao.cargar(numSerie);
		resp.getWriter().println(JsonConvertidor.toJson(car));
	}

	@RequestMapping(value = "/findCarPlaca/{placa}", method = RequestMethod.GET, produces = "application/json")
	public void findCarPlaca(HttpServletResponse resp, HttpServletRequest req, @PathVariable String placa)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		AutoEntity car = autodao.cargarPlaca(placa);
		resp.getWriter().println(JsonConvertidor.toJson(car));
	}
	
	@RequestMapping(value = "/imagePrueba/{blobid}", method = RequestMethod.GET, produces = "image/jpeg")
	public void getImageAsByte(HttpServletResponse resp, HttpServletRequest req, @PathVariable String blobid)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		BlobKey blobKey = new BlobKey(blobid);
		blobstoreService.serve(blobKey, resp);
		ImagesService imaser = ImagesServiceFactory.getImagesService();
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
		String url = imaser.getServingUrl(blobKey); 
	}

	@RequestMapping(value = "/image/{blobid}", method = RequestMethod.GET, produces = "image/jpg")
	public void getImageAsByteArray(HttpServletResponse resp, HttpServletRequest req, @PathVariable String blobid)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		BlobKey blobKey = new BlobKey(blobid);
		blobstoreService.serve(blobKey, resp);
	}

	@RequestMapping(value = "/findServicio/{blobid}", method = RequestMethod.GET,produces ="application/json")
	public void getServicio(HttpServletResponse resp, HttpServletRequest req, @PathVariable String blobid)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(blobid)));
		if (servicio.getServicio() != null) {
			if (servicio.getServicio().getIdAuto() != null) {
				servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
			}
			if (servicio.getServicio().getIdCliente() != null) {
				servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
			}
			List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
			List<CotizacionEntity> cotizaciones= cotizaciondao.consultar(Long.parseLong(blobid));
			DatosServicioVO datos = new DatosServicioVO();
			datos.setServicio(servicio);
			datos.setPresupuesto(grupos);
			datos.setCotizaciones(cotizaciones);
			resp.getWriter().println(JsonConvertidor.toJson(datos));
		}
	}

	@RequestMapping(value = "/getUpldUrl", method = RequestMethod.GET)
	public void getUploadUrl(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		UploadUrl ur = new UploadUrl();
		String s = BlobServicio.urlUpld;
		s = s.substring(s.indexOf('/') + 1);
		s = s.substring(s.indexOf('/') + 1);
		s = s.substring(s.indexOf('/'));
		ur.setUrl(s);
		resp.getWriter().println(JsonConvertidor.toJson(ur));

	}

	@RequestMapping(value = "/serviciosHoy", method = RequestMethod.GET)
	public void getHoy(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		System.out.println("flkklfj");
		AsignadorDeCharset.asignar(req, resp);
		List<ServicioEntity> a = servdao.getByDate(new DateTime(), new DateTime());
		List<NewServiceObject> ret = new ArrayList<NewServiceObject>();
		System.out.println("ffj");
		for (ServicioEntity s : a) {
			NewServiceObject servicio = new NewServiceObject();
			servicio.setServicio(s);
			System.out.println("---");
			servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
			servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
			ret.add(servicio);
		}
		System.out.println("++++");
		resp.getWriter().println(JsonConvertidor.toJson(ret));
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET )
	public void getStatus(HttpServletResponse resp, HttpServletRequest req, @PathVariable String status)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		// Object principal =
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Usuario user = null;
		// if (principal instanceof Usuario) {
		// user = ((Usuario) principal);
		// }
//		List<ServicioEntity> lista = servdao.getAllServices();
//		System.out.println("lista :"+lista.size());
//		List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
//		for (ServicioEntity si : lista) {
			List<ServicioIndex> lista = servdao.getIndiceServiciosPorStatus(status);
			System.out.println("lista :"+lista.size());
			List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
			for (ServicioIndex si : lista) {	
			AutoEntity auto = new AutoEntity();
			ClienteEntity cliente = new ClienteEntity();
			try {
				auto = autodao.cargar(Long.parseLong(si.getIdAuto()));
				cliente = clientedao.cargar(si.getIdCliente());
			} catch (Exception e) {

			}
			ServicioListVO svo = new ServicioListVO(si, auto, cliente, null);
			ret.add(svo);
		}
		//System.out.println("lista de ret :"+ret);
		resp.getWriter().println(JsonConvertidor.toJson(ret));
	}
	
	@RequestMapping(value = "/numPaginas", method = RequestMethod.GET)
	public void numpags(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int paginas = servdao.cuentaPags();
		res.getWriter().print(paginas);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getAll/{pag}", method = RequestMethod.GET)
	public void getEstatus(HttpServletResponse resp, HttpServletRequest req,@PathVariable int pag)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		// Object principal =
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Usuario user = null;
		// if (principal instanceof Usuario) {
		// user = ((Usuario) principal);
		// }
//		List<ServicioEntity> lista = servdao.getAllServicesP(pag);
//		System.out.println("lista :"+lista.size());
//		List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
//		for (ServicioEntity si : lista) {
		//List<ServicioIndex> lista = servdao.getIndiceServiciosPorStatus(status);
		//List<ServicioEntity> servicios = servdao.getAllServicesP(pag);
		
		List<ServicioIndex> lista = servdao.getIndiceServiciosPag(pag);
		//System.out.println("lista :"+lista.size());
		List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
		
		//List<PresupuestoEntity> presups =ofy().load().type(PresupuestoEntity.class).order("indice").list();
		for (ServicioIndex si: lista) {	
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
		//System.out.println("lista de ret :"+ret);
		resp.getWriter().println(JsonConvertidor.toJson(ret));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public void guardar(HttpServletResponse resp, HttpServletRequest req, @RequestBody String json) throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		DatosServicioVO data = (DatosServicioVO) JsonConvertidor.fromJson(json, DatosServicioVO.class);
		System.out.println("Yisus trae...."+json);
		List<GruposCosto> lista = data.getPresupuesto();
		System.out.println("lista de grupos:"+lista);
		
		
		List<PresupuestoEntity> presupuesto = new ArrayList<PresupuestoEntity>();
		if (lista != null) { 
			int indicegru=1;
			for (GruposCosto gru : lista) {
				System.out.println("lista de presupuestos:"+gru.getPresupuestos());
				int indice = 0;
				for (PresupuestoEntity pre : gru.getPresupuestos()) {
					
					pre.setIndice(Integer.parseInt(indicegru+"00"+indice));
					pre.setGrupo(gru.getNombre());
					pre.getPrecioCotizado()
							.setValue((pre.getCantidad() * Float.parseFloat(pre.getPrecioCliente().getValue())) + "");
					// pre.setAutorizado(false);
					pre.setId(data.getServicio().getServicio().getIdServicio());
					presupuesto.add(pre);
					indice++;
				}
				indicegru++;
			}
		}
		
		AutoEntity auto= data.getServicio().getAuto();
		autodao.guardar(auto);
//		if(data.getServicio().getServicio().getIdAuto()==null){
//			data.getServicio().getServicio().setIdAuto(auto.getIdAuto().toString());
//		}
		ClienteEntity cliente=data.getServicio().getCliente();
		clientedao.guardar(cliente);
		if(data.getServicio().getServicio().getIdCliente()==null||data.getServicio().getServicio().getIdCliente().compareTo(cliente.idCliente)!=0){
			data.getServicio().getServicio().setIdCliente(cliente.getIdCliente());
		}
		servdao.guardar(this.calcularTotal(data.getServicio().getServicio(), presupuesto));
		costodao.guardar(data.getServicio().getServicio().getIdServicio(), presupuesto);
		System.out.println("total:"+data.getServicio().getServicio().getMetadata().getCostoTotal().toString());
		
		ServicioEntity sbd= servdao.cargar(data.getServicio().getServicio().getIdServicio());
		if (sbd!=null){
			System.out.println("si entra****");
			if(sbd.getIdAuto().equals(auto.getIdAuto().toString())){				
			}else{
				System.out.println("si entra auto"+auto.getIdAuto());
				sbd.setIdAuto(auto.getIdAuto().toString());
				servdao.guardar(sbd);
				System.out.println("auto en sbd"+sbd.getIdAuto());
				servdao.guardar(this.calcularTotal(sbd, presupuesto));
				costodao.guardar(sbd.getId(), presupuesto);
				System.out.println("11");
			}
		}
//		List<PagoCobranza> pagos = data.getServicio().getServicio().getCobranza().getPagos();
//		
//		for(int i=0; i<pagos.size();i++){
//			System.out.println("Entra al for");
//			PagoCobranza pago = new PagoCobranza(); 
//			pago= pagos.get(0);
//			if (pago.getMonto().equals(null) || pago.getMonto().getValue().equals("")){
//				Moneda monto = new Moneda();
//				monto.setValue("0.0");
//				pago.setMonto(monto);
//				System.out.println("monto en cero:"+pago.getMonto().getValue());
//				
//			}
//		}
				
		

		//cotizaciondao.guarda(data.getCotizaciones());
		
		/*List<CotizacionEntity> cots= data.getCotizaciones();
		if (cots.size()>0) {
			for (CotizacionEntity cot:cots){
				
			}
		}*/
		
	}

	private ServicioEntity calcularTotal(ServicioEntity s, List<PresupuestoEntity> presupuesto) {
		double total = 0.0;
		for (PresupuestoEntity p : presupuesto) {
			double subtotal = p.getCantidad() * Double.parseDouble(p.getPrecioCliente().getValue());
			total += subtotal;
		}
		Moneda costoTotal = new Moneda();
		costoTotal.setValue(total + "");
		s.getMetadata().setCostoTotal(costoTotal);
		return s;
	}
	
	@RequestMapping(value = "/setup/{folio}", method = RequestMethod.GET)
	public void setUp(HttpServletResponse res,@PathVariable int folio) throws IOException{
		servdao.crearFoliador(folio);
		res.getWriter().println("Setup finalizado");
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public void setUp(HttpServletResponse res, HttpServletRequest req) throws IOException {
		List<ServicioEntity> lista =servdao.getAllServices();
		res.getWriter().println(lista);
	}
	
	
	@RequestMapping(value = "/AutoSerie/{serie}", method = RequestMethod.GET,produces ="application/json")
	public void getAuto(HttpServletResponse resp, HttpServletRequest req, @PathVariable String serie)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		AutoEntity auto= autodao.cargar(serie);
		
		resp.getWriter().println(JsonConvertidor.toJson(auto));
		
	}
	
	
	@RequestMapping(value = "/setPagado/{idServicio}", method = RequestMethod.GET)
	public void setStatus(HttpServletResponse resp, HttpServletRequest req, @PathVariable Long idServicio)
			throws IOException {
		AsignadorDeCharset.asignar(req, resp);
		ServicioEntity s= servdao.cargar(idServicio);
		
		s.getMetadata().setStatus("Pagado");
		
		servdao.guardar(s);
		
	}
	
	@RequestMapping(value = "/fechaPago/{idServicio}", method = RequestMethod.GET)
	public void putfecha(HttpServletResponse resp, HttpServletRequest req, @PathVariable Long idServicio)
			throws IOException, ParseException {
		
		ServicioEntity s= servdao.cargar(idServicio);
		if (s.getMetadata().getStatus().equals("Pagado")){
			Date d = new Date();	   
			s.setFechaPagado(d);
			servdao.guardar(s);
			resp.getWriter().println(JsonConvertidor.toJson("fecha de pago guardada"));
		}	else{
			resp.getWriter().println(JsonConvertidor.toJson("el estatus no es Pagado..."));
		}
		

	}
}

app.service('eventoService', [
		'$http',
		'$q',
		function($http, $q) {
			this.addEvento = function(evento) {
				var d = $q.defer();
				var fecha = "" + evento.fecha.toString();
				var mes = fecha.substring(4, 7);
				if (mes == "Jan") {
					mes = "01";
				}
				if (mes == "Feb") {
					mes = "02";
				}
				if (mes == "Mar") {
					mes = "03";
				}
				if (mes == "Apr") {
					mes = "04";
				}
				if (mes == "May") {
					mes = "05";
				}
				if (mes == "Jun") {
					mes = "06";
				}
				if (mes == "Jul") {
					mes = "07";
				}
				if (mes == "Aug") {
					mes = "08";
				}
				if (mes == "Sep") {
					mes = "09";
				}
				if (mes == "Oct") {
					mes = "10";
				}
				if (mes == "Nov") {
					mes = "11";
				}
				if (mes == "Dec") {
					mes = "12";
				}
				evento.fecha = null;
				send = {
					evento : evento,
					fecha : fecha.substring(8, 10) + "/" + mes + "/"
							+ fecha.substring(11, 24)
				}
				$http.post("/eventos/add/", send).then(function(response) {
					d.resolve(response.data);
				}, function(response) {
				});
				return d.promise;
			}
			this.getServicio = function(id) {
				var d = $q.defer();
				$http.get("/servicio/findServicio/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			this.buscarSerie=function(serie){
				var d = $q.defer();
				$http.get("/servicio/findCar/" + serie).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			this.updateBitacora=function(eventos,id){
				var d = $q.defer();
				$http.post("/eventos/update/",{id:id,eventos:eventos}).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			
			this.getBitacora = function(id) {
				var d = $q.defer();
				$http.get("/eventos/getBitacora/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
			this.setStatus = function(id) {
				var d = $q.defer();
				$http.get("/servicio/setPagado/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
							if(response == 500){
								console.log("500 Error en el Servidor");
							}
						});
				return d.promise;
			}
			this.removeEvento=function(id){
				var d = $q.defer();
				$http.post("/eventos/remove/" + id).then(
						function(response) {
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			}
		} ]);

app.controller("serviceController", [
		"$scope",
		'$http',
		'fileService',
		'$sce',
		'fileUpload',
		'eventoService',
		'$routeParams',
		'modalService',
		'$rootScope',
		'$window','$route',
		function($scope, $http, fileService, $sce, fileUpload, eventoService,
				$routeParams, modalService, $rootScope,$window,$route) {
			var slider = new Slider("#ex6");
			slider.on("change", function(sliderValue) {
				document.getElementById("ex6SliderVal").textContent = sliderValue.newValue;
				$scope.servicio.servicio.datosAuto.combustible=sliderValue.newValue;
			});
			var vm = this;
			vm.reloadData = function(){
				$route.reload();
			}
			
			
//			console.log("valor de la barra");
//			console.log(slider);
//			console.log("no manches");
			$scope.msg="Se están cargando los datos";
			$scope.ids = [ "cliente", "auto", "bitacora", "presupuesto",
					"damage", "cobranza" ];
			$scope.uri = {
				url : ""
			};
			$scope.presupuesto = {
			}
			$scope.proveedores2=[];
			$scope.gruposCosto = [];
			$scope.urlpost = $sce.trustAsResourceUrl($scope.uri.url);
			$http.get("/servicio/getUpldUrl").then(function(response) {
				$scope.uri = response.data;
			});
			$scope.loading = function(){
				$("#loadMe").modal({
				      backdrop: "static", //remove ability to close modal with click
				      keyboard: false, //remove option to close with keyboard
				      show: true //Display loader!
				    });
			}
			
			$scope.servicio = {
				servicio : {},
				cliente : {
					nombre : "",
					rfc : "",
					domicilio : {
						calle : "",
						numero : "",
						cp : "",
						colonia : "",
						ciudad : ""
					},
					contacto : "",
					telefonoContacto : []

				},
				auto : {
					equipamiento : {
						equipoAdicional : []
					}
				},
			}
			$scope.$watch('buscaCli',function(){
				
//				$scope.servicio.cliente=[];
				$scope.buscar2();
		
		},true);
			
			$scope.buscar2 = function(){
				$scope.clientesEncontrados=[];
				
				$http.get("/search/cliente/"+$scope.buscaCli).success(function(data){
					$scope.clientes=data;
					if(data.length){
						$scope.btnhide=false;
					}else{$scope.btnhide=true;}
//				
			});
			}
			$scope.setClient = function(data){
				$scope.buscaCli = data.nombre;
				$scope.servicio.cliente=data;
			}
			
			$scope.$watch('busca',function(){
				$scope.seriesEncontrados=[];
				$scope.servicio.auto.numeroSerie=$scope.busca;
//				console.log($scope.servicio.auto.numeroSerie);
//				if($scope.busca.length>3){
					$scope.buscar();
//				}
			},true);
			$scope.buscar=function(){
				$http.get("/search/auto/"+$scope.busca).success(function(data){
				
					
					for (var i=0; i < data.length; i++){
						$scope.seriesEncontrados.push(data[i].numeroSerie);
						console.log("Encontrados ", $scope.seriesEncontrados);
					}
					
					$('#NoSerie').typeahead({
						
					    source: $scope.seriesEncontrados,

					    updater:function (item) {
					    	$http.get("/servicio/findCar/"+item).success(function(data){
					    		
					    		$scope.servicio.auto=data;
					    		$scope.servicio.auto.modelo=$scope.servicio.auto.modelo*1;
					    		$scope.searchPlaca=data.placas
					    	});
					    	
					        return item;
					    }
					});
					$('#NoSerie').data('typeahead').source=$scope.seriesEncontrados;
			});
			}
			
			$scope.showPdfRevision=function(Servicio){
				
				$scope.url="/reporte/presupuestoPDFSoloDatos/"+Servicio;
				$("#mdlPDF").modal();
			}
			
			$scope.showPdfPresupuesto=function(Servicio){
					
				$scope.url="/reporte/presupuestoPDF/"+Servicio;
				$("#mdlPDF").modal();
			}
			$scope.showPdfCotizacion=function(Servicio){
				
				$scope.url="/reporte/presupuestoPDFSin/"+Servicio;
				$("#mdlPDF").modal();
			}
			$scope.showPdfInerno=function(Servicio){
				
				$scope.url="/reporte/presupuestoPDFInterno/"+Servicio;
				$("#mdlPDF").modal();
			}
			$scope.getTotal = function(name){
				console.log("Que me traes",$scope.servicio.gruposCosto[0]);
				
			    var total = 0;
			    for(var i = 0; i < $scope.servicio.gruposCosto.length; i++){
			    	if($scope.servicio.gruposCosto[i].nombre == name){
			    		var resg=$scope.servicio.gruposCosto[i];
			    		for(var o = 0; o < resg.presupuestos.length; i++){
			    		var costo= resg.presupuestos[o].precioCliente.value;
			    		var cantidad = resg.presupuestos[o].cantidad;
				        total += (costo * cantidad);	
			    	}
			    }}
			    return total;
			}
			eventoService.getServicio($routeParams.id).then(function(data) {
				$scope.Pdatos=data;
				
				console.log("Estos son los datos",$scope.Pdatos);
			});
			$scope.wServicio = false;
			$scope.isClicked = function(){
				var checkBox = document.getElementById("myCheck");
				if (checkBox.checked == true){
					if($scope.servicio.servicio.metadata.status=='Activo' || $scope.servicio.servicio.metadata.status == 'Diagnóstico'
						|| $scope.servicio.servicio.metadata.status == 'Cotización'|| $scope.servicio.servicio.metadata.status == 'Presupuesto'
						|| $scope.servicio.servicio.metadata.status == 'Reparación'){
					$scope.wServicio = true;
					  $scope.sendPresu("Facturado","Se ha detectado que ha dado click en Facturar \n Desea cambiar el estatus a ");
					  setTimeout(function(){$scope.getStatus();}, 1000) 
				}
				  } else {
				  }
			}
//			$scope.calculaTotal=function(datos){
//				var op = 0;
//				for(i in datos){
//					op = op + (datos[i].precioCotizado.value * 1);
//				}
//				return op;
//			}
			$scope.isFacturado = function(ind){
				  var checkBox = document.getElementById("myCheck");
				  
				  var text = document.getElementById("text");

				 
				  if (checkBox.checked == true){
					  
				    text.style.display = "block";
				  } else {
				    text.style.display = "none";
				  }
				$scope.servicio.servicio.cobranza.pagos[ind].facturado=true;
				
			}
			$scope.cargarServicio=function(){
				$scope.loading();
				
				eventoService.getServicio($routeParams.id).then(function(data) {
					$scope.servicio.servicio = data.servicio.servicio;
					if($scope.servicio.servicio.datosAuto.combustible){
						var com=$scope.servicio.servicio.datosAuto.combustible;
						com = parseInt(com); 
						$scope.servicio.servicio.datosAuto.combustible=com;
					}
					$scope.servicio.auto = data.servicio.auto;
					$scope.servicio.cliente = data.servicio.cliente;
					$scope.buscaCli = data.servicio.cliente.nombre;
					$scope.servicio.gruposCosto = data.presupuesto;
					$rootScope.actual=$scope.servicio;
					$rootScope.detallesView=true;
					var proveedores=$scope.servicio.servicio.proveedores;
					$scope.listcotizaciones={proveedores:proveedores};
					$scope.proveedores2=proveedores;
//					console.log($scope.servicio);
					$scope.cotizaciones();
					document.getElementById("ex6SliderVal").textContent=$scope.servicio.servicio.datosAuto.combustible;
			$scope.servicio.servicio.datosAuto.combustible;
//			console.log("cargar servicio");
//			console.log($scope.servicio.servicio.datosAuto.combustible);
//			console.log($scope.servicio.auto.placas);
//			console.log("grupo costos");
//			console.log($scope.servicio.gruposCosto);
//			console.log("lista de cotizaciones");
//			console.log($scope.listcotizaciones);
//			console.log($scope.proveedores2);
			$scope.busca=$scope.servicio.auto.numeroSerie;	
			setTimeout(function() {
			      $("#loadMe").modal("hide");
			      $('.modal-backdrop').remove();
			    }, 1000);
//			document.title ="ACE - " + $scope.servicio.servicio.metadata.status;
			});
				
			}

			$scope.showTab = function(id) {
				$rootScope.Tab=id;
				$scope.ids.forEach(function(i) {
					// console.log(i);
					var myEl = angular.element("#" + i);
					// console.log(myEl.hasClass());
					myEl.removeClass('active');
					myEl.removeClass('in');
				});
				var myEl = angular.element("#" + id);
				myEl.addClass('active in');
			};
			$('.modal').on('shown.bs.modal', function(){
				$(this).find('[autofocus]').focus();
			})
			$scope.guardar = function() {
				
				if(document.FormAuto.NoSerie.value.length==0){
					alert("Tiene que escribir el N\u00FAmero de Serie del Auto");
					document.FormAuto.NoSerie.focus() 
			      	return 0; 
				}
				if(document.FormAuto.tipo.value.length==0){
					alert("Tiene que escribir el Tipo del Auto");
					document.FormAuto.tipo.focus() 
			      	return 0; 
				}
				if(document.FormAuto.marca.value.length==0){
					alert("Tiene que escribir la Marca del Auto");
					document.FormAuto.marca.focus() 
			      	return 0; 
				}
				if(document.FormAuto.version.value.length==0){
					alert("Tiene que escribir la Verion del Auto");
					document.FormAuto.version.focus() 
			      	return 0; 
				}
				if(document.FormAuto.modelo.value.length==0){
					alert("Tiene que escribir el Modelo del Auto");
					document.FormAuto.modelo.focus() 
			      	return 0; 
				}
				if(document.FormAuto.placas.value.length==0){
					alert("Tiene que escribir las Placas del Auto");
					document.FormAuto.placas.focus() 
			      	return 0; 
				}
				if(document.FormAuto.color.value.length==0){
					alert("Tiene que escribir el Color del Auto");
					document.FormAuto.color.focus() 
			      	return 0; 
				}
//				$scope.evtPago();
				$scope.pyf();
//				document.title ="ACE - " + $scope.servicio.servicio.metadata.status;
				if($scope.listcotizaciones.proveedores){
					for(var i = 0; i<$scope.listcotizaciones.proveedores.length;i++){
						var bla = $('#proveedor'+i).val();
						$scope.listcotizaciones.proveedores[i]=bla+"";
					}
				}
//				for(i in $scope.servicio.servicio.cobranza.pagos){
//					if($scope.servicio.servicio.cobranza.pagos[i].monto.value=="")
//						$scope.servicio.servicio.cobranza.pagos[i].monto.value= "0.0";
//				}
				console.log("Esto viene en los pagos ",$scope.servicio.servicio.cobranza.pagos);
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				
				 console.log(send);
				$scope.msg="Se esta guardando el Servicio";
				$scope.loading();
				$http.post('/servicio/save', send).then(function(response) {
					var lista=$scope.listcotizaciones;
					$scope.servicio.servicio.proveedores= $scope.listcotizaciones.proveedores;
					$http.post('/cotizacion/save', {idServicio:$scope.servicio.servicio.idServicio,costos:lista.costos,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo,proveedores:lista.proveedores}).then(function(response) {
						setTimeout(function(){vm.reloadData();}, 4000)
					});
					
				});
				
			}
			$scope.cargarServicio();
			$scope.guardar2 = function() {
				if($scope.listcotizaciones.proveedores){
					for(var i = 0; i<$scope.listcotizaciones.proveedores.length;i++){
						var bla = $('#proveedor'+i).val();
						$scope.listcotizaciones.proveedores[i]=bla+"";
					}
				}
				
				var send = {
					servicio : $scope.servicio,
					presupuesto : $scope.servicio.gruposCosto
				}
				 console.log(send);
				$scope.loading();
				$http.post('/servicio/save', send).then(function(response) {
				}, function(response) {
				})
				var lista=$scope.listcotizaciones;
				$scope.servicio.servicio.proveedores= $scope.listcotizaciones.proveedores;
//				console.log(lista.proveedores);
//				var nc=$scope.newCot;
//				lista.push(nc)
//				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
				$http.post('/cotizacion/save', {idServicio:$scope.servicio.servicio.idServicio,costos:lista.costos,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo,proveedores:lista.proveedores}).then(function(response) {
					
					$scope.cargarServicio();
					$scope.cotizaciones();
				}, function(response) {
				})
			

//				$http.post('/cotizacion/save', {listcotizaciones:lista,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo}).then(function(response) {
				console.log(lista.proveedores);
				$http.post('/cotizacion/save', {costos:lista.costos,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo,proveedores:lista.proveedores}).then(function(response) {
					$scope.cotizaciones();
				}, function(response) {
				})
				eventoService.updateBitacora($scope.eventos,$routeParams.id).then(function(data) {
					$scope.eventos = data;
					for(var i =0; $scope.eventos.length; i++){
						var eve= $scope.eventos[i];
						for(var j= 0; j< eve.evidencia.length;j++){
							if(eve.evidencia[j].appended){
								$('#'+eve.image).addClass("selected");
							}
						}
					}
					setTimeout(function() {
					      $("#loadMe").modal("hide");
					    }, 1000);
					$scope.cargarServicio();
//					$window.location.href = '/reporte/presupuestoPDF/'+$routeParams.id;
				});
			}
			
			$scope.addCaract = function() {
				var caract = $scope.caracteristicaAuto;
				if ($scope.servicio.auto.equipamiento.equipoAdicional
						.indexOf(caract) < 0) {
					$scope.servicio.auto.equipamiento.equipoAdicional
							.push(caract);
				}
				$scope.caracteristicaAuto = "";
			}
			$scope.evento = {
				id : $scope.servicio.servicio.id
			};
			$scope.eventos = [];
			eventoService.getBitacora($routeParams.id).then(function(data) {
				$scope.eventos = data;
				for(var i =0; i<$scope.eventos.length; i++){
					var eve= $scope.eventos[i];
					for(var j= 0; j< eve.evidencia.length;j++){
						if(eve.evidencia[j].appended){
							$('#'+eve.image).addClass("selected");
						}
					}
				}
			});
			$scope.fecha = function() {
				var f = new Date();
				modalService.aver('showModalEvento');

			}

			$scope.sendImages = function(id) {
				
				if ($scope.images.length) {
					var file = $scope.images;
					fileUpload.uploadFileToUrl(file, $scope.uri.url, id).then(
							function(data) {
								var id= data.idEvento;
								var notfound=true;
								for(var i = 0;i< $scope.eventos.length;i++){
									if($scope.eventos[i].idEvento==id){
										$scope.eventos[i]=data;
										notfound=false;
										break;
									}
								}
								if(notfound){
									$scope.eventos.push(data);
									
								}
								$scope.cargarServicio();
								// recursivo
							});
				}
				
			}

			$scope.nuevoEvento=function(){
				var fec= new Date();
			
				$scope.evento={
						fecha: fec
				}
				$scope.verModal('showModalEvento');
			}
			$scope.getStatus = function(){
				eventoService.getServicio($routeParams.id).then(function(data) {
					console.log("Esto viene de datos", data.servicio.servicio.metadata.status);
					$scope.servicio.servicio.metadata.status = data.servicio.servicio.metadata.status;
				});
			}
			$scope.evtPago = function(){
				
				
			if(($scope.servicio.servicio.metadata.status=='Activo' || $scope.servicio.servicio.metadata.status == 'Diagnostico'
			|| $scope.servicio.servicio.metadata.status == 'Cotización'|| $scope.servicio.servicio.metadata.status == 'Presupuesto'
			|| $scope.servicio.servicio.metadata.status == 'Reparación'|| $scope.servicio.servicio.metadata.status == 'Facturado')
				&& $scope.currency($scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta,2,[]) <= 0.00 && !Number.isNaN($scope.aCuenta)){
				$scope.wServicio = true;
				$scope.sendPresu("Pagado", "Se ha detectado un pago y se cubre el adeudo \n Click en Aceptar para cambiar el estatus a ");
				$scope.getStatus();
				//$scope.guardar()
			}
			}
			$scope.sendPresu = function(tipo,msg){
				 //var r = confirm(msg + tipo);
				   // if (r == true) {
				    	$scope.evento.tipo =tipo;
							$scope.evento.fecha =new Date();
							$scope.addEvento();	
						
				   // }  
			}
			$scope.hidenGrup=false;
			$scope.indiceGru=null;
			$scope.nGrupo = function(ind){
				$scope.indiceGru=ind;
				$('#grupo'+ind).hide();
				$('#grupoIn'+ind).show();
			}
			$scope.cambioNombre = function(ind){
				
				$('#grupo'+ind).show();
				$('#grupoIn'+ind).hide();
			}
			$scope.clickAll = function(val){
				
				if(val == true){
					if($scope.servicio.servicio.metadata.status !="Reparación"){
//						var tipo ="Reparación";
						//var msg = "¿Desea cambiar el estatus " + $scope.servicio.servicio.metadata.status + " a Reparación?";
						// var r = confirm(msg);
						  //  if (r == true) {
//						    	$scope.wServicio = true;
//						    	$scope.evento.tipo =tipo;
//								$scope.evento.fecha =new Date();
//								$scope.addEvento();	
								
						   // } 
						$scope.servicio.servicio.metadata.status="Reparación";
						}
			}else if(val != true){
				var p = $scope.servicio.servicio.cobranza.pagos.length;
				$scope.servicio.servicio.metadata.status="Cotización";
				if(p > 0){
					var valid = false;
				$scope.servicio.servicio.cobranza.pagos = [];
				for(i in $scope.servicio.gruposCosto){
					
					
					for(j in $scope.servicio.gruposCosto[i].presupuestos){
						if($scope.servicio.gruposCosto[i].presupuestos[j].autorizado){
//							PONER ESTATUS EN COTIZACION SI HAY CONCEPTOS AUTORIZADOS
							$scope.servicio.servicio.metadata.status="Reparación";
							
							valid=true;								
							break;
						}
					}
				}
			}
		}
			}
			$scope.sendReparacion = function(ind){
				var checkBox = document.getElementById("ckAtorizado"+ind);
				if (checkBox.checked == true){
//					$scope.getStatus();
//					if($scope.servicio.servicio.metadata.status !="Reparación"){
//					var tipo ="Reparaci\u00F3n";
//					    	$scope.wServicio = true;
//					    	$scope.evento.tipo =tipo;
//							$scope.evento.fecha =new Date();
//							$scope.addEvento();	
//					}
					$scope.servicio.servicio.metadata.status="Reparación";
				}else if(checkBox.checked != true){
					var p = $scope.servicio.servicio.cobranza.pagos.length;
					$scope.servicio.servicio.metadata.status="Cotización";
					if(p > 0){
						var valid = false;
					$scope.servicio.servicio.cobranza.pagos = [];
					for(i in $scope.servicio.gruposCosto){
						
						
						for(j in $scope.servicio.gruposCosto[i].presupuestos){
							if($scope.servicio.gruposCosto[i].presupuestos[j].autorizado){
//								PONER ESTATUS EN COTIZACION SI HAY CONCEPTOS AUTORIZADOS
								$scope.servicio.servicio.metadata.status="Reparación";
								
								valid=true;								
								break;
							}
						}
					}
//					if(valid == false){
////						PONER ESTATUS EN REPARACION NO SI HAY CONCEPTOS AUTORIZADOS
//						$scope.servicio.servicio.metadata.status="Presupuesto";
//						}
				}
			}
//				document.title ="ACE - " + $scope.servicio.servicio.metadata.status;	
			}
			$scope.splicePag = function(x){
				$scope.servicio.servicio.cobranza.pagos.splice(x, 1);
			}
			
			$scope.pyf = function(){
				if($scope.servicio.servicio.metadata.costoTotal.value > 0){
					$scope.servicio.servicio.metadata.status="Reparación";
				}
				
				for(i in $scope.servicio.servicio.cobranza.pagos){
				if($scope.servicio.servicio.cobranza.pagos[i].facturado == true){
					$scope.servicio.servicio.metadata.status="Facturado";
					}
				}
				var op = $scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta;
				
				if($scope.servicio.servicio.cobranza.pagos.length && op <= 0.01){
					$scope.servicio.servicio.metadata.status="Pagado";
				}
			}
			
			$scope.addEvento = function() {
				// var e = $scope.evento;
				// $scope.eventos.push(e);
				// $scope.evento = {
				// evidencia:[]
				// };
				$scope.evento.id = $routeParams.id;
				if($scope.evento.tipo == "Pagado"){
					eventoService.setStatus($routeParams.id).then(function(data) {
						
					});
				}else{

				eventoService.addEvento($scope.evento).then(function(data) {
					// console.log(data);
					$scope.images = fileService.getFile("b_pics");
					$scope.indice = 0;
					if($scope.images){
						$scope.sendImages(data.idEvento);
					}else{
						$scope.eventos.push(data);
						if($scope.wServicio == false){
						$scope.cargarServicio();
						}
					}
					if($scope.wServicio == false){
					$scope.cargarServicio();
					}
				})
			}
				$scope.getStatus();
			}
			$scope.results = [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas',
					'California', 'Colorado', 'Connecticut', 'Delaware',
					'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois',
					'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
					'Maine', 'Maryland', 'Massachusetts', 'Michigan',
					'Minnesota', 'Mississippi', 'Missouri', 'Montana',
					'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey',
					'New Mexico', 'New York', 'North Dakota', 'North Carolina',
					'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania',
					'Rhode Island', 'South Carolina', 'South Dakota',
					'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia',
					'Washington', 'West Virginia', 'Wisconsin', 'Wyoming' ];
			$scope.findCliente = function() {
				// implementar servicio de búsqueda
			}

			$scope.addGrupo = function() {
				var nombregrupo = $scope.nombreGrupo;
				$scope.nombreGrupo = "";
				$scope.servicio.servicio.gruposCosto.push(nombregrupo);
				var tipo = $scope.filtro.tipo;
				$scope.servicio.gruposCosto.push({
					nombre : nombregrupo,
					presupuestos : [],
					tipo : tipo
				});

			}
			$scope.modalEvento = true;
			$scope.verModal = function(modal) {
			
				$scope.modals = modalService.aver(modal);
			}
			$scope.addPresupuesto = function(gru,tipo) {

				for(var i = 0; i<$scope.listcotizaciones.proveedores.length;i++){
					var bla = $('#proveedor'+i).val();
					$scope.listcotizaciones.proveedores[i]=bla+"";
				}
				if (!gru.presupuestos) {
					gru.presupuestos = [];
				}
//				var tipo = $scope.filtro.tipo;
				gru.presupuestos.push({
					tipo : tipo,
					subtipo:"MO",
					id : $routeParams.id,
					concepto : "",
					precioUnitario:{value:""},
					precioUnitarioConIVA:true
				});
				var lista=$scope.listcotizaciones;
				$http.post('/cotizacion/save', {idServicio:$scope.servicio.servicio.idServicio,costos:lista.costos,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo,proveedores:lista.proveedores}).then(function(response) {
					
//					$scope.cargarServicio();
//					$scope.cotizaciones();
				}, function(response) {
				})
				$scope.cotizaciones();
			}
			$scope.filtro = {
				tipo : "HP"
			};
			$scope.filtroCot = {
				concepto : ""
			};
			$scope.showPresupuesto = function(ver) {
				$scope.filtro.tipo = ver;
			}

			$scope.addCot = function() {
				var cot = $scope.newCot;
//				var concepto = $scope.cotizando.concepto;
			
				for(var i = 0; i<$scope.listcotizaciones.proveedores.length;i++){
					var bla = $('#proveedor'+i).val();
					$scope.listcotizaciones.proveedores[i]=bla;
				}
				$scope.listcotizaciones.proveedores.push("Proveedor"+ $scope.listcotizaciones.proveedores.length);
				
				for(var i=0; i< $scope.listcotizaciones.costos.length;i++){
					var costo={concepto:$scope.listcotizaciones.costos[i].concepto,
						modelo:$scope.servicio.auto.modelo,
						tipo:$scope.servicio.auto.tipo
					}
					
					$scope.listcotizaciones.costos[i].costos.push(costo);
				}
				$scope.newCot = {
					proveedor : "",
					precio : 0,
					tiempo : "",
					concepto : "",
					selected:false
				};
				console.log("Lista Cotizaciones",$scope.listcotizaciones);
				
			}
			$scope.newCot = {selected:false};
			$scope.setPrecio = function(precio,concepto,cotizacion) {
				var encontrado=false;
				
				for(var i =0; i< $scope.servicio.gruposCosto.length;i++){
					for(var j=0; j<$scope.servicio.gruposCosto[i].presupuestos.length;j++){
						if($scope.servicio.gruposCosto[i].presupuestos[j].concepto== concepto){
							var newprecio=parseFloat(precio);
							if(parseFloat=="NaN"){
								precio=0;
							}
							$scope.servicio.gruposCosto[i].presupuestos[j].precioUnitario.value=$scope.currency(precio/1.16, 2, [',', "'", '.']);
							$scope.servicio.gruposCosto[i].presupuestos[j].precioCliente={value:$scope.currency(precio*1.3, 2, [',', "'", '.'])};
							encontrado=true;
							break;
						}
					}
					if(encontrado){
						break;
					}
				}
				
				for(var i= 0; i<$scope.listcotizaciones.costos.length;i++){
					if($scope.listcotizaciones.costos[i].concepto==concepto){
						for(var j = 0;j<$scope.listcotizaciones.costos[i].costos.length;j++){
							$scope.listcotizaciones.costos[i].costos[j].selected=false;
						}
						break;
					}
				}
				cotizacion.selected=true;
//				$scope.cotizando.precioUnitario.value = precio;
			}
			$scope.listcotizaciones=[];
			
			$scope.borrarEvento=function(e){
				var index=$scope.eventos.indexOf(e);
				$scope.eventos.splice(index,1);
				eventoService.removeEvento(e.idEvento).then(function(data){
				});
			};
			$scope.verEvidencias=function(e){
				
				$scope.modals = modalService.aver('showModalEvidencias');
				$scope.evidenciasVer=e.evidencia;
				$scope.evidenciasAdd=e.idEvento;
					for(var j= 0; j< $scope.evidenciasVer.length;j++){
						if($scope.evidenciasVer[j].appended){
							$('#'+$scope.evidenciasVer[j].image).addClass("selected");
						}
					}
			};
			$scope.$watch('evidenciasVer',function(){
				if($scope.evidenciasVer){
				   for(var j= 0; j< $scope.evidenciasVer.length;j++){
						if($scope.evidenciasVer[j].appended){
							$('#'+$scope.evidenciasVer[j].image).addClass("selected");
						}
					}
				}
			},true);
			
			$scope.appendImages=function(){
				$scope.images = fileService.getFile("b_pics");
				$scope.indice = 0;
				var idEvento=$scope.evidenciasAdd
				$scope.sendImages(idEvento);
			}
			$scope.newDatoCobranza={monto:{value:""}};
			$scope.appendPago=function(){
				
				var fecha= new Date();
				var pago= {fecha:fecha,monto:{value:"0.0"}};
				$scope.servicio.servicio.cobranza.pagos.push(pago);
//				$scope.newDatoCobranza={monto:{value:""}};
//				console.log($scope.servicio.servicio);
			}
			
			$scope.cotizaciones = function(pre,append,indice) {
				var send={params:{
					
					full:true,
					tipo : $scope.servicio.auto.tipo,
					modelo : $scope.servicio.auto.modelo,
					idServicio:$scope.servicio.servicio.idServicio,
					proveedores:{proveedores:$scope.listcotizaciones.proveedores},
					cadena:{presupuesto:$scope.servicio.gruposCosto}
			
				}}				
				if(pre){
					send.params.presupuesto=pre;
					send.params.full=false;
					send.params.cadena=pre;
				}
				var send2={
						full:true,
						tipo : $scope.servicio.auto.tipo,
						modelo : $scope.servicio.auto.modelo,
						idServicio:$scope.servicio.servicio.idServicio,
						proveedores:$scope.listcotizaciones.proveedores,
						cadena:"{presupuesto:"+JSON.stringify($scope.servicio.gruposCosto)+"}"
				}
				if(pre){
					send2.presupuesto=pre;
					send2.full=false;
					send2.cadena=JSON.stringify(pre);
				}
					$http.post('/cotizacion/getFull',send2).success(function(response){
						if(!append){
							$scope.listcotizaciones=response;
							$scope.proveedores2= response.proveedores;
						}else{
							$scope.listcotizaciones.costos[indice]=response.costos[0];
							$scope.listcotizaciones.proveedores=response.proveedores;
							$scope.proveedores2= response.proveedores;
						}
					}).error(function(response){
						console.log(response);
					});
				
			}
			
			$scope.conceptoChg= function(pre,indice,gru){
				console.log(gru);
				var indiceReal=0;
				var indiceGrupo= $scope.servicio.gruposCosto.indexOf(gru);
				for(var i = 0; i<indiceGrupo; i++){
					var grupo= $scope.servicio.gruposCosto[i];
					for(var z = 0; z<grupo.presupuestos.length;z++){
						if(grupo.presupuestos[z].subtipo=="RE" || grupo.presupuestos[z].subtipo=="IN" || grupo.presupuestos[z].subtipo=="SE"){
							indiceReal++;
						}
					}
					
				}
				for(var z = 0; z<indice;z++){
					if(gru.presupuestos[z].subtipo=="RE" || gru.presupuestos[z].subtipo=="IN" || gru.presupuestos[z].subtipo=="SE"){
						indiceReal++;
					}
				}
//				indiceReal+=indice;
				if($scope.contarCotizables()>$scope.listcotizaciones.costos.length){
					var insert={concepto:pre.concepto,costos:[]};
					for(var z=0; z< $scope.listcotizaciones.proveedores.length;z++){
						insert.costos.push({concepto:pre.concepto});
					}
					
					$scope.listcotizaciones.costos.splice(indiceReal,0,insert);
				}
				if(pre.concepto){
					if(pre.subtipo== "RE" || pre.subtipo=="IN" || pre.subtipo =="SE"){
						var lista=$scope.listcotizaciones;
						for(var i = 0; i<$scope.listcotizaciones.proveedores.length;i++){
							var bla = $('#proveedor'+i).val();
							$scope.listcotizaciones.proveedores[i]=bla+"";
						}
						$http.post('/cotizacion/save', {idServicio:$scope.servicio.servicio.idServicio,costos:lista.costos,tipo:$scope.servicio.auto.tipo,modelo:$scope.servicio.auto.modelo,proveedores:lista.proveedores}).then(function(response) {
							$scope.cotizaciones(pre,true,indiceReal);
						});
					}else{
						for(var j= 0; j<$scope.listcotizaciones.costos.length;j++){
							if($scope.listcotizaciones.costos[j].concepto==pre.concepto){
								$scope.listcotizaciones.costos.splice(j,1);
								return null;
							}
						}
					}
				}
			}
			
//			$scope.selectImage=function(img){
//				if(img.appended == true){
//					img.appended=false;
//				}else{img.appended=true;}
//				$('#'+img.image).toggleClass("selected");
//			}
			
			$scope.imprimir=function(){
//				$scope.guardar2();
				$scope.modals=modalService.aver('modalImprimir');
			}
			
			$scope.verDocumento=function(tipo){
				$window.location.href = '/reporte/presupuestoPDF'+tipo+'/'+$routeParams.id;
			}
			
			$scope.currency=function(value, decimals, separators) {
			    decimals = decimals >= 0 ? parseInt(decimals, 0) : 2;
//			    separators = separators || ['.', "", ''];
			    separators = ['', "", '.'];
			    var number = (parseFloat(value) || 0).toFixed(decimals);
			    if (number.length <= (4 + decimals))
			        return number.replace('.', separators[separators.length - 1]);
			    var parts = number.split(/[-.]/);
			    value = parts[parts.length > 1 ? parts.length - 2 : 0];
			    var result = value.substr(value.length - 3, 3) + (parts.length > 1 ?
			        separators[separators.length - 1] + parts[parts.length - 1] : '');
			    var start = value.length - 6;
			    var idx = 0;
			    while (start > -3) {
			        result = (start > 0 ? value.substr(start, 3) : value.substr(0, 3 + start))
			            + separators[idx] + result;
			        idx = (++idx) % 2;
			        start -= 3;
			    }
			    return (parts.length == 3 ? '-' : '') + result;
			}
			
			$scope.$watch('servicio.servicio.cobranza',function(){
				$scope.aCuenta=0.0;
				var cobranza= $scope.servicio.servicio.cobranza;
				for(var i=0; i<cobranza.pagos.length;i++){
					$scope.aCuenta += parseFloat(cobranza.pagos[i].monto.value);
					$scope.aCuenta = $scope.aCuenta.toFixed(2) * 1;
				} 
				$rootScope.saldo='$'+$scope.currency($scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta, 2, [',', "'", '.']);
			},true);
			$scope.reloadSaldo=function(){
				$scope.aCuenta=0.0;
				var cobranza= $scope.servicio.servicio.cobranza;
				for(var i=0; i<cobranza.pagos.length;i++){
					$scope.aCuenta += parseFloat(cobranza.pagos[i].monto.value);
					$scope.aCuenta = $scope.aCuenta.toFixed(2) * 1;
				} 
				$rootScope.saldo='$'+$scope.currency($scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta, 2, [',', "'", '.']);
			
			}
			$scope.utilidad1= function(pres){
				if(pres.precioUnitarioConIVA){
					if(pres.precioCliente){
						var valor=pres.precioCliente.value-pres.precioUnitario.value*1.16;
				return $scope.currency(valor,2, [',', "'", '.']);
				}
				}
				if(pres.precioCliente.value){
					var valor=pres.precioCliente.value -
					pres.precioUnitario.value;
				
				return $scope.currency(valor,2, [',', "'", '.']);
				}
			}
			$scope.utilidad= function (pres){
				var valor=parseFloat(pres.precioUnitario.value);
				
				if(valor==0){
					return 100;
				}
				if(valor>0){
					if(pres.precioUnitarioConIVA){
						var porcentaje=$scope.currency(((pres.precioCliente.value -	pres.precioUnitario.value*1.16)/(pres.precioUnitario.value*1.16))*100,2, [',', "'", '.']);
						return porcentaje;
					}else{
						var porcentaje= $scope.currency(((pres.precioCliente.value -	pres.precioUnitario.value)/(pres.precioUnitario.value))*100,2, [',', "'", '.']);
						return porcentaje;
					}
				}
			}
			
			$scope.mayusculas=function(value){
				value=value.toUpperCase();
				return value;
			}
			
			$scope.seleccionarTodoIVA=function(grupo){
				console.log(grupo);
			}
			
			$scope.seleccionarTodoIVASub=function(gru){
				for(var i = 0; i<gru.presupuestos.length;i++){
					gru.presupuestos[i].subtotalConIVA=gru.ivaSubTodos;
				}
			}
			$scope.seleccionarTodoAutorizado=function(gru){
				for(var i = 0; i<gru.presupuestos.length;i++){
					gru.presupuestos[i].autorizado=gru.autoTodos;
				}
				
				$scope.clickAll(gru.autoTodos);
				
			}
			
			$scope.calcularPUI=function(pre){
				
				if(pre.precioUnitarioConIVA){
					return Math.round((pre.precioUnitario.value/1.16)*100)/100;
				}
				var concepto= pre.concepto;
				for(var i= 0; i< $scope.listcotizaciones.costos.length;i++){
					if($scope.listcotizaciones.costos[i].concepto==concepto){
						for(var j = 0; j < $scope.listcotizaciones.costos[i].costos.length;j++){
							if($scope.listcotizaciones.costos[i].costos[j].selected){
								return pre.precioUnitario.value=$scope.listcotizaciones.costos[i].costos[j].precio;
							}
						}
					}
				}
			}
			
			$scope.contarCotizables=function(){
				var valor= 0;
				for(var i = 0;i<$scope.servicio.gruposCosto.length;i++){
					var gru= $scope.servicio.gruposCosto[i];
					for(var j = 0; j< gru.presupuestos.length; j++){
						if(gru.presupuestos[j].subtipo=="RE" || gru.presupuestos[j].subtipo=="IN" || gru.presupuestos[j].subtipo=="SE"){
							valor++;
						}
					}
				}
				return valor;
			}
			
			$scope.$watch('servicio.gruposCosto',function(){
				calcularTotal();
			},true);
			
			$scope.facturado=function(){
					
					var total= calcularTotal();
					if($scope.servicio.servicio.cobranza.facturado){
						total= total*1.16;
						$scope.servicio.servicio.metadata.costoTotal.value=total;
					}
					
			}
			
			function calcularTotal(){
				var total=0;
				for(var i = 0; i<$scope.servicio.gruposCosto.length;i++){
					var gru= $scope.servicio.gruposCosto[i];
					var subtotal=0;
					for(var j = 0; j< gru.presupuestos.length; j++){
						var pre = gru.presupuestos[j];
						if(pre.autorizado){
							subtotal= subtotal+(pre.cantidad*pre.precioCliente.value);
						}
					}
					total= total+subtotal;
				}
				$scope.servicio.servicio.metadata.costoTotal.value=total;
				$rootScope.saldo='$'+$scope.currency($scope.servicio.servicio.metadata.costoTotal.value - $scope.aCuenta, 2, [',', "'", '.']);
				return total;
			}
			
			$scope.getFecha= function(fecha){
				if(fecha instanceof Date){
				}else{
					fecha= new Date(fecha);
				}
				var day=fecha.getDay();
				var mes=fecha.getMonth()+1;
				var anio=fecha.getFullYear();
				var hora=fecha.getHours();
				var minutos=fecha.getMinutes();
				var seg= fecha.getSeconds();
				if(day<10){
					day='0'+day;
				}
				if(mes<10){
					mes='0'+mes;
				}
				if(hora<10){
					hora='0'+hora;
				}
				if(minutos<10){
					minutos='0'+minutos
				}
				if(seg<10){
					seg='0'+seg;
				}
			
				return day+"-"+mes+"-"+anio+" "+hora+":"+minutos+":"+seg;
			}
			
			$scope.borraPresupuesto=function(grupo,indice){
				grupo.presupuestos.splice(indice,1);
				$scope.cotizaciones();
			}
			
			$scope.borrarProveedor=function(indice){
				$scope.listcotizaciones.proveedores.splice(indice,1);
				var borrar=[];
				for(var i=0; i<$scope.listcotizaciones.costos.length;i++){
					var reng= $scope.listcotizaciones.costos[i];
					borrar.push(reng.costos[indice]);
					reng.costos.splice(indice,1);
				}
				console.log(borrar);
				var costos=[]
				costos.push({costos:borrar})
				$http.post("/cotizacion/delete",{idServicio:$scope.servicio.servicio.idServicio,costos:costos}).then(function(response){
					console.log(response);
				});
			}
			$( document ).ready(function() {
				if($rootScope.Tab){
					$scope.showTab($rootScope.Tab);
					}
			});
			
		} ]);

app.controller("mainController", ["$scope",'$http','fileService','$sce','fileUpload','eventoService','$routeParams','modalService','$rootScope','$window','$route',
	function($scope, $http, fileService, $sce, fileUpload, eventoService,$routeParams, modalService, $rootScope,$window,$route) {
	$('.datepicker').datepicker({format: 'mm-dd-yyyy '});
	
	$('.input-daterange').datepicker({
	    format: "mm-dd-yyyy"
	});
	
	$('.input-daterange input').each(function() {
	    $(this).datepicker("format","mm-dd-yyyy");
	});
} ]);	
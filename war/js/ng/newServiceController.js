app.service('eventoService',['$http','$q','$location',function($http,$q,$location){

	this.addEvento=function(evento){
		$http.post("",evento).then(function(response){},function(response){});
	}
	

}]);

app
		.controller(
				"newServiceController",
				[
						"$scope",
						'$http','$location','eventoService','$rootScope','sessionService',
						function($scope, $http,$location,eventoService,$rootScope,sessionService) {
							var slider = new Slider("#ex6");
							slider.on("change", function(sliderValue) {
								document.getElementById("ex6SliderVal").textContent = sliderValue.newValue;
								$scope.servicio.servicio.datosAuto.combustible=sliderValue.newValue;
							});
							sessionService.isAuthenticated().then(function(){
							$scope.mensaje = "Texto cargado desde el controlador Pagina1Controller";
							$scope.ids = [ "cliente", "auto", "bitacora",
									"presupuesto", "damage", "cobranza" ];
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
								eventos : []
							}

							$scope.guardar = function() {
//								console.log($scope.servicio);
								$scope.servicio.auto.numeroSerie=$scope.search;
								$scope.servicio.cliente.id=null;
								$scope.servicio.cliente.idCliente=null;
								$scope.servicio.cliente.nombre = $scope.busca;
								$scope.servicio.auto.placas =$scope.searchPlaca;
								var send={servicio:$scope.servicio};
								
								if(!$scope.servicio.cliente.nombre){
									alert("Ingresar datos del Cliente")
									return
								}
								if(!$scope.servicio.auto.numeroSerie){
									alert("Ingresar datos del Auto")
									return
								}
								$scope.loading();
								$http.post('/servicio/add/'+$scope.asesor, send)
										.then(function(response) {
											 $("#loadMe").modal("hide");
											alert("Servicio Guardado");
											if(!$rootScope.serviciosHoy){
												$rootScope.serviciosHoy=[]
											}
//											$rootScope.serviciosHoy.push(response.data);
//											console.log(response);
											window.scrollTo(0, 0);
											$location.path("servicio/view/"+response.data.servicio.servicio.idServicio);
										}, function(response) {
											alert("Something went wrong");
										})
							}
							$scope.loading = function(){
								$("#loadMe").modal({
								      backdrop: "static", //remove ability to close modal with click
								      keyboard: false, //remove option to close with keyboard
								      show: true //Display loader!
								    });
							}
							$scope.showTab = function(id) {
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
							$rootScope.detallesView=false;
							$scope.addCaract = function() {
								var caract = $scope.caracteristicaAuto;
								if ($scope.servicio.auto.equipamiento.equipoAdicional
										.indexOf(caract) < 0) {
									$scope.servicio.auto.equipamiento.equipoAdicional
											.push(caract);
								}
								$scope.caracteristicaAuto = "";
							}
							$scope.fecha = function() {
								var f = new Date();
								// $scope.evento.fecha=
								// f.getDay()+"-"+f.getMonth()+"-"+f.getFullYear()+"T"+f.getHours()+":"+f.getMinutes();
							}
							$scope.$watch('busca',function(){
								
//									$scope.servicio.cliente=[];
									$scope.buscar2();
							
							},true);
							
							$scope.buscar = function(){
								$scope.clientesEncontrados=[];
								
								$http.get("/search/cliente/"+$scope.busca).success(function(data){
									console.log("Clientes Encontrados ",data);
									
//									$scope.clientesEncontrados=data;
									for (var i=0; i < data.length; i++){
										$scope.clientesEncontrados.push(data[i].nombre);
										console.log("ENcontrados ", $scope.clientesEncontrados);
									}
									
									$('#buscaClient').typeahead({
										
									    source: $scope.clientesEncontrados,

									    updater:function (item) {
									    	$http.get("/search/cliente/"+item).success(function(data){
									    		$scope.servicio.cliente=data[ind];	
									    	});
									    	var ind=$scope.clientesEncontrados.indexOf(item);
									    	console.log("Datos", ind);
									        return item;
									    }
									});
									$('#buscaClient').data('typeahead').source=$scope.clientesEncontrados;
							});
							}
							$scope.btnhide=true;
							$scope.buscar2 = function(){
								$scope.clientesEncontrados=[];
								
								$http.get("/search/cliente/"+$scope.busca).success(function(data){
									$scope.clientes=data;
									console.log("Clientes Encontrados ",data);
									if(data.length){
										$scope.btnhide=false;
									}else{$scope.btnhide=true;}
//								
							});
							}
							
							$scope.setClient = function(data){
								console.log("se selecciono ",data);
								$scope.busca = data.nombre;
								$scope.servicio.cliente=data;
							}
							$scope.findCliente = function() {
								if($scope.servicio.cliente.nombre.length>3){
									$scope.clientesEncontrados=[];
								$http.get("/search/cliente/"+$scope.servicio.cliente.nombre).success(function(data){
//									$scope.clientesEncontrados=data;
									
									console.log("Clientes Encontrados",data);
									$('#searchCliente').typeahead({

									    source: $scope.clientesEncontrados,

									    updater:function (item) {
									    	var ind=$scope.clientesEncontrados.indexOf(item);

									    	$http.get("/search/filtra/"+item+"/"+$scope.tipos[ind]).success(function(data){
									    		$scope.listaServicios=data;
									    		console.log("Datos Cliente", $scope.listaServicios);
//									    		$scope.busca="";
									    	});
									        return item;
									    }
									});
									$('#searchCliente').data('typeahead').source=$scope.clientesEncontrados;
								});
								}
							}
							$scope.mayusculas=function(value){
								value=value.toUpperCase();
								return value;
							}
							$scope.siguienteBtn=function(){
								$scope.showTab('auto');
								window.scrollTo(0, 0);
							}
							
							$scope.SetSerie=function(serie){
								$http.get("/servicio/AutoSerie/"+serie).success(function(data){
									if(data.numeroSerie){
									$scope.servicio.auto=data;
								}
								});
								
								
							}
							
							$scope.$watch('search',function(){
								
//									$scope.servicio.cliente=[];
									$scope.buscaSerie();
								
							},true);
							
							$scope.buscaSerie = function(){
								$scope.seriesEncontrados=[];
								
								$http.get("/search/auto/"+$scope.search).success(function(data){
									console.log("Series Encontrados ",data);
									
									for (var i=0; i < data.length; i++){
										$scope.seriesEncontrados.push(data[i].numeroSerie);
										console.log("Encontrados ", $scope.seriesEncontrados);
									}
									
									$('#buscaSerie').typeahead({
										
									    source: $scope.seriesEncontrados,

									    updater:function (item) {
									    	$http.get("/servicio/findCar/"+item).success(function(data){
									    		console.log("Con esto lleno todo", data);
									    		$scope.servicio.auto=data;
									    		$scope.servicio.auto.modelo=$scope.servicio.auto.modelo*1;
									    		$scope.searchPlaca=data.placas
									    	});
									    	
									        return item;
									    }
									});
									$('#buscaSerie').data('typeahead').source=$scope.seriesEncontrados;
							});
							}
							
							
							$scope.$watch('searchPlaca',function(){
								
//									$scope.servicio.cliente=[];
									$scope.buscaPlacas();
							},true);
							$scope.buscaPlacas = function(){
								$scope.placasEncontradas=[];
								
								$http.get("/search/autoPlaca/"+$scope.searchPlaca).success(function(data){
									console.log("Series Encontrados ",data);
									
									for (var i=0; i < data.length; i++){
										$scope.placasEncontradas.push(data[i].placas);
										console.log("Encontrados ", $scope.placasEncontradas);
									}
									
									$('#buscaPlaca').typeahead({
										
									    source: $scope.placasEncontradas,

									    updater:function (item) {
									    	$http.get("/servicio/findCarPlaca/"+item).success(function(data){
									    		console.log("Con esto lleno todo", data);
									    		$scope.servicio.auto=data;
									    		$scope.servicio.auto.modelo=$scope.servicio.auto.modelo*1;
									    		$scope.servicio.auto.placas=data.placas;
									    		$scope.search=data.numeroSerie;
									    	});
									    	
									        return item;
									    }
									});
									$('#buscaPlaca').data('typeahead').source=$scope.placasEncontradas;
							});
							}
							});
						} ]);
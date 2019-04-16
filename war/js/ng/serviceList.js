app.service('listService',['$http','$q','$location',function($http,$q,$location){
	this.getLista=function(){
		var d= $q.defer();
		$http.get("/servicio/status/activos").then(function(response){
//			console.log(response);
				d.resolve(response.data);
		},function(response){
//			console.log(response);
			if(response.status == 403){
				$location.path("/login");
			}
		});
		return d.promise;
	}
	this.getListaP=function(p){
		var d= $q.defer();
		$http.get("/servicio/getAll/"+p).then(function(response){
//			console.log(response);
				d.resolve(response.data);
		},function(response){
//			console.log(response);
			if(response.status == 403){
				$location.path("/login");
			}
		});
		return d.promise;
	}
	this.getPage=function(){
		var d= $q.defer();
		$http.get("servicio/numPaginas").then(function(response){
//			console.log(response);
				d.resolve(response.data);
		},function(response){
//			console.log(response);
			if(response.status == 403){
				$location.path("/login");
			}
		});
		return d.promise;
	}
	
}]);


app.controller('serviceListController',['$rootScope','$scope','$location','listService','sessionService','$http',function($rootScope,$scope,$location,listService,sessionService,$http){
	sessionService.isAuthenticated().then(function(){
	$scope.busca="";
	$rootScope.Tab=null;
//	document.title ="ACE";
	$scope.entontrados=[]
	$scope.$watch('busca',function(){
		if($scope.busca.length>1){
			$scope.buscar();
		}
	},true);
	
	$scope.getClase = function(number){
		var clase=null;
		
		if(number<=2){
			clase="success";
		}
		if(number>=3 && number<=5){
			clase="warning";
		}
		if(number>5){
			clase='danger';
		}
		return clase
	}
	$scope.classColor = function(status){
		
		if(status=="Cancelado"){
			clase="linkColorBk";
		}else{
			clase="linkColor";
		}
		return clase;
	}
	$scope.getClaseStatus = function(status,number){
		var clase=null;
		
		if(status=="Pagado"){
			clase="azul colorfnt";
			
		}else if(status=="Facturado"){
			clase="naranja colorfnt";
		}else if(status=="Cancelado"){
			clase="colorfntBk negro";
		}
		if(status!="Pagado" && status!="Facturado" && status!="Cancelado"){
			
			if(number<=2){
				clase="verde colorfnt";
			}
			if(number>=3 && number<=5){
				clase="amarillo colorfnt";
			}
			if(number>5){
				clase='rojo colorfnt';
			}
			return clase
		}
		
		return clase
	}
	Array.prototype.unique=function(a){
		  return function(){return this.filter(a)}}(function(a,b,c){return c.indexOf(a,b+1)<0
		});
	$scope.buscar=function(){
		var obj={ids:[],nombres:[],tipos:[]}
		$http.get("/search/general/"+$scope.busca).success(function(data){
			
			var valid = false;
			for(i in data.nombres){
				valid=false;
				for(e in obj.nombres){
					if(data.nombres[i] == obj.nombres[e]){
						valid = true
						
					}
				
				}
				if(valid!=true){
					obj.ids.push(data.ids[i]);
					obj.nombres.push(data.nombres[i]);
					obj.tipos.push(data.tipos[i]);
				}
			}
			console.log("Nuevo Objeto: ",obj);
			$scope.todos=obj;
			$scope.encontrados=obj.nombres.unique();
			$scope.ids=obj.ids;
			$scope.tipos=obj.tipos;
			
			$('#searchBox').typeahead({

			    source: $scope.encontrados,

			    updater:function (item) {
			    	var ind=$scope.encontrados.indexOf(item);
//			    	{params:{"ids":$scope.ids[ind]}}
//			    	$http.get("/search/filtra/"+item+"/"+$scope.tipos[ind]).success(function(data){
//			    		$scope.listaServicios=data;
//			    		$scope.busca="";
//			    	});
			    	send={
			    			nombres: [$scope.todos.nombres[ind]],
			    			ids: [$scope.todos.ids[ind]],
			    			tipos: [$scope.todos.tipos[ind]]
			    	}
			    	$scope.loading();
					$scope.msg="Se estan recopilando los Datos";
			    	$http.post("/search/filtra",send).success(function(data){
			    		$scope.listaServicios=data;
			    		$scope.busca="";
			    		setTimeout(function() {
						      $("#loadMe").modal("hide");
						    }, 1000);
			    	});
			        return item;
			    }
			});
			$('#searchBox').data('typeahead').source=$scope.encontrados;
		});
	}
	$scope.onSelect=function($item,$model,$label){
		alert("entra");
	}
	$scope.addAbierto=function(ser){
		var exist = false;
		for(i in $rootScope.abiertos)
			if ($rootScope.abiertos[i].id == ser.id){
				exist = true;
	}
		if(!exist){
			$rootScope.abiertos.push(ser);
		}
	}
	$rootScope.detallesView=false;
	
//	listService.getLista().then(function(data){
//			$scope.listaServicios=data;
//		},function(data){
//			console.log(data);
//			if(data.status == 403){
//				$location.path("/login");
//			}
//		});
	
	$scope.load=function(p){
		$scope.loading();
		$scope.msg="Se están obteniendo la lista de los servicios";
		listService.getListaP(p).then(function(data) {
			$scope.listaServicios=data;
			
			console.log("Lista del Servicio ", $scope.listaServicios)

			$scope.llenarPags();
			setTimeout(function() {
			      $("#loadMe").modal("hide");
			    }, 1000);
			
	});
	}
	$scope.loading = function(){
		$("#loadMe").modal({
		      backdrop: "static", //remove ability to close modal with click
		      keyboard: false, //remove option to close with keyboard
		      show: true //Display loader!
		    });
	}
	$scope.paginaActual=1;
	$scope.llenarPags=function(){
		var inicio=0;
		if($scope.paginaActual>5){
			inicio=$scope.paginaActual-5;
		}
		var fin = inicio+9;
		if(fin>$scope.maxPage){
			fin=$scope.maxPage;
		}
		$scope.paginas=[];
		for(var i = inicio; i< fin; i++){
			$scope.paginas.push(i+1);
		}
		for(var i = inicio; i<= fin; i++){
			$('#pagA'+i).removeClass("active");
			$('#pagB'+i).removeClass("active");
		}
		$('#pagA'+$scope.paginaActual).addClass("active");
		$('#pagB'+$scope.paginaActual).addClass("active");
	}
	
		$scope.cargarPagina=function(pag){
		if($scope.paginaActual!=pag){
			$scope.paginaActual=pag;
			$scope.load(pag);
		}
	}
		$scope.isFacturado= function(f){
			if(f!=null){
			if(f == false){
				return "No";
			}else if(f == true){
				return "Si";
			}
			}else{
				return "No";
			}
		}
		$scope.isFacturadoStyle= function(f){
				
			if(f == true){
				return "border: 1px solid orange;";
			}
			
		}
		
		listService.getPage().then(function(data){
			$scope.maxPage=data;
			$scope.llenarPags();
			
		});
		$scope.load(1);
	});	
	
}]);
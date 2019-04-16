app.service('sideBarService', [ '$http', '$q', function($http, $q) {
	this.getServicios = function() {
		$http.get("/servicio/getServiciosHoy").then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;

	}
} ]);

app.controller('sideBarController', [ '$scope', 'sideBarService', '$rootScope',
		'$location', '$timeout',
		function($scope, sideBarService, $rootScope, $location, $timeout) {

			$scope.serviciosActivos = $rootScope.serviciosHoy;
			$scope.goToView = function(id) {
				$location.path("/servicio/view/" + id);
			}
			$scope.delOpen=function(ind){
				$rootScope.abiertos.splice(ind,1);
			}

			$scope.$watch('actual', function() {
				if($rootScope.actual){
				if ($rootScope.actual.servicio.fechaInicio) {
					var hoy = new Date();
					var inicio = new Date($rootScope.actual.servicio.fechaInicio)
					var dif = hoy - inicio;
//					alert(dif);
					$scope.counter = dif;
//					$scope.dias= Math.floor($scope.counter / (1000 * 60 * 60 * 24));
//					$scope.horas= Math.floor($scope.counter / (1000 * 60 * 60))%24; 
//					$scope.minutos= Math.floor($scope.counter / (1000 * 60)) % (60); 
//					$scope.segundos= Math.floor($scope.counter / (1000))%(60); 
				}
				}	
			}, true);

			$scope.$watch('counter', function() {
//				if ($rootScope.actual.servicio.metadata.fechaInicio) {
//					var hoy = new Date();
//					var inicio = new Date($rootScope.actual.servicio.metadata.fechaInicio)
//					var dif = hoy - inicio;
//					alert(dif);
//					$scope.counter = dif;
					$scope.dias= Math.floor($scope.counter / (1000 * 60 * 60 * 24));
					$scope.horas= Math.floor($scope.counter / (1000 * 60 * 60))%24; 
					$scope.minutos= Math.floor($scope.counter / (1000 * 60)) % (60); 
					$scope.segundos= Math.floor($scope.counter / (1000))%(60); 
					
					document.getElementById("semaforo").className = "alert-success semGreen";
					document.getElementById("semaforo2").className = "alert-success semGreen";
					if($scope.dias>=3 && $scope.dias<=5){
						document.getElementById("semaforo").className = "btn-warning";
						document.getElementById("semaforo2").className = "btn-warning";
						
					}
					if($scope.dias>5){
						document.getElementById("semaforo").className = "btn-danger";
						document.getElementById("semaforo2").className = "btn-danger";
					}
//				}

			}, true);
			
			$scope.onTimeout = function() {
				$scope.counter+=1000;
				mytimeout = $timeout($scope.onTimeout, 1000);
			}
			var mytimeout = $timeout($scope.onTimeout, 1000);

			$scope.stop = function() {
				$timeout.cancel(mytimeout);
			}
			
			$scope.getFecha= function(fecha){
				if(fecha instanceof Date){
				}else{
					fecha= new Date(fecha);
				}
				var day=fecha.getDay()+15;
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
		} ]);
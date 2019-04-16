var app = angular.module('app', [ 'ngRoute' ,'ui.bootstrap']);

app
		.config([
				'$routeProvider',
				'$httpProvider',
				function($routeProvider, $httpProvider) {
					$routeProvider.when('/servicio/create', {
						templateUrl : "pages/newService.html",
						controller : "newServiceController"
					});
					$routeProvider.when('/servicio/view/:id', {
						templateUrl : "pages/service.html",
						controller : "serviceController"
					});
					$routeProvider.when('/', {
						// templateUrl: "pages/mainpage.html",
						// controller: "mainPageController"
						redirectTo : '/listServicios'
					});
					$routeProvider.when('/listServicios', {
						templateUrl : "pages/serviceList.html",
						controller : "serviceListController"
					});

					$routeProvider.when('/login', {
						templateUrl : 'pages/login.html',
						controller : 'navigation'
					});
					
					$routeProvider.when('/registro', {
						templateUrl : 'pages/registro.html',
						controller : 'usuarioController'
					});

					$routeProvider.when('/changePass', {
						templateUrl : 'pages/pss.html',
						controller : 'chgPassController'
					});
					
					$routeProvider.otherwise({
						redirectTo : '/listServicios'
					});

				} ]);

app.run([ '$rootScope', '$http','$location', function($rootScope, $http,$location) {
	$rootScope.serviciosHoy = [];
	$rootScope.detallesView=false;
	$rootScope.abiertos = [];
	$http.get("/servicio/serviciosHoy").then(function(response) {
		$rootScope.serviciosHoy = response.data;
//		console.log(response.data);
	})
	$rootScope.cerrarSesion= function(){
		if($rootScope.authenticated){
			$http.get("/cerrarSession").then(function(response){
				$rootScope.authenticated=false;
				$location.path("/login");
			});
		}
	}

} ]);

app.controller('navigation',['sessionService','$rootScope', '$scope', '$http', '$location',

function(sessionService,$rootScope, $scope, $http, $location) {
	
//	var authenticate = function(credentials, callback) {
//
//		var headers = credentials ? {
//			authorization : "Basic "
//					+ btoa(credentials.username + ":" + credentials.password)
//		} : {};
//
//		$http.get('user', {
//			headers : headers
//		}).success(function(data) {
//			if (data.name) {
//				$rootScope.authenticated = true;
//			} else {
//				$rootScope.authenticated = false;
//			}
//			callback && callback();
//		}).error(function() {
//			$rootScope.authenticated = false;
//			callback && callback();
//		});
//
//	}

//	authenticate();
	$scope.credentials = {};
	$scope.login = function() {
		sessionService.authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				$location.path("/");
				$scope.error = false;
			} else {
				$location.path("/login");
				$scope.error = true;
			}
		});
	};
}]);

app.service('sessionService',['$rootScope','$http','$location','$q',function($rootScope,$http,$location,$q){
	this.authenticate = function(credentials, callback) {

		var headers1 = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":" + credentials.password)
		} : {};
		$http.get('user',{headers:headers1}).success(function(data) {
			if (data.principal.usuario) {
				$rootScope.userNameCurr=data.principal.usuario;
				$rootScope.authenticated = true;
				$rootScope.tipo = data.principal.tipo;
				$location.path("/listServicios");
			} else {
				$rootScope.authenticated = false;
			}
		}).error(function(data) {
			//$rootScope.authenticated = false;
				$location.path("/login");
		});

	}
	this.isAuthenticated=function(){
		
		var d = $q.defer();
		$http.get("currentSession").success(function(data){
			$rootScope.authenticated=true;
			d.resolve(data);
		}).error(function(data) {
			//$rootScope.authenticated = false;
			$location.path("/login");
		});
		return d.promise;
	}
}]);

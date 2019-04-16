app.service('fileService', function () {
    var file = {};
    var fileService = {};

    fileService.getFile = function (name) {
        return file[name];
    };

    fileService.setFile = function (newFile, index, name) {
        if (index === 0 && file[name] === undefined)
          file[name] = [];
        file[name][index] = newFile;
    };

    return fileService;
})
app.directive('multipleFileModel', function (fileService) {
    return{
    	restrict: 'EA',
		scope: {
			setFileData: "&"
		},
		link:function (scope, element, attrs) {
            element.bind('change', function () {
            var index;
            var index_file = 0;
            for (index = 0; index < element[0].files.length; index++) {
              fileService.setFile(element[0].files[index], index_file, attrs.multipleFileModel);
              index_file++;
            }
            index_file = 0;
        });
    }
    }
});

// app.directive('multipleFileModel', function (fileService) {
// return function (scope, element, attrs) {
// element.bind('change', function () {
// var index;
// var index_file = 0;
// for (index = 0; index < element[0].files.length; index++) {
// fileService.setFile(element[0].files[index], index_file,
// attrs.multipleFileModel);
// index_file++;
// }
// index_file = 0;
// });
// }
// });


app.directive("fileModel",function() {
	return {
		restrict: 'EA',
		scope: {
			setFileData: "&"
		},
		link: function(scope, ele, attrs) {
			ele.on('change', function() {
				scope.$apply(function() {
					var val = ele[0].files[0];
					scope.setFileData({ value: val });
				});
			});
		}
	}
})

// myApp.directive('fileModel', [ '$parse', function($parse) {
// return {
// restrict : 'A',
// link : function(scope, element, attrs) {
// var model = $parse(attrs.fileModel);
// var modelSetter = model.assign;
//
// element.bind('change', function() {
// scope.$apply(function() {
// modelSetter(scope, element[0].files[0]);
// });
// });
// }
// };
// } ]);

app.service('fileUpload', ['$http','$q',
    function($http,$q) {

      this.uploadFileToUrl = function(data,url) {
    	  var d= $q.defer();
        var fd = new FormData();
        var indice= data.length;
        for(var i = 0; i<indice; i++){
        	fd.append('files'+i, data[i]);
        }
// fd.append('files', data);
        fd.append('length',indice);
        console.log(fd.get('files'+(indice-1)));
        
    
        
        alert("se va a enviar")
        $http.post(url, fd, {
            withCredentials: false,
            headers: {
              'Content-Type': undefined            },
//            transformRequest: angular.identity,
//            params: {
//              fd
//            },
//            responseType: "arraybuffer"
          })
          .success(function(response, status, headers, config) {
            console.log(response);
            d.resolve(response.data);
          })
          .error(function(error, status, headers, config) {
            console.log(error);
          });
        return d.promise;
      }
    
      this.uploadFileToUrl = function(data,url,id) {
    	  var d= $q.defer();
        var fd = new FormData();
        var indice= data.length;
        for(var i = 0; i<indice; i++){
        	fd.append('files'+i, data[i]);
        }
// fd.append('files', data);
        fd.append('length',indice);
//        fd.append('idEvento',id);
//        alert(url);
       //autosolver $http.post('https://facturacion.tikal.mx/uf.php', fd, {
        	   $http.post('https://facturacion.tikal.mx/uf.php', fd, {
//        $http.post(url, fd, {
//            withCredentials: true,
            headers: {
              'Content-Type': undefined,
              'Access-Control-Allow-Methods':"POST",
              'Access-Control-Allow-Origin': 'http://127.0.0.1:8888',
            //  'Access-Control-Allow-Origin': 'http://1-dot-autosolversystem.appspot.com'
            },	
            transformRequest: angular.identity,
            params: {
              fd
            },
//            responseType: "arraybuffer"
          })
          .success(function(response, status, headers, config) {
            console.log(response);
            var send= {idEvento:id,
            		images:response
            		};
            console.log(send);
            $http.post('/eventos/appendImages/'+id,send).success(function(response){
            	console.log(response);
            	d.resolve(response);
            })
            
          })
          .error(function(error, status, headers, config) {
            console.log(error);
          });
        return d.promise;
      }
  }]);

// myApp.service('fileUpload', [ '$http', function($http) {
// this.uploadFileToUrl = function(file, uploadUrl) {
// var fd = new FormData();
// fd.append('file', file);
//
// $http.post(uploadUrl, fd
// )
//
// .success(function() {
// })
//
// .error(function() {
// });
// }
// } ]);

app.controller('myCtrl', [ '$scope', 'fileUpload','$http','$sce','fileService',
		function($scope, fileUpload,$http,$sce,fileService) {
	$scope.uri={
			url:""
	};
	$scope.urlpost = $sce.trustAsResourceUrl($scope.uri.url);
    $http.get("/servicio/getUpldUrl").then(function(response){
    	console.log(response.data.url);
    	$scope.uri=response.data;
    });
    $scope.indice=0;
    $scope.sendImages = function() {
		var total = $scope.images.length;
		var indice=$scope.indice;
		if (total > indice) {
			var file = $scope.images;
			fileUpload.uploadFileToUrl(file, $scope.uri.url).then(function(data){
				var evidencia={
					imagen:data
				}
				$scope.evento.push(evidencia);
				$scope.indice++;
				// recursivo
			});
		}
	}
    $scope.addEvento = function() {
// var e = $scope.evento;
// $scope.eventos.push(e);
// $scope.evento = {
// evidencia:[]
// };
		$scope.images= fileService.getFile("b_pics");
		$scope.indice=0;
		$scope.sendImages();
		
	}
			$scope.uploadFile = function(url) {
				var file = $scope.fileToUpload;

// console.log('file is ');
// console.dir(file);

				var uploadUrl = "/servicio/uploadFile";
				
// fileUpload.uploadFileToUrl(file, uploadUrl);
				fileUpload.uploadFileToUrl(file,url);
			};
		} ]);
var app = angular.module('unexmongo', [ "ngResource" ]);

app.controller('DatosController', [ '$scope', '$http',
                                     
	function($scope, $http) {
		$scope.getDatosDia = function() {
			$http.get('/datos').success(function(data) {
				$scope.datos = data;
			});
		}
		$scope.generarCSV = function() {
			$scope.msg = 'Se está creando el CSV. Cuando el procesa acabe se cambiará este mensaje';
			$http.post('/datos', 
				{
				    coleccion :'rscat',
				    annoDia : $scope.annoDia,
				    datoAParsear : $scope.datoAParsear,
				    totalDatosDias : 0
				}
			).success(function(data) {
				$scope.msg = 'CSV Creado en la siguiente ruta d:/varios/csv';
				$scope.getDatosDia();
			}).error(function(data) {
				$scope.msg = 'Se ha producido un error al crear el CSV';
			});
		}
} ]);
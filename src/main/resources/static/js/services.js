var app = angular.module('unexmongo', [ "ngResource" ]);

app.controller('DatosController', [ '$scope', '$http',
                                     
	function($scope, $http) {
		$scope.getDatosDia = function() {
			$scope.msg = 'Rellene los datos para generar el CSV.';
			$http.get('/datos').success(function(data) {
				$scope.datos = data;
			});
		}
		$scope.generarCSV = function() {
			$("#alertinfo").removeClass('alert-success').removeClass('alert-danger').addClass('alert-info');
			var paramEnvio = "";
			for(i=0;i<$scope.datoAParsearCombo.length;i++){
				if (paramEnvio == ""){
					paramEnvio= $scope.datoAParsearCombo[i];
				}else{
					paramEnvio= paramEnvio + ',' + $scope.datoAParsearCombo[i];
				}
			}
			$scope.msg = 'Se está creando el CSV.';
			$http.post('/datos', 
				{
				    coleccion :'rscat',
				    annoDiaIni : $scope.annoDiaIni,
				    annoDiaFin : $scope.annoDiaFin,
				    //datoAParsear : $scope.datoAParsearCombo,
				    datoAParsear : paramEnvio,
				    totalDatosDias : 0
				}
			).success(function(data) {
				$("#alertinfo").removeClass('alert-info').addClass('alert-success');
				$scope.msg = 'CSV Creado en la siguiente ruta d:/varios/csv';
				//$scope.getDatosDia();
			}).error(function(data) {
				$("#alertinfo").removeClass('alert-info').addClass('alert-danger');
				$scope.msg = 'Se ha producido un error al crear el CSV.';
			});
		}
} ]);
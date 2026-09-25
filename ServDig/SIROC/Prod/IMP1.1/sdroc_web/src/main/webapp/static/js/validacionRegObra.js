//var tipoObra = 1;
//var tipoPatron = 1;
//
///**
// * Validacion de datos para registro de obra
// */
//$(document).ready(
//		function() {
//
//			$("#btnValidarEjemplos").click(function(e) {
//				validarRegPropietario();
//			});
//
//			/**
//			 * Valida la informacion requerida para el propietario
//			 */
//			function validarRegPropietario() {
//				validarPeriodo();
//				validarMonto();
//				validarSuperficie();
//				validarTipoObra();
//			}
//
//			function validarPeriodo() { 
//				var resultadol = true;
//
//				if ($("#fecInicio").val() == '') {
//					$("#msgeFecIni").removeClass("hidden");
//					$("#fecInicio").css('border-color', '#a94442');
//					resultadol = false;
//
//				} else {
//					$("#msgeFecIni").addClass("hidden");
//					$("#fecInicio").css('border-color', '#ccc');
//				}
//
//				if ($("#fecTermino").val() == '') {
//					$("#msgeFecFin").removeClass("hidden");
//					$("#fecTermino").css('border-color', '#a94442');
//					resultadol = false;
//				} else {
//					$("#msgeFecFin").addClass("hidden");
//					$("#fecTermino").css('border-color', '#ccc');
//				}
//				
//				console.log('periodo : ' + resultadol);
//
//				return resultadol;
//			}
//			
//			function validarMonto() {
//				var resultadol = true;
//				if ($("#txtMonto").val() == '') {
//					$("#msgeMonto").removeClass("hidden");
//					$("#txtMonto").css('border-color', '#a94442');
//					resultadol = false;
//				} else {
//					$("#msgeMonto").addClass("hidden");
//					$("#txtMonto").css('border-color', '#ccc');
//				}
//				
//				console.log('monto : ' + resultadol);
//				return resultadol;
//
//			}
//			function validarSuperficie() {
//				var resultadol = true;
//				if (/00/i.test($("#txtSuperficie").val())) {
//					$("#msgeSuperficie").removeClass("hidden");
//					$("#txtSuperficie").css('border-color', '#a94442');
//					resultadol = false;
//				} else {
//					$("#msgeSuperficie").addClass("hidden");
//					$("#txtSuperficie").css('border-color', '#ccc');
//				}
//				
//				console.log('superficie : ' + resultadol);
//				return resultadol;
//
//			}
//			function validarTipoObra() {
//				var resultadol = true;
//				if ($("#selTipoObra").val() == ''
//						|| $("#selTipoObra").val() == 'Seleccione ...') {
//					$("#msgeTipoObra").removeClass("hidden");
//					$("#selTipoObra").css('border-color', '#a94442');
//					resultadol = false;
//				} else {
//					$("#msgeTipoObra").addClass("hidden");
//					$("#selTipoObra").css('border-color', '#ccc');
//				}
//				console.log('tipo obra : ' + resultadol);
//				return resultadol;
//
//			}
//			
//
//		});

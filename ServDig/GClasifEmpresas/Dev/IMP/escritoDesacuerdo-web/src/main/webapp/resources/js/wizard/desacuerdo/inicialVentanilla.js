/**
 * 
 */
var escritoVentanilla = {
	contextApp: '/${mvn.web.app.root}',
	init: function() {
		escritoVentanilla.initValidator();
		
		$("#regresarEscrito").on("click", function() {
			$.blockUI();
			location.href = escritoVentanilla.contextApp + "/escrito/";
		});
		
		$("#iniciarEscrito").on("click", function() {
			var $fomulario = $("#formBusquedaRP");
			if($fomulario.valid()) {
			$fomulario.attr("action",escritoVentanilla.contextApp + "/escrito/wizard/valida");
				$fomulario.submit();
			}
		})
	},
	initValidator: function() {
		
		var longitudRP = "El RP debe ser de 10 posiciones";
		
		$("#formBusquedaRP").validate({
			errorClass: "errorDocs",
			errorElement: "span",
			rules: { 
				"nrp": {
					required:true,
					minlength: 10,
					maxlength: 10
				}
			},
			messages: {
				"nrp": {minlength: longitudRP, maxlength: longitudRP}
			}
		});
	},
}

$(document).ready(escritoVentanilla.init);
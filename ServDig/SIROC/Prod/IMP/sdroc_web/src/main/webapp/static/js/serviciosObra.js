/**
 * 
 */
var serviciosObraCtrl= {
	defaultsPeticiones: {
		type : "POST",
		contentType : "application/json",
		beforeSend: $.blockUI,
		async : false,
		cache : false,
		complete: $.unblockUI
	},
	sendRequest: function(url, numeroRegistroObra, funcionSuccess) {
		$.ajax($.extend({}, serviciosObraCtrl.defaultsPeticiones,{
			url : url,
			data : numeroRegistroObra,
			success : funcionSuccess,
		}));
	},
	validarObra: function(numeroRegistroObra) {
		var boolean;

		$.ajax($.extend({}, serviciosObraCtrl.defaultsPeticiones,{
			url : "/sdroc_web/validaRegistroObra",
			data : numeroRegistroObra,
			success : function(response) {
				boolean = response;
			}
		}));
		
		return boolean;
	},
	sensaObra: function(numeroRegistroObra) {
		
		serviciosObraCtrl.sendRequest("/sdroc_web/sensaObra", numeroRegistroObra,  function(respuesta) {
			if (respuesta == 2) {
				clearInterval(intervalo);
				$('#msgsValidacionObraReinicio').removeClass('hidden');
				crearDialogo("#msgsValidacionObraReinicio", {
					"Aceptar" : function() {
						serviciosObraCtrl.reiniciarObra(numeroRegistroObra);
						ejecutaIntervalo(numeroRegistroObra);
						$(this).dialog("close");
					},
					"Cancelar" : function() {
						serviciosObraCtrl.liberarObra(numeroRegistroObra);
						$(this).dialog("close")
					}
				}, "Registro Obra", "340px");
			} else if (respuesta == 0) {
				serviciosObraCtrl.liberarObra(numeroRegistroObra);
				clearInterval(intervalo);
			}
		});
	},
	reiniciarObra: function(numeroRegistroObra) {
		serviciosObraCtrl.sendRequest( "/sdroc_web/reiniciarRegistroObra", numeroRegistroObra);
	},
	liberarObra: function(numeroRegistroObra) {
		serviciosObraCtrl.sendRequest("/sdroc_web/liberaRegistroObra", numeroRegistroObra);
	}
};
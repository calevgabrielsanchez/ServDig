/*
 * JS Validation Message
 */

var alertValidationMessage = {
	waitingDiv : '',

	init : function() {
		$('.alertValidationMessage').bind('init-validation-message', function() {
			var _alertValidationMessage = $(this);
			var _url = _alertValidationMessage.attr('validation-url');
			
			if (_url != null && _url != '' && _url != undefined) {
				alertValidationMessage.prepararRequest(_url, null, true, function(response, callback) {
					var mensaje = '';
					if (response.mensajeError == undefined || response.mensajeError == null) {
						if (_alertValidationMessage != null) {
							
							for(var mensajeParticular in response) {
							    if(mensajeParticular != 'mensajeExito' 
							    		&& mensajeParticular != 'procesaDomicilios' && mensajeParticular != 'procesaMediosContacto' ) {
							    	mensaje += '<div class="alert alert-warning">' + response[mensajeParticular] + '</div>';
							    }							    
							    if(mensajeParticular == 'procesaMediosContacto') {
							    	//Procesar despliegue de Pantalla sólo con exito.
							    	procesarMediosContactoExistentes();
							    }
							    if(mensajeParticular == 'procesaDomicilios') {
							    	//Procesar despliegue de Pantalla sólo con exito.
							    	procesarDomiciliosExistentes();;
							    }
							}							
							//if (mensaje == '') {
							//	mensaje += '<div class="alert alert-success">' + response.mensajeExito + '</div>';								
							//}
								
							if (mensaje != '') {
								//Desplegar solo mensaje de Error
								_alertValidationMessage.html(mensaje);	
							}
													
						}
					} else {
						var mensajeParticular;
						if (response.mensajeError == "") {
							mensajeParticular = "Ocurrio un error con el servidor.";
						} else {
							mensajeParticular = response.mensajeError;
						}

						mensaje += '<div class="alert alert-danger">' + mensajeParticular + '</div>';
						_alertValidationMessage.html(mensaje);
					}
				});
				
			}
		}).bind('load-validation-message', function() {

		}).trigger('init-validation-message');
	},

	prepararRequest : function(sSource, data, async, callback) {
		var request = $.ajax({
			url : sSource,
			async : async,
			type : "POST",
			data : data ? JSON.stringify(data) : null,
			dataType : "json",
			contentType : "application/json; charset=utf-8"
		});

		request.done(callback);
		request.fail(callback);
	}
};

function procesarMediosContactoExistentes() {
	var idTipoValidacion = $('#hdnIdTipoValidacionMedios').val();
	var idPersona = parent.AtributosPersonaCtrl.personaPortal.idPersona;
	//var idTipoPersona = parent.AtributosPersonaCtrl.personaPortal.idTipoPersona;
		
	var urlDom = "/gestionMediosContacto-web/medios/contacto/mostrarMediosContactoExistentes/"
		+idTipoValidacion+"/"+idPersona;
	
	$.post(urlDom, null, function(data) {
		$('#divSeccionMediosContactoCommon').html(data);
	});
}

function procesarDomiciliosExistentes() {
	var idTipoValidacion = $('#hdnIdTipoValidacionDomicilio').val();
	var idPersona = parent.AtributosPersonaCtrl.personaPortal.idPersona;
	//var idTipoPersona = parent.AtributosPersonaCtrl.personaPortal.idTipoPersona;
		
	var urlDom = "/gestionDomicilios-web/domicilio/nacional/ubicar/mostrarDomiciliosExistentes/"
		+idTipoValidacion+"/"+idPersona;
	
	$.post(urlDom, null, function(data) {
		$('#divSeccionDomiciliosCommon').html(data);
	});
}


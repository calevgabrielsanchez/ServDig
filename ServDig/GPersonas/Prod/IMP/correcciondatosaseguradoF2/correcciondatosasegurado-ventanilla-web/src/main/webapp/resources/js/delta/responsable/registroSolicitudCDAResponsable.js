$(document).ready(function() {
	
	document.charset = 'UTF-8';

	/*Inicializo el calendario de la fecha de nacimiento*/
	$('#fechaNacimiento').datepicker({
		changeYear:true,
		changeMonth:true,
		maxDate: new Date(),
		minDate: new Date(1900, 1, -1, 1),
		language: 'es',
		yearRange : ''+(new Date().getFullYear()-1900)+':+10'
	});
	
	/*
	 * Funcion que convierte en MAYUSCULAS el valor 
	 * de campo CURP al perder el foco 
	 */
	$('input[type="text"]#registroCurp').blur(function() {
		this.value = this.value.toUpperCase();
	});
	
	$('input[type="text"]#nombreInput').blur(function() {
		this.value = this.value.toUpperCase();
	});
	
	$('input[type="text"]#primerApellidoInput').blur(function() {
		this.value = this.value.toUpperCase();
	});
	
	$('input[type="text"]#segundoApellidoInput').blur(function() {
		this.value = this.value.toUpperCase();
	});
	
	$('#continuar').click(function(){
		fnHideErrores("form#registroAseguradoDomicilioForm");
		var curp = $('#registroCurp').val();
		var nom= $('#nombreInput').val();
		var pApellido = $('#primerApellidoInput').val();
		var sApellido = $('#segundoApellidoInput').val();
		var fechaNac = $('#fechaNacimiento').val();
		var lugarNacClave = $('#lugarNacimiento\\.clave').val();
		var lugarNacNombre = $('#lugarNacimiento\\.clave option:selected').text();
		var sexId =  $('#sexo\\.idSexo').val();
		var sexDescripcion;
		if (sexId == "1"){
			sexDescripcion = "HOMBRE";
		} else if (sexId == "2") {
			sexDescripcion = "MUJER";
		} else {
			sexDescripcion = "NO BINARIO";
		}
		
		
		$.blockUI();
		$.ajax({
			url : contextpath+'/wizard/correccionDatosAsegurado/registroVentanilla/buscarPersonaRenapo',
			type : 'post',
			async : false,
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			data : JSON
					.stringify({
						curp:curp,
						nombre: nom,
						primerApellido: pApellido,
						segundoApellido: sApellido,
						fechaNacimiento: fechaNac,
						lugarNacimiento:{
							clave: lugarNacClave,
							nombre: lugarNacNombre
						},
						sexo: {
							idSexo: sexId,
							descripcion : sexDescripcion
						}
					}),		
			error : function(error) {
				$.unblockUI();
				tratamientoErrores(error, "form#registroAseguradoDomicilioForm");
                                
			},
			success : function(response, data) {
				var url = "/wizard/correccionDatosAsegurado/obtenerInformacionRenapo";
				if(response.vista != null){
					url = response.vista;
				}
				window.location.href = contextpath + url;
			},	
		});
	});
});

function tratamientoErrores(data, contenedor){
	switch (data.status){
		case 412:
			//Existen errores de captura
		  fnHideErrores(contenedor);
		  fnProcesarErroresDeCaptura( data, contenedor);
		  break;
		case 404:
			//no se encontraron datos en renapo
			var mensaje = jQuery.parseJSON(data.responseText).noResult;
			dialogoSinResultados(mensaje);			
			break;
		case 500:
			var result = jQuery.parseJSON(data.responseText);
			mensageConfirmacion(result);
			break;
	}
};

function mensageConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.append(mensaje.exception);
	if(Object.keys(mensaje).length > 1){
		$ventana.append('<br><br>');
		$.each(mensaje, function(key, value) {
		    if(key != 'exception' && key != 'vista'){
		    	$ventana.append(value);
		    	$ventana.append('<br>');
		    }
		});
	}
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		modal: true,
		width: 400,
		buttons: {
			"Aceptar": function() {
				if(Object.keys(mensaje).length > 1) {
					var redirectionUrl = mensaje.hasOwnProperty('vista') ? mensaje['vista'] : '/wizard/correccionDatosAsegurado/obtenerInformacionRenapo';
					window.location.href = contextpath + redirectionUrl;
				} else {
					cierraDialogo($(this));
				}
			}
		}
	
		});
	
	$ventana.dialog('open');
};

function dialogoSinResultados(mensaje){
	$ventana = $('<div></div');
	
	$ventana.append(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		modal: true,
		height: 280,
		width: 400,
		buttons: {
			"Cancelar": function() {
				cierraDialogo($(this));
			},
			"Aceptar": function() {
				window.location.href = contextpath + "/wizard/correccionDatosAsegurado/obtenerInformacionRenapo";
			}
		}
	});
	$ventana.dialog('open');
};

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
};	

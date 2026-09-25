var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {

	// Se incializa el blockUI para las peticiones AJAX
	//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	$('#siguientePaso').click(function() {
		siguientePaso();
	});

	$('#previoPaso').click(function() {
		previoPaso();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#guardarCerrarTramite').click(function() {
		guardarCerrarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
		 	"Cancelar": function() {
		 		$( this ).dialog( "close" );
		 	},
		 	"Aceptar": function() {
				cancelarTramite();
		 	}
		 }
	 });

	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				cerrarWizard();
		 	}
		 }
	 });
	
	//validar si se habilita RPC (funcion en modificacionSRT.js)
	validarHabilitarRPC();
	
	
	
	//function auditoria
	
	 if(origenApp == origenVENTANILLA){
		 
			$('input#auditoria').click(function(){
				//console.log("Activa la casilla");
				$("#fechaEfecto").val("");
				var valor = $('input#auditoria').filter(":checked").val();
				  if(valor == undefined){
					  console.log("Desactivado");
					  $("#fechaEfecto").datepicker("destroy");
					  fechaEfectoDatePicker= $("#fechaEfecto").datepicker({
							changeMonth : true,
							changeYear : true,
							minDate : -minDate,
							maxDate:new Date(),
							onClose : function(dateText, inst) {
								if (dateText != "") {
									runEffectHideFechaEfectoInvalidaMsg();
									$("#fechaEfecto").attr("style", "width: 100px;");
								}
							}

						});

				  }else{
					 // console.log("Activado");
					  var fecha = new Date();
						anioFin = fecha.getFullYear();
						rango = "1900:"+anioFin;
						$("#fechaEfecto").datepicker("destroy");
					   fechaEfectoDatePickerVentanilla=$("#fechaEfecto").datepicker({
							changeMonth : true,
							changeYear : true,
							yearRange : rango,
					    	maxDate:new Date(),
					    	
							onClose : function(dateText, inst) {
								if (dateText != "") {
									runEffectHideFechaEfectoInvalidaMsg();
									$("#fechaEfecto").attr("style", "width: 100px;");
								}
							}

						});
				  }
			});
	 }
	
	 setEventosChecarError("formularioPatron",evaluarClasificacion);
});


function siguientePaso() {
	var errors = evaluarClasificacion();
	var sSource = context_path + '/wizard/tramite/registro/patronal/actualizar/solicitud/clasificacion';
	
	if (errors == "") {
		$.blockUI();
		generarObjetoClasificacion();
		prepararRequest(sSource, clasificacion, true, actualizarSolicitud);
	} else {
		construirDialogoErrores(errors);
	}
}

function previoPaso() {
	var sSource = context_path + '/wizard/tramite/registro/patronal/actualizar/solicitud/clasificacion';
	
	$.blockUI();
	generarObjetoClasificacion();
	prepararRequest(sSource, clasificacion, true, pasoPrevioSolicitud);
}

function pasoPrevioSolicitud() {
	var urlAction = context_path + '/wizard/tramite/registro/patronal/pasoPrevio/solicitud/' + idSolicitud;

	document.getElementById('datosClasificacionForm').action = urlAction;
	document.getElementById('datosClasificacionForm').submit();
}

function actualizarSolicitud(response) {
	
	if(response.mensajeError!=null && response.mensajeError!=undefined &&response.mensajeError!=""){
		$.unblockUI();
		construirDialogoMensajes("Error", response.mensajeError, true, undefined, 200, 500);

	}else{
		var urlAction = context_path + '/wizard/tramite/registro/patronal/captura/clasificacion/complemento';
		
		document.getElementById('datosClasificacionForm').action = urlAction;
		document.getElementById('datosClasificacionForm').submit();
	}
}

function guardarTramite() {
	construirDialogoConfirmar('Guardar', guardarSolicitudTramite);
}

function guardarSolicitudTramite() {
	$.blockUI();
	var sSource = context_path + '/wizard/tramite/registro/patronal/actualizar/solicitud/clasificacion';

	generarObjetoClasificacion();
	prepararRequest(sSource, clasificacion, true, procesarRespuestaServer);
}

function guardarCerrarTramite() {
	construirDialogoConfirmar('Guardar antes de cerrar', guardarCerrarSolicitud);
}

function guardarCerrarSolicitud(){
	$.blockUI();
	var sSource = context_path + '/wizard/tramite/registro/patronal/actualizar/solicitud/clasificacion';

	generarObjetoClasificacion();
	prepararRequest(sSource, clasificacion, true, callbackGuardarCerrarSolicitud);
}

function callbackGuardarCerrarSolicitud(response) {
	$.unblockUI();
	procesarRespuestaServer(response, cerrarWizard);
}

function cancelarTramite() {
	var url = context_path + '/wizard/tramite/registro/patronal/cancelar/solicitud/'
		+ idSolicitud;

	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardAltaPatronalCtrl.cerrar();
}

function setEventosChecarError(idFormulario,functionPintarErrores) {
	$("#"+idFormulario+" :text").change(function() {
		verificarPersistenciaDeError(this, false,functionPintarErrores);
	});
	
	$("#"+idFormulario+" select").change(function() {
		verificarPersistenciaDeError(this, true,functionPintarErrores);
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		verificarPersistenciaDeError(this, false,functionPintarErrores);
	});
}

function setEventosChecarError(idFormulario,functionPintarErrores) {
	$("#"+idFormulario+" :text").change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" :radio").change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" select").change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		functionPintarErrores();
	});
}
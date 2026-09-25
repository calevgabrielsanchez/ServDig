var dialogoConfirmarCancelar;
var dialogoConfirmar;
var domicilioCentrotrabajo;
var campoToFocusOn;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$(":input[type=text]").addClass('input-sm');
	
	parseTelefonos();
	
	$('#siguientePaso').click(function() {
		siguientePaso();
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
	
	setEventosChecarError("domicilioCentroTrabajoForm",evaluarCentroTrabajo)
});

function parseTelefonos(){
	var arregloTelefonoPrincipal = telefonoPrincipalCompleto.split("|");
	
	if(arregloTelefonoPrincipal.length==3){
		var lada = telefonoPrincipalCompleto.split("|")[0];
		var numeroTelefono = telefonoPrincipalCompleto.split("|")[1];
		var extension = telefonoPrincipalCompleto.split("|")[2];
		$("#ctLada").val(lada);
		$("#ctTelefonoFijo").val(numeroTelefono);
		$("#ctExtension").val(extension);
		
	}else{
		//No se ha capturado nada
		$("#ctLada").val('');
		$("#ctTelefonoFijo").val('');
		$("#ctExtension").val('');
	}
	
	var arregloTelefonoSecundario = telefonoSecundarioCompleto.split("|");
	if(arregloTelefonoSecundario.length==3){
		var lada2 = telefonoSecundarioCompleto.split("|")[0];
		var numeroTelefono2 = telefonoSecundarioCompleto.split("|")[1];
		var extension2 = telefonoSecundarioCompleto.split("|")[2];
		$("#ctLada2").val(lada2);
		$("#ctTelefonoFijo2").val(numeroTelefono2);
		$("#ctExtension2").val(extension2);
	}else{
		$("#ctLada2").val('');
		$("#ctTelefonoFijo2").val('');
		$("#ctExtension2").val('');
	}
}

function siguientePaso() {
	var errors = evaluarCentroTrabajo();

	if (errors == "") {
		var urlAction = context_path + '/wizard/tramite/registro/patronal/captura/clasificacion';

		$.blockUI();
		asignarValoresCompletosDeTelefono();
		document.getElementById('domicilioCentroTrabajoForm').action = urlAction;
		document.getElementById('domicilioCentroTrabajoForm').submit();
	} else {
		construirDialogoErrores(errors);
	}
}

function guardarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar la solicitud?', guardarSolicitudTramite);
}

function guardarSolicitudTramite() {
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/domicilioCt/'
		+ idSolicitud;

	var domicilioCentroTrabajoForm = $("form#domicilioCentroTrabajoForm").toObject();
	domicilioCentroTrabajoForm.cntroTrabajo.mediosContacto=crearArrayMediosCT();
	prepararRequest(url, domicilioCentroTrabajoForm, true, procesarRespuestaServidor);
}

function guardarCerrarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar antes de cerrar la solicitud?', guardarCerrarSolicitud);
}

function guardarCerrarSolicitud(){
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/domicilioCt/' + idSolicitud;

	var domicilioCentroTrabajoForm = $("form#domicilioCentroTrabajoForm").toObject();
	domicilioCentroTrabajoForm.cntroTrabajo.mediosContacto=crearArrayMediosCT();
	prepararRequest(url, domicilioCentroTrabajoForm, true, callbackGuardarCerrarSolicitud);
}

function callbackGuardarCerrarSolicitud(response) {
	procesarRespuestaServidor(response, cerrarWizard);
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

function fnOpenBuscarDomicilio() {
	parent.DomicilioCtrl.init('domiciliosComponent'); 
	parent.DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
	parent.DomicilioCtrl.localizar();
}

var fnOnDomicilioReturn = function(){ 
	domicilioCentrotrabajo = this;

	var objeto = new Object();
	objeto.asentamiento=new Object(); 
	objeto.asentamiento.nombre = domicilioCentrotrabajo.asentamiento.nombre;
	objeto.asentamiento.clave = domicilioCentrotrabajo.asentamiento.clave;
	objeto.codigoPostal = new Object();
	objeto.codigoPostal.codigoPostal = domicilioCentrotrabajo.codigoPostal.codigoPostal;
	
	fnParseDomicilio();
	evaluarCentroTrabajo(true);
};

var fnParseDomicilio = function(){
	var d = domicilioCentrotrabajo;		
	
	if (d!=null && d.vialidadPrimaria!=undefined){						
		if ( d.vialidadReferenciaPosterior!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val(d.vialidadReferenciaPosterior.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val(d.vialidadReferenciaPosterior.clave);			
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPosterior.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val("");
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val("");			
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val("");
		}
		
		if(d.vialidadReferenciaPrimaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val(d.vialidadReferenciaPrimaria.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val(d.vialidadReferenciaPrimaria.clave);
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPrimaria.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val('');
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val('');
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val('');
		}
		
		if(typeof d.vialidadPrimaria !== 'undefined' &&  d.vialidadPrimaria != null){
			$("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val(d.vialidadPrimaria.nombre);		
			$("#cntroTrabajo\\.vialidadPrimaria\\.clave").val(d.vialidadPrimaria.clave);	
			if(d.vialidadPrimaria.tipoVialidad != null) {
				$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val(d.vialidadPrimaria.tipoVialidad.clave);
			}
		}
		
		if(d.vialidadReferenciaSecundaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val(d.vialidadReferenciaSecundaria.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val(d.vialidadReferenciaSecundaria.clave);
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaSecundaria.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val('');
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val('');
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val('');
		}
		
		if(d.domicilioCarretera!=undefined) {
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(d.domicilioCarretera.terminoGeneral.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.clave").val(d.domicilioCarretera.terminoGeneral.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(d.domicilioCarretera.derechoTransito.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.clave").val(d.domicilioCarretera.derechoTransito.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.origen").val(d.domicilioCarretera.origen);
			$("#cntroTrabajo\\.domicilioCarretera\\.destino").val(d.domicilioCarretera.destino);
			$("#cntroTrabajo\\.domicilioCarretera\\.codigoCarretera").val(d.domicilioCarretera.codigoCarretera);
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.descripcion").val(d.domicilioCarretera.administracion.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.clave").val(d.domicilioCarretera.administracion.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.cadenamiento").val(d.domicilioCarretera.cadenamiento);
		} else{
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.origen").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.destino").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.cadenamiento").val('');
		}
		
		if(d.domicilioCamino != undefined) {
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(d.domicilioCamino.terminoGeneral.descripcion);
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.clave").val(d.domicilioCamino.terminoGeneral.clave);
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.descripcion").val(d.domicilioCamino.margen.descripcion);
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.clave").val(d.domicilioCamino.margen.clave);
			$("#cntroTrabajo\\.domicilioCamino\\.origen").val(d.domicilioCamino.origen);
			$("#cntroTrabajo\\.domicilioCamino\\.destino").val(d.domicilioCamino.destino);
			$("#cntroTrabajo\\.domicilioCamino\\.cadenamiento").val(d.domicilioCamino.cadenamiento);
		} else {
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.origen").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.destino").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.cadenamiento").val('');
		}
		
		$("#cntroTrabajo\\.calle").val(d.calle);
		$("#cntroTrabajo\\.tipoBusquedaVialidad").val(d.tipoBusquedaVialidad);
	
		$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val(d.codigoPostal.codigoPostal);		
		$("#cntroTrabajo\\.numExterior1").val(d.numExterior1);		
		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val(d.asentamiento.localidad.clave);		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val(d.asentamiento.localidad.municipio.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val(d.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(d.asentamiento.localidad.municipio.entidadFederativa.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val(d.asentamiento.localidad.nombre);		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val(d.asentamiento.localidad.municipio.nombre);
		$("#cntroTrabajo\\.asentamiento\\.nombre").val(d.asentamiento.nombre);
		$("#cntroTrabajo\\.asentamiento\\.clave").val(d.asentamiento.clave);
		//$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val(d.vialidadPrimaria.tipoVialidad.descripcion);
		
		if(d.numInterior!=undefined)
			$("#cntroTrabajo\\.numInterior").val(d.numInterior);
		else 
			$("#cntroTrabajo\\.numInterior").val('');
		
		if(d.numExteriorAlf!=undefined)
			$("#cntroTrabajo\\.numExteriorAlf").val(d.numExteriorAlf);
		else
			$("#cntroTrabajo\\.numExteriorAlf").val('');
		
		if(d.numInteriorAlf!=undefined)
			$("#cntroTrabajo\\.numInteriorAlf").val(d.numInteriorAlf);
		else 
			$("#cntroTrabajo\\.numInteriorAlf").val('');
	}
};

function crearArrayMediosCT(){
	var telefonoFijo = new Object();
	telefonoFijo.idVista = 1;
	telefonoFijo.tipoMedioContacto= new Object();
	telefonoFijo.tipoMedioContacto.idTipoMedioContacto=$("#idTipoContactoTelefonoFijo").val();
	
	
	var lada = $("#ctLada").val()!=undefined ? $("#ctLada").val() :"";
	var tel = $("#ctTelefonoFijo").val()!=undefined ? $("#ctTelefonoFijo").val() : "";
	var ext = $("#ctExtension").val()!=undefined ? $("#ctExtension").val() : "";
	var descripcionTelefono = 	lada+ "|"+tel+"|"+ext;
	
	telefonoFijo.desFormaContacto=descripcionTelefono;
	
	var telefonoFijo2 = new Object();
	telefonoFijo2.idVista = 2;
	telefonoFijo2.tipoMedioContacto= new Object();
	telefonoFijo2.tipoMedioContacto.idTipoMedioContacto=$("#idTipoContactoTelefonoFijo").val();
	
	var ladaB = $("#ctLada2").val()!=undefined ? $("#ctLada2").val() :"";
	var telB = $("#ctTelefonoFijo2").val()!=undefined ? $("#ctTelefonoFijo2").val() : "";
	var extB = $("#ctExtension2").val()!=undefined ? $("#ctExtension2").val() : "";
	var descripcionTelefonoB = 	ladaB+ "|"+telB+"|"+extB;

	telefonoFijo2.desFormaContacto=descripcionTelefonoB;
	
	var correo = new Object();
	correo.idVista = 3;
	correo.tipoMedioContacto= new Object();
	correo.tipoMedioContacto.idTipoMedioContacto=$("#idTipoContactoCorreo").val();
	correo.desFormaContacto=$("#ctCorreoElectronico").val()!=undefined ? $("#ctCorreoElectronico").val() : "";
	
	var listMedios=[telefonoFijo, telefonoFijo2, correo];
	
	return listMedios;
}


function obtenerDescripcionTelefonoPrimario(){
	var lada = $("#ctLada").val()!=undefined ? $("#ctLada").val() :"";
	var tel = $("#ctTelefonoFijo").val()!=undefined ? $("#ctTelefonoFijo").val() : "";
	var ext = $("#ctExtension").val()!=undefined ? $("#ctExtension").val() : "";
	var descripcionTelefono = 	lada+ "|"+tel+"|"+ext;
	return descripcionTelefono;
}

function obtenerDescripcionTelefonoSecundario(){
	var ladaB = $("#ctLada2").val()!=undefined ? $("#ctLada2").val() :"";
	var telB = $("#ctTelefonoFijo2").val()!=undefined ? $("#ctTelefonoFijo2").val() : "";
	var extB = $("#ctExtension2").val()!=undefined ? $("#ctExtension2").val() : "";
	var descripcionTelefonoB = 	ladaB+ "|"+telB+"|"+extB;
	return descripcionTelefonoB;
}

function asignarValoresCompletosDeTelefono(){
	$("#desTelefonoPrimario").val(obtenerDescripcionTelefonoPrimario());
	$("#desTelefonoSecundario").val(obtenerDescripcionTelefonoSecundario());
}

function marcarErroresDomicilio(error) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";
	
	$("#domicilioCentroTrabajoForm").find(".required").each(function(){
		this.style.color=cssColor;
	});

	$("#domicilioCentroTrabajoForm").find(".error").each(function(){
		this.style.display=cssDisplay;
		this.innerHTML = "Este campo es obligatorio.";
	});
	
	$("#domicilioCentroTrabajoForm").find(".campoObligatorio").each(function(){
		this.style.border = cssBorder;
	});

}
var evaluarCentroTrabajo = function(checarSoloDomicilio) {
	var temp = "";
	var errors = "";
	var existeError = false;
	var $elementoValidando = null;
	var errorCampoObligarorio = "Este campo es obligatorio.";
	
	temp = $("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val();
	if (isEmpty(temp)) {
		errors += "* Los datos de la nueva direcci&oacute;n del centro de trabajo son requeridos. <br/>";
		setCampoToFocusOn("#cntroTrabajo\\.vialidadPrimaria\\.nombre");
		marcarErroresDomicilio(true);
	} else {
		marcarErroresDomicilio(false);
	}
	
	if(checarSoloDomicilio == undefined || !checarSoloDomicilio) {
		var telFijoA = $("#ctTelefonoFijo").val();
		var ladaB = $("#ctLada2").val();
		var telFijoB = $("#ctTelefonoFijo2").val();
		var extB = $("#ctExtension2").val();
		
		$elementoValidando = $("#ctTelefonoFijo");
		if (isEmpty(telFijoA)) {
			existeError = true;
			errors += "* Debe proporcionar un tel&eacute;fono fijo (Principal) para el nuevo domicilio. <br/>";
			setCampoToFocusOn("#ctTelefonoFijo");
		}
		mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
		existeError = false;
		
		if(!isEmpty(ladaB) && isEmpty(telFijoB)){
			errors += "* Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido para el nuevo domicilio. <br/>";
			setCampoToFocusOn("#ctTelefonoFijo2");
		}else if(!isEmpty(extB) && isEmpty(telFijoB)){
			errors += "* Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido para el nuevo domicilio. <br/>";
			setCampoToFocusOn("#ctTelefonoFijo2");
		}
		
		$elementoValidando = $("#ctCorreoElectronico");
		temp = 	$elementoValidando.val();
		if (isEmpty(temp)) {
			existeError=true;
			errors += "* Debe proporcionar un correo electr&oacute;nico para el nuevo domicilio. <br/>";
			setCampoToFocusOn("#ctCorreoElectronico");
		}else if(!fnValidaCorreo(temp)){
			existeError=true;
			errorCampoObligarorio="Formato inv&aacute;lido."
			errors += "* Debe proporcionar un correo electr&oacute;nico v&aacute;lido para el nuevo domicilio. <br/>";
			setCampoToFocusOn("#ctCorreoElectronico");
		}
		mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
	}
	
	pintarErrorGeneral(errors != "");
	return errors;
};

function mostrarMensajeErrorMovPat($campo, error , mensaje) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";
	
	if($campo != undefined) {
		var idCampo = $campo.attr("id");
		
		
		var tipoElemento = $campo.prop("tagName");
		var idSpanRequired = $campo.attr("spanRequired");
		var idSpanError = $campo.attr("spanError");
		//console.log("el id del campo es: " + idCampo + " y es un " + tipoElemento + " su spanRequerido es: " + idSpanRequired + " su spanError es: " + idSpanError);
		var valorCampo = $campo.val();
		
		if(tipoElemento != "table") {
			//ponemos el campo en rojo
			$campo.css("border",cssBorder);
		}
		//se obtiene asi para evitar escapar 
		var spanError = document.getElementById(idCampo+"Error");
		var campoRequired = document.getElementById(idCampo + "Req");
		
		if(spanError != undefined && spanError != null) {
			//console.log("se encontro el span de error y su id es: " + spanError.id );
			spanError.style.display = cssDisplay;
			
			if((mensaje != undefined || mensaje != null) && error) {
				spanError.innerHTML = mensaje;
			} else {
				spanError.innerHTML = "";
			}
		}
		
		if(campoRequired != undefined && campoRequired != null) {
			//console.log("se encontro el campo required y su id es: " + campoRequired.id);
			campoRequired.style.color = cssColor;
		}
		
	}
}
function isEmpty(temp) {
	return (temp == undefined || temp == "");
}

function setCampoToFocusOn(fieldName) {
	if (campoToFocusOn == undefined)
		campoToFocusOn = $(fieldName);
}

function construirDialogoErrores(errores) {
	$("#textoMensaje").html(errores);
	$("#textoMensaje").removeAttr("style");
	$("#textoMensaje").attr("style", "color: red;");
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 275,
		width : 500,
		title : "Existen faltantes en su captura.",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
				campoToFocusOn.focus();
				if (campoToFocusOn.selector == "#fechaEfecto") {
					fechaEfectoDatePicker.datepicker('show');
				}
				campoToFocusOn = undefined;

			}
		}
	});
	dialogo.dialog('open');
}

function procesarRespuestaServidor(response, callback) {
	var mensaje = "";
	var titulo = "";
	var error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
			construirDialogoMensajes(titulo, mensaje, error, callback);
		}
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoMensajes(titulo, mensaje, error, callback);
	}
	return false;
}

function construirDialogoMensajes(titulo, mensaje, error, callback) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 'auto',
		width : 400,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function setEventosChecarError(idFormulario,functionPintarErrores) {
	$("#"+idFormulario+" :text").change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" select").change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		functionPintarErrores();
	});
}
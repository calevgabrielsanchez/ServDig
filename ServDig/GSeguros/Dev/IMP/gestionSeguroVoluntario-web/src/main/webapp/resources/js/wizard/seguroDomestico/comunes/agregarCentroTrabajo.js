$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');
$.getScript('/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/Domicilio.js');
$.getScript('/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/widget/MunicipioImssWidget.js');

var domicilioCentrotrabajo;
var existeCentroTrabajo = false;
//var infoRegistroPatronal;
var registroPatronal;

$(document).ready(function() {
	$('#cancelarTramite').click(function() {
		if(ventanilla && tieneSeguros)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	$('#siguientePaso').click(function(event) {
		if(!existeCentroTrabajo) {
			event.preventDefault();
			var sSource = '/${mvn.web.app.root}/wizard/seguroDomestico/comunes/registrarNuevoCentroTrabajo';
			var request = $.ajax({
				url : sSource,
				async : false,
				type : "POST",
				data : domicilioCentrotrabajo ? JSON.stringify(domicilioCentrotrabajo) : null,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success: function(data) {
					if(data.existeCentroTrabajo) {
						$('#validacion').show();
						$('#infoDomicilio').hide();
						existeCentroTrabajo = true;
//						infoRegistroPatronal = data;
						registroPatronal = data.registroPatronal;
						$('#descripcionDomicilio').text(registroPatronal.centrotrabajo.descripcion);
					} else {
						$('#nextStepForm').submit();
					}
				}
			});
		} else {
			$('#inputIdCentroTrabajo').val(registroPatronal.centrotrabajo.idCentroTrabajo);
			$('#inputNumeroRegistroPatronal').val(registroPatronal.numeroRegistroPatronal);
			$('#inputIdModalidad').val(registroPatronal.modalidad.idModalidad);
			$('#inputNumModalidad').val(registroPatronal.modalidad.numModalidad);
			$('#inputDigitoVerificador').val(registroPatronal.digitoVerificador);
			$('#inputClaveAsentamiento').val(registroPatronal.centrotrabajo.asentamiento.clave);
			$('#inputClaveLocalidad').val(registroPatronal.centrotrabajo.asentamiento.localidad.clave);
			$('#inputClaveMunicipio').val(registroPatronal.centrotrabajo.asentamiento.localidad.municipio.clave);
			$('#inputClaveEntidadFederativa').val(registroPatronal.centrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave);
//			$('#inputIdCentroTrabajo').val(infoRegistroPatronal.idCentroTrabajo);
//			$('#inputNumeroRegistroPatronal').val(infoRegistroPatronal.numeroRegistroPatronal);
//			$('#inputIdModalidad').val(infoRegistroPatronal.idModalidad);
//			$('#inputNumModalidad').val(infoRegistroPatronal.numModalidad);
//			$('#inputDigitoVerificador').val(infoRegistroPatronal.digitoVerificador);
//			$('#descripcionDomicilio').val(infoRegistroPatronal.descripcionCentroTrabajo);
			$('#nextStepFormExisteCentroTrabajo').submit();
		}
//		request.done(validacionCentroTrabajo);
//		request.fail(validacionCentroTrabajo);
		
	});
});

//function validacionCentroTrabajo(data) {
//	alert(data.existeCentroTrabajo);
//	$('#nextStepForm').submit();
//}

function fnOpenBuscarDomicilio(){
	parent.DomicilioCtrl.init('domiciliosComponent'); 
	parent.DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
	parent.DomicilioCtrl.localizar();
}

var fnOnDomicilioReturn = function(){
	domicilioCentrotrabajo = this;
	$('#siguientePaso').show();

	fnParseDomicilio();
//	if(domicilioCentrotrabajo.codigoPostal.codigoPostal!=undefined && domicilioCentrotrabajo.codigoPostal.codigoPostal!='') {
//		$("#hdnCveEnt").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave);
//		$("#hdnCveMun").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.clave);
//		$("#hdnCodigoPostal").val(domicilioCentrotrabajo.codigoPostal.codigoPostal);
//	}

//	var idSubdelegacionOrigen = $('#idSubdelegacionOrigen').val();
//	var sSourve = '';
//	if(ventanilla)
//		sSource = '/delta-gestionPatronal-web-ventanilla/clasificacion/validarSubdelegacionCentroTrabajo?idSubdelegacionOrigen=1';//+idSubdelegacionOrigen;
//	else
//		sSource = '/delta-gestionPatronal-web/clasificacion/validarSubdelegacionCentroTrabajo?idSubdelegacionOrigen=1';//+idSubdelegacionOrigen;
//	var objeto = new Object();
//	objeto.asentamiento=new Object();
//	objeto.asentamiento.nombre = domicilioCentrotrabajo.asentamiento.nombre;
//	objeto.asentamiento.clave = domicilioCentrotrabajo.asentamiento.clave;
//	objeto.codigoPostal = new Object();
//	objeto.codigoPostal.codigoPostal = domicilioCentrotrabajo.codigoPostal.codigoPostal;
//	objeto.asentamiento.localidad = new Object();
//	objeto.asentamiento.localidad.municipio= new Object();
//	objeto.asentamiento.localidad.municipio.entidadFederativa= new Object();
//	objeto.asentamiento.localidad.clave = domicilioCentrotrabajo.asentamiento.localidad.clave;
//	objeto.asentamiento.localidad.nombre= domicilioCentrotrabajo.asentamiento.localidad.nombre;
//	objeto.asentamiento.localidad.municipio.clave = domicilioCentrotrabajo.asentamiento.localidad.municipio.clave;
//	objeto.asentamiento.localidad.municipio.nombre=domicilioCentrotrabajo.asentamiento.localidad.municipio.nombre;
//	objeto.asentamiento.localidad.municipio.entidadFederativa.clave=domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave;
//	objeto.asentamiento.localidad.municipio.entidadFederativa.nombre = domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre;
//	
//	prepararRequest(sSource, objeto, false, callbackValidaSubdelegacion);
}

//function prepararRequest(sSource, data, async, callback) {
//	var request = $.ajax({
//		url : sSource,
//		async : async,
//		type : "POST",
//		data : data ? JSON.stringify(data) : null,
//		dataType : "json",
//		contentType : "application/json; charset=utf-8"
//	});
//	request.done(callback);
//	request.fail(callback);
//}

//function callbackValidaSubdelegacion(response){
//	fnParseDomicilio();
//	if(domicilioCentrotrabajo.codigoPostal.codigoPostal!=undefined && domicilioCentrotrabajo.codigoPostal.codigoPostal!='') {
//		$("#hdnCveEnt").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave);
//		$("#hdnCveMun").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.clave);
//		$("#hdnCodigoPostal").val(domicilioCentrotrabajo.codigoPostal.codigoPostal);
//	}
//}

function construirDialogoMensajes(titulo, mensaje, error, callback, pheight, pwidth) {
	if(pheight==undefined)
		pheight='auto';
	if(pwidth==undefined)
		pwidth=400;
	
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
		height : pheight,
		width : pwidth,
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
		
		if(typeof d.vialidadPrimaria !== 'undefined' && d.vialidadPrimaria != null){
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
		
		if(d.numExteriorAlf!=undefined)
			$("#cntroTrabajo\\.numExteriorAlf").val(d.numExteriorAlf.toUpperCase());
		else
			$("#cntroTrabajo\\.numExteriorAlf").val('');
		
		if(d.numInterior!=undefined)
			$("#cntroTrabajo\\.numInterior").val(d.numInterior);
		else
			$("#cntroTrabajo\\.numInterior").val('');
		
		if(d.numInteriorAlf!=undefined)
			$("#cntroTrabajo\\.numInteriorAlf").val(d.numInteriorAlf.toUpperCase());
		else
			$("#cntroTrabajo\\.numInteriorAlf").val('');
	}
};

function obtenerMunicipiosImss() {
	var paramCentroTrabajo = $('#asentamientoForMunicipioForm').serialize();
	
	var url = '/${mvn.web.app.rootDomicilios}/widget/domicilio/utility/consulta/municipioImss/centroTrabajo';
	
	$.post(url, paramCentroTrabajo, function(data){
		$('#municipioImssContenedor').html(data);

		// Se settea la funci�n de callback para las UMF
		setFnCallback(fnSeleccionarMunicipioImss);
		
		// Se ejecuta la funci�n de inicializaci�n
		idMunicipioImssInicial = $('#municipioIMSS\\.idMunicipio').val();
		initComponenteMunicipiosImss();
	}).error(function (data){
		$('#municipioImssContenedor').html("<span>Existi&oacute; un error al cargar las Subdelegaciones</span>");
	});
}

var fnSeleccionarMunicipioImss = function () {
	var municipioImssArray = municipioSeleccionado.split('|');

	$('#municipioIMSS\\.idMunicipio').val(municipioImssArray[0]);
	$('#municipioIMSS\\.cvecMunicipioSINDO').val(municipioImssArray[1]);
	$('#municipioIMSS\\.descMunicipio').val(municipioImssArray[2]);
	
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.id').val(municipioImssArray[3]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.clave').val(municipioImssArray[4]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.descripcion').val(municipioImssArray[5]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.ciz').val(municipioImssArray[6]);

	$('#municipioIMSS\\.subdelegacion\\.id').val(municipioImssArray[7]);
	$('#municipioIMSS\\.subdelegacion\\.clave').val(municipioImssArray[8]);
	$('#municipioIMSS\\.subdelegacion\\.descripcion').val(municipioImssArray[9]);

};
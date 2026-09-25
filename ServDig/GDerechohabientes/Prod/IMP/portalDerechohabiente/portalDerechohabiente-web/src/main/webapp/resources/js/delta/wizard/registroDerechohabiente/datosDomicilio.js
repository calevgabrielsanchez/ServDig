/**
 * 
 */

CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

$(document).ready(
	function () {
		
		$("#formRegistro").deshabilitarContenido(false);
		
		/*
		$("#ubicarDomicilioDer").click( function() {
			ubicarDomicilioIntegrante();
		});
		*/
		
		$("#continuarAUmf").click(function() {
			if(validarDomicilioCapturadoComponente()){
				validarDomicilio();
			}
		});
		initDomiciliosRegistro($("#permiteCaputuraDom").val()==0);
	}
);

function validarDomicilio() {
	
	$("form#formRegistro").habilitarContenido(false);
	var oForm = $("form#formRegistro").toObject();
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/validaciones';
	fnHideErrores("form#formRegistro");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		
		if(!data2.correcto)  {
			habilitarDesabilitarCamposDatosBasicos(false,false);
			$.unblockUI();
			mostrarMensajeError(data2.mensaje);
		} else {
			$.blockUI();
			$("#formRegistro").habilitarContenido(false);
			$("#formRegistro").attr("action",CONTEXT_PATH_APLICACION + "/wizard/registro/siguiente");
			$("#formRegistro").submit();
		}
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		$.unblockUI();
		fnProcesarErrores(data, "form#formRegistro");
	});
}

function ubicarDomicilioIntegrante() {
	parent.DomicilioCtrl.init('domiciliosComponent'); 
	parent.DomicilioCtrl.setOnCloseCallback(setearDomicilio);
	parent.DomicilioCtrl.localizar();
}

function habilitarDesabilitarCamposDatosBasicos(habilitar,envio) {
	if(habilitar) {
		$("#formRegistro").habilitarContenido(false);
	} else {
		$("#formRegistro").deshabilitarContenido(false);
	}
}

var setearDomicilio = function(){
	var d = this;		
	
	if (d!=null && d.vialidadPrimaria!=undefined){		
		$("#domicilio\\.clave").val("");
		if ( d.vialidadReferenciaPosterior!=undefined){
			$("#domicilio\\.vialidadReferenciaPosterior\\.nombre").val(d.vialidadReferenciaPosterior.nombre);
			$("#domicilio\\.vialidadReferenciaPosterior\\.clave").val(d.vialidadReferenciaPosterior.clave);			
			$("#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPosterior.tipoVialidad.clave);
		}else{
			$("#domicilio\\.vialidadReferenciaPosterior\\.nombre").val("");
			$("#domicilio\\.vialidadReferenciaPosterior\\.clave").val("");			
			$("#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val("");
		}
		
		if(d.vialidadReferenciaPrimaria!=undefined){
			$("#domicilio\\.vialidadReferenciaPrimaria\\.nombre").val(d.vialidadReferenciaPrimaria.nombre);
			$("#domicilio\\.vialidadReferenciaPrimaria\\.clave").val(d.vialidadReferenciaPrimaria.clave);
			$("#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPrimaria.tipoVialidad.clave);
		}else{
			$("#domicilio\\.vialidadReferenciaPrimaria\\.nombre").val('');
			$("#domicilio\\.vialidadReferenciaPrimaria\\.clave").val('');
			$("#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val('');
		}
		
		if(typeof d.vialidadPrimaria !== 'undefined' &&  d.vialidadPrimaria != null){
			$("#domicilio\\.vialidadPrimaria\\.nombre").val(d.vialidadPrimaria.nombre);		
			$("#domicilio\\.vialidadPrimaria\\.clave").val(d.vialidadPrimaria.clave);	
			if(d.vialidadPrimaria.tipoVialidad != null) {
				$("#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave").val(d.vialidadPrimaria.tipoVialidad.clave);
			}
		}
		
		if(d.vialidadReferenciaSecundaria!=undefined){
			$("#domicilio\\.vialidadReferenciaSecundaria\\.nombre").val(d.vialidadReferenciaSecundaria.nombre);
			$("#domicilio\\.vialidadReferenciaSecundaria\\.clave").val(d.vialidadReferenciaSecundaria.clave);
			$("#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaSecundaria.tipoVialidad.clave);
		}else{
			$("#domicilio\\.vialidadReferenciaSecundaria\\.nombre").val('');
			$("#domicilio\\.vialidadReferenciaSecundaria\\.clave").val('');
			$("#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val('');
		}
		
		if(d.domicilioCarretera!=undefined) {
			$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(d.domicilioCarretera.terminoGeneral.descripcion);
			$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val(d.domicilioCarretera.terminoGeneral.clave);
			$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(d.domicilioCarretera.derechoTransito.descripcion);
			$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val(d.domicilioCarretera.derechoTransito.clave);
			$("#domicilio\\.domicilioCarretera\\.origen").val(d.domicilioCarretera.origen);
			$("#domicilio\\.domicilioCarretera\\.destino").val(d.domicilioCarretera.destino);
			$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val(d.domicilioCarretera.codigoCarretera);
			$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val(d.domicilioCarretera.administracion.descripcion);
			$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val(d.domicilioCarretera.administracion.clave);
			$("#domicilio\\.domicilioCarretera\\.cadenamiento").val(d.domicilioCarretera.cadenamiento);
			$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val(d.domicilioCarretera.codigoCarretera);
		} else{
			$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
			$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
			$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
			$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
			$("#domicilio\\.domicilioCarretera\\.origen").val('');
			$("#domicilio\\.domicilioCarretera\\.destino").val('');
			$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
			$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
			$("#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
			$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');
		}
		
		if(d.domicilioCamino != undefined) {
			$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(d.domicilioCamino.terminoGeneral.descripcion);
			$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val(d.domicilioCamino.terminoGeneral.clave);
			$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val(d.domicilioCamino.margen.descripcion);
			$("#domicilio\\.domicilioCamino\\.margen\\.clave").val(d.domicilioCamino.margen.clave);
			$("#domicilio\\.domicilioCamino\\.origen").val(d.domicilioCamino.origen);
			$("#domicilio\\.domicilioCamino\\.destino").val(d.domicilioCamino.destino);
			$("#domicilio\\.domicilioCamino\\.cadenamiento").val(d.domicilioCamino.cadenamiento);
		} else {
			$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
			$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
			$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
			$("#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
			$("#domicilio\\.domicilioCamino\\.origen").val('');
			$("#domicilio\\.domicilioCamino\\.destino").val('');
			$("#domicilio\\.domicilioCamino\\.cadenamiento").val('');
		}
		
		$("#domicilio\\.calle").val(d.calle);
		$("#domicilio\\.tipoBusquedaVialidad").val(d.tipoBusquedaVialidad);
	
		$("#domicilio\\.codigoPostal\\.codigoPostal").val(d.codigoPostal.codigoPostal);		
		$("#domicilio\\.numExterior1").val(d.numExterior1);		
		
		$("#domicilio\\.asentamiento\\.localidad\\.clave").val(d.asentamiento.localidad.clave);		
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave").val(d.asentamiento.localidad.municipio.clave);
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val(d.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(d.asentamiento.localidad.municipio.entidadFederativa.clave);
		$("#domicilio\\.asentamiento\\.localidad\\.nombre").val(d.asentamiento.localidad.nombre);		
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre").val(d.asentamiento.localidad.municipio.nombre);
		$("#domicilio\\.asentamiento\\.nombre").val(d.asentamiento.nombre);
		$("#domicilio\\.asentamiento\\.clave").val(d.asentamiento.clave);
		//$("#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val(d.vialidadPrimaria.tipoVialidad.descripcion);
		
		if(d.numInterior!=undefined)
			$("#domicilio\\.numInterior").val(d.numInterior);
		else 
			$("#domicilio\\.numInterior").val('');
		
		if(d.numExteriorAlf!=undefined)
			$("#domicilio\\.numExteriorAlf").val(d.numExteriorAlf);
		else
			$("#domicilio\\.numExteriorAlf").val('');
		
		if(d.numInteriorAlf!=undefined)
			$("#domicilio\\.numInteriorAlf").val(d.numInteriorAlf);
		else 
			$("#domicilio\\.numInteriorAlf").val('');
	}
};

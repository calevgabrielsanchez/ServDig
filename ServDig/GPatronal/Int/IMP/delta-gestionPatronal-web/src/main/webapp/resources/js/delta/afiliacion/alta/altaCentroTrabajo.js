var idDialgoAgregarMedioContactoCentroTrabajo = "#divMedioContactoAlta";

function fnUbicarDomicilio(){
	DomicilioCtrl.init('domicilioLocaliza'); 
	DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
	DomicilioCtrl.localizar();
} 

var fnOnDomicilioReturn = function(){ 
	var d = this;		
	if (d!=null && d.vialidadReferenciaPrimaria!=undefined){								
		if ( d.vialidadReferenciaPosterior!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val(d.vialidadReferenciaPosterior.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val(d.vialidadReferenciaPosterior.clave);			
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPosterior.tipoVialidad.clave);
		}
		else{
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val("");
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val("");			
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val("");
		}
		
		if(d.vialidadReferenciaPrimaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val(d.vialidadReferenciaPrimaria.nombre);		
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val(d.vialidadReferenciaPrimaria.clave);
			if(d.vialidadReferenciaPrimaria.tipoVialidad!=undefined)
				$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPrimaria.tipoVialidad.clave);
		}
		
		if(d.vialidadReferenciaSecundaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val(d.vialidadReferenciaSecundaria.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val(d.vialidadReferenciaSecundaria.clave);
			if(d.vialidadReferenciaSecundaria.tipoVialidad!=undefined)
				$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaSecundaria.tipoVialidad.clave);		
			
		}
		
		if(d.vialidadPrimaria!=undefined){
			$("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val(d.vialidadPrimaria.nombre);		
			$("#cntroTrabajo\\.vialidadPrimaria\\.clave").val(d.vialidadPrimaria.clave);		
			if(d.vialidadPrimaria.tipoVialidad!=undefined)
				$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val(d.vialidadPrimaria.tipoVialidad.clave);
		}
		
		$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val(d.codigoPostal.codigoPostal);		
		$("#cntroTrabajo\\.numExterior1").val(d.numExterior1);		
		$("#cntroTrabajo\\.numInterior").val(d.numInterior);		
		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val(d.asentamiento.localidad.clave);		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val(d.asentamiento.localidad.municipio.clave);
		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val(d.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(d.asentamiento.localidad.municipio.entidadFederativa.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val(d.asentamiento.localidad.nombre);		
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val(d.asentamiento.localidad.municipio.nombre);
		$("#cntroTrabajo\\.asentamiento\\.nombre").val(d.asentamiento.nombre);
		$("#cntroTrabajo\\.asentamiento\\.clave").val(d.asentamiento.clave);
		$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val(d.vialidadPrimaria.tipoVialidad.descripcion);
		$("#cntroTrabajo\\.numExteriorAlf").val(d.numExteriorAlf);
		$("#cntroTrabajo\\.numInteriorAlf").val(d.numInteriorAlf);							
	}
}



function inicializaComponenteMediosContacto(objDialogo, idDialogo, fnGuardar, fnCancelar){
//	oDialogAgregarMedioContactoCentroTrabajo = 	$( sIdDialgoAgregarMedioContactoCentroTrabajo).dialog({
	objDialogo = 	$( idDialogo).dialog({
		title : "Gesti\u00F3n de Medios de Contacto",
		autoOpen:false,
		resizable: false,
		width:500,
		height:400,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function(){
				fnGuardar();
//				ejecutarAccionMedioContactoRLAlta();
			},
			
			'Cancelar': function(){
				if (jQuery.isFunction(fnCancelar)) {
					fnCancelar();
				}	
				$( this ).dialog( "close" );
//					limpiarFormularioAgregarMedioContactoRLAlta();
			}
		}
	});
	return objDialogo;
}

function obtenerListaMediosContacto(oTableMedios){
	var lista = [];
	var medioContacto;
	var dataOrigen;
	
	if (oTableMedios != undefined){
		dataOrigen = obtenerDatosGrid(oTableMedios);
	} else {
		dataOrigen = obtenerDatosGrid(oTableMedios);
	}
	
	for(registro in dataOrigen){
		data = dataOrigen[registro]._aData;	
		medioContacto = new Object();
		medioContacto.desFormaContacto = data["desFormaContacto"];
		tipoMedioContacto = new Object();
		tipoMedioContacto.idTipoMedioContacto = data["tipoMedioContacto"]["idTipoMedioContacto"];
		tipoMedioContacto.descripcion = data["tipoMedioContacto"]["descripcion"];
		medioContacto.tipoMedioContacto = tipoMedioContacto;
		lista.push(medioContacto);
	}
	return lista;
}

function attachMediosCentroTrabajo(sujetoTramite, oTableMedios){
	var mediosCentroTrabajo = obtenerListaMediosContacto(oTableMedios);
	
	if(sujetoTramite.cntroTrabajo == undefined)
		sujetoTramite.cntroTrabajo = new Object();
	
	sujetoTramite.cntroTrabajo.mediosContacto = mediosCentroTrabajo;
	
	return sujetoTramite;
}

function fnInicializarDialogoAgregarCentroTrabajo(){
	oDialogoMedioContacto = inicializaComponenteMediosContacto(oDialogoMedioContacto,idDialgoAgregarMedioContactoCentroTrabajo,ejecutarAccionMedioContacto);
	fnOcultaErrores($("#errorMedioContacto"));
	fnAbrirDialogoAgregarMedioContacto();
}

$(function() {
	construirGridMediosContacto("gridMediosContactoAltaCentroTrabajo");
	oDialogoMedioContacto = inicializaComponenteMediosContacto(oDialogoMedioContacto,idDialgoAgregarMedioContactoCentroTrabajo,ejecutarAccionMedioContacto);
});
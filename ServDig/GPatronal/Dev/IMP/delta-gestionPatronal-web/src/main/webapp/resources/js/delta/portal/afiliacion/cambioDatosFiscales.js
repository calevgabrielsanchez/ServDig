var icaFisicaCtrl;
var icaMoralCtrl;
var modManualFisicaCtrl;
var modManualMoralCtrl;
var oTableMediosFiscales;
var idSolicitud;
var firmaDigitalCtrl;


$(function() {
	if(esPatronFisico){
		configurarComponenteModificacionManualFisica();
		configurarBusquedaICAFisica();
//		mostrarBusquedaICAFisica();
	}else{
		configurarComponenteModificacionManualMoral();
		configurarBusquedaICAMoral();
//		mostrarBusquedaICAMoral();
	}
	inicializaDatosContactoFiscales();
//	inicializarComponenteFirmaDigital();
});


var columnasMedioContactoFiscales = [ {
	mDataProp : "clave",
	bVisible : false
}, {
	sTitle : "Medio Contacto",
	mDataProp : "tipoMedioContacto.descripcion",
	bVisible : true
}, {
	sTitle : "Descripci\u00F3n",
	mDataProp : "desFormaContacto",
	bVisible : true
} ]; 


function configurarComponenteModificacionManualFisica(){
	
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/modificacion/manual/modificacion-manual-datos.js", function(){
	modManualFisicaCtrl = ModificacionManualDatosFisicaCtrl;
	 
	 //ID de un div vacío
	modManualFisicaCtrl.init('mdmFisicaDialog');
	 
	 //Datos de entrada
	modManualFisicaCtrl.datosEntrada.idPersona = $('#fisica\\.idPersona').val();
	modManualFisicaCtrl.datosEntrada.indCapturaNombre = true;
	modManualFisicaCtrl.datosEntrada.indCapturaCURP = true;
	modManualFisicaCtrl.datosEntrada.indCapturaSexo = true;
	modManualFisicaCtrl.datosEntrada.indCapturaFechaNacimiento = true;
	modManualFisicaCtrl.datosEntrada.indCapturaLugarNacimiento = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDocumentoProbatorio = false;
	modManualFisicaCtrl.datosEntrada.indCapturaRFC  = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDomicilioFiscal = true;
	modManualFisicaCtrl.datosEntrada.indCapturaMediosContactoFiscales = true;
	modManualFisicaCtrl.datosEntrada.indCapturaDomicilioParticular = false;
	modManualFisicaCtrl.datosEntrada.indCapturaMediosContactoParticular = false;
	modManualFisicaCtrl.datosEntrada.indAutorizacion = false;
	modManualFisicaCtrl.setOnCloseCallback(fnRetornoModificacionManualFisica);
	});
}

function fnRetornoModificacionManualFisica(){
	if(modManualFisicaCtrl.getDatosSalida()==undefined)
		return;
	var objMDM = modManualFisicaCtrl.getDatosSalida().mdmDatosEntrada;
	var fisicaResponse = objMDM.personaFisica;
	
	if(fisicaResponse==undefined)
		return; //Hubo error
	$('#fisica\\.rfc').val(fisicaResponse.rfc);
	$('#fisica\\.primerApellido').val(fisicaResponse.primerApellido);
	$('#fisica\\.segundoApellido').val(fisicaResponse.segundoApellido);
	$('#fisica\\.nombre').val(fisicaResponse.nombre);
	$('#fisica\\.curp').val(fisicaResponse.curp);
	
	vaciarDomicilioFiscal('fisica', fisicaResponse.domicilioFiscal);
	var domicilioCompleto = obtenerDescripcionDomicilio(fisicaResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(fisicaResponse.mediosContactoFiscales);
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosMDM',objMDM,
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();
}


function configurarBusquedaICAFisica(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js", function(){
		icaFisicaCtrl = identificarCambiosAutomaticosPersonaFisicaCtrl; 
		icaFisicaCtrl.init('icaFisicaDialog'); 
		 //SETTEO DE PARAMETROS DE ENTRADA 
		icaFisicaCtrl.datosEntrada.indMostrarPantalla = true; 
//		icaFisicaCtrl.datosEntrada.indUsuarioExterno = $('#isUsuarioExterno').is(':checked'); 
		icaFisicaCtrl.datosEntrada.idPersona = $('#fisica\\.idPersona').val(); 
//		icaFisicaCtrl.datosEntrada.curp = $('#busquedaCurp').val(); 
//		icaFisicaCtrl.datosEntrada.rfc = $('#busquedaRfc').val(); 
//		icaFisicaCtrl.datosEntrada.nombrePersona = $('#busquedaNombres').val(); 
//		icaFisicaCtrl.datosEntrada.primerApellido = $('#busquedaPrimerApellido').val(); 
//		icaFisicaCtrl.datosEntrada.segundoApellido = $('#busquedaSegundoApellido').val(); 
		icaFisicaCtrl.datosEntrada.indBusquedaRENAPO = true; 
		icaFisicaCtrl.datosEntrada.indBusquedaSAT = true; 
		icaFisicaCtrl.setOnCloseCallback(fnOnICAFisicaRetorno);
		icaFisicaCtrl.identificarCambios(); 
	});
		  
}

function fnOnICAFisicaRetorno(){
	if(icaFisicaCtrl.getDatosSalida()==undefined){
//		solicitarConfirmacionModificacionManualFisica();
		parent.tramiteDRSCtrl.cerrar();
		return; // No hubo respuesta.
	}
	
	var objICAF = icaFisicaCtrl.getDatosSalida();
	var fisicaResponse = objICAF.personaFisicaIMSS;
	
	if(fisicaResponse==undefined){
		if(objICAF.traza.COMPARACION_SIN_DIFERENCIAS!=undefined){
			return;
		}else{
//			solicitarConfirmacionModificacionManualFisica();
			parent.tramiteDRSCtrl.cerrar();
			return; //Hubo error
		}
	}
	$('#fisica\\.rfc').val(fisicaResponse.rfc);
	$('#fisica\\.primerApellido').val(fisicaResponse.primerApellido);
	$('#fisica\\.segundoApellido').val(fisicaResponse.segundoApellido);
	$('#fisica\\.nombre').val(fisicaResponse.nombre);
	$('#fisica\\.curp').val(fisicaResponse.curp);
	
	vaciarDomicilioFiscal('fisica', fisicaResponse.domicilioFiscal);
	var domicilioCompleto = obtenerDescripcionDomicilio(fisicaResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(fisicaResponse.mediosContactoFiscales);
	
	/* ¡ESTO SÓLO ES POR CUESTIÓN DE PRUEBAS!
	 * Se quita el campo documentos probatorios, para que
	 * al momento de que Spring parse el objeto JSON al objeto de modelo 
	 * no truene
	 */
	delete objICAF.personaFisicaIMSS.documentosProbatorios;
	delete objICAF.personaFisicaIMSS.mediosContacto;
	delete objICAF.personaFisicaIMSS.mediosContactoFiscales;
	delete objICAF.personaFisicaIMSS.fechaModificacion;
	delete objICAF.personaFisicaIMSS.fechaRegistro;
	if(objICAF.personaFisicaIMSS.registroSindicato!=undefined)
		delete objICAF.personaFisicaIMSS.registroSindicato.fechaRegistro;
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosICA',icaFisicaCtrl.getDatosSalida(),
			undefined, false);
	
}


function configurarComponenteModificacionManualMoral(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/moral/modificacion/manual/modificacion-manual-datos.js", function(){
	modManualMoralCtrl = ModificacionManualDatosMoralCtrl;
	 
	 //ID de un div vacío
	modManualMoralCtrl.init('mdmMoralDialog');
	 
	 //Datos de entrada
	modManualMoralCtrl.datosEntrada.idPersona = $('#moral\\.idPersona').val();
	modManualMoralCtrl.datosEntrada.indCapturaRFC  = true;
	modManualMoralCtrl.datosEntrada.indCapturaDomicilioFiscal = true;
	modManualMoralCtrl.datosEntrada.indCapturaMediosContactoFiscales = true;
	modManualMoralCtrl.datosEntrada.indCapturaRazonSocial = true;
	modManualMoralCtrl.datosEntrada.indCapturaFechaConstitucion = false;
	modManualMoralCtrl.datosEntrada.indCapturaTipoSociedad = true;
	modManualMoralCtrl.datosEntrada.indCapturaActaConstitutiva = false;
 	modManualMoralCtrl.datosEntrada.indCapturaRegistroSindicato = false;
	modManualMoralCtrl.datosEntrada.indAutorizacion = false;
	modManualMoralCtrl.setOnCloseCallback(fnRetornoModificacionManualMoral);
	});
}

function fnRetornoModificacionManualMoral(){
	
	if(modManualMoralCtrl.getDatosSalida()==undefined)
		return ;
	
	var objMDM = modManualMoralCtrl.getDatosSalida().mdmDatosEntrada;
	var moralResponse = objMDM.personaMoral;
	
	if(moralResponse==undefined)
		return; //Hubo error
	
	$('#moral\\.rfc').val(moralResponse.rfc);
	$('#moral\\.razonSocial').val(moralResponse.razonSocial);
	$('#moral\\.tipoSociedad\\.idTipoSociedad').val(moralResponse.tipoSociedad.idTipoSociedad);
	$('#moral\\.tipoSociedad\\.descripcion').val(moralResponse.tipoSociedad.descripcion);
	$('#moral\\.tipoSociedad\\.descripcionAbreviada').val(moralResponse.tipoSociedad.descripcionAbreviada);
	
	vaciarDomicilioFiscal('moral', moralResponse.domicilioFiscal);
	var domicilioCompleto = obtenerDescripcionDomicilio(moralResponse.domicilioFiscal);
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(moralResponse.mediosContactoFiscales);
	
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosMDM',objMDM,
			undefined, false);
	
	$("#divNombreComercial").show();
	$("#seccionMediosFiscales").show();

}


function configurarBusquedaICAMoral(){
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/moral/identificar/cambios-automaticos/identificar-cambios-automaticos.js", function(){  
		icaMoralCtrl = identificarCambiosAutomaticosPersonaMoralCtrl; 
		icaMoralCtrl.init('icaMoralDialog'); 
		icaMoralCtrl.datosEntrada.indMostrarPantalla = true; 
		icaMoralCtrl.datosEntrada.idPersona = $('#moral\\.idPersona').val();  
		icaMoralCtrl.setOnCloseCallback(fnOnICAMoralRetorno);  
		icaMoralCtrl.identificarCambios(); 
	});
	
}

function fnOnICAMoralRetorno(){
	if(icaMoralCtrl.getDatosSalida()==undefined){
//		solicitarConfirmacionModificacionManualMoral();
		parent.tramiteDRSCtrl.cerrar();
		return; //No hubo respuesta
	}
	var objICA = icaMoralCtrl.getDatosSalida();
	var moralResponse = objICA.personaMoralIMSS;
	
	if(moralResponse==undefined){
		if(objICA.traza.COMPARACION_SIN_DIFERENCIAS!=undefined){
			return;
		}else{
//			solicitarConfirmacionModificacionManualMoral();
			parent.tramiteDRSCtrl.cerrar();
			return; //Hubo error
		}
	}
	$('#moral\\.rfc').val(moralResponse.rfcSat);
	$('#moral\\.razonSocial').val(moralResponse.razonSocial);
	$('#moral\\.tipoSociedad\\.idTipoSociedad').val(moralResponse.tipoSociedad.idTipoSociedad);
	$('#moral\\.tipoSociedad\\.descripcion').val(moralResponse.tipoSociedad.descripcion);
	$('#moral\\.tipoSociedad\\.descripcionAbreviada').val(moralResponse.tipoSociedad.descripcionAbreviada);
	
	vaciarDomicilioFiscal('moral', moralResponse.domicilioFiscal);
	var domicilioCompleto = obtenerDescripcionDomicilio(moralResponse.domicilioFiscal);
	
	$('#textoDomicilio').text(domicilioCompleto);
	
	actualizaDatosFiscales(moralResponse.mediosContactoFiscales);
	
	/* ¡ESTO SÓLO ES POR CUESTIÓN DE PRUEBAS!
	 * Se quita el campo documentos probatorios, para que
	 * al momento de que Spring parse el objeto JSON al objeto de modelo 
	 * no truene
	 */
	delete objICA.personaMoralIMSS.documentosProbatorios;
	delete objICA.personaMoralIMSS.mediosContacto;
	delete objICA.personaMoralIMSS.mediosContactoFiscales;
	delete objICA.personaMoralIMSS.fechaModificacion;
	delete objICA.personaMoralIMSS.fechaRegistro;
	if(objICA.personaMoralIMSS.registroSindicato!=undefined)
		delete objICA.personaMoralIMSS.registroSindicato.fechaRegistro;
	
//	if(objICA.personaMoralIMSS.registroSindicato!=undefined && objICA.personaMoralIMSS.registroSindicato.fechaRegistro!=undefined)
//		objICA.personaMoralIMSS.registroSindicato.fechaRegistro = 
//			$.datepicker.formatDate('dd/MM/yyyy', objICA.personaMoralIMSS.registroSindicato.fechaRegistro);
	
	sendToServer('/afiliacion/almacenarTemporalmenteDatosICA',objICA,
			undefined, false);
	
}

function mostrarBusquedaICAFisica(){
	icaFisicaCtrl.identificarCambios(); 
}

function mostrarBusquedaICAMoral(){
	icaMoralCtrl.identificarCambios();
}


function vaciarDomicilioFiscal(txtTipoPersona, domicilioFiscal){
	
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.calle').val(domicilioFiscal.calle);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.colonia').val(domicilioFiscal.colonia);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.descripcion').val(domicilioFiscal.descripcion);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.numExterior1').val(domicilioFiscal.numExterior1);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.numExterior2').val(domicilioFiscal.numExterior2);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.numExteriorAlf').val(domicilioFiscal.numExteriorAlf);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.numInterior').val(domicilioFiscal.numInterior);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.numInteriorAlf').val(domicilioFiscal.numInteriorAlf);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.vialidadPrimaria\\.clave').val(domicilioFiscal.vialidadPrimaria.clave);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.vialidadPrimaria\\.nombre').val(domicilioFiscal.vialidadPrimaria.nombre);
	if(domicilioFiscal.vialidadPrimaria.tipoVialidad!=undefined){
		$('#'+txtTipoPersona+'\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(domicilioFiscal.vialidadPrimaria.tipoVialidad.clave);
		$('#'+txtTipoPersona+'\\.domicilioFiscal\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion);
	}
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.clave').val(domicilioFiscal.asentamiento.clave);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.nombre').val(domicilioFiscal.asentamiento.nombre);
	if(domicilioFiscal.asentamiento.codigoPostal!=undefined)
		$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(domicilioFiscal.asentamiento.codigoPostal.codigoPostal);
	else if(domicilioFiscal.codigoPostal!=undefined)
		$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.codigoPostal\\.codigoPostal').val(domicilioFiscal.codigoPostal.codigoPostal);
	
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.clave').val(domicilioFiscal.asentamiento.localidad.clave);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.nombre').val(domicilioFiscal.asentamiento.localidad.nombre);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.clave').val(domicilioFiscal.asentamiento.localidad.municipio.clave);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.nombre').val(domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave);
	$('#'+txtTipoPersona+'\\.domicilioFiscal\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
}

function obtenerDescripcionDomicilio(domicilioFiscal){
	var domicilioCompleto = domicilioFiscal.calle + ' #';
	if(domicilioFiscal.numExterior1!=undefined && domicilioFiscal.numExterior1!=null && domicilioFiscal.numExterior1!="")
		domicilioCompleto += domicilioFiscal.numExterior1;
	else if(domicilioFiscal.numExteriorAlf!=undefined && domicilioFiscal.numExteriorAlf!=null && domicilioFiscal.numExteriorAlf!="")
		domicilioCompleto += domicilioFiscal.numExteriorAlf;
		
	if(domicilioFiscal.numInterior!= undefined && domicilioFiscal.numInterior!=null && domicilioFiscal.numInterior!=0)
		domicilioCompleto += ' INTERIOR ' + domicilioFiscal.numInterior;
	else if(domicilioFiscal.numInteriorAlf!= undefined && domicilioFiscal.numInteriorAlf!=null && domicilioFiscal.numInteriorAlf!="")
		domicilioCompleto += ' INTERIOR ' + domicilioFiscal.numInteriorAlf;
			
	domicilioCompleto += ' COLONIA '+ domicilioFiscal.colonia + ', '+domicilioFiscal.asentamiento.localidad.municipio.nombre;
	domicilioCompleto += ', '+domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre;
	if(domicilioFiscal.asentamiento.codigoPostal!=undefined)
		domicilioCompleto += ', C\u00D3DIGO POSTAL '+domicilioFiscal.asentamiento.codigoPostal.codigoPostal;
	if(domicilioFiscal.codigoPostal!=undefined)
		domicilioCompleto += ', C\u00D3DIGO POSTAL '+domicilioFiscal.codigoPostal.codigoPostal;
	
	return domicilioCompleto;
}

function actualizaDatosFiscales(listaDatosContactoFiscales){
	oTableMediosFiscales.fnClearTable();
	oTableMediosFiscales.fnAddData(listaDatosContactoFiscales);
	oTableMediosFiscales.fnDraw();
}

function inicializaDatosContactoFiscales(){
	oTableMediosFiscales = $('#gridMediosFiscales').dataTable({
		bJQueryUI : false,
		bPaginate : true,
		bLengthChange : false,
		iDisplayLength : 4,
		bServerSide : false,
		bProcessing : false,
		sPaginationType : "full_numbers",
		bFilter : false,
		bSort : true,
		bInfo : false,
		bAutoWidth : true,
		aoColumns : columnasMedioContactoFiscales,
		sAjaxSource : '/delta-gestionPatronal-web/afiliacion/inicializarMediosFiscales',
		fnServerData : cargarDatosContactoFiscales
	});

	inicializaEstiloGrid($("#gridMediosFiscales tbody"), oTableMediosFiscales);

}

function cargarDatosContactoFiscales(sSource, aoData, fnCallback){
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function guardarNombreComercial() {
	inicializarObjetoTramite();
	sendToServer('/afiliacion/actualizarTramite?tipoTramite='+denominacionSocial+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackDenominacionTramite, false);

}


function callbackDenominacionTramite(response){
//	callbackEnviarTramite(response, 150, 750);
	procesarRespuestaServer(response, undefined, 150, 750);
	idSolicitud=response.idSolicitud;
//	evaluarBotonesSolicitud();
//	oTableTramites.fnDraw();
}


function inicializarObjetoTramite(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	
	var domicilioFiscal = obtenerDomicilioFiscal();
	
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		
		sujetoObigadoTramite.fisica.primerApellido=$("#fisica\\.primerApellido").val();
		sujetoObigadoTramite.fisica.segundoApellido=$("#fisica\\.segundoApellido").val();
		sujetoObigadoTramite.fisica.nombre=$("#fisica\\.nombre").val();
		sujetoObigadoTramite.fisica.curp=$("#fisica\\.curp").val();
		sujetoObigadoTramite.fisica.rfc=$("#fisica\\.rfc").val();
		
		sujetoObigadoTramite.fisica.idPersona=$("#fisica\\.idPersona").val();
		sujetoObigadoTramite.fisica.domicilioFiscal = domicilioFiscal;
		sujetoObigadoTramite.fisica.mediosContactoFiscales=obtenerListaMediosContactoFiscales();
		
		
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.tipoSociedad = new Object();
		
		//sujetoObigadoTramite.moral.nombreComercial=$("#moral\\.nombreComercial").val();
		sujetoObigadoTramite.moral.rfc=$("#moral\\.rfc").val();
		sujetoObigadoTramite.moral.razonSocial=$("#moral\\.razonSocial").val();
		//alert($("#moral\\.tipoSociedad\\.idTipoSociedad").val());
		//alert($("#moral\\.tipoSociedad\\.idTipoSociedad option:selected").text());
		sujetoObigadoTramite.moral.tipoSociedad.idTipoSociedad=$("#moral\\.tipoSociedad\\.idTipoSociedad").val();
		sujetoObigadoTramite.moral.tipoSociedad.descripcionAbreviada=$("#moral\\.tipoSociedad\\.idTipoSociedad option:selected").text();
		
		sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
		sujetoObigadoTramite.moral.domicilioFiscal = domicilioFiscal;
		sujetoObigadoTramite.moral.mediosContactoFiscales=obtenerListaMediosContactoFiscales();
	}
	sujetoObigadoTramite.domicilioFiscal=domicilioFiscal;
}

function obtenerDomicilioFiscal(){
	var domicilioFiscal = new Object();
	domicilioFiscal.vialidadPrimaria = new Object();
	domicilioFiscal.vialidadPrimaria.tipoVialidad = new Object();
	domicilioFiscal.asentamiento = new Object();
	domicilioFiscal.asentamiento.codigoPostal = new Object();
	domicilioFiscal.asentamiento.localidad = new Object();
	domicilioFiscal.asentamiento.localidad.municipio = new Object();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa = new Object();
	
	var origenDomicilio = null;
	if(esPatronFisico){
		origenDomicilio ='#fisica\\.domicilioFiscal\\.';
	}else{
		origenDomicilio ='#moral\\.domicilioFiscal\\.';	
	}
	domicilioFiscal.calle = $(origenDomicilio+'calle').val();
	domicilioFiscal.colonia = $(origenDomicilio+'colonia').val();
	domicilioFiscal.descripcion = $(origenDomicilio+'descripcion').val();
	domicilioFiscal.numExterior1 = $(origenDomicilio+'numExterior1').val();
	domicilioFiscal.numExterior2 = $(origenDomicilio+'numExterior2').val();
	domicilioFiscal.numExteriorAlf = $(origenDomicilio+'numExteriorAlf').val();
	domicilioFiscal.numInterior = $(origenDomicilio+'numInterior').val();
	domicilioFiscal.numInteriorAlf = $(origenDomicilio+'numInteriorAlf').val();
	domicilioFiscal.vialidadPrimaria.clave = $(origenDomicilio+'vialidadPrimaria\\.clave').val();
	domicilioFiscal.vialidadPrimaria.nombre = $(origenDomicilio+'vialidadPrimaria\\.nombre').val();
	domicilioFiscal.vialidadPrimaria.tipoVialidad.clave = $(origenDomicilio+'vialidadPrimaria\\.tipoVialidad.clave').val();
	domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion = $(origenDomicilio+'vialidadPrimaria\\.tipoVialidad.descripcion').val();
	domicilioFiscal.asentamiento.clave = $(origenDomicilio+'asentamiento\\.clave').val();
	domicilioFiscal.asentamiento.nombre = $(origenDomicilio+'asentamiento\\.nombre').val();
	domicilioFiscal.asentamiento.codigoPostal.codigoPostal = $(origenDomicilio+'asentamiento\\.codigoPostal\\.codigoPostal').val();
	domicilioFiscal.asentamiento.localidad.clave = $(origenDomicilio+'asentamiento\\.localidad\\.clave').val();
	domicilioFiscal.asentamiento.localidad.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.nombre').val();
	domicilioFiscal.asentamiento.localidad.municipio.clave = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.clave').val();
	domicilioFiscal.asentamiento.localidad.municipio.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.nombre').val();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val();
	domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre = $(origenDomicilio+'asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val();
	domicilioFiscal.descripcion=$('#textoDomicilio').text();
	
	return domicilioFiscal;
}

function obtenerListaMediosContactoFiscales(){
	var lista = [];
	var medioContacto;
	var dataOrigen = obtenerDatosGrid(oTableMediosFiscales);
	for(registro in dataOrigen){
		data = dataOrigen[registro]._aData;	
		medioContacto = new Object();
		medioContacto.idVista = data["idVista"];
		medioContacto.clave = data["clave"];
		medioContacto.desFormaContacto = data["desFormaContacto"];
			tipoMedioContacto = new Object();
			tipoMedioContacto.idTipoMedioContacto = data["tipoMedioContacto"]["idTipoMedioContacto"];
			tipoMedioContacto.descripcion = data["tipoMedioContacto"]["descripcion"];
		medioContacto.tipoMedioContacto = tipoMedioContacto;
		lista.push(medioContacto);
	}
	return lista;
};

function obtenerDatosGrid(grid){
	return grid.fnSettings().aoData;
}

function inicializarComponenteFirmaDigital(){
	$.getScript("/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js", function(){
		 firmaDigitalCtrl = FirmaDigitalCtrl;
		 
		 // Div para crear el diálogo
		 firmaDigitalCtrl.init('firmaDigitalDialogo');
		 
		 // Función de callback
		 firmaDigitalCtrl.setOnCloseCallback(procesarRespuestaFirmaDigital);
		});
	
	$('#btnConcluirConFirma').click(function(){
		 //Se settean los valores de entrada
		var rfcSujetoObligado=undefined;
		
		var registroPatronal = $('#numRegPatronal').val();
		
		if($("#fisica\\.rfc").val()!=undefined && $("#fisica\\.rfc").val()!="")
			rfcSujetoObligado = $("#fisica\\.rfc").val();
		else if ($("#moral\\.rfc").val()!=undefined && $("#moral\\.rfc").val()!="")
			rfcSujetoObligado = $("#moral\\.rfc").val();
		
		
		
		firmaDigitalCtrl.datosEntrada.rfc = rfcSujetoObligado;
		firmaDigitalCtrl.datosEntrada.nrp = rfcSujetoObligado;
		//TODO armar cadena original
		firmaDigitalCtrl.datosEntrada.contenido = rfcSujetoObligado;
		firmaDigitalCtrl.datosEntrada.firmarArchivo = false; 
		
		
		 // Se llama al servicio de firma digital
		firmaDigitalCtrl.firmaDigital();
	
	});

}

function procesarRespuestaFirmaDigital(response){
	var firmaResponse = firmaDigitalCtrl.getDatosSalida();
	if(firmaResponse!=undefined && firmaResponse!=null){
		var sSource = context_path + '/clasificacion/procesarDatosFirma';
		prepararRequest(sSource, firmaResponse, false, enviaSolicitudFirmada);
	}else{
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "La operaci\u00F3n de firma electr\u00F3nica no se realiz\u00F3 satisfactoriamente", true, undefined, undefined, 150, 450);
	}
}

function enviaSolicitudFirmada(){
	$.blockUI();
	inicializarObjetoTramite();
	if(idSolicitud==undefined)
		idSolicitud=0;
	sendToServer('/cmp/afiliacion/concluirEnLinea?tipoTramite='+tipoTramite+'&idSolicitud='+idSolicitud, sujetoObigadoTramite,
			callbackEnvioSolicitud, false);
}


function callbackEnvioSolicitud(response) {
	$.unblockUI();
	var respuesta = procesarRespuestaServer(response, callbackConfirmarEnvioSolicitud, 200, 650);
	
	if (respuesta) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen='+response.tipoDocumento;
		document.getElementById('formReporteModificacionPatronal').submit();
	}	
}

function callbackConfirmarEnvioSolicitud(response){			
	if (!error) {	
		cerrarDialogoTramite();
	}
}

function cerrarDialogoTramite(){
	parent.tramiteDRSCtrl.cerrar();
}
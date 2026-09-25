
var sIdDialogUbicarDomicilio = "#dgUbicarDomicilio";
var oDialogUbicarDomicilio;
var listaDatosContacto;
var firmaDigitalCtrl;

MedioContacto.prototype.extendValidation = function () {
    var mc = this;

    var _selectTipoFn = function() { return mc.jqSelectTipo; };
    var fnValidarDatos = mc.validarDatos;
    var alterFnValidarDatos = function(arg1, arg2) {
        this.validarDatos = fnValidarDatos;
        var _option = $([_selectTipoFn(), ' > ', 'option:selected'].join(''));
        if (/correo e|facebook|twitter/i.test(_option.text())) {
            var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
            $(mc.jqTxtFldDesc).val(_tmptxt);
            arg2 = _tmptxt
        }
        var retval = this.validarDatos(arg1, arg2);
        this.validarDatos = alterFnValidarDatos;
        return retval;
    };

    $.extend(mc, {validarDatos: alterFnValidarDatos});
}

$(function(){	
	if(idSolicitudActiva!=undefined && idSolicitudActiva!='' && idSolicitudActiva != null && idSolicitudActiva!=0){
		$("#infoSolicitud").show();
	}
	configurarBusquedaDomicilio();
	$("#btnGuardarDatosContacto").hide();
	$("#btnCancelarDatosContacto").hide();
	if (tramiteCentroTrabajoActivo){		
		modInterfaz=3;
		$("#buttonModificarVigente").hide();		
		$("#btnGuardarCentroTrabajo").hide();
		$("#btnCancelarCentroTrabajo").hide();
	}	  
	mc = new MedioContacto("mediosContactoContenedor", modInterfaz, tpPropietario, $("#cveIdSujetoObligado").val(), idSolicitudActiva, $("#cveIdSujetoObligado").val());
	mc.init();
    mc.extendValidation();
	listaDatosContacto=new Array();
	if(tramiteCentroTrabajoActivo){
		$("#divTramiteCT").attr("style","display:block");
		$("#btnGuardarCentroTrabajo").show();
		if(isOperadorIMSS){
			$("#btnFinalizarSol").hide();
			$("#btnConcluirSol").show();
			$("#btnCancelarSol").show();
		}else{
			$("#btnFinalizarSol").show();
			$("#btnConcluirSol").hide();
			$("#btnCancelarSol").show();
		}
	}else{
		$("#btnGuardarCentroTrabajo").hide();
		$("#btnConcluirSol").hide();
		$("#btnCancelarSol").hide();
		$("#btnFinalizarSol").hide();
	}
	
	$("#centroTrabajoForm #numeroRegistroPatronal").val($("#numeroRegistroPatronal").val());
	$("#centroTrabajoForm #tipoPersonaFiscal").val($("#tipoPersonaFiscal").val());	
	$("#centroTrabajoForm #cveIdSujetoObligado").val($("#cveIdSujetoObligado").val());	
	
//	if(mostrarInformacionCentroTrabajo){
//		$("#infoCentroTrabajo").show();
//		$("#divCentroTrabajoInexistente").hide();
//	}else{
//		$("#infoCentroTrabajo").hide();
//		$("#divCentroTrabajoInexistente").show();
//	}
	inicializarComponenteFirmaDigital();
});

function cancelarDC(){	
	mc.modoDespliegue(1);
	$("#btnCancelarDatosContacto").hide();
	$("#btnGuardarDatosContacto").hide();	
	//$("#buttonModificarCT").show();	
	$("#buttonModificarDC").show();	
}

function guardarDC(){	
	$("#buttonModificarDC").show();
	listaDatosContacto=mc.obtenerListaMediosContacto();
	guardarCT();
}

function guardarDatosContacto(){
	("ok");
}

function fnModificarDatosContacto(){
	$("#btnGuardarCentroTrabajo").show();	
	mc.modoDespliegue(3);
	$("#buttonModificarDC").hide();
}

function fnModificarCT(){
	$("#divTramiteCT").attr("style","display:block");
}

function fnCancelarModificarCT(){
	$("#divTramiteCT").attr("style","display:none");
}

function habilitarBotonesGuardarCancelar(){
	$("#btnGuardarCentroTrabajo").show();
	$("#btnCancelarCentroTrabajo").show();
}

function configurarBusquedaDomicilio(){
	/*configuracion para buscar un domicilio*/	
	var urlDomicilios = '/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/Domicilio.js';	
	$.getScript(urlDomicilios, function(script, textStatus){	
	}); 
			 
}

function fnOpenBuscarDomicilio() {
	$("div#showErrorForm").html("");
	DomicilioCtrl.init('domicilioLocaliza'); 
	DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
	DomicilioCtrl.localizar();
} 

function validarSubdOrigenSubdDestino(){

	var url="/afiliacion/validarReglasDelegDatosContacto";	
	$.blockUI();
	construirSujetoObligadoCentroTrabajo();
	sendToServer(url,sujetoObigadoTramite,
			callbackSubdOrigenSubdDestino, false);	
	}
	
function callbackSubdOrigenSubdDestino(response){		
	if (response.resultadoValidacion==true){
		$.unblockUI();
		
		if(isOperadorIMSS)
			fnConcluirSolicitud();
		else
			fnEnviarSolicitud(ejecutarEnvioDeSolicitud);
	}
	else{
		$.unblockUI();
		construirDialogoTramiteMensajeConfirmacion("Error al concluir la solicitud", response.mensaje, error, responseCallback, 500, 500, responseCallback);				
	}	
}	

function responseCallback(){	
}

			
var fnOnDomicilioReturn = function(){ 
	var d = this;		
	
	if (d!=null && d.vialidadPrimaria!=undefined){						
		$("#btnGuardarCentroTrabajo").show();
		$("#divCentroTrabajoInexistente").hide();
		$("#mensajeCentroInexistente").hide();
		$("#infoCentroTrabajo").show();
		if ( d.vialidadReferenciaPosterior!=undefined){
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val(d.vialidadReferenciaPosterior.nombre);
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val(d.vialidadReferenciaPosterior.clave);			
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPosterior.tipoVialidad.clave);
		}
		else{
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val("");
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val("");			
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val("");
		}
		
		if(d.vialidadReferenciaPrimaria!=undefined){
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val(d.vialidadReferenciaPrimaria.nombre);
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val(d.vialidadReferenciaPrimaria.clave);
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPrimaria.tipoVialidad.clave);
		}
		
		if(d.vialidadPrimaria!=undefined){
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.nombre").val(d.vialidadPrimaria.nombre);		
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.clave").val(d.vialidadPrimaria.clave);		
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val(d.vialidadPrimaria.tipoVialidad.clave);
		}
		
		if(d.vialidadReferenciaSecundaria!=undefined){
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val(d.vialidadReferenciaSecundaria.nombre);
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val(d.vialidadReferenciaSecundaria.clave);
			$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaSecundaria.tipoVialidad.clave);
		}
		
				
		$("#centroTrabajoForm #cntroTrabajo\\.codigoPostal\\.codigoPostal").val(d.codigoPostal.codigoPostal);		
		$("#centroTrabajoForm #cntroTrabajo\\.numExterior1").val(d.numExterior1);		
		$("#centroTrabajoForm #cntroTrabajo\\.numInterior").val(d.numInterior);		
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.clave").val(d.asentamiento.localidad.clave);		
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val(d.asentamiento.localidad.municipio.clave);
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val(d.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(d.asentamiento.localidad.municipio.entidadFederativa.clave);
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val(d.asentamiento.localidad.nombre);		
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val(d.asentamiento.localidad.municipio.nombre);
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.nombre").val(d.asentamiento.nombre);
		$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.clave").val(d.asentamiento.clave);
		$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val(d.vialidadPrimaria.tipoVialidad.descripcion);
		$("#centroTrabajoForm #cntroTrabajo\\.numExteriorAlf").val(d.numExteriorAlf);
		$("#centroTrabajoForm #cntroTrabajo\\.numInteriorAlf").val(d.numInteriorAlf);							
		$("#divTramiteCT").attr("style","display:block");
		habilitarBotonesGuardarCancelar();			
	}
	
}

function cancelarBuscarCentroTrabajo(){	
	$("#errorFormMCT").html("");
	$("#divTramiteCT").hide();
}
function buscarCentroTrabajo(){	
	oDialogUbicarDomicilio =  $( sIdDialogUbicarDomicilio ).dialog({
		autoOpen:false,
		resizable: false,
		width:800,
		height:500,
		modal: true,
		buttons: {
			'Aceptar': function() {
				if (validarFormulario()){
					$("div#showErrorForm").html("");
					habilitarBotonesGuardarCancelar();
					showTramite();
					$( this ).dialog( "close" );
				}
				else{
					$("div#showErrorForm").html("<font color=red>Por favor seleccione un domicilio v&aacute;lido con la opci&oacute;n Cargar datos de Domicilio</form>");
				}
			},
			'Cancelar':function (){
				$("div#showErrorForm").html("");
				$( this ).dialog( "close" );
			}			
		}
	});
	oDialogUbicarDomicilio.dialog('open');		
}

function navegar(formId, url){
	document.geElementById(formId).action=url;
	document.geElementById(formId).submit();
}

function validarFormulario(){
if ($("#modificarCentroTrabajoForm #modificarForm\\.codigoPostal").val()=="")
	return false;
return true;
}

function displayDomicilio(){
}


function desplegarAgregar(){
	var oSendData = new Object();
		oSendData.origen = 'delta';
		oSendData.tipoBusqueda = 3;
		oSendData.showAnterior=false;
		oSendData.showClase=true;
		oSendData.session = sessionId;   
	var valor =  window.showModalDialog('/${mvn.web.app.rootDomicilios}/domicilio/nacional/ubicar?JSESSIONID='+sessionId , oSendData, "dialogWidth:900px;dialogHeight:800px;status=yes,toolbar=no,menubar=no,location=no");
	if(valor==null){
		alert('sin valor');
	}
	alert("desplegarAgregar"+valor.asentamiento.clave);	
}

function desplegarModificar(){
	var oSendData = new Object();
		oSendData.origen = 'delta';
		oSendData.tipoBusqueda = 3;
		oSendData.showAnterior=false;
		oSendData.showClase=true;
		oSendData.session = sessionId;   
		var valor =  window.showModalDialog('/${mvn.web.app.rootDomicilios}/domicilio/nacional/ubicar?JSESSIONID='+sessionId , oSendData, "dialogWidth:900px;dialogHeight:800px;status=yes,toolbar=no,menubar=no,location=no");	
	if(valor==null){
		alert('sin valor');
	}
	alert("despleagarModificar"+valor.asentamiento.clave);
}

function loadDomicilios(){
}

function fnOnCloseDomicilio(){
	alert("close");
}

function cleanFormCT(){
	$("div#errorFormMCT").html("");
	$("#divTramiteCT").attr("style","display:none");
	$("#buttonModificarCT").attr("style","display:block");
}

function showTramite(){
	$("#divTramiteCT").show();
	$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.vialidadReferenciaPosterior").val());			
	$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.vialidadReferenciaPrimaria").val());		
	$("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.vialidadReferenciaSecundaria").val());	
	$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.vialidadPrimaria").val());		
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.codigoPostal\\.codigoPostal").val($("#modificarCentroTrabajoForm #modificarForm\\.codigoPostal").val());
	$("#centroTrabajoForm #cntroTrabajo\\.numExterior1").val($("#modificarCentroTrabajoForm #modificarForm\\.numeroExterior").val());
	$("#centroTrabajoForm #cntroTrabajo\\.numInterior").val($("#modificarCentroTrabajoForm #modificarForm\\.numeroInterior").val());
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.entidadFederativa").val());
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.localidad").val());
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.clave").val($("#modificarCentroTrabajoForm #modificarForm\\.localidad").val());
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.municipio").val());
	$("#centroTrabajoForm #cntroTrabajo\\.asentamiento\\.nombre").val($("#modificarCentroTrabajoForm #modificarForm\\.asentamiento").val());
	$("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val($("#modificarCentroTrabajoForm #modificarForm\\.tipoVialidad").val());
	$("#centroTrabajoForm #cntroTrabajo\\.numExteriorAlf").val($("#modificarCentroTrabajoForm #modificarForm\\.letraExterior").val());
	$("#centroTrabajoForm #cntroTrabajo\\.numInteriorAlf").val($("#modificarCentroTrabajoForm #modificarForm\\.letraInterior").val());
}

function guardarCT(){	
	$("div#errorFormMCT").html("");
	if ($("#centroTrabajoForm #cntroTrabajo\\.vialidadPrimaria\\.nombre").val()==""){
		$("#errorFormMCT").html("<center><font color=red>Por favor seleccione un domicilio v&aacute;lido con el bot&oacute;n Modificar Centro de Trabajo</font></center>");	
	}
	else{
		validaSolicitudTramiteActivo('/afiliacion/validarTramiteCentroTrabajo?idSolicitud='+idSolicitudActiva,callbackActualizacionCentroTrabajoTramite);		
	}
}
	
function callbackActualizacionCentroTrabajoTramite(response){
	callbackValidacionTramiteActivo(response, guardarCentroTrabajo);
}

function guardarCentroTrabajo() {	
	construirSujetoObligadoCentroTrabajo();
	sendToServer('/afiliacion/actualizarTramiteCentroTrabajo?idSolicitud='+idSolicitudActiva, sujetoObigadoTramite,
			callbackCentroTrabajoTramite, false);
}

function construirSujetoObligadoCentroTrabajo(){	

	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object(); // se queda como sujetoObigadoTramite en lugar de sujetoObligadoTramite
	}
	
	sujetoObigadoTramite.cveIdSujetoObligado=$("#centroTrabajoForm #cveIdSujetoObligado").val();
	
	sujetoObigadoTramite.numeroRegistroPatronal=$("#centroTrabajoForm #numeroRegistroPatronal").val();
	sujetoObigadoTramite.modalidad = new Object();
	sujetoObigadoTramite.modalidad.numModalidad=$("#centroTrabajoForm #modalidad\\.numModalidad").val();
	sujetoObigadoTramite.digVerificador=$("#centroTrabajoForm #digVerificador").val();
	
	sujetoObigadoTramite.tipoPersonaFiscal=new Object();
	sujetoObigadoTramite.tipoPersonaFiscal = tipoPersonaFiscal;
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona = $("#centroTrabajoForm #fisica\\.idPersona").val();
		sujetoObigadoTramite.fisica.rfc = $("#centroTrabajoForm #fisica\\.rfc").val();
		sujetoObigadoTramite.fisica.nombre = $("#centroTrabajoForm #fisica\\.nombre").val();
		sujetoObigadoTramite.fisica.primerApellido = $("#centroTrabajoForm #fisica\\.primerApellido").val();
		sujetoObigadoTramite.fisica.segundoApellido = $("#centroTrabajoForm #fisica\\.segundoApellido").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona = $("#centroTrabajoForm #moral\\.idPersona").val();
		sujetoObigadoTramite.moral.rfc = $("#centroTrabajoForm #moral\\.rfc").val();
		sujetoObigadoTramite.moral.razonSocial = $("#centroTrabajoForm #moral\\.razonSocial").val();
		sujetoObigadoTramite.moral.tipoSociedad=new Object();
		sujetoObigadoTramite.moral.tipoSociedad.idTipoSociedad=$("#centroTrabajoForm #moral\\.tipoSociedad\\.idTipoSociedad").val();
		sujetoObigadoTramite.moral.tipoSociedad.descripcion=$("#centroTrabajoForm #moral\\.tipoSociedad\\.descripcion").val();
		
	}	
		
	
	
	sujetoObigadoTramite.cntroTrabajo=new Object();
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria=new Object();
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPrimaria=new Object();
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaSecundaria=new Object();
		
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad=new Object();
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad=new Object();
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad=new Object();	
	
	sujetoObigadoTramite.cntroTrabajo.asentamiento=new Object();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad=new Object();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.codigoPostal=new Object();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio = new Object();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa = new Object();
		
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPrimaria.nombre=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaSecundaria.nombre=$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria.nombre=$("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val();		
	
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad.clave").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val();		
		
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPrimaria.clave=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaSecundaria.clave=$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val();		
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria.clave=$("#cntroTrabajo\\.vialidadPrimaria\\.clave").val();		
	
	if ($("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val()!=""){
		sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPosterior=new Object();
		sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad=new Object();
		sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPosterior.nombre=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val();		
		sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val();		
		sujetoObigadoTramite.cntroTrabajo.vialidadReferenciaPosterior.clave=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val();		
	}
	
	sujetoObigadoTramite.cntroTrabajo.cveIdPatronSujetoObligado=sujetoObigadoTramite.moral.idPersona;	
	
	sujetoObigadoTramite.cntroTrabajo.codigoPostal= new Object();
	sujetoObigadoTramite.cntroTrabajo.codigoPostal.codigoPostal=$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val();
	sujetoObigadoTramite.cntroTrabajo.numExterior1=$("#cntroTrabajo\\.numExterior1").val();
	sujetoObigadoTramite.cntroTrabajo.numInterior=$("#cntroTrabajo\\.numInterior").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val();
	
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.nombre=$("#cntroTrabajo\\.asentamiento\\.nombre").val();
	sujetoObigadoTramite.cntroTrabajo.asentamiento.clave=$("#cntroTrabajo\\.asentamiento\\.clave").val();
	sujetoObigadoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion=$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val();
	sujetoObigadoTramite.cntroTrabajo.numExteriorAlf=$("#cntroTrabajo\\.numExteriorAlf").val();
	sujetoObigadoTramite.cntroTrabajo.numInteriorAlf=$("#cntroTrabajo\\.numInteriorAlf").val();
	sujetoObigadoTramite.cntroTrabajo.mediosContacto=new Object();
	sujetoObigadoTramite.nombreComercial=$("#nombreComercial").val();		
		
	listaDatosContacto=mc.obtenerListaMediosContacto();		
	sujetoObigadoTramite.cntroTrabajo.mediosContacto=listaDatosContacto;	

}

function callbackCentroTrabajoTramite(response){
	$("#dialogoConfirmacion").dialog( "close" );		
	$("#btnCancelarCentroTrabajo").hide();		
	callbackEnviarTramite(response);
	
	if(isOperadorIMSS)
		$("#btnConcluirSol").show();
	else
		$("#btnFinalizarSol").show();
	
	$("#btnCancelarSol").show();		
	idSolicitudActiva=response.idSolicitudCT;
	if(idSolicitudActiva!=undefined && idSolicitudActiva!=0){
		$("#noFolioActual").text(response.folioSolicitud);
		$("#infoSolicitud").show();
	}
	//alert(idSolicitudActiva);
	//oTableTramites.fnDraw();
}

function fnCancelarSolicitud(){
	$.blockUI();
	construirSujetoObligadoCentroTrabajo();
	var solicitudObj = new Object();
	solicitudObj.solicitudId=idSolicitudActiva;
	solicitudObj.sujetoObligado = new Object();
	solicitudObj.sujetoObligado = sujetoObigadoTramite;
	sendToServer('/afiliacion/cancelarSolicitud', solicitudObj,
			callbackCancelarSolicitudCentroTrabajo, true);
}

function callbackCancelarSolicitudCentroTrabajo(response) {
	procesarRespuestaServer(response, callbackConfirmarCancelacionSolicitud);
	$.unblockUI();
}

function callbackConfirmarCancelacionSolicitud(response){
	if (!error) {
		idSolicitudActiva=null;
		tramiteCentroTrabajoActivo=false;
		$("#divTramiteCT").hide();
		$("#btnGuardarCentroTrabajo").hide();
		$("#btnConcluirSol").hide();
		$("#btnCancelarSol").hide();
		$("#noFolioActual").text("");
		$("#infoSolicitud").hide();
		mc.modoDespliegue(1);
	}
}


function fnConcluirSolicitud(){
	obtenerRepresentantesEnTramite();
}

function callbackSolicitadoPor(response){
	var selectedRadio = getSelectedRadioButton(document.getElementsByName("radioSolicitadoPor"));
	var idSolicitante=$("#solicitadoPorForm #idRepresentante").val();
	var validacion=true;
	if(selectedRadio.value==rolRepresentante){
		if(idSolicitante==-1){
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe seleccionar el nombre del representante legal que se ha presentado en ventanilla y solicitado la conclusi\u00F3n.", true, callbackValidacionSeleccionadoPor, undefined, 150, 450);
			validacion = false;
		}
	}
	
	if(validacion){
		$.blockUI();
		construirSujetoObligadoCentroTrabajo();
		sendToServer('/afiliacion/concluirSolicitud?idSolicitud='
				+ idSolicitudActiva+'&rolSolicitante='+selectedRadio.value+'&idSolicitante='+idSolicitante, sujetoObigadoTramite,
				callbackConcluirSolicitudCentroTrabajo, true);
	}
	
}


function callbackConcluirSolicitudCentroTrabajo(response) {
	$.unblockUI();
	var respuesta = procesarRespuestaServer(response, callbackConfirmarConclusionSolicitud,150, 450);
	
	if (respuesta) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen=AVISO';
		document.getElementById('formReporteModificacionPatronal').submit();
	}
}

function callbackConfirmarConclusionSolicitud(response){
	if(!error){
		idSolicitudActiva=0;
		//tramiteCentroTrabajoActivo=false;		
		//$("#divTramiteCT").hide();
		//mc.modoDespliegue(1);		
		//alert($("#centroTrabajoForm #numeroRegistroPatronal").val());
		var rfcParam;
		if (esPatronFisico) {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
		'centroTrabajoForm');
		
//		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
//		'centroTrabajoForm');
	}
}

function navegarABusquedaRFC(){
	navegarTo("/sujetoObligado", "busquedaRFCForm");
}

function navegarToDetalleRFC(){
	$("#detalleRFCForm").submit();
}


function fnEnviarSolicitud(funcionEjecturar) {
	$("#textoConfirmacion")
			.html(
					"Una vez enviada la solicitud al Instituto usted no podr\u00E1 realizar modificaciones.<br>"
							+ "Favor de confirmar la Finalizaci\u00F3n de la Captura/Edici\u00F3n de la Solicitud.<br> ");
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 650,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function ejecutarEnvioDeSolicitud() {	
	//Se muestra la ventana para que el usuario pueda firmar digitalmente
	dialogFirma =  $("#dialogoConcluirSolicitudFirma").dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true		
	});
	$("#dialogoConcluirSolicitudFirma").css("display", "block");
	dialogFirma.dialog('open');
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
	dialogFirma.dialog('close');		
	$.blockUI();
	construirSujetoObligadoCentroTrabajo();
	sendToServer('/afiliacion/enviarSolicitudCentroTrabajo', sujetoObigadoTramite,
			callbackEnvioSolicitud, false);
}


function ejecutarEnvioDeSolicitudSinFirma(){
	dialogFirma.dialog('close');		
	$.blockUI();
	construirSujetoObligadoCentroTrabajo();
	sendToServer('/afiliacion/enviarSolicitudCentroTrabajo', sujetoObigadoTramite,
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
		$("#btnFinalizarSol").hide();
		if (isOperadorIMSS) {
			$("#btnConcluirSol").show();
		}

		var rfcParam;
		if (esPatronFisico) {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

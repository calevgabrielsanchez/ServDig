var FirmaDigitalCtrl = {
	dialogo : {},
	init : function(idContenedor){
		//this.urlFormularioFirma = '/firmaElectronicaWeb/widget/imss';
		this.urlFormularioFirma = '/firmaElectronicaWeb/widget/chfecyn';
	    this.config.contenedor = idContenedor;	    var d = $('#' + idContenedor);
	    this.dialogo = d.dialog({
	        title : this.config.title,
	        closeOnEscape: false,
	        autoOpen : false,
	        width : 1000,
	        height : 500,
	        modal : true,
	        resizable : false,
	        autoResize : true,
	        overlay : {
	            opacity : 0.5,
	            background : "black"
	        }
	    });
	    // Configuramos el metodo onClose del dialogo
	    this.dialogo.dialog({
	    	close : function(event, ui) {
	    		FirmaDigitalCtrl.limpiarElementosSesion();
	    		FirmaDigitalCtrl.callbacks.call();
	    }});
	},
	firmaDigital : function() {		var params = new Object(),
		formFirmaDigital = '<form action="' + this.urlFormularioFirma
		+ '" method="post" target="formFirmaDigital" id="firmarForm" >'
		+ '<input type="hidden" id="params" name="params" readonly="readonly">'
		+ '</form>';
		$('body').append(formFirmaDigital);
		params.operacion = this.datosEntrada.operacion == 'autentica' ? "validarCertificado" : this.datosEntrada.operacion;
		params.aplicacion = this.datosEntrada.aplicacion;
		params.acuse = this.datosEntrada.acuse;
		params.rfc = this.datosEntrada.rfc;
		params.val_rfc = this.datosEntrada.val_rfc;
		params.curp = this.datosEntrada.curp;
		params.firma_archivo = this.datosEntrada.firma_archivo;
		params.max_archivos = this.datosEntrada.max_archivos;
		params.min_archivos = this.datosEntrada.min_archivos;
		params.tipo_archivos = this.datosEntrada.tipo_archivos;
		params.cad_original = this.datosEntrada.cad_original;
		params.forma_firma_archivos = this.datosEntrada.forma_firma_archivos;
		params.contenedores = this.datosEntrada.contenedores;
		params.id_firmas = this.datosEntrada.id_firmas;
		params.salida = this.datosEntrada.salida;
		params.origen = this.datosEntrada.origen;				params.registroPatronal = this.datosEntrada.registroPatronal;		params.nombreRazonSocial = this.datosEntrada.nombreCompleto;		params.idTipoSolicitud = this.datosEntrada.idTipoSolicitud;		params.descripcionTipoSolicitud = this.datosEntrada.descripcionTipoSolicitud;		params._fechaElectronica = this.datosEntrada.fechaElectronica;		params._folioSolicitud = this.datosEntrada.folioSolicitud;
		//se agregan los filtros necesarios		params.filtros = ".pdf";

		// Transformar los datos a json
		var json =JSON.stringify(params);
		$('#params').val(json);
		$('#' + this.config.contenedor).html('<iframe id="formFirmaDigital" name="formFirmaDigital" width="100%" height="100%" />');
		this.setDatosSalida(null);
		$('#firmarForm').submit();

		$('#formFirmaDigital').load(function(){
		    $('#formFirmaDigital').contents().find('#contenedor_titulo').hide();
		});
		this.dialogo.dialog('open');
	},
	config : {
		url : "/gestionSolicitud-web/firma-digital/init",
		title : "Firma Digital",
		contenedor : {}
	},
	callbacks : {},
	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
	datosEntrada : {
		operacion: '',
		aplicacion: '',
		origen: '',
		acuse: '',
		rfc: '',
		val_rfc: '',
		curp: '',
		firma_archivo: '',
		max_archivos: '',
		min_archivos: '',
		tipo_archivos: '',
		cad_original: '',
		forma_firma_archivos: '',
		contenedores: '',
		id_firmas: '',
		salida: ''
	},
	datosSalida : {},
	setDatosEntrada : function(objDatosEntrada){
		this.datosEntrada = objDatosEntrada;
	},
	getDatosSalida : function (){
		return this.datosSalida;
	},
	setDatosSalida : function(objDatosSalida){
		this.datosSalida = objDatosSalida;
	},
	// Funcion para el control de cerrado de la pagina
	cerrar : function(){
		this.dialogo.dialog('close');
	},	
	limpiarElementosSesion: function (){		$.postJSON('/gestionSolicitud-web/firma-digital/limpiar-firma-digital', null, function(data) {	
		});
	},
	procesarResultadoFirma: function(resultadoFirma){
		var resultadoJSON = $.parseJSON(resultadoFirma.data);
		resultadoJSON = FirmaDigitalCtrl.parseNewComponente(resultadoJSON);
		FirmaDigitalCtrl.setDatosSalida(resultadoJSON);
		FirmaDigitalCtrl.cerrar();
	},
	parseNewComponente: function(response) {
		response.Resultado = response.resultado;
		
		if(response.vigIni && response.vigIni.length == 10) {
			response.vigIni = response.vigIni + " 00:00:00 Z";
		}
		
		if(response.vigFin && response.vigFin.length == 10) {
			response.vigFin = response.vigFin + " 00:00:00 Z";
		}
		
		return response;
	}
};
if (window.addEventListener) {
   addEventListener("message", FirmaDigitalCtrl.procesarResultadoFirma, false);
} else { 
   attachEvent("onmessage", FirmaDigitalCtrl.procesarResultadoFirma);
}

//$.cometd.websocketEnabled = false;	//Funcion generica para firmar documentosvar firma,
objMensajeFirma,
TRAMITE_CORRECCION=1,
TRAMITE_PRORROGA=2,
TRAMITE_PRESENTACION=3;function firmarDocumentoSISCONET(idContenedor,fncCallback,params){	firma=FirmaDigitalCtrl;	firma.init(idContenedor);	firma.setOnCloseCallback(fncCallback);	firma.setDatosEntrada(params);			firma.firmaDigital();	}function firmarDocumentoSelloImss(fncCallback){	fncCallback.call();}function recuperaMensaje(cveTramite){	var msj;	var obje='{"cveTramite":"'+cveTramite+'"}';	var valor = jQuery.parseJSON(obje);	$.postJSON_Sync("correcion/obtenerMesajeTramite.do", valor, function(data) {		msj=data.desMensajes;		objMensajeFirma=data;	}).error(function(data){		desbloquear();				validarSesionExpirada(data);		alert("error" + data);	}).complete(function(data){							});		return msj;}
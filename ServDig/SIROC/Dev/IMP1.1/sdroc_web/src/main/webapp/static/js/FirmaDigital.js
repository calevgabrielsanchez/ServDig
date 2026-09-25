$.getScript("/gestionDocumentoProbatorio-web/static/resources/js/wizard/muestraDocumentosRequeridosWizard.js");

var FirmaDigitalCtrl = {

	dialogo : {},
	
	init : function(idContenedor, idContenedorDoctos){
		this.id_Form = 0;
		this.urlFormularioFirma = '/firmaElectronicaWeb/widget/imss';

		this.config.contenedor = idContenedor;
		this.config.contenedorDoctos = idContenedorDoctos;

		var d = $('#' + idContenedor);

	    // Configuracion del dialogo
		this.dialogo = d.dialog({
			title : this.config.title,
			//dialogClass: "no-close",
			closeOnEscape : false,
			autoOpen : false,
			width : 830,
			height : 570,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
            position: { my: "top", at: "top", of: window, offset: "0 10" }
		});

	    // Configuramos el metodo onClose del dialogo
		this.dialogo.dialog({
			close : function(event, ui) {
				FirmaDigitalCtrl.limpiarElementosSesion();
				
				if (jQuery.isFunction(FirmaDigitalCtrl.callbacks)) {
					FirmaDigitalCtrl.callbacks.call();
				}
			}
		});
	},


	iniciarFlujoFirmaDigital : function(urlFormularioFirma) {
		var formFirmaDigital = '<form action="' + urlFormularioFirma
		+ '" method="post" target="formFirmaDigital" id="firmarForm_' + this.id_Form + '" >'
		+ '<input type="hidden" id="params' + this.id_Form + '" name="params" readonly="readonly">'
		+ '</form>';
		$('body').append(formFirmaDigital);

		this.config.firmaEjecutada = false;
		
		var params = new Object();
		var extensiones=undefined;
		if(this.datosEntrada.filtros != undefined)
			extensiones=this.datosEntrada.filtros;
		else
			extensiones="Archivos IMSS Digital(pdf)^pdf";
		
		params.operacion = this.datosEntrada.operacion;
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
		params.origen = this.datosEntrada.origen;
		params.curp = this.datosEntrada.curp;
		params.registroPatronal = this.datosEntrada.registroPatronal;
		params.nombreRazonSocial = this.datosEntrada.nombreCompleto;
		params.idTipoSolicitud = this.datosEntrada.idTipoSolicitud;
		params.descripcionTipoSolicitud = this.datosEntrada.descripcionTipoSolicitud;
		params._fechaElectronica = this.datosEntrada.fechaElectronica;
		params._folioSolicitud = this.datosEntrada.folioSolicitud;
		params.filtros = extensiones;
		params.afectado = this.datosEntrada.afectado;
		params.representados = this.datosEntrada.representados;
		params.domicilioActualizado= this.datosEntrada.domicilioActualizado;
		params.afectadoNuevosValores= this.datosEntrada.afectadoNuevosValores;
		params.mediosContacto= this.datosEntrada.mediosContacto;
		params.tipoAcuse= this.datosEntrada.tipoAcuse;
		params.acuse = this.datosEntrada.acuse;

		// Transformar los datos a json
		var json =JSON.stringify(params);
		$('#params' + this.id_Form).val(json);

		$('#' + this.config.contenedor)
				.html('<iframe id="formFirmaDigital" name="formFirmaDigital" '
						+ 'width="100%" height="100%"'
						+ 'onload="\'formFirmaDigital\'" />');
		
		this.setDatosSalida(null);
		$('#firmarForm_'+ this.id_Form).submit();
		
		this.id_Form++;
		this.dialogo.dialog('open');
	},

	//Metodo para invocar la funcionalidad de la firma
	firmaDigital : function() {
		var idTipoTramite = FirmaDigitalCtrl.datosEntrada.idTipoTramite;
		
		var request = $.ajax({
			url : '/gestionSolicitud-web/firma-digital/validarTipoTramite/' + idTipoTramite,
			async : true,
			type : "POST",
			data : null,
			dataType : "json",
			cache: false,
			contentType : "application/json; charset=utf-8"
		});

		request.done(function(response) {
			var urlAction = FirmaDigitalCtrl.urlFormularioFirma;

			if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrl.datosEntrada.operacion == 'firmaCMS'
				&& FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
				urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
			} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrl.datosEntrada.operacion == 'firmaCMS'
				&& FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
				urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
			}

			if(FirmaDigitalCtrl.datosEntrada.operacion == 'firmaCMS'){
				var idTipoTramite = FirmaDigitalCtrl.datosEntrada.idTipoTramite;
				
				
				if(FirmaDigitalCtrl.datosEntrada.firma_archivo) {
					WizardMuestraDocumentosRequeridosCtrl.setOnCloseCallback(function() {
						var urlAction = FirmaDigitalCtrl.urlFormularioFirma;
						if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
							urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
						} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
							urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
						}

						FirmaDigitalCtrl.iniciarFlujoFirmaDigital(urlAction);
					});
					
					WizardMuestraDocumentosRequeridosCtrl.init(FirmaDigitalCtrl.config.contenedorDoctos, idTipoTramite);
					WizardMuestraDocumentosRequeridosCtrl.abrir();
				} else {
					var urlAction = FirmaDigitalCtrl.urlFormularioFirma;
					
					if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
						urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
					} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos){
						urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
					}

					FirmaDigitalCtrl.iniciarFlujoFirmaDigital(urlAction);
				}
			} else {
				FirmaDigitalCtrl.iniciarFlujoFirmaDigital(urlAction);
			}
		});

		request.fail(function(response) {});	
	},

	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionSolicitud-web/firma-digital/init",
//		url : "http://11.254.20.105:8080/webOPE/pages/imss.html",
		title : "Firma digital ",
		contenedorDoctos : {},
		contenedor : {},
		firmaEjecutada : false
	},

	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},

	// Objeto con los atributos para los par�metros de entrada
	datosEntrada : {
		operacion : '',
		aplicacion : '',
		origen : '',
		acuse : '',
		rfc : '',
		val_rfc : '',
		curp : '',
		firma_archivo : '',
		max_archivos : '',
		min_archivos : '',
		tipo_archivos : '',
		cad_original : '',
		forma_firma_archivos : '',
		contenedores : '',
		id_firmas : '',
		salida : '',
		idTipoSolicitud : '',
		idTipoTramite : '',
		descripcionTipoSolicitud : '',
		folioSolicitud : '',
		curp : '',
		registroPatronal : '',
		nombreCompleto : '',
		fechaElectronica : '',
		mostrarCartaTerminos : false,
		afectado: null,
		representados: null,
		domicilioActualizado: null,
		afectadoNuevosValores: null,
		mediosContacto: null,
		tipoAcuse: '',
		acuse: ''
	},

	//Objeto de salida
	datosSalida : {},

	setDatosEntrada : function(objDatosEntrada){
		this.datoEntrada = objDatosEntrada;
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

	/*
	 * Funci�n para limpiar los elementos en sesi�n, se puso aqu�
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la funci�n de callback de cada gesti�n en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		// Se limpia la sesi�n de la administraci�n de domicilios
		$.postJSON('/gestionSolicitud-web/firma-digital/limpiar-firma-digital', null, function(data) {

		});	
	}
};

function resultadoWidgetFirmaElectronica(data) {
	
	if (!FirmaDigitalCtrl.config.firmaEjecutada) {
		FirmaDigitalCtrl.config.firmaEjecutada = true;

		// Los datos de salida vienen como un string por lo hace falta convertirlo a jSON
		var resultadoJSON = $.parseJSON(data);
		FirmaDigitalCtrl.setDatosSalida(resultadoJSON);
		
		if(FirmaDigitalCtrl.datosEntrada.operacion == 'firmaCMS'
			&& FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida.acuse
			&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
//			window.open(FirmaDigitalCtrl.datosSalida.acuse);
		}
		
		FirmaDigitalCtrl.cerrar();
	}
}

if (window.addEventListener) {
	addEventListener("message", resultadoWidgetFirmaElectronica, false);
} else {
	attachEvent("onmessage", resultadoWidgetFirmaElectronica);
}

if (typeof $.cometd !== 'undefined') {
	// Para inhibir el uso de WebSockets...
	$.cometd.websocketEnabled = false;
}

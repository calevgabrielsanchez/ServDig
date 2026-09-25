$.getScript("/gestionDocumentoProbatorio-web/static/resources/js/wizard/muestraDocumentosRequeridosWizard.js");

var FirmaDigitalCtrl = {

	dialogo : {},
	init : function(idContenedor, idContenedorDoctos){
		this.id_Form = 0;
		//this.urlFormularioFirma = '/firmaElectronicaWeb/widget/imss';
		this.urlFormularioFirma = '/firmaElectronicaWeb/widget/chfecyn';
		this.config.contenedor = idContenedor;
		this.config.contenedorDoctos = idContenedorDoctos;

		var d = $('#' + idContenedor);

	    // Configuracion del dialogo
		this.dialogo = d.dialog({
			title : this.config.title,
			//dialogClass: "no-close",
			closeOnEscape : false,
			autoOpen : false,
			width : 1000,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
            position: { my: "top", at: "top", of: window, offset: "0 10" },
            buttons: [
			    {
				  id: "cancela-tramite",	
			      text: "Cancelar",
			      click: function() {
					var btnFinalizaTramite = document.getElementById('finaliza-tramite');
					btnFinalizaTramite.disabled = true;
					btnFinalizaTramite.className = 
						"ui-button ui-widget ui-state-default ui-corner-all ui-button-disabled ui-state-disabled ui-button-text-only";
					$(".ui-dialog").dialog("close");
			        $(this).dialog("close");
			      }
			    },
			    {
				  id: "finaliza-tramite",	
			      text: "Finalizar Tr\u00E1mite",
			      disabled: true,
			      click: function() {
					$("#registroAseguradoDatosBasicosForm").submit();
					FirmaDigitalCtrl.cerrar();
				  }	 
			    }
			]
		});

	    // Configuramos el metodo onClose del dialogo
		this.dialogo.dialog({
			close : function(event, ui) {
				FirmaDigitalCtrl.limpiarElementosSesion();
				if (jQuery.isFunction(FirmaDigitalCtrl.callbacks)) {
					var btnFinalizaTramite = document.getElementById('finaliza-tramite');
					btnFinalizaTramite.disabled = true;
					btnFinalizaTramite.className = 
						"ui-button ui-widget ui-state-default ui-corner-all ui-button-disabled ui-state-disabled ui-button-text-only";
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
			extensiones= this.datosEntrada.filtros == "Archivos IMSS Digital(pdf)^pdf" ? ".pdf" : this.datosEntrada.filtros;
		else
			extensiones=".pdf";
		console.log("La operacion es " + this.datosEntrada.operacion);
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
		this.setDatosSalida(null);
		/*
		var contenedor = this.config.contenedor;
		$.ajax({url: urlFormularioFirma,
			type: "POST",
	        data : {params: json},
	        success: function (result) {
	        	$('#' + contenedor).html(result);
	        }
		});*/
		
		$('#params' + this.id_Form).val(json);

		$('#' + this.config.contenedor)
				.html('<iframe id="formFirmaDigital" name="formFirmaDigital" '
						+ 'width="100%" height="100%"'
						+ 'onload="set_size(\'formFirmaDigital\')" frameBorder="0" />');
		$('#firmarForm_'+ this.id_Form).submit();
		
		$('#formFirmaDigital').load(function(){
		    $('#formFirmaDigital').contents().find('#contenedor_titulo').hide();
		});
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
		$("#"+this.config.contenedor).html('');
		this.dialogo.dialog('close');
	},

	/*
	 * Funcion para limpiar los elementos en sesion, se puso aqui
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la funci�n de callback de cada gesti�n en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		// Se limpia la sesion de la administracion de domicilios
		$.postJSON('/gestionSolicitud-web/firma-digital/limpiar-firma-digital', null, function(data) {

		}).error(function(data){
			
		});	
	},
	procesarResultadoFirma: function(resultadoFirma){
		
		if (!FirmaDigitalCtrl.config.firmaEjecutada) {
			FirmaDigitalCtrl.config.firmaEjecutada = true;
			// Los datos de salida vienen como un string por lo hace falta convertirlo a jSON
			var resultadoJSON = $.parseJSON(resultadoFirma.data);
			//"traducimos" la nrespues del nuevo componente de firma a lo que espera la anterior 
			resultadoJSON = FirmaDigitalCtrl.parseNewComponente(resultadoJSON);
			console.log("La respuesta de la firma es " + resultadoFirma.data);
			FirmaDigitalCtrl.setDatosSalida(resultadoJSON);
			
			if (resultadoJSON.resultado == 0 && resultadoJSON.texto == 'EXITO') {
				var btnFinalizaTramite = document.getElementById('finaliza-tramite');
				btnFinalizaTramite.disabled = false;
				btnFinalizaTramite.className = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
				btnFinalizaTramite.removeAttribute('aria-disabled');
			} else {
				console.log("Respuesta incorrecta de firma:");
				console.log("resultado= ", resultadoJSON.resultado);
				console.log("texto= " + resultadoJSON.texto);
			}
		}
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

if (typeof $.cometd !== 'undefined') {
	// Para inhibir el uso de WebSockets...
	$.cometd.websocketEnabled = false;
}

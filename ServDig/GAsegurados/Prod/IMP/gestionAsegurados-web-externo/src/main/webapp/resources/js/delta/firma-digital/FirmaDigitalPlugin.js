$.getScript("/gestionDocumentoProbatorio-web/static/resources/js/wizard/muestraDocumentosRequeridosWizard.js");

/*
 * JS para el control del acceso por medio de los servicios REST
 */

/**
 * Variables de error
 * 401 Invalid password   - com.sun.identity.idsvcs.InvalidPassword
 * 403 Maximum Sessions Limit Reached. - com.sun.identity.idsvcs.MaximumSessionReached
 * 401 - com.sun.identity.idsvcs.InvalidCredentials
 */
var invalidCredentials ='com.sun.identity.idsvcs.InvalidCredentials';
var invalidPassword = 'com.sun.identity.idsvcs.InvalidPassword';
var numberSessionsOpened = 'com.sun.identity.idsvcs.MaximumSessionReached';

var AuthenticateSSO = {
		
		authenticate: function(user_name, pass){
			var url = "/openam_10.0.0/identity/json/authenticate?_=" + new Date().getTime();
			
			var data = {
					'username' : user_name,
					'password': pass,
                    'hash': new Date().getTime()
			};
			
			$.getJSON(url , data , function(data){
				
				AuthenticateSSO.validateToken(data.tokenId);
			}).error(function(req,status,error){
				var errorJSON = $.parseJSON(req.responseText);
				setMensajeError(errorJSON.exception.name);
				dialogMensajes.dialog('open');
			});
			
		}, 
		
		validateToken : function(token){
			var url = "/openam_10.0.0/identity/json/isTokenValid";
			
			var data ={
					'tokenid' : token,
                    'hash': new Date().getTime()
			};
			$.getJSON(url , data , function(jsondata){
                AuthenticateSSO.setCookie(token);
                AuthenticateSSO.callbacks.call();
			});
		}, 
		
		setCookie : function(cookie_val){
			var options = {
					'path': '/',
					'domain':'.imss.gob.mx'
			};
			$.cookie( 'iPlanetDirectoryPro', cookie_val, options );
		}, 
		
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		}
		
};

function setMensajeError(exceptionName) {
	var mensaje = "";
	$("#mensajeDialogo").html(mensaje);
	
	if(exceptionName == invalidPassword) {
		mensaje = "El password es incorrecto";
	} else if(exceptionName == numberSessionsOpened) {
		mensaje = "El n&uacute;mero m&aacute;ximo de sesiones abiertas ha sido alcanzado.";
	} else {
		mensaje = "Para poder ingresar, debe estar registrado como usuario.";
	}
	
	$("#mensajeDialogo").html(mensaje);
}

var FirmaDigitalCtrl = {

	dialogo : {},
	init : function(idContenedor, idContenedorDoctos){
		this.id_Form = 0;
		this.urlFormularioFirma = '/firmaElectronicaWeb/widget/imss';

		this.config.contenedor = idContenedor;
		this.config.contenedorDoctos = idContenedorDoctos;
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
						+ 'onload="set_size(\'formFirmaDigital\')" frameBorder="0" />');
		
		this.setDatosSalida(null);
		$('#firmarForm_'+ this.id_Form).submit();
		
		this.id_Form++;
		//this.dialogo.dialog('open');
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

		}).error(function(data){
			
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
		
		FirmaDigitalCtrl.limpiarElementosSesion();
		
		if (jQuery.isFunction(FirmaDigitalCtrl.callbacks)) {
			FirmaDigitalCtrl.callbacks.call();
		}
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

//Funcion inicializar la firma digital
var iniciarFirmaDigital = function (componenteFirma) {
	//Se settean los valores de entrada
	FirmaDigitalCtrl.datosEntrada.rfc = componenteFirma.rfc;
	FirmaDigitalCtrl.datosEntrada.val_rfc = componenteFirma.validarRFC;
	FirmaDigitalCtrl.datosEntrada.cad_original = componenteFirma.cad_original;
	FirmaDigitalCtrl.datosEntrada.firma_archivo = componenteFirma.firma_archivo;
	FirmaDigitalCtrl.datosEntrada.min_archivos = componenteFirma.min_archivos;
	FirmaDigitalCtrl.datosEntrada.max_archivos = componenteFirma.max_archivos;
	
	FirmaDigitalCtrl.datosEntrada.origen = server_name;
	FirmaDigitalCtrl.datosEntrada.operacion = componenteFirma.tipo_operacion;//"autentica";
	FirmaDigitalCtrl.datosEntrada.aplicacion="portalimssdigital";
	FirmaDigitalCtrl.datosEntrada.salida="rfc,curp,rfc_rl,curp_rl,serie_cert,contenedores,acuse,firmas,vigencias";   
	FirmaDigitalCtrl.datosEntrada.acuse = 'AcuseV1.0';

	FirmaDigitalCtrl.datosEntrada.idTipoSolicitud = componenteFirma.idTipoSolicitud;
	FirmaDigitalCtrl.datosEntrada.descripcionTipoSolicitud = componenteFirma.descripcionTipoSolicitud;
	FirmaDigitalCtrl.datosEntrada.idTipoTramite = componenteFirma.idTipoTramite;

	FirmaDigitalCtrl.datosEntrada.folioSolicitud = componenteFirma.folioSolicitud;
	FirmaDigitalCtrl.datosEntrada.curp = componenteFirma.curp;
	FirmaDigitalCtrl.datosEntrada.registroPatronal = componenteFirma.registroPatronal;
	FirmaDigitalCtrl.datosEntrada.nombreCompleto = componenteFirma.nombreCompleto;
	FirmaDigitalCtrl.datosEntrada.fechaElectronica = componenteFirma.fechaElectronica;
	FirmaDigitalCtrl.datosEntrada.afectado = componenteFirma.afectado;
    FirmaDigitalCtrl.datosEntrada.representados = componenteFirma.representados;
    FirmaDigitalCtrl.datosEntrada.domicilioActualizado= componenteFirma.domicilioActualizado;
    FirmaDigitalCtrl.datosEntrada.afectadoNuevosValores= componenteFirma.afectadoNuevosValores;
    FirmaDigitalCtrl.datosEntrada.mediosContacto= componenteFirma.mediosContacto;
    FirmaDigitalCtrl.datosEntrada.tipoAcuse= componenteFirma.tipoAcuse;
    FirmaDigitalCtrl.datosEntrada.acuse= componenteFirma.acuse;

	
	if(componenteFirma.mostrarCartaTerminos){
		FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = componenteFirma.mostrarCartaTerminos;
	} else {
		FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = false;
	}

	FirmaDigitalCtrl.firmaDigital();
	
};


var FirmaDigitalCtrlAux = {

	dialogo : {},
	init : function(idContenedor, idContenedorDoctos){
		this.id_Form = 1;
		this.urlFormularioFirma = '/firmaElectronicaWeb/widget/imss';

		this.config.contenedor = idContenedor;
		this.config.contenedorDoctos = idContenedorDoctos;
	},

	iniciarFlujoFirmaDigital : function(urlFormularioFirma) {
		var formFirmaDigital = '<form action="' + urlFormularioFirma
		+ '" method="post" target="formFirmaDigitalAux" id="firmarForm_' + this.id_Form + '" >'
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
				.html('<iframe id="formFirmaDigitalAux" name="formFirmaDigitalAux" '
						+ 'width="100%" height="100%"'
						+ 'onload="set_size(\'formFirmaDigitalAux\')" frameBorder="0" />');
		
		this.setDatosSalida(null);
		$('#firmarForm_'+ this.id_Form).submit();
		
		this.id_Form++;
		//this.dialogo.dialog('open');
	},

	//Metodo para invocar la funcionalidad de la firma
	firmaDigital : function() {
		var idTipoTramite = FirmaDigitalCtrlAux.datosEntrada.idTipoTramite;

		var request = $.ajax({
			url : '/gestionSolicitud-web/firma-digital/validarTipoTramite/' + idTipoTramite,
			async : true,
			type : "POST",
			data : null,
			dataType : "json",
			contentType : "application/json; charset=utf-8"
		});

		request.done(function(response) {
			var urlAction = FirmaDigitalCtrlAux.urlFormularioFirma;
			if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrlAux.datosEntrada.operacion == 'firmaCMS'
				&& FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
				urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
			} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrlAux.datosEntrada.operacion == 'firmaCMS'
				&& FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
				urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
			}

			if(FirmaDigitalCtrlAux.datosEntrada.operacion == 'firmaCMS'){
				var idTipoTramite = FirmaDigitalCtrlAux.datosEntrada.idTipoTramite;
				
				
				if(FirmaDigitalCtrlAux.datosEntrada.firma_archivo) {
					WizardMuestraDocumentosRequeridosCtrl.setOnCloseCallback(function() {
						var urlAction = FirmaDigitalCtrlAux.urlFormularioFirma;
						if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
							urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
						} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
							urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
						}

						FirmaDigitalCtrlAux.iniciarFlujoFirmaDigital(urlAction);
					});
					
					WizardMuestraDocumentosRequeridosCtrl.init(FirmaDigitalCtrlAux.config.contenedorDoctos, idTipoTramite);
					WizardMuestraDocumentosRequeridosCtrl.abrir();
				} else {
					var urlAction = FirmaDigitalCtrlAux.urlFormularioFirma;
					
					if(response.requiereCartaTerminos == 1 && FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
						urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminos';
					} else if(response.requiereCartaTerminos == 2 && FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos){
						urlAction = '/gestionSolicitud-web/firma-digital/mostrarCartaTerminosRepresentanteLegal';
					}

					FirmaDigitalCtrlAux.iniciarFlujoFirmaDigital(urlAction);
				}
			} else {
				FirmaDigitalCtrlAux.iniciarFlujoFirmaDigital(urlAction);
			}
		});

		request.fail(function(response) {});	
	},
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

		}).error(function(data){
			
		});	
	}
};




//Funcion inicializar la firma digital
var iniciarFirmaDigitalAux = function (componenteFirma) {
	//Se settean los valores de entrada
	FirmaDigitalCtrlAux.datosEntrada.rfc = componenteFirma.rfc;
	FirmaDigitalCtrlAux.datosEntrada.val_rfc = componenteFirma.validarRFC;
	FirmaDigitalCtrlAux.datosEntrada.cad_original = componenteFirma.cad_original;
	FirmaDigitalCtrlAux.datosEntrada.firma_archivo = componenteFirma.firma_archivo;
	FirmaDigitalCtrlAux.datosEntrada.min_archivos = componenteFirma.min_archivos;
	FirmaDigitalCtrlAux.datosEntrada.max_archivos = componenteFirma.max_archivos;
	
	FirmaDigitalCtrlAux.datosEntrada.origen = server_name;
	FirmaDigitalCtrlAux.datosEntrada.operacion = componenteFirma.tipo_operacion;//"autentica";
	FirmaDigitalCtrlAux.datosEntrada.aplicacion="portalimssdigital";
	FirmaDigitalCtrlAux.datosEntrada.salida="rfc,curp,rfc_rl,curp_rl,serie_cert,contenedores,acuse,firmas,vigencias";   
	FirmaDigitalCtrlAux.datosEntrada.acuse = 'AcuseV1.0';

	FirmaDigitalCtrlAux.datosEntrada.idTipoSolicitud = componenteFirma.idTipoSolicitud;
	FirmaDigitalCtrlAux.datosEntrada.descripcionTipoSolicitud = componenteFirma.descripcionTipoSolicitud;
	FirmaDigitalCtrlAux.datosEntrada.idTipoTramite = componenteFirma.idTipoTramite;

	FirmaDigitalCtrlAux.datosEntrada.folioSolicitud = componenteFirma.folioSolicitud;
	FirmaDigitalCtrlAux.datosEntrada.curp = componenteFirma.curp;
	FirmaDigitalCtrlAux.datosEntrada.registroPatronal = componenteFirma.registroPatronal;
	FirmaDigitalCtrlAux.datosEntrada.nombreCompleto = componenteFirma.nombreCompleto;
	FirmaDigitalCtrlAux.datosEntrada.fechaElectronica = componenteFirma.fechaElectronica;
	FirmaDigitalCtrlAux.datosEntrada.afectado = componenteFirma.afectado;
    FirmaDigitalCtrlAux.datosEntrada.representados = componenteFirma.representados;
    FirmaDigitalCtrlAux.datosEntrada.domicilioActualizado= componenteFirma.domicilioActualizado;
    FirmaDigitalCtrlAux.datosEntrada.afectadoNuevosValores= componenteFirma.afectadoNuevosValores;
    FirmaDigitalCtrlAux.datosEntrada.mediosContacto= componenteFirma.mediosContacto;
    FirmaDigitalCtrlAux.datosEntrada.tipoAcuse= componenteFirma.tipoAcuse;
    FirmaDigitalCtrlAux.datosEntrada.acuse= componenteFirma.acuse;

	
	if(componenteFirma.mostrarCartaTerminos){
		FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos = componenteFirma.mostrarCartaTerminos;
	} else {
		FirmaDigitalCtrlAux.datosEntrada.mostrarCartaTerminos = false;
	}

	FirmaDigitalCtrlAux.firmaDigital();
	
};
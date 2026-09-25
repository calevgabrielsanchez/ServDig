/* Expresion regular para validar una fecha en formato dd/MM/yyyy */
var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[\/](0[1-9]|1[012])[\/](19|20)[0-9][0-9]$/;

var objCtrl = parent.ModificacionManualDatosFisicaCtrl;
var dialogoModificar;

var objCtrlDomicilios;
var objCtrlMediosContacto;
var objCtrlDocsProbatorios;
var objCtrlMediosContactoFiscales;

$(document).ready(function(){
	/*
	 * Funcion que convierte en MAYUSCULAS el valor de cualquier
	 * campo de texto al perder el foco
	 */
	$('input[type="text"]', 'form#mdmPersonaFisicaForm').blur(function() {
		$(this).val($(this).val().toUpperCase());
	});
	
	$('#fechaNacimientoErrorCliente').hide();
	
	$("#registroFechaNacimientoC").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});

	$('button.ui-datepicker-trigger').click(function(){
		$('#fechaNacimientoErrorCliente').hide();
	});
	
	$('input.hasDatepicker').click(function(){
		$('#fechaNacimientoErrorCliente').hide();
	});
						
	$('#btnAceptar').click(function() {
		
		if(validacionesTipoDato()){
			fnDeshabilitarCamposEjecucion();
		}
	});
	
	$('#btnCancelar').click(function(){
		objCtrl.cerrar();
	});
		
	$('#tabs').tabs();
	
	// Se inicializan los acordeones
	$('#acordeonDocProbatorios').accordion({
		autoHeight: false, 
		collapsible: true, 
		active: false,
		change: function(event, ui){
			/* Cada que se abra el acordeon de documentos probatorios, 
			 * se recalcula el height del iframe de la administración de documentos
			 */
			set_size('admonDocsProbatorios');
		}});
	$('#acordeonDomFiscal').accordion({autoHeight: false, collapsible: true, active: false});
	$('#acordeonMediosFiscales').accordion({
		autoHeight: false, 
		collapsible: true, 
		active: false
	});
	$('#acordeonDomiciliosParticulares').accordion({
		autoHeight: false, 
		collapsible: true, 
		active: false,
		change: function(event, ui){
			/* Cada que se abra el acordeon de domicilios particulares, 
			 * se recalcula el height del iframe de la administración de domicilios
			 */
			set_size('admonDomicilios');
		}
	});
	$('#acordeonMediosParticulares').accordion({
		autoHeight: false, 
		collapsible: true, 
		active: false
	});
	
	$('#btnAgregarDomicilioParticular').click(function(){
		
		fnHideElement('div#domiciliosParticularesDiv #errorNegocioLabel');
		
		$('#tipoDomicilio\\.claveError').removeClass('showElement');
		$('#tipoDomicilio\\.claveError').addClass('hiddenElement');
		$('#tipoDomicilio\\.claveError').text();
		
		// Se valida que no existe otro domicilio particular
		$.postJSON('/gestionDomicilios-web/domicilio/administrar/particular/validar-agregar-dom-particular', null, function(data) {
			objCtrlDomicilios = DomicilioCtrl;
			objCtrlDomicilios.init('agregarDomicilioDialog');
			objCtrlDomicilios.setOnCloseCallback(fnOnCloseAdmonDomicilio);
			objCtrlDomicilios.setTipoDomicilio($('#tipoDomParticular').val());
			objCtrlDomicilios.localizar();
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
	});
	
	$('#btnAgregarDomicilioNotificaciones').click(function(){
		
		fnHideElement('div#domiciliosParticularesDiv #errorNegocioLabel');
		
		$('#tipoDomicilio\\.claveError').removeClass('showElement');
		$('#tipoDomicilio\\.claveError').addClass('hiddenElement');
		$('#tipoDomicilio\\.claveError').text();
		
		objCtrlDomicilios = DomicilioCtrl;
		objCtrlDomicilios.init('agregarDomicilioDialog');
		objCtrlDomicilios.setOnCloseCallback(fnOnCloseAdmonDomicilio);
		objCtrlDomicilios.setTipoDomicilio($('#tipoDomNotificaciones').val());
		objCtrlDomicilios.localizar();
	});
	
	//Cada que se cargue el iframe de la administración de los domicilios, se recalcula su height
	$('#admonDomicilios').load(function() {
		set_size('admonDomicilios');
	});
	
	//Cada que se cargue el iframe de la administración de los documentos probatorios, se recalcula su height
	$('#admonDocsProbatorios').load(function() {
		set_size('admonDocsProbatorios');
	});
		
	dialogoModificar = $('#modificarDomicilioDialog').dialog({
        title: 'Modificar Domicilio',
        autoOpen: false,
        width: 980,
        height: 900,
        modal: true,
        resizable: false,
        autoResize: true,
        overlay: {
            opacity: 0.5,
            background: "black"
        }
    });
	
	// Incluimos el JS de la Administración de Documentos Probatorios
	$.getScript("/gestionDocumentoProbatorio-web/static/resources/js/delta/doctosProbatorios/administrar/DoctosProbatorios.js", function(){
		objCtrlDocsProbatorios = DocsProbatoriosCtrl;
		objCtrlDocsProbatorios.init('agregarDocsProbatoriosDialog');
		objCtrlDocsProbatorios.setOnCloseCallback(fnOnCloseAdmonDocsProbatorios);		
	});
	
	$('#btnDocsProbatorios').click(function(){
		fnHideElement('div#documentosProbatoriosDiv #errorNegocioLabel');
		objCtrlDocsProbatorios.registrar();
	});
	
	// Se carga el módulo de medios particulares
	ejecutarAdmonMedios();
	
	// Se carga el módulo de medios fiscales
	ejecutarAdmonMediosFiscales();
	
});

// Justo antes de hacer el submit, 'habilitamos' los combos para que sus respectivos valores puedan ir al controller
var fnDeshabilitarCamposEjecucion = function(){
	$('input').removeAttr("disabled");
	$('select').removeAttr("disabled");
	$('textarea').removeAttr("disabled");

	fnProcesarModifManual();
};

//Ir al servicio de MDM
var fnProcesarModifManual = function () {

	$.blockUI();
	
	// DATOS BASICOS -- Se setean las propiedades de descripcion que obtendremos a partir de la opcion seleccionada en el combo respectivo
	var desSexo = $('select#sexo\\.idSexo option:selected').text();
	$('#sexo\\.descripcion').attr('value', desSexo);
	var desEntidadNacimiento = $('select#lugarNacimiento\\.clave option:selected').text();
	$('#lugarNacimiento\\.nombre').attr('value', desEntidadNacimiento);
	
	// DOCUMENTOS PROBATORIOS -- Se setean las propiedades de descripcion que obtendremos a partir de la opcion seleccionada en el combo respectivo
	var desEntidadRegistro = $('select#actaNacimiento\\.municipio\\.entidadFederativa\\.clave option:selected').text();
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.nombre').attr('value', desEntidadRegistro);
	var desMunicipioRegistro = $('select#actaNacimiento\\.municipio\\.entidadFederativa\\.clave option:selected').text();
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.nombre').attr('value', desMunicipioRegistro);
	

	// Como las propiedades del formulario ya son clases mas complejas que a su vez tienen otras propiedades, ya no podemos usar el metodo 'serializeObject'. En vez de,
	// usaremos el metodo 'toObject'
	var oForm = $("form#mdmPersonaFisicaForm").toObject();
	
	// Se quitan propiedas de los datatables, que no deben ser enviados
	delete oForm.tblAdmonMediosFiscales_length;
	delete oForm.tblAdmonMedios_length;
	
	var url = $("form#mdmPersonaFisicaForm").attr('action');

	fnHideErrores("form#mdmPersonaFisicaForm");
	
	//Se va por los domicilios que están dentro del módulo de gestión de domicilios
	$.postJSON('/gestionDomicilios-web/domicilio/administrar/particular/obtener-domicilios', null, function(data) {
		oForm.personaFisica.domicilios = data;
		
		// Se va por los medios de contacto que están dentro del módulo de gestión de medios
		$.postJSON('/gestionMediosContacto-web/medios/particulares/administrar/obtener-medios', null, function(data) {
			oForm.personaFisica.mediosContacto = data;
			
			// Se va por los documentos probatorios que están dentro del módulo de gestión de documentos probatorios
			$.postJSON('/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/obtener-documentos-probatorios', null, function(data) {
				if(data != null){
					// Se valida si trae acta de nacimiento
					if(data.ACTA_NACIMIENTO != undefined && data.ACTA_NACIMIENTO != null){
						oForm.personaFisica.actaNacimiento = data.ACTA_NACIMIENTO;
					}					
					// Se valida si trae documentos probatorios RENAPO
					if(data.DOCUMENTO_MIGRATORIO != undefined && data.DOCUMENTO_MIGRATORIO != null){
						oForm.personaFisica.documentoMigratorio = data.DOCUMENTO_MIGRATORIO;
					}
					if(data.CARTA_NATURALIZACION != undefined && data.CARTA_NATURALIZACION != null){
						oForm.personaFisica.cartaNaturalizacion = data.CARTA_NATURALIZACION;
					}
					if(data.NUMERO_UNICO_EXTRANJERO != undefined && data.NUMERO_UNICO_EXTRANJERO != null){
						oForm.personaFisica.numeroUnicoExtranjero = data.NUMERO_UNICO_EXTRANJERO;
					}
					if(data.CERTIFICADO_NACIONALIDAD_MEXICANA != undefined && data.CERTIFICADO_NACIONALIDAD_MEXICANA != null){
						oForm.personaFisica.certificadoNacionalidadMexicana = data.CERTIFICADO_NACIONALIDAD_MEXICANA;
					}
					if(data.OFICIO_SOLICITANTE_REFUGIADO != undefined && data.OFICIO_SOLICITANTE_REFUGIADO != null){
						oForm.personaFisica.oficioSolicitanteRefugiado = data.OFICIO_SOLICITANTE_REFUGIADO;
					}
					if(data.FORMA_MIGRATORIA_TURISTA != undefined && data.FORMA_MIGRATORIA_TURISTA != null){
						oForm.personaFisica.formaMigratoriaTurista = data.FORMA_MIGRATORIA_TURISTA;
					}
				}
				
				// Se va por los medios de contacto fiscales que están dentro del módulo de gestión de medios fiscales
				$.postJSON('/gestionMediosContacto-web/medios/fiscales/administrar/obtener-medios', null, function(data) {
					oForm.personaFisica.mediosContactoFiscales = data;
							
					$.postJSON(url, oForm, function(data) {
						$.unblockUI();
						objCtrl.setDatosSalida(data);		
						objCtrl.cerrar();
					}).error(function(data){
						fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
						$.unblockUI();
					});
				}).error(function(data){
					fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
					$.unblockUI();
				});
			}).error(function(data){
				fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
				$.unblockUI();
			});
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
			$.unblockUI();
		});
	}).error(function(data){
		fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		$.unblockUI();
	});
};

// Función a llamar cuando se cierre el dialogo que agrega un domicilio particular
var fnOnCloseAdmonDomicilio = function(){
	var objetoDomicilio = this;
	
	if(objetoDomicilio.asentamiento != null){
		var url = '/gestionDomicilios-web/domicilio/administrar/particular/agregar';
		
		objetoDomicilio['dicTipoDomicilio'] = {clave: objCtrlDomicilios.getTipoDomicilio()};
		
		$.postJSON(url, objetoDomicilio, function(data) {
			actualizarListaDomicilios();
			objCtrlDomicilios.domicilio = null;
		}).error(function(data) {
			fnProcesarErrores(data, "form#formComplemento");
		});	
	}
};

// Función para actualizar la lista de domicilos particulares
var actualizarListaDomicilios = function (){
	// Se cambia el src del iframe para que refresque con los datos de la sesión
	fnHideElement('div#domiciliosParticularesDiv #errorNegocioLabel');
	$("#admonDomicilios").attr("src", '/gestionDomicilios-web/domicilio/administrar/particular');
};

//Función a llamar cuando se cierre el dialogo que agrega un medio de contacto particular
var fnOnCloseAdmonMediosContacto = function(){
	actualizarListaMediosContacto();
};

var fnDeshacerEliminarDomicilio = function (index){
	
	fnHideElement('div#domiciliosParticularesDiv #errorNegocioLabel');
	var url = '/gestionDomicilios-web/domicilio/administrar/particular/deshacer-eliminar/' + index;
	
	$.postJSON(url, null, function(data) {
		$("#admonDomicilios").attr("src", '/gestionDomicilios-web/domicilio/administrar/particular');
	}).error(function(data) {
		fnProcesarErrores(data, "div#domiciliosParticularesDiv");
	});	
	
};


function ejecutarAdmonMedios(){
	var idPersona = $('#idPersona').val();
	var url = '/gestionMediosContacto-web/medios/particulares/administrar/init/' + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoDiv").html(data);
	}).error(function(data) {
		
	});	
}

function ejecutarAdmonMediosFiscales(){
	var idPersona = $('#cveFisica').val();
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/init/' + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoFiscales").html(data);
	}).error(function(data) {
		
	});	
}

//Función a llamar cuando se cierre el dialogo que agrega un documento probatorio
var fnOnCloseAdmonDocsProbatorios = function(){
	actualizarListaDocsProbatorios();
};

var actualizarListaDocsProbatorios = function (){
	if (objCtrlDocsProbatorios.docsProbatorios.docProbatorioFormWrapper != null){
		// Se cambia el src del iframe para que refresque con los datos de la sesión
		fnHideElement('div#documentosProbatoriosDiv #errorNegocioLabel');
		$("#admonDocsProbatorios").attr("src", '/gestionDocumentoProbatorio-web/documentos/probatorios/administrar');
	}
};

var fnModificarDocProbatorio = function (index){	
	fnHideElement('div#documentosProbatoriosDiv #errorNegocioLabel');
	objCtrlDocsProbatorios.modificar(index);
};

var fnDeshacerEliminarDocProbatorio = function (index){
	
	fnHideElement('div#documentosProbatoriosDiv #errorNegocioLabel');
	var url = '/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/deshacer-eliminar/' + index;
	
	$.postJSON(url, null, function(data) {
		$("#admonDocsProbatorios").attr("src", '/gestionDocumentoProbatorio-web/documentos/probatorios/administrar');
	}).error(function(data) {
		fnProcesarErrores(data, "div#documentosProbatoriosDiv");
	});	
	
};

function validacionesTipoDato(){
	
	var isValid = true;
	
	if(objCtrl.datosEntrada.indCapturaFechaNacimiento == true){
		var fecha = $('#registroFechaNacimientoC').val();
	
		if (fecha != '' && (!ER_FECHAS.test(fecha) || !checkDate(fecha))){
			$('#fechaNacimientoErrorCliente').text("Formato de fecha inválido");
			$('#fechaNacimientoErrorCliente').show();
			isValid = false;
		}
	}
	
	return isValid;
}

function checkDate(fecha) {
	var arrFecha = fecha.split("/");

	if (arrFecha.length == 3) {
		var day = parseInt(arrFecha[0]);
		if (arrFecha[0].charAt(0) == '0') {
			day = parseInt(arrFecha[0].substr(1,1));
		}

		var month = parseInt(arrFecha[1]) - 1;
		if (arrFecha[1].charAt(0) == '0') {
			month = parseInt(arrFecha[1].substr(1,1)) - 1;
		}

		var year = parseInt(arrFecha[2]);
		var d = new Date(year, month, day);
		
		var dateDay = d.getDate();
		var dateMonth = d.getMonth();
		var dateYear = d.getFullYear();

		return dateDay === day && dateMonth === month && dateYear === year;
	} else {
		return false;
	}
}
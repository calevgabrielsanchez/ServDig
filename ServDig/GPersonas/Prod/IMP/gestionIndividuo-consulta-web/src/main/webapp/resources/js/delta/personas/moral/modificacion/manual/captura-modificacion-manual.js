var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[\/](0[1-9]|1[012])[\/](19|20)[0-9][0-9]$/;

var objCtrl = parent.ModificacionManualDatosMoralCtrl;
var objCtrlMediosContactoFiscales;

$(document).ready(function(){
	
	$('.error').hide();	
	
	$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').hide();

	if ($('#fechaRegSindicato').length) {
		$('#fechaRegistroErrorCliente').hide();
    }
	if ($('#fechaExpedicion').length) {
		$('#fechaExpedicionErrorCliente').hide();
	}
		
	$("#registroFechaCreacionC").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
	
	$("#fechaExpedicion").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
	
	$("#fechaRegSindicato").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});

	$('button.ui-datepicker-trigger').click(function(){
		$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').hide();
		if ($('#fechaRegSindicato').length) {
			$('#fechaRegistroErrorCliente').hide();
	    }
		if ($('#fechaExpedicion').length) {
			$('#fechaExpedicionErrorCliente').hide();
		}
	});
	
	$('input.hasDatepicker').click(function(){
		$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').hide();
		if ($('#fechaRegSindicato').length) {
			$('#fechaRegistroErrorCliente').hide();
	    }
		if ($('#fechaExpedicion').length) {
			$('#fechaExpedicionErrorCliente').hide();
		}
	});

	$('#btnAceptar').click(function(){
		
		if(validacionesTipoDato()) {
			fnDeshabilitarCamposEjecucion();
		}
	});
	
	$('#btnCancelar').click(function(){
		objCtrl.cerrar();
	});
		
	$('#tabs').tabs();
	$('#acordeonDomFiscal').accordion({autoHeight: false, collapsible: true, active: false});
	$('#acordeonMediosFiscales').accordion({
		autoHeight: false, 
		collapsible: true, 
		active: false,
		change: function(event, ui){
			/* Cada que se abra el acordeon de medios de contacto fiscales, 
			 * se recalcula el height del iframe de la administración de medios
			 */
			set_size('admonMediosContactoFiscales');
		}
	});
	$('#acordeonEscrituraConstitutiva').accordion({autoHeight: false, collapsible: true, active: false});
	$('#acordeonRegistroSindicato').accordion({autoHeight: false, collapsible: true, active: false});
	
	if(typeof objCtrl.datosEntrada.indCapturaActaConstitutiva != 'undefined' && 
			objCtrl.datosEntrada.indCapturaActaConstitutiva){
		$('#folioMercantil').focusout(function(){
			if($(this).val().trim().length > 0){
				$('#datosEscritura').hide();
			}else{
				$('#datosEscritura').show();
			}
		});
		
		$('#seccionEscrituraConstitutiva').focusout(function(){
			ocultarFolioMercantil($(this));
		});
		
		$('#partidaEscrituraConstitutiva').focusout(function(){
			ocultarFolioMercantil($(this));
		});
		
		$('#volumenEscrituraConstitutiva').focusout(function(){
			ocultarFolioMercantil($(this));
		});
		
		$('#fojaEscrituraConstitutiva').focusout(function(){
			ocultarFolioMercantil($(this));
		});
		
		// Se validan los campos para ocultar los campos en caso de ser necesario
		
		if($('#folioMercantil').length && $('#folioMercantil').val().trim().length > 0){
			$('#datosEscritura').hide();
		} else if ($('#seccionEscrituraConstitutiva').length
				&& $('#seccionEscrituraConstitutiva').val()
						.trim().length > 0
				&& $('#partidaEscrituraConstitutiva').val()
						.trim().length > 0
				&& $('#volumenEscrituraConstitutiva').val()
						.trim().length > 0
				&& $('#fojaEscrituraConstitutiva').val().trim().length > 0) {
			$('#folioMercantilDiv').hide();
		}
	}
	
	// Incluimos el JS de la Administración de Medios de contacto fiscales
	$.getScript("/gestionMediosContacto-web/static/resources/js/delta/mediosContacto/administrar/AdmonMedioContactoFiscal.js", function(){
		objCtrlMediosContactoFiscales = AdmonMedioContactoFiscalCtrl;
		objCtrlMediosContactoFiscales.init('agregarMedioContactoFiscalDialog');
		objCtrlMediosContactoFiscales.setOnCloseCallback(fnOnCloseAdmonMediosContactoFiscales);		
	});
	
	$('#btnMediosContactoFiscales').click(function(){
		fnHideElement('div#mediosContactoFiscalesDiv #errorNegocioLabel');
		objCtrlMediosContactoFiscales.registrar();
	});
	
	//Cada que se cargue el iframe de la administración de los medios de contacto fiscales, se recalcula su height
	$('#admonMediosContactoFiscales').load(function() {
		set_size('admonMediosContactoFiscales');
	});
	
	// Se carga el módulo de medios fiscales
	ejecutarAdmonMediosFiscales();
});

//Justo antes de hacer el submit, 'habilitamos' los combos para que sus respectivos valores puedan ir al controller
var fnDeshabilitarCamposEjecucion = function(){
	$('input').removeAttr("disabled");
	$('select').removeAttr("disabled");
	$('textarea').removeAttr("disabled");

	fnProcesarModifManual();
};

//Ir al servicio de MDM
var fnProcesarModifManual = function () {

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
	var oForm = $("form#mdmPersonaMoralForm").toObject();
	var url = $("form#mdmPersonaMoralForm").attr('action');

	fnHideErrores("form#mdmPersonaMoralForm");
	
	// Se va por los medios de contacto fiscales que están dentro del módulo de gestión de medios fiscales
	$.postJSON('/gestionMediosContacto-web/medios/fiscales/administrar/obtener-medios', null, function(data) {
		oForm.personaMoral.mediosContactoFiscales = data;
		$.postJSON(url, oForm, function(data) {
			objCtrl.setDatosSalida(data);
			objCtrl.cerrar();
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaMoralForm");
			deshabilitarCampos();
		});
	}).error(function(data){
		fnProcesarErrores(data, "form#mdmPersonaMoralForm");
		deshabilitarCampos();
	});
};

function ocultarFolioMercantil(input){
	
	if(input.val().trim().length > 0){
		$('#folioMercantilDiv').hide();
	}else if($('#seccionEscrituraConstitutiva').val().trim().length == 0 && $('#partidaEscrituraConstitutiva').val().trim().length == 0 && 
		$('#volumenEscrituraConstitutiva').val().trim().length == 0 && $('#fojaEscrituraConstitutiva').val().trim().length == 0){
			$('#folioMercantilDiv').show();
	}
}

//Función a llamar cuando se cierre el dialogo que agrega un medio de contacto fiscal
var fnOnCloseAdmonMediosContactoFiscales = function(){
	actualizarListaMediosContactoFiscales();
};

var actualizarListaMediosContactoFiscales = function (){
	if (objCtrlMediosContactoFiscales.mediosContacto.medioContactoFormWrapper != null){
		// Se cambia el src del iframe para que refresque con los datos de la sesión
		fnHideElement('div#mediosContactoFiscalesDiv #errorNegocioLabel');
		$("#admonMediosContactoFiscales").attr("src", '/gestionMediosContacto-web/medios/fiscales/administrar');
	}
};

var fnModificarMedioContactoFiscal = function (index){
	fnHideElement('div#mediosContactoFiscalesDiv #errorNegocioLabel');
	objCtrlMediosContactoFiscales.modificar(index);
};

var fnDeshacerEliminarMedioContactoFiscal = function (index){
	
	fnHideElement('div#mediosContactoFiscalesDiv #errorNegocioLabel');
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/deshacer-eliminar/' + index;
	
	$.postJSON(url, null, function(data) {
		$("#admonMediosContactoFiscales").attr("src", '/gestionMediosContacto-web/medios/fiscales/administrar');
	}).error(function(data) {
		fnProcesarErrores(data, "div#mediosContactoFiscalesDiv");
	});	
	
};

function validacionesTipoDato(){
	
	var isValid = true;
	
	var fechaCreacion = $('#registroFechaCreacionC').val();
	var fechaComp = '';
	
	if ($('#fechaRegSindicato').length) {
		fechaComp = $('#fechaRegSindicato').val();
    }
	if ($('#fechaExpedicion').length) {
		fechaComp = $('#fechaExpedicion').val();
	}

	if(objCtrl.datosEntrada.indCapturaFechaConstitucion){
		if (fechaCreacion != '' && ER_FECHAS.test(fechaCreacion) && checkDate(fechaCreacion)) {
			$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').hide(); 
		} else {
			isValid = false;
			if (fechaCreacion == '') {
				$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').text("Campo requerido");
			} else {
				$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').text("Formato de Fecha invalido");
			}
			$('#datosPersonaSAT\\.fechaConstitucionErrorCliente').show();
		}
	}

	if(objCtrl.datosEntrada.indCapturaActaConstitutiva || objCtrl.datosEntrada.indCapturaRegistroSindicato){
		if (fechaComp != '' && ER_FECHAS.test(fechaComp) && checkDate(fechaComp)) {
			if ($('#fechaRegSindicato').length) {
				$('#fechaRegistroErrorCliente').hide(); 
		    }
			if ($('#fechaExpedicion').length) {
				$('#fechaExpedicionErrorCliente').hide(); 
			}
		} else {
			isValid = false;
			if ($('#fechaRegSindicato').length) {
				if (fechaComp == '') {
					$('#fechaRegistroErrorCliente').text("Campo requerido");
				} else {
					$('#fechaRegistroErrorCliente').text("Formato de Fecha invalido");
				}
				$('#fechaRegistroErrorCliente').show(); 
		    }
			if ($('#fechaExpedicion').length) {
				if (fechaComp == '') {
					$('#fechaExpedicionErrorCliente').text("Campo requerido");
				} else {
					$('#fechaExpedicionErrorCliente').text("Formato de Fecha invalido");
				}
				$('#fechaExpedicionErrorCliente').show();
			}
		}
	}
	
	return isValid;
}

function ejecutarAdmonMediosFiscales(){
	var idPersona = $('#cveMoral').val();
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/moral/init/' + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoFiscales").html(data);
	}).error(function(data) {
		
	});	
}
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	$('#enviarSolicitud').click(function() {
		enviarSolicitud();
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#agregarDomicilio').click(function() {
		parent.DomicilioCtrl.localizar();
	});
	
	parent.DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
	parent.DomicilioCtrl.domicilio = null;
	
	ejecutarAdmonMedios();
});

function enviarSolicitud() {
	
	fnHideErrores("form#forma");
	
	/*
	 * Como las propiedades del formulario ya son clases mas complejas que 
	 * a su vez tienen otras propiedades, ya no podemos usar el metodo
	 * 'serializeObject'. En vez de, usaremos el metodo 'toObject'
	 */
	var oForm = $("form#forma").toObject();
	
	// Se quitan propiedas de los datatables, que no deben ser enviados
	delete oForm.tblAdmonMedios_length;
	
	$.ajaxSetup({async : false});
	
	// Se va por los medios de contacto que están dentro del módulo de gestión de medios
	$.postJSON('/gestionMediosContacto-web/medios/particulares/administrar/obtener-medios', null, function(data) {
		oForm.mediosContacto = data;
	}).error(function(data){
		fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
	});
	
	oForm.domicilios = new Array();
	oForm.domicilios[0] = parent.DomicilioCtrl.domicilio;
	
	var url = '/gestionAsegurados-web-externo/wizard/nss/captura/validar';
	$.postJSON(url, oForm, function(data) {
		/*
		 * Si no hubo errores en las validaciones de la vista se envía la
		 * petición para realizar las validaciones de NSS y la creación de la
		 * solicitud
		 */
		$('#procesaCapturaNSSForm').submit();
	}).error(function(data){
		fnProcesarErrores(data, "form#forma");
	});
}

function cerrarWizard() {	
	parent.WizardAsignacionNSSCtrl.cerrar();
}

var fnOnCloseDomicilio = function () {
	
	var domicilio = this;
	
	if(typeof domicilio.vialidadPrimaria  !== "undefined") {
		$("#vialidadPrimariaNombre").text(domicilio.vialidadPrimaria.nombre);
		$("#numExteriorAlf").text(domicilio.numExteriorAlf);
		$("#numExterior1").text(domicilio.numExterior1);
		$("#numInteriorAlf").text(domicilio.numInteriorAlf);
		$("#numInterior").text(domicilio.numInterior);
		$("#asentamientoNombre").text(domicilio.asentamiento.nombre);
		$("#municipioNombre").text(domicilio.asentamiento.localidad.municipio.nombre);
		$("#entidadFederativaNombre").text(domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#codigoPostal").text(domicilio.asentamiento.codigoPostal.codigoPostal);
		
		$('#detalleDomicilio').show();
	}
	
};

function ejecutarAdmonMedios(){
	var url = '/gestionMediosContacto-web/medios/particulares/administrar/init';
	
	$.post(url, function(data) {
		$("#admonMediosContactoDiv").html(data);
	}).error(function(data) {
		
	});	
}
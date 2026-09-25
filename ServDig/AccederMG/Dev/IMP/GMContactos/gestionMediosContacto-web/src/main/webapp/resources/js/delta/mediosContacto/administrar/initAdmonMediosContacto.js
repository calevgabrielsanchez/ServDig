var eliminarMedioConfirm;
var indiceDom;
var idPersona;
var objRegistroMedio;
var objModifMedio;
var oTable;

var actualizarListaMediosFromRegistro = function (){
	if (objRegistroMedio.mediosContacto.medioContactoFormWrapper != null){
		refresh();
	}
};

var actualizarListaMediosFromModificacion = function (){
	if (objModifMedio.mediosContacto.medioContactoFormWrapper != null){
		refresh();
	}
};

var fnDeshacerEliminarMedioContacto = function (index){
	var url = '/gestionMediosContacto-web/medios/particulares/administrar/deshacer-eliminar/' + index + "/" + idPersona;
	
	$.postJSON(url, null, function(data) {
		refresh();
	}).error(function(data) {
		fnProcesarErrores(data, "div#admonMediosContactoWrapper");
	});	
	
};

var fnModificarMedioContacto = function (index){
	objModifMedio.modificar(index);
};

function modificarMedioContacto(index){
	fnModificarMedioContacto(index);
}

function eliminarMedioContacto(index){
	indiceDom = index;
	eliminarMedioConfirm.dialog( "open" );
}

function deshacerEliminar(index){
	
	fnDeshacerEliminarMedioContacto(index);
	
}

function initAdmonMedios () {
	
	$.ajaxSetup({ cache: false });
	
	idPersona = $('input#idPersona').val();
	
	objRegistroMedio = AdmonRegistroMedioContactoCtrl;
	objRegistroMedio.config.contenedor = 'agregarMedioContactoDialog'; 
	objRegistroMedio.config.idPersona = idPersona;
	objRegistroMedio.setOnCloseCallback(actualizarListaMediosFromRegistro);
	
	objModifMedio = AdmonModifMedioContactoCtrl;
	objModifMedio.config.contenedor = 'modificarMedioContactoDialog';
	objModifMedio.config.idPersona = idPersona;
	objModifMedio.setOnCloseCallback(actualizarListaMediosFromModificacion);	

	$('#btnMediosContacto').click(function(){
		objRegistroMedio.registrar();
	});
	
	oTable = $('#tblAdmonMedios').dataTable({
		"iDisplayLength": 5,
		"sPaginationType": "bootstrap",
		"bLengthChange": false,
		"bSort": false,
		"bFilter": false,
		"bAutoWidth": false,
		"aoColumns": [{ "sWidth": "60%" },{ "sWidth": "20%" },{ "sWidth": "20%" }],
		"bStateSave": true,
		"sCookiePrefix": 'mediosStateSave_',
		"iCookieDuration": 60 * 5 // 5 minutos
	});
	
	eliminarMedioConfirm = $( "#eliminarMedioConfirm" ).dialog({
		resizable: false,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				
		 		var action = $('#deleteMedioForm').attr('action') + "/" + indiceDom + "/" + idPersona;
		 		
		 		$.get(action).done(function(){
		 			refresh();
		 		}).fail(function(data){
					fnProcesarErrores(data, "form#agregarMedioContactoForm");
				});
		 		
		 		$( this ).dialog( "close" );
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	setSizeWithinIframe(document, 900, 560);
}

function refresh() {
	$('div#agregarMedioContactoDialog').remove();
	$('div#modificarMedioContactoDialog').remove();
	admonMediosParticulares.refresh(idPersona);
}
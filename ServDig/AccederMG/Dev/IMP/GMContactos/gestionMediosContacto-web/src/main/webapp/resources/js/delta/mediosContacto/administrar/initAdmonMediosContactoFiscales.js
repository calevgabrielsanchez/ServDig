var dialogoConfirmarMediosFiscales;
var indiceDom;
var objCtrlMediosFiscales;	

//Función a llamar cuando se cierre el dialogo que agrega un medio de contacto particular
var fnOnCloseAdmonMediosFiscales = function(){
	actualizarListaMediosFiscales();
};

var actualizarListaMediosFiscales = function (){
	if (objCtrlMediosFiscales.mediosContacto.medioContactoFormWrapper != null){
		admonMediosFiscales.refresh();
	}
};

var fnDeshacerEliminarMedioFiscal = function (index){
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/deshacer-eliminar/' + index;
	
	$.postJSON(url, null, function(data) {
		admonMediosFiscales.refresh();
	}).error(function(data) {
		fnProcesarErrores(data, "div#admonMediosFiscalesWrapper");
	});	
	
};

var fnModificarMedioFiscal = function (index){
	//fnHideErrores('div#admonMediosFiscalesWrapper');
	objCtrlMediosFiscales.modificar(index);
};

function modificarMedioFiscal(index){
	fnModificarMedioFiscal(index);
}

function eliminarMedioFiscal(index){
	indiceDom = index;
	dialogoConfirmarMediosFiscales.dialog( "open" );
}

function deshacerEliminarMedioFiscal(index){
	
	fnDeshacerEliminarMedioFiscal(index);
	
}

function initAdmonMediosFiscales () {
	
	objCtrlMediosFiscales = AdmonMedioContactoFiscalCtrl;
	objCtrlMediosFiscales.init('agregarMedioContactoFiscalDialog');
	objCtrlMediosFiscales.setOnCloseCallback(fnOnCloseAdmonMediosFiscales);

	$('#btnMediosFiscales').click(function(){
		objCtrlMediosFiscales.registrar();
	});
	
	$('#tblAdmonMediosFiscales').dataTable({
		"iDisplayLength": 4,
		"bSort": false,
		"bFilter": false,
		"bAutoWidth": false,
		"aoColumns": [{ "sWidth": "60%" },{ "sWidth": "20%" },{ "sWidth": "20%" }]
	});
	
	dialogoConfirmarMediosFiscales = $( "#eliminarMedioFiscalConfirm" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
		 		var action = $('#deleteMedioFiscalForm').attr('action') + "/" + indiceDom;
		 		
		 		$.get(action, null, function(data) {
					objCtrlMediosFiscales.cerrar();
				}).error(function(data){
					fnProcesarErrores(data, "form#agregarMedioContactoForm");
				});
		 		
		 		$( this ).dialog( "close" );
		 		admonMediosFiscales.refresh();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	});
}
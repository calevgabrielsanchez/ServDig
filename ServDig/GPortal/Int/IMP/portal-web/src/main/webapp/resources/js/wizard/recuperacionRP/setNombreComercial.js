$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});
	
	$('#aceptarNC').click(function() {
		validacionFormulario();
	});
	
});

function validacionFormulario() {
	
	var nombreC =$.trim($("#nombreComercial").val());
	
	if(nombreC.length > 0) {
		var oForm = $("form#formNomC").toObject();
		setNombreComercial(oForm);
	} else {
		parent.WizardNombreComercialCtrl.setResultado(null);
		parent.WizardNombreComercialCtrl.cerrar();
	}
	
}

function setNombreComercial(patron) {	
	var url="/portal-web/wizard/tramite/recuperacion/patron/setNombreComercial";
	
	$.postJSON(url,patron,function(data){
		if(data.guardado) {
			parent.WizardNombreComercialCtrl.setResultado(data.nombreComercial);
		}
		parent.WizardNombreComercialCtrl.cerrar();
	});
	
}
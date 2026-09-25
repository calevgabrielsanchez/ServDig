$(document).ready(function() {
		
	$('div.site_position_center').css('width', '100%');
	
	var objCtrl = parent.dialogoModificar;

	$('#btnModificar').click(function() {

		var url = $('#formComplemento').attr('action');
		var oForm = $('#formComplemento').toObject();

		delete oForm.municipio;
		delete oForm.entidadFederativa;
		
		pasarAtributosDisabled(oForm);

		$.postJSON(url, oForm, function(data) {
			objCtrl.dialog('close');
			parent.actualizarListaDomicilios();
		}).error(function(data) {
			fnProcesarErrores(data, "form#formComplemento");
		});
	});
});
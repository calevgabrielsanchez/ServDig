$(document).ready(function() {
	$('#cerrarWizard').click(function() {
		parent.WizardDetalleSeguroCtrl.cerrar();
	});

	$('#iniciarTramite').click(function() {
		
		var _idPersona = parent.WizardAltaCVROCtrl.config.idPersona;
		var _nssCifrado =parent.WizardAltaCVROCtrl.config.nssCifrado;
		var _title='Reingreso a la Continuaci\u00F3n Voluntaria en el R\u00E9gimen Obligatorio';
		var _url = '/${mvn.web.app.root}/wizard/continuacionVoluntaria/renovacion/alta/init';
		parent.WizardAltaCVROCtrl.setDatosRenovacion( _idPersona, _nssCifrado, _title, _url );
		parent.WizardAltaCVROCtrl.open();
		closeWizard();
	});
});

var imprSegPerIvro = function(id) {
	//var idCifrado = id.getAttribute('data-id');
	//id = idCifrado;
	//var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/' + id;
	//window.open(liga, '_blank');
	
	var idCifrado = id.getAttribute('data-id');

	var form = document.createElement("form");
	form.method = "POST";
	form.action = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/' + idCifrado;
	form.target = "_blank";
	 
	
	document.body.appendChild(form);
	form.submit();
	document.body.removeChild(form);
};
$(document).ready(function() {

	$('#aceptaCartaTC').hide();

	$('#aceptaCartaTC').click(function(e) {
		e.preventDefault();
		aceptar();
	});

	$('#chkCartaTC').change(function() {
		if ($(this).prop('checked'))
			$('#aceptaCartaTC').show();
		else
			$('#aceptaCartaTC').hide();
	});

	$('#cancelarCartaTC').click(function() {
		cerrar();
	});
});


function cerrar() {
	var titulo = "Advertencia";
	var mensaje = "No se mostraran los riesgos de trabajo terminados, ya que no acepto los términos y condiciones.";
	armarDlgModal(titulo, mensaje, 400, 200);
}

function aceptar() {
	$("#idForm").submit();
}

function armarDlgModal(titulo, mensaje, dlgWidth, dlgHeight) {
	var newdiv = document.createElement('div');
	newdiv.setAttribute('id', 'divMsjDlgModal');
	newdiv.innerHTML = mensaje;
	var divv = document.getElementsByTagName('div')[0];
	divv.appendChild(newdiv);

	var objDialogo = $("#divMsjDlgModal").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		cache : false,
		height : dlgHeight,
		width : dlgWidth,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				parent.WizardRttCtrl.cerrar();
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}
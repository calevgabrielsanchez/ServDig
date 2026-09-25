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
	var mensaje = "No se mostrar&aacute;n los riesgos de trabajo terminados, ya que no acept&oacute; los t&eacute;rminos y condiciones.";
	armarDlgModal(titulo, mensaje, 400, 200);
}

async function aceptar() {
	if (await this.connection())
		$("#idForm").submit();
}

async function connection(){
	var url = '/${mvn.web.app.root}/historialRiesgoTrabajo/connection';
	var respuesta = false;
	var opciones = {
		titulo: 'Sesi\u00F3n',
		mensaje:"Intermitencia en la comunicaci\u00F3n con la Base de Datos. Favor de ingresar nuevamente."
	};

	const connectResponse = await fetch(url).then(function (response) {
		if (response.ok) {
			return respuesta = response.ok;
		} else {
			console.log("No se estableció la conexión con Base de datos:" + error.message);
			return respuesta;
		}
	}).catch(function (error) {
		console.log("No se estableció la conexión con Base de datos:" + error.message);
		return respuesta;
	});

	if (!connectResponse)
		dialogosCtrl.abrirDialogo(opciones);

	return respuesta;
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
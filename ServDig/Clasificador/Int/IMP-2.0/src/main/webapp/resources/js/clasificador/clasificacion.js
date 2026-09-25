var valorClasificador = null;

function clasificador2() {
	if (navigator.userAgent.toLowerCase().indexOf('trident') > -1){ //Si es IE
		seleccionarClasificacionNueva();
	} else {
		modernClasificador();
	}
}

//Seleccionar clasificacion desde IE
function seleccionarClasificacionNueva() {
	var dialogoConfirmarCancelar = $( "<div></div>" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		width: "350px",
		title: 'Cat&aacute;logo de Clasificaci&oacute;n de Empresas',
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				salirAClasificacion(); // Esta funcion debera estar definida en la aplicacion que consume el clasificador desde IE
				$(this).dialog('close');
		 	}
		 }
	});

	dialogoConfirmarCancelar.html("Est&aacute;s saliendo del portal IMSS Digital para seleccionar tu clasificaci&oacute;n. <br>Al concluir regresar&aacute;s al portal IMSS Digital.");
	dialogoConfirmarCancelar.dialog('open');
}

// Seleccion de la clasificacion en navegadores modernos
function modernClasificador() {
	console.log("::: En modernClasificador");	
	createIFrame();
	window.addEventListener("message", recibe,{once: true});
}

function recibe(event){
	console.log("::: En recibe");
	valorClasificador=event.data;
	console.log("valor del valorClasificador:" + valorClasificador);
	terminaClasificador(); // Esta funcion debera estar definida en la aplicacion que consume el clasificador desde navegadores modernos
}

function createIFrame() {
	$clasificador = $('<div id="idiframe"></div');
	$clasificador.html('')
	$clasificador.append('<iframe id="idifrclasificador" src="/clasificador/" width="900" height="800" scrolling="yes" ></iframe>');
	$clasificador.dialog({
		autoOpen: false,
		title: 'Cat&aacute;logo de Clasificaci&oacute;n de Empresas',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		closeOnEscape: false,
		height: 801,
		width: 901
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	$clasificador.dialog('open');
}

function removeIFrame() {
	console.log("::: Removiendo IFrame del Clasificador");
	var frame = document.getElementById("idifrclasificador");
	frame.parentNode.removeChild(frame);
	$(idiframe).remove();
	$(idiframe).dialog('close');
	$(idiframe).dialog('destroy');
	$(idiframe).html('');
}


//Funcion para asignar la fraccion devuelta por el clasificador desde IE
function salirAClasificacion() {
	console.log("::: En salirAClasificacion()");
	var oSendData = new Object();
	oSendData.origen = 'delta';
	oSendData.tipoBusqueda = 3;
	oSendData.showAnterior = true;
	oSendData.showClase = false;
	oSendData.session = sessionId;
	try {
		valorClasificador = window
				.showModalDialog(
						"/clasificador",
						oSendData,
						"dialogWidth:900px;dialogHeight:800px;status=yes,toolbar=no,menubar=no,location=no");
						
		if (valorClasificador != undefined && valorClasificador != null) {			
			terminaClasificadorIE();
		}else{
			console.log("::: La respuesta del Clasificador esta vacia");
		}
		
	} catch (e) {
		alert("Sucedi\u00F3 un error inesperado en la selecci\u00F3n de la clasificaci\u00F3n");
	}
}

function sendParameters() {
	var oSendData = new Object();
	oSendData.origen = 'delta';
	oSendData.tipoBusqueda = 3;
	oSendData.showAnterior = true;
	oSendData.showClase = false;
	oSendData.session = sessionId;
	document.getElementById('idifrclasificador').contentWindow.postMessage(oSendData,"*");
}

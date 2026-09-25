var dialogoConfirmarCancelar,
	dialogoConfirmar;


$(document).ready(function() {
	

	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	
	
	$('#modificarTramite').click(function() {
		alert("Debo modificar algo");
	});

	$('#finalizarTramite').click(function() {
		prefinalizarClasificacion();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#guardarCerrarTramite').click(function() {
		guardarCerrarTramite();
	});

	$('#cancelarTramite').click(function() {
		var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
		dialogoConfirmarCancelar.dialog( "open" );
		$('#rpMessage').html(numeroRegistroPatronal);
	});
	
	dialogoConfirmarCancelar = $("#dialog-confirm-cancelar").dialog({
		resizable: false,
		height: 'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"Cancelar": function() {
				$(this).dialog("close");
			},
			"Aceptar": function() {
				cancelarTramite();
			}
		}
	});

	dialogoConfirmar = $("#dialog-confirm").dialog({
		resizable: false,
		height: 160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardModificacionPatronClasificacionCtrl.cerrar();
			}
		}
	});

	// Se settea la funcion de callback
	/*parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		dialogoConfirmar.dialog("option", "buttons", [{
			text: 'Aceptar',
			click: function() {
				$(this).dialog('close');
			}
		}]);

		if (parent.FirmaDigitalCtrl.datosSalida == null) {
			$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
			dialogoConfirmar.dialog('open');
		} else {
			if (parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var firmaResponse = {
					cadenaOriginal: parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo: parent.FirmaDigitalCtrl.datosSalida.firmas[0],
					reciboNotarial: parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma: parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado: parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado: parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado: parent.FirmaDigitalCtrl.datosSalida.vigFin
				};

				finalizarTramite(firmaResponse);
			} else {
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmar.dialog('open');
			}
		}
	});*/

	//Si el tramite es de clasificacion, se oculta PSP Mm WO1610045/4351808
	if(codigoTramite == 11 || codigoTramite == 12 
		|| codigoTramite == 13 || codigoTramite == 14 
		|| codigoTramite == 15 || codigoTramite == 16 
		|| codigoTramite == 17 || codigoTramite == 18
		|| codigoTramite == 19 || codigoTramite == 20
		|| codigoTramite == 21 || codigoTramite == 22
	    || codigoTramite == 175 || codigoTramite == 176
	){
//		console.log("::: Se oculta seccion para marca de RPC por tramite de SRT");
//		$('#seccionPSP').hide();

		//WO1856344-4902920
		console.log("::: Se oculta columna de prima por tramite de SRT");
		$('#gridClasificacionNueva tr > *:nth-child(6)').hide();

	}

	// Si el tramite de Sustitucion por subcontratacion se oculta el valor de la prima
	if (codigoTramite == 175){
		console.log("Se obtienen los documentos de soporte del tramite en caso que se retome el tramite");
		consultarDocSoporteTramite();
	}

	var primaAux = 0;
	if(codigoTramite == 11 || codigoTramite == 12 
		|| codigoTramite == 13 || codigoTramite == 14 
		|| codigoTramite == 15 || codigoTramite == 16
	    || codigoTramite == 17 || codigoTramite == 18
		|| codigoTramite == 19 || codigoTramite == 22 || codigoTramite == 7
	){
		//Ocultamos la seccion de prima sugerida en caso de no se tramite de fusion, sustitucion
		console.log("::: Ocultamos seccion para calculo de prima sugerida");
		$("#seccionPrimaSugerida").hide();
		//guardamos el valor original de la prima recuperado del tramite
		primaAux = $("#primaSRTRestoTramites").val();
		
		if( !(isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val())
				|| isEmpty($("#divison").val())) ) {
			console.log("::: La clasificacion obtenido por el clasificador no esta vacia se aplica regla para asignar prima");
			//se aplica regla de Mm para asignar prima sugerida			
			primaClasificador = $("#primaClasificador").val();
			primaPatron = $("#objClasPrimaSRTActual").val();
			cveClaseClasificador = $("#cveclaseClasificador").val();
			cveclasePatron = $("#cveClaseActual").val();
			console.log("::: cveclasePatron: " + cveclasePatron + ", cveClaseClasificador: " + cveClaseClasificador +
				", primaPatron: " + primaPatron + ", primaClasificador: " + primaClasificador);
			aplicaReglaPrimaSugerida(cveclasePatron, cveClaseClasificador,primaPatron, primaClasificador);				
		}		
	}

	// Si el tramite es cambio de domicilio se oculta la seccion de prima sugerida
	/*if(codigoTramite == 7){
		console.log("::: Ocultamos seccion para calculo de prima sugerida por cambio de domicilio");
		$("#seccionPrimaSugerida").hide();
    	//guardamos el valor original de la prima recuperado del tramite
		primaAux = $("#primaSRTRestoTramites").val();
	}*/

	//Si el tramite es fusion se oculta la seccion para el calculo de la prima
	//ya que esta se mostrara al sugerir la prima y de acuerdo a las reglas del Mm
	if (codigoTramite == 21) {
		$("#datosCalculoPrima").hide()
	}

	console.log("::: indPrimaSugerida: " + indPrimaSugerida);
	if(indPrimaSugerida == '1'){
		console.log("::: El patron modifico su prima, se habilita campo de prima sugerida y checkBox");		
		if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16
		    || codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 22
			|| codigoTramite == 7
		){
			$("#primaSRTRestoTramites").prop('disabled', false);
			document.getElementById("modPrimaPatron").checked  = true;	
			//regresamos el valor original de la prima ya que fue editado por el patron
			$("#primaSRTRestoTramites").val(primaAux);				
		}else if (codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176){
			$("#primaSRTFusionSust").prop('disabled', false);
			document.getElementById("modPrimaPatronFS").checked  = true;	
		}
	}
	
	if(codigoTramite == 176){
		console.log("::: Ocultamos seccion para calculo de prima sugerida por tramite 176");
		$("#seccionPrimaSugerida").hide();		
	}
	
	console.log('idOrigenSolicitud', idOrigenSolicitud);
	console.log('idOrigenINTERNET', origenInternet);
	
	if (origenInternet) {
		hideDocumenosRequeridos();
	}


});


function obtienePrimaFusSust() {
	console.log("::: En obtienePrimaFusSust");
	
	if(validaPatronesyClasificacion()){		
		
		if(codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175){
			console.log("::: Aplicando reglas para tramites 20, 21 o 175");
			console.log("::: Recorriendo lista de patrones, Clase patron: " + $('#cveClaseActual').val());
			const claseNrpTramite = $('#cveClaseActual').val();
			var claseDif = false;
			patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
			var contPatrones = patronesUbicados.length;
			for (var i = 0; i <= patronesUbicados.length - 1; i++) {
				var patron = patronesUbicados[i];
				console.log("--- Revisando patron: " +
					patron.numeroRegistroPatronal + patron.modalidad.numModalidad + patron.digVerificador + " - " +
					patron.nombreComercial + " - Clase: " + patron.clasificacion.fraccion.clase.descripcion);
				if (patron.clasificacion.fraccion.clase.descripcion != $('#cveClaseActual').val()) {
					console.log("--- Encontre un patron con clase diferente");
					claseDif = true;
				}
			}
			console.log("::: claseDif: " + claseDif);			
			
			var indAux = true;
			if (codigoTramite == 21) {//Fusion
							console.log("::: El tramite es Fusion se procede con validaciones para sugerir prima");
							if (claseDif) {
								//se da formato a 5 digitos
								var primaC = Number($("#primaClasificador").val());
								primaC = primaC.toFixed(5);
								console.log("::: Una de las clases de los NRP a fusionar fue diferente se asigna el valor de la prima del clasificador, "
									+ primaC);
								$("#primaSRTFusionSust").val(""+primaC);
							} else {
								console.log("::: Las clases del NRP fusionante y fusionado son iguales se valida la clase selecccionada en el clasificador");
								console.log("::: cveclaseClasificador: " + $("#cveclaseClasificador").val() + ", claseNrpTramite: " + claseNrpTramite);	
								if ($("#cveclaseClasificador").val() == claseNrpTramite) {
									console.log(":: La clase de patron actual y la del clasificacor es la misma, se solicita la captura para el calculo de la prima");
									PrimaCtrl.habilitarCapturaPrima();
								}else{
									console.log(":: La clase de patron actual y la del clasificacor son diferentes, se asigna el valor de la prima del clasificador");
									PrimaCtrl.limpiarCalculoPrima();
									$("#datosCalculoPrima").hide();
									$("#habilitarCapturaPrima").hide();
									$("#primaPatronPrincipal").show();										
									$("#primaSRTFusionSust").prop('disabled', false);
									document.getElementById("modPrimaPatronFS").checked  = true;
									//se da formato a 5 digitos
									var primC = Number($("#primaClasificador").val());
									primC = primC.toFixed(5);
									$("#primaSRTFusionSust").val(""+primC);			
								}
							}
			}else if (codigoTramite == 20 || codigoTramite == 175) { //Sustitucion
				console.log("::: El tramite es Sustitucion se procede con validaciones para sugerir prima");
				if(contPatrones > 1){ 
					//cuando se trate de mas de una empresa susituida no importa si hay clases iguales o diferentes 
					// entre la empresa susituta y las sustituidas, la prima se ingresa manual
					console.log("::: Existe mas de un patron sustituido, la prima se ingresa manual, contPatrones: " + contPatrones);
					indAux = false;
					$("#primaSRTFusionSust").prop('disabled', false);
					document.getElementById("modPrimaPatronFS").checked  = true;								
				}else{
					console.log("::: Solo hay un patron sustituto, contPatrones: " + contPatrones);						
					if (claseDif) {
						console.log("::: La clase fue diferente se asigna el valor de la prima del clasificador, " 
							+ $("#primaClasificador").val());
						$("#primaSRTFusionSust").val($("#primaClasificador").val());
					} else {
						console.log("::: Las clases son iguales se toma la prima del patron sustituto");
						$("#primaSRTFusionSust").val($("#objClasPrimaSRTActual").val());
					}					
				}
			}
			
			//se dehabilita campo de la prima y se quita seleccion en indicador de modificacion de prima
			if(indAux){
				console.log("::: Deshabilitamos campo de prima y quitamos seleccion en checkBox ya que se calculo la prima");
				$("#primaSRTFusionSust").prop('disabled', true);
				document.getElementById("modPrimaPatronFS").checked  = false;					
			}
			
		}else if(codigoTramite == 176){
//AQUI APLICAR REGLAS		
			calculaPrima176();
		}		

	}else{
		console.log("::: No se ha capturado patrones o la clasificacion");
	}
}

//Se determina la prima para el tramite 176
function calculaPrima176(){
	console.log("::: calculaPrima176 - Se aplican reglas para calculo de prima para tramite 176 aviso de cambio de domicilio diferente municipio");		
	var claseDif = false;
	var patron = null;
	patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
	console.log("::: Recorriendo lista de patrones, contPatrones: " + patronesUbicados.length + ", clase de clasificador: " + $('#cveclaseClasificador').val());
	for (var i = 0; i <= patronesUbicados.length - 1; i++) {
		patron = patronesUbicados[i];
		console.log("--- Revisando patron: " +
			patron.numeroRegistroPatronal + patron.modalidad.numModalidad + patron.digVerificador + " - " +
			patron.nombreComercial + " - Clase: " + patron.clasificacion.fraccion.clase.descripcion);
		if (patron.clasificacion.fraccion.clase.descripcion != $('#cveclaseClasificador').val()) {
			console.log("--- Encontre un patron con clase diferente");
			claseDif = true;
		}
	}
	console.log("::: claseDif: " + claseDif);					
	if (claseDif) {
		console.log("::: Las clase del clasificador y patron domicilio anterior son diferentes se toma prima del clasificador, " + $("#primaClasificador").val());
		$("#primaSRTFusionSust").val($("#primaClasificador").val());
	} else {				
		console.log("::: Las clase del clasificador y patron domicilio anterior son iguales se toma prima del patron con domicilio anterior, " + patron.clasificacion.primaSRTActual);
		$("#primaSRTFusionSust").val(patron.clasificacion.primaSRTActual);				
	}	
}

function validaPatronesyClasificacion(){	
	console.log("::: En validaPatronesyClasificacion - Validando que se tenga patrones capturados");
	errorEnCampo = false;
	descripcionError = "Este campo es obligatorio";
	$elementoValidando = $("#numeroRegistroPatronalBusqueda");
	patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
	if (patronesUbicados == null || patronesUbicados.length == 0) {
		console.log("::: Error, no se encontraron patrones a fusionar/sustituir");
		errorEnCampo = true;
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo, descripcionError);
		$("#numeroRegistroPatronalBusqueda").focus();
		return false;
	}else{
		if(codigoTramite == 176 && patronesUbicados.length != 1){ //si el tramite es aviso de cambio de domicilio solo se permite un patron capturado
			console.log("::: Error, no se capturo un patron para el tramite de aviso cambio domicilio");
			errorEnCampo = true;
			mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo, "Solo debe capturar un patr\u00F3n con domicilio anterior");
			$("#numeroRegistroPatronalBusqueda").focus();
			return false;						
		}
		console.log("::: Valor de la clase en el clasificador: " + $("#cveclaseClasificador").val() 
			+ ", Fracion del clasificador: " + $("#divison").val() + $("#grupo").val() + $("#fraccion").val())
		console.log();
		if (isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val())
			|| isEmpty($("#divison").val())) {
			console.log("::: Error, no se tiene fraccion del clasificador");
			$elementoValidando = $("#gridClasificacionNueva");
			errorEnCampo = true;
			mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo, descripcionError);
			$("#clasificacionSelector").focus();
			return false;
			}else if(codigoTramite == 21){ //Modificacion para Mm AMSRT-2 - WO436058
						console.log("::: Tramite de fusion, se valida fecha surte efecto");
						var errorCampoObligarorio = "Este campo es obligatorio";	
						var $elementoValidando = $("#fechaEfecto");
						var tempF = $elementoValidando.val();
						var errorEnCampoFecha = isEmpty(tempF);
						if (errorEnCampoFecha) {
							console.log("::: Error, la fecha surte efecto no tiene valor, fechaEfecto");
							mostrarMensajeError($elementoValidando, errorEnCampoFecha , errorCampoObligarorio);		
							$("#fechaEfecto").focus();		
							return false;
						}					
		}			
	}
	return true;
}

function mostrarMensajeErrorMovPat($campo, error , mensaje) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";
	
	if($campo != undefined) {
		var idCampo = $campo.attr("id");
		
		
		var tipoElemento = $campo.prop("tagName");
		var idSpanRequired = $campo.attr("spanRequired");
		var idSpanError = $campo.attr("spanError");
		//console.log("el id del campo es: " + idCampo + " y es un " + tipoElemento + " su spanRequerido es: " + idSpanRequired + " su spanError es: " + idSpanError);
		var valorCampo = $campo.val();
		
		if(tipoElemento != "table") {
			//ponemos el campo en rojo
			$campo.css("border",cssBorder);
		}
		//se obtiene asi para evitar escapar 
		var spanError = document.getElementById(idCampo+"Error");
		var campoRequired = document.getElementById(idCampo + "Req");
		
		if(spanError != undefined && spanError != null) {
			//console.log("se encontro el span de error y su id es: " + spanError.id );
			spanError.style.display = cssDisplay;
			
			if((mensaje != undefined || mensaje != null) && error) {
				spanError.innerHTML = mensaje;
			} else {
				spanError.innerHTML = "";
			}
		}
		
		if(campoRequired != undefined && campoRequired != null) {
			//console.log("se encontro el campo required y su id es: " + campoRequired.id);
			campoRequired.style.color = cssColor;
		}
		
	}
}

function validaClasificacion(){	
	console.log("::: En validaClasificacion");
	errorEnCampo = false;
	descripcionError = "Este campo es obligatorio";
	console.log("::: Valor de la clase en el clasificador: " + $("#cveclaseClasificador").val())
	console.log("::: Fracion del clasificador: " + $("#divison").val() + $("#grupo").val() + $("#fraccion").val());
	if (isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val())
		|| isEmpty($("#divison").val())) {
		console.log("::: Error, no se tiene fraccion del clasificador");
		$elementoValidando = $("#gridClasificacionNueva");
		errorEnCampo = true;
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo, descripcionError);
		$("#clasificacionSelector").focus();
		return false;
	}
	return true;
}

function marcaModificarPrima(elCheck) {
	console.log("::: En marcaModificarPrima, codigoTramite: " + codigoTramite);
	if(codigoTramite == 11 || codigoTramite == 12 
		|| codigoTramite == 13 || codigoTramite == 14 
		|| codigoTramite == 15 || codigoTramite == 16
	    || codigoTramite == 17 || codigoTramite == 18
		|| codigoTramite == 19 || codigoTramite == 22
		|| codigoTramite == 7
	){
		if (elCheck.checked) {
			if(validaClasificacion()){
				console.log("::: Se edita la prima por el patron");
				$("#primaSRTRestoTramites").prop('disabled', false);			
			}else{
				console.log("::: No se ha capturado la clasificacion");
				document.getElementById("modPrimaPatron").checked  = false;
			}			
		} else {
			console.log("::: Se deshabilita editar prima por el patron, y se regresa a su valor original");
			console.log(":::fraccionActual: " + $("#fraccionActual").val() + ", fraccionClasificador: " + $("#fraccionClasificador").val()
				+ ", Prima Actual: " + $("#objClasPrimaSRTActual").val() + ", primaClasificador: " + $("#primaClasificador").val()
				+ ", Clase Actual: " + $("#cveClaseActual").val() + ", claseClasificador: " + $("#cveclaseClasificador").val());
			$("#primaSRTRestoTramites").prop('disabled', true);
			//Si la clase del clasificador es la misma que clase la actual del patron se deja la prima del patron
			if ($("#cveClaseActual").val() == $("#cveclaseClasificador").val()) {
				$("#primaSRTRestoTramites").val($("#objClasPrimaSRTActual").val());
			} else {
				$("#primaSRTRestoTramites").val($("#primaClasificador").val());
			}
		}
	}else if(codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176){
		if(elCheck.checked){			
			if(validaPatronesyClasificacion()){
				console.log("::: Se edita la prima por el patron");
				$("#primaSRTFusionSust").prop('disabled', false);				
			}else{
				console.log("::: No se ha capturado patrones o la clasificacion");
				document.getElementById("modPrimaPatronFS").checked  = false;
			}			
		}else{
			if (codigoTramite == 176) {
				console.log("::: Se regresa a su valor original");
				$("#primaSRTFusionSust").val($("#primaClasificador").val());
			} else {
				console.log("::: Se deshabilita editar prima por el patron, y se pone en blanco el campo para que vuelva a calcular la prima");
				$("#primaSRTFusionSust").val("");	
			}
			$("#primaSRTFusionSust").prop('disabled', true);
		}
	}

}

function finalizarTramite(firmaResponse) {
	var sSource = context + 'procesarDatosFirma';
	prepararRequest(sSource, firmaResponse, false, finalizarClasificacion);
}

function guardarTramite() {
	construirDialogoConfirmar('Guardar', guardarClasificacion);
}

function guardarCerrarTramite() {
	construirDialogoConfirmar('Guardar antes de cerrar', guardarCerrarClasificacion);
}

function guardarCerrarClasificacion() {
	generarObjetoClasificacion();
	$.blockUI();
	sendToServer('clasificacion', 'Guardar', clasificacion, callbackGuardarCerrarClasificacion);
}



function callbackGuardarCerrarClasificacion(response) {
	$.unblockUI();
	procesarRespuestaGuardarCerrar(response, cerrarWizard);
}

function cancelarTramite() {
	var url = context_path + '/movPat/wizard/tramite/clasificacion/cancelar/solicitud/' + idSolicitud;
	if(origenInternet){
		url = context_path + "/movPat/internet/wizard/tramite/clasificacion/cancelar/solicitud/" + idSolicitud;
	}

	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog("option", "buttons", [{
			text: 'Aceptar',
			click: function() {
				$(this).dialog('close');
				parent.WizardModificacionPatronClasificacionCtrl.cerrar();
			}
		}]);
		dialogoConfirmar.dialog("open");
	}).error(function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {
	parent.WizardModificacionPatronClasificacionCtrl.cerrar();
}

function adjuntarDocumento() {
	console.log("entrando al metodo ajax para subir documentos");
	var inputFile = $("input[name='updateFile-01']");
	var btnAdjuntar = $('#btn_AdjuntarImssRelacion-01');
	var cveSolicitud = $('#folio').val();
	var cveTipoTramite = $('#claveTipoTramite').val();
	var numDoc = $('#numDoc').val();

	/* Archivo adjunto */
	var file = inputFile.val();
	var extensionFile = file.substring(file.lastIndexOf("."));
	var fileSize;

	try {
		fileSize = inputFile[0].files[0].size;

	} catch (e) {
		fileSize = 0;

	}

	//var siezekiloByte = parseInt(fileSize / 1024);
	if (file == "") {
		construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Debe adjuntar un archivo primero", true);
	} else if (extensionFile !== ".pdf" && extensionFile !== ".PDF" && extensionFile !== ".jpg" && extensionFile !== ".doc" && extensionFile !== ".docx") {
		construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Formato de archivo invalido", true);
	} else if (fileSize > 7000000) {
		construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "El documento supera el tamaño permitido maximo permitido 7MB", true);
	} else if (numDoc == "3") {
		construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Solo se permite adjuntar 3 documentos", true);
	}
	else {
		var file_data = inputFile.prop("files")[0];

		var form_data = new FormData();
		form_data.append("file", file_data);
		form_data.append("folio", cveSolicitud);
		form_data.append("claveTipoTramite", cveTipoTramite);

		btnAdjuntar.attr("disabled", true);

		$.ajax({
			url: "/delta-gestionPatronal-web-ventanilla-dev/componente/adjuntarArchivo/guardar",
			dataType: 'json',
			cache: false,
			contentType: false,
			processData: false,
			data: form_data,
			type: 'post',
			beforeSend: function() { $.blockUI(); },
		}).done(
			function(data) {
				// var response = JSON.parse(data);
				if (data == 'ok') {
					console.log("entrando a segunda peticion ajax correcta primera");
					var form_data2 = new FormData();
					form_data2.append("folio", cveSolicitud);
					console.log("valor fromData2 " + cveSolicitud);
					$.ajax({
						url: "/delta-gestionPatronal-web-ventanilla-dev/componente/adjuntarArchivo/consultar",
						type: 'post',
						dataType: 'json',
						processData: false,
						cache: false,
						contentType: false,
						data: form_data2,
						success: function(data) {
							var nomDocs = data.substring(0, data.length - 1);
							nomDocs = nomDocs.split("|");
							$('#numDoc').val(nomDocs.length);
							console.log("datos de documentos " + nomDocs);
							if (data == "error" || data == "") {
								console.log("no se encontraron documentos");
								$('#seccionMostrarDocAdjunto').empty();
							} else {
								console.log("entramos a armar datos de documentos y vaciamos div");
								$('#seccionMostrarDocAdjunto').empty();
								$('#seccionMostrarDocAdjunto').append('<table id="tblDoc"> <tbody>  ');
								for (var i = 0; i <= nomDocs.length - 1; i++) {
									$('#seccionMostrarDocAdjunto').append("<tr> <td>"
										+ ' <p class="textDocument" id="documento-01"> ' + nomDocs[i] + '</p>'
										+ ' </td>'
										+ ' <td width="50%" valign="top"> '
										+ ' <a class="pull-right" style="cursor: pointer;" onclick="eliminarDocumento(' + '\'' + nomDocs[i] + '\'' + ')">eliminar</a> '
										+ ' </td> </tr>');
								}

								$('#seccionMostrarDocAdjunto').append(' </tbody> </table>   ');
							}
						},
						error: function(error) {
							btnAdjuntar.attr("disabled", false);
							$('#documento-01').html('Error interno al consultar documentos del tramite');
						}
					});
					btnAdjuntar.attr("disabled", false);
					$.unblockUI();
				} else {
					console.log("el servidor mando un error de datos");
					//                	console.log("peso: " + data.peso + ", existe: " + existe + ", error: " + data.error);
					$.unblockUI();
					btnAdjuntar.attr("disabled", false);
					construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + ", " + data, true);
				}
				inputFile.val('');
			}).fail(function() {
				console.log("algo fallo en el metodo ajax");
				$.unblockUI();
				btnAdjuntar.attr("disabled", false);
				construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Ocurrio un error inesperado", true);
			});

	}

}

function eliminarDocumento(nomDoc) {

	var cveSolicitud = $('#folio').val();
	var form_data = new FormData();
	form_data.append("nombreArchivo", nomDoc);
	form_data.append("folio", cveSolicitud);

	$.ajax({
		url: "/delta-gestionPatronal-web-ventanilla-dev/componente/adjuntarArchivo/quitar",
		type: 'post',
		dataType: 'json',
		processData: false,
		cache: false,
		contentType: false,
		data: form_data,
		beforeSend: function() { $.blockUI(); },
		success: function(data) {
			if (data == "ok") {
				var form_data2 = new FormData();
				form_data2.append("folio", cveSolicitud);
				console.log("valor fromData2 " + cveSolicitud);
				$.ajax({
					url: "/delta-gestionPatronal-web-ventanilla-dev/componente/adjuntarArchivo/consultar",
					type: 'post',
					dataType: 'json',
					processData: false,
					cache: false,
					contentType: false,
					data: form_data2,
					success: function(data) {
						var nomDocs = data.substring(0, data.length - 1);
						nomDocs = nomDocs.split("|");
						console.log("Numero de documentos: " + nomDocs.length);
						console.log("datos de documentos: " + nomDocs);
						if (nomDocs == "") {
							$('#numDoc').val("0");
						} else {
							$('#numDoc').val(nomDocs.length);
						}
						if (data == "error" || data == "") {
							console.log("no se encontraron documentos");
							$('#seccionMostrarDocAdjunto').empty();
						} else {
							console.log("entramos a armar datos de documentos y vaciamos div");
							$('#seccionMostrarDocAdjunto').empty();
							$('#seccionMostrarDocAdjunto').append('<table id="tblDoc"> <tbody>  ');
							for (var i = 0; i <= nomDocs.length - 1; i++) {
								$('#seccionMostrarDocAdjunto').append("<tr> <td>"
									+ ' <p class="textDocument" id="documento-01"> ' + nomDocs[i] + '</p>'
									+ ' </td>'
									+ ' <td width="50%" valign="top"> '
									+ ' <a class="pull-right" style="cursor: pointer;" onclick="eliminarDocumento(' + '\'' + nomDocs[i] + '\'' + ')">eliminar</a> '
									+ ' </td> </tr>');
							}

							$('#seccionMostrarDocAdjunto').append(' </tbody> </table>   ');
						}
					},
					error: function(error) {
						btnAdjuntar.attr("disabled", false);
						$('#documento-01').html('Error interno al consultar documentos del tramite');
					}
				});

			} else {
				$.unblockUI();
				btnAdjuntar.attr("disabled", false);
				$('#documento-01').html('Error interno al consultar documentos del tramite');
			}
			$.unblockUI();
		},
		error: function(error) {
			console.log("algo fallo en el metodo ajax");
			$.unblockUI();
			btnAdjuntar.attr("disabled", false);

			construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Ocurrio un error inesperado", true);
		}
	});

}

function consultarDocSoporteTramite() {
	var cveSolicitud = $('#folio').val();
	var form_data2 = new FormData();
	form_data2.append("folio", cveSolicitud);
	console.log("valor fromData2 " + cveSolicitud);
	$.ajax({
		url: "/delta-gestionPatronal-web-ventanilla-dev/componente/adjuntarArchivo/consultar",
		type: 'post',
		dataType: 'json',
		processData: false,
		cache: false,
		contentType: false,
		data: form_data2,
		success: function(data) {
			var nomDocs = data.substring(0, data.length - 1);
			nomDocs = nomDocs.split("|");
			console.log("datos de documentos: " + nomDocs);
			console.log("nomDocs.length: " + nomDocs.length);
			if (nomDocs == "") {
				$('#numDoc').val("0");
			} else {
				$('#numDoc').val(nomDocs.length);
			}

			if (data == "error" || data == "") {
				console.log("no se encontraron documentos");
				$('#seccionMostrarDocAdjunto').empty();
			} else {
				console.log("entramos a armar datos de documentos y vaciamos div");
				$('#seccionMostrarDocAdjunto').empty();
				$('#seccionMostrarDocAdjunto').append('<table id="tblDoc"> <tbody>  ');
				for (var i = 0; i <= nomDocs.length - 1; i++) {
					$('#seccionMostrarDocAdjunto').append("<tr> <td>"
						+ ' <p class="textDocument" id="documento-01"> ' + nomDocs[i] + '</p>'
						+ ' </td>'
						+ ' <td width="50%" valign="top"> '
						+ ' <a class="pull-right" style="cursor: pointer;" onclick="eliminarDocumento(' + '\'' + nomDocs[i] + '\'' + ')">eliminar</a> '
						+ ' </td> </tr>');
				}

				$('#seccionMostrarDocAdjunto').append(' </tbody> </table>   ');
			}
		},
		error: function(error) {
			btnAdjuntar.attr("disabled", false);
			$('#documento-01').html('Error interno al consultar documentos del tramite');
		}
	});
}

function construirDialogoMensajes(titulo, mensaje, error) {
	$("input[name='updateFile-01']").val('');
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen: false, resizable: false, modal: true,
		height: 300, width: 450,
		title: titulo,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}


function hideDocumenosRequeridos() {
	$("#seccionCocumentosRequeridos").hide();
}

/*
*Fecha UM    : 29 de Agosto del 2021
*Version UM  : 3.2
*Autor UM    : Victor Hernandez,Erika Gutierrez
*Descripcion : Cambios EREQ-SIROC-OUTSOURCING, se deja el * de REPSE solo para Subcontratista de Ejecuci�n
                de Obra Especializada, para obra p�blica y privada.
                
*Fecha UM    : 07 de Octubre del 2021
*Version UM  : 3.5
*Autor UM    : Erika Gutierrez
*Descripcion : Cambios para el mantenimiento SIROC para tratamiento de patrones con amparo
*/

(function($){
	
	$.fn.marcarBordeError = function(marcar, idError, mensajeError) {
		var color = marcar ? '#a94442' : '#ccc';
		this.css('border-color', color);
		if(idError) {
			var $elementoError = $(idError);
			if(marcar && $elementoError.length) {
				$elementoError.removeClass("hidden");
				if(mensajeError) {
					$elementoError.html(mensajeError)
				}
			} else if ($elementoError.length){
				$elementoError.addClass("hidden");
			}
		}
		return this;
	}
}(jQuery));

$(document).ready(function(){
	$("div").on( "dialogopen", function( event, ui ) {
		var $contenedor=$(this).parent();
		var $buttonSet= $contenedor.find(".ui-dialog-buttonset");
		var numeroBotones=$buttonSet.find('button').length;
		$buttonSet.find("button").each(function(){
			var $boton = $(this);
			$boton.css("text-decoration","underline");
			$boton.addClass("ui-button");
		})
		if(numeroBotones == 1) {
			$buttonSet.removeClass("ui-dialog-buttonset");
			$buttonSet.css("text-align","center");
		}	 
	});

	$("#selObjetoContrato").on("change", function () {
		if (tipoPatron == 4 && this.value == '6') {
			$("#pnlOtroObjContrato").removeClass("hidden");
		} else if (tipoPatron == 4 && this.value !== '6') {
			$("#otroObjetoContrato").val('');
			$("#pnlOtroObjContrato").addClass("hidden");
		}
	});
});

var TIPO_PATRON = {
	INTERMEDIARIO: 4
};

var TIPO_OBRA = {
	PRIVADA: 1,
	PUBLICA: 2
};

function onlyNumber(e) {
	var mensajeMonto= "Las cantidades de los montos " + (tipoPatron == 4 ? "de contrato" : "de obra" ) + " se deben capturar sin decimales";
	var elementoDelEvento = e.target,
	campos = {
		"txtMonto" : {
			idError: "#msgeMonto",
			mensaje : mensajeMonto
		}, 
		"txtSuperficie" : {
			idError: "#msgeSuperficie",
			mensaje : "Las cantidades de superficie de construcci&oacute;n se deben capturar sin decimales"
		},
		"txtMontoEjercido" : {
			idError: "#msgMontoEjercido",
			mensaje : "Las cantidades de los montos de obra se deben capturar sin decimales"
		},
		"txtNumTrabajadores" : {
			idError: "#msgeNumTrabajadores",
			mensaje : "El numero de trabajadores debe ser mayor a 0"
		}
	};
	
	if(e.which == 46) {
		var mensajeError = null;
		if(campos[elementoDelEvento.id]) {
			var elemento = campos[elementoDelEvento.id];
			console.log("Existe el error dentro del array con el valor " + elementoDelEvento.id );
			$("#"+elementoDelEvento.id).marcarBordeError(true,elemento.idError,elemento.mensaje);
		}
		return false;
	} else if(campos[elementoDelEvento.id]) {
		var elemento = campos[elementoDelEvento.id];
		$("#"+elementoDelEvento.id).marcarBordeError(false,elemento.idError);
	}
	
	if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		return false;
	} else {
		return true;
	}
}

var crearDialogo = function(idDiv, buttons, titulo, width, hight) {
       
       var width1 = width ? width : "300px";
       var hight1 = hight ? hight : "auto";
       
       var $divMensajes = $(idDiv);
       $divMensajes.dialog({
             resizable: false,
             width: width1,
             height:hight1,
             modal: true,
             title: titulo,
             autoOpen: false,
             closeOnEscape: false,
             buttons: buttons
       }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();;

       //$divMensajes.html(mensaje);
       $divMensajes.dialog('open');
}



function cargaEtiquetas() {
	var rpAmparo = $("#patronAmparo").val();
	var cadena  = "";
	if(rpAmparo == 1){
		$("#selObjetoContrato option").each(function(){
		   if($(this).attr('value') <= 1){
		   	cadena +='<option value='+$(this).attr('value')+'>'+$(this).text()+'</option>';
		   }
		});
		console.log(cadena);
	}else{
		$("#selObjetoContrato option").each(function(){
			   if($(this).attr('value')==0 || $(this).attr('value') > 1 ){
			   	cadena +='<option value='+$(this).attr('value')+'>'+$(this).text()+'</option>';
			   }
		});
	}
	$("#selObjetoContrato option").remove();
	$("#selObjetoContrato").append(cadena);
	
	
	var rfcSinNumReg = $("#rfcSinNumReg").val(), numAviso = $("#txtNumRegistro").val(),
	etiquetas = {
		enviarInfo: 'Enviar Informaci&oacute;n',
		fechaInicio: 'Fecha de inicio',
		fechaTermino: 'Fecha de t&eacute;rmino',
		objetoContr: 'Objeto del contrato',
		montoObra: 'Monto de la obra',
		superficieCons: 'Superficie de construcci&oacute;n',
		tipoObra: 'Tipo de obra',
		montoContrato: 'Monto del contrato',
		registrarObra: 'Registrar Obra',
		montoContratoSubEsp: 'Monto de contrato para la ejecuci\u00f3n de la obra especializada',
		objetoObraSubEsp: 'Objeto de la obra especializada a ejecutar',
		numTrabajadores : "N&uacute;mero de trabajadores",
		numeroREPSE: "N&uacute;mero de REPSE",
		observacionesIntermediario: "Observaciones del registro de contrato"
	};
	
	//$("#txtNumAviso").attr("disabled", true);
	var separador = rfcSinNumReg != '' && rfcSinNumReg != null ? ' :' : '  *:';
	$("#btnDatosObra").html(rfcSinNumReg != '' && rfcSinNumReg != null ? etiquetas.enviarInfo : etiquetas.registrarObra);

	$("#btnDatosObra").html(etiquetas.enviarInfo);
	$("#idLabelFechaIni").text(etiquetas.fechaInicio + separador);
	$("#idLabelFechaFin").html(etiquetas.fechaTermino +separador);
	$("#idLabObjeto").html(etiquetas.objetoContr +separador);
	$("#idLabMonto").text(etiquetas.montoObra  +separador);
	$("#idLabSuper").html(etiquetas.superficieCons  +separador);
	$("#idLabObra").text(etiquetas.tipoObra  + separador);
	$("#idNumTrabajadores").text(etiquetas.numTrabajadores + separador);

	if (tipoPatron == "4") {

		$("#observaciones").text(etiquetas.observacionesIntermediario + ":");
		$("#comment").attr('placeholder',etiquetas.observacionesIntermediario);
		$("#fechasEjecucion").text("Vigencia del contrato");
		if (rfcSinNumReg != '' && rfcSinNumReg != null) {
			$("#idLabMonto").text(etiquetas.montoContrato + ' :');
		} else {
			$("#idLabMonto").text(etiquetas.montoContrato + ' *:');
		}
		$("#msgeMonto").text("Capture monto del contrato");
		$("#txtMonto").attr('placeholder',etiquetas.montoContrato);
	} else {
		$("#fechasEjecucion").html("Periodo de ejecuci&oacute;n");
		$("#msgeMonto").text("Capture el monto de la obra");
		$("#txtMonto").attr('placeholder',etiquetas.montoObra);
	}
	
	if(tipoPatron == "5"){
	/*$("#idLabObjeto").text(etiquetas.objetoObraSubEsp + ' *:');*/
	$("#idLabMonto").text(etiquetas.montoContratoSubEsp + ' *:');
	}

	//Tipo obra privada
	if(tipoObra == TIPO_OBRA.PRIVADA) {
		if(tipoPatron == "1") { //propietario
			$("#idLabSuper").html(etiquetas.superficieCons  +' *:');
			$("#btnDatosObra").text(etiquetas.registrarObra);
			$("#pnlRFC").addClass("hidden");
			$("#pnlOtroObjContrato").addClass("hidden");
			$("#pnlNumTrabajadores").addClass("hidden");
			$("#pnlNumRegSTPS").addClass("hidden");
			$("#pnlObjetoContratoSubEsp").addClass("hidden");
			$("#infoObjetoContratoSE").addClass("hidden");

		} else if(tipoPatron == "2" || tipoPatron == "3" || tipoPatron == "4" || tipoPatron == "5") {
			
			if(tipoPatron == "4") {
				$("#fechasEjecucion").text("Vigencia del contrato");
			}
			
			if ($('#chkSinRegistro').is(":checked")) {
				$("#pnlDatosUbicacionObra").removeClass("hidden");
				$("#pnlDomicilioFiscal").addClass("hidden");
				$("#pnlRegistroPatronal").addClass("hidden");
				$("#pnlRazonSocial").addClass("hidden");

				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").addClass("hidden");
				$("#pnlObservaciones").addClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").addClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "2" || tipoPatron == "3"){
				$("#pnlRFC").addClass("hidden");
				$("#idLabRepse").html(etiquetas.numeroREPSE + ':');//se quita
				if(tipoPatron == "3") {
					$("#idLabSuper").html('Superficie de construcci&oacute;n*');
				}
				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").removeClass("hidden");
				$("#pnlTipoObra").removeClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").removeClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "4"){
				$("#pnlRFC").addClass("hidden");
				$("#pnlObjetoContrato").removeClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").addClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "5") {
				$("#idLabRepse").html(etiquetas.numeroREPSE + '*:');//SE QUEDA *
				$("#pnlRFC").addClass("hidden");
				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlNumTrabajadores").removeClass("hidden");
				$("#pnlNumRegSTPS").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlObjetoContratoSubEsp").removeClass("hidden");
				$("#infoObjetoContratoSE").removeClass("hidden");
			}
		}
	} else {
		if(tipoPatron == "2") {
			$("#idLabRepse").html(etiquetas.numeroREPSE + ':');//se quita
			$("#idLaProcedimiento").html('N&uacute;mero de procedimiento*');
			$("#idLabSuper").html('Superficie de construcci&oacute;n');
			$("#btnDatosObra").text('Registrar Obra');
			$("#pnlRFC").addClass("hidden");
			$("#pnlOtroObjContrato").addClass("hidden");
			$("#pnlNumTrabajadores").addClass("hidden");
			$("#pnlNumRegSTPS").removeClass("hidden");
			$("#pnlObjetoContratoSubEsp").addClass("hidden");
			$("#infoObjetoContratoSE").addClass("hidden");
		} else if(tipoPatron == "3" || tipoPatron == "4" || tipoPatron == "5") {
			
			if(tipoPatron == "4") {
				$("#fechasEjecucion").text("Vigencia del contrato");
			}

			if ($('#chkSinRegistro').is(":checked")) {
				$("#pnlDatosUbicacionObra").removeClass("hidden");
				$("#pnlDomicilioFiscal").addClass("hidden");
				$("#pnlRegistroPatronal").addClass("hidden");
				$("#pnlRazonSocial").addClass("hidden");
				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").addClass("hidden");
				$("#pnlObservaciones").addClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").addClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "3") {
				$("#idLabRepse").html(etiquetas.numeroREPSE + ':')
				$("#pnlRFC").addClass("hidden");
				$("#idLabSuper").html('Superficie de construcci&oacute;n');
				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").removeClass("hidden");
				$("#pnlTipoObra").removeClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").removeClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "4") {
				$("#pnlRFC").addClass("hidden");
				$("#pnlObjetoContrato").removeClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlNumTrabajadores").addClass("hidden");
				$("#pnlNumRegSTPS").addClass("hidden");
				$("#pnlObjetoContratoSubEsp").addClass("hidden");
				$("#infoObjetoContratoSE").addClass("hidden");
			} else if(tipoPatron == "5") {
				console.log('entre a funciones comunes 308 ');
				$("#idLabRepse").html(etiquetas.numeroREPSE + '*:');//SE QUEDA *
				$("#pnlRFC").addClass("hidden");
				$("#pnlObjetoContrato").addClass("hidden");
				$("#pnlOtroObjContrato").addClass("hidden");
				$("#pnlSuperficie").addClass("hidden");
				$("#pnlTipoObra").addClass("hidden");
				$("#numProcedimiento").addClass("hidden");
				$("#numAviso").removeClass("hidden");
				$("#pnlNumTrabajadores").removeClass("hidden");
				$("#pnlNumRegSTPS").removeClass("hidden");
				$("#pnlObservaciones").removeClass("hidden");
				$("#pnlObjetoContratoSubEsp").removeClass("hidden");
				$("#infoObjetoContratoSE").removeClass("hidden");
			}
		}
	}

	// Etiqueta de Registro de Obra o Registro de Aviso de Obra
	if (!((tipoObra == 1 && tipoPatron == "1") || (tipoObra == 2 && tipoPatron == "2")) && $("#chkSinRegistro").is(":checked")) {
		$("#lblTituloHeader").html("Registro de Aviso de Ubicaci&oacute;n de Obra");
		$("#lblIndicacionHeader").html("Complete los pasos para realizar el registro de un Aviso de Ubicaci&oacute;n de Obra.");
		$("#lblObligatorios").hide();
	}
	
	if(numAviso != "" && (numAviso.indexOf("C") == -1)){
		$("#txtNumAviso").val(numAviso);
		$("#txtNumAviso").attr("disabled", true);
	}
}


function getDate(){

	var d;
	$.ajax({
		type : "GET",
		contentType : "application/json",
		async : false,
		url : '/sdroc_web/fechaActual',
		success : function(response) {
			d = new Date(response);
		},
		error:function(response){
			console.log('error recuperando fecha ... ' + response);
		}
	});
	
	return d;
}

var ssoVwUsuarioDTORegistro;
var paginaActual=1;
var formaPrincipal;
var formNumeroOficio;
var formArchivoPrimero;
var formArchivoSegundo;
var formFechaPublicacion;
var formFechaPublicacionInfo;
var formFechaInicioPublicacion;
var formFechaFinPublicacion;
var formFechaRetiro;
var formNumeroOficioDocumento;
var formTipoDocumento;
var notificacionVO;
var flagPatronValido=false;
var numCaracteresDomicilio = 200;
var listDiasInhabilDTOs;
//var valorAnteriorRP;
var SUJETO_NOTIFICAR_OTRO=4
//Normativo-Proceso-AreaResponseable;

//$(document).ready(function() {
//	$("#txtRegistroPatronal").keyup(function() {
//		if ($('input:radio[name=rdSujetoNotificar]:checked').val() == 2)
//			if ($("#txtRegistroPatronal").val().length == 11) {
//				if (valorAnteriorRP != $("#txtRegistroPatronal").val()) {
//					valorAnteriorRP = $("#txtRegistroPatronal").val();
//					validaRegistroPatronal();
//				}
//			}
//		}
//	});
//});

function mostrarDivSujetoPatron() {
	ocultarCamposSujetoNotificar();
	ocultarCamposContadorPublico();
	ocultarCamposOtros();
	$("#divRegistroPatronal").show();
//	$("#botonBuscarRP").show();
	$("#txtNomDenRazonSocialPatron").rules("add", "required");
	$("#txtNomDenRazonSocialSujObl").rules("remove", "required");
	$("#txtNombreContador").rules("remove", "required");
	$("#txtNomDenRazonSocialOtro").rules("remove", "required");
	$("#idTrNomDenRazonSocialPatron").show();
	$("#divDomicilioPatron").show();
	$("#txtRegistroIMSS").rules("remove", "required");
	$("#txtRegistroPatronal").rules("add", "required");
	$("#domicilioResumen").html('<label><b>Domicilio:</b> </label>');
	flagPatronValido=false;
	limpiaDomicilios();
}

function mostrarDivSujetoObligado() {
	ocultarCamposPatron();
	ocultarCamposSujetoNotificar();
	ocultarCamposContadorPublico();
	ocultarCamposOtros();
//	$("#divRegistroPatronal").show();
//	$("#botonBuscarRP").hide();
	$("#idTrNomDenRazonSocialSujObl").show();
	$("#divDomicilioSujObl").show();
	$("#txtRegistroPatronal").rules("remove", "required");
	$("#txtNomDenRazonSocialSujObl").rules("add", "required");
	$("#txtNomDenRazonSocialPatron").rules("remove", "required");
	$("#txtNombreContador").rules("remove", "required");
	$("#txtNomDenRazonSocialOtro").rules("remove", "required");
	$("#txtRegistroIMSS").rules("remove", "required");
	$("#domicilioResumen").html('<label><b>Domicilio:</b> </label>');
	flagPatronValido = true;
	limpiaDomicilios();
}

function mostrarDivSujetoContador() {
	ocultarCamposPatron();
	ocultarCamposSujetoNotificar();
	ocultarCamposOtros();
	$("#divRegistroIMSS").show();
	$("#divDomicilioFiscal").show();
	$("#txtNomDenRazonSocialPatron").rules("remove", "required");
	$("#txtNomDenRazonSocialSujObl").rules("remove", "required");
	$("#txtNombreContador").rules("add", "required");
	$("#txtNomDenRazonSocialOtro").rules("remove", "required");
	$("#idTrNombreContador").show();
	$("#txtRegistroPatronal").rules("remove", "required");
	$("#txtRegistroIMSS").rules("add", "required");
	$("#domicilioResumen").html('<label><b>Domicilio Fiscal:</b> </label>');
	flagPatronValido=true;
	limpiaDomicilios();
}

function mostrarDivSujetoOtro() {
	ocultarCamposPatron();
	ocultarCamposSujetoNotificar();
	ocultarCamposContadorPublico();
	$("#divDomicilioSujNot").show();
	$("#txtNomDenRazonSocialPatron").rules("remove", "required");
	$("#txtNomDenRazonSocialSujObl").rules("remove", "required");
	$("#txtNombreContador").rules("remove", "required");
	$("#txtNomDenRazonSocialOtro").rules("add", "required");
	$("#idTrNomDenRazonSocialOtro").show();
	$("#divSujetoNotificarOtro").show();
	$("#txtRegistroIMSS").rules("remove", "required");
	$("#txtRegistroPatronal").rules("remove", "required");
	$("#domicilioResumen").html('<label><b>Domicilio:</b> </label>');
	flagPatronValido=true;
	limpiaDomicilios();
}

function ocultarCamposSujetoNotificar() {
	$("#divRegistroPatronal").hide();
	$("#txtRegistroPatronal").val("");
	
	$("#divRegistroIMSS").hide();
	$("#txtRegistroIMSS").val("");
	
	$("#divDomicilioSujObl").hide();
	
	$("#divSujetoNotificarOtro").hide();
	
	$("#idTrNomDenRazonSocialSujObl").hide();
	$("#txtNomDenRazonSocialSujObl").val("");
	limpiaDomicilios();
}

function ocultarCamposContadorPublico() {	
	$("#divDomicilioFiscal").hide();
	
	$("#idTrNombreContador").hide();
	$("#txtNombreContador").val("");
	limpiaDomicilios();
}

function ocultarCamposOtros() {
	$("#divDomicilioSujNot").hide();
	
	$("#idTrNomDenRazonSocialOtro").hide();
	$("#txtNomDenRazonSocialOtro").val("");
	limpiaDomicilios();
}

function ocultarCamposPatron() {
	$("#divDomicilioPatron").hide();
	
	$("#idTrNomDenRazonSocialPatron").hide();
	$("#txtNomDenRazonSocialPatron").val("");
	limpiaDomicilios();
}

function cambiarPagina(valor){
	pasaValor();
//	consultaArchivosOtros();
	
	$('form#formaResumen :input').prop('disabled','disabled');
	$('#botonVisualizaOficioAcuerdo').removeAttr('disabled');
	$('#botonVisualizaDocumentoNotificar').removeAttr('disabled');
	$('#listaArchivosOtrosResumen').removeAttr('disabled');
	$('#botonVisualizaOtrosResumen').removeAttr('disabled');
	
	$("#contenedorPrincipalReg").hide();
	$("#contenedorSecundariolReg").hide();
	$("#contenedorResumenReg").hide();
	
	paginaActual+=valor;
	switch(paginaActual){
		case 1:
			$("#etiquetaTitulo").html("&nbsp;");
			$("#contenedorPrincipalReg").show();
			break;
		case 2:
			$("#etiquetaTitulo").text("Datos del Documento a notificar");
			$("#contenedorSecundariolReg").show();
			break;
		case 3:
			$("#contenedorResumenReg").show();
			$("#etiquetaTitulo").text("Resumen de la solicitud de publicaci\u00f3n por estrados");
			break;
	}
}

function obtenerInstanciaNotificacion(idNotificacion){
	var sVarSeg='{"cveNotificaciones":"'+idNotificacion+'"}';
	var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaNotificacion.do", parametroIdNotificacion,function(data) {
		notificacionVO=data;
	}).error(function(data){
		
	}).complete(function(data){
		$.unblockUI();
	});	
}

function validaRegistroPatronal(){
	var rp = $("#txtRegistroPatronal").val();
	if (rp.length == 11) {
		var sVarSeg='{"registroPatronal":"'+rp.toUpperCase()+'"}';
		var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
		$.blockUI();
		$.postJSON_Sync(getAppContextParaJS()+"/estrados/validaRegistroPatronal.do", parametroIdNotificacion,function(data) {
			if (data==null) {
				generaDialogo("Registro patronal incorrecto");
				return;
			}
			if (data != null && (data.codigo!=0 && data.codigo != 1001)) {
				generaDialogo(data.descripcion);
				$("#txtRegistroPatronal").val("");
				$("#txtNomDenRazonSocialPatron").val("");
				$("#txtDomicilioPatron").val("");
//				if ($('input:radio[name=rdSujetoNotificar]:checked').val() == 1) {
//					$("#txtRegistroPatronal").val("");
//					$("#txtNomDenRazonSocialPatron").val("");
//					$("#txtDomicilioPatron").val("");
//				} else if ($('input:radio[name=rdSujetoNotificar]:checked').val() == 2) {
//					$("#txtRegistroPatronal").val("");
//					$("#txtNomDenRazonSocialSujObl").val("");
//					$("#txtDomicilioSujObl").val("");
//				}
			} else {
				$("#txtNomDenRazonSocialPatron").val(data.razonSocial);
				$("#txtDomicilioPatron").val(data.domicilio+" LOCALIDAD:"+data.localidad+" C.P."+data.codigoPostal);	
				flagPatronValido=true;
//				if ($('input:radio[name=rdSujetoNotificar]:checked').val() == 1) {
//					$("#txtNomDenRazonSocialPatron").val(data.razonSocial);
//					$("#txtDomicilioPatron").val(data.domicilio+" LOCALIDAD:"+data.localidad+" C.P."+data.codigoPostal);
//					flagPatronValido=true;
//				} else if ($('input:radio[name=rdSujetoNotificar]:checked').val() == 2) {
//					$("#txtNomDenRazonSocialSujObl").val(data.razonSocial);
//					$("#txtDomicilioSujObl").val(data.domicilio+" LOCALIDAD:"+data.localidad+" C.P."+data.codigoPostal);
//					flagPatronValido=true;
//				}
			}
		}).error(function(data) {
			
		}).complete(function(data) {
			$.unblockUI();
		});
	}
}

//Funcion que determina el tipo de usuario 
function determinarSujetoANotificar() {
	var resultado;
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultaAreaResponsableNotificacion.do", null,function(data) {
		resultado = data;
		if (data != null) {
			var options = "<option value='-1' >--Por favor seleccione--</option>";
			for (var s = 0; s < data.length; s++) {
				options += "<option value='"+ data[s].areanormativaDTO.cveAreanorma +"-"+data[s].procesoDTO.cveProceso+"-"
								+data[s].cveAreaRespNotif+"'>"+ data[s].procesoDTO.desProceso+"</option>";
			}
			if (ssoVwUsuarioDTORegistro.cveDelegacion != null || ssoVwUsuarioDTORegistro.cveSubdelegacion != null) {
				$('#idSpanAreaResponsableNotificacion').text( "\u00c1rea responsable de la notificaci\u00f3n:" );
				$('#idSpanAreaResponsableNotificacionResumen').text( "\u00c1rea responsable de la notificaci\u00f3n:" );
			} else {
				$('#idSpanAreaResponsableNotificacion').text( "Materia del documento a notificar:" );
				$('#idSpanAreaResponsableNotificacionResumen').text( "Materia del documento a notificar:" );
			}
			$(" #cmbAreaResposableNotificacion").html(options);
		} else {
			generaDialogo("No se pudo obtener la lista de las \u00e1reas responsables, int\u00e9ntalo m\u00e1s tarde.");
		}
	}).error(function(data) {
		$.unblockUI();
	}).complete(function(data) {
		$.unblockUI();
	});	
	return resultado;
}

function recuperaDatosHeaderRegistro() {
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultHeader.do", null,function(data) {
		ssoVwUsuarioDTORegistro = data;
		$("#usuario").html("<b>Usuario: </b>"+data.desUsrCURP);
		$("#fecha").html("<b>Fecha: </b>"+data.fechaSistema);
		
		if (data.desDelegacion!=null && data.desDelegacion!='null') {
			$("#delegacion").html("<b>Delegaci&oacute;n: </b>"+data.desDelegacion);			
		}else{
			$("#delegacion").html("<b>Delegaci&oacute;n:</b>");
		}
		
		if (data.desSubdelegacion!=null && data.desSubdelegacion!="null") {
			$("#subdelegacion").html("<b>Subdelegaci&oacute;n: </b>"+data.desSubdelegacion);			
		} else {
			$("#subdelegacion").html("<b>Subdelegaci&oacute;n: </b>");	
		}
		
		if (data.desAreaNorma!=null && data.desAreaNorma!='null') {
			$("#areaNorma").html("<b>&Aacute;rea : </b>"+data.desAreaNorma);
		} else {
			$("#areaNorma").html("<b>&Aacute;rea : </b>"+data.desAreaNorma);				
		}
		
		if (data.desDepartamento!=null && data.desDepartamento!="null") {
			$("#departamento").html("<b>Departamento: </b>"+data.desDepartamento);	
		} else {
			$("#departamento").html("<b>Departamento: </b>");	
		}
	}).error(function(data) {
		$.unblockUI();
	}).complete(function(data) {
		$.unblockUI();
	});
}

function recuperaTiposDocumento(){
	var sVarSeg='{"desProceso":"'+$("#cmbAreaResposableNotificacion").val()+'"}';
	var parametroProceso = jQuery.parseJSON(sVarSeg);
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaTiposDocumento.do", parametroProceso,function(data) {
		var options = "<option value='-1' >--Por favor seleccione--</option>";		
		for(var s=0;s<data.length;s++){
			options += "<option value='"+ data[s].cveTipodocto +"'>"+ data[s].desTipodocumento+"</option>";				
		}
		$(" #cmbCveTipoDenuncianteDT").html(options);
	}).error(function(data){
		
	}).complete(function(data){
		$.unblockUI();
	});
}

function generaValidadorPrincipal(){
	
	formaPrincipal=$("#idFormRegistroPrincipal").validate({
	       rules: {
	    	   txtRegistroPatronal:{
	    		   required:true,
	    		   minlength: 11,
	    		   maxlength: 11,
				   regexp :/^[0-9A-Za-z]{8}[0-9]{3}$/
	    	   },
	    	   txtNomDenRazonSocialPatron: {
	    		   required:true,
	    		   maxlength: 200
	    	   },
	    	   txtNomDenRazonSocialSujObl: {
	    		   required:true,
	    		   maxlength: 200
	    	   },
	    	   txtNombreContador: {
	    		   required:true,
	    		   maxlength: 200
	    	   },
	    	   txtNomDenRazonSocialOtro: {
	    		   required:true,
	    		   maxlength: 200
	    	   },
	    	   txtDomicilioPatron: {
	    		   required:true,
	    		   maxlength: 200
	    	   },txtDomicilioSujObl: {
	    		   required:true,
	    		   maxlength: 200
	    	   },txtDomicilioFiscal: {
	    		   required:true,
	    		   maxlength: 200
	    	   },txtDomicilioSujNot: {
	    		   required:true,
	    		   maxlength: 200
	    	   },
	    	   txtRegistroIMSS:{
	    		   minlength: 10,
	    		   maxlength: 10,
	    		   regexp :/^[A-Za-z0-9\-]{10}$/
	    	   }
			},
			messages: {
				txtNomDenRazonSocialPatron: "Nombre denominaci\u00f3n es requerida",
				txtNomDenRazonSocialSujObl: "Nombre denominaci\u00f3n es requerida",
				txtNombreContador: "Nombre es requerido",
				txtNomDenRazonSocialOtro: "Nombre denominaci\u00f3n es requerida",
				txtDomicilioPatron:{
					required:"Domicilio es requerido",
					maxlength:"N\u00famero m\u00e1ximo de car\u00e1cteres = 200"
				},
				txtDomicilioSujObl:{
					required:"Domicilio es requerido",
					maxlength:"N\u00famero m\u00e1ximo de car\u00e1cteres = 200"
				},
				txtDomicilioFiscal:{
					required:"Domicilio fiscal es requerido",
					maxlength:"N\u00famero m\u00e1ximo de car\u00e1cteres = 200"
				},
				txtDomicilioSujNot:{
					required:"Domicilio es requerido",
					maxlength:"N\u00famero m\u00e1ximo de car\u00e1cteres = 200"
				},
				txtRegistroIMSS:"N\u00famero seguro social es requerido",
				txtRegistroPatronal:{
					required:"Registro patronal requerido",
					minlength:"Registro patronal debe ir a 11 posiciones",
					maxlength:"Registro patronal debe ir a 11 posiciones"
				},
				txtRegistroIMSS:{
					required:"Registro imss requerido",
					minlength:"Registro imss debe ir a 10 posiciones",
					maxlength:"Registro imss debe ir a 10 posiciones"
				}
			}
		});
	$("#cmbAreaResposableNotificacion").rules("add", "comboRequerido");
}

function generaValidadorTextArea() {
	
	$('#txtDomicilioPatron').keyup(function() {
	    var longitud = $(this).val().length;
	    var resto = numCaracteresDomicilio - longitud;
	    if(resto <= 0){
	    	$('#txtDomicilioPatron').val($('#txtDomicilioPatron').val().substring(0, numCaracteresDomicilio));
	        $('#txtDomicilioPatron').attr("maxlength", numCaracteresDomicilio);
	    }
	});

	$('#txtDomicilioSujObl').keyup(function() {
	    var longitud = $(this).val().length;
	    var resto = numCaracteresDomicilio - longitud;
	    if(resto <= 0){
	    	$('#txtDomicilioSujObl').val($('#txtDomicilioSujObl').val().substring(0, numCaracteresDomicilio));
	        $('#txtDomicilioSujObl').attr("maxlength", numCaracteresDomicilio);
	    }
	});

	$('#txtDomicilioFiscal').keyup(function() {
	    var longitud = $(this).val().length;
	    var resto = numCaracteresDomicilio - longitud;
	    if(resto <= 0){
	    	$('#txtDomicilioFiscal').val($('#txtDomicilioFiscal').val().substring(0, numCaracteresDomicilio));
	        $('#txtDomicilioFiscal').attr("maxlength", numCaracteresDomicilio);
	    }
	});

	$('#txtDomicilioSujNot').keyup(function() {
	    var longitud = $(this).val().length;
	    var resto = numCaracteresDomicilio - longitud;
	    if(resto <= 0){
	    	$('#txtDomicilioSujNot').val($('#txtDomicilioSujNot').val().substring(0, numCaracteresDomicilio));
	        $('#txtDomicilioSujNot').attr("maxlength", numCaracteresDomicilio);
	    }
	});
}

function generaValidadorSecundario() {
	
	formNumeroOficio = $("#idFormNumeroOficio").validate();

	$("#numeroOficio").rules("add", {
        required: true,
        minlength: 5,
		maxlength: 50,
		regexp :/^[0-9A-Za-z\./\_\-]{5,50}$/,
        messages: {
            required: "Debe ingresar un n\u00famero de oficio del acuerdo",
        	minlength : "N\u00famero minimo de car\u00e1cteres = 5",
			maxlength : "N\u00famero m\u00e1ximo de car\u00e1cteres = 50"
        }
    });
	
	jQuery("#numeroOficio").keypress(function(tecla) {
	    if((tecla.charCode < 00 || tecla.charCode > 31) &&
	    		(tecla.charCode < 45 || tecla.charCode > 57) &&
	    		(tecla.charCode < 65 || tecla.charCode > 90) &&
	    		(tecla.charCode < 97 || tecla.charCode > 122) &&
	    		(tecla.charCode != 95) && (tecla.charCode != 127) &&
	    		(tecla.charCode != 165) && (tecla.charCode != 164)) {
	    	return false;
	    }
	});
	
	formArchivoPrimero = $("#formaArchivoPrimero").validate();

	$("form#formaArchivoPrimero #file").rules("add", {
		archivoPDF :/.{1,500}\.(pdf|PDF)$/,
	});
	
	formNumeroOficioDocumento = $("#idFormNumeroOficioDocumento").validate();

	$("#numeroOficionDocumentoNotificar").rules("add", {
        required: true,
        minlength: 5,
		maxlength: 50,
		regexp :/^[0-9A-Za-z\./\_\-]{5,50}$/,
        messages: {
            required: "Debe ingresar un n\u00famero de oficio o documento",
        	minlength : "N\u00famero minimo de car\u00e1cteres = 5",
			maxlength : "N\u00famero m\u00e1ximo de car\u00e1cteres = 50"
        }
    });
	
	jQuery("#numeroOficionDocumentoNotificar").keypress(function(tecla) {
	    if((tecla.charCode < 00 || tecla.charCode > 31) &&
	    		(tecla.charCode < 45 || tecla.charCode > 57) &&
	    		(tecla.charCode < 65 || tecla.charCode > 90) &&
	    		(tecla.charCode < 97 || tecla.charCode > 122) &&
	    		(tecla.charCode != 95) && (tecla.charCode != 127) &&
	    		(tecla.charCode != 165) && (tecla.charCode != 164)) {
	    	return false;
	    }
	});
	
	formTipoDocumento = $("#idFormCmbCveTipoDenuncianteDT").validate();

	$("#cmbCveTipoDenuncianteDT").rules("add", {
		comboTipoDocumentoRequerido: true
    });
	
	formArchivoSegundo = $("#formaArchivoSegundo").validate();

	$("form#formaArchivoSegundo #file").rules("add", {
		archivoPDF :/.{1,500}\.(pdf|PDF)$/,
    });
	
	formFechaPublicacion = $("#idFormFechaPublicacion").validate();
	
	formFechaPublicacionInfo = $("#idFormFechaPublicacionInfo").validate();
	
	$("#fechaPublicacionInfo").rules("add", {
        required: true,
        messages: {
            required: "Favor de ingresar la fecha de publicaci\u00f3n",
        }
    });
	
	formFechaInicioPublicacion = $("#idFormFechaInicioPublicacion").validate();
	
	$("#fechaInicioPublicacion").rules("add", {
        required: true,
        messages: {
            required: "Favor de ingresar la fecha inicio de publicaci\u00f3n",
        }
    });
	
	formFechaFinPublicacion = $("#idFormFechaFinPublicacion").validate();
	
	$("#fechaFinPublicacion").rules("add", {
        required: true,
        messages: {
            required: "Favor de ingresar la fecha fin de publicaci\u00f3n",
        }
    });
	
	formFechaRetiro = $("#idFormFechaRetiro").validate();
	
	$("#fechaRetiro").rules("add", {
        required: true,
        messages: {
            required: "Favor de ingresar la fecha retiro de publicaci\u00f3n",
        }
    });
}

function ajaxFormArchivos(){
	
	 	var bar = $('#barArchivoA');
	    var percent = $('#percentArchivoA');
//	    var status = $('#statusArchivoA');

	    $('#formaArchivoPrimero').ajaxForm({
	        beforeSend: function() {
	           // status.empty();
	            var percentVal = '0%';
	            bar.width(percentVal);
	            percent.html(percentVal);
	        },
	        uploadProgress: function(event, position, total, percentComplete) {
	            
	        var percentVal = percentComplete + '%';
	            bar.width(percentVal);
	            percent.html(percentVal);
	        },
	        complete: function(xhr) {
//	            status.html(xhr.responseText);
	        	$("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val(xhr.responseText);
	        	if (xhr.responseText != "" && xhr.responseText != "0") {
	        		generaDialogo("Se ha adjuntado el archivo del acuerdo para notificaci\u00f3n por estrados.");
				} else {
					$('#barArchivoA').width("0%");
	        		$('#percentArchivoA').html("0%");
	        		$("form#formaArchivoPrimero #file").val("");
	        		generaDialogo("No se pudo guardar el archivo del acuerdo para notificaci\u00f3n por estrados, int\u00e9ntalo m\u00e1s tarde.");
				}
	        	
	        }
	    });

	 	var barB = $('#barArchivoB');
	    var percentB = $('#percentArchivoB');
	    var statusB = $('#statusArchivoB');

	    $('#formaArchivoSegundo').ajaxForm({
	        beforeSend: function() {
	           // statusB.empty();
	            var percentVal = '0%';
	            barB.width(percentVal);
	            percentB.html(percentVal);
	        },
	        uploadProgress: function(event, position, total, percentComplete) {
	            
	        var percentVal = percentComplete + '%';
	            barB.width(percentVal);
	            percentB.html(percentVal);
	        },
	        complete: function(xhr) {
//	            statusB.html(xhr.responseText);
	        	$("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val(xhr.responseText);
	        	if (xhr.responseText != "" && xhr.responseText != "0") {
	        		generaDialogo("Se ha adjuntado el archivo del documento a notificar");
	        	} else {
	        		$('#barArchivoB').width("0%");
	        		$('#percentArchivoB').html("0%");
	        		$("form#formaArchivoSegundo #file").val("");
	        		generaDialogo("No se guardo el archivo del documento a notificar, int\u00e9ntalo m\u00e1s tarde.");
	        	}
	        }
	    });
	    
	 	var barVarios = $('#barArchivoVarios');
	    var percentVarios = $('#percentArchivoVarios');
	    var statusVarios = $('#statusArchivoVarios');

//	    $('#formaArchivoVarios').ajaxForm({
//	        beforeSend: function() {
//	           // statusB.empty();
//	            var percentVal = '0%';
//	            barVarios.width(percentVal);
//	            percentVarios.html(percentVal);
//	        },
//	        uploadProgress: function(event, position, total, percentComplete) {
//	            
//	        var percentVal = percentComplete + '%';
//	        	barVarios.width(percentVal);
//	        	percentVarios.html(percentVal);
//	        },
//	        complete: function(xhr) {
////	            statusB.html(xhr.responseText);
//	        	consultaArchivosOtros();
//	        }
//	    });
}


function preFinalizar(){
	
	$("form#formaArchivoPrimero #file").css("color", "black");
	$("form#formaArchivoSegundo #file").css("color", "black");
	$("#cmbCveTipoDenuncianteDT").css("color", "black");
	
	$("form#formaArchivoPrimero #file").rules("add", "archivoPrimero");
	$("form#formaArchivoSegundo #file").rules("add", "archivoSegundo");
	$("#fechaPublicacion").rules("add", {
        required: true,
        messages: {
            required: "Favor de ingresar la fecha de publicaci\u00f3n",
        }
    });
	
	var validacionNumOficio = formNumeroOficio.form();
	var validacionArchivoAdjuntoPrimero = formArchivoPrimero.form();
	var validacionNumOficioDocumento = formNumeroOficioDocumento.form();
	var validacionTipoDocumento = formTipoDocumento.form();
	var validacionArchivoAdjuntoSegundo = formArchivoSegundo.form();
	var validacionFechaPublicacion = formFechaPublicacion.form();
	var validacionFechaPublicacionInfo = formFechaPublicacionInfo.form();
	var validacionFechaInicioPublicacion = formFechaInicioPublicacion.form();
	var validacionFechaFinPublicacion = formFechaFinPublicacion.form();
	var validacionFechaRetiro = formFechaRetiro.form();

	if(!validacionNumOficio || !validacionArchivoAdjuntoPrimero || 
			!validacionTipoDocumento || !validacionNumOficioDocumento || 
			!validacionArchivoAdjuntoSegundo || !validacionFechaPublicacion || 
			!validacionFechaPublicacionInfo || !validacionFechaInicioPublicacion ||
			!validacionFechaFinPublicacion || !validacionFechaRetiro) {
		$("form#formaArchivoPrimero #file").rules("remove", "archivoPrimero");
		$("form#formaArchivoSegundo #file").rules("remove", "archivoSegundo");
		$("#fechaPublicacion").rules("remove", "required");
		return false;
	}
	
	guardadoParcialNotificacion();
	$("#etiquetaTitulo").text("Resumen de la solicitud de publicaci\u00f3n por estrados");	
	
	$("#txtDomicilioFiscalResumen").val(notificacionVO.desDomicilio);
	$("#txtNombreDenominacionResumen").val(notificacionVO.razonSocial);
}

function guardadoParcialNotificacion(autorizar){
	
	if(formaPrincipal.form()){
		if(!flagPatronValido){
			generaDialogo("Favor de validar el registro patronal nuevamente");
			return;
		}
		
//		$("#etiquetaTitulo").text("Datos del Documento a notificar");
		var valores=$("#cmbAreaResposableNotificacion").val().split("-");
		notificacionVO.areanormativaDTO.cveAreanorma=valores[0];
		notificacionVO.areaRespNotifDTO.procesoDTO.cveProceso=valores[1];
		notificacionVO.areaRespNotifDTO.cveAreaRespNotif=valores[2];

		if($('input:radio[name=rdSujetoNotificar]:checked').val()!=SUJETO_NOTIFICAR_OTRO){
			notificacionVO.sujetoANotificarDTO.cveSujetoANotificar=$('input:radio[name=rdSujetoNotificar]:checked').val();
		}else{
			notificacionVO.sujetoANotificarDTO.cveSujetoANotificar=$('input:radio[name=rdSujetoNotificarOtro]:checked').val();
		}
		
		if($("#txtDomicilioPatron").val()!=""){
			notificacionVO.desDomicilio=$("#txtDomicilioPatron").val();
		}else if($("#txtDomicilioSujObl").val()!=""){
			notificacionVO.desDomicilio=$("#txtDomicilioSujObl").val();
		}else if($("#txtDomicilioFiscal").val()!=""){
			notificacionVO.desDomicilio=$("#txtDomicilioFiscal").val();
		}else if($("#txtDomicilioSujNot").val()!=""){
			notificacionVO.desDomicilio=$("#txtDomicilioSujNot").val();
		}
		
		//Razon social
		
		if($("#txtNomDenRazonSocialPatron").val()!=""){
			notificacionVO.razonSocial=$('#txtNomDenRazonSocialPatron').val();
		}else if($("#txtNomDenRazonSocialSujObl").val()!=""){
			notificacionVO.razonSocial=$('#txtNomDenRazonSocialSujObl').val();
		}else if($("#txtNombreContador").val()!=""){
			notificacionVO.razonSocial=$('#txtNombreContador').val();
		}else if($("#txtNomDenRazonSocialOtro").val()!=""){
			notificacionVO.razonSocial=$('#txtNomDenRazonSocialOtro').val();
		}
		
		notificacionVO.registroPatronal=$('#txtRegistroPatronal').val();
		notificacionVO.desNumRegCpa=$('#txtRegistroIMSS').val();
		if($('#cmbCveTipoDenuncianteDT').val()!=-1){
			notificacionVO.tipodocumentoDTO.cveTipodocto=$('#cmbCveTipoDenuncianteDT').val();			
		}

//		if(notificacionVO.cveNotificaciones!=0){
//			cambiarPagina(1);
//			return;
//		}
		if(autorizar!=undefined){
			if($("#fechaPublicacion").val()==""){
				generaDialogo("Favor de ingresar la fecha de publicaci\u00f3n");
				return;
			}else if($("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val()==""){
				generaDialogo("Favor de adjuntar archivo para el acuerdo de notificaci\u00f3n");
				return;				
			}else if($("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val()==""){
				generaDialogo("Favor de adjuntar archivo para el acuerdo de notificaci\u00f3n");
				return;				
			}				
			notificacionVO.autorizarNotificacion=true;
			
			promtDialogo("Una vez que la solicitud ha sido registrada \u00e9sta no podr\u00e1 ser modificada, \u00bfDesea continuar con el registro de notificaci\u00f3n? ", function(){
				$('#dialogoMensaje').dialog("close");
				$.blockUI();
				$.postJSON_Sync(getAppContextParaJS()+"/estrados/guardoParcialNotificacion.do", notificacionVO,function(data) {
					notificacionVO=data;
					if (notificacionVO.autorizacionFinalizada) {
						generaDialogo("Su solicitud ha sido registrada exitosamente, favor de imprimir acuse", true);						
						visualizaAcuse(notificacionVO.desRefAcuse);
						$("#btnRegresar").prop('disabled','disabled');
						$("#btnRegistrar").prop('disabled','disabled');
						$.unblockUI();
						return;
					} else {
						generaDialogo("Su solicitud no ha sido registrada. No se encontraron documentos adjuntos relacionados a la notificaci\u00f3n?", true);
						return;
					}
				}).error(function(data){
					
				}).complete(function(data){
					$.unblockUI();
					return;
				});
			});
		
		}else{
			$.blockUI();
			$.postJSON_Sync(getAppContextParaJS()+"/estrados/guardoParcialNotificacion.do", notificacionVO,function(data) {
				notificacionVO = data;
				if (notificacionVO.cveNotificaciones != 0) {
					cambiarPagina(1);
				} else {
					generaDialogo("El servicio no se encuentra disponible por el momento, int\u00e9ntalo m\u00e1s tarde.", true);
				}
			}).error(function(data){
				generaDialogo("El servicio no se encuentra disponible por el momento, int\u00e9ntalo m\u00e1s tarde.", true);
			}).complete(function(data){
				$.unblockUI();
			});
		}
	}
	$("#cmbAreaResposableNotificacion").css("color", "black");
}

function salirAplicacion(){
	$("#redireccionListado").submit();
}

function archivoAcuerdoNotificacion(forma,funcion,tipo,campo){
	var estado=true;
	if(funcion!=undefined){
		estado=eval(funcion);
	}
	if(estado){
		
		$('#dialogoMensaje').dialog({
//	        title: "Subida de Archivos",
	        autoOpen: false,
	        width : 400,
	        height : 180,
	        modal: true,
	        resizable: false,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        },
	        buttons: [{ 
	        				text: "Subir", 
	        				click:function() {
	        					if (notificacionVO.cveNotificaciones != 0) {
	        						$("form#"+forma+" #cveNotificaAcuerdoFile").val(notificacionVO.cveNotificaciones);
		        					$("form#"+forma+" #desNumOficioFile").val($("#"+campo).val());
		        					$("form#"+forma+" #tipoDocumentoFile").val($("#cmbCveTipoDenuncianteDT").val());
		        					$("form#"+forma+" #tipoDocumentoAdjunto").val(tipo);
		        					$(this).dialog("destroy");
		        					$("#"+forma+"").submit();	        				
		        					$("#"+campo).prop('disabled','disabled');
								} else {
									generaDialogo("No se pudieron obtener algunos datos del servidor, intenta realizar un nuevo registro.", true);
									$("form#"+forma+" #file").val("");
		        					borraFile(forma);
								}
	        				}
	        			},{ 
		        			text: "Cancelar", 
		        			click:function(){
		        					$("form#"+forma+" #file").val("");
		        					borraFile(forma);
		        					$(this).dialog("destroy");
		        					
		        				}
	        			}]
	   		});
		$('#dialogoMensaje').html("\u00bfEsta seguro que desea subir el archivo?");
		$('#dialogoMensaje').dialog("open");			
	}
}

function validaArchivoAcuerdo(){
	
	if (!formNumeroOficio.form()) {
		borraFile("formaArchivoPrimero");
		return false;
	}
	
	if (!formArchivoPrimero.form()) {
		borraFile("formaArchivoPrimero");
		return false;
	}
	
	if($("form#formaArchivoPrimero #file").val()!="" && $("form#formaArchivoPrimero #file").val()==$("form#formaArchivoSegundo #file").val()){
		generaDialogo("Verificar que no se suba el mismo archivo");
		borraFile("formaArchivoPrimero");
		return false;
	}	
	return true;
}

function validaNombreAcuerdo(seccionA,cantidadNumericos,nombreArchivo){
	
//	var arreglo=nombreArchivo.split("\\");
//	
//	nombreArchivo=arreglo[arreglo.length-1];
//	  var totalCaracteres=seccionA.length+cantidadNumericos+4;
//	  if(nombreArchivo.length!=totalCaracteres){
//	    return false;
//	  }
//	  	  var regexp=/^[0-9]{4}\.(pdf|PDF)$/;
//	  var validaPrimera=nombreArchivo.substring(0,seccionA.length);
//	  var validaSegunda=nombreArchivo.substring(seccionA.length);
//		
//	  if(seccionA!=validaPrimera){	    
//	    return false;
//	  }
//	  if(!regexp.test(validaSegunda)){	  
//	    return false;
//	  }	  
	return true;
}


function validaNombreDocumento(seccionA,cantidadNumericos,nombreArchivo){
//	var arreglo=nombreArchivo.split("\\");
//	
//	nombreArchivo=arreglo[arreglo.length-1];
//	  var totalCaracteres=seccionA.length+cantidadNumericos+4;
//	  if(nombreArchivo.length!=totalCaracteres){
//	    return false;
//	  }
//	  	  var regexp=/^[0-9]{9}\.(pdf|PDF)$/;
//	  var validaPrimera=nombreArchivo.substring(0,seccionA.length);
//	  var validaSegunda=nombreArchivo.substring(seccionA.length);
//		
//	  if(seccionA!=validaPrimera){	    
//	    return false;
//	  }
//	  if(!regexp.test(validaSegunda)){	  
//	    return false;
//	  }	  
	return true;
}

function validaArchivoDocumentoNotificar(){
	
	if (!formNumeroOficioDocumento.form()) {
		borraFile("formaArchivoSegundo");
		return false;
	}
	
	if (!formArchivoSegundo.form()) {
		borraFile("formaArchivoSegundo");
		return false;
	}
	
	if (!formTipoDocumento.form()) {
		borraFile("formaArchivoSegundo");
		return false;
	}
	
	if($("form#formaArchivoSegundo #file").val()!="" && $("form#formaArchivoPrimero #file").val()==$("form#formaArchivoSegundo #file").val()){
		generaDialogo("Verificar que no se suba el mismo archivo");
		borraFile("formaArchivoSegundo");
		return false;
	}
	return true;
}


function generaDialogo(text,flag){
	
	var dialogo=$('#dialogoMensaje').dialog({
//        title: "Informativo",
        autoOpen: false,
        width : 400,
        height : 180,
        modal: true,
        resizable: false,
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons: [{
        		text:"Aceptar",
        		click:function(){
        			$(this).dialog("destroy");
        			if(flag){
        				salirAplicacion();
        			}
        		}
        }]
	});
	dialogo.html(text);
	dialogo.dialog("open");
}

function initPantalla() {
	
	//Se cargan los dias inhabiles del año para que no puedan ser seleccionados en los datepicker
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultaDiasInhabiles.do", null, function(data) {
		listDiasInhabilDTOs = data;
	}).error(function(data) {
		
	}).complete(function(data) {
		$.unblockUI();
	});
	
	$("#fechaPublicacion").datepicker({
		dateFormat: 'dd-mm-yy',
		buttonImage: getAppContextParaJS()+"/resources/imagenes/calendarIcon.gif",
		showOn: "button",
		beforeShowDay: inhabiles,
		buttonImageOnly: true,
		onSelect: function(dateText, inst) { 
			if($("#fechaPublicacion").val()!=""){
				calculaFechasPublicacion();
			}
			
	  }
	});
	
	$("#fechaPublicacion").datepicker('option', 'minDate', recuperaFechaServidor().split(",")[0]);
	$("#fechaPublicacion").datepicker('option', 'maxDate', recuperaFechaServidor().split(",")[1]);
	
	$.validator.addMethod("comboRequerido", function(value, elem, param) {
		if (value!=-1) {
			return true;
		} else {
			return false;
		}
	       return false;
	},"Este campo es obligatorio");
	
	$.validator.addMethod("comboTipoDocumentoRequerido", function(value, elem, param) {
		if (value!=-1) {
			return true;
		} else {
			return false;
		}
	},"Seleccione el tipo de documento");
	
	$.validator.addMethod("archivoPrimero", function(value, elem, param) {
		if($("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val() != "" && $("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val() != "0") {
			return true;
		} else {
			return false;
		}
	},"Favor de adjuntar acuerdo de notificaci\u00f3n");
	
	$.validator.addMethod("archivoSegundo", function(value, elem, param) {
		if($("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val() != "" && $("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val() != "0") {
			return true;
		} else {
			return false;
		}
	},"Favor de adjuntar documento a notificar");
	
	jQuery.validator.addMethod('regexp', function(value,
			element, param) {
		return this.optional(element) || value.match(param);
	}, 'El valor no coincide con la estructura requerida.');
	
	jQuery.validator.addMethod('archivoPDF', function(value,
			element, param) {
		return this.optional(element) || value.match(param);
	}, 'Solo se permite adjuntar archivos con formato PDF.');
	
	$(":text").each(function(){
		  $(this).on('blur',function(){
		      $(this).val(($(this).val()).toUpperCase()); 
		  });							
		});
	
	$('textarea').each(function(){
		  $(this).on('blur',function(){
		      $(this).val(($(this).val()).toUpperCase()); 
		  });							
		});
	
//	$('#trOtrosLabel').hide();
//	$('#trOtros').hide();
//	$('#trOtrosResumen').hide();
	
	$("#numeroOficio").on('blur',function(){
		$("#numeroOficio").val(($("#numeroOficio").val()).toUpperCase()); 
	    validaNumeroOficio( $("#numeroOficio").val(),1);
	});
	
	$("#numeroOficionDocumentoNotificar").on('blur',function(){
		$("#numeroOficionDocumentoNotificar").val(($("#numeroOficionDocumentoNotificar").val()).toUpperCase()); 
		 validaNumeroOficio($("#numeroOficionDocumentoNotificar").val(),2);
	});
}

function limpiarForma(){
	$('textarea,select').each(function(){
			$(this).val("");
		});
	
	$(":text").each(function(){
			$(this).val("");
		});
	
	$('#barArchivoA').width("0%");
	$('#percentArchivoA').html("0%");
	$('#barArchivoB').width("0%");
	$('#percentArchivoB').html("0%");	
	$("form#formaArchivoPrimero #file").val("");
	$("form#formaArchivoSegundo #file").val("");	
	
	borraFile("formaArchivoPrimero");
	borraFile("formaArchivoSegundo");
}

function limpiarDatosSujeto(){
	$("#cmbAreaResposableNotificacion").val(-1);
	$("#txtRegistroPatronal").val("");
	$("#txtDomicilioPatron").val("");	
	
	$("#txtNomDenRazonSocialPatron").val("");
	$("#txtNomDenRazonSocialSujObl").val("");
	$("#txtNombreContador").val("");
	$("#txtNomDenRazonSocialOtro").val("");
	$("#txtDomicilioSujObl").val("");	
	$("#txtRegistroIMSS").val("");
		
	$("#txtDomicilioFiscal").val("");
	$("#txtDomicilioSujNot").val("");
	formaPrincipal.resetForm();
	mostrarDivSujetoPatron();
}

function limparDatosArchivo(){
	
	$('#barArchivoA').width("0%");
	$('#percentArchivoA').html("0%");
	$('#barArchivoB').width("0%");
	$('#percentArchivoB').html("0%");	
	$("form#formaArchivoPrimero #file").val("");
	$("form#formaArchivoSegundo #file").val("");	
	
	borraFile("formaArchivoPrimero");
	borraFile("formaArchivoSegundo");
	$("#fechaPublicacion").val("");
	
	$("#fechaPublicacionInfo").val("");
	$("#fechaInicioPublicacion").val("");
	$("#fechaFinPublicacion").val("");
	$("#fechaRetiro").val("");
	$("#cmbCveTipoDenuncianteDT").val(-1);
	
	$("#numeroOficio").val("");
	$("#numeroOficionDocumentoNotificar").val("");
	
	$("#numeroOficio").removeAttr('disabled');
	$("#numeroOficionDocumentoNotificar").removeAttr('disabled');
	
	$("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val("");
	$("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val("");

	formNumeroOficio.resetForm();
	formArchivoPrimero.resetForm();
	formTipoDocumento.resetForm();
	formNumeroOficioDocumento.resetForm();
	formArchivoSegundo.resetForm();
	formFechaPublicacion.resetForm();
	formFechaPublicacionInfo.resetForm();
	formFechaInicioPublicacion.resetForm();
	formFechaFinPublicacion.resetForm();
	formFechaRetiro.resetForm();
}

function pasaValor(){

	$("#txtAreaResponsable").val($("#cmbAreaResposableNotificacion option:selected").text().toUpperCase());
	$("#txtNombreDenominacionResumen").val($("#txtNombreDenominacion").val());
	$("#txtNumeroOficioAcuerdo").val($("#numeroOficio").val());
	if($("#cmbCveTipoDenuncianteDT option:selected").val()!=-1)
	$("#txtTipoDocumento").val($("#cmbCveTipoDenuncianteDT option:selected").text().toUpperCase());
	$("#txtOficioDocumentoNotificar").val($("#numeroOficionDocumentoNotificar").val())
	if($("#txtRegistroIMSS").val()!=""){
		$("#txtRPRimss").val($("#txtRegistroIMSS").val());
	}else if($("#txtRegistroPatronal").val()!=""){
		$("#txtRPRimss").val($("#txtRegistroPatronal").val());
	}
	
//	$("#txtDomicilioFiscalResumen").val($("#domicilioValor").val());
	$("#txtFechaPublicacionResumen").val($("#fechaPublicacion").val());
	$("#txtFechaInicioPublicacionResumen").val($("#fechaInicioPublicacion").val());
	$("#txtFechaFinPublicacionResumen").val($("#fechaFinPublicacion").val());
	$("#txtFechaRetiroResumen").val($("#fechaRetiro").val());
}

function setDomicilio(componente){
	$("#domicilioValor").val(componente.value);
}

//function consultaArchivosOtros(){
//	
//	if(notificacionVO.cveNotificaciones!=0){
//		$.blockUI();
//		$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaDocumentosOtros.do", notificacionVO,function(data) {
//			var options = "";		
//			for(var s=0;s<data.length;s++){
//				options += "<option value='"+ data[s].cveDoctoAdjunto+"'>"+ data[s].desNombreArchivo+"</option>";				
//			}
//			$("#listaArchivosOtros").html(options);
//			$("#listaArchivosOtrosResumen").html(options);	
//		}).error(function(data){
//			
//		}).complete(function(data){
//			$.unblockUI();
//		});	
//	}
//}

//function eliminarArchivoOtros(){
//	
//	var idNotificacion;
//	if($("#listaArchivosOtros").val()==null || $("#listaArchivosOtros").val()=="" || $("#listaArchivosOtros").val()==undefined){
//		return;
//	}else{
//		idNotificacion=$("#listaArchivosOtros").val();
//	}
//	
//	var dialogo=$('#dialogoMensaje').dialog({
////        title: "Informativo",
//        autoOpen: false,
//        width : 400,
//        height : 180,
//        modal: true,
//        resizable: false,
//        overlay: {
//            opacity: 0.5,
//            background: "black"
//        },
//        buttons: [{
//        		text:"Aceptar",
//        		click:function(){
//        			var sVarSeg='{"cveDoctoAdjunto":"'+idNotificacion+'","notificacionesDTO":{"cveNotificaciones":"'+notificacionVO.cveNotificaciones+'"}}';
//        			var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
//        			$.blockUI();
//        			$.postJSON_Sync(getAppContextParaJS()+"/estrados/eliminarAdjuntoOtro.do", parametroIdNotificacion,function(data) {
//
//        			}).error(function(data){
//        				
//        			}).complete(function(data){
//        				$.unblockUI();
//        				generaDialogo("Archivo Eliminado Exitosamente");
//        				consultaArchivosOtros();
//        			});	
//        			
//        			
//        		}
//        },{
//    		text:"Cancelar",
//    		click:function(){
//    			$(this).dialog("destroy");
//    		}
//    }]
//	});
//	dialogo.html("\u00bfEsta seguro de eliminar el archivo?");
//	dialogo.dialog("open");
//}


function eliminarArchivoAdjunto(forma,idcveDocumentoAdjunto,campo,tipoAdjunto){
	var cveDocumentoAdjunto=$("form#"+forma+" #"+idcveDocumentoAdjunto+"").val();
	
	if (cveDocumentoAdjunto != null && cveDocumentoAdjunto != "") {
		var dialogo=$('#dialogoMensaje').dialog({
	//        title: "Informativo",
	        autoOpen: false,
	        width : 400,
	        height : 180,
	        modal: true,
	        resizable: false,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        },
	        buttons: [{
	        		text:"Aceptar",
	        		click:function(){
	        			var sVarSeg='{"cveDoctoAdjunto":"'+cveDocumentoAdjunto+'","notificacionesDTO":{"cveNotificaciones":"'+notificacionVO.cveNotificaciones+'"}}';
	        			var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
	        			$.blockUI();
	        			$.postJSON_Sync(getAppContextParaJS()+"/estrados/eliminarAdjuntoOtro.do", parametroIdNotificacion,function(data) {
	
	        			}).error(function(data){
	        				
	        			}).complete(function(data){
	        				$.unblockUI();
	        				generaDialogo("Archivo Eliminado Exitosamente");
	        				$("form#"+forma+" #file").val("");
	        				borraFile(forma);
	        				$("#"+campo).val("");
	        				$("#"+campo).removeAttr('disabled');
	        				if(tipoAdjunto=='A'){
	        					$("form#formaArchivoPrimero #idClaveDocumentoAdjunto").val("");
	        					$('#barArchivoA').width("0%");
	        					$('#percentArchivoA').html("0%");
	        				}else if(tipoAdjunto=='B'){
	        					$('#barArchivoB').width("0%");
	        					$("form#formaArchivoSegundo #idClaveDocumentoAdjunto").val("");
	        					$('#percentArchivoB').html("0%"); 
	        					$("#cmbCveTipoDenuncianteDT").val(-1);
	        				}        				
	        			});
	        		}
	        },{
	    		text:"Cancelar",
	    		click:function(){
	    			$(this).dialog("destroy");
	    		}
	    }]
		});
		dialogo.html("\u00bfEsta seguro de eliminar el archivo?");
		dialogo.dialog("open");
	} else {
		generaDialogo("No existe ning\u00fan archivo a eliminar");
	}
}

function recuperaFechaServidor(){
	var fecha="";
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaFechaServidor.do", null,function(data) {
    
	}).error(function(data){
		
	}).complete(function(data){
		$.unblockUI();
		fecha=data.responseText;
	});	
	return fecha;
}

function calculaFechasPublicacion(){
	
	var sVarSeg='{"fechaPublicacion":"'+$("#fechaPublicacion").val()+'"}';
	var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/calculaFechasPublicacion.do", parametroIdNotificacion,function(data) {
	    
		$("#fechaPublicacionInfo").val(data.fechaPublicacion);
	    $("#fechaInicioPublicacion").val(data.fechaInicioPublicacion);
	    $("#fechaFinPublicacion").val(data.fechaFinPublicacion);
	    $("#fechaRetiro").val(data.fechaRetiroPublicacion);
	    
	    notificacionVO.fecFinPublicacionCadena=data.fechaFinPublicacion;
	    notificacionVO.fecInicioPublicacionCadena=data.fechaInicioPublicacion;
	    notificacionVO.fecPublicacionCadena=data.fechaPublicacion;	
	    notificacionVO.fecRetiroPublicacionCadena=data.fechaRetiroPublicacion;
	    
	    formFechaPublicacionInfo.form();
		formFechaInicioPublicacion.form();
		formFechaFinPublicacion.form();
		formFechaRetiro.form();
	}).error(function(data){
		
	}).complete(function(data){
		$.unblockUI();
	});	
}


function visualizaDocumento(idForma){
	var idCveAdjunto=$("form#"+idForma+" #idClaveDocumentoAdjunto").val();
	if(idCveAdjunto!=""){
		visorArchivoPDF(idCveAdjunto, notificacionVO.cveNotificaciones);	
	}
}

//function visualizaAdjuntoOtros(){	
//	visorArchivoPDF($("#listaArchivosOtros").val(), notificacionVO.cveNotificaciones);	
//}

//function visualizaAdjuntoOtrosResumen(){	
//	visorArchivoPDF($("#listaArchivosOtrosResumen").val(), notificacionVO.cveNotificaciones);	
//}

function recuperaNombre(id,tipo){
	var sVarSeg='{"desNumOficio":"'+$("#"+id).val()+'","desNombreArchivo":"'+$("#cmbAreaResposableNotificacion").val()+'","tipoAdjuntoDTO":{"cveTipoAdjunto":"'+tipo+'"}}';
	var parametroIdNotificacion = jQuery.parseJSON(sVarSeg);
    var nombre;
    $.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaNombre.do", parametroIdNotificacion,function(data) {
	    
	}).error(function(data){
		
	}).complete(function(data){
		$.unblockUI();
    	nombre=data.responseText;
	});
	return nombre;
}

function promtDialogo(texto,callbackOk){
	
	$('#dialogoMensaje').dialog({
//        title: "Registro Notificacion",
        autoOpen: false,
        width : 400,
        height : 180,
        modal: true,
        resizable: false,
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons: [{ 
        			text: "SI", 
        			click:callbackOk
        		},{ 
	        		text: "NO", 
	        		click:function(){
	        				$(this).dialog("destroy");
	        		}
        			}]
   		});
	$('#dialogoMensaje').html(texto);
	$('#dialogoMensaje').dialog("open");
}

$(document).ready(function() {
	recuperaDatosHeaderRegistro();
	initPantalla();
	obtenerInstanciaNotificacion(0);
	$("#contenedorSecundariolReg").hide();
	$("#contenedorResumenReg").hide();	
	determinarSujetoANotificar();
	generaValidadorPrincipal();
	generaValidadorSecundario();
	ajaxFormArchivos();	
	generarToolTips();
	$("#txtDomicilioPatron").val("");
	generaValidadorTextArea();
});

function generarToolTips() {
	var infoToolTipAreaResponsableNotificacion = '<p>Seleccione el &aacute;rea responsable de la notificaci&oacute;n</p>';
	
	var infoToolTipSujetoNotificar = '<p>Seleccione el sujeto a notificar</p>';
	
	var infoToolTipRegistroPatronal = '<p>Ingrese el n&uacute;mero de Registro Patronal indicado en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar, en 11 caracteres, s&oacute;lo n&uacute;meros y letras y sin espacios</p>';
	var infoToolTipNomDenRazonSocialPatron = '<p>Ingrese el nombre, denominaci&oacute;n o raz&oacute;n social del Patr&oacute;n como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	var infoToolTipDomicilioPatron = '<p>Ingrese el domicilio del Patr&oacute;n, como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	
	var infoToolTipNomDenRazonSocialSujObl = '<p>Ingrese el nombre, denominaci&oacute;n o raz&oacute;n social del sujeto obligado, como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	var infoToolTipDomicilioSujObl = '<p>Ingrese el domicilio del sujeto obligado, como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	
	var infoToolTipNumRegistroImss = '<p>Ingrese el n&uacute;mero de Registro IMSS asignado al Contador P&uacute;blico Autorizado, como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	var infoToolTipNombreContador = '<p>Ingrese el  nombre del Contador P&uacute;blico Autorizado, como se indica en el  Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	var infoToolTipDomicilioFiscal = '<p>Ingrese el domicilio fiscal del Contador P&uacute;blico Autorizado, como se indique en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	
	var infoToolTipNomDenRazonSocialOtro = '<p>Ingrese el nombre, denominaci&oacute;n o raz&oacute;n social del sujeto a notificar, como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	var infoToolTipDomicilioSujNot = '<p>Ingrese el domicilio del sujeto a notificar,  como se indica en el Acuerdo de notificaci&oacute;n por estrados y el documento a notificar. </p>';
	
	var infoToolTipNumOficio = '<p align=justify>Ingrese el n&uacute;mero de oficio del acuerdo de notificaci&oacute;n por estrados. Debe ser de 5 a 50 caracteres, incluyendo n&uacute;meros, letras de la A..Z, diagonal (/), gui&oacute;n bajo (_), gui&oacute;n medio (-) y Punto (.)</p>';
		
	var infoToolTipAdjuntarAcuse = '<p>Adjunte el archivo PDF que contiene el Acuerdo de notificaci&oacute;n por estrados. </p>';
	var infoToolTipNumOficioDocumento = '<p align=justify>Ingrese el n&uacute;mero de oficio o documento del acto a notificar. Debe ser de 9 a 50 caracteres, incluyendo números, letras de la A..Z, diagonal (/), guión bajo (_), guión medio (-) y Punto (.) ej. n&uacute;mero de oficio, documento, cr&eacute;dito o folio a notificar, a nueve d&iacute;gitos sin espacios y sin caracteres, utilizando ceros a la izquierda para completar los nueve d&iacute;gitos. </p>';
	var infoToolTipTipoDocumento = '<p>Seleccione el tipo de documento a notificar. </p>';
	var infoToolTipAdjuntarDocumento = '<p>Adjunte el archivo PDF que contiene el oficio o documento a notificar. </p>';
	var infoToolTipFechaPublicacion = '<p>Seleccione el d&iacute;a h&aacute;bil en que deber&aacute; publicarse el documento en la p&aacute;gina del Instituto, que deber&aacute; ser el mismo en que se fije el documento en las oficinas de la Subdelegaci&oacute;n o autoridad que notifica. Esta fecha debe ser la misma que la fecha de constancia de fijaci&oacute;n y colocaci&oacute;n en estrados. </p>';
	var infoToolTipFechaInicioPublicacion = '<p>D&iacute;a 1 h&aacute;bil de los 6 en que deber&aacute; estar publicado el documento en la p&aacute;gina del Instituto y fijado en las oficinas de la Subdelegaci&oacute;n o autoridad que notifica, que es el d&iacute;a h&aacute;bil siguiente a la fecha de publicaci&oacute;n (art&iacute;culo 139 del CFF). </p>';
	var infoToolTipFechaFinPublicacion = '<p>D&iacute;a 10 h&aacute;bil contado a partir del d&iacute;a h&aacute;bil siguiente al que fue publicado en la p&aacute;gina del Instituto y fijado en las oficinas de la Subdelegaci&oacute;n o autoridad que notifica (art&iacute;culo 139 del CFF). </p>';
	var infoToolTipRetiroFinPublicacion = '<p>D&iacute;a h&aacute;bil siguiente a la fecha de fin de publicaci&oacute;n. Esta fecha debe ser la misma que la fecha de constancia de retiro de estrados. </p>';
	
	$('#idToolTipAreaResponsableNotificacion').popover({
		animation : true,
		html: true,
		content : infoToolTipAreaResponsableNotificacion,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipSujetoNotificar').popover({
		animation : true,
		html: true,
		content : infoToolTipSujetoNotificar,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipRegistroPatronal').popover({
		animation : true,
		html: true,
		content : infoToolTipRegistroPatronal,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNomDenRazonSocialPatron').popover({
		animation : true,
		html: true,
		content : infoToolTipNomDenRazonSocialPatron,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNomDenRazonSocialSujObl').popover({
		animation : true,
		html: true,
		content : infoToolTipNomDenRazonSocialSujObl,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNombreContador').popover({
		animation : true,
		html: true,
		content : infoToolTipNombreContador,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNomDenRazonSocialOtro').popover({
		animation : true,
		html: true,
		content : infoToolTipNomDenRazonSocialOtro,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipDomicilioPatron').popover({
		animation : true,
		html: true,
		content : infoToolTipDomicilioPatron,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNumRegistroImss').popover({
		animation : true,
		html: true,
		content : infoToolTipNumRegistroImss,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipDomicilioSujObl').popover({
		animation : true,
		html: true,
		content : infoToolTipDomicilioSujObl,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipDomicilioFiscal').popover({
		animation : true,
		html: true,
		content : infoToolTipDomicilioFiscal,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipDomicilioSujNot').popover({
		animation : true,
		html: true,
		content : infoToolTipDomicilioSujNot,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNumOficio').popover({
		animation : true,
		html: true,
		content : infoToolTipNumOficio,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipAdjuntarAcuse').popover({
		animation : true,
		html: true,
		content : infoToolTipAdjuntarAcuse,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipNumOficioDocumento').popover({
		animation : true,
		html: true,
		content : infoToolTipNumOficioDocumento,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipTipoDocumento').popover({
		animation : true,
		html: true,
		content : infoToolTipTipoDocumento,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipAdjuntarDocumento').popover({
		animation : true,
		html: true,
		content : infoToolTipAdjuntarDocumento,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipFechaPublicacion').popover({
		animation : true,
		html: true,
		content : infoToolTipFechaPublicacion,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipFechaInicioPublicacion').popover({
		animation : true,
		html: true,
		content : infoToolTipFechaInicioPublicacion,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipFechaFinPublicacion').popover({
		animation : true,
		html: true,
		content : infoToolTipFechaFinPublicacion,
		trigger: 'hover',
		placement: 'bottom'
	});
	$('#idToolTipFechaRetiroPublicacion').popover({
		animation : true,
		html: true,
		content : infoToolTipRetiroFinPublicacion,
		trigger: 'hover',
		placement: 'bottom'
	});
}

function invalidaPatron(){
	flagPatronValido=false;
}

function limpiaDomicilios(){
	$("#txtDomicilioPatron").val("");
	$("#txtDomicilioSujObl").val("");
	$("#txtDomicilioFiscal").val("");
	$("#txtDomicilioSujNot").val("");
}

function validaNumeroOficio(valor,tipo){
	if(valor==""){
		return;
	}
//	var sVarSeg='{"desNumOficio":"'+valor+'", "dtSsoVwUsuarioDTO":{"cveSubdelegacion":"'+ssoVwUsuarioDTO.cveSubdelegacion+'"}}';
	var sVarSeg='{"desNumOficio":"'+valor+'"}';
	var parametro = jQuery.parseJSON(sVarSeg);
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/validarNumeroAcuerdo.do", parametro,function(data) {
			if(data.cveDoctoAdjunto!=-1){
				if(tipo==1){
					generaDialogo("El registro de notificaci\u00f3n con n\u00famero de oficio del acuerdo '"+valor+"' ya se encuentra registrado en la base de datos, favor de verificar");	
					$("#numeroOficio").val("");
				}else if(tipo==2){
					generaDialogo("El registro de notificaci\u00f3n con n\u00famero de oficio o documento '"+valor+"' ya se encuentra registrado en la base de datos, favor de verificar");
					$("#numeroOficionDocumentoNotificar").val("");
				}				
			}
		
		}).error(function(data){
			
		}).complete(function(data){

		});
}

function borraFile(forma){
	var control = $("form#"+forma+" #file");
	control.replaceWith( control = control.clone( true ) );
	$("form#"+forma+" #file").val("");
}

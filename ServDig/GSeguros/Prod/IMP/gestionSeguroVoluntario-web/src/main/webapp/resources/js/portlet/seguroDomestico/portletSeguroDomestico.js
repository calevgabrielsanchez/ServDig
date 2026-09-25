$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/alta/wizardControl.js');
$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/renovacion/wizardControl.js');
$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/ventanilla/wizardControl.js');
$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js");

var ivroPortlet = {
	mostrarAltaSeguroDomesticoWizard : function() {
		
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		
		var url = '/${mvn.web.app.root}/wizard/seguroDomestico/validarAccesoTramite/' + idPersona + '/' + rfcPersona;
		
		$.getJSON(url, mostrarAltaSeguroDomesticoWizard);
		 
	},
	
	mostrarRenovacionFielSeguroDomesticoWizard : function() {
		
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		
		var url = '/${mvn.web.app.root}/wizard/seguroDomestico/validarAccesoFielTramiteRenovacion/' + idPersona + '/' + rfcPersona;
		$.ajax({
			  dataType: "json",
			  url: url,
			  async: false,
			  success: function(result){
				  iniciarValidacionAccesoTramiteDomesticoRenovacion(result);
				  ivroPortlet.mostrarRenovacionSeguroDomesticoWizard(result.idSeguroRenovacion);	
		      }
		});
		
	},
	
	
	mostrarRenovacionSeguroDomesticoWizard : function(idSeguro) {
		var idPersona = null;
		var rfc = null;
		if(typeof ventanilla === 'undefined' || !ventanilla) {
			idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			rfc = AtributosPersonaCtrl.personaPortal.rfc;
		}else{
			//Ventanilla y Ciudadano			
			var identidadPersona = null;
			if((typeof _identidadCtrl) !== "undefined"){
				identidadPersona 		 = _identidadCtrl.identidad('option', 'personaUbicada');
				idPersona = identidadPersona.idPersona;
				rfc = identidadPersona.rfc != null ? identidadPersona.rfc : 'SIN_RFC';
			}else{				
				if((typeof parent._identidadCtrl) !== "undefined"){
					identidadPersona 		 = parent._identidadCtrl.identidad('option', 'personaUbicada');
					idPersona = identidadPersona.idPersona;
					rfc = identidadPersona.rfc != null ? identidadPersona.rfc : 'SIN_RFC';
				}else{
					//Ciudadano
					idPersona = parent.$("#cveIdPersona").val();
					rfc = parent.$("#rfc").val() != null ? parent.$("#rfc").val() : 'SIN_RFC';
				}
				
			}
			WizardIVRORenovacionSeguroDomesticoCtrl = parent.WizardIVRORenovacionSeguroDomesticoCtrl;
		}
		/*console.debug("idPersona: " + idPersona);
		console.debug("rfc: " + rfc);
		console.debug("idSeguro: " + idSeguro);*/
		
		WizardIVRORenovacionSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', idPersona, rfc, idSeguro);
		WizardIVRORenovacionSeguroDomesticoCtrl.abrir();
				
	},
	
	mostrarAltaSeguroDomesticoVentanillaWizard: function(idPersona, rfcPersona, correoDomestico) {
		var url = '/${mvn.web.app.root}/wizard/seguroDomestico/validarAccesoTramite/' + idPersona + '/' + correoDomestico + '/' + rfcPersona;
		$.getJSON(url, mostrarAltaSeguroDomesticoWizard);
	},
	mostrarVentanillaSeguroDomesticoWizard: function() {
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var rfc = AtributosPersonaCtrl.personaPortal.rfc;
		var nssCifrado = AtributosPersonaCtrl.personaFirmada.nssCifrado;
		nssCifrado = nssCifrado != undefined && nssCifrado != null && nssCifrado != '' ? nssCifrado : -1; 
		
		WizardIVROVentanillaSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', idPersona, rfc, nssCifrado);
		WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
	}
};

$('#ivroAltaSeguroDomestico').live('click', function(event) {
	event.preventDefault();
	ivroPortlet.mostrarAltaSeguroDomesticoWizard();
});


//Renovación doméstico
$("#reviewModalidadIvroDomestico").live('click', function(event) {
	event.preventDefault();
	
	titulo = "Incorporaci\u00F3n Voluntaria al R\u00e9gimen Obligatorio";
	ivroPortlet.mostrarRenovacionFielSeguroDomesticoWizard();
	
});


var seguroIvroDomesPersonalWidget = {
		checar : function() {			
			var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
			var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			/**Se agrega SIN_CORREO por loggeo con fiel*/
			var url = '/gestionSeguroVoluntario-web/wizard/seguroDomestico/validarAccesoTramite/' + idPersona + '/' + rfcPersona;
			$.getJSON(url, callbackChecar);
		}

	};

var callbackChecar = function(data){

	var id = data.idPersona;
    if (data.error) {
		construirDialogoValidacion("#dialogoMensajes", "Notificaci\u00F3n", data.msgError, true, undefined, undefined, 250, 600);
		return;
	} else {
		var datos = {contenedor: 'wizardModalidadIvroPersonalComponent',
				idPersona: AtributosPersonaCtrl.personaPortal.idPersona,
				rfc: AtributosPersonaCtrl.personaPortal.rfc,
				correo:SIN_CORREO,
				title: titulo};
		       
		WizardSeguroIvroIndivCtrl.init(datos);
		WizardSeguroIvroIndivCtrl.abrir();
	}
};

function construirDialogoValidacion(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
	}
	
	if(height == undefined){
		height=150;
	}
	if(width == undefined){
		width=400;
	}
	
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		close: function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
				    		callbackForXButton();
				    	}
				    }
		  		},
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}


$('#ivroVentanillaSeguroDomestico').live('click', function(event) {
	event.preventDefault();
	ivroPortlet.mostrarVentanillaSeguroDomesticoWizard();
});

$('.link-detalle').live('click', function(event) {
	event.preventDefault();
	var idSeguro = $(event.currentTarget).attr('idSeguro');
	try {
		if(ventanilla) {
			$('#verDetalleSeguroDomesticoForm').attr('action', '/${mvn.web.app.root}/wizard/detalle/seguro/domestico/' + idSeguro);
			$('#verDetalleSeguroDomesticoForm').submit();
		}
		else {
			throw 'No es ventanilla';
		}
	}
	catch(e) {
		WizardDetalleSeguroCtrl.init('wizardDetalleSeguroComponent1', idSeguro);
		WizardDetalleSeguroCtrl.abrir();
	}
});

var idSerguroRenovacion = 0;
var errorRenova = false;

$(".link-renovacion").live('click', function(event) {
	event.preventDefault();
	var idSeguro = $(event.currentTarget).attr('idSeguro');
	var nssTrabajador = $(event.currentTarget).attr('nssTrabajador');

	idSerguroRenovacion = $(event.currentTarget).attr('idSeguro');
	
	if(nssTrabajador== undefined || nssTrabajador=='')
		nssTrabajador='SIN_NSS';
	
	var url = '/${mvn.web.app.root}/wizard/seguroDomestico/validarAccesoTramiteRenovacion/' +  
	nssTrabajador + '/' +
	idSerguroRenovacion  + '?' + Math.random();
	
	$.ajax({
		  dataType: "json",
		  url: url,
		  async: false,
		  success: function(result){
			  iniciarValidacionAccesoTramiteDomesticoRenovacion(result);
	      }
	});
	if(errorRenova == false){
		ivroPortlet.mostrarRenovacionSeguroDomesticoWizard(idSeguro);	
	}
});

function iniciarValidacionAccesoTramiteDomesticoRenovacion(response){
	if(response.error==true){
		errorRenova = true;
		$divError = $('<div></div');
		$divError.dialog({
			autoOpen : false,
			resizable : false,
			width: 700,
			height : 'auto',
			title : 'Mensaje del sistema',
			modal : true,
			close: function(){
			},
			buttons : {
				"Aceptar" : function() {
					$(this).dialog('close');	
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		var htmlError ='<div class="alert alert-info">'+
			''+
			'<strong>Importante: </strong>'+ response.msgError + '</div>';
		$divError.html(htmlError);
		$divError.dialog('open');
	}else{
		errorRenova = false;
	}
}

function mensajeError() {

	$decision = $('<div title="Mensaje"></div');

	$decision.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 		$( this ).dialog( "destroy" );
		 	}
		 }
	});

	$decision.html('Es necesario contar con RFC para realizar este tr&aacute;mite');
	$decision.dialog('open');
	
}

function desplegarMensajeError(mensaje, titulo, error) {

	$decision = $('<div title="Mensaje"></div');
	
	$decision.removeAttr("style");
	if (error) {
		$decision.attr("style", "color: red;");
	} else {
		$decision.attr("style", "color: blue;");
	}
	
	$decision.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    title : titulo,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 		$( this ).dialog( "destroy" );
		 	}
		 }
	});

	$decision.html(mensaje);
	$decision.dialog('open');
	
}


function construirDialogoValidacion(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
	}
	
	if(height == undefined){
		height=150;
	}
	if(width == undefined){
		width=400;
	}
	
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		close: function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
				    		callbackForXButton();
				    	}
				    }
		  		},
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}

$(document).ready(function() {
	
	var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
	var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
	
	
	if(typeof ventanilla === 'undefined' || !ventanilla) {
		var url = '/portal-web/utility/menu/opciones/1/10' + '/' + idPersona + '/' + rfcPersona;	
		$.post(url,null,function(data) {
			$("#opcionesPortletIVROSeguroDomestico").html(data); 
		});
	}
});

function limpiarDatos(listener) {
	$.ajax('/${mvn.web.app.root}/wizard/seguroDomestico/comunes/limpiarDatos').done(listener);
}

var mostrarAltaSeguroDomesticoWizard = function(data){
    if (data.error) {
    	if(typeof ventanilla === 'undefined' || !ventanilla) {
    		construirDialogoValidacion("#dialogoMensajes", "Notificaci\u00F3n", data.msgError, true, undefined, undefined, 250, 600);
    	}else{
    		desplegarMensajeError(data.msgError, "Notificaci\u00F3n", true);
    	}
		return;
	} else {
		try {
			if(ventanilla) {
				$('#comprarSeguroDomesticoForm').submit();
			}
			else
				throw 'No es ventanilla';
		} catch(e) {
			var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			 var rfc = AtributosPersonaCtrl.personaPortal.rfc;
			 var nssCifrado = AtributosPersonaCtrl.personaFirmada.nssCifrado;
			 nssCifrado = nssCifrado != undefined && nssCifrado != null && nssCifrado != '' ? nssCifrado : -1; 
			 
			 WizardIVROAltaSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', idPersona, rfc, nssCifrado);
			 WizardIVROAltaSeguroDomesticoCtrl.open();
		}
	}
};
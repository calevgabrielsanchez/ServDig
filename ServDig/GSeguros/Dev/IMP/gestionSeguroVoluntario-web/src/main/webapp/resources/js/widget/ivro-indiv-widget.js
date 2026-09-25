/*
 * JS de control del Widget de Persona Ivro Individual Seguro.
 */

var isReadOnly = $('div#idPersonaIvroIndivWidget').attr('is-readOnly'); 
var titulo   = '';
var SIN_CORREO = 'SIN_CORREO';
	$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js");
	$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/individual/WizardSeguroIvroIndivCtrl.js");


var seguroIvroPersonalWidget = {
		checar : function() {			
			var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
			var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
			/**Se agrega SIN_CORREO por loggeo con fiel*/
			var url = '/gestionSeguroVoluntario-web/wizard/individual/validarAccesoTramite/' + idPersona + '/' + SIN_CORREO + '/' + rfcPersona;
			$.getJSON(url, callbackChecar);
		}

	};

$("#reviewModalidadIvroIndiv").live('click', function(event) {
	event.preventDefault();
	
	titulo = "Incorporaci\u00F3n Voluntaria al R\u00e9gimen Obligatorio";
	seguroIvroPersonalWidget.checar();
	
});

$("#reviewModalidadIvroIndivExtem").live('click', function(event) {
	
		event.preventDefault();
		var newMessage ="La cobertura de los servicios por tu Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio del Seguro Social ha concluido. En caso de que quieras continuar con dicha cobertura, da clic en &lt;Cancelar&gt; y acude a tu Subdelegaci&oacute;n para valorar tu solicitud de renovaci&oacute;n extempor&aacute;nea. \n De lo contrario, si continuas, tu solicitud se considerar&aacute; como incorporaci&oacute;n inicial, si est&aacute;s de acuerdo selecciona la opci&oacute;n &lt;Siguiente&gt;.";
		
		parent.WizardSeguroIvroIndivCtrl.dialogoRenovacionExtemporanea("#dialogoMensajes",
				"Mensaje de sistema", newMessage);	
		parent.WizardSeguroIvroIndivCtrl.cerrar();	
});

 function dialogoRenovacionExtemporanea(divId, titulo, mensaje) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");	
	$("#textoMensaje").attr("style", "color: blue;");
	var height=300;		
	var width=600;				
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		buttons : [
			{
				text : "Cancelar",
				class : "btn btn-default",
				click : function() {
					$(this).dialog("close");
				}
			},
			{
                text: "Siguiente" ,
				class: "btn btn-primary",
                click: function () {
                	titulo = "Renovaci\u00F3n de Seguro Personal";
                	seguroIvroPersonalWidget.checar();
                  $( this ).dialog( "close" );
                }
            }
		]
	});
	objDialogo.dialog('open');
}

$("#compraModalidadIvroIndiv").live('click', function(event) {
	event.preventDefault();
	titulo = "Compra de Seguro Personal";
	seguroIvroPersonalWidget.checar();
});


var muestraDetalle = function (id) {
	WizardDetalleSeguroCtrl.init('wizardDetalleSeguroComponent', id);
	WizardDetalleSeguroCtrl.abrir();
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
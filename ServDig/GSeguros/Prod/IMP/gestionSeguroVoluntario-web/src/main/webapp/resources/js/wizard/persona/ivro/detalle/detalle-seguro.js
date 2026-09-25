var dialogoConfirmarCancelar;
var dialogoConfirmar;

$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/individual/WizardSeguroIvroIndivCtrl.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js");

$(document).ready(function(){
	$('#cerrarWizard').click(function(){
		if(domestico === '' || domestico === 'false'){
			parent.WizardDetalleSeguroCtrl.cerrar();	
		}else{
			parent.WizardIVROVentanillaSeguroDomesticoCtrl.close();
		}
		
		if(parent.WizardDetalleDomesticoCtrl !== undefined 
				&& parent.WizardDetalleDomesticoCtrl.config.idPersona !== undefined
				&& parent.WizardDetalleDomesticoCtrl.config.idPersona !== null){
			parent.WizardDetalleDomesticoCtrl.close();				
		}
		
		if(parent.WizardDetalleSeguroCtrl !== undefined 
				&& parent.WizardDetalleSeguroCtrl.config.idSeguro !== undefined
				&& parent.WizardDetalleSeguroCtrl.config.idSeguro !== null){
			parent.WizardDetalleSeguroCtrl.cerrar();
		}
		
		if((typeof $("#wizardAltaSeguroVoluntario")) !== "undefined"){
			$("#wizardAltaSeguroVoluntario").dialog('close');
		}else{
			if((typeof parent.$("#wizardAltaSeguroVoluntario")) !== "undefined"){
				parent.$("#wizardAltaSeguroVoluntario").dialog('close');
			}
		}
		
	});
	
	$('#cerrarWizardVentanilla').click(function(){
		if(domestico === '' || domestico === 'false'){
			
			if(parent.WizardDetalleSeguroCtrl !== undefined 
					&& parent.WizardDetalleSeguroCtrl.config.idSeguro !== undefined
					&& parent.WizardDetalleSeguroCtrl.config.idSeguro !== null){
				parent.WizardDetalleSeguroCtrl.cerrar();
			}
			parent.WizardSeguroIvroIndivCtrl.cerrar();
		}else{
			parent.WizardIVROVentanillaSeguroDomesticoCtrl.close();
		}
		
		if(parent.WizardDetalleDomesticoCtrl !== undefined 
				&& parent.WizardDetalleDomesticoCtrl.config.idPersona !== undefined
				&& parent.WizardDetalleDomesticoCtrl.config.idPersona !== null){
				parent.WizardDetalleDomesticoCtrl.close();				
		}
		
		if((typeof $("#wizardAltaSeguroVoluntario")) !== "undefined"){
			$("#wizardAltaSeguroVoluntario").dialog('close');
		}else{
			if((typeof parent.$("#wizardAltaSeguroVoluntario")) !== "undefined"){
				parent.$("#wizardAltaSeguroVoluntario").dialog('close');
			}
		}
		
	});
	
	$('#backWizardVentanillaDomestico').click(function(){		
		$('#__backMainVentanilla').submit();
	});
	
	$('#btnImpComprob').click(function() {
		imprSegPerIvro();
	});

    $('#btnRenovarIvro').live('click', function (event) {
        event.preventDefault();
        var idPersona;
        var sinDomicilio=false;
        var mensajeSinDomicilio="";
        parent.WizardSeguroIvroIndivCtrl.config.datos.title = undefined;
        parent.WizardSeguroIvroIndivCtrl.config.bandera = true;

        $.ajax({
            url : '/${mvn.web.app.root}/wizard/individual/validaDomicilio?'+Math.random(),
            dataType : 'json',
            success : function(response) {

                sinDomicilio = response.sinDomicilio;
                mensajeSinDomicilio=response.ErrorFormGeneral;
                idPersona = response.idPersona;
                // parent.WizardSeguroIvroIndivCtrl.cambiarRenovacionACompra = true;

                console.log("bandera detalle-seguro: "+parent.WizardSeguroIvroIndivCtrl.config.bandera);
                if(sinDomicilio==true&&mensajeSinDomicilio!=""){

                    console.log("Entrando al servicio de Domicilio");
                    $divError = $('<div></div>');
                    $divError.dialog({
                        autoOpen : false,
                        resizable : false,
                        width: 400,
                        height : 'auto',
                        title : 'Mensaje del sistema',
                        modal : true,
                        close: function(){
                            console.log("entrando a la llamada del servicio");
                            parent.WizardSeguroIvroIndivCtrl.cerrar();
                            parent.callbackActualizarDomicilio();
                        },
                        buttons : {
                            "Aceptar" : function() {
                                $(this).dialog('close');
                            }
                        }
                    }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

                    var htmlError ='<div class="alert alert-info">'+
                        ''+
                        '<strong>Importante: </strong>'+ mensajeSinDomicilio + '</div>';
                    $divError.html(htmlError);
                    $divError.dialog('open');

                }else{
                    iniciaRenovacion();
                }

            },
            error : function(error) {
                $.unblockUI();
                var msgError = error.msgError;
                if (typeof msgError === 'undefined') {
                    msgError = 'Ocurri\u00f3 un error inesperado al consultar el domicilio.';
                }
                parent.construirDialogo("#dialogoMensajes",
                    "Mensaje de sistema", msgError, true,
                    undefined, undefined, 250, 400);
            }
        });
    });


    $('#btnExtemporaneoIvro').live('click', function (event) {


		event.preventDefault();
		var newMessage ="La cobertura de los servicios por tu Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio del Seguro Social ha concluido. En caso de que quieras continuar con dicha cobertura, da clic en &lt;Cancelar&gt; y acude a tu Subdelegaci&oacute;n para valorar tu solicitud de renovaci&oacute;n extempor&aacute;nea. \n De lo contrario, si continuas, tu solicitud se considerar&aacute; como incorporaci&oacute;n inicial, si est&aacute;s de acuerdo selecciona la opci&oacute;n &lt;Siguiente&gt;.";

		parent.WizardSeguroIvroIndivCtrl.dialogoRenovacionExtemporanea("#dialogoMensajes",
				"Mensaje de sistema", newMessage);
		parent.WizardSeguroIvroIndivCtrl.cerrar();
});

    $('#btnRenovarInhabilitadoIvro').live('click', function (event) {
		event.preventDefault();
		var mensaje = "Por el momento no es posible realizar la renovaci\&oacute;n de tu seguro, puedes acudir a tu Subdelegaci\&oacute;n para realizar el tr\&aacute;mite.";

                parent.construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", mensaje, true,
						undefined, undefined, 250, 400);
	});

	$('#regresar').click(function(e){
		e.preventDefault();

		$('form#regresarForm').submit();
	});

});

function iniciaRenovacion(){

    parent.WizardSeguroIvroIndivCtrl.config.bandera = true;
    parent.WizardSeguroIvroIndivCtrl.config.renovacion = true;
    parent.WizardSeguroIvroIndivCtrl.setUrlRenovacion();

    var _wizard = null;
    _wizard = parent.WizardSeguroIvroIndivCtrl;
    _wizard.config.renovacion = true;
    _wizard.config.bandera = true;
    _wizard.config.datos.title = undefined;
    _wizard.abrir();

}


var imprimePago = function(idPago) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/lc/pago/'+idPago;
    window.open(liga, '_blank');
};

var cerrarWizard = function () {
	$("#wizardDetalleSeguroComponent").dialog('close');
	//alert("Cerrando");
};

var imprSegPerIvro = function (id) {
	var idCifrado = id.getAttribute('data-id');
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/reporteComp/'+idCifrado;
	  window.open(liga, '_blank');
};

var imprCuestionarioIvro = function (id) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/cuestionario/'+id;
	  window.open(liga, '_blank');
};

var enviaCorreo = function(idPago,cveIdSeguroIvro) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/correoElectronico/' + idPago + '/' + cveIdSeguroIvro;
	$.ajax({
		url : liga,
		dataType : 'json',
		success : function(response) {
			$.unblockUI();
		},
	});
};


var enviarComprobanteYLineaCapturaRenvacion = function(idSeguro) {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/finalizarSeguroDetalle/enviarCorreoElectronico/';
		$.ajax({
			url : liga + idSeguro,
			dataType : 'json',
			success : function(response) {
				$.unblockUI();
			},
			error : function(error) {
				$.unblockUI();
				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al enviar correo.';
				}
				parent.construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", msgError, true,
						undefined, undefined, 250, 400);
			}
		});
}
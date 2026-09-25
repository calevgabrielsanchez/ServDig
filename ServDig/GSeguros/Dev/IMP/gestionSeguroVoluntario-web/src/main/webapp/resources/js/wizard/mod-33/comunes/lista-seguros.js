$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');


$(function() {

	$('#tablaSeguros').dataTable({
		'bFilter' : false,
		'bDestroy' : true,
		'bLengthChange' : false,
		'bAutoWidth' : false,
		'bInfo' : false,
		'sPaginationType' : 'bootstrap',
		'aoColumnDefs' : [ {
			'sSortDataType' : 'html',
			'sType' : 'html',
			'aTargets' : [ 0 ]
		}, {
			'bSortable' : false,
			'aTargets' : [ 2 ]
		} ],
		'aoColumns' : [ {
			'sWidth' : '60%'
		}, {
			'sWidth' : '20%'
		}, {
			'sWidth' : '20%'
		} ]
	});

	$('#cerrar').click(function(event) {
		event.preventDefault();
		
		closeWizard();
	});

    $('#tablaSeguros').on('click', '.link-detalle', function(event) {
        event.preventDefault();

        var idSeguro = $(this).attr('idSeguro');
        var _wizardDetalle = parent.WizardDetalleSeguroCtrl;
        var _wizardSeguro = parent.WizardAltaSeguroFamiliarCtrl;

        if (typeof _wizardDetalle === 'undefined') {
            var urlDetalle = '/${mvn.web.app.root}/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js';

            $.getScript(urlDetalle, function(){
                WizardDetalleSeguroCtrl.init(_wizardSeguro.config.container, idSeguro);
                WizardDetalleSeguroCtrl.abrir(idSeguro);
            });
        } else {
            _wizardDetalle.init(_wizardSeguro.config.container, idSeguro);
            _wizardDetalle.abrir(idSeguro);
        }
    });
    
    $('#iniciarTramite').click(function() {	
    	$.ajax({
			url : parent.WizardAltaSeguroFamiliarCtrl.config.baseUrl + '/validaPersona/'+
					+ parent.WizardAltaSeguroFamiliarCtrl.config.idPersona + '/',
			dataType : 'json',
			success : function(response) {
				if(response.error){
					parent.construirDialogo("#dialogoMensajes",
							"Mensaje de sistema", response.msgError, true,
							undefined, undefined, 250, 400);
				}else{
				    parent.WizardAltaSeguroFamiliarCtrl.open();
					closeWizard();
				}				
			},
			error : function(error) {
				$.unblockUI();
				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al validar el acceso al tr\u00e1mite.';
				}
				parent.construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", msgError, true,
						undefined, undefined, 250, 400);
			}
		});    	
	});

    $('#btnExtemporaneoSSF').click(function() {
    	$.ajax({
			url : parent.WizardAltaSeguroFamiliarCtrl.config.baseUrl + '/validaPersona/'+
					+ parent.WizardAltaSeguroFamiliarCtrl.config.idPersona + '/',
			dataType : 'json',
			success : function(response) {
				if(response.error){
				var error=response.msgError;
				var mensaje = "Su seguro venci&oacute;, puede acudir a su Subdelegaci&oacute;n para realizar el tr&aacute;mite de Renovaci&oacute;n extempor&aacute;nea o continuar su solicitud como una  Incorporaci&oacute;n Inicial";
					dialogoErrorExtemporanea("#dialogoMensajes","Mensaje de sistema", mensaje,error);
				
					
				}else{
					var mensaje = "Su seguro venci&oacute;, puede acudir a su Subdelegaci&oacute;n para realizar el tr&aacute;mite de Renovaci&oacute;n extempor&aacute;nea o continuar su solicitud como una  Incorporaci&oacute;n Inicial";
					parent.WizardAltaSeguroFamiliarCtrl.dialogoRenovacionExtemporanea("#dialogoMensajes","Mensaje de sistema", mensaje);
				}				
			},
			error : function(error) {
				$.unblockUI();
				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al validar el acceso al tr\u00e1mite.';
				}
				parent.construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", msgError, true,
						undefined, undefined, 250, 400);
			}
		});		
	});
});

 var dialogoErrorExtemporanea= function (divId, titulo, mensaje,error) {
        var _wizard = this;
        $("#textoMensaje").html(mensaje);
        $("#textoMensaje").removeAttr("style");
        $("#textoMensaje").attr("style", "color: blue;");
        var height = 250;
        var width = 400;
        var objDialogo = $(divId).dialog({
            autoOpen: false,
            resizable: false,
            modal: true,
            height: height,
            width: width,
            title: titulo,
            buttons: [
                {
                    text: "Cancelar",
                    class: "btn btn-default",
                    click: function () {
                        $(this).dialog("close");
                    }
                },
                {
                    text: "Siguiente",
                    class: "btn btn-primary",
                    click: function () {
                        parent.construirDialogo("#dialogoMensajes",
							"Mensaje de sistema", error, true,
							undefined, undefined, 250, 400);
							 $(this).dialog("close");
                    }
                }
            ]
        });
        objDialogo.dialog('open');
    };

var enviaComprobanteRenvacionSSF = function() {
	var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/finalizarSeguroDetalle/enviarCorreoElectronico/';
		$.ajax({
			url : liga + '0' + '/',
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
			}
		});
};
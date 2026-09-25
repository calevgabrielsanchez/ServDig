$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-40/comunes/common.js');
$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-40/comunes/TCCuestionarioControl.js');

$(document).ready(function() {

	var infoFechaBaja = '<p>Si eliges iniciar la incorporaci&oacute;n al d&iacute;a siguiente de tu baja como asegurado, deber&aacute;s pagar las cuotas que no fueron cubiertas desde entonces hasta el d&iacute;a de hoy.</p>';
	
		$('#ayudaFechaBaja').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoFechaBaja,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		

		var infoSalario           = '<p>Escribe el salario en pesos y centavos.</p>';
		var infoSalarioRenovacion = '<p>Puedes elegir entre el &uacute;ltimo salario que ten&iacute;as registrado al momento de tu baja como asegurado, o un salario mayor que no rebase el l&iacute;mite de 25 veces el valor de la UMA.</p>';

		$('#ayudaSalario').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoSalario,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		$('#ayudaSalarioRenovacion').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoSalarioRenovacion,
			trigger : 'hover',
			placement : 'left',
			container : 'body'
		});
	
	$('#cancelarTramiteAux').click(function() {
		closeWizard();
	});
	
	$('#btnCancelarSolicitudAlta').click(function() {
		closeWizard();
	});
	
	$('#btnIniciarSolicitudAlta').click(function() {
		$('#capturarDatosSolicitudAltaForm').submit();
	});

	$('#btnIniciarSolicitudAltaCompra').click(function() {
		var _idPersona = parent.WizardAltaCVROCtrl.config.idPersona;
		var _nssCifrado = parent.WizardAltaCVROCtrl.config.nssCifrado;
		parent.WizardAltaCVROCtrl.config.url = '/gestionSeguroVoluntario-web-ciudadano/wizard/continuacionVoluntaria/alta/init';
		parent.WizardAltaCVROCtrl.config.title = 'Inscripci\u00F3n a la continuaci\u00F3n voluntaria en el r\u00E9gimen obligatorio';
		parent.WizardAltaCVROCtrl.config.container = 'divWizardContinuacionVoluntaria';		
		parent.WizardAltaCVROCtrl.setDatos(_idPersona, _nssCifrado);
		parent.WizardAltaCVROCtrl.abrir();
	});
	
	muestraMsgError = function (msg){
		$('#dialogoMsgSeleccion').html('<div class="ui-dialog-content ui-widget-content">'
		+'<span style="float: left; margin: 0 7px 20px 0;" class="ui-icon ui-icon-alert"></span>'
		+'<span style="color: black;">'+msg+'</span></div>');
		  $('#dialogoMsgSeleccion').dialog({
			  title : 'Mensaje',
			  dialogClass: "no-close",
		      modal: true,
		      resizable : false,
		      buttons: {
		        'Aceptar': function() {
		          $( this ).dialog( "close" );
		          $('#dialogoMsgSeleccion').html('');
		        }
		      }
		});
	}
	
	
	muestraMsgAyuda = function (msg){
		$('#dialogoMsgSeleccion').html('<div class="separadorseccion"><span>Mensaje de ayuda</span></div>'+'<p>'+msg+'</p>');
		  $('#dialogoMsgSeleccion').dialog({
			  title : 'IMSS Digital',
			  dialogClass: "no-close",
			  width : 800,
			  modal : true,
			  resizable : false,
			  autoResize : true,
			  position : {
					my : 'top',
					at : 'top',
					of : window.document,
					offset : '0 10'
				},
		      buttons: {
		        'Cerrar': function() {
		          $( this ).dialog( "close" );
		          $('#dialogoMsgSeleccion').html('');
		        }
		      }
		}); 
	}

	
$('#listDomicilios').selectable();
	
	$('#agregarDomicilio').click(function() {
		$('form#otraUbicacionForm').submit();
	});
	
	$('#cancelarTramite').click(function() {
	    closeWizard();		
	});
	
	$('#cancelarTramiteEnAgregarDomcilio').click(function() {
		dialogoCancelarEnDomicilio.dialog('open');
	});
	
	$('#siguientePaso').click(function(event) {
		event.preventDefault();
		
		$('#validacion').hide();
	
		var idDomicilio = $('.ui-selected').attr('idDomicilio');
		var cp = $('.ui-selected').attr('codigoPostal');
		
		if($('.ui-selected').length > 0) {
			
			$('#idDomSeguro', '#nextStepForm').val(idDomicilio);
			$('#cpDomSeguro', '#nextStepForm').val(cp);
			$('#desdeExtranjero', '#nextStepForm').val($('#desdeExtranjeroChk').is(':checked'));
			
			$('#nextStepForm').submit();
		}
		else {
			$('#validacion').show();
			
			setSizeWithinIframe(document);
		}
	});
	
	$('#linkTCCuestionario').click(function() {
		//dialogSeguroModalidad40.dialog('open');
		parent.CartaTerminosCtrl.init('divComponentCommon', 125);
	    parent.CartaTerminosCtrl.abrir();
	});

	if($('ol#listDomicilios li.domicilioOtraUbicacion').length > 0){
		var idx = $('ol#listDomicilios li.domicilioOtraUbicacion').index();
		selectSelectableElement($('ol#listDomicilios'), $('ol#listDomicilios').children(':eq(' + idx + ')'));
	} else if($('ol#listDomicilios li.domicilioParticular').length > 0){
        var idx = $('ol#listDomicilios li.domicilioParticular').index();
        selectSelectableElement($('ol#listDomicilios'), $('ol#listDomicilios').children(':eq(' + idx + ')'));
    }
	
	dialogoConfirmarCancelar = $('#dialog-confirm-cancelar').dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: 'no-close',
	    closeOnEscape: false,
		buttons: {
		 	'Cancelar': function() {
		 		$(this).dialog( "close" );
		 	},
		 	'Aceptar': function() {
		 		cancelable = false;
				$(this).dialog( "close" );					
				closeWizard();
		 	}
		 }
	});
	dialogoCancelarEnDomicilio = $('#dialog-cancelar-en-domicilio').dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: 'no-close',
	    closeOnEscape: false,
		buttons: {
		 	'Cancelar': function() {
		 		$(this).dialog( "close" );
		 	},
		 	'Aceptar': function() {
		 		cancelable = false;
				$(this).dialog( "close" );					
				closeWizard();
		 	}
		 }
	});
	
});

function selectSelectableElement(selectableContainer, elementToSelect) {
// add unselecting class to all elements in the styleboard canvas except current one
$("li", selectableContainer).each(function() {
	if (this != elementToSelect[0])
		$(this).removeClass("ui-selected").addClass("ui-unselecting");
});

// add ui-selecting class to the element to select
elementToSelect.addClass("ui-selecting");

selectableContainer.selectable('refresh');
// trigger the mouse stop event (this will select all .ui-selecting elements, and deselect all .ui-unselecting elements)
selectableContainer.data("selectable")._mouseStop(null);
}

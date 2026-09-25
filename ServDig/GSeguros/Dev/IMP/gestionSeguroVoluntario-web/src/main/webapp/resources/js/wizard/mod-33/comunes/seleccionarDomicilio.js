$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');

$(document).ready(function() {
	$('#listDomicilios').selectable({
		selected: function( event, ui ) {
			obtenerUMFs($(ui.selected).attr('codigoPostal'));
		}
	});

	$('#cerrar').click(function() {
		closeWizard();
	});
	
	$('#agregarDomicilio').click(function() {
                var input = $("<input>").attr("type", "hidden").attr("name", "desdeExtranjeroDom").val($('#desdeExtranjeroChk').is(':checked'));
                $('form#otraUbicacionForm').append(input)
		$('form#otraUbicacionForm').submit();
	});
	
	$('#siguientePaso').click(function(event) {
				
		event.preventDefault();
		
		$('#validacion').hide();
	
		if($('.ui-selected').length > 0) {
							
			var isUmfSelected = false;
			
			$('input#idUmfRadio', 'table#tblUMFs').each(function(){
				if ($(this).is(':checked')) {
					isUmfSelected = true;
				}
			});
			
			if (isUmfSelected) {
							
				$.blockUI();
				
				var url = $('form#umfForm').attr('action');
				var umf = $('form#umfForm').toObject();
				
				$.postJSON(url, umf, function(response){
				
					var idDomicilio = $('.ui-selected').attr('idDomicilio');
					var cp = $('.ui-selected').attr('codigoPostal');
					var idEntidad = $('.ui-selected').attr('idEntidad');
					var idMunicipio = $('.ui-selected').attr('idMunicipio');
					
					$('#idDomSeguro', '#nextStepForm').val(idDomicilio);
					$('#cpDomSeguro', '#nextStepForm').val(cp);
					$('#entidadDomSeguro', '#nextStepForm').val(idEntidad);
					$('#municipioDomSeguro', '#nextStepForm').val(idMunicipio);
					$('#desdeExtranjero', '#nextStepForm').val($('#desdeExtranjeroChk').is(':checked'));
					
					$.unblockUI();
					
					$('#nextStepForm').submit();
				});
			} else {
				mostrarMensajeError('Selecciona una UMF');
			}
		} else {
			mostrarMensajeError('Selecciona un domicilio');
		}
	});
	
	if($('ol#listDomicilios li.domicilioOtraUbicacion').length > 0){
		var idx = $('ol#listDomicilios li.domicilioOtraUbicacion').index();
		selectSelectableElement($('ol#listDomicilios'), $('ol#listDomicilios').children(':eq(' + idx + ')'));
	};
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

function mostrarMensajeError(msg) {
	$('#mensaje-validacion', '#validacion').text(msg);
	$('#validacion').show();
	
	/*
	 * Si es internet explorer se usa otro elemento para hacer el scroll para evitar el desfase muy marcado a la izquierda
	 */
	if ($.browser.msie || $.browser.version == '11.0') {
		document.getElementById("divContenedorDom").scrollIntoView();
	}else{
		document.getElementById("validacion").scrollIntoView();
	}
	
	setSizeWithinIframe(document);
}

function obtenerUMFs(codigoPostal) {
		
	var url = '/${mvn.web.app.rootDomicilios}/widget/domicilio/utility/umf/' + codigoPostal;
	
	$.ajax({
		url : url,
		data : null,
		beforeSend : function() {
			$('div#umfContenedor > div').show();
			$.blockUI();
        },
		success : function(data) {
			$.getScript("/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/widget/UmfImssWidget.js",function(){
				$('div#umfContenedor').html(data);
		
				// Se settea la función de callback para las UMF
				setFnCallback(fnSeleccionarUMF);
				
				initComponenteUMFs();
				
				setSizeWithinIframe(document);
				
				$.unblockUI();
			});
		},
		error : function (data){
			$('div#umfContenedor')
					.html('<div class="alert alert-danger">Ocurri\u00F3 un error inesperado al cargar las UMF</div>');
			$.unblockUI();
		}
	});
}

var fnSeleccionarUMF = function () {

	//idUMF|noEconomico|idDelegacion|cveDelegacion|idSubdelegacion|cveSubdelegacion|cveCiz;
	if(umfSeleccionada != null){
		var umfArray = umfSeleccionada.split('|');
		
		$('#idUmf').val(umfArray[0]);
		$('#noEconomicoUmf').val(umfArray[1]);
		$('#idDelegacionUmf').val(umfArray[2]);
		$('#cveDelegacionUmf').val(umfArray[3]);
		$('#idSubdelegacionUmf').val(umfArray[4]);
		$('#cveSubdelegacionUmf').val(umfArray[5]);
		$('#cveCizUmf').val(umfArray[6]);
	}
};
/**
 * JS para la vista de la caputa libre de la persona 
 */

var objTipoSerie;

$(document).ready(function(){
			
	if ($('input#fisica\\.nss').length > 0) {
		cleanSession();
	}
	
	iniciarUMF();
	
	//Verificamos los campos a readonly
	$('div#datosBasicosDiv').deshabilitarContenido(false);
	
	$('div#datosCurp').deshabilitarContenido(false);
    // los combos hechos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual	
	$('#fisica\\.lugarNacimiento\\.clave').attr('disabled', 'disabled');
	$('#fisica\\.sexo\\.idSexo').attr('disabled', 'disabled');
	
	//Configuracion del calendario
	var fechaNac = $('#registroFechaNacimientoC').val();
	var nss = $('input#fisica\\.nss').val();
	
	if ((fechaNac == null || fechaNac == '') && (nss == '' && nss == null)) {
		$("#registroFechaNacimientoC").datepicker({
			showOn: 'both',
			dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			yearRange: '-112:+0'
		});
	}
	
	$('#regresar').click(function(){
		var url = context_path + "/tramite/iniciar";
		var form = $('form#regresarForm').attr('action' , url);
		form.attr('method' , 'get');
		form.submit();
	});
	
	$('#registrar').click(function(){
		
		$('div#datosBasicosDiv').habilitarContenido(false);
		$('div#datosCurp').habilitarContenido(false);
		// los combos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual		
		$('#fisica\\.lugarNacimiento\\.clave').removeAttr('disabled');
		$('#fisica\\.sexo\\.idSexo').removeAttr('disabled');
		
		$('form#registroPersonaFisicaForm').submit();
	});

	$('#btnDescargar').click(function(e){
		e.preventDefault();
		var _idSolicitud = $('input#idSolicitud').val();
	
		var url = context_path + '/reporte/comprobante/recuperado';
	
		$('form#formComprobante').attr('action', url);
		$('input#si', 'form#formComprobante').val(_idSolicitud);
		$('form#formComprobante').attr('alreadyDownloaded', 'true');
		$('div#notDownloadedWarning').hide();
		$('form#formComprobante').submit();
	});
	
	$('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie').change(function(){
		var text = $('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie option:selected').text();
		$('#asignacionSerieNss\\.serie\\.tipoSerie\\.descripcion').val(text);
	});

	/*
	 * Se checa si el combo para las series tiene dos y sólo
	 * dos opciones, esto significa que sólo se encontró una
	 * serie disponible (la otra es la opcion default) y se
	 * selecciona la serie encontrada,
	 */
	if ($('select#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie option').length == 2) {
		$('select#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie option').not('[value = -1]').attr("selected",true);
		
		var text = $('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie option:selected').text();
		$('#asignacionSerieNss\\.serie\\.tipoSerie\\.descripcion').val(text);
	}
	
	
	$('button#nextRecordBtn').click(function(){
		
		$('span#erroUMF').hide();
		
		// Se valida si ya se seleccionó una UMF
		var idUMF = $('input#idUmfAsegurado').val();
		if (idUMF != null && idUMF != undefined && idUMF != '' ) {
			$('form#extranjeroSIMERecuperadoForm').submit();
		} else {
			$('span#erroUMF').text('Campo requerido');
			$('span#erroUMF').show();
		}
	});
	
	$('button#ignoreCurrentRecord').click(function(){
		$('form#extranjeroSIMEIgnoreForm').submit();
	});
		
	if($('form#formComprobante').length > 0) {
		$(window).on('beforeunload', function(){
			if ($('form#formComprobante').attr('alreadyDownloaded') == 'false') {
				$('div#notDownloadedWarning').show();
				return 'No has descargado el comprobante, ¿estas seguro de cambiar de página?';
			}
		});
	}
	
});

/*
 * Este metodo hace una consulta para obtener los datos de una serie en base a
 * un idSerie y si el idTipoSerie es 3 entonces mostrara el combo del año de
 * inscripcion si se diferente a 3 entonces lo ocultara
 */ 
function getSerie(){
	
	var sDivSerie = 'div#selectSerie';
	var objDivSerie = $(sDivSerie);
	
    var url = context_path + "/serie/get/serie";
    var idSerie = $('#serie\\.idSerie').val();
	   
    $.getJSON(url, {'idSerie': idSerie}, function(data){
    	
    	objTipoSerie = data.serie.tipoSerie.idTipoSerie;
		if(objTipoSerie == 3){
			objDivSerie.removeClass('hiddenElement');
			objDivSerie.addClass('showElement');
		}else{
			objDivSerie.removeClass('showElement');
			objDivSerie.addClass('hiddenElement');
		}
		
		$('#tipoSerie\\.idTipoSerie').val(objTipoSerie);
		
    }).error(function(data){
//		fnProcesarErrores(data, 'form#formCodigoPostal')
    });
}

function iniciarUMF() {

	if ($('input#fromSIME').length > 0 && $('input#fisica\\.nss').length > 0) {
		if ($('input#codigoPostalExtranjero').val() != ''
				&& $('input#codigoPostalExtranjero').val() != undefined) {
			obtenerUMFsByCodigoPostal($('input#codigoPostalExtranjero').val());
		} else {
			$('div#umfContenedor').html('No se cuenta con la información necesaria para obtener las Unidades Médico Familiar');
		}
	}
}

function cleanSession() {
	$.ajax({
		url : "/gestionAsegurados-web/tramite/limpiar-sesion",
		type: "POST",
		dataType : "json",
		data : {
			hashDatosPersona: $('#hashDatosPersona').val()
		}
	});
}
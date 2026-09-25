/**
 * Portlet de solicitudes
 */

var solicitudesPortlet = {
	
};

function ejecutarConsultaSolicitudes () {
	
	ocultarErrorCaptura();
	
	$('#tipoTramiteInputHidden').val($('#tipoTramiteInput').val());
	$('#estadoSolicitudInputHidden').val($('#estadoSolicitudInput').val());
	
	if(validaDatosRequeridos()){
		gridSolicitud.fnDraw();
	}
}

function ejecutarConsultaSolicitudPorFolio (folio) {

	ocultarErrorCaptura();
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		mostrarErrorCaptura('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
	
	$('#noFolioSolicitudDetalle').val(folio);	
	$('#formDetalleSolicitud').submit();
}

function cargarGridSolicitudesConsultadas(sSource, aoData, fnCallback) {

	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm = $('#formBusquedaSolictiudes').serializeObject(true);
	
	delete wrapper.oForm.tipoTramiteInput;
	delete wrapper.oForm.estadoSolicitudInput;
	
	wrapper.aoData = aoData;
	
	$.blockUI();
	
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	}).done(function(){
		$.unblockUI();
	});
}

function renderOwner(oObj) {
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=null 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=undefined) {
		var rowSujeto = oObj.aData.sujetoObligado;
		var texto = rowSujeto.modalidad != undefined && rowSujeto.modalidad != null ? rowSujeto.numeroRegistroPatronal+rowSujeto.modalidad.numModalidad+rowSujeto.digVerificador : rowSujeto.numeroRegistroPatronal; 
		return rowSujeto.numeroRegistroPatronal+rowSujeto.modalidad.numModalidad+rowSujeto.digVerificador;
	} else {
		return "";
	}
}

function renderTramites(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var descripcionTramites = '';
	var i = 0;
	for (i = 0; i < maxValues; i++) {
		descripcionTramites += arrayTramites[i].tipoTramite.descripcion
				+ ".<br>";
	}
	return descripcionTramites;
}

function renderRFC(oObj){
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined) {
		var sujeto = oObj.aData.sujetoObligado;
		var fisica = sujeto.fisica;
		var moral = sujeto.moral;
		var rfc;
		if(fisica!=null && fisica!= undefined){
			rfc=fisica.rfc;
		}else if(moral!=null && moral!=undefined){
			rfc=moral.rfc;
		}else{
			rfc="";
		}
		return rfc;
	} else {
		return "";
	}
}

function renderSubdelegacion(oObj) {
	var numRP = renderOwner(oObj);
	if(numRP == ""){
		return "Por determinar";
	}else{
		if (oObj.aData.sujetoObligado != null
				&& oObj.aData.sujetoObligado != undefined) {
			var sujeto = oObj.aData.sujetoObligado; 
			if(sujeto.subdelegacion!= null && sujeto.subdelegacion!= undefined){
				return sujeto.subdelegacion.descripcion;
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}

function renderDelegacion(oObj) {
	var numRP = renderOwner(oObj);
	if(numRP == ""){
		return "Por determinar";
	}else{
		if (oObj.aData.sujetoObligado != null
				&& oObj.aData.sujetoObligado != undefined) {
			var sujeto = oObj.aData.sujetoObligado; 
			if(sujeto.subdelegacion!= null && sujeto.subdelegacion!= undefined){
				var subdelegacion = sujeto.subdelegacion;
				if(subdelegacion.delegacion!=null && sujeto.subdelegacion!= undefined)
					return subdelegacion.delegacion.descripcion;
				else
					return "";
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}

function crearSolicitudDatePickers(){
	crearPicker($("#fechaInicioPresentacion"));
	crearPicker($("#fechaFinPresentacion"));
	crearPicker($("#fechaInicioConclusion"));
	crearPicker($("#fechaFinConclusion"));
}

function crearPicker(objToAddPicker){
	objToAddPicker.datepicker({
		showOn : "button",
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
}

function abrirDocumentoDeTramite(solicitud,folio,tipoDocumento){
	$('#solicitudDocumentoForm').attr('action', context_path + '/portlet/solicitudes/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento);
	$('#solicitudDocumentoForm').submit();
}


function validaDatosRequeridos(){
	
	var valido=true;
	
	// Se checa que por lo menos uno de los filtros haya sido capturado
	var rfcCaptura = $('#rfc').val();
	var rpCaptura = $('#rp').val();
	var tipoTramiteCaptura = $('#tipoTramiteInputHidden').val();
	var edoSolicitudCaptura = $('#estadoSolicitudInputHidden').val();
	var fecInicioPresentacionCaptura = $('#fechaInicioPresentacion').val();
	var fecFinPresentacionCaptura = $('#fechaFinPresentacion').val();
	var fecInicioConclusionCaptura = $('#fechaInicioConclusion').val();
	var fecFinConclusionCaptura = $('#fechaFinConclusion').val();
	
	var capturaFiltro = false;
	
	if (!isBlank(rfcCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(rpCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(tipoTramiteCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(edoSolicitudCaptura)) {
		capturaFiltro = true;
	} if (!isBlank(fecInicioPresentacionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecFinPresentacionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecInicioConclusionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecFinConclusionCaptura)) {
		capturaFiltro = true;
	}
	
	if (capturaFiltro) {
		if(!validaFormaFiltroSolicitud()) {
			valido=false;
			$('#divMsgErrores').show();
			// Para eliminar el primer <br> de los errores mostrados
			$('#msgError').children().first().children().first('br').remove();
		}
	} else {
		valido = false;
		mostrarErrorCaptura("Para poder realizar la b\u00fasqueda es necesario que especifique por lo menos un filtro.");
	}
	
	return valido;
}

function isBlank(campo) {
	var isBlank = false;
	
	if (campo == null || campo == '' || typeof campo === 'undefined') {
		isBlank = true;
	}
	
	return isBlank;
}

function validaFormaFiltroSolicitud(){
	jQuery.validator.addMethod(
			"dateMX",
			function(value, element) {
				var check = false;
				var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
				if( re.test(value)){
					var adata = value.split('/');
					var dd = parseInt(adata[0],10);
					var mm = parseInt(adata[1],10);
					var yyyy = parseInt(adata[2],10);
					var xdata = new Date(yyyy,mm-1,dd);
					if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) )
						check = true;
					else
						check = false;
				} else
					check = false;
				return this.optional(element) || check;
			},
			"<br>Por favor ingresa una fecha v\u00E1lida con el siguiente formato dd/mm/yyyy"
		);
	
	
	jQuery.validator.addMethod(
		"fechaInicioPresentacionRequerida",
		function(){														
			if ($("#fechaFinPresentacion").val()!="" && $("#fechaInicioPresentacion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de inicio de captura"
	);
	
	jQuery.validator.addMethod(
		"fechaFinPresentacionRequerida",
		function(){														
			if ($("#fechaInicioPresentacion").val()!="" && $("#fechaFinPresentacion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de fin de captura"
	);	
	
	jQuery.validator.addMethod(
		"fechaInicioConclusionRequerida",
		function(){														
			if ($("#fechaFinConclusion").val()!="" && $("#fechaInicioConclusion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de inicio de conclusi&oacute;n"
	);
	
	jQuery.validator.addMethod(
		"fechaFinConclusionRequerida",
		function(){														
			if ($("#fechaInicioConclusion").val()!="" && $("#fechaFinConclusion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de fin de conclusi&oacute;n"
	);
	
	jQuery.validator.addMethod(
		"formatoNumeroRegistroPatronal",
		function(){
			var valorRP = $("#rp").val();
			if (valorRP!=null && valorRP!=""){
				var tamanoRP = valorRP.length;
				if(tamanoRP==8 || tamanoRP==10 || tamanoRP==11){
					return true;
				}else{
					return false;
				}
			}
			return true;
		},
		"<br>Por favor ingresa un n&uacute;mero de registro patronal v&aacute;lido. Debe contener 8, 10 u 11 caracteres."
	);
	
	jQuery.validator.addMethod(
			"formatoRFC",
			function(){
				var valorRFC = $("#rfc").val();
				
				if (valorRFC != null && valorRFC != ""){
					var tamanoRFC = valorRFC.length;
					
					if(tamanoRFC==12 ||tamanoRFC==13){
						return true;
					}else{
						return false;
					}
				}
				return true;
			},
			"<br>Por favor ingresa un RFC v&aacute;lido. Debe contener 12 caracteres si pertenece a una persona moral o 13 caracteres si pertenece a una persona f\u00EDsica."
		);
		
	$("#formBusquedaSolictiudes").validate({
		rules: {
			"rfc":{
				formatoRFC: true
			},
			"rp":{
				formatoNumeroRegistroPatronal: true
			},
			"fechaInicioPresentacion": {
				dateMX: true,
				fechaInicioPresentacionRequerida: true
			},
			"fechaFinPresentacion": {
				dateMX: true,
				fechaFinPresentacionRequerida: true
			},
			"fechaInicioConclusion": {
				dateMX: true,
				fechaInicioConclusionRequerida:true
			},
			"fechaFinConclusion": {
				dateMX: true,
				fechaFinConclusionRequerida:true
			}
		},
		errorLabelContainer:"div#divMsgErrores #msgError",
		errorElement: "span",
		errorClass: "error-custom",
		highlight: function(element) {
			$(element).removeClass("error-custom");
	    },
		onclick: false,
		onfocusout: false,
		onkeyup: false
		
	});
	
	return $("#formBusquedaSolictiudes").valid();
}

function mostrarErrorCaptura(msg) {
	
	$('#msgError').text(msg);
	$('#divMsgErrores').show();
	$('#msgError').show();
}

function ocultarErrorCaptura() {
	
	$('#msgError').text('');
	$('#divMsgErrores').hide();
}
/**
 * Portlet de solicitudes
 */

var solicitudesPortlet = {
	
};

function ejecutarConsultaSolicitudes () {
	
	ocultarErrorCaptura();
		
	if(validaDatosRequeridos()){
		gridSolicitud.fnPageChange('first');
	}
}

function ejecutarConsultaSolicitudPorFolio(folio) {

	ocultarErrorCaptura();
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		mostrarErrorCaptura('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
	
	var url = context_path + '/portlet/solicitudes/validar/folio';
	
	$.blockUI();
	
	$.ajax({
		url : url,
		dataType : 'json',
		data : {folio:folio},
		success : function(data) {
			if (data.EXISTE_SOLIC == false) {
				mostrarErrorCaptura(data.MSG_ERROR);
				$.unblockUI();
				return false;
			} else {
				$.unblockUI();
				$('#noFolioSolicitudDetalle').val(folio);	
				$('#formDetalleSolicitud').submit();
			}
		}
	});
}

function ejecutarConsultaSolicitudPorFolioGrid(folio) {

	ocultarErrorCaptura();
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		mostrarErrorCaptura('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
	
	$('#noFolioSolicitudDetalleGrid').val(folio);
	$('#formDetalleSolicitudGrid').submit();
}

function cargarGridSolicitudesConsultadas(sSource, aoData, fnCallback) {

	$.blockUI();
	
	$('div#divMsgSinResultados').hide();
	
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm = $('#formBusquedaSolictiudes').serializeObject(true);
		
	if (wrapper.oForm.tramiteId == -1) {
		delete wrapper.oForm.tramiteId;
	}
	
	if (wrapper.oForm.idEstadoSolicitud == -1) {
		delete wrapper.oForm.idEstadoSolicitud;
	}
	
	delete wrapper.oForm.idDelegacionAux;
	delete wrapper.oForm.idSubdelegacionAux;
	
	wrapper.aoData = aoData;
		
	$.postJSON(sSource, wrapper, function(data) {
		if (data.iTotalRecords == 0){
			$('div#divMsgSinResultados').show();
			$('span#numRegistros').text(data.iTotalRecords);
			$('div#divNumResultados').hide();
		} else {
			$('span#numRegistros').text(data.iTotalRecords);
			$('div#divNumResultados').show();
		}
		fnCallback(data);
	}).done(function(){
		$.unblockUI();
	}).error(function(response){
		$.unblockUI();
		dialogMensajeError.dialog('open');
	});
}

function renderCurpRfcNrp(oObj) {
	var texto = '<ul class="listTramties">';
	texto += '<li>' + renderCURP(oObj) + "</li>";
	texto += '<li>' + renderRFC(oObj) + "</li>";
	texto += '<li>' + renderOwner(oObj) + "</li>";
	texto += '</ul>';
	
	return texto;
}

function renderOwner(oObj) {
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=null 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=undefined) {
		
		var rowSujeto = oObj.aData.sujetoObligado;
		 
		return rowSujeto.numeroRegistroPatronal+rowSujeto.modalidad.numModalidad+rowSujeto.digVerificador;
	} else {
		return "SIN NRP";
	}
}

function renderTramites(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var descripcionTramites = '';
	var i = 0;
	
	descripcionTramites = '<ul class="listTramties">';
	
	for (i = 0; i < maxValues; i++) {
		descripcionTramites += '<li>' + arrayTramites[i].tipoTramite.descripcion
				+ '</li>';
	}
	
	descripcionTramites += '</ul>';
	
	return descripcionTramites;
}

function renderCURP(oObj){
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined) {
		
		var sujeto = oObj.aData.sujetoObligado;
		var fisica = sujeto.fisica;
		var curp;
		
		if(fisica!=null && fisica!= undefined){
			curp=fisica.curp;
		}else{
			curp="SIN CURP";
		}
		
		if (curp == null || curp == 'null') {
			curp="SIN CURP";
		}
		
		return curp;
	} else {
		return "SIN CURP";
	}
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
			rfc="SIN RFC";
		}
		
		if (rfc == null || rfc == 'null') {
			rfc="SIN RFC";
		}
		
		return rfc;
	} else {
		return "SIN RFC";
	}
}

function renderDelegacionSubdelegacion(oObj) {
	var texto = '<ul class="listTramties">';
	texto += '<li>' + renderDelegacion(oObj) + '</li>';
	texto += '<li>' + renderSubdelegacion(oObj) + '</li>';
	texto += '</ul>';
	
	return texto;
}

function renderSubdelegacion(oObj) {
	
	var sol = oObj.aData; 
	
	if(sol.subdelegacion!= null && sol.subdelegacion!= undefined){
		return sol.subdelegacion.descripcion;
	}else{
		return "Por determinar";
	}
}

function renderDelegacion(oObj) {

	var sol = oObj.aData; 
	
	if(sol.subdelegacion != null && sol.subdelegacion != undefined){
		
		var subdelegacion = sol.subdelegacion;
		
		if(subdelegacion.delegacion !=null && subdelegacion.delegacion != undefined)
			return subdelegacion.delegacion.descripcion;
		else
			return "Por determinar";
	}else{
		return "Por determinar";
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

function abrirDocumentoResultante(idSolicitud, idTramite, idDocumentoPorTipo) {
	$('form#solicitudDocumentoForm input#idSolicitud').val(idSolicitud);
	$('form#solicitudDocumentoForm input#idTramite').val(idTramite);
	$('form#solicitudDocumentoForm input#tipoDocumento').val(idDocumentoPorTipo);
	$('form#solicitudDocumentoForm').submit();
}

function abrirDocumentoDeTramite(solicitud,folio,tipoDocumento){
	$('#solicitudDocumentoForm').attr('action', context_path + '/portlet/solicitudes/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento);
	$('#solicitudDocumentoForm').submit();
}

function abrirDocumentoTramite(solicitud,folio,tramite,tipoDocumento){
	$('#solicitudDocumentoForm').attr('action', context_path + '/portlet/solicitudes/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento);
	$('#solicitudDocumentoForm').submit();
}

function abrirAcuse(secuenciaNotaria) {
	$('#solicitudDocumentoForm').attr('action','${mvn.url.firmadigital}/reader/view/visualizadorcomprobantes/mostrarComprobante?id='+secuenciaNotaria+'');
	$('#solicitudDocumentoForm').submit();
}

function abrirCartaTerminos() {
	$('#cartaTerminos').attr('action','/gestionSolicitud-web/firma-digital/cartaTerminos');
	$('#cartaTerminos').submit();
}


function validaDatosRequeridos(){
	
	var valido=true;
	
	// Se checa que por lo menos uno de los filtros haya sido capturado
	var rfcCaptura = $('#rfc').val();
	var rpCaptura = $('#rp').val();
	var curpCaptura = $('#curp').val();
	var nssCaptura = $('#nss').val();
	var tipoTramiteCaptura = $('#tramiteId').val();
	var edoSolicitudCaptura = $('#idEstadoSolicitud').val();
	var fecInicioPresentacionCaptura = $('#fechaInicioPresentacion').val();
	var fecFinPresentacionCaptura = $('#fechaFinPresentacion').val();
	var fecInicioConclusionCaptura = $('#fechaInicioConclusion').val();
	var fecFinConclusionCaptura = $('#fechaFinConclusion').val();
	var idDelegacionCaptura = $('#idDelegacion').val();
	var idSubdelegacionCaptura = $('#idSubdelegacion').val();
	var idOrigenSolicitud = $('#idOrigenSolicitud').val();
	
	var capturaFiltro = false;
	
	if (!isBlank(rfcCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(rpCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(curpCaptura)) {
		capturaFiltro = true;
	}else if (!isBlank(nssCaptura)) {
		capturaFiltro = true;
	} else if (tipoTramiteCaptura != -1) {
		capturaFiltro = true;
	} else if (edoSolicitudCaptura != -1) {
		capturaFiltro = true;
	} if (!isBlank(fecInicioPresentacionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecFinPresentacionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecInicioConclusionCaptura)) {
		capturaFiltro = true;
	} else if (!isBlank(fecFinConclusionCaptura)) {
		capturaFiltro = true;
	} else if (idOrigenSolicitud != -1) {
		capturaFiltro = true;
	} else if (idDelegacionCaptura != -1) {
		if ($('#idDelegacionAux').length > 0) {
			/* 
			 * Significa que el usuario ya cuenta con, al menos, delegacion,
			 * por lo tanto, este valor no se debe tomar en cuenta para
			 * saber si capturo o no un filtro
			 */
			if (idSubdelegacionCaptura != -1 && idSubdelegacionCaptura != '') {
				if ($('#idSubdelegacionAux:enabled').length > 0) {
					capturaFiltro = true;
				}
			}
		} else {
			capturaFiltro = true;
		}
	}
	
	if (capturaFiltro) {
		if(!validaFormaFiltroSolicitud()) {
			valido=false;
			$('#divMsgErrores').show();
			// Para eliminar el primer <br> de los errores mostrados
			$('#msgError').children().first().children().first('br').remove();
			window.location.hash = '#divMsgErrores';
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

function validarFecha(fecha) {
	var isValid = false;
	var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;

	if (re.test(fecha)) {
		var adata = fecha.split('/');
		
		var dd = parseInt(adata[0], 10);
		var mm = parseInt(adata[1], 10);
		var yyyy = parseInt(adata[2], 10);
		
		var xdata = new Date(yyyy, mm - 1, dd);
		
		if ((xdata.getFullYear() == yyyy) && (xdata.getMonth() == mm - 1)
				&& (xdata.getDate() == dd)) {
			isValid = true;
		} else {
			isValid = false;
		}
	} else {
		isValid = false;
	}

	return isValid;
}

function validaFormaFiltroSolicitud(){
	
	var dateName = "";
	var msgErrorRangoFechaFinPresentacion = "";
	var msgErrorRangoFechaFinConclusion = "";
	
	var getMsgFechasInvalidas = function() {
		
		var msg = "<br>Por favor ingresa una fecha v\u00E1lida con el siguiente formato dd/mm/yyyy para el campo " + dateName;
		return msg; 
	};
	
	var getMsgRangoFinPresentacion = function() {
		return msgErrorRangoFechaFinPresentacion; 
	};
	
	var getMsgRangoFinConclusion = function() {
		return msgErrorRangoFechaFinConclusion; 
	};
	
	jQuery.validator.addMethod(
		"dateMX",
		function(value, element) {
			var check = validarFecha(value);
			
			if (!check) {
				dateName = $(element).attr('msgErrorElemntName');
			}
			
			return this.optional(element) || check;
		}, getMsgFechasInvalidas
	);
		
	jQuery.validator.addMethod(
		"fechaInicioPresentacionRequerida",
		function(){						
			var fecInicio = $("#fechaInicioPresentacion").val();
			var fecFin = $("#fechaFinPresentacion").val();
			
			if (validarFecha(fecFin)) {
				if (fecFin != "" && fecInicio == "") {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		},
		"<br>Por favor ingresa la Fecha Inicio para el rango Fecha de Presentaci&oacute;n"
	);
	
	jQuery.validator.addMethod(
		"fechaInicioPresentacionRango",
		function(){
			
			var fecInicio = $("#fechaInicioPresentacion").val();
			var fecFin = $("#fechaFinPresentacion").val();
			
			if (fecInicio != "" && fecFin != "") {
				if (validarFecha(fecInicio) && validarFecha(fecFin)) {
					var fecInicioAux = Date.parse(fecInicio.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
					var dateInicio = new Date(fecInicioAux);
					var fechaActual = new Date();
					
					if (dateInicio > fechaActual) {
						return false;
					} else {
						return true;
					}
				} else {
					/*
					 * Se regresa true en caso de que las fechas sean
					 * inv�lidas, porque la validaci�n anterior debi� cachar
					 * el error
					 */
					return true;
				}
			} else{
				return true;
			}
		},"<br>La Fecha Inicio para el rango Fecha de Presentaci&oacute;n no puede ser mayor a la fecha actual"
	);
	
	jQuery.validator.addMethod(
		"fechaFinPresentacionRequerida",
		function(){								
			var fecInicio = $("#fechaInicioPresentacion").val();
			var fecFin = $("#fechaFinPresentacion").val();
			
			if (validarFecha(fecInicio)) {
				if (fecInicio != "" && fecFin == "") {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		},
		"<br>Por favor ingresa la Fecha Fin para el rango Fecha de Presentaci&oacute;n"
	);	
	
	jQuery.validator.addMethod(
		"fechaFinPresentacionRango",
		function(){
			
			var fecInicio = $("#fechaInicioPresentacion").val();
			var fecFin = $("#fechaFinPresentacion").val();
			
			if (fecInicio != "" && fecFin != "") {
				if (validarFecha(fecInicio) && validarFecha(fecFin)) {
											
					var fecInicioAux = Date.parse(fecInicio.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
					var fecFinAux = Date.parse(fecFin.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
					var dateInicio = new Date(fecInicioAux);
					var dateFin = new Date(fecFinAux);
					var fechaActual = new Date();
					
					if (dateFin > fechaActual) {
						msgErrorRangoFechaFinPresentacion = "<br>La Fecha Fin para el rango Fecha de Presentaci&oacute;n no puede ser mayor a la fecha actual";
						return false;
					} else if (dateFin < dateInicio) {
						msgErrorRangoFechaFinPresentacion = "<br>La Fecha Fin para el rango Fecha de de Presentaci&oacute;n no puede ser menor a la Fecha Inicio";
						return false;
					} else {
						return true;
					}
				} else {
					/*
					 * Se regresa true en caso de que las fechas sean
					 * inv�lidas, porque la validaci�n anterior debi� cachar
					 * el error
					 */
					return true;
				}
			} else{
				return true;
			}
		},getMsgRangoFinPresentacion
	);
	
	jQuery.validator.addMethod(
		"fechaInicioConclusionRequerida",
		function(){
			var fecInicio = $("#fechaInicioConclusion").val();
			var fecFin = $("#fechaFinConclusion").val();
			
			if (validarFecha(fecFin)) {
				if (fecFin != "" && fecInicio == "") {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		},
		"<br>Por favor ingresa la Fecha Inicio para el rango Fecha de Conclusi&oacute;n"
	);
	
	jQuery.validator.addMethod(
			"fechaInicioConclusionRango",
			function(){														
				var fecInicio = $("#fechaInicioConclusion").val();
				var fecFin = $("#fechaFinConclusion").val();
				
				if (fecInicio != "" && fecFin != "") {
					if (validarFecha(fecInicio) && validarFecha(fecFin)) {
						var fecInicioAux = Date.parse(fecInicio.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
						var dateInicio = new Date(fecInicioAux);
						var fechaActual = new Date();
						
						if (dateInicio > fechaActual) {
							msgErrorRangoFechaConclusion = "<br>La Fecha Inicio para el rango Fecha de Conclusi&oacute;n no puede ser mayor a la fecha actual";
							return false;
						} else {
							return true;
						}
					} else {
						/*
						 * Se regresa true en caso de que las fechas sean
						 * inv�lidas, porque la validaci�n anterior debi� cachar
						 * el error
						 */
						return true;
					}
				} else{
					return true;
				}
				
			},"<br>La Fecha Inicio para el rango Fecha de Conclusi&oacute;n no puede ser mayor a la fecha actual"
		);
	
	jQuery.validator.addMethod(
		"fechaFinConclusionRequerida",
		function(){														
			var fecInicio = $("#fechaInicioConclusion").val();
			var fecFin = $("#fechaFinConclusion").val();
			
			if (validarFecha(fecFin)) {
				if (fecFin != "" && fecInicio == "") {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		},
		"<br>Por favor ingresa la Fecha Fin para el rango Fecha de Conclusi&oacute;n"
	);
	
	jQuery.validator.addMethod(
		"fechaFinConclusionRango",
		function(){														
			var fecInicio = $("#fechaInicioConclusion").val();
			var fecFin = $("#fechaFinConclusion").val();
			
			if (fecInicio != "" && fecFin != "") {
				if (validarFecha(fecInicio) && validarFecha(fecFin)) {
											
					var fecInicioAux = Date.parse(fecInicio.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
					var fecFinAux = Date.parse(fecFin.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3") );
					var dateInicio = new Date(fecInicioAux);
					var dateFin = new Date(fecFinAux);
					var fechaActual = new Date();
					
					if (dateFin > fechaActual) {
						msgErrorRangoFechaFinConclusion = "<br>La Fecha Fin para el rango Fecha de Conclusi&oacute;n no puede ser mayor a la fecha actual";
						return false;
					} else if (dateFin < dateInicio) {
						msgErrorRangoFechaFinConclusion = "<br>La Fecha Fin para el rango Fecha de de Conclusi&oacute;n no puede ser menor a la Fecha Inicio";
						return false;
					} else {
						return true;
					}
				} else {
					/*
					 * Se regresa true en caso de que las fechas sean
					 * inv�lidas, porque la validaci�n anterior debi� cachar
					 * el error
					 */
					return true;
				}
			} else{
				return true;
			}
			
		},getMsgRangoFinConclusion
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
		"<br>Por favor ingresa un N&uacute;mero de Registro Patronal v&aacute;lido. Debe contener 8, 10 u 11 caracteres."
	);
	
	jQuery.validator.addMethod(
			"formatoRFC",
			function(){
				var valorRFC = $("#rfc").val();
				
				if (valorRFC != null && valorRFC != ""){
					var regexRfcFisica = /^[a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u00D1\u00F1]{4}\d{6}[a-zA-Z\w]{3}$/;
					var regexRfcMoral = /^[a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u0026\u00D1\u00F1\u005F]{3}\d{6}[a-zA-Z0-9]{3}$/;
					
					if(!regexRfcFisica.test(valorRFC) && !regexRfcMoral.test(valorRFC)){
						return false;
					}else{
						return true;
					}
				}
				return true;
			},
			"<br>Por favor ingresa un RFC v&aacute;lido."
		);
	
	jQuery.validator.addMethod(
		"formatoCURP",
		function(){
			var valorCURP = $("#curp").val();
			
			if (valorCURP != null && valorCURP != ""){
				var regexCURP = /^([a-zA-Z]{4})\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$/;
									
				if(!regexCURP.test(valorCURP)){
					return false;
				}else{
					return true;
				}
			}
			return true;
		},
		"<br>Por favor ingresa un CURP v&aacute;lido."
	);
	
	jQuery.validator.addMethod(
			"formatoNSS",
			function(){
				var valorNSS = $("#nss").val();

				if (valorNSS != null && valorNSS != "") {
					if (valorNSS.length == 11) {
						return true;
					} else {
						return false;
					}
				}
				return true;
			},
			"<br>Por favor ingresa un NSS v&aacute;lido. Debe contener 11 caracteres."
		);
		
	$("#formBusquedaSolictiudes").validate({
		rules: {
			"rfc" : {
				formatoRFC : true
			},
			"rp" : {
				formatoNumeroRegistroPatronal : true
			},
			"curp" : {
				formatoCURP : true
			},
			"nss" : {
				formatoNSS : true
			},
			"fechaInicioPresentacion" : {
				dateMX : true,
				fechaInicioPresentacionRequerida : true,
				fechaInicioPresentacionRango : true,
			},
			"fechaFinPresentacion" : {
				dateMX : true,
				fechaFinPresentacionRequerida : true,
				fechaFinPresentacionRango : true
			},
			"fechaInicioConclusion" : {
				dateMX : true,
				fechaInicioConclusionRequerida : true,
				fechaInicioConclusionRango : true,
			},
			"fechaFinConclusion" : {
				dateMX : true,
				fechaFinConclusionRequerida : true,
				fechaFinConclusionRango : true,
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
	window.location.hash = '#divMsgErrores';
}

function ocultarErrorCaptura() {
	fnHideErrores('form#formBusquedaSolictiudes');
	$('#msgError').text('');
	$('#divMsgErrores').hide();
}





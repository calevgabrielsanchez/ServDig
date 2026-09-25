var idSolicitudActiva;
var folioSolicitudDatosPatronales;
var idSolicitudPreviaActiva;
var digitsOnly = "1234567890";
var noEsDigito = /[^0-9]/g;
var isOpRatificacion=false;
var sujetoObigadoTramite;
var error = false;
var checkedObject;
var aPos;
var aPosMediosContacto;
var accionSobreMediosContacto;

function validaSolicitudTramiteActivo(url, callbackAEjecutar){
	$.blockUI();
	inicializarSujetoTramiteParaValidacionDeSolicitudActiva();
	sendToServer(url,sujetoObigadoTramite,
			callbackAEjecutar, true);
}


function inicializarSujetoTramiteParaValidacionDeSolicitudActiva(){
	sujetoObigadoTramite = new Object();
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona=$("#fisica\\.idPersona").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
	}
}


function callbackValidacionTramiteActivo(response, funcionEjecturar, funcionCancelar){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Confirmaci&oacute;n... ";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
		}
		construirDialogoConfirmarTramite(mensaje, funcionEjecturar, funcionCancelar);
	}else if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error, navegarPaginaInicio, 200, 550);
	}
	$.unblockUI();
}

function navegarPaginaInicio(){
	if(isRL)
		navegarTo("/sujetoObligado", "busquedaRFCForm");
	else
		navegarTo("/afiliacion/visualizarDetalleRFC","formSupport");
}

function inicializaMensajeFolio(response){
	var folio = response.folio;
	if(response.idSolicitud!=undefined)
		idSolicitud = response.idSolicitud;
		
	if( folio != undefined){
		if($("#noFolioActual") != undefined ){
			$("#noFolioActual").html(folio);
		}
		if($("#infoFolio") != undefined ){
			$("#infoFolio").show();
		}
	}
}

function construirDialogoConfirmarTramite(mensaje, funcionEjecturar, funcionCancelar) {
	$("#textoConfirmacion").html(mensaje);
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 450,
		width : 650,
		title : "Confirmaci&oacute;n",
		close : function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( jQuery.isFunction(funcionCancelar) ) {
				    		funcionCancelar();
				    	}
				    }
		  		},
		buttons : {
			
			"Cancelar" : function() {
				if (jQuery.isFunction(funcionCancelar)) {
					funcionCancelar();
		    	}
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}



function construirDialogoConfirmar(accion, funcionEjecturar) {
	$("#textoConfirmacion").html(
			"&iquest;Esta seguro que desea " + accion + " la solicitud?");
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Cancelar" : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoConfirmarComun(mensaje, funcionEjecutar) {
	$("#textoConfirmacion").html(mensaje);
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Cancelar" : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				funcionEjecutar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function sendToServer(url, objeto, callback, async) {
	if (async == undefined) {
		async = true;
	}
	var sSource = context + url;
	if (callback == undefined) {
		prepararRequest(sSource, objeto, async, procesarRespuestaServer);
	} else {
		prepararRequest(sSource, objeto, async, callback);
	}
}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function procesarRespuestaServer(response, callback, dialogoHeight, dialogoWidth, closeCallback, requiereBotonCancelar) {
	var mensaje = "";
	var titulo = "";
	error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
			if(response.idSolicitud!=undefined){
				idSolicitudActiva = response.idSolicitud;
				idSolicitudPreviaActiva = response.idSolicitud;
			}
		} else {
			mensaje = response.mensajeExito;
			if(response.idSolicitud!=undefined){
				idSolicitudActiva = response.idSolicitud;
				idSolicitudPreviaActiva = response.idSolicitud;
			}
		}
		if(requiereBotonCancelar != undefined && requiereBotonCancelar){
			construirDialogoDeSolicitudDeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth, closeCallback);
		}else{
			construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth, closeCallback);
		}
		
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth);
	}
	return false;
}

function callbackEnviarTramite(response, dialogoHeight, dialogoWidth) {
	procesarRespuestaServer(response, undefined, dialogoHeight, dialogoWidth);
	inicializaMensajeFolio(response);
}
 

function navegarTo(url, formId){
	
	document.getElementById(formId).action = context + url;
	document.getElementById(formId).submit();
}

function inicializaEstiloGrid(tbodyObj, gridObject) {
	
	/* Add a click handler to the rows - this could be used as a callback */
	tbodyObj.click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
		$(gridObject.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar){
			$(event.target.parentNode).addClass('row_selected');
			aPos = gridObject.fnGetPosition(event.target.parentNode);
		}
	});
}

/*
* Se crea este segmento de inicializaci�n ya que con el anterior (inicializaEstiloGrid) se daba
* un conflicto al manejar v�rios datatables en la variable aPos, este segmento aplica para los
* modulos de representante legal y socios
*/
function inicializaEstiloGridParaMediosContacto(tbodyObj, gridObject) {
	
	/* Add a click handler to the rows - this could be used as a callback */
	tbodyObj.click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
		$(gridObject.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar){
			$(event.target.parentNode).addClass('row_selected');
			aPosMediosContacto = gridObject.fnGetPosition(event.target.parentNode);
		}
	});
}

function construirDialogoGenerico(divId, objDialogo, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	
	if(height == undefined){
		height='auto';
	}
	if(width == undefined){
		width=400;
	}
	
	objDialogo = $(divId).dialog({
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

function construirDialogoGenericoMsjConfirmacion(divId, objDialogo, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensajeConfirmacion").html(mensaje);
	$("#textoMensajeConfirmacion").removeAttr("style");
	if (error) {
		$("#textoMensajeConfirmacion").attr("style", "color: red;");
	} else {
		$("#textoMensajeConfirmacion").attr("style", "color: black;");
	}
	
	if(height == undefined){
		height='auto';
	}
	if(width == undefined){
		width=400;
	}
	
	objDialogo = $(divId).dialog({
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


function validarNumeros(event) {
	// Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right
	// arrow, delete, shift, ini, fin
 	event = event || window.event;
    if(!event.ctrlKey 
    && !event.metaKey 
    && !event.altKey 
    && event.keyCode != 8
	&& event.keyCode != 9
	&& event.keyCode != 46
    && !(event.keyCode >  34 && event.keyCode <  40)) {
        var charCode = (typeof event.which == "undefined") ? event.keyCode : event.which;
        if(charCode < 96 || charCode > 105){
	        if (charCode && digitsOnly.indexOf(String.fromCharCode(charCode)) < 0) {
	        	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
	            return false;
	        }
        }
    }
    if(event.shiftKey){
    	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
        return false;
    }
    if (event.ctrlKey && event.altKey) {
        // Appears to be Alt Gr
    	(event.preventDefault) ? event.preventDefault() : event.returnValue = false; 
        return false;
    }
/* */
};


function construirDialogoTramiteMensajeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth, closeCallback) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	if(dialogoHeight== undefined)
		dialogoHeight = 300;
	if(dialogoWidth== undefined)
		dialogoWidth = 450;
	
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height: dialogoHeight, width: dialogoWidth,
		title : titulo,
		close: function(){
			if (jQuery.isFunction(closeCallback)) {
				closeCallback();
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
	dialogo.dialog('open');
}

function deseleccionarCheck(){
	checkedObject.removeAttr("checked");
	checkedObject=null;
}


function construirDialogoParaPagina(divId, objDialogo, titulo, error, callback, callbackForXButton, height, width) {
	if(height==undefined)
		height = 150;
	if(width == undefined)
		width = 400;
	objDialogo = $(divId).dialog({
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
			
			"Cancelar" : function(){
				$(this).dialog("close");
			},
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


function construirDialogoGenericoDeConfirmacion(divId, objDialogo, titulo, mensaje, error, callback, callbackForXButton, pHeight, pWidth) {
	if(pHeight==undefined)
		pHeight = 150;
	if(pWidth == undefined)
		pWidth = 400;
	
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : pHeight,
		width : pWidth,
		title : titulo,
		close: function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
				    		callbackForXButton();
				    	}
				    }
		  		},
		buttons : {
			"Cancelar" : function(){
				$(this).dialog("close");
			},
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

function construirDialogoDeSolicitudDeConfirmacion(titulo, mensaje, error, callback, dialogoHeight, dialogoWidth, closeCallback) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	if(dialogoHeight== undefined)
		dialogoHeight = 300;
	if(dialogoWidth== undefined)
		dialogoWidth = 450;
	
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height: dialogoHeight, width: dialogoWidth,
		title : titulo,
		close: function(){
			if (jQuery.isFunction(closeCallback)) {
				closeCallback();
			}
		},
		buttons : {
			"Cancelar" : function(){
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function validarDatosContacto(campoTipoContacto, campoDescripcion, campoTipoContactoError, campoDescripcionError){
		fnOcultaErrores(campoTipoContactoError);
		fnOcultaErrores(campoDescripcionError);
		var cveTipoMedioContacto = campoTipoContacto.val();
		var desFormaContacto = campoDescripcion.val();
		
		if((cveTipoMedioContacto != undefined && cveTipoMedioContacto != null && cveTipoMedioContacto != "" && cveTipoMedioContacto > 0) || 
				(desFormaContacto != undefined && desFormaContacto != null && desFormaContacto != "")){
			
			if(cveTipoMedioContacto != undefined && cveTipoMedioContacto != null 
					&& cveTipoMedioContacto != "" && cveTipoMedioContacto > 0){
						if(desFormaContacto != undefined && desFormaContacto != null 
						&& desFormaContacto != ""){
							if(desFormaContacto.length <= 255){
								var expReg = "";
								switch(cveTipoMedioContacto){
								case "1":							
									var valCorreo = fnValidaCorreo(desFormaContacto);
									if (!valCorreo) {						
										this.fnDespliegaError(campoDescripcionError, "La descripci&oacute;n no cuenta con un formato correcto de correo<br>");
										return false;
									}
									if (desFormaContacto.length > 150) {
										this.fnDespliegaError(campoDescripcionError, "La longitud m&aacute;xima del campo descripci&oacute;n es de 150 caracteres");
										return false;
									}
									break;
								case "2":
									var valTelefono = fnValidaSoloNumeros(desFormaContacto);
									if (!valTelefono) {
										this.fnDespliegaError(campoDescripcionError, "La descripci&oacute;n no cuenta con un formato correcto de tel&eacute;fono(Solo permite n&uacute;meros)");
										return false;
									}
									if (desFormaContacto.length > 12) {
										this.fnDespliegaError(campoDescripcionError, "La longitud m&aacute;xima del campo descripci&oacute;n es de 12 caracteres");
										return false;
									}
									break;
								case "3":
									var valTelefono = fnValidaSoloNumeros(desFormaContacto);
									if (!valTelefono) {
										this.fnDespliegaError(campoDescripcionError, "La descripci&oacute;n no cuenta con un formato correcto de tel&eacute;fono(Solo permite n&uacute;meros)");
										return false;
									}
									if (desFormaContacto.length > 12) {
										this.fnDespliegaError(campoDescripcionError, "La longitud m&aacute;xima del campo descripci&oacute;n es de 12 caracteres");
										return false;
									}
									break;
								case "4":
									var valFacebook = fnValidaFacebook(desFormaContacto);
									if (!valFacebook) {
										this.fnDespliegaError(campoDescripcionError, "La direcci&oacute;n de facebook no cuenta con un formato correcto.");
										return false;
									}
									if (desFormaContacto.length > 50) {
										this.fnDespliegaError(campoDescripcionError, "La longitud m&aacute;xima del campo descripci&oacute;n es de 50 caracteres");
										return false;
									}
                                    var _desc = campoDescripcion.val().replace(/^\s+|\s+$/g, '');
                                    campoDescripcion.val();
									break;
								case "5":
									var valTwitter = fnValidaTwitter(desFormaContacto);							
									if (!valTwitter){
										this.fnDespliegaError(campoDescripcionError, " La descripci&oacute;n debe cumplir con un formato correcto de twitter: @nombre_usuario (hasta 15 car\u00E1cteres, letras, n\u00FAmeros y _ )");
										return false;
									}
									if (desFormaContacto.length > 50) {
										this.fnDespliegaError(campoDescripcionError, "La longitud m&aacute;xima del campo descripci&oacute;n es de 50 caracteres");
										return false;
									}
                                    var _desc = campoDescripcion.val().replace(/^\s+|\s+$/g, '');
                                    campoDescripcion.val();
									break;
								default:
									expReg = "/\w{1,255}/";
									break;
								}

							}else{
								this.fnDespliegaError(campoDescripcionError, "La longitud m�xima del campo descripci&oacute;n es de 255 caracteres");
								return false;
							}
						}else{
							this.fnDespliegaError(campoDescripcionError, "Debe agregar un valor en la descripci&oacute;n del medio de contacto");
							return false;
						}
					}else{
						this.fnDespliegaError(campoTipoContactoError, "Debe seleccionar un tipo de medio de contacto");
						return false;
					}
		}else{
			this.fnDespliegaError(campoDescripcionError, "Debe agregar un valor en la descripci&oacute;n del medio de contacto");
			this.fnDespliegaError(campoTipoContactoError, "Debe seleccionar un tipo de medio de contacto");
			return false;
		}
		
	return true;
}


function fnOcultaErrores(campoError){
		var nClassShow = 'showElement';
		var nClassHidden ='hiddenElement';
//		var campo = "#"+nombre+"Err";
		campoError.removeClass(nClassShow);
		campoError.addClass(nClassHidden);
		campoError.html();
}
	
function fnDespliegaError(campoError , mensajeError){
		var nClassShow = 'showElement';
		var nClassHidden ='hiddenElement';
		campoError.removeClass(nClassHidden);
		campoError.addClass(nClassShow);
		campoError.html(mensajeError);
};


function fnValidaCorreo(campo){		
	var result = false;
	var exr = /^\w+([\.-]?\w+)*@[0-9a-z\-\.]+\.[a-z]{1,4}$/i;

	if(exr.test(campo)){
		result = true;
	  }
	  
	 return result;
}

function fnValidaSoloNumeros(campo) {		
    var valida = campo.replace(/[^0-9()]*/gi,"");
    if (campo.length==valida.length) {
    	return true
    }
    return false;
}

function fnValidaTwitter(campo){		
	var result = false;
    var _campo = campo.replace(/^\s+|\s+$/g, '');
	var exr = /^(\@)[A-Za-z0-9_]{1,15}$/;
	if(exr.test(_campo)){
		result = true;
	}
	  
	 return result;
}

function fnValidaFacebook(campo){		
	var result = false;
//	var exr = /^(http\:\/\/|https\:\/\/)?(?:www\.)?facebook\.com\/(?:(?:\w\.)*\/)?(?:pages\/)?(?:[\w\-\.]*\/)*([\w\-\.]+)+$/;
	var exr = /^(http\:\/\/|https\:\/\/)?(?:www\.)?facebook\.com\/(?:(?:\w\.)*\/)?(?:pages\/)?(?:[\w\-\.]*\/)*([\w\-\.]+)+$/;
    var _campo = campo.replace(/^\s+|\s+$/g, '');
	if(exr.test(_campo)){
		result = true;
	  }
	 return result;
}

function fnValidaFormatoFecha(d) {
	if ( Object.prototype.toString.call(d) !== "[object Date]" ){
	    return false;
	}
	return !isNaN(d.getTime());
}

function ajustarTamanoDeCampoDescripcionPorTipo(campoTipo, campoDescripcion){
	if ($(campoTipo).val()== 1 )
		ajustarTamanoDeCampo(campoDescripcion,60);
	else if($(campoTipo).val()== 2 )
		ajustarTamanoDeCampo(campoDescripcion,12);
	else if($(campoTipo).val()== 3 )
		ajustarTamanoDeCampo(campoDescripcion,13);
	else if($(campoTipo).val()== 4 ){
		ajustarTamanoDeCampo(campoDescripcion,100);
		if($(campoDescripcion).val()=='')
			$(campoDescripcion).val("facebook.com/");
	}else if($(campoTipo).val()== 5 )
		ajustarTamanoDeCampo(campoDescripcion,100);
}

function ajustarTamanoDeCampo(nombreElemento,size){
	$(nombreElemento).attr("maxlength",size);
}

function asignaFuncionAEvento(evento, idCampo, funcion){
	switch(evento){
		case 'onkeypress':
			document.getElementById(idCampo).onkeypress = funcion;
			break;
		case 'onkeydown': 
			document.getElementById(idCampo).onkeydown = funcion;
			break;
		case 'onfocus':
			document.getElementById(idCampo).onfocus = funcion;
			break;
		case 'onblur':
			document.getElementById(idCampo).onblur = funcion;
			break;
	}
}

function inhabilitarEnterKey(event){
	var key;      
    if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox      
        
    return (key != 13);
}

function validaMediosContactoRequeridos(mediosContactoList){
	// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
	var contadorDatosContactoRequeridos = 0;
	
	if (mediosContactoList.length > 0){
		for (i=0 ; i < mediosContactoList.length ; i++){
			if ((mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == 1 && mediosContactoList[i].desFormaContacto != "")
				|| (mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == 2 && mediosContactoList[i].desFormaContacto != "")
				|| (mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == 3 && mediosContactoList[i].desFormaContacto != "")){ // implementar exp reg para correo-e
				contadorDatosContactoRequeridos++;
			}
		}
		
		if (contadorDatosContactoRequeridos == 0){
			return false;
			//alert('Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico v\u00E1lidos.');
		} 
		return true;
	}
}

function validaMediosContactoRequeridosDeRFC(mediosContactoList){
	// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
	var correoPresente = false;
	var telefonoFijoPresente = false;
	var telefonoMovilPresente = false;
	
	if (mediosContactoList.length > 0){
		
		for (i=0 ; i < mediosContactoList.length ; i++){
			if (mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == idTipoMedioContactoEmail && mediosContactoList[i].desFormaContacto != ""){
				correoPresente = true;
			} else if (mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == idTipoMedioContactoTelefonoFijo && mediosContactoList[i].desFormaContacto != ""){
				telefonoFijoPresente = true;
			} else if (mediosContactoList[i].tipoMedioContacto.idTipoMedioContacto == idTipoMedioContactoTelefonoMovil && mediosContactoList[i].desFormaContacto != ""){
				telefonoMovilPresente = true;
			}
		}

		if (!((telefonoFijoPresente || telefonoMovilPresente) && correoPresente)){
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "De capturar al menos un n\u00FAmero de tel\u00E9fono (fijo o m\u00F3vil) y un correo electr\u00F3nico.", true, undefined, undefined, 150, 450)
			return false;
		} 
		return true;
	} else {
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "De capturar al menos un n\u00FAmero de tel\u00E9fono (fijo o m\u00F3vil) y un correo electr\u00F3nico.", true, undefined, undefined, 150, 450)
		return false;
	}
}

function validaSeleccionEnGrid(grid){
	var obRowSelected = fnGetRowSelected(grid);
	if (obRowSelected == undefined) {
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe seleccionar el registro patronal que desea eliminar", true, undefined, undefined, 150, 450);
	}else{
		return true;
	}
}

function getSelectedRadioButton(radioGroup){
	for(var i=0; i<radioGroup.length; i++){
		if(radioGroup[i].checked)
			return radioGroup[i];
	}
}

function obtenerDetalleRepresentante(){
	var idSolicitante=$("#solicitadoPorForm #idRepresentante").val();
	var url = "/afiliacion/obtenerDetalleDePersona";
	sendToServer(url, idSolicitante, callbackDetalleRepresentante,
			false);
}

function callbackDetalleRepresentante(response){
	$("#detalleRepresentanteNombre").text(response.persona.nombre + ' ' + response.persona.primerApellido+' '+response.persona.segundoApellido);
	$("#detalleRepresentanteRFC").text(response.persona.rfc);
	$("#detalleRepresentanteCURP").text(response.persona.curp);
}

function callbackValidacionSeleccionadoPor(response){
	var oDialogo;
	construirDialogoParaPagina("#formaSolicitadoPor", oDialogo, "Especificar Solicitante", false, 
			callbackSolicitadoPor, undefined, 250, 600);
}

function inicializaFecha(campoId, diasPosteriores){
	$(campoId).datepicker({
		maxDate: diasPosteriores,
//		showOn: "button",
//		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
//		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
}

function inicializaMenuLateral(){
	$('#menuLateral li a').click(function(event){
		 var elem = $(this).next();
		 if(elem.is('ul')){
		 event.preventDefault();
		 $('#menuLateral ul:visible').not(elem).slideUp();
		 elem.slideToggle();
		 }
	});
}

function setUpAccordionComponent(){
	$("#accordion").accordion();
}

function obtenerRepresentantesEnTramite(){
	var solicitud = new Object();
	solicitud.sujetoObligado = new Object();
	solicitud.solicitudId=idSolicitudActiva;
	solicitud.sujetoObligado.tipoPersonaFiscal=tipoPersonaFiscal;
	if(esPatronFisico){
		solicitud.sujetoObligado.fisica = new Object();
		solicitud.sujetoObligado.fisica.idPersona=$("#fisica\\.idPersona").val();
		solicitud.sujetoObligado.fisica.tipoPersona = new Object();
		solicitud.sujetoObligado.fisica.tipoPersona.idTipoPersona=idTipoPersonaFisica;
	}else{
		solicitud.sujetoObligado.moral = new Object();
		solicitud.sujetoObligado.moral.idPersona=$("#moral\\.idPersona").val();
		solicitud.sujetoObligado.moral.tipoPersona = new Object();
		solicitud.sujetoObligado.moral.tipoPersona.idTipoPersona=idTipoPersonaMoral;
	}
		
	sendToServer('/afiliacion/obtenerRepresentantesDisponibles?esPatronFisico='+esPatronFisico,
			solicitud,
			callbackObtenerRepresentantesEnTramite, false);
}

function callbackObtenerRepresentantesEnTramite(response){
	var options = document.getElementById('idRepresentante').options;
	options.length = 0;
	options[options.length] = new Option('-- Seleccione --', '-1', true, true);
	
	for(indexRepresentante in response.representantesDisponibles){
		options[options.length] = new Option(
				response.representantesDisponibles[indexRepresentante].personaFisica.nombre+" "+
				response.representantesDisponibles[indexRepresentante].personaFisica.primerApellido+" "+
				response.representantesDisponibles[indexRepresentante].personaFisica.segundoApellido, 
				response.representantesDisponibles[indexRepresentante].personaFisica.idPersona);
	}
	
	var oDialogo;
	construirDialogoParaPagina("#formaSolicitadoPor", oDialogo, "Especificar Solicitante", false, 
			callbackSolicitadoPor, undefined, 250, 600);
}

$(document).ready(function() {	

document.charset = 'UTF-8';

$.fn.domicilioRecortado.mostrarMensajeError = function(mensaje) {
		var divMensajes = $.fn.domicilioRecortado.defaults.divMensajes != '' ? ('#'+$.fn.domicilioRecortado.defaults.divMensajes) : '<div></div>';
		var mensajeError =  $(''+divMensajes);
		mensajeError.html(mensaje);
		mensajeError.dialog({
			autoOpen : false,
			title: 'Mensaje de sistema',
			resizable: false,
			closeOnEscape: false,
			modal: true,
			heigth: 'auto',
			width: 'auto',
			buttons: {"Aceptar" : function() { $(this).dialog("close");}}
		}
		)
		mensajeError.dialog('open');
	};
	
	$.fn.domicilioRecortado.muestraMensajeSinAsentamientos = function () {
		mensajeNoError();
	};
	
	$.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS = "El C\u00F3digo Postal es inv\u00E1lido o no existe registrado en el cat\u00E1logo de SEPOMEX. ";
	//inicializamos el componente de domicilio recortado
	$("#componenteDomicilio").domicilioRecortado({
		funcionSinDatos: mensajeNoError,
		mostrarMensajeCaptura: false,
		mostrarFormulario: true,
		mostrarTitulosDialogs: true,
		onReady:setDomicilio,
		funcionLimpiar:limpiarCampo,
		funcionOk:getAsentamientoPorCodigo,
		mostrarMesajeRequeridos: false
		
	});
	
	
	//$("#domicilio\\.codigoPostal\\.codigoPostal").live('blur', function() {
	//	getAsentamientoPorCodigo(true);
	//});	
	
	
	
	$("#continuar").click(continuar);
	
	$("#datosIncorrectos").click(cancelar);
	
	$('#regresar').click(regresarPaginaAnterior);
	
	$("#cancelar").click(cancelar);	
	
	verificarChecks("motivoAclaracionVO.motivosAclaracionInfonavit4","motivoAclaracionVO.creditoDescontado");
	verificarChecks("checkOtro","motivoAclaracionVO.especificacion");
	
	$("#subdelegacion\\.clave").change(function() {
		var nClassShow = 'showElement';
		var nClassHidden ='hiddenElement';
		$('#subdelegacion\\.idError').removeClass(nClassShow);
		$('#subdelegacion\\.idError').addClass(nClassHidden);
		$('#subdelegacion\\.idError').text();
		$('#subdelegacion\\.clave').css("border","");
		$('#subReq').css({"color":"black"});
	});
	
	$('#motivoAclaracionVO\\.especificacion').change(function() {
		$("[id='motivoAclaracionVO\\.especificacion']").css("border","");
		$("[id='motivoAclaracionVO.especificacionError']").removeClass(nClassShow);
		$("[id='motivoAclaracionVO.especificacionError']").addClass(nClassHidden);
	});
	
	$('#motivoAclaracionVO\\.creditoDescontado').change(function() {
		$("[id='motivoAclaracionVO\\.creditoDescontado']").css("border","");
		$("[id='motivoAclaracionVO.creditoDescontadoError']").removeClass(nClassShow);
		$("[id='motivoAclaracionVO.creditoDescontadoError']").addClass(nClassHidden);
	});
	
	var ischeckedOtro= $("#checkOtro").is(':checked');
	var ischeckedDescuento= $("#motivoAclaracionVO\\.motivosAclaracionInfonavit4").is(':checked');
                    
	if(ischeckedOtro){
		$("#motivoAclaracionVO\\.especificacion").prop( "disabled", false );
	}
	
	if(ischeckedDescuento){
		$("#motivoAclaracionVO\\.creditoDescontado").prop( "disabled", false );
	}
	
	
});

function regresarPaginaAnterior (e) {
	document.charset = "ISO-8859-1";
	e.preventDefault();
	window.location.href = context_path  + '/wizard/correccionDatosAsegurado/obtenerInformacionRenapo?r=true';
}

function mensajeNoError(){	
	$.fn.domicilioRecortado.mostrarMensajeError($.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS.replace("%",$("[id='domicilio.codigoPostal.codigoPostal']").val()));	
}

function setDomicilio(){	
	$('#domicilio\\.codigoPostal\\.codigoPostal' ).val(validaAtributoVacio($('#codigoPostal').val()));
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(validaAtributoVacio($('#estado').val()));
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val(validaAtributoVacio($('#municipio').val()));
	$('#domicilio\\.calle').val(validaAtributoVacio($('#calle').val()));
	$('#domicilio\\.numExteriorAlf').val(validaAtributoVacio($('#numeroExterior').val()));
	$('#domicilio\\.numInteriorAlf').val(validaAtributoVacio($('#numeroInterior').val()));
	if(validaAtributoVacio($("#asentamientoClaveCatalogo").val())!=""){
		$('#domicilio\\.asentamiento\\.clave').html("");
		var optionDomicilios = "<option value='-1'>--Por favor seleccione--</option>";
		optionDomicilios += "<option value='"+$("#asentamientoClaveCatalogo").val()+"'>"+$("#asentamientoClave").val()+"</option>";
		$('#domicilio\\.asentamiento\\.clave').append(optionDomicilios);
		$('select[id="domicilio.asentamiento.clave"] option').last().prop('selected','true');
	}
	
	if(validaAtributoVacio($("[id='subdelegacionId']").val())!=""){
		$("[id='subdelegacion.clave']").html("");
		var optionDomicilios = "<option value='-1'>--Por favor seleccione--</option>";
		optionDomicilios += "<option value='"+$("[id='subdelegacionId']").val()+"'>"+$("[id='subdelegacionClave']").val()+"-"+$("#subdelegacion").val()+"</option>";
		$("[id='subdelegacion.clave']").append(optionDomicilios);
		$('select[id="subdelegacion.clave"] option').last().prop('selected','true');
	    $("#subdelegacion\\.clave").prop( "disabled", true );
	}

	//$('#domicilio\\.codigoPostal\\.codigoPostal').focus();
	
}

function limpiarCampo(){
	var nClassShow = 'showElement';
	var nClassHidden ='hiddenElement';
	$('select#subdelegacion\\.clave').html("");
	var optionSeleccione = "<option value='-1'>--Por favor seleccione--</option>";
	$("select#subdelegacion\\.clave").append(optionSeleccione);

	$('#subdelegacion\\.idError').removeClass(nClassShow);
	$('#subdelegacion\\.idError').addClass(nClassHidden);
	$('#subdelegacion\\.idError').text();	
	$('#subdelegacion\\.clave').css("border","");
	$("#subdelegacion\\.clave").prop( "disabled", false );
	limpiarMotivo();
}

function limpiarMotivo(){
	var nClassShow = 'showElement';
    var nClassHidden ='hiddenElement';
	$('#aclaracion').css({"color":"black"});
	$('#motivoAclaracionVO\\.otroError').removeClass(nClassShow);
	$('#motivoAclaracionVO\\.otroError').addClass(nClassHidden);
	$('#motivoAclaracionVO\\.otroError').text();
	$("[id='motivoAclaracionVO\\.creditoDescontado']").css("border","");
	$("[id='motivoAclaracionVO.creditoDescontadoError']").removeClass(nClassShow);
	$("[id='motivoAclaracionVO.creditoDescontadoError']").addClass(nClassHidden);
	$("[id='motivoAclaracionVO\\.especificacion']").css("border","");
	$("[id='motivoAclaracionVO.especificacionError']").removeClass(nClassShow);
	$("[id='motivoAclaracionVO.especificacionError']").addClass(nClassHidden);	
	$('#subReq').css({"color":"black"});
}

function getAsentamientoPorCodigo() {

	var codigo = $("#domicilio\\.codigoPostal\\.codigoPostal")
			.val();
	$('#domicilio\\.calle').val("");
	$('#domicilio\\.numExteriorAlf').val("");
	$('#domicilio\\.numInteriorAlf').val("");
	var url;
	var otros_parametros;

	url = context_path + "/wizard/correccionDatosAsegurado/asentamiento/get/codigoPostal";
	otros_parametros = {
		'codigo' : codigo
	};

	$
			.ajax({
				type : "POST",
				url : url,
				dataType : 'json',
				data : otros_parametros,
				beforeSend : function() {
					$('form#formCodigoPostal img#cveAsentamientoImgCargando')
							.show();
				},
				success : function(data) {
					spanHideErrores("codigoPostalError");
					limpiarCampo();
					if(data.subdelegaciones != null && data.subdelegaciones != undefined){
						setSubdelegaciones(data.subdelegaciones, "select#subdelegacion\\.clave");
					}
					
					mostrarMismaCP(data.codigoPostalMismaSubdelegacion, listaSubdelegaciones(data.subdelegaciones));
				},
				complete : function() {
					$('form#formCodigoPostal img#cveAsentamientoImgCargando').hide();
				}
				
			});
			
}

function mostrarMismaCP(codigoPostalMismaSubdelegacion, lista){
	$.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS_DE_CP = lista;
	if(codigoPostalMismaSubdelegacion == false){
	$.fn.domicilioRecortado.mostrarMensajeError($.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS_DE_CP);	
	}
	
}

function listaSubdelegaciones(subdelegaciones){
	var lista = lista = "El C\u00F3digo Postal del domicilio del solicitante "+$("[id='domicilio.codigoPostal.codigoPostal']").val()+", corresponde a la(s) subdelegaci\u00F3n(es): <br> ";
	for (var i = 0; i < subdelegaciones.length-1; i++) {
	 lista+= subdelegaciones[i].clave +"-"+ subdelegaciones[i].descripcion
		if(i!= subdelegaciones.length-2){
			lista+=", ";
		}
	
	}
	
	lista+=" <br> Si eliges continuar con el registro de la solicitud, \u00E9sta ser\u00E1 asignada a tu subdelegaci\u00F3n para atenci\u00F3n procedente.";
 return lista;	
}


		
	

function setSubdelegaciones(subdelegaciones, select) {
	var options = "<option value='-1'>--Por favor seleccione--</option>";
	
	$("#subdelegacion\\.clave").prop( "disabled", false );
	
	for (var i = 0; i < subdelegaciones.length; i++) {
		
		options += "<option value='" + subdelegaciones[i].id + "'>"
				+ subdelegaciones[i].clave +"-"+ subdelegaciones[i].descripcion+"</option>";
	}
	$("" + select).html(options);
	
	$('select[id="subdelegacion.clave"] option').last().prop('selected','true');
	$('select[id="subdelegacion.clave"]').prop('disabled','true');
	$('#subReq').css({"color":"black"});
}

var obtenerValoresCheck = function(name){
	var motivo = null;
	$("input[name='"+name+"']").each( function () {
		if($(this).prop('checked') == true){
			if(motivo === null){
			motivo=[];
			}
			motivo.push($(this).val());
		}
	});
	return motivo;
}

var obtenerValorCheckOtro = function(){
var registroOtro="";
	if($("#checkOtro").is(':checked')){
		var registroOtro = $("#checkOtro").val();
	}
	return registroOtro; 
}

var continuar = function() {
	$('#aclaracion').css({"color":"black"});
	var domicilioCapturado = $("#componenteDomicilio").domicilioRecortado("get");
	var auxDescripcion = $('#subdelegacion\\.clave :selected').text().split("-");
	var descripcion = auxDescripcion[auxDescripcion.length - 1];
	var clave = auxDescripcion[0];
	var id = $('#subdelegacion\\.clave').val();
	$('#subdelegacion').val(descripcion);
	$('#subdelegacionClave').val(clave);
	$('#subdelegacionId').val(id);
	var registroCreditoDescontado = $("#motivoAclaracionVO\\.creditoDescontado").val();	
	var registroEspecificacion = $("#motivoAclaracionVO\\.especificacion").val();
	
	if(domicilioCapturado != null) {
		setearDomicilio(domicilioCapturado);		
	}else if (id == null || typeof id === 'undefined') {
		$('#subReq').css({"color":"red"});
	}
	
	fnHideErrores("form#formCodigoPostal");
	fnHideErroresInput("form#formCodigoPostal");
	
	$.blockUI();
	$
			.ajax({
				url : context_path
						+ '/wizard/correccionDatosAsegurado/validar/domicilioCorto',
				type : 'post',
				async : false,
				dataType : 'json',
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify({
					subdelegacion : {
						id:id						
					},
					motivoAclaracionVO:{
						motivosAclaracionIMSS : obtenerValoresCheck("motivoAclaracionVO.motivosAclaracionIMSS"),
						motivosAclaracionInfonavit : obtenerValoresCheck("motivoAclaracionVO.motivosAclaracionInfonavit"),
						motivosAclaracionAfore : obtenerValoresCheck("motivoAclaracionVO.motivosAclaracionAfore"),
						creditoDescontado : registroCreditoDescontado,
						otro : obtenerValorCheckOtro(),
						especificacion:registroEspecificacion
					}
				}),
				success : function(response) {
					$("#formCodigoPostal").attr("action","capturarDomicilio");
					//$(this).dialog("close");
					document.charset = "ISO-8859-1";
					if(domicilioCapturado != null) {
						$("#formCodigoPostal").submit();
					}
					$.unblockUI();
					
				},
				error : function(error) {
					fnProcesarErrores(error,"form#formCodigoPostal");
					marcarCamposErrorFormDom();
					var objErrores = jQuery.parseJSON(error.responseText);
					 for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
						  var campo = objErrores.erroresCaptura[index].campo;						  
						  if(campo=='motivoAclaracionVO.otro'){
							 $('#aclaracion').css({"color":"red"});
						  }
					}
					$.unblockUI();
				}
			});
	$.unblockUI();
	
};

var mostrarMensajeConfirmacion = function() {
	var buttons = {
		"Cancelar" : function() {$(this).dialog("close");},
		"Aceptar" : function() {
			$("#formCodigoPostal").attr("action","capturarDomicilio");
			$(this).dialog("close");
			$("#formCodigoPostal").submit();
		}
	};
	
	//mostramos el mensaje
//	crearDialogo("A continuaci&oacute;n se asignar&aacute; su NSS, &iquest; Est&acute; seguro que desea continuar?", "Confirmaci&oacute;n requerida", buttons);
};

var cancelar = function() {
	
	var buttons = {
		"Cancelar" : function() {$(this).dialog("close");},
		"Aceptar" : 
			function() {
						 var url = context_path
					      + '/wizard/correccionDatosAsegurado/cancelarSolicitud';
		   
						 $('form#formCodigoPostal').attr('action', url);
						 $('form#formCodigoPostal').submit();
					}
	};
	//mostramos el mensaje
	crearDialogo("&iquest; Est\u00e1 seguro que desea salir? Se perder\u00e1 la informaci\u00F3n capturada", "Atenci\u00F3n", buttons);
};

var crearDialogo = function(mensaje, titulo, buttons) {
	
	$divMensajes = $( "#mensajes" );
	$divMensajes.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		title: titulo,
		autoOpen: false,
	    closeOnEscape: false,
		buttons: buttons
	 });
	
	$divMensajes.html(mensaje);
	$divMensajes.dialog('open');
};

var setearDomicilio = function(objetoDomicilio) {
	$formularioDomicilio = $("#formCodigoPostal");
	
	if (objetoDomicilio != null) {
		try {

			$formularioDomicilio.find('#calle' ).val(validaAtributoVacio(objetoDomicilio.calle));
			$formularioDomicilio.find('#codigoPostal' ).val(validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));
			$formularioDomicilio.find('#asentamientoCodigoPostal' ).val(validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));
			$formularioDomicilio.find('#estado' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));			
			$formularioDomicilio.find('#asentamientoClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.nombre));
			$formularioDomicilio.find('#asentamientoClaveCatalogo' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			$formularioDomicilio.find('#municipio' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			$formularioDomicilio.find('#municipioClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$formularioDomicilio.find('#numeroExterior' ).val(validaAtributoVacio(objetoDomicilio.numExteriorAlf));
			$formularioDomicilio.find('#numeroInterior' ).val(validaAtributoVacio(objetoDomicilio.numInteriorAlf));
			
		} catch (e) {}		
	}

};

function validaAtributoVacio (atributo) {
	if (atributo == null || typeof atributo === 'undefined') {
		return "";
	} else {
		return atributo;
	}
}

$('#continuarCarpturarHistoriaLaboral').click(function() {
	document.charset = "ISO-8859-1";
	$('#formCodigoPostal').submit();
});

function spanHideErrores(contenedor){	    
		$("#"+contenedor).removeClass("showElement");
		$("#"+contenedor).addClass("hiddenElement");
		$("#"+contenedor).text();
}

var marcarCamposErrorFormDom = function() {
			if(verificarExistenciaFuncionErrores()) {
				marcarCamposConErrores("#domicilioCorto form#formCodigoPostal",".error","div");
			}
		};
		
var verificarExistenciaFuncionErrores = function() {
			if(typeof marcarCamposConErrores === "undefined") {
				return false;
			} else if($.isFunction(marcarCamposConErrores)) {
				return true;
			} else {
				return false;
			}
		}; 

var verificarChecks = function(idCheck,idInput)	{
	$("input[id='"+idCheck+"']").change(function() {	
	var ischecked= $(this).is(':checked');
	var nClassShow = 'showElement';
	var nClassHidden ='hiddenElement';                    
			
	if(!ischecked){	
		$("[id='"+idInput+"']").prop( "disabled", true );
		$("[id='"+idInput+"']").val("");
		$("[id='"+idInput+"']").css("border","");
		if (idCheck === "checkOtro"){			
			$("[id='motivoAclaracionVO.especificacionError']").removeClass(nClassShow);
			$("[id='motivoAclaracionVO.especificacionError']").addClass(nClassHidden);
			}
		else if(idCheck === "motivoAclaracionVO.motivosAclaracionInfonavit4"){			
			$("[id='motivoAclaracionVO.creditoDescontadoError']").removeClass(nClassShow);
			$("[id='motivoAclaracionVO.creditoDescontadoError']").addClass(nClassHidden);
			}
	}else{
		$("[id='"+idInput+"']").prop( "disabled", false );
		}
	});
};
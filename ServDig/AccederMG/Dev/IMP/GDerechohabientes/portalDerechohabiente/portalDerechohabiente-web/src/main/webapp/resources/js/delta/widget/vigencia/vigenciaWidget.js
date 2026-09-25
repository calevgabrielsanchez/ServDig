/**
 * 
 */
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/registroDerechohabiente/RegistroDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/domicilioClinicaDerechohabiente/DomicilioClinicaDerechohabienteWizard.js");
$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js");

var vigenciaWidgetCtrl = {
	divContenedorRegistro:'wizardRegistroDerechohabiente',
	registrarAsegurado : function(_asegurado) {
		var idEstadoCabeza = _asegurado.estadoDerechohabiente.idEstadoDerechohabiente;
		var idAsignacion = _asegurado.asignacionNSS.idAsignacionNSS;
		var nss = _asegurado.asignacionNSS.nss;
		var parentesco = _asegurado.parentesco.idParentesco;
		
		WizardRegistroDerechohabienteCtrl.setOnCloseCallback(function() {
			if(WizardRegistroDerechohabienteCtrl.datosSalida.registroCorrecto) {
				
				if(!WizardRegistroDerechohabienteCtrl.datosSalida.redireccionar) {
					if(WizardRegistroDerechohabienteCtrl.datosSalida.mostrarDocumentos) {
						ejecutarConsultaSolicitudPorFolio(WizardRegistroDerechohabienteCtrl.datosSalida.folioSolicitud);
					}
				} else {
					vigenciaWidgetCtrl.abrirPortalAsegurado(_asegurado);
				}

			}
		});
		WizardRegistroDerechohabienteCtrl.init(this.divContenedorRegistro, idAsignacion, nss, parentesco);
		WizardRegistroDerechohabienteCtrl.abrir();
	},
	abrirPortalAsegurado: function (_asegurado) {
		crearYEnviarFomulario(_asegurado);
	}
};

$("#abrirAsegurado").live('click', function() {
	//Validaxciones de existencia de asegurado, domicilio y umf
	var url = "/${mvn.web.app.root}/widget/validacionesVigencia";
	//La persona a la que buscaremos
	var _persona = {'idPersona' : AtributosPersonaCtrl.personaPortal.idPersona};
	//Se bloquea la pantalla y se hace la llamada
	$.blockUI();
	$.postJSON(url, _persona, function(result) {
		//Una vez que ha sido atendida la peticion, se desbloquea la pantalla
		$.unblockUI();
		//obtenemos el atributo error
		var error = result.error;
		//Si hubo error
		if(error) {
			//mostramos el mensaje que haya regresado la peticion
			var mensajeErrorVig = result.mensaje;
			muestraMensajeErrorVigencia(mensajeErrorVig);
		} else {
			//Si no hubo error
			//Se obtiene la propiedad asegurado
			var asegurado = result.asegurado;
			//Y la propiedad para saber si la persona ya esta registrada como derecohabiente
			var registrado = result.registrado;
			//Si no esta registrado como derechohabiente
			if(!registrado) {
				//Obtenemos el parentesco y estado del derechohabiente
				var idEstadoAsegurado = asegurado.estadoDerechohabiente.idEstadoDerechohabiente;
				var idParentesco = asegurado.parentesco.idParentesco;
				//Verificamos que el asegurado o pensionado no tenga un estado que no permita el registrso
				if(idEstadoAsegurado == ESTADO_DERECHOHABIENTE_ENUM.FALLECIDO) {
					//de no contar con un estado valido, mostranmos el errlr
					muestraMensajeErrorVigencia("No es posible ver el detalle de vigencia ya " +
							"que no te encuentras registrado(a) como derechohabiente y no cuentas con un estado v&aacute;lido para realizar el registro.");
				} else {
					//En caso de contar con estados validos para el registro mostramos mensaje
					var mendajeSinRegistro = "Para poder ingresar al detalle de vigencia es necesario que estes registrado(a) como derechohabiente, da clic en la opci&oacute;n Aceptar para proceder con el registro o" +
							" clic en la opci&oacute;n Cancelar. <strong>Nota:</strong> No podr&aacute;s ver tu detalle de vigencia hasta que hayas completado tu registro como derechohabiente";
					//y ejecutamos el proceso de registro de asegurado o pensionado
					muestraMensajeConFunciones(mendajeSinRegistro, registrarAsegurado,asegurado);
				}
			} else {
				//en caso de estar registrado verificamos si tiene domicilio y umf
				var tieneDomUmf =result.tieneDomicilioUmf;
				//Si no tiene domicilio
				if(!tieneDomUmf) {
					//mostramos mensaje 
					var mendajeSinDomicilio = "Para poder ingresar al detalle de la vigencia es necesario contar con un domicilio dentro del grupo familiar. Para capturarlo da " +
					"clic en Aceptar, de lo contrario elije la opci&oacute;n de Cancelar. <strong>Nota:</strong> No podr&aacute;s ver tu detalle de vigencia hasta que captures" +
					" el domicilio.";
					//Y ejecutamos el proceso de actualizacion de domicilio
					muestraMensajeConFunciones(mendajeSinDomicilio, capturarDomicilio);
				} else {
					//En caso de estar registrado y contar con domicilio y UMF redireccionamos al portal de asegurado
					vigenciaWidgetCtrl.abrirPortalAsegurado(asegurado);
				}
			}
		}
		
	}).error(function(data){
		//En caso de que ocurra un error en la llamda mostramos el error
		muestraMensajeErrorVigencia("Error: " + data.estatus);
	});

});

/**
 * Funcion para mostrar mensaje de confirmacion, que mostrara dos botones, uno de aceptar y otro de cancelar
 * recibe los siguientes atributos
 * @param mensaje - Mensaje a mostrar 
 * @param funcionAceptar - Funcion que se ejecutara cuando se presione el boton aceptar, puede ir nulo 
 * @param parametros - parametros que se le pasaran a la funcion aceptar pueden venir nulos o simplemente no venir
 */
function muestraMensajeConFunciones(mensaje, funcionAceptar, parametros) {
	//Se crea el div del mensaje
	$mensajeFuncion = $('<div></div');
	//Se setea el mensaje
	$mensajeFuncion.html(mensaje);
	
	//Se crea el dialogo
	$mensajeFuncion.dialog({
		autoOpen : false,
		title: 'Mensaje de sistema',
		resizable: false,
		closeOnEscape : false,
		modal: true,
		width: 500,
		buttons: {
			"Cancelar": function() {
				//Se cierra el dialogo
				$mensajeFuncion.dialog('close');
				$mensajeFuncion.dialog('destroy');
			},
			"Aceptar" : function() {
				//Se cierra el mensaje
				$mensajeFuncion.dialog('close');
				$mensajeFuncion.dialog('destroy');
				//Se verifica si es necesario ejecutar una fuuncion
				if(funcionAceptar != undefined && funcionAceptar != null) {
					//Se verifica si los parametros para la funcion no son nulos o indefinidos
					if(parametros != undefined && parametros != null ) {
						funcionAceptar(parametros);
					} else {
						funcionAceptar();
					}
				}
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$mensajeFuncion.dialog('open');
}

/**
 * Funcion para mostrar mensaje de error
 * @param mensajeE
 */
function muestraMensajeErrorVigencia(mensajeE) {
	$mensajeErrorVigenciaA = $('<div></div');
	$mensajeErrorVigenciaA.html(mensajeE);
	$mensajeErrorVigenciaA.dialog({
		autoOpen : false,
		closeOnEscape : false,
		title: 'Error',
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar": function() {
				$mensajeErrorVigenciaA.dialog('close');
				$mensajeErrorVigenciaA.dialog('destroy');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$mensajeErrorVigenciaA.dialog('open');
}

/**
 * 
 * @returns
 */
var capturarDomicilio = function() {
	WizardDomicilioGeneralCtrl.init({idPersona	: AtributosPersonaCtrl.personaPortal.idPersona}).abrir();
};

/**
 * 
 * @param _asegurado
 * @returns
 */
var registrarAsegurado= function(_asegurado) {
	vigenciaWidgetCtrl.registrarAsegurado(_asegurado);
};

/**
 * Funcion que crea el formulario para enviarlo al portal de asegurado
 * @param _asegurado
 * @returns
 */
var crearYEnviarFomulario = function (_asegurado) {
	//Se bloquea la pantalla
	$.blockUI();
	//Se crea el formulario y se le pone id y a donde se redireccionara
	var formulario = $("form");
	formulario.attr("id","asegurado");
	formulario.attr("name","asegurado");
	formulario.attr("method","POST");
	formulario.attr("action","/portal-web/portal/asegurado/ingresar");
	//Se setean los atributos al formulario
	formulario.append("<input type='hidden' id='asignacionNSS.idPersona' name='asignacionNSS.idPersona' value='"+_asegurado.derechohabiente.idPersona+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.idAsignacionNSS' name='asignacionNSS.idAsignacionNSS' value ='"+_asegurado.asignacionNSS.idAsignacionNSS+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.nss' name='asignacionNSS.nss' value = '"+_asegurado.asignacionNSS.nssStr+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.nombre' name='asignacionNSS.nombre' value = '"+_asegurado.derechohabiente.nombre+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.primerApellido' name='asignacionNSS.primerApellido' value = '"+_asegurado.derechohabiente.primerApellido+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.segundoApellido' name='asignacionNSS.segundoApellido' value = '"+_asegurado.derechohabiente.segundoApellido+"'>");
	formulario.append("<input type='hidden' id='asignacionNSS.curp' name='asignacionNSS.curp' value = '"+_asegurado.derechohabiente.curp+"'>");
	formulario.append("<input type='hidden' id='parentesco.idParentesco' name='parentesco.idParentesco' value = '"+_asegurado.parentesco.idParentesco+"'>");
	formulario.append("<input type='hidden' id='estadoDerechohabiente.idEstadoDerechohabiente' name='estadoDerechohabiente.idEstadoDerechohabiente' value = '"+_asegurado.estadoDerechohabiente.idEstadoDerechohabiente+"'>");
	//se envia el formulario
	formulario.submit();
};

$(document).ready(
	function() {
		$.post("/portal-web/utility/menu/opciones/2/10",null,function(data) {
			$("#accionesWidgetVigenciaPortal").html(data);				
		});
	}
); 
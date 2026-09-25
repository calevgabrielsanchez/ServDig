//bandera para saber si estamos en la validacion
INICIO_VALIDACION = false;
//las umfs localizadas
UMFS_LOCALIZADAS = null;
//bandera para incidar si se guarda el registro
GUARDAR_REGISTRO=false;
//El id de la umf seleccionada
ID_UMF_SELECCIONADA=0; 
//la variable donde se guardaran los datos de adscripcion activos
MEDICO_ACTIVO = null;
var mensajeC= '<div class="ui-widget">' +
'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
'Si requiere corregir datos de clic en Regresar.</p></div></div>';
var validacion;
var guardarValidacion =false;
$.ajaxSetup({ cache: false }); 

$(document).ready(function() {
	
	//verificamos si no estamos en la validacion
	var isValidacion = $("#validacion").val() != 0;

	$("#aceptar").hide();
	$("#cancelar").hide();
	$("#aceptarValidacion").hide();
	$("#regresarGrupoFamiliar").hide();
	$("#rechazarTramite").hide();
	$("#regresar").hide();
	//verificamos si estamos en el paso de validacion del cambio de clinica
	if(isValidacion){//si estamos en la validacion realizamos las validaciones
		initValidacion();
	}else{//si no estamos en la validacion
		initTramite();
	}
	//definimos el evento del boton aceptar
	$("#aceptar").on('click',eventoBotonAceptar);	
	//definimoc el evento del boton regresar al grupo familiar
	$("#regresarLista").on('click',eventoRegresarAGrupo);
	//Asignamos el evento al boton regresar
	$("#regresar").on('click',eventoBotonRegresar);
	//Establecemos el boton del select de umf
	$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").on('change',eventoCambioUmf);
	//Establecemos el evento del select del turno
	$("#medicoEnTurno\\.turno\\.idTurno").on('change',eventoCambioTurno);
	//Establecemos el evento de cambio del select de consultorio
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").on('change',eventoCambioConsultorio);
	//evento del boton aceptar validacion
	$("#aceptarValidacion").on('click',eventoAceptarValidacion);
	//evento del boton de regresar al grupo familiar
	$("#regresarGrupoFamiliar").on('click',eventoBotonRegresarGrupoVal);

	//colocamos el turno en caso de ser validacion
	if(INICIO_VALIDACION){
		//cargamos los turnos
		findTurnosByUmf($("#idUMF").val(), "medicoEnTurno\\.turno\\.idTurno", $("#idTurno").val(), null, null, null,null);
		//Cargamos los consultorios
		setConsultorios($("#idUMF").val(),$("#idTurno").val());
		//colocamos los datos del medico
		getMedicobyUmfTurnoConsultorio($("#idUMF").val(),$("#idTurno").val(),$("#idConsultorio").val(), setMedicoPantalla, limpiarDatosMedico,null);
	}	
	
	// para todos deshabilitamos los campos de umf y solo dejamos habilitados los campos para poder cambiar de medico
	habilitaSelectsMedico(false);
	// Limites para text area de observaciones
	asignartextAreaLimites("observacion",{styles:{}});
});

/**
 * funcion para iniciar el cambio de clinica en caso de que no sea la validacion
 */
var initTramite = function() {
	$("#medicoTurno").hide();
	$("#aceptarValidacion").hide();
	
	$("#regresarGrupoFamiliar").hide();
	
	GUARDAR_REGISTRO=false;
	mostrarBotonesValidacion(false);
	//inicializamos los componentes de domicilios recortados
	initDomicilios(false);
}

var initValidacion = function() {
	
	var tipoValidacion = $("#validacion").val();
	
	$("#aceptar").hide();
	$("#regresarLista").hide();
	$("#regresar").hide();
	
	habilitaSelectsMedico(false);
	
	if($("#validacion").val() == 2) {
		$("#rechazarValidacion").show();
		$("#regresarGrupoFamiliar").show();
		$("#aceptarValidacion").show();
	}
	
	//Verificamos si existe el campo de umf y en ese caso lo asignacion a la variable global
	if($("#idUMF").length > 0){
		ID_UMF_SELECCIONADA=$("#idUMF").val(); 
	}
	
	INICIO_VALIDACION=true;
	findUmfsPorCodigoPostal($("#domicilio\\.codigoPostal\\.codigoPostal").val(), "#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
	$("#medicoTurno").show();
	$("#aceptar").hide();
	GUARDAR_REGISTRO=true;
	mostrarBotonesValidacion(true);
	//deshabilitamos los select de medico
	habilitaSelectsMedico(false);
	initDomicilios(true);
}

var eventoBotonRegresarGrupoVal = function() {
	if(guardarValidacion){
		guardarValidacion=false;
		regresarDocumentacion();
		$("#mensajeConfirmacion").val('');
	}else{
		cancelarCorreccion();
	}
}
/**
 * definimos el evento del boton aceptar
 */
var eventoBotonAceptar = function() {
	//validamos que el domicilio haya sido capturado
	var resultadoValidacion = validarDomicilioCapturado();
	//verificamos si se requiere documentos
	if(!validacionesDocumentosForm() && resultadoValidacion) {
		confirmacionGuardadoCambio();
	}
}

var validacionesDocumentosForm = function() {
	
	var requiereDocumentos = $("#requiereDocumentacion").val() == 1;
	//verificamos los documentos se estan pidiendo(registro) o se estan mostrando(validacion)
	var  muestraDocumentos = $("#documentos").val() == 0;
	//bandera para indicar si existe error
	var existeError = false;
	
	//si requiere documentos y se estan capturando y no ha sido finalizada la captura
	if(requiereDocumentos && muestraDocumentos && !fileUploadFinish){
		//en caso de que no se haya completado la documentacion mostramos un error y salimos de la funcion
		mostrarMensajeErrorCambioClinica("Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n");
		//indicamos que existe error
		existeError = true;
	}
	
	if(!existeError) {
		existeError=!validaCombos();
	}
	
	return existeError;
}

/**
 * Evento del boton regresar
 */
var eventoBotonRegresar = function() {
	var isValidacion = $("#validacion").val()!=0;
	var muestraDocumentos = $("#documentos").val() == 0;
	
	if(!isValidacion){
		$("#regresarLista").show();
	}else{
		$("#regresarGrupoFamiliar").show();
	}
	
	if(muestraDocumentos) {
		regresarDocumentacion();
	}
	//habilitamos selects de medico
	habilitaSelectsMedico(true);
	
	GUARDAR_REGISTRO=false;
	$("#regresar").hide();
	$("#mensajeConfirmacion").val('');
}

//definimos el evento del boton regresar a la lista
var eventoRegresarAGrupo = function() {
	
	var titulo = 'Selecciona una opci\u00F3n';
	var mensaje = 'La informaci\u00F3n capturada se perder\u00E1, \u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite?';
	var eventoSi = function() {
		cierraDialogo($(this));
		$.blockUI();
		location.href= "" + context_path + "/inicio/grupoFamiliar";
	};
	var eventoNo = function() {cierraDialogo($(this));};
	
	creaMensajeConfirmacionSiNO(titulo, mensaje, eventoSi, eventoNo);
}

//definimos el evento del select de id de umf
var eventoCambioUmf = function() {
	//Establecemos la umf seleccionada por el usuario
	ID_UMF_SELECCIONADA = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").val();
	//limiamos el mensaje de error
	limpiarContenidoElemento("errorUmf");
	setDatosUmf();
	$("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex=0;
	$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
	limpiarDatosMedico();
	limpiarConsultorio() ;
	
	if($("#idPerfilUsuario").val() == "1") {
		if($("#idUMFUsuario").val() != $("#idUmfOrigen").val() && $("#idUMFUsuario").val() != ID_UMF_SELECCIONADA) {
			errorUmfusuario();
			$("#aceptar").hide();
			return;
		}
	}
	
	if( $("#validacion").val() == 0 ){
		$("#aceptar").show();
	}
}

//definimos el evento cuando el select de cambio de medico cambia
var eventoCambioConsultorio = function() {
	var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
	var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
	var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
	limpiarContenidoElemento("errorConsultorio");
	getMedicobyUmfTurnoConsultorio(idUmf, idTurno, idConsultorio, setMedicoPantalla, limpiarDatosMedico,null);
}

//definimos el evento cuando se modifica el turno
var eventoCambioTurno = function() {
	var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
	var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
	limpiarContenidoElemento("errorTurno");
	$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
	limpiarDatosMedico();
	if($.trim(idUmf).length > 0)
		setConsultorios(idUmf,idTurno);
}

//definimos el evento del boton de aceptar validacion
var eventoAceptarValidacion = function() {
	
	var tipoValidacion = $("#validacion").val();
	var requiereDocumentos = $("#requiereDocumentacion").val() == 1;
	var capturandoDocumentos = $("#documentos").val() == 0;
	
	if(tipoValidacion == 2) {
		if(requiereDocumentos && capturandoDocumentos && !fileUploadFinish) {
			mostrarMensajeErrorCambioClinica("Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n");
			return;
		} else {
			mostrarMensajeConfirmacionGuardadoValidacion();
		}
	} else if(tipoValidacion == 1) {
		if(guardarValidacion){
			guardar();
		}else{
			if(requiereDocumentos && capturandoDocumentos && !fileUploadFinish){
				mostrarMensajeErrorCambioClinica("Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n");
			}else{
				guardar();
			}
		}
	}
};

var confirmacionGuardadoCambio = function() {
	var funcionNo = function() {cierraDialogo($(this));};
	var funcionSi = function() {cierraDialogo($(this));guardarCambios();}
	
	creaMensajeConfirmacionSiNO('Confirmacion','\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de cambio de clinica?',funcionSi, funcionNo);
}

var guardarCambios = function(){
	//deshabilitamos la pantalla
	$.blockUI();
	$("#aceptar").hide();
	$("#regresar").show();
	$("#regresarLista").hide();
	GUARDAR_REGISTRO=true;
	habilitaSelectsMedico(true)
	//hacemos submit del formulario
	$("#correccionDatos").submit();
}

var limpiarConsultorio = function() {
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html("<option value=''> -- Por favor seleccione -- </option>");
}

//funcion que setea los datos del medico en pantalla
var setMedicoPantalla = function(medico) {
	
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
	if(medico.medicoFamiliar != null){
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
	}else{
		limpiarDatosParticularesMedico();
	}
	
	if(medico.medicoEspecialidad != null){
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
	}else{
		limpiarEspecialidadMedico();
	}
}

var limpiarDatosMedico = function() {
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val('');
	limpiarDatosParticularesMedico();
	limpiarEspecialidadMedico();
}

var limpiarDatosParticularesMedico = function(){
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val('');
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val('');
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val('');
}

var limpiarEspecialidadMedico = function() {
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val('');
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val('');
}

var setConsultorios = function(idUmf , idTurno) {
	var idConsultorioSeleccionado = $("#idConsultorio").val();
	
	findConsultoriosByUmfTurno(idUmf, idTurno,"medicoEnTurno\\.consultorio\\.idConsultorio", idConsultorioSeleccionado, null, null, null,null);
}

function setDatosUmf() {
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0) {
		var umfSel = UMFS_LOCALIZADAS[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
		if(!INICIO_VALIDACION) {
			//Verificamos si existe algun integrante en esta umf
			var url = context_path + "/derechohabiente/correccion/getMedicoEnTurnoActivo";
			var umf = {
					"parentesco" : {
						"idParentesco" : $("#parentesco\\.idParentesco").val()
					},
					"medicoEnTurno": {
						"unidadMedicaFamiliar": {
							"idUMF" : umfSel.idUMF
						}
					}
			};
			$.postJSON(url,umf,
				function(result) {
					MEDICO_ACTIVO = result;
					if(MEDICO_ACTIVO != null){
						
						setDatosMedicoTurnoActivo();
					} else {
						findTurnosByUmf(umfSel.idUMF, "medicoEnTurno\\.turno\\.idTurno", null, null, null, null,null);
						$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
						$("#medicoEnTurno\\.consultorio\\.idConsultorio").removeAttr("disabled");
					}
				}	
			);
		}
	}else{
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val('');
	}
}

function setDatosMedicoTurnoActivo() {
	var options = "<option value='-1'>--Seleccione por favor--</option>";
	options+=	"<option value='" + MEDICO_ACTIVO.turno.idTurno + "' selected='selected'>" + MEDICO_ACTIVO.turno.descripcion + "</option>";
	$("#medicoEnTurno\\.turno\\.idTurno").html(options);
	$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	options = "<option value='-1'>--Seleccione por favor--</option>";
	options += "<option value='" + MEDICO_ACTIVO.consultorio.idConsultorio + "' selected='selected'>" + MEDICO_ACTIVO.consultorio.descripcion + "</option>";
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html(options);
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").attr("disabled","disabled");
	
	setMedicoPantalla(MEDICO_ACTIVO);
}

function funcionAsentamientosEncontrados() {
	findUmfsPorCodigoPostal($("#domicilioActualDiv #domicilio\\.codigoPostal\\.codigoPostal").val(), "#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
}

/**
 * Funcion que ubica las umfs relacionadas al codigo postal
 * @param codigoPostal
 * @param idHtml
 */
function findUmfsPorCodigoPostal(codigoPostal, idHtml) {
	//bandera para saber si estamos en la umf destino
	var enUmfDestino = $("#enUmfDestino").val() == 1;
	var umfUsuario = $("#idUMFUsuario").val();
	var umfAnterior = $("#idUMFAnt").val();
	var noIsValidacion = $("#validacion").val() == 0;
	
	
	//Verificamos si el codigo postal viene
	if(codigoPostal!=null && codigoPostal!=undefined ){
		//la url donde se buscaran las umfs por codigo postal
		var url = context_path + "/umf/getUmfsByCodigoPostalV";
		//se enviara el codigo postal como parametro
		var parametros = {'codigoPostal': codigoPostal};
		//anadiremos las opciones al combo
		var options = "<option value='-1'> -- SELECCIONE POR FAVOR --</option>";
		//Se hace la llamada a la url de busqueda de CPS
		$.postJSON(url, parametros, function(result) {
			//obtenemos las umfs
			UMFS_LOCALIZADAS = result;
			//si no se encontraron umfs
			if(UMFS_LOCALIZADAS == null || UMFS_LOCALIZADAS == undefined) {
				errorSinUmf();
				ocultarAceptarDatosUMF(true);
				return;
			} else if(UMFS_LOCALIZADAS.length == 1) { //si solo se encontro una UMF
				var umfLocalizada = UMFS_LOCALIZADAS[0];
				//verificamos si el tramite se hace en la umf destino, en ese caso la umf encontrada debe coincidir con la umf del usuario
				//en caso de no ser iguales mandamos un error
				if(enUmfDestino && umfLocalizada.idUMF != umfUsuario){
					errorSinUmfDestino();
					ocultarAceptarDatosUMF(true);
					return;
				}
				
				//si paso la validacion validamos que no sea la misma umf que la anterior
				if(umfAnterior == umfLocalizada.idUMF) {
					errorSinUmf();
					ocultarAceptarDatosUMF(true);
					return;
				} 
				
				//verificamos si existe una umf selecciona y en ese caso pondremos como seleccionada la umf
				if(ID_UMF_SELECCIONADA != 0 && ID_UMF_SELECCIONADA == umfLocalizada.idUMF){
					options += "<option value='" + umfLocalizada.idUMF + "' selected='selected'>" + umfLocalizada.descripcion + "</option>";	
				} else if(ID_UMF_SELECCIONADA == 0){
					options += "<option value='" + umfLocalizada.idUMF + "'>" + umfLocalizada.descripcion + "</option>";
				}
				//pintamos en el combo las UMFS
				$(''+idHtml).html(options);
				
				//verificamos que no estemos en la validacion 
				if(noIsValidacion) {
					//En ese caso mostramos el boton de aceptar y los datos de la UMF
					ocultarAceptarDatosUMF(false);
					//bloqueamos los select de la clinica
					habilitaSelectsMedico(true);
				}
				
				//Si estamos en la umf destino
				if(enUmfDestino) {
					$(''+idHtml).val(umfUsuario);
					setDatosUmf();
					$(''+idHtml).attr("disabled","disabled");
				}
				
				//si es validacion colocamos los datos de umf
				if(INICIO_VALIDACION){
					setDatosUmf(); 
				}	

			} else if(UMFS_LOCALIZADAS.length > 1) {//si existe mas de uns UMF
				//el indice que borraremos
				var borrar = -1;
				//bandera para saber si la umf del usuario esta en la lista
				var esta = false;
				var numeroUmfsLozalidas = UMFS_LOCALIZADAS.length;
				//recorremos la lista de las umfs
				for(var i = 0 ; i <  numeroUmfsLozalidas; i++){
					//si la umf seleccionada es la umf que se recorre se pone como seleccionada
					if(UMFS_LOCALIZADAS[i].idUMF == ID_UMF_SELECCIONADA)
						options += "<option value='" + UMFS_LOCALIZADAS[i].idUMF + "' selected='selected'>" + UMFS_LOCALIZADAS[i].descripcion + "</option>";
					else if(UMFS_LOCALIZADAS[i].idUMF != umfAnterior) { //si no es la seleccionada y no es la anterior se pone 
						options += "<option value='" + UMFS_LOCALIZADAS[i].idUMF + "'>" + UMFS_LOCALIZADAS[i].descripcion + "</option>";
					} else if(UMFS_LOCALIZADAS[i].idUMF == umfAnterior) {//Si es la umf anterior se borrara
						borrar = i;
					}
					//Verificamos si la umf actual esta dentro de la lista en caso de que esta sea la umf destino
					if(UMFS_LOCALIZADAS[i].idUMF == umfUsuario){
						esta=true;
					}
				}
				//si existe la umf anterior en la lista la borramos para que no pueda ser elegida
				if(borrar != -1) {
					UMFS_LOCALIZADAS.splice(borrar,1);
				}
				//pintamos en el combo las umfs
				$(''+idHtml).html(options);
				
				if(noIsValidacion) {
					//mostramos los botones de aceptar y datos de umf
					ocultarAceptarDatosUMF(false);
					//habilitamos el contenido de los datos de adscripcion
					habilitaSelectsMedico(true);
					//ponemos como seleccionada la primera posicion
					$('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF')[0].selectedIndex=0;
				}
				
				if(enUmfDestino) {
					if(esta) {
						$(''+idHtml).val(umfUsuario);
						setDatosUmf();
						$(''+idHtml).attr("disabled","disabled");
					} else {
						errorSinUmfDestino();
						$("#medicoTurno").hide();
						$("#aceptar").hide();
						return;
					}
				}
				//si es validacion colocamos los datos de umf
				if(INICIO_VALIDACION){
					setDatosUmf(); 
				}	
				
			}
		});
	}
}

var ocultarAceptarDatosUMF = function(ocultar) {
	if(ocultar) {
		$("#medicoTurno").hide();
		$("#aceptar").hide();
	} else {
		$("#medicoTurno").show();
		$("#aceptar").show();
	}
}

var  colocarMensajeRequerido = function(idElemento){
	$("#"+idElemento).html('<p id="mensaje" style="color: red">&nbsp;&nbsp;Requerido</p>');
}
var limpiarContenidoElemento = function(id){
	$("#"+id).html('');
}

function mostrarBotonesValidacion(estado){
	if(estado){
		$("#aceptar").hide(); 
		$("#ubicar").hide(); 
		$("#guiaTramite").hide(); 
		$("#regresar").hide(); 
		$("#cancelar").hide(); 
		$("#aceptarValidacion").show(); 
		$("#regresarGrupoFamiliar").show();
		$("#rechazarTramite").show();
	}else{
		$("#aceptarValidacion").hide(); 
		$("#regresarGrupoFamiliar").hide();
		$("#rechazarTramite").hide();
	}
	
}

var habilitaSelectsMedico = function(habilitar) {
	
	if(!habilitar && MEDICO_ACTIVO == null && $("#validacion").val() == 0) {
		$("#medicoTurno").find("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").attr("disabled","disabled");
	} else {
		//para habilitar los selects del medico
		$("#medicoTurno").find('select').each(function() {
			if(habilitar) {
				$(this).removeAttr("disabled");
			} else {
				$(this).attr("disabled","disabled");
			}		
		});
	}
}

function setValidacion(isValidacion) {
	
	validacion = isValidacion;
	if(validacion == 0) {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/cambioUmf/guardar");
		$("#medicoTurno").hide();
	} else {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/cambioUmf/validacion/guardar");
		
		//para todos deshabilitamos los campos de umf y solo dejamos habilitados
		//los campos para poder cambiar de medico
		habilitaSelectsMedico(false);
		
		//para todos deshabilitamos los campos de umf y solo dejamos habilitados
		//los campos para poder cambiar de medico
		$("#medicoTurno").find("input:text").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
		
	}
}

function mensajeConfirmacionCC(mensajeu){
	var buttons = {
		"Cerrar": function() {
			cierraDialogo($(this));
		}
	};
	crearDialogoPantalla('Mensaje', mensajeu, buttons);
}


var validaCombos = function(){
	var errores=0;
	
	habilitaSelectsMedico(true);
	
	errores += functionValidaSelect("medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF","errorUmf")
	errores += functionValidaSelect("medicoEnTurno\\.turno\\.idTurno","errorTurno")
	errores += functionValidaSelect("medicoEnTurno\\.consultorio\\.idConsultorio","errorConsultorio")
	
	if($.trim($("#observacion").val()).length == 0) {
		colocarMensajeRequerido("errorObservacion");
		errores += 1;
	}else{
		limpiarContenidoElemento("errorObservacion");
	}
	
	habilitaSelectsMedico(false);

	return (errores == 0);
}

var functionValidaSelect = function(idSelect,idCampoError) {
	//console.log("el valor del select " + idSelect + " es " + $("#"+idSelect).val());
	if($.trim($("#"+idSelect).val()).length == 0 || $("#"+idSelect).val() == "-1") {
		colocarMensajeRequerido(idCampoError);
		return 1;
	} else {
		limpiarContenidoElemento(idCampoError);
		return 0;
	}
}

var errorUmfusuario = function() {	
	mostrarMensajeErrorCambioClinica("La umf en la que se encuentra firmado no corresponde a la umf origen o destino");
}

var errorSinUmf = function() {
	mostrarMensajeErrorCambioClinica("No hay ninguna unidad medica familiar asociada al c\u00F3digo postal");
}

var errorSinUmfDestino = function() {
	mostrarMensajeErrorCambioClinica("No existen umfs o ninguna corresponde a la umf destino");
}

var mostrarMensajeErrorCambioClinica = function(errorMostrar) {
	$("#medicoTurno").hide();
	
	var mensaje= '<div class="ui-widget-content ui-corner-all"><div class="ui-state-error ui-corner-all" align="center">';
	mensaje+= '<div class="ui-icon ui-icon-alert"></div><p class="ui-helper-reset ui-state-error-text">'+errorMostrar+'</p>';
	mensaje+= '</div></div>';
	//creamos el boton que solo cerrara el dialogo
	var botones = {
		"Aceptar" : function() {cierraDialogo($(this));}
	}
	//creamos el dialogo
	 crearDialogoPantalla('Error', mensaje, botones)
}

var guardar = function(){
	$.blockUI();
	var requiereDocumentos = $("#requiereDocumentacion").val() == 1;
	var capturandoDocumentos = $("#documentos").val() == 0;
	//habilitamos los campos
	habilitaSelectsMedico(true);
	//si requiere documentos y se estaban capturando se guardan
	if(requiereDocumentos && capturandoDocumentos) {
		doSaveDocSinTramite($("#tramiteId").val());
	}
	
	$("#correccionDatos").submit();

}

var mostrarMensajeConfirmacionGuardadoValidacion= function(){
	var titulo = 'Selecciona una opci\u00F3n';
	var funcionSi = function() {
		$.blockUI();
		cierraDialogo($(this));
		habilitaSelectsMedico(true);
		$("#correccionDatos").submit();
	};
	var funcionNo = function() {guardarValidacion=false;cierraDialogo($(this));};
	var mensaje = '\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de cambio de UMF?';
	
	creaMensajeConfirmacionSiNO(titulo,mensaje,funcionSi, funcionNo);

}

//metodo para cancelar la correccion
var cancelarCorreccion = function() {
	
	var funcionSi = function() {location.href = "" + context_path + "/inicio/grupoFamiliar";};
	var funcionNo = function() {cierraDialogo($(this));};
	
	creaMensajeConfirmacionSiNO('Confirmacion','\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite de cambio de clinica?',funcionSi, funcionNo);
}

//creamos un dialog con la opcion de si y no se deben pasar los eventos para los botones
var creaMensajeConfirmacionSiNO = function(titulo, mensaje, eventoPositivo, eventoNegativo) {
	var botones = {"Si" : eventoPositivo,"No" : eventoNegativo};
	crearDialogoPantalla(titulo,mensaje,botones);
}

//metodo para crear un dialogo
var crearDialogoPantalla = function(titulo, mensaje, botones) {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width: 300,
		height : 'auto',
		title : titulo,
		modal : true,
		buttons : botones
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open')
}

var cierraDialogo = function($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

var regresarDocumentacion = function(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}
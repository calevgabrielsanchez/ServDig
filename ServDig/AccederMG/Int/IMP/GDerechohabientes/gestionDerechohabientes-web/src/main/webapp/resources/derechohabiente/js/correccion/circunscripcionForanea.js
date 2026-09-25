/**
 * Mario Teran Blanco Script para el cambio de circunscripcion 02/07/2012
 */

var asentamientosUbicados;
var umfs;
var guardarRegistro=false;
var docimicilioUbicado=false;
var mensaje="Por favor verifique la informaci\u00F3n proporcionada y de clic en aceptar para continuar con el registro. " +
"Si requiere corregir datos de clic en regresar.";
var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
var ventanaValidacion=false;
var inicioValidacion=false;
var medicoEnTurnoActivo = null;
var enUmfDestino = false;

$(document).ready(function() {
		
		$.ajaxSetup({ cache: false }); 
		
		/***
		$.getScript(urlDomicilios).done(function(script, textStatus) {
			
			DomicilioCtrl.init('domicilioUbicar');
			DomicilioCtrl.setOnCloseCallback(cambiarDomicilio);
		
			// Configuraci�n del boton disparador de domiiclios
			$('#ubicar').click(function() {
				//DomicilioCtrl.localizar();
			});
			
		}).fail(function(jqxhr, settings, exception) {
			alert('Error al cargar el script');
		});
		**/ 
		
		// Configuraci�n del boton disparador de domiiclios
		$('#ubicar').click(function() {
			validaCambioDomicilio();
			//DomicilioCtrl.localizar();
		});
		
		$("#regresarValidacion").hide();
		
		if( $("#validacion").val() == 0 ){
		
			$("#medicoEnTurno").hide();
			guardarRegistro=false;
			mostrarBotonesValidacion(false);
		
		}else{
			
			inicioValidacion=true;
			umfsByCodigoPostal($("#domicilio\\.codigoPostal\\.codigoPostal").val(), "#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
			$("#medicoEnTurno").show();
			$("#aceptar").hide();
			$("#guiaTramiteValidacion").hide();
			guardarRegistro=true;
			mostrarBotonesValidacion(true);
			
		}
		
		$("#aceptar").hide();
		$("#cancelar").hide();
				
		
		$("#aceptar").click(function() {
			
				var validacionCombos = validaCombos();
				if(validacionCombos) {
					if(guardarRegistro){
						guardarCircunscripcion();
					}else{
						
					var requiereDocs = $("#requiereDocs").val() == "1";
					if(requiereDocs){	
						if(fileUploadFinish) {
							
							$("#correccionDatos select").each(function(index) {
								$(this).attr("disabled","disabled");
							});
							
							$("#guiaTramite").hide();
							$("#ubicar").hide();
							$("#regresar").show();
							guardarRegistro=true;
							aceptarValidacionC();
							
							if( $("#validacion").val() == 0 )
							    cargaFinalizada();
							
							docimicilioUbicado=true;
						
						}else{
							mensajeConfirmacionA('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
						}
					}else{
						$("#correccionDatos select").each(function(index) {
							$(this).attr("disabled","disabled");
						});
						
						$("#guiaTramite").hide();
						$("#ubicar").hide();
						$("#regresar").show();
						guardarRegistro=true;
						aceptarValidacionC();
						
						docimicilioUbicado=true;
					}
						
					}
				}
			}
		);	
		
		$("#regresar").click(function() {
	
			if(guardarRegistro){
				
				guardarRegistro=false;
				$("#ubicar").show();
				$("#regresar").hide();
			
				$("fieldset#medicoEnTurno select").each(function(index) {
					$(this).removeAttr("disabled");
				});
				
				regresarDocumentacion();

			}else{
				
				if(docimicilioUbicado){
					
					docimicilioUbicado=false;
					$("#aceptar").hide();
					$("#medicoEnTurno").hide();
					
				}else{
					cancelarCorreccion();
				}
				    
			}
		});
		
		
		
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").change(asignarUMF);
		
		$("#medicoEnTurno\\.turno\\.idTurno").change(function() {
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			limpiarMensajeError($("#errorTurno"));
			$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
			limpiarMedico();
			setConsultorios(idUmf,idTurno);
		});

		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			limpiarMensajeError($("#errorConsultorio"));
			setMedico(idUmf,idTurno,idConsultorio);
			
		});
		
		// para todos deshabilitamos los campos de umf y solo dejamos
		// habilitados
		// los campos para poder cambiar de medico
		$("fieldset#medicoEnTurno input:text").each(function(index) {
			$(this).attr("disabled","disabled");
		});
		
		$("fieldset#medicoEnTurno select").each(function(index) {
			$(this).attr("disabled","disabled");
		});
		
		$("fieldset#domicilio input:text").each(function(index) {
			$(this).attr("disabled","disabled");
		});
			
		$("fieldset#domicilio select").each(function(index) {
			$(this).attr("disabled","disabled");
		});
			
			
		// ------------------------------------------
		// Limites para text area de observaciones
		// ------------------------------------------
		asignartextAreaLimites("observacion",{styles:{}});
		initDomicilios($("#validacion").val()==1);
			
});


function asignarUMF(){
	try{
		//console.log("entro");
		limpiarMensajeError($("#errorUmf"));
		setDatosUmf();
		$("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex=0;
		$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
		limpiarMedico();
		limpiarConsultorio();
	}catch(e){
		//console.log(e);
	}
}

function limpiarConsultorio() {
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html("<option value=''>--Seleccione umf y turno--</option>");
}

function limpiarMedico() {
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspecialidad").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("");
}

function setMedico(idUmf,idTurno,idConsultorio) {
	
	if( !(idUmf && idTurno && idConsultorio && (function(){
		try{
			
			var url = context_path + "/umf/getMedicosUmfTurno";
			var parametros = {
				'unidadMedicaFamiliar': {
					'idUMF': idUmf
				},
				'turno': {
					'idTurno': idTurno
				},
				'consultorio': {
					'idConsultorio': idConsultorio
				}
			};
			
			$.postJSON(url, parametros, function(result) {
				var medico = result[0];
				$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
				if(medico.medicoFamiliar != null && medico.medicoFamiliar != undefined) {
					$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
					$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
					$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
				}
				
				if(medico.medicoEspecialidad != null && medico.medicoEspecialidad != undefined) {
					$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
					$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
				}
			});
		
			return true;
			
		}catch(e){
			//console.log(e);
		}
		
		return false;
			
	})())){
		limpiarMedico();
	}
	
}

function setConsultorios(idUmf , idTurno, idConsultorio) {
	
	var url = context_path + "/umf/getConsultorios";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		}
	};
	
	if(inicioValidacion){
		idConsultorio= $("#idConsultorio").val();
	}
	
	if(idUmf != null && idUmf != undefined && idTurno != null && idTurno != undefined && $.trim(idTurno).length != 0) {
		$.postJSON(url, parametros, function(result) {
			var options = "<option value=''> -- Por favor seleccione -- </option>";
			
			for(var i = 0 ; i < result.length ; i++){
				if(idConsultorio != null || idConsultorio != undefined) {
					if(idConsultorio == result[i].idConsultorio)
						options += "<option value='" + result[i].idConsultorio + "' selected='selected'>" + result[i].descripcion + "</option>";
					else
						options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";
				}
				else
					options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";	
			}
			
			$("#medicoEnTurno\\.consultorio\\.idConsultorio").html(options);
		}).done(function(){
			
			if(inicioValidacion){
				$("#medicoEnTurno\\.consultorio\\.idConsultorio").attr("disabled","disabled");
			}else{
				$("#medicoEnTurno\\.consultorio\\.idConsultorio").removeAttr("disabled");
			}
			
		});
	}
}

function guardarCircunscripcion() {

	var infValida=validaCombos();
	
	if(infValida){
		save_Circunscripcion();
	}
}

function esperePorFavor() {
	$decision = $('<div></div>');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Espere un momento por favor');
	$decision.dialog('open');
}

function save_Circunscripcion() {
	$.blockUI();
	var action = context_path + "/derechohabiente/correccion/circunscripcion/autorizacion/guardar";
	$("fieldset#domicilio input:text").each(function(index) {
		$(this).removeAttr("disabled");
	});
		
	$("fieldset#domicilio select").each(function(index) {
		$(this).removeAttr("disabled");
	});
	
	$("fieldset#medicoEnTurno select").each(function(index) {
		$(this).removeAttr("disabled");
	});

	$("form#correccionDatos").attr("action",""+action);
	$("#observacion").removeAttr("disabled");
	$.unblockUI();
	$("form#correccionDatos").on("submit",function(){$.blockUI();});
	$("form#correccionDatos").submit();
}

function errorCircunscripcion() {
	docimicilioUbicado=false;
	$decision = $('<div></div');
	var mensaje= '<div class="ui-widget-content ui-corner-all">';
	mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
	mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
	mensaje+= '<p class="ui-helper-reset ui-state-error-text">La delegaci\u00F3n a la que pertenece el domicilio es igual a la actual</p>';
	mensaje+= '</div>';
	mensaje+= '</div>';
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}


function errorMensaje(message) {
	docimicilioUbicado=false;
	$decision = $('<div></div');
	var mensaje= '<div class="ui-widget-content ui-corner-all">';
	mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
	mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
	mensaje+= '<p class="ui-helper-reset ui-state-error-text">'+message+'</p>';
	mensaje+= '</div>';
	mensaje+= '</div>';
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}

function errorSinUmf() {
	$("#medicoEnTurno").hide();
	$decision = $('<div></div');
	var mensaje= '<div class="ui-widget-content ui-corner-all">';
	mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
	mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
	mensaje+= '<p class="ui-helper-reset ui-state-error-text">No hay ninguna unidad medica familiar asociada al c\u00F3digo postal</p>';
	mensaje+= '</div>';
	mensaje+= '</div>';
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}

function cancelarCorreccion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	//$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de autorizaci\u00F3n de circunscripci\u00F3n for\u00E1nea?');
	$decision.text('\u00BF Est\u00E1 Seguro que desea salir del tr\u00E1mite de Correcci\u00F3n? Se perderan todos los datos no guardados');
	$decision.dialog('open');
}

function setDatosUmf() {
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0 && umfs != null) {
		var umfSel = umfs[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
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
				medicoEnTurnoActivo = result;
				if(medicoEnTurnoActivo != null){
					
					setDatosMedicoTurnoActivo();
				} else {
					
					if( !inicioValidacion ){
						$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
						$("#medicoEnTurno\\.consultorio\\.idConsultorio").removeAttr("disabled");
					}
				}
			}	
		).done(function(data){
			
			if( enUmfDestino )
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").attr("disabled","disabled");
			else
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").removeAttr("disabled");
			
		});
	}else{
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val('');
	}
}

function setDatosMedicoTurnoActivo() {
	var options = "<option value='" + medicoEnTurnoActivo.turno.idTurno + "' selected='selected'>" + medicoEnTurnoActivo.turno.descripcion + "</option>";
	$("#medicoEnTurno\\.turno\\.idTurno").html(options);
	$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	options = "<option value='" + medicoEnTurnoActivo.consultorio.idConsultorio + "' selected='selected'>" + medicoEnTurnoActivo.consultorio.descripcion + "</option>";
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html(options);
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").attr("disabled","disabled");
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medicoEnTurnoActivo.idMedicoContultorioTurno);
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medicoEnTurnoActivo.medicoFamiliar.idMedicoFamiliar);
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medicoEnTurnoActivo.medicoFamiliar.noMatricula);
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medicoEnTurnoActivo.medicoFamiliar.nombre+" "+medicoEnTurnoActivo.medicoFamiliar.primerApellido+" "+medicoEnTurnoActivo.medicoFamiliar.segundoApellido);
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medicoEnTurnoActivo.medicoEspecialidad.idMedicoEspacialidad);
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medicoEnTurnoActivo.medicoEspecialidad.descripcion);
}

function verificarUmfs(umfUsuario,umfss) {
	
	var esta = false;
	
	for(var i= 0; i<umfss.length;i++) {
		if(umfss[i].idUMF == umfUsuario)
			return true;
	}
	
	return esta;
}

/**
 * Resetea los campos y oculta el fieldset de umf
 * 
 * Asigna el indice 0 a los selects
 * Asigna una cadena vac�a a los inputs
 * 
 */
function ocultaUMFFieldset(){
	
	
	$("fieldset#medicoEnTurno select").each(function(index) {
		try{
			$(this).val(0);
		}catch(e){
			
		}
	});
	
	$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").html("");


	$("fieldset#medicoEnTurno input").each(function(index) {
		try{
			$(this).val("");
		}catch(e){
			
		}
	});
	
	umfs = null;
	$("fieldset#medicoEnTurno").hide();
	$("#aceptar").hide(); 
	
}

function umfsByCodigoPostal(codigoPostal, idHtml) {
	
	if( codigoPostal ){
	
		var url = context_path + "/umf/getUmfsByCodigoPostalV";
		var umfo=$('#idUmfOr').val();
		var umfu=$('#idUmfUsuario').val();
		var parametros = {
			'codigoPostal': codigoPostal 
		};
			
		$.postJSON(url, parametros, function(result) {
				
				umfs = result;
				
				if(umfs == null || umfs == undefined) {
				
					errorSinUmf();
				
				}else {
					if(umfs.length > 0) {
						
						var idDelegacion = $('#idDelegacionOrigen').val();
						enUmfDestino = false;
						
						if(!inicioValidacion){
							
							if( mismaDelegacion(umfs, idDelegacion) ){
								errorCircunscripcion();
								ocultaUMFFieldset();
								return;
							}
							
							if(umfo!=umfu && !verificarUmfs(umfu,umfs)) {
								errorMensaje('La umf actual no concuerda con la umf origen ni ninguna de las posibles destino');
								ocultaUMFFieldset();
								return;
							}
							
							
						}
						
						
						// ---------------------------------------------------------
						// Combo de UMF's
						// ---------------------------------------------------------
						var options = "<option value=''> -- Por favor seleccione -- </option>";
						var idumf = ( $("#idUMF").val() || umfu );
						
						for(var i = 0 ; i < umfs.length ; i++){
							//-------------------------------------------------------------------
							//Solo si la delegacion es didstinta la agregamos al combo
							//-------------------------------------------------------------------
							if(umfs[i].subdelegacion.delegacion.id != idDelegacion) {
								if( idumf == umfs[i].idUMF ){
									options += "<option value='" + umfs[i].idUMF + "' selected='selected'>" + umfs[i].descripcion + "</option>";
									enUmfDestino = true;
								}else{
									options += "<option value='" + umfs[i].idUMF + "'>" + umfs[i].descripcion + "</option>";
								}
							}
						}
						$(''+idHtml).html(options);
						
						
						
						if($("#validacion").val()==0){
							$("#aceptar").show();
							
							$("fieldset#medicoEnTurno select").each(function(index) {
								$(this).removeAttr("disabled");
							});
							
							if( enUmfDestino ){
								asignarUMF();
							}
							
						}	
						
						
						$("#medicoEnTurno").show();
						
						//si es validacion colocamos los datos de umf
						if(inicioValidacion){
							setDatosUmf(); 
							//colocamos el turno en caso de ser validacion
							if($("#idTurno").val()==1){
								$("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex=1;
							}else{
								$("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex=2;
							}
							
							$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
							
							//colocamos los consultorios
							var idUmf = $('#idUMF').val();
							var idTurno = $('#idTurno').val();
							setConsultorios(idUmf,idTurno);
							//colocamos los datos del medico
							setMedico($("#idUMF").val(),$("#idTurno").val(),$("#idConsultorio").val()); 
						
						}	
						
					}
				}
			}
		);
		
	}
}


// Metodo que sera llamado cuando presionamos en boton cambio de domicilio
var cambiarDomicilio = function() {
	
	var objDomicilio =  this;
	
	if(objDomicilio != undefined && objDomicilio != null) {
		
		try {
			
				$('#domicilio\\.clave').val(objDomicilio.clave);
				$('#domicilio\\.codigoPostal\\.codigoPostal').val(objDomicilio.codigoPostal.codigoPostal);
				$('#domicilio\\.asentamiento\\.clave').val(objDomicilio.asentamiento.clave);
				$('#domicilio\\.asentamiento\\.nombre').val(objDomicilio.asentamiento.nombre);
				$('#domicilio\\.asentamiento\\.localidad\\.clave').val(objDomicilio.asentamiento.localidad.clave);
				$('#domicilio\\.asentamiento\\.localidad\\.nombre').val(objDomicilio.asentamiento.localidad.nombre);
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val(objDomicilio.asentamiento.localidad.municipio.clave);
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.nombre);
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
				
				$('#domicilio\\.numExterior1').val(objDomicilio.numExterior1);
				$('#domicilio\\.numExteriorAlf').val(objDomicilio.numExteriorAlf);
				$('#domicilio\\.numInterior').val(objDomicilio.numInterior);
				$('#domicilio\\.numInteriorAlf').val(objDomicilio.numInteriorAlf);
				$('#domicilio\\.numExterior2').val(objDomicilio.numExterior2);
				// Si el tipo de vialidad para la vialidad primaria vienen nulo no
				// ponemos esos campos
				
				if(objDomicilio.vialidadPrimaria != null && objDomicilio.vialidadPrimaria != undefined) {
					$('#domicilio\\.vialidadPrimaria\\.clave').val(objDomicilio.vialidadPrimaria.clave);
					$('#domicilio\\.vialidadPrimaria\\.nombre').val(objDomicilio.vialidadPrimaria.nombre);
					
					if(objDomicilio.vialidadPrimaria.tipoVialidad != undefined && objDomicilio.vialidadPrimaria.tipoVialidad != null) {
						$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadPrimaria.tipoVialidad.clave);
						$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadPrimaria.tipoVialidad.descripcion);
					}
				} else {
					$('#domicilio\\.vialidadPrimaria\\.clave').val("");
					$('#domicilio\\.vialidadPrimaria\\.nombre').val("");
					$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val("");
					$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
				}
				
				if(objDomicilio.vialidadReferenciaPrimaria != null && objDomicilio.vialidadReferenciaPrimaria != undefined) {
					$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.clave);
					$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val(objDomicilio.vialidadReferenciaPrimaria.nombre);
					// Si el tipo de vialidad vienen nulo no ponemos esos campos
					if(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != null) {
						$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
						$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
					}
				} else {
					$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val("");
					$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
				}
				
				if(objDomicilio.vialidadReferenciaSecundaria != null && objDomicilio.vialidadReferenciaSecundaria != undefined) {
					$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.clave);
					$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val(objDomicilio.vialidadReferenciaSecundaria.nombre);
					// Si el tipo de vialidad vienen nulo no ponemos esos campos
					if(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != null) {
						$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
						$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
					}
				} else {
					$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val("");
					$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
				}
				
				if(objDomicilio.vialidadReferenciaPosterior != null && objDomicilio.vialidadReferenciaPosterior != undefined) {
					$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val(objDomicilio.vialidadReferenciaPosterior.clave);
					$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val(objDomicilio.vialidadReferenciaPosterior.nombre);
					// Si el tipo de vialidad vienen nulo no ponemos esos campos
					if(objDomicilio.vialidadReferenciaPosterior.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPosterior.tipoVialidad != null) {
						$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
						$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
					} 
				} else {
					$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val("");
					$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
					$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
				}
				
				$("#domicilio\\.calle").val(objDomicilio.calle);
				$("#domicilio\\.tipoBusquedaVialidad").val(objDomicilio.tipoBusquedaVialidad);
				
				if(objDomicilio.domicilioCarretera != undefined) {
					$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCarretera.terminoGeneral.descripcion);
					$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCarretera.terminoGeneral.clave);
					$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(objDomicilio.domicilioCarretera.derechoTransito.descripcion);
					$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val(objDomicilio.domicilioCarretera.derechoTransito.clave);
					$("#domicilio\\.domicilioCarretera\\.origen").val(objDomicilio.domicilioCarretera.origen);
					$("#domicilio\\.domicilioCarretera\\.destino").val(objDomicilio.domicilioCarretera.destino);
					$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val(objDomicilio.domicilioCarretera.administracion.descripcion);
					$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val(objDomicilio.domicilioCarretera.administracion.clave);
					$("#domicilio\\.domicilioCarretera\\.cadenamiento").val(objDomicilio.domicilioCarretera.cadenamiento);
					$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val(objDomicilio.domicilioCarretera.codigoCarretera);
				} else{
					$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
					$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
					$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
					$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
					$("#domicilio\\.domicilioCarretera\\.origen").val('');
					$("#domicilio\\.domicilioCarretera\\.destino").val('');
					$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
					$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
					$("#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
					$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');
				}
				
				if(objDomicilio.domicilioCamino != undefined) {
					$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCamino.terminoGeneral.descripcion);
					$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCamino.terminoGeneral.clave);
					$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val(objDomicilio.domicilioCamino.margen.descripcion);
					$("#domicilio\\.domicilioCamino\\.margen\\.clave").val(objDomicilio.domicilioCamino.margen.clave);
					$("#domicilio\\.domicilioCamino\\.origen").val(objDomicilio.domicilioCamino.origen);
					$("#domicilio\\.domicilioCamino\\.destino").val(objDomicilio.domicilioCamino.destino);
					$("#domicilio\\.domicilioCamino\\.cadenamiento").val(objDomicilio.domicilioCamino.cadenamiento);
				} else {
					$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
					$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
					$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
					$("#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
					$("#domicilio\\.domicilioCamino\\.origen").val('');
					$("#domicilio\\.domicilioCamino\\.destino").val('');
					$("#domicilio\\.domicilioCamino\\.cadenamiento").val('');
				}
			
			umfsByCodigoPostal(objDomicilio.codigoPostal.codigoPostal, "#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
			} catch(e) {}
	}
}

function validaCombos(){
	var respuesta=true;
	
	if(medicoEnTurnoActivo==null){
		if($("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex==0){
			colocarMensajeError($("#errorUmf"));
			respuesta=false;
		}else{
			limpiarMensajeError($("#errorUmf"))
		}
		if($("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex==0){
			colocarMensajeError($("#errorTurno"));
			respuesta=false;
		}else{
			limpiarMensajeError($("#errorTurno"))
		}
		if($("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex==0){
			colocarMensajeError($("#errorConsultorio"));
			respuesta=false;
		}else{
			limpiarMensajeError($("#errorConsultorio"))
		}
	}
	return respuesta;
}

function colocarMensajeError($div){
	$div.html('<p id="mensaje" style="color: red">&nbsp;&nbsp;Requerido</p>');
}
function limpiarMensajeError($div){
	$div.html('');
}
function colocarMensaje(mensaje){
	
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
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
		//$("#guiaTramiteValidacion").show();
	}else{
		$("#aceptarValidacion").hide(); 
		$("#regresarGrupoFamiliar").hide();
		$("#rechazarTramite").hide();
		$("#guiaTramiteValidacion").hide();
	}
	
}

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}

function mensajeConfirmacionA(mensaje) {
	
	$confirmacion = $('<div></div');
	$confirmacion.html(mensaje);
	$confirmacion.dialog({
		autoOpen : false,
		title: 'Mensaje del sistema',
		show: "blind",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			'Cerrar' : function () {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$confirmacion.dialog('open');
}


/**
 * Valida si las delegaciones son iguales.
 * 
 * El conjunto de id's 15,16,39.40 representa la misma delegaci�n
 * 
 * @param delegacion1
 * @param delegacion2
 * @returns {Boolean} True si la delegaci�n es la misma
 */
function mismaDelegacion(umfs,delegacionOrigen){
	
	var mismaCircunscripcion = true;
	
	for(var i=0; i< umfs.length; i++) {
		if(umfs[0].subdelegacion.delegacion.id != delegacionOrigen) {
			mismaCircunscripcion = false;
			break;
		}
	}
	
	return mismaCircunscripcion;
	
	/*
	try{
	
		delegacion1 = parseInt(delegacion1);
		delegacion2 = parseInt(delegacion2);
		
		
		if( delegacion1 == delegacion2 )
			return true;
		
		
//		if( ($.inArray(delegacion1,mismaCircunscripcion) >= 0) && ($.inArray(delegacion2,mismaCircunscripcion) >= 0) )
//			return true;
		
	}catch(e){
		
	}
	return false;*/
	
}

function validaCambioDomicilio(){
	
	if(validarDomicilioCapturadoComponente()){
		
		if(valdaDatosMinimosDomicilioAnteriorActualDiferentes("correccionDatos")){
			var tramiteCorreccion = $("#correccionDatos").toObject();
			
			umfsByCodigoPostal(tramiteCorreccion.domicilio.codigoPostal.codigoPostal, "#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
		}else{
			alert("No existen diferencias en los domicilios");
		}
	}else{
	
		return false;
		}
	}
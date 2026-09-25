// ------------------------------------------------------------------------
// Funciones que controlaran las funcionalidades comunes de los botones  
// que aparecen en el wizard
//
// Las particularidades de cada etapa del wizard y sus pantallas se 
// manejan en archivos particulares para cada pantalla
// ------------------------------------------------------------------------


/**
 *  Variables que haran referencia a los dialogos con los mensajes 
 *  comunes a las etapas del wizard 
 */
var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;
var dialogoFinalizar;
var dialogoConfirmarExistentes;


var wizardGeneralDomicilios = {
		
		idPersona : null,		
		idTipoTramite : null,
		cerrarDialogo : true,
		idOrigen : '${mvn.web.app.origin.id}',
		url : '/${mvn.web.app.root}'+'/wizard/domicilio/',
		
		/**
		 * Funcion auxiliar para cambiar la url 
		 */
		locationReplace : function(url){
			document.location.replace(wizardGeneralDomicilios.url + url + "/" + wizardGeneralDomicilios.idPersona );
		},
		
		/**
		 * Inicia una solicitud con un nuevo cp
		 */
		iniciarTramiteActualizacion : function() {
			$.blockUI();
			wizardGeneralDomicilios.locationReplace('actualizacion') ;	
		},
		
		/**
		 * Inicia una solicitud con un nuevo cp
		 */
		iniciarTramiteCP : function() {
			$.blockUI();
			wizardGeneralDomicilios.locationReplace('crearTramite/1') ;	
		},

		
		/**
		 * Inicia una solicitud utilizando el cp actualmente guardado en la BD
		 */
		iniciarTramiteSinCP : function() {
			$.blockUI();
			wizardGeneralDomicilios.locationReplace('crearTramite/0') ;	
		},

		
		/**
		 * Retoma una solicitud existente
		 * 
		 * Cuando se carga la pagina se env�an los datos de una solicitud a la forma
		 * "solicitudForm" 
		 * 
		 */
		retomar : function() {
			$.blockUI();
			var url = wizardGeneralDomicilios.url + 'retomar';
			
			$('#solicitudForm').attr('action',url);
			$('#solicitudForm').submit();
		},
		
		
		/**
		 * Valida la direccion capturada por el usuario y accede a la pantalla de 
		 * captura de documento probatorio y firma electr�nica
		 */
		siguiente : function(){
			
			try{
				
				if(!validarDomicilioCapturadoComponente())
					return false;
			}catch( e){
				
			}
			$.blockUI();
			
			// ----------------------------------------------------------------
			// Se valida y se guardan los datos del nuevo domicilio en sesi�n
			// ----------------------------------------------------------------
			var huboError = wizardGeneralDomicilios.validarCapturaDomicilio();
			
			if(!huboError) {
				var url =  wizardGeneralDomicilios.url + 'siguiente';
				document.location.replace(url);
			} else {
				$.unblockUI();
			}
			
		},
		
		
		/**
		 * Cierra el Wizard domicilio que se inicializo en la
		 * pagina principal
		 */
		cerrarWizard : function() {	
			parent.WizardDomicilioGeneralCtrl.cerrar();
		},
		
		
		/**
		 * Cancela una solicitud del tipo "actualizaci�n de datos generales"
		 * 
		 * Los tipos de tr�mites pueden ser:
		 * 	- Actualizaci�n de domicilio particular
		 *  - Asignaci�n de domicilio
		 *  - Cambio de clinica
		 */
		 cancelarTramite : function() {
			
			dialogoConfirmarCancelar.dialog( "close" );
			$.blockUI();
			
			var idSolicitudPendiente = $('#solicitudId').val();
			var url =  wizardGeneralDomicilios.url + 'solicitud/cancelar';
			
			$.postJSON(url, {solicitudId : idSolicitudPendiente}, function() {
				
			}).always(function(data){
				$.unblockUI();
				
				// ----------------------------------------
				// Mensaje dentro del dialogo a mostrar
				// ----------------------------------------
				$('#mensajeDialogo').text(data.mensaje);
				dialogoConfirmar.dialog('open');
			});
			
			
		},
		
		/**
		 * Se limpian las variables en el controlador de documentos probatorios
		 */
		limpiarIndicadoresDocumentosProbatorios : function () {	
			parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
		},

		
		/**
		 * 
		 * Env�a los datos de domicilio al controlador para que los valide.
		 * 
		 * En caso de ser v�lido el domicilio, el controlador lo agrega a la sesi�n
		 * 
		 * @returns Boolean true si hubo error
		 * 					false no hubo error
		 */
		validarCapturaDomicilio : function() {
			
			
			// --------------------------------------
			// Forma de clinica en el �ltimo paso
			// --------------------------------------
			var $formaUMF = $("form#formUMF");
			
			// --------------------------------------------------
			// Forma con el domicilio que se esta capturando
			// --------------------------------------------------
			var $formComplemento = $("form#formComplemento");
			
			// -----------------------------------------------
			// En el �ltimo paso ya no se valida el domicilio
			// -----------------------------------------------
			if( ($formaUMF.length > 0) || ($formComplemento.length == 0) )
				return false;
			
			fnHideErrores("form#formComplemento");
			
			var url = wizardGeneralDomicilios.url + 'validarDomicilio';
			var huboError = null;
			var oDomForm = wizardGeneralDomicilios.formToObject("formComplemento", true); 
			// --------------------------------------------------------------
			// Estos campos estan dentro de asentamiento, para
			// fines informativos se agregaron a la forma.
			// Se eliminan para que el binding con el modelo de Domicilio
			// no marque error
			// --------------------------------------------------------------
			delete oDomForm.municipio;
			delete oDomForm.entidadFederativa;
			
			//pasarAtributosDisabled(oDomForm);
			
			
			$.ajax({
				url: url,
		        type: "POST",
		        data: JSON.stringify(oDomForm),
		        dataType: "json",
		        async: false,
		        contentType: "application/json; charset=utf-8",
		        success:  function(data) {
		        	huboError = false;
				}
			}).error(function(resultado){
				fnProcesarErrores(resultado, "form#formComplemento");
				fnProcesarErroresCamposComp(resultado, "form#formComplemento");
				huboError = true;
			});
			
			return huboError;
		},
		
		
		/**
		 * Asigna el mensaje a la caja de dialogo de error y la muestra en pantalla
		 */
		mostrarMensajeError : function (mensaje) {
			$('#mensajeError').html(mensaje);
			dialogoError.dialog('open');
		},


		/**
		 * Asigna el mensaje a la caja de dialogo de confirmaci�n y la muestra en pantalla
		 * 
		 * Este dialog cierra el wizard de domicilios al presionar aceptar
		 */
		mostrarMensaje : function (mensaje, cerrarDialogo) {
			
			wizardGeneralDomicilios.cerrarDialogo = ( typeof(cerrarDialogo) === 'undefined' )? true : cerrarDialogo;
			$('#mensajeDialogo').html(mensaje);
			dialogoConfirmar.dialog('open');
		},
		
		
		/**
		 * Env�a los datos en pantalla al controlador para persistirlos en la base
		 * 
		 * Funciona para las pantallas de captura de domicilio y UMF
		 * 
		 * @param cerrarWizard si se debe cerrar el wizard al guardar
		 * 					   exitosamente el tramite
		 */
		guardarTramite : function(cerrarWizard) {
			
			try{
				if(!validarDomicilioCapturadoComponente())
					return false;
			}catch( e){
				
			}
			

			$.blockUI();
			
			if( typeof(cerrarWizard) !== 'undefined' ){
				if(typeof(cerrarWizard) !== 'boolean' ){
					cerrarWizard = false;
				}
			}else{
				cerrarWizard = false;
			}
			
			
			// ----------------------------------------------------------------
			// Se valida y se guardan los datos del nuevo domicilio en sesi�n
			// ----------------------------------------------------------------
			var huboError = wizardGeneralDomicilios.validarCapturaDomicilio();
			if(!huboError) {
				
				var tramiteRegistro = {};
				var url = wizardGeneralDomicilios.url + 'guardar';
				
				
				// ---------------------------------------------
				// Forma con los datos de la clinica
				// Oculta los span.error dentro del formulario
				// ---------------------------------------------
				fnHideErrores("form#formUMF");
				
				// ---------------------------------------------
				// Forma con los datos del domicilio
				// Oculta los span.error dentro del formulario
				// ---------------------------------------------
				fnHideErrores("form#formComplemento");
				
				
				if( $("form#formUMF").length > 0 ){
					
					// -----------------------------------------------
					// Forma con los datos de la clinica
					// -----------------------------------------------
					tramiteRegistro = wizardGeneralDomicilios.formToObject("formUMF", true);
					
				}
				
				if( $("form#formaDatosTramite").length > 0 ){
					// -----------------------------------------------
					// Forma con los datos del tramite
					// -----------------------------------------------
					tramiteRegistro = wizardGeneralDomicilios.formToObject("formaDatosTramite", true);
				}
				
				
				if( $("form#formComplemento").length > 0 ){
					
					// ---------------------------------------------------------
					// Domicilio que esta capturando el usuario
					// ---------------------------------------------------------
					tramiteRegistro.domicilio = wizardGeneralDomicilios.formToObject("formComplemento", true);
					
					
					// --------------------------------------------------------------
					// Estos campos estan dentro de asentamiento, para
					// fines informativos se agregaron a la forma.
					// Se eliminan para que el binding con el modelo de Domicilio
					// no marque error
					// --------------------------------------------------------------
					delete tramiteRegistro.domicilio.municipio;
					delete tramiteRegistro.domicilio.entidadFederativa;
					
					
					// ---------------------------------------
					// Se apreto guardar en el paso 1
					// ---------------------------------------
					tramiteRegistro.paso = 2;
					
				}
			
				
				return $.postJSON(url, tramiteRegistro , function(data) {
					
					if(data.error != undefined && data.error != null && data.error != "") {
						wizardGeneralDomicilios.mostrarMensajeError(data.error);
					} else {
						wizardGeneralDomicilios.mostrarMensaje(data.mensaje, cerrarWizard);
					}
					
				}).error(function(data){
					wizardGeneralDomicilios.mostrarMensajeError(data.mensaje);
				}).always(function(){
					$.unblockUI();
				});
				
				
				
			} else {
				
				// ------------------------------------------------------------------
				// Si el domicilio no es v�lido simplemente se muestran los errores
				// ------------------------------------------------------------------
				$.unblockUI();
			}
					
		},

		
		/**
		 * Devuelve una forma como objeto json, incluyendo los
		 * campos disabled
		 */
		formToObject : function(idForm, withDisabled){
			
			
			withDisabled = ( typeof(withDisabled) === 'undefined'  )?false  : withDisabled;
			
			var myform = $('form#'+idForm);
			var disabled = null;
			
			if( withDisabled )
				disabled = myform.find(':input:disabled').removeAttr('disabled');
			
			var serialized = myform.toObject();
			
			if( withDisabled )
				disabled.attr('disabled','disabled');
			
			return serialized;
			
		},
		
		
		
		/**
		 * Muestra el applet para validar la firma electr�nica
		 */
		invocarFirmaDigital : function() {
			
			var numeroArchivos = 0;
			var requiereDoctos= false;
			
			
			parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
				
				if(parent.FirmaDigitalCtrl.datosSalida == null) {
					wizardGeneralDomicilios.mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada", false);
				}else {
					if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
						var firmaResponse = {
							cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
							recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
							reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
							urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
							serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
							strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
							strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
						};

						wizardGeneralDomicilios.firmarTramite(firmaResponse);
					} else {
						wizardGeneralDomicilios.mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada", false);
					}
				}
			});
			
			var componenteFirma = {
				tipo_operacion :'firmaCMS',
				acuse:'AcuseV1.0',
				rfc: parent.FirmanteCtrl.rfc,
				validarRFC :true,
				curp: parent.FirmanteCtrl.curp,
				firma_archivo : requiereDoctos,
				min_archivos : numeroArchivos,
				max_archivos : numeroArchivos,
				fechaElectronica : datosEntradaFirma.fechaElectronica,
				cad_original:$('#contenidoFirmar').val(),
				registroPatronal : "",
				nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
				idTipoSolicitud : codigoTipoSolicitud,
				descripcionTipoSolicitud : descripcionTipoSolicitud,
				folioSolicitud : $('#hdnFolioSolicitud').val(),
				idTipoTramite : arrayCodigoTipoTramite
			};
			
			parent.iniciarFirmaDigital(componenteFirma);
			
		},
		
		
		
		/**
		 * 	Salva la respuesta de la firma en una variable de session 
		 */
		firmarTramite : function(firmaResponse) {
			var url = wizardGeneralDomicilios.url + 'procesarDatosFirma';

			$.blockUI();
			$.postJSON(url, firmaResponse, function(data) {
				$.unblockUI();
				wizardGeneralDomicilios.finalizarTramite();
			}).error(function(data){
				$.unblockUI();
				$('#mensajeDialogo').text(data.mensaje);
				dialogoConfirmar.dialog('open');
			});
		},
		
		
		
		/**
		 * Finaliza el tramite
		 * 
		 * Ya se validaron los documentos probatorios y la firma electr�nica
		 */
		finalizarTramite : function() {
			
			var url = wizardGeneralDomicilios.url + 'finalizar';
			var formaUMF = {};
			
			
			// -----------------------------------------------------
			// Si existe la forma de UMF serializamos su contenido
			// -----------------------------------------------------
			if( $("form#formUMF").length > 0 ){
				formaUMF =  wizardGeneralDomicilios.formToObject("formUMF",true);
			}

			if (parent.mostrarProcesando) {
				var folioSolicitud = $('#hdnFolioSolicitud').val();
				parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud);
			}
			$.blockUI();
			
			$.postJSON(url, formaUMF, function(data) {
				if( (typeof(data.error) !== 'undefined') && data.error == false) {
					
					var buttons = dialogoFinalizar.dialog("option", "buttons"); 
					buttons.push({
						text : 'Ver documentos',
						click : function() {
							parent.WizardDomicilioGeneralCtrl.setOnClose(function(options){
								if(wizardGeneralDomicilios.idOrigen != 6){
									parent.ejecutarConsultaSolicitudPorFolio(options.noFolioSolicitud);
								}else{
									parent.mostrarDocumentos('divMostrarDocumentos',$("#hdnFolioSolicitudCifrado").val());
								}
								
							}).setFolioSolicitud($('#hdnFolioSolicitud').val()).cerrar();
						}
					});
				
					dialogoFinalizar.dialog("option", "buttons", buttons); 
					dialogoFinalizar.dialog("option", "width","400px");
				}
			
			}).error(function(data){
				
			}).always(function(data){
				//Cerrar Wizard
				parent.WizardDomicilioGeneralCtrl.cerrar();
								
				if (parent.mostrarProcesando) {
					parent.ProcesandoSolicitudCtrl.cerrar();
				}			
				
				if(parent.ProcesandoSolicitudCtrl.config.abrirDialogoExito != undefined &&
				   parent.ProcesandoSolicitudCtrl.config.abrirDialogoExito === true){
					$('#mensajeDialogoFinalizar').html(data.mensaje);
					dialogoFinalizar.dialog('open');
				}
				
				$.unblockUI();
			});
		},



		
		
		
		
		/**
		 * Abre el wizard de captura de documentos
		 */
		capturarDocumentos : function(){
			
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			var curpDocumento = $("#fisica\\.curp").val();
			var documentosANoMostrar = "0";
			
			// ---------------------------------------------------------
			// La base no tiene registros para asignacion de domicilio
			// ---------------------------------------------------------
			if(parseInt(tipoTramite) == 101)
				tipoTramite = 6;
				
			if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
				wizardGeneralDomicilios.mostrarMensajeDocumentosExistentes();
			} else {
				var curpCap = curpDocumento == "" ? null : curpDocumento;
				var datosComplementarios = {'curp' : curpCap, 'documentosNoMostrados': documentosANoMostrar};
				
				parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite,datosComplementarios);
				parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			}
			
			
		},
		
		
	
		/**
		 * Muestra un dialogo indicando que se sobreescribiran los 
		 * documentos existentes que ya se han capturado
		 */
		mostrarMensajeDocumentosExistentes : function() {
			
			dialogoConfirmarExistentes.dialog("option", "buttons", [ {
				text : 'CONTINUAR',
				click : function() {
					
					$(this).dialog('close');
					
					var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
					var idTramite = $("#tramiteId").val();
					parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
					parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
					parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
					
				}
			}, {
				text : 'Cancelar',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			$('#mensajeDialogoExistentes').html("Se eliminaran los datos de los documentos que ya se han capturado");
			dialogoConfirmarExistentes.dialog('open');
		}

	

};



/**
 * Configura los dialogos y asigna funcionalidad a los botones comunes
 * 
 * del wizard
 */
$(document).ready(function() {
		
	
	/**
	 * Inicia un tramite nuevo
	 */
	$('#btnInciaTramite').click(function(){
		wizardGeneralDomicilios.iniciarTramiteCP();
	});
	
	$('#btnElegirTipoActualizacion').click(function(){
		wizardGeneralDomicilios.iniciarTramiteActualizacion();
	});
	
	
	/**
	 * Inicia el tramite utilizando otro cp 
	 */
	$('#btnInciaTramiteCP').click(function(){
		wizardGeneralDomicilios.iniciarTramiteCP();
	});
	
	/**
	 * Inicia tramite utilizando el cp que tiene en BD
	 */
	$('#btnInciaTramiteSinCP').click(function(){
		wizardGeneralDomicilios.iniciarTramiteSinCP();
	});
	
	
	
	/**
	 * Retoma una solicitud existente que no se ha finalizado
	 */
	$('#btnRetomarTramite').click(function(){
		wizardGeneralDomicilios.retomar();
	});
	
	/**
	 * Cierra el wizard antes de crear una solicitud
	 */
	$('#btnInicioCancelarTramite').click(function(){
		wizardGeneralDomicilios.cerrarWizard();
	});
	
	/**
	 * Despu�s de capturar el domicilio se muestran los datos en una pantalla
	 * 
	 */
	$('#siguiente').click(function(){
		wizardGeneralDomicilios.siguiente();
	});
	
	/**
	 * Solicita confirmaci�n y cancela el tr�mite
	 */
	$('#btnCancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	
	/**
	 * Cierra el wizard que se inicalizo en la p�gina principal
	 */
	$('#cerrarWizard').click(function() {
		wizardGeneralDomicilios.cerrarWizard();
	});
	
	
	/**
	 * Guarda el tr�mite y cierra el wizard
	 */
	$("#guardarCerrarTramite").on("click",function(){
		wizardGeneralDomicilios.guardarTramite(true);
	});
	
	/**
	 * 
	 * Toma los datos que el usuario tiene en pantalla y los guarda
	 * en la base para poder retomar el tr�mite posteriormente
	 * 
	 */
	$('#btnGuardarTramite').click(function() {
		wizardGeneralDomicilios.guardarTramite();
	});
	
	/**
	 * Valida el documento probatorio
	 * Invoca a la firma electronica
	 * Finaliza el tramite
	 * 
	 */
	$("#finalizarTramite").click(function() {
		
		// --------------------------------------------------------------
		// Esperamos al resultado de la validaci�n para perdir la firma
		// electr�nica o mostrar el mensaje de error
		// --------------------------------------------------------------
		var deferredFinal = $.Deferred();
	
		
		deferredFinal.done(function(data){
			
			if($("#requiereDocs").val() == 1 && !parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada() ){
				wizardGeneralDomicilios.mostrarMensajeError("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
			} else {
				if(wizardGeneralDomicilios.idOrigen == 2){
					wizardGeneralDomicilios.invocarFirmaDigital();
				}else{
					wizardGeneralDomicilios.finalizarTramite();
				}
				
			}
			
		}).fail(function(data){
			
			// ----------------------------------------
			// En caso de que la validaci�n falle
			// ----------------------------------------
			wizardGeneralDomicilios.mostrarMensaje("La informaci&oacute;n de UMF es incorrecta", false);
			
			
		});
		
		validarUmf(deferredFinal);
	});
	
	
	
	$('#capturarDocumentos').click(function() {
		wizardGeneralDomicilios.capturarDocumentos();
	});

	
	
	/**
	 * Muestra un mensaje preguntando si esta seguro de querer cancelar la
	 * solicitud. 
	 * 
	 * En caso afirmativo se llama a la funci�n de cancelaci�n de tr�mite
	 * 
	 */
	if( $( "#dialog-confirm-cancelar" ).length > 0 ){
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
			buttons: {
				"Cancelar": function() {
			 		$( this ).dialog( "close" );
			 	},
				"Aceptar": function() {
					wizardGeneralDomicilios.cancelarTramite();
			 	}
			 },
			 beforeClose: function(event, ui) { 
				
			 }
		 });
	}
	
	
	/**
	 * Muestra un mensaje y cierra el wizard de domicilios
	 * 
	 */
	if( $( "#dialog-confirm" ).length > 0 ){
		dialogoConfirmar = $( "#dialog-confirm" ).dialog({
			resizable: false,
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
			buttons: {
				"Aceptar": function() {
					if( wizardGeneralDomicilios.cerrarDialogo )
						wizardGeneralDomicilios.cerrarWizard();
					$(this).dialog('close');
			 	}
			 },
			 beforeClose: function(event, ui) { 
				 wizardGeneralDomicilios.cerrarDialogo = true;
			 }
		 });
		
	}
	
	
	
	/**
	 * Comunuica errores al usuario
	 * 
	 */
	if( $( "#dialog-error" ).length > 0 ){
		dialogoError = $( "#dialog-error" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
		    buttons: {
		    	"Aceptar": function() {
			 		$( this ).dialog( "close" );
			 		
			 	}
			},
			beforeClose: function(event, ui) { 
				
	    	}
		 });
	}
	
	
	
	/**
	 * Dialog para preguntar si desea sobreescribir los documentos ya capturados
	 * 
	 */
	if( $( "#dialog-confirm-existentes" ).length > 0 ){
		dialogoConfirmarExistentes = $( "#dialog-confirm-existentes" ).dialog({
			resizable: false,
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false			
		 });
		
		
	}
	
	/**
	 * Dialog para preguntar si desea sobreescribir los documentos ya capturados
	 * 
	 */
	if( $( "#dialog-finalizar" ).length > 0 ){
		dialogoFinalizar = $( "#dialog-finalizar" ).dialog({
			resizable: false,
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
		    buttons : [{
				text : 'Aceptar',
				click : function() {
					$(this).dialog('close');
					wizardGeneralDomicilios.cerrarWizard();
				}
		    }]
		 });
		
	}
	
	
	
	// --------------------------------------------------------------------
	// El formulario de direcci�n es mas grande que el wizard inicial
	// Se aumenta la altura del iframe que contiene los datos de direcci�n
	// --------------------------------------------------------------------
	//set_size('wizardDatosActualizacionFrame', 1300);
	
	
	
	$('#vialidadPrimaria\\.clave').change(function(){
		$('#vialidadPrimaria\\.nombre\\.hidden').val($('#vialidadPrimaria\\.clave option:selected').text());
		$('#vialidadPrimaria\\.clave\\.hidden').val($('#vialidadPrimaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaPrimaria\\.clave').change(function(){
		$('#vialidadReferenciaPrimaria\\.clave\\.hidden').val($('#vialidadReferenciaPrimaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaSecundaria\\.clave').change(function(){
		$('#vialidadReferenciaSecundaria\\.clave\\.hidden').val($('#vialidadReferenciaSecundaria\\.clave option:selected').val());
	});
	
	$('#vialidadReferenciaPosterior\\.clave').change(function(){
		$('#vialidadReferenciaPosterior\\.clave\\.hidden').val($('#vialidadReferenciaPosterior\\.clave option:selected').val());
	});
	
	marcarCamposConErroresFromSpanErrors("formRegistro",".error");
	setEventosChecarErrorCampo("formComplemento");
});


function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return personaAfectada;
}



function asignarDomicilioExistente(){
	$.unblockUI();
		var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#folioSolicitud').val(),
			curp : datosEntradaFirma.curp,
			rfc : datosEntradaFirma.rfc,
			validarRFC : true,
			registroPatronal : datosEntradaFirma.registroPatronal,
			nombreCompleto : datosEntradaFirma.nombreCompleto,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			domicilioActualizado: nuevoDomicilio,
			afectado: [construirAfectado()],
			tipoAcuse: '1',
			acuse: 'CDP'
		};
		parent.iniciarFirmaDigital(componenteFirma);
}



function asignarDomicilio(url, isFinalizar){
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	url += idSolicitudPendiente;
	
	$.postJSON(url, function(resultado) {
		
		$.unblockUI();
		
		if (isFinalizar) {
			parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
			cerrarWizard();
		} else {
			$('#mensajeDialogo').text(resultado.mensaje);
			dialogoConfirmarCommon.dialog('open');
		}
		
	}).error(function(resultado){
		fnProcesarErrores(resultado, "form#formComplemento");
	});
}


function fnProcesarErroresCamposComp(data, contenedor){
	  var objErrores = jQuery.parseJSON(data.responseText);
	  var form = $(contenedor);
	 
	  for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
		  var campo = objErrores.erroresCaptura[index].campo;
		  if(campo == 'asentamiento.localidad.clave'){
			  campo = 'vialidadPrimariaInput';
		  }
			var cssBorder = "1px solid red"
			var cssColor = "red"
			
			//si existe error se buscaran los campos y el requerido
			var idSpanRequired = campo+'Req'
			var idCampoError = campo

			//se obtiene asi para evitar escapar lso caracteres
			var requiredRelacionado = document.getElementById(idSpanRequired);
			var campoRelacionado = document.getElementById(idCampoError);

			//si el campo existe se marcara en rojo
			if(campoRelacionado != undefined && campoRelacionado != null) {
				campoRelacionado.style.border = cssBorder;
			}

			if(requiredRelacionado != undefined && requiredRelacionado != null) {
				requiredRelacionado.style.color = cssColor;
			}
			tieneError = true
	  }
	  pintarErrorGeneral(tieneError);
}


function setEventosChecarErrorCampo(idFormulario) {
	$("#"+idFormulario+" :text").change(function() {
		verificarPersistenciaDeErrorCampo(this);
	});
	
	$("#"+idFormulario+" select").change(function() {
		verificarPersistenciaDeErrorCampo(this);
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		verificarPersistenciaDeErrorCampo(this);
	});
}

function verificarPersistenciaDeErrorCampo(element) {
	
		var campo = element.name;
		
		if(campo == 'vialidadPrimaria.nombre'){
			campo = 'vialidadPrimariaInput';
		}
	
		var idSpanRequired = campo+"Req";
		var idCampoError = campo;

		//se obtiene asi para evitar escapar lso caracteres
		var requiredRelacionado = document.getElementById(idSpanRequired);
		var campoRelacionado = document.getElementById(idCampoError);

		//si el campo existe se marcara en rojo
		if(campoRelacionado != undefined && campoRelacionado != null) {
			campoRelacionado.style.border = "1px solid #ccc";
		}

		if(requiredRelacionado != undefined && requiredRelacionado != null) {
			requiredRelacionado.style.color = "black";
		}
}

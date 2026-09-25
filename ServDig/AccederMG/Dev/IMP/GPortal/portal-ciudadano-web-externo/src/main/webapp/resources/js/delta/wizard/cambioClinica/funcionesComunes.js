// ------------------------------------------------------------------------
// Funciones que controlaran las funcionalidades comunes de los botones  
// que aparecen en el wizard
//
// Las particularidades de cada etapa del wizard y sus pantallas se 
// manejan en archivos particulares para cada pantalla
// ------------------------------------------------------------------------


var wizardGeneralClinica = {
		
		url : context_path+"/clinica/",
		dialogoConfirmar : null,
		dialogoConfirmarExistentes : null,
		scrollTop : 0,
		
		
		/**
		 * Funcion auxiliar para cambiar la url 
		 */
		locationReplace : function(url,callback){
			WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			WizardCambioClinicaGeneralCtrl.cargarPagina(url, callback);
		},
		
		
		
		/**
		 * Cierra el Wizard clinica que se inicializo en la
		 * pagina principal
		 */
		cerrarWizard : function() {	
			WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			WizardCambioClinicaGeneralCtrl.cerrar();
		},
		
	
		
		/**
		 * Asigna el mensaje a la caja de dialogo de confirmación y la muestra en pantalla
		 * 
		 * @param textoBoton texto del bot&oacute;n 
		 * @param callback Funcion a ejecutar antes de cerrar el dialogo
		 * 
		 */
		mensaje : function (mensaje, textoBoton, callback) {
			
			var _textoBoton = "ACEPTAR";
			
			wizardGeneralClinica.configurarDialogMensaje();
			
			
			if( (typeof(textoBoton) == 'string')  && (textoBoton.length > 0) ){
				_textoBoton = textoBoton;
			}
			
			var botones = wizardGeneralClinica.dialogoConfirmar.dialog('option', 'buttons');
			botones[0].text=_textoBoton;
			wizardGeneralClinica.dialogoConfirmar.dialog('option', 'buttons', botones);
			
			
			if( $.isFunction(callback) ){
				wizardGeneralClinica.dialogoConfirmar.dialog({
					beforeClose: callback
				});
			}else{
				wizardGeneralClinica.dialogoConfirmar.dialog({
					beforeClose: function(event, ui) {  }
				});
			}
			
			
			wizardGeneralClinica.dialogoConfirmar.find("#mensajeDialogo").html(mensaje);
			wizardGeneralClinica.dialogoConfirmar.dialog('open');
			
		},
		
		
		
		/**
		 * Valida y configura el dialogo para los mensajes al usuario
		 * 
		 */
		configurarDialogMensaje : function(){
			
			if( wizardGeneralClinica.dialogoConfirmar == null ){
			
				
				wizardGeneralClinica.dialogoConfirmar = $('<div></div>',{
					id: "dialogo-mensaje",
					title:"Mensaje confirmaci\u00F3n",
				}).append($('<p></p>').append($('<span></span>',{
					class:"ui-icon ui-icon-alert",
					style:"float: left; margin: 0 7px 45px 0;"
				})).append($('<label></label>',{
					id:"mensajeDialogo"
				})));

				
				wizardGeneralClinica.dialogoConfirmar.dialog({
					resizable : false,
					modal : true,
					autoOpen : false,
					dialogClass : "no-close",
					closeOnEscape : false,
					buttons:[{	
						text: "ACEPTAR",
			            click: function() {
			            	$(this).dialog('close');
			            		
			            }
			         }]
				});
					
			}
			
		}, 
		
		
		
		
		/**
		 * Muestra un dialogo de confirmación 
		 * 
		 * @param mensaje Mensaje a mostrar 
		 * @param callback Funcion a ejecutar si decide continuar
		 * @param textoBotones Arreglo con el texto para los botones
		 * 
		 */
		mensajeConfirmacion : function(mensaje, botones) {
			
			var _botones =[{
				text : 'CONTINUAR',
				click : function() {
					$(this).dialog('close');
				}
			},{
				text : 'CANCELAR',
				click : function() {
					$(this).dialog('close');
				}
			}];
			
			wizardGeneralClinica.configurarDialogConfirmar();
			wizardGeneralClinica.dialogoConfirmarExistentes.find("#mensajeDialogoConfirmar").html(mensaje);
			
			
			if( $.isArray(botones)  && (botones.length > 0) ){
				_botones = botones;
			}
			
			wizardGeneralClinica.dialogoConfirmarExistentes.dialog('option', 'buttons', _botones);
			wizardGeneralClinica.dialogoConfirmarExistentes.dialog('open');
			
		},
		
		
		
		/**
		 * Valida y configura el dialogo para los mensajes al usuario
		 * 
		 */
		configurarDialogConfirmar : function(){
			
			if( wizardGeneralClinica.dialogoConfirmarExistentes == null ){
			
				
				wizardGeneralClinica.dialogoConfirmarExistentes = $('<div></div>',{
					id: "dialogo-confirmar"
				}).append($('<p></p>').append($('<span></span>',{
					class:"ui-icon ui-icon-alert",
					style:"float: left; margin: 0 7px 45px 0;"
				})).append($('<label></label>',{
					id:"mensajeDialogoConfirmar"
				})));
				
				
				wizardGeneralClinica.dialogoConfirmarExistentes.dialog({
					resizable : false,
					modal : true,
					title:"Mensaje confirmaci\u00F3n",
					autoOpen : false,
					dialogClass : "no-close",
					closeOnEscape : false,
					buttons:[{
						text : 'CONTINUAR',
						click : function() {
							$(this).dialog('close');
						}
					},{
						text : 'CANCELAR',
						click : function() {
							$(this).dialog('close');
						}
					}]
				});
				
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
		 * Finaliza el tramite
		 * 
		 * Ya se validaron los documentos probatorios
		 */
		finalizarTramite : function() {
			
			var url = wizardGeneralClinica.url + 'finalizar';
			var formaUMF =  wizardGeneralClinica.formToObject("formUMF",true);
			
			$.postJSON(url, formaUMF, function(data) {
				
				
				if( (typeof(data.error) !== 'undefined') && data.error == false) {
					
					if( (typeof(data.beneficiarios) !== 'undefined') && data.beneficiarios == true) {
						
						wizardGeneralClinica.locationReplace("clinicaBeneficiarios",function(options){
							$(document).scrollTop($('#' + options.contenedor).offset().top - 100);
							$(this).css("height","auto");
						});
						
					}else{
					
						wizardGeneralClinica.mensajeConfirmacion(data.mensaje,[ 
						/*{
							text : 'CERRAR',
							click : function() {
								$(this).dialog('close');
								wizardGeneralClinica.cerrarWizard();
							}
						},*/{
							text : 'VER DOCUMENTOS',
							click : function() {
								$(this).dialog('close');
								WizardCambioClinicaGeneralCtrl.setOnClose(function(options){
									mostrarDocumentos("divMostrarDocumentos",options.noFolioSolicitud);
								}).setFolioSolicitud($('#hdnFolioSolicitudCifrado').val());
								
								wizardGeneralClinica.cerrarWizard();
							}
						}]); 
								
					}		
					
				}
			
			}).fail(function($xhr) {
				var data = jQuery.parseJSON($xhr.responseText);
			    wizardGeneralClinica.mensaje(data.mensaje);
			}).always(function(data){
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
				
			if(!WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
				
				wizardGeneralClinica.mensajeConfirmacion("Se eliminaran los datos de los documentos que ya se han capturado",[ {
					text : 'CONTINUAR',
					click : function() {
						$(this).dialog('close');
						wizardGeneralClinica.pintaListaCargados({documenProbatorioCapturaList:[]});
						
						var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
						var idTramite = $("#tramiteId").val();
						
						if(parseInt(tipoTramite) == 101)
							tipoTramite = 6;
						
						WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
						WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
						WizardCapturaDocumentosProbatoriosCtrl.abrir();
						
					}
				},{
					text : 'CANCELAR',
					click : function() {
						$(this).dialog('close');
					}
				}]); 
					
				
			} else {
				var curpCap = curpDocumento == "" ? null : curpDocumento;
				var datosComplementarios = {'curp' : curpCap, 'documentosNoMostrados': documentosANoMostrar};
				
				WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite,datosComplementarios);
				WizardCapturaDocumentosProbatoriosCtrl.abrir();
			}
			
			
		},
			
		
		/**
		 * Valida la curp del beneficiario
		 * 
		 *	@param curp 
		 */
		validarCurpBeneficiario : function(curp){
			
				$.blockUI();
				$.postJSON(wizardGeneralClinica.url + "beneficiario",{curpCap:curp}, function(data) {
					if( data.estado ){
						wizardGeneralClinica.locationReplace("cambio",function(){
							$(this).css("height","auto");
						});
					}else{
						
						$.unblockUI();
						if( data.modelo  ){
							wizardGeneralClinica.mensaje(data.mensaje,null,function(){
								wizardGeneralClinica.cerrarWizard();
							});
							
						}else{
							wizardGeneralClinica.mensaje(data.mensaje);
						}
						
						
					}
				}).fail(function($xhr) {
					var data = jQuery.parseJSON($xhr.responseText);
				    wizardGeneralClinica.mensaje(data.mensaje);
					$.unblockUI();
				}).always(function(){
					
				});
			
			
			
			
		},
		
		
		/**
		 * Valida las clinicas disponibles para el nuevo código postal
		 *  
		 */
		validarDomicilio : function(){
			
			var objDomicilio =  this;
			if(objDomicilio != undefined && objDomicilio != null) {
			
				$.blockUI();
				$.postJSON(wizardGeneralClinica.url + "umfCp", objDomicilio, function(data) {
					
					limpiarDatosDeAdscripcion();
					$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex = 0;
					
					if( (typeof(data.error) == 'boolean') && !data.error ){
						
						$("#cpBusquedaUmf").val(objDomicilio.codigoPostal.codigoPostal);
						$("#clinicaBotones").show();
						$("#documentosProbatorios").show();
						$("#formUMF").show();
						$("#domicilio").show();
						
						wizardGeneralClinica.cambiarDomicilio.call(objDomicilio);
						$("form#domicilio").deshabilitarContenido(true);
						getUmfsDisponibles();
						
					}else{
						$("#cpBusquedaUmf").val(objDomicilio.codigoPostal.codigoPostal);
						$("#clinicaBotones").hide();
						$("#documentosProbatorios").hide();
						$("#formUMF").hide();
						$("#domicilio").hide();
						
						wizardGeneralClinica.mensaje(data.mensaje);
						
					}
				}).fail(function($xhr) {
					var data = jQuery.parseJSON($xhr.responseText);
				    wizardGeneralClinica.mensaje(data.mensaje);
				}).always(function(){
					$.unblockUI();
				});
				
				
			}
			
		},
		
		
		/**
		 * Configura el wizard para capturar domicilios
		 */
		configurarWizardDomicilio :function(){
			
			DomicilioCtrl.init('divWizardDomicilio');
			DomicilioCtrl.setOnCloseCallback(wizardGeneralClinica.validarDomicilio);
			$('#ubicarDomicilioDer').click(function() {
				DomicilioCtrl.localizar();
			});
			
			
			$('div#divWizardDomicilio').on('dialogclose', function(event) {
				$(document).scrollTop(wizardGeneralClinica.scrollTop);
				WizardCambioClinicaGeneralCtrl.mostrar();
				
			}).on( "dialogopen", function( event) {
				wizardGeneralClinica.scrollTop = $(document).scrollTop();
				WizardCambioClinicaGeneralCtrl.ocultar();
			});
			
		},
		


		cambiarDomicilio : function() {
			
			var objDomicilio =  this;
			
			if(objDomicilio != undefined && objDomicilio != null) {
				
				try {
					
						$('#clave').val(objDomicilio.clave);
						$('#codigoPostal\\.codigoPostal').val(objDomicilio.codigoPostal.codigoPostal);
						$('#asentamiento\\.clave').val(objDomicilio.asentamiento.clave);
						$('#asentamiento\\.nombre').val(objDomicilio.asentamiento.nombre);
						$('#asentamiento\\.localidad\\.clave').val(objDomicilio.asentamiento.localidad.clave);
						$('#asentamiento\\.localidad\\.nombre').val(objDomicilio.asentamiento.localidad.nombre);
						$('#asentamiento\\.localidad\\.municipio\\.clave').val(objDomicilio.asentamiento.localidad.municipio.clave);
						$('#asentamiento\\.localidad\\.municipio\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.nombre);
						$('#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
						$('#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
						
						$('#numExterior1').val(objDomicilio.numExterior1);
						$('#numExteriorAlf').val(objDomicilio.numExteriorAlf);
						$('#numInterior').val(objDomicilio.numInterior);
						$('#numInteriorAlf').val(objDomicilio.numInteriorAlf);
						$('#numExterior2').val(objDomicilio.numExterior2);
						// Si el tipo de vialidad para la vialidad primaria vienen nulo no
						// ponemos esos campos
						
						if(objDomicilio.vialidadPrimaria != null && objDomicilio.vialidadPrimaria != undefined) {
							$('#vialidadPrimaria\\.clave').val(objDomicilio.vialidadPrimaria.clave);
							$('#vialidadPrimaria\\.nombre').val(objDomicilio.vialidadPrimaria.nombre);
							
							if(objDomicilio.vialidadPrimaria.tipoVialidad != undefined && objDomicilio.vialidadPrimaria.tipoVialidad != null) {
								$('#vialidadPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadPrimaria.tipoVialidad.clave);
								$('#vialidadPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadPrimaria.tipoVialidad.descripcion);
							}
						} else {
							$('#vialidadPrimaria\\.clave').val("");
							$('#vialidadPrimaria\\.nombre').val("");
							$('#vialidadPrimaria\\.tipoVialidad\\.clave').val("");
							$('#vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
						}
						
						if(objDomicilio.vialidadReferenciaPrimaria != null && objDomicilio.vialidadReferenciaPrimaria != undefined) {
							$('#vialidadReferenciaPrimaria\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.clave);
							$('#vialidadReferenciaPrimaria\\.nombre').val(objDomicilio.vialidadReferenciaPrimaria.nombre);
							// Si el tipo de vialidad vienen nulo no ponemos esos campos
							if(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != null) {
								$('#vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
								$('#vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
							}
						} else {
							$('#vialidadReferenciaPrimaria\\.clave').val("");
							$('#vialidadReferenciaPrimaria\\.nombre').val("");
							$('#vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
							$('#vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
						}
						
						if(objDomicilio.vialidadReferenciaSecundaria != null && objDomicilio.vialidadReferenciaSecundaria != undefined) {
							$('#vialidadReferenciaSecundaria\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.clave);
							$('#vialidadReferenciaSecundaria\\.nombre').val(objDomicilio.vialidadReferenciaSecundaria.nombre);
							// Si el tipo de vialidad vienen nulo no ponemos esos campos
							if(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != null) {
								$('#vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
								$('#vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
							}
						} else {
							$('#vialidadReferenciaSecundaria\\.clave').val("");
							$('#vialidadReferenciaSecundaria\\.nombre').val("");
							$('#vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
							$('#vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
						}
						
						if(objDomicilio.vialidadReferenciaPosterior != null && objDomicilio.vialidadReferenciaPosterior != undefined) {
							$('#vialidadReferenciaPosterior\\.clave').val(objDomicilio.vialidadReferenciaPosterior.clave);
							$('#vialidadReferenciaPosterior\\.nombre').val(objDomicilio.vialidadReferenciaPosterior.nombre);
							// Si el tipo de vialidad vienen nulo no ponemos esos campos
							if(objDomicilio.vialidadReferenciaPosterior.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPosterior.tipoVialidad != null) {
								$('#vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
								$('#vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
							} 
						} else {
							$('#vialidadReferenciaPosterior\\.clave').val("");
							$('#vialidadReferenciaPosterior\\.nombre').val("");
							$('#vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
							$('#vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
						}
						
						$("#calle").val(objDomicilio.calle);
						$("#tipoBusquedaVialidad").val(objDomicilio.tipoBusquedaVialidad);
						
						if(objDomicilio.domicilioCarretera != undefined) {
							$("#domicilioCarretera\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCarretera.terminoGeneral.descripcion);
							$("#domicilioCarretera\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCarretera.terminoGeneral.clave);
							$("#domicilioCarretera\\.derechoTransito\\.descripcion").val(objDomicilio.domicilioCarretera.derechoTransito.descripcion);
							$("#domicilioCarretera\\.derechoTransito\\.clave").val(objDomicilio.domicilioCarretera.derechoTransito.clave);
							$("#domicilioCarretera\\.origen").val(objDomicilio.domicilioCarretera.origen);
							$("#domicilioCarretera\\.destino").val(objDomicilio.domicilioCarretera.destino);
							$("#domicilioCarretera\\.administracion\\.descripcion").val(objDomicilio.domicilioCarretera.administracion.descripcion);
							$("#domicilioCarretera\\.administracion\\.clave").val(objDomicilio.domicilioCarretera.administracion.clave);
							$("#domicilioCarretera\\.cadenamiento").val(objDomicilio.domicilioCarretera.cadenamiento);
							$("#domicilioCarretera\\.codigoCarretera").val(objDomicilio.domicilioCarretera.codigoCarretera);
						} else{
							$("#domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
							$("#domicilioCarretera\\.terminoGeneral\\.clave").val('');
							$("#domicilioCarretera\\.derechoTransito\\.descripcion").val('');
							$("#domicilioCarretera\\.derechoTransito\\.clave").val('');
							$("#domicilioCarretera\\.origen").val('');
							$("#domicilioCarretera\\.destino").val('');
							$("#domicilioCarretera\\.administracion\\.descripcion").val('');
							$("#domicilioCarretera\\.administracion\\.clave").val('');
							$("#domicilioCarretera\\.cadenamiento").val('');
							$("#domicilioCarretera\\.codigoCarretera").val('');
						}
						
						if(objDomicilio.domicilioCamino != undefined) {
							$("#domicilioCamino\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCamino.terminoGeneral.descripcion);
							$("#domicilioCamino\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCamino.terminoGeneral.clave);
							$("#domicilioCamino\\.margen\\.descripcion").val(objDomicilio.domicilioCamino.margen.descripcion);
							$("#domicilioCamino\\.margen\\.clave").val(objDomicilio.domicilioCamino.margen.clave);
							$("#domicilioCamino\\.origen").val(objDomicilio.domicilioCamino.origen);
							$("#domicilioCamino\\.destino").val(objDomicilio.domicilioCamino.destino);
							$("#domicilioCamino\\.cadenamiento").val(objDomicilio.domicilioCamino.cadenamiento);
						} else {
							$("#domicilioCamino\\.terminoGeneral\\.descripcion").val('');
							$("#domicilioCamino\\.terminoGeneral\\.clave").val('');
							$("#domicilioCamino\\.margen\\.descripcion").val('');
							$("#domicilioCamino\\.margen\\.clave").val('');
							$("#domicilioCamino\\.origen").val('');
							$("#domicilioCamino\\.destino").val('');
							$("#domicilioCamino\\.cadenamiento").val('');
						}
					
					} catch(e) {}
			}
		
		},
		

		/**
		 * Pinta la lista de documentos probatorios cargados
		 * 
		 */
		pintaListaCargados : function(data){

			var lProd=data.documenProbatorioCapturaList;
			var len = lProd.length;
			var doctoPorTipo;
			var pinta = "";
			
			if( len > 0 ){
				for(var i=0;i<len;i++){
					doctoPorTipo=lProd[i].documentoProbatorio.documentoPorTipo;
					pinta += "<tr>"+
					
							"<td>"+doctoPorTipo.tipoDocumentoProbatorio.descripcion+"</td>"+	
							"<td>"+doctoPorTipo.documento.desDocumento+"</td>"+
							"</tr>"
						;
				}
				
			}	
			
			$('#listaDocCargadosClinica > table tbody').html(pinta);
			
			
		},
		
		

		/**
		 * Valida los datos de adscripción
		 */
		validarUmf : function (deferredFinal) {
			
			var oForm = wizardGeneralClinica.formToObject("formUMF",true);
			var url = wizardGeneralClinica.url + 'validaAdscripcion';
			fnHideErrores("form#formUMF");
			
			$.postJSON(url, oForm, function(data2) {
				deferredFinal.resolve(data2);
			}).fail(function($xhr) {
				var data = jQuery.parseJSON($xhr.responseText);
				fnProcesarErrores($xhr, "form#formUMF");
				deferredFinal.reject(data);
			});
			
			
		},
		
		
		/**
		 * Cancelar la solicitud registrada y continuar con el tr&aacute;mite
		 * 
		 */
		cancelarYContinuar : function(mensaje){
			
			wizardGeneralClinica.mensajeConfirmacion(mensaje, [{
				text : 'CANCELAR',
				click : function() {
					$(this).dialog('close');
				}
			},{
				text : 'CONFIRMAR',
				click : function() {
					$(this).dialog('close');
					
					$.blockUI();
					$.postJSON(wizardGeneralClinica.url + "cancelarSolicitud",{},function(data) {
						
						if( data.estado ){
							
							wizardGeneralClinica.locationReplace(data.modelo,function(){
								$(this).css("height","auto");
							});
							
						}else{
							
							$.unblockUI();
							wizardGeneralClinica.mensaje(data.mensaje);
							
						}
					
					}).fail(function($xhr) {
						var data = jQuery.parseJSON($xhr.responseText);
					    wizardGeneralClinica.mensaje(data.mensaje);
						$.unblockUI();
					});
					
					
				}
			}]); 
			
		}


	

};







/**
 * Configura los dialogos y asigna funcionalidad a los botones comunes
 * 
 * del wizard
 */
$(document).ready(function() {
		
	
	/**
	 * Valida el documento probatorio
	 * Finaliza el tramite
	 * 
	 */
	$("#finalizarTramite").click(function() {
		
		if( !WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada() ){
			wizardGeneralClinica.mensaje("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
		}else{
		
			var deferredFinal = $.Deferred();
			
			deferredFinal.done(function(data){
				wizardGeneralClinica.finalizarTramite();
			}).fail(function(data){
				$.unblockUI();
				wizardGeneralClinica.mensaje(data.mensaje);
			});
			
			$.blockUI();
			wizardGeneralClinica.validarUmf(deferredFinal);
		}
		
	});
	
	
	/**
	 * Cierra el wizard
	 * 
	 */
	$('#btnInicioCancelarTramiteClinica').click(function() {
		wizardGeneralClinica.cerrarWizard();
	});
	
	
	/**
	 * Lanza el wizard de documentos probatorios 
	 */
	$('#capturarDocumentos').click(function() {
		wizardGeneralClinica.capturarDocumentos();
	});

	
	/**
	 * Botón regresar para beneficiarios
	 */
	$("#clinicaBotonesRegresar").click(function(){
		
		wizardGeneralClinica.locationReplace("clinicaBeneficiarios",function(options){
			$(document).scrollTop($('#' + options.contenedor).offset().top - 100);
			$(this).css("height","auto");
		});
	});
	
	
	
	/**
	 * Cancelar el trámite y continuar
	 * 
	 */
	$('#btnIniciocancelarYContinuar').click(function() {
		var mensaje = $("#mensajeCancelarYContinuar").data("message");
		wizardGeneralClinica.cancelarYContinuar(mensaje);
	});
	
	
	

	/**
	 * Cierra el dialogo y le avisa que puede retomar el tr&aacute;mite en ventanilla
	 * 
	 */
	$('#btnInicioCancelarTramiteInternetClinica').click(function() {
		
		var mensaje = $("#mensajeCerrarWizard").data("message");
		wizardGeneralClinica.mensajeConfirmacion(mensaje, [{
			text : 'CONTINUAR',
			click : function() {
				$(this).dialog('close');
				wizardGeneralClinica.cerrarWizard();
			}
		}]); 
		
	});
	
	
	// ---------------------
	// Probatorios
	// ---------------------
	$('body').on("pintaListaCargados eliminaDoc",function(e,data){
		wizardGeneralClinica.pintaListaCargados(data);
	});
		
	
	var $clinicaBeneficiarios = $("#clinicaBeneficiariosForm");
	if( $clinicaBeneficiarios.length > 0  ){
		
		/**
		 * Valida la curp e inicia el trámite
		 */
		$('#btnInciaTramiteClinica').click(function() {
			if ($("#clinicaBeneficiariosForm").valid()) {
				wizardGeneralClinica.validarCurpBeneficiario($("#clinicaBeneficiariosForm input").val());
			}
			
		});
		
		
		$clinicaBeneficiarios.validate({
			 rules:{ 
				 curpBeneficiario: {
						curp: true,
						required:true,
						minlength: 18,
						maxlength: 18,
					}
				},
		 		messages: { 
		 			curpBeneficiario: {
						minlength: "Debe ser de 18 caracteres",
						maxlength: "Debe ser de 18 caracteres",
						curp: "Formato incorrecto",
						required:"Obligatorio"
					}
		 		}
		});
	
		
	}else{
		
		// ---------------------
		// Wizard domicilios
		// ---------------------
		wizardGeneralClinica.configurarWizardDomicilio();
		$("form#domicilio").deshabilitarContenido(false);
	}
	
	
	
	
	// -------------------------------------
	// Se muestran los combos de UMF
	// -------------------------------------
	if( $("#muestraUmf").data("value") ){
		$("#clinicaBotones").show();
		$("#documentosProbatorios").show();
		$("#formUMF").show();
	}
	
	
	if( $("#enCircunscipcionForanea").data("value") ){
		wizardGeneralClinica.mensaje($("#enCircunscipcionForanea").data("message"));
	}
	
});


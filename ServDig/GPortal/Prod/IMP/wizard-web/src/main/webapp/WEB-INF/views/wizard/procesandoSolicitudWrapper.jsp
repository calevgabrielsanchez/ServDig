<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script>
	var solicitudCometConn = new parent.CometCtrl();
	var cometSubscription = null;
	var retries = 0;
	var canal = null;
	var isSolicitudValidada = false;
	var codigoTipoDocumentoComprobante = <%=TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo()%>;
	var idTipoSolicitudAlta = <%=TipoSolicitudEnum.ALTA_PATRONAL.getValor()%>;
	var dialogoFinSolicitud;
	var estadoConcluido = <%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>;
	var estadoCancelado =  <%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>;
	var estadoEnProceso =  <%=EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()%>;
	var estadoIniciada =  <%=EstadoSolicitudEnum.REGISTRADA.getCodigo()%>;
	
	$(function() {
		
		var folioSolicitud = $('#hdnFolioSolicitud').val();	
		canal = '/solicitud/modificacion/' + folioSolicitud;
		
		conectarComet(canal);
							
		var dialogoConfirmarCancelar = $("#dialog-confirm-cancelar").dialog({
			resizable : false,
			height : 'auto',
			modal : true,
			autoOpen : false,
			buttons : {
				"Cancelar" : function() {
					$(this).dialog("close");
				},
				"Aceptar" : function() {
					parent.ProcesandoSolicitudCtrl.cerrar();
				}				
			}
		});

		dialogoFinSolicitud = $("#dialog-solicitud-exito").dialog({
			resizable : false,
			height : 'auto',
			width : '50%', 
			modal : true,
			closeOnEscape: false,
			autoOpen : false,
			open : function(event, ui) {
				$(this).parent().children().children(".ui-dialog-titlebar-close").hide();
			},
			buttons : {
				"Aceptar" : function() {
					/* isSolicitudValidada = false; */
					parent.ProcesandoSolicitudCtrl.cerrar();
				}
			}
		});
		//parent.WizardDomicilioGeneralCtrl.cerrar();
		//console.log('idOrigen '+ parent.ProcesandoSolicitudCtrl.config.idOrigen);
		//alert('Origen procesando: '+parent.ProcesandoSolicitudCtrl.config.idOrigen);
		//alert('Condicion origen: '+(parent.ProcesandoSolicitudCtrl.config.idOrigen=='6'));
		if( parent.ProcesandoSolicitudCtrl.config.idOrigen != undefined 
			&& parent.ProcesandoSolicitudCtrl.config.idOrigen=='6' ){
			//console.log('idOrigen '+ parent.ProcesandoSolicitudCtrl.config.idOrigen);
			//parent.WizardDomicilioGeneralCtrl.cerrar();
		}else{
			parent.ProcesandoSolicitudCtrl.setOnCloseCallback(function(folio) {
				dialogoConfirmarCancelar.dialog("open");
			});	
		}
		
		parent.ProcesandoSolicitudCtrl.setValidateCallback(avisoTardioProcesamiento);
		
		$(window).unload(function(){
			if (cometSubscription != null) {
				solicitudCometConn.unsubscribe(cometSubscription);
			}
		});
	});
	
	function conectarComet(channel) {
	    conectar(channel);
	    
	    function conectar(channel) {
			var maxRetries = 5;
			
			if (retries == maxRetries) {
				console.log("Máximo alcanzado");
			} else {
				try {
					cometSubscription = solicitudCometConn.subscribe(channel, avisoProcesamiento);	
				} catch(err) {
					retries += 1;
					console.log('Error al conectarse al COMET para checar avance de procesamiento, reintento numero ' + retries);
					setTimeout(function(){conectar(canal)}, 1000);
				} 
			}
		}
	}

	var avisoProcesamiento = function(message) {
			
		var respuesta = message.data;
		
		parent.ProcesandoSolicitudCtrl.pararValidaciones();
		
		if(respuesta.isExitoso || respuesta.isExitoso == 'true') {
			if(respuesta.estadoSolicitud == estadoConcluido){
				dialogoFinSolicitud.dialog("option", "buttons", [ {
					text : 'Aceptar',
					click : function() {
						parent.ProcesandoSolicitudCtrl.cerrar();
					}
				}, {
					text: "Ver documentos",
					click : function() {
						
						parent.ejecutarConsultaSolicitudPorFolio(respuesta.folioSolicitud);
						parent.ProcesandoSolicitudCtrl.cerrar();
					}
				}]);
				
				finalizarMensajeSolicitud(respuesta.folioSolicitud,respuesta.mensajeExito);
				
				if (parent.ProcesandoSolicitudCtrl.onSolicitudExitosaCallback != null) {
					parent.ProcesandoSolicitudCtrl.onSolicitudExitosaCallback.call(parent.ProcesandoSolicitudCtrl, respuesta.folioSolicitud);
				}
			}
		} else {
			var mensajeError = respuesta.mensajeError;
			$('#dgMensajeSistema').text(mensajeError);
			dialogoFinSolicitud.dialog("open");
		}
	};
	
	var mostrarDocumentoAviso = function(idSolicitud,folioSolicitud){
		abrirDocumentoDeTramite(idSolicitud,folioSolicitud,codigoTipoDocumentoComprobante);
	};
	
	var abrirDocumentoDeTramite = function(solicitud,folio,tipoDocumento){
	
		$('#solicitudDocumentoForm').attr('action', '/gestionSolicitud-visor-web/portlet/solicitudes/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento);
		$('#solicitudDocumentoForm').submit();
	};

	var finalizarMensajeSolicitud = function(folioSolicitud, mensajeExito) {
		var idSolicitud = $('#hdnIdSolicitud').val();
		var idTipoSolicitud = $('#hdnIdTipoSolicitud').val();
		if(mensajeExito != undefined)
			$('#dgMensajeSistema').html(mensajeExito);
		else
			$('#dgMensajeSistema').html('La solicitud <strong>' + folioSolicitud +  '</strong> fue concluida exitosamente');
		
		dialogoFinSolicitud.dialog("open");
		
		lanzarEncuestaSatisfaccion();
	};

	var avisoTardioProcesamiento = function(isUltimaValidacion) {
				
		var folioSolicitud = $('#hdnFolioSolicitud').val();
		var url = '/wizard-web/procesando/solicitud/validar/' + folioSolicitud;

		$.postJSON(url, null, function(respuesta) {
			if(respuesta.isExitoso){
				if(respuesta.estadoSolicitud == estadoConcluido){
					var mensajeExito = 'La solicitud <strong>' + respuesta.folioSolicitud +  '</strong> fue concluida exitosamente';
					$('#dgMensajeSistema').html(mensajeExito);
					dialogoFinSolicitud.dialog("option", "buttons", [ {
						text : 'Aceptar',
						click : function() {
							parent.ProcesandoSolicitudCtrl.cerrar();
						}
					}, {
						text: "Ver documentos",
						click : function() {
							
							parent.ejecutarConsultaSolicitudPorFolio(respuesta.folioSolicitud);
							parent.ProcesandoSolicitudCtrl.cerrar();
						}
					}]);
					
					dialogoFinSolicitud.dialog("open");
					
					if (parent.ProcesandoSolicitudCtrl.onSolicitudExitosaCallback != null) {
						parent.ProcesandoSolicitudCtrl.onSolicitudExitosaCallback.call(parent.ProcesandoSolicitudCtrl, folioSolicitud);
					}
					
					parent.ProcesandoSolicitudCtrl.pararValidaciones();
					
					lanzarEncuestaSatisfaccion();
				} else if(respuesta.estadoSolicitud == estadoCancelado || respuesta.estadoSolicitud == estadoIniciada){
					var mensajeError = 'Error al finalizar la solicitud ' + respuesta.folioSolicitud;
					$('#dgMensajeSistema').html(mensajeError);
					
					dialogoFinSolicitud.dialog("open");
					
					parent.ProcesandoSolicitudCtrl.pararValidaciones();
					
					console.log('Solicitud en CANCELADA (AVISO TARDIO): Se asigna null a onSolicitudExitosaCallback');
					parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
				} else if(respuesta.estadoSolicitud == estadoEnProceso && isUltimaValidacion){
					var mensajeError = 'Su solicitud contin&uacute;a en proceso. Usted puede revisar el estado de su solicitud actualizando la secci&oacute;n de solicitudes.';
					$('#dgMensajeSistema').html(mensajeError);
					
					dialogoFinSolicitud.dialog("open");
					
					parent.ProcesandoSolicitudCtrl.pararValidaciones();
					
					console.log('Solicitud en PROCESO (AVISO TARDIO): Se asigna null a onSolicitudExitosaCallback');
					parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
				} else if(respuesta.estadoSolicitud == estadoEnProceso) {
					
				}
			} else {
				parent.ProcesandoSolicitudCtrl.pararValidaciones();
				var mensajeError = respuesta.mensajeError;
				$('#dgMensajeSistema').text(mensajeError);
				dialogoFinSolicitud.dialog("open");
				
				console.log('Solicitud NO EXITOSA (AVISO TARDIO): Se asigna null a onSolicitudExitosaCallback');
				parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
			}
		}).error(function(data){
			parent.ProcesandoSolicitudCtrl.pararValidaciones();
			var mensajeError = 'Ocurri&oacute; un error inesperado';
			$('#dgMensajeSistema').html(mensajeError);
			dialogoFinSolicitud.dialog("open");
			
			console.log('Solicitud ERROR (AVISO TARDIO): Se asigna null a onSolicitudExitosaCallback');
			parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
		});	
	};
	
	var lanzarEncuestaSatisfaccion = function() {
		var homoclave = $("#hdnHomoclaveTramite").val();
		
		if($.trim(homoclave).length != 0 && parent.startEncuestaHC) {
			parent.startEncuestaHC(500,homoclave);
		}
	}
</script>

<div>
	<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
	<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
	<input type="hidden" id="hdnIdTipoSolicitud" value="${idTipoSolicitud}" />
	<input type="hidden" id="hdnHomoclaveTramite" value="${homoClaveSolicitud}" />


	<div class="well m-t-lg m-b-lg">
		<p style="font-size: large; font-weight: bold; text-align: center;">
			La solicitud ${folioSolicitud} est&aacute; en proceso ...
		</p>

		<div style="text-align: center; vertical-align: middle;">
			<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
		</div>

		<p style="margin-top: 18px;">
			Esta p&aacute;gina se recargar&aacute; autom&aacute;ticamente en cuanto tu solicitud haya sido procesada. Se
			recomienda <strong>no</strong> cerrar esta ventana.
		</p>
	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud" style="display:none;">
	<p>
		<i style="margin-right: 10px;" class="fa fa-info-circle"></i>
		¿Est&aacute;s realmente seguro de cerrar esta ventana?
	</p>
</div>

<div id="dialog-solicitud-exito" title="Mensaje del Sistema" style="display:none;">
	<p>
		<i style="margin-right: 10px;" class="fa fa-info-circle"></i>
		<span id="dgMensajeSistema"></span>
	</p>
</div>

<form id="solicitudDocumentoForm" name="solicitudDocumentoForm" method="POST" target="_blank" class="formNotBlock">
</form>

<form id="solicitudDocumentoDosForm" name="solicitudDocumentoDosForm" method="POST" target="_blank" class="formNotBlock">
</form>
<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum" %>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script type="text/javascript">
	var isSolicitudValidada = false;
	var codigoTipoDocumentoComprobante = <%=TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo()%>;
	var idTipoSolicitudAlta = <%=TipoSolicitudEnum.ALTA_PATRONAL.getValor()%>;
	var dialogoFinSolicitud;
	var estadoConcluido = <%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>;
	var estadoCancelado =  <%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>;
	var estadoEnProceso =  <%=EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()%>;
	var estadoIniciada =  <%=EstadoSolicitudEnum.REGISTRADA.getCodigo()%>;
	$(document).ready(function() {
		
		//var urlComet = "/delta-comet-web/static/resources/js/delta/comet/CometConector.js";


		var solicitudCometConn = new parent.CometCtrl();
		var folioSolicitud = $('#hdnFolioSolicitud').val();
		var channel = '/solicitud/modificacion/' + folioSolicitud;
		solicitudCometConn.subscribe(channel, avisoProcesamiento);
			
		var dialogoConfirmarCancelar = $("#dialog-confirm-cancelar").dialog({
			resizable : false,
			height : 'auto',
			modal : true,
			autoOpen : false,
			buttons : {
				"ACEPTAR" : function() {
					parent.ProcesandoSolicitudCtrl.cerrar();
				},
				"CANCELAR" : function() {
					$(this).dialog("close");
				}
			}
		});

		dialogoFinSolicitud = $("#dialog-solicitud-exito").dialog({
			resizable : false,
			height : 'auto',
			modal : true,
			closeOnEscape: false,
			autoOpen : false,
			open : function(event, ui) {
				$(this).parent().children().children(".ui-dialog-titlebar-close").hide();
			},
			buttons : {
				"ACEPTAR" : function() {
					/* isSolicitudValidada = false; */
					parent.ProcesandoSolicitudCtrl.cerrar();
				}
			}
		});

		parent.ProcesandoSolicitudCtrl.setOnCloseCallback(function() {
			dialogoConfirmarCancelar.dialog("open");
		});
		
		parent.ProcesandoSolicitudCtrl.setValidateCallback(avisoTardioProcesamiento);
	});

	avisoProcesamiento = function(message) {
		if(!isSolicitudValidada){
			
			var respuesta = message.data;

			if(respuesta.isExitoso || respuesta.isExitoso == 'true') {
				if(respuesta.estadoSolicitud == estadoConcluido){
					isSolicitudValidada = true;		
					dialogoFinSolicitud.dialog("option", "buttons", [ {
						text : 'ACEPTAR',
						click : function() {
							parent.ProcesandoSolicitudCtrl.cerrar();
						}
					}, {
						text: "VER DOCUMENTOS",
						click : function() {
							
							parent.ejecutarConsultaSolicitudPorFolio(respuesta.folioSolicitud);
							parent.ProcesandoSolicitudCtrl.cerrar();
						}
					}]);
					
					finalizarMensajeSolicitud(respuesta.folioSolicitud,respuesta.mensajeExito);
				}
			} else {
				var mensajeError = respuesta.mensajeError;
				$('#dgMensajeSistema').text(mensajeError);
				dialogoFinSolicitud.dialog("open");
			}
		}
	};
	
	mostrarDocumentoAviso = function(idSolicitud,folioSolicitud){
		abrirDocumentoDeTramite(idSolicitud,folioSolicitud,codigoTipoDocumentoComprobante);
	};
	
	abrirDocumentoDeTramite = function(solicitud,folio,tipoDocumento){
	
		$('#solicitudDocumentoForm').attr('action', '/gestionSolicitud-visor-web/portlet/solicitudes/mostrarDocumento?idSolicitud='+solicitud+'&noFolio='+folio+'&tipoDocumento='+tipoDocumento);
		$('#solicitudDocumentoForm').submit();
	};

	finalizarMensajeSolicitud = function(folioSolicitud, mensajeExito) {
		var idSolicitud = $('#hdnIdSolicitud').val();
		var idTipoSolicitud = $('#hdnIdTipoSolicitud').val();
		if(mensajeExito != undefined)
			$('#dgMensajeSistema').html(mensajeExito);
		else
			$('#dgMensajeSistema').html('La solicitud <strong>' + folioSolicitud +  '</strong> fue concluida exitosamente');
		
		dialogoFinSolicitud.dialog("open");
		
		/*
		if(idTipoSolicitud != undefined && idTipoSolicitud!=null && idTipoSolicitud!=""){
				$('#solicitudDocumentoForm').attr('action', '/delta-gestionPatronal-web/arp/comprobante/solicitud?folioSolicitud='+folioSolicitud);
				$('#solicitudDocumentoForm').submit();
				$('#solicitudDocumentoDosForm').attr('action', '/delta-gestionPatronal-web/arp/comprobante/solicitud/tip?folioSolicitud='+folioSolicitud);
				$('#solicitudDocumentoDosForm').submit();
		} else if( idSolicitud != undefined &&  idSolicitud != null && idSolicitud != ""){
			mostrarDocumentoAviso(idSolicitud,folioSolicitud);
		}*/
	};

	avisoTardioProcesamiento = function(isUltimaValidacion) {
		if(!isSolicitudValidada) {
			if(isUltimaValidacion) {
				isSolicitudValidada = true;
			}
			var folioSolicitud = $('#hdnFolioSolicitud').val();
			var url = '/portal-web/wizard/solicitud/validar/proceso/' + folioSolicitud;

			$.postJSON(url, null, function(respuesta) {
				if(respuesta.isExitoso){
					if(respuesta.estadoSolicitud == estadoConcluido){
						var mensajeExito = 'La solicitud <strong>' + respuesta.folioSolicitud +  '</strong> fue concluida exitosamente';
						$('#dgMensajeSistema').html(mensajeExito);
						dialogoFinSolicitud.dialog("option", "buttons", [ {
							text : 'ACEPTAR',
							click : function() {
								parent.ProcesandoSolicitudCtrl.cerrar();
							}
						}, {
							text: "VER DOCUMENTOS",
							click : function() {
								
								parent.ejecutarConsultaSolicitudPorFolio(respuesta.folioSolicitud);
								parent.ProcesandoSolicitudCtrl.cerrar();
							}
						}]);
						isSolicitudValidada = true;
						dialogoFinSolicitud.dialog("open");
					} else if(respuesta.estadoSolicitud == estadoCancelado || respuesta.estadoSolicitud == estadoIniciada){
						var mensajeError = 'Error al finalizar la solicitud ' + respuesta.folioSolicitud;
						$('#dgMensajeSistema').html(mensajeError);
						isSolicitudValidada = true;
						dialogoFinSolicitud.dialog("open");
					} else if(respuesta.estadoSolicitud == estadoEnProceso && isUltimaValidacion){
						var mensajeError = 'Su solicitud contin&uacute;a en proceso. Usted puede revisar el estado de su solicitud actualizando la secci&oacute;n de solicitudes.';
						$('#dgMensajeSistema').html(mensajeError);
						isSolicitudValidada = true;
						dialogoFinSolicitud.dialog("open");
					} else if(respuesta.estadoSolicitud == estadoEnProceso) {
						isSolicitudValidada = false;
					}
				} else {
					var mensajeError = respuesta.mensajeError;
					$('#dgMensajeSistema').text(mensajeError);
					dialogoFinSolicitud.dialog("open");
				}
			}).error(function(data){
				var mensajeError = 'Ocurri&oacute; un error inesperado';
				$('#dgMensajeSistema').html(mensajeError);
				dialogoFinSolicitud.dialog("open");
			});	
		}
	};
</script>

<div id="admonMediosContactoWrapper">
	<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
	<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
	<input type="hidden" id="hdnIdTipoSolicitud" value="${idTipoSolicitud}" />
	
	<!-- 
	<div class="alert alert-success">
		La solicitud <strong>${folioSolicitud}</strong> fue enviada exitosamente.
	</div>
	 -->
	 
	<div class="well">
		<p style="font-size: large; font-weight: bold; text-align: center;">La solicitud ${folioSolicitud} est&aacute; en proceso ...</p>
	
		<div style="text-align:center; vertical-align:middle;">
			<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
		</div>
		
		<p style="margin-top: 18px;">Esta p&aacute;gina se recargar&aacute; autom&aacute;ticamente
			en cuanto su solicitud haya sido procesada. Se recomienda <strong>NO</strong> cerrar 
			esta ventana.</p>
	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Est&aacute; realmente seguro de cerrar esta ventana?
	</p>
</div>

<div id="dialog-solicitud-exito" title="Mensaje del Sistema">
	<p>
		<span class="ui-icon ui-icon-info"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<span id="dgMensajeSistema"></span>
	</p>
</div>
<form id="solicitudDocumentoForm" name="solicitudDocumentoForm"
	method="POST" target="_blank" class="formNotBlock">
</form>
<form id="solicitudDocumentoDosForm" name="solicitudDocumentoDosForm"
	method="POST" target="_blank" class="formNotBlock">
</form>
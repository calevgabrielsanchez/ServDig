<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
	
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/domicilioClinicaDerechohabiente/contenido.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">
	
	<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud"/>
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	
	<div class="contenido" style="width: 100%;">
		<div class="titulo" align="center">
			<span> ${descripcionTipoSolicitud}</span>
		</div>
		<c:choose>
		<c:when test="${empty error }">
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
				<div class="alert alert-success">
				<c:choose>
					<c:when test="${!isRetomar}">
						Su solicitud ha iniciado correctamente y su n&uacute;mero de folio es <strong>${solicitud.noFolioSolicitud}</strong>
					</c:when>
					<c:otherwise>
						El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
					</c:otherwise>
				</c:choose>
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
				</div>
				
				<div id="patronesWrapper" style="width: 100%; margin: 0 auto;">
					
				<jsp:include page="../../common/datosDerechohabiente.jsp"></jsp:include>
				
				<div class="separadorseccion">
					<span>
						Datos de la baja
					</span>
				</div>
				<div>
					<form id="formularioBajaDerechohabiente" name="formularioBajaDerechohabiente" action="#" method="POST" rol="form">
					<input type="hidden" value="${tramite.persona.idPersona}" id="fisica.idPersona" name="fisica.idPersona">
					<input type="hidden" value="${tramite.tipoTramite.idTipoTramite}" id="tipoTramite.idTipoTramite" name="tipoTramite.idTipoTramite">
					<input type="hidden" value="${tramite.tramiteId}" id="tramiteId" name="tramiteId">
					<table class="table table-striped table-bordered" style="width: 100%">
						<tbody>
						<c:if test="${tramite.tipoTramite.idTipoTramite == 25}">
							<tr>
								<td align="left">
									<label class="control-label" for="fechaDefuncion"><span class="required">*</span>&nbsp;Fecha de defunci&oacute;n:</label>
								</td>
								<td align="left">
									<input type="text" id="fechaDefuncion" name="fechaDefuncion" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${tramite.fechaDefuncion}"/>" style="width: 140px"  class="form-control"/>
									<span id="fechaDefuncionError" class="error hiddenElement"></span>
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="left">
								<label class="control-label" for="observaciones"><span class="required">*</span>&nbsp;Observaciones : </label>
							</td>
							<td >
								<textarea id="observaciones" name="observaciones" style="height: 40px; width: 90%;"  class="form-control">${tramite.observaciones}</textarea>
								<span id="observacionesError" class="error hiddenElement"></span>
							</td>
						</tr>
						</tbody>
					</table>
					</form>
				</div>
				
				<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span>
							Documentos Probatorios
						</span>
					</div>
					<div class="alert alert-success">
						Este tr&aacute;mite requiere la captura de documentos probatorios, de clic en el bot&oacute;n "Captura de documentos Probatorios" para proceder a la misma, no podr&aacute; 
						finalizar el tr&aacute;mite hasta completarla.<br><br>
						<a id="capturarDocumentosBaja" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><i class="glyphicon glyphicon-file"></i> Captura de documentos probatorios</a>
					</div>
				</c:if>
					
				</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${error}
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	<br>
	<div class="pie">
		<div class="opciones">
			<c:if test="${empty error}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /> </a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i>
								Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>
								Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>
		<div class="controles"></div>

	</div>
</div>

<input type = "hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}">

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitud.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>
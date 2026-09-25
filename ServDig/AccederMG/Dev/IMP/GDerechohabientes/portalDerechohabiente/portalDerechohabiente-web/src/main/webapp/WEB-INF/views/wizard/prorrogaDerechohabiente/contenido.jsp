<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="OrigenSolicitudEnumVentanilla" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>"/>
<c:set var="OrigenSolicitudEnumInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>"/>
<c:set var="OrigenSolicitudEnumCiudadano" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()%>"/>
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
	src="<spring:url value="/static/resources/js/delta/wizard/prorrogaDerechohabiente/contenido.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/textarea/jquery.maxlength.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/textarea/textarea.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">
	
	<input type="hidden" value="${idOrigenSolicitud}" id="idOrigenSolicitud"/>
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud"/>
	
	<div class="contenido row">
	<div class="col-sm-12">
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
						Datos de la prorroga
					</span>
				</div>
				<c:if test="${tramite.tipoTramite.idTipoTramite eq 29}">
					<div class="alert alert-info">
						La fecha de inicio y fin de prorroga, ser&aacute;n las mismas que las fechas de inicio y fin de periodo escolar, por
						lo tanto es necesario que captura la informaci&oacute;n del documento probatorio.
					</div>
				</c:if>
				<div>
					<form id="formularioProrrogaDerechohabiente" name="formularioProrrogaDerechohabiente" action="#" method="POST" rol="form">
					<input type="hidden" value="${tramite.persona.idPersona}" id="fisica.idPersona" name="fisica.idPersona">
					<input type="hidden" value="${tramite.tipoTramite.idTipoTramite}" id="tipoTramite.idTipoTramite" name="tipoTramite.idTipoTramite">
					<input type="hidden" value="${tramite.tramiteId}" id="tramiteId" name="tramiteId">
					<table class="table table-striped table-bordered" style="width: 100%">
						<tbody>
							<tr id="inicioP">						
								<td id="fInicio">
									<label class="control-label" for="observaciones">Vigencia:</label>
								</td>
								<td>
									<combo:creaCombo
										idHtml="caracter.idCaracter"
										idHtmlContenedor="formularioProrrogaDerechohabiente"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicCaracter"
										idHtmlValor="${tramite.caracter.idCaracter}"
										mostrarSoloActivos="true" 
										cssClassname="form-control"
									/>
									<span id="caracter.idCaracterError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr id="fechaInicioColumna">						
								<td id="fInicio">
									<label class="control-label" for="observaciones">Fecha de inicio:</label>
								</td>
								<td>
									<form:input path="tramite.fechaInicioProrroga" type="hidden" value ="${tramite.fechaInicioProrroga}" />
									<input id="_fechaInicioProrroga" name="fechaInicioProrroga" type="text" readonly="readonly" 
										value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${tramite.fechaInicioProrroga}" />' style="width: 150px;" class="form-control"/>
									<span id="fechaInicioProrrogaError" class="error hiddenElement"></span>
								</td>
							</tr>																						
							<tr id="fechfinColumna">
								<td>
									<label class="control-label" for="observaciones">Fecha de fin:</label>
								</td>
								<td>
									<form:input path="tramite.fechaFinProrroga" type="hidden" value ="${tramite.fechaFinProrroga}"/>
									<input id="_fechaFinProrroga" name="fechaFinProrroga" type="text" readonly="readonly"  
										value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${tramite.fechaFinProrroga}"/>' style="width: 150px;" class="form-control"/>
									<span id="fechaFinProrrogaError" class="error hiddenElement"></span>
								</td>					
							</tr>
							<tr>
								<td align="left">
									<label class="control-label" for="observaciones">Observaciones : </label>
								</td>
								<td >
									<textarea id="observaciones" name="observaciones" style="height: 80px;" class="form-control">${tramite.observaciones}</textarea>
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
						<a id="capturarDocumentosProrroga" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><i class="icon-file"></i> Captura de documentos probatorios</a>
					</div>
				</c:if>
					
				</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${error}
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	</div>
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty error}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /> </a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="icon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						<c:if test="${idOrigenSolicitud eq OrigenSolicitudEnumInternet}">
						<li><a id="guardarTramite"><i class="icon-download-alt"></i>
								Guardar Tr&aacute;mite</a></li>
						</c:if>		
						<li><a id="cancelarTramite"><i class="icon-trash"></i>
								Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-secondary" id="cerrarWizard">CERRAR</button>
			</div>
		</div>

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
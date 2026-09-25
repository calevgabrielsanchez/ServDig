<%@ include file="../../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/centroTrabajo/inicial.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
<input type="hidden" id="hdnIdTipoTramite" value="${idTipoTramite}" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span><spring:message code="wizard.cambioCT.titulo"/></span>
			</div>

			<div class="descripcion">
				<p><spring:message code="wizard.cambioCT.descripcion"/>
				</p>
			</div>

			<div class="opciones">
					<c:if test="${!existenEnProceso}">
						<c:choose>
							<c:when test="${!existenPendientes}">
								<button
									class="btn btn-primary btn-block" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.inicio.btn_iniciarTramite','clickin')"
									role="button" aria-disabled="false" id="btnInciaTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.iniciar" /></span>
								</button>
							</c:when>
							<c:otherwise>
								<button
									class="btn btn-primary btn-block" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.inicio.btn_RetomarTramite','clickin')"
									role="button" aria-disabled="false" id="btnRetomarTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.retomar" /></span>
								</button>
								<button
									class="btn btn-danger btn-block" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.inicio.btn_cancelarTramite','clickin')"
									role="button" aria-disabled="false" id="btnCancelarTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar.proceso" /></span>
								</button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<button
						class="btn btn-default btn-block" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.inicio.btn_salir','clickin')"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar" /></span>
					</button>
			
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3><spring:message code="label.instrucciones"/>:</h3>

			<c:choose>
				<c:when test="${existenPendientes}">
					<div class="alert alert-info">
						<spring:message code="tramite.centrotrabajo.existente"/>
					</div>
				</c:when>
				<c:when test="${existenEnProceso}">
					<div class="alert alert-warning">
						<spring:message code="tramite.centrotrabajo.enproceso"/>
					</div>
				</c:when>
				<c:otherwise>
					<div style="background-color: white;">
						<input type="hidden" id="hdnIdTramiteActualizacionCT" value="<%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()%>" />
					</div>
				</c:otherwise>				
			</c:choose>
			<ul>
				<li>
					<p><spring:message code="tramite.centrotrabajo.instruccionesmodificacion"/></p>
				</li>
			</ul>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form modelAttribute="sujetoTramite" id="modificacionCentroTrabajoForm" method="post">
	<form:hidden path="numeroRegistroPatronal" id="hdnClasifNumeroRegistroPatronal"/>
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<spring:message code="label.cancelar.solicitud" arguments="${folioSolicitud}"/>
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

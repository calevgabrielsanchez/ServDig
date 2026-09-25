<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/recuperacionRP/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span> Recuperaci&oacute;n de Registro Patronal </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute; recuperar los registros patronales asociados a su
					rfc.</p>
			</div>

			<div class="opciones">
				<c:if test="${empty error}">
					<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnInciaTramite">
									<span class="ui-button-text">Iniciar Solicitud</span>
								</button>
							</c:when>
							<c:otherwise>
								<c:if test="${solicitudMismoOrigen}">
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnRetomarTramite">
										<span class="ui-button-text">Retomar Solicitud</span>
									</button>
								</c:if>
								<button class="btn btn-warning btn-block" role="button" aria-disabled="false" id="btnCancelarTramite">
									<span class="ui-button-text">Cancelar Solicitud</span>
								</button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:if>
				<button class="btn btn-default btn-block" role="button" aria-disabled="false" id="btnInicioCancelarTramite">
					<span class="ui-button-text">
						<spring:message code="label.boton.solicitud.cancelar" />
					</span>
				</button>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">
				<c:if test="${!existeSolProceso}">
					<c:choose>
						<c:when test="${!existeSolRegistrada}">
							<!-- INICIAR SOLICITUD -->
							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp"%>
						</c:when>
						<c:otherwise>
							<!-- SOLICITUD REGISTRADA -->
							<div class="alert alert-info">
								Usted ya cuenta con una solicitud para
								<strong>RECUPERACI&Oacute;N DE REGISTRO PATRONAL</strong>
								en proceso.
								<br>
								Folio de solicitud:
								<strong>${solicitudForm.noFolioSolicitud}</strong>
								<br>
							</div>
							<c:if test="${solicitudMismoOrigen}">
								<!-- SOLICITUD REGISTRADA (MISMO ORIGEN - mostrar pasos tramite) -->
								<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp"%>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${existeSolProceso}">
					<!-- SOLICITUD EN PROCESO -->
					<div class="alert alert-warning">
						Existe un tr&aacute;mite de
						<strong>RECUPERACI&Oacute;N DE REGISTRO PATRONAL</strong>
						en curso, espere a su conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.
						<br>
						Folio de solicitud:
						<strong>${solicitudForm.noFolioSolicitud}</strong>
						<br>
					</div>
				</c:if>
			</c:if>

			<c:if test="${not empty error }">
				<div class="alert alert-danger" id="divMsgErrores">${error}</div>
			</c:if>
		</div>
	</div>
	
	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>


<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form id="formIniciaTramite" method="post"
	action="${contextPath}/wizard/tramite/recuperacion/patron/iniciarTramite">
</form:form>
<!--  -->

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm" method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>
<!--  -->

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${solicitudForm.noFolioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>
<!--  -->
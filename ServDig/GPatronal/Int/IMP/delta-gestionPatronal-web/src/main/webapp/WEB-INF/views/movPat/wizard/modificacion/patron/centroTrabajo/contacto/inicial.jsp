<%@ include file="../../../../../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/modificacion/patron/centroTrabajo/contacto/inicial.js" htmlEscape="true" />"></script>

<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span><spring:message code="wizard.actMedios.titulo" /></span>
				
			</div>

			<div class="descripcion">
				<p><spring:message code="wizard.actMedios.descripcion"/>
				</p>
			</div>

			<div class="opciones">
					<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<button onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contacto.inicio.btn_iniciarTramite','clickin')"
									class="btn btn-primary btn-block"
									role="button" aria-disabled="false" id="btnInciaTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.iniciar" /></span>
								</button>
							</c:when>
							<c:otherwise>
								<button onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contacto.inicio.btn_retomarTramite','clickin')"
									class="btn btn-primary btn-block"
									role="button" aria-disabled="false" id="btnRetomarTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.retomar" /></span>
								</button>
								<button onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contacto.inicio.btn_cancelarTramite','clickin')"
									class="btn btn-warning btn-block"
									role="button" aria-disabled="false" id="btnCancelarTramite">
									<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar.proceso" /></span>
								</button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<button onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contacto.inicio.btn_cancelar','clickin')"
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar" /></span>
					</button>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:choose>
				<c:when test="${existeSolRegistrada}">
					<div class="alert alert-info">
						Usted ya cuenta con una solicitud para <strong>ACTUALIZACI&Oacute;N DE MEDIOS DE CONTACTO</strong> en proceso.
					</div>
				</c:when>
				<c:when test="${existeSolProceso}">
					<div class="alert alert-warning">
						Existe un tr&aacute;mite de <strong>ACTUALIZACI&Oacute;N DE MEDIOS DE CONTACTO</strong> en curso, espere a su
						conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.
					</div>
				</c:when>
			</c:choose>
				<ul>
					<li>
						<p>
							<h3>Instrucciones:</h3>
						</p>
						<p>
							Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.
						</p>
						<p>
							1.	Selecciona la opci&oacute;n iniciar solicitud y capture los datos solicitados.<br>
							2.	Los campos marcados con un asterisco (*) son datos obligatorios.<br>
						</p>
					</li>
				</ul>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>


<form:form modelAttribute="sujetoTramite" id="soForm" method="post" action="">
	<form:hidden path="numeroRegistroPatronal" id="numeroRegistroPatronal" />
	<form:hidden path="modalidad.numModalidad" id="modalidad.numModalidad" />
	<form:hidden path="digVerificador" id="digVerificador" />
	
	
	<form:hidden path="tipoPersonaFiscal" id="tipoPersonaFiscal" />
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

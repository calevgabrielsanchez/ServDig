<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/seguroDomestico/alta/init.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	//-->
</script>
<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo separadorseccion">
						<span><spring:message code="label.wizard.titulo.altaSeguroDomestico"/></span>
					</div>
					<div class="descripcion">
						<p>
							Al incorporar voluntariamente a sus trabajadores dom&eacute;sticos al r&eacute;gimen obligatorio del Seguro
							Social, &eacute;stos recibir&aacute;n asistencia m&eacute;dica familiar, quir&uacute;rgica y
							farmac&eacute;utica; servicio de hospitalizaci&oacute;n, y rehabilitaci&oacute;n.
						</p>
						<p>
							Y previo cumplimiento de los requisitos y condiciones previstas en la Ley del Seguro Social, tendr&aacute;n
							derecho a recibir pensiones de invalidez y/o viudez; por retiro, cesant&iacute;a en edad avanzada y vejez.
						</p>
					</div>
					<div class="opciones">
						<button
							class="btn btn-primary btn-block" id="btnIniciarSolicitudAlta">
							<span>Iniciar tr&aacute;mite</span>
						</button>
						<button
							class="btn btn-default btn-block" id="btnCancelarSolicitudAlta">
							<span>Cancelar</span>
						</button>
					</div>
				</div>
				<div class="instrucciones col-sm-8">
					<%@ include file="../../comunes/mensajeOpcionesIniciarSolicitud.jsp" %>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="contenido row">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6">
				</div>
				<div class="controles col-sm-6">
					<div class="pull-right">
						<a id="btnCancelarSolicitudAlta" class="btn btn-default">Salir</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<%--<form:form id="capturarDatosSolicitudAltaForm" action="${contextPath}/wizard/seguroDomestico/comunes/solicitarTipoPago"--%>
<form:form id="capturarDatosSolicitudAltaForm" action="${contextPath}/wizard/seguroDomestico/comunes/solicitarCentroTrabajo"
	method="post">
		<input name="idPersona" type="hidden" value="${persona.idPersona}"/>
		<input name="idPatron" type="hidden" value="${persona.idPersona}"/>
		<input name="otraInfo" type="hidden" value="${persona.idPersona}"/>
</form:form>

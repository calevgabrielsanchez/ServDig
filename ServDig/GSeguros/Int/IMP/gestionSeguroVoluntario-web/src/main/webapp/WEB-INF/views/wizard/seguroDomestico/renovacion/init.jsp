<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/renovacion/init.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	tipoOperacion = '${tipoOperacion}';
	ventanilla = ${esVentanilla};
</script>

<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo separadorseccion">
						<span><spring:message code="label.wizard.titulo.renovacionSeguroDomestico"/></span>
					</div>
					<div class="descripcion">
						<p>
							<spring:message code="label.wizard.descripcion.renovacionSeguroDomestico"/>
						</p>
					</div>
					<div class="opciones">
						<button
							class="btn btn-primary btn-block" id="btnIniciarSolicitudRenovacion">
							<span>Iniciar tr&aacute;mite</span>
						</button>
						<button
							class="btn btn-default btn-block" id="btnCancelarSolicitudRenovacion">
							<span>Cancelar</span>
						</button>
					</div>
				</div>
				<div class="instrucciones col-sm-8">
					<div>${datosCalculo.errorFormGeneral}</div>
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
						<a id="btnCancelarSolicitudRenovacion" class="btn btn-default">Salir</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<form:form id="capturarDatosSolicitudRenovacionForm" action="${contextPath}/wizard/seguroDomestico/comunes/solicitarCentroTrabajo"
	method="post">
		<input name="idPersona" type="hidden" value="${persona.idPersona}"/>
		<input name="idPatron" type="hidden" value="${persona.idPersona}"/>
		<input name="otraInfo" type="hidden" value="${persona.idPersona}"/>
</form:form>
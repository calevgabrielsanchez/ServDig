<%@ include file="../../general/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/comunesEscrito.js" htmlEscape="true" />"></script>
<script type="text/javascript">
$(document).ready(funcionesComunes.init);
</script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span><spring:message code="escrito.titulo"/></span>
			</div>

			<div class="descripcion">
				<p><spring:message code="escrito.descripcion"/></p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
							<c:choose>
								<c:when test="${!retomandoSolicitud}">
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="iniciarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="retomarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.retomar" /></span>
									</button>
									<button class="btn btn-danger btn-block"role="button" aria-disabled="false" id="cancelarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
					</c:if>
					<button class="btn btn-default btn-block" role="button" aria-disabled="false" id="salirTramite">
						<span class="ui-button-text"><spring:message code="wizard.button.cerrar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">
				<c:choose>
						<c:when test="${!retomandoSolicitud}">
							
							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
						</c:when>
						<c:otherwise>
								<div class="alert alert-info">
									<spring:message code="label.mensaje.solicitud.enProceso" arguments="${solicitudEscrito.tramites[0].tipoTramite.descripcion}"/>
								</div>
								<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
						</c:otherwise>
					</c:choose>
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					<spring:message code="label.mensaje.error"/>: ${error}
				</div>
			</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form action="${contextPath}/escrito/wizard/iniciarTramite" id="solicitudEscrito" 	method="post">
	<input type="hidden" id="solicitudId" name="solicitudId" value = "${solicitudEscrito.solicitudId}"/>
	<input type="hidden" id="noFolioSolicitud" name="noFolioSolicitud" value = "${solicitudEscrito.noFolioSolicitud}"/>
</form>
<!--  -->
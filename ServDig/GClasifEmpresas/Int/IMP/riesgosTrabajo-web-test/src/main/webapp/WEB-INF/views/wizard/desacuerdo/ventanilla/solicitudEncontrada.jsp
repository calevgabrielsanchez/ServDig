<%@ include file="../../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/comunesEscrito.js" htmlEscape="true" />"></script>
<script type="text/javascript">
$(document).ready(funcionesComunes.init);
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/solicitudEncontrada.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<jsp:include page="pasosEscritoDesacuerdo.jsp">
			<jsp:param value="1" name="paso"/>
		</jsp:include>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<div class="alert alert-info">
			El registro patronal ya tiena una solicitud de <strong>${solicitudEscrito.tramites[0].tipoTramite.descripcion}</strong> con folio 
			<strong>${solicitudEscrito.noFolioSolicitud}</strong>, la cual fue
			creada desde <strong>${solicitudEscrito.origenSolicitud.descripcion }</strong>, puedes retomar la solicitud o iniciar una nueva si as&iacute; lo deseas.
		</div>
		<jsp:include page="../../../common/datosPatron.jsp"></jsp:include>
		
		<form action="${contextpath}/escrito/wizard/iniciarTramite" id="solicitudEscrito" 	method="post">
			<input type="hidden" id="solicitudId" name="solicitudId" value = "${solicitudEscrito.solicitudId}"/>
			<input type="hidden" id="noFolioSolicitud" name="noFolioSolicitud" value = "${solicitudEscrito.noFolioSolicitud}"/>
		</form>
		<div class="row">
			<div class="col-sm-12 text-right" style="padding-top:20px">
				<button type="button" class="btn btn-default" id="salirTramite"><spring:message code="wizard.button.salir"></spring:message></button>
				<button type="button" class="btn btn-danger" id="cancelarTramite"><spring:message code="wizard.button.cancelarTramite" /></button>
				<button type="button" class="btn btn-primary" id="iniciarTramite">Retomar tr&aacute;mite</button>
			</div>
		</div>
	</div>
</div>
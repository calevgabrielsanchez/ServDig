<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/asignacion/confirmarCargaArchivo.js" htmlEscape="true" />"></script>

<c:if test="${not empty FROM_PORTAL}">
	<jsp:include page="wizard/estudiantes/commonFromPortal.jsp"></jsp:include>
</c:if>

<div class="contenedor">
	<form action="">
		<c:choose>
			<c:when test="${not empty MSG_ERROR }">
				<div class="alert alert-danger" style="text-align: center;">
					La solicitud fue <strong>CANCELADA</strong> debido a: ${MSG_ERROR }.
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-success" style="text-align: center;">
					La solicitud con folio <strong>${FOLIO_SOLIC_SIE}</strong> fue
					creada correctamente y el archivo fue cargado exitosamente, en
					cuanto el proceso finalice ser&aacute; notificado por correo
					electr&oacute;nico.
				</div>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty FROM_PORTAL}">
				<div id="btnCerrarWizard" style="float: right;">
					<button type="button" class="btn btn-primary">ACEPTAR</button>
				</div>
			</c:when>
			<c:otherwise>
				<div id="btnCarga" style="float: right;">
					<button type="button" class="btn btn-primary"
						onclick="location = context_path + '/asignacion/inicio'">Cargar
						Nuevo Archivo</button>
				</div>
			</c:otherwise>
		</c:choose>
	</form>
</div>
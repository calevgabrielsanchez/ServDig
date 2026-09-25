<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>
<script type="text/javascript">
			history.go(1);
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/tramite-consultaVigenciaSalida.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
	select {
		font-size: 13px;
	}

	.required {
		color: red;
	}
		
	.filtros-busqueda .filtros .etiqueta {
		width: 25%;
	}
</style>
<!-- <body onbeforeunload="HandleBackFunctionality()"></body> -->
<div class="container">
	<c:choose>
		<c:when test="${not empty NSS_RECUPERADO }">
			<div class="alert alert-success nss-recuperado">El reporte de vigencia de derechos ha sido enviado al correo electr&oacute;nico
				<strong>${tramiteAsegurado.fisica.correoElectronico.correo}</strong>
				que fue capturado en el paso anterior de este tr&aacute;mite.</div>
				<div style="text-align: left; float: left;">
					<button type="button" id="btnVerReporte" class="btn btn-default">Ver reporte</button>
					<button type="button" id="btnInicioTramite" class="btn btn-primary">Aceptar</button>
				</div>
		</c:when>
		<c:when test="${not empty tramiteAsegurado.errorFormGeneral }">
			<div class="alert alert-danger">
				${tramiteAsegurado.errorFormGeneral}
				<c:if test="${not empty mostrarInstruccionesVentanilla }">
					<br><br>
					Para un mejor servicio al momento de acudir a ventanilla, debes tener a la mano los siguientes documentos:
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a) <b>Acta de
						Nacimiento</b> de la persona a la que se le va a asignar el N&uacute;mero de Seguridad Social<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) <b>CURP</b>
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;c) <b>Identificaci&oacute;n
						Oficial</b> (Credencial para votar expedida por el Instituto Federal Electoral, Pasaporte vigente Mexicano o Extranjero, Cartilla del Servicio Militar Nacional, C&eacute;dula Profesional)
				</c:if>
			</div>
			<div style="text-align: left; float: left;">
				<br>
				<c:choose>
				<c:when test="${not empty BACK_HOME }">
					<button type="button" id="btnInicioTramite" class="btn btn-primary">Aceptar</button>
				</c:when>
				<c:otherwise>
					<button type="button" id="regresar" class="btn btn-primary">Aceptar</button>
				</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
				La CURP no fue encontrada con el NSS proporcionado en el instituto, para poder realizar el trámite y/o actualizar su información deberá presentarse en una subdelegación del Instituto.
					<br><br>
					Para un mejor servicio al momento de acudir a ventanilla, debes tener a la mano los siguientes documentos:
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a) <b>Acta de
						Nacimiento</b> de la persona a la que se le va a asignar el N&uacute;mero de Seguridad Social<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) <b>CURP</b>
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;c) <b>Identificaci&oacute;n
						Oficial</b> (Credencial para votar expedida por el Instituto Federal Electoral, Pasaporte vigente Mexicano o Extranjero, Cartilla del Servicio Militar Nacional, C&eacute;dula Profesional)

			</div>
			<div style="text-align: left; float: left;">
				<br>
				<c:choose>
				<c:when test="${not empty BACK_HOME }">
					<button type="button" id="btnInicioTramite" class="btn btn-primary">Aceptar</button>
				</c:when>
				<c:otherwise>
					<button type="button" id="regresar" class="btn btn-primary">Aceptar</button>
				</c:otherwise>
				</c:choose>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<form id="formIniciarTramite" action="homeVigencia" method="get" ></form>
<form id="formVerReporte" action="reporteVigenciaDerechos/${tramiteId}" method="post" target="_blank" >
	<input type="hidden" value="${tramiteId}" id="tramiteId"/>
</form>

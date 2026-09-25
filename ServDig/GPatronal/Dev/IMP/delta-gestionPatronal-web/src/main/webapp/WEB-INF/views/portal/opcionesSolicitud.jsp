<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portal/afiliacion/opcionesSolicitud.js" htmlEscape="true" />"></script>


<script type="text/javascript">
	var idSolicitud='${idSolicitud}';
	var idTipoTramite='${idTipoTramite}';
	var context="${contextpath}";
</script>

<div class="page_holder contenedor row cell">

	<p>
	Actualmente tiene una edici&oacute;n de informaci&oacute;n pendiente, desea continuar con esta edici&oacute;n o desea cancelarla y comenzar una nueva.
	</p>
	
	<form:form modelAttribute="sujetoTramite" id="clasificacionInvokerForm" action="/cmp/clasificacion/generarSolicitud">
		<form:hidden path="cveIdSujetoObligado"/>
		<form:hidden path="numeroRegistroPatronal"/>
		<form:hidden path="modalidad.numModalidad"/>
		<form:hidden path="digVerificador"/>
		<form:hidden path="tipoPersonaFiscal"/>
		<form:hidden path="fisica.idPersona"/>
		<form:hidden path="fisica.rfc"/>
		<form:hidden path="moral.idPersona"/>
		<form:hidden path="moral.rfc"/>
		<input type="button" id="btnEditar" 	class="mboton" onclick="cargarSolicitud()" value="Continuar edici&oacute;n"/>
		<input type="button" id="btnCancelar" 	class="mboton" onclick="cancelarSolicitudModSRT()" value="Nueva edici&oacute;n"/>
		<input type="button" id="btnRegresar" 	class="mboton" value="Regresar"/>
	</form:form>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<c:set var="scheme" value="<%=request.getScheme()%>" />
<c:set var="server" value="<%=request.getServerName()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var= "recurso" value="${scheme}://${server}"/>
<c:set var="urlInformacionHistoriaLaboral" value="${contextpath}/wizard/correccionDatosAsegurado/validar/informacionHistoriaLaboral"/>
<c:set var="paginaAnterior" value="${contextpath}/wizard/correccionDatosAsegurado/capturarDomicilio"></c:set>

<script type="text/javascript">
	var contextPath="${contextpath}";
	
	$(document).ready(function() {
	
	var infoFechaInscricion = '<div style="font-size:11px"><p >Fecha de Inscripci&oacute;n</p>';
	    infoFechaInscricion += '<ol>';
	    infoFechaInscricion += '<li>Si no conoce la fecha exacta de inicio del periodo en el que labor&oacute; con el patr&oacute;n, puede ingresar solamente el mes y el año (MM/AAAA) o en su caso s&oacutelo el año (AAAA).</li>';
	    infoFechaInscricion += '</ol>'; 
		infoFechaInscricion +='</div>';
	
	var infoFechaBaja = '<div style="font-size:11px"><p >Fecha de Baja</p>';
	    infoFechaBaja += '<ol>';
	    infoFechaBaja += '<li>Si no conoce la fecha exacta de fin del periodo en el que labor&oacute; con el patr&oacute;n, puede ingresar solamente el mes y el año (MM/AAAA) o en su caso s&oacutelo el año (AAAA).</li>';
	    infoFechaBaja += '</ol>'; 
		infoFechaBaja +='</div>';
		
	$('#ayudaFechaInscripcion').popover({
		animation : true,
		html : true,
		title : 'Ayuda',
		content : infoFechaInscricion,
		trigger : 'hover',
		placement : 'left',
		container : 'body'
	});
	
	$('#ayudaFechaBaja').popover({
		animation : true,
		html : true,
		title : 'Ayuda',
		content : infoFechaBaja,
		trigger : 'hover',
		placement : 'left',
		container : 'body'
	});
	
	$('[data-toggle="tooltip"]').tooltip();
});

</script>

<style type="text/css">
input.form-control{
  text-transform: uppercase;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/solicitud/informacionHistoriaLaboral.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/correccionDatosCurpFileUpload.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.dateSelectBoxes.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="3" />
	</jsp:include>
	<form:form method="post" modelAttribute="informacionHistoriaLaboral" id="informacionHistoriaLaboralForm" action="${urlInformacionHistoriaLaboral}" accept-charset="ISO-8859-1">
		<%@ include file="datosHistoriaLaboral/historiaLaboral.jsp"%>
		<!-- Controles -->
		<div class="pull-right">
			<%@ include file="regresar.jsp"%>	
			<button type="button" id="continuarInformacionHistoriaLaboral" class="btn btn-primary">
				<spring:message code="label.continuar" />
			</button>
		</div>
	</form:form>
	<div class="row">
		<div class="pull-right">
			<div class="col-md-7">
				<jsp:include page="cancelarSolicitud.jsp" />
			</div>
		</div>
	</div>
</div>
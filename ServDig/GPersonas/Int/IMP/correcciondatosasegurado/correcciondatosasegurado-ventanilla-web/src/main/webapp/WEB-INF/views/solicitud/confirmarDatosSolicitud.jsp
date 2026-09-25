 <%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/common.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="urlDomicilio" value="${contextpath}/wizard/correccionDatosAsegurado/confirmarSolicitud"></c:set>
<script type="text/javascript">
	var contextPath="${contextpath}";
	$(document).ready(function() {
		$("#observaciones").height( $("#observaciones")[0].scrollHeight + 5 );
	});
</script>
	<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="4" />
	</jsp:include>
	<form:form action="${urlDomicilio}" method="POST" id="formCodigoPostal" role="form">
	
	
		<%@include file="confirmarDatos/confirmarInformacionRenapo.jsp"  %>
		<br/>
		<%@include file="confirmarDatos/confirmarDomicilioSolicitud.jsp"  %> 
		<br/>
		<%@include file="confirmarDatos/confirmarHistoriaLaboral.jsp"  %>
		<br/>
		<%@include file="confirmarDatos/confirmarDocumentosAsegurado.jsp"  %>
                <br/>
                <%@include file="confirmarDatos/confirmarDocumentosSolicitud.jsp"  %>
                <br/>
		<%--<%@include file="confirmarDatos/confirmarDatosSolicitudNSS.jsp"  %>--%>
		<br/>
		<%@include file="confirmarDatos/confirmarDatosAdicionalesHistoriaLaboral.jsp"  %>
	    
	    <spring:eval expression="T(mx.gob.imss.cit.cda.web.utils.DeltaUtils).validarInformacionDomicilio(solicitud.personaInteresada.domicilios)" var="validarDomicilio" />
	    <div class="pull-right">
			<button class="btn btn-default" id="regresarPaginaAnterior">Regresar</button>
            <c:if test="${validarDomicilio}">
            <input type="submit" class="btn btn-primary" id="confirmarSolicitudButton" value="Aceptar" />
            </c:if> 
            <c:if test="${!validarDomicilio}">
            <input type="submit" class="btn btn-primary disabled" id="confirmarSolicitudButton" value="Aceptar" disabled=true/>
            </c:if>
		</div>
	</form:form>
	
	<div class= "pull-right">
			<div class="col-md-4">
				<jsp:include page="cancelarSolicitud.jsp"/>
			</div>
		</div>
	
</div>


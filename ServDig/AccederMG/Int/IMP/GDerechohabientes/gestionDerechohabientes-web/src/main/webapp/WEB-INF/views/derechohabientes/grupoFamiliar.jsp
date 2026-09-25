<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<style>
.ui-accordion .ui-accordion-content{
overflow:visible !important;
}
</style>
<script type="text/javascript">
	history.go(1);
</script>
<script>
	var contextPath = "<%=request.getContextPath()%>";
	var perfilSession='null';
	var patronImss ='null';
	
	try{
		perfilSession='<%=request.getSession().getAttribute("perfilUsuario")%>';
		patronImss =  ${patronIMSS};
	}catch(e){
		
	}
	
</script>
<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/grupoFamiliar.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/derechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/solicitud.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/tablasSolicitudes.js" htmlEscape="true" />"></script>


<div class="form-comment">
	<form:form modelAtribute="integrantes" id="integrantes" action="#" method="post">

	<h4 align="center"><strong><spring:message
		code="label.infoGrupoFamiliar" /></strong></h4>

	<!------------------------------------ Datos del asegurado ---------------------------------------------------->
	<jsp:include page="../derechohabientes/datosAsegurado.jsp"></jsp:include>
	<br>
	<!-- ---------------------------------- Datos del patron-------------------------------------------------- -->		
	<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>
	<br>
	<!-- ---------------------------------- Domicilio grupo familiar -------------------------------------------------- -->
	<fieldset class="titulo" id="accordionDomicilio">
		<div>
		
		<table style="width: 100%">
		    
			<tr>
				<td><a href="#"><strong><spring:message code="label.domicilioGrupo"/></strong></a></td>
			</tr>
				</table>
		</div>
		
		<jsp:include page="../derechohabientes/datosDomicilio.jsp"></jsp:include>
	</fieldset>	
	<!-- ---------------------------------- Datos de los beneficiarios del grupo familiar -------------------------------------------------- -->
	<br>
	<fieldset style="width: 977px" class="titulo"><legend><strong><spring:message
		code="titulo.beneficiarios" /></strong></legend>
		
		<table style="width: 100%" id="tablaIntegrantesGrupoFamiliar">
		<caption><b><spring:message code="label.datosPersonales" /></b></caption>	
			
		</table>
		
		<c:if test="${ISMODALIDAD17}">
			<span style="float:right"><strong>Sin derecho al servicio m&eacute;dico</strong></span>
		</c:if>
	</fieldset>
	<!-- ---------------------------------- Solicitudes --------------------------------------------------	-->
	<BR>
	
	<fieldset class="titulo" id="accordionSolicitudRegistrada">
		<div>
			<table style="width: 100%"> 
				<tr>
					<td><a href="#"><strong>Solicitudes del grupo familiar</strong></a></td>
				</tr>
			</table>
		</div>
		<fieldset>
		<table style="width: 100%" id="solicitudesRegistradasTable">
			<caption><b><spring:message code="titulo.solicitudReg" /></b></caption>
		</table> 
		</fieldset>
	</fieldset>	
	<br>
	<fieldset class="titulo" id="accordionSolicitudOtrosMedios">
		<div>
			<table style="width: 100%"> 
				<tr>
					<td><a href="#"><strong>Solicitudes generadas en otro origen</strong></a></td>
				</tr>
			</table>
		</div>
		<fieldset>
		<table style="width: 100%" id="solicitudesRegistradasOtrosMedios">
			<caption><b>Solicitudes registradas mediante otros origenes</b></caption>
		</table>
		</fieldset>
	</fieldset>	
	<br>
	<fieldset class="titulo" id="accordionSolicitudConcluidas">
		<div>
			<table style="width: 100%"> 
				<tr>
					<td><a href="#"><strong>Historial de solicitudes</strong></a></td>
				</tr>
			</table>
		</div>
		<fieldset>
			<table style="width: 100%" id="solicitudesConcluidasTable">
				<caption><strong><spring:message code="label.tramitesAtendidos" /></strong></caption>
			</table>
		</fieldset>
	</fieldset>	
	
	
		<input type="hidden" id="conAsegurado" name="conAsegurado" value="<c:out value="${conAsegurado}"/>"/>
	</form:form>
	<div id="msg00" title="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none">
		<c:choose>
			<c:when test="${usuarioObj.perfilUsuario.idPerfilUsuario==1}"> 
				<spring:message code="msg00" />
			</c:when>
			<c:otherwise>
				<spring:message code="msgRegistroJefeDepto" />
  			</c:otherwise>
		</c:choose>	
	</div>
</div>			
<div id="detalleSolicitudComponent"></div>
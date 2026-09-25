<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<style>
table {
	table-layout: auto;
	width: 100%;  
} 

th,td {
  border: 1px solid black;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>

<script type="text/javascript">
	//history.go(1);
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

<script>

	$(document).ready(function() {
		$('#reporteexcel').on("click",descargarReporteExcel);
		$('#regresar').on("click",irInicio);
	});

	var descargarReporteExcel = function() {
		getReporte(1);
	}

	var descargarReporteTxt = function() {
		getReporte(2);
	}

	var getReporte = function() {
		var folio = $("#folio").val();
		var $formulario = $('#formVerReporte');
		url = context_path + "/reportes/descargaReporte?idExcelTxt=" + folio;
		$formulario.attr("action", url);
		$formulario.attr("target", "blank_");
		$formulario.submit();
	}

	var irInicio = function() {
		var $formulario = $('#formVerReporte');
		url = context_path + "/homeNormativo/asignaNivel?nivelreporte=${nivelreporte}&delegacion=${delegacion}&subdelegacion=${subdelegacion}";
		$formulario.attr("action", url);
		$formulario.removeAttr("target");
		$formulario.submit();
	}	

</script>

<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/derechohabiente.js" htmlEscape="true" />"></script>
<!-- <script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/tablaRegistroConyugeConcubinario.js" htmlEscape="true" />"></script> -->


<div class="form-comment">

	<h4 align="center"><strong><spring:message
		code="label.infoReporteConyugeConcubinario" /></strong></h4>
	<br/>

	<h4 align="center">Se ha generado correctamente el reporte, para descargarlo d&eacute; click en el bot&oacute;n correspondiente.</h4>

	<br/>

	<form:form modelAtribute="integrantes" id="integrantes" action="#" method="post">

	<!-- ---------------------------------- Registro Conyuge o Concubina(rio) --------------------------------------------------	-->

		<input type="hidden" id="conAsegurado" name="conAsegurado" value="<c:out value="${conAsegurado}"/>"/>
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

		<fieldset style="align: center" style="width: 890px">
			<table style="width: 860px"  id="agregarMod">
				<tr style="border: none">
					<td align="center" style="border: none">
						<input type="button" id="reporteexcel" name="reporteexcel" class="mboton" value="Descargar reporte"/>
					</td>
					<!--
					<td align="center" style="border: none">
						<c:if test="${(nivelreporte == 1) || (nivelreporte == 2)}">
							<input type="button" onclick="reportetxt();" id="reportetxt" name="reportetxt" class="mboton" value="Generar reporte txt"/>
						</c:if>
					</td>
					-->
					<td align="center" style="border: none">
						<c:if test="${(nivelreporte == 1) || (nivelreporte == 2)}">
							<input type="button" id="regresar" name="regresar" class="mboton" value="Regresar"/>
						</c:if>
					</td>
				</tr>
			</table>
		</fieldset>
	</form:form>

	<form id="formVerReporte" action="viewReport" method="post">
		<input type="hidden" value="${tramiteId}" id="tramiteId"/>
		<input type="hidden" id="folio" value="<c:out value="${folioReporte}"/>" />
	</form>

</div>

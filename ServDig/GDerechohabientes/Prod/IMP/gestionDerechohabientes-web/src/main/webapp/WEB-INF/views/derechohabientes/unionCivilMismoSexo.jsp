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

	$(document).ready(function(){
		$('#reporteexcel').on("click",descargarReporteExcel);
		$('#reportetxt').on("click",descargarReporteTxt);
		$('#regresar').on("click",irInicio);
	});

	var descargarReporteExcel = function() {
		getReporte(3);
	}

	var descargarReporteTxt = function() {
		getReporte(4);
	}

	var getReporte = function(idExcelTxt) {
		var $formulario = $('#formVerReporte'),
		url = context_path + "/reportes/Reporte_Registro_PersonaUnionCivil?idExcelTxt=" + idExcelTxt;
		$formulario.attr("action", url);
		$formulario.attr("target", "blank_");
		$formulario.submit();
		$.unblockUI();
	}	

	var irInicio = function() {
		var $formulario = $('#formVerReporte'),
		url = context_path + "/homeNormativo/asignaNivel?nivelreporte=${nivelreporte}&delegacion=${delegacion}&subdelegacion=${subdelegacion}";
		$formulario.attr("action", url);
		$formulario.removeAttr("target");
		$formulario.submit();
		$.unblockUI();
	}	

</script>

<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/derechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/tablaRegistroUnionCivilMismoSexo.js" htmlEscape="true" />"></script>


<div class="form-comment">
	<form:form modelAtribute="integrantes" id="integrantes" action="#" method="post">

	<h4 align="center"><strong><spring:message
		code="label.infoReporteUnionCivilMismoSexo" /></strong></h4>

	<!-- ---------------------------------- Registro pesona  en union civil del mismo sexo --------------------------------------------------	-->
	<BR>
	
	<div>
		<table id="registroConyugeConcubinarioTable">
				<caption><strong><spring:message code="label.registroconyuge" /></strong></caption>
				
		</table>
	</div>

	
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

	<fieldset style="align:ceter" style="width: 890px">
		<table style="width: 860px"  id="agregarMod">
			<tr style="border: none"> 				
				<td align="center" style="border: none">											
					<input type="button" onclick="descargarReporteExcel();" id="reporteexcel" name="reporteexcel" class="mboton" value="Generar reporte Excel"/>
				</td>			
				<td align="center" style="border: none">											
					<c:if test="${(nivelreporte == 1) || (nivelreporte == 2)}">
						<input type="button" onclick="descargarReporteTxt();" id="reportetxt" name="reportetxt" class="mboton" value="Generar reporte txt"/>
					</c:if>
				</td>			
				<td align="center" style="border: none">											
					<c:if test="${(nivelreporte == 1) || (nivelreporte == 2)}">
						<input type="button" onclick="regresar();" id="regresar" name="regresar" class="mboton" value="Regresar"/>									
					</c:if>
				</td>			
			</tr>		
		</table>
	</fieldset>								
	</form:form>
	<form id="formVerReporte" action="viewReport" method="post">
		<input type="hidden" value="${tramiteId}" id="tramiteId"/>
	</form>

</div>			
<div id="detalleSolicitudComponent"></div>
<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/combosDelSubUmf.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/homeJefeDeptoSuper.js" htmlEscape="true" />">	</script>


<head>
	<link href="<c:url value="/resources/estilos/imss/estilo.css" />" rel="stylesheet"  type="text/css" />
    <script>
		$(function() {
		  $( "#fechaInicio" ).datepicker({ dateFormat: 'dd/mm/yy'});
		});
	</script>
	<script>
		$(function() {
		  $( "#fechaFin" ).datepicker({ dateFormat: 'dd/mm/yy'});
		});
	</script>
 </head>

<div class="form-comment" align="center">
	<br>
	<br>
	<br>
	<fieldset style="width:800px" >
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
	<div style="width: 90%; float: center;" align="justify">
		<h2 id="cabecero"><spring:message code="label.cabecero.home.jefedeptosuper"/></h2>
	</div>

	<c:if test="${not empty errorDetail}">
		<div id="detailsSection" class="ui-widget-content ui-corner-all">
			<div class="ui-state-error ui-corner-all" align="center">
			<p class="ui-helper-reset ui-state-error-text">
				<div class="ui-icon ui-icon-alert"></div>
				<spring:message code="${errorDetail}" />
			</p>
			</div>
		</div>
	</c:if>

	<h4 id="infoNormaito" ><spring:message code="label.instrucciones.jefedeptosuper"/></h4>	
	
	<form:form modelAttribute="busqueda" action="${contextpath}/reportes/generarReporte" method="post" id="normativoCEForm" >
		<br/>
		<center>		
		<table id="normativoTable">
			<tr>
				<td><strong><spring:message code="label.tipotramite.jefedeptosuper"/></strong> : </td>
				<td>
					<select class="form-control" id="tramite" name="tramite">
			  			<option value="0">--Selecciona por favor--</option>
			  			<option value="1">Registro de C&oacute;nyuge o Concubina(rio)</option>
			  			<option value="2">Registro de Persona en uni&oacute;n civil</option>
				  	</select>
				</td>
			</tr>

			<tr>
				<td colspan="2"><br></td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="label.periodo.jefedeptosuper"/></strong> : </td>
				<td></td>
			</tr>

			<tr>
				<td><strong><spring:message code="label.fechainicio.jefedeptosuper"/></strong> : </td>
				<td>
					<input type="text" id="fechaInicio" name="fechaInicio"  width="10" value=""/>
			    </td>
			</tr>

			<tr>
				<td colspan="2"><br></td>
			</tr>

			<tr>
				<td><strong><spring:message code="label.fechafin.jefedeptosuper"/></strong> : </td>
				<td>
					<input type="text" id="fechaFin" name="fechaFin"  width="10" value=""/>
			  	</td>
			</tr>
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
			
			<tr id="folioReporteConyuge">
				<td><strong><spring:message code="label.folio.jefedeptosuper"/></strong> : </td>
				<td>
					<input type="text" id="folioReporte" name="folioReporte"  width="10" value="" maxlength="15" />
				</td>
			</tr>

			<tr>
				<td colspan="2"><br></td>
			</tr>
			<tr>
		  		<td align="center">
					<input type="button" id="aceptarVal" class="mboton" value="Buscar">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
					<input id="cancelar" type="button" class="mboton" value="Limpiar">
				</td>
	  		</tr>
			
		</table>
		</center>
		
		<input type="hidden" id="nivelreporte" name="nivelreporte"  width="10" value="${nivelreporte}" />
		<input type="hidden" id="delegacion" name="delegacion"  width="10" value="${delegacion}"/>
		<input type="hidden" id="subdelegacion" name="subdelegacion"  width="10" value="${subdelegacion}"/>

	</form:form>
	
		<br>			
				
		
		<table id="nssTable">
		</table>
				
	</fieldset>
	<br>	
	<div id="contenedorHomeNormativoCE"></div>
	<div id="mensajes"></div>
</div>


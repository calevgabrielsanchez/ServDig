<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/homeVentanillaAdmin.js" htmlEscape="true" />">	</script>

<head>
	
	<link href="<c:url value="/resources/estilos/imss/estilo.css" />" rel="stylesheet"  type="text/css" />			
</head>

<div class="form-comment" align="center">
	<br>
	<br>
	<br>
	<fieldset style="width:800px" >
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
	<div style="width: 90%; float: center;" align="justify">
		
			<h2 id="cabecero"><spring:message code="label.cabecero.home.ventanillaAdministrativa"/></h2>
			 
	</div>
	 <br>	
	<h4 id="infoNormaito" ><spring:message code="label.instrucciones.normativoCE"/></h4>	
	
	<form:form modelAttribute="opciones" action="${contextpath}/homeNormativo/asignaPerfil" method="post" id="ventanillaAdminForm">
		<br>			
		<center>		
		<table id="ventanillaAdminTable">
		
			
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="nss" value="1" checked="checked" class="radioVentanillaAdmin"/>NSS
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="actualizacionCorreo" value="2" class="radioActualizacionCorreo"/>Solicitudes 
					pendientes de actualizaci&oacute;n de correo
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
			
			<tr>
				
		  		<td align="center">
					<input type="button" id="aceptarVal" class="mboton"  value="Continuar">
					&nbsp;
				</td>
				
	  		</tr>
			
		</table>
		</center>
		
	</form:form>	
	
		<br>			
				
		
		<table id="nssTable">
		</table>
				
	</fieldset>
	<div id="contenedorHomeNormativoCE"></div>
	<div id="mensajes"></div>
</div>


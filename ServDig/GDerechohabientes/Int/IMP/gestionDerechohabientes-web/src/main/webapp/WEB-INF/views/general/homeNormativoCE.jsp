<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/combosDelSubUmf.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/welcome/homeNormativoCE.js" htmlEscape="true" />">	</script>


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
		
			<h2 id="cabecero"><spring:message code="label.cabecero.home.normativoCE"/></h2>
			 
	</div>
	 <br>	
	<h4 id="infoNormaito" ><spring:message code="label.instrucciones.normativoCE"/></h4>	
	
	<form:form modelAttribute="busqueda" action="${contextpath}/homeNormativo/asignaPerfil" method="post" id="normativoCEForm" >
		<br>			
		<center>		
		<table id="noramtivoTable">
		
			
			<tr>
				<td><strong><spring:message code="label.perfil.normativoCE"/></strong> : </td>
				<td>
					<input type="radio" name="perfil" id="tramitador" value="1" checked="checked" class="radioPerfil"/>TRAMITADOR
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="perfil" id="subdelegado" value="8" class="radioPerfilSubdelegado"/>JEFE DE DEPARTAMENTO AFILIACION VIGENCIA
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
				
		
					
			<tr>
				<td><strong><spring:message code="label.umf.delegacion"/></strong> : </td>
				<td>
					<select class="form-control" id="delegacion" name="delegacion">
			  			<option value="0">--Selecciona por favor--</option>
				  	</select>
				  </td>
			</tr>
			
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
			
			
			<tr>
				<td><strong><spring:message code="label.umf.subdelegacion"/> </strong> : </td>
				<td>
					<select class="form-control" id="subdelegacion" name="subDelegacion">
						<option value="0">--Selecciona por favor--</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
			
		
				
			<tr id="seccionUmf">
				
				<td><strong><spring:message code="tramite.detalle.umf"/></strong> : </td>
				<td>
					
					<select class="form-control" id="clinica" name="umf">
						<option value="0">--Selecciona por favor--</option>
					</select>
					
				</td>
				
			</tr>
			
			<tr>
				<td colspan="2"><br></td>
			</tr>
			
			
			
			<tr>
				
		  		<td align="center">
					<input type="button" id="aceptarVal" class="mboton"  value="Aceptar">
					&nbsp;
				</td>
				
				<td align="center">
					&nbsp;
					<input id = "cancelar" type="button"  class="mboton"  value="Limpiar">
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


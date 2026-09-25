<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<head>
	<!-- >title><tiles:insertAttribute name="title" ignore="true"/></title-->
	<link href="<c:url value="/resources/estilos/imss/estilo.css" />" rel="stylesheet"  type="text/css" />			
</head>

<div id="contenedor-login" class="contenedor" style="height: 500px;">

<div style="width: 90%; float: center;" align="justify">
	<h1> Sistema de Gesti&oacute;n de Derechohabientes</h1>
	<h2> Bienvenido</h2>
        <p style="font-size: .8em;"> 
        El macro proceso de incorporaci&oacute;n realiza el registro, la modificaci&oacute;n y la baja de sujetos obligados y de aseguramiento, de acuerdo a  la Ley y el "Reglamento de la ley del seguro social en materia de afiliación, clasificaci&oacute;n de empresas, recaudaci&oacute;n y fiscalizaci&oacute;n", con la finalidad de que los patrones, sujetos obligados, trabajadores y otros sujetos de aseguramiento, cumplan con dichos lineamientos, y as&iacute; poder garantizar el derecho a la seguridad social.
        </p>
</div>
<div class="credenciales">
	<div class="credenciales-caja">
	
		<h2><spring:message code="label.ingresar"/></h2>				
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form:form modelAttribute="login" action="${contextpath}/welcome/uno/busqueda" method="post" id="formlogin" >			
				<table>			
					<tr>
						<td>
							<form:label path="usuario">
								<strong class="etiqueta"> <spring:message code="label.usuario"/>   </strong>
							</form:label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<form:input  type="text"  path="usuario"  maxlength="10"/>	
						</td>
					</tr>
					<br>
					<tr>
						<td>
							<form:label path="password">
								<strong class="etiqueta"><spring:message code="label.password"/> </strong>
							</form:label>
						</td>					
					</tr>
					<tr>
						<td align="right">
							<form:input  type="password"  path="password"  id="password" maxlength="10"/>
						</td>
					</tr>
					<tr>
						<td>
							<c:if test="${login.error!=null}">
								<span style="color: red;"><spring:message code="${login.error}"/></span>
							</c:if>
						</td>
					</tr>	
					<tr>
						<td align="right">
							<input type="submit" class="mboton"  value="Aceptar" >	
						</td>
					</tr>
				</table>	
		</form:form>			
	</div>
</div>
</div>


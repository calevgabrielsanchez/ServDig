<%@ include file="../general/taglibs.jsp" %>

<div id="contenedor-login" class="contenedor" >

<div class="row">
<div class="cell informacion">
	<h1>    <spring:message code="title.system" />  </h1>
	<h2> <spring:message code="label.bienvenido" /> </h2>
        <p style="font-size: .9em;"> 
        	<spring:message code="label.informacion.sistema" />
        </p>
        
        <hr>
<p style="font-size: .9em !important;"><spring:message code="label.instruccion.java" />	</p>

<ul type="disc">	<li>		<span style="font-size: 12px;"><a href="http://idse.imss.gob.mx/imss/descargas/javapolicy.exe"><span style="color: rgb(0, 102, 102);">Configuraci&oacute;n autom&aacute;tica de Internet&nbsp; Explorer. </span></a></span></li>	<li>		<span style="font-size: 12px;"><a href="http://idse.imss.gob.mx/imss/descargas/Acerca_de_Configuracion_automatica_de_IE.pdf"><span style="color: rgb(0, 102, 102);">Manual de la Configuraci&oacute;n autom&aacute;tica de Internet&nbsp; Explorer.</span> </a></span></li></ul>

</div>

<div class="cell credenciales">
	<div class="credenciales-caja">
	<h2><spring:message code="label.ingresar" /></h2>
	<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<div><form:form modelAttribute="usuario"
		action="${contextpath}/login/entrar" method="post" id="formlogin">
	
	
		<div id="usuario-contenedor">
			<form:errors path="usuario" cssClass="error" /> 
			<form:label path="usuario">
				<strong class="etiqueta"> 
				<spring:message code="label.usuario" />
				</strong>
			</form:label> 
			<form:input type="text" path="usuario" maxlength="15" />
		</div>
	
		<div id="password-contenedor">
			<form:errors path="password" cssClass="error" /> 
			<form:label path="password">
				<strong class="etiqueta">
				<spring:message code="label.password" />
				</strong>
			</form:label> 
			<form:input type="text" path="password" id="password" value="" maxlength="13" />
		</div>
		
		<br>
	
		<div class="derecha">
			<input type="submit" class="mboton" value="<spring:message code="label.ingresar" />">
		</div>
	</form:form></div>
	</div>
</div>
</div>

</div>
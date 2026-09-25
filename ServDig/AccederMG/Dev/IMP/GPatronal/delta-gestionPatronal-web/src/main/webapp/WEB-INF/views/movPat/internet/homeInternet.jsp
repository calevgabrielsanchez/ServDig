<%--
  Created by IntelliJ IDEA.
  User: hsosa
  Date: 11/08/2023
  Time: 09:56 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../general/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="registroPatronal" value="<%=session.getAttribute(\"registroPatronal\")%>" />
<c:set var="sujetoObligado" value="<%=session.getAttribute(\"sujetoObligado\")%>" />

<script>
	var context="${contextpath}";
	var context_path="${contextpath}";
	var registroPatronal="${registroPatronal}"
</script>

<link rel="stylesheet" type="text/css"
	href='<spring:url value="/static/resources/estilos/imss/movPat/ventanilla.css" htmlEscape="true" />' />
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/validatesIMSS.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/altaPatronUX/funcionesComunes.js" htmlEscape="true" />"></script>	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/wizard/domicilio/Domicilio.js" htmlEscape="true" />"></script>			
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/movPat/internet/registrar/tramiteInternet.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/internet/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionInternetWizard.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="https://framework-gb.cdn.gob.mx/data/encuesta_v1.0/encuestas.js"></script>


	

<div id="divHome" class="home-ven-msg77">
	<div class="separadorseccion text-center">
		<h4>
			<spring:message code="movpat.internet.home.title" />
		</h4>
	</div>
	<div class="text-center">
		<spring:message code="movpat.internet.home.msg77" />
	</div>
	<div class="home-ven-actions">
		<form class="form-horizontal" id="formRegistroPatronal" action="#"
				method="get">
				<button type="button"
					class="btn btn-primary" onclick="navegarADetalleDeRP();">Tr&aacute;mite de Modificaci&oacute;n Patronal en el SRT</button>
		</form>
	</div>

</div>


<div id="dialogoMensajes">
	<p>
		<span id="textoMensaje"></span>
	</p>
</div>

<div id="wizardModificacionClasificacionInternet"></div>
<div id="reporteFrame"></div>
<div id="domiciliosComponent"></div>


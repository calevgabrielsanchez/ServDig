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
<script>
	var context="${contextpath}";
	var context_path="${contextpath}";
</script>

<link rel="stylesheet" type="text/css"
	href='<spring:url value="/static/resources/estilos/imss/movPat/ventanilla.css" htmlEscape="true" />' />
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.blockUI.js" htmlEscape="true" />"></script>	
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
	src="<spring:url value="/static/resources/js/movPat/ventanilla/cita.js" htmlEscape="true" />"></script>		
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/movPat/registrar/tramiteVentanilla.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionVentanillaWizard.js" htmlEscape="true" />"></script>	

<div id="divHome" class="home-ven-msg77">
	<div class="separadorseccion text-center">
		<h4>
			<spring:message code="movpat.ventanilla.home.title" />
		</h4>
	</div>
	<div class="text-center">
		<spring:message code="movpat.ventanilla.home.msg77" />
	</div>
	<div class="home-ven-actions">
		<form action="" id="formAvisoModificacion" method="post">
			<button type="button" id="btnAvisoModificacionSRT"
				class="btn btn-primary">Tr&aacute;mite de Modificaci&oacute;n Patronal en el SRT</button>
		</form>
	</div>
</div>

<div id="divContenidoCita" class="contenido-cita">
	<div class="separadorseccion text-center">
		<h4>
			Tr&aacute;mite de Modificaci&oacute;n Patronal en el SRT
		</h4>
	</div>
	
	<div id="divOpcionesCita" class="acciones-cita">
		<button type="button" id="btnConCita" class="btn btn-default">Con
			Cita</button>
		<button type="button" id="btnSinCita" class="btn btn-default">Sin
			Cita</button>
	</div>
	<div id="divCita">
		<form class="form-horizontal" id="formBuscarFolio" action="#"
			method="get">
			<div class="form-group">
				<label class="control-label col-sm-3" for="folioCita"> Folio
					de la Cita<span class="required">(*)</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="folioCita"
						id="folioCita" value="" maxlength="25"
						placeholder="Ingresa el Folio de la Cita" />
				</div>
				<div class="col-sm-4 top-4">
					<button type="button" id="btnBuscarFolio" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"></span>
						Buscar
					</button>
					<button type="button" id="btnCancelarFolio"
						class="btn btn-danger">Cancelar</button>
				</div>
			</div>
		</form>
	</div>
	
	<div id="divRp">
		<form class="form-horizontal" id="formRegistroPatronal" action="#"
			method="get">
			<div class="form-group">
				<label class="control-label col-sm-3" for="folioCita"> 
					<spring:message code="label.nrp" />(*):
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="numRegistroPatronal"
						id="numRegistroPatronal" value="" maxlength="25"
						placeholder="Ingresa el Registro Patronal" />
				</div>
				<div class="col-sm-4 top-4">
					<input type="button" id="btnConsultarRegistroPatronal"
					onclick="navegarADetalleDeRP();" class="btn btn-primary" name="consultar"
					value="Registrar Tr&aacute;mite" />
					<input type="button" id="btnCancelarRp" class="btn btn-danger" name="cancelar"
					value="Cancelar" />
				</div>
			</div>
		</form>
	</div>
</div>


<div id="dialogoMensajes">
	<p>
		<span id="textoMensaje"></span>
	</p>
</div>

<div id="wizardModificacionClasificacionVentanilla"></div>
<div id="reporteFrame"></div>
<div id="domiciliosComponent"></div>


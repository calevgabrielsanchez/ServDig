<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@page pageEncoding="ISO-8859-1"
	contentType="text/html; charset=ISO-8859-1"%>

<c:set var="scheme" value="<%=request.getScheme()%>" />
<c:set var="server" value="<%=request.getServerName()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="recurso" value="${scheme}://${server}" />
<c:set var="urlInformacionAdicional"
	value="${contextpath}/wizard/correccionDatosAsegurado/informacionAdicional/finalizaInformacionAdicional" />
<c:set var="urlCancelarInformacionAdicional"
	value="${contextpath}/wizard/correccionDatosAsegurado/informacionAdicional/cancelaInformacionAdicional"></c:set>




<style type="text/css">
.image-upload>input {
	display: none;
}

.image-upload img {
	overflow: hidden;
	cursor: pointer;
	vertical-align: middle;
}

label img {
	pointer-events: none;
}

.icono-help {
	color: black;
	font-family: FontAwesome;
	font-size: 27px;
	padding: 0 10px;
	text-decoration: none;
}

.icono-help::before {
	content: "\f059";
}

.popover {
	max-width: 500px;
}

#registroNSS .tooltip .tooltip-inner {
	width: 350px;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/correccionDatosCurpFileUpload.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.dateSelectBoxes.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/infoAdicionalSolicitud/datosAseguradoDocumento.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/infoAdicionalSolicitud/datosNSSDocumentos.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/infoAdicionalSolicitud/datosBeneficiario.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/infoAdicionalSolicitud/informacionAdicional.js" htmlEscape="true" />"></script>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<div class="contenedor">
	<form:form method="post" modelAttribute="informacionAdicional"
		id="informacionAdicionalForm" action="${urlInformacionAdicional}"
		accept-charset="ISO-8859-1">
		<div id="info-paso" style="margin-bottom: 50px;">

			<div class="contenedor">
				<h3>
					<spring:message code="label.seguimiento.solicitud" />
				</h3>
				<hr class="red" style="margin-bottom: 20px;">
			</div>

			<!-- Forma de la consulta a RENAPO. -->
			<div class="row col-md-12">
				<label class="control-label"><spring:message
						code="label.datos.solicitante" /> </label>
			</div>
			<div class=" row col-md-2">
				<label class="control-label"><spring:message
						code="label.curp" /> </label>
			</div>
			<div class=" row col-md-3">${informacionConsulta.curp}</div>
			<div class="col-md-2">
				<label class="control-label"> <spring:message
						code="label.nombre" />
				</label>
			</div>
			<div class="col-md-5">${informacionConsulta.nombre}</div>
			<div class="row col-md-12">
				<hr class="red" style="margin-bottom: 20px;">
				<h3>
					<spring:message code="label.solicitud.infoAdicional.requerimiento" />
					<!--Requerimiento de información para el Asegurado-->
				</h3>
				<hr class="red" style="margin-bottom: 20px;">
			</div>
			<%@ include file="infoAdicionalSolicitud/tipoInteresado.jsp"%>
			<div class="row col-md-12">
				<label><spring:message
						code="label.docsProbatorios.tipoDocumento" /></label>
			</div>
			<%@ include file="infoAdicionalSolicitud/documentosProbAsegurado.jsp"%>
			<%@ include file="infoAdicionalSolicitud/datosNSS.jsp"%>
			<c:if test="${informacionAdicional.tipoSolicitante!=null && informacionAdicional.tipoSolicitante!='ASEGURADO'}">
				<%@ include file="infoAdicionalSolicitud/documentoBeneficiario.jsp"%>
				<div class="row col-md-12">
					<hr class="red" style="margin-bottom: 20px;">
				</div>
			</c:if>
			<div class="row col-md-12" id="info-paso" style="margin-bottom: 50px;">
				<h3>
					<spring:message code="label.solicitud.observaciones" />
				</h3>
				
				<div class="form-group">
					<div class="row">
						<div class="col-md-12 col-sm-7 col-xs-12">
							<form:textarea path="observaciones" rows="5" cols="10"
								cssClass="form-control" style="font-size: 18px;" maxlength="250"
								cssStyle="text-transform: uppercase;" />
						</div>
					</div>
				</div>

			</div>
		</div>
	</form:form>
	<div class="row col-md-12">
		<div class="pull-right">
			<button type="button" id="continuarInformacionAdicional"
				class="btn btn-primary">
				<spring:message code="label.continuar" />
			</button>
		</div>

		<div class="pull-right" style="margin-right: 10px;">
<%-- 			<jsp:include page="cancelarSolicitud.jsp" /> --%>
			<form:form method="POST" id="cancelarForm" action="${urlCancelarInformacionAdicional}">
<button class="btn btn-default" id="cancelarSolicitudButton" >Cancelar</button>
	<div id="cancelarSolicitud" title="Cancelar solicitud" hidden="true">
		<p>
			<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"> </span>
			<spring:message code="label.confirmacion.cancelar"/>		</p>
		<br />
	</div>
</form:form>
		</div>
	</div>
</div>




	

<script>
	
	objDialog = $('#cancelarSolicitud').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:300,
	        width:600,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {					
					$("#cancelarForm").submit();					
					},
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
	 $('#cancelarSolicitudButton').click(function(e) {
		 e.preventDefault();
		 
		objDialog.dialog('open');
	});
</script>

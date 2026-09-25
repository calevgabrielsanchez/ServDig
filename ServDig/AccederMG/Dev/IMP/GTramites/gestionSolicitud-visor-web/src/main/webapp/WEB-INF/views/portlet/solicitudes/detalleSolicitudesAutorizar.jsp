<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/portlet/solicitudesCorreoPortlet.js" htmlEscape="true" />"></script>

<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="idEstadoRechazada" value="<%=EstadoSolicitudEnum.RECHAZADA.getCodigo()%>" />
<c:set var="idEstadoCancelada" value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>" />

<style>
	.subtitulo {
		font-size: small;
		font-weight: bold;
		line-height: 1em;
		margin-bottom: 0.5em;
		margin-top: 0;
	}
	
	.linkFolio {
		cursor: pointer;
		color: #0088CC;
		font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
	}
	
	.desc-campo {
		font-weight: bold !important;
	}
	
	fieldset.seccionDocumentos {
		width: 100%;
		max-height: 200px; 
    	overflow-y: auto;
	}
	
	fieldset.seccionDocumentos legend {
		font-size: small;
		font-weight: bold;
	}
	
</style>


<script>
	$(function() {
		var gridSolicitud = $('#tblTramitesSolicitud').dataTable({
			"sPaginationType" : "bootstrap",
			"bLengthChange" : false,
			"bFilter" : false,
			"bSort" : false
		});
		
		$( "#tabs" ).tabs();
	});
</script>


<script>
	$(document).ready(function() {
		submitForm();
	});

	function submitForm() {
 		if ($("#forma").length)
			$("#forma").submit(); 

 		if ($("#formaResultantes").length)
			$("#formaResultantes").submit(); 

 		if ($("#firmaIframe").length)
			document.getElementById("firmaIframe").style.display = "inline"; 

  		if ($("#firmaIframeResultantes").length)
			document.getElementById("firmaIframeResultantes").style.display = "inline"; 
	}
</script>

<input type="hidden" id="idSolicitud" value="${solicitud.solicitudIdHashed}" />
<input type="hidden" id="idTramite" value="${tramite.tramiteIdHashed}" />

<div class="col-sm-12">
	<c:choose>
		<c:when test="${not empty solicitud.errorFormGeneral }">
			<div class="alert alert-danger" id="divMsgErrores">
				${solicitud.errorFormGeneral}
			</div>
		</c:when>
		<c:otherwise>
			<table style="width: 100% !important; font-size: smaller;"
				class="table table-bordered" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<td class="desc-campo"><spring:message
							code="label.numero.folio" />:</td>
					<td colspan="3">${solicitud.noFolioSolicitud}</td>
				</tr>

				<tr>
					<td class="desc-campo"><spring:message
							code="label.fecha.presentacion" />:</td>
					<td><fmt:formatDate value="${solicitud.fechaSolicitud}"
							pattern="dd/MM/yyyy HH:mm:ss" /></td>

				</tr>

				<tr>
					<td class="desc-campo"><spring:message
							code="label.estatus.solicitud" />:</td>
					<td colspan="3">[${
						solicitud.estadoSolicitud.idEstadoSolicitud}] ${
						solicitud.estadoSolicitud.descripcion }</td>
				</tr>

				<tr>
					<td class="desc-campo"><spring:message
							code="label.origen.solicitud" />:</td>
					<td colspan="3">${solicitud.origenSolicitud.descripcion }</td>
				</tr>
				
				<tr>
					<td class="desc-campo">Correo electr&oacute;nico:</td>
					<td colspan="3">${tramite.correoCapturado}</td>
				</tr>
				
				<tr>
					<td class="desc-campo">CURP:</td>
					<td colspan="3">${tramite.persona.curp}</td>
				</tr>
				
				<tr>
					<td class="desc-campo"><spring:message
							code="label.nombre" />:</td>
					<td colspan="3">${tramite.persona.nombre} ${tramite.persona.primerApellido} 
					${tramite.persona.segundoApellido}</td>
				</tr>
				
				
			</table>
			<br>

			<fieldset class="seccionDocumentos">

				<legend>Documentos</legend>
 				<form
					action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento"
					class="formNotBlock" method="post" target="firmaIframe" id="forma">
					<input type="hidden" name="params"
						value="{&quot;tramite&quot;:&quot;${solicitud.secuenciaDeNotaria}&quot;}" />
				</form> 
<%--  				<form
					action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento"
					class="formNotBlock" method="post" target="firmaIframeResultantes"
					id="formaResultantes">
					<input type="hidden" name="params"
						value="{&quot;tramite&quot;:&quot;${solicitud.secuenciaDeNotaria}&quot;}" />
				</form> --%> 
				
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Documentos Adjuntos</a></li>
						<!-- <li><a href="#tabs-2">Documentos Adjuntos</a></li> -->
					</ul>
 					<div id="tabs-1">
						<iframe id="firmaIframe" name="firmaIframe" height="500"
							style="display: none; width: 100%" frameborder="0"></iframe>
					</div>
<!-- 					<div id="tabs-2">
						<iframe id="firmaIframeResultantes" name="firmaIframeResultantes"
							height="500" style="display: none; width: 100%" frameborder="0"></iframe>
					</div> -->
				</div>

			</fieldset>
			<br>
				<c:if test="${not (solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida
				            || solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoCancelada)}">
				    <div style="display: flex; justify-content: center;">
				        <button type="button" id="btnAprobar" onclick="abrirDocumentoResultante('${solicitud.solicitudIdHashed}','${tramite.tramiteIdHashed}', 'APROBAR')">
				            Aprobar
				        </button>
				        <button type="button" id="rechazarSolicitud">
				            Rechazar
				        </button>
				    </div>
				</c:if>
			
			<form id="solicitudDocumentoForm" name="solicitudDocumentoForm" method="get" class="formNotBlock" action="${contextpath }/portlet/solicitudes/mostrarDocumentoResultanteCorreo">
				<input type="hidden" name="idSolicitud" id="idSolicitud" /> 
				<input type="hidden" name="idTramite" id="idTramite" /> 
				<input type="hidden" name="accion" id="accion" />
				<input type="hidden" name="motivo" id="motivo" />
			</form>

		</c:otherwise>
	</c:choose>
</div>
<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
	var contextPath="${contextpath}";
	var banderaContinuarTramite = "${banderaContinuarTramite}";
	var banderaContinuarTramiteAtendido = "${banderaContinuarTramiteAtendido}";
	var mismaSubdelegacion = "${informacionConsulta.mismaSubdelegacion}";
</script>
<c:set var="urlSeguimiento"
	value="${contextpath}/wizard/correccionDatosAsegurado/generarCertificacion.do"></c:set>
<%-- <c:set var="estadoCancelada" value="<%=EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getDescripcion().toUpperCase()%>" /> --%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/seguimientoTramite.js" htmlEscape="true" />"></script>
<div id="info-paso" style="margin-bottom: 50px;">

	<div class="contenedor">
		<h3>
			<spring:message code="label.seguimiento.solicitud" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;">
	</div>

<c:if test="${!informacionConsulta.mismaSubdelegacion && !banderaContinuarTramite}">
			<div class="alert alert-warning">
				<spring:message code="label.mensaje.seguimiento.subdelegacion"
					arguments="${informacionConsulta.subdelegacion}"/>
			</div>
		</c:if>

	<!-- Forma de la consulta a RENAPO. -->
	<div class="col-md-12">
		<label class="control-label"><spring:message
				code="label.datos.solicitante" />
		</label>
	</div>
		
		
			
		<div class="col-md-2">
			<label class="control-label"><spring:message
					code="label.curp" />
			</label>
		</div>
		<div class="col-md-3">${informacionConsulta.curp}</div>

		<div class="col-md-2">
			<label class="control-label"> <spring:message
					code="label.nombre" />
			</label>
		</div>
		<div class="col-md-5">${informacionConsulta.nombre}</div>	
	<br />
	<div class="col-md-12">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th style="vertical-align: top;"><spring:message code="label.folio" /></th>
				<th style="vertical-align: top;"><spring:message code="label.fecha.solicitud" /></th>
				<th style="vertical-align: top;"><spring:message code="label.nss.involucrado" /></th>
				<th style="vertical-align: top;"><spring:message code="label.subdelegacion.seguimiento" /></th>
				<th style="vertical-align: top;"><spring:message code="label.estatus" /></th>
				<c:if test="${informacionConsulta.estatusMot}">
				<th style="vertical-align: top;"><spring:message code="label.motivo.cancelacion" /></th>
				</c:if>
				<th style="vertical-align: top;"><spring:message code="label.constancia.tramite" /></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<c:choose>
					    <c:when test="${informacionConsulta.mismaSubdelegacion}">
					        <button class="btn btn-link" id="folioSeguimiento">${informacionConsulta.folio}</button>
					    </c:when>    
					    <c:otherwise>
					        <p style="font-size: 1.3em;">${informacionConsulta.folio}</p>
					    </c:otherwise>
					</c:choose>
				</td>
				<td><p style="font-size: 1.3em;">${informacionConsulta.fechaSolicitud}</p></td>
				<td><p style="font-size: 1.3em;">${informacionConsulta.nss}</p></td>
				<td><p style="font-size: 1.3em;">${informacionConsulta.subdelegacion}</p></td>
				<td><p style="font-size: 1.3em;">${informacionConsulta.status}</p></td>
				<c:if test="${informacionConsulta.estatusMot}">
					<td><p style="font-size: 1.3em; text-align:justify;">${informacionConsulta.motivoCancelacion}</p></td>
				</c:if>
					<c:choose>
						<c:when test="${informacionConsulta.estatusDescarga}">
							<td><button class="btn btn-default" id="descargarCertificacion">Descargar</button></td>
						</c:when>
						<c:otherwise>
							<td><button class="btn btn-default disabled">Descargar</button></td>
						</c:otherwise>
					</c:choose>
				</tr>
		</tbody>
	</table>
	</div>
</div>

<form:form action="${urlSeguimiento}" method="POST"
		modelAttribute="informacionConsulta" id="formSeguimiento" role="form" accept-charset="ISO-8859-1" target="_blank">
		<form:hidden path="folio"/>
		<form:hidden path="idTramite"/>
</form:form>

<form:form action="${urlSeguimiento}" method="POST"
		modelAttribute="informacionConsulta" id="formSeguimientoSolicitud" role="form" >
		<form:hidden path="folio"/>
		<form:hidden path="idTramite"/>
		<form:hidden path="propietarioTarea"/>
		<form:hidden path="idTarea"/>
</form:form>

<form id="concluirSolicitudForm" class="formNotBlock" method="GET">
	<div class="col-md-12">
		<div class ="pull-right">
		<button class="btn btn-default" id="concluirTramiteButton">Salir</button>
		</div>
	</div>
</form>

<script>
      
     $('#concluirTramiteButton').click(function(e) {
        e.preventDefault();
		
		 var url = context_path
				      + '/wizard/correccionDatosAsegurado/concluirSolicitud';
        
		$('form#concluirSolicitudForm').attr('action', url);
		$('form#concluirSolicitudForm').submit();
		
      }); 
      
      
      $('#folioSeguimiento').click(function(e) {
        e.preventDefault();
		
		 var url = context_path
				      + '/responsableSeguimiento';
        
		$('form#formSeguimientoSolicitud').attr('action', url);
		$('form#formSeguimientoSolicitud').submit();
		
      }); 
      
      
</script>

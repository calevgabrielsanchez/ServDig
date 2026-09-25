<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="5" />
	</jsp:include>
	<div>
		<h4>
			<spring:message code="label.folio.tramite" />
		</h4>
		<p>
			<spring:message code="label.leyenda.registro.solicitud"
			    arguments="${sol.subdelegacion.descripcion}" 
			    htmlEscape="true"
			    argumentSeparator=";"/>
		</p>
		<p>
			<spring:message code="label.leyenda.registro.solicitud.complemento" />
		</p>
		<p>
			<spring:message code="label.descargar.tramite.leyenda" />
		</p>
		<div>
				<div>
		<div class="col-md-12">
	<table class="table table-bordered" style="font-size:18px !important;">
		<thead>
			<tr>
				<th><spring:message code="label.folio.solicitud"/></th>
				<th><spring:message code="label.fecha.folio"/></th>
				<th><spring:message code="label.solicitud.nombre"/></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${sol.noFolioSolicitud}</td>
				<td><p><fmt:formatDate pattern="dd-MM-yyyy" value="${sol.fechaSolicitud}"/></p></td>
				<td>Solicitud de Regularización y/o Corrección de Datos Personales del Asegurado</td>
				<td><form id="folioTramiteForm" class="formNotBlock" method="get">
				<input type="hidden" id="datossolicitudH" value="${sol.solicitudId}" />
				<button class="btn btn-link" id="descargarComp" style="color:#545454" ><span class="glyphicon glyphicon-save" aria-hidden="true"></span>
				</button>
			</form></td>
				</tr>
		</tbody>
	</table>

		
		</div>
		</div>
		<div>
		<c:if test="${personaCorreccion.correoElectronico.correo != '' }">
			<spring:message code="label.comprobante.enviado.email" 
				arguments="${personaCorreccion.correoElectronico.correo}"/>
				</c:if>
		</div>
			<form id="concluirSolicitudForm" class="formNotBlock" method="GET">
				<div class="pull-right">
					<button class="btn btn-default" id="concluirTramiteButton">Salir</button>
				</div>
			</form>
	</div>
</div>

<script>
	$(document).ready(function() {
	$('#descargarComp').click(function(e) {
		e.preventDefault();

		var url = context_path
							+ '/wizard/correccionDatosAsegurado/descargarComprobante';

								$('form#folioTramiteForm').attr('action', url);
								$('form#folioTramiteForm').attr('alreadyDownloaded','true');
								$('form#folioTramiteForm').submit();

					});
		});
      
      
     $('#concluirTramiteButton').click(function(e) {
        e.preventDefault();
		
		 var url = context_path
				      + '/wizard/correccionDatosAsegurado/concluirSolicitud';
        
		$('form#concluirSolicitudForm').attr('action', url);
		$('form#concluirSolicitudForm').submit();
		
      });
      
      
</script>
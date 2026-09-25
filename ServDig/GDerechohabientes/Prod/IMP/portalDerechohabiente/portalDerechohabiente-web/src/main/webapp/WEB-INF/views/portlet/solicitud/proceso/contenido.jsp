<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<c:set var="tipoSolicitudRegistrada"
	value="<%=EstadoSolicitudEnum.REGISTRADA.getCodigo()%>" scope="page"></c:set>
<c:set var="tipoSolicitudEnProceso"
	value="<%=EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()%>" scope="page"></c:set>
<c:set var="tipoSolicitudAtendida"
	value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" scope="page"></c:set>
<c:set var="tipoSolicitudCancelada"
	value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>" scope="page"></c:set>

<c:choose>
	<c:when test="${not empty solicitudes }">
		<div id="tblSolicitudesWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblSolicitudesResumen" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Folio</th>
						<th>Tipo</th>
						<th>Tr&aacute;mites</th>
						<th>Estado</th>
						<th>Fecha de presentaci&oacute;n</th>
						<th>Fecha presentaci&oacute;n completa</th>
						<th>Clave estado</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${solicitudes}" var="solicitud"
						varStatus="indice">
						<tr>
							<td>${solicitud.noFolioSolicitud}</td>
							<td>${solicitud.tipoSolicitud.descripcion}</td>
							<td>
								<ul class="listTramties">
									<c:forEach items="${solicitud.tramites}" var="tramite"
										varStatus="indice">
										<li>${tramite.tipoTramite.descripcion}</li>
									</c:forEach>
								</ul>
							</td>
							<td style="text-align: center; vertical-align: middle;">
								<c:choose>
									<c:when test="${solicitud.estadoSolicitud.idEstadoSolicitud eq tipoSolicitudRegistrada}">
										<c:set var="claseDescEdoSolic" value="label label-warning" />
									</c:when>
									<c:when test="${solicitud.estadoSolicitud.idEstadoSolicitud eq tipoSolicitudEnProceso}">
										<c:set var="claseDescEdoSolic" value="label label-info" />
									</c:when>
									<c:when test="${solicitud.estadoSolicitud.idEstadoSolicitud eq tipoSolicitudAtendida}">
										<c:set var="claseDescEdoSolic" value="label label-success" />
									</c:when>
									<c:when test="${solicitud.estadoSolicitud.idEstadoSolicitud eq tipoSolicitudCancelada}">
										<c:set var="claseDescEdoSolic" value="label label-danger" />
									</c:when>
									<c:otherwise>
										<c:set var="claseDescEdoSolic" value="label label-default" />
									</c:otherwise>
								</c:choose>
								<span class="${claseDescEdoSolic}">${solicitud.estadoSolicitud.descripcion}</span>
							</td>
							<td>${solicitud.fechaPresentacionParse}</td>
							<td>${solicitud.fechaPresentacion}</td>
							<td>${solicitud.estadoSolicitud.idEstadoSolicitud}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br> <br>
	</c:when>
	<c:otherwise>
		<span> <spring:message
				code="label.portlet.sin.resultados.solicitudes" />
		</span>
	</c:otherwise>
</c:choose>

<script id="initPortlet">
	$('#tblSolicitudesResumen').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [
			{"iDataSort": 5, "aTargets": [ 4 ] },
			{ "bVisible": false, "aTargets": [ 5, 6 ] },
			{"fnRender": function ( oObj ) {					
				var row = oObj.aData;		
				return '<a onclick="ejecutarConsultaSolicitudPorFolio(\'' + row[0] + '\')" ' +
        			' class="link">' + row[0] + '</a>';
			},  "sSortDataType": "html", "sType": "html", "aTargets": [0]}
		],
		"aaSorting": [[ 4, "desc" ]]
	});
</script>
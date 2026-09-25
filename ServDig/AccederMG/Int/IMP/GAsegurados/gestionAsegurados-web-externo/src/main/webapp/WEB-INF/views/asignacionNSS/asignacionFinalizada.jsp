<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<style>

.icono-tramite {
	font-size: 2em;
}

.icono-tramite a {
	color: #545454;
	text-decoration: none;
}

.icono-tramite a:hover {
	color: black;
}

</style>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/asignacionNSS/asignacionFinalizada.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<input type="hidden" value="${tramiteAseguradoSession.tipoTramite.homoclave}" id="homoclaveTramite"/>
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="3" />
	</jsp:include>
	
	<div class="alert alert-success"> 
		<c:if test="${!NSS_RECUPERADO}">Asignaci&oacute;n de NSS exitosa.<br>NSS:</c:if> 
		<c:if test="${NSS_RECUPERADO}">Tu NSS es:</c:if> 
		${tramiteAseguradoSession.fisica.nss}
	
	</div>
	
	<h4><spring:message code="label.doctos"/></h4>
	<hr class="red" style="margin-bottom:25px">
	
	<div class="row" style="margin-bottom: 25px;">
		<div class="col-md-12">
			<p><spring:message code="label.tramite.asignacionNSS.indicaciones.final"/></p>
		</div>
	</div>
	
	<div class="row m-t-md"  style="margin-bottom: 50px;">
		<div class="col-sm-12">
			<div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.folio"/></th>
						<th><spring:message code="label.fecha"/></th>
						<th><spring:message code="label.documento"/></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>	
				</thead>
				<tbody>
					<tr>
						<td>${solicitudNssExterno.noFolioSolicitud}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${solicitudNssExterno.fechaConclusion}"/></td>
						<td><spring:message code="label.tramite.asignacionNSS.docto.comprobante"/></td>
						<td>
									<div class="row icono-tramite">
										<div class="col-sm-12  text-center">
											<a class="reenvioMAIL glyphicon glyphicon-envelope" id="enviarReporte" onclick="uid_call('imss.asegurados.asignacion_nss.enviar_reporte_NSS','clickin')"></a>
										</div>
									</div>
						</td>
						<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="icon-printing" id="imprimirReporte" onclick="uid_call('imss.asegurados.asignacion_nss.imprimir_reporte_NSS','PDF')"></a>
									</div>
								</div>
						</td>
						<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="glyphicon glyphicon-download-alt"
											id="descargarReporte" onclick="uid_call('imss.asegurados.asignacion_nss.descarga_reporte_NSS','download')"></a>
									</div>
								</div>
						</td>
					</tr>
					<tr>
						<td>${solicitudNssExterno.noFolioSolicitud}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${solicitudNssExterno.fechaConclusion}"/></td>
						<td><spring:message code="label.tramite.asignacionNSS.docto.tarjeta"/></td>
						<td>
									<div class="row icono-tramite">
										<div class="col-sm-12  text-center">
											<a class="reenvioMAIL glyphicon glyphicon-envelope" id="enviarTarjeta" onclick="uid_call('imss.asegurados.asignacion_nss.envia_tarjeta_NSS','clickin')"></a>
										</div>
									</div>
						</td>
						<td>
								<div class="row icono-tramite text-center">
									<div class="col-sm-12">
										<a class="icon-printing" id="imprimirTarjeta" onclick="uid_call('imss.asegurados.asignacion_nss.imprimir_tarjeta_NSS','PDF')"></a>
									</div>
								</div>
						</td>
						<td>
								<div class="row icono-tramite text-center">
									<div class="col-sm-12">
										<a class="glyphicon glyphicon-download-alt" id="descargarTarjeta" onclick="uid_call('imss.asegurados.asignacion_nss.descargar_tarjeta_NSS','download')"></a>
									</div>
								</div>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	
	<div class="row" >
		<div class="col-sm-12 text-right">
			<button class="btn btn-primary salir" id="finalizaTramite" onclick="uid_call('imss.asegurados.asignacion_nss.salir_tramite','clickout')"><spring:message code="label.tramite.btn.finalizar"/></button>
		</div>
	</div>
	
	<jsp:include page="../common/pieTramites.jsp">
		<jsp:param name="tipoTramite" value="true" />
	</jsp:include>
	
	<form id="formVerReporte" method="post" target="_blank" >
		<input type="hidden" value="${tramiteId}" id="tramiteId"/>
	</form>
	
	<form id="formSalir" action="salir" method="get">
	</form>
	
	<div id="mensajes"></div>
</div>
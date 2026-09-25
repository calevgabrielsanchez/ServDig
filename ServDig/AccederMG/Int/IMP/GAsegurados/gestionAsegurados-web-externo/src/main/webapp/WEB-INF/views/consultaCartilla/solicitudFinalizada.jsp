<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<style>

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.required {
	color: red;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}

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
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/consultaCartilla/solicitudFinalizada.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<input type="hidden" value="${homoclaveTramite}" id="homoclaveTramite"/>
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="2" />
	</jsp:include>
	
	<h4><spring:message code="label.doctos"/></h4>
	<hr class="red" style="margin-bottom:25px">
	
	<div class="row" style="margin-bottom: 25px;">
		<div class="col-md-12">
			<p><spring:message code="label.tramite.consultaCartilla.indicaciones.final"/></p>
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

					<c:forEach items="${solicitudes}" var="solicitudCartilla">
						<tr>
							<td>${solicitudCartilla.noFolioSolicitud}</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${solicitudCartilla.fechaConclusion}"/></td>
							<td><spring:message code="label.tramite.consultaCartilla.docto.comprobante"/></td>
							<td>
									<div class="row icono-tramite">
										<div class="col-sm-12  text-center">
											<a class="glyphicon glyphicon-envelope" id="enviarReporte${solicitudCartilla.noFolioSolicitud}"  onclick="reenviarCorreo('${solicitudCartilla.noFolioSolicitud}','${solicitudCartilla.solicitudId}')"></a>
										</div>
									</div>
							</td>
							<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="icon-printing" id="imprimirReporte${solicitudCartilla.noFolioSolicitud}" onclick="imprimirReporte('${solicitudCartilla.noFolioSolicitud}')"></a>
									</div>
								</div>
							</td>
							<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="glyphicon glyphicon-download-alt" id="descargarReporte${solicitudCartilla.noFolioSolicitud}" onclick="descargarReporte('${solicitudCartilla.noFolioSolicitud}')"></a>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>					

				</tbody>
			</table>
			</div>
		</div>
	</div>
	
	<div class="row" >
		<div class="col-sm-12 text-right">
			<button class="btn btn-primary salir" id="finalizaTramite" onclick="uid_call('imss.asegurados.consulta_vigencia.salir_tramite','clickout')"><spring:message code="label.tramite.btn.finalizar"/></button>
		</div>
	</div>
	
	<%--
	<jsp:include page="../common/pieTramites.jsp">
		<jsp:param name="tipoTramite" value="true" />
	</jsp:include> --%>
	
	<form id="formVerReporte" action="viewReport" method="post" target="_blank" >
		<input type="hidden" value="${tramiteId}" id="tramiteId"/>
	</form>
	
	<form id="formSalir" action="salir" method="get">
	</form>
	
	<div id="mensajes"></div>
</div>
<script src="${mvn.url.encuesta}"></script>
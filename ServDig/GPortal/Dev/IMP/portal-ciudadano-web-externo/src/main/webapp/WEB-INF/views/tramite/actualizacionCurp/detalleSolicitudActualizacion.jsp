<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="contadorTramites" value="0" scope="page" />

<script type="text/javascript">
	history.go(1);
</script>

<script type="text/javascript" src="<spring:url value="/resources/js/delta/common/detalleSolicitud.js" htmlEscape="true" />"></script>

<style>

.ui-widget-overlay {
	position: fixed;
}

.ui-dialog-titlebar {
    display:none;
}

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

<div>
	<c:choose>
		<c:when test="${error}">
			<div class="alert alert-danger">
				<strong>Error: </strong>${error}
			</div>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-sm-12">
					<h2><spring:message code="asegurado.tramite.correccion.title" /></h2>
					<hr class="red">
				</div>
			</div>
		
			<jsp:include page="../../common/paginadorGenerico.jsp">
				<jsp:param name="pasos" value="1,2,3" />
				<jsp:param name="activo" value="3" />
				<jsp:param name="mensaje" value="Finalizar tramite" />
			</jsp:include>
			
			<div class="row">
				<div class="col-md-7 col-sm-12">
					<h4>
						<strong>Bienvenido(a) ${ciudadano.nombreCompleto}</strong>
					</h4>
				</div>
				<div class="col-md-5 col-sm-12">
					<h4>
						<strong>CURP: ${ciudadano.curp}</strong>
					</h4>
				</div>
			</div>
			
			<div class="row m-t-md">
				<div class="col-sm-12">
					<h4>PASO 3: Finalizar Tr&aacute;mite
				</div>
			</div>
			
			<div class="row m-t-sm">
				<div class="col-sm-12">
					<div class="alert alert-success">
						<strong>Haz finalizado tu tr&aacute;mite con &eacute;xito</strong>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-6">
					Estos son los datos de tu tr&aacute;mite en l&iacute;nea:
				</div>
				<div class="col-sm-6" style="text-align: right;">
					<span><a onclick="imprimirAcuse()">Imprimir Comprobante</a></span>
					<span class="icono-tramite" id="iconImprimeAcuse"><a onclick="imprimirAcuse()" class="icon-printing"></a></span>
				</div>
			</div>

			<div class="row m-t-md">
				<div class="col-sm-12">
					<div class="table-responsive">
						<table id="tblDetalleSolicitud" class="table table-bordered table-striped" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th>Folio</th>
									<th>Fecha y hora</th>
									<th>Tr&aacute;mite</th>
									<th>CURP anterior</th>
									<th>CURP actualizada</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${keySolicitudSession.noFolioSolicitud}</td>
									<td>
										<fmt:formatDate value="${keySolicitudSession.fechaSolicitud}" pattern="dd/MM/yyyy hh:mm a" />
									</td>
									<td>${keyTramiteCambioCurp.tipoTramite.descripcion}</td>
									<td>${keyTramiteCambioCurp.fisicaAnterior.curp}</td>
									<td>${keyTramiteCambioCurp.fisicaNueva.curp}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-sm-12" style="text-align: left;">
					<div>
						<button class="btn btn-danger" type="button" id="cerrarDetalle">
							Salir del tr&aacute;mite
						</button>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<jsp:include page="../../common/pieUmf.jsp">
						<jsp:param name="tipoTramite" value="true" />
					</jsp:include>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div id="dialog-error"></div>
<div id="dialog-info"></div>

<form id="solicitudDocumentoForm" name="solicitudDocumentoForm"
	method="get" target="_blank" class="formNotBlock"
	action="${contextpath}/solicitud/mostrarDocumentoResultante">
	<input type="hidden" name="idTramite" id="idTramite" /> <input
		type="hidden" name="tipoDocumento" id="tipoDocumento" />
</form>

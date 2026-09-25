<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum"%>

<style>
.empty-state .titulo {
    font-size: 15px !important;
    margin-bottom: 20px;
}

#tblDescuentosRissResumen td {
	text-align: center;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/riss/contenido.js" htmlEscape="true" />"></script>

<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}">

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
		<c:choose>
			<c:when test="${empty error }">
				<script type="text/javascript">
					var codigoTipoSolicitud = ${codigoTipoSolicitud};
					var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
					var arrayCodigoTipoTramite = ${codigoTipoTramite};

					var datosEntradaFirma = {
						fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
						nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
						registroPatronal : '${datosFirmaElectronica.registroPatronal}',
						rfc : '${datosFirmaElectronica.rfc}',
						curp : '${datosFirmaElectronica.curp}'
					};
				</script>

				<form:form modelAttribute="beneficio" id="soForm" method="post">
					<form:hidden path="fisica.rfc" id="rfc" />
					<form:hidden path="fisica.idPersona" id="idPersona" />

					<input type="hidden" id="idSolicitud"
						value="${solicitud.solicitudId}" />
					<input type="hidden" id="folioSolicitud"
						value="${solicitud.noFolioSolicitud}" />

					<div class="alert alert-success">
						Tu solicitud ha iniciado correctamente y tu n&uacute;mero de folio es:<strong>${solicitud.noFolioSolicitud}</strong>
					</div>
					<c:choose>
						<c:when test="${not empty msgRPSPendientes}">
							<br/><div class="alert alert-success"><strong>${msgRPSPendientes}</strong></div>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>

					<div class="container-fluid empty-state">
						<div class="row">
							<!-- Imagen -->
							<div class="col-xs-12 imagen">
								<i class="glyphicon glyphicon-ok-sign"></i>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 titulo">
								<strong>Eres candidato a recibir los siguiente descuentos del beneficio:</strong>
							</div>
						</div>

						<div class="row m-b-sm">
							<div class="col-xs-12">
								<div class="row">
									<div class="col-xs-1"></div>
									<div class="col-xs-4">
										<strong>Tipo de beneficio: </strong>
									</div>
									<div class="col-xs-7">${beneficio.tipoBeneficio.descripcion}</div>
								</div>
							</div>
						</div>
						<div class="row m-b-sm">
							<div class="col-xs-12">
								<div class="row">
									<div class="col-xs-1"></div>
									<div class="col-xs-4">
										<strong>RFC:</strong>
									</div>
									<div class="col-xs-7">${beneficio.fisica.rfc}</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="row">
									<div class="col-xs-1"></div>
									<div class="col-xs-4">
										<strong>Podr&aacute;s recibir los descuentos como:</strong>
									</div>
									<div class="col-xs-7">
										<ul style="list-style: disc inside none;" class="p-l-none">
											<c:if test="${not empty beneficio.fisica.idPersona}">
												<li>Trabajador independiente</li>
											</c:if>
											<c:if test="${not empty beneficio.listaSujetosObligados}">
												<li>Patr&oacute;n</li>
											</c:if>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--  <div id="tblDescuentosRissWrapper"
							style="width: 90%; margin: 25px auto 0px;">
							<table id="tblDescuentosRissResumen" style="width: 100%;"
								class="table table-striped table-bordered" cellpadding="0"
								cellspacing="0" border="0">
								<thead>
									<tr>
										<th>A&ntilde;o fiscal</th>
										<th>Descuento</th>
										<th>Fecha Inicio - Fecha Fin</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${beneficio.listaDescuentosBeneficio}"
										var="descuentoB" varStatus="indice">
										<tr>
											<td align="center">${descuentoB.anioFiscal}</td>
											<td align="center">${descuentoB.porcentajeDescuento}%</td>
											<td align="center"><fmt:formatDate
													value="${descuentoB.fecInicio}" pattern="dd/MM/yyyy" /> -
												<fmt:formatDate value="${descuentoB.fechaFin}"
													pattern="dd/MM/yyyy" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="col-xs-2"></div>
						</div> -->
					</div>
				</form:form>
			</c:when>
			<c:otherwise>
				<div class="container-fluid empty-state">
					<div class="row">
						<!-- Imagen -->
						<div class="col-xs-12 imagen">
							<i class="glyphicon glyphicon-remove-sign"></i>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 titulo">Lo sentimos, no aplicas al beneficio por el siguiente motivo:</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="alert alert-danger">
								${error}
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		</div>
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span>Campos obligatorios</div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard" onclick="uid_call('imss.beneficio_riss.inscripcion.captura_info.link_cerrar','clickout')">Cerrar</button>
			<c:if test="${empty error}">
				
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite" onclick="uid_call('imss.beneficio_riss.inscripcion.captura_info.link_finalizar','clickin')"><i class="glyphicon glyphicon-ok"></i>
								Finalizar tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash" onclick="uid_call('imss.beneficio_riss.inscripcion.captura_info.link_cancelar','clickout')"></i>
								Cancelar tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			</div>
			
		</div>
	</div>

</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitud.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
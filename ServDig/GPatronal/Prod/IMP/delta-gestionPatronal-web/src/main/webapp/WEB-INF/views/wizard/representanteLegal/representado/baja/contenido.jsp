<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/representanteLegal/representado/baja/contenido.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	
<script>
	var tipoSolicitud = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor()%>;

	if(parent.WizardBajaRepresentadoLegalCtrl.config.idOrigen == 2){
		var codigoTipoSolicitud = ${codigoTipoSolicitud};
		var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
		var arrayCodigoTipoTramite = ${codigoTipoTramite};
		
		var datosEntradaFirma = {
			fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
			nombreCompleto : "${datosFirmaElectronica.nombreCompleto}",
			registroPatronal : '${datosFirmaElectronica.registroPatronal}',
			rfc : '${datosFirmaElectronica.rfc}',
			curp : '${datosFirmaElectronica.curp}'
		};
	}
		
</script>


<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:choose>
				<c:when test="${empty error}">

					<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
					<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
					<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />

					<div class="alert alert-success">
						<c:choose>
							<c:when test="${!isRetomar}">
							Su solicitud ha iniciado correctamente y su n&uacute;mero de folio es <strong>${solicitud.noFolioSolicitud}</strong>
							</c:when>
							<c:otherwise>
							El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
							</c:otherwise>
						</c:choose>
					</div>

					<ul>
						<li>
							<p>A continuaci&oacute;n selecciona a las empresas representadas a dar de baja:</p>
						</li>
					</ul>
					<div id="representadosWrapper" style="width: 100%; margin: 0 auto;">
						<table id="tblPersonasAutorizadasResumen" style="width: 100%;" class="table table-striped" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th colspan="3" align="center">Datos de las empresas representadas</th>
								</tr>
								<tr>
									<th>Selecci&oacute;n</th>
									<th>Nombre / Raz&oacute;n Social</th>
									<th>Tipo persona</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${representantesLegales}" var="representanteLegal" varStatus="indice">
									<tr>
										<td>
											<c:choose>
												<c:when test="${representanteLegal.checked}">
													<input type="checkbox" id="representanteL" name="representanteL"
														value="${representanteLegal.cveIdRepresentanteLegal}" checked="checked">
										</td>
										</c:when>
										<c:otherwise>
											<input type="checkbox" id="representanteL" name="representanteL"
												value="${representanteLegal.cveIdRepresentanteLegal}">
											</td>
										</c:otherwise>
										</c:choose>
										</td>
										<c:choose>
											<c:when test="${not empty representanteLegal.personaMoralRepresentada}">
												<td>${representanteLegal.personaMoralRepresentada.razonSocial}</td>
												<td>Moral</td>
											</c:when>
											<c:otherwise>
												<td>${representanteLegal.personaFisicaRepresentada.nombre }
													${representanteLegal.personaFisicaRepresentada.primerApellido }
													${representanteLegal.personaFisicaRepresentada.segundoApellido }</td>
												<td>Fisica</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						${error}
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br>

	<div class="opciones col-sm-6">
		<c:if test="${empty error}">
			<div class="btn-group dropup">
				<a href="#" class="btn btn-primary">
					<spring:message code="label.menus.opciones" />
				</a>
				<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a id="finalizarTramite">
							<i class="glyphicon glyphicon-ok"></i>
							Finalizar Tr&aacute;mite
						</a>
					</li>
					<li>
						<a id="guardarTramite">
							<i class="glyphicon glyphicon-download-alt"></i>
							Guardar Tr&aacute;mite
						</a>
					</li>
					<li>
						<a id="cancelarTramite">
							<i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite
						</a>
					</li>
				</ul>
			</div>
		</c:if>

	</div>
	<div class="controles col-sm-6">
		<div class="pull-right">
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>

	</div>


</div>

<input type="hidden" id="folioSolicitud" value="${folioSolicitud}">

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

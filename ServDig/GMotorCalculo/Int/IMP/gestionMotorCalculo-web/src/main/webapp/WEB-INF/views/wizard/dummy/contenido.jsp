<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/personaAutorizada/baja/contenido.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

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

<div class="contenedor">
	<input type="hidden" id="hdnRfcPersonaSesion" value="${rfcPersonaSesion}" />
	<div class="contenido" style="width: 100%;">
		<c:choose>
		<c:when test="${empty error }">
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
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
					
				<div id="representadosWrapper" style="width: 100%; margin: 0 auto;">
					<table id="tblPersonasAutorizadasResumen" style="width: 100%;"
						class="table table-striped table-bordered" cellpadding="0"
						cellspacing="0" border="0">
						<thead>
							<tr>
								<th>Selecci&oacute;n</th>
								<th>RFC</th>
								<th>Nombre</th>
								<th>Registro Patronal</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${personasAutorizadas}" var="personaAutorizada" varStatus="indice">
								<tr>
									<td>
										<c:choose>
											<c:when test="${personaAutorizada.checked}">
												<input type="checkbox" id="personaA" name = "personaA" value="${personaAutorizada.cvePersonaAutorizada}" checked="checked"></td>
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="personaA" name = "personaA" value="${personaAutorizada.cvePersonaAutorizada}"></td>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										${personaAutorizada.fisica.rfc}
									</td>
									<td>
										${personaAutorizada.fisica.nombre } 
										${personaAutorizada.fisica.primerApellido } 
										${personaAutorizada.fisica.segundoApellido }
									</td>
									<td>
										${personaAutorizada.sujetoObligado.numeroRegistroPatronal}
										${personaAutorizada.sujetoObligado.modalidad.numModalidad} 
										${personaAutorizada.sujetoObligado.digVerificador}
										
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">�</button>
					${error}
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	<br>
	<div class="pie">
		<div class="opciones">
			<c:if test="${empty error}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /> </a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="icon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite"><i class="icon-download-alt"></i>
								Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="icon-trash"></i>
								Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-inverse" id="cerrarWizard">CERRAR</button>
		</div>
		<div class="controles"></div>

	</div>
</div>

<input type = "hidden" id="folioSolicitud" value="${folioSolicitud}">

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> �Desea cancelar la
		solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>

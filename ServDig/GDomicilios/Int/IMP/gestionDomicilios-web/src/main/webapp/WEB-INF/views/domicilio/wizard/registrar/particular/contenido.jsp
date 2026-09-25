<%@ include file="../../../../general/taglibs.jsp"%>

<c:set var="FROM_WIZARD" value="true" scope="request" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/wizard/registrar/particular/contenido.js" htmlEscape="true" />"></script>

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

<div class="contenedor col-sm-12">
	<div class="contenido row" style="width: 100%;">
		<div class="col-sm-12">
		<c:if test="${not empty mdmDatosEntrada.errorFormGeneral}">
			<form:hidden path="errorFormGeneral" />
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert" 
					onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_close','clickout');">×</button>
				<strong>Error: </strong>${mdmDatosEntrada.errorFormGeneral}
			</div>
		</c:if>

		<c:if test="${empty mdmDatosEntrada.errorFormGeneral}">
			<form:form modelAttribute="mdmDatosEntrada" id="mdmPersonaFisicaForm">

				<form:hidden path="indCapturaDatosRENAPO" id="indCapturaDatosRENAPO" />
				<form:hidden path="indCapturaDatosSAT" id="indCapturaDatosSAT" />
				<form:hidden path="indCapturaDatosComplementarios"
					id="indCapturaDatosComplementarios" />

				<form:hidden path="indCapturaNombre" id="indCapturaNombre" />
				<form:hidden path="indCapturaCURP" id="indCapturaCURP" />
				<form:hidden path="indCapturaSexo" id="indCapturaSexo" />
				<form:hidden path="indCapturaFechaNacimiento"
					id="indCapturaFechaNacimiento" />
				<form:hidden path="indCapturaLugarNacimiento"
					id="indCapturaLugarNacimiento" />
				<form:hidden path="indCapturaDocumentoProbatorio"
					id="indCapturaDocumentoProbatorio" />

				<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
				<form:hidden path="indCapturaDomicilioFiscal"
					id="indCapturaDomicilioFiscal" />
				<form:hidden path="indCapturaMediosContactoFiscales"
					id="indCapturaMediosContactoFiscales" />

				<form:hidden path="indCapturaDomicilioParticular"
					id="indCapturaDomicilioParticular" />
				<form:hidden path="indAsignacionDomicilio"
					id="indAsignacionDomicilio" />
				<form:hidden path="indActualizacionDomicilioDerechohabiente"
					id="indActualizacionDomicilioDerechohabiente" />
				<form:hidden path="indCambioClinica"
					id="indCambioClinica" />
				<form:hidden path="indCapturaMediosContactoParticular"
					id="indCapturaMediosContactoParticular" />

				<form:hidden path="indAutorizacion" id="indAutorizacion" />

				<form:hidden path="personaFisica.idPersona" id="idPersona" />
				<form:hidden path="personaFisica.cveFisica" id="cveFisica" />
			</form:form>

			<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
			<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
			<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<input type="hidden" id="isRetomar" value="${isRetomar}" />
			<input type="hidden" id="esAsignaacionDomicilio" value="${mdmDatosEntrada.indAsignacionDomicilio}" />
			<input type="hidden" id="esActualizacionDomicilioDerechohabiente" value="${mdmDatosEntrada.indActualizacionDomicilioDerechohabiente}" />
			<input type="hidden" id="indCambioClinica" value="${mdmDatosEntrada.indCambioClinica}" />
			
			<input type="hidden" id="cveIdPersonafDom" value="${domicilio.cveIdPersonafDom}" />
			
			<div class="alert alert-info">
				<c:choose>
					<c:when test="${!isRetomar}">
							Su solicitud ha sido iniciado correctamente y se le ha asignado a dicha solicitud el folio: <strong>${folioSolicitud}</strong>
					</c:when>
					<c:otherwise>
							El folio de la solicitud que esta retomando es: <strong>${folioSolicitud}</strong>
					</c:otherwise>
				</c:choose>
			</div>

			<span id="errorNegocioLabel" class="error hiddenElement"></span>

			<div id="domParticularDiv">
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
				<div id="admonDomParticularDiv">
					<div class="row">

						<div class="col-xs-12">
							<h2>Registrar Domicilio Geogr&aacute;fico  JC</h2>
						</div>
					</div>
					
					<jsp:include page="../../../common/datosComplementariosCommon.jsp"></jsp:include>
				</div>
			</div>
			<br />
		</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_acciones','clickin');">Acciones</a> 
						<a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_finalizarTramite','clickin');">
							<i class="glyphicon glyphicon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_guardarTramite','clickin');">
							<i class="glyphicon glyphicon-download-alt"></i>
								Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cancelarTramite','clickin');">
							<i class="glyphicon glyphicon-trash"></i>
								Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			
		</div>
		<div class="controles col-sm-6"> 
			<div class="pull-right">
			<c:if test="${!isRetomar}">
				<button class="btn btn-secondary" id="regresar" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_regresar','clickin');">
					Regresar</button>
			</c:if>
				<button class="btn btn-secondary" id="cerrarWizard" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cerrar','clickout');">
					Cerrar</button>
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
		solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
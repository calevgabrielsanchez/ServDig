<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/modificacion/medios/particulares/contenido.js" htmlEscape="true" />"></script>

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
	<div class="contenido row">
		<div class="col-sm-12">
			<form:form modelAttribute="mdmDatosEntrada" id="mdmPersonaFisicaForm">
				<form:hidden path="indCapturaDatosRENAPO" id="indCapturaDatosRENAPO" />
				<form:hidden path="indCapturaDatosSAT" id="indCapturaDatosSAT" />
				<form:hidden path="indCapturaDatosComplementarios" id="indCapturaDatosComplementarios" />
	
				<form:hidden path="indCapturaNombre" id="indCapturaNombre" />
				<form:hidden path="indCapturaCURP" id="indCapturaCURP" />
				<form:hidden path="indCapturaSexo" id="indCapturaSexo" />
				<form:hidden path="indCapturaFechaNacimiento" id="indCapturaFechaNacimiento" />
				<form:hidden path="indCapturaLugarNacimiento" id="indCapturaLugarNacimiento" />
				<form:hidden path="indCapturaDocumentoProbatorio" id="indCapturaDocumentoProbatorio" />
	
				<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
				<form:hidden path="indCapturaDomicilioFiscal" id="indCapturaDomicilioFiscal" />
				<form:hidden path="indCapturaMediosContactoFiscales" id="indCapturaMediosContactoFiscales" />
	
				<form:hidden path="indCapturaDomicilioParticular" id="indCapturaDomicilioParticular" />
				<form:hidden path="indCapturaMediosContactoParticular" id="indCapturaMediosContactoParticular" />
	
				<form:hidden path="indAutorizacion" id="indAutorizacion" />
	
				<c:if test="${not empty mdmDatosEntrada.errorFormGeneral}">
					<form:hidden path="errorFormGeneral" />
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong>Error: </strong>
						${mdmDatosEntrada.errorFormGeneral}
					</div>
				</c:if>
	
				<c:if test="${empty mdmDatosEntrada.errorFormGeneral}">
					<form:hidden path="personaFisica.idPersona" id="idPersona" />
					<form:hidden path="personaFisica.cveFisica" id="cveFisica" />
	
					<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
					<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
					<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
					<input type="hidden" id="isRetomar" value="${isRetomar}" />
	
					<div class="alert alert-success">
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
	
					<c:if test="${mdmDatosEntrada.indCapturaDatosComplementarios}">
						<div id="datosComplementariosDiv">
							<c:if test="${mdmDatosEntrada.indCapturaMediosContactoParticular}">
								<div id="mediosContactoDiv">
									<span id="errorNegocioLabel" class="error hiddenElement"></span>
									<div id="admonMediosContactoDiv"></div>
								</div>
							</c:if>
						</div>
					</c:if>
	
					<br />
	
					<!-- Para medios de contacto fiscales -->
					<div id="mediosContactoRegistrar"></div>
	
				</c:if>
			</form:form>
		</div>
	</div>

	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">Acciones</a>
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
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>
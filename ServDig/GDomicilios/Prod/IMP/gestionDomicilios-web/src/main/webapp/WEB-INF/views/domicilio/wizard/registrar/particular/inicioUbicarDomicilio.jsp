<%@ include file="../../../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />
<c:set var="FROM_WIZARD" value="true" scope="request" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/wizard/registrar/particular/contenido.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<div class="contenido row" style="width: 100%;">
		<div class="col-sm-12">
		<c:if test="${not empty mdmDatosEntrada.errorFormGeneral}">
			<form:hidden path="errorFormGeneral" />
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">×</button>
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
				<form:hidden path="indCapturaMediosContactoParticular"
					id="indCapturaMediosContactoParticular" />
				<form:hidden path="indAsignacionDomicilio"
					id="indAsignacionDomicilio" />
				<form:hidden path="indCambioClinica"
					id="indCambioClinica" />
				<form:hidden path="indActualizacionDomicilioDerechohabiente"
					id="indActualizacionDomicilioDerechohabiente" />

				<form:hidden path="indAutorizacion" id="indAutorizacion" />

				<form:hidden path="personaFisica.idPersona" id="idPersona" />
				<form:hidden path="personaFisica.cveFisica" id="cveFisica" />
			</form:form>

			<input type="hidden" id="idSolicitud" value="${SOLICITUD_SESSION.solicitudId}" />
			<input type="hidden" id="folioSolicitud" value="${SOLICITUD_SESSION.noFolioSolicitud}" />
			<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<input type="hidden" id="isRetomar" value="${isRetomar}" />

			<div class="alert alert-success">
				<c:choose>
					<c:when test="${!isRetomar}">
							Su solicitud ha sido iniciado correctamente y se le ha asignado a dicha solicitud el folio: <strong>${SOLICITUD_SESSION.noFolioSolicitud}</strong>
					</c:when>
					<c:otherwise>
							El folio de la solicitud que esta retomando es: <strong>${SOLICITUD_SESSION.noFolioSolicitud}</strong>
					</c:otherwise>
				</c:choose>
			</div>
			
			<!-- En esta seccion se debe evaluar si cambio de clinica, si lo es se debe mostrar el JSP que busca por UMF
			de acuerdo a la circunscripcion -->
			
			<div>
				<c:choose>
					<c:when test="${not empty tipoTramite && tipoTramite==36}">
						<jsp:include page="inicioUbicarDomicilioCambioClinica.jsp" ></jsp:include>	
					</c:when>
					<c:otherwise>
						<jsp:include page="../../../common/inicioCommon.jsp" ></jsp:include>
					</c:otherwise>
				</c:choose>

			</div>
		</c:if>
		</div>
	</div>
	<br>
	<div class="pie row" style="margin-top: 25px;">
		<div class="opciones col-sm-6">
			<div class="btn-group">
				<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_acciones','clickin');"
					>Acciones</a> <a href="#"
					data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="cancelarTramite" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cancelarTramite','clickin');">
						<i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite</a></li>
				</ul>
			</div>
			
		</div>
		<div class="controles col-sm-6"> 
			<div class="pull-right">
				<button class="btn btn-secondary" id="cerrarWizard" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cerrar','clickout');">
					CERRAR</button>
			</div>
		</div>

	</div>
</div>

<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${SOLICITUD_SESSION.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
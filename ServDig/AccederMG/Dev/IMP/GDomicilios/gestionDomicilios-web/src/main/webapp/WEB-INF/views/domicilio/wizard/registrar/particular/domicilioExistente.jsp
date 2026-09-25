<%@ include file="../../../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />
<c:set var="FROM_WIZARD" value="true" scope="request" />

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
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Error: </strong>${mdmDatosEntrada.errorFormGeneral}
			</div>
		</c:if>

		<c:if test="${empty mdmDatosEntrada.errorFormGeneral}">
			<!-- Forma para invocar el retomar o cancelar una solicitud -->
			<form:form action="" modelAttribute="solicitud" id="solicitudForm"
				method="post">
				<form:hidden path="solicitudId" id="idSolicitudPendiente" />
				<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
				<form:hidden path="observacion" id="observacion" />
			</form:form>
			<!--  -->
			
			<input type="hidden" id="idSolicitud" value="${SOLICITUD_SESSION.solicitudId}" />
			<input type="hidden" id="cveIdPersonafDom" value="${domicilio.cveIdPersonafDom}" />
			<input type="hidden" id="folioSolicitud" value="${SOLICITUD_SESSION.noFolioSolicitud}" />
			<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<input type="hidden" id="isRetomar" value="${isRetomar}" />
			<input type="hidden" id="esAsignaacionDomicilio" value="${mdmDatosEntrada.indAsignacionDomicilio}" />
			<input type="hidden" id="indCambioClinica" value="${mdmDatosEntrada.indCambioClinica}" />
			<input type="hidden" id="idTipoTramite" value="${tipoTramite}" />

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

			<div>Se ha detectado que ya cuenta con un domicilio con la siguiente informacion:</div>
			<form action="">
				Vialidad: ${domicilio.calle} <br/>
				Numero interior, Numero exterior: ${domicilio.numExterior1}<br/>
				Asentamiento: ${domicilio.asentamiento.nombre}<br/>
				Estado: ${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br/>
				Municipio: ${domicilio.asentamiento.localidad.nombre}<br/>
				CodigoPostal: ${domicilio.codigoPostal.codigoPostal}<br/>
			</form>
		</c:if>
		</div>
	</div>
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<div class="btn-group">
				<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_acciones','clickin');">
					Acciones</a> <a href="#"
					data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="asignarDomicilio" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_asignarMismoDom','clickin');">
						<i class="glyphicon glyphicon-trash"></i>
							Asignar Mismo Domicilio</a></li>
					<li><a id="nuevoDomicilio" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_nuevoDom','clickin');">
						<i class="glyphicon glyphicon-trash"></i>
							Nuevo Domicilio</a></li>
					<li><a id="cancelarTramite" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cancelarTramite','clickin');">
						<i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite</a></li>
				</ul>
			</div>
			
		</div>
		<div class="controles col-sm-6"> 
			<div class="pull-right">
				<button class="btn btn-inverse" id="cerrarWizard" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cerrar','clickout');">
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
<%@ include file="../../../../../layout/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/modificacion/medios/particulares/inicial.js" htmlEscape="true" />"></script>
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<c:if test="${origenApp eq origenVENTANILLA}">
<script type="text/javascript">
$(document).ready(function() {
	$("#usuarioSesion").val(parent.usuarioSesionPortal);
});
</script>
</c:if>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span>Medios de Contacto</span>
			</div>

			<div class="descripcion">
				<p>En esta opci&oacute;n podr&aacute;s registrar o modificar los datos de tus medios de contacto personales
					(Correo electr&oacute;nico, tel&eacute;fono fijo o m&oacute;vil).</p>
			</div>

			<div class="opciones">
				<c:if test="${!existeSolProceso}">
					<c:choose>
						<c:when test="${!existeSolRegistrada}">
							<button class="btn btn-primary btn-block" id="btnInciaTramite">
								Iniciar Solicitud
							</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-primary btn-block" id="btnRetomarTramite">
								Retomar Solicitud
							</button>
							<button class="btn btn-warning btn-block" id="btnCancelarTramite">
								Cancelar Solicitud en Proceso
							</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				<button class="btn btn-default btn-block" id="btnInicioCancelarTramite">
					Cancelar
				</button>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3>Instrucciones :</h3>

			<c:choose>
				<c:when test="${existeSolRegistrada}">
					<div class="alert alert-info">
						Usted ya cuenta con una solicitud para
						<strong>MODIFICACI&Oacute;N DE MEDIOS DE CONTACTO PARTICULARES</strong>
						registrada.
					</div>

					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.</p>
						</li>
					</ul>
					
					<ol>
						<li>Selecciona la opci&oacute;n <strong>RETOMAR SOLICITUD</strong> y captura los datos solicitados.</li>
						<li>Los campos marcados con un asterisco (*) son datos obligatorios.</li>
					</ol>
				</c:when>
				<c:when test="${existeSolProceso}">
					<div class="alert">
						Usted ya cuenta con una solicitud para
						<strong>MODIFICACI&Oacute;N DE MEDIOS DE CONTACTO PARTICULARES</strong>
						en proceso.
					</div>
				</c:when>
				<c:otherwise>
					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.</p>
						</li>
					</ul>
					<ol>
						<li>Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.</li>
						<li>Los campos marcados con un asterisco (*) son datos obligatorios.</li>
					</ol>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form modelAttribute="mdmDatosEntrada" id="mdmForm" method="post"
	action="${contextoOrigen}/wizard/tramite/modificar/medios/particulares/crear/solicitud">
	<form:hidden path="personaFisica.idPersona" id="busquedaIdPersona" />
	<form:hidden path="indCapturaMediosContactoParticular" id="indCapturaMediosContactoParticular" />
	<c:if test="${origenApp eq origenVENTANILLA}"><form:hidden path="usuarioSesion" id="usuarioSesion" /></c:if>
</form:form>
<!--  -->

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm" method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>
<!--  -->

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${solicitudForm.noFolioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>
<!--  -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/riss/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span>Solicitud de beneficio R&eacute;gimen de Incorporaci&oacute;n Fiscal (RIF)</span>
			</div>
			<div class="descripcion">
				<p>En esta opci&oacute;n podr&aacute;s solicitar y hacerte
					acreedor del beneficio RIF.</p>
			</div>
			<div class="opciones">
				<div style="max-width: 400px;">
					<c:choose>
						<c:when test="${solicitudEnProceso eq false }">
							<button class="btn btn-primary btn-block"
								role="button" aria-disabled="false" id="btnInciarTramite" onclick="uid_call('imss.beneficio_riss.inscripcion.inicio.btn_iniciarTramite','clickin')">
								<span class="ui-button-text">Iniciar solicitud</span>
							</button>
						</c:when><c:otherwise></c:otherwise>
					</c:choose>
					<button
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnCancelarInicioTramite" onclick="uid_call('imss.beneficio_riss.inscripcion.inicio.btn_cancelarTramite','clickout')">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3>Instrucciones:</h3>
			<c:choose>
				<c:when test="${solicitudEnProceso eq false }">
					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu RFC y
								archivos de tu FIEL.</p>
						</li>
						<li>
							<p>
								1. Selecciona la opci&oacute;n iniciar solicitud.<br> 2. Los campos marcados con un
								asterisco (*) son datos obligatorios.
							</p>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">${msgError}</div>
					<c:choose>
						<c:when test="${not empty solicitudRegistrada && solicitudRegistrada eq true }">
							<button class="btn-block ui-button ui-widget ui-state-default ui-button-text-only"
								role="button" aria-disabled="false" id="btnCancelarTramite">
								<span class="ui-button-text">Cancelar solicitud</span>								
							</button>
							<input type="hidden" id="idSolicitudRegistrada" value="${idSolicitudRegistrada}" />									
						</c:when>
						<c:otherwise>
						</c:otherwise>	
					</c:choose>					
				</c:otherwise>
			</c:choose>
			
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<form:form modelAttribute="beneficio" id="soForm" method="post" action="">
	<form:hidden path="fisica.rfc" id="rfc" />
	<form:hidden path="fisica.idPersona" id="idPersona" />
</form:form>


<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> &iquest;Deseas cancelar la
		solicitud registrada con folio: <strong>${folioSolicitudRegistrada}</strong>?
	</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
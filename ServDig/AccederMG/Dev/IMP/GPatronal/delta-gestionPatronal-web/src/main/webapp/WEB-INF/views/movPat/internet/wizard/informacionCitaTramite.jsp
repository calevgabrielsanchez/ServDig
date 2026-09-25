<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>	
<%@ page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.DELTA_SESSION_VARIABLES"%>

<c:set var="idOrigenSolicitud" value="<%=session.getAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD)%>" />
<c:set var="idOrigenInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<script>
	var idOrigenSolicitud = ${idOrigenSolicitud},
	idOrigenVENTANILLA = <%=OrigenSolicitudEnum.VENTANILLA.getId()%>,
	idOrigenINTERNET = <%=OrigenSolicitudEnum.INTERNET.getId()%>;
</script>

<style>
	#selectable .ui-selecting {
		background: #1A79A7;
		color: white;
	}
	
	#selectable .ui-selected {
		background: #428BCA;
		color: white;
	}
	
	#selectable {
		list-style-type: none;
		margin: 0;
		padding: 0;
		width: 100%;
	}
	
	#selectable li {
		margin: 3px;
		padding: 0.4em;
		color: #67666A;
	}

	a:active {
		outline: none;
	}
	
	a:focus {
		-moz-outline-style: none;
	}
	
	.icono-help {
	    font-size: 20px;
	}
	
	.ui-datepicker-trigger {
	    width: 30px !important;
    	padding-left: 5px !important;
	}
</style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/internet/wizard/modificacion/patron/clasificacion/informacionCitaTramite.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
<input type="hidden" id="hdnIdTipoTramite" value="${idTipoTramite}" />


<div class="contenedor col-sm-12">

	<div class="contenido row">
		<div class="col-sm-12" style="display: grid; gap: 10px;">
			<div class="titulo separadorseccion">
				<span> <spring:message code="movpat.internet.cita.titulo" /></span>
			</div>
						
			<div>
				<div class="cell">
					<div class="col-sm-12"><h2 style="font-size: 14px; ">Has realizado el pre registro del  tr&aacute;mite ${desTipoTramiteVigente}:</h2></div>
					<div class="col-sm-12" style="text-align: justify;">
						<span id="fechaGeneracionCita"> </span> <span>con folio: </span><b id="folioCita">${refFolioCita}</b>
					</div>
					<div class="col-sm-12">
						<b id="nombreCita">${nombreCita}</b>
					</div>										
					<div class="col-sm-12"><p>  </p></div>
					<div class="col-sm-12" style="text-align: justify;"><span>Debes presentar la documentaci&oacute;n probatoria se&ntilde;alada en el comprobante de cita en original y copia con el fin de corroborar la informaci&oacute;n en la subdelegaci&oacute;n </span> <span id="subDelCita">${subdelegacion}</span> <span>,</span> <span>el d&iacute;a</span> <b id="diaCita">${fechaHora}</b>.</div>
					<div class="col-sm-12"><p>  </p></div>
					<div class="col-sm-12" style="text-align: justify;">
					<span>Recuerda imprimir tu comprobante de cita para presentarlo en la subdelegaci&oacute;n.</span></div>
					<div class="col-sm-12"><p>  </p></div>
					<div class="col-sm-12" style="text-align: justify;">
						<span>Puedes asistir a las ventanillas del IMSS en su subdelegaci&oacute;n antes del d&iacute;a de la cita; pero tendr&aacute;s; que esperar turno para 
							ser atendido.</span></div>
					<div class="col-sm-12"><p>  </p></div>
				</div>
			</div>
			
			
			<div id="divAcciones" style="display: inline-flex; gap: 10px; align-items: end;">

				<c:if test="${muestraBtnCambioCita}">			
					<button
						class="btn btn-default btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
						role="button" aria-disabled="false" id="btnModificarCita">
						<span class="ui-button-text"><spring:message code="movpat.internet.cita.button.modificarCita" /></span>
					</button> 				
				</c:if>

				<button
					class="btn btn-default btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
					role="button" aria-disabled="false" id="btnCancelarTramite">
					<span class="ui-button-text"><spring:message code="movpat.internet.cita.button.cancelarTramite" /></span>
				</button>				
					
				<button
					class="btn btn-danger btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
					role="button" aria-disabled="false" id="btnCerrar">
					<span class="ui-button-text"><spring:message code="movpat.internet.cita.button.internet.cerrar" /></span>
				</button>
				
			</div>
			
			<div id="divModificaCita" style="display: flex; align-items: center; justify-content: flex-start; gap: 30px;">
				<div style="display: flex; gap: 15px;">
					<label class="control-label">
						Fecha de reasignaci&oacute;n de la cita</span>:
					</label>
					<div>
						<input type="text" id="fechaEfecto" class="form-control input-sm ns_" readonly
							value="<fmt:formatDate pattern="dd/MM/yyyy" value="${fechaHoraDate}"/>"
							onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)"
							onclick="showCalendar()" style="width: 100px;"/>
						<label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">fecha inv&aacute;lida </label>
						<span style="display:none;" class="error" id="fechaEfectoError"></span>
					</div>	
				</div>
				<div style="display: flex; gap: 15px;">
					<button
						class="btn btn-primary"
						role="button" aria-disabled="false" id="btnCambiarFechaCita">
						<span class="ui-button-text">Cambiar Cita</span>
					</button>
					<button
						class="btn btn-danger"
						role="button" aria-disabled="false" id="btnCancelarCambiarFecha">
						<span class="ui-button-text"><spring:message code="movpat.internet.cita.button.internet.cerrar" /></span>
					</button>
				</div>  	
			</div>
			
			<!-- div id="divImprimir" style="display: inline-flex; gap: 10px; align-items: end;">								
				<button
					class="btn btn-default btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
					role="button" aria-disabled="false" id="btnImprimirComprobanteCita">
					<span class="ui-button-text">Imprimir Cita</span>
				</button>
				
			</div-->
			
		</div>
	</div>


	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form modelAttribute="sujetoTramite" id="modificacionClasificacionForm" method="post">
	<form:hidden path="numeroRegistroPatronal" id="hdnClasifNumeroRegistroPatronal"/>
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			Est&aacute;s a punto de cancelar el tr&aacute;mite ${desTipoTramiteVigente} del RP ${numeroRegistroPatronal}. &iquest;Est&aacute;s seguro de querer cancelarlo? Se perder&aacute; toda la informaci&oacute;n y cancelar&aacute; el folio de cita y tr&aacute;mite
	</p>
</div>

<!-- Divs para dialogos confirmar cambio de cita -->
<div id="dialog-confirm-cita" title="Confirmar cambio de cita">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			&iquest;Est&aacute;s seguro de cambiar tu cita?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>
<div id="pdfcontainer"></div>
<div id="dialogoAcuseCancelacion" style="width='100%' height='100%'"><div id="reporteFrame" style="width='100%' height='100%'"></div></div>

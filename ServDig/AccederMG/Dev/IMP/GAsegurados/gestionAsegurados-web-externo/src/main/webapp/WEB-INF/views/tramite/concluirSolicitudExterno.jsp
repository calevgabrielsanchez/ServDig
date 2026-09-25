<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 4: Solicitud Concluida</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Se ha concluido la captura de la solicitud, imprima el comprobante de la solicitud.</p>
			</div>
			<input type="hidden" id="folio" name="folio" value="${folio}" />
			<input type="hidden" id="id" name="id" value="${idSolicitud}" />
		</div>
		
		<!-- Forma de la consulta de personas por datos basicos. -->
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<div id="acordeon" class="contenedor" style="height: 800px !important;">
				<div class="row" style="width: 900px;">
					<h2 style="font-size: .9em !important">${mensaje}</h2>
					<br /><br /><br />
				</div>
				
 				<div id="comprobante" class="hiddenElement">
					<h3 style="font-size: 1.1em !important"> Imprimir comprobante del tr&aacute;mite</h3>
					<div class="textwidget">
						<p style="font-size: .9em;">Imprima el comprobante de la solicitud.</p>
					</div>
					<form id="formComprobante">
						<input type="submit" value="Imprimir" class="mboton" />
					</form>
				</div>
				
			</div>
		</div>
	</div>
</div>

<div id="timer">

</div>
<div id="reporteFrame"></div>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/tramite-concluirSolicitudExterno.js" htmlEscape="true" />"></script>

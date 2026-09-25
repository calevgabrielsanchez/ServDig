<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 4: Solicitud Cancelada</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">El tramite de la solicitud de NSS ha sido cancelado</p>
			</div>
		</div>
		
		<div class="form-comment">
		
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		
			<div id="acordeon" class="contenedor" style="height: 800px !important;">
				<div class="row" style="width: 900px;">
					<h2 style="font-size: .9em !important">${mensaje}</h2>
					<br /><br /><br />
				</div>
						  				
			</div>
		</div>
	</div>
</div>

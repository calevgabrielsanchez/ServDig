<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>

<script>
// Objeto del dialogo de reporte
var objReporte;

$(document).ready(function() {
	$('#imprimirComprobante').click(function() {


 		var urlFrame = context_path + "/solicitud/reporte-comprobante/<c:out value='${folio}' />";

		$('#reporteFrame').html(
			'<iframe id="site" src="' + urlFrame
		 			+ '" width="100%" height="100%" />');
		
		objReporte.dialog('open');

	});
	$('#solicitudNuevaBtn').click(function() {
		$('#confirmacionRegistroPersonaFisicaForm').submit();
	});
	
	
	
	/*
	 *  Dialogo para mostrar el reporte de comprobante
	 */
	
	  
	/*
	 * Inicializamos el dialogo que contiene la pantalla 
	 * de la consulta de personas morales.
	 */
	var horizontalPadding = 15;
    var verticalPadding = 15;
   
	
	var d = $('#reporteFrame');
	    /*
	     * Configuracion del dialogo
	     */
	    objReporte = d.dialog({
	    	
	        title: 'Comprobante',
	        autoOpen: false,
	        width: 500,
	        height: 500,
	        modal: true,
	        resizable: false,
	        autoResize: true,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        }
	    }).width(500).height(500);
	    
	    
	   
	
});
</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<br />
			<c:choose>
				<c:when test="${folio == null}">
					<h1 style="font-size: 2.0em !important; color: red;" align="center">
						<c:out value="${errorMsg}" />
					</h1>
				</c:when>
				<c:otherwise>
					<h2 style="font-size: 1.5em !important;" align="center"> La Solicitud se gener&oacute; exitosamente con el siguiente folio:
						<c:out value="${folio}" />
					</h2>
					<br />
					<form:form id="confirmacionRegistroPersonaFisicaForm" method="get" action="${contextpath}/persona/tramites/${role}">
						<div style="text-align: right; float: right;">
							<input type="button" value="Iniciar Nueva Solicitud" class="mboton" id="solicitudNuevaBtn" />
							<input type="button" value="Imprimir Comprobante" id="imprimirComprobante" class="mboton" id="imprimirComprobante" />
						</div>
					</form:form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<div id="reporteFrame"></div>

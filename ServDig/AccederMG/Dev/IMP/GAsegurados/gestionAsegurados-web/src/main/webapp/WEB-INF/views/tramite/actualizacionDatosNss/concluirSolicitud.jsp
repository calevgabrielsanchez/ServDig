<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script>

	$(document).ready(
		function() {
			$("#regresar").click(
				function(){
					location.href = context_path + "/home/ventanilla";
				}		
			);
		}		
	);
</script>
<div class="page_holder_custom">
	<div>
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Solicitud Concluida</h3>
			<div class="textwidget">
				<c:if test="${empty errorMessage}">
					<div id="successDiv">
						<div class="alert alert-success alert-block"
									style="font-size: 15px; text-align: center;">
						<p style="font-size: .9em;">La solicitud de actualizaci&oacute;n de datos del asegurado con folio
						 <strong>${solicitud.noFolioSolicitud}</strong> ha concluido exitosamente</p>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger alert-block"
								style="font-size: 15px; text-align: center;">
								${errorMessage}
					</div>
				</c:if>
			</div>
			<div style="text-align: right; float: right;">
				<button type="button" id="regresar" class="btn btn-default">REGRESAR</button>
			</div>
		</div>
		
	</div>
</div>

<div id="timer">

</div>
<div id="reporteFrame"></div>

<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<c:if test="${verDocumentos}">
<script type="text/javascript" src="/portal-web/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"></script>
</c:if>
<script type="text/javascript">

$(document).ready(
	function() {
		$("#salir").click(
			function(){
				$.blockUI();
				$("#frmCita").submit();
			}		
		);
		<c:if test="${verDocumentos}">
		$("#verDocumentos").click(function() {
			ejecutarConsultaSolicitudPorFolio('${solicitud.noFolioSolicitud}');
		});
		</c:if>
	}		
);

function ejecutarConsultaSolicitudPorFolio (folio) {
	//console.log("Folio de la solicitud a consultar: %s", folio);
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		return false;
	}
	
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}
</script>


<div class="form-comment">
<form:form id="frmCita" name="frmCita" action="/${mvn.web.app.root}/inicio/grupoFamiliar">
	<br><br><br>
	<fieldset class="titulo">
		<h2 align="center">El tr&aacute;mite de 
		<c:if test="${cambioClinica == null || !cambioClinica }">
		Asignaci&oacute;n de Domicilio
		</c:if>
		<c:if test="${cambioClinica != null && cambioClinica }">
		Cambio de cl&iacute;nica
		</c:if>
		 ha sido finalizado correctamente
		</h2>
		<br><br>
		<div align="center">
		<c:if test="${verDocumentos}">
			<input type="button" id="verDocumentos" value="Ver Documentos" class="mboton"/>
		</c:if>
		<input type="button" id="salir" value="Aceptar" class="mboton"/>
		</div>
	</fieldset> 
</form:form>
</div>

<div id="detalleSolicitudComponent"></div>

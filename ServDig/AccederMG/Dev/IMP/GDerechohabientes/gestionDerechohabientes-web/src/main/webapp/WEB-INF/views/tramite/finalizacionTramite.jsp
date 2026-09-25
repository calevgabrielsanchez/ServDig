<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<jsp:include page="../general/llenaTipoTramite.jsp"></jsp:include>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/finalizacionTramite.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"></script>

<script type="text/javascript">
	$(document).ready(
		function() {
			blockBackButton();
			
			$("#imprimir").click(
				function() {
					var llamarDetalleSolicitud = $("#folioSolicitud").val() != "";
					if(llamarDetalleSolicitud) {
						ejecutarConsultaSolicitudPorFolio($("#folioSolicitud").val());
					} else{
						var imprimeReporte = new impresionReporte("${reporte.idSolicitud}","${reporte.idTramite}","${reporte.personas}",${reporte.tipoTramite.idTipoTramite},"${reporte.idPersona}", "${reporte.tipoTramite.descripcion}", "${reporte.idUmf}","${reporte.idUmfUsuario}",${reporte.rechazado});
						
						imprimeReporte.mostrar();
					}
				}
			);
			
			<c:if test="${impresionSav005 != null && impresionSav005}">
			$("#imprimirSav005").click(function() {
				var imprimeReporte = new impresionReporte("${reporte.idSolicitud}","${reporte.idTramite}","${reporte.personas}",${reporte.tipoTramite.idTipoTramite},"${reporte.idPersona}", "${reporte.tipoTramite.descripcion}", "${reporte.idUmf}","${reporte.idUmfUsuario}",${reporte.rechazado});
				
				imprimeReporte.mostrar();
			});
			</c:if>
			
			$("#salir").click(
				function() {
					$.blockUI();
					if("${reporte.tipoTramite.idTipoTramite}" == 101){
						location.href = ""+context_path + "/welcome/uno/busqueda";
					}else{
						location.href = ""+context_path + "/inicio/grupoFamiliar";
					}
				}		
			);
			
		}
	);
	
	//Funcion general para mostrar el detalle de una solicitud a trav�s de su folio
	function ejecutarConsultaSolicitudPorFolio (folio) {
		//console.log("Folio de la solicitud a consultar: %s", folio);
		if (folio == null || folio == '' || typeof folio === 'undefined') {
			return false;
		}
		
		DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
		DetalleSolicitudCtrl.abrir();
		
	}
</script>

<div id="detalleSolicitudComponent"></div>
<div class="form-comment">
<form:form id="frmCita" name="frmCita" action="#">
	<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
	
	<br><br><br>
	<fieldset class="titulo">
		<h2 align="center">El tr&aacute;mite 
		<c:if test="${!reporte.pendienteAut}">
			<c:if test="${reporte.rechazado}">
				 ha sido rechazado correctamente
			</c:if>
			<c:if test="${!reporte.rechazado}">
				 de ${reporte.tipoTramite.descripcion} ha sido 
				 <c:if test="${impresionSav005 != null && impresionSav005}">
				 registrado 
				 </c:if>
				 <c:if test="${impresionSav005 == null || !impresionSav005}">
				 finalizado 
				 </c:if>
				 correctamente
			</c:if>
		</c:if>
		<c:if test="${reporte.pendienteAut}">
			de ${reporte.tipoTramite.descripcion} ha quedado pendiente de autorizaci&oacute;n
		</c:if>
		</h2>
		<h4 align="center">${reporte.mensaje}</h4>
		<br><br>
		<div align="center">
		<c:if test="${impresionSav005 != null && impresionSav005}">
			<input type="button" id="imprimirSav005" value="Imprimir SAV005" class="mboton"/>
		</c:if>
		<c:if test="${!reporte.pendienteAut}">
			<c:if test="${reporte.muestraBotonImpresion}">
				<input type="button" id="imprimir" value="Imprimir Documentos" class="mboton"/>
			</c:if>
		</c:if>
		<input type="button" id="salir" value="Regresar" class="mboton"/>
		</div>
	</fieldset> 
</form:form>
</div>

<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>


<c:if test="${documento == 1}">
	<script type="text/javascript">
	$(document).ready(
		function(){
			$.getJSON(context_path + "/fileupload/saveDocSinTramite",
					{cveIdTramite:${resultado.tramiteId}
					},              
					function(data) {
			
					}           	
				) 
		}		
	);
	
	
	
	</script>		
</c:if>

<c:choose>
<c:when test="${empty errores}">
<script type="text/javascript">
	$(document).ready(
		function() {
			mostrarComprobante(${resultado.tramiteId},${idTipoTramite},${idPersona}, "${titulo}", ${umf},${umfUsuario});
			location.href = context_path + "/inicio/grupoFamiliar";
		}
	);
	
	function mostrarComprobante(idTramite,idTipoTramite, idPersona, titulo, umf, umfusuario) {
		
		switch(idTipoTramite) {
			case 1: showComprobanteCambioDatos(idTramite, titulo)
				break;
			case 2: showComprobanteCambioUmf(idPersona,idTramite, titulo, umf, umfusuario);
				break;
			case 3: showComprobanteCambioMedico(idPersona);
				break;
			case 4: showComprobanteAutorizacionCircunscripcion(idPersona,idTramite, titulo, umf, umfusuario);
				break;
			case 5: showComprobanteCambioConsultorio(idTramite, titulo);
				break;
			case 6: showComprobanteCambioConsultorio(idTramite, titulo);
				break;
		}
	}
	
	function showComprobanteCambioDatos(idTramite, titulo){
		var direccion= context_path + "/documentos/cambioDatos?idTramite="+idTramite+"&titulo="+titulo+"";
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
		window.resizeTo(800,600);
	}
	
	function showComprobanteCambioUmf(idPersona,idTramite, titulo, umf, umfusuario){
		if(umfusuario == umf) {
			var direccion= context_path + "/documentos/cambioClinica?idTramite="+idTramite+"&titulo="+titulo+"";
			var page= context_path + "/resources/js/delta/viewPdf.html";
			window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
		} else {
			var direccion= context_path + "/documentos/cambioClinicaO?idPersona="+idPersona+"";
			var page= context_path + "/resources/js/delta/viewPdf.html";
			window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
		}
	}
	
	function showComprobanteAutorizacionCircunscripcion(idPersona,idTramite, titulo, umf, umfUsuario){
		var direccion= "";
		
		if(umf == umfUsuario)
			direccion = context_path + "/documentos/circunscripcionA?idTramite="+idTramite+"&titulo="+titulo+"";
		else
			direccion = context_path + "/documentos/documentosAutorizacion?idPersona="+idPersona+"&ind=1";
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
	}
	
	function showComprobanteCambioMedico(idPersona){
		var direccion= context_path + "/documentos/cartilla?idPersona="+idPersona+"";
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
	}
	function showComprobanteCambioConsultorio(idTramite,titulo){
		var direccion= context_path + "/documentos/cambioConsultorio?idTramite="+idTramite+"&titulo="+titulo+"";
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
	}
</script>
</c:when>
<c:otherwise>
	<script type="text/javascript">
	$(document).ready(
		function() {
			$('#regresar').click(
				function() {
					location.href = context_path + "/inicio/grupoFamiliar";
				});
		}
	);
</script>
</c:otherwise>
</c:choose>


<c:choose>
<c:when test="${empty errores}">
<div class="form-comment">
<form:form id="frmCita" name="frmCita" action="#">
	<br><br><br>
	<fieldset class="titulo">
		<h2 align="center">El tr&aacute;mite ha sido finalizado correctamente</h2>	
		<center>
			<c:if test="${idTipoTramite == 4 && umf != umfUsuario}">
				Debe acudir a la umf destino para finalizar el trámite
			</c:if>
		<center>
		<br>
	</fieldset> 
</form:form>
</div>
</c:when>
<c:otherwise>
<div>
	<br>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
	<div>
		<form class="form-comment">
			<input type="button" id="regresar" value="<spring:message code="button.regresar"/>" class="mboton"/>
		</form>
	</div>
</div>
</c:otherwise>
</c:choose>
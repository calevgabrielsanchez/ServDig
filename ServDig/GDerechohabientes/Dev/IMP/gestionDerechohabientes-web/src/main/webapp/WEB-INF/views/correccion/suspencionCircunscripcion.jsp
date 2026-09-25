<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/suspencionCircunscripcion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionSuspencionCircunscripcion.js" htmlEscape="true" />"></script>

<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/domicilio/domicilioAutorizacionCircunscripcionInit.js" htmlEscape="true" />"></script>


<input type="hidden" value="${validacion}" id="validacion"/>
<input id="requiereDocs" type="hidden" value="${requiereDocs?1:0}"/>
<c:if test="${validacion == 0 && requiereDocs}">
	<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
	<script>
		$(document).ready(
			function() {
				loadFileUpload(${tipoTramite},undefined,undefined,'${tipoDocsNoMostrar}');
			}	
		);
	</script>
</c:if>
<c:if test="${validacion == 1}">
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
</c:if>
<c:if test="${validacion == 1}">
		<h4 align="center">VALIDACI&Oacute;N DE SUSPENSI&Oacute;N DE SERVICIOS EN CIRCUNSCRIPCI&Oacute;N FOR&Aacute;NEA</h4>
	</c:if>
	<c:if test="${validacion == 0}">
		<h4 align="center">SUSPENSI&Oacute;N DE SERVICIOS EN CIRCUNSCRIPCI&Oacute;N FOR&Aacute;NEA</h4>
	</c:if>
<div class="form-comment">

<br><br>
<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
<br>
	<div id="mensajeConfirmacion"></div>
<br>
<jsp:include page="./detalleUmf.jsp"></jsp:include>
<br>

<div id="tabla" class="containerRows contenedorPantalla">
			<div id="separadorMediosDomicilio" class="Row divSeparador"></div>
			<div id="filaDomicilios" class="Row">
						 <fieldset>
				<legend><strong>Datos del domicilio origen</strong></legend>
				<div id="domicilioAnteriorDiv"></div>
			</fieldset>
			<BR>
			<fieldset>
				<legend><strong>Datos del domicilio destino</strong></legend>
				<div id="domicilioActualDiv"></div>
			</fieldset>
			
			</div>
 </div> 
		


<!-- datos de la calle -->

<form:form id="correccionDatos" method="POST" action="#" commandName="derechohabiente">
<input type="hidden" value="${validacion}" id="validacion">
<input type="hidden" value="${derechohabiente.tramiteId}" name = "tramiteId" id="tramiteId">
<input type="hidden" value="${derechohabiente.persona.idPersona}" id="idPersona">
<input type="hidden" value="${solicitud.solicitudId}" id="solicitudId">
<input type="hidden" value="${derechohabiente.tramiteSuspension.tramiteId}" id="idTramiteSuspencion">
<input type="hidden" value="${umfAnterior.unidadMedicaFamiliar.idUMF}" id="idUmfOri">
<input type="hidden" value="${umfActual.unidadMedicaFamiliar.idUMF}" id="idUmfDest">
<input type="hidden" value="${usuarioObj.idUmf}" id="umfUsuario">
<input type="hidden" value="${derechohabiente.tramiteSuspension.tramiteId}" id="tramiteSuspension.tramiteId" name="tramiteSuspension.tramiteId">

		<form:hidden path="domicilioOrigen.calle"/>
		<form:hidden path="domicilioOrigen.vialidadPrimaria.nombre"/>
		<form:hidden path="domicilioOrigen.vialidadPrimaria.clave" />
		<form:hidden path="domicilioOrigen.vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="domicilioOrigen.vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="domicilioOrigen.tipoBusquedaVialidad"/>
			
		<form:hidden path="domicilioOrigen.codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="domicilioOrigen.asentamiento.nombre"/>
		<form:hidden path="domicilioOrigen.asentamiento.clave" />
		<!-- Datos de la localidad -->
		<form:hidden path="domicilioOrigen.asentamiento.localidad.nombre"/>
		<form:hidden path="domicilioOrigen.asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="domicilioOrigen.asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="domicilioOrigen.asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="domicilioOrigen.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="domicilioOrigen.asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="domicilioOrigen.numExterior1"/>
		<form:hidden path="domicilioOrigen.numExteriorAlf" />
		<form:hidden path="domicilioOrigen.numInterior"/>
		<form:hidden path="domicilioOrigen.numInteriorAlf"/>
		
			
		<!-- datos de la calle -->
		<form:hidden path="domicilioDestino.calle"/>
		<form:hidden path="domicilioDestino.vialidadPrimaria.nombre"/>
		<form:hidden path="domicilioDestino.vialidadPrimaria.clave" />
		<form:hidden path="domicilioDestino.vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="domicilioDestino.vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="domicilioDestino.tipoBusquedaVialidad"/>
		
		<form:hidden path="domicilioDestino.codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="domicilioDestino.asentamiento.nombre"/>
		<form:hidden path="domicilioDestino.asentamiento.clave" />
		<!-- Datos de la localidad -->
		<form:hidden path="domicilioDestino.asentamiento.localidad.nombre"/>
		<form:hidden path="domicilioDestino.asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="domicilioDestino.asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="domicilioDestino.asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="domicilioDestino.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="domicilioDestino.asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="domicilioDestino.numExterior1"/>
		<form:hidden path="domicilioDestino.numExteriorAlf" />
		<form:hidden path="domicilioDestino.numInterior"/>
		<form:hidden path="domicilioDestino.numInteriorAlf"/>
		

<c:if test="${validacion == 0 }">
		<fieldset>
				<legend><strong>Causa de suspensi&oacute;n de servicios : </strong></legend> 
				<textarea id="observacion" name="observacion" style="height: 40px; width: 770px;"></textarea>
		</fieldset>
</c:if>
<c:if test="${validacion == 1 }">
		<fieldset>
				<legend><strong>Causa de suspensi&oacute;n de servicios : </strong></legend>
				 <textarea id="observacion" name="observacion" style="height: 40px; width: 770px;" disabled="disabled">${observaciones}</textarea>
		</fieldset>
</c:if>
</form:form>
<br><br>
<c:if test="${validacion == 0 && requiereDocs}">
	<div id="cagarDocProbDiv">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
				<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
					<spring:message code="msgDocumentosProb"/>		
				</div>
				</div>
				<div id="docProbTramDiv">
				</div>
				<br><br>
</c:if>
<c:if test="${validacion == 1}">
	<div id="docProbTramDiv"></div>
	<script type="text/javascript">
		$(document).ready(
			function() {
				initMuestraDocumentosTramite(${derechohabiente.tramiteSuspension.tramiteId});
			}
		);
	</script>
</c:if>
<form>
	<div align="center">
			<table>
			<tr>
				<td align="center">
					<input id="aceptar" type="button" value = "Aceptar" class="mboton" />
					<input id="cancelar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
					
					<input id="aceptarValidacion" type="button" value = "Aceptar" class="mboton" />
					<input id="rechazarValidacion" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />
					<input id="regresarGrupoFamiliar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<!--<input id="guiaTramite" type="button" value="<spring:message code="button.guiaTramite"/>" class="mboton" />-->
					<input id="regresar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
					<input id="regresarValidacion" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
				</td>
			</tr>
			</table>
	</div>
</form>

</div>
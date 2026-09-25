<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/cambioMedico.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCambioMedico.js" htmlEscape="true" />"></script>
<c:if test="${validacion == 1}">

<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
	
	<script type="text/javascript">
		$(document).ready(
			function() {
				loadFileUpload(${derechohabiente.tipoTramite.idTipoTramite},undefined,undefined,'${tipoDocsNoMostrar}');
			}	
		);
	</script>
</c:if>
<script type="text/javascript">
	$(document).ready(
		function() {
			setValidacion(${validacion});
		}
	);
	
</script>

<br><br><br>
<div class="form-comment">
<input type="hidden" id="validacion" value="${validacion}">
<input type="hidden" id="idSolicitud" value="${datosSolicitudSession.solicitudId}">
<input id="requiereDocs" type="hidden" value="${requiereDocs?1:0}"/>

<c:if test="${validacion == 1}">
		<h4 align="center">VALIDACI&Oacute;N DE CAMBIO DE M&Eacute;DICO, CONSULTORIO Y TURNO</h4>
	</c:if>
	<c:if test="${validacion == 0}">
		<h4 align="center">CAMBIO DE M&Eacute;DICO, CONSULTORIO Y TURNO</h4>
	</c:if>
	

<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
<form:form id="correccionDatos" method="POST" commandName="derechohabiente">

<form:hidden path="idPersona" />
<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.idUMF"/>
<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />
<form:hidden path="nss" />
<form:hidden path="tramiteId" />
<form:hidden path="tipoTramite.idTipoTramite" />
<form:hidden path="tipoTramite.descripcion" />
<form:hidden path="domicilio.clave" />
<br>

<div id="mensajeConfirmacion"></div>
<br><br>
<fieldset style="width: 97%">
	<table style="width: 100%">
		<tr style="width: 100%">
			<td style="width: 50%">
				<fieldset style="height: 130px;width: 95%">
				<legend><strong><spring:message code="titulo.datosUMF"/>anterior</strong></legend>
				<table style="width: 100%">
					<tr>
						<td>
							<spring:message code="label.umfTurno"/>:
						</td>
						<td>
							<input type="hidden" value="${datosActuales.medicoEnTurno.turno.idTurno}" id="idturnoAnterior"/>
							<input type="text" value="${datosActuales.medicoEnTurno.turno.descripcion}" style="width: 160px;" disabled="disabled"/>
						</td>
						<tr>
							<td>
								<spring:message code="label.consultorio"/>:
							</td>
							<td>
								<input type="hidden" value="${datosActuales.medicoEnTurno.consultorio.idConsultorio}" id="idConsultorioAnterior"/>
								<input type="text" value="${datosActuales.medicoEnTurno.consultorio.descripcion}" style="width: 160px" disabled="disabled"/>
							</td>	
						</tr>
				        <tr>
							<td>
								<spring:message code="label.medicoFamiliar"/>:
							</td>
							<td>
								<input type="text" 
								value="${datosActuales.medicoEnTurno.medicoFamiliar.nombre} ${datosActuales.medicoEnTurno.medicoFamiliar.primerApellido} ${datosActuales.medicoEnTurno.medicoFamiliar.segundoApellido}" style="width: 300px" disabled="disabled"/>
							</td>
						</tr>
				</table>
				</fieldset>
			</td>
			<td style="width: 50%;height: 130px" >
				<fieldset id="medicoEnTurno" style="height: 130px;width: 95%">
					<legend><strong><spring:message code="titulo.datosUMF"/> nueva</strong></legend>
					
					<table class="page_holder_no_height" style="width: 100px">
						<tr>
							<td>
								<spring:message code="label.umfTurno"/>:
							</td>
							<td>
								<combo:creaCombo idHtml="medicoEnTurno.turno.idTurno"
										idHtmlContenedor="correccionDatos"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicTurno" 
										idHtmlValor="${derechohabiente.medicoEnTurno.turno.idTurno}"
									   mostrarSoloActivos = "true" 
									 />
							</td>
							<td>
								<div id="errorIdTurno" class="error">
									</div>
							</td>
						</tr>	
						<tr>
							<td>
								<spring:message code="label.consultorio"/>:
							</td>
							<td>
								<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" >
								</select>
								
								<script type="text/javascript">
									$(document).ready(
										function() {
											setConsultorios(${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}, ${derechohabiente.medicoEnTurno.turno.idTurno},${derechohabiente.medicoEnTurno.consultorio.idConsultorio})
											setMedico(${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF},${derechohabiente.medicoEnTurno.turno.idTurno},${derechohabiente.medicoEnTurno.consultorio.idConsultorio});
										}
									);
								</script>
							</td>
							<td>
								<div id="errorIdConsultorio" class="error">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<spring:message code="label.medicoFamiliar"/>:
							</td>
							<td>
								<form:input path="medicoEnTurno.medicoFamiliar.nombre" style="width: 300px"/>
							</td>
						</tr>
					</table>
						</fieldset>
								</td>
							</tr>
						</table>
					<c:if test="${validacion == 1}">
						<fieldset>
							<spring:message code="label.observaciones"/> : 
							<textarea id="observaciones" name="observaciones" style="height: 40px; width: 770px;">${tramite.observacion}</textarea>
						</fieldset>
					</c:if>	
					
					</fieldset>


	<!-- c:if test="${requiereDocs}"-->
		<br><br>
		<div id="cagarDocProbDiv">
			<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
		</div>
		<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
			<spring:message code="msgDocumentosProb"/>		
		</div>
		<div id="docProbTramDiv"></div>
	<!-- /c:if-->
	<br><br>
	<div align="center">
		<form>
			<table>
			<tr>
				<td align="center">
					<input id="aceptar" type="button" value = "Aceptar" class="mboton" />
					<input id="regresar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
					<input id="cancelar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
					
					<input id="aceptarValidacion" type="button" value = "Aceptar" class="mboton" />
					<input id="rechazarTramite" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />
					<input id="regresarGrupoFamiliar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<!--  <input id="guiaTramite" type="button" value="<spring:message code="button.guiaTramite"/>" class="mboton" />-->
					<input id="regresarValidacion" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
				</td>
			</tr>
			</table>
		</form>
	</div>
</form:form>
</div>
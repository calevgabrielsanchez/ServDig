	<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/correccion/asignarMedicoConsultorioTurno.js" htmlEscape="true" />"></script>
	<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionAsignarMedicoConsultorioTurno.js" htmlEscape="true" />"></script>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<c:if test="${validacion == 1}">
<script type="text/javascript">
		$(document).ready(
			function() {
				loadFileUpload(${derechohabiente.tipoTramite.idTipoTramite});
			}	
		);
	</script>
</c:if>
<div class="form-comment">

<input type="hidden" value="${validacion}" id="validacion">
<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>

<div id="mensajeConfirmacion"></div>

<form:form id="correccionDatos" method="POST" action="#" commandName="derechohabiente">
<br>
    <input type="hidden" value="${validacion}" id="validacion">
	<input type="hidden" name = "tramiteId" value="${derechohabiente.tramiteId}" id="tramiteId">
	<form:hidden path="idPersona"/>
	<input type="hidden" id="idTramiteSuspencion">
	<input type="hidden" id="idUmfU" value="${umf}"/>
	<input type="hidden" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" id="medicoEnTurno.unidadMedicaFamiliar.idUMF" value="${umf}"/>		
									
<fieldset id="medicoEnTurno" style="height: 130px">
		<legend><strong>Datos de la UMF a ingresar</strong></legend>
					
					<table class="page_holder_no_height">
						<tr>
							<td style="width: 50px">
							   <spring:message code="label.umfTurno"/>:
							</td>
							<td style="width: 50px">
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
							<td style="width: 50px">
								<spring:message code="label.consultorio"/>:
							</td>
							<td style="width: 50px">
								<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" 
									>
									<option value=''> -- Por favor seleccione -- </option>
								</select>
								<script type="text/javascript">
									$(document).ready(
										function() {
											setConsultorios($("#idUmfU").val(), "${derechohabiente.medicoEnTurno.turno.idTurno}","${derechohabiente.medicoEnTurno.consultorio.idConsultorio}")
											setMedico($("#idUmfU").val(),"${derechohabiente.medicoEnTurno.turno.idTurno}","${derechohabiente.medicoEnTurno.consultorio.idConsultorio}");
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
								<form:hidden path="medicoEnTurno.idMedicoContultorioTurno"/>
								<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
								<form:input path="medicoEnTurno.medicoFamiliar.nombre" style="width: 300px"/>
								<form:hidden path = "medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
							</td>
						</tr>	
								
						<c:if test="${validacion == 1 }">
							<tr>
								<td colspan="6">
								<form:hidden path="nss" />
								<form:hidden path="tipoTramite.idTipoTramite" />
								<form:hidden path="domicilio.clave" />
								<br></td>
							</tr>
							</c:if>
					</table>
						</fieldset>
					<c:if test="${validacion == 1 }">	
					<fieldset>
						<table>
							<tr>
									<td><spring:message code="label.observaciones"/>: </td>
									<td >
										<textarea id="observaciones" name="observaciones"  disabled="disabled" style="height: 40px; width: 800px;">${derechohabiente.observacion}</textarea>
									</td>
							</tr>
						</table>	
					</fieldset>	
					</c:if>
					<c:if test="${validacion == 1}">
						<div id="cagarDocProbDiv">		
							<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
						</div>
						<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
							<spring:message code="msgDocumentosProb"/>		
						</div>
						
					</c:if>
					<div id="docProbTramDiv"></div>
					<br>
	

					<br>
	<div align="center">
	<form>
			<table>
			<tr>
				<td align="center">
					<input id="aceptar" type="button" value = "Aceptar" class="mboton" />
					<input id="cancelar" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />

					<input id="aceptarValidacion" type="button" value = "Aceptar" class="mboton" />
					<input id="rechazarValidacion" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />
					<input id="regresarGrupoFamiliar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<!--  input id="guiaTramite" type="button" value="<spring:message code="button.guiaTramite"/>" class="mboton" /-->
					<input id="regresar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
				</td>
			</tr>
			</table>
	</form>		
	</div>
												
</form:form>
</div>
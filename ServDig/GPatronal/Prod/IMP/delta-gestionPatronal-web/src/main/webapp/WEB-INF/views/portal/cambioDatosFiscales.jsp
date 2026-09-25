<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>

<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portal/afiliacion/cambioDatosFiscales.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<script lang="javascript">
	var esPatronFisico = ${esPatronFisico};
	var context_path = '<%= request.getContextPath()%>';
	var context = '<%= request.getContextPath()%>';
	var tipoPersonaFiscal = '${sujetoTramite.tipoPersonaFiscal}';
	var tipoTramite = <%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo()%>
</script>


<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="tramiteActualizacionDatosFiscales" style="width: 900px;" >
					<form:form id="nombreComercialForm" modelAttribute="sujetoTramite">	
						<form:hidden path="tipoPersonaFiscal"/>
						<c:if test="${bFisica}">
							<form:hidden path="fisica.idPersona"/>
							<form:hidden path="fisica.rfc"/>
							
							<table style="width: 100% !important; border-width:0 0px 0px 0;" >
								<tr>
									<td style="border-width:0px 0 0 0px; width: 100px !important;" class="label_patrones">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.primer.apellido" />:
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="fisica.primerApellido" disabled="true" size="25" maxlength="60"/>
									</td>
									<td style="border-width:0px 0 0 0px; width: 130px !important;" class="label_patrones">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.segundo.apellido" />:
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="fisica.segundoApellido" disabled="true" size="25" maxlength="60"/>
									</td>
								</tr>
								<tr>
									<td style="border-width:0px 0 0 0px;" class="label_patrones">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.nombres" />:
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="fisica.nombre" disabled="true" size="25" maxlength="60"/>
									</td>
									<td style="border-width:0px 0 0 0px;" class="label_patrones">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="rep.legal.curp" />
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="fisica.curp" disabled="true" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td style="border-width:0px 0 0 0px;" class="label_patrones">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.rfc" />:
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="fisica.rfc" disabled="true" size="15" maxlength="13"/>
									</td>
								</tr>
							</table>
							
							<!-- Seccion Datos generales -->
							<form:hidden path="fisica.pais.idPais"/>
							<form:hidden path="fisica.pais.descripcion"/>
							<form:hidden path="fisica.pais.nacionalidad"/>
							<form:hidden path="fisica.sexo.idSexo"/>
							<form:hidden path="fisica.sexo.descripcion"/>
							<form:hidden path="fisica.sexo.genero"/>
							<form:hidden path="fisica.lugarNacimiento.clave"/>
							<form:hidden path="fisica.lugarNacimiento.nombre"/>
							
							
							
							<!-- Sección domicilio -->
							<form:hidden path="fisica.domicilioFiscal.calle"/>
							<form:hidden path="fisica.domicilioFiscal.colonia"/>
							<form:hidden path="fisica.domicilioFiscal.descripcion"/>
							<form:hidden path="fisica.domicilioFiscal.numExterior1"/>
							<form:hidden path="fisica.domicilioFiscal.numExterior2"/>
							<form:hidden path="fisica.domicilioFiscal.numExteriorAlf"/>
							<form:hidden path="fisica.domicilioFiscal.numInterior"/>
							<form:hidden path="fisica.domicilioFiscal.numInteriorAlf"/>
							<form:hidden path="fisica.domicilioFiscal.numExterior1"/>
							<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.clave"/>
							<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.nombre"/>
							<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave"/>
							<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.clave"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.nombre"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.codigoPostal.codigoPostal"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.clave"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.nombre"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.clave"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.nombre"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave"/>
							<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
							<c:set var="descripcionDomicilio" value="${ACTUALIZACION_DENOMINACION_SOCIAL.fisica.domicilioFiscal.descripcion }"></c:set>
						</c:if>
					
						<c:if test="${!bFisica}">
							<form:hidden path="moral.idPersona"/>
							
							
							<!-- Datos de retorno del servicio de personas -->
							<!-- PERSONA -->
							
							<!-- DOMICILIO -->
							
							
							<table style="width: 100% !important; border-width:0 0px 0px 0;">
								<tr>
									<td class="label_patrones" style="width: 100px !important;">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.rfc" />
										</label>
									</td>
									<td style=" width: 150px !important; border-width:0px 0 0 0px;">
										<form:input path="moral.rfc" disabled="true" maxlength="13"/>
									</td>
									<td class="label_patrones" style="width: 160px !important;">
										<label>
											<span class="indicador_campo_requerido">*</span><spring:message code="label.razon.social" />
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;">
										<form:input path="moral.razonSocial" disabled="true" size="55" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label style="width:25%">
											<span class="indicador_campo_requerido">*</span><spring:message code="label.tipo.sociedad" />
										</label>
									</td>
									<td style="border-width:0px 0 0 0px;" colspan="3">
										<combo:creaCombo idHtml="moral.tipoSociedad.idTipoSociedad"  
								idHtmlContenedor="nombreComercialForm"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad"
								idHtmlValor="${idTipoSociedadVar}"  
								mostrarSoloActivos = "true"/>
								<form:hidden path="moral.tipoSociedad.descripcion"/>
								<form:hidden path="moral.tipoSociedad.descripcionAbreviada"/>
									</td>
								</tr>
							</table>
							
							<form:hidden path="moral.domicilioFiscal.calle"/>
							<form:hidden path="moral.domicilioFiscal.colonia"/>
							<form:hidden path="moral.domicilioFiscal.descripcion"/>
							<form:hidden path="moral.domicilioFiscal.numExterior1"/>
							<form:hidden path="moral.domicilioFiscal.numExterior2"/>
							<form:hidden path="moral.domicilioFiscal.numExteriorAlf"/>
							<form:hidden path="moral.domicilioFiscal.numInterior"/>
							<form:hidden path="moral.domicilioFiscal.numInteriorAlf"/>
							<form:hidden path="moral.domicilioFiscal.numExterior1"/>
							<form:hidden path="moral.domicilioFiscal.codigoPostal.codigoPostal"/>
							<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.clave"/>
							<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.nombre"/>
							<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave"/>
							<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.clave"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.nombre"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.codigoPostal.codigoPostal"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.clave"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.nombre"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.clave"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.nombre"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave"/>
							<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
							<c:set var="descripcionDomicilio" value="${ACTUALIZACION_DENOMINACION_SOCIAL.moral.domicilioFiscal.descripcion }"></c:set>
						</c:if>
								
						<table style="width: 100% !important; border:none;">
							<tr>
								<td class="label_patrones" style="width: 150px;">
									<spring:message code="label.domicilio.fiscal"/>
								</td>
								<td class="label_patrones_data">
									<div id="textoDomicilio">
										${descripcionDomicilio }
									</div>
								<td>
							</tr>
						</table>
								
								
						<div id="seccionMediosFiscales" style="display: none;">	
							<table id="gridMediosFiscales"
									style="width: 500px; vertical-align: top;">
								<thead>
								</thead>
								<tbody style="width: 500px;">
								</tbody>
							</table>
						</div>
						
						<input type="button" class="mboton" id="btnConcluirConFirma" onclick="enviaSolicitudFirmada()" value="Actualizar">
						<input type="button" class="mboton" id="testBtn" onclick="cerrarDialogoTramite()" value="Regresar">
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<form:form modelAttribute="sujetoTramite"  action="" id="formReporteModificacionPatronal"></form:form>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="icaFisicaDialog"></div>
<div id="icaMoralDialog"></div>
<div id="mdmFisicaDialog"></div>
<div id="mdmMoralDialog"></div>
<div id="firmaDigitalDialogo"></div>

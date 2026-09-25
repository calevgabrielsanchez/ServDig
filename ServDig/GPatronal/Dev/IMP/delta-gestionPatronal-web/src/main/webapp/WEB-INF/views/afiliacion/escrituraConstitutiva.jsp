<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/manejadorTabs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/escrituraConstitutiva.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
	
<script>
	var tramiteEscrituraConstitutivaActivo = ${tramiteEscrituraConstitutivaActivo};
	var tramiteEscrituraConstitutivaRatificado = ${tramiteEscrituraConstitutivaRatificado};
	var tipoTramiteEscrituraConstitutiva='<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA%>';
	var bFisicaVar = '${bFisica}';

</script>

<form:form modelAttribute="sujetoObligado" id="escrituraConstitutivaOriginalForm">
	<div align=right>
		<a style="cursor: pointer;" onclick="mensajeCambioTabEC();" ><font color="#0A6659" size="10"><b>..</b></font></a>
	</div>
		<h3><spring:message code="titulo.informacion.actual" /></h3>
		<form:hidden path="escrituraConstitutiva.cveEscrituraConstitutiva"/>
		<form:hidden path="escrituraConstitutiva.cveIdPersonaMoral"/> 
		<form:hidden path="escrituraConstitutiva.cveIdPatronSujetoObligado"/>
		<form:hidden path="escrituraConstitutiva.numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />
		
		<table style="width: 100%; border: none">
			<tr>
				<td class="label_patrones" style="width: 140px !important;">
					<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 280px !important;">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.numEscritura}"/></label>
								
				</td>				
				<td class="label_patrones" style="width: 200px !important;">
					<label> <spring:message code="label.escritura.conts.notaria" />:</label>
				</td>
				<td class="label_patrones_data">
					<label style="width:30%"><c:out value="${sujetoObligado.moral.escrituraConstitutiva.numNotaria}"/></label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
						<label><spring:message code="label.entidad.federativa"/></label>
				</td>
				<td class="label_patrones_data">
					<label style="width:30%"><c:out value="${sujetoObligado.moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre}"/></label>
				</td>				
				<td class="label_patrones">
					<label  ><spring:message code="label.escritura.conts.municipio"/>:</label>
				</td>
				<td class="label_patrones_data">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.lugarExpedicion.nombre}"/></label>
				</td>
			</tr>
			<tr>	
				<td style="border-width:0px 0 0 0px;" class="label_patrones">
					<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
				</td>
				<td class="label_patrones_data">
					<label id="labelfechaEC"><c:out value="${sujetoObligado.moral.escrituraConstitutiva.fechaExpedicion}"/></label>				
				</td>
				<td class="label_patrones">
					<label> <spring:message code="label.escritura.conts.folio" />:</label>
				</td>			
				<td class="label_patrones_data">
					<c:if test="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil!='' && sujetoObligado.moral.escrituraConstitutiva.folioMercantil != null}">
						<c:out value="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil}"/>
					</c:if>
					<c:if test="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil=='' || sujetoObligado.moral.escrituraConstitutiva.folioMercantil == null}">
						<spring:message code="msg.sin.informacion"/>
					</c:if>
				</td>
			</tr>
		</table>
		<table style="width: 100% !important; border: none">
			<tr>
				<td class="label_patrones" style="width: 55px !important;">
					<label> <spring:message code="label.seccion" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.seccion!='' && sujetoObligado.moral.escrituraConstitutiva.seccion != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.seccion}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.seccion=='' || sujetoObligado.moral.escrituraConstitutiva.seccion == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>			
				<td class="label_patrones" style="width: 55px !important;">
					<label  > <spring:message code="label.partida" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.partida!='' && sujetoObligado.moral.escrituraConstitutiva.partida != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.partida}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.partida=='' || sujetoObligado.moral.escrituraConstitutiva.partida == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>
				<td class="label_patrones" style="width: 55px !important;">
					<label> <spring:message code="label.volumen" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.volumen!='' && sujetoObligado.moral.escrituraConstitutiva.volumen != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.volumen}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.volumen=='' ||  sujetoObligado.moral.escrituraConstitutiva.volumen == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>				
				</td>					
				<td class="label_patrones" style="width: 55px !important;">			
					<label> <spring:message code="label.foja" />:</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.foja!='' && sujetoObligado.moral.escrituraConstitutiva.foja != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.foja}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.foja=='' || sujetoObligado.moral.escrituraConstitutiva.foja == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>
			</tr>			
		</table>		
		<table style="margin: 0px; width: 100%; border: none !important;" >
			<tr>
				<td style="border: none !important;">
					<input type="button" id="btnGuardarEscrituraConstitutiva" class="mboton" style="width:200px;" 
							onclick="actualizarEC();" value="Modificar">
				</td>
				<td style="border: none !important;" align="right">
					<div id="grupoRatificarEC">
						<!-- 
						<input type="checkbox" name="chkRatificaEC" id="chkRatificaEC" onclick="ratificaCheckEC();" />
						<b><spring:message code="label.ratificar"/></b>
						 -->
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border: none !important;" align="center">
					<div id="mensajeEscrituraRatificacion">
						<legend class="legendaConfirmacion">
							La informaci&oacute;n del tr&aacute;mite ha sido ratificada
						</legend>
					</div>
				</td>
			</tr>		
		</table>
		</form:form>

<form:form id="escrituraConstitutivaForm" name="escrituraConstitutivaForm" modelAttribute="<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.name()%>">
		
	<div id="divActualizarEscrituraConstitutiva">
	<h3><spring:message code="titulo.tramite" /></h3>
	<form:hidden path="moral.escrituraConstitutiva.cveEscrituraConstitutiva"/>
	<table style="width: 100% !important; border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 150px !important;">
					<span id="errorNegocioLabel" class=" hiddenElement error"></span>
					<span id="numEscrituraError" class="error hiddenElement"></span>
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.num.esc" />:</label>
			</td>
			<td class="label_patrones_data">
					<form:input path="moral.escrituraConstitutiva.numEscritura" maxlength="12" onkeydown="validarNumeros(event)"/>
			</td>
			<td class="label_patrones" style="width: 200px !important;">
					<span id="numNotariaError" class="error hiddenElement"></span>
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.notaria" />:</label>
			</td>
			<td class="label_patrones_data">
					<form:input path="moral.escrituraConstitutiva.numNotaria" maxlength="15"/>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.entidad.federativa"/> </label><input type="hidden" id="validaEntidad" value=""/>
			</td>
			<td class="label_patrones_data">
					<combo:creaCombo idHtml="moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave" idHtmlContenedor="escrituraConstitutivaForm"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="${claveEdo}" 
						mostrarSoloActivos = "true"/>
			</td>
			<td class="label_patrones">
					<span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.municipio"/>
			</td>
			<td class="label_patrones_data">
					<combo:creaCombo
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
						idHtml="moral.escrituraConstitutiva.lugarExpedicion.clave"
						entidadPadre="id.cveEnt"
						idHtmlPadre="moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave" 
						idHtmlContenedor="escrituraConstitutivaForm"
						idHtmlValor="${claveMun}"
						mostrarSoloActivos = "false"/>
			</td>
		</tr>
		<tr>
			<td style="border-width:0px 0 0 0px;" class="label_patrones" width=170>
				<span id="volumenError" class="error hiddenElement"></span>
				<label style="width:40%"><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.fecha"/> </label>
			</td>
			<td class="label_patrones_data" width=230>
				<input type="text" name="txtFechaRegistroEdicionEC" id="txtFechaRegistroEdicionEC" style="width:50%"
					value='${"moral.escrituraConstitutiva.fechaExpedicion"}'/>			
				<form:hidden path="moral.escrituraConstitutiva.fechaExpedicion"/>				
				<div id="divErrorFecha"></div>				
			</td>
			<td style="border-width:0px 0 0 0px;" class="label_patrones">
					<span id="folioMercantilError" class="error hiddenElement"></span>
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.folio" />:</label>
			</td>
			<td class="label_patrones_data">
				<form:input path="moral.escrituraConstitutiva.folioMercantil" maxlength="13"  
				onkeydown="validarNumeros(event);" 
				onfocus="verificar1()"/>
			</td>
		</tr>
	</table>
	<table style="width: 100% !important; border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 55px !important;">
				<span id="seccionError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.seccion" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" path="moral.escrituraConstitutiva.seccion" maxlength="13" size="15"  onfocus="verificar2()"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
				<span id="partidaError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.partida" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" path="moral.escrituraConstitutiva.partida" maxlength="13" size="15" onfocus="verificar2()"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
				<span id="volumenError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.volumen" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" path="moral.escrituraConstitutiva.volumen" maxlength="13" size="15"  onfocus="verificar2()"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
					<span id="fojaError" class="error hiddenElement"></span>
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.foja" />:	</label>
			</td>
			<td class="label_patrones_data" >
				<form:input readonly="false" path="moral.escrituraConstitutiva.foja" maxlength="13" size="15" onfocus="verificar2()"/>
			</td>
		</tr>		
	</table>
	<input type="button" id="btnGuardarEscrituraConstitutiva" class="mboton" style="width:200px;" 
		onclick="guardarEC();" value="Guardar">			
	<input type="button" id="btnCancelarEscrituraConstitutiva" class="mboton" style="width:200px;" 
		onclick="cancelarEC()" value="Cancelar">					
	</div>
</form:form>

<div style="display:none" id="mensajeCambioTabEC" title="Informaci&oacute;n">
	Al dar clic en Aceptar, el registro del acta constitutiva ser&aacute; borrado, &iquest;Est&aacute; seguro?
</div>

<div style="display:none" id="msgDescartarNumEscritura" title="Informaci&oacute;n">
	Al dar clic en los campos Secci&oacute;n, Partida, Volumen o Foja se intenta capturar informaci&oacute;n en ellos lo cual indica
	que se descartar&aacute; la informaci&oacute;n en el campo Folio Mercantil, &iquest;Est&aacute; seguro que desea proceder?
</div>

<div style="display:none" id="msgDescartarSeccion" title="Informaci&oacute;n">
	Al dar clic en campo Folio Mercantil indica	que intenta capturar informaci&oacute;n, se descartar&aacute; la informaci&oacute;n en los campos Secci&oacute;n, Partida, Volumen y Foja, &iquest;Est&aacute; seguro que desea proceder?
</div>

<script language="javascript">
	function guardarEC(){
		if (validarFormEscritura()){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+escrituraConstitutiva+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionEscrituraTramite);
		}
	}
</script>

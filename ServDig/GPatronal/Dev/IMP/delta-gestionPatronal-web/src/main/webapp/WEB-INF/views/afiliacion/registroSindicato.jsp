<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/manejadorTabs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/registroSindicato.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<script>
	var tramiteRegistroSindicatoActivo = ${tramiteRegistroSindicatoActivo};
	var tramiteRegistroSindicatoRatificado = ${tramiteRegistroSindicatoRatificado};
	var tipoTramiteRegistroSindicato='<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO%>';
</script>
<form:form modelAttribute="sujetoObligado" id="registroSindicatoOriginalForm">	
	<div align=right>
		<a style="cursor:pointer;" onclick="mensajeCambioTab();" ><font color="#0A6659" size="10px"><b>..</b></font></a>
	</div>
		<h3><spring:message code="titulo.informacion.actual" /></h3>
		<table style="width: 100%; border: none">
		<tr>
			<td class="label_patrones" style="width: 100px !important;">
				<label><spring:message code="label.sindicato.num.ref" />:</label>			
			</td>
			<td class="label_patrones_data" style="width: 100px !important;">
				<label><c:out value="${sujetoObligado.moral.registroSindicato.numReferenciadocRegistro }"/></label>				
			</td>			
			<td class="label_patrones" style="width: 100px !important;">		
				<label><spring:message code="label.sindicato.fecha" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 100px !important;">
				<label>
					<c:out value="${sujetoObligado.moral.registroSindicato.fechaRegistro }"/>
				</label>					
			</td>
		</tr>
		<tr>
			<td class="label_patrones" style="width: 100px !important;">	
					<label><spring:message code="label.sindicato.autoridad" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 100px !important;">
				<label>
					<c:out value="${sujetoObligado.moral.registroSindicato.autoridadLaboral }"/>
				</label>				
			</td>
		</tr>
	</table>	
	<table style="width: 100%; border: none !important;" >
		<tr>
			<td style="border: none !important;">
				<input type="button" id="btnGuardarRegistroSindicato" class="mboton" style="width:200px;" 
						onclick="actualizarRS();" value="Modificar">
			</td>
			<td style="border: none !important;" align="right">
				<div id="grupoRatificarRS">
					<!-- 
					<input  type="checkbox" name="chkRatificaRS" id="chkRatificaRS" onclick="ratificaCheckRS();" />
					<b><spring:message code="label.ratificar"/></b>
					 -->
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="border: none !important;" align="center">
				<div id="mensajeSindicatoRatificacion" style="display: none;">
					<legend class="legendaConfirmacion">
						La informaci&oacute;n del tr&aacute;mite ha sido ratificada
					</legend>
				</div>
			</td>
		</tr>
	</table>
	</form:form>
			
<form:form id="registroSindicatoForm" modelAttribute="<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.name()%>">
	<form:hidden path="moral.registroSindicato.cveRegistroSindicato"/>
	<div id="divActualizarRegistroSindicato">
		<h3><spring:message code="titulo.tramite" /></h3>
		<table style="width: 100% !important; border: none !important;">
			<tr>
			<td class="label_patrones" style="width: 200px !important;">
				<span id="errorNegocioLabel" class=" hiddenElement error"></span> 
				<span id="numReferenciadocRegistroError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.num.ref" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 200px !important;">
				<form:input readonly="false" path="moral.registroSindicato.numReferenciadocRegistro" onkeydown="validarNumeros(event)" size="20" maxlength="20"/>
			</td>
			<td class="label_patrones">
				<span id="fechaRegistroError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.fecha" />:</label>				
			</td>
			<td class="label_patrones_data">
				<input type="text" readonly="readonly" id="txtFechaRegistroEdicion"
					value='${moral.registroSindicato.fechaRegistro}'/>			
				<form:hidden path="moral.registroSindicato.fechaRegistro"/>
				<div id="divErrorFecha"></div>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">			
				<span id="autoridadLaboralError" class="error hiddenElement"></span>
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.autoridad" />:</label>
			</td>
			<td class="label_patrones_data">
				<form:input readonly="false" path="moral.registroSindicato.autoridadLaboral" maxlength="100"/>
			</td>
		</tr>				
		</table>
		
		<input type="button" id="btnGuardarRegistroSindicato" class="mboton" style="width:200px;" 
			onclick="guardarRS()" value="Guardar">
		
		<input type="button" id="btnCancelarRegistroSindicato" class="mboton" style="width:200px;" 
			onclick="cancelarRS()" value="Cancelar">
					
		</div>
</form:form>
<div style="display:none" id="mensajeCambioTab" title="Informaci&oacute;n">
	Al dar clic en Aceptar, el registro del sindicato ser&aacute; borrado, &iquest;Est&aacute; seguro?
</div>

<script language="javascript">
	function guardarRS(){
		if (validarFormRegistroSindicato()){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+registroSindicato+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionSindicatoTramite);
		}
	}
</script>

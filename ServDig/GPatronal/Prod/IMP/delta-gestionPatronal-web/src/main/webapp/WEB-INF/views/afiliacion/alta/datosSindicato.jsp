<%@ include file="../../general/taglibs.jsp"%>

<div id="seccionSindicato" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.sindicato"/>
	</legend>
	<table style="width: 920px !important; border: none !important;">
			<tr>
			<td class="label_patrones" style="width: 200px !important;">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.num.ref" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 200px !important;">
				<form:input readonly="false" path="moral.registroSindicato.numReferenciadocRegistro" 
				class="validate[required]"
				onkeydown="validarNumeros(event)" size="20" maxlength="20"/>
			</td>
			<td class="label_patrones">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.fecha" />:</label>				
			</td>
			<td class="label_patrones_data">
				<form:input path="moral.registroSindicato.fechaRegistro" type="text" readonly="readonly" id="txtFechaRegistroSindicato"
					class="validate[required,custom[date]]" 
					value='${moral.registroSindicato.fechaRegistro}'/>			
				
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.sindicato.autoridad" />:</label>
			</td>
			<td class="label_patrones_data">
				<form:input readonly="false" path="moral.registroSindicato.autoridadLaboral" class="validate[required]"  maxlength="100"/>
			</td>
		</tr>				
		</table>

</div>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../general/taglibs.jsp"%>

<div id="seccionEscritura" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.escritura.const"/>
	</legend>
	<table style="width: 920px !important; border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 200px !important;">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.num.esc" />:</label>
			</td>
			<td class="label_patrones_data">
					<form:input path="moral.escrituraConstitutiva.numEscritura" class="validate[required]" maxlength="12" onkeydown="validarNumeros(event)"/>
			</td>
			<td class="label_patrones" style="width: 200px !important;">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.notaria" />:</label>
			</td>
			<td class="label_patrones_data">
					<form:input path="moral.escrituraConstitutiva.numNotaria" class="validate[required]" maxlength="15"/>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.entidad.federativa"/> </label><input type="hidden" id="validaEntidad" value=""/>
			</td>
			<td class="label_patrones_data">
					<combo:creaCombo idHtml="moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave" 
						idHtmlContenedor="altaPatronalForm"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
						idHtmlValor="${claveEdo}" 
						cssClassname="validate[required,funcCall[validateEntidadOptionSelection]]"
						mostrarSoloActivos = "true"
						  />
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
						idHtmlContenedor="altaPatronalForm"
						idHtmlValor="${claveMun}" 
						cssClassname="validate[required,funcCall[validateMunicipioOptionSelection]]"
						mostrarSoloActivos = "false"
						/>
			</td>
		</tr>
		<tr>
			<td style="border-width:0px 0 0 0px;" class="label_patrones" width=170>
				<label style="width:40%"><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.fecha"/> </label>
			</td>
			<td class="label_patrones_data" width=230>
				<form:input path="moral.escrituraConstitutiva.fechaExpedicion" class="validate[required,custom[date]]" id="txtFechaRegistroEdicionEC" style="width:50%"/>				
			</td>
			<td style="border-width:0px 0 0 0px;" class="label_patrones">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.escritura.conts.folio" />:</label>
			</td>
			<td class="label_patrones_data">
				<form:input path="moral.escrituraConstitutiva.folioMercantil" maxlength="13"  
				onkeydown="validarNumeros(event);" class="validate[required,funcCall[validate_FolioMercantil_y_Archivo],funcCall[validate_Archivo]] text-input"
				onfocus="solicitarConfirmacionDeFolioMecantil()"/>
			</td>
		</tr>
	</table>
	<table style="width: 920px !important; border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 55px !important;">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.seccion" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" class="validate[required,funcCall[validate_FolioMercantil_y_Archivo],funcCall[validate_Archivo]] text-input" path="moral.escrituraConstitutiva.seccion" maxlength="13" size="15"  onfocus="solicitarConfirmacionSeccionPartidaVolumenFoja(this)"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.partida" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" class="validate[required,funcCall[validate_FolioMercantil_y_Archivo],funcCall[validate_Archivo]] text-input" path="moral.escrituraConstitutiva.partida" maxlength="13" size="15" onfocus="solicitarConfirmacionSeccionPartidaVolumenFoja(this)"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
				<label><span class="indicador_campo_requerido">*</span><spring:message code="label.volumen" />:</label>
			</td>
			<td class="label_patrones_data" style="width: 130px !important;">
				<form:input readonly="false" class="validate[required,funcCall[validate_FolioMercantil_y_Archivo],funcCall[validate_Archivo]] text-input" path="moral.escrituraConstitutiva.volumen" maxlength="13" size="15"  onfocus="solicitarConfirmacionSeccionPartidaVolumenFoja(this)"/>
			</td>
			<td class="label_patrones" style="width: 55px !important;">
					<label><span class="indicador_campo_requerido">*</span><spring:message code="label.foja" />:	</label>
			</td>
			<td class="label_patrones_data" >
				<form:input readonly="false" class="validate[required,funcCall[validate_FolioMercantil_y_Archivo],funcCall[validate_Archivo]] text-input" path="moral.escrituraConstitutiva.foja" maxlength="13" size="15" onfocus="solicitarConfirmacionSeccionPartidaVolumenFoja(this)"/>
			</td>
		</tr>		
	</table>
</div>
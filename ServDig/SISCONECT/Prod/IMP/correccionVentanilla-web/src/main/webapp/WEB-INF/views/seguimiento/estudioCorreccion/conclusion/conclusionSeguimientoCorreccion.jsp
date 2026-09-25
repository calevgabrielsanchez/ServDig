<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="conclusionSeguimientoCorreccionForm" modelAttribute="revOficiosVO">				
				<form:hidden path="cveSolCorr" id="cveSolCorrConclusionSegCorr"/>
				<form:hidden path="cvePresentaCorr" id="cvePresentaCorrConclusionSegCorr"/>
				<form:hidden path="cveRevOficios" id="cveRevOficiosConclusionSegCorr"/>
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Folio del Oficio de Conclusi&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="numFolioOficio" id="folioConclusion" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);" onblur="jsValidaRequeridosConclusion();">
							<label id="labelNumFolioConclusion"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Fecha de Emisi&oacute;n del Oficio :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaEmiOf" id="fecEmiConclusion" size="12">
							<span class="boton_limpiar" id="btnLimpiafecEmiConclusion" onclick="javaScript:jsLimpiafecEmiConclusion();">X</span>
							<label id="labelfecFechaEmiConclusion"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Notificaci&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaNotOf" id="fecNotConclusion" size="12">
							<span class="boton_limpiar" id="btnLimpiafecNotConclusion" onclick="javaScript:jsLimpiafecNotConclusion();">X</span>
							<label id="labelfecFechaNotConclusion"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" id="btnGuardarConclusion" value="Guardar" onclick="jsGuardaConclusion();">
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>
			</form:form>
		</td>
	</tr>
</table>

		
		
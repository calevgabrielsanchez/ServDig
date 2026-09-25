<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="ofSeguimientoCorreccionForm" modelAttribute="revOficiosVO">				
				<form:hidden path="cveSolCorr" id="cveSolCorrOfResSegCorr"/>
				<form:hidden path="cvePresentaCorr" id="cvePresentaCorrOfResSegCorr"/>
				<form:hidden path="cveRevOficios" id="cveRevOficiosOfResSegCorr"/>
				<input type="hidden" id="fecPresentaCorrRecepcion">
				<input type="hidden" id="fecAutProrrogaRO">
				<input type="hidden" id="fecAutSolicitudRO">
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Folio Oficio de Resultados :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="numFolioOficio" id="folioOfiRes" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);" onblur="jsValidaRequeridos();">
							<label id="labelNumFolioOficio"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Fecha de Emisi&oacute;n del Oficio de Resultados :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaEmiOf" id="fecEmiORSegCorr" size="12">
							<span class="boton_limpiar" id="btnLimpiafecEmiORSegCorr" onclick="javaScript:jsLimpiafecEmiORSegCorr();">X</span>
							<label id="labelfecFechaEmiOf"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Notificaci&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaNotOf" id="fecNotORSegCorr" size="12">
							<span class="boton_limpiar" id="btnLimpiafecNotORSegCorr" onclick="javaScript:jsLimpiafecNotORSegCorr();">X</span>
							<label id="labelfecFechaNotOf"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Atenci&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaAtencionOf" id="fecAteORSegCorr" size="12">
							<span class="boton_limpiar" id="btnLimpiafecAteORSegCorr" onclick="javaScript:jsLimpiafecAteORSegCorr();">X</span>
							<label id="labelfecFechaAtencionOf"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" id="btnGuardarOfRes" value="Guardar" onclick="jsGuardaOfResultados();">
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

		
		
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="reqDocumentacionSeguimientoCorreccionForm" modelAttribute="revOficiosVO" >
				<form:hidden path="cveSolCorr" id="cveSolCorrReqDocSegCorr"/>
				<form:hidden path="cveRevOficios" id="cveRevOficiosReqDocSegCorr"/>
				<form:hidden path="cvePresentaCorr" id="cvePresentaCorrReqDocSegCorr"/>
				<form:hidden path="fecElaboraPresentacion" id="fechaElaboraPresenHdn"/>
				<input type="hidden" id="fecAutCedulaRevRD">
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Folio Req. Documentaci&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="numFolioOficio" id="numFolioOficioReqDocSegCorr" size="25" maxlength="25" onblur="javaScript:jsValidaActivaFecNot();" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);">
							<label id="labelNumFolio"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Fecha Emisi&oacute;n del Oficio :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaEmiOf" id="fecEmisionReqDocSegCorr" size="12" readonly="readonly">
							<span class="boton_limpiar" id="btnLimpiafecEmisionReqDocSegCorr" onclick="javaScript:jsLimpiafecEmisionReqDocSegCorr();">X</span>
							<label id="labelFecEmi"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha Notifcaci&oacute;n:</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaNotOf" id="fecNotifReqDocSegCorr" size="12" readonly="readonly">
							<span class="boton_limpiar" id="btnLimpiafecNotifReqDocSegCorr" onclick="javaScript:jsLimpiafecNotifReqDocSegCorr();">X</span>
							<label id="labelFecNotif"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha Atenci&oacute;n Oficio :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input name="fecFechaAtencionOf" id="fecAtenReqDocSegCorr" size="12" readonly="readonly">
							<span class="boton_limpiar" id="btnLimpiafecAtenReqDocSegCorr" onclick="javaScript:jsLimpiafecAtenReqDocSegCorr();">X</span>
							<label id="labelFecAte"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Observaci&oacute;n :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							&nbsp;
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
	                        <textarea rows="4" cols="92" name="txObservaciones" id="txObservacionesReqDocSegCorr" onkeyup="valTamTextArea(event,this,200)"></textarea>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" id="btnGuardarReqDoc" value="Guardar" onclick="javaScript:jsGuardaReqDoc();">
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

		
		
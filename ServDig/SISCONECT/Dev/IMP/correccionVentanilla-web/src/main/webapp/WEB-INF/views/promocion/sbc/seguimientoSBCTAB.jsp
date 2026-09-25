<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form id="seguimientoSBCTABForm" method="Post">
				<input type="hidden" name="fechaOficio" id="fechaOficioSBC">
				<input type="hidden" name="cvePromocion" id="cvePromocionSBCTAB">
				<input type="hidden" id="rolSBCSeguimiento">
				<table class="tablaverde2" style="width: 100%">		
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
							<label id="labelAlert"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha de Notificaci&oacute;n del Oficio :</label>
						</td>
						<td align="left">
							<input type="text" name="fechaNotificacion" id="fechaNotificacionSBCSeg" size="12" readonly="readonly">
							<span class="boton_limpiar" onclick="jsLimpiaFechaNotif();" id="btnLimpiaFechaNotif">X</span>
						</td>
						<td align="left" class="etiqueta2">
							<label>Fecha Cancelaci&oacute;n del Oficio :</label>
						</td>
						<td align="left">
							<input type="text" id="labelFechaCancelacion" size="12" readonly="readonly">
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha de Atenci&oacute;n del Oficio :</label>
						</td>
						<td align="left">
							<input type="text" name="fechaAtencion" id="fechaAtencionSBCSeg" size="12" readonly="readonly">
							<span class="boton_limpiar" onclick="jsLimpiaFechaAtencion();" id="btnLimpiaFechaAtencion">X</span>
						</td>
						<td align="left" class="etiqueta2">
							<label>Fecha de Aut. del Aviso de Dictamen :</label>
						</td>
						<td align="left">
							<input type="text" id="labelFechaAutAviso" size="12" readonly="readonly">
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Cierre por Cot. Raz. :</label>
						</td>
						<td align="left">
							<input type="text" id="labelFechaCierreCotRaz" size="12" readonly="readonly">
						</td>
						<td align="center" class="etiqueta2" colspan="2">
							<label>Periodo de Correcci&oacute;n :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha de Reg. Promoci&oacute;n :</label>
						</td>
						<td align="left">
							<input type="text" id="labelFechaRegPromocion" size="12" readonly="readonly">
						</td>
						<td align="center"  colspan="2">
							<table>
								<tr>
									<td align="left" class="etiqueta2">
										<label>Del :</label>
									</td>
									<td align="left">
										<input type="text" id="labelFechaDelSegSBC" name="fechaInicio" size="12" readonly="readonly">
									</td>
									<td align="left" class="etiqueta2">
										<label>Al :</label>
									</td>
									<td align="left">
										<input  type="text" id="labelFechaAlSegSBC" name="fechaFin" size="12" readonly="readonly">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha de la Solicitud de Corr :</label>
						</td>
						<td align="left">
							<input  type="text" id="labelFechaSolCorr" size="12" readonly="readonly">
						</td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Oficio Invitaci&oacute;n :</label>
						</td>
						<td align="left">
							<input  type="text" id="labelFechaOficioInv" size="12" readonly="readonly">
						</td>
						<td align="left" colspan="2">
							<input type="button" class="boton" id="butonSBCInv" value="Generar Invitaci&oacute;n" onclick="javaScript:inicializaInvitacionSBC();">
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" colspan="4">
							<input type="checkbox" id="banderaSBC" value="rp" onclick="jsValidaRPSBC(this.value);">
							<label for="banderaSBC">Regularizar Promoci&oacute;n :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" colspan="4">
							<label>Observaciones: :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
	                        <textarea rows="4" cols="92" name="txObservaciones" id="txObservacionesSBCTAB"   onchange="validaCampo('noCaracteresEspeciales','txObservacionesSBCTAB','seguimientoSBCTABForm')" onkeyup="valTamTextArea(event,this,200)"></textarea>
						</td>
					</tr>
					<tr class=par>
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
	                        <input type="button" class="boton" value="Guardar" id="btnGuardarSBCTAB" onclick="javaScript:guardaSeguimientoSBC();">
						</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>						
				</table>
			</form>
		</td>
	</tr>
</table>
		
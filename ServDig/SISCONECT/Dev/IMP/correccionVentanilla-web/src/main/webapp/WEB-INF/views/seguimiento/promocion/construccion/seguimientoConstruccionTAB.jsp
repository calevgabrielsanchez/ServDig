<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form id="seguimientoConstruccionTABForm" method="Post">
				<input type="hidden" name="fechaOficio" id="fechaOficioConstruccion">
				<input type="hidden" name="cvePromocion" id="cvePromocionConstruccionTAB">
				<input type="hidden" id="rolConstruccionSeguimiento">
				<input type="hidden" id="regPatronConstruccion" name="regPatron">
				<input type="hidden" id="rolSeguimientoEX">
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
							<input type="text" name="fechaNotificacion" id="fechaNotificacionConstruccionSeg" size="12" readonly="readonly">
							<span class="boton_limpiar" id="btnLimpiaFechaNotifConstruccion" onclick="javaScript:jsLimpiaFechaNotificacionEX();">X</span>
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
							<label>Fecha de Derivaci&oacute;n Subdelegaci&oacute;n :</label>
						</td>
						<td align="left">
							<input type="text" readonly="readonly" size="12" id="lableFechaDerivSubdelegacion">
						</td>
						<td align="left" class="etiqueta2">
							<label>Fecha de Autorizaci&oacute;n del Aviso de Dictamen :</label>
						</td>
						<td align="left">
							<input type="text" id="labelFechaAutAviso" size="12" readonly="readonly">
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Solicitud de Correcci&oacute;n :</label>
						</td>
						<td align="left" >
							<input type="text" id="labelFechaSolCorr" size="12" readonly="readonly">
						</td>	
						<td colspan="2">&nbsp;</td>					
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Derivaci&oacute;n Fiscalizaci&oacute;n :</label>
						</td>
						<td align="left" >
							<input type="text" id="labelFechaFiscalizacion" size="12" readonly="readonly">
						</td>	
						<td colspan="2">&nbsp;</td>					
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Atenci&oacute;n del Oficio:</label>
						</td>
						<td align="left">
							<input type="text" id="fechaAtencionConstruccion" name="fechaAtencion" size="12" readonly="readonly">
							<span class="boton_limpiar" id="btnLimpiaFechaAtencionConstruccion" onclick="jsLimpiaFechaAtencionEX();">X</span>
						</td>
						<td align="left" class="etiqueta2">
							<label>Afil-15 :</label>
						</td>
						<td align="left">
							<input type="text" id="afil15Construccion" name="cveNroregobraSatic" size="13" maxlength="12" onkeypress="return jsvalidarNumerico(event);">
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Cierre por Cotizar Razonablemente :</label>
						</td>
						<td align="left">
							<input  type="text" id="labelFechaCierreCotR" size="12" readonly="readonly">
						</td>
						<td colspan="2" align="center" class="etiqueta2">
							<label id="lableTituloPeriodo">Periodo a Regularizar</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha de Regularizaci&oacute;n de Promoci&oacute;n :</label>
						</td>
						<td align="left">
							<input  type="text" id="labelFechaRegPromo" size="12" readonly="readonly">
						</td>
						<td align="center"  colspan="2">
							<table>
								<tr>
									<td align="left" class="etiqueta2">
										<label>Del :</label>
									</td>
									<td align="left">
										<input type="text" id="labelFechaDelSegConstruccion" name="fechaInicio" size="12" readonly="readonly">
									</td>
									<td align="left" class="etiqueta2">
										<label>Al :</label>
									</td>
									<td align="left">
										<input  type="text" id="labelFechaAlSegConstruccion" name="fechaFin" size="12" readonly="readonly">
									</td>
								</tr>
							</table>
						</td>						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Fecha Oficio Invitaci&oacute;n :</label>
						</td>
						<td align="left">
							<input  type="text" id="labelFechaInvitacion" size="12" readonly="readonly">
						</td>
						<td align="left" colspan="2">
							<input type="button" class="boton" id="butonConstruccionInv" value="Generar Invitaci&oacute;n" onclick="javaScript:inicializaInvitacionConstruccion();">
						</td>						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" colspan="4">
							<input type="checkbox" id="banderaConstruccion" value="rp" onclick="javaScript:jsValidaRP(this.value);">
							<label for="banderaConstruccion">Regulariza Obra :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" colspan="4">
							<label>Observaciones: :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
	                        <textarea rows="4" cols="92" name="txObservaciones" id="txObservacionesConstruccionTAB" onchange="validaCampo('noCaracteresEspeciales','txObservacionesConstruccionTAB','seguimientoConstruccionTABForm')" onkeyup="valTamTextArea(event,this,200)"></textarea>
						</td>
					</tr>
					<tr class=par>
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
	                        <input type="button" class="boton" value="Guardar" id="btnGuardarConstruccionTAB" onclick="javaScript:inicializaDialogConfirmarTAB();">
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
		
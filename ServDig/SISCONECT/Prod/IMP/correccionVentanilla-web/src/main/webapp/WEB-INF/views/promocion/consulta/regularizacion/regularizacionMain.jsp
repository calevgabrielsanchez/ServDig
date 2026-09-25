<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/regularizacion/regularizacion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/estiloTablas.css">


	<div id="dgRegularizaPromocion" 
		style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
		<div id="wrapperDialogRegularizacionPromocion"
			style="background-color: #f2fff2;">
			<form action="" method="post"
				id="promocionRegularizaForm">
				<input type="hidden" name="cveRegulapagos" id="cveRegulapagos">
				<input type="hidden" name="cvePromocion" id="cvePromocion">
				<input type="hidden" name="fecPeriodo" id="fecPeriodo">
				<input type="hidden" name="regPatron" id="regPatron">
				<input type="hidden" name="tipoProm" id="tipoProm">
				<div id="labelCveRegulaPagos"></div>
				<fieldset>
					<table style="width: 700px" align="center">
						<tr valign="middle">
							<td align="center" width="700px">
								<table class="tablaverde2" style="width: 700px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="5">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Fecha de Atenci&oacute;n:</td>
											<td align="left" width="20%" colspan="1">
												<input name="fechaAtencionPro" id="fechaAtencionPro" 
												onchange="validafechaSistema('promocionRegularizaForm','fechaAtencionPro','Fecha de Atenci&oacute;n');activaPagos()"/> 
												<label for="fechaAtencionPro"></label>
											</td>
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Fecha PAI:</td>
											<td align="left" width="20%" colspan="1" >												
												<input name="fechaPAI" id="fechaPAI" 
												onchange="validafechaSistema('promocionRegularizaForm','fechaPAI','Fecha PAI');activaPagos()" />
												<label for="fechaPAI"></label></td>
											<td>											
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> % Regularizado:</td>
											<td align="left" width="20%"><input type="text"
												name="porRegularizado" id="porRegularizado"
												 size="15" maxlength="5" disabled="disabled"
												onkeyup="validaCampo('noCaracteresEspeciales','porRegularizado','promocionRegularizaForm');validaCampo('PermiteSoloNumeros','porRegularizado','promocionRegularizaForm');valorMaxim('porRegularizado','Porcentaje Regularizado','promocionRegularizaForm')" />
												<label for="porRegularizado"></label>
											</td>
											<td align="left" width="10%" colspan="1" ><span
												class="required">*</span> % Avance:</td>
											<td align="left" width="30%" colspan="2"><input type="text"
												name="porAvance" id="porAvance" disabled="disabled"
												 size="15" maxlength="5"
												onkeyup="validaCampo('noCaracteresEspeciales','porAvance','promocionRegularizaForm');validaCampo('PermiteSoloNumeros','porAvance','promocionRegularizaForm');valorMaxim('porAvance','Porcentaje de avance','promocionRegularizaForm')" />
												<label for="porAvance"></label>
											</td>											
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Periodo de Correcci&oacute;n del:</td>
											<td align="left" width="20%" colspan="4" >
												<input name="fecPerIni" id="fecPerIni" readonly="readonly" disabled="disabled"
												onchange="validafechaSistema('promocionRegularizaForm','fecPerIni','Fecha Incial del Periodo')"/> 
												<label for="fecPerIni"></label> 
												<span class="required">*</span>al: 
												<input name="fecPerFin" id="fecPerFin" readonly="readonly" disabled="disabled"
												onchange="validaFecha('promocionRegularizaForm','fecPerIni','fecPerFin','Fecha Final del Periodo','Fecha Inicial del Periodo');validafechaSistema('promocionRegularizaForm','fecPerFin','Fecha Final del Periodo')" />
												<label for="fecPerFin"></label></td>											
										</tr>
										<tr valign="top" class="par">
											<td valign="top" class="par" width="20%" colspan="2">&nbsp;</td>
											<td valign="top" class="par" width="10%" colspan="1">&nbsp;</td>											
											<td valign="top" class="par" width="10%" align="center">COP</td>
											<td valign="top" class="par" width="10%" align="center">RCV</td>
										</tr>										
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Trabajadores Revisados:</td>
											<td align="left" width="20%"><input type="text"
												name="numTrabrevisados" id="numTrabrevisados" disabled="disabled"
												 size="15" maxlength="5"
												onkeyup="validaCampo('noCaracteresEspeciales','numTrabrevisados','promocionRegularizaForm');validaCampo('PermiteSoloNumeros','numTrabrevisados','promocionRegularizaForm')" 
												onblur="sumaTrabajadores()" />
												<label for="numTrabrevisados"></label>
											</td>
											<td valign="top"  width="10%" align="right">SP</td>											
											<td align="center" width="10%"><input type="text"
												id="txCopSp" readonly="readonly"
												 size="15"/>
												<label for="numTrabrevisados"></label>
											</td>										
											<td align="center" width="10%"><input type="text"
												id="txRcvSp" readonly="readonly"
												 size="15" />
												<label for="numTrabrevisados"></label>
											</td>										
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Trabajadores Omisos:</td>
											<td align="left" width="20%"><input type="text"
												name="numTrabomisos" id="numTrabomisos" disabled="disabled"
												 size="15" maxlength="5"
												onkeyup="validaCampo('noCaracteresEspeciales','numTrabomisos','promocionRegularizaForm');validaCampo('PermiteSoloNumeros','numTrabomisos','promocionRegularizaForm')"
												onblur="sumaTrabajadores()" />
												<label for="numTrabomisos"></label>
												<div id="labelTabOmisos"></div>
											</td>
											<td valign="top"  width="10%" align="right">Act</td>
											<td align="center" width="10%"><input type="text"
												id="txCopAct" readonly="readonly"
												 size="15" />
												<label for="numTrabrevisados"></label>
											</td>										
											<td align="center" width="10%"><input type="text"
												id="txRcvAct" readonly="readonly"
												 size="15" />
												<label for="numTrabrevisados"></label>
											</td>										
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span> Trabajadores Subdeclarados:</td>
											<td align="left" width="20%"><input type="text"
												name="numTrabsubdclara" id="numTrabsubdclara"
												 size="15" maxlength="5" disabled="disabled"
												onkeyup="validaCampo('noCaracteresEspeciales','numTrabsubdclara','promocionRegularizaForm');validaCampo('PermiteSoloNumeros','numTrabsubdclara','promocionRegularizaForm')" 
												onblur="sumaTrabajadores()" />
												<label for="numTrabsubdclara"></label>
											</td>
											<td valign="top"  width="10%" align="right">Rec</td>
											<td align="center" width="10%"><input type="text"
												id="txCopRec" readonly="readonly"
												 size="15"/>
												<label for="numTrabrevisados"></label>
											</td>										
											<td align="center" width="10%"><input type="text"
												id="txRcvRec" readonly="readonly"
												 size="15"/>
												<label for="numTrabrevisados"></label>
											</td>										
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span>Trabajadores Regularizados:</td>
											<td align="left" width="20%"><input type="text" id="numTrabReg"
												 size="15"/>
											</td>
											<td valign="top"  width="10%" align="right">TP</td>
											<td align="center" width="10%"><input type="text"
												id="txCopTp" readonly="readonly"
												 size="15" />
												<label for="numTrabrevisados"></label>
											</td>										
											<td align="center" width="10%"><input type="text"
												id="txRcvTp" readonly="readonly"
												 size="15" />
												<label for="numTrabrevisados"></label>
											</td>										
										</tr>
										<tr valign="top" class="par" id="convenio" style="display: none">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span>Numero de Convenio:</td>
											<td align="left" width="20%"><input type="text" id="numConvenio" name="numConvenio"
												 size="15"/>
											</td>
											<td valign="top"  width="10%" align="right"></td>
											<td align="center" width="10%"><input type="text"
												id="txSPCop" readonly="readonly"
												 size="15" maxlength="5"/>												
											</td>								
											<td align="center" width="10%"><input type="text"
												id="txSPRcv" readonly="readonly"
												 size="15" maxlength="5"/>												
											</td>								
										</tr>
										<tr valign="top" class="par" id="parcialidades" style="display: none">
											<td align="left" width="20%" colspan="1" ><span
												class="required">*</span>Numero de Parcialidades:</td>
											<td align="left" width="20%"><input type="text" id="numParcialidades" name="numParcialidades"
												 size="15"/>
											</td>
											<td valign="top"  width="10%" align="right">SPP</td>
											<td align="center" width="10%"><input type="text"
												id="txSPRcvTt" readonly="readonly"
												 size="15" maxlength="10"/>												
											</td>								
											<td align="center" width="10%"><input type="text"
												id="txSPCopTt" readonly="readonly"
												 size="15" maxlength="10"/>												
											</td>
										</tr>											
										<tr valign="top" class="par">
											<td align="left" colspan="5">&nbsp;</td>
										</tr>										
										<tr valign="top" class="par">
											<td align="left" colspan="3">&nbsp;</td>
											<td align="center" width="80px" colspan="2"><a href="#"
												onclick="javascript:agregarPago();"><span class="boton">Agregar Pago</span>
											</a></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="1">&nbsp;</td>
											<td align="center" width="80px" colspan="3"><a href="#"
												onclick="javascript:Guardar();"><span class="boton">Guardar</span>
											</a></td>
											<td align="left" colspan="1">&nbsp;</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="5">&nbsp;</td>
										</tr>
									</tbody>
								</table></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
	
	
	<div id="regularizacionPagos">
		<jsp:include page="regularizacionPagos.jsp" />
	</div>
	
</html>
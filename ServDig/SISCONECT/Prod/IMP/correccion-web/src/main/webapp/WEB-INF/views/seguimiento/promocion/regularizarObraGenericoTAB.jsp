<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<table class="tablaverde2" width="100%">
		<tr>
			<td colspan="4">
				<form:form id="regularizarObraGenericoTABForm" method="Post" modelAttribute="seguimientoGenericoVO" action="seguimiento/generico/regulaObraGenerico.do">
					<input type="hidden" id="functionAuxRegularizaObra"/>
					<form:hidden  path="cvePromocion" id="cvePromocion"/>
					<form:hidden  path="registroPatronal" id="registroPatronalPagosDt"/>
					
					<form:hidden  path="segRegularizaObraVo.cveRegulaPagos" id="cveRegulaPagos"/>
					<form:hidden  path="segRegularizaObraVo.registroPatronalPromocionVal" id="registroPatronalValidacionProm"/>
					<form:hidden  path="fechaNotificacionOficio" id="fechaNotificacionHdn"/>
					<table class="tablaverde2" style="width: 100%">	
						<tr valign="top" class="impar">
							<td align="left" colspan="9">&nbsp;</td>
						</tr>
						<tr class="par">
							<td align="center" class="etiqueta2" width="80%" colspan="7">
								
							</td>
							<td align="center" class="etiqueta2" width="10%" >
								<label> COP </label>
							</td> 
							<td align="center" class="etiqueta2" width="10%" >
								<label> RCV </label>
							</td>  
						</tr>	
						<tr class="par">
							<td align="center" class="etiqueta2" width="20%" colspan="2">
								<label>Periodo a Regularizar : </label>
							</td> 
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trab. Revisados</label>
							</td>
							<td align="left" width="15%" >
								<form:input path="segRegularizaObraVo.trabRevisados" id="trabRevisados" maxlength="5" size="7" 
								onkeyup="validaRangoEntero(this,'0','9999');$('form#regularizarObraGenericoTABForm #labelTrabRevisados').html('');" 
								onblur="moneyMask(this,0);calcTotalTrabRegularizado()" cssStyle="text-align:right;" /> 
							<div id="labelTrabRevisados"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>		
							<td align="left" class="etiqueta2" width="10%">
								<span class="required">* </span><label>Suerte Ppal Det.</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="segRegularizaObraVo.suertePpalDetCOP" id="suertePpalDetCop" maxlength="13" size="18"
								  onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetCop','regularizarObraGenericoTABForm');validaRangoDouble(this,'0','999999999');$('form#regularizarObraGenericoTABForm #labelSuertePpalDetCop').html('');$('form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv').html('');" 
								  onblur="moneyMask(this,2);validaMontoSuertePpalBaseDeterminada()" cssStyle="text-align:right;"
								/>
								<div align="right" id="labelSuertePpalDetCop"></div>
							</td>
							<td align="left"  width="14%">
								<form:input path="segRegularizaObraVo.suertePpalDetRCV" id="suertePpalDetRcv" maxlength="13" size="18"
								onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetRcv','regularizarObraGenericoTABForm');validaRangoDouble(this,'0','999999999');$('form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv').html('');$('form#regularizarObraGenericoTABForm #labelSuertePpalDetCop').html('');"
								onblur="moneyMask(this,2);validaMontoSuertePpalRCVBaseDeterminada()   "cssStyle="text-align:right;"
								 />
								<div id="labelSuertePpalDetRcv"></div>
							</td>										
						</tr>
						<tr class="par">
							<td align="left" class="etiqueta2" width="10%">
								<span class="required">*</span><label>Del : </label>
							</td> 
							
							<td align="left" width="10%" >
								<form:input path="segRegularizaObraVo.periodoRegDel" size="12" id="perRegularizaDel" readOnly ="readOnly"  onchange="javaScript:completaFechaPeriodo()"/>
								<div id="labelPerRegularizaDel"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trab. Omisos (NSS Unicos) </label>
							</td>
							<td  align="left" width="15%">
								<form:input  id="trabOmisosUni" path="segRegularizaObraVo.trabOmisos" maxlength="5" size="7" 
								 onblur="moneyMask(this,0);calcTotalTrabRegularizado()" onkeyup="validaRangoEntero(this,'0','9999');$('form#regularizarObraGenericoTABForm #labelTrabOmisosUni').html('');"
								  cssStyle="text-align:right;" />
								<div id="labelTrabOmisosUni"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Suerte Ppal Pent Pago</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="segRegularizaObraVo.suertePpalPentPagoCOP" id="suertePpalPenPagoCop" readonly="true" disabled="disabled"
								cssStyle="text-align:right;" size="18"
								/>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.suertePpalPentPagoRCV" id="suertePpalPenPagoRcv" readonly="true" disabled="disabled"
								cssStyle="text-align:right;"  size="18"
								/>
							</td>		
					</tr>
					<tr class="par">
							<td align="left" class="etiqueta2" width="10%">
								<span class="required">*</span><label>Al : </label>
							</td>
							<td align="left" width="10%">
								<form:input path="segRegularizaObraVo.periodoRegAl" size="12" id="perRegularizaAl" readOnly="readonly" onchange="complementaFecha()"/>
								<span class="boton_limpiar" onclick="limpiaFechaNotificacionRegObra()" id="spnPeriodoRegObra">X</span>
								<div id="labelPerRegularizaAl"></div>
							</td> 
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trab. Subdeclarados (NSS unicos) </label>
							</td>
							<td  align="left"  width="15%">
								<form:input  id="trabSubdeclaUni" path="segRegularizaObraVo.trabSubdeclarados"  maxlength="5" size="7" 
								 onblur="moneyMask(this,0);calcTotalTrabRegularizado()" onkeyup="validaRangoEntero(this,'0','9999');$('form#regularizarObraGenericoTABForm #labelTrabSubdeclaUni').html('');"	
								 cssStyle="text-align:right;"
								 />
								<div id="labelTrabSubdeclaUni"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label >Suerte Principal</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="segRegularizaObraVo.suertePrincipalCOP" id="suertePrincipalCop"  disabled="disabled" readonly="true"
								cssStyle="text-align:right;"  size="18" />
							</td>	
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.suertePrincipalRCV" id="suertePrincipalRcv" readonly="true" disabled="disabled"
								cssStyle="text-align:right;" size="18"
								/>
							</td>	
					</tr>
					<tr class="par">
							<td align="left" class="etiqueta2" width="10">
								&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10">
								&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td> 
							
							<td  align="left" class="etiqueta2" width="15%">
								<label>Trab. Regularizados </label>
							</td>
							<td  align="left" width="15%">
								<form:input  id="trabRegularizadosRegObra" path="segRegularizaObraVo.trabRegularizados" readonly="true" disabled="disabled" 
								onchange="moneyMask(this,0)"  
								cssStyle="text-align:right;"/>
								<div id="labelTrabRegularizadosRegObra"></div>
								
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Actualizaci&oacute;n</label>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.actualizacionCOP" id="actualizacionCop" disabled="disabled" readonly="true"
								cssStyle="text-align:right;"  size="18"/>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.actualizacionRCV" id="actualizacionRcv" readonly="true" disabled="disabled"
								cssStyle="text-align:right;"  size="18"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="10%">
								<label>% Avance</label>
							</td>
							<td align="left"  width="10%">
								<form:input path="segRegularizaObraVo.porcAvance" id="porcentajeAvance" size="5" maxlength="3" 
								onkeyup="validaCampo('PermiteSoloNumeros','porcentajeAvance','regularizarObraGenericoTABForm');validaPorcentaje('porcentajeAvance','labelPorcAvanceGen')"/>
								<div id="labelPorcAvanceGen"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td> 
							
							<td  align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Base determinada </label>
							</td>
							<td  align="left" width="15%">
								<form:input  id="baseDeterminada" path="segRegularizaObraVo.baseDeterminada" maxlength="13" size="16" 
								onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','baseDeterminada','regularizarObraGenericoTABForm');validaRangoDouble(this,'0','999999999');$('form#regularizarObraGenericoTABForm #labelBaseDeterminada').html('');" 
								cssStyle="text-align:right;" 
								onblur="moneyMask(this,2);validaMontoSuertePpalBaseDeterminada();validaMontoSuertePpalRCVBaseDeterminada()"
								/>
								<div id="labelBaseDeterminada"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Recargos</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="segRegularizaObraVo.recargosCOP" id="recargosCop" disabled="disabled" readonly="true"
								cssStyle="text-align:right;" size="18"
								/>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.recargosRCV" id="recargosRcv" readonly="true" disabled="disabled"
								cssStyle="text-align:right;"  size="18"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="10%">
								<label>% Regularizado</label>
							</td>
							<td align="left" width="10%">
								<form:input path="segRegularizaObraVo.porcRegularizado" id="porcentajeRegularizado" size="5" maxlength="3"
								onkeyup="validaCampo('PermiteSoloNumeros','porcentajeRegularizado','regularizarObraGenericoTABForm');validaPorcentaje('porcentajeRegularizado','labelPorcRegularizadoGen')" />
								<div id="labelPorcRegularizadoGen"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%" >
								&nbsp;&nbsp;
							</td> 
							
							<td  align="left" class="etiqueta2" width="15%">
								&nbsp;
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Multas : </label>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.multasCOP" id="multasCopSegProm" disabled="disabled" readonly="true" cssStyle="text-align:right;" size="18"/>
							</td>
							<td align="left" width="14%">
								<form:input path="segRegularizaObraVo.multasRCV" id="multasRcvSegProm" readonly="true" disabled="disabled" cssStyle="text-align:right;" size="18"/>
							</td>		
					</tr>
					<tr valign="top" class="par">
						<td align="right" class="etiqueta2" width="10%">
							<label>N&uacute;m. de Parcialidades </label>
						</td>
						<td align="left"  width="10%" >
							<form:input path="segRegularizaObraVo.numParcialidades" id="numeroParcialidades" size="5" maxlength="3" 
							onkeyup="validaCampo('PermiteSoloNumeros','numeroParcialidades','regularizarObraGenericoTABForm')" onblur="validaParcialidadesSegProm()"/>
							<div id="labelNumeroParcialidadesPro"></div>
						</td>
						<td align="left" width="5%" >&nbsp;</td>
				    	<td align="left" >&nbsp;</td>
				    	<td align="left" >&nbsp;</td>
				    	<td align="left" >&nbsp;</td>
				    	<td align="left" ><label>Total Pagado : </label></td>
				    	<td align="left"><form:input path="segRegularizaObraVo.totalPagadoCOP" id="totalPagadoCop" disabled="disabled" readonly="true"
								cssStyle="text-align:right;" size="19"/></td>
				    	<td align="left"><form:input path="segRegularizaObraVo.totalPagadoRCV" id="totalPagadoRcv" readonly="true" disabled="disabled" cssStyle="text-align:right;" size="19"/></td>
					</tr>
					<tr valign="top" class="par">
						
						<td align="left" colspan=9" >&nbsp;</td>
				    	
					</tr>
					<tr valign="top" class="par">
						<td align="left" width="10%" >&nbsp;</td>
				    	<td align="center" colspan="5" >
							<input type="button" value="Guardar" id="btnGuardarRegularizaObra" width="10px" height="12px" class="boton" onclick="procesaFormularioRegObraGenerico(validaCamposRegularizaObra())" >
						</td>
						<td align="center" colspan="3" >
							<input type="button" value="Datos de la Regularizaci&oacute;n" id="btnDatosRegularizacion" width="9px" height="9px" class="boton" onclick="openDialogoPagosVersion2()" >
						</td>
					</tr>
				</table>
				</form:form>
			</td>
		</tr>
		<tr valign="top" class="impar">
			<td align="left" colspan="4">&nbsp;</td>
		</tr>
	</table>
	
	
		<div id="divSeguimientoPromocionSaticb" align="center"  style="display: none">		
		<jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/anexoPagosGenerico.jsp" />
	</div>	
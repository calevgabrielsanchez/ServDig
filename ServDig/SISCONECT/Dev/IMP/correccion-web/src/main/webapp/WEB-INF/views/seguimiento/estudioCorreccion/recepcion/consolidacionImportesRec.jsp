	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
	
	<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td align="center" colspan="9">Consolidaci&oacute;n de Importes por Aclarar (Total RP´s y Total Ejercicios)</td>
							</tr>
						</thead>
						<tbody>
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
							<td align="right" class="etiqueta2" width="10%" >
								<span class="required" id="spanReqTRabRegulaSegCorr" style="display:none;">*</span><label>% Avance</label>
							</td> 
							<td align="left"  width="5%">
								<form:input path="consolidaImporteVo.porcAvance" id="porcentajeAvanceSegCorr" size="5" maxlength="3" 
									onkeyup="validaCampo('PermiteSoloNumeros','porcentajeAvanceSegCorr','formRecepcionSeguimiento');validaPorcentajeConsolImpte('porcentajeAvanceSegCorr','labelPorcAvanceGenSegCorr')"/>
								<div id="labelPorcAvanceGenSegCorr" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trabajadores Revisados</label>
							</td>
							<td align="left" width="15%" >
								<form:input path="consolidaImporteVo.trabRevisados" id="trabRevisadosSegCorr" maxlength="5" size="7" 
								onkeyup="validaRangoEntero(this,'0','9999');$('form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr').html('');" 
								onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpte()"/> 
							<div id="labelTrabRevisadosSegCorr" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>		
							<td align="left" class="etiqueta2" width="10%">
								<!--   <span class="required">* </span> -->
								<label>Suerte Principal Autodeterminada</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="consolidaImporteVo.suertePpalDetCOP" id="suertePpalDetCopSegCorr" maxlength="13" size="18"
								  onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetCopSegCorr','formRecepcionSeguimiento');validaRangoDouble(this,'0','999999999');$('form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr').html('');$('form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr').html('');" 
								  onblur="moneyMask(this,2);calculaSuertePpalPentPago(null)" 
								  
								/>
								<div align="right" class="etiqueta2" id="labelSuertePpalDetCopSegCorr"></div>
							</td>
							<td align="left"  width="14%">			
								<form:input path="consolidaImporteVo.suertePpalDetRCV" id="suertePpalDetRcvSegCorr" maxlength="13" size="18"
								onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetRcvSegCorr','formRecepcionSeguimiento');validaRangoDouble(this,'0','999999999');$('form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr').html('');$('form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr').html('');"
								onblur="moneyMask(this,2);calculaSuertePpalPentPago(null)"
								
								 />
								<div id="labelSuertePpalDetRcvSegCorr" class="etiqueta2"></div>
							</td>										
						</tr>
						<tr class="par">
							<td align="right" class="etiqueta2" width="10%">
								<span class="required" id="spanReqTRabRegulaSegCorr" style="display:none;">*</span><label>% Regularizado</label>
							</td> 
							
							<td align="left" width="10%" >
								<form:input path="consolidaImporteVo.porcRegularizado" id="porcentajeRegularizadoSegCorr" size="5" maxlength="3"
									onkeyup="validaCampo('PermiteSoloNumeros','porcentajeRegularizadoSegCorr','formRecepcionSeguimiento');validaPorcentajeConsolImpte('porcentajeRegularizadoSegCorr','labelPorcRegularizadoSegCor')" />
									<div id="labelPorcRegularizadoSegCor" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trabajadores Omisos (NSS Unicos) </label>
							</td>
							<td  align="left" width="15%">
								<form:input  id="trabOmisosUniSegCorr" path="consolidaImporteVo.trabOmisos" maxlength="5" size="7" 
								 onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpte()" onkeyup="validaRangoEntero(this,'0','9999');$('form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr').html('');"/>
								<div id="labelTrabOmisosUniSegCorr" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label >Suerte Principal Pagado</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="consolidaImporteVo.suertePrincipalCOP" id="suertePrincipalCopSegCorr" disabled="disabled" readonly="true"/>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.suertePrincipalRCV" id="suertePrincipalRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="left" class="etiqueta2" width="10%">
								
							</td>
							<td align="left" width="10%">
								
							</td> 
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								<span class="required">* </span><label>Trabajadores Subdeclarados (NSS unicos) </label>
							</td>
							<td  align="left"  width="15%">
								<form:input  id="trabSubdeclaUniSegCorr" path="consolidaImporteVo.trabSubdeclarados"  maxlength="5" size="7" 
								 onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpte()" onkeyup="validaRangoEntero(this,'0','9999');$('form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr').html('');"	/>
								<div id="labelTrabSubdeclaUniSegCorr" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Actualizaci&oacute;n</label>
							</td>
							<td align="left"  width="14%">
								<form:input path="consolidaImporteVo.actualizacionCOP" id="actualizacionCopSegCorr" disabled="disabled" readonly="true"/>
							</td>	
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.actualizacionRCV" id="actualizacionRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>	
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="10%"><span
												class="required">*</span>
								<label>N&uacute;mero de Parcialidades </label>
							</td>
							<td align="left"  width="10">
								<form:input path="consolidaImporteVo.numParcialidades" id="numeroParcialidadesSegCorr" size="4" maxlength="2" 
								onkeyup="validaCampo('PermiteSoloNumeros','numeroParcialidadesSegCorr','formRecepcionSeguimiento')" onblur="validaParcialidadesSegCorr()"/>
								<div id="labelNumParcialidiSegCorr" class="etiqueta2"></div>
							</td>
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td> 
							
							<td  align="left" class="etiqueta2" width="15%">
								<label>Trabajadores Regularizados </label>
							</td>
							<td  align="left" width="15%">
								<form:input  id="trabRegularizadosRegObraSegCorr" path="consolidaImporteVo.trabRegularizados" readonly="true" disabled="disabled" 
								onchange="moneyMask(this,0)"/>
								<div id="labelTrabRegularizadosRegObraSegCorr"></div>
								
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Recargos</label>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.recargosCOP" id="recargosCopSegCorr" disabled="disabled" readonly="true"/>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.recargosRCV" id="recargosRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="20%" colspan="2">
								<form:checkbox path="comprobanteConvenio" id="cbxComprobConvenio" />
								<label>Comprobante de Convenio</label>
							</td>
							<!-- td align="left"  width="10%" class="etiqueta2">
								
							</td -->
							<td align="left" class="etiqueta2" width="5%">
								&nbsp;&nbsp;
							</td> 
							
							<td  align="left" class="etiqueta2" width="15%">
								<!--  span class="required">* </span><label>Base determinada </label -->
							</td>
							<td  align="left" width="15%">
								<!--  form:input  id="baseDeterminadaSegCorr" path="consolidaImporteVo.baseDeterminada" maxlength="11" size="13" 
								onkeyup="moneyMask(this,0);$('form#formRecepcionSeguimiento #labelBaseDeterminadaSegCorr').html('');" />
								<div id="labelBaseDeterminadaSegCorr"></div -->
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								<label>Multas : </label>
							</td>
							<td align="left"  width="14%">
								<form:input path="consolidaImporteVo.multasCOP" id="multasCopSegCorr" disabled="disabled" readonly="true"/>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.multasRCV" id="multasRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="10%">
									&nbsp;
							</td>
							<td align="left" width="10%">
								
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
								<label>Total Pagado : </label>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.totalPagadoCOP" id="totalPagadoCopSegCorr" disabled="disabled" readonly="true"/>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.totalPagadoRCV" id="totalPagadoRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="15%" colspan="3">
									Fecha de Recepci&oacute;n :
							</td>
							
						
							
							<td  align="left" width="15%">
								<form:input path="fechaPresentacionCorr" id="fecPresentCorrecConsol" size="12" readonly="true"/>
							</td>
							<td  align="left" class="etiqueta2" width="15%">
								&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								
							<label>Suerte Principal Pendiente Pago</label></td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.suertePpalPentPagoCOP" id="suertePpalPenPagoCopSegCorr" readonly="true" disabled="disabled"/>
							</td>
							<td align="left" width="14%">
								<form:input path="consolidaImporteVo.suertePpalPentPagoRCV" id="suertePpalPenPagoRcvSegCorr" readonly="true" disabled="disabled"/>
							</td>		
					</tr>
					<tr class="par">
							<td align="right" class="etiqueta2" width="15%" colspan="3">
									Estatus de la Presentaci&oacute;n de la Correcci&oacute;n :
							</td>
														
							<td  align="left"  width="45%" colspan="4">
								<form:input path="estatusPresentacionCorr" id="estatusPresentaCorrecConsolida" size="80" readonly="true" />
							</td>
							
							<td align="left" class="etiqueta2" width="2%">
								&nbsp;&nbsp;
							</td>
							<td align="left" class="etiqueta2" width="10%">
								
							</td>
								
					</tr>
					<tr valign="top" class="par">
						<td align="right" class="etiqueta2" width="10%">
							
						</td>
						<td align="left"  width="10%" >
							
						</td>
						<td align="left" width="5%" >&nbsp;</td>
				    	<td align="left" colspan="6">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
						<td align="left" width="10%" colspan="7" >&nbsp;</td>
				    	<!-- <td align="center" colspan="5" >
							<input type="button" value="Guardar" id="btnGuardarRegularizaObra" width="10px" height="12px" class="boton" onclick="procesaFormularioRegObraGenerico(validaCamposRegularizaObra())" >
						</td> -->
						<td align="center" colspan="3" >
							<input type="button" value="Datos de la Regularizaci&oacute;n" id="btnDatosRegularizacionSegCorr" width="9px" height="9px" class="boton" onclick="openDialogoPagosAutodetermina()" >
						</td>
					</tr>
					<tr valign="top" class="par">
						<td align="left" colspan="9">&nbsp;</td>
					</tr>
				</tbody>
			</table>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/recepcion/recepcionSeguimiento.js"></script>
<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/cedulaRevision/cedulaRevision.js"></script>
<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet" >

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="formCedRevAudCorrSeguimiento" name="formCedRevAudCorrSeguimiento" method="get" >
			<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
				<input type="hidden" id="idPresentaCoor">
						<fieldset>
							<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								<tr>
									<td align="center" colspan="6">Conceptos</td>
								</tr>
							</thead>
							<tbody>
								<tr class="impar">
									<td align="left" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par" valign="top">
									<td colspan="1">
										<div id="wrapperCheckPresuntivo"><input  type="checkbox" id="checkPresuntivo"  readonly="readonly" >&nbsp;&nbsp;<label id="lbFecha"  class="etiqueta2">Presuntivo</label></div>						
									</td>		
									<td align="left" colspan="5">&nbsp;</td>						
								</tr>
								
								
								<tr class="par" valign="top">
									<td colspan="1">
										<label id="lbFecha"  class="etiqueta2"> 
											Fecha de Aplicaci&oacute;n de C&eacute;dula:
										</label>						
									</td>
									<td colspan="5">
										<input  id="fechaAplicaCedula" readonly="readonly" />	
									</td>								
								</tr>
								
								
								
								
								<tr class="par" valign="top">
									<td>
										<label id="lbEjerCedRevAud"  class="etiqueta2"> 
											Ejercicio:
										</label>						
									</td>
									<td>
										<select id="numEjercicio">
											<option value="-1">--Por Favor Seleccione--</option>
										</select>
									</td>
									<td>
										<label id="lbRegPatrCedRevAud"  class="etiqueta2">
											Registro Patronal:
										</label>
									</td>
									<td>
										<select id="regPatronal">
											<option value="-1">--Por Favor Seleccione--</option>
										</select>
									</td>
								</tr>		
								<tr class="impar">
									<td align="right" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par" valign="top">
									<td colspan="2">						
									</td>
									
									<td>
										<label id="lbPatronCedRevAud"  class="etiqueta2">
											Patr&oacute;n:
										</label>
									</td>
									<td>
										<label id="lbIMSSCedRevAud"  class="etiqueta2">
											IMSS:
										</label>
									</td>
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbBaseCotImssCedRevAud"  class="etiqueta2" > 
											Base de Cotizaci&oacute;n del IMSS:
										</label>						
									</td>	
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="baseCotPatron"  maxlength="16" readonly="readonly"  class="inputMoney" />					
									</td>
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="baseCotImss" onclick="edit(this);" maxlength="16" onkeyup="validaCampo('PermiteSoloNumerosYPunto','baseCotImss','formCedRevAudCorrSeguimiento');" onblur="moneyMask(this,2);validaPresicionImportes(this);" class="inputMoney"/>					
									</td>	
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbDifeCotImssCedRevAud"  class="etiqueta2" > 
											Diferencias en Base de Cotizaci&oacute;n Pagada en la Correcci&oacute;n (1% Guarder&iacute;as)(A):
										</label>						
									</td>	
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="difeBaseCotiPatron"  maxlength="20" readonly="readonly" class="inputMoney"  />					
									</td>
									<td>
										<label class="etiqueta2" >$</label><input value="0.0"  id="difeBaseCotiIMSS" onclick="edit(this);" maxlength="20"  onkeyup="validaCampo('PermiteSoloNumerosYPunto','difeBaseCotiIMSS','formCedRevAudCorrSeguimiento');" onblur="moneyMask(this,2);validaPresicionImportes(this);" class="inputMoney" />					
									</td>	
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbTotalConcep"  class="etiqueta2" > 
											Total:
										</label>						
									</td>	
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" readonly="readonly" id="totalConceptoPatron" class="inputMoney" />					
									</td>
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" readonly="readonly" id="totalConceptoImss"  class="inputMoney" />					
									</td>	
								</tr>							
							</tbody>			
						</table>	
						
						<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								
							</thead>
							<tbody>	
							<tr>
								<td align="center" >
									<div>
										<table id="dtRubrosAB" style="width: 900px">
											<thead></thead>
											<tbody></tbody>
										</table>
									</div>
								</td>
								</tr>
							</tbody>
						</table>	
						
						 <table style="width: 900px" align="center" class="tablaverde2" border="0">	
						 	<thead>
								<tr  valign="top">
									<td colspan="2">
										<label id="lbFolCorr"  > 
											Concepto
										</label>						
									</td>	
									<td>
										<label id="lbFolCorr"   > 
											Total Importe Aclarado
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total Importe por Aclarar
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total Importes
										</label>	
									</td>
								</tr>
							</thead>					
							<tbody>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbFolCorr" for="inRecFolioCorr" class="etiqueta2" > 
											Total
										</label>						
									</td>	
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="valTotImporteAclarado" readonly="readonly" class="inputMoney"  />
									</td>
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="valTotImportePorAclarar" readonly="readonly"  class="inputMoney" />
									</td>
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="valTotImporte" readonly="readonly"  class="inputMoney" />
									</td>
								</tr>
							
							</tbody>
						</table>
						
						
						<table style="width: 900px" align="center" class="tablaverde2" border="0">	
									
							<tbody>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbTotImportAcla" class="etiqueta2" > 
											Total Importe Por Aclarar (C)
										</label>						
									</td>	
									<td>
										<label class="etiqueta2" >$</label><input value="0.0" id="valTotImportAcla" readonly="readonly"  class="inputMoney" />
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbPorcRaz"  class="etiqueta2" > 
											Porcentaje de Razonabilidad: (C) entre (A)
										</label>						
									</td>	
									<td>
										<input value="0.0" id="valPorcRaz" readonly="readonly"  class="inputMoney" />
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbDifDet"  class="etiqueta2" > 
											Las diferencias determinadas son:
										</label>						
									</td>	
									<td>
										<input value="0.0" id="valDifDet" readonly="readonly"  class="inputMoney" />
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td colspan="3"  >
										<label id="lbObserv"  class="etiqueta2" > 
											Observaciones:
										</label>						
									</td>	
																		
								</tr>
								<tr class="par" valign="top">
									<td colspan="3" >
										<textarea rows="4" cols="75" id="txAreaObserva" onKeyDown="limitText(this,250);" ></textarea>
									</td>								
								</tr>
							
							</tbody>
						</table>
						
						
						
						
						
						<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								<tr>
									<td align="center" colspan="6">Administraci&oacute;n de Conceptos</td>
								</tr>
							</thead>
							<tbody>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbFolCorr" for="inRecFolioCorr" class="etiqueta2" > 
											Seleccionar Concepto
										</label>						
									</td>	
									<td>
										<select id="percepcionesList">
											<option value="-1"  >--Por Favor Seleccione--</option>
										</select>
									</td>
									<td>
									</td>
								</tr>
								<tr class="par" valign="top">
									<td colspan="2">
																
									</td>	
									<td>
										<input  id="percepcionNueva" onkeyup="limpiaCombo()" maxlength="50" />	
									</td>
									<td>
										<input type="button" value="Agregar Listado" id="btnAgregarListadoRubros" width="9px" height="9px" class="boton"  >
									</td>
								</tr>
							</tbody>
						</table>
						<table style="width: 900px" align="center" class="tablaverde2"  border="0" >
							<thead>
								<tr>
									<td align="center" colspan="6">Consolidaci&oacute;n de Importes por Aclarar (Total RP's y Total Ejercicios)</td>
								</tr>
							</thead>
							<tbody>	
								<tr class="par" valign="top">
									<td>
										<table id="dtTotalBdDif" style="width: 900px">
												<thead></thead>
												<tbody></tbody>
										</table>
									</td>
								</tr>							
							</tbody>
						</table>
						
				<div id="btnGuardarValidacionSeguimiento" align="center" style="">					
					<input type="button" value="Guardar" id="btnGuardarRevision" width="9px" height="9px" class="boton" >&nbsp;&nbsp;
					<input type="button" value="Finalizar" id="btnFinalizaCedularRevision" width="9px" height="9px" class="boton" >	
				</div>
					
				</fieldset>		
			</form:form>
		</td>
	</tr>
</table>

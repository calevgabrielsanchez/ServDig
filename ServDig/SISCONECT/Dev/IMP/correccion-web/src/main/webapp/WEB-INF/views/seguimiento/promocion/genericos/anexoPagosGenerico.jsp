<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	    
<div id="dgAnexoPagosGenerico"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogAnexoPagosGenerico" style="background-color: #f2fff2;">	
		<form:form id="anexoPagosGenericoForm" name="anexoPagosGenericoForm" modelAttribute="seguimientoGenericoVO" action="seguimiento/generico/guardarPagoDet.do">
			<form:hidden path="cvePromocion" id="cvePromocion"/>
			<form:hidden  path="segRegularizaObraVo.cveRegulaPagos" id="cveRegulaPagosGral"/>
			<form:hidden path="anexoPagosVO.cveRegulaPago" id="cveRegulaPago" />
			<table  style="width:1000px" align="center" >
				<tr valign="middle">
					<td align="center" width="100%">
						<table class="tablaverde2" style="width: 1100px"  >
							<thead>
							<tr >
								<td colspan="2" width="20%">Datos Generales</td>
								<td colspan="2" width="20%"><form:checkbox path="anexoPagosVO.seccionCOP" id="seccionCOP" onclick="seleccionaCOP()"/> COP</td>
								<td colspan="2" width="20%"><form:checkbox path="anexoPagosVO.seccionRCV" id="seccionRCV" onclick="seleccionRCV()"/>RCV</td>
								<td colspan="2" width="30%">Movimientos Afiliatorios</td>
								<td colspan="2" width="10%"></td>
							</tr>
						
							</thead>
							<tbody >
								<tr valign="top" class="impar">
							    	<td colspan="2" width="20%"> &nbsp; </td>
									<td colspan="2" width="20%">&nbsp;</td>
									<td colspan="2" width="20%"><div id="labelTipoPagoCOPoRCV"></div>&nbsp;</td>
									<td colspan="2" width="30%">&nbsp;</td>
									<td colspan="2" width="10%">&nbsp;</td>
								</tr>
								<tr class="par">
							  		<td align="left" class="etiqueta2" width="10%" >
							  			<label>Folio Sua : </label>
							  		</td>
							  		<td align="left" width="10%"  >
							  			<form:input path="anexoPagosVO.folioSUA"  id="folioSUAPagos" size="8" maxlength="6"  
							  			onkeyup="validaCampo('PermiteSoloNumeros','folioSUAPagos','anexoPagosGenericoForm')" onblur="completaAccionSUA()"  
							  			 onkeypress="limpiaOrdenIngreso()"/>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			Periodo :
							  		</td>
							  		<td align="left" width="10%" >
							  			<span class="required">*</span>
							  			<form:select path="anexoPagosVO.periodoCOP" id="periodoCOPGen" disabled="disabled" onchange="jsLimpiaLabelPeriodoCOPGen()" >
							  			</form:select>
							  			<div id="labelPeriodoCOPGen"></div>
							  		</td>
							  		<td align="left" width="10%"  class="etiqueta2">
							  			<label>Periodo :</label>	
							  		</td>
							  		<td align="left" width="10%"  >
							  			<span class="required">*</span>
							  			<form:select path="anexoPagosVO.periodoRCV" id="periodoRCVGen" disabled="disabled" onchange="jsLimpiaLabelperiodoRCVGen()" >
							  			</form:select>
							  			<div id="labelperiodoRCVGen"></div>
							  		</td>
							  		<td align="left" width="30%" colspan="2" class="etiqueta2">
							  			
							  		</td>
							  		<td align="left" width="10%" colspan="2" class="etiqueta2">
							  			
							  		</td>
							  		
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Orden de Ingreso : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.ordenIngreso"  id="ordenIngreso" maxlength="11"   size="13" onkeyup="validaCampo('PermiteSoloNumeros','ordenIngreso','anexoPagosGenericoForm')"
							  			onkeypress="limpiaFolioSUA()" onblur="validaMinimoOrdenIng()"/>
							  			<div id="labelOrdenIngresoGen"></div>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			<span class="required">*</span><label>SP</label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.spCOP"  id="spCOP" disabled="disabled" readonly="true" maxlength="12" size="15" 
							  			 onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','spCOP','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');jsLimpiaLabelSpCOP()"
							  			onblur="validaRangoDouble(this,'0','999999999');moneyMask(this,2);calculaTotalCop()"/>
							  			<div id="labelSpCOP"></div>
							  		</td>
							  		<td align="left" width="10%"  class="etiqueta2">
							  			<span class="required">*</span><label>SP</label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.spRCV"  id="spRCV" disabled="disabled" readonly="true" maxlength="12" size="15" 
										onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','spRCV','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');jsLimpiaLabelSpRCV()"
							  			onblur="moneyMask(this,2);calculaTotalRcv()" />
							  			<div id="labelSpRCV"></div>
							  		</td>
							  		<td align="left" width="15%"  class="etiqueta2">
							  		   <span class="required" id="spanReqTRabRegula">*</span>
							  			<label>Trab Regularizados</label>
							  		</td>
							  		<td align="left" width="15%" >
							  			<form:input path="anexoPagosVO.trabRegularizados"  id="trabRegularizados" maxlength="5" size="7" 
							  			onkeyup="moneyMask(this,0);jsBorraLabelTrabRegula()"  
							  			 onblur="completaTrabRegulariza()"/>
							  			<div id="labelTrabRegularizados"></div>
							  		</td>
							  		
							  		<td align="left" width="10%" colspan="2" >
							  			<input type="button" value="Guardar" id="btnGuardarPagosGenericos" width="9px" height="9px" class="boton" onclick="procesaFormularioPagos()"/>
							  		</td>
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Num. de Credito : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.numCredito"  id="numCredito" maxlength="11" size="13"   onkeyup="validaCampo('PermiteSoloNumeros','numCredito','anexoPagosGenericoForm')"/>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Act</label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.actCOP"  id="actCOP" disabled="disabled" readonly="true" maxlength="12" size="15" onblur="moneyMask(this,2);calculaTotalCop()"
							  			onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','actCOP','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			 /> 
							  			
							  		</td>
							  		<td align="left" width="10%"  class="etiqueta2">
							  			<label>Act</label>
							  		</td>
							  		<td align="left" width="10%"  >
							  			<form:input path="anexoPagosVO.actRCV"  id="actRCV" disabled="disabled" readonly="true" maxlength="12" size="15" 
                               onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','actRCV','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			onblur="moneyMask(this,2);calculaTotalRcv()" 
							  			/>
							  		</td>
							  		<td align="left" width="15%"  class="etiqueta2">
							  			<span class="required" id="spnAltasPagos">*</span>
							  			<label>Altas</label>
							  		</td>
							  		<td align="left" width="15%"  >
							  			<form:input path="anexoPagosVO.altas"  id="altasPagos" size="7" maxlength="5"  
							  			onkeyup="moneyMask(this,0);jsBorraLabelAltas()"
							  			/>
							  			<div id="labelAltasPagos"></div>
							  		</td>
							  		
							  		<td align="left" width="10%" colspan="2" >
							  			<input type="button" value="Eliminar" id="btnEliminarPagosGenericos" width="9px" height="9px" class="boton" onclick="eliminaPagoDet()"/>
							  		</td>
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="10%">
							  			<span class="required">*</span><label>Fecha de Pago : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.fechaPagoGenerico"  id="fechaPagoGenerico" readonly="true"  disabled="disabled"
							  			onchange="jsLimpiaLabelFecPagoGen()"
							  			/>
							  			 <div id="labelFechaPagoGenerico"></div>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Rec</label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.recCOP"  id="recCOP" disabled="disabled" readonly="true" maxlength="12" size="15" 
							  			onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','recCOP','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			onblur="moneyMask(this,2);calculaTotalCop()"/>
							  		</td>
							  		<td align="left" width="10%"  class="etiqueta2">
							  			<label>Rec</label>
							  		</td>
							  		<td align="left" width="10%"  >
							  			<form:input path="anexoPagosVO.recRCV"  id="recRCV" disabled="disabled" readonly="true" maxlength="12" size="15" 
										onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','recRCV','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			onblur="moneyMask(this,2);calculaTotalRcv()" />
							  		</td>
							  		<td align="left" width="15%"  class="etiqueta2">
							  			<span class="required" id="spnReqBajasPagos">*</span>
							  			<label>Bajas</label>
							  		</td>
							  		<td align="left" width="15%"  >
							  			<form:input path="anexoPagosVO.bajas"  id="bajasPagos" maxlength="5" size="7"  
							  			onkeyup="moneyMask(this,0);jsBorraLabelBajas()"
							  			/>
							  			<div id="labelBajasPagos"></div>
							  		</td>
							  		<td align="left" width="10%"  class="etiqueta2" colspan="2">
							  			
							  		</td>
							  		
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="10%">
							  			<span class="required">*</span><label>Tipo Docto : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:select path="anexoPagosVO.tipoDocto"  id="tipoDoctoPagos" onchange="jsLimpiaLabelTipoDocto()" >
								  			<form:option value="-1" label="<-----Seleccione un Origen----->" />
								  			<form:option value="58" label="Docto 58" />
								  			<form:option value="53" label="Docto 53" />
							  			</form:select> 
							  			<div id="labelTipoDocto"></div>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Total : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.totalCOP"  id="totalCOP" readonly="true" disabled="disabled"/>
							  		</td>
							  		<td align="left" width="10%" class="etiqueta2">
							  			<label>Total</label>
							  		</td>
							  		<td align="left" width="10%"  >
							  			<form:input path="anexoPagosVO.totalRCV"  id="totalRCV" readonly="true" disabled="disabled"/>
							  		</td>
							  		<td align="left" width="15%" class="etiqueta2">
							  			<label>Modif. Salario</label>
							  		</td>
							  		<td align="left" width="15%"  >
							  			<form:input path="anexoPagosVO.modifSalario"  id="modifSalario" maxlength="5" size="7" 
							  			onchange="moneyMask(this,0)"/>
							  		</td>
					  		
							  		<td align="left" width="10%" colspan="2" >
							  			<input type="button" value="Cancelar" id="btnCancelarPagosGenericos" width="9px" height="9px" class="boton" onclick="limpiarPagoDetalle()"/>
							  		</td>
							  	</tr>
				  				
				  				
								<tr class="par">
									<td align="left" class="etiqueta2" width="10%">
							  			&nbsp;
							  		</td>
							  		<td align="left" width="10%">
							  			<label id="saticb"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="10%">
							  			<label>Multas : </label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.multasCOP"  id="multasCOP" disabled="disabled" readonly="true" maxlength="12" size="15"
										onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','multasCOP','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			onblur="moneyMask(this,2)"
							  			/>
							  		</td>
							  		<td align="left" width="10%" class="etiqueta2">
							  			<label>Multas</label>
							  		</td>
							  		<td align="left" width="10%" >
							  			<form:input path="anexoPagosVO.multasRCV"  id="multasRCV" disabled="disabled" readonly="true" maxlength="12" size="15"
										onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','multasRCV','anexoPagosGenericoForm');validaRangoDouble(this,'0','999999999');"
							  			onblur="moneyMask(this,2)"
							  			 />
							  		</td>
							  		
							  		<td align="left" class="etiqueta2" width="30%" colspan="2">
										
							  		</td>
							  		<td align="left"  width="10%" colspan="2">
										<input type="button" value="Salir" id="btnSalirPagosGenericos" width="9px" height="9px" class="boton" onclick="salir()"/>
							  		</td>
							  	</tr>
							  	
								<tr class="par">
									<td align="left" class="etiqueta2" width="100%" colspan="10">
										&nbsp;
							  		</td>
							  	</tr>
							  	<tr class="par">
									<td align="left" class="etiqueta2" width="100%" colspan="10">
										&nbsp;	
							  		</td>
							  	</tr>
							  	<tr class="par">
									<td align="left" class="etiqueta2" width="100%" colspan="10">
									<div id="divAnexoPagoDetalleAux" style="overflow: auto; width:1200px;"  align="center">
										<div id="divAnexoPagoDetalle" style="background-color: white !important; width: 2100px;" align="center">
											
											<table id="dtAnexoPagosGenDetalle"  style="width: 2100px" height="200px" align="center" >
											</table>
											<table id="totalesDT"  style="width: 2000px" align="center"  >
											<!--  thead -->
											<tr class="impar">
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">Num. Pagos</td>
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">SP COP</td>
											<td colspan="1" width="5%">Act COP</td>
											<td colspan="1" width="5%">Rec COP</td>
											<td colspan="1" width="5%">Total COP</td>
											<td colspan="1" width="5%">Multas COP</td>
											<td colspan="1" width="5%">&nbsp;</td>
											<td colspan="1" width="5%">SP RCV</td>
											<td colspan="1" width="5%">Act RCV</td>
											<td colspan="1" width="5%">Rec RCV</td>
											<td colspan="1" width="5%">Total RCV</td>
											<td colspan="1" width="5%">Multas RCV</td>
											<td colspan="1" width="5%">Trab. Regularizados</td>
											<td colspan="1" width="5%">Altas</td>
											<td colspan="1" width="5%">Bajas</td>
											<td colspan="1" width="5%">Modif. Salario</td>
											</tr>
											<!--  /thead -->
											<tr id="totalesPagos"></tr>
											<!--   tfoot id="totalesPagos"></tfoot -->
											<!--   tbody id="totalesPagos22" ></tbody -->
											</table>
										</div>
									</div>
							  		</td>
							  	</tr>	
							  	
							  	
								<tr valign="top" class="impar">
							    	<td align="left" colspan="10">&nbsp;</td>
								</tr>
							</tbody>
						</table>									
					</td>
				</tr>
			</table>							
		</form:form>
		</div>	
	</div>
			
			
											
							  				
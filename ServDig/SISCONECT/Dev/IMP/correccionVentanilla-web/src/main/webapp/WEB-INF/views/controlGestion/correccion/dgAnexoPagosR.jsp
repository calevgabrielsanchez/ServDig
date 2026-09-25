<div id="dgAnexoPagosR"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formPagosR" >
<table id="dtAnexoPagosR"  style="width: 980px" >
	
			 <thead>
                   <tr>
                   				<th colspan="1" rowspan="2"></th>
                   				<th colspan="1" rowspan="2">ID</th>
                   				<th colspan="1" rowspan="2">RP</th>
                                <th colspan="2" rowspan="1">Folio</th>
                                <th colspan="1" rowspan="2">N&uacute;mero de Credito</th>
                                <th colspan="1" rowspan="2">Fecha de Pago</th>
                                <th colspan="6" rowspan="1">COP</th>
                                <th colspan="6" rowspan="1">RCV</th>
                   </tr>
                   <tr>
                                <th colspan="1" rowspan="1">SUA</th>
                                <th colspan="1" rowspan="1">Orden Ingreso</th>
                                
                                <th colspan="1" rowspan="1">Periodo</th>
                                <th colspan="1" rowspan="1">SP</th>
                                <th colspan="1" rowspan="1">Act</th>
                                <th colspan="1" rowspan="1">Rec</th>
                                <th colspan="1" rowspan="1">Total</th>
                                <th colspan="1" rowspan="1">Multas</th>


								<th colspan="1" rowspan="1">Periodo</th>
								<th colspan="1" rowspan="1">SP</th>
                                <th colspan="1" rowspan="1">Act</th>
                                <th colspan="1" rowspan="1">Rec</th>
                                <th colspan="1" rowspan="1">Total</th>
                                <th colspan="1" rowspan="1">Multas</th>
                                
                   </tr>            
              </thead>
                <tfoot>
               <tr>
                                <td colspan="1" rowspan="1" ></td>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
								<th colspan="1" rowspan="1" ></th>
								<th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                                <th colspan="1" rowspan="1" ></th>
                             
                                                                
                   </tr>          
             
                     
            </tfoot>
	</table>
	
	<table align="center">
		<tr >
			<td valign="top">
				<table border ="1" width="180px">
					<tr>
						<td colspan="2" align="center">Datos Generales</td>
					</tr>
					<tr>
						<td>RP</td> 
						<td><select id="regpatR" name="regpatR" class="red">
						   <option value="">--Por favor seleccione--</option>
					    	</select><label for="regpatR" >  </label>
					    </td>
					</tr>
					<tr>
						<td>Concepto</td> 
						<td><select id="conceptoR" name="conceptoR" onchange="validaConceptoR();" class="red" >
    						<option value="">Seleccione una Opción</option>
    						<option value="COP">COP</option>
    						<option value="RCV">RCV</option>
    						<option value="COPRCV">COP y RCV</option>
    						<option value="MultaCOP">Multa COP</option>
    						<option value="MultaRCV">Multa RCV</option>
    						<option value="MultaCOPRCV">Multa COP y RCV</option>
  						</select><label for="conceptoR" >  </label></td>
					</tr>
					<tr>
						<td>Folio SUA</td> 
						<td><input type="text" size="20" maxlength="6" style="text-align: center;" id="txtFolioSUAR" name="txtFolioSUAR" onkeypress="validarFolioSUAR(event, 'txtFolioSUAR', 'txtOrdenIngresoR');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="validaFolioSUAR( 'txtFolioSUAR', 'txtOrdenIngresoR');" class="red"><label for="txtFolioSUAR" >  </label></td>
					</tr>
					<tr>
						<td>Orden de Ingreso</td> 
						<td><input type="text" size="20" maxlength="15" style="text-align: center;" id="txtOrdenIngresoR" class="red" name="txtOrdenIngresoR" onchange="validaFolioSUAR('txtOrdenIngresoR', 'txtFolioSUAR'); validarCamposOns('txtOrdenIngresoR', 'txtNoCreditoR');" onkeypress="validarFolioSUAR(event,  'txtOrdenIngresoR', 'txtFolioSUAR'); validarCamposOn(event,  'txtOrdenIngresoR', 'txtNoCreditoR')"><label for="txtOrdenIngresoR" >  </label></td>
					</tr>
					<tr>
						<td >No. Cr&eacute;dito</td> 
						<td><input type="text" size="20" maxlength="15" style="text-align: center;" disabled="disabled" id="txtNoCreditoR" name="txtNoCreditoR" onkeypress="return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"><label for="txtNoCreditoR" >  </label></td>
					</tr>
					<tr>
						<td>Fecha de Pago</td> 
						<td><input type="text" size="20" maxlength="15" style="text-align: center;" id="fechaPagoAnexoPagosR" name="fechaPagoAnexoPagosR" class="red" onchange="validafechaSistema('fechaPagoAnexoPagosR')"><label for="fechaPagoAnexoPagosR" >  </label></td>
					</tr>
					
					
				
				</table>
				
			</td>
			<td valign="top">
				<table border ="1" width="180px">
					<tr>
						<td colspan="2" align="center">COP</td>
					</tr>
					<tr>
						<td>Per&iacute;odo</td> 
						<td><select id="periodoCOPR" name="periodoCOPR">
					    <option value="">--Por favor seleccione--</option>
					    </select><label for="periodoCOPR" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled"  id="txtCOPSPR" name="txtCOPSPR" onkeypress="sumaR(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('COP')">
						<label for="txtCOPSPR" >  </label></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled" id="txtCOPActR" name="txtCOPActR" onkeypress="sumaR(event, 'COP'); return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('COP')"><label for="txtCOPActR" >  </label></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled" id="txtCOPRecR" name="txtCOPRecR" onkeypress="sumaR(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('COP')"><label for="txtCOPRecR" >  </label></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="12"  readonly="readonly" id="txtCOPTotalR" name="txtCOPTotalR" na onkeypress="$('txtCOPTotalR').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" >
						<label for="txtCOPTotalR" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="12" disabled="disabled" id="txtCOPMultasR" name="txtCOPMultasR" onchange="$('txtCOPMultasR').formatCurrency();"  onkeypress="$('txtCOPMultasR').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" > <label for="txtCOPMultasR"> </label> </td>
					</tr>
				</table>
				
			</td>
		<td valign="top">
				<table border ="1" width="1kpx">
					<tr>
						<td colspan="2" align="center">RCV</td>
					</tr>
					<tr>
						<td>Per&iacute;odo</td> 
						<td><select id="periodoRCVR" name="periodoRCVR">
					   <option value="">--Por favor seleccione--</option>
					   </select><label for="periodoRCVR" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="12" maxlength="12" disabled="disabled" id="txtRCVSPR" onkeypress="sumaR(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('RCV')" name="txtRCVSP"></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="12" maxlength="12" disabled="disabled" id="txtRCVActR" onkeypress="sumaR(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('RCV')"></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="12" maxlength="12"" disabled="disabled" id="txtRCVRecR" onkeypress="sumaR(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumarR('RCV')"></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="12"  readonly="readonly" id="txtRCVTotalR" name="txtRCVTotalR" onchange="$('txtRCVTotal').formatCurrency();">
						<label for="txtRCVTotal" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="12"  disabled="disabled" id="txtRCVMultasR" name="txtRCVMultasR" onchange="$('txtRCVMultas').formatCurrency();" onkeypress="$('txtRCVMultas').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');$('txtRCVMultasR').formatCurrency();"> <label for="txtRCVMultasR"> </label> </td>
					</tr>
				</table>
				
			</td>
			<td valign="top">
				<table border ="1" width="100px">
					<tr>
						<td colspan="1"><input type="button" value="Eliminar" size="100px" id="btnEliminar" width="40px" onclick="eliminaAnexoPagosR();"></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Guardar" size="100px" id="btnGuardarEleR" name="btnGuardarEleR" width="40px" /></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Cancelar" size="100px" id="btnCancelarEles" width="40px" onclick="cancelarDgPagos('formPagosR');" ></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Cerrar" size="100px" id="btnSalirEles" width="40px" onclick="salirAnexoPagosR();" ></td>
					</tr>
					
				</table>
				
			</td>
		<tr>
	
	</table>
	 </form>
</div>




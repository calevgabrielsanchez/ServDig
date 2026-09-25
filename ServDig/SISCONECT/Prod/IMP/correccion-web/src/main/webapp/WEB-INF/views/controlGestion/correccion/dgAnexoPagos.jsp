<div id="dgAnexoPagos"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formPagos" >
<table id="dtAnexoPagos"  style="width: 980px" >
	
			 <thead>
                   <tr>
                   				<th colspan="1" rowspan="2"></th>
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
						<td><select id="regpat" name="regpat" class="red">
						   <option value="">--Por favor seleccione--</option>
					    	</select><label for="regpat" >  </label>
					    </td>
					</tr>
					<tr>
						<td>Concepto</td> 
						<td><select id="concepto" name="concepto" onchange="validaConcepto();" class="red" >
    						<option value="">Seleccione una Opción</option>
    						<option value="COP">COP</option>
    						<option value="RCV">RCV</option>
    						<option value="COPRCV">COP y RCV</option>
    						<option value="MultaCOP">Multa COP</option>
    						<option value="MultaRCV">Multa RCV</option>
    						<option value="MultaCOPRCV">Multa COP y RCV</option>
  						</select><label for="concepto" >  </label></td>
					</tr>
					<tr>
						<td>Folio SUA</td> 
						<td><input type="text" size="20" maxlength="6" style="text-align: center;" id="txtFolioSUA" name="txtFolioSUA" onkeypress="validarFolioSUA(event, 'txtFolioSUA', 'txtOrdenIngreso');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="validaFolioSUA( 'txtFolioSUA', 'txtOrdenIngreso');" class="red"><label for="txtFolioSUA" >  </label></td>
					</tr>
					<tr>
						<td>Orden de Ingreso</td> 
						<td><input type="text" size="20" maxlength="15" style="text-align: center;" id="txtOrdenIngreso" class="red" name="txtOrdenIngreso" onchange="validaFolioSUA('txtOrdenIngreso', 'txtFolioSUA'); validarCamposOns('txtOrdenIngreso', 'txtNoCredito');" onkeypress="validarFolioSUA(event,  'txtOrdenIngreso', 'txtFolioSUA'); validarCamposOn(event,  'txtOrdenIngreso', 'txtNoCredito')"><label for="txtOrdenIngreso" >  </label></td>
					</tr>
					<tr>
						<td >No. Cr&eacute;dito</td> 
						<td><input type="text" size="20" maxlength="15" style="text-align: center;" disabled="disabled" id="txtNoCredito" name="txtNoCredito" onkeypress="return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"><label for="txtNoCredito" >  </label></td>
					</tr>
					<tr>
						<td>Fecha de Pago</td> 
						<td><input type="text" size="20" style="text-align: center" id="fechaPagoAnexoPagos" name="fechaPagoAnexoPagos" class="red" onchange="validafechaSistema('fechaPagoAnexoPagos')"><label for="fechaPagoAnexoPagos" >  </label></td>
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
						<td><select id="periodoCOP" name="periodoCOP">
					    <option value="">--Por favor seleccione--</option>
					    </select><label for="periodoCOP" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled"  id="txtCOPSP" name="txtCOPSP" onkeypress="suma(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP')">
						<label for="txtCOPSP" >  </label></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled" id="txtCOPAct" onkeypress="suma(event, 'COP'); return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP')"><label for="txtCOPAct" >  </label></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="12" maxlength="12"  disabled="disabled" id="txtCOPRec" onkeypress="suma(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP')"><label for="txtCOPRec" >  </label></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="12"  readonly="readonly" id="txtCOPTotal" name="txtCOPTotal" onkeypress="$('txtCOPTotal').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" >
						<label for="txtCOPTotal" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="12" disabled="disabled" id="txtCOPMultas" onchange="$('txtCOPMultas').formatCurrency();"  onkeypress="$('txtCOPMultas').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" > <label for="txtCOPMultas" > </label></td>
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
						<td><select id="periodoRCV" name="periodoRCV">
					   <option value="">--Por favor seleccione--</option>
					   </select><label for="periodoRCV" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="12" maxlength="12" disabled="disabled" id="txtRCVSP" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')" name="txtRCVSP"></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="12" maxlength="12" disabled="disabled" id="txtRCVAct" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')"></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="12" maxlength="12"" disabled="disabled" id="txtRCVRec" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')"></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="12"  readonly="readonly" id="txtRCVTotal" name="txtRCVTotal" onchange="$('txtRCVTotal').formatCurrency();">
						<label for="txtRCVTotal" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="12"  disabled="disabled" id="txtRCVMultas" name="txtRCVMultas" onchange="$('txtRCVMultas').formatCurrency();" onkeypress="$('txtRCVMultas').formatCurrency();return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');$('txtRCVMultas').formatCurrency();"> <label for="txtRCVMultas"> </label></td>
					</tr>
				</table>
				
			</td>
			<td valign="top">
				<table border ="1" width="100px">
					<tr>
						<td colspan="1"><input type="button" value="Eliminar" size="100px" id="btnEliminar" width="40px" onclick="eliminaAnexoPagos();"></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Guardar" size="100px" id="btnGuardarEle" name="btnGuardarEle" width="40px" /></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Cancelar" size="100px" id="btnCancelarEles" width="40px" onclick="cancelarDgPagos('formPagos');" ></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Salir" size="100px" id="btnSalirEles" width="40px" onclick="salirAnexoPagos();" ></td>
					</tr>
					
				</table>
				
			</td>
		<tr>
	
	</table>
	 </form>
</div>




<div id="dgPromocionAnexoPagos"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formPagos" >
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
  						</select><label for="concepto" >  </label></td>
					</tr>
					<tr>
						<td>Folio SUA</td> 
						<td><input type="text" size="10" maxlength="20" id="txtFolioSUA" class="red" name="txtFolioSUA" onkeypress="validarFolioSUA(event, 'txtFolioSUA', 'txtOrdenIngreso');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="validaFolioSUA('txtFolioSUA', 'txtOrdenIngreso')"><label for="txtFolioSUA" >  </label></td>
					</tr>
					<tr>
						<td>Orden de Ingreso</td> 
						<td><input type="text" size="10" maxlength="20" id="txtOrdenIngreso" class="red" name="txtOrdenIngreso" onkeypress="validarFolioSUA(event,  'txtOrdenIngreso', 'txtFolioSUA'); validarCamposOn(event,  'txtOrdenIngreso', 'txtNoCredito');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="validaFolioSUA('txtOrdenIngreso', 'txtFolioSUA');validarCamposOns('txtOrdenIngreso', 'txtNoCredito')"><label for="txtOrdenIngreso" >  </label></td>
					</tr>
					<tr>
						<td >No. Cr&eacute;dito</td> 
						<td><input type="text" size="10" maxlength="20" disabled="disabled" id="txtNoCredito" name="txtNoCredito"><label for="txtNoCredito" onkeypress="return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" >  </label></td>
					</tr>
					<tr>
						<td>Fecha de Pago</td> 
						<td><input type="text" size="10" id="fechaPagoAnexoPagos" name="fechaPagoAnexoPagos" onchange="validafechaSistema('fechaPagoAnexoPagos')" class="red" ><label for="fechaPagoAnexoPagos" >  </label></td>
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
						<td><select id="periodoCOP" name="periodoCOP" class="red">
					    <option value="">--Por favor seleccione--</option>
					    </select><label for="periodoCOP" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="20" maxlength="15"  disabled="disabled" id="txtCOPSP" name="txtCOPSP" onkeypress="suma(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP');">
						<label for="txtCOPSP" >  </label></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="20" maxlength="15"  disabled="disabled" id="txtCOPAct" onkeypress="suma(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP');"><label for="txtCOPAct" >  </label></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="20" maxlength="15"  disabled="disabled" id="txtCOPRec" onkeypress="suma(event, 'COP');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('COP');"><label for="txtCOPRec" >  </label></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="20"  readonly="readonly" id="txtCOPTotal" name="txtCOPTotal" >
						<label for="txtCOPTotal" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="15" disabled="disabled" id="txtCOPMultas"></td>
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
						<td><select id="periodoRCV" name="periodoRCV" class="red">
					   <option value="">--Por favor seleccione--</option>
					   </select><label for="periodoRCV" >  </label>
					   </td>
					</tr>
					<tr>
						<td>SP</td> 
						<td><input type="text" size="20" maxlength="15" disabled="disabled" id="txtRCVSP" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')" name="txtRCVSP"></td>
					</tr>
					<tr>
						<td>Act</td> 
						<td><input type="text" size="20" maxlength="15" disabled="disabled" id="txtRCVAct" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')"></td>
					</tr>
					<tr>
						<td>Rec</td> 
						<td><input type="text" size="20" maxlength="15" disabled="disabled" id="txtRCVRec" onkeypress="suma(event, 'RCV');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="sumar('RCV')"></td>
					</tr>
					<tr>
						<td>Total</td> 
						<td><input type="text" size="20"  readonly="readonly" id="txtRCVTotal" name="txtRCVTotal">
						<label for="txtRCVTotal" >  </label>
						</td>
					</tr>
					<tr>
						<td>Multas</td> 
						<td><input type="text" size="15"  disabled="disabled" id="txtRCVMultas"></td>
					</tr>
				</table>
				
			</td>
			<td valign="top">
				<table border ="1" width="100px">
					<tr>
						<td colspan="1"><input type="button" value="Eliminar" size="100px" id="btnEliminar" width="40px" onclick="eliminaAnexoPagos();"></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Guardar" size="100px" id="btnGuardarEles" width="40px" onclick="guardaAnexoPagos();" ></td>
					</tr>
					<tr>
						<td colspan="1"><input type="button" value="Cerrar" size="100px" id="btnSalirEles" width="40px" onclick="salirAnexoPagos();" ></td>
					</tr>
					
					
				</table>
				
			</td>
		<tr>
	
	</table>
<table id="dtPromocion"  style="width: 980px" >
	
			 <thead>
                   <tr>
                   				
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
	</table>
	
	
	 </form>
</div>




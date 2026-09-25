<div id="dgPromocionRegularizacion" 
	style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">	
	<form id="CrtRegulapagosdetForm" action="">
	<input type="hidden" id="cveRegulapagos" name="crtRegulapagos.cveRegulapagos">
	<input type="hidden" id="cveRegulapagosdet" name="cveRegulapagosdet">
	<input type="hidden" id="idPagoCaratula" name="idPagoCaratula">
	<input type="hidden" id="bandera" name="bandera">	
	
	<table align="center">
		<tr >
			<td valign="top">
				<table style="border: 1px solid #999;margin: 0 auto 1em auto; width: 290px">
					<thead>
						<tr>
							<th colspan="2" align="center" class="titulo">Datos Generales</th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<td class="filaPar">RP</td> 
						<td class="filaPar"><select id="regpat" name="regpat">
						   <option value="">--Por favor seleccione--</option>
					    	</select>
					    	<div id="regpatError"></div>
					    </td>					    
					</tr>
					<tr>
						<td class="filaImpar">Concepto</td> 
						<td class="filaImpar"><select id="idConcepto" name="idConcepto" >
    						<option value="">Seleccione una Opción</option>
    						<option value="0">COP</option>
    						<option value="1">RCV</option>
    						<option value="2">COP y RCV</option>
  						</select><div id="idConceptoError"></div>
					</tr>
					<tr>
						<td class="filaPar">Folio SUA</td> 
						<td class="filaPar"><input type="text" size="10" maxlength="6" id="numFoliosua" name="numFoliosua"
						onkeyup="validaCampo('noCaracteresEspeciales','numFoliosua','CrtRegulapagosdetForm');validaCampo('PermiteSoloNumeros','numFoliosua','CrtRegulapagosdetForm')">
						<div id="numFoliosuaError"></div></td>
					</tr>
					<tr>
						<td class="filaImpar">Orden de Ingreso</td> 
						<td class="filaImpar"><input type="text" size="10" maxlength="10" id="numOrdeningreso" name="numOrdeningreso" disabled="disabled"
						onkeyup="validaCampo('noCaracteresEspeciales','numOrdeningreso','CrtRegulapagosdetForm');validaCampo('PermiteSoloNumeros','numOrdeningreso','CrtRegulapagosdetForm')">
						
					</tr>
					<tr>
						<td class="filaPar">No. Cr&eacute;dito</td> 
						<td class="filaPar"><input type="text" size="10" maxlength="18" disabled="disabled" id="numCredito" name="numCredito"
						onkeyup="validaCampo('noCaracteresEspeciales','numCredito','CrtRegulapagosdetForm');validaCampo('PermiteSoloNumeros','numCredito','CrtRegulapagosdetForm')">
						<div id="numCreditoError"></div></td>
					</tr>
					<tr>
						<td class="filaImpar">Fecha de Pago</td> 
						<td class="filaImpar"><input type="text" size="10" id="fechaPago" name="fechaPago" readonly="readonly"
						onchange="validafechaSistema('CrtRegulapagosdetForm','fechaPago','Fecha de Pago')">
						<div id="fechaPagoError"></div></td>
					</tr>
					</tbody>
					
				
				</table>
				
			</td>
			<td valign="top">
				<table style="border: 1px solid #999;margin: 0 auto 1em auto; width: 210px">
					<thead>
						<tr>
							<th colspan="2" class="titulo">COP</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="filaPar">Per&iacute;odo</td> 
							<td  class="filaPar"><select id="numPeriodoCop" name="numPeriodoCop" disabled="disabled">
						    <option value="">--Por favor seleccione--</option>
						    </select><div id="numPeriodoCopError"></div>
						   </td>
						</tr>
						<tr>
							<td class="filaImpar">SP</td> 
							<td class="filaImpar"><input type="text" size="12" maxlength="8"  disabled="disabled" id="impCopsp" name="impCopsp" onblur="copTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCopsp','CrtRegulapagosdetForm')">
							<div id="impCopspError"></div></td>
						</tr>
						<tr>
							<td class="filaPar">Act</td> 
							<td class="filaPar"><input type="text" size="12" maxlength="8"  disabled="disabled" id="impCopact" name="impCopact" onblur="copTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCopact','CrtRegulapagosdetForm')">
							<div id="impCopactError"></div></td>
						</tr>
						<tr>
							<td class="filaImpar">Rec</td> 
							<td class="filaImpar"><input type="text" size="12" maxlength="8"  disabled="disabled" id="impCoprec" name="impCoprec" onblur="copTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCoprec','CrtRegulapagosdetForm')">
							<div id="impCoprecError"></div></td>
						</tr>
						<tr>
							<td class="filaPar">Total</td> 
							<td class="filaPar"><input type="text" size="12"  readonly="readonly" id="txtCOPTotal">
							<label for="txtCOPTotal" >  </label>
							</td>
						</tr>
						<tr>
							<td class="filaImpar">Multas</td> 
							<td class="filaImpar"><input type="text" size="8" disabled="disabled" id="impMultasCop" name="impMultasCop"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impMultasCop','CrtRegulapagosdetForm')">
							<div id="impMultasCopError"></div></td>
					</tr>
					</tbody>
				</table>
				
			</td>
		<td valign="top">
				<table style="border: 1px solid #999;margin: 0 auto 1em auto; width: 210px">
					<thead>
						<tr>
							<th colspan="2" class="titulo">RCV</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="filaPar">Per&iacute;odo</td> 
							<td class="filaPar"><select id="numPeriodoRcv" name="numPeriodoRcv" disabled="disabled">
						   <option>--Por favor seleccione--</option>
						   </select><div id="numPeriodoRcvError"></div>
						   </td>
						</tr>
						<tr>
							<td class="filaImpar">SP</td> 
							<td class="filaImpar"><input type="text" size="12" maxlength="8" disabled="disabled" id="impRcvsp" name="impRcvsp" onblur="rcvTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impRcvsp','CrtRegulapagosdetForm')">
							<div id="impRcvspError"></div></td>
						</tr>
						<tr>
							<td class="filaPar">Act</td> 
							<td class="filaPar"><input type="text" size="12" maxlength="8" disabled="disabled" id="impRcvact" name="impRcvact" onblur="rcvTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impRcvact','CrtRegulapagosdetForm')">
							<div id="impRcvactError"></div></td>
						</tr>
						<tr>
							<td class="filaImpar">Rec</td> 
							<td class="filaImpar"><input type="text" size="12" maxlength="8" disabled="disabled" id="impRcvrec" name="impRcvrec" onblur="rcvTotal()"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impRcvrec','CrtRegulapagosdetForm')">
							<div id="impRcvrecError"></div></td>
						</tr>
						<tr>
							<td class="filaPar">Total</td> 
							<td class="filaPar"><input type="text" size="12"  readonly="readonly" id="txtRCVTotal">							
							</td>
						</tr>
						<tr>
							<td class="filaImpar">Multas</td> 
							<td class="filaImpar"><input type="text" size="12" maxlength="8" disabled="disabled" id="impMultasRcv" name="impMultasRcv"
							onkeyup="validaCampo('PermiteSoloNumerosYPunto','impMultasRcv','CrtRegulapagosdetForm')">
							<div id="impMultasRcvError"></div></td>
						</tr>
					</tbody>
				</table>
				
			</td>
			<td valign="top">
				<table width="100px">
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>					
					<tr>
						<td colspan="1">
							<a href="#"	onclick="javascript:eliminar();"><span class="boton">Eliminar</span></a>
						</td>						
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<a href="#"	onclick="javascript:guardaPago();"><span class="boton">Guardar</span></a>
						</td>
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="1">
							<a href="#"	onclick="javascript:cancelar();"><span class="boton">Cancelar</span></a>
						</td>
					</tr>
					<tr>
						<td colspan="1">&nbsp;</td>
					</tr>					
				</table>
				
			</td>
		<tr>
	
	</table>
	</form>
	<table id="dtRegularizacion" style="width: 980px">
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
</div>
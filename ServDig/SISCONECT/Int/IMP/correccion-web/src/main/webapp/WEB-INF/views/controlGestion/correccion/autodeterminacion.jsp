 <%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
 <table class="tablaverde2" >
			 <tr>
			 <td>
			 <table>
			 <tr class="par">
			 	<td class="etiqueta2">OI</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAOI" class="etiqueta2" name="dtpAOI" onfocus="setMensaje('dtpAOI')" disabled="disabled" onkeypress="validar(event, 'dtpAOI')" onchange="if(validaFechasCGCorre('dtpAOI', 'dtpGPeriodoAl', 'dtpFechaSistema')==true){validar(event, 'dtpAOI');inhabilitar(event, 'dtpAOI');}"><label for="dtpAOI" > </label></td>
			 	<td class="etiqueta2">No. Oficio</td>
			 	<td><input type="text" size="15" maxlength="11" class="etiqueta2" id="txtANoOficioOI" name="txtANoOficioOI" onfocus="setMensaje('txtANoOficioOI')" onkeypress="validar(event, 'txtANoOficioOI');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" disabled="disabled" onchange="buscaElemento('txtANoOficioOI');"></td>
			 	<td class="etiqueta2">OIN</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAOIN" class="etiqueta2" name="dtpAOIN" onfocus="setMensaje('dtpAOIN')" disabled="disabled" onkeypress="validar(event, 'dtpAOIN')" onchange="if(validaFechasCGCorre('dtpAOIN', 'dtpAOI', 'dtpFechaSistema')==true){validar(event, 'dtpAOIN');inhabilitar(event, 'dtpAOIN');}"><label for="dtpAOIN" > </label></td>
			 </tr>
			 <tr class="impar">
			 	<td class="etiqueta2">SP</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpASP" class="etiqueta2" name="dtpASP" onfocus="setMensaje('dtpASP')" disabled="disabled" onkeypress="validar(event, 'dtpASP')" onchange="if(validaFechasCGCorre('dtpASP', 'dtpGPeriodoAl,dtpAOIN', 'dtpFechaSistema')==true){validar(event, 'dtpASP');inhabilitar(event, 'dtpASP');}"><label for="dtpASP" > </label></td>
			 	<td class="etiqueta2">P</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAP" class="etiqueta2" name="dtpAP" onfocus="setMensaje('dtpAP')" disabled="disabled" onkeypress="validar(event, 'dtpAP')" onchange="if(validaFechasCGCorre('dtpAP', 'dtpASA', 'dtpPFechaMax')==true){validar(event, 'dtpAP');inhabilitar(event, 'dtpAP');}"><label for="dtpAP" > </label></td>
			 	<td></td>
			 	<td></td>
			 	
			 </tr>
			 <tr class="par">
			 	<td class="etiqueta2">SA</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpASA" class="etiqueta2" name="dtpASA" onfocus="setMensaje('dtpASA')" disabled="disabled"  onkeypress="validar(event, 'dtpASA')" onchange="if(validaFechasCGCorre('dtpASA', 'dtpASP', 'dtpFechaSistema')==true){validar(event, 'dtpASA');inhabilitar(event, 'dtpASA');verificaNivel('dtpASA');}"><label for="dtpASA" > </label></td>
			 	<td></td>
			 	<td></td>
			 	<td></td>
			 	<td></td>
			 </tr>
			 <tr class="impar">
			 	<td class="etiqueta2">SR</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpASR" class="etiqueta2" name="dtpASR" onfocus="setMensaje('dtpASR')" disabled="disabled" onkeypress="validar(event, 'dtpASR')" onchange="if(validaFechasCGCorre('dtpASR', 'dtpASP', 'dtpFechaSistema')==true){validar(event, 'dtpASR');inhabilitar(event, 'dtpASR');verificaNivel('dtpASR');llenaMotivosRechazos('cbxAMotivoRechazo')}"><label for="dtpASR" > </label></td>
			 	
			 	<td colspan="2" class="etiqueta2">Motivo de rechazo</td>
			 	<td align="center" width="5%" colspan="2"><combo:creaCombo  disabled="disabled"
  								id="cbxAMotivoRechazo" entidad="mx.gob.imss.ctirss.correccion.model.CgcCatMotivoRechazo" 
  								idHtml="cbxAMotivoRechazo"
  								idHtmlContenedor="formCorre" style="width:180px; font-family: Verdana;	font-size: 9px;	color: #000000;	letter-spacing : 0px;	line-height : 12px;	font-weight: bold;"  
  								onchange="buscaElemento('cbxAMotivoRechazo');" /></td>
  			
			 	
			 </tr>
			 <tr class="par">
			 	<td class="etiqueta2">APP</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAAPP" class="etiqueta2" name="dtpAAPP" onfocus="setMensaje('dtpAAPP')" disabled="disabled" onkeypress="validar(event, 'dtpAAPP')" onchange="if(validaFechasCGCorre('dtpAAPP', 'dtpASA', 'dtpFechaSistema')==true){validar(event, 'dtpAAPP');inhabilitar(event, 'dtpAAPP');verificaNivel('dtpAAPP');minFechaACR();}"><label for="dtpAAPP" > </label></td>
			 	<td class="etiqueta2">ACP</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAACP" class="etiqueta2" name="dtpAACP" onfocus="setMensaje('dtpAACP')" disabled="disabled" onkeypress="validar(event, 'dtpAACP')" onchange="if(validaFechasCGCorre('dtpAACP', 'dtpASA', 'dtpFechaSistema')==true){validar(event, 'dtpAACP');inhabilitar(event, 'dtpAACP');verificaNivel('dtpAACP');minFechaACR();}"><label for="dtpAACP" > </label></td>
			 	<td class="etiqueta2">ASR</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpAASR" class="etiqueta2" name="dtpAASR" onfocus="setMensaje('dtpAASR')" disabled="disabled" onkeypress="validar(event, 'dtpAASR')" onchange="if(validaFechasCGCorre('dtpAASR', 'dtpASA', 'dtpFechaSistema')==true){validar(event, 'dtpAASR');inhabilitar(event, 'dtpAASR');verificaNivel('dtpAASR');minFechaACR();}"><label for="dtpAASR" > </label></td>
			 </tr>
			 <tr >
			 	<td colspan="6" style="vertical-align: top">
			 		<table style="width: 100%; vertical-align: top" >
			 			<tr>
			 				<td style="vertical-align: top">
			 					<table>
			 						<tr class="par">
			 							<td class="etiqueta2">
			 								No. Convenio
			 							</td>
			 							<td>
			 								<input type="text" class="etiqueta2" style="text-align: center;" id="txtANoConvenio" name="txtANoConvenio" size="15" maxlength="12" disabled="disabled" onfocus="setMensaje('txtANoConvenio')" disabled="disabled" onkeypress="validar(event, 'txtANoConvenio'); return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtANoConvenio');buscaElemento('txtANoConvenio')"/><label for="txtANoConvenio" > </label>
			 							</td>
			 						</tr>
			 						<tr class="impar">
			 							<td class="etiqueta2">
			 								No. Parcialidades
			 							</td>
			 							<td>										 
			 								<input type="text" class="etiqueta2" style="text-align: center;" id="txtANoParcialidades" name="txtANoParcialidades" size="15" maxlength="2" disabled="disabled" onfocus="setMensaje('txtANoParcialidades')" disabled="disabled" onkeypress="validar(event, 'txtANoParcialidades');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtANoParcialidades');buscaElemento('txtANoParcialidades');"/><label for="txtANoParcialidades" > </label>
			 							</td>
			 						</tr>
			 						
			 					</table>
			 				</td>
			 				<td style="text-align: center">
			 					<table style="text-align: center">
			 						<tr class="par">
			 							<td class="etiqueta2">Trab. Revisados</td>
			 							<td><input type="text" class="etiqueta2" id="txtATrabRevisados" name="txtATrabRevisados" size="10" disabled="disabled" onkeypress="return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"/></td>
			 							</tr>
			 						<tr class="impar"><td class="etiqueta2">Trab. Omisos</td>
			 							<td><input type="text" class="etiqueta2" id="txtATrabOmisos" name="txtATrabOmisos" size="10" disabled="disabled" onkeypress="sumaTrabajadoresAutodeterminacion(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"/></td></tr>
			 						<tr class="par"><td class="etiqueta2">Trab. Subdeclarados</td>
			 							<td><input type="text" class="etiqueta2" id="txtATrabSubdeclarados" onchange="sumarTrabajadoresAuto();" name="txtATrabSubdeclarados" size="10" disabled="disabled" onkeypress="sumaTrabajadoresAutodeterminacion(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"/></td></tr>
			 						<tr class="impar"><td class="etiqueta2">Trab. Regularizados</td>
			 							<td><input type="text" class="etiqueta2" id="txtATrabRegularizados" onchange="sumarTrabajadoresAuto();" name="txtATrabRegularizados" size="10" disabled="disabled" /></td></tr>
			 						<tr class="par"><td colspan="2"><input type="button" class="etiqueta2" value="Trabajadores" onclick="openDgTrabajadores('A');" id="cmdATrabajadores" name="cmdATrabajadores" disabled="disabled"/><td></tr>
			 					</table>
			 				</td>
			 			</tr>
			 		</table>
			 		</td>
			 		</table>
			 	</td>
			 	<td valign="top">
			 		<table style="width: 100%" >
			 			<tr class="par">
			 				<td></td>
			 				<td class="etiqueta2" style="text-align: center">COP</td>
			 				<td class="etiqueta2" style="text-align: center">RCV</td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">SP</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPPagSP" name="txtACOPPagSP" size="20" disabled="disabled" onchange="$('txtACOPPagSP').formatCurrency();"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVPagSP" name="txtARCVPagSP" size="20" disabled="disabled" onchange="$('txtARCVPagSP').formatCurrency();"/></td>
			 			</tr>
			 		<tr class="par">
			 				<td class="etiqueta2">Act</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPPagAct" name="txtACOPPagAct" size="20" disabled="disabled"  onchange="$('txtACOPPagAct').formatCurrency();"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVPagAct" name="txtARCVPagAct" size="20" disabled="disabled"  onchange="$('txtARCVPagAct').formatCurrency();"/></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">Rec</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPPagRec" name="txtACOPPagRec" size="20" disabled="disabled"  onchange="$('txtACOPPagRec').formatCurrency();"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVPagRec" name="txtARCVPagRec" size="20" disabled="disabled"  onchange="$('txtARCVPagRec').formatCurrency();"/></td>
			 			</tr>
			 			<tr class="par">
			 				<td class="etiqueta2">Mul</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPPagMul" name="txtACOPPagMul" size="20" disabled="disabled" onchange="$('txtACOPPagMul').formatCurrency();"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVPagMul" name="txtARCVPagMul" size="20" disabled="disabled" onchange="$('txtARCVPagMul').formatCurrency();"/></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">TP</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPPagTotal" name="txtACOPPagTotal" size="20" disabled="disabled"  onchange="$('txtACOPPagTotal').formatCurrency();"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVPagTotal" name="txtARCVPagTotal" size="20" disabled="disabled" onchange="$('txtACOPPagTotal').formatCurrency();"/></td>
			 			</tr>
			 			<tr class="par">
			 			<td>
			 				<div id="spc" style="visibility: visible;vertical-align: middle;" class="etiqueta2">
			 					SPC
			 				</div>
							<div id="spa" style="visibility: hidden;vertical-align: middle;" class="etiqueta2">
			 					SPA
			 				</div>
						</td>	
			 				<td style="text-align: center"><input type="text" style="text-align: right;" onfocus="setMensaje('txtACOPConvSP')" class="etiqueta2" id="txtACOPConvSP" name="txtACOPConvSP" size="20" disabled="disabled" onkeypress="return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="totalAPagarAutodetermacion()" /></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" onfocus="setMensaje('txtARCVConvSP')" class="etiqueta2" id="txtARCVConvSP" name="txtARCVConvSP" size="20" disabled="disabled" onkeypress="return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="totalAPagarAutodetermacion()" /></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">SPP</td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtACOPxPagSP" name="txtACOPxPagSP" size="20" disabled="disabled"/></td>
			 				<td style="text-align: center"><input type="text" style="text-align: right;" class="etiqueta2" id="txtARCVxPagSP" name="txtARCVxPagSP" size="20" disabled="disabled"/></td>
			 			</tr>
			 			<tr>
			 				<td></td>
			 				<td style="text-align: center"><input type="button" class="etiqueta2" value="C. Omitidos" name="cmdAConceptos" id="cmdAConceptos" onclick="openDgAgregarConceptos();" disabled="disabled"/></td>
			 				<td style="text-align: center"><input type="button" class="etiqueta2" value="Agregar Pago" name="cmdAPagos" id="cmdAPagos" onclick="openDgAgregarPagos();" disabled="disabled"/></td>
			 			</tr>
			 		</table>
			 	</td>
			 </tr>
			 </table>
<table class="tablaverde2">
			 <tr>
			 <td>
			 <table>
			 <tr class="par">
			 	<td class="etiqueta2">ACR</td>
			 <td><input type="text" style="text-align: center;" size="15" id="dtpRCR" class="etiqueta2" name="dtpRCR" onfocus="setMensaje('dtpRCR')" disabled="disabled" onkeypress="validar(event, 'dtpRCR')" onchange="validar(event, 'dtpRCR');inhabilitar(event, 'dtpRCR');verificaNivel('dtpRCR');"><label for="dtpRCR" > </label></td>
			 	<td class="etiqueta2">Porc. Ced. de Raz.</td>
			 	<td><input type="text" class="etiqueta2" id="txtRPorcCR" name="txtRPorcCR" size="15" disabled="disabled" onfocus="setMensaje('txtRPorcCR')" onkeypress="validar(event, 'txtRPorcCR');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtRPorcCR'); buscaElemento('txtRPorcCR');"/>	</td>
			 	<td class="etiqueta2">CCPR</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRCCPR" class="etiqueta2" name="dtpRCCPR" onfocus="setMensaje('dtpRCCPR')" disabled="disabled" onkeypress="validar(event, 'dtpRCCPR')" onchange="if(validaFechasCGCorre('dtpRCCPR', 'dtpRCR', 'dtpFechaSistema')==true){validar(event, 'dtpRCCPR');inhabilitar(event, 'dtpRCCPR');verificaNivel('dtpRCCPR');}"><label for="dtpRCCPR" > </label></td>
			 </tr>
			 <tr class="impar">
			 	<td class="etiqueta2">RDE</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRRDE" class="etiqueta2" name="dtpRRDE" onfocus="setMensaje('dtpRRDE')" disabled="disabled" onkeypress="validar(event, 'dtpRRDE')" onchange="if(validaFechasCGCorre('dtpRRDE', 'dtpRCR', 'dtpFechaSistema')==true){verificaNivel('dtpRRDE');validar(event, 'dtpRRDE');inhabilitar(event, 'dtpRRDE');}"><label for="dtpRRDE" > </label></td>
			 	<td class="etiqueta2">No. Oficio</td>
			 	<td><input type="text" class="etiqueta2" id="txtRNoOficioRDE" name="txtRNoOficioRDE" size="15" disabled="disabled" onfocus="setMensaje('txtRNoOficioRDE')" onkeypress="validar(event, 'txtRNoOficioRDE');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtRNoOficioRDE'); buscaElemento('txtRNoOficioRDE');"/>	</td>
			 	<td class="etiqueta2">RDN</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRRDN" class="etiqueta2" name="dtpRRDN" onfocus="setMensaje('dtpRRDN')" disabled="disabled" onkeypress="validar(event, 'dtpRRDN')" onchange="if(validaFechasCGCorre('dtpRRDN', 'dtpRRDE', 'dtpFechaSistema')==true){validar(event, 'dtpRRDN');inhabilitar(event, 'dtpRRDN');verificaNivel('dtpRRDN');}"><label for="dtpRRDN" > </label></td>
			 	
			 </tr>
			 <tr class="par">
			 	<td class="etiqueta2">OD</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpROD" class="etiqueta2" name="dtpROD" onfocus="setMensaje('dtpROD')" disabled="disabled" onkeypress="validar(event, 'dtpROD')" onchange="if(validaFechasCGCorre('dtpROD', 'dtpRCR,dtpRRDN', 'dtpFechaSistema')==true){validar(event, 'dtpROD');inhabilitar(event, 'dtpROD');verificaNivelAdicional('dtpROD', 'dtpRVDA');}"><label for="dtpROD" > </label></td>
			 	<td class="etiqueta2">No. Oficio</td>	
			 	<td><input type="text" size="15" class="etiqueta2"   id="txtRNoOficioOD" name="txtRNoOficioOD" onfocus="setMensaje('txtRNoOficioOD')" onkeypress="validar(event, 'txtRNoOficioOD')" onchange="inhabilitar(event, 'txtRNoOficioOD'); buscaElemento('txtRNoOficioOD');" disabled="disabled"></td>
			 	<td class="etiqueta2">NOD</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRNOD" class="etiqueta2" name="dtpRNOD" onfocus="setMensaje('dtpRNOD')" disabled="disabled" onkeypress="validar(event, 'dtpRNOD')" onchange="if(validaFechasCGCorre('dtpRNOD', 'dtpROD', 'dtpFechaSistema')==true){validar(event, 'dtpRNOD');inhabilitar(event, 'dtpRNOD');}" > </label></td>
			 </tr>
			 <tr class="impar">
			 	<td class="etiqueta2">DC</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRDC" class="etiqueta2" name="dtpRDC" onfocus="setMensaje('dtpRDC')" disabled="disabled" onkeypress="validar(event, 'dtpRDC')" onfocus="setMensaje('dtpRDC')" disabled="disabled" onkeypress="validar(event, 'dtpRDC')" onchange="if(validaFechasCGCorre('dtpRDC', 'dtpRNOD', 'dtpFechaSistema')==true){validar(event, 'dtpRDC');inhabilitar(event, 'dtpRDC');verificaNivel('dtpRDC');}"><label for="dtpRDC" > </label></td>
			 	<td></td>
			 	<td></td>
			 	<td></td>
			 	<td></td>
			 </tr>
			 <tr class="par">
			 	<td class="etiqueta2">VPP</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRVPP" class="etiqueta2" name="dtpRVPP" onfocus="setMensaje('dtpRVPP')" disabled="disabled" onkeypress="validar(event, 'dtpRVPP')" onchange="if(validaFechasCGCorre('dtpRVPP', 'dtpRNOD', 'dtpFechaSistema')==true){validar(event, 'dtpRVPP');inhabilitar(event, 'dtpRVPP');verificaNivel('dtpRVPP');}"><label for="dtpRVPP" > </label></td>
			 	<td class="etiqueta2">VTC</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRVTC" class="etiqueta2" name="dtpRVTC" onfocus="setMensaje('dtpRVTC')" disabled="disabled" onkeypress="validar(event, 'dtpRVTC')" onchange="if(validaFechasCGCorre('dtpRVTC', 'dtpRNOD', 'dtpFechaSistema')==true){validar(event, 'dtpRVTC');inhabilitar(event, 'dtpRVTC');verificaNivel('dtpRVTC');}" > </label></td>
			 	<td class="etiqueta2">VDA</td>
			 	<td><input type="text" style="text-align: center;" size="15" id="dtpRVDA" class="etiqueta2" name="dtpRVDA" onfocus="setMensaje('dtpRVDA')" disabled="disabled" onkeypress="validar(event, 'dtpRVDA')" onchange="if(validaFechasCGCorre('dtpRVDA', 'dtpRRDN,dtpRNOD', 'dtpFechaSistema')==true){validar(event, 'dtpRVDA');inhabilitar(event, 'dtpRVDA');verificaNivelAdicionalVDA('dtpRVDA', 'dtpROD');verificaNivelAdicionalVDA('dtpRVDA', 'dtpRNOD');verificaNivelAdicionalVDA('dtpRVDA', 'dtpRDC');verificaNivelAdicionalVDA('dtpRVDA', 'dtpRVPP');verificaNivelAdicionalVDA('dtpRVDA', 'dtpRVTC');}" onblur="validaReglasCierre();"><label for="dtpRVDA" > </label></td>
			 </tr>
			 <tr class="impar">
			 	<td colspan="6">
			 		<table>
			 			<tr>
			 				<td>
			 					<table>
			 						<tr>
			 							<td class="etiqueta2">
			 								No. Convenio
			 							</td>
			 							<td>
			 								<input type="text" size="25" maxlength="20" class="etiqueta2" id="txtRNoConvenio" name="txtRNoConvenio" onfocus="setMensaje('txtRNoConvenio')" onkeypress="validar(event, 'txtRNoConvenio');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtRNoConvenio'); buscaElemento('txtRNoConvenio');" disabled="disabled">
			 							</td>
			 						</tr>
			 						<tr>
			 							<td class="etiqueta2">
			 								No. Parcialidades
			 							</td>
			 							<td>
			 								<input type="text" size="10" maxlength="2" class="etiqueta2" id="txtRNoParcialidades" name="txtRNoParcialidades" onfocus="setMensaje('txtRNoParcialidades')" onkeypress="validar(event, 'txtRNoParcialidades');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtRNoParcialidades'); buscaElemento('txtRNoParcialidades');" disabled="disabled">
			 							</td>
			 						</tr>
			 					</table>
			 				</td>
			 				<td>
			 					<table>
			 						<tr>
			 							<td class="etiqueta2">Trab. Revisados</td>
			 							<td><input type="text" class="etiqueta2" id="txtRTrabRevisados" name="txtRTrabRevisados" size="10" disabled="disabled"/></td>
			 							</tr>
			 						<tr><td class="etiqueta2">Trab. Omisos</td>
			 							<td><input type="text" class="etiqueta2" id="txtRTrabOmisos" name="txtRTrabOmisos" size="10" disabled="disabled" onchange="sumarTrabajadoresRevision();" onkeypress="sumaTrabajadoresRevision(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"/></td></tr>
			 						<tr><td class="etiqueta2">Trab. Subdeclarados</td>
			 							<td><input type="text" class="etiqueta2" id="txtRTrabSubdeclarados" name="txtRTrabSubdeclarados" size="10" disabled="disabled" onchange="sumarTrabajadoresRevision();" onkeypress="sumaTrabajadoresRevision(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"/></td></tr>
			 						<tr><td class="etiqueta2">Trab. Regularizados</td>
			 							<td><input type="text" class="etiqueta2" id="txtRTrabRegularizados" name="txtRTrabRegularizados" size="10" disabled="disabled"/></td></tr>
			 						<tr><td colspan="2"><input type="button" class="etiqueta2" value="Trabajadores" id="cmdRTrabajadores" disabled="disabled" onclick="openDgTrabajadores('R');"/><td></tr>
			 					</table>
			 				</td>
			 			</tr>
			 		</table>
			 		</td>
			 		</table>
			 	</td>
			 	<td style="vertical-align: top">
			 		<table>
			 			<tr class="par">
			 				<td></td>
			 				<td class="etiqueta2">COP</td>
			 				<td class="etiqueta2">RCV</td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">SP</td>
			 				<td ><input type="text" size="10" id="txtRCOPPagSP" class="etiqueta2" onfocus="setMensaje('txtRCOPPagSP')" disabled="disabled" onkeypress="validar(event, 'txtRCOPPagSP');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVPagSP" class="etiqueta2" onfocus="setMensaje('txtRRCVPagSP')" disabled="disabled" onkeypress="validar(event, 'txtRRCVPagSP');"></td>
			 			</tr>
			 		<tr class="par">
			 				<td class="etiqueta2">Act</td>
			 				<td ><input type="text" size="10" id="txtRCOPPagAct" class="etiqueta2" onfocus="setMensaje('txtRCOPPagAct')" disabled="disabled" onkeypress="validar(event, 'txtRCOPPagAct');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVPagAct" class="etiqueta2" onfocus="setMensaje('txtRRCVPagAct')" disabled="disabled" onkeypress="validar(event, 'txtRRCVPagAct');"></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">Rec</td>
			 				<td ><input type="text" size="10" id="txtRCOPPagRec" class="etiqueta2" onfocus="setMensaje('txtRCOPPagRec')" disabled="disabled" onkeypress="validar(event, 'txtRCOPPagRec');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVPagRec" class="etiqueta2" onfocus="setMensaje('txtRRCVPagRec')" disabled="disabled" onkeypress="validar(event, 'txtRRCVPagRec');"></td>
			 			</tr>
			 			<tr class="par">
			 				<td class="etiqueta2">Mul</td>
			 				<td ><input type="text" size="10" id="txtRCOPPagMul" class="etiqueta2" onfocus="setMensaje('txtRCOPPagMul')" disabled="disabled" onkeypress="validar(event, 'txtRCOPPagMul');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVPagMul" class="etiqueta2" onfocus="setMensaje('txtRRCVPagMul')" disabled="disabled" onkeypress="validar(event, 'txtRRCVPagMul');"></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">TP</td>
			 				<td ><input type="text" size="10" id="txtRCOPPagTotal" class="etiqueta2" onfocus="setMensaje('txtRCOPPagTotal')" disabled="disabled" onkeypress="validar(event, 'txtRCOPPagTotal');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVPagTotal" class="etiqueta2" onfocus="setMensaje('txtRRCVPagTotal')" disabled="disabled" onkeypress="validar(event, 'txtRRCVPagTotal');"></td>
			 			</tr>
			 			<tr class="par">
			 				<td>
			 				<div id="rspc" style="visibility: visible;vertical-align: middle;" class="etiqueta2">
			 					SPC
			 				</div>
							<div id="spd" style="visibility: hidden;vertical-align: middle;" class="etiqueta2">
			 					SPD
			 				</div>
						</td>	
			 				<td ><input type="text" size="10" id="txtRCOPConvSP" class="etiqueta2" onfocus="setMensaje('txtRCOPConvSP')" disabled="disabled" onkeypress="validar(event, 'txtRCOPConvSP');" onchange="totalAPagarRevision()" ></td>
			 				<td ><input type="text" size="10" id="txtRRCVConvSP" class="etiqueta2" onfocus="setMensaje('txtRRCVConvSP')" disabled="disabled" onkeypress="validar(event, 'txtRRCVConvSP');" onchange="totalAPagarRevision()" ></td>
			 			</tr>
			 			<tr class="impar">
			 				<td class="etiqueta2">SPP</td>
			 				<td ><input type="text" size="10" id="txtRCOPxPagSP" class="etiqueta2" onfocus="setMensaje('txtRCOPxPagSP')" disabled="disabled" onkeypress="validar(event, 'txtRCOPxPagSP');"></td>
			 				<td ><input type="text" size="10" id="txtRRCVxPagSP" class="etiqueta2" onfocus="setMensaje('txtRRCVxPagSP')" disabled="disabled" onkeypress="validar(event, 'txtRRCVxPagSP');"></td>
			 			</tr>
			 			<tr class="par">
			 				<td></td>
			 				<td><input type="button" class="etiqueta2" value="C. Omitidos" name="cmdRConceptos" id="cmdRConceptos" onclick="openDgAgregarConceptosC();" disabled="disabled"/></td>
			 				<td><input type="button" class="etiqueta2" value="Agregar Pago" name="cmdRPagos" id="cmdRPagos" onclick="openDgAgregarPagosR();" disabled="disabled"/></td>
			 			</tr>
			 		</table>
			 	</td>
			 </tr>
			 </table>
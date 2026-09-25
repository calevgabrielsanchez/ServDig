
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/seguimientoTAB.js"></script>

	<table class="tablaverde2">
		<tr>
			<td colspan="4">
			<form id="seguimientoTabForm" >
			
				<table class="tablaverde2" style="width: 800px">		
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">* </span><label>Fecha de notificaci&oacute;n del oficio : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecNotificacionOficio" name="fecNotificacionOficio" onchange="javaScript:jsValidaFecNotif(this.value);complementaPantalla('fecNotificacionOficio','fecAtencionOficio');">
							
							<span class="boton_limpiar" onclick="limpiaFechaNotificacion()" id="spnFecNot" >X</span>
							<div id="labelFecNotif"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Cancelacion del oficio: </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecCancelaOficio" disabled="disabled" >
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<!-- span class="required">* </span -->
							<label>Fecha de atenci&oacute;n del oficio : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecAtencionOficio" name="fecAtencionOficio" onchange="javaScript:jsValidaFechaAtencionOficio(this.value);complementaPantalla('fecAtencionOficio','ee')">
							<span class="boton_limpiar" onclick="limpiaFechaAtencion()" id="spnFecAtn">X</span>
							<div id="labelFecAtencionOficio"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Aut. del aviso de Dict : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecAutDict" disabled="disabled" name="fecAutDict">
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de la solicitud de correcci&oacute;n : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecSol" name="fecSol" disabled="disabled">
						</td>
						<td colspan="2" align="left" class="etiqueta2" width="50%">
							<label>Periodo de correcci&oacute;n</label>
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha oficio invitaci&oacute;n : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="12" readonly="readonly" id="fecOfiInvitacion" disabled="disabled">
						</td>
						<td align="left" colspan="2" width="50%" >
							<label ><b>Del :   </b></label>
							<input size="12" readonly="readonly" id="fecSolCorrIni" disabled="disabled" >
						
							<label><b>  Al :   </b></label>
							<input size="12" readonly="readonly" id="fecSolCorrFin"  disabled="disabled">
							<div id="labelFecCorr"></div>
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Observaciones : </label>
						</td> 
						<td align="left" width="30%">
						
						</td>
						<td align="center" width="50%" colspan="2">
							<!-- input type="button" value="Generar invitacion" id="btnGenInvita" width="10px" height="12px" class="boton" onclick="javascript:jsMuestraInvitacion();" -->
							<input type="button" value="Generar invitacion" id="btnGenInvita" width="10px" height="12px" class="boton" onclick="javascript:muestraInvitacion();" >
							
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
							<textarea rows="5" cols="100" id="observaciones" name="observaciones" onkeyup="valTamTextArea(event,this,200)"></textarea>
						</td> 
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">

								<input type="button" value="Guardar" id="btnGuardarSeg" width="10px" height="12px" class="boton" onclick="onClickBtnGuardarSeg()" >
						</td>
					</tr>
						<tr valign="top" class="par">
				    	<td align="center" colspan="4">
							
						</td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>
	<!-- opcion2 -->
	 	
	
		
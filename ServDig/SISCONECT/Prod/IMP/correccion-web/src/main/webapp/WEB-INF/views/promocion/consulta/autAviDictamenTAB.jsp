<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/autAviDictamenTAB.js"></script>

	<table class="tablaverde2">
		<tr>
			<td colspan="4">
			<form id="autAviDictamenSegForm" >
				<input type="hidden" name="cvePromocion" id="cvePromocion" />
				<input type="hidden" name="bandera" id="bandera" value="autorizaDictamen"/>
				
				<table class="tablaverde2" style="width: 800px">		
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">* </span><label>Fecha de Aut. de aviso de Dic : </label>
						</td> 
						<td align="left" width="30%" colspan="3">
							<input type="text" size="12" readonly="readonly" id="fechaAvisoDictamen" name="fechaAvisoDictamen" onchange="javaScript:jsValidaFecAutAviso(this.value);">
							<span class="boton_limpiar" onclick="limpiaFechasAutDict()" id="spnFecAut">X</span>
							<div id="labelFecAutDictamen"></div>
						</td>						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">* </span><label>N&uacute;mero de aviso de Dictamen : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="20" id="numAvisoDictamen" name="numAvisoDictamen" maxlength="20">
							<!--onkeyup="validaCampo('PermiteSoloNumeros','numAvisoDictamen','autAviDictamenSegForm')" -->
							<div id="labelNumAviso"></div>
						</td>
						<td colspan="2" align="left" class="etiqueta2">
							<label>Periodo a dictaminar</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que registra : </label>
						</td> 
						<td align="left" width="30%">
							<input type="text" size="45" readonly="readonly" disabled="disabled" id="funcionarioReg">
						</td>
						<td align="left" colspan="2" width="50%">
							<span class="required">* </span>
							<label ><b>Del :   </b></label>
							<input size="12" readonly="readonly" id="fechaInicio" name="fechaInicio" onchange="javaScript:jsFlujoAut(this.value)">
							<!-- div id="labelFecInicio"></div -->
							<label><b>  Al :   </b></label>
							<input size="12" readonly="readonly" id="fechaFin" name="fechaFin" onchange="javaScript:jsFlujoAut(this.value)">
							<span class="boton_limpiar" onclick="limpiaFechasPeriodo()" id="spnFecPer">X</span>
							<div id="labelFecFin"></div>
							
						</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">
							<input type="button" value="Guardar" id="btnGuardarAut" width="10px" height="12px" class="boton" onclick="onClickBtnGuardarAut()" >
						</td>
					</tr>
				</table>
				</form>
			</td>
		</tr>
	</table>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form:form method="post" id="formAudAviDicSITAB" modelAttribute="crtInvitacion" action="">	
				
					<form:hidden path="cveInvitacion" id="cveInvitacionAudAviDictSITAB"/>
					<table class="tablaverde2" style="width: 100%">	
						<tr class="impar">
							<td align="left" class="etiqueta2" width="20%" colspan="4">
								&nbsp;
							</td> 						
						</tr>	
						<tr class="par">
							<td align="left" width="20%" class="etiqueta2">
								<span class="required">* </span><label>Fecha de Aut. de aviso de Dict : </label>
							</td> 
							<td align="left" width="30%" >
								<form:input path="fechaAisoDictamenTx" id="fechaAudAviDicSITAB" onchange="validaFechaAutAviDictSITAB()"/>		
								<span class="boton_limpiar" onclick="limpiaFechaAviDictSITAB()" id="btnLimpiaFechaAviDictSITAB" >X</span>						
								<div id="errorFechaAviDicSITAB">													
									<label class="etiquetaError">La fecha no puede ser menor a la fecha de emisi&oacute;n </label>									
								</div>
								<div id="errorFechaNotificaAviDicSITAB">													
									<label class="etiquetaError">La fecha no puede ser menor a la fecha de notificaci&oacute;n </label>									
								</div>
								<div id="reqFechaAviDicSITAB">													
									<label class="etiquetaError">La fecha es requerida</label>									
								</div>
							</td>		
							<td align="left" width="20%" colspan="2" class="etiqueta2">
								<label>Ejercicio a dictaminar</label>
							</td>				
						</tr>
						<tr class="par">
							<td align="left" width="20%" class="etiqueta2">
								<span class="required">* </span><label>N&uacute;mero de Aviso : </label>
							</td> 
							<td align="left" width="30%">
								<form:input path="numAvisoDictamen" size="20" maxlength="12" id="numeroAudAviDicSITAB" onkeyup="validaCampo('noCaracteresEspeciales','numeroAudAviDicSITAB','formAudAviDicSITAB');"/>
								<div id="reqNumeroAudAviDicSITAB">
									<label class="etiquetaError">El n&uacute;mero de aviso es requerido</label>
								</div>
							</td>
							<td colspan="2" align="left">
								<span class="required">* </span>
								<label class="etiqueta2"><b>Del :   </b></label>
								<form:input size="12" path="fechaPeriodoIniDicTx" id="fechaIniAudAviDicSITAB" name="fechaIniAudAviDicSITAB" onchange="validaPerFechasAutAviDicSITAB()" />
								<label class="etiqueta2"><b>  Al :   </b></label>
								<form:input size="12" path="fechaPeriodoFinDicTx" id="fechaFinAudAviDicSITAB" name="fechaFinAudAviDicSITAB" onchange="validaPerFechasAutAviDicSITAB()" />								
								<div id="errorPeriodoFecAvisoDictSITAB">
									<label class="etiquetaError">La fecha de inicio no puede ser mayor a la de fin</label>
								</div>
								<div id="reqPeriodoFecAvisoDictSITAB">
									<label class="etiquetaError">La fecha del periodo es requerida</label>
								</div>
							</td>
						</tr>
						<tr class="par">
							<td align="left" width="20%" class="etiqueta2">
								<label>Funcionario que registra : </label>
							</td> 
							<td align="left" width="30%">
								<form:label path="cveUsuario" size="50" readonly="true" disabled="disabled" id="funcionarioRegAudAviDicSITAB"/>
							</td>
						<td align="left" colspan="2" width="50%">
							&nbsp;
						</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">
							<input type="button" class="boton" value="Guardar" id="btnGuardarAudAviDicSITAB" width="10px" height="12px" class="boton" onclick="validaGuardarAutAviSITAB()" >
						</td>
					</tr>
					<tr class="impar">
						<td align="left" class="etiqueta2" width="20%" colspan="4">
							&nbsp;
						</td> 						
					</tr>
				</table>																																								
									
			</form:form>
		</td>							    
	</tr>
</table>
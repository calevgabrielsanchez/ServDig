<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form:form id="formSeguimientoInvitacionTAB" method="Post" modelAttribute="crtInvitacion" action="/seguimiento/seginvitacion/guardaSeguimientoInvitacion.do">				
				<form:hidden path="cveInvitacion" id="inCveInviSITAB"/>
				<table class="tablaverde2" style="width: 100%">						
					<tr class="impar">
						<td align="left" class="etiqueta2" width="20%" colspan="4">
							&nbsp;
						</td> 
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label> 
								Fecha de notificaci&oacute;n del Oficio :
							</label>							
						</td> 
						<td align="left" width="30%">
							<form:input path="fechaNotOficioTx" id="inFecNotificaOfiSITAB" readonly="true" size="10" onchange="validaFechaNotOficioSITAB()"/>	
							<span class="boton_limpiar" onclick="limpiaFechaNotificacionSITAB()" id="btnLimpiaFechaNotificacionSITAB" >X</span>		
							<label class="etiquetaError" id="errorValidaFechaNot"></label>											
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Cancelaci&oacute;n del Oficio: </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="fechaCancelacionTx" id="inFecCancelaOfiSITAB" size="12" disabled="true"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Derivaci&oacute;n Subdelegaci&oacute;n : </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="fechaDerSubdelegacionTx" id="inFecDerSubdelSITAB" size="12" disabled="true"/>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Aut. del aviso de Dict. : </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="fechaAisoDictamenTx" id="inFecAviDictSITAB" size="12" disabled="true"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de Derivaci&oacute;n a Fiscalizaci&oacute;n. : </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="fechaPaiTx" id="inFecDerFiscaSITAB" size="12" disabled="true"/>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							&nbsp;
						</td> 
						<td align="left" width="30%" class="etiqueta2">
							&nbsp;
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Fecha de la Solicitud de Correcci&oacute;n. : </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="" id="inFecSolCorrSITAB" size="12" disabled="true"/>
						</td>
						<td colspan="2" align="left" class="etiqueta2" width="50%">
							<label>Periodo de correcci&oacute;n</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							&nbsp;
						</td> 
						<td align="left" width="30%" class="etiqueta2">
							&nbsp;
						</td>
						<td align="left" colspan="2" width="50%">
							<label class="etiqueta2"><b>Del :   </b></label>
							<form:input path="fechaPeriodoIniDicTx" id="inFecSolCorrIniSITAB" size="12" disabled="true"/>
							<label class="etiqueta2"><b>  Al :   </b></label>
							<form:input path="fechaPeriodoFinDicTx" id="inFecSolCorrFinSITAB" size="12" disabled="true"/>
						</td>
						
					</tr>
					<tr class="par">
						<td align="right" class="etiqueta2" width="20%">
							
						</td> 
						<td align="left" width="30%" class="etiqueta2">
							
						</td>
						<td align="left" colspan="2" width="50%"  class="etiqueta2">
							&nbsp;
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Observaciones : </label>
						</td> 
						<td align="left" width="30%" class="etiqueta2">
						
						</td>
						<td align="center" width="50%" colspan="2" class="etiqueta2">
							&nbsp;
						</td>
					</tr>
					<tr class="par" >
						<td align="left" colspan="4">
							<form:textarea path="txObservaciones" id="taObservacionesSITAB" onchange="validaCampo('noCaracteresEspeciales','taObservacionesSITAB','formSeguimientoInvitacionTAB');" onkeyup="valTamTextArea(event,this,200)"  cols="100" rows="5"/>
						</td> 
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4" class="etiqueta2">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">
								<input type="button" value="Guardar" id="btnGuardarSITAB" width="10px" height="12px" class="boton" onclick="javascript:validaGuardaFormSITAB();" >
						</td>
					</tr>
						<tr valign="top" class="par">
				    	<td align="center" colspan="4" class="etiqueta2">
							
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
	
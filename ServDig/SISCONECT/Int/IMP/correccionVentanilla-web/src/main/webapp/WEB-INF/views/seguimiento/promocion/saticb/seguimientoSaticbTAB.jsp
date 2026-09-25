<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form:form id="seguimientoSaticbTABForm" method="Post" modelAttribute="seguimientoSaticbVO" action="seguimiento/saticb/actualizaPromSaticb.do">
				<table class="tablaverde2" style="width: 100%">	
				<form:hidden  path="cvePromocion" id="cvePromocion"/>	
				<form:hidden path="rolUsuario" id="rolGenericoSaticB"/>
					<tr class="impar">
						<td align="left" class="etiqueta2" width="20%" colspan="4">
							&nbsp;
						</td> 
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">* </span>Fecha de Notificaci&oacute;n del Oficio :
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaNotificacion" id="fechaNotificaSaticb" readonly="true" size="12"
							onchange="javaScript:jsValidaFecNotifSaticb(this.value)" />
							<span class="boton_limpiar" onclick="limpiaFechaNotificacionSaticb()" id="spnFecNotSaticb" >X</span>
							<div id="labelFechaNotificaSaticb"></div>
						</td>
						<td align="left"  class="etiqueta2" width="20%">
							<label>Fecha de Cancelaci&oacute;n del Oficio: </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaCancelacion" id="fechaCancelaSaticb" size="12" />
						</td>
					</tr>
					<tr class="par">
						<td align="left"  class="etiqueta2" width="20%">
							<label>Fecha de Derivaci&oacute;n Subdelegaci&oacute;n : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaDerivaSubdel" id="fechaDerSubdelSaticb" size="12"/>
						</td>
						<td align="left"  class="etiqueta2" width="20%">
							<label>Fecha  Autorizaci&oacute;n Aviso de Dictamen : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaAutAvisoDictamen" id="fecAviDictSaticb" size="12"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left"  class="etiqueta2" width="20%">
							<label>Fecha de Derivaci&oacute;n a Fiscalizaci&oacute;n : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaDerivaFiscaliza" id="fechaDerFiscaSaticb" size="12"/>
						</td>
						<td align="left"   width="20%">
							&nbsp;
						</td> 
						<td align="left" width="30%"  >
							&nbsp;
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2"  width="20%">
							<label>Fecha de Atenci&oacute;n del Oficio : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaAtencionOfi" id="fecAtenOficioSaticb" size="12"/>
						</td>
						<td align="right"   width="20%">
							<form:checkbox path="segSaticTabVo.estatusObra" id="cbxEstatusObraSaticB" onclick="seleccionaEstatusObra()"/>							
						</td> 
						<td align="left" class="etiqueta2" width="30%"  >
							<label>Estatus Obra</label>
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2"  width="20%">
							<label>Fecha de la Solicitud de Correcci&oacute;n : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaSolCorreccion" id="fecSolCorrSaticb" size="12"/>
						</td>
						<td colspan="2" align="left"  class="etiqueta2" width="50%">
							<label>Periodo de Correcci&oacute;n</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2"  width="20%">
							<label>Fecha Oficio Invitaci&oacute;n : </label>
						</td> 
						<td align="left" width="30%"  >
							<form:input path="segSaticTabVo.fechaOficioInvitacion" id="fecOficioInvSaticb" size="12"/>
						</td>
						<td align="left" colspan="2" width="50%"   >
							<label class="etiqueta2"><b>Del :   </b></label>
							<form:input path="segSaticTabVo.periodoCorrecIni" id="fecSolCorrIniSaticb" size="12"/>
							<label class="etiqueta2"><b>  Al :   </b></label>
							<form:input path="segSaticTabVo.periodoCorrecFin" id="fecSolCorrFinSaticb" size="12"/>
						</td>
						
					</tr>
					<tr class="par">
						<td align="right"   width="20%">
							
						</td> 
						<td align="left" width="30%"  >
							
						</td>
						<td align="left" colspan="2" width="50%"   >
							&nbsp;
						</td>
						
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" >
							<label>Observaciones : </label>
						</td> 
						<td align="left" width="30%"  >
						
						</td>
						<td align="center" width="50%" colspan="2"  >
							<input type="button" value="Generar invitación" id="btnGenInvitaSaticB" width="10px" height="12px" class="boton" onclick="javascript:muestraInvitacionSaticb();" >
							
						</td>
					</tr>
					<tr class="par" >
						<td align="left" colspan="4"  >
							<form:textarea path="segSaticTabVo.observaciones" id="observacionesSegSaticb" onchange="validaCampo('noCaracteresEspeciales','observacionesSegSaticb','seguimientoSaticbTABForm')" 
							onkeyup="valTamTextArea(event,this,200)"  cols="100" rows="5"/>
						</td> 
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4"  >&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4"  >

								<input type="button" value="Guardar" id="btnGuardarSegSaticb" width="10px" height="12px" class="boton" onclick="procesaFormularioSegSaticb(validaDatosSaticbTab())" >
						</td>
					</tr>
						<tr valign="top" class="par">
				    	<td align="center" colspan="4"  >
							
						</td>
					</tr>
					<tr class="impar">
						<td align="left"   width="20%" colspan="4">
							&nbsp;
						</td> 
						
					</tr>
				</table>
				</form:form>
			</td>
		</tr>
	</table>
	
	
		
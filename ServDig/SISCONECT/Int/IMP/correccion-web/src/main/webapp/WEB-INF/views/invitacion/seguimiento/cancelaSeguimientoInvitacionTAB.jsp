<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
			    
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
					<form:form method="post" id="formCancelaSITAB" modelAttribute="crtInvitacion" action="">						
									
							<form:hidden path="cveInvitacion" id="hidCveInvitacionCancelacionSITAB"/>		
									<table class="tablaverde2" style="width: 100%">								  	  
									  <tbody>
									  	<tr class="impar">
											<td align="left" class="etiqueta2" width="20%" colspan="4">
											&nbsp;
											</td> 
						
										</tr>									  	
										<tr valign="top" class="par">
										   	<td align="left" width="25%" class="etiqueta2">
										   		<span class="required">*</span>Referencia de Cancelaci&oacute;n:																									
											</td>
											<td align="left" width="25%">
												<form:input path="numOficioCancelacion" id="inRefCancelacionSITAB" size="26" maxlength="25" onkeyup="mayusculasTextField(this);"/>												
												<div id="reqReferenciaCancelacionSITAB">													
													<label class="etiquetaError">La referencia es requerida</label>									
												</div>
											</td>
											<td align="left" width="25%" class="etiqueta2">
												<span class="required">*</span>Fecha de Cancelaci&oacute;n																									
											</td>
											<td align="left" width="25%">
												<form:input path="fechaCancelacionTx" name="inFecCancelacionSITAB" id="inFecCancelacionSITAB" size="10" readonly="true" onchange="validaFechaCancelacionSITAB()"/>										    														
												<span class="boton_limpiar" onclick="limpiaFechaCancelaSITAB()" id="btnLimpiaFechaCancelaSITAB" >X</span>
												<div id="errorFechaCancelacionSITAB">													
													<label class="etiquetaError">La fecha no puede ser menor a la fecha de noficaci&oacute;n</label>									
												</div>
												<div id="errorFechaUnoCancelacionSITAB">													
													<label class="etiquetaError">La fecha no puede ser menor a la fecha de emisi&oacute;n</label>									
												</div>
												<div id="reqFechaCancelacionSITAB">													
													<label class="etiquetaError">La fecha de cancelaci&oacute;n es requerida</label>									
												</div>
											</td>																					   	
										</tr>										
									  	<tr valign="top" class="par">   	
									  		<td align="left" width="25%" class="etiqueta2">
												<span class="required">*</span>Funcionario que Autoriza:	    		
											 </td>
											<td align="left" width="25%">
					  							<form:select path="cveUsuarioAutoriza" id="selFuncionarioAutCancelacionSITAB" name="selFuncionarioAutCancelacionSITAB" >
					  								<form:option value="-1" label="--Seleccione Por favor--" />
					  							</form:select>
					  							<div id="reqFuncionarioCancelacionSITAB">													
													<label class="etiquetaError">El funcionario que autoriza es requerido</label>									
												</div>						  						
											</td>
										   	<td align="left" width="25%" class="etiqueta2">
										   		Funcionario que Registra																									
											</td>
											<td align="left" width="25%">
												<form:label path="cveUsuario" name="inFuncionarioRegCancelacionSITAB" id="inFuncionarioRegCancelacionSITAB" size="25" readonly="true" disabled="true"/>
												
											</td>																							
										</tr>		
										<tr valign="top" class="par">											
											<td align="left" width="25%" class="etiqueta2">
									  			<span class="required">*</span>Motivo de Cancelaci&oacute;n:	
									  		</td>
									  		<td align="left" width="25%" colspan="4">
										   		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion"
																 idHtml="idMotivoCancelacion"
																 idHtmlContenedor="formCancelaSITAB"/>																						 
												<div id="reqMotivoCancelacionSITAB">													
													<label class="etiquetaError">El motivo de cancelaci&oacute;n es requerido</label>									
												</div>				    		
										  	</td>																							
										  </tr>
										  <tr valign="top" class="par">
				    							<td align="left" colspan="4" class="etiqueta2">&nbsp;</td>
											</tr>	
										   <tr valign="top" class="par">
												<td align="center" width="25%" colspan="4">														
													<input type="button" value="Guardar" id="btnGuardaCancelaSITAB" width="10px" height="12px" class="boton" onclick="validaCamposReqCancelaSITAB();" >					    		
											   </td>
										   </tr>
										   <tr class="impar">
											<td align="left" class="etiqueta2" width="20%" colspan="4">
												&nbsp;
											</td> 						
										</tr>
										 </tbody>
									</table>																									 																							
					</form:form>
				</td>							    
		    </tr>
	</table>
	
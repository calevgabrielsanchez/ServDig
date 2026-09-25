<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form:form method="post" id="formDerSubdelegacionSITAB" modelAttribute="crtInvitacion" action="">						
				
					<form:hidden path="cvePatronFiscal" id="cvePatronDerSubdelegSITAB"/>
					<form:hidden path="cveInvitacion" id="cveInvitacionDerSubdelegSITAB"/>
					<form:hidden path="cveSubdelegDest" id="cveSubdDelegSITAB"/>
					<form:hidden path="cveFkSubdelegacion" id="cveSubdDelegOriginalSITAB"/>
					<table  class="tablaverde2" width="100%" >		
						<thead>
							<tr>
								<td colspan="4">Domicilio Fiscal</td>
							</tr>
						</thead>						
						<tbody>
							<tr valign="top" class="impar">
							    <td align="left" colspan="4">&nbsp;</td>
							</tr>										   
							<tr valign="top" class="par">   												  
							   	<td align="left" width="25%" class="etiqueta2">
							   		<span class="required">*</span>Registro Patronal: 	    		
								</td>
								<td align="left" width="25%">
									<form:input path="" id="regPatronDerSubdelegacionSITAB" size="20" maxlength="10" onkeydown="limpiaCamposDeriva();" onchange="limpiaCamposDeriva();" onkeyup="validaCampo('noCaracteresEspeciales','regPatronDerSubdelegacionSITAB','formDerSubdelegacionSITAB')"/>
									<div id="reqRegPatronDerSubdelSITAB">
										<label class="etiquetaError">El registro patronal es requerida</label>
									</div>
									<div id="errorIgualDerSubdelSITAB">
										<label class="etiquetaError">La derivaci&oacute;n no puede ser a la misma subdelegaci&oacute;n </label>
									</div>
									<input type="button" class="boton" onclick="validarRegistroPatronalFiscalSITAB()" value="Validar" id="btnValidaRegistroPatronSITAB">
								</td>
								<td align="left" width="100%" class="etiqueta2">
									<span class="required">*</span>Nombre &oacute; Raz&oacute;n Social: 										    											
								</td>	
								<td align="left" width="100%">
									<form:input path="" id="razonSocialDerSubdelegacionSITAB" size="90" maxlength="80" readonly="true" />
								</td>									      
							</tr>										   										  										   
							<tr valign="top" class="par">
								<td align="left" width="200px" class="etiqueta2">
									<span class="required">*</span>Calle: 
								</td>
								<td align="left" width="200px">										    		
									<input name="calleDerivarSubdel" id="calleDerSubdelegacionSITAB" size="50" readonly="readonly"/>
									<div id="reqCalleDerSubdelSITAB">
										<label class="etiquetaError">La calle es requerida</label>
									</div>
								</td>
								<td align="left" width="200px" class="etiqueta2">
									<span class="required">*</span>Colonia:			
								</td>
								<td align="left" width="200px">							    		
									<input name="coloniaDerSubdelegacionSITAB" id="coloniaDerSubdelegacionSITAB" size="50" readonly="readonly"/>
									<div id="reqColoniaDerSubdelSITAB">
										<label class="etiquetaError">La colonia es requerida</label>
									</div>
								</td>        
			   				</tr>
			   				<tr valign="top" class="par">
								<td align="left" width="100px" class="etiqueta2">
									<span class="required">*</span>N&uacute;mero Exterior: 										    												    		
							    </td>
								<td align="left" width="100px">
									<input name="numExtDerSubdelegacionSITAB" id="numExtDerSubdelegacionSITAB" size="12" readonly="readonly"/>
									<div id="reqNumExtDerSubdelSITAB">
										<label class="etiquetaError">El n&uacute;mero exterior es requerido</label>
									</div>
								</td>			   							   
								<td align="left" width="100px" class="etiqueta2">
									N&uacute;mero Interior:
								</td>
								<td align="left" width="100px">	
								   	<input name="numIntDerSubdelegacionSITAB" id="numIntDerSubdelegacionSITAB" size="12" readonly="readonly"/>
								</td>
			   				</tr>
			   					<tr valign="top" class="par">
								    <td align="left" width="100px" class="etiqueta2">
								    	<span class="required">*</span>C&oacute;digo Postal: 									    												    	
								    </td>
									<td align="left" width="100px">
										<input name="codigoPostalDerSubdelegacionSITAB" id="codigoPostalDerSubdelegacionSITAB" size="12" readonly="readonly"/>
										<div id="reqCodPosDerSubdelSITAB">
											<label class="etiquetaError">C&oacute;digo Postal es requerido</label>
										</div>
									</td>
									<td align="center" colspan="2">
										<div id="divBtnServiceDomicilio">
											<input type="button" class="boton" onclick="cargaDomicilioDerSubSITAB()" value="Agregar/Modificar Domicilio">
										</div>										
									</td>											
								</tr>
							</table>
							<br>
							<table class="tablaverde2" width="100%">
								<thead>
									<tr >
										<td colspan="4">Datos de la Derivaci&oacute;n</td>
									</tr>
								</thead>  	
								<tbody>  
									<tr valign="top" class="impar">
							    		<td align="left" colspan="4">&nbsp;</td>
									</tr>	
								    <tr valign="top" class="par">
									    <td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span>Fecha de la derivaci&oacute;n
								    	</td>
										<td align="left" width="100px">
									   		<form:input path="fechaDerSubdelegacionTx" id="fechaDerSubdelegacionSITAB" size="12" readonly="true" onchange="validaFechaDerSubdelSITAB()"/>
											<span class="boton_limpiar" onclick="limpiaFechaDerSubdelegSITAB()" id="btnLimpiaFechaSubdelegSITAB" >X</span>
											<div id="reqFechaDerSubdelSITAB">
												<label class="etiquetaError">La fecha de derivaci&oacute;n es requerida</label>
											</div>
											<div id="errorFechaDerSubdelSITAB">
												<label class="etiquetaError">La fecha no puede ser menor a la fecha de emisi&oacute;n</label>
											</div>
											<div id="errorFechaNotificaDerSubdelSITAB">
												<label class="etiquetaError">La fecha no puede ser menor a la fecha de noficaci&oacute;n</label>
											</div>
										</td>			   							   
										<td align="left" width="100px" class="etiqueta2">
											Subdelegaci&oacute;n Destino: 										    												    	
										</td>
										<td align="left" width="100px">
									  		<form:input  path="" id="destinoSubdelDerSubdelegacionSITAB" size="50" readonly="true"/>
									   		<div id="reqDestinoDerSubdelSITAB">
												<label class="etiquetaError">La Subdelegaci&oacute;n destino es requerida</label>
											</div>
										</td>
									</tr>										   
									<tr valign="top" class="par">
										<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="required">*</span>Referencia de la derivaci&oacute;n
								    	</td>
										<td align="left" width="100px">
									   		<form:input path="txReferenciaSubdeleg" id="referenciaDerSubdelegacionSITAB" size="20" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','referenciaDerSubdelegacionSITAB','formDerSubdelegacionSITAB');"/>										
											<div id="reqReferenciaDerSubdelSITAB">
												<label class="etiquetaError">La referencia de derivaci&oacute;n es requerida</label>
											</div>
										</td>								  		
								    	<td align="left" width="100px" class="etiqueta2">
								    		Funcionario que registra 										    												    	
										</td>
										<td align="left" width="100px">
									  		<form:label path="cveUsuario" name="funRegDerSubdelegacionSITAB" id="funRegDerSubdelegacionSITAB" size="50" readonly="true"/>
										</td>
									</tr>
									<tr valign="top" class="par">										  	
								    	<td align="center" width="100px" colspan="4" >
								    		<div id="divBtnDerSubdelSITAB">
								    			<input type="button" class="boton" onclick="validaFormDerSubdelegSITAB()" value="Confirmar" id="btnConfirmaDerSubdelSITAB">
								    		</div>								    														 										    												   
								    	</td>
									</tr>										  	
								</tbody>
							</table>																																										
											
					</form:form>
				</td>							    
		    </tr>
	</table>
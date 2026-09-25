<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    <div id="dgPromocionExhortoCons"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogExhortCons" style="background-color: #f2fff2;">	
					<form action=""	method="post" id="promocionFormExhortoCons">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion"/>
						<input type="hidden" name="sdelegOrig" id="sdelegOrig"/>
						<input type="hidden" name="cveTipocorr" id="cveTipocorr"/>
						<input type="hidden" name="cveFkPatron" id="cveFkPatron"/>
						<input type="hidden" name="domicilioId" id="domicilioId"/>
						<input type="hidden" name="idObligado" id="idObligado"/>
						<input type="hidden" name="idPromovido" id="idPromovido"/>
						<input type="hidden" name="fechaRegistro" id="fechaRegistro"/>
						<input type="hidden" name="cveUsuario" id="cveUsuario"/>
						<input type="hidden" name="cveCensor" id="cveCensor"/>
						<input type="hidden" name="nuReportectrlobra" id="nuReportectrlobra"/>
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos de Detección de la Obra</td>
									  	</tr>
									  </thead>
									  <tbody>
									  	<tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										</tr>
									  	<tr valign="top" class="par">   	
										   		<td align="left" width="25%" class="etiqueta2">Folio de Detecci&oacute;n																									
												</td>
												<td align="left" width="25%">
													<input name="nuFoliodeteccion" id="nuFoliodeteccion" size="25" readonly="readonly"/>
													<label for="nuFoliodeteccion"></label>
												</td>
										</tr>		
									  	<tr valign="top" class="par">   	
										   		<td align="left" width="25%" class="etiqueta2">Fecha de Detecci&oacute;n																									
												</td>
												<td align="left" width="25%">
													<input name="fechaDeteccion" id="fechaDeteccion" readonly="readonly"/>
													<label for="fechaDeteccion"></label>
												</td>												 											   									   	   
										   </tr>
										   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>
										 </tbody>
									</table>
									<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Ubicaci&oacute;n de la Obra</td>
									  	</tr>
									  </thead>
									   <tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>
										   <tr valign="top" class="par">
										    <td align="left" width="200px" colspan="1" class="etiqueta2">Estado:
										    </td>
										    <td align="left" width="200px" colspan="1">
										    	<input name="estado" id="estado" size="50" readonly="readonly"/>
													<label for="estado"></label> 										    		
										    </td>
										    <td align="left" width="200px" colspan="1" class="etiqueta2"><span class="required">*</span>Municipio:
										    </td>
										    <td align="left" width="200px" colspan="1"> 										    		
										    		<input name="municipio" id="municipio" size="50" readonly="readonly"/>
													<label for="municipio"></label>
										    </td>        
			   							   </tr>
									  	   <tr valign="top" class="par">
										    <td align="left" width="200px" colspan="1" class="etiqueta2">Calle: 
										    </td>
										    <td align="left" width="200px" colspan="1">										    		
										    		<input name="domCalle" id="domCalle" size="50" readonly="readonly"/>
													<label for="domCalle"></label>
										    </td>
										    <td align="left" width="200px" colspan="1" class="etiqueta2">Colonia:
										    </td>
										    <td align="left" width="200px" colspan="1">										    		
										    		<input name="refColonia" id="refColonia" size="50" readonly="readonly"/>
													<label for="refColonia"></label>
										    </td>        
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px" colspan="1" class="etiqueta2">N&uacute;mero Interior: 										    												    	
										    </td>
										    <td align="left" width="100px" colspan="1">
										    	<input name="numNroint" id="numNroint" size="12" readonly="readonly"/>
												<label for="numNroint"></label>
											</td>
										    <td align="left" width="100px" colspan="1" class="etiqueta2">N&uacute;mero Exterior: 										    												    		
										    </td>
										    <td align="left" width="100px" colspan="1">
										    	<input name="numNroext" id="numNroext" size="12" readonly="readonly"/>
												<label for="numNroext"></label>
											</td>
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px" colspan="1" class="etiqueta2">Codigo Postal: 									    												    	
										    </td>
										    <td align="left" width="100px" colspan="1">
										    	<input name="numCodigopostal" id="numCodigopostal" size="12" readonly="readonly"/>
												<label for="numCodigopostal"></label>
											</td>
										   </tr>
										   <tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</table>
									<table class="tablaverde2" style="width: 900px">
										<thead>
										  	<tr >
										  		<td colspan="4">Datos Generales de la Obra</td>
										  	</tr>
									  	</thead>
										<tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>									  	   				  
										   <tr valign="top" class="par">   												  
											   <td align="left" width="25%" class="etiqueta2">Registro Patronal: 	    		
											   </td>
											   <td align="left" width="25%">
											   		<input name="regPatron" id="regPatron" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatron','promocionFormExhortoCons')"/>
											   		<a href="#" onclick="javascript:validaRegPatron();"><span class="boton">Validar</span></a>
											   		<label for="regPatron"></label>												   												   													   	
											   </td>
											   <td align="left" width="25%" class="etiqueta2">Fecha Estimada de Inicio de la Obra																									
												</td>
												<td align="left" width="25%">
													<input name="fechaEstimIncio2" id="fechaEstimIncio2" readonly="readonly" onchange="validafechaSistema('promocionFormExhortoCons','fechaEstimIncio2','Fecha Estimada de Inicio')"/>
													<label for="fechaEstimIncio2"></label>
												</td>																							   
										   </tr>
										   <tr valign="top" class="par">
										    <td align="left" width="100%" colspan="4" class="etiqueta2">Nombre &oacute; Raz&oacute;n Social: 										    		
										    		<input name="nomRazonsocial" id="nomRazonsocial" size="90" maxlength="80" onkeyup="validaCampo('noCaracteresEspeciales','nomRazonsocial','promocionFormExhortoCons')"/>
													<label for="nomRazonsocial"></label>
										    </td>										      
			   							   </tr>			   			
										   <tr>
										   		<td align="left" width="100px" colspan="1" class="etiqueta2">Clase de la Obra: 
												</td>												
												<td align="left" width="100px" colspan="1" class="etiqueta2">
													<select name="tipClaseobra" id="tipClaseobra">
														<option value="" id="0">Seleccionar</option>
														<option value="Publica" id="1">Publica</option>
														<option value="Privada" id="2">Privada</option>
													</select>
												</td>
											</tr>										   
										   					<tr valign="top" class="par" >
										   						<td align="left" width="100px" class="etiqueta2">Superficie m<sup>2</sup>:	    		
																</td>
																<td align="left" width="100px">
																	<input name="canSuperficie" id="canSuperficie" size="20" maxlength="6" onkeyup="validaCampo('PermiteSoloNumerosYPunto','canSuperficie','promocionFormExhortoCons')"/>
																   	<label for="canSuperficie"></label>
																</td>
										   						<td align="left" width="25%" class="etiqueta2">Fecha Estimada de T&eacute;rmino de la obra:																									
																</td>
																<td align="left" width="25%">
																	<input name="fechaEstTerm2" id="fechaEstTerm2" readonly="readonly" onchange="validaFecha('promocionFormExhortoCons','fecFechainicioEst2','fecFechaterminoEst2','Fecha estimada de termino','Fecha estimada de Inicio')"/>
																	<label for="fechaEstTerm2"></label>	
																</td>										   																					
										   					</tr>										   					
										   					<tr valign="top" class="par">
																<td align="left" width="100px" class="etiqueta2">RFC: 	    													   																	
																</td>																
																<td align="left" width="100px">
																	<input name="txRfcpatron" id="txRfcpatron" size="15" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','txRfcpatron','promocionFormExhortoCons')"/>
																	<label for="txRfcpatron"></label>
																</td>
																<td align="left" width="110px" class="etiqueta2">Curp Patr&oacute;n: 	    		
															    </td>
															    <td align="left" width="90px">
															   		<input name="txCurppatron" id="txCurppatron" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','txCurppatron','promocionFormExhortoCons')"/>
															   		<label for="txCurppatron"></label>
															    </td>															    
															</tr>															 
															<tr>
														   		<td colspan="4">
																	<div id="divCombosObra" style="width:100%;display:none" >
																		<table  style="width: 100%" >																			
																			<tr valign="top" class="par">   	
																			   <td align="left" width="50%" colspan="2" class="etiqueta2">Tipo de Obra: 	    			    
																			   </td>															   
																			   <td align="left" width="50%" colspan="2" class="etiqueta2">Fase de la Obra: 	    		
																			   </td>															   	   
																		   </tr>														   		
																		   <tr valign="top" class="par">
																		   		<td align="left" width="50%" colspan="2">
																			   			<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoobra"
																						 idHtml="cvePkTipObra"																						 
																						 idHtmlContenedor="promocionFormExhortoCons"/>		    
																			   	</td>
																			   	<td align="left" width="50%" colspan="2">	    						    		
																			    		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcFaseconstruccion"
																						 idHtml="cvePkFaseConst"
																						 idHtmlContenedor="promocionFormExhortoCons"/>	    						
																			   	</td>
																		   </tr>
																	   	</table>
																	</div>
																</td>
															</tr>																	   																																
														   <tr valign="top" class="par">   												  
															   <td align="left" width="110px" class="etiqueta2">Importe de la Obra: 	    		
															   </td>
															   <td align="left" width="90px">
															   		<input name="impCostoobra" id="impCostoobra" size="20" maxlength="10" onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCostoobra','promocionFormExhortoCons')"/>
															   		<label for="impCostoobra"></label>
															   </td>
															   <td align="left" width="100px" class="etiqueta2">Porcentaje de Avance: 	    		
															   </td>
															   <td align="left" width="100px">
															   		<input name="porAvanceobraEst" id="porAvanceobraEst" size="20" maxlength="3" onkeyup="validaCampo('PermiteSoloNumeros','porAvanceobraEst','promocionFormExhortoCons');valorMaxim('porAvanceobraEst','Porcentaje Regularizado','promocionFormExhortoCons')"/>
															   		<label for="porAvanceobraEst"></label>
															   </td>		   
															</tr>															
															<tr valign="top" class="par">   												  
															   <td align="left" width="110px" class="etiqueta2">E-mail: 	    		
															   </td>
															   <td align="left" width="90px">
															   		<input name="txEmail" id="txEmail" size="20" maxlength="150" onblur="validaMail('promocionFormExhortoCons','txEmail','E-mail')"/>
															   		<label for="txEmail"></label>
															   </td>
															   <td align="left" width="110px" class="etiqueta2">Telefono: 	    		
															   </td>
															   <td align="left" width="90px">
															   		<input name="txTelefono" id="txTelefono" size="20" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','txTelefono','promocionFormExhortoCons')"/>
															   		<label for="txTelefono"></label>
															   </td>
														   </tr>
										   				
										   <tr valign="top" class="par">
										   		<td align="left" width="110px" class="etiqueta2">Dependencia: 	    		
												</td>
												<td align="left" width="90px">
													<input name="desDependenciapub" id="desDependenciapub" size="20" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','desDependenciapub','promocionFormExhortoCons')"/>
													<label for="desDependenciapub"></label>
												</td>
												<td align="left" width="110px" class="etiqueta2">Dependencia Contratante: 	    		
												</td>
												<td align="left" width="90px">
													<input name="desDepcontratante" id="desDepcontratante" size="20" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','desDepcontratante','promocionFormExhortoCons')"/>
													<label for="desDepcontratante"></label>
												</td>
										   </tr>																					
										   <tr valign="top" class="par">
										   	<td align="left" width="110px" class="etiqueta2">Zona Salarial: 
											   </td>
											   <td align="left" width="100px">	    						    		
											    	<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacZona"
																		 idHtml="cveFkZona"
																		 idHtmlContenedor="promocionFormExhortoCons"/>
											   </td>   												  											   
											   <td align="left" width="110px" class="etiqueta2">Numero de Trabajadores: 											    	
											   </td>
											   <td align="left" width="100px">	    						    													   	    															
													<input name="numTrabajdores" id="numTrabajdores" size="20" maxlength="5" onkeyup="validaCampo('noCaracteresEspeciales','numTrabajdores','promocionFormExhortoCons');validaCampo('PermiteSoloNumeros','numTrabajdores','promocionFormExhortoCons')"/>
													<label for="numTrabajdores"></label>																   													
											   </td>	   											  		   
										   </tr>										   									   										 
										    <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>										   
									  </tbody>
									</table>									
								  </td>
								  </tr>
							</table>													
						</fieldset>
					</form>							    
		    	</div>
			</div>
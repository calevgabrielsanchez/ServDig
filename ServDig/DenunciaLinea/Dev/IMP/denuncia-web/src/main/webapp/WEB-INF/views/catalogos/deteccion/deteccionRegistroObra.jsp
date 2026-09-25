<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
			    
		    <div id="dgDeteccionRegistro" title="Registro de Obra" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form action="/catalogo/deteccion/agregar.do"
						method="post" id="deteccionFormRegistro">
						<input type="hidden" name="cveFkPatron" id="cveFkPatron">
						<input type="hidden" name="domicilioId" id="domicilioId">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
						<input type="hidden" name="bandera" id="bandera">
						
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">  
								  <td align="center" width="900px">
								  
								  <table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
										<td colspan="4">Datos del Censo</td>
										</tr>
									  </thead>									  
									  <tbody>
										   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>										   
										   <tr valign="top" class="par">
										    <td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span>Origen:										    												    												    	
										    </td>    
										    <td align="left" width="200px" colspan="1">										    	
										    	<input type="radio" name="cveTipocorr" id="cveTipocorr" value="8" onclick="activaDiv()"/>Fuente Externa
										    	<div id="labelcveTipocorr"></div>
										    </td>
										    <td align="left" width="100px" colspan="2">
										    	<input type="radio" name="cveTipocorr" id="cveTipocorr" value="9" onclick="activaDiv()"/>Detecci&oacute;n
										    	<label class="required" for="cveTipocorr"></label>
										    </td>										    
			   							   </tr>
										   
										   <tr valign="top" class="par" id="nmCensor">
										    <td align="left" width="100px" colspan="1"class="etiqueta2"><span class="required">*</span>Nombre del Censor: 										    												    		
										    </td>
										    <td align="left" width="100px" colspan="3">												
												<select id="cveCensor" name="cveCensor">
												   <option value="">--Por favor seleccione--</option>
											    </select>
											    <div id="labelcveCensor"></div>	    
											</td>											
			   							   </tr>										   
										   <tr valign="top" class="par" id="dtCensor">   	
											   <td align="left" width="100px" class="etiqueta2">NSS: 
											   </td>
											   <td align="left" width="100px">
											   		<input name="nss" id="nss" size="50" readonly="readonly"></input>
											   	</td>
											   <td align="left" width="110px" class="etiqueta2">Matr&iacute;cula: 	
											   </td>
											   <td align="left" width="90px">
											   		<input name="matricula" id="matricula" size="50" readonly="readonly"></input>
											   	</td>		   
										   </tr>										   
										   <tr valign="top" class="par">   	
										   		<td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Fecha de Detecci&oacute;n																									
												</td>
												<td align="left" width="100px" >
													<!-- input name="fechaDeteccion" id="fechaDeteccion" onchange="validafechaSistema('deteccionFormRegistro','fechaDeteccion','Fecha de Detecci&oacute;n')"/ -->
													<input name="fechaDeteccion" id="fechaDeteccion" "/>
													<div id="labelfechaDeteccion"></div>													
												</td>
												<td colspan="2">
													<div id="divNumReporte">
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="140px" class="etiqueta2"><span class="required">*</span>Numero de Reporte: 
														    	</td>
														    	<td align="left" width="100px">	    		
																   	<input name="nuReportectrlobra" id="nuReportectrlobra" size="20" maxlength="4"  onkeyup="validaCampo('noCaracteresEspeciales','nuReportectrlobra','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','nuReportectrlobra');" />
																   	<label for="nuReportectrlobra"></label>	
																   	<div id="labelnuReportectrlobra"></div>																   	
															   	</td>
															</tr>
													   	</table>
													</div>
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
										    <td align="left" colspan="4"></td>
										   </tr>
										   
										  <%--  <tr valign="top" class="par">
										    <td align="left" colspan="4">
										    	<div id="domInegiButtons" align="center">										    		
										    		<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','registro')"><span class="boton">Agregar Domicilio</span></a>													
											   </div>
										    </td>
										   </tr> --%>
										   
										   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Estado:</td>
										    <td align="left"> 										    		
										    		<input name="estado" id="estado" size="50" onkeyup="validaCampo('noCaracteresEspeciales','estado')" readonly="readonly"/>
													<label for="estado"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Municipio:</td>
										    <td align="left"> 										    		
										    		<input name="municipio" id="municipio" size="50" onkeyup="validaCampo('noCaracteresEspeciales','municipio')" readonly="readonly"/>
													<label for="municipio"></label>
										    </td>        
			   							   </tr>
									  	   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Calle: 	</td>
										    <td align="left">									    		
										    		<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly"/>
													<label for="domCalle"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Colonia: 	</td>
										    <td align="left">									    		
										    		<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly"/>
													<label for="refColonia"></label>
										    </td>        
			   							   </tr>
			   							   <tr valign="top" class="par">										    
										    <td align="left" width="100px" colspan="1"  class="etiqueta2"><span class="required">*</span>N&uacute;mero Exterior: 										    												    		
										    </td>
										    <td align="left" width="100px" colspan="1">
										    	<input name="numNroext" id="numNroext" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly"/>
												<label for="numNroext"></label>
											</td>
											<td align="left" width="100px" colspan="1" class="etiqueta2">N&uacute;mero Interior: 										    												    	
										    </td>
										    <td align="left" width="100px" colspan="1"  >
										    	<input name="numNroint" id="numNroint" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly"/>
												<label for="numNroint"></label>
											</td>
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span>C&oacute;digo Postal: 										    												    	
										    </td>
										    <td align="left" width="100px" colspan="1"  >
										    	<input name="numCodigopostal" id="numCodigopostal" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly"/>
												<label for="numCodigopostal"></label>
											</td>
										   </tr>
										   <tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</table>
								  <table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos Generales</td>
									  	</tr>
									  </thead>
									  <tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>									  	   				  
										   <tr valign="top" class="par">   												  
											   <td align="left" width="15%" class="etiqueta2">Registro Patronal:</td>
											   <td align="left" width="35%" >
											   		<input name="regPatron" id="regPatron" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatron','deteccionFormRegistro')"/>
											   		<label for="regPatron"></label>
											   		<a href="#" onclick="javascript:validaRegPatronAlta();"><span class="boton">Validar</span></a>
											   </td>
											   <td align="left" width="20%" class="etiqueta2">Fecha Estimada de Inicio de la Obra:																									
												</td>
												<td align="left" width="30%" >
													<input name="fechaEstimIncio2" id="fechaEstimIncio2" onchange="validafechaSistema('deteccionFormRegistro','fechaEstimIncio2','Fecha Estimada de Inicio')"/>
													<label for="fechaEstimIncio2"></label>
												</td>																							   
										   </tr>
										   <tr valign="top" class="par">
										   		<td align="left" width="15%" class="etiqueta2">Nombre &oacute; Raz&oacute;n Social:</td>
											    <td align="left" width="55%" colspan="2" > 										    		
											    		<input name="nomRazonsocial" id="nomRazonsocial" size="90" maxlength="80" onkeyup="validaCampo('noCaracteresEspeciales','nomRazonsocial','deteccionFormRegistro')"/>
														<label for="nomRazonsocial"></label>
											    </td>										      
			   							   </tr>			   			
											<tr valign="top" class="par">
												<td align="left" width="15%" class="etiqueta2"><span
													class="required">*</span>Clase de la Obra:</td>
												<td align="left" width="35%" >
													<select name="tipClaseobra" id="tipClaseobra">
														<option value="" id="0">Seleccionar</option>
														<option value="Publica" id="1">Publica</option>
														<option value="Privada" id="2">Privada</option>
													</select>
													<div id="labeltipClaseobra"></div>
												</td>
												<td align="left" width="20%" class="etiqueta2"><span
													class="required">*</span>Tipo de Promoci&oacute;n:</td>
												<td align="left" width="30%" >
													<select name="idPromovido" id="idPromovido">
														<option value="">Seleccionar</option>
														<option value="1" id="1">SATIC-A</option>
														<option value="0" id="0">EXHORTO DE CONSTRUCCION</option>
													</select>
													<div id="labelPromovido"></div>
												</td>
											</tr>
											<tr valign="top" class="par">
										   		<td colspan="4">
										   			<div id="divDatosCenso">
										   				<table style="width: 900px">
										   					<tr valign="top" class="par" >
										   						<td align="left" width="25%" class="etiqueta2">Superficie m<sup>2</sup>: 	    		
																</td>
																<td align="left" width="25%" >
																	<input name="canSuperficie" id="canSuperficie" size="20" maxlength="6" onkeyup="validaCampo('noCaracteresEspeciales','canSuperficie','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','canSuperficie','deteccionFormRegistro')"/>
																   	<label for="canSuperficie"></label>
																</td>
										   						<td align="left" width="25%" class="etiqueta2">Fecha Estimada de Termino de la obra:																									
																</td>
																<td align="left" width="25%" >
																	<input name="fechaEstTerm2" id="fechaEstTerm2" onchange="validaFecha('deteccionFormRegistro','fechaEstimIncio2','fechaEstTerm2','Fecha estimada de termino','Fecha estimada de Inicio')"/>
																	<label for="fechaEstTerm2"></label>	
																</td>																
										   					</tr>										   					
										   					<tr valign="top" class="par">
																<td align="left" width="25%" class="etiqueta2">RFC: 	    													   																	
																</td>																
																<td align="left" width="25%" >
																	<input name="txRfcpatron" id="txRfcpatron" size="15" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','txRfcpatron','deteccionFormRegistro')"/>
																	<label for="txRfcpatron"></label>
																</td>
																<td align="left" width="25%" class="etiqueta2">Curp Patr&oacute;n: 	    		
															    </td>
															    <td align="left" width="25%">
															   		<input name="txCurppatron" id="txCurppatron" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','txCurppatron','deteccionFormRegistro')"/>
															   		<label for="txCurppatron"></label>
															    </td>															    
															</tr>															
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
																		 entidadPadre="mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion"																		  
																		 idHtmlPadre="tipClaseobra"
																		 idHtmlContenedor="deteccionFormRegistro"/>		    
															   	</td>
															   	<td align="left" width="50%" colspan="2">	    						    		
															    		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcFaseconstruccion"
																		 idHtml="cvePkFaseConst"
																		 idHtmlContenedor="deteccionFormRegistro"/>	    						
															   	</td>
														   </tr>
														   <tr valign="top" class="par">   												  
															   <td align="left" width="25%" class="etiqueta2">Importe de la Obra: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="impCostoobra" id="impCostoobra" size="20" maxlength="12" onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCostoobra','deteccionFormRegistro')"/>
															   		<label for="impCostoobra"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta2">Porcentaje de Avance: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="porAvanceobraEst" id="porAvanceobraEst" size="20" maxlength="3" onkeyup="validaCampo('noCaracteresEspeciales','porAvanceobraEst','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','porAvanceobraEst','deteccionFormRegistro');valorMaxim('porAvanceobraEst','Porcentaje Regularizado','deteccionFormRegistro')"/>
															   		<label for="porAvanceobraEst"></label>
															   </td>		   
															</tr>															
															<tr valign="top" class="par">   												  
															   <td align="left" width="25%" class="etiqueta2">E-mail: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="txEmail" id="txEmail" size="50" maxlength="150"/>
															   		<label for="txEmail"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta2">Telefono: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="txTelefono" id="txTelefono" size="20" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','txTelefono','deteccionFormRegistro')"/>
															   		<label for="txTelefono"></label>
															   </td>
														   </tr>
										   				</table>											   
											   		</div>
										   		</td>											   
										   </tr>
										   <tr valign="top" class="par">
										   		<td align="left" width="25%" class="etiqueta2">Dependencia:</td>
												<td align="left" width="25%" >
													<input name="desDependenciapub" id="desDependenciapub" size="50" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','desDependenciapub','deteccionFormRegistro')"/>
													<label for="desDependenciapub"></label>
												</td>
												<td align="left" width="25%" class="etiqueta2">Dependencia Contratante: 	    		
												</td>
												<td align="left" width="25%" >
													<input name="desDepcontratante" id="desDepcontratante" size="50" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','desDepcontratante','deteccionFormRegistro')"/>
													<label for="desDepcontratante"></label>
												</td>
										   </tr>																					
										   <tr valign="top" class="par">   												  
											   <td align="left" width="25%" class="etiqueta2">Zona Salarial: </td>
											   <td align="left" width="25%">	    						    		
											    	<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacZona"
																		 idHtml="cveFkZona"
																		 idHtmlContenedor="deteccionFormRegistro"/>
											   </td>
											   <td align="left" width="25%" class="etiqueta2">N&uacute;mero de Trabajadores: 											    	
											   </td>
											   <td align="left" width="25%">	    						    													   	    															
													<input name="numTrabajdores" id="numTrabajdores" size="20" maxlength="5" onkeyup="validaCampo('noCaracteresEspeciales','numTrabajdores','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','numTrabajdores','deteccionFormRegistro')"/>
													<label for="numTrabajdores"></label>																   													
											   </td>	   											  		   
										   </tr>
										   <tr valign="top" class="par">
										   		<td colspan="2">
													<div id="divActividad" >
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="25%" class="etiqueta2">Actividad:
														    	</td>
														    	<td align="left" width="25%" >	    																		   	
																   	<input name="actividad" id="actividad" size="63" maxlength="60" onkeyup="validaCampo('noCaracteresEspeciales','actividad','deteccionFormRegistro')"/>
																	<label for="actividad"></label>																   																													   	
															   	</td>
															</tr>
													   	</table>
													</div>
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
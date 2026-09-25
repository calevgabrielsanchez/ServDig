<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
			    
		    <div id="dgDeteccionRegistroLayout" title="Registro de Obra" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistroLayout" style="background-color: #f2fff2;">	
					<form action="/catalogo/deteccion/agregar.do"
						method="post" id="deteccionLayoutForm">											
						<input type="hidden" name="cveDeteccion" id="cveDeteccion"/>
						<input type="hidden" name="sdelegOrig" id="sdelegOrig"/>
						<input type="hidden" name="cveTipocorr" id="cveTipocorr"/>
						<input type="hidden" name="cveFkPatron" id="cveFkPatron"/>
						<input type="hidden" name="domicilioId" id="domicilioId"/>
						<input type="hidden" name="idObligado" id="idObligado"/>
						<input type="hidden" name="idPromovido" id="idPromovido"/>
						<input type="hidden" name="fechaRegistro" id="fechaRegistro"/>
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
										   		<td align="left" width="200px" class="etiqueta2">Folio de Detecci&oacute;n																									
												</td>
												<td align="left" width="100px" >
													<input name="nuFoliodeteccion" id="nuFoliodeteccion" size="25" readonly="readonly"/>
													<label for="nuFoliodeteccion"></label>													
												</td>
											</tr>							   									   
										   <tr valign="top" class="par">   	
										   		<td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Fecha de Detecci&oacute;n																									
												</td>
												<td align="left" width="100px"  >
													<input name="fechaDeteccion" id="fechaDeteccion" readonly="readonly"/>
													<label for="fechaDeteccion"></label>													
												</td>
												<td colspan="2">
													<div id="divNumReporte" style="width:200px;display:none" >
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="140px" class="etiqueta2"><span class="required">*</span>Numero de Reporte: 
														    	</td>
														    	<td align="left" width="100px" >	    		
																   	<input name="nuReportectrlobra" id="nuReportectrlobra" readonly="readonly" size="20" maxlength="5" onkeyup="validaCampo('noCaracteresEspeciales','nuReportectrlobra','deteccionLayoutForm');validaCampo('PermiteSoloNumeros','nuReportectrlobra')"/>
																   	<label for="nuReportectrlobra"></label>																   	
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
										   
										   <tr valign="top" class="par">
										    <td align="left" colspan="4">
										    	<div id="domInegiButtons" align="center">										    		
										    		<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','layout')"><span class="boton">Agregar Domicilio</span></a>													
											   </div>
										    </td>
										   </tr>
										   
										   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Estado: 	</td>
										    <td align="left">									    		
										    		<input name="estado" id="estado" size="50" onkeyup="validaCampo('noCaracteresEspeciales','estado')" readonly="readonly"/>
													<label for="estado"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Municipio: 	</td>
										    <td align="left">									    		
										    		<input name="municipio" id="municipio" size="50" onkeyup="validaCampo('noCaracteresEspeciales','municipio')" readonly="readonly"/>
													<label for="municipio"></label>
										    </td>        
			   							   </tr>
									  	   <tr valign="top" class="par">
										    <td align="left" width="200px"class="etiqueta2"><span class="required">*</span>Calle: </td>
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
										    <td align="left" width="100px" colspan="1"  >
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
											   <td align="left" width="25%" class="etiqueta2">Registro Patronal: 										    			    	
											   </td>
											   <td align="left" width="25%" >
											   		<input name="regPatron" id="regPatron" size="20" maxlength="10" readonly="readonly" onkeyup="validaCampo('noCaracteresEspeciales','regPatron','deteccionLayoutForm')"/>
											   		<label for="regPatron"></label>
											   		<!-- <a href="#" onclick="javascript:validaRegPatron();"><span class="boton">Validar</span></a> -->
											   </td>
											   <td align="left" width="25%" class="etiqueta2">Fecha Estimada de Inicio de la Obra:																									
												</td>
												<td align="left" width="25%" >
													<input name="fechaEstimIncio2" id="fechaEstimIncio2" readonly="readonly" onchange="validafechaSistema('deteccionLayoutForm','fechaEstimIncio2','Fecha Estimada de Inicio')"/>
													<label for="fechaEstimIncio2"></label>
												</td>																							   
										   </tr>
										   <tr valign="top" class="par">
										    <td align="left" width="25%" class="etiqueta2">Nombre &oacute; Raz&oacute;n Social: </td>
										    <td align="left" colspan="3">										    		
										    		<input name="nomRazonsocial" id="nomRazonsocial" readonly="readonly" size="90" maxlength="80" onkeyup="validaCampo('noCaracteresEspeciales','nomRazonsocial','deteccionLayoutForm')"/>
													<label for="nomRazonsocial"></label>
										    </td>										      
			   							   </tr>			   			
											<tr valign="top" class="par">
												<td align="left" width="25%"class="etiqueta2"><span
													class="required">*</span>Clase de la Obra:</td>
												<td align="left" width="25%" colspan="1" >
													<select name="tipClaseobra" id="tipClaseobra">
														<option value="" id="0">Seleccionar</option>
														<option value="Publica" id="1">Publica</option>
														<option value="Privada" id="2">Privada</option>
													</select>
												</td>
												<td align="left" width="25%" colspan="1" class="etiqueta2"><span
													class="required">*</span>Tipo de Promoci&oacute;n:</td>
												<td align="left" width="25%" colspan="1" >
													<select name="idPromovido" id="idPromovido">
														<option value="">Seleccionar</option>
														<option value="1" id="1">SATIC-A</option>
														<option value="0" id="0">EXHORTO DE CONSTRUCCION</option>
													</select>
												</td>
											</tr>
											<tr valign="top" class="par">
										   		<td colspan="4">
										   			<div id="divDatosCenso" style="width:200px;display:none">
										   				<table style="width: 900px">
										   					<tr valign="top" class="par" >
										   						<td align="left" width="25%" class="etiqueta2">Superficie m<sup>2</sup>: 	    		
																</td>
																<td align="left" width="25%" >
																	<input name="canSuperficie" id="canSuperficie" readonly="readonly"  size="20" maxlength="6" onkeyup="validaCampo('noCaracteresEspeciales','canSuperficie','deteccionLayoutForm');validaCampo('PermiteSoloNumeros','canSuperficie','deteccionLayoutForm')"/>
																   	<label for="canSuperficie"></label>
																</td>
										   						<td align="left" width="25%" class="etiqueta2">Fecha Estimada de Termino de la obra:																									
																</td>
																<td align="left" width="25%" >
																	<input name="fechaEstTerm2" id="fechaEstTerm2" readonly="readonly" onchange="validafechaSistema('deteccionLayoutForm','fechaEstTerm2','Fecha Estimada de Inicio');validaFecha('deteccionLayoutForm','fechaEstimIncio2','fechaEstTerm2','Fecha estimada de termino','Fecha estimada de Inicio')"/>
																	<label for="fechaEstTerm2"></label>	
																</td>																
										   					</tr>										   					
										   					<tr valign="top" class="par">
																<td align="left" width="25%" class="etiqueta2">RFC: 	    													   																	
																</td>																
																<td align="left" width="25%" >
																	<input name="txRfcpatron" id="txRfcpatron" readonly="readonly" size="15" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','txRfcpatron','deteccionLayoutForm')"/>
																	<label for="txRfcpatron"></label>
																</td>
																<td align="left" width="25%" class="etiqueta2">Curp Patr&oacute;n: 	    		
															    </td>
															    <td align="left" width="25%">
															   		<input name="txCurppatron" id="txCurppatron" readonly="readonly" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','txCurppatron','deteccionLayoutForm')"/>
															   		<label for="txCurppatron"></label>
															    </td>															    
															</tr>															
															<tr valign="top" class="par">   	
															   <td align="left" width="50%" colspan="2" class="etiqueta2">Tipo de Obra: 	    			    
															   </td>															   
															   <td align="left" width="25%" colspan="2" class="etiqueta2">Fase de la Obra: 	    		
															   </td>															   	   
														   </tr>														   		
														   <tr valign="top" class="par">
														   		<td align="left" width="50%" colspan="2">
																	<select id="selectTipoObra">
																		<option value="-1">--Por favor seleccione--</option>
																	</select>	    
															   	</td>
															   	<td align="left" width="50%" colspan="2">	    						    		
															    		<combo:creaCombo entidad="mx.imss.ctirss.catalogos.model.CrcFaseconstruccion"
																		 idHtml="cvePkFaseConst"
																		 idHtmlContenedor="deteccionLayoutForm"/>	    						
															   	</td>
														   </tr>
														   <tr valign="top" class="par">   												  
															   <td align="left" width="25%" class="etiqueta2">Importe de la Obra: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="impCostoobra" id="impCostoobra" readonly="readonly" size="20" maxlength="12" onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCostoobra','deteccionLayoutForm')"/>
															   		<label for="impCostoobra"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta2">Porcentaje de Avance: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="porAvanceobraEst" id="porAvanceobraEst" readonly="readonly"  size="20" maxlength="3" onkeyup="validaCampo('noCaracteresEspeciales','porAvanceobraEst','deteccionLayoutForm');validaCampo('PermiteSoloNumeros','porAvanceobraEst','deteccionLayoutForm');valorMaxim('porAvanceobraEst','Porcentaje Regularizado','deteccionLayoutForm')"/>
															   		<label for="porAvanceobraEst"></label>
															   </td>		   
															</tr>															
															<tr valign="top" class="par">   												  
															   <td align="left" width="25%" class="etiqueta2">E-mail: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="txEmail" id="txEmail" size="50" readonly="readonly"  maxlength="150"/>
															   		<label for="txEmail"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta2">Telefono: 	    		
															   </td>
															   <td align="left" width="25%">
															   		<input name="txTelefono" id="txTelefono" size="20" readonly="readonly" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','txTelefono','deteccionLayoutForm')"/>
															   		<label for="txTelefono"></label>
															   </td>
														   </tr>
										   				</table>											   
											   		</div>
										   		</td>											   
										   </tr>
										   <tr valign="top" class="par">
										   		<td align="left" width="25%" class="etiqueta2">Dependencia: 	    		
												</td>
												<td align="left" width="25%" >
													<input name="desDependenciapub" id="desDependenciapub" readonly="readonly" size="50" />
													<label for="desDependenciapub"></label>
												</td>
												<td align="left" width="25%" class="etiqueta2">Dependencia Contratante: 	    		
												</td>
												<td align="left" width="25%" >
													<input name="desDepcontratante" id="desDepcontratante" readonly="readonly" size="50" />
													<label for="desDepcontratante"></label>
												</td>
										   </tr>																					
										   <tr valign="top" class="par">   												  
											   <td align="left" width="25%" class="etiqueta2">Zona Salarial: 
											   </td>
											   <td align="left" width="25%">	    						    		
											    	<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacZona"
																		 idHtml="cveFkZona"
																		 idHtmlContenedor="deteccionLayoutForm"/>
											   </td>
											   <td align="left" width="25%" class="etiqueta2">N&uacute;mero de Trabajadores: 											    	
											   </td>
											   <td align="left" width="25%">	    						    													   	    															
													<input name="numTrabajdores" id="numTrabajdores" readonly="readonly" size="20" maxlength="5" onkeyup="validaCampo('noCaracteresEspeciales','numTrabajdores','deteccionLayoutForm');validaCampo('PermiteSoloNumeros','numTrabajdores','deteccionLayoutForm')"/>
													<label for="numTrabajdores"></label>																   													
											   </td>	   											  		   
										   </tr>
										   <tr valign="top" class="par">
										   		<td colspan="2">
													<div id="divActividad" style="width:200px;display:none" >
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="25%" class="etiqueta2">Actividad:
														    	</td>
														    	<td align="left" width="25%">	    																		   	
																   	<input name="actividad" id="actividad" readonly="readonly" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','actividad','deteccionLayoutForm')" readonly="readonly"/>
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
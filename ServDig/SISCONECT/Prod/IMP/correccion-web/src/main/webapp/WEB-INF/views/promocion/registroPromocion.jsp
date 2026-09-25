<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    <div id="dgPromocionRegistro"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogPromocionRegistro" style="background-color: #f2fff2;">	
					<form action=""
						method="post" id="promocionFormRegPromocion">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion"/>
						<input type="hidden" name="cveTipocorr" id="cveTipocorr"/>
						<input type="hidden" name="cveFkPatron" id="cveFkPatron"/>
						<input type="hidden" name="cveDomGeoPatron" id="cveDomGeoPatron"/>
						<input type="hidden" name="idTipo" id="idTipo"/>
						<input type="hidden" name="idOrigen" id="idOrigen"/>						
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos de Promoci&oacute;n</td>
									  	</tr>
									  </thead>
									  <tbody>									  		
									  	<tr valign="top" class="par">
									  		<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Criterio de Selecci&oacute;n: 	    		
											</td>
									  		<td align="left" width="25%" colspan="3">												
												<select id="idCriterioSeleccion" name="idCriterioSeleccion">
												   <option value="">--Por favor seleccione--</option>
											    </select><label for="idCriterioSeleccion" >  </label>			    
											</td>
									  	</tr>
									  	<tr valign="top" class="par">   	
									  		<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Numero de Oficio: 	    		
											</td>
											<td align="left" width="25%">
												<input name="nuOficiopro" id="nuOficiopro" size="20" maxlength="18"/>
												<label for="nuOficiopro"></label>
											</td>
											<td align="left" width="25%" colspan="1" class="etiqueta2">Numero de Registro de Obra: 
											</td>
											<td align="left" width="25%">
												<input name="cveNroregobraSatic" id="cveNroregobraSatic" size="20" maxlength="12" onkeyup="validaCampo('PermiteSoloNumeros','cveNroregobraSatic','promocionFormRegPromocion')"/>
												<label for="cveNroregobraSatic"></label>
											</td>											   																							   	 
										</tr>									  									  											  
										<tr valign="top" class="par">
											<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Fecha de Oficio de Promoci&oacute;n																									
											</td>
											<td align="left" width="25%">
												<input name="fechaOficio" id="fechaOficio" readonly="readonly" onchange="validafechaSistema('promocionFormRegPromocion','fechaOficio','Fecha de Oficio');"/>
												<label for="fechaOficio"></label>
											</td>   	
											<td align="left" width="25%" class="etiqueta2">Fecha de Notificaci&oacute;n																									
											</td>
											<td align="left" width="25%">
												<input name="fechaNotificacion" id="fechaNotificacion" readonly="readonly" onchange="validafechaSistema('promocionFormRegPromocion','fechaNotificacion','Fecha de Notificación');
												validaFecha('promocionFormRegPromocion','fechaOficio','fechaNotificacion','Fecha de Notificación','Fecha de Oficio')"/>
												<label for="fechaNotificacion"></label>
											</td>																																
										 </tr>
										 <tr valign="top" class="par">   	
											<td align="left" width="25%" class="etiqueta2">Fecha de Atenci&oacute;n																									
											</td>
											<td align="left" width="25%">
												<input name="fecFechaAtencion" readonly="readonly" disabled="disabled"/>
												<label for="fecFechaAtencion"></label>
											</td>
											<td align="left" width="25%" class="etiqueta2">Fecha de Regularizaci&oacute;n																									
											</td>
											<td align="left" width="25%">
												<input name="fecFecharegulariza" readonly="readonly" disabled="disabled"/>
												<label for="fecFecharegulariza"></label>
											</td>																						
										 </tr>
										 <tr valign="top" class="par">   	
											<td align="left" width="25%" class="etiqueta2">Fecha PAI																									
											</td>
											<td align="left" width="25%">
												<input name="fecFechapai" readonly="readonly" disabled="disabled"/>
												<label for="fecFechapai"></label>
											</td>																																
										 </tr>
										 <tr valign="top" class="impar">
										   	<td align="left" colspan="4">&nbsp;</td>
										 </tr>
									  </tbody>
									  <thead>
									  	<tr >
									  		<td colspan="4">Domicilio del Patr&oacute;n</td>
									  	</tr>
									  </thead>
									  <tbody>
											<tr valign="top" class="impar">
												<td align="left" colspan="4">&nbsp;</td>
											</tr>
											<tr valign="top" class="par">
												<td align="left" colspan="4">
													<div id="domInegiButtons" align="center">
														<a href="#"
															onclick="mostarDomGeo('<%=request.getContextPath()%>')"><span
															class="boton">Agregar Domicilio</span>
														</a>
													</div></td>
											</tr>
											<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										   </tr>
										   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Estado:
										    </td>
										    <td align="left" width="200px"> 										    		
										    	<input name="estado" id="estado" size="40" readonly="readonly"/>
												<label for="estado"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Municipio:
										    </td>
										    <td align="left" width="200px"> 										    		
										    	<input name="municipio" id="municipio" size="40" readonly="readonly"/>
												<label for="municipio"></label>
										    </td>        
			   							   </tr>										   
										   <tr valign="top" class="par">   	
											   <td align="left" width="100px" class="etiqueta2"><span class="required">*</span>Calle: 	    		    
											   </td>
											   <td align="left" width="100px">	    		
											    		<input name="domCalle" id="domCalle"  size="40" readonly="readonly"/>
											    		<label for="domCalle"></label>	    	
											   </td>
											   <td align="left" width="100px" class="etiqueta2"><span class="required">*</span>Colonia: 	    		
											   </td>	   
											   <td align="left" width="100px">	    		
											    		<input name="refColonia" id="refColonia"   size="40" readonly="readonly"/>
											    		<label for="refColonia" ></label>
											   </td>	   
										   </tr>
										   <tr valign="top" class="par">
											    <td align="left" width="100px" colspan="1"class="etiqueta2">N&uacute;mero Interior:										    												    	
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numNroint" id="numNroint"   size="12" readonly="readonly"/>
													<label for="numNroint" ></label>
												</td>
											    <td align="left" width="100px" colspan="1"class="etiqueta2"><span class="required">*</span>N&uacute;mero Exterior:										    												    		
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numNroext" id="numNroext"   size="12" readonly="readonly"/>
													<label for="numNroext" ></label>
												</td>
				   							</tr>
				   							<tr valign="top" class="par">
											    <td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span>Codigo Postal:										    												    	
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numCodigopostal" id="numCodigopostal"   size="12" readonly="readonly"/>
													<label for="numCodigopostal" ></label>
												</td>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
			    
		    <div id="dgDeteccionRegistro" title="Domicilios Geogr&aacuteficos" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form action="/catalogo/deteccion/agregar.do"
						method="post" id="deteccionFormRegistro">
						<input type="hidden" name="cveFkPatron" id="cveFkPatron">
						<input type="hidden" name="domicilioId" id="domicilioId">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
						<input type="hidden" name="bandera" id="bandera">
						<input type="hidden" name="valRegPat" id="valRegPat" value="0">
						
						
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
										    <td align="left" width="100px" colspan="1" class="etiqueta"><span class="required">*</span>Origen:										    												    												    	
										    </td>    
										    <td align="left" width="200px" colspan="1">										    	
										    	<input type="radio" name="cveTipocorr" id="cveTipocorr" value="8" onclick="activaDiv()"/>Fuente Externa
										    	<div id="labelcveTipocorr"></div>
										    </td>
										    <td align="left" width="100px" colspan="2">
										    	<input type="radio" name="cveTipocorr" id="cveTipocorr" value="9" onclick="activaDiv()" checked="checked" />Detecci&oacute;n
										    	<label class="required" for="cveTipocorr"></label>
										    </td>										    
			   							   </tr>
										   
										   <tr valign="top" class="par" id="nmCensor">
										    <td align="left" width="100px" colspan="1"class="etiqueta"><span class="required">*</span>Nombre del Censor: 										    												    		
										    </td>
										    <td align="left" width="100px" colspan="3">												
												 <select id="cveCensor" name="cveCensor" onchange="jsLlenaInfoCensores(this.value);">
												   <option value="">--Por favor seleccione--</option>
											    </select> 
											    <label id="valorIdCensors"></label>
											    <div id="labelcveCensor"></div>	    
											</td>											
			   							   </tr>										   
										   <tr valign="top" class="par" id="dtCensor">   	
											   <td align="left" width="100px" class="etiqueta">NSS: 
											   </td>
											   <td align="left" width="100px">
											   		<input name="nss" id="nss" size="50" readonly="readonly" style="text-align: right;"></input>
											   	</td>
											   <td align="left" width="110px" class="etiqueta">Matr&iacute;cula: 	
											   </td>
											   <td align="left" width="90px">
											   		<input name="matricula" style="text-align: right;" id="matricula" size="20" readonly="readonly"></input>
											   	</td>		   
										   </tr>										   
										   <tr valign="top" class="par">   	
										   		<td align="left" width="200px" class="etiqueta"><span class="required">*</span>Fecha de Detecci&oacute;n																									
												</td>
												<td align="left" width="100px" >
													<!-- input name="fechaDeteccion" id="fechaDeteccion" onchange="validafechaSistema('deteccionFormRegistro','fechaDeteccion','Fecha de Detecci&oacute;n')"/ -->
													<input name="fechaDeteccion" style="text-align: center;" id="fechaDeteccion" readonly="readonly" onchange="jsValidaFechaDetFecIni();"/>
													<span class="boton_limpiar" id="btnLimpiafechaDetecc2" onclick="$('form#deteccionFormRegistro #fechaDeteccion').val('');">X</span>
													<label id="labelfechaDeteccion"></label>													
												</td>
												<td colspan="2">
													<div id="divNumReporte">
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="140px" class="etiqueta"><span class="required">*</span>N&uacute;mero de Reporte: 
														    	</td>
														    	<td align="left" width="100px">	    		
																   	<input name="nuReportectrlobra" style="text-align: right;" id="nuReportectrlobra" size="20" maxlength="4"  
																   		   	onkeyup="validaCampo('PermiteSoloNumeros','nuReportectrlobra','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','nuReportectrlobra');"
														   		   			onblur="jsValidaNUmReporte();" onchange="$('form#deteccionFormRegistro #labelnuReportectrlobra').html('');"/>
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
										    <td align="left" width="200px" class="etiqueta"><span class="required">*</span>Estado:</td>
										    <td align="left"> 										    		
										    		<input name="estado" id="estado" size="50" onkeyup="validaCampo('noCaracteresEspeciales','estado')" readonly="readonly"/>
													<label for="estado"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta"><span class="required">*</span>Municipio:</td>
										    <td align="left"> 										    		
										    		<input name="municipio" id="municipio" size="50" onkeyup="validaCampo('noCaracteresEspeciales','municipio')" readonly="readonly"/>
													<label for="municipio"></label>
										    </td>        
			   							   </tr>
									  	   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta"><span class="required">*</span>Calle: 	</td>
										    <td align="left">									    		
										    		<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly"/>
													<label for="domCalle"></label>
										    </td>
										    <td align="left" width="200px" class="etiqueta"><span class="required">*</span>Colonia: 	</td>
										    <td align="left">									    		
										    		<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly"/>
													<label for="refColonia"></label>
										    </td>        
			   							   </tr>
			   							   <tr valign="top" class="par">										    
										    <td align="left" width="100px" colspan="1"  class="etiqueta"><span class="required">*</span>N&uacute;mero Exterior: 										    												    		
										    </td>
										    <td align="left" width="100px" colspan="1">
										    	<input name="numNroext" id="numNroext" size="12" style="text-align: right;" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly"/>
												<label for="numNroext"></label>
											</td>
											<td align="left" width="100px" colspan="1" class="etiqueta">N&uacute;mero Interior: 										    												    	
										    </td>
										    <td align="left" width="100px" colspan="1"  >
										    	<input name="numNroint" id="numNroint" style="text-align: right;" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly"/>
												<label for="numNroint"></label>
											</td>
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px" colspan="1" class="etiqueta"><span class="required">*</span>C&oacute;digo Postal: 										    												    	
										    </td>
										    <td align="left" width="100px" colspan="1"  >
										    	<input name="numCodigopostal" id="numCodigopostal" size="12" style="text-align: right;" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly"/>
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
											   <td align="left" width="15%" class="etiqueta">Registro Patronal:</td>
											   <td align="left" width="35%" >
											   		<input name="regPatron" id="regPatron" style="text-align: center;" size="20" maxlength="10" onchange="mayusculasTextField(this);" onkeyup="mayusculasTextField(this); limpiaCampos();"   onkeypress="return KeyPressed(this,event,'alphanumeric', null, 'uppercase=no', null, 'no');"/>
											   		<label id="labelRegPatron"></label>
											   		<a href="#" onclick="javascript:validaRegPatronAlta()	;"><span class="boton">Validar</span></a>
											   </td>
											   <td align="left" width="20%" class="etiqueta">Fecha Estimada de Inicio de la Obra:																									
												</td>
												<td align="left" width="30%" >
													<input name="fechaEstimIncio2" id="fechaEstimIncio2" readonly="readonly" style="text-align: center;"/>
													<span class="boton_limpiar" id="btnLimpiafechaEstimIncio2" onclick="$('form#deteccionFormRegistro #fechaEstimIncio2').val('');">X</span>
													<label id="LabelfechaEstimIncio2"></label>
												</td>																							   
										   </tr>
										   <tr valign="top" class="par">
										   		<td align="left" width="15%" class="etiqueta">Nombre &oacute; Raz&oacute;n Social:</td>
											    <td align="left" width="55%" colspan="2" > 										    		
											    		<input name="nomRazonsocial" id="nomRazonsocial" size="90" maxlength="80" onkeypress="return KeyPressed(this,event,'alphanumeric', null, 'uppercase=no', null, 'no');" />
														<label for="nomRazonsocial"></label>
											    </td>										      
			   							   </tr>			   			
											<tr valign="top" class="par">
												<td align="left" width="15%" class="etiqueta"><span
													class="required">*</span>Clase de la Obra:</td>
												<td align="left" width="35%" >
													<select name="tipClaseobra" id="tipClaseobra" onchange="$('form#deteccionFormRegistro #labeltipClaseobra').html('');">
														<option value="" id="0">Seleccionar</option>
														<option value="Publica" id="1">P&uacute;blica</option>
														<option value="Privada" id="2">Privada</option>
													</select>
													<div id="labeltipClaseobra"></div>
												</td>
												<td align="left" width="20%" class="etiqueta"><span
													class="required">*</span>Tipo de Promoci&oacute;n:</td>
												<td align="left" width="30%" >
													<select name="idPromovido" id="idPromovido" onchange="$('form#deteccionFormRegistro #labelPromovido').html('');">
														<option value="">Seleccionar</option>
														<option value="1" id="1">SATIC A</option>
														<option value="0" id="0">EXHORTO DE CONSTRUCCION</option>
													</select>
													<div id="labelPromovido"></div>
												</td>
											</tr>
											<tr valign="top" class="par">
										   		<td colspan="4">
										   			<div id="divDatosCenso">
										   				<table style="width: 900px" >
										   					<tr valign="top" class="par" >
										   						<td align="left" style="text-align: left;" width="15%" class="etiqueta">Superficie m<sup>2</sup>: 	    		
																</td>
																<td align="left" width="25%" >
																	<input name="canSuperficie" style="text-align: right;" id="canSuperficie" size="20" maxlength="20" onkeypress="return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="validaMaximo('canSuperficie', '999999999.99', ' la superficie');"/>
																   	<label for="canSuperficie"></label>
																</td>
										   						<td align="left" width="25%" class="etiqueta">Fecha Estimada de T&eacute;rmino de la obra:																									
																</td>
																<td align="left" width="25%" >
																	<input name="fechaEstTerm2" id="fechaEstTerm2" readonly="readonly" style="text-align: center;" />
																	<span class="boton_limpiar" id="btnfechaEstTerm2" onclick="$('form#deteccionFormRegistro #fechaEstTerm2').val('');">X</span>
																	<label id="LabelfechaEstTerm2"></label>	
																</td>																
										   					</tr>										   					
										   					<tr valign="top" class="par">
																<td align="left" width="15%" class="etiqueta">RFC: 	    													   																	
																</td>																
																<td align="left" width="25%" >
																	<input name="txRfcpatron" id="txRfcpatron" style="text-align: center;" size="15" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','txRfcpatron','deteccionFormRegistro')" onchange="validaRfc('txRfcpatron');"/>
																	<div id="labeltxRfcpatron"></div>
																</td>
																<td align="left" width="25%" class="etiqueta">CURP Patr&oacute;n: 	    		
															    </td>
															    <td align="left" width="25%">
															   		<input name="txCurppatron" id="txCurppatron" style="text-align: center;" size="20" maxlength="18" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);"/>
															   		<div id="labeltxCurppatron"></div>
															    </td>															    
															</tr>															
															<tr valign="top" class="par">   	
															   <td align="left" width="50%" colspan="2" class="etiqueta">Tipo de Obra: 	    			    
															   </td>															   
															   <td align="left" width="50%" colspan="2" class="etiqueta">Fase de la Obra: 	    		
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
															   <td align="left" width="25%" class="etiqueta">Importe de la Obra: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="impCostoobra" style="text-align: right;" id="impCostoobra" size="30" maxlength="20" onkeypress="return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="validaMaximo('impCostoobra', '999999999999.99', ' el importe de la obra');"/>
															   		<label for="impCostoobra"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta">Porcentaje de Avance: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="porAvanceobraEst" id="porAvanceobraEst" style="text-align: right;" size="20" maxlength="3" onkeyup="validaCampo('noCaracteresEspeciales','porAvanceobraEst','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','porAvanceobraEst','deteccionFormRegistro');valorMaxim('porAvanceobraEst','Porcentaje Regularizado','deteccionFormRegistro')"/>
															   		<label for="porAvanceobraEst"></label>
															   </td>		   
															</tr>															
															<tr valign="top" class="par">   												  
															   <td align="left" width="25%" class="etiqueta">E-mail: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="txEmail" id="txEmail" size="50" maxlength="150" onchange="$('form#deteccionFormRegistro #labelMail').html('');" />
															   		<label for="txEmail" id="labelMail" style="color: red;"></label>
															   </td>
															   <td align="left" width="25%" class="etiqueta">Tel&eacute;fono: 	    		
															   </td>
															   <td align="left" width="25%" >
															   		<input name="txTelefono" id="txTelefono" style="text-align: center;" size="20" onchange="$('form#deteccionFormRegistro #labelTelefono').html('');" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','txTelefono','deteccionFormRegistro')" onkeypress="return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');"/>
															   		<label for="txTelefono" id="labelTelefono" style="color: red;"></label>
															   </td>
														   </tr>
										   				</table>											   
											   		</div>
										   		</td>											   
										   </tr>
										   <tr valign="top" class="par">
										   		<td align="left" width="25%" class="etiqueta">Dependencia:</td>
												<td align="left" width="25%" >
													<input name="desDependenciapub" id="desDependenciapub" size="50" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','desDependenciapub','deteccionFormRegistro')"/>
													<label for="desDependenciapub"></label>
												</td>
												<td align="left" width="25%" class="etiqueta">Dependencia Contratante: 	    		
												</td>
												<td align="left" width="25%" >
													<input name="desDepcontratante" id="desDepcontratante" size="50" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','desDepcontratante','deteccionFormRegistro')"/>
													<label for="desDepcontratante"></label>
												</td>
										   </tr>																					
										   <tr valign="top" class="par">   												  
											   <td align="left" width="25%" class="etiqueta">Zona Salarial: </td>
											   <td align="left" width="25%">	    						    		
											    	<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacZona"
																		 idHtml="cveFkZona"
																		 idHtmlContenedor="deteccionFormRegistro"/>
											   </td>
											   <td align="left" width="25%" class="etiqueta">N&uacute;mero de Trabajadores: 											    	
											   </td>
											   <td align="left" width="25%">	    						    													   	    															
													<input name="numTrabajdores" id="numTrabajdores" size="20" maxlength="5" style="text-align: right;" onkeyup="validaCampo('noCaracteresEspeciales','numTrabajdores','deteccionFormRegistro');validaCampo('PermiteSoloNumeros','numTrabajdores','deteccionFormRegistro')"/>
													<label for="numTrabajdores"></label>																   													
											   </td>	   											  		   
										   </tr>
										   <tr valign="top" class="par">
										   		<td colspan="2">
													<div id="divActividad" >
														<table  style="width: 400px" >
															<tr>
																<td align="left" width="25%" class="etiqueta">Actividad:
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
		    <div id="saticaSeguimientoTab"  style="background-color: white !important; ">
			    <div id="wrapperSaticaSeguimientoTab" style="background-color: #f2fff2;">	
					<form:form action="seguimiento/satica/guardarSaticaSeguimiento.do"	method="post" id="saticASeguimientoTabForm" modelAttribute="seguimientoSaticaVO">
						<form:hidden type="hidden" path="cvePromocion" id="cvePromocionSaticaSeg"/>
						<form:hidden type="hidden" path="cveFkPatron" id="cveFkPatronSaticaSeg"/>
						<form:hidden type="hidden" path="estatusPromocion" id="estatusPromocionSaticaSeg"/>
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
<!-- 								  	  <thead>
									  	<tr >
									  		<td colspan="4">Seguimiento</td>
									  	</tr>
									  </thead> -->
									  <tbody>
									  
 									  	<tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										</tr>
										    <tr valign="top" class="par">
										    	<td align="left" width="25%" class="etiqueta2">Fecha de Notificaci&oacute;n del Oficio:																									
													</td>
												<td align="left" width="25%">
													<form:input path="saticaSeguimientoTabVO.fecNotificacionOficio" id="fechaNotificacionSaticaSeg" onchange="validaFechaNotificacionSaticaSeg();reglasSaticA();" size="12"/>
													<span class="boton_limpiar" onclick="javascript:limpiaFechaNotificacionSaticaSeg()" id="spnFechaNotificacionSaticaSeg">X</span>
													<div id="labelFechaNotificacionSaticaSeg"></div>
												</td>
										   		<td align="left" width="25%" class="etiqueta2">Fecha de Cancelaci&oacute;n del Oficio:																									
												</td>
												<td align="left" width="25%">
													<form:input path="saticaSeguimientoTabVO.fecCancelacionOficio" id="fechaCancelacionOfSaticaSeg" size="12" readonly="true"/>
													<label for="fechaCancelacionOfSaticaSeg"></label>
												</td>
											</tr>										
									  	<tr valign="top" class="par">   	
										   		<td align="left" width="25%" class="etiqueta2">Fecha de Derivaci&oacute;n Subdelegaci&oacute;n:
												</td>
												<td align="left" width="25%" colspan="3">
													<form:input path="saticaSeguimientoTabVO.fecDerivaSubDel" id="fechaDerivacionSubDelSaticaSeg" size="12" readonly="true"/>
													<label for="fechaDerivacionSubDelSaticaSeg"></label>
												</td>
										</tr>		
									  	<tr valign="top" class="par">   	
												<td align="left" width="25%" class="etiqueta2">Fecha de Derivaci&oacute;n a Fiscalizaci&oacute;n:	    		
											   </td>
												<td align="left" width="25%">
												   		<form:input path="saticaSeguimientoTabVO.fecDerivaFiscalizacion" id="fechaDerivacionFisSaticaSeg" size="12" maxlength="12" readonly="true"/>
												   		<label for="fechaDerivacionFisSaticaSeg"></label>
												</td>												 
											   <td colspan="2">&nbsp;
												</td>											   	   
										   </tr>
									  	<tr valign="top" class="par">   	
												<td align="left" width="25%" class="etiqueta2">Fecha de Atenci&oacute;n del Oficio:	    		
											   </td>
												<td align="left" width="25%">
												   		<form:input path="saticaSeguimientoTabVO.fecAtencionOficio" id="fechaAtnOficioSaticaSeg" onChange="validaFechaAtencionSaticaSeg();" size="12" maxlength="12" readonly="true"/>
												   		<span class="boton_limpiar" onclick="javascript:limpiaFechaAtencionSaticaSeg()" id="spnFechaAtencionSaticaSeg">X</span>
												   		<div id="labelFechaAtencionSaticaSeg"></div>
												</td>												 
											   <td align="left" class="etiqueta2">N&uacute;mero de Registro de Obra:</td>		
	   												<td align="left" width="25%" >
												   		<form:input path="saticaSeguimientoTabVO.numRegistroObra" id="numRegistroObraSaticaSeg" size="15" maxlength="12" onchange="reseteaValObra();"/>		
												   		<input type="button" class="boton" onclick="javascript:validarDomVsSatic();" value="Validar" id="btnValidaRegistroObraSaticaSeg">										   		
												   		<div class='etiquetaError' id="labelNumRegistroObraSaticaSeg"></div>
												</td>
										   </tr>										   

									  	<tr valign="top" class="par">   	<!-- regularizaObra -->
												<td align="left" width="25%" >
												<input type="checkbox" id="regularizaObraSaticaSeg" onclick="checkRegularizarObraSaticaSeg()"/><span class="etiqueta2"> Regulariza Obra:</span>
											   </td>
												 
											   <td colspan="2" ><span class="etiqueta2"> Per&iacute;odo de Correcci&oacute;n<BR>Del: </span>
												   		<form:input path="saticaSeguimientoTabVO.fecIniPeriodo" id="fechaInicioPCSaticaSeg" size="12" maxlength="12" readonly="true"/>

												   		<BR><BR>&nbsp;&nbsp;<span class="etiqueta2"> Al: </span> 
												   		<form:input path="saticaSeguimientoTabVO.fecFinPeriodo" id="fechaFinPCSaticaSeg" size="12" maxlength="12"  readonly="true"/>
											   
												</td>	
												<td>&nbsp;</td>										   	   
										   </tr>	
   										   <tr valign="top" class="par">
												<td align="left" width="25%" colspan="4"><span class="etiqueta2"> Observaciones: <BR> </span>
												<form:textarea path="saticaSeguimientoTabVO.observaciones" id="observacionesSaticaSeg" cols="100" rows="4" onmouseup="validaCampo('noCaracteresEspeciales','observacionesSaticaSeg','saticASeguimientoTabForm');if(this.value.length > 200){ this.value=this.value.substring(0,200); }" onkeyup="validaCampo('noCaracteresEspeciales','observacionesSaticaSeg','saticASeguimientoTabForm');if(this.value.length > 200){ this.value=this.value.substring(0,200); }"/>    		
											   </td>
										   </tr>

										  	<tr valign="top" class="par">										  	
										    <td align="center" width="100px"  colspan="4" >
										    	<input type="button" class="boton" onclick="javascript:procesaFormularioSaticaSeg('validaCamposSaticaSeg()');" value="Guardar"> 										    												    	
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

						
					</form:form>							    
		    	</div>
			</div>
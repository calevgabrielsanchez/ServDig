<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
	
		    <div id="derivarSubdelegacionGenericoTab" title="Derivar a otra Subdelegacion" >
			    <div id="wrapperderivarSubdelegacionGenericoTab" >	
					<form:form modelAttribute="seguimientoGenericoVO" method="post" id="derivarSubdelegacionGenericoTabForm" action="seguimiento/generico/derivarSubdelegacion.do" >
						<form:hidden path="cvePromocion" name="cvePromocion" id="cvePromocion"/>
						<form:hidden path="nombreFuncionarioRegistra" name="hidFuncionarioRegistra" id="hidFuncionarioRegistra"/>
						<input type="hidden" name="fechaEmisionOficioGenerico" id="fechaEmisionOficioGenerico" >
						<input type="hidden" name="tieneDatosPatronObraDerSubGT" id="tieneDatosPatronObraDerSubGT" value="false">
						<form:hidden path="fechaNotificacionOficio" name="fechaNotificacionOficio" id="fechaNotificacionOficioGenerico"/>
						<form:hidden path="segDerivaSubdelTabVo.cveFkPatronFis" name="cveFkPatronFis" id="cveFkPatronFis"/>
						<form:hidden path="segDerivaSubdelTabVo.cveFkPatronObra" name="cveFkPatronObra" id="cveFkPatronObra"/>
						<form:hidden path="segDerivaSubdelTabVo.cveSubdelegacionDestino" name="cveSubdelegacionDestino" id="cveSubdelegacionDestino"/>
						<form:hidden path="subdelegacionFuncionarioReg" name="subdelegacionFuncionarioReg" id="subdelegacionFuncionarioReg"/>						

						<!-- <fieldset> -->
							<table  class="tablaverde2" width="100%" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" width="100%" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Domicilio Fiscal</td>
									  	</tr>
									  </thead>
									   <tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>
										   
										   <tr valign="top" class="par">   												  
											   <td align="left" width="15%" class="etiqueta2"><span class="required">*</span>Registro Patronal: 	    		
											   </td>
											   <td align="left" width="35%">
											   		<form:input path="segDerivaSubdelTabVo.regPatronalFis" id="regPatronDerivarSubdelFis" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronDerivarSubdelFis','derivarSubdelegacionGenericoTabForm')" onChange="borraDatosRPFiscalSGT();" />											   		
											   		&nbsp;<input type="button" class="boton" onclick="javascript:validarRegistroPatronalFiscal(document.getElementById('regPatronDerivarSubdelFis').value);" value="Validar">
											   		<div id="labelRegPatronDerivarSubdelFis"></div>
											   </td>
										    <td align="left" width="100%" colspan="2" ><span class="etiqueta2">Nombre &oacute; Raz&oacute;n Social: </span>
										     		<label id="razonSocialDerivarSubdelFis"></label>								    		
<%-- 										    		<form:input path="segDerivaSubdelTabVo.razonSocialFis" id="razonSocialDerivarSubdelFis" size="90" maxlength="80" readonly="true" /> --%>
										    </td>										      
										   </tr>										   										  
										   
									  	   <tr valign="top" class="par">
										    <td align="left" width="200px" colspan="2"><span class="required">*</span><span class="etiqueta2">Calle: </span> 										    		
										    		<input name="calleDerivarSubdel" id="calleDerivarSubdel" size="50" readonly="readonly"/>
													<div id="labelCalleDerivarSubdel"></div>
										    </td>
										    <td align="left" width="200px" colspan="2"><span class="required">*</span><span class="etiqueta2">Colonia: </span>									    		
										    		<input name="coloniaDerivarSubdel" id="coloniaDerivarSubdel" size="50" readonly="readonly"/>
													<div id="labelColoniaDerivarSubdel"></div>
										    </td>        
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px"><span class="required">*</span><span class="etiqueta2">N&uacute;mero Exterior: </span>										    												    		
										    </td>
										    <td align="left" width="100px">
										    	<input name="numExtDerivarSubdel" id="numExtDerivarSubdel" size="12" readonly="readonly"/>
												<div id="labelNumExtDerivarSubdel"></div>
											</td>			   							   
										    <td align="left" width="100px"><span class="etiqueta2">N&uacute;mero Interior: </span>
										    	<input name="numIntDerivarSubdel" id="numIntDerivarSubdel" size="12" readonly="readonly"/>
										    </td>
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="100px"><span class="required">*</span><span class="etiqueta2">C&oacute;digo Postal: </span>									    												    	
										    </td>
										    <td align="left" width="100px">
										    	<input name="codigoPostalDerivarSubdel" id="codigoPostalDerivarSubdel" size="12" readonly="readonly"/>
										    	<div id="labelCodigoPostalDerivarSubdel"></div>
											</td>
											<td align="center" colspan="2">
											<input type="button" class="boton" onclick="javascript:cargaDomicilioDerSubDGT();" value="Agregar/Modificar Domicilio">
											</td>											
										   </tr>

									</table>
									<div id="datosPatronObraDerSubGT" style="display: none;">
									<table class="tablaverde2" width="100%">
										<thead>
										  	<tr >
										  		<td colspan="4">Patr&oacute;n Responsable de la Obra</td>
										  	</tr>
									  	</thead>
										<tbody>
									  	   <tr valign="top" class="par">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>									  	   				  
										   <tr valign="top" class="par">   												  
											   <td align="left" width="15%"><span class="required">*</span><span class="etiqueta2">Registro Patronal: </span> 	    		
											   </td>
											   <td align="left" width="35%" >
											   		<form:input path="segDerivaSubdelTabVo.regPatronalObra" id="regPatronDerivarSubdelObra" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronDerivarSubdelObra','derivarSubdelegacionGenericoTabForm')"/>
											   		&nbsp;<input type="button" class="boton" onclick="javascript:validarRegistroPatronalObra(document.getElementById('regPatronDerivarSubdelObra').value);" value="Validar">
											   		<div id="labelRegPatronDerivarSubdelObra"></div>											   		
											   </td>
										    <td align="left" width="100%" colspan="2"><span class="etiqueta2">Nombre &oacute; Raz&oacute;n Social: </span>
										    	<label id="razonSocialDerivarSubdelObra"></label> 										    		
<%-- 										    		<form:input path="segDerivaSubdelTabVo.razonSocialObra" id="razonSocialDerivarSubdelObra" size="90" maxlength="80" readonly="true" /> --%>
										    </td>										      
										   </tr>										   										  
										    <tr valign="top" class="par">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>										   
									  </tbody>
									</table>	
									</div>
																	
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
										    <td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span>Fecha de la derivaci&oacute;n:
										    </td>
										    <td align="left" width="100px">
										    	<form:input path="segDerivaSubdelTabVo.fechaDerivacionSubdel" id="fechaDerivarSubdel" size="12" readonly="true" onchange="javascript:validaFechaEmisionDerSubDGT();"/>
										    	<span class="boton_limpiar" onclick="javascript:limpiaFechaDerivacionSubdelGenerico()" id="spnFechaDerivarSubdel">X</span>												
											</td>			   							   
										    <td align="left" width="100px" class="etiqueta2">Subdelegaci&oacute;n Destino: 										    												    	
										    </td>
										    <td align="left" width="100px">
										    	<input id="descSubdelDestDerivarSubdel" size="50" readonly="readonly" />
										    	<div id="labelDescSubdelDestDerivarSubdel"></div>
											</td>
										   </tr>										   
										  	<tr valign="top" class="par">
										  	<td colspan="2" ><div id="labelFechaDerivarSubdel"></div></td>
										    <td align="left" width="100px" class="etiqueta2">Funcionario que registra: 										    												    	
										    </td>
										    <td align="left" width="100px">
										    	<label id="funcionarioRegistraDerivarSubdel"></label>
											</td>
										  	</tr>
										  	<tr valign="top" class="par">										  	
										    <td align="center" width="100px" colspan="4" >
										    	<input type="button" class="boton" onclick="javascript:procesaFormularioDerivacionSub('validaCamposDerivacionSub()');" value="Confirmar">												 										    												    	
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
							<input  type="hidden"  id="functionAuxDerSubdelegacion"/>													
						<!-- </fieldset> -->
						
					</form:form>							    
		    	</div>
		    	
			</div>



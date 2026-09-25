<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    <div id="dgRegistroPromocionSB"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistroPromocionSB" style="background-color: #f2fff2;">	
					<form:form modelAttribute="crtPromocion" action=""	method="post" id="formRegistroPromocionSB">
						<form:hidden path="cveDeteccion" id="cveDeteccion"/>
						<form:hidden path="cveSelector" id="cveSelector"/>
						<form:hidden path="idCriterioSeleccion" id="idCriterioSeleccion"/>
						<form:hidden path="cveTipocorr" id="cveTipocorr"/>
						<form:hidden path="cveFkPatron" id="cveFkPatron"/>
						<form:hidden path="idTipo" id="cgcCatTipo.idTipo"/>
						<form:hidden path="idOrigen" id="cgcCatOrigen.idOrigen"/>						
						
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4" class="etiqueta2">Origen de Promoci&oacute;n</td>
									  	</tr>
									  </thead>
									  <tbody>
									  	<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
										<tr class="par">
									  		<td colspan="4">
									  			<div id="labelSBC"></div>
									  		</td>
									  	</tr>
									  	<tr valign="top" class="par" >
									  		<td align="left" width="25%" class="etiqueta2">
											   	<label>Criterio de Selecci&oacute;n: </label>   		
											</td>
                                          	<td align="left" width="25%" >
												<label id="descCriterioseleccion"></label>
											</td>						  		
									  	</tr>
									  	<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2">Datos del Patr&oacute;n</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>
									  	<tr valign="top" class="par" >
									  		<td align="left" width="25%" class="etiqueta2">
											   	<label>Registro Patronal: </label>   		
											</td>
                                          	<td align="left" width="25%" >
												<label id="regPatronalSalarioBase"></label>
											</td>		
											<td align="left" width="25%" class="etiqueta2">
											   	<label>Nombre o Raz&oacute;n Social: </label>   		
											</td>
                                          	<td align="left" width="25%" >
												<label id="nomRazonSocial"></label>
											</td>						  		
									  	</tr>
									    <thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2">Informaci&oacute;n del Oficio de Promoci&oacute;n</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>									  										  									  											  
										<tr valign="top" class="par"> 
 											<td align="left" width="25%" class="etiqueta2">
 												<label style="color: red;">* </label>
											   	<label> Fecha Oficio de la Promoci&oacute;n: </label>    		
											</td>
											<td align="left" width="25%">
												<input type="text" size="12" id="fecOficio" readonly="readonly">
											</td>   
											<td align="left" width="25%" class="etiqueta2">
 												<label style="color: red;">* </label>
											   	 <label> N&uacute;mero de Oficio de la Promoci&oacute;n: </label> 		
											</td>
											<td align="left" width="25%">
												<input type="text" size="27" maxlength="25" id="nuOficiopro" name="nuOficiopro" onkeypress="return jsvalidarNumerico(event);">
											</td>                																												
										 </tr>
										 <tr valign="top" class="par">   	
											<td align="left" width="25%" class="etiqueta2">
											  	<form:label id="txObservacionesLabel" for="txObservaciones" path="txObservaciones"
												cssErrorClass="error">Observaciones</form:label>											  																							
											</td>
											<td align="left" width="25%" colspan="3" height="60">
                                                <form:textarea path="txObservaciones" cols="92" rows="3" resizable="false" id="txObservaciones" onkeypress="return jsvalidarAlfaNumerico(event);"  onchange="javaScript:jsCortaTextArea(this.value);" /> 
												<form:errors path="txObservaciones" />
											</td>																																					
										 </tr>
										 
									  </tbody>
									</table>						
								  </td>
								  </tr>
							</table>													
						
					</form:form>	
					<form:form modelAttribute="promocionCargaModel" method="POST" action="">				
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px">
									<thead>
									  	<tr >
									  		<td colspan="4" class="etiqueta2">Domicilio Geogr&aacute;fico</td>
									  	</tr>
									  </thead>
									<tbody>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
										
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>Calle: 
											</td>
											<td align="left">
												<input type="hidden" name="municipio" id="municipio">
												<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly" /> 
												<label for="domCalle"></label>
											</td>
											<td align="left" class="etiqueta2">
												<span class="required">*</span>Colonia: 
											</td>
											<td align="left">
												<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly" /> 
												<label for="refColonia"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>N&uacute;mero Exterior:
											</td>
											<td align="left">
												<input name="numNroext" id="numNroext" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly" /> 
												<label for="numNroext"></label>
											</td>
											<td align="left" class="etiqueta2">N&uacute;mero Interior:
											</td>
											<td align="left" >
												<input name="numNroint" id="numNroint" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly" />
												<label for="numNroint"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>C&oacute;digo Postal:
											</td>
											<td align="left" >
												<input name="numCodigopostal" id="numCodigopostal" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly" />
												<label for="numCodigopostal"></label>
											</td>
											<td align="left" colspan="2" valign="bottom">
												<div id="domInegiButtons" align="center">
													<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','registro')"><span class="boton">Agregar Domicilio</span></a>
												</div>
											</td>
										</tr>
										<tr align="center" colspan="4" valign="bottom">
											<td>&nbsp;&nbsp;</td>
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
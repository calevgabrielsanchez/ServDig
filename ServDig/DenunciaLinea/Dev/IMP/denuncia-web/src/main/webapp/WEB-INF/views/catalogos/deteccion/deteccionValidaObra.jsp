<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/deteccion/deteccion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
		
	<div id="cuerpo">	
	
		<div class="menu_principal" style="height: 2em !important;"> 
	      <!--inicia menu principal-->
	      <div align="center">
	        <div class="centrado">
	          <ul style="height: 1em !important;">
	            <li><a> Validaci&oacute;n y registro de obras censadas </a></li>
	          </ul>
	        </div>
	        <!--fin centrado--> 
	      </div>
	    </div>  
	      
		<div id="dgDeteccionValida" title="Deteccion Alta" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogValida" style="background-color: #f2fff2;">	
					<form id="deteccionFormValida">
						<input type="hidden" name="cveFkPatron" id="cveFkPatron">
						<input type="hidden" name="domicilioId" id="domicilioIdV">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
						<fieldset>
							<table  style="width: 900px" align="center" >
								 <tr valign="middle">
								  <td align="center" width="900px">
								  
								  <table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
											<td align="center" colspan="4">Domicilio geografico</td>
										</tr>
									  </thead>	
									  <tbody>
										   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>										   
										   <tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										   </tr>
										   <tr valign="top" class="par">
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Estado: 										    												    		
										    </td>
										    <td align="left" width="100px">
										    	<input name="estado" id="estado" size="40" class="etiqueta2" readonly="readonly"/>
												<div id="labelEstado"></div>
											</td>
										    <td align="left" width="200px" class="etiqueta2"><span class="required">*</span>Municipio:
										    </td>
										    <td align="left" width="100px"> 										    		
										    		<input name="municipio" id="municipio" class="etiqueta2" size="40" readonly="readonly"/>
													<div id="labelMunicipio"></div>
										    </td>        
			   							   </tr>										   
										   <tr valign="top" class="par">   	
											   <td align="left" width="100px">
											    	<label for="domCalle" id="domCalleLabel" class="etiqueta2"><span class="required">*</span>Calle: </label>	    		    
											   </td>
											   <td align="left" width="100px">	    		
											    		<input name="domCalle" id="domCalle" class="etiqueta2" size="40" readonly="readonly"/>
											    		<div id="labelDomCalle"></div>    	
											   </td>
											   <td align="left" width="100px">
											    		<label for="refColonia" id="refColoniaLabel" class="etiqueta2"><span class="required">*</span>Colonia: </label>	    		
											   </td>	   
											   <td align="left" width="100px">	    		
											    		<input name="refColonia" id="refColonia" class="etiqueta2"  size="40" readonly="readonly"/>
											    		<div id="labelRefColonia"></div>
											   </td>	   
										   </tr>
										   <tr valign="top" class="par">											    
											    <td align="left" width="100px" colspan="1"class="etiqueta2"><span class="required">*</span>N&uacute;mero Exterior:										    												    		
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numNroext" id="numNroext" class="etiqueta2"  size="12" readonly="readonly"/>
													<div id="labelNumNroext"></div>
												</td>
												<td align="left" width="100px" colspan="1"class="etiqueta2">N&uacute;mero Interior:										    												    	
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numNroint" id="numNroint" class="etiqueta2"  size="12" readonly="readonly"/>
													<div id="labelNumNroint"></div>
												</td>
				   							</tr>
				   							<tr valign="top" class="par">
											    <td align="left" width="100px" colspan="1"class="etiqueta2"><span class="required">*</span>C&oacute;digo Postal:										    												    	
											    </td>
											    <td align="left" width="100px" colspan="1">
											    	<input name="numCodigopostal" id="numCodigopostal" class="etiqueta2"  size="12" readonly="readonly"/>
													<div id="labelNumCodigopostal"></div>
												</td>
											</tr>
											<tr valign="top" class="par">
										    	<td align="left" colspan="4">&nbsp;</td>
										   </tr>
										   
										    <tr class="par">
												<td align="right" colspan="4">
																							    		
											    	<a href="#" onclick="mostarDomGeoAlta('<%=request.getContextPath() %>','validar')">
											    		<span class="boton">Agregar Domicilio</span>
											    	</a>													
												   
													<a href="#" id="btnValidaObra">
														<span class="boton">Validar detecci&oacute;n y obra</span>
													</a>
													<a href="#" id="btnLimpiar">
														<span class="boton">Limpiar</span>
													</a>
												</td>
											</tr>
											<tr valign="top" class="par">
										    	<td align="left" colspan="4">&nbsp;</td>
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
					<div id="deteccionTableVal">
						<jsp:include page="deteccionResultadosValidacion.jsp" />
					</div>
					<div id="deteccionSaticVal">
						<jsp:include page="deteccionResultadosSatic.jsp" />
					</div>
					
					<div id="deteccionRegistraObra" style="overflow: auto; width:900px; height:300px;" align="center">
				<jsp:include page="deteccionRegistroObra.jsp" />
			</div>
					<div id="deteccionConfirmar" align="center">
				<jsp:include page="deteccionConfirmar.jsp" />
			</div>
		    	</div>
			</div>	
			
</div>
													
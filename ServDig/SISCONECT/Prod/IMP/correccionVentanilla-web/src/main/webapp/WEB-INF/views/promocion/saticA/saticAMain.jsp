<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
		
		    <div id="dgPromocionSeguimientoSaticaMain" title="Seguimiento SATIC A"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogSaticaMain" style="background-color: #f2fff2;">	
					<form:form action="" method="post" id="saticaMainForm" modelAttribute="seguimientoSaticaVO">
						<form:hidden type="hidden" path="cvePromocion" id="cvePromocionSatica"/>
						<form:hidden type="hidden" path="cveFkPatron" id="cveFkPatronSatica"/>
						<form:hidden type="hidden" path="rolUsuario" id="rolUsuarioSaticaSeg"/>
						<form:hidden type="hidden" path="estatusPromocion" id="estatusPromocionSatica"/>						

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
									  	<tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										</tr>
										    <tr>
										    	<td align="left" width="25%" class="etiqueta2">Criterio de Selecci&oacute;n:																									
													</td>
												<td align="left" width="25%" colspan="3">													
													<label id="sCriterioSelSaticA"></label>
												</td>
												<td align="left" width="25%">&nbsp;
												</td>												
											</tr>										
									  	<tr valign="top" class="par">   	
										   		<td align="left" width="25%" class="etiqueta2">Folio de la promoci&oacute;n																									
												</td>
												<td align="left" width="25%">
													<label id="folioPromocionSatica"></label>
												</td>
												<td align="left" width="25%" class="etiqueta2">Fecha de Oficio de Promoci&oacute;n																									
												</td>
												<td align="left" width="25%">
													<label id="fechaOficioPromocionSatica"></label>												
												</td>	
										</tr>		
									  	<tr valign="top" class="par">   	
												<td align="left" width="25%" class="etiqueta2">N&uacute;mero de Oficio:	    		
											   </td>
												<td align="left" width="25%">
													<label id="numeroOficioPromocionSatica"></label>												   		
												</td>												 
											   <td colspan="2">&nbsp;
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
									  		<td colspan="4">Ubicaci&oacute;n de la Obra (Detecci&oacute;n)</td>
									  	</tr>
									  </thead>
									   <tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>
									  	   <tr valign="top" class="par">
										    <td align="left" width="15%" class="etiqueta2">Estado:										    											    		
										    </td>
										    <td align="left" width="35%"> 	
										    	<label id="estadoSatica"></label>										    		
										    </td>										    
										    <td align="left" width="15%" class="etiqueta2">Municipio:
										    </td>
										    <td align="left" width="35%">
										    		<label id="municipioSatica"></label>										    		
										    </td>        										            
			   							   </tr>
										   
									  	   <tr valign="top" class="par">
										    <td align="left" width="15%" class="etiqueta2">Calle:										    											    		
										    </td>
										    <td align="left" width="35%"> 	
										    	<label id="calleSatica"></label>										    		
										    </td>										    
										    <td align="left" width="15%" class="etiqueta2">Colonia:
										    </td>
										    <td align="left" width="35%">
										    		<label id="coloniaSatica"></label>										    		
										    </td>        										            
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="15%" class="etiqueta2">N&uacute;mero Exterior: 										    												    		
										    </td>
										    <td align="left" width="35%" >
										    	<label id="numExtSatica"></label>
											</td>			   							   
										    <td align="left" width="15%" class="etiqueta2">N&uacute;mero Interior: 										    												    	
										    </td>
										    <td align="left" width="35%" colspan="1">
										    	<label id="numIntSatica"></label>
											</td>
			   							   </tr>
			   							   <tr valign="top" class="par">
										    <td align="left" width="15%" colspan="1" class="etiqueta2">C&oacute;digo Postal: 									    												    	
										    </td>
										    <td align="left" width="35%" colspan="1">
										    	<label id="codigoPostalSatica"></label>										    
											</td>
										   </tr>
										   <tr valign="top" class="par">   												  
											   <td align="left" width="15%" class="etiqueta2">Registro Patronal: </td>
											   <td align="left" width="25%">
											   		<label id="registroPatronalDeteccion"></label>										   		
											   </td>
										    <td align="left" width="60%" colspan="2" ><span class="etiqueta2">Nombre &oacute; Raz&oacute;n Social:</span>
										    <label id="razonSocialDeteccion"></label>
										    </td>										      
										   </tr>										   										  
										   
										   <tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</table>
									<table class="tablaverde2" style="width: 900px">
										<thead>
										  	<tr >
										  		<td colspan="4">Datos del Patr&oacute;n</td>
										  	</tr>
									  	</thead>
										<tbody>
									  	   <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>									  	   				  
										   <tr valign="top" class="par">   												  
											   <td align="left" width="15%" class="etiqueta2">Registro Patronal: 	    		
											   </td>
											   <td align="left" width="25%">
											   		<input name="registroPatronal" id="regPatronSatica" size="20" maxlength="10" onchange="modificaRPMain();mayusculasTextField(this);" onkeyup="mayusculasTextField(this);"/>											   		
											   		<input type="button" class="boton" onclick="javascript:validarRegistroPatronalSatica();" value="Validar" id="btnValidaRegPatronSaticaMain">
											   		<div id="regPatronSaticaLabel" class="etiquetaError"></div>
											   </td>
										    <td align="left" width="60%" colspan="2" ><span class="etiqueta2">Nombre &oacute; Raz&oacute;n Social:</span>
										    		&nbsp;<label id="razonSocialSatica"></label> 										    		
										    </td>										      
										   </tr>										   										  
										    <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										   </tr>										   
									  </tbody>
									</table>	
																	
									<table class="tablaverde2" style="width: 900px">
										<thead>
										  	<tr >
										  		<td colspan="4">Seguimiento</td>
										  	</tr>
									  	</thead>
									</table>																	
																	
								  </td>
								  </tr>
							</table>													
						</fieldset>
					</form:form>							    
		    	</div>
		    	
<!-- 	<div id="saticaMain" title="Seguimiento" -->
<!-- 		style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =   70) !important;"> -->
		
		<table style="width: 1100px" align="center" border="0">
			<tr valign="middle">
				<td align="left" width="1100px">

					<table width="100%" align="left" border="0">
						<tr>
							<td>
								<ul class="tabs">
									<li class="etiqueta4" id="saticaSeguimientoTabLI"><a href="#saticaSeguimientoTab" id="saticaSeguimientoTabLink">Seguimiento</a></li>
									<li class="etiqueta4" id="cancelacionGenericoTab_saticaLI"><a href="#cancelacionGenericoTab_satica" id="cancelacionGenericoTab_saticaLink">Cancelar Promoci&oacute;n </a></li>
									<li class="etiqueta4" id="derivarSubdelegacionGenericoTab_saticaLI"><a href="#derivarSubdelegacionGenericoTab_satica" id="derivarSubdelegacionGenericoTab_saticaLink">Derivar a otra Subdeleg </a></li>
									
 									<li class="etiqueta4" id="derivarFiscalizacionTAB_saticaLI"><a href="#derivarFiscalizacionTAB_satica" id="derivarFiscalizacionTAB_saticaLink">Derivar a Fiscalizaci&oacute;n </a></li> 
 									<li class="etiqueta4" id="regularizarObraGenericoTAB_saticaLI"><a href="#regularizarObraGenericoTAB_satica" id="regularizarObraGenericoTAB_saticaLink">Regularizar Obra </a></li> 
									
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								<div id="saticaSeguimientoTab" class="tab_content" ">
									<jsp:include page="saticASeguimientoTab.jsp" />
								</div>
								<div id="cancelacionGenericoTab_satica" class="tab_content" >
									<jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cancelacionGenericoTAB.jsp" />
								</div>
								<div id="derivarSubdelegacionGenericoTab_satica" class="tab_content" >
									<jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/derivarSubdelegacionGenericoTAB.jsp" />
								</div>							

								<div id="derivarFiscalizacionTAB_satica" class="tab_content">
									<jsp:include page="/WEB-INF/views/seguimiento/promocion/derivarFiscalizacionGenericoTAB.jsp" />
								</div>

								<div id="regularizarObraGenericoTAB_satica" class="tab_content">
									<jsp:include page="/WEB-INF/views/seguimiento/promocion/regularizarObraGenericoTAB.jsp" />
								</div>
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

<!-- 	</div>		    	 -->
			
			</div>

	<!-- se incluye el jsp para validar domicilio vs SATIC -->
	<div id="validaDomicilios" style="display:none" align="center">
		<jsp:include page="saticAValidaDomicilios.jsp" />
	</div>


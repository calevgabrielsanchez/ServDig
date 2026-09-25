<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">	
	
	<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">	
	    
		    <div id="dgSeguimientoPromocion"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogSeguimientoPromocion" style="background-color: #f2fff2;">	
					<form id="seguimientoForm" name="seguimientoForm">
						<input type="hidden" id="cvePromocion">
						<input type="hidden" id="rolUsuarioOrdHdn">
						<input type="hidden" id="regPatronalOrdinarioHdn">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
						  		<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
						  	  			<thead>
							  				<tr >
							  					<td colspan="4">Informaci&oacute;n del oficio de promoci&oacute;n</td>
							  				</tr>
							  			</thead>
							  			<tbody>
							  				<tr valign="top" class="impar">
										    	<td align="left" colspan="4">&nbsp;</td>
											</tr>
													   
							  				<tr class="par">
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>Criterio de selecci&oacute;n : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="criterioSeleccion"></label>
							  					</td>
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>N&uacute;mero del folio de la promoci&oacute;n : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="numFolioPromocion"></label>
							  					</td>
							  				</tr>
							  				<tr class="par">
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>Fecha oficio de promoci&oacute;n : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="fecOficio"></label>
							  					</td>
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>N&uacute;mero de oficio de la promoci&oacute;n : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="numOficioPromocion"></label>
							  					</td>
							  				</tr>
							  				<thead>
								  				<tr >
								  					<td colspan="4">Datos del Patr&oacute;n</td>
								  				</tr>
							  				</thead>	
							  				<tr valign="top" class="impar">
										    	<td align="left" colspan="4">&nbsp;</td>
											</tr>
											
							  				<tr class="par">
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>Registro patronal : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="registroPatronal"></label>
							  					</td>
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>Nombre &oacute; raz&oacute;n social : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="razonSocial"></label>
							  					</td>
							  				</tr>
							  				<thead>
								  				<tr >
								  					<td colspan="4">Domicilio Geografico</td>
								  				</tr>
							  				</thead>	
							  				<tr valign="top" class="impar">
										    	<td align="left" colspan="4">&nbsp;</td>
											</tr>
												   
							  				<tr class="par" width="20%">
							  					<td align="left" class="etiqueta2">
							  						<label>Calle : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="domCalle"></label>
							  					</td>
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>Colonia : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="colonia"></label>
							  					</td>
							  				</tr>
							  				<tr class="par">
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>N&uacute;mero exterior : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="numExt"></label>
							  					</td>
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>N&uacute;mero interior : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="numInt"></label>
							  					</td>
						
							  				</tr>
							  				<tr class="par">
							  					<td align="left" class="etiqueta2" width="20%">
							  						<label>C&oacute;digo Postal : </label>
							  					</td>
							  					<td align="left" width="30%">
							  						<label id="cp"></label>
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
					</form>	
					<table  style="width: 900px" align="center" >
			 				<tr valign="top" class="impar">
					    		<td align="left" colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4" align="left">
									<div id="tab_container_href"  >
										<ul class="tabs">
											<li class="etiqueta2"><a href="#seguimientoTAB" id="seguimiento">Seguimiento</a></li>
											<li class="etiqueta2"><a href="#cancelacionTAB" id="cancelacion">Cancelar promoci&oacute;n</a></li>
											<li class="etiqueta2"><a href="#autDictamenTAB">Aviso de dictamen</a></li>
										</ul>
										
										<div>
											<div id="seguimientoTAB" class="tab_content" >
											<jsp:include page="seguimientoTAB.jsp" />
											</div>
											<div id="cancelacionTAB" class="tab_content">
												<jsp:include page="cancelacionTAB.jsp" />
											</div>
											<div id="autDictamenTAB" class="tab_content">
												<jsp:include page="autAviDictamenTAB.jsp" />
											</div>
										</div>
									</div>
								</td>
							</tr>
					</table>									    
		    	</div>
			</div>
			
			
											
							  				
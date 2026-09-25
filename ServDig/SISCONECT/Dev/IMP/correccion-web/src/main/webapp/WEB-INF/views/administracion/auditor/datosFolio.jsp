<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
		    <div id="dgDatosFolio"    style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogDatosFolio" style="background-color: #f2fff2;">	
					<form action=""  method="post" id="datosFolioForm">
						<input type="hidden" name="cvePromocion" id="cvePromocion">
						<input type="hidden" name="cveInvitacion" id="cveInvitacion">
						<input type="hidden" name="cveSolicitudCorr" id="cveSolicitudCorr">
						<input type="hidden" name="cveAuditor" id="cveAuditor">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
									<tbody>
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Datos del Folio Seleccionado</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" class="etiqueta2" width="15%">
										  			<label>Folio : </label>   		
												</td>	
												<td align="left">
													<label id="labelFolio"></label>
												</td>
												<td>
													<table>
														<tr>
															<td align="left" class="etiqueta2">
																<label>Periodo del:</label> &nbsp; 
															</td>
															<td>
																<label id="labelPeriodoIni"></label>&nbsp;&nbsp;
															</td>
															<td align="left" class="etiqueta2">
																<label>al:</label> &nbsp; 
															</td>
															<td>
																<label id="labelPeriodoFin"></label>
															</td>
														</tr>
													</table>
												</td>	
												<td>
													<table>
														<tr>
															<td align="left" class="etiqueta2">
																<label>Fecha de Emisi&oacute;n : </label>
															</td>
															<td align="left">
																<label id="labelFechaEmision"></label>
															</td>
														</tr>
													</table>										  				
												</td>				  		
									  		</tr>
									  		<tr class="par" >
									  			<td align="left" class="etiqueta2">
										  			<label>Registro Patronal : </label>   		
												</td>	
												<td align="left">
													<label id="labelRegPatronal"></label>
												</td>	
									  			<td colspan="2">
									  				<table>
									  					<tr>									  						
															<td align="left" class="etiqueta2">
													  			<label>Raz&oacute;n Social : </label>  
													  		</td>
													  		<td align="left">
													  			<label id="labelRazonSocial"></label> 		
															</td>	
									  					</tr>
									  				</table>
									  			</td>	  		
									  		</tr>
										  	<tr class="par">
										  		<td colspan="4">&nbsp;</td>
										  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2">Auditores Disponibles</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>									  										  									  											  
										<tr class="par" >
									  		<td colspan="4" align="center">
									  			<table style="width: 850px">
									  				<tbody>
									  					<tr>
									  						<td colspan="4" width="850px">
									  							<table id="dtAuditoresDisponibles"  style="width: 850px" align="center">
																	<thead>			
																	</thead>
																	<tbody align="center">
																	</tbody>
														    	</table>
									  						</td>
									  					</tr>
									  				</tbody>
									  			</table>
									  		</td>		  		
								  		</tr>
							  		 	<tr class="par">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2" >Carga de Trabajo</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>
									  	<tr class="par" >
									  		<td colspan="4" align="center">
									  			<table style="width: 870px">
									  				<tbody>
									  					<tr>   
													  		<td colspan="4" width="870px">
													  			<table id="dtCargaTrabajo"  style="width: 870px; overflow: auto; width:870px;" align="center" >
																	<thead>			
																	</thead>
																	<tbody align="center">
																	</tbody>
														    	</table>
													  		</td>		  		
												  		</tr>
									  				</tbody>
									  			</table>
									  		</td>		  		
								  		</tr>		
								  		<tr class="par" >
								  			<td colspan="4" align="center">
												<a href="#" onclick="javascript:confirmar();"><span class="boton">Asignar</span></a>	
												<a href="#" onclick="javascript:salir();"><span class="boton">Salir</span></a>
											</td>	  		
								  		</tr>
							  		 	<tr class="par">
									  		<td colspan="4">&nbsp;&nbsp;</td>
									  	</tr>	
									</tbody>
									</table>
								</td>
							</tr>
						</table>													
					</form>											    
		    	</div>
			</div>
			
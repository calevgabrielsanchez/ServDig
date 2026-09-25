<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/administracion/auditor/reasignarAuditor.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js?v=2"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>
	
		    <div id="dgReasigaAuditor"    style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogReasignarAuditor" style="background-color: #f2fff2;">	
					<form action=""  method="post" id="reasignarAuditorForm">
						<input type="hidden" name="cvePromocion" id="cvePromocion">
						<input type="hidden" name="cveInvitacion" id="cveInvitacion">
						<input type="hidden" name="cveSolicitudCorr" id="cveSolicitudCorr">
						<input type="hidden" name="cveAuditor" id="cveAuditor">
						<input type="hidden" name="cveUsuarioAsignado" id="cveUsuarioAsignado">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
									<tbody>
										<thead>
											<tr>
												<td colspan="6" class="etiqueta2">Informaci&oacute;n del Folio</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="6">&nbsp;</td>
											</tr>	
											<tr class="par">
												<td colspan="6">
													<table>
														<tr>
															<td align="left" class="etiqueta2">
																<label>Folio : </label>
															</td>
															<td align="left" colspan="5">
																<input type="text" id="folioTemp" name="folioTemp" size="22" maxlength="21" onkeyup="validaCampo('noCaracteresEspeciales','folioTemp','reasignarAuditorForm');" >
																<a href="#" onclick="javascript:buscar();"><span class="boton">Consultar</span></a>
																<label id="labelFolioConsulta"></label>
															</td>
														</tr>
													</table>
												</td>												
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label>Folio : </label>   		
												</td>	
												<td align="left">
													<label id="labelFolio"></label>
												</td>
												<td align="left" colspan="2">
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
												<td align="left" class="etiqueta2">
														<label>Fecha de Emisi&oacute;n : </label>
												</td>
												<td align="left">
													<label id="labelFechaEmision"></label>
												</td>						  		
									  		</tr>
									  		<tr class="par" >
									  			<td align="left" class="etiqueta2">
										  			<label>Registro Patronal : </label>   		
												</td>	
												<td align="left">
													<label id="labelRegPatronal"></label>
												</td>	
									  			<td align="left" class="etiqueta2">
										  			<label>Raz&oacute;n Social : </label>  
										  		</td>
										  		<td align="left">
										  			<label id="labelRazonSocial"></label> 		
												</td>	
												<td align="left" class="etiqueta2">
										  			<label>Auditor : </label>  
										  		</td>
										  		<td align="left">
										  			<label id="labelAuditor"></label> 		
												</td>		  	  		
									  		</tr>
										  	<tr class="par">
										  		<td colspan="6">&nbsp;</td>
										  	</tr>	
										  	<tr>
										  		<td colspan="6">
										  			<div id="contenido" style="display: none;">
										  				<table>
										  					<thead>
															  	<tr >
															  		<td colspan="6" class="etiqueta2">Auditores Disponibles</td>
															  	</tr>
															</thead>
															<tr class="impar">
														  		<td colspan="6">&nbsp;</td>
														  	</tr>									  										  									  											  
															<tr class="par" >
														  		<td colspan="6" align="center">
														  			<table style="width: 850px">
														  				<tbody>
														  					<tr>
														  						<td colspan="6" width="850px">
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
														  		<td colspan="6">&nbsp;</td>
														  	</tr>	
													  		<thead>
															  	<tr >
															  		<td colspan="6" class="etiqueta2" >Carga de Trabajo</td>
															  	</tr>
															</thead>
															<tr class="impar">
														  		<td colspan="6">&nbsp;</td>
														  	</tr>
														  	<tr class="par" >
														  		<td colspan="6" align="center">
														  			<table style="width: 870px">
														  				<tbody>
														  					<tr>   
																		  		<td colspan="6" width="870px">
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
													  			<td colspan="6" align="center">
													  				<a href="#" id="btnAsignar"><span class="boton">Asignar</span></a>
																	<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>	
																	<a href="#" onclick="goToWelcomePage('<%=request.getContextPath()%>')"><span class="boton">Salir</span></a>
																</td>	  		
													  		</tr>
												  		 	<tr class="par">
														  		<td colspan="6">&nbsp;&nbsp;</td>
														  	</tr>	
										  				</table>
										  			</div>
										  		</td>
										  	</tr>								  		
										</tbody>
									</table>
								</td>
							</tr>
						</table>													
					</form>											    
		    	</div>
			</div>
			
			
			<div id="confirmar" align="center" class="centrado" >
				<jsp:include page="confirmarAsignarAuditor.jsp" />
			</div>	
			
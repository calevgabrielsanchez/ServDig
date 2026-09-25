<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
		    <div id="dgInvitacionAntecedente"   style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogObraRegistrada" style="background-color: #f2fff2;">	
					<form action=""  method="post" id="invitacionAntecedenteForm">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
						<input type="hidden" name="cvePromocion" id="cvePromocion">
						<input type="hidden" name="tipoPrograma" id="tipoPrograma">
						<input type="hidden" id="funcionSeguimientoInv">
						<input type="hidden" id="fechaNotificacionInv">
						<input type="hidden" id="cveFkPatronInv" name="cveFkPatron">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Datos de la Promoci&oacute;n</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label>Folio Antecedente: </label>   		
												</td>	
												<td align="left" colspan="3">
													<label id="labelFolioAntecedente"></label>
												</td>
															  		
									  		</tr>
									  		<tr class="par" >
										  		<td align="left"  class="etiqueta2">
										  			<label>Registro Patronal: </label>   		
												</td>	
												<td align="left" >
													<label id="labelRegPatronal"></label>
												</td>		
												<td align="left"  class="etiqueta2">
										  			<label>Nombre &oacute; Raz&oacute;n Social: </label>   		
												</td>	
												<td align="left" >
													<label id="labelNomRazonSocial"></label>
												</td>			  		
									  		</tr>
										  	<tr class="par">
										  		<td colspan="4">&nbsp;</td>
										  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2" width="100%">Datos de la Invitaci&oacute;n</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>									  										  									  											  
										<tr class="par">
											<td align="left"  class="etiqueta2">
												<label style="color: red;">* </label>
									  			<label>Oficio Invitaci&oacute;n: </label>   		
											</td>	
											<td align="left" >
												<input type="text" id="nuOficioinv" name="nuOficioinv"  maxlength="25" size="25" onkeyup="mayusculasTextField(this);">
												<div id="labelOficio"></div>
											</td>	
											<td align="left"  class="etiqueta2">
												<label style="color: red;">* </label>
									  			<label>Fecha Emisi&oacute;n: </label>   		
											</td>	
											<td align="left" >
												<input type="text" id="fechaEmisionFec" name="fechaEmisionFec" readonly="readonly"  >
												<input type="hidden" id="fechaEmision" name="fechaEmision" >
												
												<div id="labelFecEmision"></div>
											</td>		
										</tr>
										<tr class="par">
											<td align="center"  class="etiqueta2" colspan="4">
												<label>Periodo a Corregir </label>   		
											</td>	
										</tr>
										<tr class="par">	
											<td align="center" colspan="4">
												<table>
													<tr>
														<td class="etiqueta2">
															<label style="color: red;">* </label>
															<label>Del:  </label>
														</td>
														<td>
															<input type="text" id="fechaIncialinv" name="fechaIncialinv" readonly="readonly" >
															<input type="hidden" id="fechaIncial" name="fechaIncial">
														</td>
														<td class="etiqueta2">
															<label> Al:  </label>
														</td>
														<td>
															<input type="text" id="fechaFinalInv" name="fechaFinalInv" readonly="readonly" > 
															<input type="hidden" id="fechaFinal" name="fechaFinal">
															<label id="labelFechaEmisionGuardar"></label>
														</td>
													</tr>
												</table>											
											</td>	
										</tr>
										<tr class="par">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
										</tbody>
									</table>
								</td>
							</tr>
						</table>													
					</form>											    
		    	</div>
			</div>
			
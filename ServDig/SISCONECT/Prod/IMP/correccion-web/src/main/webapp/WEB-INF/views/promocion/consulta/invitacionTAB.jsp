			
			<table class="tablaverde2">
				<tr>
					<td colspan="4">
					<form id="invitacionFormRegistro" action="" >				
							<input type="hidden" name="fechaIncial" id="invFecIni"/>
							<input type="hidden" name="fechaFinal" id="invFecFin"/>
							<input type="hidden" name="cvePromocion" id="cvePromocion"/>
							<input type="hidden" name="cveDeteccion" id="cveDeteccion"/>
							<input type="hidden" name="patron" id="patron"/>
							<input type="hidden" name="tipoPrograma" id="tipoPrograma"/>
									<table  class="tablaverde2" style="width: 900px">							
								 
											
										<tr class="par">
											<td align="center" colspan="4" class="etiqueta2">
												<div id="labelOficio" class="etiquetaError"></div>
											</td>	
										</tr>						
					 					<tr valign="middle" class="par">
								  			<td align="left"  class="etiqueta2" id="tdFolioDet" style="width: 105px; ">Folio Detecci&oacute;n</td>
								  			<td width="25%" align="left"  id="tdInputFolioDet">
					  							<input type="text" id="folioDeteccion" size="22" readonly="readonly"/>
											</td>
											<td width="25%" align="left"  class="etiqueta2" id="tdFolioProm">Folio Promoci&oacute;n</td>
								  			<td width="25%" align="left"  id="tdInputFolioProm">
					  							<input name="folioPromocion" id="folioPromocion" size="25" readonly="readonly"/>
											</td>
											
								  		</tr>
					 					<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2"><label style="color: red;">* </label>Oficio Invitaci&oacute;n</td>
								  			<td width="25%" align="left">
					  							<input name="nuOficioinv" id="oficio" size="20" maxlength="18" onkeypress="return jsvalidarNumerico(event);"/>
					  							
											</td>
											<td width="25%" align="left"  class="etiqueta2"><label style="color: red;">* </label>Fecha Emisi&oacute;n</td>
								  			<td width="25%" align="left" >
					  							<input name="fechaEmision" id="fecEmision" readonly="readonly" maxlength="10"  onchange="jsValidaFecEmision(this.value);"/>
											</td>											
								  		</tr>
										<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2" ><label style="color: red;">* </label>Registro Patronal</td>
								  			<td width="75%" align="left" colspan="3">
					  							<input type="text" id="registroPatronal" maxlength="10" onkeypress="return jsvalidarAlfaNumerico(event);"/>
					  							<a href="#" id="btnValidar">
													<span class="boton">Validar</span>
												</a>
												<div id="labelRegistroPatronal" class="etiquetaError"></div>
											</td>
											
											
								  		</tr>
				  						<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2" >Raz&oacute;n Social</td>
								  			<td width="75%" align="left" colspan="3">
								  				<input type="text" id="razonSocial" readonly="readonly" size="70" >
								  			</td>
								  		</tr >
								  		<tr valign="middle" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>	
										<tr valign="middle" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
									    	<td align="center" colspan="4">
												<a href="#" id="btnGuardarInv">
													<span class="boton">Guardar</span>
												</a>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>	
									

			
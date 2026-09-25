<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
		
		<div id="dgInvitacionGuardar" >
			<div id="pie">
				<form id="invitacionFormRegistro" action="">				
					<input type="hidden" name="cveInvitacion" id="cveInvitacion"/>
					<fieldset>
						<table  style="width: 700px" align="center" >
								 <tr valign="middle" class="par">  
								  <td align="center" width="700px">
									<table  class="tablaverde2" style="width: 700px;">	
										<tr class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>	
										<tr class="par">
											<td align="center" colspan="4" class="etiqueta2">
												<div id="labelOficio" class="etiquetaError"></div>
											</td>	
										</tr>						
					 					<tr valign="middle" class="par">
								  			<td align="left"  class="etiqueta2" id="tdFolioDet" style="width: 105px; ">Folio Invitaci&oacute;n</td>
								  			<td width="25%" align="left"  id="tdInputFolioDet" class="etiqueta2" colspan="3">
					  							<input type="text" id="folioDeteccion" size="22" readonly="readonly" class="etiqueta2"/>
											</td>
										</tr>
										<tr class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>	
					 					<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2"><label style="color: red;">* </label>Oficio Cancelaci&oacute;n</td>
								  			<td width="25%" align="left" class="etiqueta2" colspan="3">
					  							<input name="nuOficioinv" id="oficio" size="20" maxlength="10" class="etiqueta2" onkeypress="return jsvalidarNumerico(event);"/>
					  							
											</td>											
								  		</tr>
										<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2" ><label style="color: red;">* </label>Motivo Cancelaci&oacute;n</td>
								  			<td width="75%" align="left" class="etiqueta2" colspan="3">
					  							<combo:creaCombo 
					  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatMotivoCancelacion"
					  								idHtml="idMotivocancelacion"
					  								idHtmlContenedor="invitacionFormRegistro"													
					  							/>
											</td>
											
											
								  		</tr>
				  						<tr valign="middle" class="par">
								  			<td width="25%" align="left"  class="etiqueta2" ><label style="color: red;">* </label>Auditor</td>
								  			<td width="75%" align="left" class="etiqueta2" colspan="3">
								  				<select id="selectAuditor" ><option value="-1">--Por favor seleccione--</option></select>
								  			</td>
								  		</tr >
								  		<tr valign="middle" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>	
										<tr valign="middle" class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>	
									</table>
							
						</table>
					</fieldset>
				</form>
			</div>
		</div>
			
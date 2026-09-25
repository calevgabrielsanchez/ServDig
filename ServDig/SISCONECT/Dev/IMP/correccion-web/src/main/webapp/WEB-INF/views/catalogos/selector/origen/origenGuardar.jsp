<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
			    
		    <div id="dgorigenGuardar" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogtipo" style="background-color: #f2fff2;">	
					
						<fieldset>
						<form id="origenForm">
							<table style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
	
										<table class="tablaverde2" style="width: 900px">
											<tbody>
												<tr valign="top" class="impar">
											<td align="left" colspan="2">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="120px" colspan="1">
												<label style="color: red;">* </label>
												<label> Descripci&oacute;n : </label>
											</td>
											<td align="left" width="780px">
												<input type="text" id="descOrigenGuardar" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','descOrigenGuardar','origenForm');">
					  							<div class="etiquetaError" id="labelTipo"></div>									
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="2">&nbsp;</td>
										</tr>	
										<tr valign="top" class="impar">
											<td align="left" colspan="2">&nbsp;</td>
										</tr>				
											</tbody>
										</table>
									</td>
								</tr>
						</table>
					</form>		
				 </fieldset>
				 <input type="hidden" name="idOrigen" id="idOrigen"/>	
									    
		    	</div>
			</div>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    <div id="dgtipoGuardar" title="Agregar Tipo" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogtipo" style="background-color: #f2fff2;">	
					<form action="" id="guardarForm">
						<fieldset>
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
														<label> Secci&oacute;n : </label>
													</td>
													<td align="left" width="780px">
														<combo:creaCombo 
							  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatFlujo"
							  								idHtml="cgcCatflujo.idFlujo"
							  								idHtmlContenedor="guardarForm"
							  								 														
							  							/>
							  							<div class="etiquetaError" id="labelTipo"></div>									
													</td>
												</tr>
												<tr valign="top" class="par">
														<td align="left" width="100px" colspan="1">
															<label>Descripci&oacute;n : </label>
														</td>
														<td align="left" width="780px" >
															<input id="cboSeccionDescGuardar" maxlength="15" onkeypress="return jsvalidarAlfaNumerico(event);"/>
														</td>
												</tr>
												<tr valign="top" class="par">
													<td align="left" colspan="2">&nbsp;</td>
												</tr>							
											</tbody>
										</table>
									</td>
								</tr>
						</table>
									
				 </fieldset>
				 <input type="hidden" name="idTipo" id="idTipo"/>	
				</form>						    
		    	</div>
			</div>
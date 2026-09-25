<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
		    <div id="dgPromoverSaticA"  title="Generar Nuevo Folio SATIC A"   style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogObraRegistrada" style="background-color: #f2fff2;">	
					<form action=""  method="post" id="promoverSatiAForm">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
						<input type="hidden" id="fechaDeteccionPromover">
						<input type="hidden" id="regPatron" name="regPatron">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Datos de Promoci&oacute;n</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" width="20%" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Criterio de Selecci&oacute;n: </label>   		
												</td>	
												<td align="left" colspan="3" width="70%">
													<select id="selectCriterios" name="idCriterioSeleccion" onchange="$('form#promoverSatiAForm #labelcriterios').html('');">
														<option value="">--Por favor seleccione--</option>
													</select>
													<div id="labelcriterios"></div>
												</td>			  		
									  		</tr>
									  		<tr class="par" >
										  		<td align="left" width="20%" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>N&uacute;mero de Oficio </label>   		
												</td>	
												<td align="left" width="30%">
													<input name="nuOficiopro" id="nuOficiopro" onkeyup="mayusculasTextField(this)" onchange="mayusculasTextField(this)" size="26" maxlength="25" onblur="$('form#promoverSatiAForm labelNuOficio').html('');"  >
													<div id="labelNuOficio"></div>
												</td>
												<td align="left" width="20%" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Fecha Oficio Promoci&oacute;n </label>   		
												</td>	
												<td align="left" width="30%">
													<input name="fechaOficio" id="fechaOficioPromocion" size="10" readonly="readonly">
													<div id="labelFecOficio"></div>
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
			
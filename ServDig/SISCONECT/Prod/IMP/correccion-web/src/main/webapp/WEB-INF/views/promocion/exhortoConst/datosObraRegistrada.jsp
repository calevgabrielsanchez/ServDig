<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
		    <div id="dgObraRegistrada"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogObraRegistrada" style="background-color: #f2fff2;">	
					<form action=""  method="post" id="obraRegistradaForm">
						<input type="hidden" name="cveDeteccion" id="cveDeteccion">
					
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Datos de Detecci&oacute;n de la Obra</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" width="20%" class="etiqueta2">
										  			<label>Folio de Detecci&oacute;n: </label>   		
												</td>	
												<td align="left" width="30%">
													<label id="labelFolioDeteccion"></label>
												</td>
												<td align="left" width="20%" class="etiqueta2">
										  			<label>N&uacute;mero de Reporte de la Obra: </label>   		
												</td>	
												<td align="left" width="30%">
													<label id="labelNumReporteObra"></label>
												</td>				  		
									  		</tr>
									  		<tr class="par" >
										  		<td align="left" width="20%" class="etiqueta2">
										  			<label>Fecha Detecci&oacute;n: </label>   		
												</td>	
												<td align="left" width="80%" colspan="3">
													<label id="labelFechaDeteccion"></label>
												</td>			  		
									  		</tr>
									  		<tr class="par" >
										  		<td align="left" width="20%" class="etiqueta2">
										  			<label>Registro Patronal: </label>   		
												</td>	
												<td align="left" width="30%">
													<label id="labelRegPatronal"></label>
												</td>
												<td align="left" width="20%" class="etiqueta2">
										  			<label>Raz&oacute;n Social:</label>   		
												</td>	
												<td align="left" width="30%">
													<label id="labelRazonSocial"></label>
												</td>				  		
									  		</tr>
										  	<tr class="par">
										  		<td colspan="4">&nbsp;</td>
										  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2" width="100%">Ubicaci&oacute;n de la Obra</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>									  										  									  											  
										<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Estado </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelEstado"></label>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Municipio </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelMunicipio"></label>
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Calle </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelCalle"></label>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Colonia </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelColonia"></label>
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>N&uacute;mero Interior </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelNUmInt"></label>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>N&uacute;mero exterior </label>   		
											</td>	
											<td align="left" width="30%">
												<label id="labelNumExt"></label>
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>C&oacute;digo Postal </label>   		
											</td>	
											<td align="left" width="20%" colspan="3">
												<label id="labelCP"></label>
											</td>													  		
								  		</tr>
							  		 	<tr class="par">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2" width="100%">Datos Generales de la Obra</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>
									  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Registro Patronal </label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="regPatronalDetecc" size="25" maxlength="10" onkeyup="limpiaCampo();" id="regPatronalDetecc" style="text-transform: uppercase;" > 
												<input type="button" class="boton" onclick="javascript:validarRegistroPatronalExhorto();"  value="Validar" id="btnValidaRegPatronExhortoMain">
												<div id="labelRegistroPatronal"></div>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<!-- <label style="color: red;">* </label> -->
									  			<label>Raz&oacute;n Social </label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="razonSocialDetecc" size="40" id="razonSocialDetecc" readonly="readonly">
												
											</td>				  		
								  		</tr>
									  	
									  	
									  	
									  	<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label style="color: red;">* </label>
									  			<label>Clase de la Obra </label>   		
											</td>	
											<td align="left" width="30%">
												<select name="tipClaseobra" id="tipClaseobra">
													<option value="" id="0">Seleccionar</option>
													<option value="Publica" id="1">P&uacute;blica</option>
													<option value="Privada" id="2">Privada</option>
												</select>
												<div id="labelClaseObra"></div>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Fecha Estimada Inicio de la Obra </label>   		
											</td>	
											<td align="left" width="30%">
												<input name="fechaEstimIncio2" id="fechaEstimIncio2" size="10" readonly="readonly"  onchange="validarFechaFinEx()">
												
											</td>				  		
								  		</tr>
							  			<tr class="par" id="tdFase1">
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Superficie m<sup>2</sup> </label>   		
											</td>	
											<td align="left" width="30%">
												<input name="canSuperficie" id="canSuperficie" style="text-align: right;" size="20" maxlength="16"  onkeyup="validaCampo('noCaracteresEspeciales','canSuperficie','deteccionLayoutForm');validaCampo('PermiteSoloNumeros','canSuperficie','deteccionLayoutForm')">
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Fecha Estimada T&eacute;rmino de la Obra </label>   		
											</td>	
											<td align="left" width="30%">
												<input name="fechaEstTerm2" id="fechaEstTerm2" size="10" readonly="readonly"  onchange="validarFechaFinEx()">
												
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="50%" colspan="2" class="etiqueta2">
									  			<label style="color: red;">* </label>
									  			<label>Tipo de Obra</label>   		
											</td>	
											<td align="left" width="50%" colspan="2" class="etiqueta2">
									  			<label>Fase de Obra</label>   		
											</td>					  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="50%" colspan="2" >
									  			<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoobra"
																 idHtml="cvePkTipObra"
																 entidadPadre="mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion"
																 idHtmlPadre="tipClaseobra"
																 idHtmlContenedor="obraRegistradaForm"/>
												<div id="labeltipoObra"></div>	
											</td>	
											<td align="left" width="50%" colspan="2" >
									  			<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcFaseconstruccion"
																 idHtml="cvePkFaseConst"
																 idHtmlContenedor="obraRegistradaForm"/>	
											</td>					  		
								  		</tr>
								  		<tr class="par" id="tdFase2">
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Importe de la Obra </label>   		
											</td>	
											<td align="left" width="30%">
												<input id="impCostoobra" name="impCostoobra" style="text-align: right;" size="25" maxlength="18" onkeyup="validaCampo('PermiteSoloNumerosYPunto','impCostoobra','deteccionLayoutForm')" />
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Porcentaje de Avance</label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="porAvanceobraEst" id="porAvanceobraEst" size="20" maxlength="3" onkeypress="return jsvalidarNumerico(event);" onkeyup="valorMaxim('porAvanceobraEst','Porcentaje Regularizado','obraRegistradaForm');">
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Zona Salarial</label>   		
											</td>	
											<td align="left" width="30%">
												<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacZona"
																 idHtml="cveFkZona"
																 idHtmlContenedor="obraRegistradaForm"/>
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>N&uacute;mero de Trabajadores</label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="numTrabajdores" id="numTrabajdores" size="20" maxlength="5" onkeypress="return jsvalidarNumerico(event);">
											</td>				  		
								  		</tr>
								  		<tr class="par" >
									  		<td align="left" width="20%" class="etiqueta2">
									  			<label>Dependencia</label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="desDependenciapub" id="desDependenciapub" size="50" maxlength="50" onkeypress="return KeyPressed(this,event,'alphanumericnotspecial', null, 'uppercase=yes', null, 'no');">
											</td>
											<td align="left" width="20%" class="etiqueta2">
									  			<label>Dependencia Contratante</label>   		
											</td>	
											<td align="left" width="30%">
												<input type="text" name="desDepcontratante" id="desDepcontratante" size="50" maxlength="50" onkeypress="return KeyPressed(this,event,'alphanumericnotspecial', null, 'uppercase=yes', null, 'no');">
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
			
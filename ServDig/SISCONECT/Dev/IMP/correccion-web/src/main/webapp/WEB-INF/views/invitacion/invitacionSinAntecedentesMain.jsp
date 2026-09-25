<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/invitacion/invitacionSinAntecedentes.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js?v=2"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

		    <div id="dgInvitacionSinAntecedentes" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialog style="background-color: #f2fff2;">	
					<form action=""  method="post" id="invitacionSinAntecedentesForm">
						<input type="hidden" id="cveFkPatron" name="cveFkPatron">
						<input type="hidden" id="rpValidado" name="rpValidado" value="-1">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Generar Folio de Invitaci&oacute;n</td>
											</tr>
										</thead> 
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Tipo de Invitaci&oacute;n: </label>   		
												</td>	
												<td align="left" colspan="3">
													<combo:creaCombo
						  								entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr"
						  								idHtml="cveTipocorr"
						  								idHtmlContenedor="invitacionSinAntecedentesForm"
						  								param="idTipocorr" paramValue="4"	
						  								onchange="javaScript:jsLlenaPeriodos(this.value);jsValidaUnoVariosRP(this)"  														
					  								/>
					  								<label id="labelcveTipocorr"></label> 
												</td>
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Criterio de Selecci&oacute;n: </label>   		
												</td>	
												<td align="left" colspan="3">
													<select id="selectCriterios" name="idCriterioSeleccion" onchange="$('#labelselectCriterios').html('');">
														<option value="">--Por Favor Seleccione--</option>
													</select>
													<label id="labelselectCriterios"></label> 
												</td>
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Oficio Invitaci&oacute;n: </label>   		
												</td>	
												<td align="left">
													<input type="text" id="nuOficioinv" name="nuOficioinv" size="26" maxlength="25"  onkeyup="mayusculasTextField(this);" onchange="$('#labelnuFolioInvitacion').html('');">
													<label id="labelnuFolioInvitacion"></label> 
												</td>
												<td align="left" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Fecha Emisi&oacute;n: </label>   		
												</td>	
												<td align="left">
													<input type="text" id="fechaEmision" name="fechaEmision" readonly="readonly" size="12">
													<label id="labelfechaEmision"></label> 
												</td>
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2" colspan="3">
										  			<label style="color: red;">* </label>
										  			<label>Periodo a Corregir </label> 
										  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  			<label>Del: </label> 
										  			<input type="text" id="fechaIncial" name="fechaIncial" readonly="readonly" size="12">
										  			<label>  Al:  </label> 
										  			<input type="text" id="fechaFinal" name="fechaFinal" readonly="readonly" size="12">	
												</td>	
												<td>
													<label id="labelfechaPeriodo"></label> 	
												</td>
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2" colspan="4">
										  			<label>Observaciones: </label> 
												</td>	
											</tr>
											<tr class="par" >
										  		<td align="left" colspan="4">
										  			<textarea rows="3" cols="92" id="txObservaciones" name="txObservaciones" onchange="javaScript:jsCortaTextArea(this.value);"></textarea>
												</td>	
											</tr>
											<tr class="par">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<thead>
												<tr>
													<td colspan="4" class="etiqueta2"><label id="labelDom">Domicilio del Centro de Trabajo</label></td>
												</tr>
											</thead> 
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" class="etiqueta2" colspan="4">
										  			<input type="radio" id="rarioRP" name="rarioRP" value="simple" checked onclick="jsMuestraDiv(this.value);">
										  			<label>Un Registro Patronal </label>
										  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  			<input type="radio" id="rarioRP" name="rarioRP" value="varios" onclick="jsMuestraDiv(this.value);">
										  			<label>Varios Registros Patronales </label>
										  			<label id="labelRPS"></label>
												</td>	
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label style="color: red;">* </label>
										  			<label>Registro Patronal: </label>  		
												</td>	
												<td align="left">
													<input type="text" id="regPatronal" name="regPatronal" size="11" maxlength="10" onchange="$('#labelRegistroPatronal').html('');"
															 onkeyup="validaCampo('noCaracteresEspeciales','regPatronal','invitacionSinAntecedentesForm')" onkeyup = "this.value=this.value.toUpperCase()">
													<input type="button" value="Validar" onclick="jsValidarRP();">
													<label id="labelRegistroPatronal"></label> 
												</td>
												<td align="left" class="etiqueta2">
										  			<label>Nombre o Raz&oacute;n Social: </label>   		
												</td>	
												<td align="left">
													<label id="labelNomRazonSocial"></label>
												</td>
											</tr>
											<tr class="par" >
										  		<td align="left" class="etiqueta2">
										  			<label>Domicilio: </label>   		
												</td>	
												<td align="left" colspan="3">
													<label id="labelDomicilio"></label>
												</td>
											</tr>
											<tr class="par">
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr id="trRegPatronales" style="display: none;">
												<td colspan="4">
													<table>
														<thead>
															<tr>
																<td colspan="4" class="etiqueta2">Registros Patronales Asociados</td>
															</tr>
														</thead> 
														<tr class="impar">
															<td colspan="4">&nbsp;</td>
														</tr>
														<tr class="par">
															<td align="left" class="etiqueta2">
													  			<label style="color: red;">* </label>
													  			<label>Registro Patronal: </label>   		
															</td>	
															<td align="left" colspan="2">
																<input type="text" id="cveFkPatronTemp" name="cveFkPatronTemp" size="12" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','cveFkPatronTemp','invitacionSinAntecedentesForm')" onkeyup = "this.value=this.value.toUpperCase()">
																<label id="labelcveFkPatronTemp"></label>
															</td>
															<td align="center" >
																<input type="button" value="Agregar" onclick="javaScript:jsValidaAgregarRP();">
																<input type="button" value="Eliminar" onclick="javaScript:jsEliminaRP();">
															</td>
														</tr>
														<tr>
															<td colspan="4" align="center">
																<table id="tableResult" style="width:883px; ">
																	<thead>
																	</thead>
																	<tbody>
																	</tbody>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="par">
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr class="par">
												<td colspan="4" align="center">
													<input type="button" class="boton" value="Generar" onclick="javaScript:jsGenerar();">
													<input type="button" class="boton" value="Salir" name="btnSalir" id="btnSalir"
															onclick="goToWelcomePage('<%=request.getContextPath()%>')"/>
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
			
			
			<div id="confirmarInvitacion">
				<jsp:include page="confirmarInvitacion.jsp" />
			</div>	
			
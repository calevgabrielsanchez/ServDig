<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionCritSeleccion.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionExhortoOrdinarioIndividual.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
		
	<div id="cuerpo">
		<div id="dialog-mensaje" title="Mensaje de SISCONET">
			
		</div>
		
		<div class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a id="tituloArriba">Generar Nuevo Folio Promoci&oacute;n Ordinario</a></li>
					</ul>
				</div>
			</div>
		</div>
		<form:form modelAttribute="promocionCargaModel" method="POST" action="" id="promocionOrdinarioSubForm">
			<div style="background-color: #f2fff2;">
				<form:hidden path="cveSubdelegacion" id="cveSubdelegacionHdnEx"/>
				<form:hidden path="cveDelegacion" id="cveDelegacionHdnEx"/>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
							
									<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Origen de Promoci&oacute;n</b></td>
										</tr>
										<tr>
											<td colspan="4">
									  			<div id="labelSBC"></div>
										  	</td>
									  	</tr>
										<tr>
											
											<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Criterio de Selecci&oacute;n: </td>
											
										  		
										
										  	
												<td width="65%" align="left" colspan="2">
													<select id="critSele" onchange="$('form#promocionOrdinarioSubForm #labelCriterioSeleccion').html('');">
													</select>
													<div id="labelCriterioSeleccion"></div>
												</td>
												
											
										</tr>
										
									</tbody>
								</table>							
										
							
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Informaci&oacute;n del Oficio de Promoci&oacute;n</b></td>
										</tr>
										<tr>
											<td colspan="4">
									  			<div id="labelSBC2"></div>
										  	</td>
									  	</tr>
									  	<tr>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<span class="required">*</span><label id="fechaPromocionLabel" for="fechaPromocion">Fecha Oficio de Promoci&oacute;n:</label>																								
											</td>
											<td align="left" width="25%">
												<input name="fechaPromocionID" id="fechaPromocionID" maxlength="10" readonly="readonly"/>
												<label id="labelFecOficio"></label>
											</td>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<span class="required">*</span><label id="numOficionLabel" for="numOficioLabel">N&uacute;mero de Oficio de la Promoci&oacute;n: </label>																								
											</td>
											<td align="left" width="25%">
												<input name="numeroOficioID" id="numeroOficioID" maxlength="25" onkeyup="validaCampo('noCaracteresEspeciales','numeroOficioID', 'promocionCargaModel'); this.value=this.value.toUpperCase();" onchange="$('form#promocionOrdinarioSubForm #labelNumOficio').html('');"/>
												<label id="labelNumOficio"></label>
											</td>
									  	</tr>
									  	<tr>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<label id="observacionesLabel" for="observaciones">Observaciones:</label>																								
											</td>
											<td align="left" width="75%" colspan="3">
												<textarea rows="3" cols="92" id="observacionesId"  
														  onchange="validaCampo('noCaracteresEspeciales','observacionesId','promocionOrdinarioSubForm')" onkeyup="valTamTextArea(event,this,200)"></textarea>
											</td>
									  	</tr>
									  	<tr>
											<td colspan="4">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</tbody>
								</table>
								
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Domicilio Geogr&aacute;fico</b></td>
										</tr>
										<tr valign="top">
											<td align="left" colspan="2" class="etiqueta2">
												<span class="required">*</span>Calle: 
												<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly" /> 
												<label for="domCalle"></label>
											</td>
											<td align="left" colspan="2" class="etiqueta2">
												<span class="required">*</span>Colonia: 
												<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly" /> 
												<label for="refColonia"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>N&uacute;mero Exterior:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numNroext" id="numNroext" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly" /> 
												<label for="numNroext"></label>
											</td>
											<td align="left" class="etiqueta2">N&uacute;mero Interior:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numNroint" id="numNroint" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly" />
												<label for="numNroint"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>C&oacute;digo Postal:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numCodigopostal" id="numCodigopostal" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly" />
												<label for="numCodigopostal"></label>
											</td>
											<td align="left" colspan="2" valign="bottom">
												<div id="domInegiButtons" align="center">
													<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','registro')"><span class="boton">Agregar / Modificar Domicilio</span></a>
												</div>
											</td>
										</tr>
										<tr valign="top">
											<td colspan="4" align="center">
												<label id="labelDomGeo"></label>
											</td>
										</tr>
																			  	<tr>
											<td colspan="4">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										
									</tbody>
								</table>
								
								<table class="tablaverde2" style="width: 900px" border="0">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="5"><b>Datos del Patr&oacute;n</b></td>

										<tr>
											<td colspan="5">
									  			<div id="labelSBC1"></div>
										  	</td>
									  	</tr>
										<tr>
											<td align="left" width="15%" class="etiqueta2">
											<span class="required">*</span>Registro Patronal: </td>
											<td align="left" width="20%">
												<input name="regPat" id="regPat" maxlength="10" onkeypress="return jsvalidarAlfaNumerico(event);" onkeyup="mayusculasTextField(this);resetRP();"/>
												<label id="labelRegPatronal"></label>
											</td>
											<td align="left" width="20%" colspan="1" valign="bottom">
												<input type="button" value="Validar" onclick="validarRegPatronal();"/>												
											</td>
											<td align="left" width="20%" class="etiqueta2">Nombre o Raz&oacute;n Social</td>
										  	<td align="left" width="25%"><input name="razonSocial" id="razonSocial" maxlength="50" size="50" readonly="readonly" /></label></td>
										  	<!-- id delegacion y subdelegacion del regPatronal -->
										  	<form:hidden path="cveSubdelegacion" id="cveSubdelegacionHdnEx"/>
											<form:hidden path="cveDelegacion" id="cveDelegacionHdnEx"/>
										</tr>
										
									</tbody>
								</table>		
								<table class="tablaverde2" style="width: 900px">
								<tr>
											<td align="center" colspan="1" valign="bottom">
												<a href="#" id="btnPromocionar"><span class="boton">Promover</span></a>
											</td>
											
										</tr>	
								</table>	
							</td>
						</tr>
						
					</table>
				
			</div>
		</form:form>
	</div>
</html>
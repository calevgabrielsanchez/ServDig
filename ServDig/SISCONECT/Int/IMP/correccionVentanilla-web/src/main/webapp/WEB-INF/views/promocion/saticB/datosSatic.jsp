<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgPromocionDatosSaticB" 
	style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
	<div id="wrapperDialogDatosSaticB" style="background-color: #f2fff2;">
		<form action="" method="post" id="promocionFormDatosSaticB">			
			<input type="hidden" name="sdelegOrig" id="sdelegOrig" />
			<input type="hidden" name="cveTipocorr" id="cveTipocorr" />
			<input type="hidden" name="cveFkPatron" id="cveFkPatron" />
			<input type="hidden" name="cvePromocion" id="cvePromocion" />
			<input type="hidden" name="cvePromocion" id="cveDeteccion" />
			
			<fieldset>
				<table style="width: 900px" align="center">
					<tr valign="middle">
						<td align="center" width="900px" colspan="2">
							<table class="tablaverde2" style="width: 900px">
								<thead>
									<tr>
										<td colspan="4">Datos Generales de la Obra</td>
									</tr>
								</thead>
								<tbody>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>
									</tr>
									<tr valign="top" class="par">
										<td align="left"  width="25%" class="etiqueta2">Nombre
											&oacute; Raz&oacute;n Social: 
										</td>
										<td align="left" colspan="3">
											<input name="nomRazonsocial"
											id="nomRazonsocial" size="90" maxlength="80" readonly="readonly"/>
											<label for="nomRazonsocial"></label>
										</td>
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="25%" class="etiqueta2">Registro
											Patronal:</td>
										<td align="left" width="25%"><input name="regPatron"
											id="regPatron" size="25" maxlength="10" readonly="readonly"/>
											<label for="regPatron"></label></td>
										<td align="left" width="100px" colspan="1" class="etiqueta2"></td>
										<td align="left" width="25%"></td>
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="100px" class="etiqueta2">RFC:</td>
										<td align="left" width="100px"><input name="txRfcpatron"
											id="txRfcpatron" size="25" maxlength="13" readonly="readonly"/>
											<label for="txRfcpatron"></label></td>
										<td align="left" width="110px" class="etiqueta2">Curp
											Patr&oacute;n:</td>
										<td align="left" width="90px"><input name="txCurppatron"
											id="txCurppatron" size="25" maxlength="18" readonly="readonly"/>
											<label for="txCurppatron"></label></td>
									</tr>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>
									</tr>
									<tr>
										<td align="left" width="25%" class="etiqueta2">SATIC</td>
										<td align="left" width="25%">
											<input	name="bandera" id="bandera" readonly="readonly" size="25"/>
										</td>										
									</tr>	
									<tr>
										<td align="left" width="25%" class="etiqueta2">N&uacute;mero de
											Registro de la Obra</td>
										<td align="left" width="25%"><input
											name="numRegObra" id="numRegObra"
											readonly="readonly" size="25"/> <label for="numRegObra"></label>
										</td>										
										<td align="left" width="25%" class="etiqueta2">Tipo de
											Incidencia</td>
										<td align="left" width="25%"><input
											name="incidencia" id="incidencia"
											readonly="readonly"/ size="25">
											<label for="incidencia"></label></td>
									</tr>									
									<tr>
										<td align="left" width="25%" class="etiqueta2">Fecha
											Estimada de Inicio de la Obra</td>
										<td align="left" width="25%"><input
											name="fechaIncial" id="fechaIncial"
											readonly="readonly" size="25"/> 
											<label for="fechaIncial"></label>
										</td>										
										<td align="left" width="25%" class="etiqueta2">Fecha
											Estimada de T&eacute;rmino de la obra:</td>
										<td align="left" width="25%"><input
											name="fechaFinal" id="fechaFinal"
											readonly="readonly" size="25"/>
											<label for="fechaFinal"></label></td>
									</tr>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>
									</tr>																																			
								</tbody>
								
							</table>
							<table class="tablaverde2" style="width: 900px">
								<thead>
									<tr>
										<td colspan="4">Ubicaci&oacute;n de la Obra</td>
									</tr>
								</thead>
								<tbody>
								<tr valign="top" class="impar">
									<td align="left" colspan="4">&nbsp;</td>
								</tr>
								<tr>
										<td align="left" width="10%" class="etiqueta2">Calle:</td>
										<td align="left" width="25%"><input
											name="domCalle" id="domCalle"
											readonly="readonly" size="55"/> <label for="domCalle"></label>
										</td>										
										<td align="left" width="10%" class="etiqueta2">Colonia:</td>
										<td align="left" width="25%"><input
											name="refColonia" id="refColonia"
											readonly="readonly"/ size="55">
											<label for="refColonia"></label></td>
									</tr>									
									<tr>
										<td align="left" width="10%" class="etiqueta2">N&uacute;mero:</td>
										<td align="left" width="25%"><input
											name="numNroext" id="numNroext"
											readonly="readonly" size="55"/> 
											<label for="numNroext"></label>
										</td>										
										<td align="left" width="10%" class="etiqueta2">C&oacute;digo Postal:</td>
										<td align="left" width="25%"><input
											name="numCodigopostal" id="numCodigopostal"
											readonly="readonly" size="55"/>
											<label for="numCodigopostal"></label></td>
									</tr>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>
									</tr>		
								</tbody>
							</table>																													
						</td>												
					</tr>
					<tr>
						<td>
							<table style="width: 430px" align="center">
								<tr valign="middle">
									<td align="center" width="430px">
										<table class="tablaverde2" style="width: 430px">
											<thead>
												<tr>
													<td colspan="4">Periodos Faltantes</td>
												</tr>
											</thead>
											<tbody>
												<tr valign="top" class="impar">
													<td align="left" colspan="4">&nbsp;</td>
												</tr>
												<tr valign="top" class="par">
													<td>
														<select id="periodosfaltantes" multiple="multiple" style="height:200px;width:200px;text-align: center;"></select>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="width: 430px" align="center">
								<tr valign="middle">
									<td align="center" width="430px">
										<table class="tablaverde2" style="width: 430px">
											<thead>
												<tr>
													<td colspan="4">Periodos Presentados</td>
												</tr>
											</thead>
											<tbody>
												<tr valign="top" class="impar">
													<td align="left" colspan="4">&nbsp;</td>
												</tr>
												<tr valign="top" class="par">
													<td>
														<select id="periodosPresentados" multiple="multiple" style="height:200px;width:200px;text-align: center;"></select>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	
	<%-- <div id="promocionPeriodosSatic">
		<jsp:include page="periodos.jsp" />
	</div>					    	  --%>  
</div>
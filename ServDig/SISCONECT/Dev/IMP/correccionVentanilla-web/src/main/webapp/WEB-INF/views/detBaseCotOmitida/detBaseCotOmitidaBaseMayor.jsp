<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
input{
	text-align: right;
}

.inputFolio{
	text-align: left;
}
</style>
	<div id="dgdetBaseCotOmitidaBaseMayor" >
		<div id="dgdetBaseCotOmitidaBaseMayorCabecera" title="Determinaci&oacute;n de la Base de Cotizaci&oacute;n Omitida">
			<fieldset>
				<table style="width: 700px">
					<tr valign="middle">
						<td align="center" width="700px">
							<table class="tablaverde2" style="width: 700px">
								<tbody>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>	
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="20%">
											<label>Raz&oacute;n Social : </label>
										</td>
										<td align="left" width="25%">
											<label id="labelMayorRazonSocial"></label>
										</td>
										<td align="left" width="20%" class="etiqueta2">
											<label>Folio Correcci&oacute;n : </label>
										</td>
										<td align="left" width="25%">
											<label id="labelMayorFolio"></label>
										</td>
									</tr>
									<tr valign="top" class="impar">
										<td align="left" colspan="4">&nbsp;</td>	
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="20%" class="etiqueta2">
											<label>Registro Patronal : </label>
										</td>
										<td align="left" width="25%">
											<label id="labelMayorRegistroP"></label>
										</td>
										<td align="left" width="20%" class="etiqueta2">
											<label>Ejercicio : </label>
										</td>
										<td align="left" width="25%">
											<label id="labelMayorEjercicio"></label>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr valign="middle" class="impar">
						<td align="center" width="700px">
							<table class="tablaverde2" style="width: 700px">
								<tbody>	
									<tr valign="top" class="impar">
										<td align="left" colspan="2">
											<div class="menu_principal" style="height: 2em !important;" id="subtitleMayorDos"> 
												<!--inicia menu principal-->
												<div align="center">
													<div class="centrado">
													    <ul style="height: 1em !important;">
													    	<li><a> Determinaci&oacute;n de la Base de Cotizaci&oacute;n Omitida</a></li>
													  	</ul>
													</div>
												<!--fin centrado--> 
											  </div>
											</div>
										</td>
									</tr>						
									<tr valign="top" class="par">
										<td align="left" width="60%">
											<label>Sueldos y Salarios Registrados en Balanza de Comprobaci&oacute;n o Auxiliar de Nomina </label>
										</td>
										<td align="left" width="40%">
											$<input type="text" id="impBalanzaComp" readonly="readonly" onchange="jsSumaBalanzaComp();">
										</td>
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="60%">
											<label>Sueldos y Salarios Manifestados en la Declaraci&oacute;n Anual del ISR </label>
										</td>
										<td align="left" width="40%">
											$<input type="text" id="impISR" value="0"  maxlength="10" onchange="jsSumaBalanzaComp(this.value);jsCalculaAutoDet();moneyMask(this,2)" onkeypress="return jsvalidarNumeros(event);">
											<label style="color: red;">* </label>
											<div id="labelimpISR"></div>											
										</td>
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="60%">
											<label>Base Mayor</label>
										</td>
										<td align="left" width="40%">
											$<input type="text" id="impMayor" readonly="readonly">
										</td>
									</tr>
									<tr valign="top" class="impar">
										<td align="left" colspan="2">
												<label><b>Mas</b></label>
										</td>
									</tr>
									<tr valign="top" class="par">
										<td align="left" width="60%">
											<label>Variables del 6º Bimestre del Ejercicio Inmediato Anterior</label>
										</td>
										<td align="left" width="40%">
											$<input type="text" id="impInmediatoA" value="0" maxlength="10" onchange="jsCalculaAutoDet();moneyMask(this,2)" onkeypress="return jsvalidarNumeros(event);">
											<label style="color: red;">* </label>
											<div id="labelimpInmediatoA"></div>
										</td>
									</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="2">
												<label><b>Menos</b></label>
											</td>
										</tr>	
										<tr valign="top" class="par">
											<td align="left" width="60%">
												<label>Variables del 6º Bimestre del Ejercicio o del &Uacute;ltimo Bimestre del Periodo Adicional</label>
											</td>
											<td align="left" width="40%">
												$<input type="text" id="impAdicional" value="0" maxlength="10" onchange="jsactualizaTotalPercepciones();jsCalculaAutoDet();moneyMask(this,2)" onkeypress="return jsvalidarNumeros(event);" >
												<label style="color: red;">* </label>
												<label id="labelimpAdicional"></label>
											</td>
										</tr>												
										<tr>
											<td align="center" colspan="2" class="par">
												<table class="tablaverde2" style="width: 700px" id="tablaMenos">
												</table>
											</td>
												
										</tr>

										<tr valign="top" class="par">
											<td width="60%" align="right"><label>Excedentes Topados : </label></td>
											<td align="left" width="40%">												
												$<input type="text" id="excedentesTopados" name="excedentesTopados" readonly="readonly" value="0">
											</td>
										</tr>
										<tr valign="top" class="par">
											<td width="60%" align="right"><label>Importe : </label></td>
											<td align="left" width="40%">												
												$<input type="text" id="totalMenos" readonly="readonly">
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="60%">
												<label><b>IGUAL A : </b> Base de Cotizaci&oacute;n Autodeterminada </label>
											</td>
											<td align="left" width="40%">
												$<input type="text" id="autoDet" readonly="readonly" value="0">
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="60%">
												<label><b>MENOS : </b> Base de Cotizaci&oacute;n Pagada </label>
											</td>
											<td align="left" width="40%">
												$<input type="text" id="cotPagada" readonly="readonly" >
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="60%">
												<label><b>IGUAL A : </b> Base de Cotizaci&oacute;n Omitida </label>
											</td>
											<td align="left" width="40%">
												$<input type="text" id="cotOmitida" readonly="readonly" value="0">
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="2">&nbsp;</td>	
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="2">
												<label><b>Conceptos que Integran la Base de Cotizaci&oacute;n Omitida</b></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="700px" colspan="2">
												<table class="tablaverde2" style="width: 700px">
													<tbody>
														<tr>	
															<td align="center" width="40%">
																<select id="selectConceptos"><option id="-1">--Por favor Seleccione--</option></select>
																<a href="#" id="btnAgregarConceptos"><span class="boton">Agregar</span></a>
															</td>
															<td align="center" width="60%">
																<table id="tableConceptos">
																	
																</table>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="right" width="40%">
												<label>Importe : </label>
											</td>
											<td align="left" width="60%">
												$<input type="text" id="impConceptos" readonly="readonly">
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="2">&nbsp;</td>	
										</tr>
								</tbody>
							</table>
						</td>
					</tr>
					
				</table>
			</fieldset>
		</div>

	
		
		
	
</div>
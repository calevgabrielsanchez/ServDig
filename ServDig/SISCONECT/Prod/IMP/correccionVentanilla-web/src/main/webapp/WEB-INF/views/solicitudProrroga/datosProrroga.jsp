<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
		
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<div id="dialog-error" title="Mensaje de SISCONET"></div>
	<div id="dgProrrogaValida"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDatosProrroga" style="background-color: #f2fff2;">	
					<form:form modelAttribute="crtAnexosolcorrpat" action="prorroga/muestraReporte.do"
						method="post" id="prorrogaFormDatos">
						<form:hidden path="cveSolicitudCorr" id="cveSolicitudCorr"/>
						<form:hidden path="numExterior" id="hiddenNumExterior"/>
						<form:hidden path="numExteriorAlfa" id="hiddenNumExteriorAlfa"/>
						<form:hidden path="numInterior" id="hiddenNumInterior"/>
						<form:hidden path="numInteriorAlfa" id="hiddeNumInteriorAlfa"/>
						<input id="cveNroRegObra" type="hidden" value=""/>
						<input id="idTipoDeSolicitud" type="hidden" value=""/>
						<input id="idSubDelegacion" type="hidden" value=""/>
						<input id="fechaCadenaOriginal" type="hidden" value=""/>
						<input id="cveDelegacion" type="hidden" value=""/>
						<input id="cveSubDelegacion" type="hidden" value=""/>
						<input id="numeroTrabajadores" type="hidden" value=""/>
						
						<input id="actividad" type="hidden" value=""/>
						<input id="clase" type="hidden" value=""/>
						<input id="fraccion" type="hidden" value=""/>
						<input id="prima" type="hidden" value=""/>
						<fieldset>
							<table class="tablaverde2" border="1" style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
										<table   class="tablaverde2" style="width: 100%">
										  	  <thead>
									  	        <tr >
									  		     <td colspan="4">Solicitud de Pr&oacute;rroga para la Presentaci&oacute;n de la Correcci&oacute;n Patronal</td>
									  	       </tr>
									          </thead>
											<tbody>
												<tr>
									  		     <td colspan="4">&nbsp;</td>
									  	       </tr>										
												<tr>
													<td colspan="3" align="left">
														<form:input path="txRazonSocial" id="txRazonSocialInput" readonly="true" size="98" maxlength="90" />
														<form:errors path="txRazonSocial" />												
													</td>
													<td align="left">
														<form:input path="nuFolio" id="txNuFolio" size="30" readonly="true" maxlength="14" readonly="true"/>
														<form:errors path="nuFolio" />												
													</td>
												</tr>
												<tr>
													<td align="left" class="etiqueta2" colspan="3">Nombre o Denominaci&oacute;n Social</td>
													<td align="left" class="etiqueta2">Folio de Correci&oacute;n</td>
												</tr>
												<tr>
													<td align="left">
														<form:input path="registroPatronal" id="txRegistroPatronalInput" readonly="true" size="25" maxlength="25" />
														<form:errors path="registroPatronal" />												
													</td>
													<td align="left">
														<form:input path="digitoVerificador" id="txdigitoVerificadorInput" readonly="true" size="5" maxlength="5"/>
														<form:errors path="digitoVerificador" />												
													</td>
													<td align="left">
														<form:input path="txCurp" id="txCurpInput" readonly="true" size="25" maxlength="25" />
														<form:errors path="txCurp" />												
													</td>
													<td align="left">
														<form:input path="txRfc" id="txRfcInput" readonly="true" size="30" maxlength="20" />
														<form:errors path="txRfc" />												
													</td>
												</tr>
												<tr>
													<td  align="left" class="etiqueta2">N&uacute;mero Registro Patronal</td>
													<td  align="left" class="etiqueta2">Dig. Ver.</td>
													<td  align="left" class="etiqueta2">Clave Unica de Registro de Poblaci&oacute;n</td>
													<td   align="left"class="etiqueta2">Registro Federal de Contribuyentes</td>
												</tr>
												<tr>
													<td align="left">
														<input type="text" path="solicitudCorreccion.fecFechaPeriodoIni" id="txFechaInicialInput" readonly="true" size="25" maxlength="25" />											
													</td>
													<td align="left">	
														<input type="text" path="solicitudCorreccion.fecFechaPeriodoFin" id="txFechaFinalInput" readonly="true" size="25" maxlength="25" />											
													</td>
													<td align="left">
													  <input type="text" path="solicitudCorreccion.fecFechaElacoracionCorreccion" id="txFecElaboracionInput" readonly="true" size="25" maxlength="25" />												
													</td>
													<td align="left">
													 <input type="text" path="solicitudCorreccion.fecFechaLimite" id="txFechaLimiteInput" readonly="true" size="25" maxlength="25" />											
													</td>
												</tr>
												<tr>
													<td  align="left" class="etiqueta2">Fecha Inicial</td>
													<td  align="left" class="etiqueta2">Fecha Final</td>
													<td  align="left" class="etiqueta2">Fecha de Elaboraci&oacute;n</td>
													<td   align="left"class="etiqueta2">Fecha Limite</td>
												</tr>
												<tr>
												   <td align="left" colspan="2">
														<form:input path="tipoCorreccion" id="txtipoCorreccionInput" readonly="true" size="55" maxlength="60" />
														<form:errors path="tipoCorreccion" />												
													</td>
													<td align="left" colspan="2">
													 <input type="text" path="solicitudCorreccion.fecFechaRecepcionOficio" id="txfecFechaRecepcionOficioInput" readonly="true" size="25" maxlength="25" />										
													</td>
												</tr>
												<tr>
													<td colspan="2" align="left" class="etiqueta2">Tipo de Correcci&oacute;n</td>
													<td colspan="2"  align="left"class="etiqueta2">Fecha de Invitaci&oacute;n</td>
												</tr>
												<tr>
									  		     <td colspan="4">&nbsp;</td>
									  	       </tr>
											</tbody>
										</table>
										
										<table  class="tablaverde2" style="width: 900px">
										  	  <thead>
									  	        <tr >
									  		   <td colspan="5"> Domicilio Fiscal o Del Centro de Trabajo</td>
									  	       </tr>
									         </thead>
											<tbody>
											   <tr>
									  		     <td colspan="5">&nbsp;</td>
									  	       </tr>	
											    <tr>
													<td align="left">
														<form:input path="calle" id="calleRegPatInput" readonly="true" size="50" maxlength="50" />
														<form:errors path="calle" />												
													</td>
													<td align="left">
													<input type="text" path="numExterior" id="numExteriorRegPatInput" readonly="true" size="10" maxlength="50" />												
													</td>
													<td align="left">
													<input type="text" path="numInterior" id="numInteriorRegPatInput" readonly="true" size="10" maxlength="50" />										
													</td>
													<td align="left" colspan="2">
														<form:input path="colonia" id="coloniaRegPatInput" readonly="true" size="50" maxlength="50" />
														<form:errors path="colonia" />												
													</td>
												</cdtr>
												<tr>
													<td  align="left" class="etiqueta2">Calle</td>
													<td  align="left" class="etiqueta2">N&uacute;mero exterior</td>
													<td  align="left" class="etiqueta2">N&uacute;mero interior</td>
													<td   align="left" colspan="2" class="etiqueta2">Colonia</td>
												</tr>

												<tr>
													<td align="left">
														<form:input path="municipio" id="municipioRegPatInput" readonly="true" size="50" maxlength="50" />
														<form:errors path="municipio" />												
													</td>
													<td align="left" colspan="2">
														<form:input path="localidad" id="localidadRegPatInput" readonly="true" size="30" maxlength="50" />
														<form:errors path="localidad" />												
													</td>
													<td align="left">
														<form:input path="entidadFederativa" id="entidadFederativaRegPatInput" readonly="true" size="30" maxlength="50" />
														<form:errors path="entidadFederativa" />												
													</td>
													<td align="left">
														<form:input path="codigoPostal" id="codigoPostalRegPatInput" readonly="true" size="10" maxlength="10" />
														<form:errors path="codigoPostal" />												
													</td>
												</tr>
												<tr>
													<td align="left" class="etiqueta2">Municipio</td>
													<td align="left" colspan="2" class="etiqueta2">Localidad</td>
													<td align="left" class="etiqueta2">Entidad federativa</td>
													<td class="etiqueta2">C&oacute;digo Postal</td>
												</tr>
												<tr>
													<td  align="left">
														<form:input path="txTelefono" id="telefonoRegPatInput" readonly="true" size="20" maxlength="30" />
														<form:errors path="txTelefono" />												
													</td>
													<td  align="left" colspan="2">
														<form:input path="txEmail" id="emailRegPatInput" readonly="true" size="30" maxlength="50" />
														<form:errors path="txEmail" />												
													</td>
													<td  colspan="2">
														&nbsp;										
													</td>
												</tr>
												<tr>
													<td  align="left" class="etiqueta2">Telefono</td>
													<td  align="left" colspan="2" class="etiqueta2">Correo electronico</td>
													<td  colspan="2" class="etiqueta2">&nbsp;</td>
												</tr>
												<tr>
													<td  align="left" colspan="5" class="etiqueta2"><span class="required">*</span>Motivo por el que solicita pr&oacute;rroga por 10 d&iacute;as h&aacute;biles 
													para presentar la correci&oacute;n </td>
												</tr>
												<tr>
													<td colspan="5"><form:textarea path="motivo" id="motivoInput" style="width: 900px; height: 65px;" 
													onkeyup="verificarComentario()" /> </td>
													<form:errors path="motivo" />	
												</tr>
												<tr>
													<td  align="left">
														<form:input path="txRepresentanteLegal" id="txRepresentanteLegalInput" size="40" maxlength="80"
															onkeyup="validaCampo('PermiteSoloLetrasYPunto','txRepresentanteLegalInput','prorrogaFormDatos')"
														 />
														<form:errors path="txRepresentanteLegal" />												
													</td>
													<td   align="left" colspan="2">
														<form:input path="lugar" id="txLugarInput" size="35" maxlength="100"
															onkeyup="validaCampo('PermiteSoloLetrasYPunto','txLugarInput','prorrogaFormDatos')"
														 />
														<form:errors path="lugar" />												
													</td>
													<td  colspan="2">
														&nbsp;										
													</td>
												</tr>
												<tr>
													<td   align="left"class="etiqueta2"><span class="required">*</span>Nombre del patr&oacute;n o representante legal:</td>
													<td  align="left" colspan="2" class="etiqueta2"><span class="required">*</span>Lugar:</td>
													<td  colspan="2" class="etiqueta2">&nbsp;</td>
												</tr>
											</tbody>
										</table>
										
									</td>
								</tr>
							</table>
							<form:hidden path="firmaElectronica"/>
							<form:hidden path="cadenaOriginal"/>
							<form:hidden path="tipoCertificado"/>
						</fieldset>
						
					</form:form>	
				
		    	</div>
			</div> 											

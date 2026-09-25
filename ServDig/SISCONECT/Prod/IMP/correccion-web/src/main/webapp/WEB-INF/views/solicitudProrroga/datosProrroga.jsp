<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
		
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<div id="dialog-error" title="Mensaje de SISCONET"></div>
<div id="dgProrrogaValida">
    <div id="wrapperDatosProrroga">	
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
				<table>
					<tr>
						<td>
							<table style="border-collapse: separate; border-spacing:  5px 5px;">
						  	  	<thead>
				  	        		<tr>
						  		    	<td colspan="4">
						  		    		<div class="separadorseccion">
												<span>
													Solicitud de pr&oacute;rroga para la presentaci&oacute;n de la correcci&oacute;n patronal
												</span>
											</div>
										</td>
						  	       	</tr>
					          	</thead>
								<tbody>	
									<tr>
										<td colspan="2">
											<label>Nombre o denominaci&oacute;n social</label>
										</td>
										<td>
											<label>Folio de correci&oacute;n</label>
										</td>
									</tr>									
									<tr>
										<td colspan="2" align="left">
											<form:input path="txRazonSocial" id="txRazonSocialInput" readonly="true" size="60" maxlength="90" />
											<form:errors path="txRazonSocial" />												
										</td>
										<td align="left">
											<form:input path="nuFolio" id="txNuFolio" size="30" readonly="true" maxlength="14" readonly="true"/>
											<form:errors path="nuFolio" />												
										</td>
									</tr>
									<tr>
										<td><label>N&uacute;mero registro patronal</label></td>
										<td><label>Dig. ver.</label></td>
										<td><label>Clave &Uacute;nica de Registro de Poblaci&oacute;n</label></td>
<!-- 										<td><label>Registro Federal de Contribuyentes</label></td> -->
									</tr>
									<tr>
										<td align="left">
											<form:input path="registroPatronal" id="txRegistroPatronalInput" readonly="true" size="30" maxlength="25" />
											<form:errors path="registroPatronal" />												
										</td>
										<td align="left">
											<form:input path="digitoVerificador" id="txdigitoVerificadorInput" readonly="true" size="5" maxlength="5"/>
											<form:errors path="digitoVerificador" />												
										</td>
										<td align="left">
											<form:input path="txCurp" id="txCurpInput" readonly="true" size="30" maxlength="25" />
											<form:errors path="txCurp" />												
										</td>
<!-- 										<td align="left"> -->
<%-- 											<form:input path="txRfc" id="txRfcInput" readonly="true" size="30" maxlength="20" /> --%>
<%-- 											<form:errors path="txRfc" />												 --%>
<!-- 										</td> -->
									</tr>
									<tr>
										<td><label>Registro Federal de Contribuyentes</label></td>
										<td><label>Fecha l&iacute;mite</label></td>
										<td><label></label></td>
										<td><label></label></td>
									</tr>
									<tr>
										<td align="left">
											<form:input path="txRfc" id="txRfcInput" readonly="true" size="30" maxlength="20" />
											<form:errors path="txRfc" />												
										</td>
										<td align="left">
											 <input type="text" path="solicitudCorreccion.fecFechaLimite" id="txFechaLimiteInput" readonly="true" size="25" maxlength="25" />											
										</td>
										<td></td>
										<td></td>									
									</tr>
									<tr>
										<td><label>Fecha inicial</label></td>
										<td><label>Fecha final</label></td>
										<td><label>Fecha de elaboraci&oacute;n</label></td>
<!-- 										<td><label>Fecha l&iacute;mite</label></td> -->
									</tr>
									<tr>
										<td align="left">
											<input type="text" path="solicitudCorreccion.fecFechaPeriodoIni" id="txFechaInicialInput" readonly="true" size="30" maxlength="25" />											
										</td>
										<td align="left">	
											<input type="text" path="solicitudCorreccion.fecFechaPeriodoFin" id="txFechaFinalInput" readonly="true" size="25" maxlength="25" />											
										</td>
										<td align="left">
										  <input type="text" path="solicitudCorreccion.fecFechaElacoracionCorreccion" id="txFecElaboracionInput" readonly="true" size="30" maxlength="25" />												
										</td>
<!-- 										<td align="left"> -->
<!-- 										 <input type="text" path="solicitudCorreccion.fecFechaLimite" id="txFechaLimiteInput" readonly="true" size="30" maxlength="25" />											 -->
<!-- 										</td> -->
									</tr>
									<tr>
										<td colspan="2"><label>Tipo de correcci&oacute;n</label></td>
										<td colspan="2"><label>Fecha de invitaci&oacute;n</label></td>
									</tr>
									<tr>
									   <td align="left" colspan="2">
											<form:input path="tipoCorreccion" id="txtipoCorreccionInput" readonly="true" size="60" maxlength="60" />
											<form:errors path="tipoCorreccion" />												
										</td>
										<td align="left" colspan="2">
										 <input type="text" path="solicitudCorreccion.fecFechaRecepcionOficio" id="txfecFechaRecepcionOficioInput" readonly="true" size="30" maxlength="25" />										
										</td>
									</tr>

									<tr>
						  		     <td colspan="4">&nbsp;</td>
						  	       </tr>
								</tbody>
							</table>
							
							<table style="border-collapse: separate; border-spacing:  5px 5px;">
								<thead>
					  	        	<tr>
				  		   				<td colspan="5">
				  		   					<div class="separadorseccion">
												<span>
													Domicilio fiscal o del centro de trabajo
												</span>
											</div>
					  		   			</td>
						  	     	</tr>
					       		</thead>
								<tbody>
									<tr>
										<td><label>Calle</label></td>
										<td><label>N&uacute;mero exterior</label></td>
										<td><label>N&uacute;mero interior</label></td>
										<td><label></label></td>
									</tr>
								    <tr>
										<td>
											<form:input path="calle" id="calleRegPatInput" readonly="true" size="42" maxlength="50" />
											<form:errors path="calle" />												
										</td>
										<td>
											<input type="text" path="numExterior" id="numExteriorRegPatInput" readonly="true" size="10" maxlength="50" />												
										</td>
										<td>
											<input type="text" path="numInterior" id="numInteriorRegPatInput" readonly="true" size="10" maxlength="50" />										
										</td>
<!-- 										<td colspan="2"> -->
<%-- 											<form:input path="colonia" id="coloniaRegPatInput" readonly="true" size="43" maxlength="50" /> --%>
<%-- 											<form:errors path="colonia" />												 --%>
<!-- 										</td> -->
									</tr>
									<tr>
										<td><label>Colonia</label></td>
										<td><label>C&oacute;digo Postal</label></td>
										<td><label></label></td>
										<td><label></label></td>
									</tr>
									<tr>
										<td colspan="1">
											<form:input path="colonia" id="coloniaRegPatInput" readonly="true" size="42" maxlength="50" />
											<form:errors path="colonia" />												
										</td>
										<td>
											<form:input path="codigoPostal" id="codigoPostalRegPatInput" readonly="true" size="9" maxlength="10" />
											<form:errors path="codigoPostal" />												
										</td>
										<td></td>
										<td></td>
									</tr>									
									<tr>
										<td><label>Municipio</label></td>
										<td colspan="2"><label>Localidad</label></td>
										<td><label>Entidad federativa</label></td>
										<td><label></label></td>
									</tr>
									<tr>
										<td>
											<form:input path="municipio" id="municipioRegPatInput" readonly="true" size="42" maxlength="50" />
											<form:errors path="municipio" />												
										</td>
										<td colspan="2">
											<form:input path="localidad" id="localidadRegPatInput" readonly="true" size="30" maxlength="50" />
											<form:errors path="localidad" />												
										</td>
										<td>
											<form:input path="entidadFederativa" id="entidadFederativaRegPatInput" readonly="true" size="30" maxlength="50" />
											<form:errors path="entidadFederativa" />												
										</td>
<!-- 										<td> -->
<%-- 											<form:input path="codigoPostal" id="codigoPostalRegPatInput" readonly="true" size="9" maxlength="10" /> --%>
<%-- 											<form:errors path="codigoPostal" />												 --%>
<!-- 										</td> -->
									</tr>
									<tr>
										<td><label>Tel&eacute;fono</label></td>
										<td colspan="3"><label>Correo electr&oacute;nico</label></td>
									</tr>
									<tr>
										<td>
											<form:input path="txTelefono" id="telefonoRegPatInput" readonly="true" size="42" maxlength="30" />
											<form:errors path="txTelefono" />												
										</td>
										<td colspan="3">
											<form:input path="txEmail" id="emailRegPatInput" readonly="true" size="30" maxlength="50" />
											<form:errors path="txEmail" />												
										</td>
									</tr>

									<tr>
										<td colspan="5">
											<span class="required">*</span>
											<label>
												Motivo por el que solicita pr&oacute;rroga por 10 d&iacute;as h&aacute;biles para presentar la correci&oacute;n 
											</label>
										</td>
									</tr>
									<tr>
										<td colspan="5">
											<form:textarea path="motivo" id="motivoInput" style="width: 960px; height: 65px;" onkeyup="verificarComentario()" /> </td>
										<form:errors path="motivo" />	
									</tr>
									<tr>
										<td>
											<span class="required">*</span><label>Nombre del patr&oacute;n o representante legal:</label>
										</td>
										<td colspan="3">
											<span class="required">*</span><label>Lugar:</label>
										</td>
									</tr>
									<tr>
										<td>
											<form:input path="txRepresentanteLegal" id="txRepresentanteLegalInput" size="40" maxlength="80"
												onkeyup="validaCampo('PermiteSoloLetrasYPunto','txRepresentanteLegalInput','prorrogaFormDatos')"/>
											<form:errors path="txRepresentanteLegal" />												
										</td>
										<td colspan="3">
											<form:input path="lugar" id="txLugarInput" size="35" maxlength="100"
												onkeyup="validaCampo('PermiteSoloLetrasYPunto','txLugarInput','prorrogaFormDatos')"
											 />
											<form:errors path="lugar" />												
										</td>
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

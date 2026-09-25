<%@ include file="../general/taglibs.jsp"%>
		
		
<table style="width: 97%">
				<tr style="width: 100%">
					<td style="width: 50%">
						<fieldset  style="height: 700px" >
							<legend><strong><spring:message code="tramite.detalle.domicilioAnterior" /></strong></legend>			
							<table>			
							
										<!-- Domicilio -->
										<tr>
											<td align="left"><spring:message code="tramite.detalle.codigoPostal" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.codigoPostal.codigoPostal}"
												style="width: 180px" /></td>
							
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.nombre}"
												style="width: 180px" /></td>
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.localidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.nombre}"
												style="width: 180px" /></td>
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.delegacionMun" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.municipio.nombre}"
												style="width: 180px" /></td>
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.entidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.municipio.entidadFederativa.nombre}"
												style="width: 180px" /></td>
							
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadPrimaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadPrimaria.nombre}"
												style="width: 180px" /></td>
											
										</tr>
										
										<tr>
											<td ><spring:message code="label.numeroExt" />
												:</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.numExterior1}"
														style="width: 40px" /></td>
										</tr>
										<tr>
											<td ><spring:message code="label.numeroLExt" />
											:</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.numExteriorAlf}"
														style="width: 40px" /></td>
										</tr>
										<tr>
											<td ><spring:message code="label.numeroInt" />:</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.numInterior}"
														style="width: 40px" /></td>
										</tr>		
										<tr>
											<td ><spring:message code="label.numeroLInt" />
												:</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.numInteriorAlf}"
														style="width: 40px" /></td>
										</tr>
										<tr>	
											<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.numExterior2}"
														style="width: 40px" /></td>
										</tr>
										<!-- Referencia 1 -->
											
										<tr>
											<td ><strong><spring:message code="tramite.detalle.referencia1" /></strong></td>
											
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
											
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPrimaria.nombre}"
												style="width: 180px" /></td>
										
										</tr>
										
										
								<!-- Referencia 2 -->
										<tr>
											<td ><strong><spring:message code="tramite.detalle.referencia2" /></strong>
											</td>
										
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
										
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaSecundaria.nombre}"
												style="width: 180px" /></td>
										</tr>
										
										<!-- Referencia 3 -->
										
										<tr>
											<td><strong><spring:message code="tramite.detalle.referencia3" /></strong>
											</td>
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
										
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPosterior.nombre}"
												style="width: 180px" /></td>
										</tr>
													
								</table>		
							</fieldset>	
						</td>
						<td style="width: 50%">
						<fieldset  style="height: 700px" >
							<legend><strong><spring:message code="tramite.detalle.domicilioNuevo" /></strong></legend>			
							<table>			
							
										<!-- Domicilio -->
										<tr>
											<td align="left"><spring:message code="tramite.detalle.codigoPostal" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.codigoPostal.codigoPostal}"
												style="width: 180px" /></td>
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.nombre}"
												style="width: 180px" /></td>
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.localidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.nombre}"
												style="width: 180px" /></td>
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.delegacionMun" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.municipio.nombre}"
												style="width: 180px" /></td>
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.entidad" />: 
												</td>
												<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.municipio.entidadFederativa.nombre}"
													style="width: 180px" /></td>
											</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadPrimaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadPrimaria.nombre}"
												style="width: 180px" /></td>
											
							
										</tr>
										
										
										<tr>
											<td ><spring:message code="label.numeroExt" />
												:</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.numExterior1}"
														style="width: 40px" /></td>
										</tr>
										<tr>
											<td ><spring:message code="label.numeroLExt" />
											:</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.numExteriorAlf}"
														style="width: 40px" /></td>
										</tr>
										<tr>
											<td ><spring:message code="label.numeroInt" />:</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.numInterior}"
														style="width: 40px" /></td>
										</tr>		
										<tr>
											<td ><spring:message code="label.numeroLInt" />
												:</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.numInteriorAlf}"
														style="width: 40px" /></td>
										</tr>
										<tr>	
											<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.numExterior2}"
														style="width: 40px" /></td>
										</tr>										
										<!-- Referencia 1 -->
										<tr>
											<td ><strong><spring:message code="tramite.detalle.referencia1" /></strong></td>
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
											
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPrimaria.nombre}"
												style="width: 180px" /></td>
										</tr>
										
										
								<!-- Referencia 2 -->
										<tr>
											<td ><strong><spring:message code="tramite.detalle.referencia2" /></strong>
											</td>
											
							
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
											
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaSecundaria.nombre}"
												style="width: 180px" /></td>
											
							
										</tr>
										
										<tr>
											<td ><strong><spring:message code="tramite.detalle.referencia3" /></strong>
											</td>
										
							
										</tr>
										<tr>
											<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
											</td>
											<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
												style="width: 180px" /></td>
											
							
										</tr>
										
										<tr>
											<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
											</td>
											<td>
												<input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPosterior.nombre}"
												style="width: 180px" /></td>
											
							
										</tr>
													
								</table>		
							</fieldset>	
						</td>
					</tr>
</table>
					
		
				
	
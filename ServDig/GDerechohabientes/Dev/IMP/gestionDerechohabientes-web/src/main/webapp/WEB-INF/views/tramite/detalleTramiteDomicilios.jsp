<%@ include file="../general/taglibs.jsp"%>
		
		
			
			

			<!-- Domicilio -->
			<tr>
				<td colspan="6"><br></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.domicilioAnterior" /></strong></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.domicilioNuevo" /></strong></td>
				
			</tr>

			<tr>
				<td align="left"><spring:message code="tramite.detalle.codigoPostal" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.codigoPostal.codigoPostal}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.codigoPostal" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.codigoPostal.codigoPostal}"
					style="width: 180px" /></td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.localidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.localidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.delegacionMun" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.municipio.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.delegacionMun" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.municipio.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.entidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.asentamiento.localidad.municipio.entidadFederativa.nombre}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.entidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.asentamiento.localidad.municipio.entidadFederativa.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td colspan="4"><br></td>
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadPrimaria.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadPrimaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraExt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.numExterior1} ${domicilioAnterior.numExteriorAlf}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraExt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.numExterior1} ${domicilioActual.numExteriorAlf}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraExtNoOficial" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.numExterior2}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraExtNoOficial" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.numExterior2}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraInt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.numInterior} ${domicilioAnterior.numInterior}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraInt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.numInterior} ${domicilioActual.numInteriorAlf}"
					style="width: 180px" /></td>
				

			</tr>
			
			<!-- Referencia 1 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia1" /></strong></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia1" /></strong></td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPrimaria.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPrimaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
	<!-- Referencia 2 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia2" /></strong>
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia2" /></strong>
				</td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaSecundaria.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaSecundaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<!-- Referencia 3 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia3" /></strong>
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia3" /></strong>
				</td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioAnterior.vialidadReferenciaPosterior.nombre}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${domicilioActual.vialidadReferenciaPosterior.nombre}"
					style="width: 180px" /></td>
				

			</tr>
						
			
		
		
				
	
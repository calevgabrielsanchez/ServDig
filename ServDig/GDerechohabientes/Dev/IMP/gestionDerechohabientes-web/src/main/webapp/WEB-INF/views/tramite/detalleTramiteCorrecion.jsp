<%@ include file="../general/taglibs.jsp"%>
		<%@ include file="detalleTramiteDerechohabiente.jsp" %>
		<br>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloCorrecionDatos" /></strong></legend>
		<table>
			
			<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.datosCorrecion" /></strong></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.datosCorregidos" /></strong></td>
				
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.nombre" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${correccion.nombre}" />
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.nombre" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${corregido.nombre}" />
				</td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aPaterno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.primerApellido}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
					<td align="left"><spring:message code="tramite.detalle.aPaterno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.primerApellido}"
					style="width: 180px" /></td>	
			</tr>
						<tr>
				<td align="left"><spring:message code="tramite.detalle.aMaterno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.segundoApellido}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
					<td align="left"><spring:message code="tramite.detalle.aMaterno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.segundoApellido}"
					style="width: 180px" /></td>	
			</tr>
			
			<tr>	
				<td align="left"><spring:message
					code="tramite.detalle.estadoCivil" /></td>
				<td><input type="text" readonly="readonly" value="${correccion.estadoCivil.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message
					code="tramite.detalle.estadoCivil" /></td>
				<td><input type="text" readonly="readonly" value="${corregido.estadoCivil.descripcion}"
					style="width: 180px" /></td>
				
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.curp" />: </td>

				<td ><input type="text" readonly="readonly" value="${correccion.curpCap}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>	
				<td align="left"><spring:message code="tramite.detalle.curp" />: </td>

				<td ><input type="text" readonly="readonly" value="${corregido.curpCap}"
					style="width: 180px" /></td>
					
			</tr>
			<tr>
			
				<td align="left"><spring:message
					code="tramite.detalle.fNacimiento" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${correccion.fechaNacimiento}"/>"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>	
					<td align="left"><spring:message
					code="tramite.detalle.fNacimiento" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${corregido.fechaNacimiento}"/>"
					style="width: 180px" /></td>	
			</tr>
			<tr>
				<td align="left"><spring:message
					code="tramite.detalle.lNacimiento" /></td>
				<td><input type="text" readonly="readonly" value="${correccion.lugarNacimiento.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message
					code="tramite.detalle.lNacimiento" /></td>
				<td><input type="text" readonly="readonly" value="${corregido.lugarNacimiento.nombre}"
					style="width: 180px" /></td>	
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.sexo" />: </td>

				<td ><input type="text" readonly="readonly" value="${correccion.sexo.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>	
					<td align="left"><spring:message code="tramite.detalle.sexo" />: </td>

				<td ><input type="text" readonly="readonly" value="${corregido.sexo.descripcion}"
					style="width: 180px" /></td>
					
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.parentesco" />: </td>
				<td><input type="text" readonly="readonly" value="${correccion.parentesco.descripcion}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.parentesco" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.parentesco.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			<!-- Domicilio -->
			<tr>
				<td colspan="4"><br></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.asentamiento.nombre}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.asentamiento" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.asentamiento.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadPrimaria.nombre}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadPrimaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraExt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.numExterior1} ${correccion.domicilio.numExteriorAlf}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraExt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.numExterior1} ${corregido.domicilio.numExteriorAlf}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraExtNoOficial" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.numExterior2}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraExtNoOficial" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.numExterior2}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.numLetraInt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.numInterior} ${correccion.domicilio.numInterior}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.numLetraInt" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.numInterior} ${corregido.domicilio.numInterior}"
					style="width: 180px" /></td>
				

			</tr>
			
			<!-- Referencia 1 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia1" />: </strong>
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia1" />: </strong>
				</td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaPrimaria.nombre}"
					style="width: 180px" /></td>
					<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaPrimaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
	<!-- Referencia 2 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia2" />: </strong>
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia2" />: </strong>
				</td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaSecundaria.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaSecundaria.nombre}"
					style="width: 180px" /></td>
				

			</tr>
			
			
			<!-- Referencia 3 -->
			<tr>
				<td colspan="4"><br></td>
			</tr>	
				
				<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia3" />: </strong>
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.referencia3" />: </strong>
				</td>
				

			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.tipoVialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
					style="width: 180px" /></td>
				

			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${correccion.domicilio.vialidadReferenciaPosterior.nombre}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.vialidad" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${corregido.domicilio.vialidadReferenciaPosterior.nombre}"
					style="width: 180px" /></td>
				

			</tr>
						
			</table>
		</fieldset>
		
				
	
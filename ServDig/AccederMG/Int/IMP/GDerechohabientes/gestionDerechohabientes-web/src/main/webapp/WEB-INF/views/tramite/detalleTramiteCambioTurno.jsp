<%@ include file="../general/taglibs.jsp"%>
		<%@ include file="detalleTramiteDerechohabiente.jsp" %>
		<br>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloCambioTurno" /></strong></legend>
		<table>
			
			<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.consultorioActual" /></strong></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.consultorioNuevo" /></strong></td>
				
			</tr>
			
			<tr>	
				<td align="left"><spring:message
					code="tramite.detalle.consultorio" /></td>
				<td><input type="text" readonly="readonly" value="${umfAnterior.consultorio.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>	
				<td align="left"><spring:message
					code="tramite.detalle.consultorio" /></td>
				<td><input type="text" readonly="readonly" value="${umfActual.consultorio.descripcion}"
					style="width: 180px" /></td>
				
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.turno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${umfAnterior.turno.descripcion}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
					<td align="left"><spring:message code="tramite.detalle.turno" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${umfActual.turno.descripcion}"
					style="width: 180px" /></td>	
			</tr>
			
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.medico" />: </td>

				<td ><input type="text" readonly="readonly" value="${umfAnterior.medicoFamiliar.nombre} ${umfAnterior.medicoFamiliar.primerApellido} ${umfAnterior.medicoFamiliar.segundoApellido}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>	
				<td align="left"><spring:message code="tramite.detalle.medico" />: </td>

				<td ><input type="text" readonly="readonly" value="${umfActual.medicoFamiliar.nombre} ${umfActual.medicoFamiliar.primerApellido} ${umfActual.medicoFamiliar.segundoApellido}"
					style="width: 180px" /></td>
					
			</tr>
			
			
						
			</table>
		</fieldset>
		
				
	
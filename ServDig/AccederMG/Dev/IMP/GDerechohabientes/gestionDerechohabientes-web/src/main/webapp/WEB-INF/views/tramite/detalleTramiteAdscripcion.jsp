<%@ include file="../general/taglibs.jsp"%>
		<%@ include file="detalleTramiteDerechohabiente.jsp" %>
		<br>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloAdscripcion" /></strong></legend>
		<table>
			<tr>
				<td align="right"><spring:message code="tramite.detalle.consultorio" />: 
				</td>
				<td><input type="text" readonly="readonly" value="${medico.consultorio.descripcion}"
					style="width: 150px" /></td>
				<td><br></td>
				<td align="right"><spring:message code="tramite.detalle.turno" />: </td>
				<td><input type="text" readonly="readonly" value="${medico.turno.descripcion}"
					style="width: 150px" /></td>

			</tr>
			<tr>
				<td align="right"><spring:message code="tramite.detalle.medico" />: </td>
				<td><input type="text" readonly="readonly" style="width: 150px"
					value="${medico.medicoFamiliar.nombre} ${medico.medicoFamiliar.primerApellido} ${medico.medicoFamiliar.segundoApellido}" />
				</td>
								
				
			</tr>
		
		</table>
		</fieldset>
		
				
	
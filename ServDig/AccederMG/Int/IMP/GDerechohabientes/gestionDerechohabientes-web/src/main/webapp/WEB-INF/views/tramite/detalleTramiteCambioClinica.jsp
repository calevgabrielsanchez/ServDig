<%@ include file="../general/taglibs.jsp"%>
		<%@ include file="detalleTramiteDerechohabiente.jsp" %>
		<br>
		<script type="text/javascript">
		
			$(document).ready(
					function() {
						var umfDest = ${umfActual.unidadMedicaFamiliar.idUMF};
						var umfOri = ${umfAnterior.unidadMedicaFamiliar.idUMF};
						var umfUsu = ${usuarioObj.idUmf}
						var perf = ${usuarioObj.perfilUsuario.idPerfilUsuario};
						
						if(perf == 1) {
							if(umfUsu == umfDest)
								$("#validar").show();
							else
								$("#validar").hide();
						}
					}
			);
			
		</script>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloCorrecion" /></strong></legend>
		<table>
			
			<tr>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.umfOrigen" /></strong></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="center" colspan="2"><strong><spring:message code="tramite.detalle.umfDestino" /></strong></td>
				
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.delegacion" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${umfAnterior.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.delegacion" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${umfActual.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
				</td>
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.umf" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${umfAnterior.unidadMedicaFamiliar.descripcion}" />
				</td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.umf" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px"
					value="${umfActual.unidadMedicaFamiliar.descripcion}" />
				</td>
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
				<td align="left"><spring:message code="tramite.detalle.medico" />: </td>

				<td ><input type="text" readonly="readonly" value="${umfAnterior.medicoFamiliar.nombre} ${umfAnterior.medicoFamiliar.primerApellido} ${umfAnterior.medicoFamiliar.segundoApellido}"
					style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.medico" />: </td>

				<td ><input type="text" readonly="readonly" value="${umfActual.medicoFamiliar.nombre} ${umfActual.medicoFamiliar.primerApellido} ${umfActual.medicoFamiliar.segundoApellido}"
					style="width: 180px" /></td>
					
			</tr>
			
			<%@ include file="detalleTramiteDomicilios.jsp" %>
						
			</table>
		</fieldset>
		
				
	
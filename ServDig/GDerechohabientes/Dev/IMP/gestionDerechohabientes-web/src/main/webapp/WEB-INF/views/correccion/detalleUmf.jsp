<%@ include file="../general/taglibs.jsp"%>
<br>
<fieldset style="width: 97%">
	<legend>
		<strong><spring:message
				code="tramite.detalle.tituloCircunscripcion" /> </strong>
	</legend>
	<table style="width: 100%">
		<tr style="width: 100%">
			<td style="width: 50%">
				<fieldset>
					<legend><strong><spring:message
						code="tramite.detalle.umfOrigen" /></strong></legend>
					<table style="width: 100%">
						<tr>
							<td align="left"><spring:message
									code="tramite.detalle.delegacion" />:</td>
							<td ><input type="text" readonly="readonly" style="width: 180px"
								value="${umfAnterior.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
							</td>
						</tr>
						<tr>
							<td align="left"><spring:message code="tramite.detalle.umf" />:
							</td>
							<td ><input type="text" readonly="readonly" style="width: 180px"
								value="${umfAnterior.unidadMedicaFamiliar.descripcion}" />
							</td>
						</tr>	
						<tr>
							<td align="left"><spring:message code="tramite.detalle.turno" />:
							</td>
							<td ><input type="text" readonly="readonly"
								value="${umfAnterior.turno.descripcion}" style="width: 180px" /></td>
						</tr>
						<tr>
							<td align="left"><spring:message
									code="tramite.detalle.consultorio" />:</td>
							<td ><input type="text" readonly="readonly"
								value="${umfAnterior.consultorio.descripcion}" style="width: 180px" />
							</td>
						</tr>
						<tr>
							<td align="left"><spring:message code="tramite.detalle.medico" />:
							</td>
				
							<td ><input type="text" readonly="readonly"
								value="${umfAnterior.medicoFamiliar.nombre} ${umfAnterior.medicoFamiliar.primerApellido} ${umfAnterior.medicoFamiliar.segundoApellido}"
								style="width: 180px" /></td>
						</tr>
													
					</table>
				
				</fieldset>
			</td>
			<td style="width: 50%">
				<fieldset>
				<legend><strong><spring:message
						code="tramite.detalle.umfDestino" /></strong></legend>
					<table style="width: 100%">
						<tr>
							<td align="left"><spring:message
									code="tramite.detalle.delegacion" />:</td>
							<td ><input type="text" readonly="readonly" style="width: 180px"
								value="${umfActual.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
							</td>
						</tr>
						<tr>
							<td align="left"><spring:message code="tramite.detalle.umf" />:
							</td>
							<td >
								<input type="text" readonly="readonly" style="width: 180px"
								value="${umfActual.unidadMedicaFamiliar.descripcion}" />
							</td>
						</tr>
						<tr>
							<td align="left"><spring:message code="tramite.detalle.turno" />:
							</td>
							<td ><input type="text" readonly="readonly"
								value="${umfActual.turno.descripcion}" style="width: 180px" /></td>
						</tr>
						<tr>
							<td align="left"><spring:message
									code="tramite.detalle.consultorio" />:</td>
							<td ><input type="text" readonly="readonly"
								value="${umfActual.consultorio.descripcion}" style="width: 180px" />
							</td>
				
						</tr>
						<tr>
						<td align="left"><spring:message code="tramite.detalle.medico" />:
							</td>
				
							<td ><input type="text" readonly="readonly"
								value="${umfActual.medicoFamiliar.nombre} ${umfActual.medicoFamiliar.primerApellido} ${umfActual.medicoFamiliar.segundoApellido}"
								style="width: 180px" /></td>
				
						</tr>
												
					</table>
				</fieldset>
			</td>
		</tr>
	</table>
</fieldset>



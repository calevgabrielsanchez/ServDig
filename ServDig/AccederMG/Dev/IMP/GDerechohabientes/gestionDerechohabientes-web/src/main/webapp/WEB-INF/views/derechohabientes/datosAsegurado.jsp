<%@ include file="../general/taglibs.jsp"%>

<fieldset style="width: 977px" class="titulo">
	<legend>
		<strong><spring:message code="titulo.datosAsegurado" /></strong>
	</legend>
	<table style="width: 100%">
		<COLGROUP id="col1" style="border: 1px solid lightgray;" span="6">
		<tbody style="border: 1px solid lightgray;">
			<tr>
				<th colspan="6"><spring:message code="label.infoGenreal" /></th>
			</tr>
		</tbody>
		<tbody style="border: 1px solid lightgray;">
			<tr>
				<td align="right"><spring:message code="label.nss" />:</td>
				<td>
					<input id="nss" readonly="readonly" type="text" style="width: 140px" value="<c:out value="${miGrupoFamiliar.asignacionNSS.nssStr}"/>">
				</td>
				<td align="right"><spring:message code="label.curp" />:</td>
				<td>
					<input id="curp" readonly="readonly" type="text"style="width: 140px" value="<c:out value="${miGrupoFamiliar.derechohabiente.curp}"/>">
				</td>
				<td align="right"><spring:message code="label.sexo" />:</td>
				<td>
					<input id="sexo" readonly="readonly" type="text" style="width: 140px" value="<c:out value="${miGrupoFamiliar.derechohabiente.sexo.descripcion}"/>" />
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.nombre" />:</td>
				<td>
					<input id="nombre" readonly="readonly" type="text" style="width: 140px" value="<c:out value="${miGrupoFamiliar.derechohabiente.nombre}"/>">
				</td>
				<td align="right"><spring:message code="label.primerApe" />:</td>
				<td>
					<input id="primerApellido" readonly="readonly" type="text" style="width: 140px" value="<c:out value="${miGrupoFamiliar.derechohabiente.primerApellido}"/>">
				</td>
				<td align="right" style="width: 150px"><spring:message code="label.segundoApe" />:</td>
				<td>
					<input id="segundoApellido" readonly="readonly" type="text"
					style="width: 140px"
					value="<c:out value="${miGrupoFamiliar.derechohabiente.segundoApellido}"/>">
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.lugarNac" />:</td>
				<td>
					<input id="lugarNacmiento" readonly="readonly" type="text"
					style="width: 140px"
					value="<c:out value="${miGrupoFamiliar.derechohabiente.lugarNacimiento.nombre}"/>">
				</td>
				<td align="right"><spring:message code="label.fechaNac" />:</td>
				<td>
					<input id="fechaNacimiento" readonly="readonly" type="text"
					style="width: 140px"
					value="<fmt:formatDate pattern="dd/MM/yyyy" value="${miGrupoFamiliar.derechohabiente.fechaNacimiento}"/>">
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td colspan="6"><br></td>
			</tr>
		</tbody>
		<tr>
			<th colspan="6"><spring:message code="label.datosVigencia" /></th>
		</tr>
		<tbody style="border: 1px solid lightgray;" >
			<tr>
				<td align="right"><spring:message code="label.situacion" />:</td>
				<td><input id="vigencia" readonly="readonly" type="text"
					style="width: 155px"
					value="<c:out value="${miGrupoFamiliar.estadoDerechohabiente.descripcion}"/>" />
				</td>
				
					
				<td  align="right"><spring:message code="label.umf" />: </td>
			 	<td colspan="3"><input id="umf" readonly="readonly" style="width: 510px"
						type="text"
						value="<c:out value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>" />
					</td>
			
			</tr>
			<tr>
				
				<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
					
					
				<td align="right"><spring:message code="label.subEstadoDer" />
					</td>
					<td><input type="text" readonly="readonly" style="width: 155px"
						value="${miGrupoFamiliar.subEstadoDerechohabiente.descripcion}" /></td>
					
				</c:if>
				<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 1)
						|| (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente ==3)}">
					<td align="right">&nbsp;</td>
					<td>&nbsp;</td>
				</c:if>
				

				<td align="right"><spring:message code="label.medicoFamiliar" />:
				</td>
				<td colspan="3"><input readonly="readonly" type="text"
					style="width: 510px"
					value="<c:out value="${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.nombre} ${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.primerApellido} ${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.segundoApellido}  "/>" />
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.turno" />:</td>
				<td><input id="turno" readonly="readonly" type="text"
					style="width: 155px"
					value="<c:out value="${miGrupoFamiliar.medicoEnTurno.turno.descripcion}"/>" />
				</td>
				
				<td align="right"><spring:message code="label.delegacion" />:
				</td>
				<td colspan="3"><input type="text" readonly="readonly"
					type="text" style="width: 510px"
					value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.consultorio" />:
				</td>
				<td><input id="consultorio" readonly="readonly" type="text"
					style="width: 155px"
					value="<c:out value="${miGrupoFamiliar.medicoEnTurno.consultorio.descripcion}"/>" />
				</td>
				<td align="right">Tipo pensi&oacute;n:</td>
				<td colspan="3">
					<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 510px" />
				</td>
			</tr>
			<tr>
				
				<td colspan="2" align="center">
					<c:if test="${patronIMSS}">
						<br>
						<strong>CCT 74 IMSS</strong>
						<br>
					</c:if>
				</td>
				
				<td align="right" colspan="3" ><spring:message code="label.servicioMedico" />:</td>
				<td > 
					<c:choose>
						<c:when test="${isPensionadoMod17Convenio}">
							<input id="servicioMedico" readonly="readonly" type="text" style="width: 30px"
							value="<c:out value="${miGrupoFamiliar.conDerechoSm}"/>" />
						</c:when>
						<c:otherwise>
							<c:if test="${ISMODALIDAD17}">
								<table style="width: 95%; margin-top:5px; margin-left: 15px">
									<tr>
										<td>1er<br>Nivel</td>
										<td>2do<br>Nivel</td>
										<td>3er<br>Nivel</td>
									</tr>
									<tr>
										<td><input type="text" value="NO" disabled="disabled" style="width: 20px"></td>
										<td><input type="text" value="SI" disabled="disabled" style="width: 20px"></td>
										<td><input type="text" value="SI" disabled="disabled" style="width: 20px"></td>
									</tr>
								</table>
							</c:if>
							<c:if test="${!ISMODALIDAD17}">
								<input id="servicioMedico" readonly="readonly" type="text" style="width: 30px"
										value="<c:out value="${miGrupoFamiliar.conDerechoSm}"/>" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</td>
				
				
			
			</tr>
			<tr>
				<td colspan="6"><br></td>
			</tr>
		</tbody>
	</table>
	<div align="center">
		<br> <input type="button" class="mboton"
			value="Ver detalle asegurado"
			onclick="detalleDerechohabiente(${miGrupoFamiliar.derechohabiente.idPersona})" />
	</div>
</fieldset>
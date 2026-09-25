<%@ include file="../general/taglibs.jsp"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal"%>
<div id="formaSolicitadoPor" style="display: none;">
	<form id="solicitadoPorForm">
		<table>
			<tr>
				<td class="label_patrones_data" colspan="4">
					<spring:message code="texto.solicitado.por"/>
				</td>
			</tr>
			<c:if test="${bFisica}"> 
				<tr>
					<td>
						<input type="radio" name="radioSolicitadoPor" id="radioSolicitadoPor" onclick="inhabilitaSeleccionRepresentante()" value="<%=CodigoRolTemporal.PATRON_SUJETO_OBLIGADO%>" checked="checked">
					</td>
					<td class="label_patrones">
						<spring:message code="label.patron"/>
					</td>
					<td>
						<input type="radio" name="radioSolicitadoPor" id="radioSolicitadoPor" onclick="habilitaSeleccionRepresentante()" value="<%=CodigoRolTemporal.REPRESENTANTE_LEGAL%>">
					</td>
					<td class="label_patrones">
						<spring:message code="label.tramite.respresentante.legal"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${!bFisica}"> 
				<tr>
					<td>
						<input type="radio" name="radioSolicitadoPor" id="radioSolicitadoPor" onclick="habilitaSeleccionRepresentante()" value="<%=CodigoRolTemporal.REPRESENTANTE_LEGAL%>" readonly="true" checked="checked">
					</td>
					<td class="label_patrones" colspan="3">
						<spring:message code="label.tramite.respresentante.legal"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">
					<select id="idRepresentante" onchange="obtenerDetalleRepresentante()">
						<option value="-1"><spring:message code="label.seleccione"/></option>
						<c:forEach var="repr" items="${listaRepresentantesSolicitantes}">
							<option value="${ repr.personaFisica.idPersona }">
								${ repr.personaFisica.nombre} ${repr.personaFisica.primerApellido} ${repr.personaFisica.segundoApellido} | ${repr.personaFisica.rfc} | ${repr.personaFisica.curp}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		
		<table border="1" style="width: 100%">
			<tr>
				<td class="label_patrones" width="150px;"><spring:message code="label.nombre"/></td>
				<td class="label_patrones" width="70px;"><spring:message code="label.rfc"/></td>
				<td class="label_patrones"><spring:message code="label.curp"/></td>
			</tr>
			<tr>
				<td><div id="detalleRepresentanteNombre"></div></td>
				<td><div id="detalleRepresentanteRFC"></div></td>
				<td><div id="detalleRepresentanteCURP"></div></td>
			</tr>
		</table>
	</form>
</div>
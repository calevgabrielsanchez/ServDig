<%@ include file="../../general/taglibs.jsp"%>
<c:set var="fisica" value="${sujetoObligado.fisica}"/>
<c:set var="moral" value="${sujetoObligado.moral}"/>

<fieldset class="fsInterno" style="width: 900px !important;">
	<br>
	<legend class="separadorseccion">
		<spring:message code="titulo.detalle.rp" />
	</legend>
	<table style="border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 80px !important;">
					<spring:message code="label.rfc"/>
			</td>
			<td class="label_patrones_data" style="width: 120px !important;">
				<c:if test="${fisica != null}">
					${fisica.rfc}		
				</c:if>
				<c:if test="${moral != null}">
					${moral.rfc}
				</c:if>
			</td>
			<td class="label_patrones" style="width: 150px !important">
				
				<spring:message code="label.nrp"/>
				
			</td>
			<td class="label_patrones_data" style="width: 80px !important">
				${ sujetoObligado.numeroRegistroPatronal }${ sujetoObligado.modalidad.numModalidad }${ sujetoObligado.digVerificador }
			</td>
		</tr>
	</table>
</fieldset>

<fieldset class="fsInterno" style="width: 900px !important;">
	<br>
	<legend class="separadorseccion">
		<spring:message code="label.centro.trabajo" />
	</legend>
	<table style="width: 100%; border: none !important;">
		<tr>
			<td class="label_patrones" style="width: 140px !important;">
				<label>
					<spring:message code="label.calle"/>:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.vialidadPrimaria.nombre}"></c:out> 
				</label>
			</td>
			<td class="label_patrones" style="width: 170px !important;">
				<label>
					<spring:message code="label.numero.ext" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.numExterior1}"></c:out> 
					<c:out value="${sujetoObligado.cntroTrabajo.numExteriorAlf}"></c:out> 
				</label>
			</td>
			<td class="label_patrones" style="width: 180px !important;">
				<label>
						<spring:message code="label.numero.int" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label maxlength="14" cssStyle="width:70%">
					<c:out value="${sujetoObligado.cntroTrabajo.numInterior}"></c:out> 
					<c:out value="${sujetoObligado.cntroTrabajo.numInteriorAlf}"></c:out> 
				</label>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<label>
					<spring:message code="label.ref.primaria" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label maxlength="14" cssStyle="width:70%">
					<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaPrimaria.nombre}"></c:out>
				</label>
			</td>
			<td class="label_patrones">
				<label>
					<spring:message code="label.ref.secundaria" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label maxlength="14" cssStyle="width:70%">
					<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaSecundaria.nombre}"></c:out>
				</label>
			</td>
			<td class="label_patrones">
				<label>
						<spring:message code="label.ref.posterior" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label maxlength="14" cssStyle="width:70%">
					<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaPosterior.nombre}"></c:out>
				</label>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<label>
					<spring:message code="label.colonia" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label maxlength="14" cssStyle="width:70%">
					<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.nombre}"></c:out>
				</label>
			</td>
			<td class="label_patrones">
				<label style="width:25%">
					<spring:message code="label.localidad" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.nombre}"></c:out>
				</label>
			</td>
			<td class="label_patrones">
				<label style="width:25%">
						<spring:message code="label.municipio" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.municipio.nombre}"></c:out>
				</label>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<label>
						<spring:message code="label.entidad.federativa" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}"></c:out>
				</label>
			</td>
			<td class="label_patrones">
				<label>
						<spring:message code="label.codigo.postal" />:
				</label>
			</td>
			<td class="label_patrones_data">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.codigoPostal.codigoPostal}"></c:out>
				</label>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<label>
						Descripci&oacute;n:
				</label>
			</td>
			<td class="label_patrones_data" colspan="3">
				<label>
					<c:out value="${sujetoObligado.cntroTrabajo.descripcion}"></c:out>
				</label>
			</td>
		</tr>
	</table>
</fieldset>
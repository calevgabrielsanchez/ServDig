<!-- JSP Contenido del Widget de Datos Basicos del Centro de Trabajo. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="centroTrabajo" value="${sujetoObligado.cntroTrabajo}" />
<c:set var="fisica" value="${patron.fisica}" />
<c:set var="moral" value="${patron.moral}" />

<div class="well">
	<address>
		<strong> <span>${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}</span>
		</strong><br>
		<c:if test="${fisica != null}">
			<span>${fisica.nombre}
				${fisica.primerApellido} ${fisica.segundoApellido}</span>
		</c:if>
		<c:if test="${moral != null}">
			<span>${moral.razonSocial}</span>
		</c:if>
		<br> 
		<c:choose>
			<c:when test="${patron.nombreComercial eq null }">
				<span class="no-data"> Sin Nombre Comercial</span>
			</c:when>
			<c:otherwise>
				<span>${patron.nombreComercial}</span>
			</c:otherwise>
		</c:choose>
		<br> <span>${patron.tipoPersonaFiscal}</span><br>
		<c:if test="${fisica != null}">
			
			<c:choose>
				<c:when test="${fisica.rfc eq null }">
					<span class="no-data"> No tiene RFC</span>
				</c:when>
				<c:otherwise>
					<span>${fisica.rfc}</span>
				</c:otherwise>
			</c:choose>
			<br>
			
			<span>${fisica.curp}</span>
			<br>
		</c:if>
		<c:if test="${moral != null}">
			
			<span>${moral.rfc}</span>
			<br>
		</c:if>
	</address>

	<c:choose>
		<c:when test="${centroTrabajo != null}">
			<address>
				<c:if test="${centroTrabajo.vialidadPrimaria != null}">
					<spring:message code="label.calle.num" />
					: ${centroTrabajo.vialidadPrimaria.nombre}
					${centroTrabajo.numExterior1} ${centroTrabajo.numExteriorAlf} ,
					${centroTrabajo.numInterior} ${centroTrabajo.numInteriorAlf}<br>
					<spring:message code="label.colonia" />
					: ${centroTrabajo.asentamiento.nombre}<br>
					<spring:message code="label.municipio" />
					: ${centroTrabajo.asentamiento.localidad.municipio.nombre}<br>
					<spring:message code="label.entidad.federativa" />
					:
					${centroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
					C.P. ${centroTrabajo.codigoPostal.codigoPostal}<br>
				</c:if>
				<c:if test="${centroTrabajo.vialidadPrimaria == null}">
					<div class="alert alert-warning">
						Se recomienda actualizar su domicilio.
					</div>
					${centroTrabajo.descripcion}<br>
				</c:if>
				<spring:message code="label.delegacion" />
				: ${sujetoObligado.subdelegacion.delegacion.descripcion}<br>
				<spring:message code="label.subdelegacion" />
				: ${sujetoObligado.subdelegacion.descripcion}<br>
				<c:if test="${ sujetoObligado.municipioIMSS != null }">
					<spring:message code="label.municipio.imss" />: ${sujetoObligado.municipioIMSS.descMunicipio} (${sujetoObligado.municipioIMSS.cvecMunicipioSINDO})<br>
				</c:if>
			</address>
		</c:when>
		<c:otherwise>
			<p>
				<span class="no-data">No cuenta con domicilio.</span>
			</p>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${centroTrabajo != null and not empty centroTrabajo.mediosContacto}">
			<div>
				<c:forEach items="${centroTrabajo.mediosContacto}" var="medio"
					varStatus="indice">
					<address>
						<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
						${medio.desFormaContacto}<br>
					</address>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p>
				<span class="no-data">No cuenta con medios de contacto.</span>
			</p>
		</c:otherwise>
	</c:choose>
</div>

<%@ include file="../../../general/taglibs.jsp"%>

<fieldset>
	<legend>DOMICILIO FISCAL</legend>
	<c:choose>
		<c:when test="${not empty domFiscal }">
			<address>
				<c:if test="${not empty domFiscal.calle}">
					${domFiscal.calle}
				</c:if>
				<c:if test="${not empty domFiscal.numExteriorAlf || not empty domFiscal.numExterior1}">
					<br> ${domFiscal.numExteriorAlf} ${domFiscal.numExterior1},
				</c:if>
				<c:if test="${not empty domFiscal.numInteriorAlf || not empty domFiscal.numInterior}"> 
					${domFiscal.numInteriorAlf} ${domFiscal.numInterior}
				</c:if>
				<c:if test="${not empty domFiscal.colonia}">
					<br>${domFiscal.colonia}
				</c:if>
				<c:if test="${not empty domFiscal.asentamiento.localidad.municipio.nombre}">
					<br>${domFiscal.asentamiento.localidad.municipio.nombre}
				</c:if>
				<c:if test="${not empty domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}">
					<br>${domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
				</c:if>
				<c:if test="${not empty domFiscal.asentamiento.codigoPostal.codigoPostal}">
					C.P. ${domFiscal.asentamiento.codigoPostal.codigoPostal}
				</c:if>
			</address>
		</c:when>
		<c:otherwise>
			<p>No cuenta con domicilio fiscal.</p>
		</c:otherwise>
	</c:choose>
</fieldset>
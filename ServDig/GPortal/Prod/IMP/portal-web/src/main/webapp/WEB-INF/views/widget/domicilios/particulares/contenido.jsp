<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:choose>
	<c:when test="${not empty domicilio }">
		<input type="hidden" id="idDomiclioPartWidget"
			value="${domicilio.clave}" />
		<address>
			${domicilio.vialidadPrimaria.nombre}<br>
			${domicilio.numExteriorAlf} ${domicilio.numExterior1},
			${domicilio.numInteriorAlf} ${domicilio.numInterior}<br>
			${domicilio.asentamiento.nombre}<br>
			${domicilio.asentamiento.localidad.municipio.nombre}<br>
			${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
			C.P. ${domicilio.asentamiento.codigoPostal.codigoPostal}<br>
		</address>
	</c:when>
	<c:otherwise>
		<p>No cuenta con domicilio particular.</p>
	</c:otherwise>
</c:choose>


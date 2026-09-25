<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:choose>
	<c:when test="${not empty mdmDatosEntrada.personaFisica}">
		<c:set var="persona" scope="page" value="personaFisica" />
		<c:set var="personaObj" scope="page" value="${mdmDatosEntrada.personaFisica}" />
	</c:when>
	<c:otherwise>
		<c:set var="persona" scope="page" value="personaMoral" />
		<c:set var="personaObj" scope="page" value="${mdmDatosEntrada.personaMoral}" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${empty personaObj.domicilioFiscal.clave}">
		<c:set var="addressClass" scope="page" value="hidden" />
		<div id="msgSinDomicilio">
			<label>La persona no cuenta con domicilio fiscal</label> <br>
		</div>
	</c:when>
	<c:otherwise>
		<c:set var="addressClass" scope="page" value="showElement" />
	</c:otherwise>
</c:choose>

<address id="addressParticular" class="domicilioFiscal ${addressClass}">
	<spring:message code="label.calle.num" />
	: <span id="nombreVialidadPrimaria">
		${personaObj.domicilioFiscal.vialidadPrimaria.nombre} </span> <span
		id="numExterior">
		${personaObj.domicilioFiscal.numExterior1} </span> <span
		id="numExteriorAlfa">
		${personaObj.domicilioFiscal.numExteriorAlf} </span>, <span
		id="numInterior">
		${personaObj.domicilioFiscal.numInterior} </span> <span
		id="numInteriorAlfa">
		${personaObj.domicilioFiscal.numInteriorAlf} </span><br>
	<spring:message code="label.colonia" />
	: <span id="nombreAsentamiento">
		${personaObj.domicilioFiscal.asentamiento.nombre} </span><br>
	<spring:message code="label.municipio" />
	: <span id="nombreMunicipio">
		${personaObj.domicilioFiscal.asentamiento.localidad.municipio.nombre}
	</span><br>
	<spring:message code="label.entidadFederativa" />
	: <span id="nombreEstado">
		${personaObj.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}
	</span><br> C.P. <span id="codigoPostal">
		${personaObj.domicilioFiscal.asentamiento.codigoPostal.codigoPostal}
	</span><br>
</address>

<form:hidden path="${persona}.domicilioFiscal.clave" />
<form:hidden path="${persona}.domicilioFiscal.calle" />
<form:hidden path="${persona}.domicilioFiscal.colonia" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion" />
<form:hidden path="${persona}.domicilioFiscal.vialidadPrimaria.nombre"
	id="vialidadPrimariaFiscal" />
<form:hidden path="${persona}.domicilioFiscal.numExterior1"
	id="numeroExteriorPrincipalFiscal" />
<form:hidden path="${persona}.domicilioFiscal.numExteriorAlf"
	id="numeroExteriorAlfanumericoFiscal" />
<form:hidden path="${persona}.domicilioFiscal.numInterior"
	id="numeroInteriorFiscal" />
<form:hidden path="${persona}.domicilioFiscal.numInteriorAlf"
	id="numeroInteriorAlfanumericoFiscal" />
<form:hidden path="${persona}.domicilioFiscal.numExterior2" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion"
	id="tipoAsentamientoFiscal" />
<form:hidden path="${persona}.domicilioFiscal.asentamiento.nombre" id="asentamientoFiscal" />
<form:hidden path="${persona}.domicilioFiscal.codigoPostal.codigoPostal"
	id="codigoPostalFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.nombre"
	id="localidadFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.municipio.nombre"
	id="municipioFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre"
	id="entidadFederativaFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPrimaria.tipoVialidad.descripcion" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPrimaria.nombre"
	id="vialidadReferenciaPrimariaFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaSecundaria.tipoVialidad.descripcion" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaSecundaria.nombre"
	id="vialidadReferenciaSecundariaFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPosterior.tipoVialidad.descripcion" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPosterior.nombre"
	id="vialidadReferenciaPosteriorFiscal" />
<form:hidden path="${persona}.domicilioFiscal.descripcion" id="descripcionFiscal" />
<form:hidden path="${persona}.domicilioFiscal.vialidadPrimaria.clave"
	id="vialidadPrimariaFiscal.clave" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPrimaria.clave"
	id="vialidadReferenciaPrimariaFiscal.clave" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaSecundaria.clave"
	id="vialidadReferenciaSecundariaFiscal.clave" />
<form:hidden
	path="${persona}.domicilioFiscal.vialidadReferenciaPosterior.clave"
	id="vialidadReferenciaPosteriorFiscal.clave" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave"
	id="claveEntidadFederativaFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.municipio.clave"
	id="claveMunicipioFiscal" />
<form:hidden
	path="${persona}.domicilioFiscal.asentamiento.localidad.clave"
	id="claveLocalidadFiscal" />
<form:hidden path="${persona}.domicilioFiscal.asentamiento.clave"
	id="claveAsentamientoFiscal" />
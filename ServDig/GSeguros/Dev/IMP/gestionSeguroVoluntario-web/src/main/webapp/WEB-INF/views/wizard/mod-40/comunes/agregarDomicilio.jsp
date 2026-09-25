<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/agregarDomicilio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<jsp:include page="encabezadoMod40.jsp">
				<jsp:param name="paso" value="1" />
			</jsp:include>
			<div class="titulo">
				<span>Otro domicilio</span>
				<hr class="red m-b-md">
			</div>
			
			<div id="domicilioRecortadoErrorContainer"></div>
			<div id="domicilioRecortadoContainer" style="min-height: 380px;"></div>
			
			<form:form id="agregarDomicilioForm" modelAttribute="domicilioAlterno"
				action="${contextPath}/wizard/continuacionVoluntaria/comunes/agregarDomicilioNueva" accept-charset="ISO-8859-1">
				<form:hidden path="calle" id="calle" />
				<form:hidden path="asentamiento.codigoPostal.codigoPostal" id="codigoPostal" />
				<form:hidden path="codigoPostal.codigoPostal" id="codigoPostalAux" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.nombre" id="entidadFederativa" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" id="entidadFederativaClave" />
				<form:hidden path="asentamiento.localidad.municipio.nombre" id="municipio" />
				<form:hidden path="asentamiento.localidad.municipio.clave" id="municipioClave" />
				<form:hidden path="asentamiento.localidad.nombre" id="localidad" />
				<form:hidden path="asentamiento.localidad.clave" id="localidadClave" />
				<form:hidden path="asentamiento.nombre" id="asentamiento" />
				<form:hidden path="asentamiento.clave" id="asentamientoClave" />
				<form:hidden path="vialidadPrimaria.nombre" id="vialidadPrimaria" />
				<form:hidden path="vialidadPrimaria.clave" id="vialidadPrimaria.clave" />

				<form:hidden path="numExteriorAlf" id="numeroExteriorAlfanumerico" />
				<form:hidden path="numInteriorAlf" id="numeroInteriorAlfanumerico" />
			</form:form>
			<form action="${contextPath}/wizard/continuacionVoluntaria/comunes/agregarDomicilio" id="previousStep" accept-charset="ISO-8859-1"></form>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-5">
		</div>
		<div class="controles col-sm-7">
			<div class="pull-right">
				<a id="regresar" class="btn btn-default">
					Regresar
				</a>
				<button id="cancelarTramite" class="btn btn-danger m-r-md">
					Cancelar
				</button>
				<a id="siguientePaso" class="btn btn-primary">
					Continuar
				</a>
			</div>
		</div>
	</div>
</div>

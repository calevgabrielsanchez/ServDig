<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<script type="text/javascript" src="/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/asignacionNSS/capturaDomicilio.js" htmlEscape="true" />"></script>

<div class="contenedor">
	
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="2" />
	</jsp:include>
	
	<div class="alert alert-info"><spring:message code="label.tramite.asignacionNSS.instrucciones.seAsignara"/></div>
	<div class="alert alert-danger" id="mensajeError" style="display:none"></div>
	<h4 style="margin-top:25px">Datos del domicilio</h4>
	<hr class="red" style="margin-bottom:25px"/>
	<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
	<p>Para continuar con el tr&aacute;mite captura los datos de tu domicilio: </p>
	<%--Div en el que se cargara el componente de domicilio recortado --%>
	<div class="row" style="margin-bottom:30px">
		<div class="col-sm-12" id="componenteDomicilio"></div>
	</div>
	
	<div>
		<form:form modelAttribute="domicilio" id="formularioDomicilioNSS">
			<form:hidden path="calle" id="calle" />
			<form:hidden path="codigoPostal.codigoPostal" id="codigoPostal" />
			<form:hidden path="asentamiento.codigoPostal.codigoPostal" id="asentamientoCodigoPostal" />
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
			
			<!-- Atributos de domicilio carretera -->
			<form:hidden path="domicilioCarretera.terminoGeneral.descripcion" id="domCar.ter.des"/>
			<form:hidden path="domicilioCarretera.terminoGeneral.clave" id="domCar.ter.cve"/>
			<form:hidden path="domicilioCarretera.derechoTransito.descripcion" id="domCar.der.des"/>
			<form:hidden path="domicilioCarretera.derechoTransito.clave" id="domCar.der.cve"/>
			<form:hidden path="domicilioCarretera.origen" id="domCar.or"/>
			<form:hidden path="domicilioCarretera.destino" id="domCar.des"/>
			<form:hidden path="domicilioCarretera.administracion.descripcion" id="domCar.adm.des"/>
			<form:hidden path="domicilioCarretera.administracion.clave" id="domCar.adm.cve"/>
			<form:hidden path="domicilioCarretera.cadenamiento"  id="domCar.cad"/>
			<form:hidden path="domicilioCarretera.codigoCarretera"  id="domCar.cod"/>
			
			<!-- Atrbutos de domicilio camino -->
			<form:hidden path="domicilioCamino.terminoGeneral.descripcion" id="domCam.ter.des"/>
			<form:hidden path="domicilioCamino.terminoGeneral.clave" id="domCam.ter.cve"/>
			<form:hidden path="domicilioCamino.margen.descripcion"  id="domCam.mar.des"/>
			<form:hidden path="domicilioCamino.margen.clave" id="domCam.mar.cve"/>
			<form:hidden path="domicilioCamino.origen" id="domCam.or"/>
			<form:hidden path="domicilioCamino.destino" id="domCam.des"/>
			<form:hidden path="domicilioCamino.cadenamiento" id="domCam.cad"/>
			
			<form:hidden path="numExterior1" id="numeroExteriorPrincipal" />
			<form:hidden path="numExteriorAlf" id="numeroExteriorAlfanumerico" />
			<form:hidden path="numInterior" id="numeroInterior" />
			<form:hidden path="numInteriorAlf" id="numeroInteriorAlfanumerico" />
			<form:hidden path="numExterior2" id="numeroExteriorSecundario" />
			<form:hidden path="vialidadReferenciaPrimaria.nombre" id="vialidadReferenciaPrimaria" />
			<form:hidden path="vialidadReferenciaPrimaria.clave" id="vialidadReferenciaPrimaria.clave" />
			<form:hidden path="vialidadReferenciaSecundaria.nombre" id="vialidadReferenciaSecundaria" />
			<form:hidden path="vialidadReferenciaSecundaria.clave" id="vialidadReferenciaSecundaria.clave" />
			<form:hidden path="vialidadReferenciaPosterior.nombre" id="vialidadReferenciaPosterior" />
			<form:hidden path="vialidadReferenciaPosterior.clave" id="vialidadReferenciaPosterior.clave" />
			<form:hidden path="descripcion" id="descripcion" />
			<form:hidden path="latitud" id="latitud" />
			<form:hidden path="longitud" id="longitud" />
		</form:form>
	</div>
	
	<%--Div para los botones de accion --%>
	<div class="row">
		<div class="col-sm-6 text-left" style="padding: 10px;">
			<span id="labelCamposObligatoriosGeneral" class="required">*</span> <spring:message code="label.camposObligatorios"/>
		</div>
		<div class="col-sm-6 text-right">
			<button type="button" id="cancelar" class="btn btn-danger salir" onclick="uid_call('imss.asegurados.asignacion_nss.captura_domicilio.btn_cancelar_tramite','clickout')"><span class="glyphicon glyphicon-trash"></span> <spring:message code="label.cancelar"/></button>
			<button type="button" id="continuar" class="btn btn-primary" onclick="uid_call('imss.asegurados.asignacion_nss.captura_domicilio.btn_continuar','clickin')"><span class="glyphicon glyphicon-ok"></span> <spring:message code="label.continuar"/></button>
		</div>
	</div>
	
	<div id="mensajes"></div>
	<form id="formSalir" action="salir"></form>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
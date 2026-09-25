<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/persona/ivro/cartaTerminos/cartaTerminos.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/persona/ivro/cartaTerminos/cartaTerminosCtrl.js" htmlEscape="true" />"></script>

<c:set var="tipoTramiteIVROPersonal" value="118" />
<c:set var="tipoTramiteIVRODomestico" value="121" />

		<div class="contenedor col-sm-12">
			<div class="contenido row">
				<div class="col-sm-12">
					<c:choose>
						<c:when test="${tipoTramite == tipoTramiteIVROPersonal}">
							<!-- Carta de Terminos y Condiciones para IVRO Individual -->
							<%@ include file="terminosSP.jsp"%>
						</c:when>
						<c:when test="${tipoTramite == tipoTramiteIVRODomestico}">
							<!-- Carta de Terminos y Condiciones para IVRO Domestico -->
							<%@ include file="terminosSD.jsp"%>
						</c:when>			
					</c:choose>
					<div class="m-t-lg">
						<input type="checkbox" id="chkCartaTC">
						Declaro que he le&iacute;do y acepto los t&eacute;rminos y condiciones.
					</div>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6"></div>
				<div class="controles col-sm-6 text-right">
					<button id="cancelarCartaTC" class="btn btn-default">Cancelar</button>
					<button id="aceptaCartaTC" class="btn btn-primary">Aceptar</button>
				</div>
			</div>
		</div>

<form:form action="${contextpath}/gestionSeguroVoluntario-web-ciudadano/wizard/individual/initRenovacion" modelAttribute="seguro" id="mdmDatosEntradaRenovacion"
  method="post">
  <form:hidden path="fechaInicio" maxlength="40" />
  <form:hidden path="fechaFin" maxlength="40" />
  <form:hidden path="extemporanea" maxlength="20" />
  <form:hidden path="enRenovacion" maxlength="20" />
  <form:hidden path="cveIdSeguroIvro" maxlength="20" />
  <form:hidden path="titular.nss" maxlength="20" />
  <form:hidden path="titular.idPersona" maxlength="20" />
  <form:hidden path="titular.curp" maxlength="20" />
  <form:hidden path="titular.rfc" maxlength="20" />
  <form:hidden path="tramite.tramiteId" maxlength="20" />
  <form:hidden path="modalidad.numModalidad" maxlength="20" />
  <form:hidden path="modalidad.idModalidad" maxlength="20" />
  <form:hidden path="compra.idCompra" maxlength="20" />
</form:form>
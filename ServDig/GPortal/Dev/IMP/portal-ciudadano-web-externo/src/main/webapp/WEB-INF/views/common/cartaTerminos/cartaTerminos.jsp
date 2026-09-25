<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<script type="text/javascript"
	 src="<spring:url value="/resources/js/delta/common/cartaTerminos.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	 src="<spring:url value="/resources/js/delta/common/cartaTerminosCtrl.js" htmlEscape="true" />"></script>

<c:set var="tipoTramiteIVROPersonal" value="<%=TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo()%>" />
<c:set var="tipoTramiteIVRODomestico" value="<%=TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()%>" />
<c:set var="tipoTramiteModifDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />
<c:set var="tipoTramiteSeguroFamiliar" value="<%=TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo()%>" />
<c:set var="tipoTramiteContinuacionVoluntaria" value="<%=TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo()%>" />

<c:choose>
	<c:when test="${tipoTramite == tipoTramiteSeguroFamiliar || tipoTramite == tipoTramiteContinuacionVoluntaria}">
		<div class="contenedor col-sm-12">
			<div class="contenido row">
				<div class="col-sm-12">
					<!-- Carta de Terminos y Condiciones Comun -->
					<%@ include file="terminosUnica.jsp" %>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6"></div>
				<div class="controles col-sm-6 text-right">
					<button id="cancelarCartaTC" class="btn btn-default">Cerrar</button>
				</div>
			</div>
		</div>
	</c:when>
	<c:when test="${tipoTramite != tipoTramiteSeguroFamiliar && tipoTramite != tipoTramiteContinuacionVoluntaria}">
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
						<c:when test="${tipoTramite == tipoTramiteModifDatosGrales}">
							<!-- Carta de Terminos y Condiciones para Actualizacion de CURP y/o Fecha Nacimiento -->
							<%@ include file="terminosModifCurp.jsp"%>
						</c:when>
			
						<c:otherwise>
							<!-- Carta de Terminos y Condiciones Comun -->
							<%@ include file="terminosUnica.jsp" %>
						</c:otherwise>
			
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
	</c:when>
</c:choose>

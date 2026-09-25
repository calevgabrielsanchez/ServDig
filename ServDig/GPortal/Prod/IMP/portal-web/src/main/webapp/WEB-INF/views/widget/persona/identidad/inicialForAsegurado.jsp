<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/identidadWidget.js" htmlEscape="true" />"></script>

<!-- Se inhabilita la opcion para generar el reporte de HLDA.
	En caso de que se quiera volver a poner, sólo basta con poner
	el if completo después del llamado asíncrono que se trae las opciones
	
	<c:if test="${persona.tipoPersona.idTipoPersona eq 1 && idPersonaTercero eq '0' }">
		var nssCifrado = AtributosPersonaCtrl.personaFirmada.nssCifrado;
		if (nssCifrado != null && nssCifrado != '' && nssCifrado != undefined) {
			var opcionHLDA = "<li><a id=\"reporteHLDA\">Generar Reporte Semanas Cotizadas</a></li>";
			$('ul#accionesWidgetIdentidad li:last').append(opcionHLDA);
		}
	</c:if>	
-->

<script type="text/javaScript">
	$(document).ready(function() {
		<c:if test="${empty readOnly}">
			$.post("/portal-web/utility/menu/opciones/8/${persona.tipoPersona.idTipoPersona}",null,function(data) {
				$("#accionesWidgetIdentidad").html(data);				
			});
		</c:if>
	});
</script>

<c:choose>
	<c:when test="${empty readOnly }">
		<c:set var="widgetURL" value="/portal-web/widget/persona/identidad/detalle/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}/${muestraDomicilio}/${muestraMedios}" />
	</c:when>
	<c:otherwise>
		<c:set var="widgetURL" value="/portal-web/widget/persona/identidad/detalle/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}/${muestraDomicilio}/${muestraMedios}/read-only" />
	</c:otherwise>
</c:choose>

<div class="contenedor" id="idPersonaIdentidadWidget"
	widget-name="personaIdentidadWidget"
	widget-url="${widgetURL}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}"
	is-readOnly="${readOnly }">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<c:choose>
				<c:when test="${idPersonaTercero eq '0'}">
					<span><spring:message
							code="label.widget.titulo.persona.portal" /></span>
				</c:when>
				<c:otherwise>
					<span><spring:message
							code="label.widget.titulo.persona.fisica" /></span>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.persona.identidad" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false"
			load-on-startup="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>

		<div class="estado"></div>

		<div class="pie">
			<div class="opciones">
				<c:if test="${empty readOnly }">
					<div class="btn-group">
						<a class="btn btn-default btn-sm" href="#"><spring:message
								code="label.menus.opciones" /> </a> <a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu" id="accionesWidgetIdentidad">
						</ul>
					</div>
				</c:if>
			</div>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>
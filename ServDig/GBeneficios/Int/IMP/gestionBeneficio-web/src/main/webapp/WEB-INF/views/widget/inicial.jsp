<!-- JSP Inicial del Widget de Beneficios. -->
<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/widget/beneficiosWidget.js" htmlEscape="true" />"></script>

<script type="text/javaScript">
	$(document).ready(function() {
		var _context 		= $('input#portalContext').val();
		var _portalPersona 	= $('input#cvePortalPersona').val();
		var rfc 			= ""+AtributosPersonaCtrl.personaPortal.rfc;
		if(rfc==""){			
			$('div#opcionesBeneficios').remove();
		}
		if (_context == _portalPersona) {
			$.post("/portal-web/utility/menu/opciones/2/5",null,function(data) {
				$("#accionesWidgetBeneficios").html(data);				
			});		
		} else {
			$('div#opcionesBeneficios').remove();	
		}
	});
</script>

<c:choose>
	<c:when test="${tipoRelacionBeneficio eq 1 }">
		<c:set var="widgetURL" value="/gestionBeneficio-web/widget/beneficios/persona/detalle/${persona.idPersona}" />
	</c:when>
	<c:otherwise>
		<c:set var="widgetURL" value="/gestionBeneficio-web/widget/beneficios/patsujoblig/detalle/${sujetoObligado.numeroRegistroPatronal}" />
	</c:otherwise>
</c:choose>

<div class="contenedor" id="beneficiosWidget"
	widget-name="beneficiosWidget" 
	widget-url="${widgetURL}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message code="label.widget.titulo.beneficios" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.beneficio" />
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
				<div class="btn-group" id="opcionesBeneficios">
					<a class="btn btn-default btn-sm" href="#"><spring:message
							code="label.menus.opciones" /> </a> <a class="btn btn-default btn-sm dropdown-toggle"
						data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu" id="accionesWidgetBeneficios">
					</ul>
				</div>
			</div>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>
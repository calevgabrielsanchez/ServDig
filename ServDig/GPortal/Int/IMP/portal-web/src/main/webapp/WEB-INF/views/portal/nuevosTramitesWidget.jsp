<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
$(document).ready(
		function() {
			//parametros que sirven para mostrar los tramites de derechohabientes de acuerdo al parentesco y estado
			var idParentesco = 0;
			var idEstado = 0;
			var patronPlataforma = false;
			var patronListaBlanca = false;

			//verificamos si existen los campos de parentesco y estado de ser asi los seteamos
			if($("#hdnIdParentesco").length) {
				idParentesco = $("#hdnIdParentesco").val();
			}
			if($("#hdnIdEstadoDerechohabiente").length) {
				idEstado = $("#hdnIdEstadoDerechohabiente").val();
			}
			
			if($("#patronPlataforma").length) {
				patronPlataforma = $("#patronPlataforma").val();
			}
			
			if($("#patronListaBlanca").length) {
				patronListaBlanca = $("#patronListaBlanca").val();
			}
			
			$.post("/portal-web/utility/menu/opciones/widgetNavegacion/tramite/4/${portalContext}/"+idParentesco+"/"+idEstado+"/"+patronPlataforma+"/"+patronListaBlanca,null,function(data) {
				$("#opcionesTramites").html(data);
			});
		}
	);
</script>
<div class="contenedor" id="menuNuevoTramite"
	widget-name="menuNuevoTramite">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message code="label.widget.titulo.nuevo.tramite" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.nuevo.tramite" />
			</p>
		</div>

		<div id="opcionesTramites" class="contenido" style="display: none;" already-loaded="false"
			load-on-startup="false">
			
		</div>

		<div class="estado"></div>

		<div class="pie">
			<div class="opciones"></div>

			<div class="controles">
				<a class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>


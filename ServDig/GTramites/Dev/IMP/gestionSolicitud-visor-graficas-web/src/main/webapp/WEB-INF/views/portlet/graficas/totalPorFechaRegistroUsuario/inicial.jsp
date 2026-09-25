<%@ include file="../../../general/taglibs.jsp"%>

<div class="contenedor" id="graficaTotalPorRegistroUsuarioContent"
	portlet-url="/gestionSolicitud-visor-graficas-web/portlet/grafica/movRegistroUsuario/totalTipoFecha/resumen">

	<div class="encabezado"></div>
	<div class="cuerpo">
		
		<div class="titulo">
			<div class="controles" style="display: inline-block; float: left;">
				<a class="widget-tool widget-resize"> <i
					class="icono-abrir"></i>
				</a>
			</div>
			
			<span class="title"><spring:message code="label.portlet.titulo.grafica.registroUsuario.totalPorMovFecha" />
			</span>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.portlet.descripcion.grafica.registroUsuario.totalPorTipoMovFecha" />
			</p>
		</div>
		
		<div class="filtros" style="display: none;">
			<fieldset>
				<table>
					<tr>
						<td>Fecha Inicio</td>
						<td><input type="text" id="fechaInicioRegistroUsuario"
							value="${fechaInicio }" style="width: auto; margin-right: 10px;"
							size="10" /></td>
						<td>Fecha Fin</td>
						<td><input type="text" id="fechaFinRegistroUsuario" value="${fechaFin }"
							style="width: auto; margin-right: 10px;" size="10" /></td>
						<td><button type="button" class="btn btn-primary"
								id="btnConsultaRegistroUsuarioTMovF">CONSULTAR</button></td>
					</tr>
				</table>
			</fieldset>
		</div>

		<div class="contenido" style="display: none;" load-on-startup="true" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>
	</div>


	<div class="estado"></div>

	<div class="pie">
		<div class="opciones"></div>
	</div>
</div>

<script type="text/javascript">
	crearDatePicker($('input#fechaInicioRegistroUsuario'));
	crearDatePicker($('input#fechaFinRegistroUsuario'));
	
	$('button#btnConsultaRegistroUsuarioTMovF').click(function(){
		$(this).parents('.cuerpo').find('a.widget-refresh', '.controles').trigger('click');
	});
</script>
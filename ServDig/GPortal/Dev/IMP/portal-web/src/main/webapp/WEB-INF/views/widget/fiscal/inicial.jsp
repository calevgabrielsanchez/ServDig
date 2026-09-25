<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>

<script>
	var indShowMsgPat = ${IND_SHOW_MSG_PAT};
	var msgPat = '${MSG_PAT}';
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/identidadFiscalWidget.js" htmlEscape="true" />"></script>
	
<script type="text/javascript" src="/gestionCobranza-web/static/resources/js/widget/comprobanteFiscalWidgetRFC.js"></script>

<div class="contenedor" id="idPersonaIdentidadFiscalWidget"
	widget-url="/portal-web/widget/general/fiscales/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	widget-name="personaIdentidadFiscalWidget"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">


	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize">
					<i class="icono-abrir"></i>
				</a>
			</div>

			<span><spring:message
					code="label.widget.titulo.identidad.fiscales" /> </span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
			La presente actualizaci&oacute;n representa la comunicaci&oacute;n al Instituto Mexicano del Seguro Social, 
			del Cambio de Domicilio en cumplimiento a la obligaci&oacute;n que establece a los patrones o sujetos 
			obligados, del Art&iacute;culo 15 fracci&oacute;n I de la Ley del Seguro Social y al Art&iacute;culo 16 del Reglamento 
			de la  Ley del Seguro Social en materia de afiliaci&oacute;n clasificaci&oacute;n de empresas recaudaci&oacute;n 
			y fiscalizaci&oacute;n.</p>
		
			<!-- <p>
				<spring:message code="label.widget.descripcion.identidad.fiscales" />
			</p>-->
			
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>


		<div class="estado"></div>

		<div class="pie">
			<div class="opciones">
				<div class="btn-group" id="opcionesDatosFiscales">
					<a class="btn btn-default btn-sm" href="#"><spring:message
							code="label.menus.opciones" /> </a> <a class="btn btn-default btn-sm dropdown-toggle"
						data-toggle="dropdown" href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu" id="accionesWidgetDatosFiscales">
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

<div id="dialogoMensajesGeneral">
	<p><span id="textoMensajeGeneral"></span></p>
</div>
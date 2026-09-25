<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/solicitudesPortlet.js" htmlEscape="true" />"></script>

<style>
.portlet .titulo span {
	cursor: auto;
}
.portlet .titulo span:hover {
    text-decoration: none;
}
</style>

<div id="homecontenido" class="contenedor">
	<div class="row">
		<div class="contenedor-widget col-xs-4">
			<c:if test="${empty solicitud.errorFormGeneral && not empty idPersonaWidget}">
				<div class="widget"
					widget-url="/portal-web/widget/persona/identidad/${idPersonaWidget}/${idTipoPersonaWidget }/true/true/read-only">
				</div>
			</c:if>
		</div>

		<div class="contenedor-portlet col-xs-8">

			<div class="portlets">

				<div class="portlet">
					<div class="contenedor">
						<div class="encabezado"></div>
						<div class="cuerpo">

							<div class="titulo">
								<span><spring:message
										code="label.portlet.titulo.detalle.solicitud" /> </span>
								
								<!-- <div class="controles">
									<a class="btn btn-sm widget-tool widget-resize"><i
										class="icon-resize-small"></i></a> <a
										class="btn btn-sm widget-tool widget-move"><i
										class="icon-move handle"></i></a>
								</div> -->

							</div>

							<div class="descripcion">
								<p>
									<spring:message
										code="label.portlet.descripcion.detalle.solicitud" />
								</p>
							</div>

							<div class="contenido">
								<c:choose>
									<c:when test="${empty solicitud.errorFormGeneral }">
										<jsp:include page="detalleSolicitudContenido.jsp" />
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger" id="divMsgErrores">
											${solicitud.errorFormGeneral }</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="estado"></div>

						<div class="pie" style="margin-bottom: 5px;">
							<div class="opciones">
								<form id="regresarForm"
									action="/gestionSolicitud-visor-web/portal/regresar"
									method="post">
									<button type="submit" class="btn btn-primary" id="btnRegresar">REGRESAR</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
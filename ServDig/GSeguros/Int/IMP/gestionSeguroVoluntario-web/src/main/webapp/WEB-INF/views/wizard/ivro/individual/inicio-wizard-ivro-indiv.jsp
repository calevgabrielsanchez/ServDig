<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/individual/tramite-ivro-individual.js" htmlEscape="true" />"></script>
	
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idOrigenINTERNET"
	value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<div class="contenedor col-sm-12">

	<div class="contenido row">
		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span><c:choose>
						<c:when test="${ esRenovacion && !esExtemporanea }">
					Renovaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio
				</c:when>
						<c:otherwise>
					Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio
				</c:otherwise>
					</c:choose></span>


			</div>

			<div class="descripcion">
				<p style="text-align:justify;">A trav&eacute;s de esta ventana usted podr&aacute;
					seleccionar la compra o renovaci&oacute;n de la
					incorporaci&oacute;n voluntaria al r&eacute;gimen obligatorio.</p>
				<p style="text-align:justify;">Al asegurarse en la Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio,
				tendr&aacute; cobertura para asistencia m&eacute;dico quir&uacute;rgica, farmac&eacute;utica y hospitalaria,
				y con derecho a las pensiones de invalidez y/o vida; por retiro o por vejez,
				en su caso, aplicar&aacute;n las prestaciones por riesgos de trabajo.
				Todas las prestaciones anteriores se otorgan en t&eacute;rminos de la Ley del Seguro Social.”</p>
			</div>

			<div class="opciones">
				<button class="btn btn-primary btn-block" id="btnInciaTramite">Iniciar
					Tr&aacute;mite</button>

				<button class="btn btn-default btn-block"
					id="btnInicioCancelarTramite">Cancelar</button>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3>Instrucciones :</h3>
			<ul>
				<li>
					<p>Para realizar la compra o renovaci&oacute;n de la
						incorporaci&oacute;n voluntaria al r&eacute;gimen obligatorio por
						favor, seleccionar el bot&oacute;n iniciar tr&aacute;mite y
						atender con un clic en el bot&oacute;n siguiente o cancelar para
						continuar o abandonar el proceso.</p>
				</li>
				<li>
					<p>
						<strong>Te recomendamos validar tu domicilio y medios de
							contacto asociados <c:choose>
								<c:when test="${ambienteId eq idOrigenINTERNET}">
				y en caso de querer actualizarlos, lo podr&aacute; hacer en la sección "Datos personales" dentro del mismo portal.
			</c:when>
								<c:otherwise>
			.
			</c:otherwise>
							</c:choose>
						</strong>
					</p>
				</li>

			</ul>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>

</div>
<form:form action="${contextpath}/wizard/individual/iniciarTramite"
	modelAttribute="persona" id="mdmDatosEntrada" method="post">
	<form:hidden path="idPersona" maxlength="20" />
	<form:hidden path="rfc" maxlength="20" />
	<form:hidden path="tipoPersona.idTipoPersona" maxlength="20" />
</form:form>

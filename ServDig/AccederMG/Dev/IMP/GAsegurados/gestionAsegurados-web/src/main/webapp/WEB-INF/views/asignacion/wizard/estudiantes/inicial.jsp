
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/asignacion/wizard/estudiantes/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor">

	<div class="contenido">

		<div class="introduccion">

			<div class="titulo">
				<span> Asignaci&oacute;n de NSS a Estudiantes </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					generar o recuperar el NSS (N&uacute;mero de Seguridad Social) a un
					conjunto de estudiantes de entidad educativa.</p>
				<p>Este tr&aacute;mite obtiene sus datos en las entidades
					externas del SAT y de RENAPO, para poder identificar si existe
					alguna actualizaci&oacute;n de sus datos y reflejarlos en la
					informaci&oacute;n que el Instituto tiene.</p>
			</div>

			<div class="opciones">

				<div style="max-width: 400px;">
					<button
						class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-button-text-only"
						role="button" aria-disabled="false" id="btnInciaTramite">
						<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
					</button>

					<button
						class="ui-button btn-block ui-widget ui-state-default ui-button-text-only"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones" style="width: 65% !important;">
			<h3>Instrucciones :</h3>
			<ul>
				<li>
					<p>Si Ud. realizo una modificación recientemente de su
						información en el SAT o RENAPO</p>
				</li>
				<li>
					<p>Si Ud. realizo una modificación recientemente de su
						información en el SAT o RENAPO</p>
				</li>
			</ul>

		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del ICA -->
<form action="/gestionAsegurados-web/wizard/tramite/asignacion/nss/estudiantes/iniciar/tramite"
	id="nssEstudiantesForm" method="post">
	<input type="hidden" id="nrpPatronNSS" name="nrpPatronNSS" value="${nrp}"/>
</form>
<!--  -->
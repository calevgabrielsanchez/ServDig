<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/dummy/inicial.js" htmlEscape="true" />"></script>
	
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">

	<div class="contenido">

		<div class="introduccion">

			<div class="titulo">
				<span><spring:message code="label.wizard.titulo.dummy" /></span>
			</div>

			<div class="descripcion">
				<p>
					<spring:message code="label.wizard.descripcion.dummy" />
				</p>
			</div>

			<div class="opciones">
				<button
					class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-button-text-only"
					role="button" aria-disabled="false" id="btnInciaTramite">
					<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
				</button>

				<button
					class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-button-text-only"
					role="button" aria-disabled="false" id="btnRetomarTramite">
					<span class="ui-button-text">Retomar Tr&aacute;mite</span>
				</button>
				<button
					class="btn-block ui-button ui-widget ui-state-default ui-button-text-only"
					role="button" aria-disabled="false" id="btnCancelarTramite">
					<span class="ui-button-text">Cancelar Tr&aacute;mite en
						Proceso</span>
				</button>

				<button
					class="ui-button btn-block ui-widget ui-state-default ui-button-text-only"
					role="button" aria-disabled="false" id="btnInicioCancelarTramite">
					<span class="ui-button-text">Cancelar</span>
				</button>
			</div>
		</div>

		<div class="instrucciones">
			
			<div class="alert alert-info">
				Usted ya cuenta con una solicitud para <strong>xxxxxx</strong> en proceso.
			</div>
	
			<h3>Instrucciones :</h3>
			<ul>
				<li>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In
						adipiscing nulla in lacus porttitor viverra. Donec tempus felis
						vitae dui consectetur, non commodo est placerat. Integer non eros
						est. Aliquam porttitor in orci sit amet tincidunt. Donec nec
						varius enim. Donec sit amet posuere velit. Phasellus commodo quam
						eu massa aliquam posuere. Quisque pharetra ipsum non urna porta,
						at dapibus mi pharetra. Sed auctor, arcu non consequat sodales,
						enim magna commodo sem, in ultricies ligula libero in dolor. Morbi
						gravida lacus id luctus hendrerit. Nam ac quam ullamcorper,
						imperdiet quam sed, vehicula risus. Sed ac risus a enim mollis
						pellentesque. In a metus suscipit, dapibus orci scelerisque,
						cursus dolor.</p>
				</li>
				<li>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In
						adipiscing nulla in lacus porttitor viverra. Donec tempus felis
						vitae dui consectetur, non commodo est placerat. Integer non eros
						est. Aliquam porttitor in orci sit amet tincidunt. Donec nec
						varius enim. Donec sit amet posuere velit. Phasellus commodo quam
						eu massa aliquam posuere. Quisque pharetra ipsum non urna porta,
						at dapibus mi pharetra. Sed auctor, arcu non consequat sodales,
						enim magna commodo sem, in ultricies ligula libero in dolor. Morbi
						gravida lacus id luctus hendrerit. Nam ac quam ullamcorper,
						imperdiet quam sed, vehicula risus. Sed ac risus a enim mollis
						pellentesque. In a metus suscipit, dapibus orci scelerisque,
						cursus dolor.</p>
				</li>
			</ul>
		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> &iquest;Desea cancelar
		la solicitud pendiente con folio: <strong>${noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
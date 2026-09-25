<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/asignacionNSS/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor">

	<div class="contenido">

		<div class="introduccion" style="height: 410px;">

			<div class="titulo">
				<span> Asignaci&oacute;n de N&uacute;mero de Seguridad Social
				</span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					obtener su N&uacute;mero de Seguridad Social (NSS).</p>
				<p>Este tr&aacute;mite obtiene sus datos en las entidades
					externas del SAT y de RENAPO, para poder identificar si existe
					alguna actualizaci&oacute;n de sus datos y reflejarlos en la
					informaci&oacute;n que el Instituto tiene.</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty SIN_CURP }">
						<button
							class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-button-text-only"
							role="button" aria-disabled="false" id="btnInciaTramite">
							<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
						</button>
					</c:if>
					<button
						class="ui-button btn-block ui-widget ui-state-default ui-button-text-only"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones"
			style="width: 65% !important; height: 410px;">
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
			<c:choose>
				<c:when test="${empty SIN_CURP }">
					<div class="well" style="background-color: white;">
						<ul>
							<li>
								<p>
									<spring:message code="mensaje.curp.registro" />
								</p>
							</li>
						</ul>
						<div style="text-align: center;">
							<span style="font-size: large; font-weight: bold;">${fisica.curp
								}</span>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">
						<spring:message code="mensaje.sin.curp.registro" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>

<c:choose>
	<c:when test="${not empty CON_FIEL && CON_FIEL}">
		<form
			action="/gestionAsegurados-web-externo/wizard/nss/captura/procesar"
			id="initCapturaNSSForm" method="post"></form>
	</c:when>
	<c:otherwise>
		<form action="/gestionAsegurados-web-externo/wizard/nss/captura"
			id="initCapturaNSSForm" method="get"></form>
	</c:otherwise>
</c:choose>
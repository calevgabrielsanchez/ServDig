<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/test/pruebaPatronal.js" htmlEscape="true" />"></script>

<div id="contenedor-testPatronal" class="contenedor">
	<div class="row">
		<div class="cell informacion">
			<h1>
				{
				<spring:message code="title.system" />
				}
			</h1>
			<h2>
				<spring:message code="label.bienvenido" />
			</h2>
			<p style="font-size: .9em;">
				<spring:message code="label.informacion.sistema" />
			</p>

			<hr>
			<p style="font-size: .9em !important;">
				<spring:message code="label.instruccion.java" />
			</p>

			<ul type="disc">
				<li><span style="font-size: 12px;"><a
						href="http://idse.imss.gob.mx/imss/descargas/javapolicy.exe"><span
							style="color: rgb(0, 102, 102);">Configuraci�n
								autom�tica de Internet&nbsp; Explorer. </span></a></span></li>
				<li><span style="font-size: 12px;"><a
						href="http://idse.imss.gob.mx/imss/descargas/Acerca_de_Configuracion_automatica_de_IE.pdf"><span
							style="color: rgb(0, 102, 102);">Manual de la
								Configuraci�n autom�tica de Internet&nbsp; Explorer.</span> </a></span></li>
			</ul>

		</div>

		<div class="cell detalleSujetoObligado">
			<div class="detalleSujetoObligado-caja">
				<h2>Test de Prueba Gestion Patronal</h2>
				<c:set var="contextpath" value="<%=request.getContextPath()%>" />
				<div>
					<form method="post" id="formDetalleSujetoObligado" action="#">
						<div id="usuario-contenedor">
							<label for="rfcpatron">
								<strong class="etiqueta">RFC</strong>
							</label>
							<input type="text" id="rfcpatron" maxlength="13" />

							<br>

							<div class="derecha">
								<input type="submit" class="mboton" value="Probar">
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>

</div>
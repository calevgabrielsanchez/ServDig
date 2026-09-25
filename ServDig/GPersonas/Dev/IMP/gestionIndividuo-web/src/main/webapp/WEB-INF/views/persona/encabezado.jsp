<%@ include file="taglibs.jsp" %>

<!-- ENCABEZADO. INICIA -->
	<div class="header_top ">
		<div class="top_version cell">
			<span><spring:message code="label.version" /></span>:
			<span><spring:message code="version" /></span>
		</div>
			
		<div class="top_nav cell">
			<ul>
				<li>
					<a href="http://www.imss.gob.mx" title="Portal IMSS">Visita el portal oficial del Instituto Mexicano del Seguro Social</a>
				</li>
			</ul>
		</div>
	</div>

	<!--Inicio logo-->
	<img height="83" src="<spring:url value="/static/resources/imagenes/banner.gif" htmlEscape="true" />" title="Portal IMSS" width="471" />
	<!--Termino logo-->
<!-- ENCABEZADO. FINAL -->
	
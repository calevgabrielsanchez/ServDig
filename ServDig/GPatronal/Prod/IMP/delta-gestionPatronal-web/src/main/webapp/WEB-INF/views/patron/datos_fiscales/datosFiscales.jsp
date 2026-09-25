<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/datosFiscales.js" htmlEscape="true" />"></script>

<div id="homecontenido" class="contenedor">

	<div class="row" style="height: 500px;">

		<div class="cell" style="padding: 20px;">

			<div class="row">
				<div id="datosFiscales" style="height: 100%">

					<!-- JSP principal del modulo de Representante Legal -->

					<c:set var="cveIdPatronSujetoObligado"
						value="${patronSujetoObligado.cveIdPatronSujetoObligado}"></c:set>
					
					<c:out value="${cveIdPatronSujetoObligado}"></c:out>
				</div>
			</div>
		</div>
	</div>
</div>
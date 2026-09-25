<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="sinAccionesLabel" scope="page"><spring:message code="sin.acciones.disponibles" /></c:set>

<script type="text/javascript">
	$(function() {
		if ($('ul#menuAccionesSujeto li a.menuIdentidad').length <= 0) {
			var _sinAcciones = '<li role="presentation">'
				+ '<a role="menuitem" tabindex="-1">'
				+ '<i class="icon-warning-sign" '
				+ 'style="margin-right: 6px;"></i>'
				+ '${sinAccionesLabel}</a></li>';
				
			$('ul#menuAccionesSujeto').append(_sinAcciones);
		}
	});
</script>


<c:choose>
	<c:when test="${idTipoSujeto eq 1}">
		<!-- ACCIONES PARA NSS 
		<li role="presentation">
			<a role="menuitem" tabindex="-1" id="datosPersonales" class="menuIdentidad">
				<spring:message code="accion.identidad.fisica.datos" />
			</a>
		</li>
		-->
	</c:when>
	<c:when test="${idTipoSujeto eq 2}">
		<!--  ACCIONES PARA NRP 
		<li role="presentation">
			<a role="menuitem" tabindex="-1" id="datosPersonales" class="menuIdentidad">
				<spring:message code="accion.identidad.moral.datos" />
			</a>
		</li>
		-->
	</c:when>
</c:choose>
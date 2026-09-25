<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/mediosContacto/cmpMedioContacto.js" htmlEscape="true" />"></script>

<div style="width:100%;">
	<div id="mcContenedor">
		<script>
		var mc ;
		$(function(){
				var modInterfaz = 3; 
				var tpPropietario = 1;
				var idPropietario = 25129175;
				var idSolicitud = 0;
				var idSujetoOblgiado = 1;
				
				mc = new MedioContacto("mcContenedor", modInterfaz, tpPropietario, idPropietario, idSolicitud, idSujetoOblgiado);
				mc.init();
			});
			
		function obtenerDatos(){
			return mc.obtenerListaMediosContacto();
		}
		</script>
		<div id="mcContenedor" class="contenedor" style="width: 100% !important;">
			
		</div>
		
	</div>
</div>
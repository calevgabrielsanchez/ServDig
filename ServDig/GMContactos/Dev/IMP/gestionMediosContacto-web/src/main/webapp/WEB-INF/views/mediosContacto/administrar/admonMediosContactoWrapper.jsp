<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript">
	
	var admonMediosParticulares = {
		init : function() {
			var wrapper = $('#admonMediosContactoWrapper')
			
			var url = wrapper.attr('widget-url');
			wrapper.load(url, function (){
				initAdmonMedios();
			});
		},
		
		refresh : function(idPersona) {
			var url = '/gestionMediosContacto-web/medios/particulares/administrar/' + idPersona;
			
			$('#admonMediosContactoWrapper').empty();
			
			$('#admonMediosContactoWrapper').load(url, function (){
				initAdmonMedios();
			});
		}		
	};
	
	// Se pone al final del script para que se ejecute sin problemas
	$(document).ready(function() {
		admonMediosParticulares.init();
	});

	
</script>

<c:choose>
	<c:when test="${not empty idPersona }">
		<c:choose>
			<c:when test="${not empty isRetomar }">
				<c:set var="WIDGET_URL"
					value="/gestionMediosContacto-web/medios/particulares/administrar/retomar/fisica/${idSolicitud}/${idPersona}" />
			</c:when>
			<c:otherwise>
				<c:set var="WIDGET_URL"
					value="/gestionMediosContacto-web/medios/particulares/administrar/fisica/${idPersona}" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:set var="WIDGET_URL"
			value="/gestionMediosContacto-web/medios/particulares/administrar/fisica" />
	</c:otherwise>
</c:choose>

<div id="admonMediosContactoWrapper" widget-url="${WIDGET_URL}">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript">
	
	var admonMediosFiscales = {
		init : function() {
			$('#admonMediosFiscalesWrapper').each(function() {
				var url = $(this).attr('widget-url');
				$(this).load(url, function (){
					initAdmonMediosFiscales();
				});
				
			});
		},
		
		refresh : function() {
			var url = '/gestionMediosContacto-web/medios/fiscales/administrar';
			
			$('#admonMediosFiscalesWrapper').each(function() {
				$(this).load(url, function (){
					initAdmonMediosFiscales();
				});
			});
		}		
	};
	
	// Se pone al final del script para que se ejecute sin problemas
	$(document).ready(function() {
		admonMediosFiscales.init();
	});

	
</script>

<div id="admonMediosFiscalesWrapper"
	<c:choose>
		<c:when test="${not empty isRetomar }">
			widget-url="/gestionMediosContacto-web/medios/fiscales/administrar/retomar/fisica/${idSolicitud}/${cveFisica}" 
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${not empty isMoral }">
					widget-url="/gestionMediosContacto-web/medios/fiscales/administrar/moral/${cveFisica}"
				</c:when>
				<c:otherwise>
					widget-url="/gestionMediosContacto-web/medios/fiscales/administrar/fisica/${cveFisica}"
				</c:otherwise>
			</c:choose>
		</c:otherwise>	
	</c:choose>
>

	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>

</div>
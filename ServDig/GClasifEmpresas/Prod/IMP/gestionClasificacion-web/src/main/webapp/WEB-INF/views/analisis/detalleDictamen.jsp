<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.js" htmlEscape="true" />"></script>
<script>
$.ajax({
    'type': 'POST',
    'url': '/consultas-web-mac-1.0.0-SNAPSHOT/views/consultaDictamen/consultaPatronDictamenDetalle.xhtml',
    'headers': {
    'cveIdPatronDictamen': '${cveIdPatronDictamen}'
    },
    'success': function(data) {
    	$("#detalleDictamenMac").html(data);
    }
});
</script>
<div id="detalleDictamenMac">
Cargando detalle...
</div>
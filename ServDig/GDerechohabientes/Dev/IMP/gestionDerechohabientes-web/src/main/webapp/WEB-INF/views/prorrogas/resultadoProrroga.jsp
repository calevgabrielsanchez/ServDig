<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>


<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/jquery.jqprint-0.3.js" htmlEscape="true" />"></script>
<script>
	$(document).ready(
		function() {
			loadFileUpload(${idTipoTramite},${idTramite});
			$("#grupoFamiliar").click(
					function(){
						grupoFamiliar();
					}
				);
		}	
		
	
	);
	
	function grupoFamiliar() {
		location.href = "" + context_path + "/inicio/grupoFamiliar";
	}

</script>



<div class="form-comment" align="center" >
	<form >
		<input type="button" id="grupoFamiliar" value="Grupo Familiar" class="mboton"/>
	</form>
</div>

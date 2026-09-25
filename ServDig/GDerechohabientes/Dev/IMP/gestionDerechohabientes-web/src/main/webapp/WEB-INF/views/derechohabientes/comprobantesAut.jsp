<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/general/impresionDocumentosImport.jsp" %>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>

<head>
</head>
<div class="form-comment">
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script>
$(document).ready(function() {
	var tipo = $('#tipo').val();
	var idTramite = $('#tramite').val();		
	
	showCompValRegistro(${idTramite},'REGISTRO DERECHOHABIENTE');			          			 			
	$("#frmComprobantes").submit();		
});
history.go(1);

</script>

<form:form commandName="comprobantes" id="frmComprobantes" name="frmComprobantes" action="${contextpath}/derechohabientes/pendientes" method="post">	
	<input type="hidden" id="tipo" name="tipo" value="<c:out value="${tipo}"/>"></input>
	<input type="hidden" id="tramite" name="tramite" value="<c:out value="${idTramite}"/>"></input>	
</form:form>
</div>
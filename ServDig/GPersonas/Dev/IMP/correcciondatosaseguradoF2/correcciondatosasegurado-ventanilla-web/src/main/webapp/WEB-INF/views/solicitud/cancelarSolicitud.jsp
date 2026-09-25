<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<%-- <c:set var="urlCancelarSolicitud" value="${contextpath}/wizard/correccionDatosAsegurado/cancelarSolicitud"></c:set> --%>
<c:set var="urlCancelarSolicitud" value="${contextpath}/atencionResponsable"></c:set>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/common.js" htmlEscape="true" />"></script>
<form:form method="POST" id="cancelarForm" action="${urlCancelarSolicitud}">
<button class="btn btn-default" id="cancelarSolicitudButton" >Cancelar</button>
	<div id="cancelarSolicitud" title="Cancelar solicitud" hidden="true">
		<p>
			<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"> </span>
<spring:message code="label.confirmacion.cancelar"/>
		</p>
		<br />
	</div>
</form:form>	

<script>
	
	objDialog = $('#cancelarSolicitud').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:300,
	        width:600,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {					
					$("#cancelarForm").submit();					
					},
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
	 $('#cancelarSolicitudButton').click(function(e) {
		 e.preventDefault();
		 
		objDialog.dialog('open');
	});
</script>
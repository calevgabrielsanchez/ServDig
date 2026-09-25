<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="rutaController" value="${contextpath}/iniciarTramite"/>
<form:form modelAttribute="fisica" id="iniciarTramiteCorreccionForm" action="${rutaController}"cssClass="form-horizontal" method="post" role="form">
	<div class="col-sm-8">
		 <h1>Bandeja de solicitudes</h1>
        <hr class="red bottom-buffer"/>
    </div>
	<div class="row">
		<div class="col-sm-8 text-right">
			<button type="submit" id="iniciar" class="btn btn-primary">Iniciar</button>
		</div>
	</div>
</form:form>
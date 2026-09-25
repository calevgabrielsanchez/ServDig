<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/comprobanteFiscal/contenido.js" htmlEscape="true" />"></script>

<style>
label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
} 

textarea {
	height: 100%;
}

input,textarea,.uneditable-input {
	width: auto;
	text-transform: uppercase;
}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<jsp:include page="../../common/descargaComprobantesCommon.jsp"></jsp:include>
		</div>
	</div>

	<div class="pie row">
		<div class="opciones col-sm-6">
			
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-secondary" id="cerrarWizard">CERRAR</button>
			</div>
		</div>
	</div>
</div>
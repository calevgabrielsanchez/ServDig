<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
$(document).ready(function() {
	$('#siguientePaso').click(function() {
		$.blockUI();
		var periodo = $('#anioPeriodo').val() + $('#mesPeriodo').val();
		$('#periodo').val(periodo);
		document.getElementById('busquedaComprobanteFiscalForm').submit();
	});
});
</script>

<style>
.contenedor .pie .opciones {
	float: left;
	width: 80%;
}

.contenedor .pie .controles {
	float: right;
	width: 20%;
}

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

.contenedor .pie .opciones {
    float: left;
    width: 60%;
}

.contenedor .pie .controles {
    float: right;
    width: 40%;
}

</style>

<div class="contenedor">
	<div class="contenido" style="width: 100%;">
		<div class="well" style="background-color: white;" >
			<form:form modelAttribute="pagoFiscal" id="busquedaComprobanteFiscalForm" method="post"
				action="${contextpath}/edoadeudo/obtener/comprobanteFiscal/porPeriodo">
				<jsp:include page="../common/inicioCommon.jsp"></jsp:include>
			</form:form>
		</div>
	</div>
	<br>
	<div class="pie">
		<div class="opciones"></div>
		<div class="controles">
			<a id="siguientePaso" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
		</div>
	</div>
</div>
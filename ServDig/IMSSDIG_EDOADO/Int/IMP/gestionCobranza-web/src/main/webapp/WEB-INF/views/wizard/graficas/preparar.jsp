<%@ include file="../../general/taglibs.jsp"%>

<script>
	$(function() {
		$('form#edoAdeudoForm').submit(function() {
			preDownload();
		});

		$('form#edoAdeudoForm').trigger('submit');
	});

	var fileDownloadCheckTimer;

	function preDownload() {
		var token = 'edoAdeudo${tipoReporte}_${nrp}_' + new Date().getTime();
		$('#token','form#edoAdeudoForm').val(token);

		fileDownloadCheckTimer = window.setInterval(function() {
			var cookieValue = $.cookie('edoAdeudo${tipoReporte}Cookie');

			if (cookieValue == token) {
				finishDownload();
			}
		}, 1000);
	}

	function finishDownload() {
		window.clearInterval(fileDownloadCheckTimer);
		$.removeCookie('edoAdeudo${tipoReporte}Cookie');
		$.unblockUI();
		parent.WizardImpresionReportesCobranzaCtrl.cerrar();
	}
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />

<c:choose>
	<c:when test="${tipoReporte eq 1}">
		<c:set var="url" value="${contextpath}/reportesCobranza/edoCuenta/trabajoAdeudo/tipoCobro" scope="page" />		
	</c:when>
	<c:when test="${tipoReporte eq 2}">
		<c:set var="url" value="${contextpath}/reportesCobranza/edoCuenta/trabajoAdeudo/tipoCobroRCV" scope="page" />		
	</c:when>
</c:choose>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<form id="edoAdeudoForm" action="${url}" method="post">
				<input type="hidden" name="nrp" value="${nrp}" />
				<input type="hidden" name="token" id="token" />
			</form>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6">
			<div class="pull-right"></div>
		</div>
	</div>
</div>

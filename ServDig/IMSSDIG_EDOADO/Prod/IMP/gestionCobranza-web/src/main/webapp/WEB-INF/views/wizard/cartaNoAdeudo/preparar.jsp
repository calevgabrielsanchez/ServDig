<%@ include file="../../general/taglibs.jsp"%>

<script>
	$(function() {
		$('form#carta32dForm').submit(function() {
			preDownload();
		});

		$('form#carta32dForm').trigger('submit');
	});

	var fileDownloadCheckTimer;

	function preDownload() {
		var token = '32D${idPersona}_' + new Date().getTime();
		$('#token').val(token);

		fileDownloadCheckTimer = window.setInterval(function() {
			var cookieValue = $.cookie('32DCookie');

			if (cookieValue == token) {
				finishDownload();
			}
		}, 1000);
	}

	function finishDownload() {
		window.clearInterval(fileDownloadCheckTimer);
		$.removeCookie('32DCookie');
		$.unblockUI();
		parent.WizardCartaNoAdeudoCtrl.cerrar();
	}
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<form id="carta32dForm" action="${contextpath }/wizard/cartaNoAdeudo/generar" method="post">
				<input type="hidden" name="idPersona" value="${idPersona }" />
				<input type="hidden" name="idTipoPersona" value="${idTipoPersona }" />
				<input type="hidden" name="rfc" value="${rfc }" />
				<input type="hidden" name="usuario" value="${usuario }" />
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

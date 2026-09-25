<%@ include file="../../general/taglibs.jsp" %>

<script>

	$(document).ready(function() {
		$('#cerrarWizard').click(function() {
			cerrarWizard();
		});
	});
	
	function cerrarWizard() {	
		parent.WizardCartaNoAdeudoCtrl.cerrar();
	}
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<span>${error}</span>
			</div>
		</div>
	</div>
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
			</div>
		</div>
	</div>
</div>

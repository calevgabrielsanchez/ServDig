<%@ include file="../../../general/taglibs.jsp" %>
<script type="text/JavaScript">

$(document).ready(function(){
	$("#consultarEscritos").on("click", function() {
		$.blockUI();
		location.href="${contextpath}/escrito/consultar";
	});
	
	$("#registrarEscritos").on("click", function() {
		$.blockUI();
		location.href="${contextpath}/escrito/wizard";
	});
});
	
</script>

<div class="row">
	<div class="col-sm-12">
		<h3>Escrito patronal de desacuerdo</h3>
		<div class="alert alert-info" style="margin-bottom: 0px">
			Seleccionar el tipo de operaci&oacute;n que deseas realizar.
		</div>
		<div class="row">
			<div class="col-sm-6" style="padding: 25px;">
				<h4>Consultar escrito de desacuerdo</h4>
				<hr class="red" style="margin-bottom: 15px">
				<p style="text-align: justify;">
					Mediante esta opci&oacute;n podr&aacute;s realizar la b&uacute;squeda
					de escritos de desacuerdo, mediante el folio de recepci&oacute;n, 
					N&uacute;mero de Registro Patronal (NRP)
				</p>
				<div style="text-align: center">
					<button class="btn btn-default" type="button" id="consultarEscritos">Consultar escrito de desacuerdo</button>
				</div>
			</div>
			<div class="col-sm-6" style="padding: 25px;">
				<h4>Registrar escrito de desacuerdo</h4>
				<hr class="red" style="margin-bottom: 15px">
				<p style="text-align: justify;">
					Mediante esta opci&oacute;n puedes realizar el registro del escrito de desacuerdo, para lo cual
					requieres el N&uacute;mero de Registro Patronal (NRP) y la informaci&oacute;n relacionada al escrito.
				</p>
				<div style="text-align: center">
					<button class="btn btn-primary" type="button" id="registrarEscritos">Registrar escrito de desacuerdo</button>
				</div>
			</div>
		</div>
	</div>
</div>
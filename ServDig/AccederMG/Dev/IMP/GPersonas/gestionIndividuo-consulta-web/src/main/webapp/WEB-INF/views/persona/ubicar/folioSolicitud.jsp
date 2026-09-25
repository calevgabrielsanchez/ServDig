<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script>
	$(function(){
		$('div#pie').hide();
		$('button#botonFolio').click(function(e){
			$('button#iniciarTramite').attr("disabled", false);
		     parent.dialogoBuscar.persona({folioRecibido : null});
			 parent.dialogoBuscar.persona('cerrar');
		});
		$('button#botonFolio').focus();
	});
</script>

<div id="busquedaFormContainerPF">
	<div class="col-sm-12">
		<div class="empty-state">
			<div class="">
				<div class="imagen">
					<i class="glyphicon glyphicon-ok-sign"></i>
				</div>
				<div class="alert alert-success">
					La persona fue registrada con &eacute;xito, 
					el n&uacute;mero de folio generado para esta solicitud es: 
					<strong>
						<c:out value="${folio}"></c:out>
					</strong>
				</div>
			</div>
		</div>
		<div class="text-right">
			<button type="button" id="botonFolio" class="btn btn-primary">ACEPTAR</button>
		</div>
	</div>
</div>
<%@ include file="../../layout/taglibs.jsp"%>

<style>
	.contenedor {
	    display: table;
	    height: auto;
	    width: 100%;
	}
	
	.contenedor .row {
	    border-bottom: thin solid #DDDDDD;
	    width: 100%;
	}
	
	.contenedor .row:last-child {
	    border-bottom: none;
	}
	
	.contenedor .cell {
		padding: 5px !important;
		display: table-cell;
	}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		
		$('div.detalle-cambio-cerrado').live('click', function() {
			/* Open this row */
			$(this).attr('class','detalle-cambio-abierto icon-minus');
			$('#divDetalleCambios', $(this).parent()).slideDown();		
		});
		
		$('div.detalle-cambio-abierto').live('click', function() {
			/* Close this row */
			$(this).attr('class','detalle-cambio-cerrado icon-plus');
			$('#divDetalleCambios', $(this).parent()).slideUp();		
		});
		
		$('#tblCambiosRENAPO').addClass('table table-striped table-bordered');
		$('#tblCambiosSAT').addClass('table table-striped table-bordered');
		$('#tblCambiosDatosComplementarios').addClass('table table-striped table-bordered');		
		$('div.detalle-cambio-cerrado').addClass('icon-plus');
	});
	
</script>

<div class="contenedor">
	<div class="contenido">
		<div class="introduccion" style="height: auto;">

			<div class="titulo">
				<span> Detalle Notificaci&oacute;n </span>
			</div>

			<div class="descripcion">
				<p>A continuaci&oacute;n se muestra el detalle de la 
					notificaci&oacute;n que eligi&oacute;
				</p>
				
							
			</div>

			<div class="opciones">
				
			</div>
		</div>

		<div class="instrucciones" style="width: 64% !important; height: auto; background-color: transparent;">
		
			<div class="well">
				<p>Tipo Tr&aacute;mite: <strong>${notificacion.tramite.tipoTramite.descripcion }</strong></p>
				<p style="margin-bottom: 0px;">Fecha Tr&aacute;mite: <strong>${notificacion.tramite.fechaPresentacionParse }</strong></p>
			</div>
			
			<h5>Detalle de los cambios:</h5>

			<div id="detalleCambios">
				${notificacion.detalleCambio }
			</div>

		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>
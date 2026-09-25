<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="fechaActual" value="<%=new java.util.Date()%>" />

<script>
	$(document).ready(function() {
		$('#tbRiesgosTrabajo').dataTable({
			'bFilter' : false,
			'bDestroy' : true,
			'bLengthChange' : false,
			'bAutoWidth' : false,
			'bPaginate' : true,
			'bInfo' : true,
			'bSort' : false,
			'sPaginationType' : 'bootstrap'
		});

	});
</script>
<style>
.ui-widget-overlay {
	position: fixed;
}

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.required {
	color: red;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}

.icono-tramite {
	font-size: 2em;
}

.icono-tramite a {
	color: #545454;
	text-decoration: none;
}

.icono-tramite a:hover {
	color: black;
}

.contenedor {
	padding-top: 45px;
}
</style>
<br>
<div class="col-sm-12">
	<table id="tbRiesgosTrabajo"
		class="table table-striped table-bordered table-condensed table-responsive"
		style="width: 100%" cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr>
				<th style="text-align: center;"><strong>Registro
						Patronal</strong></th>
				<th style="text-align: center;"><strong>Nombre</strong></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patrones}" var="sujeto">
				<tr>
					<td style="text-align: center;"><a
						href="javascript:busquedaRTTCtrl.buscarRiesgosPorRP('${sujeto[0]}')">${sujeto[0]}</a>
					</td>
					<td style="text-align: center;"><a
						href="javascript:busquedaRTTCtrl.buscarRiesgosPorRP('${sujeto[0]}')">${sujeto[1]}</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
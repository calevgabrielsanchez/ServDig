<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/consultaEscritoDesacuerdo.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="fechaActual" value="<%=new java.util.Date()%>" />

<script>

	$(document).ready(function() {
		$('#tbEscritoDesacuerdo').dataTable({
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



<c:if test="${tipoConsulta == 'findEscritoDesacuerdoRegPatron'}">
<!-- encabezado -->
<div class="row">
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			RP:
		</label> <br> <span id="">${registroPatronal}</span>	
	</div>
	<div class="col-sm-6"></div>
</div>
</c:if>


<!-- Consulta por periodo -->

<c:if test="${tipoConsulta == 'findEscritoDesacuerdoPeriodo'}">

<div class="row">
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			Fecha Inicio:
		</label> <br> <span id="">${fechaInicio}</span>	
	</div>
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			Fecha Fin:
		</label> <br> <span id="">${fechaFin}</span>	
	</div>

</div>

</c:if>

<!-- Consulta por registro patronal y periodo -->


<c:if test="${tipoConsulta == 'findEscritoDesacuerdoRegPatronPeriodo'}">

<div class="row">
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			RP:
		</label> <br> <span id="">${registroPatronal}</span>	
	</div>
	<div class="col-sm-6"></div>

</div>

<div class="row">
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			Fecha Inicio:
		</label> <br> <span id="">${fechaInicio}</span>	
	</div>
	<div class="col-sm-6">
		<label for="" class="control-label" style="text-align: center;">
			Fecha Fin:
		</label> <br> <span id="">${fechaFin}</span>	
	</div>
</div>

</c:if>


<div class="col-sm-12">
	<table id="tbEscritoDesacuerdo"
		class="table table-striped table-bordered table-condensed table-responsive"
		style="width: 100%" cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr>
				<th style="text-align: center;"><strong>Folio(s) Escrito Desacuerdo</strong></th>
				<th style="text-align: center;"><strong>Delegaci&oacute;n </strong></th>
				<th style="text-align: center;"><strong>SubDelegaci&oacute;n</strong></th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaEscrito}" var="escrito" varStatus="loop">
				<tr>
					<td style="text-align: center;"><a
						href="javascript:busquedaEscDesCtrl.buscarEscritoPorFolio('${escrito.folioRecepcion}');">${escrito.folioRecepcion}</a>
					</td>	
					<td style="text-align: center;">${escrito.patron.subdelegacion.delegacion.descripcion}</td>
					<td style="text-align: center;">${escrito.patron.subdelegacion.descripcion}</td>	
								
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="col-sm-12 text-right">
	<button onclick="generarExcelEscritoDesacuerdo()" class="btn btn-default">
		<span class="glyphicon glyphicon-download-alt"></span>Descargar
		Excel
	</button>
</div>

<c:if test="${tipoConsulta == 'findEscritoDesacuerdoPeriodo'}">


<div class="row">
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			Total de Registros Patronales:
		</label> <br> <span id="spanNRP"> ${fn:length(listaEscrito)}</span>
	</div>
	<div class="col-sm-6">
	</div>
	
	
</div>
</c:if>

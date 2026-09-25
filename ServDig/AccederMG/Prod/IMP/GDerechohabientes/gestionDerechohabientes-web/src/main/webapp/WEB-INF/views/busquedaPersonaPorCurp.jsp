<%@ include file="general/taglibs.jsp"%>
<jsp:include page="layout/staticResources.jsp"></jsp:include>

<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/busqueda/busquedaPersonaPorCurp.js" htmlEscape="true" />"></script>

<input type="hidden" id="curpBusqueda" value="${curp}"/>
<div id="errores" style="display: none">
	<div class="contenido" style="width: 100%;">
			<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<span id="mensajeError"></span>
			</div>
	</div>
</div>

<div id="resultados"> 
	<table id="personasFisicasFoundIMSSTable" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th>ID</th>
			<th>RFC</th>
			<th>CURP</th>
			<th>NSS</th>
			<th>Nombre(s)</th>
			<th>Primer Apellido</th>
			<th>Segundo Apellido</th>
			<th>Sexo</th>
			<th>Fecha de Nacimiento</th>
			<th>Año/Mes N.</th>
			<th>Entidad de Nacimento</th>
			<th>Calificaci&oacute;n</th>
			<th>Selecci&oacute;n</th>
		</tr>
	</thead>
	<tbody>

	</tbody>
</table>
</div>
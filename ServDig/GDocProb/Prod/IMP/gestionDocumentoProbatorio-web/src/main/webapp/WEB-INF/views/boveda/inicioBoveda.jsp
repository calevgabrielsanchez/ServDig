<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="${staticResourcesPath}/js/jquery/bootstrap-filestyle.min.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<style>
.group-span-filestyle .btn-secondary {
	color: #333;
	background-color: #fff;
	border-color: #ccc;
	border: 1px solid #ccc;
	padding: 6px 25px
}

.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 50%;
	top: 4px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}
</style>
<input type="hidden" id="idTipoTramite" value="${keyIdTipoTramite}"/>
<input type="hidden" id="tipoComponente" value="${keyTipoComponente}"/>
<input type="hidden" id="idTramiteBoveda" value="${idTramiteBoveda}"/>
<div class="row" id="adjuntarDoctos" style="display: none">
	<div class="col-sm-12">
		<h4>Documentos requeridos</h4>
		<hr class="red" style="margin-bottom: 20px">
		<div class="alert alert-danger" id="errorGuardado" style="display:none"></div>
		<div class="row">
		<div class="col-sm-12">
		<form class="form form-horizontal" id="adjuntarDoctoForm" method="post" action="/gestionDocumentoProbatorio-web/boveda/api" enctype="multipart/form-data">
			<div class="form-group">
					<label class="control-label col-sm-6" for="tipoDocumento">
						Documento *:
					</label>
				<div class="col-sm-6">
					<select class="form-control" name="tipoDocumento" id="tipoDocumento">
						<option value="">--Seleccionar archivo--</option>
						<option value="1">Credencial</option>
					</select>
				</div>
				
			</div>
			<div class="form-group">
					<label class="control-label col-sm-6" for="documento">
						Seleccionar archivo *:
					</label>
				<div class="col-sm-6">
					<input type="file" name="documento" id="documento">
					<div id="progressbar" style="display:none"><div class="progress-label">Cargando...</div></div>
					<span id="errorDoctosNecesarios" class="errorDocs" style="display: none">Es necesario adjuntar todos los archivos marcados como obligatorios.</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-right">
					<button class="btn btn-primary" type="button" id="adjuntarDocumento">Adjuntar documento</button>
				</div>
			</div>
		</form>
		</div>
		</div>
		
	</div>
</div>

<div class="row" id="mostrarDoctosResultantes" style="margin-bottom: 20px; display: none" >
<div class="col-sm-12">
	<h4>Documentos resultantes</h4>
	<hr class="red"  style="margin-bottom: 20px">
	<table class="table table-striped table-bordered" id="documentosResultantes">
		<thead>
			<tr>
				<th>
					Tipo documento
				</th>
				<th>
					Nombre documento
				</th>
				<th>
					Ver
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="3">No se encontro ning&uacute;n documento resultante</td>
			</tr>
		</tbody>
	
	</table>
</div>
</div>

<div class="row" id="mostrarDoctos" style="margin-bottom: 20px; display: none" >
<div class="col-sm-12">
	<h4>Documentos adjuntos</h4>
	<hr class="red"  style="margin-bottom: 20px">
	
	<table class="table table-striped table-bordered" id="documentosCapturados">
		<thead>
			<tr>
				<th>
					Tipo documento
				</th>
				<th>
					Nombre documento
				</th>
				<th>
					Acciones
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="3">No se ha adjuntado ning&uacute;n documento</td>
			</tr>
		</tbody>
	
	</table>
</div>
</div>

<form id="formularioMostrar" target="_blank" method="GET" action="${contextpath}/boveda/api">
	<input type="hidden" value="" id="bovedaDocId" name="bovedaDocId"/>
</form>
<script type="text/javascript" src="<spring:url value="/static/resources/js/boveda/inicioBoveda.js" htmlEscape="true" />"></script>

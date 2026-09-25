<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<style>
input,textarea,.uneditable-input {
	text-transform: uppercase;
}

.required {
	color: red;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

.filtros-busqueda .filtros .filtro {
    width: 37%;
}

.table th {
    font-size: 10px;
}
</style>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/busquedaNss/contenido.js" htmlEscape="true" />"></script>


<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">

	<div class="contenido" style="width: 95%;">
				<div class="alert alert-success">
					A continuaci&oacute;n seleccione el tipo de busqueda e introduzca la informaci&oacute;n de la persona a buscar
				</div>
				
				<div class="separadorseccion">
					<span>
						Datos de la persona
					</span>
				</div>
					
				<div id="personasWrapper" style="width: 100%; margin: 0 auto;">
				<form:form modelAttribute="asignacion" id="asignacion" cssClass="form-horizontal" method="post" rol="form">
				
						<div class="form-group">
							<label for="curp" class="col-sm-5 control-label">
							<span class="required">*</span>Seleccione el tipo b&uacute;squeda: </label>
							<div class="col-sm-7">
								<div class="btn-group" data-toggle="buttons-radio">
								    <button type="button" class="btn btn-primary active" onclick="setTipoBusqueda(1)" id="busquedaCurp">Por CURP</button>
								    <button type="button" class="btn btn-primary" onclick="setTipoBusqueda(2)" id="busquedaDatos">Por datos estad&iacute;sticos</button>
							    </div>
							</div>
						</div>
						
						<div class="form-group" id="camposCurp">
							<label for="curp" class="col-sm-5 control-label">
							<span class="required">*</span>CURP</label>
							<div class="col-sm-7">
								<form:input path="curp" cssStyle="width: 300px"  cssClass="form-control" maxlength="18" />
								<span id="curpError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div id="camposDatosBasicos" style="display:none;">
						<div class="form-group">
							<label for="nombre" class="col-sm-5 control-label">
							<span class="required">*</span>Nombre(s)</label>
							<div class="col-sm-7">
								<form:input path="nombre" cssStyle="width: 300px"cssClass="form-control"  maxlength="50" />
								<span id="nombreError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="primerApellido" class="col-sm-5 control-label">
							<span class="required">*</span>Primer Apellido</label>
							<div class="col-sm-7">
								<form:input path="primerApellido" cssStyle="width: 300px" cssClass="form-control" maxlength="50" />
								<span id="primerApellidoError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="segundoApellido" class="col-sm-5 control-label">Segundo Apellido</label>
							<div class="col-sm-7">
								<form:input path="segundoApellido" cssStyle="width: 300px" cssClass="form-control" maxlength="50" />
								<span id="segundoApellidoError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="sexo.idSexo" class="col-sm-5 control-label">
							<span class="required">*</span>Sexo</label>
							<div class="col-sm-7">
								<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="asignacion" 
								entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${asignacion.sexo.idSexo}" 
								mostrarSoloActivos="true" cssClassname="form-control"/>
								<span id="sexo.idSexoError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="fechaNacimiento" class="col-sm-5 control-label">
							<span class="required">*</span>Fecha de Nacimiento</label>
							<div class="col-sm-7">
								<form:input readonly="true" path="fechaNacimiento" style="width: 100px" cssClass="form-control" maxlength="10" />
								<span id="fechaNacimientoError" class="error hiddenElement"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label for="lugarNacimiento.clave" class="col-sm-5 control-label">
							<span class="required">*</span>Lugar de Nacimiento</label>
							<div class="col-sm-7">
								<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="asignacion" 
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${asignacion.lugarNacimiento.clave}" 
								mostrarSoloActivos="true" cssClassname="form-control"/>
								<span id="lugarNacimiento.claveError" class="error hiddenElement"></span>
							</div>
						</div>
					</div>
					<br>
					<div class="alert alert-danger" id="divError" style="display:none">
						<strong>Error: </strong><span id="mensajeError"></span>
					</div>
					<div style="text-align: right; float: right;">
						<input type="button" value="Buscar" id="buscar" class="btn btn-primary" />
						<input type="button" value="Limpiar" class="btn btn-primary" id="limpiar" />
					</div>
					
				</form:form>
					
					<br>
				<div id="listadoPersonas" style="display: none; width: 100%">
					<div class="separadorseccion">
						<span>
							NSS Encontrados
						</span>
					</div>
						<table id="nssFoundIMSSTable" style="width: 100%;"
							class="table table-striped table-bordered" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th>NSS</th>
									<th>RFC</th>
									<th>CURP</th>
									<th>Nombre(s)</th>
									<th>Primer Apellido</th>
									<th>Segundo Apellido</th>
									<th>Sexo</th>
									<th>Fecha de Nacimiento</th>
									<th>Entidad de Nacimento</th>
									<th>Selecci&oacute;n</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
							</table>
					</div>
				</div>
	</div>
</div>
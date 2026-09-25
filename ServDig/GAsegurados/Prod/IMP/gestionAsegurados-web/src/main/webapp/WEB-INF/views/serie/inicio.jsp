<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/serie/serie-inicio.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/serie/CreacionSerieCtrl.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor">
	<div class="row">
		<div class="col-xs-12">
			<form:form action="${contextpath}/serie/inicio"
				modelAttribute="asignacionSerie" id="consultarSerieForm"
				method="post" cssClass="form-horizontal">
				<h4 class="separadorseccion">&nbsp;Administración de
					Series&nbsp;</h4>
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
				<br />
				<div id="filtrosBusqueda">
					<div class="form-group">
						<form:label path="delegacion.clave"
							cssClass="col-xs-2 control-label">Delegaci&oacute;n</form:label>
						<div class="col-xs-4">
							<combo:creaCombo idHtml="delegacion.clave"
								idHtmlContenedor="consultarSerieForm"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion"
								idHtmlValor="${asignacionSerie.delegacion.clave }"
								mostrarSoloActivos="false" cssClassname="form-control" />
						</div>
						<form:label path="subdelegacion.clave"
							cssClass="col-xs-2 control-label">Subdelegaci&oacute;n</form:label>
						<div class="col-xs-4">
							<combo:creaCombo idHtml="subdelegacion.clave"
								idHtmlContenedor="consultarSerieForm"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion"
								entidadPadre="dicDelegacion.cveIdDelegacion"
								idHtmlPadre="delegacion.clave"
								idHtmlValor="${asignacionSerie.subdelegacion.clave }"
								idHtmlValorPadre="${asignacionSerie.delegacion.clave }"
								mostrarSoloActivos="false" cssClassname="form-control" />
						</div>
					</div>
					<div class="form-group">
						<form:label path="serie.anioRegistro"
							cssClass="col-xs-2 control-label">A&ntilde;o de Registro</form:label>
						<div class="col-xs-4">
							<form:select path="serie.anioRegistro" cssClass="form-control">
								<form:option value="-1" label="--Por favor seleccione--" />
								<form:options items="${listAnioRegistro}" />
							</form:select>
						</div>
						<form:label path="serie.tipoSerie.idTipoSerie"
							cssClass="col-xs-2 control-label">Tipo Serie</form:label>
						<div class="col-xs-4">
							<combo:creaCombo idHtml="serie.tipoSerie.idTipoSerie"
								idHtmlContenedor="consultarSerieForm"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSerie"
								idHtmlValor="${asignacionSerie.serie.tipoSerie.idTipoSerie }"
								mostrarSoloActivos="false" cssClassname="form-control" />
						</div>
					</div>
				</div>
				<div class="row m-t-md">
					<div class="col-xs-12 text-right">
						<button type="submit" id="consultar" class="btn btn-primary">Consultar</button>
						<button type="button" id="btnLimpiarFormulario"
							class="btn btn-default">Limpiar</button>
					</div>
				</div>
				
				<div class="row m-t-md">
					<div class="col-xs-12">
						<table style="width: 100%;" id="tableSeriesActivas"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>Num. Serie</th>
									<th>A&ntilde;o Registro</th>
									<th>Tipo de Serie</th>
									<th>Delegaci&oacute;n</th>
									<th>Subdelegaci&oacute;n</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listAsignacion}" var="asignacion"
									varStatus="index">
									<tr>
										<td>${asignacion.serie.idSerie}</td>
										<td>${asignacion.serie.numSerieFormatedo}</td>
										<td>${asignacion.serie.anioRegistroFormateado}</td>
										<td>${asignacion.serie.tipoSerie.descripcion}</td>
										<td>${asignacion.delegacion.descripcion}</td>
										<td>${asignacion.subdelegacion.descripcion}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<button type="button" style="float: right;" id="crearSerie"
							class="btn btn-primary">Crear Serie Nueva</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div id="creacionSerieDiv"></div>

<form action="${contextpath}/serie/inicio" method="get"
	id="refreshSeriesForm"></form>

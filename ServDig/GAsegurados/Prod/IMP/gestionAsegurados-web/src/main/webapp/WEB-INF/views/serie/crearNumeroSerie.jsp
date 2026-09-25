<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/serie/serie-crearNumeroSerie.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">

			<form:form modelAttribute="asignacionSerie" id="crearSerieForm"
				action="${contextpath}/serie/crearSerie/crear" method="post"
				cssClass="formNotBlock form-horizontal">
				
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
				<br>
				
				<div class="form-group">
					<form:label path="serie.tipoSerie.idTipoSerie" class="col-xs-3 control-label">
							<span class="required">*</span>Tipo de Serie</form:label>
					<div class="col-xs-9">
						<combo:creaCombo idHtml="serie.tipoSerie.idTipoSerie"
							idHtmlContenedor="crearSerieForm"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSerie"
							idHtmlValor="${asignacion.serie.tipoSerie.idTipoSerie}" 
							mostrarSoloActivos="true"
							cssClassname="form-control"/>
						<span id="serie.tipoSerie.idTipoSerieError"
							class="hiddenElement error"></span>
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="serie.anioRegistro" cssClass="col-xs-3 control-label">
							<span class="required">*</span>A&ntilde;o de Registro</form:label>
					<div class="col-xs-9">
						<form:select path="serie.anioRegistro" cssClass="form-control">
							<form:option value="-1" label="--Por favor seleccione--" />
							<form:options items="${listAnioRegistro}" />
						</form:select>
						<span id="serie.anioRegistroError" class="hiddenElement error"></span>
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="delegacion.clave" class="col-xs-3 control-label">Delegaci&oacute;n</form:label>
					<div class="col-xs-9">
						<combo:creaCombo idHtml="delegacion.clave"
							idHtmlContenedor="crearSerieForm"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion" 
							mostrarSoloActivos="true"
							cssClassname="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="subdelegacion.clave" class="col-xs-3 control-label">
						Subdelegaci&oacute;n
					</form:label>
					<div class="col-xs-9">
						<combo:creaCombo idHtml="subdelegacion.clave"
							idHtmlContenedor="crearSerieForm"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion"
							entidadPadre="dicDelegacion.cveIdDelegacion"
							idHtmlPadre="delegacion.clave" 
							mostrarSoloActivos="true"
							cssClassname="form-control"/>
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="serie.numSerie" cssClass="col-xs-3 control-label">
							<span class="required">*</span>N&uacute;mero de Serie</form:label>
					<div class="col-xs-9">
						<form:select path="serie.numSerie" cssClass="form-control">
							<form:option value="-1" label="--Por favor seleccione--" />
							<form:options items="${listNumeroSerie}" />
						</form:select>
						<span id="serie.numSerieError" class="hiddenElement error"></span>
					</div>
				</div>
			</form:form> 
			
			<div class="row">
				<div class="col-xs-12">
					<button type="button" id="crearSerie" class="btn btn-primary"
						style="float: right;">CREAR</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgConfirmCrearSerie" title="Crear Serie">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span> ¿Desea crear la
		serie con los datos ingresados?
	</p>
	<span id="errorNegocioLabel" class=" hiddenElement error"></span>
</div>


<div id="operacionExitosa" style="width: 500px;" align="center"
	title="Operaci&oacute;n Exitosa">
	<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-info"></span> <label id="msgExito"></label>
			</p>
		</div>
	</div>
</div>
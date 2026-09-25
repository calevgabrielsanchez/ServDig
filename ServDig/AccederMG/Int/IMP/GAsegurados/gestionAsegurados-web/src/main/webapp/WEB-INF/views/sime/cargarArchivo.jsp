<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	$(function() {
		$("input#fileData").filestyle({
			buttonText : 'EXAMINAR',
			icon : false,
			buttonName: 'btn-primary'
		});
	});
</script>

<div class="contenedor">
	<div class="alert alert-info">
		A continuaci&oacute;n debe capturar el registro patronal y elegir el
		archivo XML a cargar. El l&iacute;mite de registros por archivo es de
		<strong>50</strong>.
	</div>
	
	<form:form modelAttribute="uploadItem" id="uploadFileForm"
		action="${contextpath}/sime/cargarArchivo" method="post"
		enctype="multipart/form-data" cssClass="form-horizontal" role="form">
		
		<form:errors path="errorFormGeneral" cssClass="alert alert-danger"
			element="div" />
		
		<fieldset>
			<legend>
				Ubicaci&oacute;n de Fuente de Informaci&oacute;
			</legend>
			<div class="form-group">
				<label for="erpName" class="col-xs-4 control-label"><span
					class="error">*</span>Registro Patronal</label>
				<div class="col-xs-6">
					<form:input path="erpName" id="erpName"
						cssStyle="text-transform: uppercase;" maxlength="11"
						cssClass="form-control" />
					<form:errors path="erpName" cssClass="error"/>
				</div>
			</div>
			<div class="form-group">
				<label for="fileData" class="col-xs-4 control-label"> <span
					class="error">*</span>Ruta
				</label>
				<div class="col-xs-6">
					<input type="file" name="fileData" id="fileData"
						class="form-control" />
					<form:errors path="fileData" cssClass="error"/>
				</div>
			</div>
		</fieldset>
		<br>
		<div class="row">
			<div class="col-xs-6 col-xs-offset-6">
				<button id="cargarArchivo" type="submit" class="btn btn-primary"
					style="float: right;">CARGAR ARCHIVO</button>
			</div>
		</div>
		<br />
	</form:form>
</div>
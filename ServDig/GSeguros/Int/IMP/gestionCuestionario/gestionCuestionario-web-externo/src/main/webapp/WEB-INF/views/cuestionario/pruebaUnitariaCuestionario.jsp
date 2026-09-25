<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/cuestionario/CuestionarioCtrl.js" htmlEscape="true" />"></script>

<script>
	$(function() {
		var _cuestionario = null;
		
		$('button#generarCuestionarioBtn').click(function(e) {
			e.preventDefault();
			
			_cuestionario = $('div#cuestionarioTest').cuestionario({
                idCuestionario: $('select#idCuestionario').val(),
                formId : 'testCuestionarioForm',
                onSuccess: function() {
                	$('button#calificarCuestionario').prop('disabled', false);
                }
			});
		});

		$('button#calificarCuestionario').click(function(e) {
			e.preventDefault();
			_cuestionario.cuestionario('validar');
		});
	});
</script>
<div id="homecontenido" class="container">
	<form action="" id="cuetionarioTestForm">
		<div class="form-group">
			<label for="idCuestionario">Tipo Cuestionario</label>
			<combo:creaCombo idHtmlContenedor="cuetionarioTestForm"
				idHtml="idCuestionario" mostrarSoloActivos="false"
				entidad="mx.gob.imss.ctirss.delta.persistence.DicCuestionario"
				cssClassname="form-control" />
		</div>
		<button type="button" id="generarCuestionarioBtn"
			class="btn btn-primary">GENERAR CUESTIONARIO</button>
	</form>
	
	<fieldset style="margin-top: 20px;">
		<legend>CUESTIONARIO</legend>
		<form:form action="${contextPath}/cuestionario/test/calificar"
			modelAttribute="tramiteDummy" id="testCuestionarioForm"
			cssClass="form-horizontal">
			<div class="form-group">
				<label for="fisica.nombr" class="col-sm-2 control-label">NOMBRE:</label>
				<div class="col-sm-10">
					<form:input path="fisica.nombre" cssClass="form-control" />
				</div>
			</div>
		</form:form>
		
		<div id="cuestionarioTest" style="margin-top: 20px;"></div>
		
		<button id="calificarCuestionario" class="btn btn-primary" disabled="disabled">CALIFICAR</button>
	</fieldset>
</div>
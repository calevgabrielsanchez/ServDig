<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="/${appRootCuestionario}/static/resources/js/delta/cuestionario/CuestionarioCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/alta/cuestionario.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	-->
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row" style="min-height: 400px;">
		<div class="col-sm-12">
			<div id="cuestionarioContainer"></div>
			<div class="alert alert-info" style="text-align:center;">
				<form:form id="aceptarTerminosCondiciones">
					<label>
						<input id="chkTCCuestionario" name="aceptarTC" type="checkbox"/>
						<span>He le&iacute;do y acepto los <a id="linkTCCuestionario" href="#">T&eacute;rminos y condiciones</a></span>
					</label>
				</form:form>
			</div>
			<form:form id="nextStepForm" modelAttribute="respuestasCuestionario"
				action="${contextPath}/wizard/seguroDomestico/alta/validarCuestionarioyAgregarAlistaTrabajadores">
			</form:form>
			<form:form id="cancelarProcesoForm" action="${contextPath}/wizard/seguroDomestico/alta/listaTrabajadores">
			</form:form>
			
			<div style="display: none;">
				<%@ include file="../../comunes/terminosCondicionesCuestionario.jsp" %>
			</div>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarProceso" class="btn btn-default">Cancelar</button>
				<button id="siguientePaso" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</button>
			</div>
		</div>
	</div>
</div>

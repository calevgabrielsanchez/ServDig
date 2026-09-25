<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/individual/comunes/seleccionarTipoPago.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.ui-selectable li {
	    padding: 15px 25px;
	}
</style>
<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	//-->
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:if test="${ not esRenovacion and not esExtemporanea }">
			<jsp:include page="../pasosIndividual.jsp">
				<jsp:param name="paso" value="2" />
			</jsp:include>
			</c:if>
			<div class="titulo separadorseccion">
				<span>Tipo de pago del seguro</span>
			</div>
			<div class="alert alert-danger" style="display: none;" id="validacion">
				<span id="mensaje-validacion">Selecciona el tipo de pago.</span>
			</div>
			<p>
				Selecciona el tipo de pago que deseas:
			</p>
			<ol id="listTipoPago" class="m-b-lg">
				<li class="ui-state-default" value="0">
					<h3>Anual</h3>
					<p>
						El pago anual te permite asegurarte durante un a&ntilde;o, a partir del
						primer d&iacute;a del mes siguiente a aquel en que se realiz&oacute; la solicitud y pago de la
						incorporaci&oacute;n.
					</p>
				</li>
				<li class="ui-state-default" value="1">
					<h3>Bimestral</h3>
					<p>
						Esta es una facilidad que otorga el Instituto, para realizar el pago del aseguramiento de
						forma parcial, y tu periodo de aseguramiento ser&aacute; proporcional.
					</p>
					<p>
						El pago de la parcialidad integrar&aacute; la actualizaci&oacute;n correspondiente a la facilidad
						otorgada.
					</p>
				</li>
			</ol>
			<form:form id="nextStepForm"
				cssClass="form-horizontal"
				action="${contextPath}/wizard/individual/seleccionarTipoPago">
				<input id="inputTipoPago" name="tipoPago" type="hidden"/>
			</form:form>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<c:choose>
					<c:when test="${esVentanilla and tieneSeguros}">
						<c:set var="classButton" value="btn-default"></c:set>
					</c:when>
					<c:otherwise><c:set var="classButton" value="btn-danger"></c:set></c:otherwise>
				</c:choose>
				<button id="cancelarTramite" class="btn ${classButton}" 
					onclick="uid_call('imss.gestion.seguro.voluntario.domestico.comunes.seleccionarTipoPago.btn_cerrar','clickout');">
					<c:choose>
						<c:when test="${esVentanilla and tieneSeguros}">Regresar</c:when>
						<c:otherwise>Cerrar</c:otherwise>
					</c:choose>
				</button>
				<a id="siguientePaso" class="btn btn-primary"
					onclick="uid_call('imss.gestion.seguro.voluntario.domestico.comunes.seleccionarTipoPago.btn_siguiente','clickin');">
					<i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
			</div>
		</div>
	</div>
</div>
<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
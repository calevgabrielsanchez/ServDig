<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/acuerdo/candidatos.js" htmlEscape="true" />"></script>
<br>
<h4 align="center">
	<spring:message code="tramite.acuerdoPadres.titulo"/>
</h4>

<div class="form-comment">
	<%--incluimos la pantalla del detalle del asegurado --%>
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
	<%--Incluimos el listado de candidatos --%>
	<fieldset style="margin-bottom: 20px">
	<legend> <strong>Candidatos al tr&aacute;mite</strong></legend>
	<form>
		<c:if test="${not empty error}">
			<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
		</c:if>
		<c:if test="${not empty candidatos && empty error}">
		<table id="candidatos" style="width:100%">
			<thead>
				<tr>
					<th  style="width: 40px"><spring:message code="candidatos.seleccion"/></th>
					<th><spring:message code="label.nombre" /></th>
					<th><spring:message code="label.primerApe" /></th>
					<th><spring:message code="label.segundoApe" /></th>
					<th><spring:message code="label.fechaNac" /></th>
					<th><spring:message code="label.sexo" /></th>
					<th><spring:message code="label.curp" /></th>
					<th><spring:message code="label.parentesco" /></th>
					<th><spring:message code="label.estadoDer" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${candidatos}" var="candidato">
					<tr>
						<td>
							<input type="radio" style="width: 20px" id="candidato" name="candidato" value="${candidato.derechohabiente.idPersona}">
						</td>
						<td>${candidato.derechohabiente.nombre}</td>
						<td>${candidato.derechohabiente.primerApellido}</td>
						<td>${candidato.derechohabiente.segundoApellido}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${candidato.derechohabiente.fechaNacimiento}"/></td>
						<td>${candidato.derechohabiente.sexo.descripcion}</td>
						<td>${candidato.derechohabiente.curp}</td>
						<td>${candidato.parentesco.descripcion}</td>
						<td>${candidato.estadoDerechohabiente.descripcion}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
		<c:if test="${empty candidatos && empty error}">

		<div class="ui-widget" id="divMensajeDomicilio">
			<div class="ui-state-highlight ui-corner-all" style="padding: 0 .7em;">
			<p align="center"><span class="ui-icon ui-icon-info" style="margin-right: .3em;"></span>
				<b>No existe informaci&oacute;n para mostrar.</b>
			</p>
			</div>
		</div>
		</c:if>
	</form>
	</fieldset>
	<div align="center">
		<form>
			<table>
				<tr>
					<td align="center">
					<c:if test="${not empty candidatos}">
						<input id="aceptar" type="button" value="Aceptar" class="mboton"/>
					</c:if>
					<input id="regresar" type="button" value="Regresar" class="mboton"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<form id="integrante" action="#" method="post">
		<input type="hidden" id="derechohabiente.idPersona" name="derechohabiente.idPersona"/>
	</form>

</div>

<script type="text/javascript">
$(document).ready(function() {
	//ponemos el estilo del grid a nuestra tabla
	$('#candidatos').dataTable( {
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : true,
        "iDeferLoading" : 0
        });
});
</script>

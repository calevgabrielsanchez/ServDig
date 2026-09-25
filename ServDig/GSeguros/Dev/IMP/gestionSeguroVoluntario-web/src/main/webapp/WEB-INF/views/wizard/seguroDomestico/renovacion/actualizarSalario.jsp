<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/renovacion/actualizarSalario.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	tipoOperacion = '${tipoOperacion}';
	ventanilla = ${esVentanilla};
</script>

<div class="contenedor col-sm-12">
	<div class="contenedor row">
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}"/>
			<fmt:setLocale value="es_MX" scope="session"/>
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">&#215;</button>
					<span>${error}</span>
				</div>
			</c:if>
			<div class="titulo separadorseccion">
				<span>Datos del trabajador dom&eacute;stico</span>
			</div>
			<p>Ingresa el nuevo salario para el empleado <strong>${tramites[inputIndex].beneficiarios[0].nombre}</strong> con n&uacute;mero de seguridad social <strong>${tramites[inputIndex].beneficiarios[0].nss}</strong></p>
			<form:form id="nextStepForm"
				cssClass="form-horizontal"
				cssStyle="margin: 20px 0px"
				action="${contextPath}/wizard/seguroDomestico/comunes/cotizacionTrabajador">
				<div class="form-group">
					<label class="col-sm-3 control-label">
						<span class="required">*</span>
						<span>Sueldo mensual:</span>
					</label>
					<div class="col-sm-4">
						<input class="form-control numerico" name="sueldoDiarioTrabajador" type="text" maxlength="9"/>
					</div>
				</div>
				<input name="nssTrabajador" type="hidden" value="${tramites[inputIndex].beneficiarios[0].nss}"/>
				<input name="inputIndex" type="hidden" value="${inputIndex}"/>
			</form:form>
			<form:form id="cancelarForm" action="${contextPath}/wizard/seguroDomestico/renovacion/listaTrabajadores">
			</form:form>
	           <div>
	           <ul>
	           <li>
	           		Sueldo mensual. Deber&aacute;s indicar el salario que se remunera de forma mensual al trabajador
					dom&eacute;stico, con independencia de los periodos trabajados durante el mes.<br/>
					Este salario no debe ser inferior a un salario m&iacute;nimo mensual del Distrito Federal vigente en el momento de la
					incorporaci&oacute;n, ni deber&aacute; exceder el monto del salario m&aacute;ximo mensual.
	           </li>
	           <li>
	           		Salario m&iacute;nimo mensual es de
	           		<span><strong><fmt:formatNumber value="${datosCalculo.salarioMinimo * 30}" type="currency"/></strong></span>
	           </li>
	           <li>
	           		Salario m&aacute;ximo mensual es de
	           		<span><strong><fmt:formatNumber value="${datosCalculo.salarioMinimo * 30 * 25}" type="currency"/></strong></span>
	           </li>
	           </ul>
	           </div>
			<fmt:setLocale value="${defaultLocale}" scope="session"/>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarCotizacion" class="btn btn-default">Cancelar</button>
				<a id="actualizarycotizar" class="btn btn-primary">
					<i class="glyphicon glyphicon-step-forward"></i>
					Siguiente
				</a>
			</div>
		</div>
	</div>
</div>
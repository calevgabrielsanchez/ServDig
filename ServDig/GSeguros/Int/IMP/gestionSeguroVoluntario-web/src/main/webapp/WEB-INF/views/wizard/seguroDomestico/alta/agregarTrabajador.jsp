<%@page import="java.math.BigDecimal"%>
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/alta/agregarTrabajador.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	-->
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
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
			<p>Ingresa la información del trabajador</p>
			<form:form id="nextStepForm"
				cssClass="form-horizontal"
				cssStyle="margin: 20px 0px"
				action="${contextPath}/wizard/seguroDomestico/comunes/cotizacionTrabajador">
				<div class="form-group">
					<label class="col-sm-5 col-sm-offset-1 control-label">
						<span class="required">*</span>
						<span>N&uacute;mero de Seguridad Social (NSS):</span>
					</label>
					<div class="col-sm-4">
						<input class="form-control numericoSinPunto" name="nssTrabajador" type="text" maxlength="11" tabindex="1"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-sm-offset-2 control-label">
						<span class="required">*</span>
						<span>Sueldo mensual:</span>
					</label>
					<div class="col-sm-4">
						<input class="form-control numericovv" name="sueldoDiarioTrabajador" id="sueldoDiarioTrabajador" type="text" maxlength="9" tabindex="2"/>
					</div>
				</div>
				<input name="tipoOperacion" type="hidden" value="1"/>
			</form:form>
			<form:form id="cancelarForm" action="${contextPath}/wizard/seguroDomestico/alta/listaTrabajadores">
			</form:form>

			<div class="well p-sm m-b-sm m-t-lg">
				<ul>
					<li>
						Sueldo mensual. Deber&aacute;s indicar el salario que se remunera de forma mensual al trabajador
						dom&eacute;stico, con independencia de los periodos trabajados durante el mes.
						<br />
						Este salario no debe ser inferior a un salario m&iacute;nimo mensual del Distrito Federal vigente en el momento de
						la incorporaci&oacute;n, ni deber&aacute; exceder el monto del salario m&aacute;ximo mensual.
					</li>
					<li>
						Salario m&iacute;nimo mensual es de
						<span>
							<strong>
								<fmt:formatNumber value="${datosCalculo.salarioMinimo * 30}" type="currency" />
							</strong>
						</span>
					</li>
					<li>
						Salario m&aacute;ximo mensual es de
						<span>
							<strong>
								<fmt:formatNumber value="${salMax}" type="currency" />
							</strong>
						</span>
					</li>
				</ul>
			</div>
			<fmt:setLocale value="defaultLocale" scope="session"/>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarCotizacion" class="btn btn-default" tabindex="4">Cancelar</button>
				<a id="buscarCotizar" class="btn btn-primary" tabindex="3"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
			</div>
		</div>
	</div>
</div>

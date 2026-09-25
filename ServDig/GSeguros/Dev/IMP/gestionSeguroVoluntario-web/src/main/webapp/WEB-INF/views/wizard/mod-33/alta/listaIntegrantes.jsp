<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/alta/listaIntegrantes.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.total {
		font-size: 25px;
	}
	
	table.table {
		font-size: initial !important;
	}
	
	input[type="text"] {
	    font-size: inherit;
	}
</style>

<script type="text/javascript">
	<!--
	/* ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros}; */
	-->
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}" />

			<fmt:setLocale value="es_MX" scope="session" />

			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</c:if>

			<div class="titulo">
				<c:if test="${enRenovacion and not extemporanea}">
					<span>Paso 2 de 4: Ingresar datos de familiares</span>
				</c:if>

				<c:if test="${!enRenovacion or extemporanea}">
					<span>Paso 3 de 5: Ingresar datos de familiares</span>
				</c:if>
			</div>
			<div class="titulo">
				<span>Datos del n&uacute;cleo familiar</span>
				<hr class="red m-b-none">
			</div>

			<p>Ingresa los datos de los familiares que deseas incorporar en el Seguro de Salud para la Familia. Si no cuentas
				con el N&uacute;mero de Seguridad Social de alguno de ellos, puedes solicitarlo o localizarlo <a href="/gestionAsegurados-web-externo/home/asegurado" target="_blank">aqu&iacute;</a></p>

			<form class="form-horizontal m-b-xl" 
				role="form" id="agregarIntegranteForm"
				action="${contextPath}/wizard/seguroFamiliar/alta/agregarCotizarIntegrante"
				method="POST">
				<div class="form-group">
					<label class="col-sm-2 control-label p-r-none" for="nss">
						<span class="required">*</span>
						CURP
					</label>
					<div class="col-sm-4">
						<input type="text" name="curpFamiliar" 
							class="form-control alfanumericoEstricto" id="curpFamiliar" 
							maxlength="18" style="text-transform: uppercase;"/>
						<span id="curpFamiliarError" class="error"></span>
					</div>
					<label class="col-sm-2 control-label p-r-none" for="nss">
						<span class="required">*</span>
						NSS
					</label>
					<div class="col-sm-4">
						<input type="text" name="nssFamiliar" class="form-control numerico" id="nssFamiliar" maxlength="11"/>
						<span id="nssFamiliarError" class="error"></span>
					</div>
					<label class="col-sm-2 control-label p-r-none m-t-sm">
						<span class="required">*</span>
						Parentesco
					</label>
					<div class="col-sm-4 m-t-sm">
						<select name="idParentescoFamiliar" id="idParentescoFamiliar" class="form-control">
							<option value="-1">-- Selecciona --</option>
							<c:forEach items="${parentescos}" var="parentesco">
								<c:if test="${parentesco.id ne 0}">
									<option value="${parentesco.id}">${parentesco.descripcion}</option>
								</c:if>
							</c:forEach>
						</select>
						<span id="idParentescoFamiliarError" class="error"></span>
					</div>
				</div>

				<div class="form-group m-t-lg">
					<c:if test="${!tramiteSeguro.desdeExtranjero }">
						<div class="col-sm-7 m-b-sm">
							<div class="checkbox">
								<label>
									<input type="checkbox" id="soloSolicitanteChk"
									<c:if test="${enRenovacion && soloSolicitante}">
										checked="checked"
									</c:if> >
									Deseo incorporarme de forma individual
								</label>
							</div>
						</div>
					</c:if>
					<c:choose>
						<c:when test="${tramiteSeguro.desdeExtranjero}">
							<c:set var="colClass" value="col-sm-12 text-right"/>
						</c:when>
						<c:otherwise>
							<c:set var="colClass" value="col-sm-5 text-right"/>
						</c:otherwise>
					</c:choose>
					<div class="${colClass}">				
						<!-- <button type="button" class="btn btn-default" id="btnLimpiarIntegrante">LIMPIAR</button>-->
						<button type="button" class="btn btn-primary" id="btnAgregarIntegrante">Agregar</button>
					</div>
				</div>
			</form>

			<div class="table-responsive">
				<table id="tblIntegrantes" class="table table-striped table-bordered table-word-wrap-fixed">
					<thead>
						<tr>
							<th>CURP</th>
							<th>NSS</th>
							<th>Parentesco</th>
							<th>Pago anual por persona</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tramites}" var="tramite" varStatus="status">
							<tr>
								<td>${tramite.beneficiarios[0].curp}</td>
								<td>${tramite.beneficiarios[0].nss}</td>
								<td>
									<c:choose>
										<c:when test="${status.index eq 0}">
											TITULAR
										</c:when>
										<c:otherwise>
											${tramite.beneficiarios[0].parentesco.descripcion}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<fmt:formatNumber value="${tramite.cotizacion.cuotaTotal}" type="currency" />
								</td>
								<td class="text-center">										
									<c:if test="${status.index gt 0}">
										<button type=" button" class="btn btn-danger btn-sm eliminarIntegrante" idx="${status.index}">Eliminar</button>
									</c:if>
								</td>
							</tr>
							<c:set var="costoTotal" value="${costoTotal + tramite.cotizacion.cuotaTotal}"/>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th colspan="3" class="text-right" style="padding-right: 20px;">
								<strong class="total">Total</strong>
							</th>
							<th id="costoTotal" colspan="2">
								<strong class="total">
									<fmt:formatNumber value="${costoTotal}" type="currency" />
								</strong>
							</th>
						</tr>
					</tfoot>
				</table>
			</div>

			<form id="quitarIntegranteForm" action="${contextPath}/wizard/seguroFamiliar/alta/quitarIntegrante">
				<input id="inputIndex" name="inputIndex" type="hidden" />
			</form>
 
			<form id="nextStepForm" action="${contextPath}/wizard/seguroFamiliar/comunes/resumen">
				<input type="hidden" name="soloSolicitante" id="soloSolicitante"/>
			</form>

			<fmt:setLocale value="${defaultLocale}" scope="session" />

		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cerrar" class="btn btn-default">					
						Cancelar
				</button>
				<a id="siguientePaso" class="btn btn-primary">
					<i class="glyphicon glyphicon-step-forward"></i>
					Continuar
				</a>
			</div>
		</div>
	</div>
</div>

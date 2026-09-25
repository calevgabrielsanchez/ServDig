<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../general/taglibs.jsp"%>

<div id="seccionPeriodoPago" style="width: 500px" class="table_form">
	<div class="separadorseccion">
		<span>
			<spring:message code="titulo.periodo.pago.fiscal" />
		</span>
	</div>

	<table width="100%">
		<tr>
			<td width="30%">
				<span class="required">*</span>
				<span class="etiqueta">N&uacute;mero de Registro Patronal: </span>
			</td>
			<td width="70%">
				<form:input path="numeroRegistroPatronal" maxlength="11" />
			</td>
		</tr>
		<tr>
			<td>
				<span class="required">*</span><span class="etiqueta">RFC:</span>
			</td>
			<td><form:input path="rfc" maxlength="13" /></td>
		</tr>
		<tr>
			<td>
				<span class="required">*</span> <span class="etiqueta">Proporcione
					el per&iacute;odo del cual obtendr&aacute; sus comprobantes
					fiscales:</span>
			</td>
			<td>
				<select id="mesPeriodo">
					<c:forEach var="mesPeriodo" items="${listMesPeriodo}" varStatus="indice">
						<option value="${mesPeriodo.key}">${mesPeriodo.value}</option>
					</c:forEach>
				</select>
				<span> - </span>
				<select id="anioPeriodo">
					<c:forEach var="anioPeriodo" items="${listAnioPeriodo}" varStatus="indice">
						<option value="${anioPeriodo.key}">${anioPeriodo.value}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>

	<input type="hidden" id="periodo" name="periodo"/>
</div>




<%@ include file="../../general/taglibs.jsp"%>

			<fieldset class="fsInterno">
				<span id="errorNegocioLabel" class=" hiddenElement error"></span> 
				<span id="numReferenciadocRegistroError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.num.ref" /></label>
				<form:input id="numReferenciadocRegistro" path="numReferenciadocRegistro" cssStyle="width:50%" maxlength="20" readonly="true"/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="fechaRegistroError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.fecha" /></label>
				<input type="text" readonly="readonly" id="txtFechaRegistro" style="width:50%"
					value='<fmt:formatDate pattern="dd/MM/yyyy" value="${registroSindicato.fechaRegistro}"/>'/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="autoridadLaboralError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.autoridad" /></label>
				<form:input path="autoridadLaboral" cssStyle="width:50%" maxlength="100" readonly="true"/>
			</fieldset>
			
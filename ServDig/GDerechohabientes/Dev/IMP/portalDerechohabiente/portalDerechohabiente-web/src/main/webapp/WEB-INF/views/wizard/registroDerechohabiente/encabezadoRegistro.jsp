<%@ include file="../../general/taglibs.jsp"%>
<%--Si existe un error se mostrara en un div --%>
<c:if test="${not empty error}">
	<div class="alert alert-error">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<strong>Error: </strong>${error}
	</div>
</c:if>

<%--Si la solicitud se cre correctamente se muestra la informcion de la solicitud y el paso en el que va --%>
<c:if test="${not empty folioSolicitud}">
	<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}"/>
	
	<%--Esto sive para mostrar el paso en el que se encuentra --%>
	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5>
				<spring:message code="label.wizard.paso" arguments="1" />
			</h5> <span><spring:message code="label.wizard.registro.paso1" /></span>
		</li>
		<li class="${2<=param.paso?"completed":""}">
			<h5>
				<spring:message code="label.wizard.paso" arguments="2" />
			</h5> <span><spring:message code="label.wizard.registro.paso2" /></span>
		</li>
		<li class="${3<=param.paso?"completed":""}">
			<h5>
				<spring:message code="label.wizard.paso" arguments="3" />
			</h5> <span><spring:message code="label.wizard.registro.paso3" /></span>
		</li>
		<li><i class="glyphicon glyphicon-ok-circle"></i></li>
	</ul>
	
	<div class="alert alert-info">
		<c:choose>
			<c:when test="${!isRetomar}">
				<spring:message code="label.solicitudiniciada" arguments="${folioSolicitud}"/>
			</c:when>
			<c:otherwise>
				<spring:message code="label.solicitudRetomada" arguments="${folioSolicitud}"/>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
</c:if>
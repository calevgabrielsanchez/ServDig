<%@ include file="../../../general/taglibs.jsp"%>
	<h3>Registro de escrito de desacuerdo</h3>
<%--pasos del tramite --%>
	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="1"/></h5> 
			<span><spring:message code="label.tramite.desacuerdo.paso1"/></span>
		</li>
		<li class="${2<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="2"/></h5> 
			<span><spring:message code="label.tramite.desacuerdo.paso2"/></span>
		</li>
		<li class="${3<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="3"/></h5> 
			<span><spring:message code="label.tramite.desacuerdo.paso3"/></span>
		</li>
		<li class="${3<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>

	</ul>
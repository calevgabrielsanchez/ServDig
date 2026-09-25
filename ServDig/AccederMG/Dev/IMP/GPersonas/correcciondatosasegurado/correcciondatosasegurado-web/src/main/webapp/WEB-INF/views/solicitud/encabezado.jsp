<%@ include file="../general/taglibs.jsp"%>
	<ol class="breadcrumb">
	  <li><a url="http://www.imss.gob.mx"><i class="icon icon-home"></i></a></li>
	  <li><a url="http://www.imss.gob.mx/servicios-digitales">Tr&aacute;mites</a></li>
	  <li class="active"><spring:message code="label.solicitud.correccionDatos.curp.titulo" /></li>
	</ol>
	<%--Division del titulo --%>
	<h3><spring:message code="label.solicitud.correccionDatos.curp.titulo" /></h3>
	<hr class="red" style="margin-bottom: 30px;">
	<%--pasos del tramite --%>
	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}" >
			<h5><spring:message code="label.solicitud.paso" arguments="1"/></h5> 
			<span><spring:message code="label.solicitud.correccionDatos.paso1"/></span>
		</li>
		<li class="${2<=param.paso?"completed":""}" >
			<h5><spring:message code="label.solicitud.paso" arguments="2"/></h5> 
			<span><spring:message code="label.solicitud.correccionDatos.paso2"/></span>
		</li>
		<li class="${3<=param.paso?"completed":""}" >
			<h5><spring:message code="label.solicitud.paso" arguments="3"/></h5> 
			<span><spring:message code="label.solicitud.correccionDatos.paso3"/></span>
		</li>
		<li class="${4<=param.paso?"completed":""}" >
			<h5><spring:message code="label.solicitud.paso" arguments="4"/></h5> 
			<span><spring:message code="label.solicitud.correccionDatos.paso4"/></span>
		</li>		
			<li class="${5<=param.paso?"success":""}" style="${5<=param.paso?"width:180px!important":"display: none"}">
			<i class="glyphicon glyphicon-ok"></i>
		</li>
		
	</ul>
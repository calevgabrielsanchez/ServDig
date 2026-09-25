<%@ include file="../general/taglibs.jsp"%>
	<%--breadcrumb --%>
	<ol class="breadcrumb">
	  <li><a url="http://www.imss.gob.mx"><i class="icon icon-home"></i></a></li>
	  <li><a url="http://www.imss.gob.mx/servicios-digitales">Tr&aacute;mites</a></li>
	  <li class="active"><spring:message code="label.tramite.actualizacionCorreo.titulo" /></li>
	</ol>
	
	<div class="row"> 
		<div class="col-sm-7" style="margin-top:0px">
			<h3><spring:message code="label.tramite.actualizacionCorreo.titulo" /></h3>
		</div>

	</div>

	<%--pasos del tramite --%>
	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="1"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso1"/></span>
		</li>
		<li class="${2<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="2"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso3"/></span>
		</li>

	</ul>
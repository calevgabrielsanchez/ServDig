<%@ include file="../general/taglibs.jsp"%>
	<%--breadcrumb --%>
	<ol class="breadcrumb">
	  <li><a url="http://www.imss.gob.mx" class="item-breadcrumb"><i class="icon icon-home"></i></a></li>
	  <li><a url="http://www.imss.gob.mx/servicios-digitales" class="item-breadcrumb">Tr&aacute;mites</a></li>
	  <li class="active"><spring:message code="label.tamite.asignacionNSS.titulo" /></li>
	</ol>
	<div class="row"> 
				<div class="col-sm-6" style="margin-top:0px">
					<h3><spring:message code="label.tamite.asignacionNSS.titulo" /></h3>
				</div>
  				<div class="col-sm-6">
  					<c:if test="${param.paso!=1}">
  					<div class="pull-right" style="border: 1px solid #ccc; padding: 10px">
						<span style="margin-right: 10px; float: left">
  							<strong>Bienvenido: </strong><br>
							${tramiteAseguradoSession.fisica.curp}
							<br>
							${tramiteAseguradoSession.fisica.nombreCompleto}
						</span>
	            		<button style="float: right" class="btn btn-link salir" id="btn-salir"  onclick="uid_call('imss.asegurados.asignacion_nss.captura_domicilio.btn_salir','clickout')">Salir</button>
					</div>
					</c:if>
 			 	</div>
	</div>
	<%--pasos del tramite --%>
	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="1"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso1"/></span>
		</li>
		<c:if test="${NSS_RECUPERADO}">
		<li class="${2<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="2"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso3"/></span>
		</li>
		<li class="${2<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
		</c:if>
		<c:if test="${!NSS_RECUPERADO}">
		<li class="${2<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="2"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso2"/></span>
		</li>
		<li class="${3<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="3"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso3"/></span>
		</li>
		<li class="${3<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
		</c:if>
	</ul>
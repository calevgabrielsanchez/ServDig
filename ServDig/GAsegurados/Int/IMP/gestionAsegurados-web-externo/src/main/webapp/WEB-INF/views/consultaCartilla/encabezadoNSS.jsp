<%@ include file="../general/taglibs.jsp"%>
	<%--breadcrumb --%>
<!--	<ol class="breadcrumb">
	  <li><a url="http://www.imss.gob.mx"><i class="icon icon-home"></i></a></li>
	  <li><a url="http://www.imss.gob.mx/servicios-digitales">Tr&aacute;mites</a></li>
	  <li class="active"><spring:message code="label.tramite.consultacartilla.titulo" /></li>
	</ol>
-->
	<%--Division del titulo --%>
	<div class="row"> 
				<div class="col-sm-7" style="margin-top:0px">
					<h3><spring:message code="label.tramite.consultacartilla.titulo" /></h3>
				</div>
  				<div class="col-sm-5">
  					<c:if test="${param.paso!=1}">
  					<div class="pull-right" style="border: 1px solid #ccc; padding: 10px">
						<span style="margin-right: 10px; float: left">
  							<strong>Bienvenido: </strong><br>
							${tramiteAsegurado.fisica.curp}
							<br>
							${tramiteAsegurado.fisica.nombre} ${tramiteAsegurado.fisica.primerApellido} ${tramiteAsegurado.fisica.segundoApellido}
						</span>
<!--	            		<button style="float: right" class="btn btn-link salir" id="btn-salir"  onclick="uid_call('imss.asegurados.asignacion_nss.captura_domicilio.btn_salir','clickout')">Salir</button> -->
					</div>
					</c:if>
 			 	</div>
	</div>
	<%--pasos del tramite --%>
<!--	<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="1"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso1"/></span>
		</li>
		<li class="${2<=param.paso?"completed":""}">
			<h5><spring:message code="label.tramite.paso" arguments="2"/></h5> 
			<span><spring:message code="label.tramite.asignacionNSS.paso3"/></span>
		</li>
		<li class="${2<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
	</ul>
-->
<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/checkBackDomicilios.js" htmlEscape="true"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/domicilios.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/procesaErrores.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="urlDomicilio"
	value="${contextpath}/wizard/correccionDatosAsegurado/capturarDomicilio"></c:set>
<c:set var="paginaAnterior" value="${contextpath}/wizard/correccionDatosAsegurado/obtenerInformacionRenapo"></c:set>
<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="3" />
	</jsp:include>

	<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
	<%--Div en el que se cargara el componente de domicilio recortado --%>
 	<div class="row">
		<div class="col-sm-12" id="componenteDomicilio"></div>
	</div>
	 
	<div id="domicilioCorto">
		<form:form action="${urlDomicilio}" method="POST"
		modelAttribute="domicilioAclaracion" id="formCodigoPostal" role="form" accept-charset="ISO-8859-1">
			<form:hidden path="calle" id="calle" />
			<form:hidden path="codigoPostal.codigoPostal" id="codigoPostal" />
			<form:hidden path="asentamiento.codigoPostal.codigoPostal" id="asentamientoCodigoPostal" />
			<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.nombre" id="estado" />
			<form:hidden path="asentamiento.nombre" id="asentamientoClave" />
			<form:hidden path="asentamiento.clave" id="asentamientoClaveCatalogo" />
			<form:hidden path="asentamiento.localidad.municipio.nombre" id="municipio" />
			<form:hidden path="numExteriorAlf" id="numeroExterior" />
			<form:hidden path="numInteriorAlf" id="numeroInterior" />
			<form:hidden path="subdelegacion.descripcion" id="subdelegacion" />
			<form:hidden path="subdelegacion.clave" id="subdelegacionClave" />
			<form:hidden path="subdelegacion.id" id="subdelegacionId" />
			
			<div class="row">
				<div class="col-md-6 "></div>

				<div class="col-md-3">
					<h5>
					<form:label path="subdelegacion.clave" cssClass="control-label">
						<spring:message code="label.subdelegacion" />
					</form:label><span class="required" id="subReq">*</span>
					</h5>
				</div>
				<div class="col-md-3 ">
					<select id="subdelegacion.clave"
						name="subdelegacion.id" class="form-control"
						style="display: inline-block;">
						<option value='-1'>--Por favor seleccione--</option>
					</select> <img id="cveSubdelegacionImgCargando" class="cargando-combo"
						style="display: none;"
						src="${staticResourcesPath}/imagenes/loading.gif" alt="Cargando" />
					<form:errors path="subdelegacion.id"
						cssClass="error" campoRelacionado="subdelegacion.clave" />
						<span id="subdelegacion.idError" class="error hiddenElement"></span>
				</div>
			</div>
			<h3>
				<spring:message code="label.motivo.aclaracion" /><span class="required" id="subReq">*</span>
			</h3>
			<hr class="red" style="margin-bottom: 20px;" />
			<br/>

			<div class="col-md-12">
				<div class="row">
					<form:errors path="motivoAclaracionVO.otro" cssClass="error" />
					<span id="motivoAclaracionVO.otroError" class="error hiddenElement"></span>					
				</div>
				<div class="row">
					<div class="col-md-3"style="font-size:15px">
						<div class="row">
							<label class="control-label"> 
								<spring:message code="label.motivo.aclaracion.imss" />
							</label>
						</div>
						<div class="radio">
							<form:checkboxes items="${motivosAclaracionIMSS}" path="motivoAclaracionVO.motivosAclaracionIMSS" delimiter="<br/>"/>
						</div>
						<form:errors path="motivoAclaracionVO.motivosAclaracionIMSS" cssClass="error" />
					</div>

					<div class="col-md-4"style="font-size:15px">
						<div class="row">
							<label class="control-label"> 
								<spring:message code="label.motivo.aclaracion.infonavit" />
							</label>
						</div>
						<div class="radio">
							<form:checkboxes items="${motivosAclaracionInfonavit}" path="motivoAclaracionVO.motivosAclaracionInfonavit" delimiter="<br/>"/>
						</div>
						<form:errors path="motivoAclaracionVO.motivosAclaracionInfonavit" cssClass="error" />
						<label class="control-label"> 
							<spring:message code="label.motivo.aclaracion.infonavit.numero.credito" />
						</label>
						<form:input type="text" id="motivoAclaracionVO.creditoDescontado" path="motivoAclaracionVO.creditoDescontado" disabled="true" cssStyle="text-transform: uppercase;"/>
						<br/>
						<span id="motivoAclaracionVO.creditoDescontadoError" class="error hiddenElement"></span>					
						<form:errors path="motivoAclaracionVO.creditoDescontado" cssClass="error" />
					</div>
					
					<div class="col-md-5"style="font-size:15px">
						<div class="row">
							<label class="control-label"> 
								<spring:message code="label.motivo.aclaracion.afore" />
							</label>
						</div>
						<div class="radio">
							<form:checkboxes items="${motivosAclaracionAfore}" path="motivoAclaracionVO.motivosAclaracionAfore" delimiter="<br/>"/>
						</div>
						<form:errors path="motivoAclaracionVO.motivosAclaracionAfore" cssClass="error" />
						<br/>
						<div class="radio">
							<form:checkbox path="motivoAclaracionVO.otro" value="otro" id="checkOtro"/> <label for="checkOtro">OTRO</label>
						</div>
						<label class="control-label"> 
							<spring:message code="label.registrar.motivo.aclaracion.especificacion" />
						</label>						
						<div class="col-md-12">
							<form:textarea id="motivoAclaracionVO.especificacion" path="motivoAclaracionVO.especificacion" disabled="true" style="width: 454px; height: 76px;" maxlength="256" cssStyle="text-transform: uppercase;" />														
							<span id="motivoAclaracionVO.especificacionError" class="error hiddenElement"></span>
						</div>						
					</div>					
				</div>
			</div>
		</form:form>
	</div>
	<br/>
	<br/>
	
	<%--Div para los botones de accion --%>
	<div class="row">
		<div class="col-sm-6 text-left" style="padding: 10px;">
			<span>*</span>
			<spring:message code="label.camposObligatorios" />
		</div>

		<div class="row col-md-12">
			<div class="pull-right">
				<button type="submit" id="continuar" class="btn btn-primary"
					id="continuarCarpturarHistoriaLaboral">
					<spring:message code="label.continuar" />
				</button>
			</div>
			<div class="pull-right" style="margin-right: 10px;">
				<button type="button" id="regresar" class="btn btn-default">
					<spring:message code="label.regresar" />
				</button>
			</div>
			<div class="pull-right" style="margin-right: 10px;">
				<jsp:include page="cancelarSolicitud.jsp" />
			</div>
		</div>
	</div>
	<div id="mensajes"></div>
	<form id="formSalir" action="cancelarSolicitud" method="POST"></form>
</div>
	<script>
window.onload = function() { 
  var txts = document.getElementsByTagName('TEXTAREA'); 

  for(var i = 0, l = txts.length; i < l; i++) {
    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) { 
      var func = function() { 
        var len = parseInt(this.getAttribute("maxlength"), 10); 
        if(this.value.length > len) { 
          this.value = this.value.substr(0, len); 
          return false; 
        } 
      }
      txts[i].onkeyup = func;
      txts[i].onblur = func;
    } 
  };
}
	</script>
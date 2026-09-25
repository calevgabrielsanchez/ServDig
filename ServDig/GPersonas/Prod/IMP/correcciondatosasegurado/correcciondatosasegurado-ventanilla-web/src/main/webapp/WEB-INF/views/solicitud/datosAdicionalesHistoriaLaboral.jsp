<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="urlDatosAdicionalesHistoriaLaboral" value="${contextpath}/wizard/correccionDatosAsegurado/validar/datosAdicionalesHistoriaLaboral"></c:set>
<c:set var="paginaAnterior" value="${contextpath}/wizard/correccionDatosAsegurado/documentosProbatorios/datosHistoriaLaboral"></c:set>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/common.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/popovercda.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	$(document).ready(function() {	
	$('#continuarDatosAdicionales').click(function(e) {
		document.charset = "ISO-8859-1";	
		$('#continuarDatosAdicionales').submit();
	});});
</script>

<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="3" />
	</jsp:include>
	<form:form method="post"
		modelAttribute="datosAdicionalesHistoriaLaboral"
		id="datosAdicionalesForm" action="${urlDatosAdicionalesHistoriaLaboral}" accept-charset="ISO-8859-1">
		<div id="info-paso" style="margin-bottom: 50px;">
			<h3>
				<spring:message code="label.solicitud.datosContacto" />
			</h3>
			<hr class="red" style="margin-bottom: 20px;">
			
			<div class="form-group">
				<div class="row">
					<label for="telefonoFijo"
						class="col-md-3 col-sm-5 col-xs-12 control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.telefonoFijo" /><span>:</span>
					</label>
					<div class="col-md-3 col-sm-7 col-xs-12">
						<spring:message code="label.solicitud.placeholder.telefonoFijo"
							var="placeHolderTelefonoFijo" />
						<spring:message code="label.solicitud.tooltip.telefonoFijo"
							var="tooltipTelefonoFijo" />
						<form:input path="datosContacto.telefonoFijo"
							id="registroTelefonoFijo" cssClass="form-control" maxlength="10"
							style="font-size: 18px;" placeholder="${placeHolderTelefonoFijo}" 
							data-toggle="tooltip" data-placement="top" title="${tooltipTelefonoFijo}" />
						<form:errors path="datosContacto.telefonoFijo" cssClass="error" />
					</div>
					<label for="telefonoCelular"
						class="col-md-3 col-sm-5 col-xs-12 control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.telefonoCelular" /><span>:</span>
					</label>
					<div class="col-md-3 col-sm-7 col-xs-12">
						<spring:message code="label.solicitud.placeholder.telefonoCelular"
							var="placeHolderTelefonoCelular" />
						<spring:message code="label.solicitud.tooltip.telefonoCelular"
							var="tooltipTelefonoCelular" />
						<form:input path="datosContacto.telefonoCelular"
							id="registroTelefonoCelular" cssClass="form-control"
							maxlength="10" style="font-size: 18px;" placeholder="${placeHolderTelefonoCelular}" 
							data-toggle="tooltip" data-placement="top" title="${tooltipTelefonoCelular}" />
						<form:errors path="datosContacto.telefonoCelular" cssClass="error" />
					</div>
				</div>
				
				<div class="row top-buffer">
					<label for="correoElectronico"
						class="col-md-3 col-sm-5 col-xs-12 control-label"
						style="text-align: left;"> <spring:message code="label.datosContacto.correoElectronico" />
					</label>
					<div class="col-md-3 col-sm-7 col-xs-12">
						<spring:message code="label.placeholder.mail"
							var="placeHolderMail" />
						<form:input path="datosContacto.correoElectronico" style="font-size: 18px;"
							id="correoElectronico" cssClass="form-control" placeholder="${placeHolderMail}" />
						<form:errors path="datosContacto.correoElectronico" cssClass="error" />
					</div>
				</div>

		</div>
		
		<div id="info-paso" style="margin-bottom: 50px;">
			<h3>
				<spring:message code="label.solicitud.observaciones" />
			</h3>
			<hr class="red" style="margin-bottom: 20px;">
			<div class="form-group">
				<div class="row">
					<div class="col-md-12 col-sm-7 col-xs-12">
					<form:textarea path="observaciones" rows="5" cols="10" style="font-size: 18px;"
						cssClass="form-control" maxlength="250" cssStyle="text-transform: uppercase;"/>
					</div>
				</div>
			</div>
			
		</div>

	<!-- Controles -->
		
			<div class="pull-right">
					<%@ include file="regresar.jsp"%>
					<button type="submit" id="continuarDatosAdicionales"
						class="btn btn-primary">
						<spring:message code="label.continuar" />
					</button>			
			</div>

	</form:form>
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
	<div class="row">
	<div class= "pull-right">
			<div class="col-md-7">
				<jsp:include page="cancelarSolicitud.jsp"/>
			</div>
		</div>
	</div>
</div>
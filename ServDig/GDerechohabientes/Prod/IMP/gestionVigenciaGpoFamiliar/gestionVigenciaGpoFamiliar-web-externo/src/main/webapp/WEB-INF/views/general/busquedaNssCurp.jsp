<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portal/busquedaNssCurp.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="${staticServsDigPath}/gestionAsegurados-web-externo/static/resources/js/delta/wizard/busquedaNss/busquedaNssWizard.js"></script>


<div class="container">

	<!-- Seccion de datos de la identidad -->
		<div class="page-header">
			<h2 style="margin: 0px;">
				Busqueda de asegurado / pensionado
			</h2>
		</div>
			
	<c:if test="${not empty error}">
		<div class="alert alert-danger" style="text-align: justify;" id="error">
			${error}
		</div>
	</c:if>
	<div class="alert alert-info" style="text-align: justify;">
		A continuaci&oacute;n proporcione el NSS del asegurado o pensionado. En caso de no contar con el
		de clic en el bot&oacute;n "BUSCAR POR DATOS B&Aacute;SICOS"
	</div>

		<div align="center">
				<form:form modelAttribute="asignacion" method="post" action="#" role="form">
					<fieldset  class="bordered" >
						<div class="form-group">
							<label for="nss" class="col-sm-4 control-label">
							<span class="required">*</span>NSS</label>
							<div class="col-sm-5">
								<form:hidden path="idPersona"/>
								<form:hidden path="idAsignacionNSS"/>
								<form:hidden path="curp"/>
								<form:input path="nss" cssStyle="width: 300px" cssClass="form-control numerico" maxlength="11" />
								<span id="nssError" class="error hiddenElement"></span>
							</div>
						</div>
					</fieldset>
					<br>
					
					<div style="text-align: right; float: right;">
						<button type="button" id="buscar" class="btn btn-primary">BUSCAR</button>
						<button type="button" id="buscarPersona" class="btn btn-primary">BUSCAR POR DATOS B&Aacute;SICOS</button>
						<button type="button" id="limpiar" class="btn btn-default">LIMPIAR</button>
					</div>
				</form:form>
		</div>
</div>

<div id="busquedaNss"></div>
<br><br>
<!--Script al final para que la pagina cargue mas rapido-->
<script type="text/javascript">
    $(document).ready(function() {
        if (self != top) {
            top.location = self.location
        }
        
        $.postJSON("/portalDerechohabiente-ventanilla/wizard/tramite/1",null,function() {
        	//console.log("se hace llamada");
        })
    })
</script>

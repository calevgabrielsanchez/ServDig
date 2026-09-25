<%@ include file="../../../layout/taglibs.jsp" %>


	
	
	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/rest/pasoConsultaCtrl.js" htmlEscape="true" />"></script>
	
	
<div class="container">

    
	
      <div class="hero-unit">
   
        
        <div class="form-comment" style="padding-right: 20px;">
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<form:form modelAttribute="fisica" id="forma" method="post" action="${contextpath}/persona/fisica/consultar/validar/datos" cssClass="formNotBlock">	
						
						<c:if test="${ warning == true }">
		<div class="row" >

			<div class="alert alert-danger">
				<h6> Nota: ${mensajeException}</h6>
				Por favor verifique que los datos proporcionados sean correctos.
			</div>
		</div>
	</c:if>
						
						
						    <div class="alert">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <strong>Nota:</strong> Para un mejor funcionamiento del servicio todos los datos son requeridos.
    </div>
						
						<fieldset>
							<legend>
								<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
							</legend>

							<form:label path="curp" cssClass="wide">CURP</form:label>
							<form:errors path="curp" cssClass="error" ></form:errors>
							<span class="hiddenElement error" id="curpError"></span>
							<form:input path="curp" id="busquedaCurp" cssStyle="width: 300px" maxlength="18" />
							<br /><br /><br />
							
							<form:label path="rfc" cssClass="wide">RFC</form:label>
							<form:errors path="rfc" cssClass="error"></form:errors>
							<span class="hiddenElement error" id="rfcError"></span>
							<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
							<br /><br /><br />
							
							<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
							<form:errors path="nombre" cssClass="error"></form:errors>
							<span class="hiddenElement error" id="nombreError"></span>
							<form:input path="nombre" id="busquedaNombres" cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
							
							<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
							<form:errors path="primerApellido" cssClass="error"></form:errors>
							<span class="hiddenElement error" id="primerApellidoError"></span>
							<form:input path="primerApellido" id="busquedaPrimerApellido" cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
		
							<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
							<form:errors path="segundoApellido" cssClass="error"></form:errors>
							<span class="hiddenElement error" id="segundoApellidoError"></span>
							<form:input path="segundoApellido" id="busquedaSegundoApellido"	cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
							
							
														
						</fieldset>
	
						<div style="float: right;">
							
							<button type="submit" class="btn btn-secondary" id="buscar"> Buscar </button>
							<button type="button" class="btn btn-secondary" id="limpiar"> Limpiar </button>
						</div>	
						<br />
						
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
					</form:form>
						
					<form name="aoDataForm" id="aoDataForm">
						<input type="hidden" id="iDisplayStart" name="iDisplayStart" value="0"/>
						<input type="hidden" id="iDisplayLength" name="iDisplayLength" value="10"/>
					</form>
					
				</div>
        
        
      </div>

    
	<!-- Modal de confirmación del registro para complementar su informacion-->
	<div class="modal" id="modalInfoProcess" tabindex="-1" role="dialog" aria-labelledby="modalInfoProcessLabel" aria-hidden="true">
		<div class="modal-header">

			<h4 id="modalInfoProcessLabel">Mensaje de Sistema</h4>
		</div>
		<div class="modal-body">
			<div>
				

				<img alt="Cargando..." src="<spring:url value="/static/resources/imagenes/loading.gif" htmlEscape="true" />">
				
				
				<h6 id="messageProcesoConsulta"> Iniciando ...</h6>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancelar</button>
			<button class="btn btn-secondary continuar">Continuar</button>
		</div>
	</div>

    </div>
    
    <!-- Forma de la transicion al paso2 -->
    <form id="formaSiguiente" method="get" action="${contextpath}/servicios/internos/persona/fisica/paso2"></form>
    
    
    
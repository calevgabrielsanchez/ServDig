<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<style>
	#detalleDomicilio label {
	    display: inline;
	    
	}
	
	legend + .control-group {
	    margin-top: 0px;
	}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/asignacionNSS/contenido.js" htmlEscape="true" />"></script>

<div class="contenedor">

	<div class="contenido" style="width: 100%;">

		<form:form modelAttribute="fisica" id="forma" method="post">
			
			<c:if test="${not empty fisica.errorFormGeneral }">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${fisica.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${empty fisica.errorFormGeneral}">	
				
				<fieldset>
					<legend>DATOS B&Aacute;SICOS</legend>
						<span id="curpError" class="error hiddenElement"></span>
						<label class="control-label" for="curp" style="margin-right:10px; display:inline;">CURP:</label>
						<form:input path="curp" id="curp" maxlength="18"/>
				</fieldset>
				
				<fieldset>
					<legend>MEDIOS CONTACTO</legend>
					<div id="mediosContactoDiv">
						<span id="mediosContactoError" class="error hiddenElement"></span>
						<div id="admonMediosContactoDiv"></div>
					</div>
				</fieldset>
				
				<fieldset>
					<legend>DOMICILIO PARTICULAR</legend>
					<div id="domParticularDiv" >
						<span id="domiciliosError" class="error hiddenElement"></span>
						<div id="detalleDomicilio" style="display: none;">
							<address>
								<label id="vialidadPrimariaNombre"></label><br>
								<label id="numExteriorAlf"></label> <label id="numExterior1"></label>  
								<label id="numInteriorAlf"></label> <label id="numInterior"></label><br>
								<label id="asentamientoNombre"></label><br>
								<label id="municipioNombre"></label><br>
								<label id="entidadFederativaNombre"></label><br>
								C.P. <label id="codigoPostal"></label><br>
							</address>
						</div>
						<div style="float: right;">
							
							<button class="btn btn-primary" id="agregarDomicilio" type="button">
								AGREGAR DOMICILIO
							</button>
							
						</div>
					</div>
				</fieldset>
			</c:if>
		</form:form>
		<br><br>
		
	</div>

	<div class="pie">
		<div class="opciones">
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="enviarSolicitud"><i class="glyphicon glyphicon-ok"></i>Enviar Solicitud</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>
		
		<div class="controles"></div>
	</div>
</div>

<form action="/gestionAsegurados-web-externo/wizard/nss/captura/procesar"
	id="procesaCapturaNSSForm" method="post"></form>
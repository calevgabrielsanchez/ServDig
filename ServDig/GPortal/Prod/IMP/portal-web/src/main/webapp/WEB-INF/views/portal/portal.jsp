<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>

<script type="text/javascript"
	src="<spring:url value="${contextpath}/static/resources/js/wizard/registroUsuario/solicitudUsuarioWizard.js" htmlEscape="true" />">
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/authenticate.js"htmlEscape="true" />">
</script>

<script type="text/javascript"
	src="<spring:url value="${contextpath}/static/resources/js/delta/portal.js"htmlEscape="true" />">
</script>

<script type="text/javascript"
	src="/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/registro-usuario/registroUsuarioWizard.js"></script>

<script type="text/javascript"
	src="/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/renovacion-fiel/renovacionFielWizard.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="col-sm-12">
	<div class="row">
		<div class="col-md-6 col-sm-12">
			<div class="separadorseccion">
				<h2>Acceso escritorio virtual</h2>
			</div>
		</div>
		<div class="col-md-6  col-sm-12">
			<div class="pull-right" style="padding: 20px 10px">
				<img alt="" src="${staticResourcesPath}/imagenes/logoescri.png" width="120px" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 m-b-lg">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div style="margin-bottom: 10px" id="login">
						<form:form modelAttribute="usuario"
							action="${contextpath}/j_spring_security_check" method="get"
							id="formlogin">
							<form:hidden path="fisica.rfc"/>
							<button type="button" id="enviarForm"
								class="btn btn-primary btn-block" style="width:450px">
								<spring:message code="label.ingresar" />
							</button>
						</form:form>
					</div>
			
					<div id="registronuevousuario">
						<form id="formRegistroUsuarioNuevo">
							<button type="button" id="registrarUsuario"
								class="btn btn-default btn-block" style="width:450px">
								<spring:message code="label.portal.button.crear.cuenta.nueva" />
							</button>
						</form>
					</div>
					
					<div id="divRenovacionFiel" style="height: 100%; font-size: 15px;">
						Si ya te encuentras registrado y cambiaste tu e.firma o CURP
						da <span id="renovacionFiel" class="btn-link">clic
							aqu&iacute;</span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 m-b-lg">
		</div>
	</div>
</div>

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="wizardRegistroUsuario"></div>
<div id="wizardRenovacionFiel"></div>
<!-- div para la forma de firma -->
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>


<div id="dialog-mensajes" title="Mensaje del sistema">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo">Para poder ingresar, debe estar
			registrado como usuario</label>
	</p>
</div>


<div id="dialog-mensajesInicioBienvenido" title="Mensaje del sistema">
	<div class="container-fluid empty-state">
		<div class="row m-b-lg">
			<!-- Titulo -->
			<div class="col-xs-12 titulo">
				<h2 class="m-n">
					<spring:message code="label.registro.usuario.exito.bienvenido" />
				</h2>
			</div>
		</div>
		<div class="row">
			<!-- Imagen -->
			<div class="col-xs-12 imagen">
				<i class="fa fa-group fa-2x"></i>
			</div>
		</div>
		<div class="row m-t-lg">
			<div class="col-xs-12 text-center">
				<spring:message code="label.registro.usuario.exito" />
				<br>
				<strong>
					<spring:message code="label.registro.usuario.exito.espera" />
				</strong>
			</div>
		</div>
	</div>
</div>


<div id="dialog-mensajesInicio" title="Mensaje del sistema">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogoInicio"></label>
	</p>
</div>

<div id="dialog-mensajes-opinion" title="Aviso">
	<p>
		 <label
			id="mensajeDialogoOpinion">
				 <c:out value="${msgCartaOpinion}" escapeXml="false" />
			</label>
	</p>
</div>




</div>

<!--Script al final para que la pagina cargue mas rapido-->
<script type="text/javascript">
    $(document).ready(function() {
        if (self != top) {
            top.location = self.location
        }
        <c:if test="${muestraCartaOpinion}">
   	     mostrarMsgOpinion();
        </c:if>
    })
</script>
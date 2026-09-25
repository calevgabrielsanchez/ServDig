<%@ include file="../../../layout/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/renovacion-fiel/inicial.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
</script>

<div class="contenedor col-sm-12">

	<div class="contenido row">
		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> Actualizaci&oacute;n de cuenta de usuario</span>
			</div>

			<div class="descripcion">
				<p>Si usted ya se encuentra registrado y modific&oacute; su FIEL o CURP es necesaria la renovaci&oacute;n de su
					cuenta.</p>
			</div>
		</div>


		<div class="instrucciones col-sm-8">

			<div class="opciones">
				<c:if test="${not empty msgError}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong>Error: </strong>
						${msgError}
					</div>
				</c:if>
			</div>

			<h3>Instrucciones :</h3>
			<ul>
				<li>
					<p>
						1. Captura tu clave CURP a 18 posiciones con el que fue registrado en el sistema.
						<br>
						2. Una vez capturada tu clave CURP, oprime el bot&oacute;n Siguiente. El sistema te requerir&aacute; los archivos
						de tu Firma digital.
					</p>
				</li>
			</ul>
			<div class="well" style="background-color: white;">
				<div class="credenciales-caja">
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<div class="container" style="width: 100%">

						<form:form action="${contextpath}/wizard/tramite/renovacion/fiel/validaciones/negocio" modelAttribute="usuario"
							id="usuarioForm" method="post" cssClass="form-horizontal">

							<div class="form-group">

								<label class="control-label col-sm-2" for="usuario">CURP:</label>
								<div class="col-sm-8">
									<input type="hidden" id="fisica.curp" name="fisica.curp" />
									<input type="hidden" id="fisica.rfc" name="fisica.rfc" />
									<input type="hidden" id="password" name="password" />
									<input id="usuario" type="text" name="usuario" maxlength="18" class="form-control" />
									<span class="hiddenElement error" id="usuarioError"></span>

								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>

			<div class="opciones" align="right">
				<button class="btn btn-default" id="btnInicioCancelarTramite">Cancelar</button>
				<button class="btn btn-primary" id="btnInciaTramite">Siguiente</button>
			</div>
		</div>
	</div>

	<div class="pie"></div>
</div>


<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>
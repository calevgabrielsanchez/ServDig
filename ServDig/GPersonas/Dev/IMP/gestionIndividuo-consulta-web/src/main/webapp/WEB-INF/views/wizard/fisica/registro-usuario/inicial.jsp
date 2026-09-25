<%@ include file="../../../layout/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/registro-usuario/inicial.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> Registro de Usuario</span>
			</div>

			<div class="descripcion">
				<p>Para ingresar a los servicios del Portal deber&aacute;s registrarte previamente.</p>
				<p>Para ello, debes contar con el dato de tu clave CURP y los archivos de tu Firma Electr&oacute;nica Avanzada (e.firma),
					una vez comprobada la validez de la informaci&oacute;n, deber&aacute;s leer y aceptar la 'Carta de t&eacute;rminos
					y condiciones' para utilizar la Firma Electr&oacute;nica Avanzada (e.firma) en los actos que se realizan ante el IMSS.</p>
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

			<div>
				<ul>
					<li>
						<p>
							A trav&eacute;s de este sitio, podr&aacute;s crear tu cuenta de usuario para realizar tr&aacute;mites en
							l&iacute;nea y consultar los servicios que el Instituto ponga a tu disposici&oacute;n.
							<br>
							Para la creaci&oacute;n de la cuenta de usuario el Instituto verificar&aacute; tus datos con las entidades
							externas SAT y RENAPO para validar tu identidad.
						</p>
						<p>
							1. Captura tu clave CURP a 18 posiciones.
							<br>
							2. Una vez capturada tu clave CURP, oprime el bot&oacute;n Siguiente. El sistema te requerir&aacute; los archivos
							de tu Firma Electr&oacute;nica Avanzada (e.firma).
						</p>
					</li>
				</ul>
				<div class="credenciales-caja well" style="background-color: white;">
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />

						<form:form action="${contextpath}/wizard/tramite/registro/usuario/recupera/informacion" modelAttribute="fisica"
							id="fisicaForm" method="post" cssClass="form-horizontal">

							<div class="form-group">
								<label class="control-label col-sm-4" for="curpInputTmp">CURP *:</label>
								<div class="col-sm-8">
									<input id="curp" type="text" name="curp" maxlength="18" class="form-control" />
									<span id="curpInputTmpError" class="error hiddenElement"></span>
									<span class="hiddenElement error" id="curpError"></span>
									<input type="hidden" id="rfc" name="rfc" maxlength="13" />
								</div>
							</div>

						</form:form>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6" style="padding-top:10px">
					* Campos obligatorios
				</div>
				<div class="col-sm-6 text-right">
					<button class="btn btn-default" type="button" id="btnInicioCancelarTramite">Cancelar</button>
					<button class="btn btn-primary" type="button" id="btnInciaTramite">Siguiente</button>
				</div>
			</div>
		</div>
	</div>
	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>


<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> &iquest;Desea cancelar la
		solicitud?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
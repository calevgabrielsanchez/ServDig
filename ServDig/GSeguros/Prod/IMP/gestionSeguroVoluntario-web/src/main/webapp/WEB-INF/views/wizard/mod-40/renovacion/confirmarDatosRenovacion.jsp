<%@ include file="../../../general/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/resumen.js" htmlEscape="true" />">
	
</script>
<script type="text/javascript"
		src="<spring:url value="/static/resources/js/wizard/comunes/obtenerPais.js" htmlEscape="true" />">
</script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
.ui-selectable li {
	padding: 15px 25px;
}

.sub-header {
	padding-bottom: 10px;
	border-bottom: 2px solid #eee;
}

table.table {
	font-size: initial !important;
}

.row_right{
	float: right;
}
</style>

<script type="text/javascript">
	$(function() {

		$('a#condiciones')
				.click(
						function() {
							$('#dialogoMsgCondiciones')
									.html(
											'<div class="separadorseccion"><span>Términos y Condiciones</span></div><p>CARTA DE TÉRMINOS Y CONDICIONES EN LOS ACTOS QUE SE REALICEN ANTE EL INSTITUTO MEXICANO DEL SEGURO SOCIAL (IMSS) EN EL PORTAL CIUDADANO, MEDIANTE EL USO DE LA CLAVE ÚNICA DEL REGISTRO DE POBLACIÓN (CURP) Y EL REGISTRO FEDERAL DE CONTRIBUYENTES (RFC).</p>'
													+ '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque purus lorem, maximus nec nisl ac, vehicula ornare erat. Curabitur pharetra, orci ac viverra commodo, purus sem convallis quam, sed ornare arcu erat ac ipsum. Aenean ultrices ante nec ipsum ultricies tincidunt.'
													+ 'Praesent ultrices augue dapibus volutpat pulvinar. Nam malesuada fringilla efficitur. Donec at justo non sapien dapibus varius. Aliquam vitae urna vitae turpis sodales dapibus. Curabitur pharetra ac turpis a consequat. Nunc vel est pulvinar, venenatis elit at, auctor dui.</p>');
							$('#dialogoMsgCondiciones').dialog({
								title : 'IMSS Digital',
								dialogClass : "no-close",
								width : 800,
								modal : true,
								resizable : false,
								autoResize : true,
								position : {
									my : 'top',
									at : 'top',
									of : window.document,
									offset : '0 10'
								},
								buttons : {
									'ACEPTAR' : function() {
										$(this).dialog("close");
										$('#dialogoMsgCondiciones').html('');
									}
								}
							});

						});

		$('a#cancelarTramiteDialogo')
				.click(
						function() {
							$('#dialogoCancelarTramite')
									.html(
											'<p style="text-align: justify">'
													+ '<span style="float: left; margin: 0 7px 20px 0;" class="ui-icon ui-icon-alert"></span>'
													+ '¿Estas seguro de cancelar el proceso de registro de Incripci&oacute;n a la Continuaci&oacute;n Voluntaria en el R&eacute;gimen Obligatorio?</p>');
							$('#dialogoCancelarTramite').dialog({
								title : 'IMSS Digital',
								dialogClass : "no-close",
								height : 'auto',
								width : 300,
								modal : true,
								resizable : false,
								autoResize : true,
								position : {
									my : 'top',
									at : 'top',
									of : window.document,
									offset : '0 10'
								},
								buttons : {
									'ACEPTAR' : function() {
										closeWizard();
									},
									'CANCELAR' : function() {
										$(this).dialog("close");
									}
								}
							});
						});

	});
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12 form-horizontal">
			<c:set var="defaultLocale" value="${pageContext.request.locale}" />
			<fmt:setLocale value="es_MX" scope="session" />
			<div class="alert alert-success">
				Tu solicitud ha sido creada exitosamente: <strong>${solicitud.numSolicitud}</strong>
			</div>
            <input type="hidden" id="idSolicitud" value=${solicitud.idSolicitud} >

			<div id="datosSolicitante" class="m-b-lg">
				<div class="titulo">
					<span>Paso 3 de 4: Confirma tus datos</span>
				</div>
				<div class="titulo">
					<span>Datos del solicitante</span>
					<hr class="red m-b-none">
				</div>
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-3 control-label">Nombre</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.nombre}</p>
						</div>

						<label class="col-sm-3 control-label">NSS</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.nss}</p>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">Correo
							electr&oacute;nico</label>
						<div class="col-sm-3">
							<p class="form-control-static" style="word-wrap: break-word;">${solicitante.correoElectronico.correo}</p>
						</div>

						<label class="col-sm-3 control-label">Fecha solicitud</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<fmt:formatDate value="${fechaSolicitud}" pattern="dd/MM/yyyy" />
							</p>
						</div>
					</div>
				</form>
			</div>

			<div id="datosDomicilio" class="m-b-lg">
				<div class="titulo">
					<span>Domicilio</span>
					<hr class="red m-b-none">
				</div>
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-3 control-label">C&oacute;digo postal</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.domicilioParticular.codigoPostal}</p>
						</div>

						<label class="col-sm-3 control-label">Colonia</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.domicilioParticular.colonia}</p>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">Delegaci&oacute;n o
							municipio</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.domicilioParticular.localidad.municipio.nombre}</p>
						</div>

						<label class="col-sm-3 control-label">Estado</label>
						<div class="col-sm-3">
							<p class="form-control-static">${solicitante.domicilioParticular.localidad.municipio.entidadFederativa.nombre}</p>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-3 control-label">Calle</label>
						<div class="col-sm-3">
							<p class="form-control-static" style="word-wrap: break-word;">${solicitante.domicilioParticular.calle}</p>
						</div>

						<label class="col-sm-3 control-label">N&uacute;mero</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								${solicitante.domicilioParticular.numExteriorAlf}
								<c:if
									test="${solicitante.domicilioParticular.numExterior1 gt 0}">
											${solicitante.domicilioParticular.numExterior1}
										</c:if>
								<span> </span> ${solicitante.domicilioParticular.numInteriorAlf}
								<c:if test="${solicitante.domicilioParticular.numInterior gt 0}">
											${solicitante.domicilioParticular.numInterior}
										</c:if>
							</p>
						</div>
					</div>
				</form>
			</div>



			<div id="datosMovimientoAfiliatorio" class="m-b-lg">
				<div class="titulo">
					<span>Datos de &uacute;ltimo movimiento afiliatorio</span>
					<hr class="red m-b-none">
				</div>
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-3 control-label">Fecha de baja</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<fmt:formatDate value="${fechaPreviaBajaMora}" pattern="dd/MM/yyyy" />
							</p>
						</div>

						<label class="col-sm-3 control-label">&Uacute;ltimo
							salario registrado</label>
						<div class="col-sm-3">
							<p class="form-control-static">
								<fmt:formatNumber value="${ultSdi}" type="currency" />
							</p>
						</div>
					</div>
				</form>
			</div>

			<div id="detalleCotizacion" class="m-b-lg">
				<div class="titulo">
					<span>Detalle de la cotizaci&oacute;n</span>
					<hr class="red m-b-none">
				</div>
				<div class="table-responsive">
					<table
						class="table table-striped table-bordered table-word-wrap-fixed">
						<thead>
							<tr>
								<th>Inicio del periodo</th>
								<th>T&eacute;rmino del periodo</th>
								<th>Salario base de cotizaci&oacute;n</th>
								<th>Pago mensual</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${periodos}" var="periodo" varStatus="indice">
								<tr>
									<td style="text-align: center;"><fmt:formatDate
											value="${periodo.inicioPeriodo.time}" pattern="dd/MM/yyyy" /></td>
									<td style="text-align: center;"><fmt:formatDate
											value="${periodo.finPeriodo.time}" pattern="dd/MM/yyyy" /></td>
									<td style="text-align: center;"><fmt:formatNumber
											value="${periodo.salarioPeriodo}" type="currency" /></td>
									<td style="text-align: center;"><fmt:formatNumber
											value="${periodo.total}" type="currency" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>



			<div class="col-sm-12 alert alert-info">
				<p style="text-align: justify;">El c&aacute;lculo de los
					importes de la presente cotizaci&oacute;n, considera las cuotas
					obrero patronales correspondientes a los Seguros de Invalidez y
					Vida; de Retiro, Cesant&iacute;a en Edad Avanzada y Vejez,
					as&iacute; como las señaladas en el p&aacute;rrafo segundo del
					Art&iacute;culo 25 de la Ley del Seguro Social.</p>
				<br>
				<div class="" style="text-align: center;">
					<form:form id="aceptarTerminosCondiciones">
						<label> <input id="chkTCCuestionario" name="aceptarTC"
							type="checkbox" /> <span> Acepto los <a
								id="linkTCCuestionario" href="#">T&eacute;rminos y
									condiciones</a>
						</span>
						</label>
					</form:form>
				</div>
				<div style="display: none;">
					<%@ include
						file="../../mod-40/comunes/terminosCondicionesCuestionario.jsp"%>
				</div>
			</div>
			<fmt:setLocale value="${defaultLocale}" scope="session" />
		</div>
	</div>
	<br>

	<form:form id="impresionDocumentosForm"
		action="${contextPath}/wizard/continuacionVoluntaria/comunes/impresionDocumentos"
		method="post">
	</form:form>


	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6  text-right">
                <button id="cancelarSolicitud" class="btn btn-danger">
                    Cancelar
                </button>
                <button id="siguientePaso" class="btn btn-primary">
                    Finalizar
                </button>
		</div>
	</div>
	<div id="dialogoMsgSeleccion"></div>
	<div id="dialogoMsgCondiciones" style="width: 100%; height: 100%"></div>
	<div id="dialogoCancelarTramite"></div>

	<div id="dialog-confirm-cancelar"
		title="Confirmar cancelaci&oacute;n de solicitud">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> ¿Est&aacute;s seguro de cancelar la Solicitud <strong>${solicitud.numSolicitud}</strong> ?
		</p>
	</div>

	<div id="dialog-confirm" title="Mensaje">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="mensajeDialogo"></label>
		</p>
	</div>
</div>

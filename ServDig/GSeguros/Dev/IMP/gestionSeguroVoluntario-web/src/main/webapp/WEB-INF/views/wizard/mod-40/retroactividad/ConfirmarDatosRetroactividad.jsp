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

.row_right {
	float: right;
}

#tblCotizacionRetroactividad {
	width: 100%;
	margin-top: 15px;
}

#tblCotizacionRetroactividad thead th {
	text-align: center;
	vertical-align: middle;
	font-weight: bold;
}

#tblCotizacionRetroactividad tbody td {
	text-align: center;
	vertical-align: middle;
}

.linkDetalleRetroactividad {
	color: #337ab7;
	text-decoration: underline;
	cursor: pointer;
}

.linkDetalleRetroactividad:hover, .linkDetalleRetroactividad:focus {
	color: #23527c;
	text-decoration: underline;
}

#errorCalculoPagos {
	margin-top: 15px;
	margin-bottom: 15px;
}

.modalRetroactividadDialog {
	padding: 0 !important;
	background: #ffffff !important;
	border: 1px solid #d8d8d8 !important;
	border-radius: 0 !important;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.25);
}

.modalRetroactividadDialog .ui-dialog-titlebar {
	background: #ffffff !important;
	border: none !important;
	border-radius: 0 !important;
	border-bottom: 3px solid #9f2241 !important;
	padding: 16px 45px 12px 20px !important;
}

.modalRetroactividadDialog .ui-dialog-title {
	float: none !important;
	display: block;
	width: 100%;
	margin: 0 !important;
	color: #333333 !important;
	font-size: 18px;
	font-weight: normal;
	text-align: left;
}

.modalRetroactividadDialog .ui-dialog-titlebar-close {
	position: absolute;
	right: 12px;
	top: 50%;
	margin-top: -11px;
	width: 24px;
	height: 24px;
	border: none !important;
	background: transparent !important;
	box-shadow: none !important;
	outline: none !important;
}

.modalRetroactividadDialog .ui-dialog-titlebar-close:hover,
	.modalRetroactividadDialog .ui-dialog-titlebar-close:focus {
	background: #eeeeee !important;
	border: none !important;
	outline: none !important;
}

.modalRetroactividadDialog .ui-dialog-content {
	background: #ffffff !important;
	padding: 20px !important;
	overflow: visible !important;
}

#modalDetalleRetroactividad {
	padding: 20px !important;
}

.textoDetalleRetroactividad {
	margin-bottom: 15px;
	color: #555555;
	font-size: 14px;
	text-align: justify;
}

.detallePeriodosScroll {
	max-height: 420px;
	overflow-y: auto;
	overflow-x: auto;
}

#tblDetalleRetroactividad {
	width: 100%;
	margin: 0;
	background: #ffffff;
	border-collapse: collapse;
}

#tblDetalleRetroactividad thead th {
	background: #f5f5f5 !important;
	color: #333333 !important;
	text-align: center;
	vertical-align: middle;
	font-size: 14px;
	font-weight: bold;
	padding: 10px 8px;
	border: 1px solid #dddddd !important;
}

#tblDetalleRetroactividad tbody td {
	background: #ffffff !important;
	color: #333333;
	text-align: center;
	vertical-align: middle;
	font-size: 14px;
	padding: 10px 8px;
	border: 1px solid #dddddd !important;
}

#tblDetalleRetroactividad tbody tr:nth-child(even) td {
	background: #f9f9f9 !important;
}

#tblDetalleRetroactividad tbody tr:hover td {
	background: #f5f5f5 !important;
}

.sinDetalleRetroactividad {
	text-align: center !important;
	padding: 20px !important;
	background: #ffffff !important;
	color: #777777 !important;
}

.modalRetroactividadDialog .ui-dialog-buttonpane {
	background: #ffffff !important;
	border: none !important;
	border-top: 1px solid #eeeeee !important;
	margin: 0 !important;
	padding: 12px 15px !important;
}

.modalRetroactividadDialog .ui-dialog-buttonpane
.ui-dialog-buttonset {
	float: right;
}

.modalRetroactividadDialog .ui-dialog-buttonpane button {
	margin: 0 !important;
	padding: 6px 12px !important;
	border-radius: 4px !important;
	font-family: inherit !important;
	font-size: 14px !important;
}

@media ( max-width : 767px) {
	.modalRetroactividadDialog {
		width: 94% !important;
		left: 3% !important;
	}
	.modalRetroactividadDialog .ui-dialog-content {
		padding: 15px !important;
	}
	#tblDetalleRetroactividad {
		min-width: 500px;
	}
}
</style>


<script type="text/javascript">
	var calculoPagosActual = null;

	$(function() {

		$('a#condiciones')
				.click(
						function() {

							$('#dialogoMsgCondiciones').html(

									'<div class="separadorseccion">' +

									'<span>Términos y Condiciones</span>' +

									'</div>' +

									'<p>' +

									'CARTA DE TÉRMINOS Y CONDICIONES EN LOS ACTOS QUE SE '
											+

											'REALICEN ANTE EL INSTITUTO MEXICANO DEL SEGURO SOCIAL '
											+

											'(IMSS) EN EL PORTAL CIUDADANO, MEDIANTE EL USO DE LA '
											+

											'CLAVE ÚNICA DEL REGISTRO DE POBLACIÓN (CURP) Y EL '
											+

											'REGISTRO FEDERAL DE CONTRIBUYENTES (RFC).'
											+

											'</p>' +

											'<p>' +

											'Lorem ipsum dolor sit amet, consectetur adipiscing elit. '
											+

											'Quisque purus lorem, maximus nec nisl ac, vehicula ornare erat. '
											+

											'Curabitur pharetra, orci ac viverra commodo, purus sem convallis '
											+

											'quam, sed ornare arcu erat ac ipsum. Aenean ultrices ante nec '
											+

											'ipsum ultricies tincidunt. Praesent ultrices augue dapibus '
											+

											'volutpat pulvinar. Nam malesuada fringilla efficitur. Donec at '
											+

											'justo non sapien dapibus varius. Aliquam vitae urna vitae turpis '
											+

											'sodales dapibus. Curabitur pharetra ac turpis a consequat. '
											+

											'Nunc vel est pulvinar, venenatis elit at, auctor dui.'
											+

											'</p>'

							);

							$('#dialogoMsgCondiciones')
									.dialog(
											{

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

														$(
																'#dialogoMsgCondiciones')
																.html('');

														uid_call(

																'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCcondiciones.btn_aceptar',

																'clickin'

														);

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
													+

													'<span ' +

						'style="float: left; margin: 0 7px 20px 0;" ' +

						'class="ui-icon ui-icon-alert">'
													+

													'</span>' +

													'¿Estas seguro de cancelar el proceso de registro de '
													+

													'Incripci&oacute;n a la Continuaci&oacute;n Voluntaria '
													+

													'en el R&eacute;gimen Obligatorio?'
													+

													'</p>'

									);

							$('#dialogoCancelarTramite')
									.dialog(
											{

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

														uid_call(

																'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCancelar.btn_aceptar',

																'clickin'

														);

													},

													'CANCELAR' : function() {

														$(this).dialog("close");

														uid_call(

																'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCancelar.btn_cancelar',

																'clickin'

														);

													}

												}

											});

						});

		$('#modalDetalleRetroactividad')
				.dialog(
						{

							title : 'Detalle de montos a pagar por periodo',

							dialogClass : 'modalRetroactividadDialog',

							autoOpen : false,

							width : 700,

							height : 'auto',

							modal : true,

							resizable : false,

							draggable : false,

							closeOnEscape : true,

							position : {

								my : 'center',

								at : 'center',

								of : window

							},

							buttons : {

								'Cerrar' : function() {

									$(this).dialog('close');

								}

							},

							open : function() {

								$(this)
										.parent()
										.find('.ui-dialog-buttonpane button')
										.removeClass(
												'ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only')
										.addClass('btn btn-default');

							}

						});

		$(document).on(

		'click',

		'.linkDetalleRetroactividad',

		function(event) {

			event.preventDefault();

			abrirDetalleRetroactividad();

		}

		);

		$('#finalizarRetroactividad').prop('disabled', true);

		$('#chkTCCuestionario').change(
				function() {

					$('#finalizarRetroactividad').prop('disabled',
							!$(this).is(':checked'));

				});

		$('#finalizarRetroactividad')
				.click(
						function(event) {

							event.preventDefault();

							$('#errorCalculoPagos').hide().empty();

							if (!$('#chkTCCuestionario').is(':checked')) {

								$('#errorCalculoPagos')
										.html(
												'Debes aceptar los t&eacute;rminos y condiciones.')
										.show();

								return;

							}

							var idCalculo = null;

							if (calculoPagosActual
									&& calculoPagosActual.idCalculo) {

								idCalculo = calculoPagosActual.idCalculo;

							} else {

								idCalculo = $('#idCalculoRetroactividad').val();

							}

							if (!idCalculo) {

								$('#errorCalculoPagos')
										.html(
												'No fue posible obtener el identificador del c&aacute;lculo.')
										.show();

								return;

							}

							var requestMultilinea = {
								idCalculo : String(idCalculo)
							};

							$('#finalizarRetroactividad')
									.prop('disabled', true);

							$
									.ajax({

										url : '${contextPath}/retroactividad/generarMultilinea',

										type : 'POST',

										contentType : 'application/json; charset=UTF-8',

										dataType : 'json',

										data : JSON
												.stringify(requestMultilinea),

										complete : function() {

											var ventanaPadre = window.parent
													&& window.parent !== window ? window.parent
													: window;

											var jqueryPadre = ventanaPadre.jQuery
													|| ventanaPadre.$;

											var dialogo = null;

											if (jqueryPadre && jqueryPadre.fn
													&& jqueryPadre.fn.dialog) {

												dialogo = jqueryPadre('#dialogoTramiteEnCursoRetroactividad');

												if (dialogo.length === 0) {

													jqueryPadre('body')
															.append(
																	'<div id="dialogoTramiteEnCursoRetroactividad"></div>');

													dialogo = jqueryPadre('#dialogoTramiteEnCursoRetroactividad');

												}

												dialogo
														.html(
																'<p>El tr&aacute;mite se encuentra en curso, favor de intentar m&aacute;s tarde.</p>')
														.dialog(
																{

																	title : 'IMSS Digital',

																	autoOpen : false,

																	width : 450,

																	modal : true,

																	resizable : false,

																	buttons : {

																		'ACEPTAR' : function() {

																			jqueryPadre(
																					this)
																					.dialog(
																							'close')
																					.remove();

																		}

																	}

																});

											}

											closeWizard();

											if (dialogo) {

												dialogo.dialog('open');

											} else {

												ventanaPadre
														.alert('El tramite se encuentra en curso, favor de intentar mas tarde.');

											}

										}

									});

						});

		var idCalculo = $('#idCalculoRetroactividad').val();

		var nss = $('#nssRetroactividad').val();

		var entidadInegi = $('#entidadInegiRetroactividad').val();

		var municipioInegi = $('#municipioInegiRetroactividad').val();

		var salarioElegido = $('#salarioElegidoRetroactividad').val();

		var origenCalculo = $('#origenCalculoRetroactividad').val();

		var usuario = $('#usuarioRetroactividad').val();

		console.log('Servicio 2 - idCalculo: ', idCalculo);

		console.log('Servicio 2 - NSS: ', nss);

		console.log('Servicio 2 - salarioElegido: ', salarioElegido);

		if (!idCalculo || !nss || !entidadInegi || !municipioInegi
				|| !salarioElegido || !origenCalculo || !usuario) {

			$('#errorCalculoPagos').html(
					'No fue posible obtener todos los datos necesarios '
							+ 'para realizar el c&aacute;lculo de pagos.')
					.show();

			return;
		}

		var requestCalculo = crearRequestCalculoPagos(idCalculo, nss,
				entidadInegi, municipioInegi, salarioElegido, origenCalculo,
				usuario);

		console.log('Request Servicio 2: ', requestCalculo);

		consultarCalculoPagos(requestCalculo);

	});

	function procesarRespuestaCalculoPagos(response) {

		$('#errorCalculoPagos').hide().empty();

		if (!response) {

			$('#errorCalculoPagos').html(
					'No se obtuvo respuesta del servicio de c&aacute;lculo.')
					.show();

			return;

		}

		if (response.codigo != '200') {

			$('#errorCalculoPagos')
					.html(
							response.descripcion ? response.descripcion
									: 'No fue posible realizar el c&aacute;lculo de pagos.')
					.show();

			return;

		}

		if (!response.vrDto) {

			$('#errorCalculoPagos')
					.html(
							'El servicio no devolvi&oacute; informaci&oacute;n de c&aacute;lculo.')
					.show();

			return;

		}

		calculoPagosActual = response.vrDto;

		pintarResumenCalculoPagos(response.vrDto);

	}

	function pintarResumenCalculoPagos(response) {

		var body = $('#bodyCotizacionRetroactividad');

		body.empty();

		if (!response || !response.periodos || response.periodos.length === 0) {

			body.append(

			'<tr>' +

			'<td colspan="4" class="text-center">' +

			'No se encontraron periodos de cotizaci&oacute;n.' +

			'</td>' +

			'</tr>'

			);

			return;

		}

		var primerPeriodo = response.periodos[0];

		var ultimoPeriodo = response.periodos[response.periodos.length - 1];

		$('#fechaBajaRetroactividad').text(
				obtenerFechaBajaRetroactividad(primerPeriodo.fechaInicio));

		var fila =

		'<tr>'
				+

				'<td>'
				+

				formatearFechaServicio(primerPeriodo.fechaInicio)
				+

				'</td>'
				+

				'<td>'
				+

				formatearFechaServicio(ultimoPeriodo.fechaFin)
				+

				'</td>'
				+

				'<td>'
				+

				formatearMoneda(response.importeTotal)
				+

				'</td>'
				+

				'<td>'
				+

				'<a href="#" ' +

						'class="linkDetalleRetroactividad" ' +

						'data-id-calculo="' +

							response.idCalculo +

						'">'
				+

				'Ver detalle' +

				'</a>' +

				'</td>' +

				'</tr>';

		body.append(fila);

	}

	function abrirDetalleRetroactividad() {

		if (!calculoPagosActual || !calculoPagosActual.periodos) {

			return;

		}

		pintarDetalleRetroactividad(calculoPagosActual.periodos);

		$('#modalDetalleRetroactividad').dialog('open');

	}

	function pintarDetalleRetroactividad(periodos) {

		var body = $('#bodyDetalleRetroactividad');

		body.empty();

		if (!periodos || periodos.length === 0) {

			body.append(

			'<tr>' +

			'<td colspan="4" ' +

						'class="sinDetalleRetroactividad">' +

			'No se encontr&oacute; ' +

			'informaci&oacute;n de detalle.' +

			'</td>' +

			'</tr>'

			);

			return;

		}

		$.each(

		periodos,

		function(index, periodo) {

			var fila =

			'<tr>' +

			'<td>' +

			formatearFechaServicio(periodo.fechaInicio) +

			'</td>' +

			'<td>' +

			formatearFechaServicio(periodo.fechaFin) +

			'</td>' +

			'<td>' +

			formatearMoneda(periodo.salarioElegido) +

			'</td>' +

			'<td>' +

			formatearMoneda(periodo.importePago) +

			'</td>' +

			'</tr>';

			body.append(fila);

		}

		);

	}

	function obtenerFechaBajaRetroactividad(fechaInicio) {

		if (!fechaInicio) {

			return '';

		}

		var anio;
		var mes;
		var dia;

		if (fechaInicio.indexOf('-') !== -1) {

			var partesGuion = fechaInicio.split('-');

			if (partesGuion.length !== 3) {

				return '';

			}

			anio = parseInt(partesGuion[0], 10);

			mes = parseInt(partesGuion[1], 10);

			dia = parseInt(partesGuion[2], 10);

		} else if (fechaInicio.indexOf('/') !== -1) {

			var partesDiagonal = fechaInicio.split('/');

			if (partesDiagonal.length !== 3) {

				return '';

			}

			dia = parseInt(partesDiagonal[0], 10);

			mes = parseInt(partesDiagonal[1], 10);

			anio = parseInt(partesDiagonal[2], 10);

		} else {

			return '';

		}

		var fecha = new Date(anio, mes - 1, dia);

		fecha.setDate(fecha.getDate() - 1);

		var diaBaja = fecha.getDate();

		var mesBaja = fecha.getMonth() + 1;

		var anioBaja = fecha.getFullYear();

		if (diaBaja < 10) {

			diaBaja = '0' + diaBaja;

		}

		if (mesBaja < 10) {

			mesBaja = '0' + mesBaja;

		}

		return diaBaja + '/' + mesBaja + '/' + anioBaja;

	}

	function formatearFechaServicio(fecha) {

		if (!fecha) {

			return '';

		}

		var partes = fecha.split('-');

		if (partes.length !== 3) {

			return fecha;

		}

		return partes[2] + '/' + partes[1] + '/' + partes[0];

	}

	function formatearMoneda(valor) {

		if (valor === null || valor === undefined || valor === '') {

			return '';

		}

		var numero = parseFloat(valor);

		if (isNaN(numero)) {

			return valor;

		}

		return '$' + numero.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');

	}

	function crearRequestCalculoPagos(idCalculo, nss, entidadInegi,
			municipioInegi, salarioElegido, origenCalculo, usuario) {

		return {

			idCalculo : parseInt(idCalculo, 10),

			nss : nss,

			entidadInegi : entidadInegi,

			municipioInegi : municipioInegi,

			salarioElegido : parseFloat(salarioElegido),

			origenCalculo : origenCalculo,

			usuario : usuario

		};

	}

	function consultarCalculoPagos(request) {

		$('#errorCalculoPagos').hide().empty();

		$.ajax({

			url : '${contextPath}/retroactividad/calculoPagos',

			type : 'POST',

			contentType : 'application/json; charset=UTF-8',

			dataType : 'json',

			data : JSON.stringify(request),

			success : function(response) {

				console.log('Respuesta Servicio 2: ', response);

				procesarRespuestaCalculoPagos(response);

			},

			error : function(xhr) {

				console.log('Error Servicio 2: ', xhr);

				manejarErrorCalculoPagos(xhr);

			}

		});

	}

	function manejarErrorCalculoPagos(xhr) {

		var response = xhr.responseJSON;

		var mensaje = 'No fue posible realizar el c&aacute;lculo de pagos.';

		if (response && response.mensaje) {

			mensaje = response.mensaje;

		} else if (response && response.descripcion) {

			mensaje = response.descripcion;

		}

		$('#errorCalculoPagos').html(mensaje).show();

	}
</script>


<div class="contenedor col-sm-12">


	<div class="contenido row">


		<div class="col-sm-12 form-horizontal">


			<c:set var="defaultLocale" value="${pageContext.request.locale}" />


			<fmt:setLocale value="es_MX" scope="session" />


			<jsp:include page="encabezadoMod40.jsp">


				<jsp:param name="paso" value="3" />


			</jsp:include>


			<div class="alert alert-success">


				Tu solicitud ha sido creada exitosamente. <strong> </strong>


			</div>



			<input type="hidden" id="idCalculoRetroactividad"
				value="${solicitud.idCalculo}" /> <input type="hidden"
				id="nssRetroactividad" value="${solicitante.nss}" /> <input
				type="hidden" id="entidadInegiRetroactividad"
				value="${entidadInegi}" /> <input type="hidden"
				id="municipioInegiRetroactividad" value="${municipioInegi}" /> <input
				type="hidden" id="origenCalculoRetroactividad"
				value="${solicitud.origenCalculo}" /> <input type="hidden"
				id="usuarioRetroactividad" value="${solicitud.usuario}" /> <input
				type="hidden" id="salarioElegidoRetroactividad" value="${sbc}" />


			<div id="datosSolicitante" class="m-b-lg">


				<div class="titulo">


					<span> Datos del solicitante </span>


					<hr class="red m-b-none">


				</div>


				<form class="form-horizontal" role="form">


					<div class="form-group">


						<label class="col-sm-3 control-label"> Nombre: </label>


						<div class="col-sm-3">


							<p class="form-control-static">${solicitante.nombre}</p>


						</div>


						<label class="col-sm-3 control-label"> NSS: </label>


						<div class="col-sm-3">


							<p class="form-control-static">${solicitante.nss}</p>


						</div>


					</div>


					<div class="form-group">


						<label class="col-sm-3 control-label"> Correo
							electr&oacute;nico: </label>


						<div class="col-sm-3">


							<p class="form-control-static" style="word-wrap: break-word;">


								${solicitante.correoElectronico.correo}</p>


						</div>


						<label class="col-sm-3 control-label"> Fecha solicitud: </label>


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


					<span> Domicilio </span>


					<hr class="red m-b-none">


				</div>


				<form class="form-horizontal" role="form">


					<div class="form-group">


						<label class="col-sm-3 control-label"> C&oacute;digo
							postal: </label>


						<div class="col-sm-3">


							<p class="form-control-static">

								${solicitante.domicilioParticular.codigoPostal}</p>


						</div>


						<label class="col-sm-3 control-label"> Colonia: </label>


						<div class="col-sm-3">


							<p class="form-control-static">

								${solicitante.domicilioParticular.colonia}</p>


						</div>


					</div>


					<div class="form-group">


						<label class="col-sm-3 control-label"> Municipio o
							Alcald&iacute;a: </label>


						<div class="col-sm-3">


							<p class="form-control-static">

								${solicitante.domicilioParticular.localidad.municipio.nombre}</p>


						</div>


						<label class="col-sm-3 control-label"> Estado: </label>


						<div class="col-sm-3">


							<p class="form-control-static">

								${solicitante.domicilioParticular.localidad.municipio.entidadFederativa.nombre}

							</p>


						</div>


					</div>


					<div class="form-group">


						<label class="col-sm-3 control-label"> Calle: </label>


						<div class="col-sm-3">


							<p class="form-control-static" style="word-wrap: break-word;">


								${solicitante.domicilioParticular.calle}</p>


						</div>


						<label class="col-sm-3 control-label"> N&uacute;mero: </label>


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


					<span> Datos de &uacute;ltimo movimiento afiliatorio: </span>


					<hr class="red m-b-none">


				</div>


				<form class="form-horizontal" role="form">


					<div class="form-group">


						<label class="col-sm-3 control-label"> Fecha de baja: </label>


						<div class="col-sm-3">


							<p id="fechaBajaRetroactividad" class="form-control-static"></p>


						</div>


						<label class="col-sm-3 control-label"> &Uacute;ltimo
							salario registrado: </label>


						<div class="col-sm-3">


							<p class="form-control-static"></p>


						</div>


					</div>


				</form>


			</div>


			<div id="detalleCotizacion" class="m-b-lg">


				<div class="titulo">


					<span> Detalle de la cotizaci&oacute;n para la
						Retroactividad: </span>


					<hr class="red m-b-none">


				</div>


				<div id="errorCalculoPagos" class="alert alert-danger"
					style="display: none;"></div>


				<div class="table-responsive m-t-md">


					<table id="tblCotizacionRetroactividad"
						class="table table-striped table-bordered table-word-wrap-fixed">


						<thead>


							<tr>


								<th>Inicio del periodo</th>


								<th>T&eacute;rmino del periodo</th>


								<th>Pago</th>


								<th>Detalle</th>


							</tr>


						</thead>


						<tbody id="bodyCotizacionRetroactividad">


						</tbody>


					</table>


				</div>


			</div>


			<div class="col-sm-12 alert alert-info">


				<p style="text-align: justify;">El c&aacute;lculo de los
					importes de la presente cotizaci&oacute;n, considera las cuotas
					obrero patronales correspondientes a los seguros de invalidez y
					vida; de retiro, cesant&iacute;a en edad avanzada y vejez,
					as&iacute; como las señaladas en el p&aacute;rrafo segundo del
					art&iacute;culo 25 de la ley del seguro social.</p>


				<br>


				<div style="text-align: center;">


					<form:form id="aceptarTerminosCondiciones">


						<label> <input id="chkTCCuestionario" name="aceptarTC"
							class="ns_" type="checkbox" /> <span> Acepto los <a
								id="linkTCCuestionario" href="#"> T&eacute;rminos y
									condiciones </a>


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


	<div id="modalDetalleRetroactividad" style="display: none;">


		<p class="textoDetalleRetroactividad">Consulta el desglose de los
			montos correspondientes a los periodos calculados.</p>


		<div class="table-responsive detallePeriodosScroll">


			<table id="tblDetalleRetroactividad"
				class="table table-striped table-bordered">


				<thead>


					<tr>


						<th>Inicio del periodo</th>


						<th>T&eacute;rmino del periodo</th>


						<th>Salario base</th>


						<th>Pago</th>


					</tr>


				</thead>


				<tbody id="bodyDetalleRetroactividad">


				</tbody>


			</table>


		</div>


	</div>


	<form:form id="impresionDocumentosForm"
		action="${contextPath}/wizard/continuacionVoluntaria/comunes/impresionDocumentos"
		method="post">


	</form:form>


	<div class="pie row">


		<div class="opciones col-sm-6"></div>


		<div class="col-sm-6 text-right">


			<button id="cancelarSolicitud" class="btn btn-danger"
				onclick="uid_call(
					'imss.gestion.seguro.voluntario.mod40.confirmarDatos.btn_cancelar',
					'clickout'
				);">


				Cancelar</button>

			<button type="button" id="finalizarRetroactividad"
				class="btn btn-primary" disabled="disabled"
				onclick="uid_call(
					'imss.gestion.seguro.voluntario.mod40.confirmarDatos.btn_finalizar',
					'clickout'
				);">


				Finalizar</button>


		</div>


	</div>


	<div id="dialogoMsgSeleccion"></div>


	<div id="dialogoMsgCondiciones" style="width: 100%; height: 100%">


	</div>


	<div id="dialogoCancelarTramite"></div>


	<div id="dialog-confirm-cancelar"
		title="Confirmar cancelaci&oacute;n de solicitud">


		<p>


			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> ¿Desea cancelar
			la solicitud pendiente con folio: <strong> </strong> ?


		</p>


	</div>


	<div id="dialog-confirm" title="Mensaje">


		<p>


			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> <label
				id="mensajeDialogo"> </label>


		</p>


	</div>


</div>


<script language="JavaScript1.2"
	src="${staticResourcesPath}/js/comscore/Form.js">
	
	
	



</script>

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

/* ============================================================
   TABLA PRINCIPAL RETROACTIVIDAD
   ============================================================ */
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

/* ============================================================
   MENSAJE ERROR CÁLCULO
   ============================================================ */
#errorCalculoPagos {
	margin-top: 15px;
	margin-bottom: 15px;
}

/* ============================================================
   POPUP DETALLE RETROACTIVIDAD
   ============================================================ */
.modalRetroactividadDialog {
	padding: 0 !important;
	background: #ffffff !important;
	border: 1px solid #d8d8d8 !important;
	border-radius: 0 !important;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.25);
}

/* ============================================================
   ENCABEZADO POPUP
   ============================================================ */
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

/* ============================================================
   BOTÓN X POPUP
   ============================================================ */
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

/* ============================================================
   CONTENIDO POPUP
   ============================================================ */
.modalRetroactividadDialog .ui-dialog-content {
	background: #ffffff !important;
	padding: 20px !important;
	overflow: visible !important;
}

#modalDetalleRetroactividad {
	padding: 20px !important;
}

/* ============================================================
   TEXTO POPUP
   ============================================================ */
.textoDetalleRetroactividad {
	margin-bottom: 15px;
	color: #555555;
	font-size: 14px;
	text-align: justify;
}

/* ============================================================
   SCROLL DETALLE
   ============================================================ */
.detallePeriodosScroll {
	max-height: 420px;
	overflow-y: auto;
	overflow-x: auto;
}

/* ============================================================
   TABLA POPUP
   ============================================================ */
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

/* ============================================================
   SIN INFORMACIÓN
   ============================================================ */
.sinDetalleRetroactividad {
	text-align: center !important;
	padding: 20px !important;
	background: #ffffff !important;
	color: #777777 !important;
}

/* ============================================================
   PIE POPUP
   ============================================================ */
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

/* ============================================================
   RESPONSIVO
   ============================================================ */
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


	/* ============================================================
	   RESPUESTA ACTUAL DEL SERVICIO DE CÁLCULO
	   ============================================================ */

	var calculoPagosActual = null;



	$(function() {


		/* ========================================================
		   TÉRMINOS Y CONDICIONES
		   ======================================================== */

		$('a#condiciones').click(function() {

			$('#dialogoMsgCondiciones').html(

				'<div class="separadorseccion">' +

					'<span>Términos y Condiciones</span>' +

				'</div>' +

				'<p>' +

					'CARTA DE TÉRMINOS Y CONDICIONES EN LOS ACTOS QUE SE ' +

					'REALICEN ANTE EL INSTITUTO MEXICANO DEL SEGURO SOCIAL ' +

					'(IMSS) EN EL PORTAL CIUDADANO, MEDIANTE EL USO DE LA ' +

					'CLAVE ÚNICA DEL REGISTRO DE POBLACIÓN (CURP) Y EL ' +

					'REGISTRO FEDERAL DE CONTRIBUYENTES (RFC).' +

				'</p>' +

				'<p>' +

					'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ' +

					'Quisque purus lorem, maximus nec nisl ac, vehicula ornare erat. ' +

					'Curabitur pharetra, orci ac viverra commodo, purus sem convallis ' +

					'quam, sed ornare arcu erat ac ipsum. Aenean ultrices ante nec ' +

					'ipsum ultricies tincidunt. Praesent ultrices augue dapibus ' +

					'volutpat pulvinar. Nam malesuada fringilla efficitur. Donec at ' +

					'justo non sapien dapibus varius. Aliquam vitae urna vitae turpis ' +

					'sodales dapibus. Curabitur pharetra ac turpis a consequat. ' +

					'Nunc vel est pulvinar, venenatis elit at, auctor dui.' +

				'</p>'

			);


			$('#dialogoMsgCondiciones').dialog({

				title: 'IMSS Digital',

				dialogClass: "no-close",

				width: 800,

				modal: true,

				resizable: false,

				autoResize: true,

				position: {

					my: 'top',

					at: 'top',

					of: window.document,

					offset: '0 10'

				},

				buttons: {

					'ACEPTAR': function() {

						$(this).dialog("close");

						$('#dialogoMsgCondiciones').html('');

						uid_call(

							'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCcondiciones.btn_aceptar',

							'clickin'

						);

					}

				}

			});

		});



		/* ========================================================
		   CANCELAR TRÁMITE
		   ======================================================== */

		$('a#cancelarTramiteDialogo').click(function() {

			$('#dialogoCancelarTramite').html(

				'<p style="text-align: justify">' +

					'<span ' +

						'style="float: left; margin: 0 7px 20px 0;" ' +

						'class="ui-icon ui-icon-alert">' +

					'</span>' +

					'¿Estas seguro de cancelar el proceso de registro de ' +

					'Incripci&oacute;n a la Continuaci&oacute;n Voluntaria ' +

					'en el R&eacute;gimen Obligatorio?' +

				'</p>'

			);


			$('#dialogoCancelarTramite').dialog({

				title: 'IMSS Digital',

				dialogClass: "no-close",

				height: 'auto',

				width: 300,

				modal: true,

				resizable: false,

				autoResize: true,

				position: {

					my: 'top',

					at: 'top',

					of: window.document,

					offset: '0 10'

				},

				buttons: {

					'ACEPTAR': function() {

						closeWizard();

						uid_call(

							'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCancelar.btn_aceptar',

							'clickin'

						);

					},

					'CANCELAR': function() {

						$(this).dialog("close");

						uid_call(

							'imss.gestion.seguro.voluntario.mod40.confirmarDatos.dialogoCancelar.btn_cancelar',

							'clickin'

						);

					}

				}

			});

		});



		/* ========================================================
		   INICIALIZACIÓN POPUP RETROACTIVIDAD
		   ======================================================== */

		$('#modalDetalleRetroactividad').dialog({

			title: 'Detalle de montos a pagar por periodo',

			dialogClass: 'modalRetroactividadDialog',

			autoOpen: false,

			width: 700,

			height: 'auto',

			modal: true,

			resizable: false,

			draggable: false,

			closeOnEscape: true,

			position: {

				my: 'center',

				at: 'center',

				of: window

			},

			buttons: {

				'Cerrar': function() {

					$(this).dialog('close');

				}

			},

			open: function() {

				$(this)
					.parent()
					.find('.ui-dialog-buttonpane button')
					.removeClass(
						'ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'
					)
					.addClass('btn btn-default');

			}

		});



		/* ========================================================
		   CLICK VER DETALLE
		   ======================================================== */

		$(document).on(

			'click',

			'.linkDetalleRetroactividad',

			function(event) {

				event.preventDefault();

				abrirDetalleRetroactividad();

			}

		);



		/* ========================================================
		   MOCK TEMPORAL

		  SERVICIOO
		   ======================================================== */

		var responseMock = obtenerCalculoPagosMock();

		procesarRespuestaCalculoPagos(
			responseMock
		);


	});



	/* ============================================================
	   MOCK DEL SERVICIO

	   POST /API/MOD40/V1/CALCULO-PAGOS
	   ============================================================ */

	function obtenerCalculoPagosMock() {


		return {


			idCalculo: 123,


			idCotizacion: 123,


			idTramite: 'M40-CALC-123',


			nss: '90000000120',


			municipio: '02A06',


			moneda: 'MXN',


			numeroPeriodos: 6,


			importeTotal: 6994.02,


			calculoProvisional: false,


			periodos: [


				{

					idPagoPeriodo: 301,

					anio: 2021,

					mes: 10,

					fechaInicio: '2021-10-15',

					fechaFin: '2021-10-31',

					diasNaturales: 17,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 812.34,

					importeActualizacion: 145.22,

					importeRecargo: 208.11,

					importePago: 1165.67,

					detalleRamas: []

				},


				{

					idPagoPeriodo: 302,

					anio: 2021,

					mes: 11,

					fechaInicio: '2021-11-01',

					fechaFin: '2021-11-30',

					diasNaturales: 30,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 820.00,

					importeActualizacion: 140.00,

					importeRecargo: 205.67,

					importePago: 1165.67,

					detalleRamas: []

				},


				{

					idPagoPeriodo: 303,

					anio: 2021,

					mes: 12,

					fechaInicio: '2021-12-01',

					fechaFin: '2021-12-31',

					diasNaturales: 31,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 820.00,

					importeActualizacion: 140.00,

					importeRecargo: 205.67,

					importePago: 1165.67,

					detalleRamas: []

				},


				{

					idPagoPeriodo: 304,

					anio: 2022,

					mes: 1,

					fechaInicio: '2022-01-01',

					fechaFin: '2022-01-31',

					diasNaturales: 31,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 820.00,

					importeActualizacion: 140.00,

					importeRecargo: 205.67,

					importePago: 1165.67,

					detalleRamas: []

				},


				{

					idPagoPeriodo: 305,

					anio: 2022,

					mes: 2,

					fechaInicio: '2022-02-01',

					fechaFin: '2022-02-28',

					diasNaturales: 28,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 820.00,

					importeActualizacion: 140.00,

					importeRecargo: 205.67,

					importePago: 1165.67,

					detalleRamas: []

				},


				{

					idPagoPeriodo: 306,

					anio: 2022,

					mes: 3,

					fechaInicio: '2022-03-01',

					fechaFin: '2022-03-31',

					diasNaturales: 31,

					salarioElegido: 1500.00,

					salarioAplicado: 1500.00,

					importeBase: 820.00,

					importeActualizacion: 140.00,

					importeRecargo: 205.66,

					importePago: 1165.67,

					detalleRamas: []

				}


			]


		};

	}



	/* ============================================================
	   PROCESAR RESPUESTA DEL SERVICIO
	   ============================================================ */

	function procesarRespuestaCalculoPagos(response) {


		$('#errorCalculoPagos')
			.hide()
			.empty();


		calculoPagosActual = response;


		pintarResumenCalculoPagos(
			response
		);

	}



	/* ============================================================
	   PINTAR TABLA PRINCIPAL
	   ============================================================ */

	function pintarResumenCalculoPagos(response) {


		var body =
			$('#bodyCotizacionRetroactividad');


		body.empty();



		if (!response ||
			!response.periodos ||
			response.periodos.length === 0) {


			body.append(

				'<tr>' +

					'<td colspan="5" class="text-center">' +

						'No se encontraron periodos de cotizaci&oacute;n.' +

					'</td>' +

				'</tr>'

			);


			return;

		}



		var primerPeriodo =
			response.periodos[0];


		var ultimoPeriodo =
			response.periodos[
				response.periodos.length - 1
			];



		var salarioElegido =
			primerPeriodo.salarioElegido;



		var fila =

			'<tr>' +

				'<td>' +

					formatearFechaServicio(
						primerPeriodo.fechaInicio
					) +

				'</td>' +

				'<td>' +

					formatearFechaServicio(
						ultimoPeriodo.fechaFin
					) +

				'</td>' +

				'<td>' +

					formatearMoneda(
						salarioElegido
					) +

				'</td>' +

				'<td>' +

					formatearMoneda(
						response.importeTotal
					) +

				'</td>' +

				'<td>' +

					'<a href="#" ' +

						'class="linkDetalleRetroactividad" ' +

						'data-id-calculo="' +

							response.idCalculo +

						'">' +

						'Ver detalle' +

					'</a>' +

				'</td>' +

			'</tr>';



		body.append(
			fila
		);

	}



	/* ============================================================
	   ABRIR DETALLE
	   ============================================================ */

	function abrirDetalleRetroactividad() {


		if (!calculoPagosActual ||
			!calculoPagosActual.periodos) {


			return;

		}



		pintarDetalleRetroactividad(
			calculoPagosActual.periodos
		);



		$('#modalDetalleRetroactividad')
			.dialog('open');

	}



	/* ============================================================
	   PINTAR DETALLE DE PERIODOS
	   ============================================================ */

	function pintarDetalleRetroactividad(periodos) {


		var body =
			$('#bodyDetalleRetroactividad');


		body.empty();



		if (!periodos ||
			periodos.length === 0) {


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

							formatearFechaServicio(
								periodo.fechaInicio
							) +

						'</td>' +

						'<td>' +

							formatearFechaServicio(
								periodo.fechaFin
							) +

						'</td>' +

						'<td>' +

							formatearMoneda(
								periodo.salarioAplicado
							) +

						'</td>' +

						'<td>' +

							formatearMoneda(
								periodo.importePago
							) +

						'</td>' +

					'</tr>';



				body.append(
					fila
				);

			}

		);

	}



	/* ============================================================
	   FORMATEAR FECHA
	   YYYY-MM-DD -> DD/MM/YYYY
	   ============================================================ */

	function formatearFechaServicio(fecha) {


		if (!fecha) {

			return '';

		}


		var partes =
			fecha.split('-');


		if (partes.length !== 3) {

			return fecha;

		}


		return partes[2]
			+ '/'
			+ partes[1]
			+ '/'
			+ partes[0];

	}



	/* ============================================================
	   FORMATEAR MONEDA
	   ============================================================ */

	function formatearMoneda(valor) {


		if (valor === null ||
			valor === undefined ||
			valor === '') {


			return '';

		}



		var numero =
			parseFloat(valor);



		if (isNaN(numero)) {

			return valor;

		}



		return '$'
			+ numero
				.toFixed(2)
				.replace(
					/\B(?=(\d{3})+(?!\d))/g,
					','
				);

	}



	/* ============================================================
	   REQUEST REAL DEL SERVICIO

	   CUANDO ESTÉ DISPONIBLE EL SERVICIO SE UTILIZARÁ
	   UNA ESTRUCTURA COMO ESTA.

	   CAMPOS REQUERIDOS:

	   idCalculo
	   nss
	   municipioImss
	   salarioElegido
	   origenCalculo
	   usuario
	   ============================================================ */

	function crearRequestCalculoPagos(
		idCalculo,
		nss,
		municipioImss,
		salarioElegido
	) {


		return {


			idCalculo:
				parseInt(
					idCalculo,
					10
				),


			nss:
				nss,


			municipioImss:
				municipioImss,


			salarioElegido:
				parseFloat(
					salarioElegido
				),


			origenCalculo:
				'CONTRATACION',


			usuario:
				'MODALIDAD40'


		};

	}



	/* ============================================================
	   CONSUMO REAL DEL SERVICIO

	   ESTA FUNCIÓN YA QUEDA PREPARADA.

	   FALTA CONFIRMAR HOST REAL DEL SERVICIO.

	   NO SE LLAMA TODAVÍA.
	   ============================================================ */

	function consultarCalculoPagos(
		request,
		urlServicio
	) {


		$('#errorCalculoPagos')
			.hide()
			.empty();



		$.ajax({


			url:
				urlServicio,


			type:
				'POST',


			contentType:
				'application/json; charset=UTF-8',


			dataType:
				'json',


			data:
				JSON.stringify(
					request
				),


			success: function(response) {


				procesarRespuestaCalculoPagos(
					response
				);


			},


			error: function(xhr) {


				manejarErrorCalculoPagos(
					xhr
				);


			}


		});

	}



	/* ============================================================
	   MANEJO DE ERROR DEL SERVICIO
	   ============================================================ */

	function manejarErrorCalculoPagos(xhr) {


		var response =
			xhr.responseJSON;


		var mensaje =
			'No fue posible realizar el c&aacute;lculo de pagos.';



		if (response &&
			response.mensaje) {


			mensaje =
				response.mensaje;

		}



		$('#errorCalculoPagos')
			.html(
				mensaje
			)
			.show();

	}



</script>



<div class="contenedor col-sm-12">


	<div class="contenido row">


		<div class="col-sm-12 form-horizontal">


			<c:set var="defaultLocale" value="${pageContext.request.locale}" />


			<fmt:setLocale value="es_MX" scope="session" />



			<!-- ===================================================
			     ENCABEZADO
			     =================================================== -->

			<jsp:include page="encabezadoMod40.jsp">


				<jsp:param name="paso" value="3" />


			</jsp:include>



			<!-- ===================================================
			     MENSAJE SOLICITUD
			     =================================================== -->

			<div class="alert alert-success">


				Tu solicitud ha sido creada exitosamente: <strong>

					${solicitud.numSolicitud} </strong>


			</div>



			<input type="hidden" id="idSolicitud"
				value="${solicitud.idSolicitud}" />



			<!-- ===================================================
			     DATOS SOLICITANTE
			     =================================================== -->

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



			<!-- ===================================================
			     DOMICILIO
			     =================================================== -->

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



			<!-- ===================================================
			     ÚLTIMO MOVIMIENTO AFILIATORIO
			     =================================================== -->

			<div id="datosMovimientoAfiliatorio" class="m-b-lg">


				<div class="titulo">


					<span> Datos de &uacute;ltimo movimiento afiliatorio: </span>


					<hr class="red m-b-none">


				</div>



				<form class="form-horizontal" role="form">


					<div class="form-group">


						<label class="col-sm-3 control-label"> Fecha de baja: </label>


						<div class="col-sm-3">


							<p class="form-control-static">


								<fmt:formatDate value="${fechaBaja}" pattern="dd/MM/yyyy" />


							</p>


						</div>



						<label class="col-sm-3 control-label"> &Uacute;ltimo
							salario registrado: </label>


						<div class="col-sm-3">


							<p class="form-control-static">


								<fmt:formatNumber value="${ultSdi}" type="currency" />


							</p>


						</div>


					</div>


				</form>


			</div>



			<!-- ===================================================
			     DETALLE COTIZACIÓN RETROACTIVIDAD
			     =================================================== -->

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


								<th>Salario base</th>


								<th>Pago</th>


								<th>Detalle</th>


							</tr>


						</thead>



						<tbody id="bodyCotizacionRetroactividad">


						</tbody>


					</table>


				</div>


			</div>



			<!-- ===================================================
			     TEXTO INFORMATIVO
			     =================================================== -->

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



	<!-- =========================================================
	     POPUP DETALLE PERIODOS
	     ========================================================= -->

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



	<!-- =========================================================
	     FORMULARIO IMPRESIÓN
	     ========================================================= -->

	<form:form id="impresionDocumentosForm"
		action="${contextPath}/wizard/continuacionVoluntaria/comunes/impresionDocumentos"
		method="post">


	</form:form>



	<!-- =========================================================
	     BOTONES
	     ========================================================= -->

	<div class="pie row">


		<div class="opciones col-sm-6"></div>



		<div class="col-sm-6 text-right">


			<button id="cancelarSolicitud" class="btn btn-danger"
				onclick="uid_call(
					'imss.gestion.seguro.voluntario.mod40.confirmarDatos.btn_cancelar',
					'clickout'
				);">


				Cancelar</button>



			<button id="siguientePaso" class="btn btn-primary"
				onclick="uid_call(
					'imss.gestion.seguro.voluntario.mod40.confirmarDatos.btn_finalizar',
					'clickout'
				);">


				Finalizar</button>


		</div>


	</div>



	<!-- =========================================================
	     DIÁLOGOS EXISTENTES
	     ========================================================= -->

	<div id="dialogoMsgSeleccion"></div>



	<div id="dialogoMsgCondiciones" style="width: 100%; height: 100%">


	</div>



	<div id="dialogoCancelarTramite"></div>



	<div id="dialog-confirm-cancelar"
		title="Confirmar cancelaci&oacute;n de solicitud">


		<p>


			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span> ¿Desea cancelar
			la solicitud pendiente con folio: <strong>

				${solicitud.numSolicitud} </strong> ?


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
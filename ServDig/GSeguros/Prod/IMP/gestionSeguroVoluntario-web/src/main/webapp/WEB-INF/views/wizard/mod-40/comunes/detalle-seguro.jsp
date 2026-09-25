<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
    a.print {
        color: inherit;
        text-decoration: none;
    }

    a.print:hover {
        color: black;
        text-decoration: none;
    }

    table.table {
        font-size: initial !important;
    }
</style>

<script src="<spring:url value='/static/resources/js/jquery-1.12.4.min.js'/>"></script>
<script src="<spring:url value='/static/resources/js/bootstrap.min.js'/>"></script>
<script src="<spring:url value='/static/resources/js/wizard/mod-40/comunes/detalle-seguro.js'/>"></script>


<script type="text/javascript"
        src="<spring:url value='/static/resources/js/wizard/mod-40/comunes/detalle-seguro.js' htmlEscape='true' />">
</script>

<script type="text/javascript">
    // Variables globales
    var idSeguroActual = '<c:out value="${seguro.cveIdSeguroIvro}"/>';
    var emailUsuario   = '<c:out value="${correoCVRO}"/>';
    var personaEsp   = '<c:out value="${persona}"/>';

    // Cuando el DOM este listo
    $(document).ready(function () {
        // Logica existente
        idSeguro = idSeguroActual;
        email    = emailUsuario;
        enviaComprobanteRenvacionCVRO(idSeguro, email);

        // Contador de caracteres para el motivo de baja expresa
        $('#motivoBajaExpresa').on('input', function () {
            var maxLength     = 500;
            var currentLength = $(this).val().length;
            var remaining     = maxLength - currentLength;
            $('#contadorMotivo').text(remaining);
        });
    });

    function enviaMultipago() {
        document.getElementById("envia_info_multipago").submit();
    }

    /**
     * Mostrar modal de confirmaci�n baja expresa
     */
    function mostrarModalBajaExpresa() {
        // Limpiar campos
        $('#motivoBajaExpresa').val('');
        $('#contadorMotivo').text('500');
        $('#mensajeErrorBaja').hide();

        // Mostrar modal Bootstrap
        $('#modalBajaExpresa').modal('show');
    }

    /**
     * Confirmar y enviar solicitud de baja expresa
     */
    function confirmarSolicitudBaja() {
        var motivo       = $('#motivoBajaExpresa').val().trim();
        var btnConfirmar = $('#btnConfirmarSolicitud');

        // Deshabilitar bot�n para evitar doble clic
        btnConfirmar.prop('disabled', true)
                    .html('<i class="fa fa-spinner fa-spin"></i> Enviando...');
        $('#mensajeErrorBaja').hide();

        // AJAX POST a controller
        $.ajax({
            url: '<spring:url value="/baja-expresa/solicitar" htmlEscape="true"/>',
            type: 'POST',
            data: {
                idSeguro: idSeguroActual,
                motivo: motivo
            },
            success: function (respuesta) {
                if (respuesta.exito) {
                    // Cerrar modal
                    $('#modalBajaExpresa').modal('hide');

                    // Mostrar mensaje de �xito VERDE en la p�gina principal
                    $('#mensajeExitoBajaPrincipal').html(
                        '<div style="text-align: center; padding: 15px;">' +
                            '<i class="fa fa-check-circle" style="font-size: 48px; color: #5cb85c;"></i><br/><br/>' +
                            '<strong style="font-size: 20px;">�Solicitud enviada exitosamente!</strong><br/><br/>' +
                            '<span style="font-size: 16px;">' + respuesta.mensaje + '</span>' +
                        '</div>'
                    ).fadeIn('slow');

                    // Scroll suave al mensaje
                    $('html, body').animate({
                        scrollTop: $('#mensajeExitoBajaPrincipal').offset().top - 100
                    }, 500);

                    // Deshabilitar bot�n de solicitar baja (ya se solicit�)
                    $('#btnSolicitarBajaExpresa').prop('disabled', true)
                                                 .removeClass('btn-danger')
                                                 .addClass('btn-default')
                                                 .html('<i class="fa fa-check"></i> Solicitud Enviada');

                    // Opcional: Ocultar mensaje despu�s de 10 segundos
                    // setTimeout(function() { $('#mensajeExitoBajaPrincipal').fadeOut(); }, 10000);
                } else {
                    // Mostrar error gen�rico en modal
                    $('#mensajeErrorBaja').html(
                        '<i class="fa fa-exclamation-circle"></i> ' +
                        'Ocurri� un error al procesar su solicitud, favor de intentarlo m�s tarde.'
                    ).show();
                }
            },
            error: function () {
                // Error de comunicaci�n
                $('#mensajeErrorBaja').html(
                    '<i class="fa fa-exclamation-circle"></i> Error de comunicaci�n con el servidor. ' +
                    'Por favor intente nuevamente.'
                ).show();
            },
            complete: function () {
                // Rehabilitar boton
                btnConfirmar.prop('disabled', false)
                            .html('<i class="fa fa-check"></i> Enviar Solicitud');
            }
        });
    }
    
    function iniciarVentanaTramiteNuevo(idpersonaParam) {
    	var _WizardAltaCVROCtrl;
    	_WizardAltaCVROCtrl = parent.WizardAltaCVROCtrl;
		var _idPersona = idpersonaParam;
		var _nssCifrado = $("#nssCifrado").val();
				
		_idPersona = _WizardAltaCVROCtrl.config.idPersona;		
		
		_WizardAltaCVROCtrl.init('divWizardContinuacionVoluntaria', _idPersona, _nssCifrado);
		_WizardAltaCVROCtrl.abrirConBajaExpresa();
    }
    
</script>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum"%>

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="codigoTrabajadorUrbano" value="<%=ModalidadEnum.TREINTAYCUATRO.getId()%>" />

<c:set var="estadoPagoPendiente" value="<%=EstadoPagoEnum.POR_PAGAR.getId()%>" />
<c:set var="estadoPagoActivo"    value="<%=EstadoPagoEnum.PAGADO.getId()%>" />
<c:set var="estadoPagoVencido"   value="<%=EstadoPagoEnum.VENCIDO.getId()%>" />
<c:set var="estadoActivo"        value="<%=EstadoSeguroIvroEnum.ACTIVO.getId()%>" /> 

<div class="contenedor col-sm-12 m-t-sm">
    <div id="errorNegocio"></div>
    <c:set var="defaultLocale" value="${pageContext.request.locale}" />
    <fmt:setLocale value="es_MX" scope="session" />

    <c:if test="${mensajeExito}">
        <div class="alert alert-success">
            La solicitud de inscripci&oacute;n en la continuaci&oacute;n voluntaria en el
            r&eacute;gimen obligatorio ha sido exitosa.
        </div>
    </c:if>

    <!-- ================== DATOS GENERALES DEL SEGURO ================== -->
    <div id="datosSeguro">
        <jsp:include page="encabezadoMod40.jsp">
            <jsp:param name="paso" value="4" />
        </jsp:include>

        <div class="titulo">
            <span>Datos del seguro</span>
            <hr class="red m-b-none">
        </div>

        <div class="row">
            <div class="col-xs-2">
                <strong>Contratante:</strong>
            </div>
            <div class="col-xs-8">${seguro.titular.nombre}</div>
            <div class="col-xs-2"></div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-2">
                <strong>Tipo seguro:</strong>
            </div>
            <div class="col-xs-2">Continuaci&oacute;n Voluntaria</div>
            <div class="col-xs-2">
                <strong>Modalidad:</strong>
            </div>
            <div class="col-xs-6">
                ${seguro.modalidad.numModalidad} - ${seguro.modalidad.descripcion}
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-2">
                <strong>Fecha inicio vigencia:</strong>
            </div>
            <div class="col-xs-2">
                <fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaInicio}" />
            </div>
            <div class="col-xs-2">
                <strong>Fecha fin vigencia:</strong>
            </div>
            <div class="col-xs-2">
                <fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaFin}" />
            </div>
            <div class="col-xs-1">
                <strong>Estado:</strong>
            </div>
            <div class="col-xs-3">
                <span class="${claseEstado}">${seguro.estadoSeguro.descripcion}</span>
            </div>
        </div>
    </div>

    <!-- (El bloque de integrantes con tramite sigue comentado; no controla nada) -->

    <%-- ==================== REQUERIMIENTO 6 - Detalle Reingreso RO ==================== --%>
<c:if test="${not empty detalleReingresoRO}">
        <div id="infoReingresoRO" class="m-t-xl">
            <div class="titulo">
                <span>Informaci&oacute;n del reingreso al R&eacute;gimen Obligatorio</span>
                <hr class="red m-b-none">
            </div>

            <div class="alert alert-info m-t-md" style="text-align: justify;">
                <strong><i class="glyphicon glyphicon-info-sign"></i> Importante:</strong>
                Su seguro fue dado de baja debido a su reingreso al R&eacute;gimen Obligatorio. 
                Si requiere m&aacute;s informaci&oacute;n consulte su reporte de Vigencia de Derechos 
                o solicite informaci&oacute;n en la Subdelegaci&oacute;n que corresponda a su domicilio o 
                en los medios electr&oacute;nicos que este Instituto pone a su disposici&oacute;n en la p&aacute;gina: 
                <a href="http://www.imss.gob.mx/contacto" target="_blank">http://www.imss.gob.mx/contacto</a>.
            </div>
        </div>
    </c:if>

    <%-- ==================== DETALLE BAJA POR FALTA DE PAGO (MORA) ==================== --%>
    <c:if test="${not empty detalleMora}">
        <div id="infoBajaMora" class="m-t-xl">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <i class="fa fa-exclamation-triangle"></i>
                        Informaci&oacute;n de baja por falta de pago
                    </h4>
                </div>
                <div class="panel-body">
                    <p class="text-warning">
                        <strong>Este seguro fue dado de baja por falta de pago.</strong>
                    </p>

                    <div class="table-responsive">
                        <table class="table table-bordered table-striped">
                            <tbody>
                                <tr>
                                    <th class="col-md-4">Fecha de baja:</th>
                                    <td class="col-md-8">
                                        <fmt:formatDate value="${detalleMora.fechaEfectivaBaja}"
                                                        pattern="dd/MM/yyyy" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>Meses sin pago:</th>
                                    <td>${detalleMora.mesesMora}</td>
                                </tr>
                                <tr>
                                    <th>Montos adeudados:</th>
                                    <td>
                                        <c:out value="${detalleMora.montosAdeudos}" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>Per&iacute;odos vencidos:</th>
                                    <td><c:out value="${detalleMora.periodosVencidos}" /></td>
                                </tr>
                                <tr>
                                    <th>Primer vencimiento:</th>
                                    <td>
                                        <fmt:formatDate value="${detalleMora.fechaPrimerVencimiento}"
                                                        pattern="dd/MM/yyyy" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>&Uacute;ltimo vencimiento:</th>
                                    <td>
                                        <fmt:formatDate value="${detalleMora.fechaUltimoVencimiento}"
                                                        pattern="dd/MM/yyyy" />
                                    </td>
                                </tr>
                                <c:if test="${not empty detalleMora.fechaUltimoPago}">
                                    <tr>
                                        <th>&Uacute;ltimo pago recibido:</th>
                                        <td>
                                            <fmt:formatDate value="${detalleMora.fechaUltimoPago}"
                                                            pattern="dd/MM/yyyy" />
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

                    <p class="text-muted small">
                        <i class="fa fa-info-circle"></i>                        
                    </p>
                </div>
            </div>
        </div>
    </c:if>

    <%-- ==================== DETALLE BAJA EXPRESA ==================== --%>
    <c:if test="${not empty detalleBajaExpresa}">
        <div id="infoBajaExpresa" class="m-t-xl">
        <br/>
            <div class="titulo">
                <span><strong>Informaci&oacute;n de la baja expresa en Modalidad 40</strong></span>
                <hr class="red m-b-none" style="margin: 0px 0 20px;">
            </div>

            <div class="alert alert-info m-t-md" style="text-align: justify;">
                <strong><i class="glyphicon glyphicon-info-sign"></i> Importante:</strong>
                Su seguro en Modalidad 40 fue dado de baja mediante una solicitud de baja expresa
                confirmada a trav&eacute;s del enlace enviado a su correo electr&oacute;nico.
            </div>

            <div class="row m-t-md">
                <div class="col-xs-6">
                    <div class="form-group">
                        <label><strong>Fecha de baja expresa:</strong></label>
                        <p class="form-control-static">
                            <fmt:formatDate value="${detalleBajaExpresa.fechaBajaExpresa}"
                                            pattern="dd 'de' MMMM 'de' yyyy" />
                        </p>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
    

    <%-- ==================== PAGOS ==================== --%>
    <div id="pagos" class="m-t-xl">
        <div class="titulo">
            <span>Pagos</span>
            <hr class="red m-b-none" style="margin: 0px 0 20px;">
        </div>
        <div id="recordatorio" class="m-t-md">
            <div class="alert alert-info" style="text-align: justify;">
                Obt&eacute;n el comprobante de tr&aacute;mite y las l&iacute;neas de captura
                para realizar los pagos correspondientes. Utiliza la banca electr&oacute;nica o
                acude a la sucursal de alguno de estos
                <a href="javascript:mostrarBancosPagos()">bancos</a>. Realiza los pagos antes de
                la fecha de vencimiento de las l&iacute;neas de captura. La falta de pago de tus
                l&iacute;neas de captura puede ocasionar la cancelaci&oacute;n de tu
                continuaci&oacute;n voluntaria en el r&eacute;gimen obligatorio del seguro social.
            </div>
        </div>

        <div id="tblSegurosWrapper" class="table-responsive">
            <table id="tblIvroSegurosIndivResume"
                   class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Fecha inicio</th>
                        <th>Fecha fin</th>
                        <th>Monto</th>
                        <th>Fecha l&iacute;mite pago</th>
                        <th>Estatus</th>
                        <c:if test="${imprimir}">
                            <th>Descargar</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${seguro.compra.pagos}" var="pago">
                        <tr>
                            <td>
                                <fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${pago.fechaInicioPeriodo}" />
                            </td>
                            <td>
                                <fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${pago.fechaFinPeriodo}" />
                            </td>
                            <td>
                                <fmt:formatNumber value="${pago.monto}" type="currency" />
                            </td>
                            <td>
                                <fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${pago.fechaLimitePago}" />
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoPendiente}">
                                        <c:set var="cssClassEdoPago" value="label-warning" />
                                    </c:when>
                                    <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoActivo}">
                                        <c:set var="cssClassEdoPago" value="label-success" />
                                    </c:when>
                                    <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoVencido}">
                                        <c:set var="cssClassEdoPago" value="label-danger" />
                                    </c:when>
                                </c:choose>
                                <span class="label ${cssClassEdoPago}">
                                    ${pago.estadoPago.descripcion}
                                </span>
                            </td>

                            <c:if test="${today.time lt (pago.fechaLimitePago.time + 86400000) 
                                         and (pago.imprimible 
                                              or (pago.estadoPago.idEstadoPago ne estadoPagoActivo
                                                  and pago.estadoPago.idEstadoPago ne estadoPagoVencido))
													and (empty detalleReingresoRO && empty detalleBajaExpresa)}">
                                <td class="text-center">
                                    <a class="link print"
                                       onclick="imprimePago(${pago.idPago})"
                                       title="Descargar">
                                        <i class="glyphicon glyphicon-download-alt fa-2x"></i>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ================== MODAL BAJA EXPRESA ================== -->
    <div id="modalBajaExpresa" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-danger">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">
                        <i class="fa fa-exclamation-triangle"></i> Confirmar Baja Expresa
                    </h4>
                </div>
                <div class="modal-body">
                    <p class="lead">
                        <strong>�Est&aacute; seguro que desea darse de baja de su seguro
                            de Continuaci&oacute;n Voluntaria?</strong>
                    </p>

                    <div class="alert alert-warning">
                        <i class="fa fa-warning"></i>
                        <strong>Advertencia:</strong> Esta acci&oacute;n es permanente.
                    </div>

                    <div class="form-group">
                        <label for="motivoBajaExpresa">Motivo de baja (opcional):</label>
                        <textarea id="motivoBajaExpresa" class="form-control" rows="3"
                                  maxlength="500"
                                  placeholder="Escriba el motivo por el cual solicita la baja (opcional)"></textarea>
                        <small class="text-muted">
                            Caracteres restantes: <span id="contadorMotivo">500</span>
                        </small>
                    </div>

                    <p class="text-info">
                        <i class="fa fa-envelope"></i>
                        Se enviar&aacute; un correo electr&oacute;nico a
                        <strong><c:out value="${correoCVRO}" /></strong> con un enlace de confirmaci&oacute;n.
                        La baja no ser&aacute; efectiva hasta que confirme desde el enlace (v&aacute;lido por 72 horas).
                    </p>

                    <div id="mensajeErrorBaja" class="alert alert-danger" style="display: none;">
                        <!-- Error message will be shown here -->
                    </div>
                    <div id="mensajeExitoBaja" class="alert alert-success" style="display: none;">
                        <!-- Success message will be shown here -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        Cancelar
                    </button>
                    <button type="button" id="btnConfirmarSolicitud"
                            class="btn btn-danger"
                            onclick="confirmarSolicitudBaja()">
                        <i class="fa fa-check"></i> Enviar Solicitud
                    </button>
                </div>
            </div>
        </div>
    </div>

    
    <!-- ================== MENSAJE DE �XITO BAJA EXPRESA ================== -->
    <div id="mensajeExitoBajaPrincipal" class="alert alert-success"
         style="display: none; margin: 20px 0; font-size: 16px;">
        <!-- Success message will be shown here -->
    </div>

<!-- ================== PIE DE P�GINA CON BOTONES ================== -->
    <div class="pie row">
        <div class="opciones col-sm-12 text-right">

            <c:if test="${seguro.estadoSeguro.idEstadoSeguro == estadoActivo}">
                <button type="button" id="btnSolicitarBajaExpresa"
                        class="btn btn-danger"
                        style="margin-right: 5px;"
                        onclick="mostrarModalBajaExpresa()">
                    <i class="fa fa-times-circle"></i> Solicitar Baja Expresa
                </button>
            </c:if>
            
            <c:if test="${not empty detalleBajaExpresa}">
            <a class="btn btn-primary" id="iniciarTramite"
               onclick="iniciarVentanaTramiteNuevo('${seguro.titular.idPersona}');">
                Iniciar tr&aacute;mite
            </a>            
            </c:if>

            <button class="btn btn-danger" id="cerrarWizard"
                    onclick="uid_call('imss.gestion.seguro.voluntario.mod40.detalleSeguro.btn_cerrar', 'clickout');">
                Cerrar
            </button>

            <a class="btn btn-primary" id="verComprobante" data-id="${ID_CIFRADO}"
               onclick="imprSegPerIvro(this); uid_call('imss.gestion.seguro.voluntario.mod40.detalleSeguro.btn_comprobante', 'download');">
                Obtener comprobante
            </a>

            <c:if test="${not empty jsonInfoPago}">
                <a class="btn btn-primary" onclick="enviaMultipago();">Pagar</a>
            </c:if>

        </div>
    </div>

    <fmt:setLocale value="${defaultLocale}" scope="session" />
</div>

<form id="envia_info_multipago"
      action="http://10.104.23.2:8001/multipagos/inicioMultipago"
      method="POST" target="_blank">
    <input type="hidden" id="infoPagoJSON" name="infoPagoJSON" value='${jsonInfoPago}'/>
</form>

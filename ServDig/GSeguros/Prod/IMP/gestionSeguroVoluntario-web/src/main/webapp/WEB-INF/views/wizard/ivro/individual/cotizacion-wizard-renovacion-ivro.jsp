<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FormaPagoEnum" %>
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript"
src="<spring:url value="/static/resources/js/wizard/individual/finaliza-ivro-individual.js" htmlEscape="true" />"></script>
<script type="text/javascript">
    $("#siguienteCotizacion").live('click', function (event) {
        event.preventDefault();
        $('#formCotIvro').submit();
    });

    $("#agregarDomicilio").live('click', function (event) {
        event.preventDefault();
        $('#otraUbicacionForm').submit();
    });

    $("#cerrarWizard, #cerrarWizardCot").live('click', function (event) {
        event.preventDefault();
        parent.WizardSeguroIvroIndivCtrl.cerrar();
    });
</script>
<script type="text/javascript">
    isInternet = ${internet};
    datosSol = {};
    datosSol.idTipoSolicitud = ${solicitud.tipoSolicitud.idTipoSolicitud};
    datosSol.descripcionTipoSolicitud = '${solicitud.tramite[0].tipoTramite.descripcion}';
    datosSol.folioSolicitud = '${solicitud.numSolicitud}';
    datosSol.idTipoTramite = [];
    datosSol.idTipoTramite[0] = ${solicitud.tramite[0].tipoTramite.idTipoTramite};
    datosEntradaFirma = {
        fechaElectronica: '${firmaEntrada.fechaElectronicaFormateada}',
        cadenaOriginal: '${firmaEntrada.cadenaOriginal}'
    };
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<c:set var="formaPagoAnual" value="<%=FormaPagoEnum.ANUAL.getId()%>" />
<c:set var="formaPagoBimestral" value="<%=FormaPagoEnum.BIMESTRAL.getId()%>" />

<div class="contenedor col-sm-12">
    <c:set var="defaultLocale" value="${pageContext.request.locale}" />
    <fmt:setLocale value="es_MX" scope="session" />

    <div class="contenido row" style="min-height: 400px;">
        <div class="col-sm-12">


            <!-- Mensaje de Error RISS -->
            <c:if test="${not empty datosRiss.errorFormGeneral}">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>

                    <c:choose>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Debido a cancelaci')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> Se ubica en los supuestos de terminaci&oacute;n del otorgamiento al subsidio, de conformidad con las disposiciones de car&aacute;cter general para la aplicaci&oacute;n del est&iacute;mulo fiscal al pago de las cuotas obrero patronales al Seguro Social.
                        </c:when>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'No cumple requisitos RIF')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> El SAT informa que no pertenece al R&eacute;gimen de Incorporaci&oacute;n Fiscal (RIF).
                        </c:when>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Error de comunicaci')}">
                            <strong>LO SENTIMOS, POR EL MOMENTO NO ES POSIBLE VERIFICAR SI PERTENCE AL R&Eacute;GIMEN DE INCORPORACI&Oacute;N FISCAL (RIF) ANTE EL SAT, PARA GOZAR DEL BENEFICIO DEL R&Eacute;GIMEN </strong>
                        </c:when>
                        <c:when test="${!fn:contains(datosRiss.errorFormGeneral, 'Ya cuentas')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> ${datosRiss.errorFormGeneral}
                        </c:when>
                        <c:otherwise>${datosRiss.errorFormGeneral}</c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <c:if test="${not empty cotizacion.errorFormGeneral}">
                <div style="min-height: 200px;">
                    <div class="alert alert-danger">${cotizacion.errorFormGeneral}</div>
                </div>
            </c:if>

            <c:if test="${not empty solicitud.errorFormGeneral}">
                <div style="min-height: 200px;">
                    <div class="alert alert-danger">${solicitud.errorFormGeneral}</div>
                </div>
            </c:if>

            <c:if test="${empty cotizacion.errorFormGeneral && empty solicitud.errorFormGeneral}">

            <div class="alert alert-success">
                Tu solicitud ha iniciado correctamente y tu n&uacute;mero de folio es: <strong>${solicitud.numSolicitud}</strong>
            </div>

            <div id="divVigencias">

                    <br>
                    <div class="titulo">
                        <span>Confirmar detalle de la cotizaci&oacute;n</span>
                    </div>

                    <div class="separadorseccion">
                        <span>Datos generales del solicitante</span>
                    </div>

                    <c:forEach var="emp" items="${cotizacion.detalle.empleados}">
                        <div class="row">
                            <div class="col-xs-5">
                                <strong>Nombre :</strong>
                            </div>
                            <div class="col-xs-6">${emp.nombreTrabajador}</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5">
                                <strong>N&uacute;mero de seguridad social :</strong>
                            </div>
                            <div class="col-xs-6">${emp.numeroSeguridadSocial}</div>
                        </div>                        
			<c:choose>
			  <c:when  test="${not empty correoIVRO}">
			    <div class="row">
				<div class="col-xs-5">
				  <strong>Correo electr&oacute;nico:</strong>
				</div>
				<div class="col-xs-6"><span>${correoIVRO}</span></div>
			    </div>
			  </c:when>
			  <c:otherwise>
			    <div class="row">
			      <div class="col-xs-5">
				<strong>Correo electr&oacute;nico :</strong>
			      </div>
			      <div class="col-xs-6">
				<%--<c:forEach items="${personaMC.mediosContacto}" var="medioContacto">
				  <div class="row">
				    <div class="col-xs-4">
				      <c:choose>
					<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto eq 1}">
					  <div class="col-xs-2">
					    <span>${medioContacto.desFormaContacto}</span>
                                          </div>
                                        </c:when>
                                      </c:choose>
                                    </div>
                                  </div>
                                </c:forEach> --%>
                                <c:forEach items="${personaMC.mediosContacto}" var="medioContacto">
				  <c:choose>
				    <c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto eq 1}">
				      <c:set var="correo" value="${medioContacto.desFormaContacto}" />                                        
				    </c:when>
                                  </c:choose>
                                </c:forEach>
                                <span>${correo}</span>
			      </div>
			    </div>
			  </c:otherwise>
			  </c:choose>
                        <div class="row">
                            <div class="col-xs-5">
                                <strong>Fecha de solicitud :</strong>
                            </div>
                            <div class="col-xs-6"><fmt:formatDate pattern="dd/MM/yyyy"
                                            value="${cotizacion.fecha}" /></div>
                        </div>

                        <div class="separadorseccion">
                            <span>Domicilio del solicitante</span>
                        </div>
                        <div class="row">
                            <div class="col-xs-2">
                                <strong>Calle :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.calle}</div>
                            <div class="col-xs-2">
                                <strong>N&uacute;m. Ext. :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.numExterior1}
                                ${domicilio.numExteriorAlf}</div>
                            <div class="col-xs-2">
                                <strong>N&uacute;m. Int. :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.numInterior}
                                ${domicilio.numInteriorAlf}</div>

                        </div>
                        <div class="row">
                            <div class="col-xs-2">
                                <strong>Colonia :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.colonia}</div>
                            <div class="col-xs-2">
                                <strong>Municipio :</strong>
                            </div>
                            <div class="col-xs-2">
                                <c:if
                                    test="${domicilio.localidad != null and domicilio.localidad.municipio != null}">
                                    ${domicilio.localidad.municipio.nombre}
                                </c:if>
                            </div>
                            <div class="col-xs-2">
                                <strong>C&oacute;digo Postal :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.codigoPostal}</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-2">
                                <strong>Estado :</strong>
                            </div>
                            <div class="col-xs-2">${domicilio.localidad.municipio.entidadFederativa.nombre}</div>

                        </div>




                        <br>
                        <div class="separadorseccion">
                            <span> Informaci&oacute;n cotizaci&oacute;n de la renovaci&oacute;n</span>
                        </div>

                        <div class="row">
                            <div class="col-xs-4">
                                <strong>Fecha inicio vigencia:</strong>
                            </div>
                            <div class="col-xs-2">
                                <fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${cotizacion.detalle.fechaInicioCalculo.time}" />
                            </div>
                            <div class="col-xs-4">
                                <strong>Fecha fin vigencia:</strong>
                            </div>
                            <div class="col-xs-2">
                                
                            <fmt:formatDate pattern="dd/MM/yyyy"
                                            value="${cotizacion.detalle.fechaFinCalculo.time}" />

                            </div>
                        </div>
                        <fmt:setLocale value="es_MX" />
                        <div class="row">
                            <div class="col-xs-4">
                                <strong>Cuota a pagar :</strong>
                            </div>
                            <div class="col-xs-2">
                                <c:choose>
                                    <c:when test="${cotizacion.detalle.empleados[0].conBeneficio}">
                                        <fmt:formatNumber
                                            value="${cotizacion.detalle.empleados[0].periodos[0].total}"
                                            type="currency" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber value="${cotizacion.detalle.cuotaTotal}"
                                                          type="currency" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-xs-4">
                                <strong>Forma de Pago :</strong>
                            </div>
                            <div class="col-xs-2">
                                <c:choose>
                                    <c:when test="${formaPago eq formaPagoAnual}">
                                        Anual
                                    </c:when>
                                    <c:when test="${formaPago eq formaPagoBimestral}">
                                        Bimestral
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                        <br>

                        <c:if test="${emp.conBeneficio}">
                            <div class="row">
                                <div class="alert alert-success">
                                    <span> <strong>Usted cuenta con beneficio RISS</strong>
                                    </span>
                                </div>
                            </div>


                        </c:if>
                           
                        <table class="table table-striped table-bordered" cellpadding="0"
                            cellspacing="0" border="0">
                            <thead>
                                <tr>
                                    <th>Fecha de inicio bimestre</th>
                                    <th>Fecha de fin bimestre</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="p" items="${emp.periodos}">
                                <tr>
                                    <td><fmt:formatDate pattern="dd/MM/yyyy"
                                            value="${p.inicioPeriodo.time}" /></td>
                                    <td><fmt:formatDate pattern="dd/MM/yyyy"
                                            value="${p.finPeriodo.time}" /></td>
                                    <td style="text-align: right;"><strong> <fmt:formatNumber
                                                value="${p.total}" type="currency" />
                                    </strong></td>
                                </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2"><strong>Importe de la cuota anual :</strong></td>
                                    <td style="text-align: right;">
                                        <h5>
                                            <fmt:formatNumber
                                        value="${emp.cuotaTotal}" type="currency" />
                                        </h5>
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                    </c:forEach>
                </div>
        </c:if>
    </div>
    <fmt:setLocale value="${defaultLocale}" scope="session" />
</div>
<form:form id="otraUbicacionForm" method="post"
           action="${contextpath}/wizard/individual/otraUbicacion"></form:form>
<form:form id="formCotIvro" method="post"
           action="${contextpath}/wizard/individual/aplicaCuestionario"></form:form>
    <br>	
    <div id="cancelar-dialogo" title="Cancelaci&oacute;n de solicitud">
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
            solicitud pendiente?
        </p>
    </div>

    <div id="confirmar-dialogo" title="Notificaci&oacute;n de cancelaci&oacute;n">
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> Tu solicitud fue cancelada
            exitosamente.
        </p>
    </div>
    <div id="dialog-confirm" title="Aviso">
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> A continuaci&oacute;n se te
            pedir&aacute; la informaci&oacute;n de tu FIEL para finalizar el tr&aacute;mite.
        </p>
    </div>
	<br>
    <div class="pie row">
        <div class="opciones col-sm-6">
            <button id="agregarDomicilio" class="btn btn-primary">Modificar
                Domicilio</button>
        </div>
        <div class="controles col-sm-6">
            <div class="pull-right">
            <c:if test="${empty cotizacion.errorFormGeneral}">
                <button class="btn btn-default" id="cancelarTramite">Cancelar</button>
                <a id="terminarTramite" class="btn btn-primary"> <i
                        class="glyphicon glyphicon-ok"></i> Finalizar
                </a>
            </c:if>
            <c:if test="${not empty cotizacion.errorFormGeneral}">
                <button class="btn btn-default" id="cerrarWizard">Cerrar</button>
            </c:if>
        </div>
    </div>
</div>